package co.matisses.bcs.b1ws.client.binlocations;

import co.matisses.bcs.dto.SesionSAPB1WSDTO;
import co.matisses.bcs.mbean.BCSApplicationMBean;
import co.matisses.bcs.mbean.SAPB1WSBean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
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
 * @author ygil
 */
@Stateless
@Path("binlocations")
public class BinLocationsREST {

    @Inject
    private SAPB1WSBean sapB1WSBean;
    @Inject
    private BCSApplicationMBean applicationMBean;
    private static final Logger console = Logger.getLogger(BinLocationsREST.class.getSimpleName());

    @POST
    @Path("create/{usuario}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response createBinLocations(@PathParam("usuario") String usuario, BinLocationsDTO dto) {
        SesionSAPB1WSDTO sesionSAPDTO = applicationMBean.obtenerSesionSAP(usuario);
        if (sesionSAPDTO == null) {
            console.log(Level.SEVERE, "No fue posible iniciar una sesion en SAP B1WS.");
            return Response.ok(new BinLocationsResponseDTO(-1, "No fue posible iniciar una sesión en SAP B1WS.")).build();
        }

        BinLocationsServiceConnector connection = sapB1WSBean.getBinLocationsServiceConnectorInstance(sesionSAPDTO.getIdSesionSAP());
        Long absEntry = connection.createBinLocation(dto);

        if (absEntry == null || absEntry <= 0) {
            return Response.ok(new BinLocationsResponseDTO(-1, "No fue posible crear la ubicación solicitada.")).build();
        } else {
            return Response.ok(new BinLocationsResponseDTO(0, absEntry.intValue())).build();
        }
    }

    @POST
    @Path("update/{usuario}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response editBinLocations(@PathParam("usuario") String usuario, BinLocationsDTO dto) {
        SesionSAPB1WSDTO sesionSAPDTO = applicationMBean.obtenerSesionSAP(usuario);
        if (sesionSAPDTO == null) {
            console.log(Level.SEVERE, "No fue posible iniciar una sesion en SAP B1WS.");
            return Response.ok(new BinLocationsResponseDTO(-1, "No fue posible iniciar una sesión en SAP B1WS.")).build();
        }

        BinLocationsServiceConnector connection = sapB1WSBean.getBinLocationsServiceConnectorInstance(sesionSAPDTO.getIdSesionSAP());

        if (!connection.editBinLocation(dto)) {
            return Response.ok(new BinLocationsResponseDTO(-1, "No fue posible modificar la ubicación solicitada.")).build();
        } else {
            return Response.ok(new BinLocationsResponseDTO(0, dto.getAbsEntry())).build();
        }
    }
}
