package co.matisses.bcs.b1ws.client.warehousesublevelcodes;

import static co.matisses.bcs.b1ws.client.B1WSServiceInfo.WAREHOUSE_SUBLEVEL_CODES_SERVICE_WSDL_NAME;
import co.matisses.bcs.b1ws.ws.warehousesublevelcodes.Add;
import co.matisses.bcs.b1ws.ws.warehousesublevelcodes.AddResponse;
import co.matisses.bcs.b1ws.ws.warehousesublevelcodes.MsgHeader;
import co.matisses.bcs.b1ws.ws.warehousesublevelcodes.WarehouseSublevelCode;
import co.matisses.bcs.b1ws.ws.warehousesublevelcodes.WarehouseSublevelCodesService;
import co.matisses.bcs.b1ws.ws.warehousesublevelcodes.WarehouseSublevelCodesServiceSoap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ygil
 */
public class WarehouseSublevelCodesServiceConnector {

    private static final Logger console = Logger.getLogger(WarehouseSublevelCodesServiceConnector.class.getSimpleName());
    private WarehouseSublevelCodesService service;
    private String sessionId;

    public WarehouseSublevelCodesServiceConnector() {
    }

    public WarehouseSublevelCodesServiceConnector(WarehouseSublevelCodesService service, String sessionId) {
        super();
        this.service = service;
        this.sessionId = sessionId;
    }

    public Long createWarehouseSublevel(WarehouseSublevelCodesDTO dto) {
        console.log(Level.INFO, "Creando propiedad de piso {0} para ubicaciones con sesion {1}", new Object[]{dto.getCode(), sessionId});
        WarehouseSublevelCodesServiceSoap port = service.getWarehouseSublevelCodesServiceSoap12();
        if (sessionId == null) {
            console.log(Level.SEVERE, "No se recibio un ID de sesion de B1WS valido.");
            return -1L;
        }

        MsgHeader header = new MsgHeader();
        header.setSessionID(sessionId);
        header.setServiceName(WAREHOUSE_SUBLEVEL_CODES_SERVICE_WSDL_NAME);

        WarehouseSublevelCode sublevel = new WarehouseSublevelCode();

        sublevel.setCode(dto.getCode());
        sublevel.setWarehouseSublevel(dto.getWarehouseSublevel().longValue());

        try {
            Add value = new Add();
            value.setWarehouseSublevelCode(sublevel);
            AddResponse resp = port.add(value, header);
            console.log(Level.INFO, "Se creo la propiedad de piso {0} correctamente", dto.getCode());
            return resp.getWarehouseSublevelCodeParams().getAbsEntry();
        } catch (Exception e) {
            console.log(Level.SEVERE, "Ocurrio un error al crear la propiedad de piso usando B1WS. ", e);
            return -1L;
        }
    }
}
