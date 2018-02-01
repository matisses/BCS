package co.matisses.bcs.mailchimp;

import co.matisses.bcs.dto.ResponseDTO;
import co.matisses.bcs.mbean.BCSApplicationMBean;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author ygil
 */
@Stateless
@Path("mailchimp")
public class MailChimpREST implements Serializable {

    @Inject
    private BCSApplicationMBean applicationMBean;
    private static final Logger CONSOLE = Logger.getLogger(MailChimpREST.class.getSimpleName());

    @POST
    @Path("subscribe")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response suscribe(final MailChimpSubscribeDTO subscriber) {
        if (subscriber == null) {
            CONSOLE.log(Level.SEVERE, "No se encontraron datos para suscribir al cliente");
            return Response.ok(new ResponseDTO(-1, "No se encontraron datos para suscribir al cliente.")).build();
        }

        subscriber.setLanguage("es");
        CONSOLE.log(Level.INFO, "{0}", subscriber);
        MailChimpAuthentication mailAuth = new MailChimpAuthentication(applicationMBean.obtenerValorPropiedad("mailchimp.username"), applicationMBean.obtenerValorPropiedad("mailchimp.apiKey"));
        MailChimpClient client = new MailChimpClient(applicationMBean.obtenerValorPropiedad("mailchimp.url"), mailAuth.getToken());
        try {
            MailChimpSubscribeResponseDTO response = client.subscribe(applicationMBean.obtenerValorPropiedad("mailchimp.idLista"), subscriber);
            if (response.getId() != null && !response.getId().isEmpty()) {
                return Response.ok(new ResponseDTO(0, "Se envió un correo de confirmación para finalizar la suscripción.")).build();
            } else {
                return Response.ok(new ResponseDTO(-1, "No fue posible realizar la suscripción del correo.")).build();
            }
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "", e);
            return Response.ok(new ResponseDTO(-1, "El correo ya está registrado.")).build();
        }
    }
}
