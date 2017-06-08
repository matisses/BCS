package co.matisses.bcs.google;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author dbotero
 */
public class GoogleAPIClient {

    private WebTarget webTarget;
    private Client client;

    public GoogleAPIClient(String BASE_URI) {
        client = ClientBuilder.newClient();
        webTarget = client.target(BASE_URI);
    }

    public Response shortenURL(String apiKey, String urlLarga) {
        return webTarget.path("urlshortener").path("v1").path("url").queryParam("key", apiKey)
                .request(MediaType.APPLICATION_JSON).post(Entity.entity("{\"longUrl\":\"" + urlLarga + "\"}", MediaType.APPLICATION_JSON), Response.class);
    }
}
