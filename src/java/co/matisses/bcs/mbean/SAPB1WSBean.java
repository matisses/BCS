package co.matisses.bcs.mbean;

import co.matisses.bcs.b1ws.client.payments.IncomingPaymentServiceConnector;
import co.matisses.bcs.b1ws.client.journalentries.JournalEntriesServiceConnector;
import static co.matisses.bcs.b1ws.client.B1WSServiceInfo.GOODS_RECEIPT_SERVICE_WSDL_NAME;
import static co.matisses.bcs.b1ws.client.B1WSServiceInfo.INCOMING_PAYMENT_SERVICE_WSDL_NAME;
import static co.matisses.bcs.b1ws.client.B1WSServiceInfo.JOURNAL_ENTRIES_SERVICE_WSDL_NAME;
import static co.matisses.bcs.b1ws.client.B1WSServiceInfo.RUTA_WSDL;
import co.matisses.bcs.b1ws.client.invoices.InvoicesServiceConnector;
import co.matisses.bcs.b1ws.client.B1WSServiceInfo;
import static co.matisses.bcs.b1ws.client.B1WSServiceInfo.CREDIT_NOTES_SERVICE_WSDL_NAME;
import static co.matisses.bcs.b1ws.client.B1WSServiceInfo.GOODS_ISSUE_SERVICE_WSDL_NAME;
import co.matisses.bcs.b1ws.client.SAPSessionManager;
import co.matisses.bcs.b1ws.client.binlocations.BinLocationsServiceConnector;
import co.matisses.bcs.b1ws.client.businesspartners.BusinessPartnersServiceConnector;
import co.matisses.bcs.b1ws.client.creditnotes.CreditNotesServiceConnector;
import co.matisses.bcs.b1ws.client.goodsissue.GoodsIssueServiceConnector;
import co.matisses.bcs.b1ws.client.goodsreceipt.GoodsReceiptServiceConnector;
import co.matisses.bcs.b1ws.client.journalentries.JournalEntriesServiceConnector;
import co.matisses.bcs.b1ws.client.orders.OrdersServiceConnector;
import co.matisses.bcs.b1ws.ws.binlocations.BinLocationsService;
import co.matisses.bcs.b1ws.ws.businessparters.BusinessPartnersService;
import co.matisses.bcs.b1ws.ws.creditnotes.CreditNotesService;
import co.matisses.bcs.b1ws.ws.goodsissue.InventoryGenExitService;
import co.matisses.bcs.b1ws.ws.goodsreceipt.InventoryGenEntryService;
import co.matisses.bcs.b1ws.ws.incomingpayment.IncomingPaymentsService;
import co.matisses.bcs.b1ws.ws.invoices.InvoicesService;
import co.matisses.bcs.b1ws.ws.journalentries.JournalEntriesService;
import co.matisses.bcs.b1ws.ws.orders.OrdersService;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import static co.matisses.bcs.b1ws.client.B1WSServiceInfo.BIN_LOCATIONS_SERVICE_WSDL_NAME;
import static co.matisses.bcs.b1ws.client.B1WSServiceInfo.BIN_LOCATION_ATTRIBUTES_SERVICE_WSDL_NAME;
import static co.matisses.bcs.b1ws.client.B1WSServiceInfo.WAREHOUSE_SUBLEVEL_CODES_SERVICE_WSDL_NAME;
import co.matisses.bcs.b1ws.client.binlocationattributes.BinLocationAttributesServiceConnector;
import co.matisses.bcs.b1ws.client.warehousesublevelcodes.WarehouseSublevelCodesServiceConnector;
import co.matisses.bcs.b1ws.ws.binlocationattributes.BinLocationAttributesService;
import co.matisses.bcs.b1ws.ws.warehousesublevelcodes.WarehouseSublevelCodesService;

/**
 *
 * @author dbotero
 */
@ApplicationScoped
@Named("SAPB1WSBean")
public class SAPB1WSBean {

    private static final Logger log = Logger.getLogger(SAPB1WSBean.class.getSimpleName());
    private SAPSessionManager sapSessionManager = new SAPSessionManager();
    private BusinessPartnersService businessPartnerService = null;
    private OrdersService ordersService = null;
    private InventoryGenEntryService inventoryGenEntryService = null;
    private JournalEntriesService journalEntriesService = null;
    private IncomingPaymentsService incomingPaymentService = null;
    private InvoicesService invoicesService = null;
    private CreditNotesService creditNotesService = null;
    private InventoryGenExitService inventoryGenExitService = null;
    private BinLocationsService binLocationsService = null;
    private BinLocationAttributesService binLocationAttributesService = null;
    private WarehouseSublevelCodesService warehouseSublevelCodesService = null;

    @PostConstruct
    public void initialize() {
        log.info("Iniciando instancias de servicios de SAPB1WS");
        initializeServices();
    }

    private void initializeServices() {
        try {
            businessPartnerService = new BusinessPartnersService(new URL(String.format(B1WSServiceInfo.RUTA_WSDL, B1WSServiceInfo.BUSINESS_PARTNERS_SERVICE_WSDL_NAME)));
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al inicializar el servicio de clientes. ", e);
        }

        try {
            ordersService = new OrdersService(new URL(String.format(B1WSServiceInfo.RUTA_WSDL, B1WSServiceInfo.ORDER_SERVICE_WSDL_NAME)));
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al inicializar el servicio de ordenes de venta. ", e);
        }

        try {
            inventoryGenEntryService = new InventoryGenEntryService(new URL(String.format(RUTA_WSDL, GOODS_RECEIPT_SERVICE_WSDL_NAME)));
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al inicializar el servicio de entradas. ", e);
        }

        try {
            journalEntriesService = new JournalEntriesService(new URL(String.format(RUTA_WSDL, JOURNAL_ENTRIES_SERVICE_WSDL_NAME)));
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al inicializar el servicio de asientos contables. ", e);
        }

        try {
            incomingPaymentService = new IncomingPaymentsService(new URL(String.format(RUTA_WSDL, INCOMING_PAYMENT_SERVICE_WSDL_NAME)));
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al inicializar el servicio de pagos. ", e);
        }

        try {
            invoicesService = new InvoicesService(new URL(String.format(B1WSServiceInfo.RUTA_WSDL, B1WSServiceInfo.INVOICES_SERVICE_WSDL_NAME)));
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al inicializar el servicio de ordenes de venta. ", e);
        }

        try {
            creditNotesService = new CreditNotesService(new URL(String.format(RUTA_WSDL, CREDIT_NOTES_SERVICE_WSDL_NAME)));
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al inicializar el servicio de notas credito. ", e);
        }

        try {
            inventoryGenExitService = new InventoryGenExitService(new URL(String.format(RUTA_WSDL, GOODS_ISSUE_SERVICE_WSDL_NAME)));
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al inicializar el servicio de salidas de mercancia. ", e);
        }

        try {
            binLocationsService = new BinLocationsService(new URL(String.format(RUTA_WSDL, BIN_LOCATIONS_SERVICE_WSDL_NAME)));
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al inicializar el servicio de ubicaciones. ", e);
        }

        try {
            binLocationAttributesService = new BinLocationAttributesService(new URL(String.format(RUTA_WSDL, BIN_LOCATION_ATTRIBUTES_SERVICE_WSDL_NAME)));
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al inicializar el servicio de atributos para ubicaciones. ", e);
        }

        try {
            warehouseSublevelCodesService = new WarehouseSublevelCodesService(new URL(String.format(RUTA_WSDL, WAREHOUSE_SUBLEVEL_CODES_SERVICE_WSDL_NAME)));
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al inicializar el servicio de propiedades ubicaciones. ", e);
        }
    }

    public BusinessPartnersServiceConnector getBusinessPartnersServiceConnectorInstance(String sessionId) {
        return new BusinessPartnersServiceConnector(businessPartnerService, sessionId);
    }

    public OrdersServiceConnector getOrderServiceConnectorInstance(String sessionId) {
        return new OrdersServiceConnector(ordersService, sessionId);
    }

    public GoodsReceiptServiceConnector getGoodsReceiptServiceConnectorInstance(String sessionId) {
        return new GoodsReceiptServiceConnector(inventoryGenEntryService, sessionId);
    }

    public JournalEntriesServiceConnector getJournalEntriesServiceConnectorInstance(String sessionId) {
        return new JournalEntriesServiceConnector(journalEntriesService, sessionId);
    }

    public IncomingPaymentServiceConnector getIncomingPaymentServiceConnectorInstance(String sessionId) {
        return new IncomingPaymentServiceConnector(incomingPaymentService, sessionId);
    }

    public InvoicesServiceConnector getInvoicesServiceConnectorInstance(String sessionId) {
        return new InvoicesServiceConnector(invoicesService, sessionId);
    }

    public CreditNotesServiceConnector getCreditNotesServiceConnectorInstance(String sessionId) {
        return new CreditNotesServiceConnector(creditNotesService, sessionId);
    }

    public GoodsIssueServiceConnector getGoodsIssueServiceConnectorInstance(String sessionId) {
        return new GoodsIssueServiceConnector(inventoryGenExitService, sessionId);
    }

    public BinLocationsServiceConnector getBinLocationsServiceConnectorInstance(String sessionId) {
        return new BinLocationsServiceConnector(binLocationsService, sessionId);
    }

    public BinLocationAttributesServiceConnector getBinLocationAttributesServiceConnectorInstance(String sessionId) {
        return new BinLocationAttributesServiceConnector(binLocationAttributesService, sessionId);
    }

    public WarehouseSublevelCodesServiceConnector getWarehouseSublevelCodesServiceConnectorInstance(String sessionId) {
        return new WarehouseSublevelCodesServiceConnector(warehouseSublevelCodesService, sessionId);
    }

    public String obtenerSesionSAP() {
        try {
            return sapSessionManager.login();
        } catch (Exception e) {
            log.log(Level.SEVERE, "No fue posible registrar una nueva sesion en el DI Server. ", e);
            return null;
        }
    }

    public void cerrarSesionSAP(String idSesion) {
        try {
            sapSessionManager.logout(idSesion);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al finalizar la sesion en el DI Server. ", e);
        }
    }
}
