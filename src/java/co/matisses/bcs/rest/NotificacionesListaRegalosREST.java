package co.matisses.bcs.rest;

import co.matisses.bcs.dto.MailMessageDTO;
import co.matisses.bcs.dto.MensajeTextoDTO;
import co.matisses.bcs.mbean.BCSApplicationMBean;
import co.matisses.bcs.mbean.BCSGenericMBean;
import co.matisses.bcs.mbean.ImagenProductoMBean;
import co.matisses.persistence.web.entity.CompraListaRegalos;
import co.matisses.persistence.web.entity.ListaRegalos;
import co.matisses.persistence.web.facade.CompraListaRegalosFacade;
import co.matisses.persistence.web.facade.ListaRegalosFacade;
import java.util.ArrayList;
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
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author ygil
 */
@Stateless
@Path("notificacioneslistaregalo")
public class NotificacionesListaRegalosREST {

    @Inject
    private ImagenProductoMBean imagenProductoMBean;
    @Inject
    private BCSGenericMBean genericMBean;
    @Inject
    private BCSApplicationMBean applicationMBean;
    private static final Logger CONSOLE = Logger.getLogger(NotificacionesListaRegalosREST.class.getSimpleName());
    @EJB
    private SendHtmlEmailREST emailREST;
    @EJB
    private SMSServiceREST smsServiceREST;
    @EJB
    private ListaRegalosFacade listaRegalosFacade;
    @EJB
    private CompraListaRegalosFacade compraListaRegalosFacade;

    @GET
    @Path("diaria/{pruebas}")
    @Consumes({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response ejecutarDiario(@PathParam("pruebas") boolean pruebas) {
        CONSOLE.log(Level.INFO, "Se esta ejecutando la sonda de notificacion diaria de lista de regalos");
        List<ListaRegalos> listas = listaRegalosFacade.obtenerListasNotificacionDiaria();

        if (listas != null && !listas.isEmpty()) {
            for (ListaRegalos l : listas) {
                List<String[]> datos = new ArrayList<>();
                List<CompraListaRegalos> compras = compraListaRegalosFacade.obtenerComprasDiariasLista(l.getIdLista().intValue());

                if (compras != null && !compras.isEmpty()) {
                    for (CompraListaRegalos c : compras) {
                        boolean existe = false;

                        for (String[] s : datos) {
                            if (s[0].equals(c.getProducto().getReferencia())) {
                                s[1] = String.valueOf(Integer.parseInt(s[1]) + c.getCantidad());
                                break;
                            }
                        }

                        if (!existe) {
                            String[] s = new String[2];

                            s[0] = c.getProducto().getReferencia();
                            s[1] = String.valueOf(c.getCantidad());

                            datos.add(s);
                        }
                    }

                    if (datos != null && !datos.isEmpty()) {
                        armarCorreo("Este es el resumen diario de los regalos que te han comprado por Lista de Regalos", l, datos);
                    }

                    try {
                        MensajeTextoDTO sms = new MensajeTextoDTO();

                        sms.setCodigoPais("57");
                        sms.setIp("192.168.5.56");
                        sms.setPruebas(pruebas);
                        sms.setUsuario("sonda");

                        if (l.getNotificacionDiariaSmsCreador()) {
                            sms.setDestino(l.getTelefonoCreador());

                            if (l.getNotificacionDiariaMailCreador()) {
                                sms.setMensaje("Hola " + l.getNombreCreador() + ", ya se genero tu informe diario con los regalos comprados. Revisa tu correo para mas detalle.");
                            } else {
                                sms.setMensaje("Hola " + l.getNombreCreador() + ", ya se genero tu informe diario con los regalos comprados. Revisa tu lista para mas detalle.");
                            }

                            smsServiceREST.enviarSMS(sms);
                        }
                        if (l.getNotificacionDiariaSmsCocreador()) {
                            sms.setDestino(l.getTelefonoCocreador());

                            if (l.getNotificacionDiariaMailCocreador()) {
                                sms.setMensaje("Hola " + l.getNombreCocreador() + ", ya se genero tu informe diario con los regalos comprados. Revisa tu correo para mas detalle.");
                            } else {
                                sms.setMensaje("Hola " + l.getNombreCocreador() + ", ya se genero tu informe diario con los regalos comprados. Revisa tu lista para mas detalle.");
                            }

                            smsServiceREST.enviarSMS(sms);
                        }
                    } catch (Exception e) {
                    }
                }
            }
        }

        return Response.ok().build();
    }

    @GET
    @Path("semana/{pruebas}")
    @Consumes({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response ejecutarSemanal(@PathParam("pruebas") boolean pruebas) {
        List<ListaRegalos> listas = listaRegalosFacade.obtenerListasNotificacionSemanal();

        if (listas != null && !listas.isEmpty()) {
            for (ListaRegalos l : listas) {
                List<String[]> datos = new ArrayList<>();
                List<CompraListaRegalos> compras = compraListaRegalosFacade.obtenerComprasSemanalesLista(l.getIdLista().intValue());

                if (compras != null && !compras.isEmpty()) {
                    for (CompraListaRegalos c : compras) {
                        boolean existe = false;

                        for (String[] s : datos) {
                            if (s[0].equals(c.getProducto().getReferencia())) {
                                s[1] = String.valueOf(Integer.parseInt(s[1]) + c.getCantidad());
                                break;
                            }
                        }

                        if (!existe) {
                            String[] s = new String[2];

                            s[0] = c.getProducto().getReferencia();
                            s[1] = String.valueOf(c.getCantidad());

                            datos.add(s);
                        }
                    }

                    if (datos != null && !datos.isEmpty()) {
                        armarCorreo("Este es el resumen semanal de los regalos que te han comprado por Lista de Regalos", l, datos);
                    }

                    try {
                        MensajeTextoDTO sms = new MensajeTextoDTO();

                        sms.setCodigoPais("57");
                        sms.setIp("192.168.5.56");
                        sms.setPruebas(pruebas);
                        sms.setUsuario("sonda");

                        if (l.getNotificacionDiariaSmsCreador()) {
                            sms.setDestino(l.getTelefonoCreador());

                            if (l.getNotificacionDiariaMailCreador()) {
                                sms.setMensaje("Hola " + l.getNombreCreador() + ", ya se genero tu informe semanal con los regalos comprados. Revisa tu correo para mas detalle.");
                            } else {
                                sms.setMensaje("Hola " + l.getNombreCreador() + ", ya se genero tu informe semanal con los regalos comprados. Revisa tu lista para mas detalle.");
                            }

                            smsServiceREST.enviarSMS(sms);
                        }
                        if (l.getNotificacionDiariaSmsCocreador()) {
                            sms.setDestino(l.getTelefonoCocreador());

                            if (l.getNotificacionDiariaMailCocreador()) {
                                sms.setMensaje("Hola " + l.getNombreCocreador() + ", ya se genero tu informe semanal con los regalos comprados. Revisa tu correo para mas detalle.");
                            } else {
                                sms.setMensaje("Hola " + l.getNombreCocreador() + ", ya se genero tu informe semanal con los regalos comprados. Revisa tu lista para mas detalle.");
                            }

                            smsServiceREST.enviarSMS(sms);
                        }
                    } catch (Exception e) {
                    }
                }
            }
        }

        return Response.ok().build();
    }

    private void armarCorreo(String comentario, ListaRegalos lista, List<String[]> datos) {
        Map<String, String> params = new HashMap<>();

        params.put("comentario", comentario);
        params.put("mensaje", "<tr><td style=\"background-color: #EEEEEE; padding: 5px; text-align: left;\">Regalos que te compraron.</td></tr>");

        if (datos != null && !datos.isEmpty()) {
            StringBuilder sb = new StringBuilder();

            sb.append("<table style=\"width: 100%; border: 1px solid #DDDDDD; margin-top: 10px; border-collapse: collapse;\">");
            sb.append("<tr>");
            sb.append("<th style=\"border: 1px solid #DDDDDD;\">Imagen</th>");
            sb.append("<th style=\"border: 1px solid #DDDDDD; margin: 10px;\">PRODUCTO</th>");
            sb.append("<th style=\"border: 1px solid #DDDDDD; margin: 10px;\">MONTO</th>");
            sb.append("</tr>");

            for (String[] s : datos) {
                sb.append("<tr>");
                sb.append("<td style=\"border: 1px solid #DDDDDD; text-align: center; margin: 10px;\"><img src=\"");
                sb.append(imagenProductoMBean.obtenerUrlProducto(s[0], true));
                sb.append("\"></img></td>");
                sb.append("<td style=\"border: 1px solid #DDDDDD; margin: 10px;\">");
                sb.append(genericMBean.obtenerNombreReferencia(s[0]));
                sb.append("<br/>Ref: ");
                sb.append(genericMBean.convertirARefCorta(s[0]).replace("*", ""));
                sb.append("</td>");
                sb.append("<td style=\"border: 1px solid #DDDDDD; margin: 10px; text-align: right;\">");
                sb.append(s[1]);
                sb.append("</td>");
                sb.append("</tr>");
            }

            sb.append("</table>");
            params.put("tabla", sb.toString());
        } else {
            params.put("tabla", "");
        }

        if (lista.getNotificacionDiariaMailCreador()) {
            params.put("cliente", lista.getNombreCreador());

            enviarCorreo("lista_regalos", "Lista Regalos Matisses <listaideal@matisses.co>", "Lista Regalos Matisses", lista.getCorreoCreador(),
                    applicationMBean.getDestinatariosPlantillaEmail().get("lista_regalos").getTo().get(0), null, params);
        }
        if (lista.getNotificacionDiariaMailCocreador()) {
            params.put("cliente", lista.getNombreCocreador());

            enviarCorreo("lista_regalos", "Lista Regalos Matisses <listaideal@matisses.co>", "Lista Regalos Matisses", lista.getCorreoCocreador(),
                    applicationMBean.getDestinatariosPlantillaEmail().get("lista_regalos").getTo().get(0), null, params);
        }
    }

    private void enviarCorreo(String template, String from, String subject, String toAddress, String bccAddress, List<String[]> adjuntos, Map<String, String> params) {
        MailMessageDTO dtoMail = new MailMessageDTO();

        dtoMail.setTemplateName(template);
        dtoMail.setParams(params);
        dtoMail.setAttachments(adjuntos);
        dtoMail.setFrom(from);
        dtoMail.setSubject(subject);

        dtoMail.addToAddress(toAddress);
        dtoMail.addBccAddress(bccAddress);

        try {
            emailREST.sendMail360(dtoMail);
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al enviar la notificacion", e);
        }
    }
}
