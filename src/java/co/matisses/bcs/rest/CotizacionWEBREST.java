package co.matisses.bcs.rest;

import co.matisses.bcs.dto.MailMessageDTO;
import co.matisses.bcs.dto.ResponseDTO;
import co.matisses.bcs.mbean.BCSApplicationMBean;
import co.matisses.bcs.mbean.BCSGenericMBean;
import co.matisses.persistence.sap.entity.ItemInventario;
import co.matisses.persistence.sap.facade.ItemInventarioFacade;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author ygil
 */
@Stateless
@Path("cotizacionweb")
public class CotizacionWEBREST {

    @Inject
    private BCSApplicationMBean applicationMBean;
    @Inject
    private BCSGenericMBean genericMBean;
    @EJB
    private SendHtmlEmailREST emailREST;
    @EJB
    private ItemInventarioFacade inventarioFacade;

    @GET
    @Path("create/{itemcode}/{nombre}/{email}")
    @Consumes({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response createWebQuotation(@PathParam("itemcode") String itemCode, @PathParam("nombre") String nombre, @PathParam("email") String email) {
        ItemInventario item = inventarioFacade.find(itemCode);

        if (item != null && item.getItemCode() != null && !item.getItemCode().isEmpty()) {
            MailMessageDTO mail = new MailMessageDTO();

            mail.setFrom("Cotizaci&oacute;n Web Matisses <info@matisses.co>");
            mail.setSubject("Cotizaci&oacute;n Web Matisses");
            mail.addToAddress(email);
            mail.addBccAddress(applicationMBean.getDestinatariosPlantillaEmail().get("cotizacion_web").getTo().get(0));
            mail.setAttachments(null);
            mail.setTemplateName("cotizacion_web");

            Map<String, String> params = new HashMap<>();

            params.put("cliente", nombre.contains(" ") ? nombre.split(" ")[0].toUpperCase() : nombre.toUpperCase());
            params.put("item", itemCode);
            params.put("shortitemcode", genericMBean.convertirARefCorta(itemCode).replace("*", ""));
            params.put("descripcion", item.getFrgnName());
            params.put("precio", genericMBean.getValorFormateado("Double", genericMBean.obtenerPrecioVenta(itemCode).toString(), 0));

            mail.setParams(params);

            emailREST.sendMail360(mail);

            return Response.ok(new ResponseDTO(0)).build();
        } else {
            return Response.ok(new ResponseDTO(-1)).build();
        }
    }
}
