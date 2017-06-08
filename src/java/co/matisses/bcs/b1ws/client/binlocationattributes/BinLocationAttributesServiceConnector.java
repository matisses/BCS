package co.matisses.bcs.b1ws.client.binlocationattributes;

import static co.matisses.bcs.b1ws.client.B1WSServiceInfo.BIN_LOCATION_ATTRIBUTES_SERVICE_WSDL_NAME;
import co.matisses.bcs.b1ws.ws.binlocationattributes.Add;
import co.matisses.bcs.b1ws.ws.binlocationattributes.AddResponse;
import co.matisses.bcs.b1ws.ws.binlocationattributes.BinLocationAttribute;
import co.matisses.bcs.b1ws.ws.binlocationattributes.BinLocationAttributesService;
import co.matisses.bcs.b1ws.ws.binlocationattributes.BinLocationAttributesServiceSoap;
import co.matisses.bcs.b1ws.ws.binlocationattributes.MsgHeader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ygil
 */
public class BinLocationAttributesServiceConnector {

    private static final Logger console = Logger.getLogger(BinLocationAttributesServiceConnector.class.getSimpleName());
    private BinLocationAttributesService service;
    private String sessionId;

    public BinLocationAttributesServiceConnector() {
    }

    public BinLocationAttributesServiceConnector(BinLocationAttributesService service, String sessionId) {
        super();
        this.service = service;
        this.sessionId = sessionId;
    }

    public Long createBinLocationAttribute(BinLocationAttributesDTO dto) {
        console.log(Level.INFO, "Creando atributo {0} con sesion {1}", new Object[]{dto.getCode(), sessionId});
        BinLocationAttributesServiceSoap port = service.getBinLocationAttributesServiceSoap12();
        if (sessionId == null) {
            console.log(Level.SEVERE, "No se recibio un ID de sesion de B1WS valido.");
            return -1L;
        }

        MsgHeader header = new MsgHeader();
        header.setSessionID(sessionId);
        header.setServiceName(BIN_LOCATION_ATTRIBUTES_SERVICE_WSDL_NAME);

        BinLocationAttribute attribute = new BinLocationAttribute();

        attribute.setAttribute(dto.getAttributo().longValue());
        attribute.setCode(dto.getCode());

        try {
            Add value = new Add();
            value.setBinLocationAttribute(attribute);
            AddResponse resp = port.add(value, header);
            console.log(Level.INFO, "Se creo el atributo {0} correctamente", dto.getCode());
            return resp.getBinLocationAttributeParams().getAbsEntry();
        } catch (Exception e) {
            console.log(Level.SEVERE, "Ocurrio un error al crear el atributo usando B1WS", e);
            return -1L;
        }
    }
}
