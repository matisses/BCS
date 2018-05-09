package co.matisses.bcs.rest;

import co.matisses.bcs.b1ws.client.B1WSServiceUnavailableException;
import co.matisses.bcs.b1ws.client.items.ItemsREST;
import co.matisses.bcs.b1ws.ws.items.Item;
import co.matisses.bcs.encode.Encode128;
import co.matisses.persistence.sap.facade.ItemInventarioFacade;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
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
@Path("encode128")
public class Encode128REST {

    private static final Logger CONSOLE = Logger.getLogger(ItemsREST.class.getSimpleName());
    @EJB
    private ItemInventarioFacade itemInventarioFacade;
    @EJB
    private ItemsREST itemsREST;

    @GET
    @Path("codificar/{parametro}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response codificarRest(@PathParam("parametro") String parameter) {
        if (parameter != null && !parameter.isEmpty()) {
            return Response.ok(Encode128.encode(parameter)).build();
        }
        return Response.ok().build();
    }

    @GET
    @Path("procesar")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response calcularCodeBarItem() {
        List<String> items = itemInventarioFacade.obternerItemSinCodeBars();

        if (!items.isEmpty()) {
            for (String item : items) {
                try {
                    Response res = itemsREST.findItem(item);//TODO: Obtener datos por item
                    Item itm = (Item) res.getEntity();

                    itm.setBarCode(Encode128.encode(item));
                    //TODO: Actulizar barcode por item
                    itemsREST.editItem(itm);
                } catch (B1WSServiceUnavailableException e) {
                    CONSOLE.log(Level.SEVERE, "Ocurrio un error al obtener los datos", e);
                    return null;
                }
            }
        }
        return Response.ok().build();
    }
}
