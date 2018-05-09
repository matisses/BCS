package co.matisses.bcs.rest;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ws.rs.Produces;
import javax.naming.InitialContext;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.sql.DataSource;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.apache.pdfbox.pdmodel.PDDocument;
import co.matisses.bcs.dto.PrintReportDTO;
import co.matisses.bcs.dto.ResponseDTO;
import co.matisses.bcs.mbean.BCSApplicationMBean;
import co.matisses.bcs.mbean.BCSGenericMBean;
import co.matisses.persistence.web.entity.Impresora;
import co.matisses.persistence.web.facade.ImpresoraFacade;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.IOException;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.OrientationRequested;
import javax.print.attribute.standard.Sides;
import net.sf.jasperreports.engine.JRException;
import org.apache.pdfbox.printing.PDFPageable;

/**
 *
 * @author jguisao
 */
@Stateless
@Path("printreport")
public class PrintReportREST {

    private static final Logger CONSOLE = Logger.getLogger(PrintReportREST.class.getSimpleName());
    @Inject
    private BCSApplicationMBean applicationMBean;
    @Inject
    private BCSGenericMBean bcsGenericMBean;
    @EJB
    private ImpresoraFacade impresoraFacade;

    @POST
    @Path("generar/")
    @Produces({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public ResponseDTO generarReporte(PrintReportDTO dto) throws Exception {
        String reportName = null;
        String report = null;
        String rutaArchivo = "";
        File folder = null;

        switch (dto.getDocumento()) {
            case "cotizacion":
                rutaArchivo = applicationMBean.obtenerValorPropiedad("url.archivo.ventas");
                reportName = dto.getId() + ".pdf";
                report = JasperCompileManager.compileReportToFile(applicationMBean.obtenerValorPropiedad("url.jasper.cotizacion"));
                rutaArchivo = rutaArchivo + dto.getDocumento() + File.separator + reportName;
                break;
            case "demostracion":
                rutaArchivo = applicationMBean.obtenerValorPropiedad("url.archivo.ventas");
                reportName = "[" + dto.getId() + "] " + dto.getAlias() + ".pdf";
                report = JasperCompileManager.compileReportToFile(applicationMBean.obtenerValorPropiedad("url.jasper.demostracion"));
                rutaArchivo = rutaArchivo + dto.getDocumento() + File.separator + reportName;
                break;
            case "factura":
                rutaArchivo = applicationMBean.obtenerValorPropiedad("url.archivo.ventas");
                reportName = dto.getId() + ".pdf";
                report = JasperCompileManager.compileReportToFile(applicationMBean.obtenerValorPropiedad("url.jasper.factura"));

                //TODO: Se crea el directorio para la fv
                folder = new File(rutaArchivo + dto.getDocumento() + File.separator + dto.getId());
                folder.mkdirs();

                rutaArchivo = rutaArchivo + dto.getDocumento() + File.separator + dto.getId() + File.separator + reportName;
                break;
            case "devolucion":
                rutaArchivo = applicationMBean.obtenerValorPropiedad("url.archivo.ventas");
                reportName = dto.getId() + ".pdf";
                report = JasperCompileManager.compileReportToFile(applicationMBean.obtenerValorPropiedad("url.jasper.devolucion"));
                rutaArchivo = rutaArchivo + dto.getDocumento() + File.separator + reportName;
                break;
            case "facturaServicio":
                rutaArchivo = applicationMBean.obtenerValorPropiedad("url.archivo.ventas");
                report = JasperCompileManager.compileReportToFile(applicationMBean.obtenerValorPropiedad("url.jasper.facturaServicio"));

                //TODO: Se crea el directorio para la fv servicio
                folder = new File(rutaArchivo + "factura" + File.separator + dto.getId());
                folder.mkdirs();

                rutaArchivo = rutaArchivo + "factura" + File.separator + dto.getId() + File.separator + dto.getId() + ".pdf";
                break;
            case "traslado":
                rutaArchivo = applicationMBean.obtenerValorPropiedad("url.archivo.logistica");
                reportName = dto.getId() + ".pdf";
                report = JasperCompileManager.compileReportToFile(applicationMBean.obtenerValorPropiedad("url.jasper.traslado"));
                rutaArchivo = rutaArchivo + dto.getDocumento() + File.separator + reportName;
                break;
            case "solicitud":
                rutaArchivo = applicationMBean.obtenerValorPropiedad("url.archivo.logistica");
                reportName = dto.getId() + ".pdf";
                report = JasperCompileManager.compileReportToFile(applicationMBean.obtenerValorPropiedad("url.jasper.solicitud"));
                rutaArchivo = rutaArchivo + dto.getDocumento() + File.separator + reportName;
                break;
            case "certificado":
                rutaArchivo = System.getProperty("jboss.server.temp.dir") + File.separator;
                reportName = dto.getAlias() + ".pdf";
                report = JasperCompileManager.compileReportToFile(applicationMBean.obtenerValorPropiedad("url.jasper.certificado"));
                rutaArchivo = rutaArchivo + reportName;
                break;
            case "alarmaVentas":
                rutaArchivo = System.getProperty("jboss.server.temp.dir") + File.separator;
                reportName = dto.getAlias() + ".pdf";
                report = JasperCompileManager.compileReportToFile(applicationMBean.obtenerValorPropiedad("url.jasper.alarma") + dto.getAlias() + File.separator + dto.getAlias() + ".jrxml");
                rutaArchivo = rutaArchivo + reportName;
                break;
            case "alarmaDevoluciones":
                rutaArchivo = System.getProperty("jboss.server.temp.dir") + File.separator;
                reportName = dto.getAlias() + ".pdf";
                report = JasperCompileManager.compileReportToFile(applicationMBean.obtenerValorPropiedad("url.jasper.alarma") + dto.getAlias() + File.separator + dto.getAlias() + ".jrxml");
                rutaArchivo = rutaArchivo + reportName;
                break;
            case "alarmaCursoAlturas":
                rutaArchivo = System.getProperty("jboss.server.temp.dir") + File.separator;
                reportName = dto.getAlias() + ".pdf";
                report = JasperCompileManager.compileReportToFile(applicationMBean.obtenerValorPropiedad("url.jasper.alarma") + dto.getAlias() + File.separator + dto.getAlias() + ".jrxml");
                rutaArchivo = rutaArchivo + reportName;
                break;
            case "rotuloCcyga":
                rutaArchivo = applicationMBean.obtenerValorPropiedad("url.archivo.logistica");
                reportName = dto.getId() + ".pdf";
                report = JasperCompileManager.compileReportToFile(applicationMBean.obtenerValorPropiedad("url.jasper.rotuloCcyga"));
                rutaArchivo = rutaArchivo + dto.getDocumento() + File.separator + reportName;
                break;
            case "comisiones":
                rutaArchivo = applicationMBean.obtenerValorPropiedad("url.archivo.ventas");
                reportName = dto.getAlias() + ".pdf";
                report = JasperCompileManager.compileReportToFile(applicationMBean.obtenerValorPropiedad("url.jasper.comisiones"));
                rutaArchivo = rutaArchivo + dto.getDocumento() + File.separator + reportName;
                break;
            case "custodias":
                rutaArchivo = applicationMBean.obtenerValorPropiedad("url.archivo.empleados");
                reportName = dto.getAlias() + "[" + dto.getId() + "].pdf";
                report = JasperCompileManager.compileReportToFile(applicationMBean.obtenerValorPropiedad("url.jasper.custodias") + dto.getAlias() + ".jrxml");
                rutaArchivo = rutaArchivo + dto.getDocumento() + File.separator + reportName;
                break;
            case "invitados":
                rutaArchivo = applicationMBean.obtenerValorPropiedad("url.archivo.listaRegalos");
                reportName = dto.getDirigido() + ".pdf";
                report = JasperCompileManager.compileReportToFile(applicationMBean.obtenerValorPropiedad("url.jasper.listaRegalos") + dto.getDocumento() + ".jrxml");
                rutaArchivo = rutaArchivo + dto.getDocumento() + File.separator + reportName;
                break;
            case "terminosCondiciones":
                rutaArchivo = applicationMBean.obtenerValorPropiedad("url.archivo.listaRegalos");
                reportName = dto.getDirigido() + ".pdf";
                report = JasperCompileManager.compileReportToFile(applicationMBean.obtenerValorPropiedad("url.jasper.listaRegalos") + dto.getDocumento() + ".jrxml");
                rutaArchivo = rutaArchivo + dto.getDocumento() + File.separator + reportName;
                break;
            case "servicio":
                rutaArchivo = applicationMBean.obtenerValorPropiedad("url.archivo.logistica");
                reportName = dto.getId() + ".pdf";
                report = JasperCompileManager.compileReportToFile(applicationMBean.obtenerValorPropiedad("url.jasper.servicio") + dto.getDocumento() + ".jrxml");
                rutaArchivo = rutaArchivo + dto.getDocumento() + File.separator + reportName;
                break;
            default:
                reportName = "";
                break;
        }

        //TODO: Se crea la coneccion con la base de datos
        InitialContext initialContext = new InitialContext();
        DataSource dataSource = (DataSource) initialContext.lookup("java:/sap_ds");
        Connection connection = dataSource.getConnection();

        //TODO: Se mandan los parametros al Jasper
        Map<String, Object> mapa = new HashMap<>();
        if (dto.getId() != 0) {
            mapa.put("id", dto.getId());
        }

        if (dto.getDocumento().equals("certificado")) {
            mapa.put("comision", dto.getComision() == null ? 0 : "$" + bcsGenericMBean.getValorFormateado("Double", dto.getComision().toString(), 0));
            mapa.put("dirigido", dto.getDirigido().toUpperCase());
        }

        if (dto.getDocumento().equals("alarmaVentas") || dto.getDocumento().equals("alarmaDevoluciones") || dto.getDocumento().equals("invitados")
                || dto.getDocumento().equals("terminosCondiciones")) {
            mapa.put("parameter", dto.getDirigido());
        }

        if (dto.getDocumento().equals("comisiones")) {
            mapa.put("start", dto.getInicio());
            mapa.put("end", dto.getFin());
        }

        for (int i = 0; i < dto.getCopias(); i++) {
            if (i == 0) {
                mapa.put("documento", "ORIGINAL");
            } else if (i == 1) {
                mapa.put("documento", "COPIA");
                rutaArchivo = rutaArchivo.replace(".pdf", " - Copia.pdf");
            }
            generarInforme(report, rutaArchivo, dto, mapa, connection);
        }

        //TODO: Se verifica si el documento tiene documentos relacionados
        if (dto.getDocumentosRelacionados() != null && dto.getDocumentosRelacionados().size() > 0) {
            ResponseDTO res = new ResponseDTO(0, "");

            for (String[] s : dto.getDocumentosRelacionados()) {
                /*[0] - Tipo
                  [1] - Copias
                 */
                if (s != null) {
                    if (s[0].contains("reciboCaja")) {
                        report = JasperCompileManager.compileReportToFile(applicationMBean.obtenerValorPropiedad("url.jasper.factura.recibo"));
                        rutaArchivo = applicationMBean.obtenerValorPropiedad("url.archivo.ventas") + dto.getDocumento() + File.separator + dto.getId() + File.separator
                                + reportName.replace(".pdf", " - Recibo.pdf");
                    } else if (s[0].contains("daka")) {
                        report = JasperCompileManager.compileReportToFile(applicationMBean.obtenerValorPropiedad("url.jasper.factura.daka"));
                        rutaArchivo = applicationMBean.obtenerValorPropiedad("url.archivo.ventas") + dto.getDocumento() + File.separator + dto.getId() + File.separator
                                + reportName.replace(".pdf", " - Contrato.pdf");
                    }
                    for (int i = 0; i < Integer.parseInt(s[1]); i++) {
                        if (i == 0) {
                            mapa.put("documento", "ORIGINAL");
                        } else if (i == 1) {
                            mapa.put("documento", "COPIA");
                            rutaArchivo = rutaArchivo.replace(".pdf", " - Copia.pdf");
                        }
                        generarInforme(report, rutaArchivo, dto, mapa, connection);
                    }
                    res.setMensaje(res.getMensaje() + rutaArchivo + "||");
                }
            }

            connection.close();
            return res;
        } else {
            connection.close();
            return new ResponseDTO(0, rutaArchivo);
        }
    }

    private void generarInforme(String report, String rutaArchivo, PrintReportDTO dto, Map<String, Object> mapa, Connection connection)
            throws JRException, IOException, PrinterException {
        //TODO: Se guarda el documento en la ruta definida en el bcs.properties
        JasperPrint jasperPrint = JasperFillManager.fillReport(report, mapa, connection);
        JasperExportManager.exportReportToPdfFile(jasperPrint, rutaArchivo);
        PDDocument document = PDDocument.load(new File(rutaArchivo));
        CONSOLE.log(Level.INFO, "Se guardo el documento {0} numero {1}", new Object[]{dto.getDocumento(), dto.getId()});

        //TODO: Se imprime el documento solo si se necesita
        if (dto.isImprimir()) {
            Impresora printer = impresoraFacade.obtenerImpresoraSucursal(dto.getSucursal(), "DOC");

            if (printer != null && printer.getIdImpresora() != null && printer.getIdImpresora() != 0) {
                PrintService myPrintService = findPrintService(printer.getNombreImpresoraServidor());

                PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
                pras.add(Sides.DUPLEX);
                pras.add(OrientationRequested.PORTRAIT);

                PrinterJob printerJob = PrinterJob.getPrinterJob();

                if (myPrintService != null) {
                    CONSOLE.log(Level.INFO, "Impresora seleccionada: {0}", myPrintService.getName());
                    printerJob.setPageable(new PDFPageable(document));
                    printerJob.setPrintService(myPrintService);
                    printerJob.print(pras);
                    CONSOLE.log(Level.INFO, "Se mando a imprimir el documento {0} numero {2} a la impresora {1}",
                            new Object[]{dto.getDocumento(), myPrintService.getName(), dto.getId()});
                }
            }
        }
        document.close();
    }

    private static PrintService findPrintService(String printerName) {
        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
        for (PrintService printService : printServices) {
            if (printService.getName().trim().equals(printerName)) {
                return printService;
            }
        }
        return null;
    }
}
