package co.matisses.bcs.b1ws.client.employeesinfo;

import co.matisses.bcs.b1ws.client.B1WSServiceUnavailableException;
import co.matisses.bcs.b1ws.client.SAPSessionManager;
import co.matisses.bcs.b1ws.ws.employeesinfo.EmployeeInfo;
import co.matisses.bcs.dto.ResponseDTO;
import co.matisses.bcs.mbean.SAPB1WSBean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
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
@Path("employeesinfo")
public class EmployeesInfoREST {

    @Inject
    SAPB1WSBean sapB1WSBean;
    private static final Logger CONSOLE = Logger.getLogger(EmployeesInfoREST.class.getSimpleName());
    private SAPSessionManager sessionManager = new SAPSessionManager();

    @GET
    @Path("find/{empID}")
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    public Response find(@PathParam("empID") Integer empID) throws B1WSServiceUnavailableException {
        CONSOLE.log(Level.INFO, "Buscando cliente por id [{0}]", empID);
        if (empID == null || empID == 0) {
            return Response.serverError().build();
        }
        String sesionId = sessionManager.login();

        if (sesionId == null) {
            CONSOLE.log(Level.SEVERE, "No fue posible iniciar una sesion en SAP B1WS.");
            return Response.ok(new ResponseDTO(0, "No fue posible iniciar una sesión en SAP B1WS.")).build();
        }

        try {
            EmployeesInfoServiceConnector eisc = sapB1WSBean.getEmployeesInfoService(sesionId);

            EmployeeInfo employee = eisc.findEmployeesInfo(empID);
            return Response.ok(employee).build();
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al consultar el empleado. ", e);
            return Response.ok(new ResponseDTO(-1, e.getMessage())).build();
        } finally {
            sessionManager.logout(sesionId);
        }
    }

    @POST
    @Path("edit")
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response editEmployee(EmployeeInfo employee) throws B1WSServiceUnavailableException {
        String sesionId = sessionManager.login();

        if (sesionId == null) {
            CONSOLE.log(Level.SEVERE, "No fue posible iniciar una sesion en SAP B1WS.");
            return Response.ok(new ResponseDTO(0, "No fue posible iniciar una sesión en SAP B1WS.")).build();
        }

        try {
            EmployeesInfoServiceConnector eisc = sapB1WSBean.getEmployeesInfoService(sesionId);

            eisc.editEmployeesInfo(employee);
            return Response.ok(new ResponseDTO(0)).build();
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al editar el cliente. ", e);
            return Response.ok(new ResponseDTO(-1, e.getMessage())).build();
        } finally {
            sessionManager.logout(sesionId);
        }
    }

    @POST
    @Path("create")
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createEmployee(EmployeeInfo employee) throws B1WSServiceUnavailableException {
        String sesionId = sessionManager.login();

        if (sesionId == null) {
            CONSOLE.log(Level.SEVERE, "No fue posible iniciar una sesion en SAP B1WS.");
            return Response.ok(new ResponseDTO(0, "No fue posible iniciar una sesión en SAP B1WS.")).build();
        }

        try {
            EmployeesInfoServiceConnector eisc = sapB1WSBean.getEmployeesInfoService(sesionId);

            eisc.createEmployeesInfo(employee);
            return Response.ok(new ResponseDTO(0)).build();
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al editar el cliente. ", e);
            return Response.ok(new ResponseDTO(-1, e.getMessage())).build();
        } finally {
            sessionManager.logout(sesionId);
        }
    }
}
