package co.matisses.bcs.rest;

import co.matisses.bcs.dto.DatosCompraWebDTO;
import co.matisses.bcs.dto.ItemDTO;
import co.matisses.bcs.dto.ResponseDTO;
import co.matisses.bcs.mbean.BCSGenericMBean;
import co.matisses.persistence.web.entity.ProgramacionDescuento;
import co.matisses.persistence.web.facade.ProgramacionDescuentoFacade;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author ygil
 */
@Stateless
@Path("shoppingcart")
public class ShoppingCartValidatorREST {

    @Inject
    private BCSGenericMBean genericMBean;
    private static final Logger CONSOLE = Logger.getLogger(ShoppingCartValidatorREST.class.getSimpleName());
    @EJB
    private ProgramacionDescuentoFacade programacionDescuentoFacade;

    @POST
    @Path("validate")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response validarCarrito(DatosCompraWebDTO dto) {
        if (dto == null) {
            CONSOLE.log(Level.SEVERE, "No se recibieron datos del carrito.");
            return Response.ok(new ResponseDTO(-1, "No se recibieron datos del carrito")).build();
        }

        CONSOLE.log(Level.INFO, "Validando datos del carrito {0}", dto);

        if (dto.getItems() != null && !dto.getItems().isEmpty()) {
            for (ItemDTO i : dto.getItems()) {
                //Validar si el item tiene o se le cambio el descuento

                ProgramacionDescuento descuento = programacionDescuentoFacade.consultarDescuentosReferencia("WE", i.getItemcode());

                if (descuento != null && descuento.getPorcentaje() != null && descuento.getPorcentaje() > 0) {
                    if ((i.getDescuento() > 0 && i.getDescuento() == descuento.getPorcentaje().intValue())) {
                        return Response.ok(new ResponseDTO(-1, "true")).build();
                    }
                } else if (i.getDescuento() != null && i.getDescuento() > 0) {
                    return Response.ok(new ResponseDTO(-1, "false")).build();
                }

                //Si pasa el paso del descuento, se valida el precio de la referencia
                Integer precio = genericMBean.obtenerPrecioVenta(i.getItemcode());

                if (i.getPrecio() != null && !i.getPrecio().equals(precio)) {
                    return Response.ok(new ResponseDTO(-1, "false")).build();
                }
            }
        } else {
            CONSOLE.log(Level.SEVERE, "No se recibieron items del carrito.");
            return Response.ok(new ResponseDTO(-1, "No se recibieron items del carrito")).build();
        }
        return Response.ok(new ResponseDTO(-1, "true")).build();
    }
}
