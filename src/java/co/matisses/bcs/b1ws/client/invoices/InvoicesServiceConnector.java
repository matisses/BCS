package co.matisses.bcs.b1ws.client.invoices;

import co.matisses.b1ws.dto.SalesDocumentDTO;
import co.matisses.b1ws.dto.SalesDocumentLineBinAllocationDTO;
import co.matisses.b1ws.dto.SalesDocumentLineDTO;
import co.matisses.bcs.b1ws.client.B1WSServiceInfo;
import co.matisses.bcs.b1ws.ws.invoices.Add;
import co.matisses.bcs.b1ws.ws.invoices.AddResponse;
import co.matisses.bcs.b1ws.ws.invoices.Document;
import co.matisses.bcs.b1ws.ws.invoices.InvoicesService;
import co.matisses.bcs.b1ws.ws.invoices.InvoicesServiceSoap;
import co.matisses.bcs.b1ws.ws.invoices.MsgHeader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dbotero
 */
public class InvoicesServiceConnector extends B1WSServiceInfo {

    private static final Logger CONSOLE = Logger.getLogger(InvoicesServiceConnector.class.getSimpleName());
    private InvoicesService invoicesService = null;
    private String sessionId = null;

    public InvoicesServiceConnector(InvoicesService invoicesService, String sessionId) {
        super();
        this.sessionId = sessionId;
        this.invoicesService = invoicesService;
    }

    private Document createBasicDocument(SalesDocumentDTO docDto) {
        Document document = new Document();
        document.setCardCode(docDto.getCardCode());
        if (docDto.getComments() != null && docDto.getComments().length() > 254) {
            docDto.setComments(docDto.getComments().substring(0, 254));
        }
        document.setComments(docDto.getComments());

        //assign sales people and commissions
        if (docDto.getSalesEmployees() != null && !docDto.getSalesEmployees().isEmpty()) {
            for (int i = 0; i < docDto.getSalesEmployees().size(); i++) {
                if (docDto.getSalesEmployees().size() == 3) {
                    switch (i) {
                        case 0:
                            document.setUVendedor1(docDto.getSalesEmployees().get(i).getName());
                            document.setUComVend1("0.34");
                            break;
                        case 1:
                            document.setUVendedor2(docDto.getSalesEmployees().get(i).getName());
                            document.setUComVend2("0.33");
                            break;
                        case 2:
                            document.setUVendedor3(docDto.getSalesEmployees().get(i).getName());
                            document.setUComVend3("0.33");
                            break;
                        default:
                            break;
                    }
                } else {
                    double comision = (1.0 / docDto.getSalesEmployees().size());
                    switch (i) {
                        case 0:
                            document.setUVendedor1(docDto.getSalesEmployees().get(i).getName());
                            document.setUComVend1(String.valueOf(comision));
                            break;
                        case 1:
                            document.setUVendedor2(docDto.getSalesEmployees().get(i).getName());
                            document.setUComVend2(String.valueOf(comision));
                            break;
                        case 2:
                            document.setUVendedor3(docDto.getSalesEmployees().get(i).getName());
                            document.setUComVend3(String.valueOf(comision));
                            break;
                        case 3:
                            document.setUVendedor4(docDto.getSalesEmployees().get(i).getName());
                            document.setUComVend4(String.valueOf(comision));
                            break;
                        case 4:
                            document.setUVendedor5(docDto.getSalesEmployees().get(i).getName());
                            document.setUComVend5(String.valueOf(comision));
                            break;
                        default:
                            break;
                    }
                }
            }
        }

//        for (int i = 0; i < 5; i++) {
//            int salesEmployees = docDto.getSalesEmployees().size();
//            if (i < salesEmployees) {
//                String slpName = docDto.getSalesEmployees().get(i).getName();
//                switch (i) {
//                    case 0:
//                        document.setUVendedor1(slpName);
//
//                        document.setUComVend1(String.valueOf(SalesDocumentDTO.calculateCommission(1, salesEmployees)));
//                        break;
//                    case 1:
//                        document.setUVendedor2(slpName);
//                        document.setUComVend2(String.valueOf(SalesDocumentDTO.calculateCommission(2, salesEmployees)));
//                        break;
//                    case 2:
//                        document.setUVendedor3(slpName);
//                        document.setUComVend3(String.valueOf(SalesDocumentDTO.calculateCommission(3, salesEmployees)));
//                        break;
//                    case 3:
//                        document.setUVendedor4(slpName);
//                        document.setUComVend4(String.valueOf(SalesDocumentDTO.calculateCommission(4, salesEmployees)));
//                        break;
//                    case 4:
//                        document.setUVendedor5(slpName);
//                        document.setUComVend5(String.valueOf(SalesDocumentDTO.calculateCommission(5, salesEmployees)));
//                        break;
//                    default:
//                        break;
//                }
//            }
//        }
        document.setUOrigen(docDto.getSource());
        document.setSalesPersonCode(docDto.getSalesPerson() == null || docDto.getSalesPerson() == 0 ? 98L : docDto.getSalesPerson());
        document.setSeries(Long.parseLong(docDto.getSeriesCode()));
        document.setPaymentGroupCode(Long.parseLong(docDto.getPaymentGroupCode()));
        document.setUWUID(docDto.getWuid());
        document.setPOSCashierNumber(docDto.getPosShiftId());
        document.setNumAtCard(docDto.getRefDocnum());
        document.setUPrestashopID(docDto.getPrestashopOrderID());
        document.setUDiseno(docDto.getDesignerCode());

        Document.DocumentLines documentLines = new Document.DocumentLines();
        long lineNum = 0;
        for (SalesDocumentLineDTO docLine : docDto.getDocumentLines()) {
            Document.DocumentLines.DocumentLine line = new Document.DocumentLines.DocumentLine();
            line.setItemCode(docLine.getItemCode());
            line.setDiscountPercent(docLine.getDiscountPercent());
            line.setQuantity(docLine.getQuantity().doubleValue());
            line.setWarehouseCode(docLine.getWhsCode());
            //ventas
            line.setCostingCode2(docDto.getSalesCostingCode());
            //logistica
            line.setCostingCode3(docDto.getLogisticsCostingCode());
            //ruta
            line.setCostingCode4(docDto.getRouteCostingCode());
            line.setProjectCode(docDto.getProjectCode());
            line.setUEstadoP(docLine.getShippingStatus());
            line.setLineNum(lineNum);
            if (docLine.getBaseLine() != null && docLine.getBaseEntry() != null && docLine.getBaseEntry() != 0) {
                line.setBaseLine(docLine.getBaseLine());
                line.setBaseEntry(docLine.getBaseEntry());
                line.setBaseType(23L);
            }
            Document.DocumentLines.DocumentLine.DocumentLinesBinAllocations binAllocations = new Document.DocumentLines.DocumentLine.DocumentLinesBinAllocations();

            for (SalesDocumentLineBinAllocationDTO binAllocDto : docLine.getBinAllocations()) {
                Document.DocumentLines.DocumentLine.DocumentLinesBinAllocations.DocumentLinesBinAllocation binAllocation = new Document.DocumentLines.DocumentLine.DocumentLinesBinAllocations.DocumentLinesBinAllocation();
                binAllocation.setBinAbsEntry(Long.valueOf(binAllocDto.getBinAbsEntry()));
                binAllocation.setBaseLineNumber(lineNum);
                binAllocation.setQuantity(binAllocDto.getQuantity().doubleValue());

                binAllocations.getDocumentLinesBinAllocation().add(binAllocation);
            }
            line.setDocumentLinesBinAllocations(binAllocations);

            documentLines.getDocumentLine().add(line);
            lineNum++;
        }
        document.setDocumentLines(documentLines);
        return document;
    }

    public Long createInvoice(SalesDocumentDTO docDto) throws InvoiceServiceException {
        if (docDto != null) {
            CONSOLE.log(Level.INFO, docDto.toString());
        }
        InvoicesServiceSoap port = invoicesService.getInvoicesServiceSoap12();
        if (sessionId == null) {
            throw new InvoiceServiceException("No se recibio un ID de sesion de B1WS valido. ");
        }

        //Configura el encabezado de la solicitud
        MsgHeader requestHeader = new MsgHeader();
        requestHeader.setSessionID(sessionId);
        requestHeader.setServiceName(INVOICES_SERVICE_WSDL_NAME);

        Document document = createBasicDocument(docDto);

        //Configura el cuerpo de la solicitud
        Add parameters = new Add();
        parameters.setDocument(document);

        Long invoiceNumber = null;
        try {
            AddResponse resp = port.add(parameters, requestHeader);
            invoiceNumber = resp.getDocumentParams().getDocEntry();
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al crear la factura. ", e);
            throw new InvoiceServiceException("No fue posible crear la factura. " + e.getMessage());
        }

        return invoiceNumber;
    }

    //TODO: Crear factura por servicio de transporte
    public Long createServiceInvoice(SalesDocumentDTO docDto) throws InvoiceServiceException {
        if (docDto != null) {
            CONSOLE.log(Level.INFO, docDto.toString());
        }
        InvoicesServiceSoap port = invoicesService.getInvoicesServiceSoap12();
        if (sessionId == null) {
            throw new InvoiceServiceException("No se recibio un ID de sesion de B1WS valido. ");
        }

        //TODO: Configurar el encabezado de la solicitud
        MsgHeader requestHeader = new MsgHeader();
        requestHeader.setSessionID(sessionId);
        requestHeader.setServiceName(INVOICES_SERVICE_WSDL_NAME);

        Document document = new Document();
        document.setCardCode(docDto.getCardCode());
        if (docDto.getComments() != null && docDto.getComments().length() > 254) {
            docDto.setComments(docDto.getComments().substring(0, 254));
        }
        document.setDocType("S");//Tipo servicio
        document.setComments(docDto.getComments());
        document.setUOrigen(docDto.getSource());
        document.setUNumFact(docDto.getuNumFact());
        document.setSalesPersonCode(98L);//Vendedor Web
        document.setSeries(Long.parseLong(docDto.getSeriesCode()));
        document.setPaymentGroupCode(Long.parseLong(docDto.getPaymentGroupCode()));
        document.setUWUID(docDto.getWuid());
        document.setUVendedor1("Vendedor Web");
        document.setUComVend1("1.0");//Asignar comision

        Document.DocumentLines documentLines = new Document.DocumentLines();

        for (SalesDocumentLineDTO docLine : docDto.getDocumentLines()) {
            Document.DocumentLines.DocumentLine line = new Document.DocumentLines.DocumentLine();

            line.setItemDescription(docLine.getDscription());
            line.setAccountCode(docLine.getAcctCode().toString());
            line.setTaxCode(docLine.getTaxCode());
            line.setLineTotal(docLine.getPrice().doubleValue());
            line.setPrice(docLine.getPrice().doubleValue());

            documentLines.getDocumentLine().add(line);
        }
        document.setDocumentLines(documentLines);

        //TODO: Configuarar el cuerpo de la solicitud
        Add parameters = new Add();
        parameters.setDocument(document);

        Long invoiceNumber = null;
        try {
            AddResponse resp = port.add(parameters, requestHeader);
            invoiceNumber = resp.getDocumentParams().getDocEntry();
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al crear la factura por servicio. ", e);
            throw new InvoiceServiceException("No fue posible crear la factura por servicio. " + e.getMessage());
        }
        return invoiceNumber;
    }
}
