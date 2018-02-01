package co.matisses.bcs.rest;

import co.matisses.bcs.dto.DatosCompraWebDTO;
import co.matisses.bcs.dto.ResponseDTO;
import co.matisses.bcs.mbean.BCSApplicationMBean;
import co.matisses.bcs.mbean.BCSGenericMBean;
import co.matisses.carriers.coordinadora.CoordinadoraWSMain;
import co.matisses.persistence.sap.entity.ItemInventario;
import co.matisses.persistence.sap.entity.SaldoItemInventario;
import co.matisses.persistence.sap.facade.ItemInventarioFacade;
import co.matisses.persistence.sap.facade.SaldoItemInventarioFacade;
import co.matisses.persistence.web.entity.ProgramacionDescuento;
import co.matisses.persistence.web.facade.ProgramacionDescuentoFacade;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
@Path("coordinadora")
public class CoordinadoraWSREST {

    @Inject
    private BCSGenericMBean genericMBean;
    @Inject
    private BCSApplicationMBean applicationMBean;
    @EJB
    private ItemInventarioFacade itemInventarioFacade;
    @EJB
    private ProgramacionDescuentoFacade programacionDescuentoFacade;
    @EJB
    private SaldoItemInventarioFacade saldoItemInventarioFacade;

    @POST
    @Path("quotation")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response crearCotizacionEnvio(DatosCompraWebDTO datos) {
        Map<String, List<String[]>> datosEnvio = new HashMap<>();

        datos.getItems().stream().forEach((item) -> {
            List<SaldoItemInventario> saldos = saldoItemInventarioFacade.buscarReferenciaSaldoBodegaVenta(item.getItemcode());
            if (saldos != null && !saldos.isEmpty()) {
                int cantidad = item.getSelectedQuantity();
                for (SaldoItemInventario s : saldos) {
                    String almacenOrigen = s.getSaldoItemInventarioPK().getWhsCode().getCodigoCiudad() + "000";
                    ItemInventario inv = itemInventarioFacade.find(item.getItemcode());
                    String[] art = new String[6];

                    art[0] = inv.getSHeight1().toString();
                    art[1] = inv.getSWidth1().toString();
                    art[2] = inv.getSLength1().toString();
                    art[3] = inv.getSWeight1().toString();
                    ProgramacionDescuento descuento = programacionDescuentoFacade.consultarDescuentosReferencia("WE", item.getItemcode());

                    Integer precio = genericMBean.obtenerPrecioVenta(item.getItemcode());

                    if (descuento != null && descuento.getPorcentaje() != null && descuento.getPorcentaje() != 0) {
                        art[5] = String.valueOf(((precio / 100) * descuento.getPorcentaje().intValue()));
                    } else {
                        art[5] = precio.toString();
                    }

                    if (s.getOnHand().intValue() >= item.getSelectedQuantity()) {
                        art[4] = item.getSelectedQuantity().toString();
                        cantidad = 0;
                    } else {
                        art[4] = String.valueOf(s.getOnHand().intValue());
                        cantidad = cantidad - s.getOnHand().intValue();
                    }

                    if (cantidad == 0) {
                        if (datosEnvio.containsKey(almacenOrigen)) {
                            datosEnvio.get(almacenOrigen).add(art);
                        } else {
                            List<String[]> sal = new ArrayList<>();
                            sal.add(art);

                            datosEnvio.put(almacenOrigen, sal);
                        }
                    }
                }
            }
        });

        if (!datosEnvio.isEmpty()) {
            try {
                CoordinadoraWSMain coordinadora = new CoordinadoraWSMain();
                Integer totalEnvio = coordinadora.cotizarEnvio(applicationMBean.obtenerValorPropiedad("coordinadora.wsdl"), datos.getCiudadDestino() + "000", datosEnvio);
                return Response.ok(new ResponseDTO(1, genericMBean.redondearValor(totalEnvio, 10))).build();
            } catch (Exception e) {
                return Response.ok(new ResponseDTO(0, e.getMessage())).build();
            }
        }
        return Response.ok(new ResponseDTO(0, "No se genero el valor del envio")).build();
    }
}
