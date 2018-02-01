package co.matisses.bcs.b1ws.client.employeesinfo;

import static co.matisses.bcs.b1ws.client.B1WSServiceInfo.EMPLOYEES_INFO_SERVICE_WSDL_NAME;
import co.matisses.bcs.b1ws.ws.employeesinfo.Add;
import co.matisses.bcs.b1ws.ws.employeesinfo.AddResponse;
import co.matisses.bcs.b1ws.ws.employeesinfo.EmployeeInfo;
import co.matisses.bcs.b1ws.ws.employeesinfo.EmployeeInfoParams;
import co.matisses.bcs.b1ws.ws.employeesinfo.EmployeesInfoService;
import co.matisses.bcs.b1ws.ws.employeesinfo.EmployeesInfoServiceSoap;
import co.matisses.bcs.b1ws.ws.employeesinfo.GetByParams;
import co.matisses.bcs.b1ws.ws.employeesinfo.MsgHeader;
import co.matisses.bcs.b1ws.ws.employeesinfo.Update;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ygil
 */
public class EmployeesInfoServiceConnector {

    private static final Logger CONSOLE = Logger.getLogger(EmployeesInfoServiceConnector.class.getSimpleName());
    private String sessionId;
    private EmployeesInfoService service;

    public EmployeesInfoServiceConnector(String sessionId, EmployeesInfoService service) {
        super();
        this.sessionId = sessionId;
        this.service = service;
    }

    public EmployeeInfo findEmployeesInfo(Integer empID) throws EmployeesInfoServiceException {
        EmployeesInfoServiceSoap port = service.getEmployeesInfoServiceSoap12();
        if (sessionId == null) {
            throw new EmployeesInfoServiceException("No se recibio un ID de sesion de B1WS valido.");
        }

        MsgHeader header = new MsgHeader();
        header.setSessionID(sessionId);
        header.setServiceName(EMPLOYEES_INFO_SERVICE_WSDL_NAME);

        EmployeeInfoParams employee = new EmployeeInfoParams();
        employee.setEmployeeID(empID.longValue());

        try {
            GetByParams value = new GetByParams();
            value.setEmployeeInfoParams(employee);
            CONSOLE.log(Level.INFO, "Se obtuvo el empleado con empID {0} satisfactoriamente", employee.getEmployeeID());

            EmployeeInfo employeeInfo = (port.getByParams(value, header)).getEmployeeInfo();

            CONSOLE.log(Level.INFO, employeeInfo.toString());

            return employeeInfo;
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al obtener un empleado usando B1WS. ", e);
            throw new EmployeesInfoServiceException("No fue posible obtener el empleado. " + e.getMessage());
        }
    }

    public void editEmployeesInfo(EmployeeInfo employee) throws EmployeesInfoServiceException {
        CONSOLE.log(Level.INFO, employee.toString());
        EmployeesInfoServiceSoap port = service.getEmployeesInfoServiceSoap12();
        if (sessionId == null) {
            throw new EmployeesInfoServiceException("No se recibio un ID de sesion de B1WS valido.");
        }

        MsgHeader header = new MsgHeader();
        header.setServiceName(EMPLOYEES_INFO_SERVICE_WSDL_NAME);
        header.setSessionID(sessionId);

        try {
            Update value = new Update();
            value.setEmployeeInfo(employee);
            port.update(value, header);
            CONSOLE.log(Level.INFO, "Se actualizo el empleado con empID {0} satisfactoriamente", employee.getEmployeeID());
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al modificar un empleado usando B1WS. ", e);
            throw new EmployeesInfoServiceException("No fue posible modificar el empleado. " + e.getMessage());
        }
    }

    public void createEmployeesInfo(EmployeeInfo employee) throws EmployeesInfoServiceException {
        CONSOLE.log(Level.INFO, "Creando cliente {0} con sesion {1}", new Object[]{employee, sessionId});
        EmployeesInfoServiceSoap port = service.getEmployeesInfoServiceSoap12();
        if (sessionId == null) {
            throw new EmployeesInfoServiceException("No se recibio un ID de sesion de B1WS valido.");
        }

        MsgHeader header = new MsgHeader();
        header.setServiceName(EMPLOYEES_INFO_SERVICE_WSDL_NAME);
        header.setSessionID(sessionId);

        try {
            Add value = new Add();
            value.setEmployeeInfo(employee);
            AddResponse resp = port.add(value, header);
            CONSOLE.log(Level.INFO, "Se registro el empleado con empID {0} satisfactoriamente", resp.getEmployeeInfoParams().getEmployeeID());
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al crear un empleado usando B1WS. ", e);
            throw new EmployeesInfoServiceException("No fue posible crear el empleado. " + e.getMessage());
        }
    }
}
