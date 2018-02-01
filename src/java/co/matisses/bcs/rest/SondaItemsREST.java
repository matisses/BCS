package co.matisses.bcs.rest;

import co.matisses.bcs.sync.ItemDataService;
import co.matisses.persistence.sap.facade.ItemInventarioFacade;
import java.text.ParseException;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author ygil
 */
@Stateless
@Path("sondaitems")
public class SondaItemsREST {

    @EJB
    private MercadoLibreREST mercadoLibre;
    @EJB
    private ItemDataService itemData;
    @EJB
    private ItemInventarioFacade itemFacade;

    @GET
    @Path("ejecutarsonda")
    @Produces({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response ejecutarSonda(@QueryParam("minutos") Integer minutos, @QueryParam("informe") boolean informe) throws ParseException {
        List<String> result = itemFacade.obtenerCambiosItem(minutos);

        if (result != null && !result.isEmpty()) {
            /*Procesamos items en mercado libre. TRUE: soloMedellin*/
            mercadoLibre.ejecutarSonda(informe, result, true);
            /*Procesamos items en mercado libre. FALSE: soloBogota*/
            //Pausado por cierre de tienda 2018-01-15.
            //mercadoLibre.ejecutarSonda(informe, result, false);
            /*Procesamos items para la pagina*/
            itemData.sincronizarItems(informe, result);
        }
        return Response.ok(result).build();
    }
}
