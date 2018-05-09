package co.matisses.bcs.rest;

import co.matisses.bcs.dto.ResponseDTO;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ws.rs.GET;
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
@Path("emailvalidator")
public class EmailPatternValidatorREST {

    private static final Logger CONSOLE = Logger.getLogger(ListaRegalosREST.class.getSimpleName());

    @GET
    @Path("validarformatoemail/{email}")
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response validarFormatoEmail(@PathParam("email") String email) {
        Pattern ptr = Pattern.compile(EmailPatternValidator.getInstance().getEmailPattern());
        if (email == null || email.trim().isEmpty()) {
            CONSOLE.log(Level.SEVERE, "Debes ingresar un email");
            return Response.ok(new ResponseDTO(-1, "Debes ingresar un email.")).build();
        } else if (!ptr.matcher(email).matches()) {
            CONSOLE.log(Level.SEVERE, "El formato del email es incorrecto");
            return Response.ok(new ResponseDTO(-1, "El formato del email es incorrecto.")).build();
        }

        return Response.ok(new ResponseDTO(0, "El formato del email es correcto.")).build();
    }
}