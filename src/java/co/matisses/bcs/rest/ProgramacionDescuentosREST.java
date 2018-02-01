package co.matisses.bcs.rest;

import co.matisses.bcs.dto.ProgramacionDescuentoDTO;
import co.matisses.bcs.dto.ProgramacionDescuentoResponseDTO;
import co.matisses.persistence.sap.facade.PrecioVentaItemFacade;
import co.matisses.persistence.web.entity.ProgramacionDescuento;
import co.matisses.persistence.web.facade.ProgramacionDescuentoFacade;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author dbotero
 */
@Stateless
@Path("descuentos")
public class ProgramacionDescuentosREST {

    private static final Logger CONSOLE = Logger.getLogger(ProgramacionDescuentosREST.class.getSimpleName());
    @EJB
    private ProgramacionDescuentoFacade descuentosFacade;
    @EJB
    private PrecioVentaItemFacade precioFacade;

    @GET
    @Path("consultaritem/{referencia}")
    @Produces({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response consultarPrecioArticulo(@PathParam("referencia") String referencia) {
        CONSOLE.log(Level.FINE, "Iniciando proceso de consulta de precio y descuentos activos para la referencia [{0}]", referencia);
        if (referencia == null || referencia.trim().isEmpty() || referencia.length() > 20) {
            return Response.ok(new ProgramacionDescuentoResponseDTO(-1, "La referencia ingresada no es válida")).build();
        }

        referencia = referencia.replaceAll("\\.", "");
        referencia = referencia.replaceAll("\\*", "");
        referencia = StringUtils.rightPad(referencia.substring(0, 3), 20 - (referencia.length() - 3), "0") + referencia.substring(3);

        CONSOLE.log(Level.FINE, "Consultando precio y descuentos para la referencia [{0}]", referencia);
        ProgramacionDescuentoResponseDTO response = new ProgramacionDescuentoResponseDTO(precioFacade.findByItemCodeTaxIncluded(referencia));
        List<ProgramacionDescuento> descuentos = descuentosFacade.obtenerProgramacionesReferencia(referencia);
        for (ProgramacionDescuento entidad : descuentos) {
            response.getDescuentos().add(new ProgramacionDescuentoDTO(referencia, entidad.getPorcentaje(), entidad.getCanal()));
        }

        return Response.ok(response).build();
    }

    @GET
    @Path("consultaritemweb/{referencia}")
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response consultarDescuentoWeb(@PathParam("referencia") String referencia) {
        CONSOLE.log(Level.FINE, "Iniciando proceso de consulta de precio y descuentos activos para la referencia [{0}] para la pagina web", referencia);
        if (referencia == null || referencia.trim().isEmpty() || referencia.length() > 20) {
            return Response.ok(new ProgramacionDescuentoResponseDTO(-1, "La referencia ingresada no es válida")).build();
        }

        CONSOLE.log(Level.FINE, "Consultando precio y descuentos para la referencia [{0}] para la pagina web", referencia);
        ProgramacionDescuentoResponseDTO response = new ProgramacionDescuentoResponseDTO(precioFacade.findByItemCodeTaxIncluded(referencia));
        List<ProgramacionDescuento> descuentos = descuentosFacade.obtenerProgramacionesWebReferencia(referencia);
        for (ProgramacionDescuento entidad : descuentos) {
            response.getDescuentos().add(new ProgramacionDescuentoDTO(referencia, entidad.getPorcentaje(), entidad.getCanal()));
        }

        return Response.ok(response).build();
    }

    @GET
    @Path("consultardescuento/{referencia}/{canal}")
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response consultarDescuento(@PathParam("referencia") String referencia, @PathParam("canal") String canal) {
        CONSOLE.log(Level.FINE, "Iniciando proceso de consulta de precio y descuentos activos para la referencia [{0}] para promos de la pagina web", referencia);
        if (referencia == null || referencia.trim().isEmpty() || referencia.length() > 20) {
            return Response.ok(new ProgramacionDescuentoResponseDTO(-1, "La referencia ingresada no es válida")).build();
        }

        CONSOLE.log(Level.FINE, "Consultando precio y descuentos para la referencia [{0}] para promos de la pagina web", referencia);
        ProgramacionDescuentoResponseDTO response = new ProgramacionDescuentoResponseDTO(precioFacade.findByItemCodeTaxIncluded(referencia));
        ProgramacionDescuento descuento = descuentosFacade.consultarDescuentosReferencia(canal, referencia);

        response.getDescuentos().add(new ProgramacionDescuentoDTO(referencia, descuento.getPorcentaje(), descuento.getCanal()));

        return Response.ok(response).build();
    }
}
