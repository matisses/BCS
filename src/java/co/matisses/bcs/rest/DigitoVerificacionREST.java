package co.matisses.bcs.rest;

import javax.ws.rs.core.Response;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author jguisao
 */
@Stateless
@Path("digitoverificacion")
public class DigitoVerificacionREST {

    private static final Logger CONSOLE = Logger.getLogger(DigitoVerificacionREST.class.getSimpleName());

    private int calcularDigitoVerificacion(String nit) {
        int digitoChequeo = -1;
        int lisPeso[] = {71, 67, 59, 53, 47, 43, 41, 37, 29, 23, 19, 17, 13, 7, 3};
        int liSuma = 0;

        if (nit != null && nit.trim().length() > 0) {
            while (nit.length() < 15) {
                nit = "0" + nit;
            }
            try {
                for (int i = 0; i < 15; i++) {
                    liSuma += (new Integer(nit.substring(i, i + 1))) * lisPeso[i];
                }
                digitoChequeo = liSuma % 11;
                if (digitoChequeo >= 2) {
                    digitoChequeo = 11 - digitoChequeo;
                }
            } catch (Exception e) {
                return -2;
            }
        } else {
            return -1;
        }
        return digitoChequeo;
    }

    @GET
    @Path("consultar/{nit}")
    @Produces({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response obtenerDigitoVerificacion(@PathParam("nit") String nit) {
        return Response.ok(calcularDigitoVerificacion(nit)).build();
    }
}