package co.matisses.bcs.sync;

import co.matisses.bcs.dto.MailMessageDTO;
import co.matisses.bcs.dto.MercadolibreItemDTO;
import co.matisses.bcs.mbean.BCSApplicationMBean;
import co.matisses.bcs.mbean.BCSGenericMBean;
import co.matisses.bcs.mbean.ImagenProductoMBean;
import co.matisses.bcs.mongodb.BrandMongoDBFacade;
import co.matisses.bcs.mongodb.ColorMongoDBFacade;
import co.matisses.bcs.mongodb.ItemDataMongoDBFacade;
import co.matisses.bcs.mongodb.ItemStockMongoDBFacade;
import co.matisses.bcs.mongodb.MaterialMongoDBFacade;
import co.matisses.bcs.mongodb.MongoDBClient;
import co.matisses.bcs.rest.MercadoLibreClient;
import co.matisses.bcs.rest.SendHtmlEmailREST;
import co.matisses.persistence.sap.facade.ItemInventarioFacade;
import co.matisses.persistence.web.entity.ProgramacionDescuento;
import co.matisses.persistence.web.facade.ProgramacionDescuentoFacade;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author dbotero
 */
@Stateless
@Path("itemdata")
public class ItemDataService implements Serializable {

    @Inject
    private BCSApplicationMBean appBean;
    @Inject
    private BCSGenericMBean bcsGenericMBean;
    @Inject
    private ImagenProductoMBean imagenProductoMBean;
    private static final Logger CONSOLE = Logger.getLogger(ItemDataService.class.getSimpleName());
    @EJB
    private SendHtmlEmailREST emailREST;
    @EJB
    private ItemInventarioFacade itemFacade;
    @EJB
    private ProgramacionDescuentoFacade descuentoFacade;

    @GET
    @Path("get/{itemcode}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    public Response consultar(@PathParam("itemcode") String itemcode, @QueryParam("model") Boolean wholeModel) throws ParseException {
        List<ItemDataDTO> items = queryAndParseData(Arrays.asList(itemcode), wholeModel == null ? false : wholeModel);
        if (items.size() == 1) {
            return Response.ok(items.get(0)).build();
        } else {
            return Response.ok(items).build();
        }
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private List<ItemDataDTO> queryAndParseData(List<String> referencias, boolean includeAllModel) throws ParseException {
        CONSOLE.log(Level.INFO, "Consultando informacion en BD para {0} items", referencias.size());
        HashMap<String, Integer> posicionItems = new HashMap<>();
        List<ItemDataDTO> items = new ArrayList<>();
        List<String> itemcodes = new ArrayList<>();
        if (referencias != null && !referencias.isEmpty()) {
            if (includeAllModel) {
                itemcodes = itemFacade.obtenerReferenciasSimilares(referencias);
                if (itemcodes.isEmpty()) {
                    itemcodes.addAll(referencias);
                }
            } else {
                itemcodes.addAll(referencias);
            }
        } else {
            //TODO: comentado por cierre de tienda Bogota. 2018-01-15.
            //itemcodes.addAll(itemFacade.obtenerReferenciasConSaldo());
            itemcodes.addAll(itemFacade.obtenerReferenciasConSaldoMED());
        }
        CONSOLE.log(Level.INFO, "Procesando informacion de {0} items", itemcodes.size());
        int item = 1;
        for (String ref : itemcodes) {
            if (item++ % 50 == 0) {
                CONSOLE.log(Level.INFO, "Procesando item {0} de {1}", new Object[]{item, itemcodes.size()});
            }
            List<Object[]> results = itemFacade.consultarItem(ref, false);
            for (Object[] cols : results) {
                //Object cols[] = (Object[]) result;
                ItemDataDTO invItem = processQueryResult(cols);
                if (posicionItems.containsKey(invItem.getItemCode())) {
                    //el item ya se agrego a la lista, usar metodo merge
                    items.get(posicionItems.get(invItem.getItemCode())).mergeStockAndMaterials(invItem);
                } else {
                    //el item no se ha agregado a la lista
                    posicionItems.put(invItem.getItemCode(), items.size());
                    items.add(invItem);
                }
            }
        }
        return items;
    }

    private ItemDataDTO processQueryResult(Object cols[]) throws ParseException {
        int col = 0;

        String itemCode = (String) cols[col++];
        String itemName = (String) cols[col++];
        BigDecimal precioConIVA = (BigDecimal) cols[col++];
        BigDecimal precioSinIVA = (BigDecimal) cols[col++];
        BigDecimal porcentajeIVA = (BigDecimal) cols[col++];
        String department = (String) cols[col++];
        String departmentName = (String) cols[col++];
        String group = (String) cols[col++];
        String groupName = (String) cols[col++];
        String subgroup = (String) cols[col++];
        String subgroupName = (String) cols[col++];

        Integer height = (Integer) cols[col++];
        Integer depth = (Integer) cols[col++];
        Integer width = (Integer) cols[col++];
        Integer weight = (Integer) cols[col++];

        String warehouse = (String) cols[col++];
        Integer quantity = (Integer) cols[col++];
        String model = (String) cols[col++];
        String colorCode = (String) cols[col++];
        String colorName = (String) cols[col++];
        String colorHexa = (String) cols[col++];
        String materialCode = (String) cols[col++];
        String materialName = (String) cols[col++];
        String materialCare = (String) cols[col++];
        String description = (String) cols[col++];
        Date newFrom = (Date) cols[col++];
        String brandCode = (String) cols[col++];
        String brandName = (String) cols[col++];
        String collectionName = (String) cols[col++];
        String urlMercadoLibre = null;

        try {
            MercadoLibreClient client = new MercadoLibreClient(appBean.obtenerValorPropiedad("mdolibre.service.url"));

            Response res = client.obtenerDatosItem((String) cols[col++]);
            MercadolibreItemDTO dto = res.readEntity(MercadolibreItemDTO.class);
            urlMercadoLibre = dto.getPermaLink();
        } catch (Exception e) {
        }
        Boolean showPrice = ((String) cols[col++]).equals("Y");
        Boolean discount = false;

        try {
            ProgramacionDescuento p = descuentoFacade.consultarDescuentos("WE", itemCode, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + " 00:00:00"),
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + " 23:59:59"));
            if (p != null && p.getIdReglaDescuento() != null && p.getIdReglaDescuento() != 0) {
                discount = true;
            } else {
                discount = false;
            }
        } catch (Exception e) {
        }

        ItemDataDTO invItem = new ItemDataDTO();
        invItem.setItemCode(itemCode);
        invItem.setShortItemCode(itemCode.substring(0, 3).concat(itemCode.substring(16)));
        invItem.setItemName(itemName);
        invItem.setDepartment(new BasicDTO(department, departmentName.replaceAll("_", " ")));
        invItem.setGroup(new BasicDTO(group, groupName.replaceAll("_", " ")));
        invItem.setSubgroup(new BasicDTO(subgroup, subgroupName));
        invItem.setCollection(collectionName);
        invItem.setPriceAfterVAT(precioConIVA != null ? precioConIVA.doubleValue() : 0);
        invItem.setPriceBeforeVAT(precioSinIVA != null ? precioSinIVA.doubleValue() : 0);
        invItem.setTaxPercent(porcentajeIVA != null ? porcentajeIVA.doubleValue() : 0);
        invItem.setDimensions(new ItemDataDimensionsDTO(depth != null ? depth : 0, width != null ? width : 0, height != null ? height : 0));
        invItem.setWeight(weight != null ? weight : 0);
        invItem.setModel(model);
        invItem.setDescription(description);
        invItem.setUrlMercadolibre(urlMercadoLibre);
        invItem.setShowPrice(showPrice);
        invItem.setDiscount(discount);

        if (newFrom != null) {
            invItem.setNewFrom(Long.toString(newFrom.getTime()));
        }
        invItem.addStock(warehouse, quantity);

        ItemDataColorDTO itemColor = new ItemDataColorDTO(null, colorCode, colorName, colorHexa, null);
        invItem.setColor(itemColor);

        ItemDataMaterialDTO itemMaterial = new ItemDataMaterialDTO(null, materialCode, materialName, materialCare);
        invItem.addMaterial(itemMaterial);

        ItemDataBrandDTO itemBrand = new ItemDataBrandDTO(null, brandCode, brandName);
        invItem.setBrand(itemBrand);

        return invItem;
    }

    @GET
    @Path("syncimages")
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response sincronizarImagenes(@QueryParam("itemcode") String itemCode) {
        if (itemCode == null) {
            return Response.ok().build();
        }
        List<String> items = new ArrayList<>();
        HashMap<String, List<String>> res = new HashMap<>();
        if (itemCode.isEmpty()) {
            //items = itemFacade.obtenerReferenciasConSaldo();
            items = itemFacade.obtenerReferenciasConSaldoMED();
        } else {
            items.add(itemCode);
        }

        MongoClient mongo = MongoDBClient.crearConexion();
        long startTime = System.currentTimeMillis();
        CONSOLE.log(Level.INFO, "Inicia proceso de sincronizacion de articulo(s)...");

        for (String s : items) {
            ItemDataMongoDBFacade itemMongoFacade = new ItemDataMongoDBFacade();

            HashMap<String, String> cond = new HashMap<>();
            cond.put("itemcode", s);

            HashMap<String, ItemDataDTO> result = itemMongoFacade.consultar(mongo.getDatabase("matisses"), cond);
            if (!result.isEmpty()) {
                List<String> images = listImages(s);
                res.put(s, images);
                itemMongoFacade.actualizarImagenes(mongo.getDatabase("matisses"), s, images);
            } else {
                CONSOLE.log(Level.WARNING, "No se encontro la referencia {0} en MongoDB.", s);
            }
        }

        mongo.close();
        CONSOLE.log(Level.INFO, "Finaliza proceso de sincronizacion de las imagenes. {0}[ms]", (System.currentTimeMillis() - startTime));
        return Response.ok(res).build();
    }

    @POST
    @Path("syncitem")
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response sincronizarItems(@QueryParam("informe") boolean informe, List<String> referencias) throws ParseException {
        if (referencias == null) {
            return Response.ok().build();
        }
        long startTime = System.currentTimeMillis();
        CONSOLE.log(Level.INFO, "Inicia proceso de sincronizacion de articulo(s)...");
        MongoClient mongo = MongoDBClient.crearConexion();

        HashMap<String, ItemDataMaterialDTO> materials = cargarMateriales(mongo.getDatabase("matisses"));
        HashMap<String, ItemDataColorDTO> colors = cargarColores(mongo.getDatabase("matisses"));
        HashMap<String, ItemDataGenericColorDTO> genericColors = cargarColoresGenericos(mongo.getDatabase("matisses"));
        HashMap<String, ItemDataBrandDTO> marcas = cargarMarcas(mongo.getDatabase("matisses"));

        //1. Consultar el articulo en SAP y los productos con el mismo modelo y procesar datos
        List<ItemDataDTO> items = queryAndParseData(referencias, true);
        CONSOLE.log(Level.INFO, "Iniciando sincronizacion de {0} items", items.size());
        int item = 1;
        for (ItemDataDTO dto : items) {
            if (item++ % 50 == 0) {
                CONSOLE.log(Level.INFO, "Procesando item {0} de {1}", new Object[]{item, items.size()});
            }
            //2. Asignar IDs de materiales correspondientes de MongoDB
            for (ItemDataMaterialDTO material : dto.getMaterials()) {
                try {
                    //CONSOLE.log(Level.INFO, "  material {0}=>{1}", new Object[]{material.getId(), materials.get(material.getCode()).getId()});
                    material.setId(materials.get(material.getCode()).getId());
                } catch (Exception e) {
                    CONSOLE.log(Level.SEVERE, "No se pudo asignar el id del material " + material.getCode() + " ya que no se encuentra en la base de datos", e);
                }
            }

            try {
                //3. Asignar ID de color correspondiente de MongoDB
                //CONSOLE.log(Level.INFO, "  color {0}=>{1}", new Object[]{dto.getColor().getId(), colors.get(dto.getColor().getCode()).getId()});
                dto.setColor(colors.get(dto.getColor().getCode()));
            } catch (Exception e) {
                CONSOLE.log(Level.SEVERE, "No se pudo asignar el id del color " + dto.getColor() + " ya que no se encuentra en la base de datos", e);
            }

            try {
                //4. Asignar ID de marca correspondiente de MongoDB
                //CONSOLE.log(Level.INFO, "  marca {0}=>{1}", new Object[]{dto.getBrand().getId(), marcas.get(dto.getBrand().getCode()).getId()});
                dto.getBrand().setId(marcas.get(dto.getBrand().getCode()).getId());
            } catch (Exception e) {
                CONSOLE.log(Level.SEVERE, "No se pudo asignar el id de la marca " + dto.getBrand() + " ya que no se encuentra en la base de datos", e);
            }

            //5 Guardar documento en bd
            List<ItemDataStockDTO> stock = dto.getStock();
            dto.setStock(null);
            dto.setAvailableStock(sumTotalStock(stock));
            dto.setImages(listImages(dto.getItemCode()));
            ItemDataMongoDBFacade itemMongoFacade = new ItemDataMongoDBFacade();
            itemMongoFacade.insertualizar(mongo.getDatabase("matisses"), dto.toDocument());
            //itemMongoFacade.agregar(Arrays.asList(dto.toDocument()));

            //6. Sincronizar informacion de stock
            //CONSOLE.log(Level.INFO, "stock {0}: {1}", new Object[]{dto.getItemCode(), stock});
            mergeStock(mongo.getDatabase("matisses"), dto.getItemCode(), stock);
        }
        mongo.close();
        CONSOLE.log(Level.INFO, "Finaliza proceso de sincronizacion de articulo. {0}[ms]", (System.currentTimeMillis() - startTime));

        if (informe) {
            enviarNotificacion(items);
        }

        return Response.ok(items).build();
    }

    private void enviarNotificacion(List<ItemDataDTO> items) {
        MailMessageDTO mail = new MailMessageDTO();

        mail.setFrom("Sonda página web <notificaciones@matisses.co>");
        mail.setSubject("Sonda página web");
        mail.addToAddress(appBean.getDestinatariosPlantillaEmail().get("sonda_pagina").getTo().get(0));
        mail.setTemplateName(appBean.getDestinatariosPlantillaEmail().get("sonda_pagina").getTemplateName());

        Map<String, String> params = new HashMap<>();

        params.put("fecha", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        params.put("hora", new SimpleDateFormat("HH:mm:ss").format(new Date()));
        params.put("totalItems", String.valueOf(items.size()));

        StringBuilder sb = new StringBuilder();
        sb.append("<table style=\"width: 60%; border-collapse: collapse\">");
        sb.append("<tr style=\"background: #DDDDDD\">");
        sb.append("<th style=\"padding: 10px; border: 1px solid #DDDDDD\">Imagen</th>");
        sb.append("<th style=\"padding: 10px; border: 1px solid #DDDDDD\">Referencia</th>");
        sb.append("<th style=\"padding: 10px; border: 1px solid #DDDDDD\">Datos</th>");
        sb.append("</tr>");

        for (ItemDataDTO i : items) {
            sb.append("<tr>");
            sb.append("<td style=\"border: 1px solid #DDDDDD\"><img style=\"width: 103px; height: 83px;\" src=\"");
            sb.append(imagenProductoMBean.obtenerUrlProducto(i.getItemCode(), true));
            sb.append("\"></img></td>");
            sb.append("<td style=\"border: 1px solid #DDDDDD\">");
            sb.append(bcsGenericMBean.convertirARefCorta(i.getItemCode()));
            sb.append("</td>");
            sb.append("<td style=\"border: 1px solid #DDDDDD\">");
            sb.append(i.toString());
            sb.append("</td>");
            sb.append("</tr>");
        }

        sb.append("</table>");
        params.put("tablaItems", sb.toString());

        mail.setParams(params);

        try {
            emailREST.sendMail360(mail);
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "", e);
        }
    }

    private List<String> listImages(String itemCode) {
        List<String> images = new ArrayList<>();
        String carpetaImagenes = String.format(appBean.obtenerValorPropiedad("url.local.catalogo.dir"), itemCode);
        File folder = new File(carpetaImagenes);
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles((File path) -> (!path.isHidden() && path.getPath().contains(".jpg")));
            for (File file : files) {
                images.add(file.getName());
            }
        }
        Collections.sort(images);
        CONSOLE.log(Level.INFO, "Se encontraron {0} imagenes para la referencia {1}", new Object[]{images.size(), itemCode});
        return images;
    }

    private Integer sumTotalStock(List<ItemDataStockDTO> stock) {
        int totalStock = 0;
        for (ItemDataStockDTO stockLine : stock) {
            totalStock += stockLine.getQuantity();
        }
        return totalStock;
    }

    private HashMap<String, ItemDataMaterialDTO> cargarMateriales(MongoDatabase db) {
        try {
            MaterialMongoDBFacade materialMongoFacade = new MaterialMongoDBFacade();
            return materialMongoFacade.consultar(db, null);
        } catch (Exception e) {
            return null;
        }
    }

    private HashMap<String, ItemDataColorDTO> cargarColores(MongoDatabase db) {
        try {
            ColorMongoDBFacade colorMongoFacade = new ColorMongoDBFacade();
            return colorMongoFacade.consultar(db, null);
        } catch (Exception e) {
            return null;
        }
    }

    private HashMap<String, ItemDataGenericColorDTO> cargarColoresGenericos(MongoDatabase db) {
        try {
            ColorMongoDBFacade colorMongoFacade = new ColorMongoDBFacade();
            return colorMongoFacade.consultarGenericos(db, null);
        } catch (Exception e) {
            return null;
        }
    }

    private HashMap<String, ItemDataBrandDTO> cargarMarcas(MongoDatabase db) {
        try {
            BrandMongoDBFacade marcaMongoFacade = new BrandMongoDBFacade();
            return marcaMongoFacade.consultar(db, null);
        } catch (Exception e) {
            return null;
        }
    }

    private void mergeStock(MongoDatabase db, String itemCode, List<ItemDataStockDTO> stock) {
        HashMap<String, String> condiciones = new HashMap<>();
        condiciones.put("itemCode", itemCode);
        ItemStockMongoDBFacade stockFacade = new ItemStockMongoDBFacade();
        stockFacade.marcarSinInventario(db, itemCode);
        stockFacade.actualizarMultiple(db, stock);
    }
}
