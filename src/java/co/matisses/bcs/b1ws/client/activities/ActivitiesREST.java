package co.matisses.bcs.b1ws.client.activities;

import co.matisses.bcs.b1ws.client.B1WSServiceUnavailableException;
import co.matisses.bcs.b1ws.client.SAPSessionManager;
import co.matisses.bcs.b1ws.ws.activities.Activity;
import co.matisses.bcs.dto.ResponseDTO;
import co.matisses.bcs.mbean.SAPB1WSBean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
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
@Path("activities")
public class ActivitiesREST {

    @Inject
    private SAPB1WSBean sapB1WSBean;
    private static final Logger CONSOLE = Logger.getLogger(ActivitiesREST.class.getSimpleName());
    private SAPSessionManager sessionManager = new SAPSessionManager();

    @GET
    @Path("find/{activityID}")
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    public Response find(@PathParam("activityID") Integer activityID) throws B1WSServiceUnavailableException {
        CONSOLE.log(Level.INFO, "Buscando actividad por id [{0}]", activityID);
        if (activityID == null || activityID == 0) {
            return Response.serverError().build();
        }
        String sesionId = sessionManager.login();

        if (sesionId == null) {
            CONSOLE.log(Level.SEVERE, "No fue posible iniciar una sesion en SAP B1WS.");
            return Response.ok(new ResponseDTO(-1, "No fue posible iniciar una sesión en SAP B1WS.")).build();
        }

        try {
            ActivitiesServiceConnector asc = sapB1WSBean.getActivitiesService(sesionId);

            Activity activity = asc.findActivity(activityID);
            return Response.ok(activity).build();
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al consultar la actividad. ", e);
            return Response.ok(new ResponseDTO(-1, e.getMessage())).build();
        } finally {
            sessionManager.logout(sesionId);
        }
    }
}
