package co.matisses.bcs.rest;

import co.matisses.persistence.sap.entity.ServiceCallSolutions;
import co.matisses.persistence.sap.facade.ServiceCallSolutionsFacade;
import co.matisses.persistence.web.entity.LlamadaSolucion;
import co.matisses.persistence.web.facade.LlamadaSolucionFacade;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author ygil
 */
@Stateless
@Path("sondasolucionesllamada")
public class SondaSolucionesLlamadaREST implements Serializable {

    @EJB
    private LlamadaSolucionFacade llamadaSolucionFacade;
    @EJB
    private ServiceCallSolutionsFacade serviceCallSolutionsFacade;

    @GET
    @Path("ejecutarsonda")
    @Produces({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response ejecutarSonda() {
        List<Object[]> ids = serviceCallSolutionsFacade.obtenerSolucionesProcesar(llamadaSolucionFacade.obtenerIds());

        if (ids != null && !ids.isEmpty()) {
            ids.stream().forEach((id) -> {
                ServiceCallSolutions solution = serviceCallSolutionsFacade.find((Integer) id[0]);

                if (solution != null && solution.getSltCode() != null && solution.getSltCode() != 0) {
                    LlamadaSolucion call = new LlamadaSolucion();

                    call.setCausa(solution.getCause());
                    call.setComentarios(solution.getDescriptio());
                    call.setDocEntry((Integer) id[1]);
                    call.setEstado(solution.getStatusNum());
                    call.setSintoma(solution.getSymptom());
                    call.setSolucion(solution.getSubject());
                    call.setItemCode(solution.getItemCode());
                    call.setFecha(solution.getDateCreate());
                    call.setPropietario(solution.getOwner().toString());
                    call.setIdLlamadaSolucion(solution.getSltCode().longValue());

                    try {
                        llamadaSolucionFacade.create(call);
                    } catch (Exception e) {
                    }
                }
            });
        }

        return Response.ok().build();
    }
}
