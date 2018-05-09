package co.matisses.bcs.b1ws.client.serviceCalls;

import co.matisses.bcs.b1ws.client.B1WSServiceUnavailableException;
import co.matisses.bcs.b1ws.client.SAPSessionManager;
import co.matisses.bcs.b1ws.ws.serviceCalls.ServiceCall;
import co.matisses.bcs.dto.ResponseDTO;
import co.matisses.bcs.mbean.SAPB1WSBean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
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
 * @author ygil
 */
@Stateless
@Path("servicecalls")
public class ServiceCallsREST {

    @Inject
    private SAPB1WSBean sapB1WSBean;
    private static final Logger CONSOLE = Logger.getLogger(ServiceCallsREST.class.getSimpleName());
    private SAPSessionManager sessionManager = new SAPSessionManager();

    @GET
    @Path("find/{callID}")
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    public Response find(@PathParam("callID") Integer callID) throws B1WSServiceUnavailableException {
        CONSOLE.log(Level.INFO, "Buscando llamada de servicio por id [{0}]", callID);
        if (callID == null || callID == 0) {
            return Response.serverError().build();
        }
        String sesionId = sessionManager.login();

        if (sesionId == null) {
            CONSOLE.log(Level.SEVERE, "No fue posible iniciar una sesion en SAP B1WS.");
            return Response.ok(new ResponseDTO(-1, "No fue posible iniciar una sesión en SAP B1WS.")).build();
        }

        try {
            ServiceCallsServiceConnector scsc = sapB1WSBean.getServiceCallsService(sesionId);

            ServiceCall call = scsc.findServiceCall(callID);
            return Response.ok(call).build();
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al consultar la llamada de servicio. ", e);
            return Response.ok(new ResponseDTO(-1, e.getMessage())).build();
        } finally {
            sessionManager.logout(sesionId);
        }
    }

    @POST
    @Path("create")
    @Consumes({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    public Response create(ServiceCall call) throws B1WSServiceUnavailableException {
        String sesionId = sessionManager.login();

        if (sesionId == null) {
            CONSOLE.log(Level.SEVERE, "No fue posible iniciar una sesion en SAP B1WS.");
            return Response.ok(new ResponseDTO(-1, "No fue posible iniciar una sesión en SAP B1WS.")).build();
        }

        try {
            ServiceCallsServiceConnector scsc = sapB1WSBean.getServiceCallsService(sesionId);

            Long res = scsc.createServiceCall(call);
            return Response.ok(new ResponseDTO(0, res)).build();
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al crear la llamada de servicio. ", e);
            return Response.ok(new ResponseDTO(-1, e.getMessage())).build();
        } finally {
            sessionManager.logout(sesionId);
        }
    }

    @POST
    @Path("edit")
    @Consumes({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    public Response edit(ServiceCall call) throws B1WSServiceUnavailableException {
        String sesionId = sessionManager.login();

        if (sesionId == null) {
            CONSOLE.log(Level.SEVERE, "No fue posible iniciar una sesion en SAP B1WS.");
            return Response.ok(new ResponseDTO(-1, "No fue posible iniciar una sesión en SAP B1WS.")).build();
        }

        try {
            ServiceCallsServiceConnector scsc = sapB1WSBean.getServiceCallsService(sesionId);

            scsc.editServiceCall(call);
            return Response.ok(new ResponseDTO(0)).build();
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al editar la llamada de servicio. ", e);
            return Response.ok(new ResponseDTO(-1, e.getMessage())).build();
        } finally {
            sessionManager.logout(sesionId);
        }
    }
}
