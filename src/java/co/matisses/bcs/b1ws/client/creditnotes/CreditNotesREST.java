package co.matisses.bcs.b1ws.client.creditnotes;

import co.matisses.b1ws.dto.SalesDocumentDTO;
import co.matisses.bcs.b1ws.client.B1WSServiceUnavailableException;
import co.matisses.bcs.b1ws.client.SAPSessionManager;
import co.matisses.bcs.dto.ResponseDTO;
import co.matisses.bcs.mbean.BCSApplicationMBean;
import co.matisses.bcs.mbean.SAPB1WSBean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author ygil
 */
@Stateless
@Path("creditnotes")
public class CreditNotesREST {

    @Inject
    private SAPB1WSBean sapB1WSBean;
    @Inject
    private BCSApplicationMBean applicationMBean;
    private final SAPSessionManager sessionManager = new SAPSessionManager();
    private static final Logger CONSOLE = Logger.getLogger(CreditNotesREST.class.getSimpleName());

    @POST
    @Path("create")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public ResponseDTO createCreditNote(SalesDocumentDTO dto) throws B1WSServiceUnavailableException {
        String sesionId = sessionManager.login();

        if (sesionId == null) {
            CONSOLE.log(Level.SEVERE, "No fue posible iniciar una sesion en SAP B1WS.");
            return new ResponseDTO(0, "No fue posible iniciar una sesión en SAP B1WS.");
        }

        CreditNotesServiceConnector connection = sapB1WSBean.getCreditNotesServiceConnectorInstance(sesionId);

        try {
            Long docEntry = connection.createCreditNote(dto, applicationMBean);

            if (docEntry == null || docEntry <= 0) {
                return new ResponseDTO(0, "No fue posible crear la nota credito.");
            } else {
                return new ResponseDTO(1, docEntry.intValue());
            }
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al crear la nota credito. ", e);
            return new ResponseDTO(0, e.getMessage());
        } finally {
            sessionManager.logout(sesionId);
        }
    }
}
