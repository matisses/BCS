package co.matisses.bcs.rest;

import co.matisses.bcs.b1ws.client.invoices.InvoiceServiceException;
import co.matisses.bcs.b1ws.client.invoices.InvoicesServiceConnector;
import co.matisses.b1ws.dto.SalesDocumentDTO;
import co.matisses.b1ws.dto.SalesDocumentLineBinAllocationDTO;
import co.matisses.b1ws.dto.SalesDocumentLineDTO;
import co.matisses.b1ws.dto.SalesEmployeeDTO;
import co.matisses.bcs.b1ws.client.businesspartners.BusinessPartnerAddressDTO;
import co.matisses.bcs.b1ws.client.businesspartners.BusinessPartnerDTO;
import co.matisses.bcs.b1ws.client.businesspartners.BusinessPartnersServiceConnector;
import co.matisses.bcs.dto.ActualizarImagenesItemDTO;
import co.matisses.bcs.dto.AgregarAtributosMdoLibreDTO;
import co.matisses.bcs.dto.CategoriaMercadolibreDTO;
import co.matisses.bcs.dto.ConfiguracionEnvioDTO;
import co.matisses.bcs.dto.ConsultaProductosMercadolibreDTO;
import co.matisses.bcs.dto.EmailTemplateDestinationDTO;
import co.matisses.bcs.dto.MailMessageDTO;
import co.matisses.bcs.dto.MercadoLibreItemModificarDTO;
import co.matisses.bcs.dto.MercadolibreAccessCodeResponseDTO;
import co.matisses.bcs.dto.MercadolibrePublicarItemDTO;
import co.matisses.bcs.dto.MercadolibrePublicacionDTO;
import co.matisses.bcs.dto.ModificarDescripcionMercadolibreDTO;
import co.matisses.bcs.dto.NotificacionMercadolibreDTO;
import co.matisses.bcs.dto.OrdenMercadoLibreDTO;
import co.matisses.bcs.dto.PagoMercadoLibreDTO;
import co.matisses.bcs.dto.ProductoMercadoLibreDTO;
import co.matisses.bcs.dto.ResponseDTO;
import co.matisses.bcs.mbean.BCSApplicationMBean;
import co.matisses.bcs.mbean.ImagenProductoMBean;
import co.matisses.bcs.mbean.SAPB1WSBean;
import co.matisses.jasperbeans.ProductoMercadolibreFactory;
import co.matisses.persistence.sap.entity.BaruSubgrupo;
import co.matisses.persistence.sap.entity.DireccionSocioDeNegocios;
import co.matisses.persistence.sap.entity.DireccionSocioDeNegociosPK;
import co.matisses.persistence.sap.entity.FacturaSAP;
import co.matisses.persistence.sap.entity.ItemInventario;
import co.matisses.persistence.sap.entity.SocioDeNegocios;
import co.matisses.persistence.sap.facade.AlmacenFacade;
import co.matisses.persistence.sap.facade.BaruSubgrupoFacade;
import co.matisses.persistence.sap.facade.DireccionSocioDeNegociosFacade;
import co.matisses.persistence.sap.facade.FacturaSAPFacade;
import co.matisses.persistence.sap.facade.ItemInventarioFacade;
import co.matisses.persistence.sap.facade.MunicipioFacade;
import co.matisses.persistence.sap.facade.SaldoUbicacionFacade;
import co.matisses.persistence.sap.facade.SocioDeNegociosFacade;
import co.matisses.persistence.web.entity.CategoriaMercadolibre;
import co.matisses.persistence.web.entity.ProgramacionDescuento;
import co.matisses.persistence.web.entity.TokenMercadoLibre;
import co.matisses.persistence.web.facade.CategoriaMercadolibreFacade;
import co.matisses.persistence.web.facade.ProgramacionDescuentoFacade;
import co.matisses.persistence.web.facade.TokenMercadoLibreFacade;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrSubstitutor;

/**
 *
 * @author dbotero
 */
@Stateless
@Path("mercadolibre")
public class MercadoLibreREST {

    private static final Logger CONSOLE = Logger.getLogger(MercadoLibreREST.class.getSimpleName());
    private static final String TOPIC_PAYMENTS = "payments";
    private static final String DIRECCION_MERCADOLIBRE = "MERCADOLIBRE";
    private static final String PAIS_COLOMBIA = "CO";
    @Inject
    private SAPB1WSBean sapB1MBean;
    @Inject
    private BCSApplicationMBean applicationMBean;
    @Inject
    private SendHtmlEmailREST emailREST;
    @Inject
    private ImagenProductoMBean imagenProductoMBean;
    @EJB
    private ItemInventarioFacade itemFacade;
    @EJB
    private BaruSubgrupoFacade subgrupoFacade;
    @EJB
    private ProgramacionDescuentoFacade descuentoFacade;
    @EJB
    private TokenMercadoLibreFacade tokenFacade;
    @EJB
    private SocioDeNegociosFacade clienteFacade;
    @EJB
    private DireccionSocioDeNegociosFacade direccionFacade;
    @EJB
    private MunicipioFacade municipioFacade;
    @EJB
    private AlmacenFacade almacenFacade;
    @EJB
    private SaldoUbicacionFacade saldoUbicacionFacade;
    @EJB
    private FacturaSAPFacade facturaSAPFacade;
    @EJB
    private CategoriaMercadolibreFacade categoriaMercadolibreFacade;
    @EJB
    private ProgramacionDescuentoFacade decuentosFacade;

    public MercadoLibreREST() {
    }

    public BCSApplicationMBean getApplicationMBean() {
        return applicationMBean;
    }

    public void setApplicationMBean(BCSApplicationMBean applicationMBean) {
        this.applicationMBean = applicationMBean;
    }

    public SendHtmlEmailREST getEmailREST() {
        return emailREST;
    }

    public void setEmailREST(SendHtmlEmailREST emailREST) {
        this.emailREST = emailREST;
    }

    @GET
    @Path("urlAutenticacion")
    @Produces({MediaType.APPLICATION_JSON})
    public Response obtenerURLAutenticacion() {
        CONSOLE.log(Level.INFO, "Obteniendo URL para autenticacion de usuario en mercadolibre");
        try {
            StringBuilder sb = new StringBuilder(applicationMBean.obtenerValorPropiedad("mdolibre.auth.url"));
            sb.append("authorization?response_type=code&client_id=");
            sb.append(applicationMBean.obtenerValorPropiedad("mdolibre.client_id"));
            sb.append("&redirect_uri=");
            sb.append(URLEncoder.encode(applicationMBean.obtenerValorPropiedad("mdolibre.redirect.uri"), "UTF-8"));
            CONSOLE.log(Level.INFO, "La URL para autenticacion en mercadolibre es {0}", sb.toString());
            return Response.ok(sb.toString()).build();
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al armar la URL de autenticacion en mercadolibre. ", e);
            return Response.serverError().build();
        }
    }

    @GET
    @Path("procesarAuthCode")
    public Response procesarAuthCode(@QueryParam("code") String code) {
        CONSOLE.log(Level.INFO, "Procesando codigo de autorizacion de mercadolibre {0}", code);
        if (code == null) {
            return Response.serverError().build();
        }
        MercadolibreAccessCodeResponseDTO response = obtenerAccessToken(code);
        //TODO: notificar creacion de nuevo token?
        return Response.temporaryRedirect(URI.create(applicationMBean.obtenerValorPropiedad("mdolibre.redirect.authorization.url"))).build();
    }

    @GET
    @Path("validartoken")
    public Response validarToken() {
        MercadolibreAccessCodeResponseDTO dto = cargarTokenJson();
        return Response.ok("Token valido " + dto.toString()).build();
    }

    private MercadolibreAccessCodeResponseDTO renovarAccessToken(String refreshToken) {
        CONSOLE.log(Level.INFO, "El access_token esta vencido. Intentando renovarlo. ");

        MercadoLibreClient client = new MercadoLibreClient(applicationMBean.obtenerValorPropiedad("mdolibre.service.url"), "oauth/token");
        MercadolibreAccessCodeResponseDTO dto = client.renovarAccessToken(applicationMBean.obtenerValorPropiedad("mdolibre.client_id"),
                applicationMBean.obtenerValorPropiedad("mdolibre.client_secret"), refreshToken);
        if (dto == null) {
            return null;
        }
        guardarToken(dto);
        return dto;
    }

    private MercadolibreAccessCodeResponseDTO obtenerAccessToken(String code) {
        CONSOLE.log(Level.INFO, "Inicia proceso para obtener token de acceso");
        try {
            MercadoLibreClient client = new MercadoLibreClient(applicationMBean.obtenerValorPropiedad("mdolibre.service.url"), "oauth/token");
            Response res = client.obtenerAccessToken(applicationMBean.obtenerValorPropiedad("mdolibre.client_id"),
                    applicationMBean.obtenerValorPropiedad("mdolibre.client_secret"), code, applicationMBean.obtenerValorPropiedad("mdolibre.redirect.uri"));

            MercadolibreAccessCodeResponseDTO response = res.readEntity(MercadolibreAccessCodeResponseDTO.class);
            CONSOLE.log(Level.INFO, "Token de acceso recibido. {0}", response);
            guardarToken(response);
            return response;
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al obtener el token de acceso de mercadolibre. ", e);
        }
        return null;
    }

    private void guardarToken(MercadolibreAccessCodeResponseDTO jsonObject) {
        if (jsonObject == null) {
            return;
        }
        try {
            if (jsonObject.getSysTime() == 0) {
                jsonObject.setSysTime(System.currentTimeMillis());
            }
            TokenMercadoLibre tokenEntidad = new TokenMercadoLibre();
            tokenEntidad.setAccessToken(jsonObject.getAccessToken());
            tokenEntidad.setExpiresIn(jsonObject.getExpiresIn());
            tokenEntidad.setRefreshToken(jsonObject.getRefreshToken());
            tokenEntidad.setScope(jsonObject.getScope());
            tokenEntidad.setSystime(jsonObject.getSysTime());
            tokenEntidad.setUserId(jsonObject.getUserId());

            tokenFacade.create(tokenEntidad);

            CONSOLE.log(Level.INFO, "Se guardo la informacion del token de mercadolibre en bd {0}", tokenEntidad);
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al guardar el nuevo token de mercado libre. ", e);
        }
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void ejecutarSonda(boolean informe, List<String> result, boolean soloMedellin) {
        try {
            File file = new File(applicationMBean.obtenerValorPropiedad("mdolibre.log.url"));
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            //List<String> result = itemFacade.obtenerCambiosItem(minutos);

            if (result != null && !result.isEmpty()) {
                int errores = 0;
                for (String item : result) {
                    //TODO: Envia true para procesar automaticamente solo los productos que tengan saldo en medellin
                    ResponseDTO res = procesarItem(item, soloMedellin);

                    if (res.getEstado() < 0) {
                        errores++;
                    }

                    String mini = imagenProductoMBean.obtenerUrlProducto(item, true);

                    StringBuilder linea = new StringBuilder();
                    linea.append(mini);
                    linea.append("\t");
                    linea.append(item);
                    linea.append("\t");
                    linea.append(res.getMensaje());

                    bw.write(StringUtils.replace(linea.toString(), "\n", " "));
                    bw.newLine();
                }
                bw.close();
                fw.close();

                if (informe) {
                    //TODO: llamando el jasper
                    String report = JasperCompileManager.compileReportToFile(applicationMBean.obtenerValorPropiedad("url.jasper.notificacion.mercadolibre"));
                    JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(ProductoMercadolibreFactory.consultarDetalleCarga());

                    JasperPrint jasperPrint = JasperFillManager.fillReport(report, null, ds);

                    try {
                        JRExporter exporter = new JRPdfExporter();
                        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                        exporter.setParameter(JRExporterParameter.OUTPUT_FILE, new File(System.getProperty("jboss.server.temp.dir") + File.separator + "productosProcesadosMercadolibre.pdf"));
                        exporter.exportReport();
                    } catch (Exception e) {
                        CONSOLE.log(Level.SEVERE, "Ocurrio un error al guardar el PDF del reporte. ", e);
                    }
                    enviarNotificacion(result.size(), errores, null, true);
                }
            } else {
                CONSOLE.log(Level.INFO, "No se encontraron datos para procesar.");
                enviarNotificacion(0, 0, null, false);
            }
        } catch (IOException | JRException e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al procesar la sonda de productos de mercadolibre. ", e);
            enviarNotificacion(0, 0, e.getMessage(), false);
        }
    }

    private void enviarNotificacion(Integer totalRegistros, Integer errores, String msgError, boolean adjunto) {
        //TODO: Enviar por correo el detalle con la repuesta
        MailMessageDTO msg = new MailMessageDTO();

        msg.setFrom("Sonda MercadoLibre <notificaciones@matisses.co>");
        msg.setSubject("Sonda MercadoLibre");

        Map<String, String> params = new HashMap<>();
        if (msgError != null && !msgError.isEmpty()) {
            params.put("mensajeError", msgError);
            msg.setTemplateName("error_sonda_mercadolibre");
            msg.addToAddress(applicationMBean.getDestinatariosPlantillaEmail().get("error_sonda_mercadolibre").getTo().get(0));
        } else {
            params.put("totalItems", String.valueOf(totalRegistros));
            params.put("totalErrores", String.valueOf(errores));
            if (totalRegistros > 0) {
                params.put("mensajeComplemento", ", los resultados se adjuntan a continuaci&oacute;n");
            } else {
                params.put("mensajeComplemento", "");
            }
            msg.setTemplateName("sonda_mercadolibre");
            msg.addToAddress(applicationMBean.getDestinatariosPlantillaEmail().get("sonda_mercadolibre").getTo().get(0));
        }
        msg.setParams(params);

        if (adjunto) {
            List<String[]> adjuntos = new ArrayList<>();
            String[] attached = new String[]{System.getProperty("jboss.server.temp.dir") + File.separator + "productosProcesadosMercadolibre.pdf", "productosProcesadosMercadolibre.pdf"};
            adjuntos.add(attached);

            msg.setAttachments(adjuntos);
        }

        emailREST.sendMail(msg);
    }

    @GET
    @Path("suspender/todo")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response suspenderTodo() {
        MercadoLibreClient client = new MercadoLibreClient(applicationMBean.obtenerValorPropiedad("mdolibre.service.url"));
        Response res = client.consultarItemsTienda();
        int i = 0;
        ConsultaProductosMercadolibreDTO resultado = res.readEntity(ConsultaProductosMercadolibreDTO.class);
        while (!resultado.getResults().isEmpty()) {
            i++;
            CONSOLE.log(Level.INFO, "Desactivando {0} productos", resultado.getResults().size());
            String accessToken = cargarTokenJson().getAccessToken();
            for (ProductoMercadoLibreDTO producto : resultado.getResults()) {
                //TODO: Obtener itemcode
                String shortItemcode = producto.getTitle().substring(producto.getTitle().length() - 7);
                String itemcode = shortItemcode.substring(0, 3) + "0000000000000" + shortItemcode.substring(3);
                CONSOLE.log(Level.INFO, "Procesando producto {0}\t{1}", new Object[]{producto.getId(), shortItemcode});

                //TODO: Consultar item en sap y quitar id_mercadolibre
                //ItemInventario entidad = itemFacade.find(itemcode);
                itemFacade.actualizarIdMercadoLibre(itemcode, null);

                //Desactivar producto mercadolibre
                MercadoLibreClient client2 = new MercadoLibreClient(applicationMBean.obtenerValorPropiedad("mdolibre.service.url"), "items");
                Response respFinalizar = client2.finalizarPublicacion(accessToken, producto.getId());
                //console.log(Level.INFO, "\t{0}", respFinalizar.readEntity(String.class));
                client2.close();
            }
            CONSOLE.log(Level.INFO, "Finalizo tanda #{0}. Volviendo a consultar...", i);
            res = client.consultarItemsTienda();
            resultado = res.readEntity(ConsultaProductosMercadolibreDTO.class);
        }
        client.close();
        return Response.ok().build();
    }

    @POST
    @Path("suspender")
    @Produces({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response finalizarPublicacion(String[] referencias) {
        int exito = 0;
        int fallido = 0;
        CONSOLE.log(Level.INFO, "Finalizando {0} publicaciones", referencias.length);
        for (String ref : referencias) {
            ItemInventario itemEntidad = itemFacade.find(ref);
            CONSOLE.log(Level.INFO, "Finalizando la publicacion del item {0} con id_mercadolibre {1}", new Object[]{ref, itemEntidad.getuIdMercadoLibre()});
            if (itemEntidad.getuIdMercadoLibre() != null && !itemEntidad.getuIdMercadoLibre().trim().isEmpty()) {
                //Finaliza la publicacion en mercadolibre
                MercadoLibreClient client = new MercadoLibreClient(applicationMBean.obtenerValorPropiedad("mdolibre.service.url"), "items");
                client.finalizarPublicacion(cargarTokenJson().getAccessToken(), itemEntidad.getuIdMercadoLibre());
                client.close();
                //Borra el id de mercadolibre para el item
                itemFacade.actualizarIdMercadoLibre(ref, "");
                CONSOLE.log(Level.INFO, "  Item {0} procesado con exito", ref);
                exito++;
            } else {
                CONSOLE.log(Level.WARNING, "  No se pudo procesar el item {0} porque no tiene configurado un id_mercadolibre", ref);
                fallido++;
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Proceso finalizado. Se procesaron ");
        sb.append(exito);
        sb.append(" referencias satisfactoriamente. ");
        sb.append(fallido);
        sb.append(" referencias fallaron. ");
        return Response.ok(sb.toString()).build();
    }

    @POST
    @Path("agregaratributossimple")
    @Produces({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response agregarAtributosSimple(List<String> referencias) {
        for (String ref : referencias) {
            CONSOLE.log(Level.INFO, "Procesando referencia {0}", ref);
            ItemInventario itemEntidad = itemFacade.find(ref);
            if (itemEntidad.getuIdMercadoLibre() == null || itemEntidad.getuIdMercadoLibre().trim().isEmpty()) {
                continue;
            }

            AgregarAtributosMdoLibreDTO dto = new AgregarAtributosMdoLibreDTO();
            dto.getAttributes().add(new AgregarAtributosMdoLibreDTO.Attribute("BRAND", itemFacade.obtenerNombreMarca(itemEntidad.getuCodigoMarca())));
            dto.getAttributes().add(new AgregarAtributosMdoLibreDTO.Attribute("EAN", itemEntidad.getuCodigoEan()));

            MercadoLibreClient client = new MercadoLibreClient(applicationMBean.obtenerValorPropiedad("mdolibre.service.url"), "items");
            Response response = client.agregarAtributosItem(cargarTokenJson().getAccessToken(), itemEntidad.getuIdMercadoLibre(), dto);
            String jsonResponse = response.readEntity(String.class);
            CONSOLE.log(Level.INFO, "Respuesta de referencia {0}: {1}", new Object[]{ref, jsonResponse});
        }
        return Response.ok().build();
    }

    @POST
    @Path("procesarnotificacion")
    @Produces({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response procesarNotificacion(NotificacionMercadolibreDTO notificacion) {
        CONSOLE.log(Level.INFO, "Procesando notificacion {0}", notificacion);
        //TODO: Validar el tipo de notificacion
        switch (notificacion.getTopic()) {
            case TOPIC_PAYMENTS:
                procesarNotificacionPago(notificacion);
                break;
        }

        notificacion.getTopic();
        return Response.ok().build();
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private void procesarNotificacionPago(NotificacionMercadolibreDTO notificacion) {
        String sesionSAP = sapB1MBean.obtenerSesionSAP();
        try {
            String accessToken = cargarTokenJson().getAccessToken();
            //TODO: consultar informacion pago
            MercadoLibreClient client = new MercadoLibreClient(applicationMBean.obtenerValorPropiedad("mdolibre.service.url"), "collections");
            Response res = client.consultarPago(notificacion.getResource().substring(notificacion.getResource().lastIndexOf("/") + 1), accessToken);
            PagoMercadoLibreDTO pago = res.readEntity(PagoMercadoLibreDTO.class);
            CONSOLE.log(Level.INFO, pago.toString());

            //TODO: consultar la orden
            client = new MercadoLibreClient(applicationMBean.obtenerValorPropiedad("mdolibre.service.url"), "orders");
            res = client.consultarOrden(pago.getOrderId(), accessToken);
            OrdenMercadoLibreDTO orden = res.readEntity(OrdenMercadoLibreDTO.class);
            orden.getBuyer().setEmail(pago.getPayer().getEmail());
            CONSOLE.log(Level.INFO, orden.toString());

            //TODO: consultar el cliente
            if (orden.getBuyer().getBillingInfo().getDocNumber() == null) {
                enviarEmailError("Validacion datos cliente mercadolibre", "No se recibio el número de documento del cliente en la notificación del pago. Se debe proceder manualmente con la facturación. Orden ML #" + orden.getId());
                return;
            }
            SocioDeNegocios clienteEntidad = clienteFacade.findByCardCode(orden.getBuyer().getBillingInfo().getDocNumber());
            if (clienteEntidad == null) {
                //TODO: Si el cliente no existe, crearlo
                if (sesionSAP == null) {
                    enviarEmailError("Crear cliente mercadolibre", "Ocurrio un error al obtener una sesion de B1WS para crear un cliente de mercadolibre en SAP. ");
                    return;
                }

                BusinessPartnersServiceConnector service = sapB1MBean.getBusinessPartnersServiceConnectorInstance(sesionSAP);
                BusinessPartnerDTO dtoCliente = new BusinessPartnerDTO();
                dtoCliente.setBirthDate(null);
                dtoCliente.setCardCode(orden.getBuyer().getBillingInfo().getDocNumber() + "CL");
                dtoCliente.setCardName(orden.getBuyer().getLastName() + " " + orden.getBuyer().getFirstName());
                dtoCliente.setCardType(BusinessPartnerDTO.CardType.CUSTOMER);
                dtoCliente.setDefaultBillingAddress(DIRECCION_MERCADOLIBRE);
                dtoCliente.setDefaultShippingAddress(DIRECCION_MERCADOLIBRE);
                dtoCliente.setFirstName(orden.getBuyer().getFirstName());
                dtoCliente.setFiscalID(orden.getBuyer().getBillingInfo().getDocNumber());
                dtoCliente.setFiscalIdType("13");
                dtoCliente.setForeignType(BusinessPartnerDTO.ForeignType.NO_APLICA);
                dtoCliente.setGender(BusinessPartnerDTO.Gender.NINGUNO);
                dtoCliente.setLastName1(orden.getBuyer().getLastName());
                dtoCliente.setLastName2("");
                dtoCliente.setNationality(BusinessPartnerDTO.Nationality.NATIONAL);
                dtoCliente.setPersonType(BusinessPartnerDTO.PersonType.NATURAL);
                dtoCliente.setSalesPersonCode("501");
                dtoCliente.setSelfRetainer("N");
                dtoCliente.setTaxRegime(BusinessPartnerDTO.TaxRegime.REGIMEN_SIMPLIFICADO);

                dtoCliente.addAddress(construirDireccionEntidad(orden, BusinessPartnerAddressDTO.AddressType.BILLING));
                dtoCliente.addAddress(construirDireccionEntidad(orden, BusinessPartnerAddressDTO.AddressType.SHIPPING));

                try {
                    service.createBusinessPartner(dtoCliente);
                } catch (Exception e) {
                    CONSOLE.log(Level.SEVERE, "Ocurrio un error al crear el cliente en SAP. ", e);
                    enviarEmailError("Crear cliente mercadolibre", "Ocurrio un error al crear el cliente en SAP. " + e.getMessage());
                    return;
                }
            } else {
                //TODO: Si el cliente existe, valida si ya tiene direccion de mercadolibre.
                DireccionSocioDeNegocios direccionEntidad = null;
                try {
                    direccionEntidad = direccionFacade.find(new DireccionSocioDeNegociosPK(DIRECCION_MERCADOLIBRE, clienteEntidad.getCardCode(), 'S'));
                } catch (Exception e) {
                }

                if (direccionEntidad == null) {
                    //TODO: Si la direccion de mercadolibre no existe, la crea
                    List<BusinessPartnerAddressDTO> addresses = new ArrayList<>();
                    addresses.add(construirDireccionEntidad(orden, BusinessPartnerAddressDTO.AddressType.BILLING));
                    addresses.add(construirDireccionEntidad(orden, BusinessPartnerAddressDTO.AddressType.SHIPPING));
                    try {
                        BusinessPartnersServiceConnector service = sapB1MBean.getBusinessPartnersServiceConnectorInstance(sesionSAP);
                        service.addBusinessPartnerAddresses(orden.getBuyer().getBillingInfo().getDocNumber() + "CL", addresses);
                    } catch (Exception e) {
                        CONSOLE.log(Level.SEVERE, "Ocurrio un error al agregar las nuevas direcciones al cliente. ", e);
                    }
                } else {
                    //TODO: TODO: Si la direccion "mercadolibre" ya existe, valida si son iguales. Sino, la modifica
                }
            }

            //TODO: crear factura
            try {
                crearFactura(orden);
            } catch (Exception e) {
                enviarEmailError("Crear factura mercadolibre", "Ocurrio un error al crear la factura en SAP. " + e.getMessage());
                return;
            }

            //TODO: crear recibo de caja
            try {

            } catch (Exception e) {
                enviarEmailError("Crear recibo de caja mercadolibre", "Ocurrio un error al crear el recibo de caja en SAP. " + e.getMessage());
                return;
            }

            //TODO: crear registro para procesar documentos relacionados
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al procesar la notificacion de venta por mercadolibre. ", e);
            //TODO: enviar correo para generar facturacion manual
        } finally {
            if (sesionSAP != null) {
                sapB1MBean.cerrarSesionSAP(sesionSAP);
            }
        }
    }

    private void enviarEmailError(String proceso, String mensaje) {
        EmailTemplateDestinationDTO emailDestinationDto = applicationMBean.getDestinatariosPlantillaEmail().get("error");
        MailMessageDTO messageDto = new MailMessageDTO();
        messageDto.setTo(emailDestinationDto.getTo());
        messageDto.setCc(emailDestinationDto.getCc());
        messageDto.setBcc(emailDestinationDto.getBcc());
        messageDto.setFrom("Ventas MercadoLibre <mdolibre@matisses.co>");
        messageDto.setSubject("Ocurrio un error al procesar una venta de mercadolibre");
        messageDto.setTemplateName("error");
        messageDto.setAttachments(null);

        HashMap<String, String> params = new HashMap<>();
        params.put("processName", proceso);
        params.put("errorMessage", mensaje);
        params.put("username", "mercadolibre");
        messageDto.setParams(params);
        try {
            emailREST.sendMail(messageDto);
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al enviar el mail de notificacion de error. ", e);
        }
    }

    private Long crearFactura(OrdenMercadoLibreDTO orden) throws Exception {
        HashMap<String, Double> descuentosVigentes = cargarDescuentosVigentes();
        //TODO: consultar datos configurados para el detalle de la factura
        // 0. Codigo serie numeracion
        // 1. Nombre serie numeracion
        // 2. Codigo ventas
        // 3. Codigo logistica
        // 4. Codigo ruta
        // 5. Codigo proyecto
        // 6. WUID
        Object[] datosFacturaAlmacen = almacenFacade.cargarDatosFacturaMercadoLibre();

        SalesDocumentDTO facturaDto = new SalesDocumentDTO();
        facturaDto.setCardCode(orden.getBuyer().getBillingInfo().getDocNumber() + "CL");
        facturaDto.setComments("Venta desde mercadolibre facturada automaticamente por BCS");
        facturaDto.setProjectCode((String) datosFacturaAlmacen[5]);
        facturaDto.setRouteCostingCode((String) datosFacturaAlmacen[4]);
        facturaDto.setSalesCostingCode((String) datosFacturaAlmacen[2]);
        facturaDto.setLogisticsCostingCode((String) datosFacturaAlmacen[3]);
        facturaDto.setPaymentGroupCode("20");

        if (applicationMBean.getEmpleadoMercadoLibre() == null) {
            facturaDto.setSalesEmployees(SalesEmployeeDTO.getOneEmployeeList("VENDEDOR WEB", "98"));
        } else {
            facturaDto.setSalesEmployees(SalesEmployeeDTO.getOneEmployeeList(applicationMBean.getEmpleadoMercadoLibre()));
        }

        facturaDto.setSeriesCode((String) datosFacturaAlmacen[0]);
        facturaDto.setSource("M");
        facturaDto.setWuid((String) datosFacturaAlmacen[6]);

        int lineNum = 0;
        for (OrdenMercadoLibreDTO.OrderItem orderItem : orden.getOrderItems()) {
            String itemCode = itemFacade.obtenerItemCode(orderItem.getItem().getId());
            if (itemCode == null) {
                enviarEmailError("Facturación mercadolibre", "No fue posible asociar el ID " + orderItem.getItem().getId() + " con un producto de SAP. Se debe facturar manualmente la orden");
                return null;
            }
            SalesDocumentLineDTO docLine = new SalesDocumentLineDTO();
            List<Object[]> saldosUbicacion = saldoUbicacionFacade.buscarPorReferencia(itemCode);
            //TODO: Asigna las ubicaciones por cantidad
            int cantidadPendiente = orderItem.getQuantity();
            CONSOLE.log(Level.INFO, "Verificando ubicaciones para la referencia [{0}]", itemCode);
            //TODO: Valida que la cantidad total en saldo sea suficiente para cubrir la cantidad solicitada
            int cantidadTotal = 0;
            for (Object[] row : saldosUbicacion) {
                cantidadTotal += (Integer) row[4];
            }
            if (cantidadTotal < cantidadPendiente) {
                CONSOLE.log(Level.SEVERE, "No hay saldo suficiente de la referencia {0}. Cantidad necesaria: {1}, cantidad disponible: {2}",
                        new Object[]{itemCode, orderItem.getQuantity(), cantidadTotal});
                enviarEmailError("Facturación mercadolibre", "No hay saldo suficiente de la referencia " + itemCode
                        + ". Cantidad necesaria: " + orderItem.getQuantity() + ", cantidad disponible: " + cantidadTotal);
                return null;
            }
            for (Object[] row : saldosUbicacion) {
                String whsCode = (String) row[0];
                //Integer saldoAlmacen = (Integer) row[1];
                Integer binAbs = (Integer) row[2];
                String binCode = (String) row[3];
                Integer saldoUbicacion = (Integer) row[4];
                if (docLine.getWhsCode() == null) {
                    docLine.setWhsCode(whsCode);
                } else if (!docLine.getWhsCode().equals(whsCode)) {
                    //TODO: Si el almacen cambia, significa que el saldo en las ubicaciones del primer almacen no fue suficiente y se debe crear una nueva linea
                    facturaDto.addLine(docLine);
                    docLine = new SalesDocumentLineDTO();
                    docLine.setItemCode(itemCode);
                    docLine.setLineNum(lineNum++);
                    docLine.setPrice(null);
                    docLine.setWhsCode(whsCode);
                    if (descuentosVigentes.containsKey(itemCode)) {
                        docLine.setDiscountPercent(descuentosVigentes.get(itemCode));
                    }
                }
                if (docLine.getQuantity() == null) {
                    docLine.setQuantity(Math.min(Math.min(saldoUbicacion, cantidadPendiente), orderItem.getQuantity()));
                } else {
                    docLine.setQuantity(docLine.getQuantity() + Math.min(saldoUbicacion, cantidadPendiente));
                }
                SalesDocumentLineBinAllocationDTO bin = new SalesDocumentLineBinAllocationDTO();
                bin.setBinAbsEntry(binAbs);
                bin.setWhsCode(whsCode);
                if (cantidadPendiente <= saldoUbicacion) {
                    CONSOLE.log(Level.INFO, "La cantidad [{0}] en la ubicacion [{1}] satisface la cantidad pendiente [{2}]",
                            new Object[]{saldoUbicacion, binCode, cantidadPendiente});
                    bin.setQuantity(cantidadPendiente);
                    cantidadPendiente = 0;
                } else {
                    CONSOLE.log(Level.INFO, "La cantidad [{0}] en la ubicacion [{1}] NO satisface la cantidad pendiente [{2}]. Faltan [{3}]",
                            new Object[]{saldoUbicacion, binCode, cantidadPendiente, cantidadPendiente - saldoUbicacion});
                    bin.setQuantity(saldoUbicacion);
                    cantidadPendiente -= saldoUbicacion;
                }
                docLine.addBinAllocation(bin);
                if (cantidadPendiente == 0) {
                    break;
                }
            }
            facturaDto.addLine(docLine);
        }

        String sesionSAP = null;
        String invoiceDocNum;
        Long docEntryFactura;
        FacturaSAP facturaSAP;
        try {
            sesionSAP = sapB1MBean.obtenerSesionSAP();
            InvoicesServiceConnector isc = sapB1MBean.getInvoicesServiceConnectorInstance(sesionSAP);
            docEntryFactura = isc.createInvoice(facturaDto);

            facturaSAP = facturaSAPFacade.findNoTransaction(docEntryFactura.intValue());
            invoiceDocNum = Integer.toString(facturaSAP.getDocNum());
            CONSOLE.log(Level.INFO, "Se creo con exito la factura {0}", invoiceDocNum);
            return docEntryFactura;
        } catch (InvoiceServiceException e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al crear la factura para mercadolibre. ", e);
            return null;
        } finally {
            if (sesionSAP != null) {
                sapB1MBean.cerrarSesionSAP(sesionSAP);
            }
        }
    }

    /*
    private Long crearReciboCaja(String docEntryFactura, TransactionStatusResponseDTO datosPago) throws Exception {
        String nroDocumento = datosPago.getRequest().getPayer().getDocument();
        if (!nroDocumento.toUpperCase().endsWith("CL")) {
            nroDocumento += "CL";
        }
        PaymentDTO pagoDto = new PaymentDTO();
        pagoDto.setPaymentType(PaymentDTO.PaymentTypeDTO.CUSTOMER);
        pagoDto.setCardCode(nroDocumento);
        pagoDto.setCreditType("I");
        pagoDto.setInvoiceDocEntry(docEntryFactura);
        pagoDto.setPaidTotal(Long.toString(datosPago.getRequest().getPayment().getAmount().getTotal()));
        pagoDto.setSeriesCode("151"); //TODO: parametrizar serie de numeracion
        pagoDto.setDocType(ConstantTypes.DocType.INVOICE);
        List<CreditCardPaymentDTO> creditCardPayments = new ArrayList<>();

        CreditCardPaymentDTO creditPayment = new CreditCardPaymentDTO();
        creditPayment.setCreditCardCode(applicationBean.getTipoTarjetaP2P(datosPago.getPayment().get(0).getFranchise()).getIdTarjetaSAP().toString());
        creditPayment.setCreditCardNumber(StringUtils.right(datosPago.getPayment().get(0).getProcessorFields().get(0).getValue(), 4));
        creditPayment.setNumberOfPayments("1");
        creditPayment.setPaidSum(pagoDto.getPaidTotal());
        creditPayment.setValidUntil(null);//TODO: configurar fecha de validez
        creditPayment.setVoucherNumber(datosPago.getPayment().get(0).getAuthorization());
        creditCardPayments.add(creditPayment);
        pagoDto.setCreditCardPayments(creditCardPayments);

        String sesionSAP = null;
        try {
            sesionSAP = sapB1MBean.obtenerSesionSAP();
            IncomingPaymentServiceConnector ipsc = sapB1MBean.getIncomingPaymentServiceConnectorInstance(sesionSAP);
            Long docEntryRecibo = ipsc.addPayment(pagoDto);
            console.log(Level.INFO, "Se creo satisfactoriamente el recibo de caja con DocEntry={0}", docEntryRecibo);
            return docEntryRecibo;
        } catch (Exception e) {
        } finally {
            if (sesionSAP != null) {
                sapB1MBean.cerrarSesionSAP(sesionSAP);
            }
        }
    }
     */
    private HashMap<String, Double> cargarDescuentosVigentes() {
        HashMap<String, Double> descuentosVigentes = new HashMap<>();
        List<ProgramacionDescuento> entidades = descuentoFacade.consultarDescuentosCanalActivos("ML");
        for (ProgramacionDescuento entidad : entidades) {
            if (!descuentosVigentes.containsKey(entidad.getReferencia())) {
                descuentosVigentes.put(entidad.getReferencia(), entidad.getPorcentaje());
            }
        }
        return descuentosVigentes;
    }

    private BusinessPartnerAddressDTO construirDireccionEntidad(OrdenMercadoLibreDTO orden, BusinessPartnerAddressDTO.AddressType addressType) throws Exception {
        if (orden.getShipping().getReceiverAddress() == null || orden.getShipping().getReceiverAddress().getAddressLine() == null
                || orden.getShipping().getReceiverAddress().getCity() == null || orden.getShipping().getReceiverAddress().getState() == null) {
            throw new Exception("La informacion recibida en la orden no es suficiente para configurar las direcciones de factura y despacho");
        }
        Object[] datosGeograficos = municipioFacade.buscarMunicipioPorNombre(
                orden.getShipping().getReceiverAddress().getCity().getName(),
                orden.getShipping().getReceiverAddress().getState().getName());
        BusinessPartnerAddressDTO dir = new BusinessPartnerAddressDTO();
        dir.setAddress(orden.getShipping().getReceiverAddress().getAddressLine());
        dir.setAddressName(DIRECCION_MERCADOLIBRE);
        dir.setAddressType(addressType);
        dir.setCellphone(orden.getBuyer().getPhone().getNumber());
        dir.setCityCode((String) datosGeograficos[0]);
        dir.setCityName((String) datosGeograficos[1]);
        dir.setCountry(PAIS_COLOMBIA);
        dir.setEmail(orden.getBuyer().getEmail());
        dir.setLandLine("");
        dir.setStateCode((String) datosGeograficos[2]);
        dir.setStateName((String) datosGeograficos[3]);
        dir.setTaxCode("IGV19");

        return dir;
    }

    @POST
    @Path("agregaratributos")
    @Produces({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response agregarAtributos(HashMap<String, AgregarAtributosMdoLibreDTO> datos) {
        for (String ref : datos.keySet()) {
            CONSOLE.log(Level.INFO, "Procesando referencia {0}", ref);
            ItemInventario itemEntidad = itemFacade.find(ref);
            if (itemEntidad.getuIdMercadoLibre() == null || itemEntidad.getuIdMercadoLibre().trim().isEmpty()) {
                continue;
            }

            MercadoLibreClient client = new MercadoLibreClient(applicationMBean.obtenerValorPropiedad("mdolibre.service.url"), "items");
            Response response = client.agregarAtributosItem(cargarTokenJson().getAccessToken(), itemEntidad.getuIdMercadoLibre(), datos.get(ref));
            String jsonResponse = response.readEntity(String.class);
            CONSOLE.log(Level.INFO, "Respuesta de referencia {0}: {1}", new Object[]{ref, jsonResponse});
        }
        return Response.ok().build();
    }

    private ResponseDTO procesarItem(String itemCode, boolean soloMedellin) {
        CONSOLE.log(Level.INFO, "Procesando el item {0}", itemCode);
        if (itemCode == null || itemCode.trim().length() != 20) {
            CONSOLE.log(Level.INFO, "No se encontro item valido");
            return new ResponseDTO(-1, "No se recibio una referencia valida.");
        }
        try {
            MercadoLibreClient client = new MercadoLibreClient(applicationMBean.obtenerValorPropiedad("mdolibre.service.url"), "items");
            MercadolibreAccessCodeResponseDTO tokenInfo = cargarTokenJson();
            if (tokenInfo == null) {
                CONSOLE.log(Level.SEVERE, "No se pudo obtener un token valido");
                return new ResponseDTO(-1, "No se pudo obtener un token valido");
            }
            ItemInventario itemEntidad = itemFacade.find(itemCode);
            //TODO: Consultar producto sap y saldo
            Integer saldo = itemFacade.consultarSaldoParaMercadolibre(itemCode, soloMedellin);
            Integer precio = itemFacade.getItemPrice(itemCode, true);
            ProgramacionDescuento descuento = descuentoFacade.consultarDescuentosReferencia("ME", itemCode);
            if (saldo == null || saldo <= 0 || precio == null || precio == 0) {
                if (itemEntidad.getuIdMercadoLibre() == null || itemEntidad.getuIdMercadoLibre().isEmpty()) {
                    CONSOLE.log(Level.INFO, "El item no se ha publicado aun, no tiene precio o no tiene saldo");
                    return new ResponseDTO(-1, "El item " + itemCode + " no se ha publicado aun, no tiene precio o no tiene saldo");
                }
                try {
                    Response res = client.finalizarPublicacion(tokenInfo.getAccessToken(), itemEntidad.getuIdMercadoLibre());
                    String obj = res.readEntity(String.class);
                    CONSOLE.log(Level.INFO, "Se finalizo la publicacion del item {0}", obj);
                    itemFacade.actualizarIdMercadoLibre(itemCode, null);
                    return new ResponseDTO(0, "publicacion finalizada " + obj);
                } catch (Exception e) {
                    CONSOLE.log(Level.SEVERE, "Ocurrio un error al suspender el item. ", e);
                    return new ResponseDTO(-1, "Ocurrio un error al suspender el item " + itemCode + ". " + e.getMessage());
                }
            }

            BaruSubgrupo subgrupoEntidad = subgrupoFacade.find(itemEntidad.getUSubGrupo());
            if (subgrupoEntidad.getUcategoriaML() == null) {
                CONSOLE.log(Level.SEVERE, "La categoria del producto ({0}) no tiene categoria asociada de mercadolibre", itemEntidad.getUSubGrupo());
                return new ResponseDTO(-1, "La categoria del producto (" + itemEntidad.getUSubGrupo() + ") no tiene categoria asociada de mercadolibre");
            }

            MercadolibrePublicarItemDTO item = new MercadolibrePublicarItemDTO();
            item.setAvailableQuantity(saldo);
            item.setBuyingMode("buy_it_now");
            item.setCategoryId(subgrupoEntidad.getUcategoriaML());
            item.setCondition("new");
            item.setCurrencyId("COP");
            //String descripcionHtml = construirDescripcionHTML(itemCode, itemEntidad.getFrgnName(), itemEntidad.getUdescripciona(), itemEntidad.getuCodigoMarca());
            String descripcionPlana = construirDescripcionPlana(itemCode, itemEntidad.getFrgnName(), itemEntidad.getUdescripciona(), itemEntidad.getuCodigoMarca());
            item.setDescription(null, descripcionPlana);
            //item.setOriginalPrice(precio);
            item.setPrice(precio);
            /*if (descuento != null && descuento.getPorcentaje() > 0D) {
                Integer precioFinal = new Double(precio * (100 - descuento.getPorcentaje()) / 100).intValue();
                console.log(Level.INFO, "Configurando precio con descuento del {1}% para la ref {0}. Precio final {2} ",
                        new Object[]{itemCode, descuento.getPorcentaje(), precioFinal});
                item.setPrice(precioFinal);
                item.setBasePrice(precioFinal);
            } else {
                item.setPrice(precio);
            }*/

            item.setListingType("gold_pro");
            if (precio >= 150000) { //TODO: parametrizar valor minimo para envio gratis
                MercadolibrePublicarItemDTO.Shipping shipping = new MercadolibrePublicarItemDTO.Shipping();

                if (soloMedellin) {
                    shipping.setFreeShipping(true);
                    shipping.setLocalPickUp(true);
                    shipping.setStorePickUp(true);
                } else {
                    shipping.setFreeShipping(false);
                    shipping.setLocalPickUp(false);
                    shipping.setStorePickUp(false);
                }

                if (tieneMercadoEnvioHabilitado(subgrupoEntidad.getUcategoriaML())) {
                    CONSOLE.log(Level.INFO, "Configurando envio gratuito por mercadoenvios para el item {0}", itemCode);
                    MercadolibrePublicarItemDTO.Shipping.FreeMethod.Rule rule = new MercadolibrePublicarItemDTO.Shipping.FreeMethod.Rule();
                    rule.setDef(true);
                    rule.setFreeMode("country");
                    rule.setFreeShippingFlag(true);

                    MercadolibrePublicarItemDTO.Shipping.FreeMethod freeMethod = new MercadolibrePublicarItemDTO.Shipping.FreeMethod();
                    freeMethod.setId(501745L);//TODO: consultar el id del envio a partir de la categoria del item
                    freeMethod.setRule(rule);

                    shipping.setMode("me2");
                } else {
                    shipping.setMode("me1");
                }
                item.setShipping(shipping);
            } else {
                CONSOLE.log(Level.INFO, "Configurando envio por mercadoenvios para el item {0}", itemCode);
                MercadolibrePublicarItemDTO.Shipping shipping = new MercadolibrePublicarItemDTO.Shipping();
                shipping.setFreeShipping(false);
                shipping.setLocalPickUp(false);
                shipping.setStorePickUp(true);
                shipping.setMode("custom");
                item.setShipping(shipping);
            }
            if (itemEntidad.getuCodigoEan() != null && !itemEntidad.getuCodigoEan().trim().isEmpty() && !itemEntidad.getuCodigoEan().equals("0")) {
                MercadolibrePublicarItemDTO.Attribute attr = new MercadolibrePublicarItemDTO.Attribute();
                attr.setId("EAN");
                attr.setValueName(itemEntidad.getuCodigoEan());
                item.getAttributes().add(attr);
            }
            String nombreMarca = itemFacade.obtenerNombreMarca(itemEntidad.getuCodigoMarca());
            if (applicationMBean.obtenerValorPropiedad("mdolibre.pruebas").equals("true")) {
                item.setTitle("Item de prueba Por favor no Ofertar " + itemCode.substring(0, 3) + itemCode.substring(16));
            } else {
                StringBuilder sb = new StringBuilder();
                if (itemEntidad.getuDescripcionML() == null || itemEntidad.getuDescripcionML().trim().isEmpty()) {
                    sb.append(itemEntidad.getFrgnName());
                } else {
                    sb.append(itemEntidad.getuDescripcionML());
                }
                sb.append(" Marca ");
                sb.append(nombreMarca);
                sb.append(" ");
                sb.append(itemCode.substring(0, 3));
                sb.append(itemCode.substring(16));

                if (sb.length() > 60) {
                    return new ResponseDTO(-1, "El titulo del item supera los 60 caracteres. ");
                }

                item.setTitle(sb.toString());
            }
            if (!itemEntidad.getuCodigoMarca().equals("0001")) {
                MercadolibrePublicarItemDTO.Attribute attr = new MercadolibrePublicarItemDTO.Attribute();
                attr.setId("BRAND");
                attr.setValueName(nombreMarca);
                item.getAttributes().add(attr);
            }
            if (!applicationMBean.obtenerValorPropiedad("mdolibre.official.id").equals("0")) {
                item.setOfficialStoreId(applicationMBean.obtenerValorPropiedad("mdolibre.official.id"));
            }
            //item.setWarranty("ninguna");
            List<String> imagenes = consultarImagenesDisponibles(itemCode);
            if (imagenes.isEmpty()) {
                CONSOLE.log(Level.SEVERE, "El item {0} no tiene imagenes", itemCode);
                return new ResponseDTO(-1, "El item " + itemCode + " no tiene imagenes");
            }
            for (String fileName : imagenes) {
                if (fileName.startsWith(itemCode)) {
                    item.getPictures().add(new MercadolibrePublicarItemDTO.Picture("https://img.matisses.co/" + itemCode + "/images/" + fileName));
                }
            }
            item.getPictures().add(new MercadolibrePublicarItemDTO.Picture("https://img.matisses.co/" + itemCode + "/plantilla/" + itemCode + ".jpg"));

            Response res;
            CONSOLE.log(Level.INFO, "{0}", item.toString());
            if (itemEntidad.getuIdMercadoLibre() == null || itemEntidad.getuIdMercadoLibre().trim().isEmpty()) {
                /*Crear publicacion*/
                res = client.listarProducto(tokenInfo.getAccessToken(), item);
            } else {
                /*Modificar publicacion*/
                res = client.modifificarPublicacion(tokenInfo.getAccessToken(),
                        itemEntidad.getuIdMercadoLibre(), new MercadoLibreItemModificarDTO(item));
            }
            String jsonResponse = res.readEntity(String.class);
            ObjectMapper mapper = new ObjectMapper();
            try {
                ProductoMercadoLibreDTO producto = mapper.readValue(jsonResponse, ProductoMercadoLibreDTO.class);
                try {
                    itemFacade.actualizarIdMercadoLibre(itemCode, producto.getId());
                } catch (Exception e) {
                    CONSOLE.log(Level.SEVERE, "Ocurrio un error al actualizar el ID de mercadolibre para la referencia {0}. {1}", new Object[]{itemCode, e.getMessage()});
                }
                CONSOLE.log(Level.INFO, "Item {0} procesado con exito. {1}", new Object[]{itemCode, jsonResponse});
                return new ResponseDTO(0, jsonResponse);
            } catch (UnrecognizedPropertyException e) {
                CONSOLE.log(Level.INFO, "Se obtuvo un error de mercadolibre: {0}", jsonResponse);
                return new ResponseDTO(-1, jsonResponse);
            }
        } catch (ClientErrorException | IOException e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al procesar el item " + itemCode, e);
            return new ResponseDTO(-1, "Ocurrio un error al procesar el item. " + e.getMessage());
        }
    }

    @POST
    @Path("enviogratis/")
    @Produces({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response configurarEnvioGratuito(String[] itemCodes) {
        CONSOLE.log(Level.INFO, "Configurando envio gratuito a {0} referencias. ", itemCodes.length);
        List<String> response = new ArrayList<>();
        int refIndex = 0;
        for (String ref : itemCodes) {
            MercadoLibreClient client = new MercadoLibreClient(applicationMBean.obtenerValorPropiedad("mdolibre.service.url"), "items");
            Response res = client.configurarEnvioGratuito(cargarTokenJson().getAccessToken(), itemFacade.find(ref).getuIdMercadoLibre());
            String jsonResponse = res.readEntity(String.class);
            CONSOLE.log(Level.INFO, "{0}", jsonResponse);
            CONSOLE.log(Level.INFO, "  Avance: {0}%", (float) ++refIndex / itemCodes.length * 100);
        }
        return Response.ok(response).build();
    }

    @POST
    @Path("envio/{itemcode}")
    @Produces({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response configurarTipoEnvio(String tipoEnvio, @PathParam("itemcode") String itemCode) {
        MercadoLibreClient client = new MercadoLibreClient(applicationMBean.obtenerValorPropiedad("mdolibre.service.url"), "items");
        Response res = client.configurarEnvioItem(cargarTokenJson().getAccessToken(), itemFacade.find(itemCode).getuIdMercadoLibre(), tipoEnvio);
        String jsonResponse = res.readEntity(String.class);
        CONSOLE.log(Level.INFO, "{0}", jsonResponse);
        return Response.ok(jsonResponse).build();
    }

    @POST
    @Path("envio/")
    @Produces({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response configurarTipoEnvioMasivo(ConfiguracionEnvioDTO confEnvio) {
        List<String> resp = new ArrayList<>();
        for (String ref : confEnvio.getItems()) {
            MercadoLibreClient client = new MercadoLibreClient(applicationMBean.obtenerValorPropiedad("mdolibre.service.url"), "items");
            Response res = client.configurarEnvioItem(cargarTokenJson().getAccessToken(), itemFacade.find(ref).getuIdMercadoLibre(), confEnvio.getShipping());
            String jsonResponse = res.readEntity(String.class);
            CONSOLE.log(Level.INFO, "{0}", jsonResponse);
            resp.add(jsonResponse);
        }
        return Response.ok(resp).build();
    }

    @POST
    @Path("imagenes/{borrarPrimero}")
    @Produces({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response actualizarImagenes(@PathParam("borrarPrimero") Boolean borrarPrimero, String[] itemCodes) {
        List<String> resp = new ArrayList<>();
        for (String ref : itemCodes) {
            MercadolibreAccessCodeResponseDTO token = cargarTokenJson();
            MercadoLibreClient clientActualizar = new MercadoLibreClient(applicationMBean.obtenerValorPropiedad("mdolibre.service.url"), "items");
            ActualizarImagenesItemDTO imagenes = new ActualizarImagenesItemDTO();
            List<String> sources = consultarImagenesDisponibles(ref);
            for (String fileName : sources) {
                imagenes.agregarImagen("https://img.matisses.co/" + ref + "/images/" + fileName);
            }
            ItemInventario itemEntidad = itemFacade.find(ref);
            if (borrarPrimero) {
                MercadoLibreClient clientBorrar = new MercadoLibreClient(applicationMBean.obtenerValorPropiedad("mdolibre.service.url"), "items");
                clientBorrar.borrarImagenes(itemEntidad.getuIdMercadoLibre(), token.getAccessToken());
            }
            Response res = clientActualizar.actualizarImagenes(itemEntidad.getuIdMercadoLibre(), imagenes, token.getAccessToken());
            String jsonResponse = res.readEntity(String.class);
            CONSOLE.log(Level.INFO, "{0}", jsonResponse);
            resp.add(jsonResponse);
        }
        return Response.ok(resp).build();
    }

    @GET
    @Path("publicar/{itemcode}/{solomedellin}")
    @Produces({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response publicarItem(@PathParam("itemcode") String itemCode, @PathParam("solomedellin") Boolean soloMedellin) {
        ResponseDTO res = procesarItem(itemCode, soloMedellin);
        return Response.ok(res).build();
    }

    @POST
    @Path("publicar/{solomedellin}")
    @Produces({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response publicarItem(String[] itemCodes, @PathParam("solomedellin") Boolean soloMedellin) {
        CONSOLE.log(Level.INFO, "Procesando {0} referencias. ", itemCodes.length);
        List<ResponseDTO> response = new ArrayList<>();
        int refIndex = 0;
        for (String ref : itemCodes) {
            response.add(procesarItem(ref, soloMedellin));
            CONSOLE.log(Level.INFO, "Avance: {0}%", (float) ++refIndex / itemCodes.length * 100);
        }
        return Response.ok(response).build();
    }

    @GET
    @Path("descripcion/{referencia}")
    @Produces({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response actualizarDescripcionItem(@PathParam("referencia") String referencia) {
        ItemInventario itemEntidad = itemFacade.find(referencia);
        if (itemEntidad.getuIdMercadoLibre() == null || itemEntidad.getuIdMercadoLibre().trim().isEmpty()) {
            return Response.ok("La referencia " + referencia + " no ha sido cargada a mercadolibre").build();
        }
        MercadolibreAccessCodeResponseDTO token = cargarTokenJson();
        try {
            MercadoLibreClient client = new MercadoLibreClient(applicationMBean.obtenerValorPropiedad("mdolibre.service.url"), "items");
            ModificarDescripcionMercadolibreDTO modDto = new ModificarDescripcionMercadolibreDTO();
            //modDto.setText(construirDescripcionHTML(referencia, itemEntidad.getFrgnName(), itemEntidad.getUdescripciona(), itemEntidad.getuCodigoMarca()));
            modDto.setPlainText(construirDescripcionPlana(referencia, itemEntidad.getFrgnName(), itemEntidad.getUdescripciona(), itemEntidad.getuCodigoMarca()));
            Response res = client.actualizarDescripcion(itemEntidad.getuIdMercadoLibre(), modDto, token.getAccessToken());
            String jsonResponse = res.readEntity(String.class);
            CONSOLE.log(Level.INFO, "Se modifico la descripcion del item {0} satisfactoriamente {1}", new Object[]{referencia, jsonResponse});
            return Response.ok(jsonResponse).build();
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al modificar la descripcion del item " + referencia + " en mdolibre. ", e);
            return Response.ok("Ocurrio un error al modificar la descripcion del item " + referencia + " en mdolibre. " + e.getMessage()).build();
        }
    }

    @POST
    @Path("descripcion/")
    @Produces({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response actualizarDescripcionItems(String[] referencias) {
        if (referencias == null || referencias.length == 0) {
            return Response.ok("No se recibieron referencias para procesar.").build();
        }
        for (String referencia : referencias) {
            try {
                actualizarDescripcionItem(referencia);
            } catch (Exception e) {
            }
        }
        return Response.ok("OK").build();
    }

    private List<String> consultarImagenesDisponibles(String referencia) {
        File imgDirectoy = new File(String.format(applicationMBean.obtenerValorPropiedad("url.local.catalogo.dir"), referencia));
        if (!imgDirectoy.exists()) {
            return new ArrayList<>();
        }
        String[] files = imgDirectoy.list((File dir, String name) -> name != null && name.endsWith(".jpg"));
        /*for (String fileName : files) {
            if(fileName.startsWith(referencia)){
                console.log(Level.INFO, fileName);
            }
        }*/
        List<String> fileNames = Arrays.asList(files);
        Collections.sort(fileNames);
        return fileNames;
    }

    private MercadolibreAccessCodeResponseDTO cargarTokenJson() {
        try {
            MercadolibreAccessCodeResponseDTO dto;
            long currentTime = System.currentTimeMillis();
            TokenMercadoLibre tokenEntidad = tokenFacade.consultarUltimoToken();
            if (tokenEntidad == null) {
                CONSOLE.log(Level.SEVERE, "No se encontro un token en la base de datos. Se debe obtener uno nuevo");
                return null;
            } else if ((currentTime - tokenEntidad.getSystime()) / 1000 > tokenEntidad.getExpiresIn()) {
                CONSOLE.log(Level.SEVERE, "El token ha expirado. Obteniendo uno nuevo");
                dto = renovarAccessToken(tokenEntidad.getRefreshToken());
            } else {
                dto = new MercadolibreAccessCodeResponseDTO();
                dto.setAccessToken(tokenEntidad.getAccessToken());
                dto.setExpiresIn(tokenEntidad.getExpiresIn());
                dto.setRefreshToken(tokenEntidad.getRefreshToken());
                dto.setScope(tokenEntidad.getScope());
                dto.setSysTime(tokenEntidad.getSystime());
                dto.setTokenType("bearer");
                dto.setUserId(tokenEntidad.getUserId());
            }
            CONSOLE.log(Level.INFO, "Tiempo de vigencia restante: {0}seg", dto.getExpiresIn() - (currentTime - dto.getSysTime()) / 1000);
            return dto;
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al cargar la informacion del archivo. ", e);
            return null;
        }
    }

    private String construirDescripcionHTML(String referencia, String nombreExtranjero, String descripcion, String marca) {
        try {
            HashMap< String, String> params = new HashMap<>();
            params.put("nombreExtranjero", nombreExtranjero);
            params.put("referencia", referencia);
            params.put("descripcion", descripcion);
            params.put("materialesCuidados", null);
            params.put("marca", marca);

            String fullTemplateName = applicationMBean.obtenerValorPropiedad("mail.templates") + "mercadolibre_descripcion.html";
            String templateContent = new String(Files.readAllBytes(Paths.get(fullTemplateName)), StandardCharsets.UTF_8);
            return StrSubstitutor.replace(templateContent, params);
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al procesar la plantilla de descripcion para el item de mercadolibre. ", e);
            return descripcion;
        }
    }

    private String construirDescripcionPlana(String referencia, String nombreExtranjero, String descripcion, String marca) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(nombreExtranjero.substring(0, 1).toUpperCase());
            sb.append(nombreExtranjero.substring(1).toLowerCase());
            sb.append(". \n\n");
            sb.append(descripcion);
            sb.append("\n\n");
            sb.append("Nuestro horario de atención:");
            sb.append("\n");
            sb.append("Estamos atentos a responder todas tus inquietudes. Nuestros asesores responderán de lunes a viernes de 8:00 am a 6:00 pm y sábados de 8:00 am a 12:00 pm.");
            return sb.toString();
        } catch (Exception e) {
            return "";
        }
    }

    @POST
    @Path("sincronizarcategorias")
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response sincronizarCategorias(List<String> categoriasAProcesar) {
        MercadoLibreClient client = new MercadoLibreClient(applicationMBean.obtenerValorPropiedad("mdolibre.service.url"));
        Response res = client.consultarCategorias();
        List<LinkedHashMap> categorias = res.readEntity(List.class);
        for (LinkedHashMap categoria : categorias) {
            if (categoriasAProcesar == null || categoriasAProcesar.isEmpty() || categoriasAProcesar.contains((String) categoria.get("id"))) {
                procesarCategoria(client, (String) categoria.get("id"), null);
            } else {
                CONSOLE.log(Level.INFO, "La categoria {0} no se procesara porque no se incluyo en la lista", categoria);
            }
        }
        return Response.ok().build();
    }

    private void procesarCategoria(MercadoLibreClient client, String idCategoria, String idCategoriaPadre) {
        CONSOLE.log(Level.INFO, "{0}", idCategoria);
        Response resCat = client.consultarCategoria(idCategoria);
        try {
            CategoriaMercadolibreDTO categoria = resCat.readEntity(CategoriaMercadolibreDTO.class);
            CONSOLE.log(Level.INFO, categoria.pathFromRoot());
            if (categoria.getChildrenCategories() != null && !categoria.getChildrenCategories().isEmpty()) {
                for (CategoriaMercadolibreDTO.SubcategoriaMercadolibreDTO dto : categoria.getChildrenCategories()) {
                    procesarCategoria(client, dto.getId(), idCategoria);
                }
            }
            try {
                CategoriaMercadolibre entidad = categoriaMercadolibreFacade.find(categoria.getId());
                //TODO: La categoria ya existe en la base de datos
                if (!entidad.getNombre().equalsIgnoreCase(categoria.getName())) {
                    entidad.setNombre(categoria.getName());
                    categoriaMercadolibreFacade.edit(entidad);
                }
            } catch (Exception e) {
                //TODO: La categoria no existe en base de datos
                try {
                    CategoriaMercadolibre entidad = new CategoriaMercadolibre(categoria.getId(), categoria.getName(), idCategoriaPadre, null);
                    categoriaMercadolibreFacade.create(entidad);
                    CONSOLE.log(Level.INFO, "Se creo con exito la categoria {0}", entidad);
                } catch (Exception ex) {
                    CONSOLE.log(Level.SEVERE, "Ocurrio un error al crear la categoria en la base de datos. ", ex);
                }
            }
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al procesar la respuesta del servicio para la categoria " + idCategoria, e);
        }
    }

    private boolean tieneMercadoEnvioHabilitado(String idCategoria) {
        MercadoLibreClient client = new MercadoLibreClient(applicationMBean.obtenerValorPropiedad("mdolibre.service.url"));
        Response res = client.consultarCategoria(idCategoria);
        try {
            CategoriaMercadolibreDTO dto = res.readEntity(CategoriaMercadolibreDTO.class);
            if (dto.getSettings().getShippingModes().contains("me2")) {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    @GET
    @Path("duplicados")
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response consultarDuplicados() {
        Set<String> refs = new HashSet<>();
        HashMap<String, Integer> duplicadas = new HashMap<>();
        MercadoLibreClient client = new MercadoLibreClient(applicationMBean.obtenerValorPropiedad("mdolibre.service.url"));
        int pagina = 1;
        MercadolibrePublicacionDTO dto = client.consultarItemsPublicados(pagina).readEntity(MercadolibrePublicacionDTO.class);
        while (!dto.getResults().isEmpty()) {
            int fila = 0;
            CONSOLE.log(Level.INFO, "Procesando pagina #{0}", pagina);
            for (MercadolibrePublicarItemDTO itemDto : dto.getResults()) {
                String refCorta = StringUtils.right(itemDto.getTitle(), 7);
                if (refs.contains(refCorta)) {
                    if (duplicadas.containsKey(refCorta)) {
                        duplicadas.put(refCorta, duplicadas.get(refCorta) + 1);
                    } else {
                        duplicadas.put(refCorta, 1);
                    }
                } else {
                    refs.add(refCorta);
                }
                fila++;
            }
            if (fila == 200) {
                //TODO: Carga siguiente pagina;
                dto = client.consultarItemsPublicados(++pagina).readEntity(MercadolibrePublicacionDTO.class);
            } else {
                dto.setResults(new ArrayList<>());
            }
        }
        CONSOLE.log(Level.INFO, "Finalizo validacion de duplicados en mercadolibre. {0} refs activas, {1} refs duplicadas",
                new Object[]{refs.size(), duplicadas.size()});
        return Response.ok(duplicadas).build();
    }
}
