package co.matisses.bcs.rest;

import co.matisses.b1ws.dto.SalesDocumentDTO;
import co.matisses.b1ws.dto.SalesDocumentLineBinAllocationDTO;
import co.matisses.b1ws.dto.SalesDocumentLineDTO;
import co.matisses.b1ws.dto.SalesEmployeeDTO;
import co.matisses.bcs.b1ws.client.B1WSServiceUnavailableException;
import co.matisses.bcs.b1ws.client.invoices.InvoicesREST;
import co.matisses.bcs.b1ws.client.journalentries.JournalEntriesServiceConnector;
import co.matisses.bcs.b1ws.client.journalentries.JournalEntryDTO;
import co.matisses.bcs.b1ws.client.journalentries.JournalEntryLineDTO;
import co.matisses.bcs.b1ws.client.payments.ConstantTypes;
import co.matisses.bcs.b1ws.client.payments.CreditCardPaymentDTO;
import co.matisses.bcs.b1ws.client.payments.IncomingPaymentServiceREST;
import co.matisses.bcs.b1ws.client.payments.PaymentDTO;
import co.matisses.bcs.b1ws.client.payments.PaymentInvoicesDTO;
import co.matisses.bcs.dto.DatosCompraWebDTO;
import co.matisses.bcs.dto.ResponseDTO;
import co.matisses.bcs.mbean.BCSApplicationMBean;
import co.matisses.bcs.placetopay.DatosPagoPlaceToPayDTO;
import co.matisses.bcs.dto.ItemDTO;
import co.matisses.bcs.dto.MailMessageDTO;
import co.matisses.bcs.dto.MensajeTextoDTO;
import co.matisses.bcs.dto.PrintReportDTO;
import co.matisses.bcs.mbean.BCSGenericMBean;
import co.matisses.bcs.mbean.ImagenProductoMBean;
import co.matisses.bcs.mbean.SAPB1WSBean;
import co.matisses.bcs.newmatisses.client.ShoppingCartClient;
import co.matisses.bcs.newmatisses.client.ShoppingCartDTO;
import co.matisses.bcs.placetopay.P2PServiceConnector;
import co.matisses.bcs.placetopay.P2PWSAuthentication;
import co.matisses.bcs.placetopay.RespuestaPlaceToPayDTO;
import co.matisses.bcs.placetopay.SolicitudPagoResponseDTO;
import co.matisses.bcs.placetopay.TransactionStatusRequestDTO;
import co.matisses.bcs.placetopay.TransactionStatusResponseDTO;
import co.matisses.persistence.sap.entity.BaruDocumentoPendiente;
import co.matisses.persistence.sap.entity.FacturaSAP;
import co.matisses.persistence.sap.entity.SocioDeNegocios;
import co.matisses.persistence.sap.facade.AlmacenFacade;
import co.matisses.persistence.sap.facade.BaruDocumentoPendienteFacade;
import co.matisses.persistence.sap.facade.FacturaSAPFacade;
import co.matisses.persistence.sap.facade.ItemInventarioFacade;
import co.matisses.persistence.sap.facade.SaldoUbicacionFacade;
import co.matisses.persistence.sap.facade.SocioDeNegociosFacade;
import co.matisses.persistence.web.entity.CompraListaRegalos;
import co.matisses.persistence.web.entity.ListaRegalos;
import co.matisses.persistence.web.entity.ProductoListaRegalos;
import co.matisses.persistence.web.entity.ProgramacionDescuento;
import co.matisses.persistence.web.entity.TransaccionPlacetopay;
import co.matisses.persistence.web.facade.ListaRegalosFacade;
import co.matisses.persistence.web.facade.CompraListaRegalosFacade;
import co.matisses.persistence.web.facade.ProductoListaRegalosFacade;
import co.matisses.persistence.web.facade.ProgramacionDescuentoFacade;
import co.matisses.persistence.web.facade.TransaccionPlacetopayFacade;
import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author dbotero
 */
@Stateless
@Path("placetopay")
public class PlaceToPayREST implements Serializable {

    @Inject
    private BCSApplicationMBean applicationBean;
    @Inject
    private SAPB1WSBean sapB1WSBean;
    @Inject
    private BCSGenericMBean bcsGenericMBean;
    @Inject
    private ImagenProductoMBean imagenProductoMBean;
    private static final Logger CONSOLE = Logger.getLogger(PlaceToPayREST.class.getSimpleName());
    private String placetopayLogin;
    private String placetopayTranKey;
    private String placetopayServiceURL;
    @EJB
    private ListaRegalosFacade listaRegalosFacade;
    @EJB
    private TransaccionPlacetopayFacade transaccionP2PFacade;
    @EJB
    private SocioDeNegociosFacade socioDeNegociosFacade;
    @EJB
    private AlmacenFacade almacenFacade;
    @EJB
    private ItemInventarioFacade itemInventarioFacade;
    @EJB
    private SaldoUbicacionFacade saldoUbicacionFacade;
    @EJB
    private FacturaSAPFacade facturaSAPFacade;
    @EJB
    private SendHtmlEmailREST sendHtmlEmailREST;
    @EJB
    private BaruDocumentoPendienteFacade baruDocumentoPendienteFacade;
    @EJB
    private ProgramacionDescuentoFacade programacionDescuentoFacade;
    @EJB
    private PrintReportREST printReportREST;
    @EJB
    private ProductoListaRegalosFacade productoListaRegalosFacade;
    @EJB
    private CompraListaRegalosFacade compraListaRegalosFacade;
    @EJB
    private SMSServiceREST smsServiceREST;

    public PlaceToPayREST() {
    }

    @PostConstruct
    protected void initialize() {
        placetopayLogin = applicationBean.obtenerValorPropiedad("placetopay.login");
        placetopayTranKey = applicationBean.obtenerValorPropiedad("placetopay.trankey");
        placetopayServiceURL = applicationBean.obtenerValorPropiedad("placetopay.url");
    }

    @GET
    @Path("sonda/{web}")
    @Produces({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response ejecutarSonda(@PathParam("web") boolean web) {
        CONSOLE.log(Level.INFO, "Inicia la validando transacciones pendientes de PalceToPay.");
        List<TransaccionPlacetopay> transacciones = transaccionP2PFacade.listarTransaccionesPendientes(web);
        transacciones.addAll(transaccionP2PFacade.listarTransaccionesPENDING());

        CONSOLE.log(Level.INFO, "Se encontraron {0} transacciones pendientes por procesar.", transacciones.size());
        if (!transacciones.isEmpty()) {
            transacciones.stream().map((t) -> {
                CONSOLE.log(Level.INFO, "Procesando idPago {0}", t.getIdPago());
                return t;
            }).forEach((t) -> {
                ShoppingCartClient client = new ShoppingCartClient(applicationBean.obtenerValorPropiedad("api.url"));

                try {
                    ShoppingCartDTO res = client.consultar(t.getRefPago());
                    DatosCompraWebDTO dto = new DatosCompraWebDTO();

                    dto.setIdCarrito(res.getId());
                    dto.setItems(res.getItems());
                    dto.setMetodoEnvio(Integer.parseInt(res.getMetodoEnvio()));
                    dto.setTiendaRecoge(res.getTiendaRecoge());

                    consultarTransaccion(dto);
                } catch (Exception e) {
                    CONSOLE.log(Level.INFO, "", e);
                }
            });
        }
        CONSOLE.log(Level.INFO, "Finaliza la validacion de transacciones pendientes de PlaceToPay.");
        return Response.ok(transacciones).build();
    }

    @POST
    @Path("redirect")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response redirect(final DatosPagoPlaceToPayDTO datosPago, @Context HttpServletRequest req) {
        if (datosPago == null) {
            CONSOLE.log(Level.SEVERE, "No se recibieron los datos para el pago.");
            return Response.ok(new SolicitudPagoResponseDTO(-1, "No se recibieron los datos para el pago. ")).build();
        }
        CONSOLE.log(Level.INFO, "{0}", datosPago);
        P2PWSAuthentication p2pAuth = new P2PWSAuthentication(placetopayLogin, placetopayTranKey);
        datosPago.setAuth(p2pAuth.getLogin(), p2pAuth.getSeed(), p2pAuth.getNonce(), p2pAuth.getTranKey());
        switch (datosPago.getBuyer().getDocumentType()) {
            case "13":
                datosPago.getBuyer().setDocumentType("CC");
                break;
            case "31":
                datosPago.getBuyer().setDocumentType("NIT");
                break;
            case "22":
                datosPago.getBuyer().setDocumentType("CE");
                break;
            case "12":
                datosPago.getBuyer().setDocumentType("TI");
                break;
            case "41":
                datosPago.getBuyer().setDocumentType("PPN");
                break;
            default:
                break;
        }
        datosPago.setIpAddress(req.getRemoteAddr());
        if (datosPago.getExpiration() == null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ", Locale.getDefault());
            Calendar cal = new GregorianCalendar();
            cal.add(Calendar.HOUR, 12);
            datosPago.setExpiration(sdf.format(cal.getTime()));
        }
        TransaccionPlacetopay transaccion = crearTransaccionPlaceToPay(datosPago);
        CONSOLE.log(Level.INFO, "Se registro la transaccion en la base de datos, pendiente por respuesta de placetopay. {0}", transaccion);
        try {
            RespuestaPlaceToPayDTO respuestaP2P;
            try {
                respuestaP2P = P2PServiceConnector.postPlaceToPayRequest(placetopayServiceURL, "session", datosPago, RespuestaPlaceToPayDTO.class);
            } catch (Exception e) {
                if (e.getMessage().contains("(401) Unauthorized")) {
                    DatosPagoPlaceToPayDTO.Buyer buyer = datosPago.getBuyer();
                    datosPago.setBuyer(null);
                    respuestaP2P = P2PServiceConnector.postPlaceToPayRequest(placetopayServiceURL, "session", datosPago, RespuestaPlaceToPayDTO.class);
                    datosPago.setBuyer(buyer);
                } else {
                    CONSOLE.log(Level.SEVERE, "Ocurrio un error al conectarse con placetopay. ", e);
                    //TODO: notificar al dpto sistemas
                    return Response.ok(new SolicitudPagoResponseDTO(-1, "Ocurrio un error al conectarse con placetopay. " + e.getMessage())).build();
                }
            }

            actualizarTransaccionPlaceToPay(transaccion, respuestaP2P);
            if (respuestaP2P.getStatus().getStatus().equalsIgnoreCase("OK")) {
                return Response.ok(new SolicitudPagoResponseDTO(0, respuestaP2P)).build();
            } else {
                return Response.ok(new SolicitudPagoResponseDTO(-1, respuestaP2P.getStatus().getMessage())).build();
            }
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al conectarse con placetopay. ", e);
            //TODO: notificar al dpto sistemas
            return Response.ok(new SolicitudPagoResponseDTO(-1, "Ocurrio un error al conectarse con placetopay. " + e.getMessage())).build();
        }
    }

    @POST
    @Path("consultar")
    @Consumes({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response consultarTransaccion(final DatosCompraWebDTO datos) {
        if (datos.getIdCarrito() != null && !datos.getIdCarrito().isEmpty()) {
            CONSOLE.log(Level.INFO, "Inicia proceso de consulta de transaccion PlaceTpPay para el carrito {0}", datos.getIdCarrito());
            TransaccionPlacetopay transaccion = transaccionP2PFacade.consultarPorReferenciaPago(datos.getIdCarrito());

            if (transaccion == null) {
                CONSOLE.log(Level.INFO, "No se encontro ninguna transaccion PalceToPay para el carrito {0}", datos.getIdCarrito());
                notificarErrorTransaccion("Error transacción", "Transacción PlaceToPay", "Id carrito: " + datos.getIdCarrito() + "<br/>" + "No se encontraró ninguna transacción con el id carrito");
                return Response.ok(new ResponseDTO(-1, "No se encontró ninguna transacción.")).build();
            }

            TransactionStatusResponseDTO transactionStatus = consultarTransaccionPlaceToPay(transaccion.getRequestId());
            if (transaccion.getPaymentStatus() == null || transaccion.getPaymentStatus().equals("PENDING") && transactionStatus != null) {
                CONSOLE.log(Level.INFO, "Validando estado PlaceToPay para el carrito {0}", datos.getIdCarrito());
                transaccion.setPaymentStatus(transactionStatus.getStatus().getStatus());
                transaccion.setPaymentMessage(transactionStatus.getStatus().getMessage());

                try {
                    transaccionP2PFacade.edit(transaccion);
                } catch (Exception e) {
                    CONSOLE.log(Level.SEVERE, "Ocurrio un error al actualizar los datos de P2P en la base de datos", e);
                    notificarErrorTransaccion("Error actualización", "Actualizar BD Transacción PlaceToPay", "Id carrito: " + datos.getIdCarrito() + "<br/>" + e.getMessage());
                    return Response.ok(new ResponseDTO(-1, "No se encontró ninguna transacción.")).build();
                }
            }

            if (transactionStatus != null && transactionStatus.getPayment() != null && !transactionStatus.getPayment().isEmpty()) {
                CONSOLE.log(Level.INFO, "Validando pagos PlaceToPay para el carrito {0}", datos.getIdCarrito());
                transaccion.setPaymentReference(transactionStatus.getPayment().get(0).getReference());
                transaccion.setPaymentMethod(transactionStatus.getPayment().get(0).getPaymentMethod());
                transaccion.setPaymentMethodName(transactionStatus.getPayment().get(0).getPaymentMethodName());
                transaccion.setPaymentIssuerName(transactionStatus.getPayment().get(0).getIssuerName());
                transaccion.setPaymentFranchise(transactionStatus.getPayment().get(0).getFranchise());
                transaccion.setPaymentReceipt(transactionStatus.getPayment().get(0).getReceipt());
                transaccion.setPaymentAuthorization(transactionStatus.getPayment().get(0).getAuthorization());
                transaccion.setPaymentLastDigits(transactionStatus.getPayment().get(0).getProcessorFields().get(0).getValue());
                transaccion.setPaymentDate(transactionStatus.getPayment().get(0).getStatus().getDate());
                transaccion.setPaymentInternalReference(transactionStatus.getPayment().get(0).getInternalReference());
                transaccion.setTipoCompra("P");

                try {
                    transaccionP2PFacade.edit(transaccion);
                } catch (Exception e) {
                    CONSOLE.log(Level.SEVERE, "Ocurrio un error al actualizar los datos de P2P en la base de datos", e);
                    notificarErrorTransaccion("Error actualización", "Actualizar BD Transacción PlaceToPay", "Id carrito: " + datos.getIdCarrito() + "<br/>" + e.getMessage());
                }
            }

            if (transaccion.getPaymentStatus().equals("APPROVED") && (transaccion.getDocEntryFactura() == null || transaccion.getDocEntryFactura() == 0)) {
                CONSOLE.log(Level.INFO, "Si la transaccion PlaceToPay para el carrito {0} es exitosa se crea la factura", datos.getIdCarrito());
                SocioDeNegocios socio = socioDeNegociosFacade.findByCardCode(transaccion.getNroDocumento().contains("CL") ? transaccion.getNroDocumento() : transaccion.getNroDocumento() + "CL");

                if (socio == null || socio.getCardCode() == null || !socio.getCardCode().isEmpty()) {
                    ResponseDTO res = crearFactura(datos.getMetodoEnvio(), datos.getTiendaRecoge(), datos.getItems(), transaccion, datos);

                    if (res.getEstado() <= 0) {
                        notificarErrorTransaccion("Error factura WEB", "Factura PlaceToPay", "Id carrito: " + datos.getIdCarrito() + "<br/>" + res.getMensaje());
                    } else {
                        String[] s = res.getMensaje().split(";");
                        transaccion.setDocNumFactura(s[0]);
                        transaccion.setDocEntryFactura(Long.parseLong(s[1]));

                        try {
                            FacturaSAP fac = facturaSAPFacade.findByDocNum(Integer.parseInt(s[0]));
                            FacturaSAP facServicio = null;
                            Double costoEnvio = 0.0;
                            Integer docEntryFacturaServicio = 0;
                            if (datos.getMetodoEnvio() == 3) {
                                costoEnvio = transaccion.getTotalPago() - fac.getDocTotal().doubleValue();
                                if (costoEnvio > 0) {
                                    ResponseDTO resTransp = crearFacturaServicio(fac.getDocNum(), fac.getUWuid(), costoEnvio, transaccion);
                                    if (resTransp.getEstado() <= 0) {
                                        notificarErrorTransaccion("Error factura servicio WEB", "Factura PlaceToPay", "Id carrito: " + datos.getIdCarrito() + "<br/>" + resTransp.getMensaje());
                                    } else {
                                        facServicio = facturaSAPFacade.findByDocNum(resTransp.getValor());
                                        docEntryFacturaServicio = facServicio.getDocEntry();
                                        if (datos.getMetodoEnvio() == 3 && (fac.getDocTotal().doubleValue() + facServicio.getDocTotal().doubleValue()) != transaccion.getTotalPago().doubleValue()) {
                                            notificarErrorTransaccion("Error pago", "Transacción PlaceToPay", "Id carrito: " + datos.getIdCarrito() + "<br/>" + "El pago no coincide con el total de la factura.");
                                        } else if (datos.getMetodoEnvio() != 3 && fac.getDocTotal().doubleValue() != transaccion.getTotalPago().doubleValue()) {
                                            notificarErrorTransaccion("Error pago", "Transacción PlaceToPay", "Id carrito: " + datos.getIdCarrito() + "<br/>" + "El pago no coincide con el total de la factura.");
                                        }
                                    }
                                }
                            }
                            List<PaymentInvoicesDTO> facturas = new ArrayList<>();
                            facturas.add(new PaymentInvoicesDTO(transaccion.getDocEntryFactura(), fac.getDocTotal().doubleValue()));
                            if (datos.getMetodoEnvio() == 3) {
                                facturas.add(new PaymentInvoicesDTO(docEntryFacturaServicio.longValue(), costoEnvio));
                            }

                            crearReciboCaja(facturas, transaccion.getTotalPago().doubleValue(), fac.getCardCode(), transactionStatus);

                            String prefijo = "";
                            PrintReportDTO print = new PrintReportDTO();
                            List<String[]> adjuntos = new ArrayList<>();
                            //List<String[]> documentosRelacionados = new ArrayList<>();

                            print.setAlias(null);
                            print.setCopias(1);
                            print.setImprimir(false);
                            print.setSucursal(null);

                            //TODO: Generar documento factura
                            if (facturas.size() == 2 && datos.getMetodoEnvio() == 3) {
                                //TODO: Generar documento factura servicio
                                prefijo = facturaSAPFacade.obtenerPrefijoFactura(String.valueOf(facServicio.getDocNum()));
                                print.setDocumentosRelacionados(null);
                                print.setDocumento("facturaServicio");
                                print.setId(facServicio.getDocNum());
                                try {
                                    ResponseDTO responseServ = printReportREST.generarReporte(print);

                                    if (responseServ.getEstado() == 0) {
                                        String[] adjunto = new String[2];
                                        if (new File(responseServ.getMensaje()).exists()) {
                                            adjunto = new String[]{responseServ.getMensaje(), "Factura Transporte-" + prefijo + String.valueOf(facServicio.getDocNum()).substring(1) + ".pdf"};
                                            adjuntos.add(adjunto);
                                        }
                                    }
                                } catch (Exception e) {
                                    CONSOLE.log(Level.SEVERE, "Ocurrrio un error al generar el documento factura por servicio", e);
                                }
                            }

                            prefijo = facturaSAPFacade.obtenerPrefijoFactura(s[0]);
                            //documentosRelacionados.add(null);
                            print.setDocumento("factura");
                            print.setId(Integer.parseInt(s[0]));
                            try {
                                ResponseDTO response = printReportREST.generarReporte(print);

                                if (response.getEstado() == 0) {
                                    String[] adjunto = new String[2];
                                    if (new File(response.getMensaje()).exists()) {
                                        adjunto = new String[]{response.getMensaje(), "Factura Matisses-" + prefijo + s[0].substring(1) + ".pdf"};
                                        adjuntos.add(adjunto);
                                    }
                                }
                            } catch (Exception e) {
                                CONSOLE.log(Level.SEVERE, "Ocurrrio un error al generar el documento factura", e);
                            }

                            if (transaccion.getCodigoLista() != null && !transaccion.getCodigoLista().isEmpty()) {
                                CONSOLE.log(Level.SEVERE, "transaccion codigo lista", transaccion.getCodigoLista());
                                //Se envia notificacion al usuario de la lista de regalos, si y solo si, este pidio notificacion por este medio
                                ListaRegalos lista = listaRegalosFacade.consultarListaPorCodigo(transaccion.getCodigoLista());

                                if (lista != null && lista.getIdLista() != null && lista.getIdLista() != 0) {
                                    Map<String, String> params = new HashMap<>();

                                    params.put("comentario", "Mira los regalos que te han comprado por Lista de Regalos");
                                    params.put("link", applicationBean.obtenerValorPropiedad("listaregalos.url.web") + lista.getCodigo());

                                    if (transaccion.getMensajeComprador() != null && !transaccion.getMensajeComprador().isEmpty()) {
                                        StringBuilder sb = new StringBuilder();

                                        sb.append("<tr><td style=\"text-align: justify; padding: 20px; border: 1px solid #DDDDDD; border-radius: 10px;\">");
                                        sb.append(transaccion.getMensajeComprador());
                                        sb.append("</td></tr><tr><td style=\"text-align: right; padding-bottom: 20px;\"><br/>");
                                        sb.append("Atentamente,<br/>");
                                        sb.append("<span style=\"font-style: italic\">");
                                        sb.append(transaccion.getNombres());
                                        sb.append("</span></td></tr>");
                                        sb.append("<tr><td style=\"background-color: #EEEEEE; padding: 5px; text-align: left;\">Regalos que te compraron.</td></tr>");

                                        params.put("mensaje", sb.toString());
                                    }

                                    if (datos.getItems() != null && !datos.getItems().isEmpty()) {
                                        StringBuilder sb = new StringBuilder();

                                        sb.append("<table style=\"width: 100%; border: 1px solid #DDDDDD; margin-top: 10px; border-collapse: collapse;\">");
                                        sb.append("<tr>");
                                        sb.append("<th style=\"border: 1px solid #DDDDDD;\">Imagen</th>");
                                        sb.append("<th style=\"border: 1px solid #DDDDDD; margin: 10px;\">PRODUCTO</th>");
                                        sb.append("<th style=\"border: 1px solid #DDDDDD; margin: 10px;\">MONTO</th>");
                                        sb.append("</tr>");

                                        for (ItemDTO i : datos.getItems()) {
                                            sb.append("<tr>");
                                            sb.append("<td style=\"border: 1px solid #DDDDDD; text-align: center; margin: 10px;\"><img src=\"");
                                            sb.append(imagenProductoMBean.obtenerUrlProducto(i.getItemcode(), true));
                                            sb.append("\"></img></td>");
                                            sb.append("<td style=\"border: 1px solid #DDDDDD; margin: 10px;\">");
                                            sb.append(bcsGenericMBean.obtenerNombreReferencia(i.getItemcode()));
                                            sb.append("<br/>Ref: ");
                                            sb.append(bcsGenericMBean.convertirARefCorta(i.getItemcode()).replace("*", ""));
                                            sb.append("</td>");
                                            sb.append("<td style=\"border: 1px solid #DDDDDD; margin: 10px; text-align: right;\">");
                                            sb.append(i.getSelectedQuantity());
                                            sb.append("</td>");
                                            sb.append("</tr>");
                                        }

                                        sb.append("</table>");
                                        params.put("tabla", sb.toString());
                                    } else {
                                        params.put("tabla", "");
                                    }

                                    if (lista.getNotificacionInmediataMailCreador()) {
                                        params.put("cliente", lista.getNombreCreador());

                                        enviarCorreo("lista_regalos", "Lista Regalos Matisses <listaideal@matisses.co>", "Lista Regalos Matisses", lista.getCorreoCreador(),
                                                applicationBean.getDestinatariosPlantillaEmail().get("lista_regalos").getTo().get(0), null, params);
                                    }
                                    if (lista.getNotificacionInmediataMailCocreador()) {
                                        params.put("cliente", lista.getNombreCocreador());

                                        enviarCorreo("lista_regalos", "Lista Regalos Matisses <listaideal@matisses.co>", "Lista Regalos Matisses", lista.getCorreoCocreador(),
                                                applicationBean.getDestinatariosPlantillaEmail().get("lista_regalos").getTo().get(0), null, params);
                                    }

                                    try {
                                        MensajeTextoDTO sms = new MensajeTextoDTO();

                                        sms.setCodigoPais("57");
                                        sms.setIp("192.168.5.56");
                                        sms.setPruebas(false);
                                        sms.setUsuario("sonda");

                                        if (lista.getNotificacionDiariaSmsCreador()) {
                                            sms.setDestino(lista.getTelefonoCreador());

                                            if (lista.getNotificacionDiariaMailCreador()) {
                                                sms.setMensaje("Hola " + lista.getNombreCreador() + ", te acaban de comprar un regalo. Revisa tu correo para mas detalle.");
                                            } else {
                                                sms.setMensaje("Hola " + lista.getNombreCreador() + ", te acaban de comprar un regalo. Revisa tu lista para mas detalle.");
                                            }

                                            smsServiceREST.enviarSMS(sms);
                                        }
                                        if (lista.getNotificacionDiariaSmsCocreador()) {
                                            sms.setDestino(lista.getTelefonoCocreador());

                                            if (lista.getNotificacionDiariaMailCocreador()) {
                                                sms.setMensaje("Hola " + lista.getNombreCocreador() + ", te acaban de comprar un regalo. Revisa tu correo para mas detalle.");
                                            } else {
                                                sms.setMensaje("Hola " + lista.getNombreCocreador() + ", te acaban de comprar un regalo. Revisa tu lista para mas detalle.");
                                            }

                                            smsServiceREST.enviarSMS(sms);
                                        }
                                    } catch (Exception e) {
                                    }

                                    //Se envia mail de agradecimiento al comprador
                                    Map<String, String> paramsInvitado = new HashMap<>();

                                    paramsInvitado.put("comentario", "");

                                    if (lista.getMensajeAgradecimiento() != null && !lista.getMensajeAgradecimiento().isEmpty()) {
                                        StringBuilder sb = new StringBuilder();

                                        sb.append("<tr><td style=\"text-align: justify; padding: 20px; border: 1px solid #DDDDDD; border-radius: 10px;\">");
                                        sb.append(lista.getMensajeAgradecimiento());
                                        sb.append("</td></tr><tr><td style=\"text-align: right; padding-bottom: 20px;\"><br/>");
                                        sb.append("Atentamente,<br/>");
                                        sb.append("<span style=\"font-style: italic\">");
                                        sb.append(lista.getNombre().replace("Boda", "").replace("Matrimonio", ""));
                                        sb.append("</span></td></tr>");
                                        sb.append("<tr><td style=\"background-color: #EEEEEE; padding: 5px; text-align: left;\">Regalos que compro.</td></tr>");

                                        paramsInvitado.put("mensaje", sb.toString());

                                        if (datos.getItems() != null && !datos.getItems().isEmpty()) {
                                            StringBuilder sb1 = new StringBuilder();

                                            sb1.append("<table style=\"width: 100%; border: 1px solid #DDDDDD; margin-top: 10px; border-collapse: collapse;\">");
                                            sb1.append("<tr>");
                                            sb1.append("<th style=\"border: 1px solid #DDDDDD;\">Imagen</th>");
                                            sb1.append("<th style=\"border: 1px solid #DDDDDD; margin: 10px;\">PRODUCTO</th>");
                                            sb1.append("<th style=\"border: 1px solid #DDDDDD; margin: 10px;\">MONTO</th>");
                                            sb1.append("</tr>");

                                            for (ItemDTO i : datos.getItems()) {
                                                sb1.append("<tr>");
                                                sb1.append("<td style=\"border: 1px solid #DDDDDD; text-align: center; margin: 10px;\"><img src=\"");
                                                sb1.append(imagenProductoMBean.obtenerUrlProducto(i.getItemcode(), true));
                                                sb1.append("\"></img></td>");
                                                sb1.append("<td style=\"border: 1px solid #DDDDDD; margin: 10px;\">");
                                                sb1.append(bcsGenericMBean.obtenerNombreReferencia(i.getItemcode()));
                                                sb1.append("<br/>Ref: ");
                                                sb1.append(bcsGenericMBean.convertirARefCorta(i.getItemcode()).replace("*", ""));
                                                sb1.append("</td>");
                                                sb1.append("<td style=\"border: 1px solid #DDDDDD; margin: 10px; text-align: right;\">");
                                                sb1.append(i.getSelectedQuantity());
                                                sb1.append("</td>");
                                                sb1.append("</tr>");
                                            }

                                            sb1.append("</table>");
                                            paramsInvitado.put("tabla", sb1.toString());
                                        } else {
                                            paramsInvitado.put("tabla", "");
                                        }

                                        if (transaccion.getCorreo() != null && !transaccion.getCorreo().isEmpty()) {
                                            paramsInvitado.put("cliente", transaccion.getNombres());

                                            enviarCorreo("lista_regalos", "Lista Regalos Matisses <listaideal@matisses.co>", "Lista Regalos Matisses", transaccion.getCorreo(),
                                                    applicationBean.getDestinatariosPlantillaEmail().get("lista_regalos").getTo().get(0), null, paramsInvitado);
                                        }
                                    }
                                }
                            }

                            Map<String, String> mapa = new HashMap<>();
                            mapa.put("cliente", socio.getNombres());
                            mapa.put("factura", prefijo + s[0].substring(1));
                            mapa.put("fecha", new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
                            mapa.put("hora", new SimpleDateFormat("HH:mm:ss").format(new Date()));

                            switch (datos.getMetodoEnvio()) {
                                case 1:
                                    mapa.put("medioEnvio", "Matisses. Una semana el mejor precio.");
                                    mapa.put("facturaServicio", "Free Shipping.");
                                    break;
                                case 2:
                                    mapa.put("medioEnvio", "Recoger en tienda " + datos.getTiendaRecoge() + ", nuestro colaborador se contactara para coordinar la fecha de entrega.");
                                    mapa.put("facturaServicio", "No aplica.");
                                    break;
                                case 3:
                                    mapa.put("medioEnvio", "Coordinadora.");
                                    mapa.put("facturaServicio", prefijo + String.valueOf(facServicio.getDocNum()).substring(1));
                                    break;
                                default:
                                    mapa.put("medioEnvio", "");
                                    break;
                            }

                            MailMessageDTO dtoMail = new MailMessageDTO();

                            dtoMail.setTemplateName("compraWeb");
                            dtoMail.setParams(mapa);
                            dtoMail.setAttachments(adjuntos);
                            dtoMail.setFrom("Factura Web Matisses <info@matisses.co>");
                            //dtoMail.setFrom(socio.getNombres() + "<" + socio.getEmail() + ">");
                            dtoMail.setSubject("Confirmación compra web");

                            dtoMail.addBccAddress(applicationBean.getDestinatariosPlantillaEmail().get("compra_web").getTo().get(0));
                            dtoMail.addToAddress(socio.getEmail());

                            try {
                                sendHtmlEmailREST.sendMail360(dtoMail);
                            } catch (Exception e) {
                                CONSOLE.log(Level.SEVERE, "Ocurrio un error al enviar la notificacion", e);
                            }

                            //TODO: Se registra la factura en documentos pendientes, para que se generen los documentos necesarios
                            BaruDocumentoPendiente doc = new BaruDocumentoPendiente();

                            String id = String.valueOf(System.currentTimeMillis());

                            doc.setCode(id);
                            doc.setName(id);
                            doc.setuIntentos(0);
                            doc.setuNumeroDocumento(Integer.parseInt(s[0]));
                            doc.setuTipoDocumento("FV");
                            doc.setuEstado("P");

                            baruDocumentoPendienteFacade.create(doc);
                            CONSOLE.log(Level.INFO, "Se registro la factura {0} en documentos pendientes", s[0]);
                        } catch (Exception e) {
                            CONSOLE.log(Level.SEVERE, "Ocurrio un error inesperado", e);
                        }
                        try {
                            transaccionP2PFacade.edit(transaccion);
                        } catch (Exception e) {
                            CONSOLE.log(Level.SEVERE, "Ocurrio un error al actualizar los datos de P2P de la factura en la base de datos", e);
                            notificarErrorTransaccion("Error actualización", "Actualizar bd transacción PlaceToPay", "Id carrito: " + datos.getIdCarrito() + "<br/>" + e.getMessage());
                        }
                    }
                }
            }

            RespuestaPlaceToPayDTO res = new RespuestaPlaceToPayDTO();

            res.setDescripcionPago(transaccion.getDescPago());
            res.setEmail(transaccion.getCorreo());
            res.setFecha(transaccion.getPaymentDate());
            res.setImpuestos(transaccion.getTotalImpuesto().doubleValue());
            res.setIp(transaccion.getIp());
            res.setNombrePagador(transaccion.getNombres() + " " + transaccion.getApellidos());
            res.setReferenciaPago(transaccion.getRefPago());
            res.setValorTotal(transaccion.getTotalPago().doubleValue());
            if (transaccion.getDocNumFactura() != null && !transaccion.getDocNumFactura().isEmpty()) {
                res.setFactura(Integer.parseInt(transaccion.getDocNumFactura()));
            }

            RespuestaPlaceToPayDTO.Status status = new RespuestaPlaceToPayDTO.Status();

            status.setStatus(transaccion.getPaymentStatus());
            status.setMessage(transaccion.getPaymentMessage());

            res.setStatus(status);
            return Response.ok(res).build();
        } else {
            notificarErrorTransaccion("Error transacción", "Transacción PlaceToPay", "Id carrito: " + datos.getIdCarrito() + "<br/>" + "No se encontraron datos para consultar el estado de la transacción");
            return Response.ok(new ResponseDTO(0, "No se encontraron datos para consultar el estado de la transacción")).build();
        }
    }

    public void enviarCorreo(String template, String from, String subject, String toAddress, String bccAddress, List<String[]> adjuntos, Map<String, String> params) {
        MailMessageDTO dtoMail = new MailMessageDTO();

        dtoMail.setTemplateName(template);
        dtoMail.setParams(params);
        dtoMail.setAttachments(adjuntos);
        dtoMail.setFrom(from);
        dtoMail.setSubject(subject);

        dtoMail.addToAddress(toAddress);
        dtoMail.addBccAddress(bccAddress);

        try {
            sendHtmlEmailREST.sendMail360(dtoMail);
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al enviar la notificacion", e);
        }
    }

    private void notificarErrorTransaccion(String from, String processName, String errorMessage) {
        MailMessageDTO mail = new MailMessageDTO();

        mail.setFrom(from + " <notificaciones@matisses.co>");
        mail.setSubject(from);
        mail.addToAddress(applicationBean.getDestinatariosPlantillaEmail().get("error").getTo().get(0));
        mail.setAttachments(null);
        mail.setTemplateName("error");

        Map<String, String> params = new HashMap<>();

        params.put("processName", processName);
        params.put("errorMessage", errorMessage);
        params.put("username", "WebService");

        mail.setParams(params);

        sendHtmlEmailREST.sendMail360(mail);
    }

    private TransactionStatusResponseDTO consultarTransaccionPlaceToPay(String requestId) {
        try {
            return P2PServiceConnector.postPlaceToPayRequest(applicationBean.obtenerValorPropiedad("placetopay.url"), "session/" + requestId,
                    new TransactionStatusRequestDTO(applicationBean.obtenerValorPropiedad("placetopay.login"),
                            applicationBean.obtenerValorPropiedad("placetopay.trankey")), TransactionStatusResponseDTO.class);
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al consultar el estado de la transaccion en placetopay. ", e);
            return null;
        }
    }

    private ResponseDTO crearFactura(Integer metodoEnvio, String tiendaRecoge, List<ItemDTO> items, TransaccionPlacetopay transaccion, DatosCompraWebDTO datos) {
        if (items != null && !items.isEmpty()) {
            //consultar datos configurados para el detalle de la factura
            // 0. Codigo serie numeracion
            // 1. Nombre serie numeracion
            // 2. Codigo ventas
            // 3. Codigo logistica
            // 4. Codigo ruta
            // 5. Codigo proyecto
            // 6. WUID
            //productoPuedeAgregarse

            Object[] datosFacturaAlmacen = almacenFacade.cargarDatosFacturaWeb();

            if (!transaccion.getNroDocumento().toUpperCase().contains("CL")) {
                transaccion.setNroDocumento(transaccion.getNroDocumento() + "CL");
            }

            SalesDocumentDTO enc = new SalesDocumentDTO();

            enc.setCardCode(transaccion.getNroDocumento());
            enc.setPaymentGroupCode("17");
            enc.setShippingStatus("P");
            enc.setSource("T");
            enc.setComments("Compra WEB.");
            if (transaccion.getCodigoLista() != null && !transaccion.getCodigoLista().isEmpty()) {
                enc.setComments("Compra Lista de Regalos " + transaccion.getCodigoLista());
            }

            switch (metodoEnvio) {
                case 1:
                    enc.setComments(enc.getComments() + " Matisses realizara la entrega");
                    break;
                case 2:
                    enc.setComments(enc.getComments() + " El cliente recoge en " + tiendaRecoge);
                    break;
                case 3:
                    enc.setComments(enc.getComments() + " Coordinadora realizara la entrega");
                    break;
            }
            enc.setSeriesCode((String) datosFacturaAlmacen[0]);
            enc.setWuid((String) datosFacturaAlmacen[6]);
            enc.setLogisticsCostingCode((String) datosFacturaAlmacen[3]);
            enc.setRouteCostingCode((String) datosFacturaAlmacen[4]);
            enc.setSalesCostingCode((String) datosFacturaAlmacen[2]);
            enc.setProjectCode((String) datosFacturaAlmacen[5]);

            //TODO: Se asigna el asesor web para las compras por la pagina
            SalesEmployeeDTO salesEmp = new SalesEmployeeDTO();

            salesEmp.setSlpCode("98");
            salesEmp.setName("Vendedor Web");
            enc.addSalesEmployee(salesEmp);

            int lineNum = 0;
            for (ItemDTO item : items) {
                SalesDocumentLineDTO line = new SalesDocumentLineDTO();

                line.setItemCode(item.getItemcode());
                line.setLineNum(lineNum);
                line.setShippingStatus("P");
                if (datos.isPrecioNuevo()) {
                    line.setPrice(new BigDecimal(item.getNuevoPrecio()));
                } else {
                    line.setPrice(new BigDecimal(itemInventarioFacade.getItemPrice(item.getItemcode())));
                }

                //TODO: Validar si existe algun descuento para el item
                List<ProgramacionDescuento> descuentos = programacionDescuentoFacade.obtenerProgramacionesWebReferencia(item.getItemcode());
                if (datos.isPrecioNuevo() && item.getItemcode().equals("24400000000000000121")) {
                    line.setDiscountPercent(descuentos.get(0).getPorcentaje());
                } else if (descuentos != null && !descuentos.isEmpty() && !item.getItemcode().equals("24400000000000000121")) {
                    line.setDiscountPercent(descuentos.get(0).getPorcentaje());
                }

                //TODO: Consultar saldo en ubicaciones por referencia , ordenado de mayor a menor
                List<Object[]> saldosUbicacion = saldoUbicacionFacade.buscarPorReferencia(item.getItemcode());

                //TODO: Asignar las ubicaciones por cantidad
                int cantidadPendiente = item.getSelectedQuantity();
                CONSOLE.log(Level.INFO, "Verificando ubicaciones para la referencia [{0}]", item.getItemcode());

                int cantidadTotal = 0;
                cantidadTotal = saldosUbicacion.stream().map((saldo) -> (Integer) saldo[4]).reduce(cantidadTotal, Integer::sum);

                if (cantidadTotal < cantidadPendiente) {
                    CONSOLE.log(Level.SEVERE, "No hay saldo suficiente de la referencia {0}. Cantidad necesaria: {1}, cantidad disponible {2}", new Object[]{item.getItemcode(), item.getSelectedQuantity(), cantidadTotal});
                    return new ResponseDTO(-1, "No hay saldo suficiente de la referencia " + item.getItemcode() + ". Cantidad necesaria: " + item.getSelectedQuantity() + ", cantidad disponible: " + cantidadTotal);
                }

                for (Object[] saldo : saldosUbicacion) {
                    Integer binAbs = (Integer) saldo[2];
                    Integer saldoUbicacion = (Integer) saldo[4];
                    String whsCode = (String) saldo[0];
                    String binCode = (String) saldo[3];

                    if (line.getWhsCode() == null) {
                        line.setWhsCode(whsCode);
                    } else if (!line.getWhsCode().equals(whsCode)) {
                        //TODO: Si el almacen cambia, significa que el saldo en las ubicaciones del primer almacen no fue suficiente y se debe crear una nueva linea
                        enc.addLine(line);

                        line = new SalesDocumentLineDTO();

                        line.setItemCode(item.getItemcode());
                        line.setLineNum(lineNum++);
                        line.setPrice(new BigDecimal(itemInventarioFacade.getItemPrice(item.getItemcode())));
                        line.setWhsCode(whsCode);
                        line.setShippingStatus("P");
                    }

                    if (line.getQuantity() == null) {
                        line.setQuantity(Math.min(Math.min(saldoUbicacion, cantidadPendiente), item.getSelectedQuantity()));
                    } else {
                        line.setQuantity(line.getQuantity() + Math.min(saldoUbicacion, cantidadPendiente));
                    }

                    SalesDocumentLineBinAllocationDTO bin = new SalesDocumentLineBinAllocationDTO();

                    bin.setBinAbsEntry(binAbs);
                    bin.setWhsCode(whsCode);

                    if (cantidadPendiente <= saldoUbicacion) {
                        CONSOLE.log(Level.INFO, "La cantidad [{0}] en la ubicacion [{1}] satisface la cantidad pendiente [{2}]", new Object[]{saldoUbicacion, binCode, cantidadPendiente});
                        bin.setQuantity(cantidadPendiente);
                        cantidadPendiente = 0;
                    } else {
                        CONSOLE.log(Level.INFO, "La cantidad [{0}] en la ubicacion [{1}] No satisface la cantidad pendiente [{2}]. Faltan [{3}]",
                                new Object[]{saldoUbicacion, binCode, cantidadPendiente, cantidadPendiente - saldoUbicacion});
                        bin.setQuantity(saldoUbicacion);
                        cantidadPendiente -= saldoUbicacion;
                    }

                    line.addBinAllocation(bin);
                    if (cantidadPendiente == 0) {
                        break;
                    }
                }
                enc.addLine(line);
            }

            //Crear factura
            String numeroFV;
            Long docEntry;
            FacturaSAP factura;

            try {
                ResponseDTO res = new InvoicesREST().createInvoice("web", enc);

                if (res.getEstado() == 1) {
                    docEntry = res.getValor().longValue();
                    factura = facturaSAPFacade.findNoTransaction(docEntry.intValue());
                    numeroFV = Integer.toString(factura.getDocNum());
                    CONSOLE.log(Level.INFO, "Se creo con exito la factura {0}", numeroFV);

                    CONSOLE.log(Level.SEVERE, "a crear la compra");

                    ArrayList<ItemDTO> itemsCompra = (ArrayList<ItemDTO>) datos.getItems();
                    if (transaccion.getCodigoLista() != null) {
                        for (ItemDTO item : itemsCompra) {
                            ArrayList<ProductoListaRegalos> productosLista = (ArrayList<ProductoListaRegalos>) productoListaRegalosFacade.productoListaRegalos(datos.getIdLista(), item.getItemcode());

                            if (productosLista != null) {
                                for (ProductoListaRegalos productoLista : productosLista) {
                                    CompraListaRegalos entity = new CompraListaRegalos();

                                    entity.setCantidad(item.getSelectedQuantity());
                                    entity.setFactura(Integer.toString(factura.getDocNum()));
                                    entity.setFecha(new Date());
                                    entity.setLista(new ListaRegalos(datos.getIdLista()));
                                    entity.setMensaje(transaccion.getMensajeComprador());
                                    entity.setProducto(productoLista);
                                    entity.setQuienCompra(transaccion.getNombres() + " " + transaccion.getApellidos());
                                    entity.setTotal(item.getSelectedQuantity().longValue() * productoLista.getPrecio().longValue());

                                    compraListaRegalosFacade.create(entity);
                                    productoLista.setCantidadComprada(productoLista.getCantidadComprada() + item.getSelectedQuantity());
                                    productoListaRegalosFacade.edit(productoLista);
                                }
                            }

                        }
                    }

                    return new ResponseDTO(1, numeroFV + ";" + docEntry);
                } else {
                    CONSOLE.log(Level.SEVERE, "Ocurrio un error al crear la factura. {0}", res.getMensaje());
                    return new ResponseDTO(0, "Ocurrió un error al crear la factura. " + res.getMensaje());
                }
            } catch (B1WSServiceUnavailableException | NumberFormatException e) {
                CONSOLE.log(Level.SEVERE, "Ocurrio un error al crear la factura. {0}", e);
                return new ResponseDTO(0, "Ocurrió un error al crear la factura. " + e.getMessage());
            }
        }
        return new ResponseDTO(0, "No se encontraron ítems para facturar.");
    }

    private ResponseDTO crearFacturaServicio(Integer nroFactura, String wuid, Double costoEnvio, TransaccionPlacetopay transaccion) {
        SalesDocumentDTO enc = new SalesDocumentDTO();

        enc.setCardCode(transaccion.getNroDocumento());
        enc.setDocType("S");
        enc.setSeriesCode("266");
        enc.setSource("T");
        enc.setPaymentGroupCode("17");
        enc.setComments("Correspondiente al transporte de la factura " + nroFactura);
        enc.setuNumFact(nroFactura.toString());
        enc.setWuid(wuid);
        enc.setDocumentLines(new ArrayList<>());

        SalesDocumentLineDTO det = new SalesDocumentLineDTO();

        det.setDscription("Transporte y envío de mercancía (Coordinadora)");

        det.setAcctCode(28150501L);//TODO: Cuenta configurada por finanzas para fvs por servicio de trasnsporte
        det.setTaxCode("I_LEG_T0");
        det.setPrice(new BigDecimal(costoEnvio));

        //TODO: Se asigna el asesor web para las compras por la pagina
        SalesEmployeeDTO salesEmp = new SalesEmployeeDTO();

        salesEmp.setSlpCode("98");
        salesEmp.setName("Vendedor Web");
        enc.addSalesEmployee(salesEmp);

        enc.getDocumentLines().add(det);

        //TODO: Crear factura servicio transporte
        Integer numeroFvTransp;
        FacturaSAP factura;
        try {
            ResponseDTO res = new InvoicesREST().createServiceInvoice(enc);
            if (res.getEstado() == 1) {
                factura = facturaSAPFacade.findNoTransaction(res.getValor());
                numeroFvTransp = factura.getDocNum();
                CONSOLE.log(Level.SEVERE, "Se creo con exito la factura por servicio de transporte. {0}", numeroFvTransp);
                return new ResponseDTO(1, numeroFvTransp);
            } else {
                CONSOLE.log(Level.SEVERE, "Ocurrio un error al crear la factura por servicio de trasnporte. {0}", res.getMensaje());
                return new ResponseDTO(0, "Ocurrió un error al crear la factura. " + res.getMensaje());
            }
        } catch (B1WSServiceUnavailableException | NumberFormatException e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al crear la factura por servico de transporte. {0}", e);
            return new ResponseDTO(0, "Ocurrió un error al crear la factura por servico de transporte. " + e.getMessage());
        }
    }

    private Response crearReciboCaja(List<PaymentInvoicesDTO> facturas, Double totalPago, String nit, TransactionStatusResponseDTO datosPago) throws Exception {
        String nroDocumento = datosPago.getRequest().getPayer().getDocument();
        if (!nroDocumento.toUpperCase().endsWith("CL")) {
            nroDocumento += "CL";
        }

        PaymentDTO pagoDto = new PaymentDTO();

        pagoDto.setPaymentType(PaymentDTO.PaymentTypeDTO.CUSTOMER);
        pagoDto.setCardCode(nit);
        pagoDto.setCreditType("I");
        pagoDto.setPaymentInvoice(facturas);
        pagoDto.setSeriesCode("151");//TODO: parametrizar serie de numeracion
        pagoDto.setDocType(ConstantTypes.DocType.INVOICE);
        List<CreditCardPaymentDTO> creditCardPayments = new ArrayList<>();

        CreditCardPaymentDTO creditPayment = new CreditCardPaymentDTO();

        creditPayment.setCreditCardCode(applicationBean.getTipoTarjetaP2P(datosPago.getPayment().get(0).getFranchise()).getIdTarjetaSAP().toString());
        creditPayment.setCreditCardNumber(StringUtils.right(datosPago.getPayment().get(0).getProcessorFields().get(0).getValue(), 4));
        creditPayment.setNumberOfPayments("1");
        creditPayment.setPaidSum(totalPago.toString());
        creditPayment.setValidUntil(null);//TODO: configurar fecha de validez
        creditPayment.setVoucherNumber(datosPago.getPayment().get(0).getAuthorization());
        creditCardPayments.add(creditPayment);
        pagoDto.setCreditCardPayments(creditCardPayments);

        try {
            Response res = new IncomingPaymentServiceREST().addIncomingPayment(pagoDto, "web");
            ResponseDTO dto = (ResponseDTO) res.getEntity();
            CONSOLE.log(Level.SEVERE, "inmediatamente despues del recibo {0}. ", dto.getMensaje());
            if (dto.getEstado() == 0) {
                return Response.ok(new ResponseDTO(0, dto.getMensaje())).build();
            } else {
                return Response.ok(new ResponseDTO(1, "")).build();
            }
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al crear el recibo de caja. ", e);
            return Response.ok(new ResponseDTO(0, "No fue posible registrar el pago")).build();
        }
    }

    private TransaccionPlacetopay crearTransaccionPlaceToPay(DatosPagoPlaceToPayDTO dto) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ", Locale.getDefault());
        TransaccionPlacetopay entidad = new TransaccionPlacetopay();

        entidad.setApellidos(dto.getBuyer().getSurname());
        entidad.setCiudad(dto.getBuyer().getAddress().getCity());
        entidad.setCorreo(dto.getBuyer().getEmail());
        entidad.setDescPago(dto.getPayment().getDescription());
        entidad.setDireccion(dto.getBuyer().getAddress().getStreet());
        try {
            entidad.setExpira(sdf.parse(dto.getExpiration()));
        } catch (Exception e) {
            Calendar cal = new GregorianCalendar();
            cal.add(Calendar.HOUR, 12);
            entidad.setExpira(cal.getTime());
        }
        entidad.setIp(dto.getIpAddress());
        entidad.setLogin(dto.getAuth().getLogin());
        entidad.setMoneda("COP");
        entidad.setNombres(dto.getBuyer().getName());
        entidad.setNonce(dto.getAuth().getNonce());
        entidad.setNroDocumento(dto.getBuyer().getDocument().replace("CL", ""));
        entidad.setPais("CO");
        entidad.setRefPago(dto.getPayment().getReference());
        entidad.setSeed(dto.getAuth().getSeed());
        entidad.setTipoDocumento(dto.getBuyer().getDocumentType());
        entidad.setTipoImpuesto(dto.getPayment().getAmount().getTaxes().getKind());
        entidad.setTotalImpuesto(Integer.parseInt(dto.getPayment().getAmount().getTaxes().getAmount()));
        entidad.setTotalPago(Integer.parseInt(dto.getPayment().getAmount().getTotal()));
        entidad.setTranKey(dto.getAuth().getTranKey());
        entidad.setUrlRetorno(dto.getReturnUrl());
        entidad.setUserAgent(dto.getUserAgent());
        entidad.setTipoCompra("P");
        entidad.setCodigoLista(dto.getCodigoLista());
        entidad.setMensajeComprador(dto.getMessage());

        transaccionP2PFacade.create(entidad);
        return entidad;
    }

    private void actualizarTransaccionPlaceToPay(TransaccionPlacetopay entidad, RespuestaPlaceToPayDTO dto) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ", Locale.getDefault());
        entidad.setRequestId(dto.getRequestId().toString());
        try {
            entidad.setResponseDate(sdf.parse(dto.getStatus().getDate()));
        } catch (Exception e) {
            entidad.setResponseDate(new Date());
        }
        entidad.setStatus(dto.getStatus().getStatus());
        entidad.setMessage(dto.getStatus().getMessage());
        entidad.setProcessUrl(dto.getProcessUrl());
        entidad.setReason(dto.getStatus().getReason());

        try {
            transaccionP2PFacade.edit(entidad);
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al actualizar la transaccion de placetopay con la respuesta recibida. ", e);
        }
    }

    @POST
    @Path("consultarbono")
    @Consumes({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response consultarTransaccionBono(final DatosCompraWebDTO datos) {
        CONSOLE.log(Level.INFO, "entra en el servicio -->", datos.getIdCarrito());
        if (datos.getIdCarrito() != null && !datos.getIdCarrito().isEmpty()) {
            CONSOLE.log(Level.INFO, "Inicia proceso de consulta de transaccion PlaceTpPay para el carrito {0}", datos.getIdCarrito());
            TransaccionPlacetopay transaccion = transaccionP2PFacade.consultarPorReferenciaPago(datos.getIdCarrito());

            if (transaccion == null) {
                CONSOLE.log(Level.INFO, "No se encontro ninguna transaccion PalceToPay para el carrito {0}", datos.getIdCarrito());
                notificarErrorTransaccion("Error transacción", "Transacción PlaceToPay", "Id carrito: " + datos.getIdCarrito() + "<br/>" + "No se encontraró ninguna transacción con el id carrito");
                return Response.ok(new ResponseDTO(-1, "No se encontró ninguna transacción.")).build();
            }

            TransactionStatusResponseDTO transactionStatus = consultarTransaccionPlaceToPay(transaccion.getRequestId());
            if (transaccion.getPaymentStatus() == null || transaccion.getPaymentStatus().equals("PENDING") && transactionStatus != null) {
                CONSOLE.log(Level.INFO, "Validando estado PlaceToPay para el carrito {0}", datos.getIdCarrito());
                transaccion.setPaymentStatus(transactionStatus.getStatus().getStatus());
                transaccion.setPaymentMessage(transactionStatus.getStatus().getMessage());

                try {
                    transaccionP2PFacade.edit(transaccion);
                } catch (Exception e) {
                    CONSOLE.log(Level.SEVERE, "Ocurrio un error al actualizar los datos de P2P en la base de datos", e);
                    notificarErrorTransaccion("Error actualización", "Actualizar BD Transacción PlaceToPay", "Id carrito: " + datos.getIdCarrito() + "<br/>" + e.getMessage());
                    return Response.ok(new ResponseDTO(-1, "No se encontró ninguna transacción.")).build();
                }
            }

            if (transactionStatus.getPayment() != null && !transactionStatus.getPayment().isEmpty()) {
                CONSOLE.log(Level.INFO, "Validando pagos PlaceToPay para el carrito {0}", datos.getIdCarrito());
                transaccion.setPaymentReference(transactionStatus.getPayment().get(0).getReference());
                transaccion.setPaymentMethod(transactionStatus.getPayment().get(0).getPaymentMethod());
                transaccion.setPaymentMethodName(transactionStatus.getPayment().get(0).getPaymentMethodName());
                transaccion.setPaymentIssuerName(transactionStatus.getPayment().get(0).getIssuerName());
                transaccion.setPaymentFranchise(transactionStatus.getPayment().get(0).getFranchise());
                transaccion.setPaymentReceipt(transactionStatus.getPayment().get(0).getReceipt());
                transaccion.setPaymentAuthorization(transactionStatus.getPayment().get(0).getAuthorization());
                transaccion.setPaymentLastDigits(transactionStatus.getPayment().get(0).getProcessorFields().get(0).getValue());
                transaccion.setPaymentDate(transactionStatus.getPayment().get(0).getStatus().getDate());
                transaccion.setPaymentInternalReference(transactionStatus.getPayment().get(0).getInternalReference());
                transaccion.setTipoCompra("P");

                try {
                    transaccionP2PFacade.edit(transaccion);
                } catch (Exception e) {
                    CONSOLE.log(Level.SEVERE, "Ocurrio un error al actualizar los datos de P2P en la base de datos", e);
                    notificarErrorTransaccion("Error actualización", "Actualizar BD Transacción PlaceToPay", "Id carrito: " + datos.getIdCarrito() + "<br/>" + e.getMessage());
                }
            }

            if (transaccion.getPaymentStatus().equals("APPROVED") && (transaccion.getDocEntryFactura() == null || transaccion.getDocEntryFactura() == 0)) {
                CONSOLE.log(Level.INFO, "Si la transaccion PlaceToPay para el carrito {0} es exitosa se crea el recibo", datos.getIdCarrito());
                SocioDeNegocios socio = socioDeNegociosFacade.findByCardCode(transaccion.getNroDocumento().contains("CL") ? transaccion.getNroDocumento() : transaccion.getNroDocumento() + "CL");
                ListaRegalos lista = listaRegalosFacade.consultarListaPorCodigo(transaccion.getCodigoLista());
                if (socio == null || socio.getCardCode() == null || !socio.getCardCode().isEmpty()) {

                    try {
                        //Long docEntryRC = crearReciboCajaBono(null, transactionStatus);
                        String docEntryRC = crearReciboCajaBono(null, transaccion.getTotalPago().doubleValue(), transaccion.getNroDocumento() + "CL", transactionStatus);
                        CONSOLE.log(Level.INFO, "Se creo el recibo {0}", docEntryRC);

                        //2. Crear asiento saldo a favor
                        String nroDocPagador = transaccion.getNroDocumento();
                        String nroDocPropietario = lista.getCedulaCreador();
                        if (!nroDocPagador.toUpperCase().endsWith("CL")) {
                            nroDocPagador += "CL";
                        }
                        if (!nroDocPropietario.toUpperCase().endsWith("CL")) {
                            nroDocPropietario += "CL";
                        }
                        crearAsientoAjusteSaldoFavor(docEntryRC,
                                transaccion.getTotalPago().longValue(), nroDocPagador, nroDocPropietario);

                        CompraListaRegalos compra = new CompraListaRegalos();
                        compra.setCantidad(1);
                        compra.setFactura(null);
                        compra.setFecha(new Date());
                        compra.setLista(new ListaRegalos(lista.getIdLista()));
                        compra.setMensaje(transaccion.getMensajeComprador());
                        compra.setProducto(null);
                        compra.setQuienCompra(transaccion.getNombres() + " " + transaccion.getApellidos());
                        compra.setTotal(transaccion.getTotalPago().longValue());

                        compraListaRegalosFacade.create(compra);

                    } catch (Exception e) {
                        CONSOLE.log(Level.SEVERE, "Ocurrio un error al crear el recibo de caja. ", e);
                        //TODO: notificar dpto sistemas
                    }

                    try {

                        if (transaccion.getCodigoLista() != null && !transaccion.getCodigoLista().isEmpty()) {
                            CONSOLE.log(Level.SEVERE, "transaccion codigo lista", transaccion.getCodigoLista());
                            //Se envia notificacion al usuario de la lista de regalos, si y solo si, este pidio notificacion por este medio
                            //ListaRegalos lista = listaRegalosFacade.consultarListaPorCodigo(transaccion.getCodigoLista());

                            if (lista != null && lista.getIdLista() != null && lista.getIdLista() != 0) {
                                Map<String, String> params = new HashMap<>();

                                params.put("comentario", "Mira los regalos que te han comprado por Lista de Regalos");
                                params.put("link", applicationBean.obtenerValorPropiedad("listaregalos.url.web") + lista.getCodigo());

                                if (transaccion.getMensajeComprador() != null && !transaccion.getMensajeComprador().isEmpty()) {
                                    StringBuilder sb = new StringBuilder();

                                    sb.append("<tr><td style=\"text-align: justify; padding: 20px; border: 1px solid #DDDDDD; border-radius: 10px;\">");
                                    sb.append(transaccion.getMensajeComprador());
                                    sb.append("</td></tr><tr><td style=\"text-align: right; padding-bottom: 20px;\"><br/>");
                                    sb.append("Atentamente,<br/>");
                                    sb.append("<span style=\"font-style: italic\">");
                                    sb.append(transaccion.getNombres());
                                    sb.append("</span></td></tr>");
                                    sb.append("<tr><td style=\"background-color: #EEEEEE; padding: 5px; text-align: left;\">Regalos que te compraron.</td></tr>");

                                    params.put("mensaje", sb.toString());
                                }

                                if (datos.getItems() != null && !datos.getItems().isEmpty()) {
                                    StringBuilder sb = new StringBuilder();

                                    sb.append("<table style=\"width: 100%; border: 1px solid #DDDDDD; margin-top: 10px; border-collapse: collapse;\">");
                                    sb.append("<tr>");
                                    sb.append("<th style=\"border: 1px solid #DDDDDD;\">Imagen</th>");
                                    sb.append("<th style=\"border: 1px solid #DDDDDD; margin: 10px;\">PRODUCTO</th>");
                                    sb.append("<th style=\"border: 1px solid #DDDDDD; margin: 10px;\">MONTO</th>");
                                    sb.append("</tr>");

                                    for (ItemDTO i : datos.getItems()) {
                                        sb.append("<tr>");
                                        sb.append("<td style=\"border: 1px solid #DDDDDD; text-align: center; margin: 10px;\"><img src=\"");
                                        sb.append(imagenProductoMBean.obtenerUrlProducto(i.getItemcode(), true));
                                        sb.append("\"></img></td>");
                                        sb.append("<td style=\"border: 1px solid #DDDDDD; margin: 10px;\">");
                                        sb.append(bcsGenericMBean.obtenerNombreReferencia(i.getItemcode()));
                                        sb.append("<br/>Ref: ");
                                        sb.append(bcsGenericMBean.convertirARefCorta(i.getItemcode()).replace("*", ""));
                                        sb.append("</td>");
                                        sb.append("<td style=\"border: 1px solid #DDDDDD; margin: 10px; text-align: right;\">");
                                        sb.append(i.getSelectedQuantity());
                                        sb.append("</td>");
                                        sb.append("</tr>");
                                    }

                                    sb.append("</table>");
                                    params.put("tabla", sb.toString());
                                } else {
                                    StringBuilder sb = new StringBuilder();
                                    sb.append("<table style=\"width: 100%; border: 1px solid #DDDDDD; margin-top: 10px; border-collapse: collapse;\">");
                                    sb.append("<tr>");

                                    sb.append("<th style=\"border: 1px solid #DDDDDD; margin: 10px;\">Bono de Regalos</th>");
                                    sb.append("<th style=\"border: 1px solid #DDDDDD; margin: 10px;\">MONTO</th>");
                                    sb.append("</tr>");
                                    sb.append("<tr>");

                                    sb.append("<td style=\"border: 1px solid #DDDDDD; margin: 10px;\">");

                                    sb.append("<br/>Bono de Regalos ");

                                    sb.append("</td>");
                                    sb.append("<td style=\"border: 1px solid #DDDDDD; margin: 10px; text-align: right;\">");
                                    sb.append(transaccion.getTotalPago());
                                    sb.append("</td>");
                                    sb.append("</tr>");
                                    params.put("tabla", sb.toString());
                                }

                                if (lista.getNotificacionInmediataMailCreador()) {
                                    params.put("cliente", lista.getNombreCreador());

                                    enviarCorreo("lista_regalos", "Lista Regalos Matisses <listaideal@matisses.co>", "Lista Regalos Matisses", lista.getCorreoCreador(),
                                            applicationBean.getDestinatariosPlantillaEmail().get("lista_regalos").getTo().get(0), null, params);
                                }
                                if (lista.getNotificacionInmediataMailCocreador()) {
                                    params.put("cliente", lista.getNombreCocreador());

                                    enviarCorreo("lista_regalos", "Lista Regalos Matisses <listaideal@matisses.co>", "Lista Regalos Matisses", lista.getCorreoCocreador(),
                                            applicationBean.getDestinatariosPlantillaEmail().get("lista_regalos").getTo().get(0), null, params);
                                }

                                try {
                                    MensajeTextoDTO sms = new MensajeTextoDTO();

                                    sms.setCodigoPais("57");
                                    sms.setIp("192.168.5.56");
                                    sms.setPruebas(false);
                                    sms.setUsuario("sonda");

                                    if (lista.getNotificacionDiariaSmsCreador()) {
                                        sms.setDestino(lista.getTelefonoCreador());

                                        if (lista.getNotificacionDiariaMailCreador()) {
                                            sms.setMensaje("Hola " + lista.getNombreCreador() + ", te acaban de comprar un regalo. Revisa tu correo para mas detalle.");
                                        } else {
                                            sms.setMensaje("Hola " + lista.getNombreCreador() + ", te acaban de comprar un regalo. Revisa tu lista para mas detalle.");
                                        }

                                        smsServiceREST.enviarSMS(sms);
                                    }
                                    if (lista.getNotificacionDiariaSmsCocreador()) {
                                        sms.setDestino(lista.getTelefonoCocreador());

                                        if (lista.getNotificacionDiariaMailCocreador()) {
                                            sms.setMensaje("Hola " + lista.getNombreCocreador() + ", te acaban de comprar un regalo. Revisa tu correo para mas detalle.");
                                        } else {
                                            sms.setMensaje("Hola " + lista.getNombreCocreador() + ", te acaban de comprar un regalo. Revisa tu lista para mas detalle.");
                                        }

                                        smsServiceREST.enviarSMS(sms);
                                    }
                                } catch (Exception e) {
                                }

                                //Se envia mail de agradecimiento al comprador
                                Map<String, String> paramsInvitado = new HashMap<>();

                                paramsInvitado.put("comentario", "");

                                if (lista.getMensajeAgradecimiento() != null && !lista.getMensajeAgradecimiento().isEmpty()) {
                                    StringBuilder sb = new StringBuilder();

                                    sb.append("<tr><td style=\"text-align: justify; padding: 20px; border: 1px solid #DDDDDD; border-radius: 10px;\">");
                                    sb.append(lista.getMensajeAgradecimiento());
                                    sb.append("</td></tr><tr><td style=\"text-align: right; padding-bottom: 20px;\"><br/>");
                                    sb.append("Atentamente,<br/>");
                                    sb.append("<span style=\"font-style: italic\">");
                                    sb.append(lista.getNombre().replace("Boda", "").replace("Matrimonio", ""));
                                    sb.append("</span></td></tr>");
                                    sb.append("<tr><td style=\"background-color: #EEEEEE; padding: 5px; text-align: left;\">Regalos que compro.</td></tr>");

                                    paramsInvitado.put("mensaje", sb.toString());

                                    if (datos.getItems() != null && !datos.getItems().isEmpty()) {
                                        StringBuilder sb1 = new StringBuilder();

                                        sb1.append("<table style=\"width: 100%; border: 1px solid #DDDDDD; margin-top: 10px; border-collapse: collapse;\">");
                                        sb1.append("<tr>");
                                        sb1.append("<th style=\"border: 1px solid #DDDDDD;\">Imagen</th>");
                                        sb1.append("<th style=\"border: 1px solid #DDDDDD; margin: 10px;\">PRODUCTO</th>");
                                        sb1.append("<th style=\"border: 1px solid #DDDDDD; margin: 10px;\">MONTO</th>");
                                        sb1.append("</tr>");

                                        for (ItemDTO i : datos.getItems()) {
                                            sb1.append("<tr>");
                                            sb1.append("<td style=\"border: 1px solid #DDDDDD; text-align: center; margin: 10px;\"><img src=\"");
                                            sb1.append(imagenProductoMBean.obtenerUrlProducto(i.getItemcode(), true));
                                            sb1.append("\"></img></td>");
                                            sb1.append("<td style=\"border: 1px solid #DDDDDD; margin: 10px;\">");
                                            sb1.append(bcsGenericMBean.obtenerNombreReferencia(i.getItemcode()));
                                            sb1.append("<br/>Ref: ");
                                            sb1.append(bcsGenericMBean.convertirARefCorta(i.getItemcode()).replace("*", ""));
                                            sb1.append("</td>");
                                            sb1.append("<td style=\"border: 1px solid #DDDDDD; margin: 10px; text-align: right;\">");
                                            sb1.append(i.getSelectedQuantity());
                                            sb1.append("</td>");
                                            sb1.append("</tr>");
                                        }

                                        sb1.append("</table>");
                                        paramsInvitado.put("tabla", sb1.toString());
                                    } else {
                                        StringBuilder sb1 = new StringBuilder();
                                        sb1.append("<table style=\"width: 100%; border: 1px solid #DDDDDD; margin-top: 10px; border-collapse: collapse;\">");
                                        sb1.append("<tr>");

                                        sb1.append("<th style=\"border: 1px solid #DDDDDD; margin: 10px;\">Bono de Regalos</th>");
                                        sb1.append("<th style=\"border: 1px solid #DDDDDD; margin: 10px;\">MONTO</th>");
                                        sb1.append("</tr>");
                                        sb1.append("<tr>");

                                        sb1.append("<td style=\"border: 1px solid #DDDDDD; margin: 10px;\">");

                                        sb1.append("<br/>Bono de Regalos ");

                                        sb1.append("</td>");
                                        sb1.append("<td style=\"border: 1px solid #DDDDDD; margin: 10px; text-align: right;\">");
                                        sb1.append(transaccion.getTotalPago());
                                        sb1.append("</td>");
                                        sb1.append("</tr>");
                                        params.put("tabla", sb1.toString());
                                    }

                                    if (transaccion.getCorreo() != null && !transaccion.getCorreo().isEmpty()) {
                                        paramsInvitado.put("cliente", transaccion.getNombres());

                                        enviarCorreo("lista_regalos", "Lista Regalos Matisses <listaideal@matisses.co>", "Lista Regalos Matisses", transaccion.getCorreo(),
                                                applicationBean.getDestinatariosPlantillaEmail().get("lista_regalos").getTo().get(0), null, paramsInvitado);
                                    }
                                }
                            }
                        }

                        Map<String, String> mapa = new HashMap<>();
                        mapa.put("cliente", transaccion.getNombres());
                        mapa.put("recibo", "");
                        mapa.put("fecha", new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
                        mapa.put("hora", new SimpleDateFormat("HH:mm:ss").format(new Date()));

                        switch (datos.getMetodoEnvio()) {
                            case 1:
                                mapa.put("medioEnvio", "Matisses. Una semana el mejor precio.");
                                mapa.put("facturaServicio", "Free Shipping.");
                                break;
                            case 2:
                                mapa.put("medioEnvio", "Recoger en tienda " + datos.getTiendaRecoge() + ", nuestro colaborador se contactara para coordinar la fecha de entrega.");
                                mapa.put("facturaServicio", "No aplica.");
                                break;
                            case 3:
                                mapa.put("medioEnvio", "Coordinadora.");

                                break;
                            default:
                                mapa.put("medioEnvio", "");
                                break;
                        }

                        MailMessageDTO dtoMail = new MailMessageDTO();

                        dtoMail.setTemplateName("compraWeb");
                        dtoMail.setParams(mapa);
                        //dtoMail.setAttachments(adjuntos);
                        dtoMail.setFrom("Compra Web Matisses <info@matisses.co>");
                        //dtoMail.setFrom(socio.getNombres() + "<" + socio.getEmail() + ">");
                        dtoMail.setSubject("Confirmación compra web");

                        dtoMail.addBccAddress(applicationBean.getDestinatariosPlantillaEmail().get("compra_web").getTo().get(0));
                        dtoMail.addToAddress(socio.getEmail());

                        try {
                            sendHtmlEmailREST.sendMail360(dtoMail);
                        } catch (Exception e) {
                            CONSOLE.log(Level.SEVERE, "Ocurrio un error al enviar la notificacion", e);
                        }

                        //TODO: Se registra la factura en documentos pendientes, para que se generen los documentos necesarios
//                            BaruDocumentoPendiente doc = new BaruDocumentoPendiente();
//
//                            String id = String.valueOf(System.currentTimeMillis());
//
//                            doc.setCode(id);
//                            doc.setName(id);
//                            doc.setuIntentos(0);
//                            doc.setuNumeroDocumento(Integer.parseInt(s[0]));
//                            doc.setuTipoDocumento("FV");
//                            doc.setuEstado("P");
//
//                            baruDocumentoPendienteFacade.create(doc);
                        CONSOLE.log(Level.INFO, "Se registro recibo ");
                    } catch (Exception e) {
                        CONSOLE.log(Level.SEVERE, "Ocurrio un error inesperado", e);
                    }
                    try {
                        transaccionP2PFacade.edit(transaccion);
                    } catch (Exception e) {
                        CONSOLE.log(Level.SEVERE, "Ocurrio un error al actualizar los datos de P2P de la factura en la base de datos", e);
                        notificarErrorTransaccion("Error actualización", "Actualizar bd transacción PlaceToPay", "Id carrito: " + datos.getIdCarrito() + "<br/>" + e.getMessage());
                    }

                }
            }

            RespuestaPlaceToPayDTO res = new RespuestaPlaceToPayDTO();

            res.setDescripcionPago(transaccion.getDescPago());
            res.setEmail(transaccion.getCorreo());
            res.setFecha(transaccion.getPaymentDate());
            res.setImpuestos(transaccion.getTotalImpuesto().doubleValue());
            res.setIp(transaccion.getIp());
            res.setNombrePagador(transaccion.getNombres() + " " + transaccion.getApellidos());
            res.setReferenciaPago(transaccion.getRefPago());
            res.setValorTotal(transaccion.getTotalPago().doubleValue());
            if (transaccion.getDocNumFactura() != null && !transaccion.getDocNumFactura().isEmpty()) {
                res.setFactura(Integer.parseInt(transaccion.getDocNumFactura()));
            }

            RespuestaPlaceToPayDTO.Status status = new RespuestaPlaceToPayDTO.Status();

            status.setStatus(transaccion.getPaymentStatus());
            status.setMessage(transaccion.getPaymentMessage());

            res.setStatus(status);
            return Response.ok(res).build();
        } else {
            notificarErrorTransaccion("Error transacción", "Transacción PlaceToPay", "Id carrito: " + datos.getIdCarrito() + "<br/>" + "No se encontraron datos para consultar el estado de la transacción");
            return Response.ok(new ResponseDTO(0, "No se encontraron datos para consultar el estado de la transacción")).build();
        }
    }

    private void crearAsientoAjusteSaldoFavor(String docNumRC, Long valorBono, String cedulaPagador, String cedulaPropietario) throws Exception {
        String sesionSAP = sapB1WSBean.obtenerSesionSAP();
        CONSOLE.log(Level.INFO, "entra a crear el asiento {0}", docNumRC);
        JournalEntryDTO journalEntryHeader = new JournalEntryDTO();
        journalEntryHeader.setMemo("BONO LISTA DE REGALOS");
        journalEntryHeader.setRef1(docNumRC);
        journalEntryHeader.setTransactionCode("BONO");//TODO: parametrizar codigo de asiento
        journalEntryHeader.setDueDate(new Date());
        journalEntryHeader.setRefDate(new Date());
        journalEntryHeader.setTaxDate(new Date());

        JournalEntryLineDTO line1 = new JournalEntryLineDTO();
        line1.setLineId(0L);
        line1.setCredit(0D);
        line1.setDebit(valorBono.doubleValue());
        line1.setShortName(cedulaPagador);
        line1.setContraAccount(cedulaPropietario);
        line1.setInfoCo01(cedulaPagador);

        JournalEntryLineDTO line2 = new JournalEntryLineDTO();
        line2.setLineId(1L);
        line2.setCredit(valorBono.doubleValue());
        line2.setDebit(0D);
        line2.setShortName(cedulaPropietario);
        line2.setContraAccount(cedulaPagador);
        line2.setInfoCo01(cedulaPropietario);

        journalEntryHeader.addLine(line1);
        journalEntryHeader.addLine(line2);
        JournalEntriesServiceConnector jesc = sapB1WSBean.getJournalEntriesServiceConnectorInstance(sesionSAP);
        Long idEntradaDiario = jesc.createJournalEntry(journalEntryHeader);
        CONSOLE.log(Level.INFO, "Se creo el asiento contable con id={0}", idEntradaDiario);

    }

    private String crearReciboCajaBono(List<PaymentInvoicesDTO> facturas, Double totalPago, String nit, TransactionStatusResponseDTO datosPago) throws Exception {
        String nroDocumento = datosPago.getRequest().getPayer().getDocument();
        if (!nroDocumento.toUpperCase().endsWith("CL")) {
            nroDocumento += "CL";
        }

        PaymentDTO pagoDto = new PaymentDTO();

        pagoDto.setPaymentType(PaymentDTO.PaymentTypeDTO.CUSTOMER);
        pagoDto.setCardCode(nit);
        pagoDto.setCreditType("I");
        pagoDto.setPaymentInvoice(facturas);
        pagoDto.setSeriesCode("151");//TODO: parametrizar serie de numeracion
        pagoDto.setDocType(ConstantTypes.DocType.INVOICE);
        List<CreditCardPaymentDTO> creditCardPayments = new ArrayList<>();

        CreditCardPaymentDTO creditPayment = new CreditCardPaymentDTO();

        creditPayment.setCreditCardCode(applicationBean.getTipoTarjetaP2P(datosPago.getPayment().get(0).getFranchise()).getIdTarjetaSAP().toString());
        creditPayment.setCreditCardNumber(StringUtils.right(datosPago.getPayment().get(0).getProcessorFields().get(0).getValue(), 4));
        creditPayment.setNumberOfPayments("1");
        creditPayment.setPaidSum(totalPago.toString());
        creditPayment.setValidUntil(null);//TODO: configurar fecha de validez
        creditPayment.setVoucherNumber(datosPago.getPayment().get(0).getAuthorization());
        creditCardPayments.add(creditPayment);
        pagoDto.setCreditCardPayments(creditCardPayments);

        try {
            Response res = new IncomingPaymentServiceREST().addIncomingPayment(pagoDto, "web");
            ResponseDTO dto = (ResponseDTO) res.getEntity();

            if (dto.getEstado() == 0) {
                return dto.getMensaje();
            } else {
                return null;
            }
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al crear el recibo de caja. ", e);
            return null;
        }
    }

}
