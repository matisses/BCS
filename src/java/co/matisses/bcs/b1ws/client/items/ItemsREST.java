package co.matisses.bcs.b1ws.client.items;

import co.matisses.bcs.b1ws.client.B1WSServiceUnavailableException;
import co.matisses.bcs.b1ws.client.SAPSessionManager;
import co.matisses.bcs.b1ws.ws.items.Item;
import co.matisses.bcs.dto.ResponseDTO;
import co.matisses.bcs.mbean.SAPB1WSBean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author jguisao
 */
@Stateless
@Path("Items")
public class ItemsREST {

    @Inject
    private SAPB1WSBean sapB1WSBean;
    private SAPSessionManager sessionManager = new SAPSessionManager();
    private static final Logger console = Logger.getLogger(ItemsREST.class.getSimpleName());

    @GET
    @Path("find/{id}")
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    public Response findItem(@PathParam("id") String id) throws B1WSServiceUnavailableException {
        String sesionId = sessionManager.login();

        if (sesionId == null) {
            console.log(Level.SEVERE, "No fue posible iniciar una sesion en SAP B1WS.");
            return Response.ok(new ResponseDTO(0, "No fue posible iniciar una sesión en SAP B1WS.")).build();
        }

        try {
            ItemsServiceConnector itsc = sapB1WSBean.getItemService(sesionId);

            Item item = itsc.findItem(id);
            return Response.ok(item).build();
        } catch (Exception e) {
            console.log(Level.SEVERE, "Ocurrio un error al consultar el item. ", e);
            return Response.ok(new ResponseDTO(-1, e.getMessage())).build();
        } finally {
            sessionManager.logout(sesionId);
        }
    }

    @POST
    @Path("edit")
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    public Response editItem(Item item) throws B1WSServiceUnavailableException {
        String sesionId = sessionManager.login();

        if (sesionId == null) {
            console.log(Level.SEVERE, "No fue posible iniciar una sesion en SAP B1WS.");
            return Response.ok(new ResponseDTO(0, "No fue posible iniciar una sesión en SAP B1WS.")).build();
        }

        try {
            ItemsServiceConnector itsc = sapB1WSBean.getItemService(sesionId);

            itsc.editItem(item);
            return Response.ok(new ResponseDTO(0)).build();
        } catch (Exception e) {
            console.log(Level.SEVERE, "Ocurrio un error al editar el item. ", e);
            return Response.ok(new ResponseDTO(-1, e.getMessage())).build();
        } finally {
            sessionManager.logout(sesionId);
        }
    }
}