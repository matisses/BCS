package co.matisses.bcs.b1ws.client.goodsreceipt;

import co.matisses.bcs.dto.ResponseDTO;
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

/**
 *
 * @author ygil
 */
@Stateless
@Path("goodsreceipt")
public class GoodsReceiptREST {

    @Inject
    private SAPB1WSBean sapB1WSBean;
    @Inject
    private BCSApplicationMBean applicationMBean;
    private static final Logger console = Logger.getLogger(GoodsReceiptREST.class.getSimpleName());

    @POST
    @Path("create/{usuario}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public ResponseDTO createGoodsReceipt(@PathParam("usuario") String usuario, GoodsReceiptDTO dto) {
        SesionSAPB1WSDTO sesionSAPDTO = applicationMBean.obtenerSesionSAP(usuario);
        if (sesionSAPDTO == null) {
            console.log(Level.SEVERE, "No fue posible iniciar una sesion en SAP B1WS.");
            return new ResponseDTO(0, "No fue posible iniciar una sesión en SAP B1WS.");
        }

        GoodsReceiptServiceConnector connection = sapB1WSBean.getGoodsReceiptServiceConnectorInstance(sesionSAPDTO.getIdSesionSAP());

        try {
            Long docEntry = connection.createDocument(dto);

            if (docEntry == null || docEntry <= 0) {
                return new ResponseDTO(0, "No fue posible crear la entrada de mercancia.");
            } else {
                return new ResponseDTO(1, docEntry.intValue());
            }
        } catch (Exception e) {
            return new ResponseDTO(0, e.getMessage());
        }
    }
}
