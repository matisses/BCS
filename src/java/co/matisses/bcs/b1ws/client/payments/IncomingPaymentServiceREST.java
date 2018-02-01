package co.matisses.bcs.b1ws.client.payments;

import co.matisses.bcs.b1ws.client.B1WSServiceUnavailableException;
import co.matisses.bcs.b1ws.client.SAPSessionManager;
import co.matisses.bcs.dto.ResponseDTO;
import co.matisses.bcs.mbean.SAPB1WSBean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
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
@Path("payment")
public class IncomingPaymentServiceREST {

    private static final Logger console = Logger.getLogger(IncomingPaymentServiceREST.class.getSimpleName());

    @Inject
    private SAPB1WSBean sapB1WSBean;
    private SAPSessionManager sessionManager = new SAPSessionManager();

    @POST
    @Path("add/{username}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response addIncomingPayment(PaymentDTO paymentDto, @PathParam("username") String username) throws B1WSServiceUnavailableException {
        if (sapB1WSBean == null) {
            sapB1WSBean = new SAPB1WSBean();
            sapB1WSBean.initialize();
        }

        String sesionId = sessionManager.login();
        if (sesionId == null) {
            console.log(Level.SEVERE, "No fue posible iniciar una sesion en SAP B1WS.");
            return Response.ok(new ResponseDTO(0, "No fue posible iniciar una sesión en SAP B1WS.")).build();
        }

        IncomingPaymentServiceConnector ipsc = sapB1WSBean.getIncomingPaymentServiceConnectorInstance(sesionId);

        try {
            Long docEntry = ipsc.addPayment(paymentDto);

            return Response.ok(new ResponseDTO(0, docEntry.toString())).build();
        } catch (Exception e) {
            return Response.ok(new ResponseDTO(-1, "Ocurrio un error al invocar el servicio. " + e.getMessage())).build();
        } finally {
            sessionManager.logout(sesionId);
        }
    }
}
