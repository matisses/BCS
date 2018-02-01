package co.matisses.bcs.rest;

import co.matisses.bcs.dto.MetodosEnvioDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author ygil
 */
@Path("shippingmethods")
public class ShippingMethodsREST {

    private static final Logger console = Logger.getLogger(ShippingMethodsREST.class.getSimpleName());

    @GET
    @Path("")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response obtenerMetodosEnvio() {
        //TODO: Se debe hacer la consulta de los metodos de envio para mostrarlos en la pagina

        //TODO: Se deben obtener los datos de los items que se van a facturar para sugerirle al usuario el mejor envio
        List<MetodosEnvioDTO> metodos = new ArrayList<>();

        metodos.add(new MetodosEnvioDTO(1, 0.0, "Matisses.", "Una semana el mejor precio"));
        metodos.add(new MetodosEnvioDTO(2, 0.0, "Recoger en tienda.", ""));
        metodos.add(new MetodosEnvioDTO(3, 0.0, "Coordinadora.", ""));

        return Response.ok(metodos).build();
    }
}