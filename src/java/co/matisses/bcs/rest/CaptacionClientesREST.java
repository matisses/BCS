package co.matisses.bcs.rest;

import co.matisses.bcs.dto.CaptacionClienteDTO;
import co.matisses.persistence.web.entity.CaptacionCliente;
import co.matisses.persistence.web.facade.CaptacionClienteFacade;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
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
@Path("captacionclientes")
public class CaptacionClientesREST {

    private static final Logger console = Logger.getLogger(CaptacionClientesREST.class.getSimpleName());
    @EJB
    private CaptacionClienteFacade captacionClienteFacade;

    @POST
    @Path("registrarcliente/")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response captarCliente(final CaptacionClienteDTO cliente) {
        if (cliente == null) {
            return Response.ok(new CaptacionClienteDTO(-1, "No se recibieron datos para ejecutar la consulta")).build();
        }

        if (cliente.getNombres() == null || cliente.getNombres().trim().isEmpty()) {
            return Response.ok(new CaptacionClienteDTO(-1, "Se debe ingresar los nombres del cliente")).build();
        }
        if (cliente.getApellidos() == null || cliente.getApellidos().trim().isEmpty()) {
            return Response.ok(new CaptacionClienteDTO(-1, "Se debe ingresar los apellidos del cliente")).build();
        }
        if (cliente.getCorreo() == null || cliente.getCorreo().trim().isEmpty()) {
            return Response.ok(new CaptacionClienteDTO(-1, "Se debe ingresar el correo del cliente")).build();
        }
        if (cliente.getCelular() == null || cliente.getCelular().trim().isEmpty()) {
            return Response.ok(new CaptacionClienteDTO(-1, "Se debe ingresar el número de celular del cliente")).build();
        } else if (cliente.getCelular().length() < 10) {
            return Response.ok(new CaptacionClienteDTO(-1, "El número de celular que está ingresando no es válido")).build();
        }
        if (cliente.getRestaurante() == null || cliente.getRestaurante().trim().isEmpty()) {
            return Response.ok(new CaptacionClienteDTO(-1, "Se debe ingresar el restaurante donde el cliente esta realizando el registro")).build();
        }
        if (cliente.isPermitirUsoDatos() == false) {
            return Response.ok(new CaptacionClienteDTO(-1, "Se debe aprobar el uso de datos por parte de Matisses")).build();
        }

        console.log(Level.INFO, "Se registrara un cliente en la tabla de captacion de clientes.");
        CaptacionCliente cap = new CaptacionCliente();

        cap.setApellidos(cliente.getApellidos());
        cap.setCelular(cliente.getCelular());
        cap.setConoceMatisses(cliente.isConoceMatisses());
        cap.setCorreo(cliente.getCorreo());
        cap.setNombres(cliente.getNombres());
        cap.setRestaurante(cliente.getRestaurante());
        if (cliente.getCodigo() != null && cliente.getCodigo().equals("0305")) {
            cap.setCodigo(cliente.getCodigo());
        } else if (cliente.getCodigo() != null && !cliente.getCodigo().isEmpty()) {
            return Response.ok(new CaptacionClienteDTO(-1, "El código ingresado no es permitido")).build();
        }

        try {
            captacionClienteFacade.create(cap);
            console.log(Level.INFO, "Se creo el registro de captacion de cliente con la siguiente informacion {0}", cliente.toString());

            cliente.setCodigoError(1);
            cliente.setMensajeError("Se hizo el registro exitosamente.");
            return Response.ok(cliente).build();
        } catch (Exception e) {
            console.log(Level.SEVERE, "Ocurrio un error al registrar la informacion del cliente en la base de datos. Error {0}.", e.getMessage());
            return Response.ok(new CaptacionClienteDTO(0, "Ocurrió un error al registrar la información del cliente.")).build();
        }
    }
}
