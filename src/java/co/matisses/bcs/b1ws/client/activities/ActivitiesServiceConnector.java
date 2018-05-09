package co.matisses.bcs.b1ws.client.activities;

import static co.matisses.bcs.b1ws.client.B1WSServiceInfo.ACTIVITIES_SERVICE_WSDL_NAME;
import co.matisses.bcs.b1ws.ws.activities.ActivitiesService;
import co.matisses.bcs.b1ws.ws.activities.ActivitiesServiceSoap;
import co.matisses.bcs.b1ws.ws.activities.Activity;
import co.matisses.bcs.b1ws.ws.activities.ActivityParams;
import co.matisses.bcs.b1ws.ws.activities.GetActivity;
import co.matisses.bcs.b1ws.ws.activities.MsgHeader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ygil
 */
public class ActivitiesServiceConnector {

    private static final Logger CONSOLE = Logger.getLogger(ActivitiesServiceConnector.class.getSimpleName());
    private String sessionId;
    private ActivitiesService service;

    public ActivitiesServiceConnector(String sessionId, ActivitiesService service) {
        super();
        this.sessionId = sessionId;
        this.service = service;
    }

    public Activity findActivity(Integer activityID) throws ActivitiesException {
        ActivitiesServiceSoap port = service.getActivitiesServiceSoap12();
        if (sessionId == null) {
            throw new ActivitiesException("No se recibio un ID de sesion de B1WS valido.");
        }

        MsgHeader header = new MsgHeader();
        header.setSessionID(sessionId);
        header.setServiceName(ACTIVITIES_SERVICE_WSDL_NAME);

        ActivityParams activity = new ActivityParams();
        activity.setActivityCode(activityID.longValue());

        try {
            GetActivity value = new GetActivity();
            value.setActivityParams(activity);
            CONSOLE.log(Level.INFO, "Se obtuvo la llamada de servicio con activityID {0} satisfactoriamente", activityID);

            Activity actividad = (port.getActivity(value, header)).getActivity();

            return actividad;
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al obtener una actividad usando B1WS. ", e);
            throw new ActivitiesException("No fue posible obtener una actividad. " + e.getMessage());
        }
    }
}
