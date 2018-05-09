package co.matisses.bcs.b1ws.client.serviceCalls;

import static co.matisses.bcs.b1ws.client.B1WSServiceInfo.SERVICE_CALLS_SERVICE_WSDL_NAME;
import co.matisses.bcs.b1ws.ws.serviceCalls.Add;
import co.matisses.bcs.b1ws.ws.serviceCalls.AddResponse;
import co.matisses.bcs.b1ws.ws.serviceCalls.GetByParams;
import co.matisses.bcs.b1ws.ws.serviceCalls.MsgHeader;
import co.matisses.bcs.b1ws.ws.serviceCalls.ServiceCall;
import co.matisses.bcs.b1ws.ws.serviceCalls.ServiceCallParams;
import co.matisses.bcs.b1ws.ws.serviceCalls.ServiceCallsService;
import co.matisses.bcs.b1ws.ws.serviceCalls.ServiceCallsServiceSoap;
import co.matisses.bcs.b1ws.ws.serviceCalls.Update;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ygil
 */
public class ServiceCallsServiceConnector {

    private static final Logger CONSOLE = Logger.getLogger(ServiceCallsServiceConnector.class.getSimpleName());
    private String sessionId;
    private ServiceCallsService service;

    public ServiceCallsServiceConnector(String sessionId, ServiceCallsService service) {
        super();
        this.sessionId = sessionId;
        this.service = service;
    }

    public ServiceCall findServiceCall(Integer callID) throws ServiceCallsException {
        ServiceCallsServiceSoap port = service.getServiceCallsServiceSoap12();
        if (sessionId == null) {
            throw new ServiceCallsException("No se recibio un ID de sesion de B1WS valido.");
        }

        MsgHeader header = new MsgHeader();
        header.setSessionID(sessionId);
        header.setServiceName(SERVICE_CALLS_SERVICE_WSDL_NAME);

        ServiceCallParams call = new ServiceCallParams();
        call.setServiceCallID(callID.longValue());

        try {
            GetByParams value = new GetByParams();
            value.setServiceCallParams(call);
            CONSOLE.log(Level.INFO, "Se obtuvo la llamada de servicio con callID {0} satisfactoriamente", call.getServiceCallID());

            ServiceCall serviceCall = (port.getByParams(value, header)).getServiceCall();

            return serviceCall;
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al obtener una llamada de servicio usando B1WS. ", e);
            throw new ServiceCallsException("No fue posible obtener la llamada de servicio. " + e.getMessage());
        }
    }

    public Long createServiceCall(ServiceCall call) throws ServiceCallsException {
        CONSOLE.log(Level.INFO, "Creando llamada de servicio {0} con sesion {1}", new Object[]{call, sessionId});
        ServiceCallsServiceSoap port = service.getServiceCallsServiceSoap12();
        if (sessionId == null) {
            throw new ServiceCallsException("No se recibio un ID de sesion de B1WS valido.");
        }

        MsgHeader header = new MsgHeader();
        header.setSessionID(sessionId);
        header.setServiceName(SERVICE_CALLS_SERVICE_WSDL_NAME);

        try {
            Add value = new Add();
            value.setServiceCall(call);
            AddResponse resp = port.add(value, header);
            CONSOLE.log(Level.INFO, "Se registro una llamada de servicio {0} satisfactoriamente", resp.getServiceCallParams().getServiceCallID());
            return resp.getServiceCallParams().getServiceCallID();
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al crear una nueva llamada de servicio usando B1WS. ", e);
            throw new ServiceCallsException("No fue posible crear la llamada de servicio. " + e.getMessage());
        }
    }

    public void editServiceCall(ServiceCall call) throws ServiceCallsException {
        ServiceCallsServiceSoap port = service.getServiceCallsServiceSoap12();
        if (sessionId == null) {
            throw new ServiceCallsException("No se recibio un ID de sesion de B1WS valido.");
        }

        MsgHeader header = new MsgHeader();
        header.setSessionID(sessionId);
        header.setServiceName(SERVICE_CALLS_SERVICE_WSDL_NAME);

        try {
            Update value = new Update();
            value.setServiceCall(call);
            port.update(value, header);
            CONSOLE.log(Level.INFO, "Se actualizo la llamada de servicio con id {0} satisfactoriamente", call.getServiceCallID());
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al modificar una llamada de servicio usando B1WS. ", e);
            throw new ServiceCallsException("No fue posible modificar la llamada de servicio. " + e.getMessage());
        }
    }
}
