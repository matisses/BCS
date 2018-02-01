package co.matisses.bcs.newmatisses.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author ygil
 */
public class ShoppingCartClient {

    private WebTarget webTarget;
    private Client client;

    public ShoppingCartClient(String BASE_URI) {
        client = ClientBuilder.newClient();
        webTarget = client.target(BASE_URI);
    }

    public ShoppingCartDTO consultar(String idCarrito) {
        return webTarget.path("shoppingcart/buscar").path(idCarrito).path("BCS").request(MediaType.APPLICATION_JSON).get(ShoppingCartDTO.class);
    }
}
