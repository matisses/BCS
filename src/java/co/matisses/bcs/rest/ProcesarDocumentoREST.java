package co.matisses.bcs.rest;

import co.matisses.b1ws.dto.SalesDocumentDTO;
import co.matisses.b1ws.dto.SalesDocumentLineBinAllocationDTO;
import co.matisses.b1ws.dto.SalesDocumentLineDTO;
import co.matisses.b1ws.dto.SalesEmployeeDTO;
import co.matisses.bcs.b1ws.client.creditnotes.CreditNotesServiceConnector;
import co.matisses.bcs.b1ws.client.creditnotes.CreditNotesServiceException;
import co.matisses.bcs.b1ws.client.goodsissue.GoodsIssueDTO;
import co.matisses.bcs.b1ws.client.goodsissue.GoodsIssueDetailDTO;
import co.matisses.bcs.b1ws.client.goodsissue.GoodsIssueLocationsDTO;
import co.matisses.bcs.b1ws.client.goodsissue.GoodsIssueServiceConnector;
import co.matisses.bcs.b1ws.client.goodsissue.GoodsIssueServiceException;
import co.matisses.bcs.b1ws.client.journalentries.JournalEntriesServiceConnector;
import co.matisses.bcs.b1ws.client.journalentries.JournalEntryDTO;
import co.matisses.bcs.b1ws.client.journalentries.JournalEntryLineDTO;
import co.matisses.bcs.b1ws.client.journalentries.JournalEntryServiceException;
import co.matisses.bcs.b1ws.client.payments.IncomingPaymentServiceConnector;
import co.matisses.bcs.b1ws.client.goodsreceipt.GoodsReceiptDTO;
import co.matisses.bcs.b1ws.client.goodsreceipt.GoodsReceiptDetailDTO;
import co.matisses.bcs.b1ws.client.goodsreceipt.GoodsReceiptServiceConnector;
import co.matisses.bcs.b1ws.client.goodsreceipt.GoodsReceiptServiceException;
import co.matisses.bcs.b1ws.client.orders.OrderDTO;
import co.matisses.bcs.b1ws.client.orders.OrderDetailDTO;
import co.matisses.bcs.b1ws.client.orders.OrderServiceException;
import co.matisses.bcs.b1ws.client.orders.OrdersServiceConnector;
import co.matisses.bcs.b1ws.client.payments.IncomingPaymentServiceException;
import co.matisses.bcs.dto.MailMessageDTO;
import co.matisses.bcs.dto.ResponseDTO;
import co.matisses.bcs.mbean.BCSApplicationMBean;
import co.matisses.bcs.mbean.BCSGenericMBean;
import co.matisses.bcs.mbean.SAPB1WSBean;
import co.matisses.persistence.sap.entity.Almacen;
import co.matisses.persistence.sap.entity.AsientoContable;
import co.matisses.persistence.sap.entity.BaruDetalleDocumentoPendiente;
import co.matisses.persistence.sap.entity.BaruDocumentoPendiente;
import co.matisses.persistence.sap.entity.DetalleEntrada;
import co.matisses.persistence.sap.entity.DetalleFacturaSAP;
import co.matisses.persistence.sap.entity.DetalleOrdenVenta;
import co.matisses.persistence.sap.entity.DevolucionSAP;
import co.matisses.persistence.sap.entity.DocumentosExcluidos;
import co.matisses.persistence.sap.entity.Entrada;
import co.matisses.persistence.sap.entity.FacturaSAP;
import co.matisses.persistence.sap.entity.OrdenVenta;
import co.matisses.persistence.sap.entity.ReciboCaja;
import co.matisses.persistence.sap.entity.Salida;
import co.matisses.persistence.sap.entity.UbicacionSAP;
import co.matisses.persistence.sap.facade.AlmacenFacade;
import co.matisses.persistence.sap.facade.AsientoContableFacade;
import co.matisses.persistence.sap.facade.BaruDetalleDocumentoPendienteFacade;
import co.matisses.persistence.sap.facade.BaruDocumentoPendienteFacade;
import co.matisses.persistence.sap.facade.DetalleEntradaFacade;
import co.matisses.persistence.sap.facade.DetalleFacturaSAPFacade;
import co.matisses.persistence.sap.facade.DetalleOrdenVentaFacade;
import co.matisses.persistence.sap.facade.DevolucionSAPFacade;
import co.matisses.persistence.sap.facade.DocumentosExcluidosFacade;
import co.matisses.persistence.sap.facade.EntradaFacade;
import co.matisses.persistence.sap.facade.FacturaSAPFacade;
import co.matisses.persistence.sap.facade.OrdenVentaFacade;
import co.matisses.persistence.sap.facade.ReciboCajaFacade;
import co.matisses.persistence.sap.facade.SalidaFacade;
import co.matisses.persistence.sap.facade.UbicacionSAPFacade;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author ygil
 */
@Stateless
@Path("procesardocumento")
public class ProcesarDocumentoREST {

    @Inject
    private BCSApplicationMBean applicationMBean;
    @Inject
    private SAPB1WSBean sapB1MBean;
    @Inject
    private BCSGenericMBean bcsGenericMBean;
    @Inject
    private SendHtmlEmailREST emailREST;
    private static final Logger console = Logger.getLogger(ProcesarDocumentoREST.class.getSimpleName());
    @EJB
    private BaruDocumentoPendienteFacade documentoPendienteFacade;
    @EJB
    private BaruDetalleDocumentoPendienteFacade detalleDocumentoPendienteFacade;
    @EJB
    private DetalleFacturaSAPFacade detalleFacturaSAPFacade;
    @EJB
    private FacturaSAPFacade facturaSAPFacade;
    @EJB
    private EntradaFacade entradaFacade;
    @EJB
    private DetalleEntradaFacade detalleEntradaFacade;
    @EJB
    private OrdenVentaFacade ordenVentaFacade;
    @EJB
    private DetalleOrdenVentaFacade detalleOrdenVentaFacade;
    @EJB
    private AlmacenFacade almacenFacade;
    @EJB
    private AsientoContableFacade asientoContableFacade;
    @EJB
    private ReciboCajaFacade reciboCajaFacade;
    @EJB
    private UbicacionSAPFacade ubicacionSAPFacade;
    @EJB
    private DocumentosExcluidosFacade documentosExcluidosFacade;
    @EJB
    private DevolucionSAPFacade devolucionSAPFacade;
    @EJB
    private SalidaFacade salidaFacade;

    public ProcesarDocumentoREST() {
    }

    @GET
    @Path("ejecutarsonda/{procesando}")
    @Produces({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response ejecutarSonda(@PathParam("procesando") String procesando) {
        /*Se abre sesion en SAP para poder realizar las operaciones*/
        String sesionSAP = sapB1MBean.obtenerSesionSAP();
        try {
            List<BaruDocumentoPendiente> pendingDocuments = documentoPendienteFacade.obtenerDocumentosPendientes(procesando.charAt(0));

            if (pendingDocuments != null && !pendingDocuments.isEmpty()) {
                for (BaruDocumentoPendiente b : pendingDocuments) {
                    /*Se valida que tipo de documento es, para decidir en que tablas consultar*/
                    if (b.getuTipoDocumento().equals("FV")) {
                        FacturaSAP invoice = facturaSAPFacade.findByDocNum(b.getuNumeroDocumento());

                        if (invoice != null && invoice.getDocEntry() != null && invoice.getDocEntry() != 0) {
                            List<DetalleFacturaSAP> details = detalleFacturaSAPFacade.obtenerDetalleFactura(invoice.getDocEntry().doubleValue());

                            if (details != null && !details.isEmpty()) {
                                /*Se valida si se debe realizar entrada de mercancia a almacenes de clientes*/
                                boolean entregar = false;
                                for (DetalleFacturaSAP d : details) {
                                    if (d.getUEstadoP().equals("P") || d.getUEstadoP().equals("G")) {
                                        entregar = true;
                                        break;
                                    } else {
                                        entregar = false;
                                    }
                                }

                                List<BaruDetalleDocumentoPendiente> baruDet = detalleDocumentoPendienteFacade.obtenerDetalleDocumento(b.getCode());

                                boolean crearEntrega = true;
                                boolean crearOrdenVenta = true;
                                boolean crearAsiento = true;
                                Long numeroEntrada = null;
                                Long numeroOrdenVenta = null;
                                Long numeroAsiento = null;

                                if (baruDet != null && !baruDet.isEmpty()) {
                                    for (BaruDetalleDocumentoPendiente d : baruDet) {
                                        switch (d.getuTipoDocumento()) {
                                            case "EM":
                                                crearEntrega = false;
                                                numeroEntrada = d.getuNumeroDocumento().longValue();
                                                break;
                                            case "OV":
                                                crearOrdenVenta = false;
                                                numeroOrdenVenta = d.getuNumeroDocumento().longValue();
                                                break;
                                            case "AC":
                                                crearAsiento = false;
                                                numeroAsiento = d.getuNumeroDocumento().longValue();
                                                break;
                                            default:
                                                break;
                                        }
                                    }
                                }

                                /*Se verifica si es necesario hacer asiento contable de los productos en consignacion*/
                                List<Object[]> asiento = facturaSAPFacade.consultarDatosAsientoConsignacionFactura(invoice.getDocEntry().longValue());

                                if (asiento != null && !asiento.isEmpty() && crearAsiento) {
                                    try {
                                        /*Se debe crear asiento contable*/
                                        numeroAsiento = crearAsientoContable(sesionSAP, invoice, asiento);

                                        if (numeroAsiento != null && numeroAsiento > 0) {
                                            AsientoContable asientoC = asientoContableFacade.find(numeroAsiento.intValue());

                                            if (asientoC != null && asientoC.getNumber() != 0) {
                                                console.log(Level.INFO, "Se creao asiento numero {0} para la factura {1}", new Object[]{asientoC.getNumber(), invoice.getDocNum()});

                                                registrarDetalleDocumentoPendiente(b.getCode(), asientoC.getNumber(), "AC");
                                            }
                                        }
                                    } catch (Exception e) {
                                        console.log(Level.SEVERE, "Ocurrio un error al crear el asiento contable.", e);
                                    }
                                } else {
                                    crearAsiento = false;
                                    b.setuEstado("F");
                                }

                                if (entregar) {
                                    if (crearEntrega) {
                                        try {
                                            numeroEntrada = crearEntradaMercancia(sesionSAP, invoice, details);

                                            if (numeroEntrada != null && numeroEntrada > 0) {
                                                Entrada entrada = entradaFacade.find(numeroEntrada.intValue());

                                                if (entrada != null && entrada.getDocEntry() != null && entrada.getDocEntry() != 0) {
                                                    crearEntrega = false;
                                                    console.log(Level.INFO, "Se creo la entrada de mercancia {0}, de la factura {1}", new Object[]{entrada.getDocNum(), b.getuNumeroDocumento()});

                                                    /*Se hace el registro en la tabla de detalle, informando el tipo de documento que se creo exitosamente*/
                                                    registrarDetalleDocumentoPendiente(b.getCode(), entrada.getDocNum(), "EM");
                                                } else {
                                                    console.log(Level.SEVERE, "No se encontraron datos correspondiente a la entrada de mercancia con id {0}", numeroEntrada);
                                                }
                                            }
                                        } catch (Exception e) {
                                            console.log(Level.SEVERE, "Ocurrio un error al crear la entrada.", e);
                                        }
                                    }

                                    if (crearOrdenVenta) {
                                        try {
                                            /*Se crea la orden de venta*/
                                            numeroOrdenVenta = crearOrdenVenta(sesionSAP, invoice, details);

                                            if (numeroOrdenVenta != null && numeroOrdenVenta > 0) {
                                                OrdenVenta orden = ordenVentaFacade.find(numeroOrdenVenta.intValue());

                                                if (orden != null && orden.getDocEntry() != null && orden.getDocEntry() != 0) {
                                                    crearOrdenVenta = false;
                                                    console.log(Level.INFO, "Se creo la orden de venta {0}, de la factura {1}", new Object[]{orden.getDocNum(), b.getuNumeroDocumento()});

                                                    /*Se hace el registro en la tabla de detalle, informando el tipo de documento que se creo exitosamente*/
                                                    registrarDetalleDocumentoPendiente(b.getCode(), orden.getDocNum(), "OV");
                                                }
                                            } else {
                                                console.log(Level.SEVERE, "No se encontraron datos correspondiente a la orden de venta con id {0}", numeroOrdenVenta);
                                            }
                                        } catch (Exception e) {
                                            console.log(Level.SEVERE, "Ocurrio un error al crear la orden de venta.", e);
                                        }
                                    }

                                    /*Se marca el documento pendiente como terminado o error, si no se pudo generar algun documento*/
                                    if (crearAsiento) {
                                        b.setuEstado((numeroEntrada != null && numeroEntrada > 0) && (numeroOrdenVenta != null && numeroOrdenVenta > 0)
                                                && (crearAsiento && numeroAsiento != null && numeroAsiento > 0) ? "F" : "E");
                                    } else {
                                        b.setuEstado((numeroEntrada != null && numeroEntrada > 0) && (numeroOrdenVenta != null && numeroOrdenVenta > 0) ? "F" : "E");
                                    }
                                }
                            } else {
                                console.log(Level.SEVERE, "No se encontro detalle relacionado a la factura numero: {0}", b.getuNumeroDocumento());
                            }
                        } else {
                            console.log(Level.SEVERE, "No se puede procesar la factura con numero: {0}, debido a que no se encontro el documento registrado en la base de datos", b.getuNumeroDocumento());
                        }
                    } else if (b.getuTipoDocumento().equals("NC")) {
                        FacturaSAP invoice = facturaSAPFacade.findByDocNum(b.getuNumeroDocumento());

                        if (invoice != null && invoice.getDocEntry() != null && invoice.getDocEntry() != 0) {
                            List<BaruDetalleDocumentoPendiente> baruDet = detalleDocumentoPendienteFacade.obtenerDetalleDocumento(b.getCode());

                            List<Object[]> enc = facturaSAPFacade.consultarDatosFacturaAnulacion(String.valueOf(invoice.getDocNum()));

                            int guardadoPendiente = 0;
                            SalesDocumentDTO dto = new SalesDocumentDTO();
                            if (enc != null && !enc.isEmpty()) {
//                                if (!DateUtils.isSameDay((Date) enc.get(0)[1], new Date())) {
//                                    console.log(Level.SEVERE, "No se puede realizar la anulacion de una factura de fechas pasadas. ");
//                                    continue;
//                                }
                                dto.setDocEntry(((Integer) enc.get(0)[2]).longValue());
                                dto.setRefDocnum(String.valueOf(invoice.getDocNum()));
                                dto.setCardCode((String) enc.get(0)[4]);
                                dto.setComments("Anulacion generada por 360 para la factura " + invoice.getNumAtCard());
                                dto.setPaymentGroupCode(((Short) enc.get(0)[6]).toString());
                                dto.setSource("P");
                                dto.setSeriesCode(((Short) enc.get(0)[32]).toString());
                                dto.setWuid((String) enc.get(0)[17]);
                                if ((Integer) enc.get(0)[18] != null) {
                                    dto.setPosShiftId(((Integer) enc.get(0)[18]).longValue());
                                }
                                dto.setCreditNoteType("A");

                                //Agrega los vendedores a la devolucion
                                if (enc.get(0)[7] != null) {
                                    SalesEmployeeDTO salesEmp = new SalesEmployeeDTO();
                                    salesEmp.setName((String) enc.get(0)[7]);
                                    dto.addSalesEmployee(salesEmp);
                                    if (enc.get(0)[8] != null) {
                                        SalesEmployeeDTO salesEmp2 = new SalesEmployeeDTO();
                                        salesEmp2.setName((String) enc.get(0)[8]);
                                        dto.addSalesEmployee(salesEmp2);
                                        if (enc.get(0)[9] != null) {
                                            SalesEmployeeDTO salesEmp3 = new SalesEmployeeDTO();
                                            salesEmp3.setName((String) enc.get(0)[9]);
                                            dto.addSalesEmployee(salesEmp3);
                                            if (enc.get(0)[10] != null) {
                                                SalesEmployeeDTO salesEmp4 = new SalesEmployeeDTO();
                                                salesEmp4.setName((String) enc.get(0)[10]);
                                                dto.addSalesEmployee(salesEmp4);
                                                if (enc.get(0)[11] != null) {
                                                    SalesEmployeeDTO salesEmp5 = new SalesEmployeeDTO();
                                                    salesEmp5.setName((String) enc.get(0)[11]);
                                                    dto.addSalesEmployee(salesEmp5);
                                                }
                                            }
                                        }
                                    }
                                }

                                List<SalesDocumentLineDTO> detDtos = new ArrayList<>();
                                for (Object[] cols : enc) {
                                    if (((String) cols[28]).equals("G") || ((String) cols[28]).equals("P")) {
                                        guardadoPendiente++;
                                    }
//                                    dto.setShippingStatus((String) cols[28]);
                                    dto.setSalesCostingCode((String) cols[24]);
                                    dto.setLogisticsCostingCode((String) cols[25]);
                                    dto.setRouteCostingCode((String) cols[26]);

                                    SalesDocumentLineBinAllocationDTO binAllocDto = new SalesDocumentLineBinAllocationDTO();
                                    binAllocDto.setBinAbsEntry((Integer) cols[29]);
                                    binAllocDto.setQuantity((Integer) cols[31]);
                                    binAllocDto.setWhsCode((String) cols[23]);

                                    SalesDocumentLineDTO lineDto = new SalesDocumentLineDTO((String) cols[21], binAllocDto.getWhsCode(), ((Integer) cols[33]).doubleValue());
                                    int pos = detDtos.indexOf(lineDto);
                                    if (pos >= 0) {
                                        lineDto = detDtos.get(pos);
                                        lineDto.addBinAllocation(binAllocDto);
                                        //lineDto.setQuantity(lineDto.getQuantity() + ((BigDecimal) cols[22]).intValue());
                                        detDtos.set(pos, lineDto);
                                    } else {
                                        lineDto.setQuantity(((BigDecimal) cols[22]).intValue());
                                        lineDto.addBinAllocation(binAllocDto);
                                        lineDto.setLineNum((Integer) cols[20]);
                                        detDtos.add(lineDto);
                                    }
                                }
                                dto.setDocumentLines(detDtos);
                            } else {
                                console.log(Level.SEVERE, "No se pudo consultar la información de la factura necesaria para crear la anulacion.");
                            }

                            boolean reciboAnulado = false;
                            boolean notaCredito = false;
                            boolean asientoContable = false;
                            boolean salidaMercancia = false;
                            boolean ordenVentaCancelada = false;
                            Long numeroReciboCajaAnulado = null;
                            Long numeroNotaCredito = null;
                            Long numeroAsientoContable = null;
                            Long numeroSalidaMercancia = null;
                            Long numeroOrdenVentaCancelada = null;

                            if (baruDet != null && !baruDet.isEmpty()) {
                                for (BaruDetalleDocumentoPendiente d : baruDet) {
                                    switch (d.getuTipoDocumento()) {
                                        case "RC":
                                            reciboAnulado = true;
                                            numeroReciboCajaAnulado = d.getuNumeroDocumento().longValue();
                                            break;
                                        case "NC":
                                            notaCredito = true;
                                            numeroNotaCredito = d.getuNumeroDocumento().longValue();
                                            break;
                                        case "AC":
                                            asientoContable = true;
                                            numeroAsientoContable = d.getuNumeroDocumento().longValue();
                                            break;
                                        case "SM":
                                            salidaMercancia = true;
                                            numeroSalidaMercancia = d.getuNumeroDocumento().longValue();
                                            break;
                                        case "OV":
                                            ordenVentaCancelada = true;
                                            numeroOrdenVentaCancelada = d.getuNumeroDocumento().longValue();
                                            break;
                                        default:
                                            break;
                                    }
                                }
                            }

                            if (!reciboAnulado) {
                                try {
                                    numeroReciboCajaAnulado = cancelarReciboCaja(String.valueOf(invoice.getDocNum()), sesionSAP);

                                    if (numeroReciboCajaAnulado != null && numeroReciboCajaAnulado > 0) {
                                        console.log(Level.INFO, "Se cancelo el recibo de caja numero {0} para la factura {1}", new Object[]{numeroReciboCajaAnulado, invoice.getDocNum()});
                                        reciboAnulado = true;

                                        registrarDetalleDocumentoPendiente(b.getCode(), numeroReciboCajaAnulado.intValue(), "RC");
                                    } else if (numeroReciboCajaAnulado != null && numeroReciboCajaAnulado < 0) {
                                        reciboAnulado = true;
                                    }
                                } catch (Exception e) {
                                    console.log(Level.SEVERE, "Ocurrio un error al cancelar el recibo de caja para el documento pendiente con id {0}. Error {1}",
                                            new Object[]{b.getuNumeroDocumento(), e.getMessage()});
                                }
                            }
                            if (!notaCredito) {
                                try {
                                    numeroNotaCredito = crearNotaCredito(sesionSAP, dto);

                                    if (numeroNotaCredito != null && numeroNotaCredito > 0) {
                                        DevolucionSAP devolucion = devolucionSAPFacade.find(numeroNotaCredito.intValue());

                                        if (devolucion != null && devolucion.getDocEntry() != null && devolucion.getDocEntry() != 0) {
                                            console.log(Level.INFO, "Se creo la nota credito numero {0} para la factura {1}", new Object[]{devolucion.getDocNum(), invoice.getDocNum()});
                                            notaCredito = true;

                                            registrarDetalleDocumentoPendiente(b.getCode(), devolucion.getDocNum(), "NC");
                                        }
                                    }
                                } catch (Exception e) {
                                    console.log(Level.SEVERE, "Ocurrio un error al crear la nota credito para el documento pendiente con id {0}. Error {1}",
                                            new Object[]{b.getuNumeroDocumento(), e.getMessage()});
                                }
                            }
                            if (!asientoContable) {
                                try {
                                    numeroAsientoContable = crearAsientoContableAnulacion(sesionSAP, numeroNotaCredito);

                                    if (numeroAsientoContable != null && numeroAsientoContable > 0) {
                                        AsientoContable asiento = asientoContableFacade.find(numeroAsientoContable.intValue());

                                        if (asiento != null && asiento.getNumber() != 0) {
                                            console.log(Level.INFO, "Se creao asiento numero {0} para la factura {1}", new Object[]{asiento.getNumber(), invoice.getDocNum()});
                                            asientoContable = true;

                                            registrarDetalleDocumentoPendiente(b.getCode(), asiento.getNumber(), "AC");
                                        }
                                    } else if (numeroAsientoContable != null && numeroAsientoContable < 0) {
                                        asientoContable = true;
                                    }
                                } catch (Exception e) {
                                    console.log(Level.SEVERE, "Ocurrio un error al crear el asiento contable para el documento pendiente con id {0}. Error {1}",
                                            new Object[]{b.getuNumeroDocumento(), e.getMessage()});
                                }
                            }
                            /*Se valida si se necesita salida de mercancia y cierre de orden de venta*/
                            if (/*dto.getShippingStatus().trim().isEmpty() || dto.getShippingStatus().equals("P")*/guardadoPendiente > 0) {
                                if (!salidaMercancia) {
                                    try {
                                        numeroSalidaMercancia = crearSalidaMercancia(sesionSAP, dto, String.valueOf(invoice.getDocNum()), numeroNotaCredito);

                                        if (numeroSalidaMercancia != null && numeroSalidaMercancia > 0) {
                                            Salida salida = salidaFacade.find(numeroSalidaMercancia.intValue());

                                            if (salida != null && salida.getDocNum() != null && salida.getDocNum() != 0) {
                                                console.log(Level.INFO, "se creo salida de mercancia numero {1} para la factura {1}", new Object[]{salida.getDocNum(), invoice.getDocNum()});
                                                salidaMercancia = true;

                                                registrarDetalleDocumentoPendiente(b.getCode(), salida.getDocNum(), "SM");
                                            }
                                        }
                                    } catch (Exception e) {
                                        console.log(Level.SEVERE, "Ocurrio un error al crear la salida de mercancia para el documento pendiente con id {0}. Error {1}",
                                                new Object[]{b.getuNumeroDocumento(), e.getMessage()});
                                    }
                                }
                                if (!ordenVentaCancelada) {
                                    try {
                                        numeroOrdenVentaCancelada = cancelarOrdenVenta(String.valueOf(invoice.getDocNum()), sesionSAP);

                                        if (numeroOrdenVentaCancelada != null && numeroOrdenVentaCancelada > 0) {
                                            OrdenVenta orden = ordenVentaFacade.find(numeroOrdenVentaCancelada.intValue());

                                            if (orden != null && orden.getDocNum() != 0) {
                                                console.log(Level.INFO, "Se cancelo la orden de venta numero {0} para la factura {1}", new Object[]{orden.getDocNum(), invoice.getDocNum()});
                                                ordenVentaCancelada = true;

                                                registrarDetalleDocumentoPendiente(b.getCode(), orden.getDocNum(), "OV");
                                            }
                                        }
                                    } catch (Exception e) {
                                        console.log(Level.SEVERE, "Ocurrio un error al cerrar la orden de venta para el documento pendiente con id {0}. Error {1}",
                                                new Object[]{b.getuNumeroDocumento(), e.getMessage()});
                                    }
                                }

                                if (reciboAnulado && notaCredito && asientoContable && salidaMercancia && ordenVentaCancelada) {
                                    b.setuEstado("F");
                                } else {
                                    b.setuEstado("E");
                                }
                            } else if (reciboAnulado && notaCredito && asientoContable) {
                                b.setuEstado("F");
                            } else {
                                b.setuEstado("E");
                            }

                            //registra la factura y la nota credito en la tabla de documentos excluidos para que no se visualicen en el informe de ventas
                            long id = System.currentTimeMillis();

                            if (!documentosExcluidosFacade.verificarDocumentoExcluidoExiste(String.valueOf(invoice.getDocNum()), "Factura")) {
                                DocumentosExcluidos fac = new DocumentosExcluidos();

                                fac.setCode(Long.toString(id));
                                fac.setName(Long.toString(id));
                                fac.setDocNum(String.valueOf(invoice.getDocNum()));
                                fac.setFechaExclusion(new Date());
                                fac.setUsuarioExcluye("sonda");
                                fac.setTipoDocumento("Factura");

                                try {
                                    documentosExcluidosFacade.create(fac);
                                    console.log(Level.INFO, "Se excluyo la factura {0} del informe de ventas. ", invoice.getDocNum());
                                } catch (Exception e) {
                                    console.log(Level.WARNING, "No fue posible excluir la factura del informe de ventas. ", e);
                                }
                            }
                            if (!documentosExcluidosFacade.verificarDocumentoExcluidoExiste(String.valueOf(invoice.getDocNum()), "Devolucion") && notaCredito) {
                                DocumentosExcluidos dev = new DocumentosExcluidos();

                                dev.setCode(Long.toString(id + 1));
                                dev.setName(Long.toString(id + 1));
                                dev.setDocNum(String.valueOf(invoice.getDocNum()));
                                dev.setFechaExclusion(new Date());
                                dev.setUsuarioExcluye("sonda");
                                dev.setTipoDocumento("Devolucion");

                                try {
                                    documentosExcluidosFacade.create(dev);
                                    console.log(Level.INFO, "Se excluyo la nota credito {0} del informe de ventas. ", numeroNotaCredito);
                                } catch (Exception e) {
                                    console.log(Level.WARNING, "No fue posible excluir la nota credito del informe de ventas. ", e);
                                }
                            }
                        }
                    }

                    b.setuIntentos(b.getuIntentos() + 1);
                    b.setuUltimoIntento(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS").format(new Date()));

                    try {
                        documentoPendienteFacade.edit(b);
                        console.log(Level.INFO, "Se actualizo el numero de intentos del documento pendiente con id {0}", b.getCode());
                    } catch (Exception e) {
                        console.log(Level.SEVERE, "Ocurrio un error al actualizar el numero de intentos del documento pendiente con id {0}. Error {1}", new Object[]{b.getCode(), e.getMessage()});
                    }
                }
            }
        } catch (Exception e) {
            console.log(Level.SEVERE, "Ocurrio un error al procesar el documento pendiente. ", e);
            return Response.ok(new ResponseDTO(0, "Ocurrio un error al procesar el documento pendiente. " + e.getMessage())).build();
        } finally {
        }
        return Response.ok(new ResponseDTO(1)).build();
    }

    private void registrarDetalleDocumentoPendiente(String codeDocPdte, Integer numeroDocumento, String tipoDocumento) {
        BaruDetalleDocumentoPendiente det = new BaruDetalleDocumentoPendiente();

        det.setCode(new SimpleDateFormat("yyyyMMddHHmmssSS").format(new Date()));
        det.setName(new SimpleDateFormat("yyyyMMddHHmmssSS").format(new Date()));
        det.setuCodeDocPdte(codeDocPdte);
        det.setuNumeroDocumento(numeroDocumento);
        det.setuTipoDocumento(tipoDocumento);

        try {
            detalleDocumentoPendienteFacade.create(det);
        } catch (Exception e) {
            console.log(Level.SEVERE, "No fue posible crear el registro de la operacion OV realizada al documento pendiente con id {0}. Error {1}", new Object[]{codeDocPdte, e.getMessage()});
        }
    }

    private Long crearEntradaMercancia(String sesionSAP, FacturaSAP factura, List<DetalleFacturaSAP> detalle) throws GoodsReceiptServiceException {
        /*Se valida si la entrada fue hecha manualmente en SAP*/
        List<Entrada> entradas = entradaFacade.obtenerEntradasFactura(factura.getDocNum());

        if (entradas != null && !entradas.isEmpty()) {
            StringBuilder sb = new StringBuilder();

            for (Entrada e : entradas) {
                /*Se valida el detalle de la entrada para evitar que hayan errores*/
                List<DetalleEntrada> detEntrada = detalleEntradaFacade.obtenerDetalleEntrada(e.getDocEntry());

                if (detEntrada != null && !detEntrada.isEmpty()) {
                    for (DetalleEntrada d : detEntrada) {
                        for (DetalleFacturaSAP f : detalle) {
                            if (d.getItemCode().equals(f.getItemCode()) && f.getUEstadoP().equals("P") || f.getUEstadoP().equals("G")) {
                                if (f.getQuantity().intValue() - d.getQuantity().intValue() <= 0) {
                                    sb.append(e.getDocNum());
                                    sb.append(", ");
                                }
                                break;
                            }
                        }
                    }
                }
            }

            if (sb.length() > 0) {
                sb.deleteCharAt(sb.length() - 1);
                sb.deleteCharAt(sb.length() - 1);
                /*Se mandara notificacion de inconcistencia en las entradas*/
                enviarNotificacion("CREAR ENTRADA DE MERCANCIA",
                        "Los siguientes documentos presentan inconcistencia al ser comparados con la factura <b>" + factura.getDocNum() + "</b>; entradas de mercancia: <b>" + sb.toString() + "</b>");
                throw new GoodsReceiptServiceException("Se encontraron inconcistencias al crear la entrada de la factura " + factura.getDocNum());
            }
        }

        GoodsReceiptDTO entrada = new GoodsReceiptDTO();

        entrada.setComments("Entrada de mercancia para FV " + factura.getDocNum() + " y cliente " + factura.getCardCode());
        entrada.setInvoiceNumber(Integer.toString(factura.getDocNum()));
        entrada.setJournalMemo("Good Receipt");
        entrada.setSeries(69L);
        entrada.setOrigen("T");

        Integer contador = 0;
        for (DetalleFacturaSAP d : detalle) {
            if ((d.getUEstadoP().equals("P") || d.getUEstadoP().equals("G")) && d.getQuantity().intValue() > 0) {
                Almacen almacen = almacenFacade.find(d.getWhsCode());

                GoodsReceiptDetailDTO detEntrada = new GoodsReceiptDetailDTO();

                detEntrada.setAccountCode("91051001");
                detEntrada.setItemCode(d.getItemCode());
                detEntrada.setLineNum(contador.longValue());
                detEntrada.setQuantity(d.getQuantity().intValue());
                if (almacen != null && almacen.getuBodegaClientes() != null && !almacen.getuBodegaClientes().isEmpty()) {
                    detEntrada.setWhsCode(almacen.getuBodegaClientes());
                } else {
                    detEntrada.setWhsCode(d.getWhsCode().contains("CL") ? d.getWhsCode() : "CL" + d.getWhsCode());
                }
                if (d.getDiscPrcnt() != null && d.getDiscPrcnt().doubleValue() > 0) {
                    detEntrada.setDiscount(d.getDiscPrcnt().doubleValue());
                }
                detEntrada.setPrice(d.getPrice().doubleValue());

                entrada.addDetail(detEntrada);
                contador++;
            }
        }

        try {
            GoodsReceiptServiceConnector grsc = sapB1MBean.getGoodsReceiptServiceConnectorInstance(sesionSAP);
            return grsc.createDocument(entrada);
        } catch (Exception e) {
            console.log(Level.SEVERE, "Error al crear la entrada al almacen de clientes ", e);
            return 0L;
        }
    }

    private Long crearOrdenVenta(String sesionSAP, FacturaSAP factura, List<DetalleFacturaSAP> detalle) throws OrderServiceException {
        /*Se valida si la entrada fue hecha manualmente en SAP*/
        List<OrdenVenta> ordenVenta = ordenVentaFacade.obtenerOrdenesVentaFactura(factura.getDocNum());

        if (ordenVenta != null && !ordenVenta.isEmpty()) {
            StringBuilder sb = new StringBuilder();

            for (OrdenVenta o : ordenVenta) {
                /*Se valida el detalle de la entrada para evitar que hayan errores*/
                List<DetalleOrdenVenta> detOrden = detalleOrdenVentaFacade.obtenerDetalleOrdenVenta(o.getDocEntry());

                if (detOrden != null && !detOrden.isEmpty()) {
                    for (DetalleOrdenVenta d : detOrden) {
                        for (DetalleFacturaSAP f : detalle) {
                            if (d.getItemCode().equals(f.getItemCode()) && f.getUEstadoP().equals("P") || f.getUEstadoP().equals("G")) {
                                if (f.getQuantity().intValue() - d.getQuantity().intValue() <= 0) {
                                    sb.append(o.getDocNum());
                                    sb.append(", ");
                                }
                                break;
                            }
                        }
                    }
                }
            }

            if (sb.length() > 0) {
                sb.deleteCharAt(sb.length() - 1);
                sb.deleteCharAt(sb.length() - 1);
                /*Se mandara notificacion de inconcistencia en las entradas*/
                enviarNotificacion("CREAR ORDEN DE VENTA",
                        "Los siguientes documentos presentan inconcistencia al ser comparados con la factura <b>" + factura.getDocNum() + "</b>; ordenes de venta: <b>" + sb.toString() + "</b>");
                throw new OrderServiceException("Se encontraron inconcistencias al crear la orden de venta de la factura " + factura.getDocNum());
            }
        }

        GregorianCalendar cal = new GregorianCalendar();
        cal.add(Calendar.DATE, 1);

        OrderDTO order = new OrderDTO();

        order.setCardCode(factura.getCardCode());
        order.setComments(factura.getComments());
        order.setDocDate(new Date());
        order.setDocDueDate(cal.getTime());
        order.setInvoiceNumber(Integer.toString(factura.getDocNum()));
        order.setOrigen("T");
        order.setSalesPersonCode(factura.getSlpCode().longValue());
        order.setSeries(13L);

        Integer contador = 0;
        for (DetalleFacturaSAP d : detalle) {
            if ((d.getUEstadoP().equals("P") || d.getUEstadoP().equals("G")) && d.getQuantity().intValue() > 0) {
                Almacen almacen = almacenFacade.find(d.getWhsCode());

                OrderDetailDTO detail = new OrderDetailDTO();

                detail.setEstado(d.getUEstadoP());
                detail.setItemCode(d.getItemCode());
                detail.setLineNum(contador.longValue());
                detail.setQuantity(d.getQuantity().doubleValue());
                detail.setuLineNumFv(((Integer) d.getDetalleFacturaSAPPK().getLineNum()).longValue());
                if (almacen != null && almacen.getuBodegaClientes() != null && !almacen.getuBodegaClientes().isEmpty()) {
                    detail.setWarehouseCode(almacen.getuBodegaClientes());
                } else {
                    detail.setWarehouseCode(d.getWhsCode().contains("CL") ? d.getWhsCode() : "CL" + d.getWhsCode());
                }
                detail.setPrice(d.getPrice().doubleValue());
                if (d.getDiscPrcnt() != null && d.getDiscPrcnt().doubleValue() > 0) {
                    detail.setDiscount(d.getDiscPrcnt().doubleValue());
                }

                order.addLine(detail);
                contador++;
            }
        }

        try {
            OrdersServiceConnector osc = sapB1MBean.getOrderServiceConnectorInstance(sesionSAP);
            return osc.createOrder(order);
        } catch (Exception e) {
            console.log(Level.SEVERE, "Error al crear la orden de venta ", e);
            return 0L;
        }
    }

    private Long crearAsientoContable(String sesionSAP, FacturaSAP factura, List<Object[]> asiento) throws JournalEntryServiceException {
        /*Se valida si el asiento fue hecho manualmente*/
        List<AsientoContable> asientos = asientoContableFacade.obtenerAsientosFactura(factura.getDocNum());

        if (asientos != null && !asientos.isEmpty() && asientos.size() > 1) {
            StringBuilder sb = new StringBuilder();

            asientos.stream().map((e) -> {
                sb.append(e.getTransId());
                return e;
            }).forEach((_item) -> {
                sb.append(", ");
            });

            if (sb.length() > 0) {
                sb.deleteCharAt(sb.length() - 1);
                sb.deleteCharAt(sb.length() - 1);
                /*Se mandara notificacion de inconcistencia en las entradas*/
                enviarNotificacion("CREAR ASIENTO CONTABLE",
                        "Los siguientes documentos presentan inconcistencia al ser comparados con la factura <b>" + factura.getDocNum() + "</b>; Asientos contables: <b>" + sb.toString() + "</b>");
                throw new JournalEntryServiceException("Se encontraron inconcistencias al crear el asiento contable de la factura " + factura.getDocNum());
            }
        }

        /*Si hay productos en consignacion, realiza el asiento contable correspondiente*/
        if (!asiento.isEmpty()) {
            JournalEntryDTO journalEntryHeader = null;
            for (Object[] row : asiento) {
                if (journalEntryHeader == null) {
                    journalEntryHeader = new JournalEntryDTO();
                    journalEntryHeader.setDueDate((Date) row[0]);
                    journalEntryHeader.setTaxDate((Date) row[0]);
                    journalEntryHeader.setRefDate((Date) row[0]);
                    journalEntryHeader.setMemo((String) row[1]);
                    journalEntryHeader.setRef1((String) row[2]);
                    journalEntryHeader.setRef2((String) row[3]);
                    journalEntryHeader.setRef3((String) row[4]);
                    journalEntryHeader.setTransactionCode((String) row[5]);
                }

                JournalEntryLineDTO line = new JournalEntryLineDTO();
                line.setRef1((String) row[2]);
                line.setRef2((String) row[3]);
                line.setLineId(((BigInteger) row[6]).longValue());
                line.setShortName((String) row[7]);
                line.setLineMemo((String) row[8]);
                line.setOcrCode2((String) row[9]);
                line.setProject((String) row[10]);
                line.setInfoCo01((String) row[11]);
                line.setDebit(((Integer) row[12]).doubleValue());
                line.setCredit(((Integer) row[13]).doubleValue());

                journalEntryHeader.addLine(line);
            }
            JournalEntriesServiceConnector jesc = sapB1MBean.getJournalEntriesServiceConnectorInstance(sesionSAP);
            return jesc.createJournalEntry(journalEntryHeader);
        } else {
            return -1L;
        }
    }

    private Long cancelarReciboCaja(String nroFactura, String sesionSAP) throws Exception {
        ReciboCaja recibo = reciboCajaFacade.obtenerReciboCaja(nroFactura);

        if (recibo == null || recibo.getDocEntry() == null || recibo.getDocEntry() == 0) {
            console.log(Level.SEVERE, "No se encontraron recibos de cajas relacionados con la FV {0}", nroFactura);
            return -1L;
        }

        try {
            IncomingPaymentServiceConnector ipsc = sapB1MBean.getIncomingPaymentServiceConnectorInstance(sesionSAP);
            ipsc.cancelPayment(recibo.getDocEntry().longValue());
            console.log(Level.INFO, "Se cancelo el recibo de caja con ID {0}", recibo.getDocEntry());

            return Long.parseLong(String.valueOf(recibo.getDocNum()));
        } catch (IncomingPaymentServiceException | NumberFormatException e) {
            console.log(Level.SEVERE, "Ocurrio un error al cancelar el recibo de caja.", e);
            return 0L;
        }
    }

    private Long crearNotaCredito(String sesionSAP, SalesDocumentDTO dto) throws CreditNotesServiceException {
        if (dto != null) {
            CreditNotesServiceConnector sc = sapB1MBean.getCreditNotesServiceConnectorInstance(sesionSAP);
            Long docEntry = sc.createCreditNote(dto);
            console.log(Level.INFO, "Se creo la nota credito con ID {0}", docEntry);

            return docEntry;
        } else {
            return 0L;
        }
    }

    private Long crearAsientoContableAnulacion(String sesionSAP, Long docEntry) {
        List<Object[]> asiento = asientoContableFacade.consultarDatosAsientoConsignacion(docEntry);
        if (!asiento.isEmpty()) {
            JournalEntryDTO journalEntryHeader = null;
            for (Object[] row : asiento) {
                if (journalEntryHeader == null) {
                    journalEntryHeader = new JournalEntryDTO();
                    journalEntryHeader.setRefDate((Date) row[0]);
                    journalEntryHeader.setDueDate((Date) row[1]);
                    journalEntryHeader.setTaxDate((Date) row[2]);

                    journalEntryHeader.setMemo((String) row[3]);
                    journalEntryHeader.setRef1((String) row[4]);
                    journalEntryHeader.setRef2((String) row[5]);
                    journalEntryHeader.setRef3((String) row[6]);
                    journalEntryHeader.setTransactionCode((String) row[7]);
                }

                JournalEntryLineDTO line = new JournalEntryLineDTO();
                line.setRef1((String) row[4]);
                line.setRef2((String) row[5]);
                line.setLineId(((BigInteger) row[8]).longValue());
                line.setShortName((String) row[9]);
                line.setLineMemo((String) row[10]);
                line.setOcrCode2((String) row[11]);
                line.setProject((String) row[12]);
                line.setInfoCo01((String) row[13]);
                line.setCredit(((Integer) row[14]).doubleValue());
                line.setDebit(((Integer) row[15]).doubleValue());

                journalEntryHeader.addLine(line);
            }
            try {
                JournalEntriesServiceConnector jesc = sapB1MBean.getJournalEntriesServiceConnectorInstance(sesionSAP);
                Long docEntryAsiento = jesc.createJournalEntry(journalEntryHeader);
                console.log(Level.INFO, "Se creo el asiento contable con ID {0}", docEntryAsiento);

                return docEntryAsiento;
            } catch (Exception e) {
                console.log(Level.SEVERE, "No se pudo crear el asiento contable. ", e);
                return 0L;
            }
        }
        return -1L;
    }

    private Long crearSalidaMercancia(String sesionSAP, SalesDocumentDTO enc, String nroFactura, Long docEntryNC) throws GoodsIssueServiceException {
        GoodsIssueDTO document = new GoodsIssueDTO();

        document.setComments("Doc. creado con B1WS Segun Nota Credito #" + docEntryNC + " (DocEntry) para Factura #" + nroFactura);
        document.setJournalMemo("Salida de mercancia de clientes");
        document.setSeries("26"); //TODO: parametrizar numero de serie de salia de mercancias por inventario
        document.setGroupNum(String.valueOf(-1L)); //Ultimo precio de compra
        document.setInvoiceNumber(nroFactura);
        document.setuOrigen("T");

        List<DetalleFacturaSAP> details = detalleFacturaSAPFacade.obtenerDetalleFactura(enc.getDocEntry().doubleValue());
        if (details != null && !details.isEmpty()) {
            Integer contador = 0;

            for (DetalleFacturaSAP d : details) {
                if ((d.getUEstadoP().equals("P") || d.getUEstadoP().equals("G")) && d.getQuantity().intValue() > 0) {
                    Almacen almacen = almacenFacade.find(d.getWhsCode());

                    GoodsIssueDetailDTO det = new GoodsIssueDetailDTO();

                    if (ubicacionSAPFacade.validarUbicacionesAlmacen(d.getWhsCode().contains("CL") ? d.getWhsCode() : "CL" + d.getWhsCode())) {
                        UbicacionSAP ubi = ubicacionSAPFacade.obtenerDatosUbicacionBinCode(d.getWhsCode().contains("CL") ? d.getWhsCode() : "CL" + d.getWhsCode() + "TM");

                        if (ubi != null && ubi.getAbsEntry() != null && ubi.getAbsEntry() != 0) {
                            GoodsIssueLocationsDTO loc = new GoodsIssueLocationsDTO();
                            loc.setBinAbs(ubi.getAbsEntry().toString());
                            loc.setQuantity(d.getQuantity().toString());
                            det.addLocation(loc);
                        }
                    }

                    det.setAccountCode("91051001");
                    det.setItemCode(d.getItemCode());
                    det.setLineNum(contador.toString());
                    det.setQuantity(d.getQuantity().toString());
                    if (almacen != null && almacen.getuBodegaClientes() != null && !almacen.getuBodegaClientes().isEmpty()) {
                        det.setWhsCode(almacen.getuBodegaClientes());
                    } else {
                        det.setWhsCode(d.getWhsCode().contains("CL") ? d.getWhsCode() : "CL" + d.getWhsCode());
                    }

                    document.addDetail(det);
                    contador++;
                }
            }

            try {
                GoodsIssueServiceConnector gisc = sapB1MBean.getGoodsIssueServiceConnectorInstance(sesionSAP);
                Long docEntry = gisc.createDocument(document);
                console.log(Level.INFO, "Se creo la salida de mercancia de clientes con docEntry = {0}", docEntry);

                return docEntry;
            } catch (Exception e) {
                console.log(Level.SEVERE, "Ocurrio un error al crear la salida de mercancia del documento pendiente.", e);
                return 0L;
            }
        } else {
            throw new GoodsIssueServiceException("No se encontraron datos para realizar la salida de mercancia");
        }
    }

    private Long cancelarOrdenVenta(String nroFactura, String sesionSAP) throws OrderServiceException {
        List<OrdenVenta> orden = ordenVentaFacade.obtenerOrdenesVentaFactura(Integer.parseInt(nroFactura));

        if (orden != null && orden.size() == 1) {
            OrdersServiceConnector osc = sapB1MBean.getOrderServiceConnectorInstance(sesionSAP);

            try {
                osc.cancelOrder(orden.get(0).getDocEntry().longValue());
                console.log(Level.INFO, "Se cancelo con exito la orden de venta {0}", orden.get(0).getDocEntry().longValue());

                return orden.get(0).getDocEntry().longValue();
            } catch (Exception e) {
                console.log(Level.SEVERE, "Ocurrio un error al consultar la orden de venta. ", e);
                return 0L;
            }
        } else {
            StringBuilder sb = new StringBuilder();

            for (OrdenVenta o : orden) {
                /*Se valida el detalle de la entrada para evitar que hayan errores*/
                List<DetalleOrdenVenta> detOrden = detalleOrdenVentaFacade.obtenerDetalleOrdenVenta(o.getDocEntry());

                if (detOrden != null && !detOrden.isEmpty()) {
                    detOrden.stream().map((_item) -> {
                        sb.append(o.getDocNum());
                        return _item;
                    }).forEach((_item) -> {
                        sb.append(", ");
                    });
                }
            }

            if (sb.length() > 0) {
                sb.deleteCharAt(sb.length() - 1);
                sb.deleteCharAt(sb.length() - 1);
                /*Se mandara notificacion de inconcistencia en las entradas*/
                enviarNotificacion("CANCELAR ORDEN DE VENTA", "No se puede cerrar la orden de venta necesaria para la nota credito, debido a que se encontraron mas de una orden de venta asociadas a la factura <b>"
                        + nroFactura + "</b>; ordenes de venta: <b>" + sb.toString() + "</b>");
                throw new OrderServiceException("Se encontraron inconcistencias al cancelar la orden de venta de la factura " + nroFactura);
            }

            throw new OrderServiceException("Se encontraron mas de una orden de venta, se debe verificar.");
        }
    }

    private void enviarNotificacion(String nombreProceso, String msgError) {
        //Enviar por correo el detalle con la repuesta
        MailMessageDTO msg = new MailMessageDTO();

        msg.setFrom("Error Sonda Documentos <notificaciones@matisses.co>");
        msg.setSubject("Error Sonda Documentos");
        msg.setTemplateName("error");
        msg.addToAddress(applicationMBean.getDestinatariosPlantillaEmail().get("error").getTo().get(0));

        Map<String, String> params = new HashMap<>();

        params.put("errorMessage", msgError);
        params.put("processName", nombreProceso);
        params.put("username", "Sonda");
        msg.setParams(params);

        emailREST.sendMail(msg);
    }
}
