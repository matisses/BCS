package co.matisses.bcs.b1ws.client.journalentries;

import co.matisses.bcs.b1ws.client.B1WSServiceUnavailableException;
import co.matisses.bcs.b1ws.client.SAPSessionManager;
import co.matisses.bcs.b1ws.ws.journalentries.JournalEntry;
import co.matisses.bcs.dto.ResponseDTO;
import co.matisses.bcs.dto.SesionSAPB1WSDTO;
import co.matisses.bcs.mbean.BCSApplicationMBean;
import co.matisses.bcs.mbean.SAPB1WSBean;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author dbotero
 */
@Stateless
@Path("journalentry")
public class JournalEntryREST {

    private static final Logger CONSOLE = Logger.getLogger(JournalEntryREST.class.getSimpleName());

    @Inject
    private BCSApplicationMBean applicationMBean;
    @Inject
    private SAPB1WSBean sapB1WSBean;
    private final SAPSessionManager sessionManager = new SAPSessionManager();

    @GET
    @Path("update/{username}/{docEntry}/{account}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response uptadeEntryLineCardCode(@PathParam("username") String username, @PathParam("docEntry") Long docEntry, @PathParam("account") String account) {
        SesionSAPB1WSDTO sesionSAPDTO = applicationMBean.obtenerSesionSAP(username);
        if (sesionSAPDTO == null) {
            CONSOLE.log(Level.SEVERE, "No fue posible iniciar una sesion en SAP B1WS.");
            return Response.ok(new ResponseDTO(-1, "No fue posible iniciar una sesión en SAP B1WS.")).build();
        }

        JournalEntriesServiceConnector jesc = sapB1WSBean.getJournalEntriesServiceConnectorInstance(sesionSAPDTO.getIdSesionSAP());
        try {
            JournalEntry sapObject = jesc.findEntry(docEntry);
            JournalEntry.JournalEntryLines lines = sapObject.getJournalEntryLines();
            for (JournalEntry.JournalEntryLines.JournalEntryLine line : lines.getJournalEntryLine()) {
                if (line.getAccountCode() != null && line.getAccountCode().equals(account)) {
                    line.setUInfoCo01("70073598PR");
                    jesc.updateEntry(sapObject);
                    break;
                }
            }
            return Response.ok(new ResponseDTO(0, null)).build();
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al actualizar el asiento. ", e);
            return Response.ok(new ResponseDTO(-1, "Ocurrio un error al actualizar el asiento. " + e.getMessage())).build();
        }
    }

    @POST
    @Path("create")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public ResponseDTO createJournalEntry(JournalEntryDTO dto) throws B1WSServiceUnavailableException {
        String sesionId = sessionManager.login();

        if (sesionId == null) {
            CONSOLE.log(Level.SEVERE, "No fue posible iniciar una sesion en SAP B1WS.");
            return new ResponseDTO(0, "No fue posible iniciar una sesión en SAP B1WS.");
        }

        JournalEntriesServiceConnector connection = sapB1WSBean.getJournalEntriesServiceConnectorInstance(sesionId);

        try {
            Long docEntry = connection.createJournalEntry(dto);

            if (docEntry == null || docEntry <= 0) {
                return new ResponseDTO(0, "No fue posible crear el asiento.");
            } else {
                return new ResponseDTO(1, docEntry.intValue());
            }
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al crear el asiento. ", e);
            return new ResponseDTO(0, e.getMessage());
        } finally {
            sessionManager.logout(sesionId);
        }
    }
}
