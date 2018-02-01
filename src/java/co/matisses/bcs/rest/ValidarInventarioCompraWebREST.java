package co.matisses.bcs.rest;

import co.matisses.bcs.dto.ItemDTO;
import co.matisses.persistence.sap.entity.SaldoUbicacion;
import co.matisses.persistence.sap.facade.SaldoUbicacionFacade;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author ygil
 */
@Stateless
@Path("validarinventario")
public class ValidarInventarioCompraWebREST {

    @EJB
    private SaldoUbicacionFacade saldoUbicacionFacade;

    @PostConstruct
    protected void initialize() {

    }

    @POST
    @Path("validar")
    @Consumes({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response validarInventario(List<ItemDTO> items) {
        for (ItemDTO i : items) {
            int cantidadTotal = 0;
            List<SaldoUbicacion> saldos = saldoUbicacionFacade.buscarXReferencia(i.getItemcode(), false);

            if (saldos != null && !saldos.isEmpty()) {
                int cantidadNecesaria = i.getSelectedQuantity();
                for (SaldoUbicacion s : saldos) {
                    cantidadTotal+= s.getOnHandQty().intValue();
                    if (s.getWhsCode().substring(0, 1).equals("0")) {
                        if (s.getOnHandQty().intValue() >= cantidadNecesaria) {
                            cantidadNecesaria = 0;
                        } else {
                            cantidadNecesaria -= s.getOnHandQty().intValue();
                        }
                        if (cantidadNecesaria == 0) {
                            break;
                        }
                    }
                }

                if (cantidadNecesaria == 0) {
                    i.setSinSaldo(false);
                } else {
                    i.setAvailableStock(cantidadTotal);
                    i.setSinSaldo(true);
                }
            } else {
                i.setSinSaldo(true);
            }
        }

        return Response.ok(items).build();
    }
}
