package co.matisses.bcs.b1ws.client.warehousesublevelcodes;

import co.matisses.bcs.b1ws.client.B1WSServiceUnavailableException;
import co.matisses.bcs.b1ws.client.SAPSessionManager;
import co.matisses.bcs.dto.ResponseDTO;
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

/**
 *
 * @author ygil
 */
@Stateless
@Path("warehousesublevelcodes")
public class WarehouseSublevelCodesREST {

    @Inject
    private SAPB1WSBean sapB1WSBean;
    private SAPSessionManager sessionManager = new SAPSessionManager();
    private static final Logger console = Logger.getLogger(WarehouseSublevelCodesREST.class.getSimpleName());

    @POST
    @Path("create/{usuario}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public ResponseDTO createWarehouseSublevel(@PathParam("usuario") String usuario, WarehouseSublevelCodesDTO dto) throws B1WSServiceUnavailableException {
        String sesionId = sessionManager.login();
        if (sesionId == null) {
            console.log(Level.SEVERE, "No fue posible iniciar una sesion en SAP B1WS.");
            return new ResponseDTO(0, "No fue posible iniciar una sesión en SAP B1WS.");
        }

        WarehouseSublevelCodesServiceConnector connection = sapB1WSBean.getWarehouseSublevelCodesServiceConnectorInstance(sesionId);
        Long absEntry = connection.createWarehouseSublevel(dto);

        sessionManager.logout(sesionId);

        if (absEntry == null || absEntry <= 0) {
            return new ResponseDTO(0, "No fue posible crear la propiedad para la ubicación solicitada.");
        } else {
            return new ResponseDTO(1, absEntry.intValue());
        }
    }
}
