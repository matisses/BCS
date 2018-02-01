package co.matisses.bcs.b1ws.client.payments;

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
@Path("incomingpayment")
public class IncomingPaymentREST {

    @Inject
    private SAPB1WSBean sapB1WSBean;
    private final SAPSessionManager sessionManager = new SAPSessionManager();
    private static final Logger CONSOLE = Logger.getLogger(IncomingPaymentREST.class.getSimpleName());

    @POST
    @Path("create/{usuario}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public ResponseDTO createIncomingPayment(@PathParam("usuario") String usuario, PaymentDTO dto) throws B1WSServiceUnavailableException {
        String sesionId = sessionManager.login();

        if (sesionId == null) {
            CONSOLE.log(Level.SEVERE, "No fue posible iniciar una sesion en SAP B1WS.");
            return new ResponseDTO(0, "No fue posible iniciar una sesión en SAP B1WS.");
        }

        IncomingPaymentServiceConnector connection = sapB1WSBean.getIncomingPaymentServiceConnectorInstance(sesionId);

        try {
            Long docEntry = connection.addPayment(dto);

            if (docEntry == null || docEntry <= 0) {
                return new ResponseDTO(0, "No fue posible agregar el pago.");
            } else {
                return new ResponseDTO(1, docEntry.intValue());
            }
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al generar el recibo de caja. ", e);
            return new ResponseDTO(0, e.getMessage());
        } finally {
            sessionManager.logout(sesionId);
        }
    }

    @POST
    @Path("cancel")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public ResponseDTO cancelIncomingPayment(Long docEntry) throws B1WSServiceUnavailableException {
        String sesionId = sessionManager.login();

        if (sesionId == null) {
            CONSOLE.log(Level.SEVERE, "No fue posible iniciar una sesion en SAP B1WS.");
            return new ResponseDTO(0, "No fue posible iniciar una sesión en SAP B1WS.");
        }

        IncomingPaymentServiceConnector connection = sapB1WSBean.getIncomingPaymentServiceConnectorInstance(sesionId);

        try {
            connection.cancelPayment(docEntry);
            return new ResponseDTO(1, "Se anulo correctamente el pago.");
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al generar el recibo de caja. ", e);
            return new ResponseDTO(0, e.getMessage());
        } finally {
            sessionManager.logout(sesionId);
        }
    }
}
