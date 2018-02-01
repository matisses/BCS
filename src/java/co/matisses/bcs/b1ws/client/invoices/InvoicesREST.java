package co.matisses.bcs.b1ws.client.invoices;

import co.matisses.b1ws.dto.SalesDocumentDTO;
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
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author ygil
 */
@Stateless
@Path("invoices")
public class InvoicesREST {

    @Inject
    private SAPB1WSBean sapB1WSBean;
    private final SAPSessionManager sessionManager = new SAPSessionManager();
    private static final Logger CONSOLE = Logger.getLogger(InvoicesREST.class.getSimpleName());

    @POST
    @Path("create/{usuario}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public ResponseDTO createInvoice(@PathParam("usuario") String usuario, SalesDocumentDTO dto) throws B1WSServiceUnavailableException {
        if (sapB1WSBean == null) {
            sapB1WSBean = new SAPB1WSBean();
            sapB1WSBean.initialize();
        }
        String sesionId = sessionManager.login();
        if (sesionId == null) {
            CONSOLE.log(Level.SEVERE, "No fue posible iniciar una sesion en SAP B1WS.");
            return new ResponseDTO(0, "No fue posible iniciar una sesión en SAP B1WS.");
        }

        InvoicesServiceConnector connection = sapB1WSBean.getInvoicesServiceConnectorInstance(sesionId);

        try {
            Long docEntry = connection.createInvoice(dto);

            if (docEntry == null || docEntry <= 0) {
                return new ResponseDTO(0, "No fue posible crear la factura.");
            } else {
                return new ResponseDTO(1, docEntry.intValue());
            }
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al crear la factura. ", e);
            return new ResponseDTO(0, e.getMessage());
        } finally {
            sessionManager.logout(sesionId);
        }
    }
    
    @POST
    @Path("createService")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public ResponseDTO createServiceInvoice(SalesDocumentDTO dto) throws B1WSServiceUnavailableException {
        if (sapB1WSBean == null) {
            sapB1WSBean = new SAPB1WSBean();
            sapB1WSBean.initialize();
        }
        String sesionId = sessionManager.login();
        if (sesionId == null) {
            CONSOLE.log(Level.SEVERE, "No fue posible iniciar una sesion en SAP B1WS.");
            return new ResponseDTO(0, "No fue posible iniciar una sesión en SAP B1WS.");
        }

        InvoicesServiceConnector connection = sapB1WSBean.getInvoicesServiceConnectorInstance(sesionId);

        try {
            Long docEntry = connection.createServiceInvoice(dto);

            if (docEntry == null || docEntry <= 0) {
                return new ResponseDTO(0, "No fue posible crear la factura.");
            } else {
                return new ResponseDTO(1, docEntry.intValue());
            }
        } catch (Exception e) {
            return new ResponseDTO(0, e.getMessage());
        } finally {
            sessionManager.logout(sesionId);
        }
    }
}