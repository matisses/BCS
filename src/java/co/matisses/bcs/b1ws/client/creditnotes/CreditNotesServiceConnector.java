package co.matisses.bcs.b1ws.client.creditnotes;

import co.matisses.b1ws.dto.SalesDocumentDTO;
import co.matisses.b1ws.dto.SalesDocumentLineBinAllocationDTO;
import co.matisses.b1ws.dto.SalesDocumentLineDTO;
import co.matisses.bcs.b1ws.client.B1WSServiceInfo;
import co.matisses.bcs.b1ws.client.payments.ConstantTypes;
import co.matisses.bcs.b1ws.ws.creditnotes.Add;
import co.matisses.bcs.b1ws.ws.creditnotes.AddResponse;
import co.matisses.bcs.b1ws.ws.creditnotes.CreditNotesService;
import co.matisses.bcs.b1ws.ws.creditnotes.CreditNotesServiceSoap;
import co.matisses.bcs.b1ws.ws.creditnotes.Document;
import co.matisses.bcs.b1ws.ws.creditnotes.MsgHeader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dbotero
 */
public class CreditNotesServiceConnector extends B1WSServiceInfo {

    private CreditNotesService creditNotesService = null;
    private String sessionId = null;

    public CreditNotesServiceConnector(CreditNotesService creditNotesService, String sessionId) {
        super();
        this.sessionId = sessionId;
        this.creditNotesService = creditNotesService;
    }

    private Document createBasicDocument(SalesDocumentDTO docDto) {
        Document document = new Document();

        document.setCardCode(docDto.getCardCode());
        if (docDto.getComments() != null && docDto.getComments().length() > 254) {
            docDto.setComments(docDto.getComments().substring(0, 254));
        }
        document.setComments(docDto.getComments());
        document.setUTipoNota(docDto.getCreditNoteType());

        //assign sales people and commissions
        for (int i = 0; i < 5; i++) {
            int salesEmployees = docDto.getSalesEmployees().size();
            if (i < salesEmployees) {
                String slpName = docDto.getSalesEmployees().get(i).getName();
                switch (i) {
                    case 0:
                        document.setUVendedor1(slpName);
                        if (docDto.getSalesEmployees().get(i).getComision() != null && docDto.getSalesEmployees().get(i).getComision() != 0) {
                            document.setUComVend1(String.valueOf(docDto.getSalesEmployees().get(i).getComision()));
                        } else {
                            document.setUComVend1(String.valueOf(SalesDocumentDTO.calculateCommission(1, salesEmployees)));
                        }
                        break;
                    case 1:
                        document.setUVendedor2(slpName);
                        if (docDto.getSalesEmployees().get(i).getComision() != null && docDto.getSalesEmployees().get(i).getComision() != 0) {
                            document.setUComVend2(String.valueOf(docDto.getSalesEmployees().get(i).getComision()));
                        } else {
                            document.setUComVend2(String.valueOf(SalesDocumentDTO.calculateCommission(2, salesEmployees)));
                        }
                        break;
                    case 2:
                        document.setUVendedor3(slpName);
                        if (docDto.getSalesEmployees().get(i).getComision() != null && docDto.getSalesEmployees().get(i).getComision() != 0) {
                            document.setUComVend3(String.valueOf(docDto.getSalesEmployees().get(i).getComision()));
                        } else {
                            document.setUComVend3(String.valueOf(SalesDocumentDTO.calculateCommission(3, salesEmployees)));
                        }
                        break;
                    case 3:
                        document.setUVendedor4(slpName);
                        if (docDto.getSalesEmployees().get(i).getComision() != null && docDto.getSalesEmployees().get(i).getComision() != 0) {
                            document.setUComVend4(String.valueOf(docDto.getSalesEmployees().get(i).getComision()));
                        } else {
                            document.setUComVend4(String.valueOf(SalesDocumentDTO.calculateCommission(4, salesEmployees)));
                        }
                        break;
                    case 4:
                        document.setUVendedor5(slpName);
                        if (docDto.getSalesEmployees().get(i).getComision() != null && docDto.getSalesEmployees().get(i).getComision() != 0) {
                            document.setUComVend5(String.valueOf(docDto.getSalesEmployees().get(i).getComision()));
                        } else {
                            document.setUComVend5(String.valueOf(SalesDocumentDTO.calculateCommission(5, salesEmployees)));
                        }
                        break;
                    default:
                        break;
                }
            }
        }

        document.setNumAtCard(docDto.getRefDocnum());
        document.setUOrigen(docDto.getSource());
        document.setSalesPersonCode((docDto.getSalesPerson() != null && docDto.getSalesPerson() != 0) ? docDto.getSalesPerson() : 98L);
        document.setSeries(Long.parseLong(docDto.getSeriesCode()));
        document.setPaymentGroupCode(Long.parseLong(docDto.getPaymentGroupCode()));
        document.setUWUID(docDto.getWuid());
        document.setPOSCashierNumber(docDto.getPosShiftId());

        Document.DocumentLines documentLines = new Document.DocumentLines();

        long lineNum = 0;
        for (SalesDocumentLineDTO docLine : docDto.getDocumentLines()) {
            Document.DocumentLines.DocumentLine line = new Document.DocumentLines.DocumentLine();
            line.setItemCode(docLine.getItemCode());
            line.setQuantity(docLine.getQuantity().doubleValue());
            line.setWarehouseCode(docLine.getWhsCode());
            if (docLine.getGrossBuyPrice() != null && docLine.getGrossBuyPrice() != 0) {
                line.setGrossBuyPrice(docLine.getGrossBuyPrice());
            }
            //ventas
            line.setCostingCode2(docDto.getSalesCostingCode());
            //logistica
            line.setCostingCode3(docDto.getLogisticsCostingCode());
            //ruta
            line.setCostingCode4(docDto.getRouteCostingCode());
            line.setProjectCode(PROYECTO_POS);
            if (docDto.getShippingStatus() == null || docDto.getShippingStatus().trim().isEmpty()) {
                line.setUEstadoP(ESTADO_PRODUCTO_DESPACHADO);
            } else {
                line.setUEstadoP(docDto.getShippingStatus());
            }
            line.setLineNum(lineNum);

            if (!docDto.getCreditNoteType().equals("D")) {
                line.setBaseEntry(docDto.getDocEntry());
                line.setBaseLine(docLine.getLineNum().longValue());
                line.setBaseType(Long.valueOf(ConstantTypes.DocType.INVOICE.value));
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

    public Long createCreditNote(SalesDocumentDTO docDto) throws CreditNotesServiceException {
        CreditNotesServiceSoap port = creditNotesService.getCreditNotesServiceSoap12();
        if (sessionId == null) {
            throw new CreditNotesServiceException("No se recibio un ID de sesion de B1WS valido. ");
        }

        //Configura el encabezado de la solicitud
        MsgHeader requestHeader = new MsgHeader();
        requestHeader.setSessionID(sessionId);
        requestHeader.setServiceName(CREDIT_NOTES_SERVICE_WSDL_NAME);

        Document document = createBasicDocument(docDto);

        Add addOperation = new Add();
        addOperation.setDocument(document);

        Long creditNoteNumber = null;
        try {
            AddResponse resp = port.add(addOperation, requestHeader);
            creditNoteNumber = resp.getDocumentParams().getDocEntry();
        } catch (Exception e) {
            Logger.getLogger(CreditNotesServiceConnector.class.getName()).log(Level.SEVERE, "Ocurrio un error al crear la nota credito. ", e);
            throw new CreditNotesServiceException("No fue posible crear la nota credito. " + e.getMessage());
        }
        return creditNoteNumber;
    }
}
