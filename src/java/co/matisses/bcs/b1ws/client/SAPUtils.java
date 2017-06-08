package co.matisses.bcs.b1ws.client;

import co.matisses.bcs.b1ws.client.businesspartners.BusinessPartnersServiceConnector;
import co.matisses.bcs.b1ws.ws.businessparters.BusinessPartner;
import co.matisses.bcs.b1ws.ws.businessparters.BusinessPartnersService;

/**
 *
 * @author dbotero
 */
public class SAPUtils { 
    public boolean validarSesionActiva(String idSesion) {
        BusinessPartnersService service = new BusinessPartnersService();
        BusinessPartnersServiceConnector bpsc = new BusinessPartnersServiceConnector(service, idSesion);
        try {
            BusinessPartner bp = bpsc.findBusinessPartner("1CL");
        } catch (Exception e) {
            if(e.getMessage().contains("invalid session")){
                return false;
            }
        }
        return true;
    }
}
