package co.matisses.bcs.rest;

import co.matisses.bcs.dto.ResponseDTO;
import co.matisses.bcs.dto.ZebraPrintDTO;
import co.matisses.bcs.zebra.ZPLPrinter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author dbotero
 */
@Stateless
@Path("zebraprint")
public class ZebraPrintREST {

    private static final Logger console = Logger.getLogger(ZebraPrintREST.class.getSimpleName());

    @POST
    @Path("print")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response print(ZebraPrintDTO dto) {
        console.log(Level.INFO, "Iniciando proceso de impresion {0}", dto);
        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
        if (services == null || services.length == 0) {
            return Response.ok(new ResponseDTO(-1, "No se encontraron impresoras instaladas en el sistema.")).build();
        }
        PrintService printService = null;
        for (PrintService ps : services) {
            if (ps.getName().equals(dto.getPrinterName())) {
                printService = ps;
                break;
            }
        }
        if (printService == null) {
            return Response.ok(new ResponseDTO(-1, "No se encontró una impresora con nombre [" + dto.getPrinterName() + "] instalada en el servidor.")).build();
        }
        DocPrintJob job = printService.createPrintJob();
        Doc doc = new SimpleDoc(ZPLPrinter.getPrintData(dto), DocFlavor.BYTE_ARRAY.AUTOSENSE, null);
        try {
            job.print(doc, null);
        } catch (Exception e) {
            console.log(Level.SEVERE, "Ocurrio un error al imprimir. ", e);
            return Response.ok(new ResponseDTO(-1, "Ocurrio un error al imprimir. " + e.getMessage())).build();
        }
        return Response.ok(new ResponseDTO(0, "Los datos se enviaron correctamente a la impresora")).build();
    }

    @GET
    @Path("list")
    @Produces({MediaType.APPLICATION_JSON})
    public Response print() {
        console.log(Level.INFO, "Listando impresoras zebra configuradas");
        List<String> printerNames = new ArrayList<>();
        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
        if (services != null & services.length > 0) {
            for (PrintService ps : services) {
                if (ps.getName().toLowerCase().contains("zebra")) {
                    printerNames.add(ps.getName());
                }
            }
        }
        return Response.ok(printerNames).build();
    }
}
