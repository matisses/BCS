package co.matisses.bcs.b1ws.client.quotations;

import co.matisses.bcs.b1ws.client.B1WSServiceUnavailableException;
import co.matisses.bcs.b1ws.client.SAPSessionManager;
import co.matisses.bcs.dto.ResponseDTO;
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
@Path("quotations")
public class QuotationsREST {

    @Inject
    private SAPB1WSBean sapB1WSBean;
    private SAPSessionManager sessionManager = new SAPSessionManager();
    private static final Logger console = Logger.getLogger(QuotationsREST.class.getSimpleName());

    @POST
    @Path("create")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public ResponseDTO createQuotations(QuotationsDocumentDTO dto) throws B1WSServiceUnavailableException {
        String sesionId = sessionManager.login();
        if (sesionId == null) {
            console.log(Level.SEVERE, "No fue posible iniciar una sesion en SAP B1WS.");
            return new ResponseDTO(0, "No fue posible iniciar una sesión en SAP B1WS.");
        }

        QuotationsServiceConnector connection = sapB1WSBean.getQuotationsServiceConnectorInstance(sesionId);
        Long absEntry = connection.createQuotationDocument(dto);

        if (absEntry == null || absEntry <= 0) {
            return new ResponseDTO(-1, "No fue posible crear la cotización");
        } else {
            return new ResponseDTO(1, absEntry.intValue());
        }
    }

    @POST
    @Path("edit")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public ResponseDTO editQuotations(QuotationsDocumentDTO dto) throws B1WSServiceUnavailableException {
        String sesionId = sessionManager.login();
        if (sesionId == null) {
            console.log(Level.SEVERE, "No fue posible iniciar una sesion en SAP B1WS.");
            return new ResponseDTO(0, "No fue posible iniciar una sesión en SAP B1WS.");
        }

        QuotationsServiceConnector connection = sapB1WSBean.getQuotationsServiceConnectorInstance(sesionId);

        try {
            connection.editQuotation(dto);
            return new ResponseDTO(1, 1000);
        } catch (Exception e) {
            return new ResponseDTO(-1, "No fue posible modificar la cotización " + e.getMessage());
        }
    }
}
