package co.matisses.bcs.rest;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 *
 * @author dbotero
 */
@ApplicationPath("res")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method. It is automatically
     * populated with all resources defined in the project. If required, comment
     * out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(co.matisses.bcs.b1ws.client.binlocationattributes.BinLocationAttributesREST.class);
        resources.add(co.matisses.bcs.b1ws.client.binlocations.BinLocationsREST.class);
        resources.add(co.matisses.bcs.b1ws.client.businesspartners.BusinessPartnerREST.class);
        resources.add(co.matisses.bcs.b1ws.client.journalentries.JournalEntryREST.class);
        resources.add(co.matisses.bcs.b1ws.client.payments.IncomingPaymentServiceREST.class);
        resources.add(co.matisses.bcs.b1ws.client.warehousesublevelcodes.WarehouseSublevelCodesREST.class);
        resources.add(co.matisses.bcs.google.URLShortener.class);
        resources.add(co.matisses.bcs.mbean.BCSApplicationMBean.class);
        resources.add(co.matisses.bcs.mbean.ImagenProductoMBean.class);
        resources.add(co.matisses.bcs.rest.CaptacionClientesREST.class);
        resources.add(co.matisses.bcs.rest.MercadoLibreREST.class);
        resources.add(co.matisses.bcs.rest.ProcesarDocumentoREST.class);
        resources.add(co.matisses.bcs.rest.ProgramacionDescuentosREST.class);
        resources.add(co.matisses.bcs.rest.SMSServiceREST.class);
        resources.add(co.matisses.bcs.rest.SendHtmlEmailREST.class);
        resources.add(co.matisses.bcs.rest.ZebraPrintREST.class);
    }
}
