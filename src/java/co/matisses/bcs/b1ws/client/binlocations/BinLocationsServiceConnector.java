package co.matisses.bcs.b1ws.client.binlocations;

import co.matisses.bcs.b1ws.client.B1WSServiceInfo;
import co.matisses.bcs.b1ws.ws.binlocations.Add;
import co.matisses.bcs.b1ws.ws.binlocations.AddResponse;
import co.matisses.bcs.b1ws.ws.binlocations.BinLocation;
import co.matisses.bcs.b1ws.ws.binlocations.BinLocationsService;
import co.matisses.bcs.b1ws.ws.binlocations.BinLocationsServiceSoap;
import co.matisses.bcs.b1ws.ws.binlocations.MsgHeader;
import co.matisses.bcs.b1ws.ws.binlocations.Update;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ygil
 */
public class BinLocationsServiceConnector extends B1WSServiceInfo {

    private static final Logger console = Logger.getLogger(BinLocationsServiceConnector.class.getSimpleName());
    private BinLocationsService service;
    private String sessionId;

    public BinLocationsServiceConnector() {
    }

    public BinLocationsServiceConnector(BinLocationsService service, String sessionId) {
        super();
        this.service = service;
        this.sessionId = sessionId;
    }

    public Long createBinLocation(BinLocationsDTO dto) {
        console.log(Level.INFO, "Creando ubicacion {0} con sesion {1}", new Object[]{dto.getBinCode(), sessionId});
        BinLocationsServiceSoap port = service.getBinLocationsServiceSoap12();
        if (sessionId == null) {
            console.log(Level.SEVERE, "No se recibio un ID de sesion de B1WS valido.");
            return -1L;
        }

        MsgHeader header = new MsgHeader();
        header.setSessionID(sessionId);
        header.setServiceName(BIN_LOCATIONS_SERVICE_WSDL_NAME);

        BinLocation location = new BinLocation();

        location.setWarehouse(dto.getWarehouse());
        location.setSublevel1(dto.getSublevel1());
        location.setSublevel2(dto.getSublevel2());
        location.setBinCode(dto.getBinCode());
        location.setAttribute1(dto.isAttribute1() ? "Sí" : "No");
        location.setAttribute2(dto.getAttribute2());
        location.setAttribute3(dto.getAttribute3());
        location.setAttribute4(dto.getAttribute4());
        location.setAttribute5(dto.getAttribute5());
        location.setAttribute6(dto.isAttribute6() ? "Sí" : "No");
        location.setAttribute7(dto.getAttribute7());
        location.setAttribute8(dto.getAttribute8());
        location.setAttribute9(dto.isAttribute9() ? "Sí" : "No");
        location.setInactive(dto.isInactive() ? "tYES" : "tNO");
        location.setRestrictedItemType("briNone");
        location.setBatchRestrictions("brbNoRestrictions");
        location.setRestrictedTransType("brtNoRestrictions");
        location.setMinimumQty(0.0);
        location.setMaximumQty(0.0);
        location.setIsSystemBin("tNO");
        location.setReceivingBinLocation("tNO");
        location.setExcludeAutoAllocOnIssue("tNO");

        try {
            Add value = new Add();
            value.setBinLocation(location);
            AddResponse resp = port.add(value, header);
            console.log(Level.INFO, "Se creo la ubicacion {0} satisfactoriamente", resp.getBinLocationParams().getAbsEntry());
            return resp.getBinLocationParams().getAbsEntry();
        } catch (Exception e) {
            console.log(Level.SEVERE, "Ocurrio un error al crear la ubicacion usando B1WS. ", e);
            return -1L;
        }
    }

    public boolean editBinLocation(BinLocationsDTO dto) {
        console.log(Level.INFO, "Modificando ubicacion {0} con sesion {1}", new Object[]{dto.getBinCode(), sessionId});
        BinLocationsServiceSoap port = service.getBinLocationsServiceSoap12();
        if (sessionId == null) {
            console.log(Level.SEVERE, "No se recibio un ID de sesion de B1WS valido.");
            return false;
        }

        MsgHeader header = new MsgHeader();
        header.setSessionID(sessionId);
        header.setServiceName(BIN_LOCATIONS_SERVICE_WSDL_NAME);

        BinLocation location = new BinLocation();

        location.setAbsEntry(dto.getAbsEntry().longValue());
        location.setWarehouse(dto.getWarehouse());
        location.setSublevel1(dto.getSublevel1());
        location.setSublevel2(dto.getSublevel2());
        location.setBinCode(dto.getBinCode());
        location.setAttribute1(dto.isAttribute1() ? "Sí" : "No");
        location.setAttribute2(dto.getAttribute2());
        location.setAttribute3(dto.getAttribute3());
        location.setAttribute4(dto.getAttribute4());
        location.setAttribute5(dto.getAttribute5());
        location.setAttribute6(dto.isAttribute6() ? "Sí" : "No");
        location.setAttribute7(dto.getAttribute7());
        location.setAttribute8(dto.getAttribute8());
        location.setAttribute9(dto.isAttribute9() ? "Sí" : "No");
        location.setInactive(dto.isInactive() ? "tYES" : "tNO");
        location.setRestrictedItemType("briNone");
        location.setBatchRestrictions("brbNoRestrictions");
        location.setRestrictedTransType("brtNoRestrictions");
        location.setMinimumQty(0.0);
        location.setMaximumQty(0.0);
        location.setIsSystemBin("tNO");
        location.setReceivingBinLocation("tNO");
        location.setExcludeAutoAllocOnIssue("tNO");

        try {
            Update value = new Update();
            value.setBinLocation(location);
            port.update(value, header);
            console.log(Level.INFO, "Se modifico la ubicacion {0} satisfactoriamente", dto.getAbsEntry());
            return true;
        } catch (Exception e) {
            console.log(Level.SEVERE, "Ocurrio un error al modificar la ubicacion usando B1WS. ", e);
            return false;
        }
    }
}