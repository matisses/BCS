package co.matisses.bcs.rest;

import co.matisses.bcs.dto.ResponseDTO;
import co.matisses.bcs.mbean.BCSApplicationMBean;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.io.Serializable;
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
 * @author dbotero
 */
@Stateless
@Path("jwt")
public class JWTTokenValidatorREST implements Serializable {

    private static final Logger CONSOLE = Logger.getLogger(JWTTokenValidatorREST.class.getSimpleName());
    @Inject
    private BCSApplicationMBean applicationBean;

    @GET
    @Path("validate/{token}")
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    public Response validateToken(@PathParam("token") String token) {
        CONSOLE.log(Level.INFO, "Validando el token recibido {0}", token);
        try {
            Algorithm algorithm = Algorithm.HMAC256(applicationBean.obtenerValorPropiedad("jwt.secret"));
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("auth0")
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
            CONSOLE.log(Level.INFO, "Token validado con exito. admin={0}", jwt.getClaim("admin"));
            return Response.ok(new ResponseDTO(0, "Token validado con exito")).build();
        } catch (InvalidClaimException e) {
            CONSOLE.log(Level.SEVERE, "El token no es valido.");
            return Response.ok(new ResponseDTO(-1, "El token no es válido. " + e.getMessage())).build();
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al decodificar el token. ", e);
            return Response.ok(new ResponseDTO(-1, "Ocurrio un error al validar el token. " + e.getMessage())).build();
        }
    }
}
