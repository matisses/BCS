package co.matisses.bcs.b1ws.client.quotations;

import static co.matisses.bcs.b1ws.client.B1WSServiceInfo.QUOTATIONS_SERVICE_WSDL_NAME;
import co.matisses.bcs.b1ws.ws.quotations.Add;
import co.matisses.bcs.b1ws.ws.quotations.AddResponse;
import co.matisses.bcs.b1ws.ws.quotations.Document;
import co.matisses.bcs.b1ws.ws.quotations.MsgHeader;
import co.matisses.bcs.b1ws.ws.quotations.QuotationsService;
import co.matisses.bcs.b1ws.ws.quotations.QuotationsServiceSoap;
import co.matisses.bcs.b1ws.ws.quotations.Update;
import co.matisses.bcs.mbean.BCSGenericMBean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;

/**
 *
 * @author ygil
 */
public class QuotationsServiceConnector {

    @Inject
    private BCSGenericMBean genericMBean;
    private static final Logger console = Logger.getLogger(QuotationsServiceConnector.class.getSimpleName());
    private QuotationsService service;
    private String sessionId;

    public QuotationsServiceConnector() {
    }

    public QuotationsServiceConnector(QuotationsService service, String sessionId) {
        super();
        this.service = service;
        this.sessionId = sessionId;
    }

    public Long createQuotationDocument(QuotationsDocumentDTO document) {
        QuotationsServiceSoap port = service.getQuotationsServiceSoap12();
        if (sessionId == null) {
            console.log(Level.SEVERE, "No se recibio un ID de sesion de B1WS valido.");
            return -1L;
        }

        MsgHeader requestHeader = new MsgHeader();
        requestHeader.setSessionID(sessionId);
        requestHeader.setServiceName(QUOTATIONS_SERVICE_WSDL_NAME);

        Document quotation = new Document();

        quotation.setCardCode(document.getCardCode());
        quotation.setSalesPersonCode(document.getSalesPersonCode());
        quotation.setJournalMemo("Oferta de ventas - " + document.getCardCode());
        quotation.setSeries(20L);
        quotation.setComments("Cotización creada desde 360.");
        quotation.setUOrigen("T");

        Document.DocumentLines lines = new Document.DocumentLines();

        long lineNum = 0;
        for (QuotationsDocumentLinesDTO line : document.getQuotationsDocumentLines()) {
            Document.DocumentLines.DocumentLine linea = new Document.DocumentLines.DocumentLine();

            linea.setItemCode(line.getItemCode());
            linea.setQuantity(line.getQuantity().doubleValue());
            linea.setWarehouseCode(line.getWarehouseCode().replace("DM", ""));
            linea.setLineNum(lineNum);
            if (line.getUnitPrice() != null && line.getUnitPrice().doubleValue() > 0) {
                linea.setUnitPrice(line.getUnitPrice());
            }

            lines.getDocumentLine().add(linea);
            lineNum++;
        }

        quotation.setDocumentLines(lines);

        Add parameters = new Add();
        parameters.setDocument(quotation);

        try {
            console.log(Level.INFO, quotation.toString());
            AddResponse resp = port.add(parameters, requestHeader);
            return resp.getDocumentParams().getDocEntry();
        } catch (Exception e) {
            console.log(Level.SEVERE, "Ocurrio un error al crear la cotizacion.", e);
            return -1L;
        }
    }

    public void editQuotation(QuotationsDocumentDTO document) {
        QuotationsServiceSoap port = service.getQuotationsServiceSoap12();
        if (sessionId == null) {
            console.log(Level.SEVERE, "No se recibio un ID de sesion de B1WS valido.");
        }

        MsgHeader requestHeader = new MsgHeader();
        requestHeader.setSessionID(sessionId);
        requestHeader.setServiceName(QUOTATIONS_SERVICE_WSDL_NAME);

        Document quotation = new Document();

        quotation.setDocEntry(document.getDocEntry());
        quotation.setDocNum(document.getDocEntry());
        quotation.setDocDate(document.getDocDate());
        quotation.setDocDueDate(document.getDocDueDate());
        quotation.setCardCode(document.getCardCode());
        quotation.setCardName(document.getCardName());
        quotation.setAddress(document.getAddress());
        quotation.setJournalMemo("Oferta de ventas - " + document.getCardCode());
        quotation.setComments("Cotización modificada desde 360.");
        quotation.setPaymentGroupCode(document.getPaymentGroupCode());
        quotation.setSalesPersonCode(document.getSalesPersonCode());
        quotation.setContactPersonCode(document.getContactPersonCode());
        quotation.setShipToCode(document.getShipToCode());
        quotation.setDocumentsOwner(document.getDocumentsOwner());
        quotation.setAddress2(document.getAddress2());
        quotation.setPayToCode(document.getPayToCode());
        quotation.setControlAccount("13050501");
        quotation.setExtraMonth(document.getExtraMonth());
        quotation.setExtraDays(document.getExtraDays());
        quotation.setStartFrom("pdt_None");
        quotation.setDownPaymentStatus("so_Open");
        quotation.setApplyCurrentVATRatesForDownPaymentsToDraw("tNO");
        quotation.setDocumentDelivery("ddtNoneSeleted");
        quotation.setUOrigen("T");

        Document.DocumentLines lines = new Document.DocumentLines();

        long linea = 0L;
        for (QuotationsDocumentLinesDTO l : document.getQuotationsDocumentLines()) {
            Document.DocumentLines.DocumentLine line = new Document.DocumentLines.DocumentLine();

            line.setItemCode(l.getItemCode());
            line.setQuantity(l.getQuantity().doubleValue());
            line.setCurrency("COP");
            line.setWarehouseCode(l.getWarehouseCode());
            line.setFactor1(1.0);
            line.setFactor2(1.0);
            line.setFactor3(1.0);
            line.setFactor4(1.0);
            line.setTaxCode(l.getTaxCode());
            line.setInventoryQuantity(l.getQuantity().doubleValue());
            if (l.getLinea() >= 0) {
                line.setLineNum(l.getLinea().longValue());
                linea = l.getLinea();
            } else {
                line.setLineNum(linea);
            }

            lines.getDocumentLine().add(line);
            linea++;
        }

        quotation.setDocumentLines(lines);

        Document.TaxExtension taxExtension = new Document.TaxExtension();

        taxExtension.setStreetS(document.getTaxExtension().getStreetS());
        taxExtension.setBlockS(document.getTaxExtension().getBlockS());
        taxExtension.setBuildingS(document.getTaxExtension().getBuildingS());
        taxExtension.setCityS(document.getTaxExtension().getCityS());
        taxExtension.setCountyS(document.getTaxExtension().getCountyS());
        taxExtension.setStateS(document.getTaxExtension().getStateS());
        taxExtension.setCountryS(document.getTaxExtension().getCountryS());
        taxExtension.setStreetB(document.getTaxExtension().getStreetB());
        taxExtension.setBlockB(document.getTaxExtension().getBlockB());
        taxExtension.setBuildingB(document.getTaxExtension().getBuildingB());
        taxExtension.setCityB(document.getTaxExtension().getCityB());
        taxExtension.setCountyB(document.getTaxExtension().getCountyB());
        taxExtension.setStateB(document.getTaxExtension().getStateB());
        taxExtension.setCountryB(document.getTaxExtension().getCountryB());

        quotation.setTaxExtension(taxExtension);

        Document.AddressExtension addressExtension = new Document.AddressExtension();

        addressExtension.setShipToStreet(document.getAddressExtension().getShipToStreet());
        addressExtension.setShipToBlock(document.getAddressExtension().getShipToBlock());
        addressExtension.setShipToBuilding(document.getAddressExtension().getShipToBuilding());
        addressExtension.setShipToCity(document.getAddressExtension().getShipToCity());
        addressExtension.setShipToCounty(document.getAddressExtension().getShipToCounty());
        addressExtension.setShipToState(document.getAddressExtension().getShipToState());
        addressExtension.setShipToCountry(document.getAddressExtension().getShipToCountry());
        addressExtension.setBillToStreet(document.getAddressExtension().getBillToStreet());
        addressExtension.setBillToBlock(document.getAddressExtension().getBillToBlock());
        addressExtension.setBillToBuilding(document.getAddressExtension().getBillToBuilding());
        addressExtension.setBillToCity(document.getAddressExtension().getBillToCity());
        addressExtension.setBillToCounty(document.getAddressExtension().getBillToCounty());
        addressExtension.setBillToState(document.getAddressExtension().getBillToState());
        addressExtension.setBillToCountry(document.getAddressExtension().getBillToCountry());

        quotation.setAddressExtension(addressExtension);

        try {
            Update update = new Update();
            update.setDocument(quotation);
            port.update(update, requestHeader);
            console.log(Level.INFO, "Se actualizo la cotizacion {0} satisfactoriamente", document.getDocEntry());
        } catch (Exception e) {
            console.log(Level.SEVERE, "Ocurrio un error al modificar la cotizacion. ", e);
        }
    }
}
