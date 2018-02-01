package co.matisses.bcs.mailchimp;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author ygil
 */
public class MailChimpClient {

    private WebTarget webTarget;
    private Client client;
    private String token;

    public MailChimpClient(String BASE_URI, String token) {
        client = ClientBuilder.newClient();
        webTarget = client.target(BASE_URI);
        this.token = token;
    }

    public MailChimpSubscribeResponseDTO subscribe(String listId, MailChimpSubscribeDTO dto) {
        return webTarget.path("lists").path(listId).path("members").request(MediaType.APPLICATION_JSON).header("Authorization", "Basic " + token)
                .post(Entity.entity(dto, MediaType.APPLICATION_JSON), MailChimpSubscribeResponseDTO.class);
    }
}
