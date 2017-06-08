package co.matisses.bcs.b1ws.client.businesspartners;

import co.matisses.bcs.dto.ResponseDTO;
import co.matisses.bcs.dto.SesionSAPB1WSDTO;
import co.matisses.bcs.mbean.BCSApplicationMBean;
import co.matisses.bcs.mbean.SAPB1WSBean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author dbotero
 */
@Path("businesspartner")
public class BusinessPartnerREST {

    private static final Logger console = Logger.getLogger(BusinessPartnerREST.class.getSimpleName());

    @Inject
    private BCSApplicationMBean applicationMBean;
    @Inject
    private SAPB1WSBean sapB1WSBean;

    @POST
    @Path("create/{usuario}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response createBusinessPartner(@PathParam("usuario") String usuario, BusinessPartnerDTO dto) {
        SesionSAPB1WSDTO sesionSAPDTO = applicationMBean.obtenerSesionSAP(usuario);
        if (sesionSAPDTO == null) {
            console.log(Level.SEVERE, "No fue posible iniciar una sesion en SAP B1WS.");
            return Response.ok(new ResponseDTO(-1, "No fue posible iniciar una sesión en SAP B1WS.")).build();
        }

        try {
            BusinessPartnersServiceConnector bpsc = sapB1WSBean.getBusinessPartnersServiceConnectorInstance(sesionSAPDTO.getIdSesionSAP());
            bpsc.createBusinessPartner(dto);
            return Response.ok(new ResponseDTO(0, "")).build();
        } catch (Exception e) {
            console.log(Level.SEVERE, "Ocurrio un error al crear el cliente. ", e);
            return Response.ok(new ResponseDTO(-1, e.getMessage())).build();
        }

    }
}
