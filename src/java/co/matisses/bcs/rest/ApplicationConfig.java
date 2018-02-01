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
        resources.add(co.matisses.bcs.b1ws.client.creditnotes.CreditNotesREST.class);
        resources.add(co.matisses.bcs.b1ws.client.employeesinfo.EmployeesInfoREST.class);
        resources.add(co.matisses.bcs.b1ws.client.goodsissue.GoodsIssueREST.class);
        resources.add(co.matisses.bcs.b1ws.client.goodsreceipt.GoodsReceiptREST.class);
        resources.add(co.matisses.bcs.b1ws.client.invoices.InvoicesREST.class);
        resources.add(co.matisses.bcs.b1ws.client.items.ItemsREST.class);
        resources.add(co.matisses.bcs.b1ws.client.journalentries.JournalEntryREST.class);
        resources.add(co.matisses.bcs.b1ws.client.orders.OrderREST.class);
        resources.add(co.matisses.bcs.b1ws.client.payments.IncomingPaymentREST.class);
        resources.add(co.matisses.bcs.b1ws.client.payments.IncomingPaymentServiceREST.class);
        resources.add(co.matisses.bcs.b1ws.client.quotations.QuotationsREST.class);
        resources.add(co.matisses.bcs.b1ws.client.stocktransfer.StockTransferREST.class);
        resources.add(co.matisses.bcs.b1ws.client.warehousesublevelcodes.WarehouseSublevelCodesREST.class);
        resources.add(co.matisses.bcs.google.URLShortener.class);
        resources.add(co.matisses.bcs.mailchimp.MailChimpREST.class);
        resources.add(co.matisses.bcs.mbean.BCSApplicationMBean.class);
        resources.add(co.matisses.bcs.mbean.ImagenProductoMBean.class);
        resources.add(co.matisses.bcs.rest.AlarmaREST.class);
        resources.add(co.matisses.bcs.rest.CaptacionClientesREST.class);
        resources.add(co.matisses.bcs.rest.CoordinadoraWSREST.class);
        resources.add(co.matisses.bcs.rest.CotizacionWEBREST.class);
        resources.add(co.matisses.bcs.rest.Encode128REST.class);
        resources.add(co.matisses.bcs.rest.FiltrosProductoREST.class);
        resources.add(co.matisses.bcs.rest.JWTTokenValidatorREST.class);
        resources.add(co.matisses.bcs.rest.ListaRegalosSessionValidatorREST.class);
        resources.add(co.matisses.bcs.rest.MercadoLibreREST.class);
        resources.add(co.matisses.bcs.rest.NotificacionesListaRegalosREST.class);
        resources.add(co.matisses.bcs.rest.PlaceToPayREST.class);
        resources.add(co.matisses.bcs.rest.PrintReportREST.class);
        resources.add(co.matisses.bcs.rest.ProcesarDocumentoREST.class);
        resources.add(co.matisses.bcs.rest.ProcesoPagosREST.class);
        resources.add(co.matisses.bcs.rest.ProgramacionDescuentosREST.class);
        resources.add(co.matisses.bcs.rest.SMSServiceREST.class);
        resources.add(co.matisses.bcs.rest.SendHtmlEmailREST.class);
        resources.add(co.matisses.bcs.rest.ShippingMethodsREST.class);
        resources.add(co.matisses.bcs.rest.ShoppingCartValidatorREST.class);
        resources.add(co.matisses.bcs.rest.SondaItemsREST.class);
        resources.add(co.matisses.bcs.rest.ValidarInventarioCompraWebREST.class);
        resources.add(co.matisses.bcs.rest.ZebraPrintREST.class);
        resources.add(co.matisses.bcs.sync.ItemDataBrandService.class);
        resources.add(co.matisses.bcs.sync.ItemDataColorService.class);
        resources.add(co.matisses.bcs.sync.ItemDataMaterialService.class);
        resources.add(co.matisses.bcs.sync.ItemDataService.class);
    }
}
