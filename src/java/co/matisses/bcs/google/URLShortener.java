package co.matisses.bcs.google;

import co.matisses.bcs.mbean.BCSApplicationMBean;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author dbotero
 */
@Stateless
@Path("google")
public class URLShortener {

    private static final Logger log = Logger.getLogger(URLShortener.class.getSimpleName());

    @Inject
    private BCSApplicationMBean appBean;

    @GET
    @Path("generarurlcorta/")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response generar(@QueryParam("urlLarga") String urlLarga) {
        log.log(Level.INFO, "Generando URL corta para {0}", urlLarga);
        GoogleAPIClient client = new GoogleAPIClient(appBean.obtenerValorPropiedad("google.url.shortener"));
        try {
            Response res = client.shortenURL(appBean.obtenerValorPropiedad("google.api.key"), urlLarga);
            String response = res.readEntity(String.class);
            return Response.ok(response).build();
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al codificar la URL larga a UTF-8. ", e);
            return Response.ok().build();
        }

    }
}
