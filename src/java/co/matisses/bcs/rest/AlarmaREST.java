package co.matisses.bcs.rest;

import co.matisses.bcs.dto.MailMessageDTO;
import co.matisses.bcs.dto.PrintReportDTO;
import co.matisses.bcs.dto.ResponseDTO;
import co.matisses.bcs.mbean.BCSApplicationMBean;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import net.sf.jasperreports.engine.JRException;

/**
 *
 * @author jguisao
 */
@Stateless
@Path("alarma")
public class AlarmaREST {

    private static final Logger CONSOLE = Logger.getLogger(MercadoLibreREST.class.getSimpleName());
    @Inject
    private BCSApplicationMBean applicationMBean;
    @Inject
    private SendHtmlEmailREST emailREST;
    @EJB
    private PrintReportREST reportREST;
    private Date date;
    private List<String[]> adjuntos;

    @GET
    @Path("sondaVentaDevolucion")
    @Produces({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void ejecutarInformeVentasDevoluciones() throws JRException, Exception {
        adjuntos = new ArrayList<>();
        date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        date = calendar.getTime();

        PrintReportDTO dto = new PrintReportDTO();

        /*asignando parametros para el informe de ventas*/
        dto.setDirigido(new SimpleDateFormat("yyyy-MM-dd").format(date));
        dto.setDocumento("alarmaVentas");
        dto.setAlias("ventas");
        dto.setCopias(1);

        ResponseDTO resVt = null;
        try {
            resVt = reportREST.generarReporte(dto);
            if (resVt.getEstado() == 0) {
                String[] adjunto = new String[2];
                if (new File(resVt.getMensaje()).exists()) {
                    adjunto = new String[]{resVt.getMensaje(), dto.getAlias() + ".pdf"};
                    adjuntos.add(adjunto);
                }
            }
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al generar el informe de ventas", e);
        }

        dto = new PrintReportDTO();

        /*asignando parametros para el informe de devoluciones*/
        dto.setDirigido(new SimpleDateFormat("yyyy-MM-dd").format(date));
        dto.setDocumento("alarmaDevoluciones");
        dto.setAlias("devoluciones");
        dto.setCopias(1);

        ResponseDTO resDv = null;

        try {
            resDv = reportREST.generarReporte(dto);
            if (resDv.getEstado() == 0) {
                String[] adjunto = new String[2];
                if (new File(resDv.getMensaje()).exists()) {
                    adjunto = new String[]{resDv.getMensaje(), dto.getAlias() + ".pdf"};
                    adjuntos.add(adjunto);
                }
            }
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al generar el informe de devoluciones", e);
        }

        if ((resDv.getEstado() == 0 || resVt.getEstado() == 0) && (!adjuntos.isEmpty())) {
            notificarAlarma("Ventas y devoluciones",
                    "Adjunto se encuentra el informe de art&iacute;culos vendidos y devueltos en las tiendas matisses el d&iacute;a " + new SimpleDateFormat("yyyy-MM-dd").format(date) + ".",
                    "Artículos vendidos y devueltos en tiendas Matisses-" + new SimpleDateFormat("yyyy-MM-dd").format(date), "ventasDevoluciones", false, adjuntos);
        }
    }

    @GET
    @Path("sondaCursoAlturas")
    @Produces({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void ejecutarInformeCursoAlturas() {
        adjuntos = new ArrayList<>();
        date = new Date();

        PrintReportDTO dto = new PrintReportDTO();

        /*asignando parametros para el informe de ventas*/
        dto.setDocumento("alarmaCursoAlturas");
        dto.setAlias("cursoAlturas");
        dto.setCopias(1);

        ResponseDTO res = null;

        try {
            res = reportREST.generarReporte(dto);
            if (res.getEstado() == 0) {
                String[] adjunto = new String[2];
                if (new File(res.getMensaje()).exists()) {
                    adjunto = new String[]{res.getMensaje(), dto.getAlias() + ".pdf"};
                    adjuntos.add(adjunto);
                }
            }
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al generar el informe de curso alturas", e);
        }

        if (res.getEstado() == 0 && !adjuntos.isEmpty()) {
            notificarAlarma("Curso Alturas",
                    "Adjunto se encuentra el Informe consolidado del curso de alturas de los empleados Matisses.",
                    "Consolidación de Curso de Alturas-" + new SimpleDateFormat("yyyy-MM-dd").format(date), "cursoAlturas", false, adjuntos);
        }
    }

    public void notificarAlarma(String tipoAlarma, String mensaje, String asunto, String nombreAlarma, boolean error, List<String[]> adjuntos) {
        //Enviar por correo el detalle con la repuesta
        MailMessageDTO msg = new MailMessageDTO();

        msg.setFrom("Alarma <notificaciones@matisses.co>");
        msg.setSubject(asunto);

        Map<String, String> params = new HashMap<>();

        if (error) {
            params.put("processName", tipoAlarma);
            params.put("errorMessage", mensaje);
            params.put("username", "Sonda");

            msg.setTemplateName("error");
            msg.addToAddress(applicationMBean.getDestinatariosPlantillaEmail().get("error").getTo().get(0));
        } else {
            params.put("tipoAlarma", tipoAlarma);
            params.put("mensaje", mensaje);

            if (nombreAlarma.equals("ventasDevoluciones")) {
                msg.setTemplateName("alarma_ventas_devoluciones");
                msg.addToAddress(applicationMBean.getDestinatariosPlantillaEmail().get("alarma_ventas_devoluciones").getTo().get(0));
                msg.addBccAddress(applicationMBean.getDestinatariosPlantillaEmail().get("alarma_ventas_devoluciones").getBcc().get(0));
                msg.setCc(applicationMBean.getDestinatariosPlantillaEmail().get("alarma_ventas_devoluciones").getCc());
            }
            if (nombreAlarma.equals("cursoAlturas")) {
                msg.setTemplateName("alarma_curso_alturas");
                msg.addToAddress(applicationMBean.getDestinatariosPlantillaEmail().get("alarma_curso_alturas").getTo().get(0));
            }
            msg.setAttachments(adjuntos);
        }
        msg.setParams(params);
        emailREST.sendMail(msg);
    }
}
