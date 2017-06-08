package co.matisses.bcs.rest;

import co.matisses.bcs.dto.ActualizarImagenesItemDTO;
import co.matisses.bcs.dto.AgregarAtributosMdoLibreDTO;
import co.matisses.bcs.dto.MercadolibreAccessCodeResponseDTO;
import co.matisses.bcs.dto.ModificarDescripcionMercadolibreDTO;
import javax.ws.rs.ClientErrorException;
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
public class MercadoLibreClient {

    private WebTarget webTarget;
    private Client client;

    public MercadoLibreClient(String BASE_URI) {
        client = ClientBuilder.newClient();
        webTarget = client.target(BASE_URI);
    }

    public MercadoLibreClient(String BASE_URI, String path) {
        client = ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path(path);
    }

    public Response listarProducto(String accessToken, Object requestEntity) throws ClientErrorException {
        return webTarget.queryParam("access_token", accessToken)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(requestEntity, MediaType.APPLICATION_JSON), Response.class);
    }

    public Response agregarAtributosItem(String accessToken, String itemId, AgregarAtributosMdoLibreDTO dto) {
        return webTarget.path(itemId).queryParam("access_token", accessToken)
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(dto, MediaType.APPLICATION_JSON), Response.class);
    }

    public Response modifificarPublicacion(String accessToken, String itemId, Object requestEntity) throws ClientErrorException {
        return webTarget.path(itemId).queryParam("access_token", accessToken)
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(requestEntity, MediaType.APPLICATION_JSON), Response.class);
    }

    public Response suspenderPublicacion(String accessToken, String itemId) throws ClientErrorException {
        return webTarget.path(itemId).queryParam("access_token", accessToken)
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.entity("{\"status\": \"paused\"}", MediaType.APPLICATION_JSON), Response.class);
    }

    public Response finalizarPublicacion(String accessToken, String itemId) throws ClientErrorException {
        return webTarget.path(itemId).queryParam("access_token", accessToken)
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.entity("{\"status\": \"closed\"}", MediaType.APPLICATION_JSON), Response.class);
    }

    public Response configurarEnvioGratuito(String accessToken, String itemId) {
        return webTarget.path(itemId).queryParam("access_token", accessToken)
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.entity("{\"shipping\":{\"mode\": \"me2\",\"free_shipping\": true,\"free_methods\":[{\"id\":501745,\"rule\":{\"default\": true,\"free_mode\":\"country\",\"free_shipping_flag\": true}}]}}",
                        MediaType.APPLICATION_JSON), Response.class);
    }

    public Response configurarEnvioItem(String accessToken, String itemId, String tipoEnvioJson) {
        return webTarget.path(itemId).queryParam("access_token", accessToken)
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(tipoEnvioJson, MediaType.APPLICATION_JSON), Response.class);
    }

    public MercadolibreAccessCodeResponseDTO renovarAccessToken(String clientId, String clientSecret, String refreshToken) {
        return webTarget.queryParam("grant_type", "refresh_token").queryParam("client_id", clientId)
                .queryParam("client_secret", clientSecret).queryParam("refresh_token", refreshToken).request(MediaType.APPLICATION_JSON)
                .post(Entity.entity("", MediaType.TEXT_PLAIN), MercadolibreAccessCodeResponseDTO.class);
    }

    public Response obtenerAccessToken(String clientId, String clientSecret, String code, String redirectURI) {
        return webTarget.queryParam("grant_type", "authorization_code").queryParam("client_id", clientId)
                .queryParam("client_secret", clientSecret).queryParam("code", code)
                .queryParam("redirect_uri", redirectURI).request(MediaType.APPLICATION_JSON)
                .post(Entity.entity("", MediaType.TEXT_PLAIN), Response.class);
    }

    public Response actualizarDescripcion(String itemId, ModificarDescripcionMercadolibreDTO desc, String accessToken) throws Exception {
        return webTarget.path(itemId).path("description").queryParam("access_token", accessToken)
                .request(MediaType.APPLICATION_JSON).put(Entity.entity(desc, MediaType.APPLICATION_JSON), Response.class);
    }

    public Response consultarPago(String idPago, String accessToken) {
        return webTarget.path(idPago).queryParam("access_token", accessToken)
                .request(MediaType.APPLICATION_JSON).get(Response.class);
    }

    public Response consultarOrden(String idOrden, String accessToken) {
        return webTarget.path(idOrden).queryParam("access_token", accessToken)
                .request(MediaType.APPLICATION_JSON).get(Response.class);
    }

    public Response borrarImagenes(String itemId, String accessToken) {
        return webTarget.path(itemId).queryParam("access_token", accessToken)
                .request(MediaType.APPLICATION_JSON).put(Entity.entity(new ActualizarImagenesItemDTO(), MediaType.APPLICATION_JSON), Response.class);
    }

    public Response actualizarImagenes(String itemId, ActualizarImagenesItemDTO actDto, String accessToken) {
        return webTarget.path(itemId).queryParam("access_token", accessToken)
                .request(MediaType.APPLICATION_JSON).put(Entity.entity(actDto, MediaType.APPLICATION_JSON), Response.class);
    }

    public <T> T ejecutarConsulta(String path, String accessToken, Class<T> responseType) {
        return webTarget.path(path).queryParam("access_token", accessToken)
                .request(MediaType.APPLICATION_JSON).get(responseType);
    }

    public void close() {
        client.close();
    }
}
