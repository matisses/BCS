package co.matisses.bcs.rest;

import co.matisses.persistence.dwb.entity.Entradas;
import co.matisses.persistence.dwb.entity.RotacionImportacion;
import co.matisses.persistence.dwb.entity.RotacionImportacionPK;
import co.matisses.persistence.dwb.entity.VentasNetas;
import co.matisses.persistence.dwb.facade.EntradasFacade;
import co.matisses.persistence.dwb.facade.RotacionImportacionFacade;
import co.matisses.persistence.dwb.facade.VentasNetasFacade;
import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
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
@Path("tiemporotacion")
public class TiempoRotacionREST implements Serializable {

    private static final Logger CONSOLE = Logger.getLogger(TiempoRotacionREST.class.getSimpleName());
    @EJB
    private RotacionImportacionFacade rotacionImportacionFacade;
    @EJB
    private EntradasFacade entradasFacade;
    @EJB
    private VentasNetasFacade ventasNetasFacade;

    public TiempoRotacionREST() {
    }

    private List<Date> getAllDates() {
        GregorianCalendar date = new GregorianCalendar(2007, 0, 1);
        GregorianCalendar today = new GregorianCalendar();
        List<Date> dates = new ArrayList<>();

        while (date.before(today)) {
            dates.add(date.getTime());
            date.add(Calendar.DAY_OF_MONTH, 1);
        }

        return dates;
    }

    @POST
    @Path("calcularrotacion/{log}")
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    @Consumes({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response calcularRotacion(@PathParam("log") boolean log, List<String> referencias) {
        CONSOLE.log(Level.INFO, "Calculando rotacion para {0} referencias", ((referencias != null && !referencias.isEmpty()) ? referencias.size() : "todas las"));

        StringBuilder sb = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        List<Date> relevantDates = null;
        Map<String, List<Integer>> colaSaldos = new HashMap<>();

        sb.append("<b>").append(sdf2.format(new Date())).append("</b>").append(" Calculando rotacion para ");
        sb.append((referencias != null && !referencias.isEmpty()) ? referencias.size() : "todas las").append(" referencias<br>");

        if (referencias == null || referencias.isEmpty()) {
            rotacionImportacionFacade.eliminarTodo();
            relevantDates = getAllDates();
        } else {
            rotacionImportacionFacade.eliminarReferencias(referencias);
            relevantDates = entradasFacade.obtenerFechasRelevantes(referencias);
        }

        CONSOLE.log(Level.INFO, "Procesando {0} dias", relevantDates.size());
        sb.append("<b>").append(sdf2.format(new Date())).append("</b>").append(" Procesando ").append(relevantDates.size()).append(" dias<br>");

        for (Date currentDate : relevantDates) {
            List<Entradas> entradas;

            if (referencias == null || referencias.isEmpty()) {
                entradas = entradasFacade.obtenerEntradasFecha(currentDate);
            } else {
                entradas = entradasFacade.obtenerEntradasReferencias(currentDate, referencias);
            }

            CONSOLE.log(Level.FINE, "Se encontraron {0} entradas", entradas.size());

            //Crea nuevos registros de RotacionImportacion por cada elemento de la lista de entradas para la fecha
            for (Entradas entrada : entradas) {
                if (colaSaldos.containsKey(entrada.getReferencia())) {
                    if (entrada.getCantidad().intValue() > 0) {
                        //Valida si la primera entrada es mayor que cero. En caso de ser menor es un saldo negativo pendiente por cerrar
                        if (colaSaldos.get(entrada.getReferencia()).get(0) > 0) {
                            //Es una nueva entrada que debe ser registrda en la base de datos y en el mapa de pendientes
                            colaSaldos.get(entrada.getReferencia()).add(entrada.getCantidad().intValue());

                            RotacionImportacionPK pk = new RotacionImportacionPK(entrada.getReferencia(), rotacionImportacionFacade.obtenerSiguienteID(entrada.getReferencia()));
                            RotacionImportacion rotacion = new RotacionImportacion(pk);

                            rotacion.setCantidadBaja(0);
                            rotacion.setCantidadCompra(entrada.getCantidad().intValue());
                            rotacion.setCantidadVenta(0);
                            rotacion.setFechaCompra(entrada.getFecha());
                            rotacion.setSaldoPendiente(entrada.getCantidad().intValue());
                            rotacion.setTipo(entrada.getTipo());

                            try {
                                rotacionImportacionFacade.create(rotacion);
                                CONSOLE.log(Level.FINE, "Se crea el registro de rotacion {0}", rotacion.toString());
                            } catch (Exception e) {
                                CONSOLE.log(Level.SEVERE, "Ocurrio un error al crear el registro de rotacion. ", e);
                            }
                        } else {
                            //Existe un saldo negativo pendiente por cruzar, por lo que se debe actualizar el saldo con esta entrada
                            int pendiente = colaSaldos.get(entrada.getReferencia()).get(0);
                            int cantidad = entrada.getCantidad().intValue();

                            RotacionImportacion rotacion = rotacionImportacionFacade.obtenerPrimeraImportacionAbierta(entrada.getReferencia());

                            if (pendiente + cantidad == 0) {
                                CONSOLE.log(Level.FINE, "Procesando NEGATIVO entrada pendiente = cantidad");

                                //Eliminar el registro de la lista de saldos pendientes para la referencia
                                colaSaldos.get(entrada.getReferencia()).remove(0);

                                //Si la lista queda vacia, tambien se elimina la referencia del mapa de saldos pendientes
                                if (colaSaldos.get(entrada.getReferencia()).isEmpty()) {
                                    colaSaldos.remove(entrada.getReferencia());
                                }

                                //Actualizar datos del registro en la base de datos
                                rotacion.setFechaCierre(entrada.getFecha());
                                rotacion.setCantidadCompra(entrada.getCantidad().intValue());
                                rotacion.setSaldoPendiente(pendiente + cantidad);
                                rotacion.setDiasStock((int) obtenerDiferenciaDias(rotacion.getFechaCompra(), rotacion.getFechaCierre()));

                                cantidad = 0;
                            } else if (pendiente + cantidad > 0) {
                                CONSOLE.log(Level.FINE, "Procesando entrada pendiente > cantidad");

                                //Actualizar el registro de la lista de saldos pendientes para la referencia
                                colaSaldos.get(entrada.getReferencia()).set(0, pendiente + cantidad);

                                //Actualizar datos del registro en la base de datos
                                if (cantidad > 0) {
                                    rotacion.setCantidadCompra(Math.abs(cantidad));
                                } else {
                                    rotacion.setCantidadBaja(rotacion.getCantidadBaja() + Math.abs(cantidad));
                                }

                                rotacion.setSaldoPendiente(pendiente + cantidad);
                                cantidad = 0;
                            } else {
                                CONSOLE.log(Level.FINE, "Procesando entrada pendiente < cantidad");

                                if (pendiente > 0) {
                                    //Eliminar el registro de la lista de saldos pendientes para la referencia
                                    colaSaldos.get(entrada.getReferencia()).remove(0);

                                    //Si la lista queda vacia, tambien se elimina la referencia del mapa de saldos pendientes
                                    if (colaSaldos.get(entrada.getReferencia()).isEmpty()) {
                                        colaSaldos.remove(entrada.getReferencia());
                                    }

                                    //Actualizar datos del registro en base de datos
                                    rotacion.setFechaCierre(entrada.getFecha());
                                    rotacion.setCantidadBaja(rotacion.getCantidadBaja() + pendiente);
                                    rotacion.setSaldoPendiente(0);
                                    rotacion.setDiasStock((int) obtenerDiferenciaDias(rotacion.getFechaCompra(), entrada.getFecha()));

                                    cantidad += pendiente;
                                } else if (Math.abs(pendiente) < cantidad) {
                                    //Eliminar ek registro de la lista de saldos pendientes para la referencia
                                    colaSaldos.get(entrada.getReferencia()).remove(0);

                                    //Si la lista queda vacia, tambien se elimina la referencia del mapa de saldos pendientes
                                    if (colaSaldos.get(entrada.getReferencia()).isEmpty()) {
                                        colaSaldos.remove(entrada.getReferencia());
                                    }

                                    rotacion.setFechaCierre(entrada.getFecha());
                                    rotacion.setDiasStock((int) obtenerDiferenciaDias(rotacion.getFechaCompra(), rotacion.getFechaCierre()));
                                    rotacion.setSaldoPendiente(0);
                                } else {
                                    //Actualizar el registro de la lista de saldos pendientes para la referencia
                                    colaSaldos.get(entrada.getReferencia()).set(0, pendiente + cantidad);
                                    rotacion.setSaldoPendiente(pendiente + cantidad);
                                }

                                rotacionImportacionFacade.edit(rotacion);
                            }
                        }
                    } else {
                        //Se trata de una salida y se debe restar hasta que se cumpla la cantidad total
                        int cantidad = entrada.getCantidad().intValue();

                        while (cantidad < 0) {
                            int pendiente = colaSaldos.get(entrada.getReferencia()).get(0);

                            RotacionImportacion rotacion = rotacionImportacionFacade.obtenerPrimeraImportacionAbierta(entrada.getReferencia());

                            if (pendiente + cantidad == 0) {
                                CONSOLE.log(Level.FINE, "Procesando entrada pendiente = cantidad");

                                //Eliminar el registro de la lista de saldos pendientes para la referencia
                                colaSaldos.get(entrada.getReferencia()).remove(0);

                                //Si la lista queda vacia, tambien se elimina la referencia del mapa de saldos pendientes
                                if (colaSaldos.get(entrada.getReferencia()).isEmpty()) {
                                    colaSaldos.remove(entrada.getReferencia());
                                }

                                //Actualizar datos del registro en la base de datos
                                if (rotacion.getFechaUltimaVenta() != null) {
                                    rotacion.setFechaCierre(rotacion.getFechaUltimaVenta());
                                } else {
                                    rotacion.setFechaCierre(rotacion.getFechaCompra());
                                }

                                rotacion.setCantidadBaja(rotacion.getCantidadBaja() + Math.abs(cantidad));
                                rotacion.setSaldoPendiente(pendiente + cantidad);
                                rotacion.setDiasStock((int) obtenerDiferenciaDias(rotacion.getFechaCompra(), rotacion.getFechaCierre()));

                                cantidad = 0;
                            } else if (pendiente + cantidad > 0) {
                                CONSOLE.log(Level.FINE, "Procesando entrada pendiente > cantidad");

                                //Actualizar el registro de la lista de saldos pendientes para la referencia
                                colaSaldos.get(entrada.getReferencia()).set(0, pendiente + cantidad);

                                //Actualizar datos del registro en la base de datos
                                rotacion.setCantidadBaja(rotacion.getCantidadBaja() + Math.abs(cantidad));
                                rotacion.setSaldoPendiente(pendiente + cantidad);

                                cantidad = 0;
                            } else {
                                CONSOLE.log(Level.FINE, "Procesando entrada pendiente < cantidad");

                                //Eliminar el registro de la lista de saldos pendientes para la referencia
                                colaSaldos.get(entrada.getReferencia()).remove(0);

                                //Si la lista queda vacia, tambien se elimina la referencia del mapa de saldos pendientes
                                if (colaSaldos.get(entrada.getReferencia()).isEmpty()) {
                                    colaSaldos.remove(entrada.getReferencia());
                                }

                                //Actualizar datos del registro en la base de datos
                                if (rotacion.getFechaUltimaVenta() != null) {
                                    rotacion.setFechaCierre(rotacion.getFechaUltimaVenta());
                                } else {
                                    rotacion.setFechaCierre(rotacion.getFechaCompra());
                                }

                                rotacion.setCantidadBaja(rotacion.getCantidadBaja() + pendiente);
                                rotacion.setSaldoPendiente(0);
                                rotacion.setDiasStock((int) obtenerDiferenciaDias(rotacion.getFechaCompra(), rotacion.getFechaCierre()));

                                cantidad += pendiente;

                                //Si ya no quedan saldos pendientes en el mapa, se debe crear una entrada negativa con la cantidad pendiente
                                if (!colaSaldos.containsKey(entrada.getReferencia())) {
                                    List<Integer> saldos = new ArrayList<>();

                                    saldos.add(cantidad);
                                    colaSaldos.put(entrada.getReferencia(), saldos);

                                    RotacionImportacionPK pk = new RotacionImportacionPK(entrada.getReferencia(), rotacionImportacionFacade.obtenerSiguienteID(entrada.getReferencia()));
                                    RotacionImportacion nuevo = new RotacionImportacion(pk);

                                    nuevo.setCantidadBaja(cantidad);
                                    nuevo.setFechaCompra(entrada.getFecha());
                                    nuevo.setSaldoPendiente(cantidad);
                                    nuevo.setTipo(entrada.getTipo());

                                    try {
                                        rotacionImportacionFacade.create(nuevo);
                                        CONSOLE.log(Level.FINE, "Se crea el registro de rotacion {0}", nuevo.toString());

                                        cantidad = 0;
                                    } catch (Exception e) {
                                        CONSOLE.log(Level.SEVERE, "Ocurrio un error al crear el registro de rotacion. ", e);
                                    }
                                }
                            }

                            //Actualizar registro en la base de datos
                            rotacionImportacionFacade.edit(rotacion);
                            CONSOLE.log(Level.FINE, "Se modifica el registro de rotacion por entrada {0}", rotacion.toString());
                        }
                    }
                } else if (entrada.getCantidad().intValue() > 0) {
                    //Se agrega una entrada para una referencia que no tiene saldo pendiente en el mapa de saldos
                    List<Integer> saldos = new ArrayList<>();

                    saldos.add(entrada.getCantidad().intValue());
                    colaSaldos.put(entrada.getReferencia(), saldos);

                    RotacionImportacionPK pk = new RotacionImportacionPK(entrada.getReferencia(), rotacionImportacionFacade.obtenerSiguienteID(entrada.getReferencia()));
                    RotacionImportacion rotacion = new RotacionImportacion(pk);

                    rotacion.setCantidadBaja(0);
                    rotacion.setCantidadCompra(entrada.getCantidad().intValue());
                    rotacion.setCantidadVenta(0);
                    rotacion.setDiasStock(0);
                    rotacion.setFechaCompra(entrada.getFecha());
                    rotacion.setSaldoPendiente(entrada.getCantidad().intValue());
                    rotacion.setTipo(entrada.getTipo());

                    try {
                        rotacionImportacionFacade.create(rotacion);
                        CONSOLE.log(Level.FINE, "Se crea el registro de rotacion {0}", rotacion.toString());
                    } catch (Exception e) {
                        CONSOLE.log(Level.SEVERE, "Ocurrio un error al crear el registro de rotacion. ", e);
                    }
                } else {
                    StringBuilder sb2 = new StringBuilder();

                    sb2.append("Se esta intentando realizar una salida de un producto que no tiene entrada. REF");
                    sb2.append("[");
                    sb2.append(entrada.getReferencia());
                    sb2.append("]");
                    sb2.append(" CANT");
                    sb2.append("[");
                    sb2.append(entrada.getCantidad().intValue());
                    sb2.append("]");
                    sb2.append(" FECHA");
                    sb2.append("[");
                    sb2.append(sdf.format(entrada.getFecha()));
                    sb2.append("]");

                    CONSOLE.log(Level.INFO, sb2.toString());
                    if (log) {
                        sb.append("<b>").append(sdf2.format(new Date())).append("</b> ").append(sb2.toString());
                    }

                    //Es una salida sin entrada que debe ser registrada en la base de datos y en el mapa de pendientes con saldo negativo
                    List<Integer> saldos = new ArrayList<>();
                    saldos.add(-1 * entrada.getCantidad().intValue());
                    colaSaldos.put(entrada.getReferencia(), saldos);

                    RotacionImportacionPK pk = new RotacionImportacionPK(entrada.getReferencia(), rotacionImportacionFacade.obtenerSiguienteID(entrada.getReferencia()));
                    RotacionImportacion rotacion = new RotacionImportacion(pk);

                    rotacion.setCantidadBaja(Math.abs(entrada.getCantidad().intValue()));
                    rotacion.setCantidadCompra(0);
                    rotacion.setCantidadVenta(0);
                    rotacion.setDiasStock(0);
                    rotacion.setFechaCompra(entrada.getFecha());
                    rotacion.setSaldoPendiente(entrada.getCantidad().intValue());
                    rotacion.setTipo(entrada.getTipo());

                    try {
                        rotacionImportacionFacade.create(rotacion);
                        CONSOLE.log(Level.FINE, "Se crea el registro de rotacion {0}", rotacion.toString());
                    } catch (Exception e) {
                        CONSOLE.log(Level.SEVERE, "Ocurrio un error al crear el registro de rotacion. ", e);
                    }
                }
            }

            //Eliminar los datos de entradas para liberar memoria
            entradas = null;

            //Cargar listado de ventas para el dia
            List<VentasNetas> ventasNetas;

            if (referencias == null || referencias.isEmpty()) {
                ventasNetas = ventasNetasFacade.obtenerVentasFecha(currentDate);
            } else {
                ventasNetas = ventasNetasFacade.obtenerVentasFechaReferencia(currentDate, referencias);
            }

            CONSOLE.log(Level.FINE, "Se encontraron {0} ventas", ventasNetas.size());
            for (VentasNetas ventas : ventasNetas) {
                if (colaSaldos.containsKey(ventas.getReferencia())) {
                    int cantidad = ventas.getCantidad().intValue();

                    while (cantidad > 0 && colaSaldos.containsKey(ventas.getReferencia())) {
                        int pendiente = colaSaldos.get(ventas.getReferencia()).get(0);

                        RotacionImportacion rotacion = rotacionImportacionFacade.obtenerPrimeraImportacionAbierta(ventas.getReferencia());

                        if (pendiente - cantidad == 0) {
                            CONSOLE.log(Level.FINE, "Procesando venta pendiente = cantidad");

                            //Eliminar el registro de la lista de saldos pendientes para la referencia
                            colaSaldos.get(ventas.getReferencia()).remove(0);

                            //Si la lista queda vacia, tambien se elimina la referencia del mapa de saldos pendientes
                            if (colaSaldos.get(ventas.getReferencia()).isEmpty()) {
                                colaSaldos.remove(ventas.getReferencia());
                            }

                            //Actualizar datos del registro en la base de datos
                            rotacion.setFechaUltimaVenta(ventas.getFecha());
                            rotacion.setFechaCierre(ventas.getFecha());
                            rotacion.setCantidadVenta(rotacion.getCantidadVenta() + cantidad);
                            rotacion.setSaldoPendiente(pendiente - cantidad);
                            rotacion.setDiasStock((int) obtenerDiferenciaDias(rotacion.getFechaCompra(), ventas.getFecha()));

                            cantidad = 0;
                        } else if (pendiente - cantidad > 0) {
                            CONSOLE.log(Level.FINE, "Procesando ventas pendientes > cantidad");

                            //Actualizar el registro de la lista de saldos pendientes para la referencia
                            colaSaldos.get(ventas.getReferencia()).set(0, pendiente - cantidad);

                            //Actualizar datos del registro de la base de datos
                            rotacion.setCantidadVenta(rotacion.getCantidadVenta() + cantidad);
                            rotacion.setSaldoPendiente(pendiente - cantidad);
                            rotacion.setFechaUltimaVenta(ventas.getFecha());

                            cantidad = 0;
                        } else {
                            CONSOLE.log(Level.FINE, "Procesando ventas pendientes < cantidad");

                            if (pendiente > 0) {
                                //Eliminar el registro de la lista de saldos pendientes para la referencia
                                colaSaldos.get(ventas.getReferencia()).remove(0);

                                //Si la lista queda vacia, tambien se elimina la referencia del mapa de saldos pendientes
                                if (colaSaldos.get(ventas.getReferencia()).isEmpty()) {
                                    colaSaldos.remove(ventas.getReferencia());
                                }

                                //Actualizar datos del registro en base de datos
                                rotacion.setFechaUltimaVenta(ventas.getFecha());
                                rotacion.setFechaCierre(ventas.getFecha());
                                rotacion.setCantidadVenta(rotacion.getCantidadVenta() + pendiente);
                                rotacion.setSaldoPendiente(0);
                                rotacion.setDiasStock((int) obtenerDiferenciaDias(rotacion.getFechaCompra(), ventas.getFecha()));

                                cantidad = cantidad - pendiente;
                            } else {
                                //Actualizar el registro de la lista de saldos pendientes para la referencia
                                colaSaldos.get(ventas.getReferencia()).set(0, pendiente - cantidad);

                                //Actualizar datos del registro en la base de datos
                                rotacion.setFechaUltimaVenta(ventas.getFecha());
                                rotacion.setCantidadVenta(rotacion.getCantidadVenta() + cantidad);
                                rotacion.setSaldoPendiente(rotacion.getSaldoPendiente() - cantidad);

                                cantidad = 0;
                            }
                        }

                        //Validar el valor de la utilidad minima, si es nulo o mayor que el actual, se modifica
                        if (rotacion.getUtilidadMinima() == null || rotacion.getUtilidadMinima().floatValue() > ventas.getPorcentajeUtilidad().floatValue()) {
                            rotacion.setUtilidadMinima(ventas.getPorcentajeUtilidad());
                        }

                        //Acumular el valor de la utilidad para poder calcular el promedio
                        float utilidadCantidadVendida = ventas.getPorcentajeUtilidad().floatValue() * ventas.getCantidad().floatValue();
                        if (rotacion.getSumaUtilidades() == null) {
                            rotacion.setSumaUtilidades(new BigDecimal(utilidadCantidadVendida));
                        } else {
                            rotacion.setSumaUtilidades(new BigDecimal(rotacion.getSumaUtilidades().floatValue() + (utilidadCantidadVendida)));
                        }

                        //Incrementar el contador de salidas para poder calcular el promedio
                        if (rotacion.getNroSalidas() == null) {
                            rotacion.setNroSalidas(ventas.getCantidad().intValue());
                        } else {
                            rotacion.setNroSalidas(rotacion.getNroSalidas() + ventas.getCantidad().intValue());
                        }

                        //Calcular el valor de la utilidad promedio
                        if (rotacion.getNroSalidas() == 1) {
                            rotacion.setUtilidadPromedio(ventas.getPorcentajeUtilidad());
                        } else {
                            rotacion.setUtilidadPromedio(new BigDecimal(rotacion.getSumaUtilidades().floatValue() / rotacion.getNroSalidas()));
                        }

                        //Actualizar registro en la base de datos
                        CONSOLE.log(Level.INFO, "Modificando el registro de rotacion {0}", rotacion.toString());
                        if (log) {
                            sb.append("<b>").append(sdf2.format(new Date())).append("</b>").append(" Modificando registro de rotacion ").append(rotacion.toString()).append("<br>");
                        }

                        try {
                            rotacionImportacionFacade.edit(rotacion);
                            CONSOLE.log(Level.FINE, "Se modifica el registro de rotacion por venta {0}", rotacion.toString());
                        } catch (Exception e) {
                            CONSOLE.log(Level.SEVERE, "Ocurrio un error al modificar la rotacion por venta. ", e);
                        }
                    }
                } else {
                    StringBuilder sb2 = new StringBuilder();

                    sb2.append("Se esta intentando registrar una venta de un producto que no tiene entrada. REF");
                    sb2.append("[");
                    sb2.append(ventas.getReferencia());
                    sb2.append("]");
                    sb2.append(" CANT");
                    sb2.append("[");
                    sb2.append(ventas.getCantidad());
                    sb2.append("]");
                    sb2.append(" FECHA");
                    sb2.append("[");
                    sb2.append(sdf.format(ventas.getFecha()));
                    sb2.append("]");

                    CONSOLE.log(Level.INFO, sb2.toString());
                    if (log) {
                        sb.append("<b>").append(sdf2.format(new Date())).append("</b> ").append(sb2.toString());
                    }

                    //Es una venta sin entrada que debe ser registrada en la base de datos y en el mapa de pendientes con saldo negativo
                    List<Integer> saldos = new ArrayList<>();
                    saldos.add(-1 * ventas.getCantidad().intValue());
                    colaSaldos.put(ventas.getReferencia(), saldos);

                    RotacionImportacionPK pk = new RotacionImportacionPK(ventas.getReferencia(), rotacionImportacionFacade.obtenerSiguienteID(ventas.getReferencia()));
                    RotacionImportacion rotacion = new RotacionImportacion(pk);

                    rotacion.setCantidadBaja(0);
                    rotacion.setCantidadCompra(0);
                    rotacion.setCantidadVenta(ventas.getCantidad().intValue());
                    rotacion.setFechaCompra(ventas.getFecha());
                    rotacion.setSaldoPendiente(-1 * ventas.getCantidad().intValue());
                    rotacion.setFechaUltimaVenta(ventas.getFecha());
                    rotacion.setTipo("N/A");

                    //Validar el valor de la utilidad minima, si es nulo o mayor que la actual, se modifica
                    if (rotacion.getUtilidadMinima() == null || rotacion.getUtilidadMinima().floatValue() > ventas.getPorcentajeUtilidad().floatValue()) {
                        rotacion.setUtilidadMinima(ventas.getPorcentajeUtilidad());
                    }

                    //Acumular el valor de la utilidad para poder calcular el promedio
                    float utilidadCantidadVendida = ventas.getPorcentajeUtilidad().floatValue() * ventas.getCantidad().intValue();
                    if (rotacion.getSumaUtilidades() == null) {
                        rotacion.setSumaUtilidades(new BigDecimal(utilidadCantidadVendida));
                    } else {
                        rotacion.setSumaUtilidades(new BigDecimal(rotacion.getSumaUtilidades().floatValue() + utilidadCantidadVendida));
                    }

                    //Incrementar el contador de salidas para poder calcular el promedio
                    if (rotacion.getNroSalidas() == null) {
                        rotacion.setNroSalidas(ventas.getCantidad().intValue());
                    } else {
                        rotacion.setNroSalidas(rotacion.getNroSalidas() + ventas.getCantidad().intValue());
                    }

                    //Calcular el valor de la utilidad promedio
                    if (rotacion.getNroSalidas() == 1) {
                        rotacion.setUtilidadPromedio(ventas.getPorcentajeUtilidad());
                    } else {
                        rotacion.setUtilidadPromedio(new BigDecimal(rotacion.getSumaUtilidades().floatValue() / rotacion.getNroSalidas()));
                    }

                    try {
                        rotacionImportacionFacade.create(rotacion);
                        CONSOLE.log(Level.FINE, "Se crea el registro de rotacion NEGATIVO {0}", rotacion.toString());
                    } catch (Exception e) {
                        CONSOLE.log(Level.SEVERE, "Ocurrio un error al crear el registro de rotacion. ", e);
                    }
                }
            }
        }

        //Actualizar el valor de diasStock para las entradas que permanecen abiertas
        for (String ref : colaSaldos.keySet()) {
            rotacionImportacionFacade.obtenerImportacionesAbiertas(ref).stream().map((rotacion) -> {
                rotacion.setDiasStock((int) obtenerDiferenciaDias(rotacion.getFechaCompra(), new Date()));
                return rotacion;
            }).forEach((rotacion) -> {
                try {
                    rotacionImportacionFacade.edit(rotacion);
                } catch (Exception e) {
                }
            });
        }

        //Consultar ventas netas en rango de fechas
        CONSOLE.log(Level.INFO, "Proceso finalizado. {0} entradas sin cerrar", colaSaldos.size());
        sb.append("<b>").append(sdf2.format(new Date())).append("</b>").append(" Proceso finalizado. ").append(colaSaldos.size()).append(" entradas sin cerrar<br>");
        colaSaldos = new HashMap<>();

        return Response.ok(sb.toString()).build();
    }

    private long obtenerDiferenciaDias(Date fecha1, Date fecha2) {
        return (fecha2.getTime() - fecha1.getTime()) / 1000 / 60 / 60 / 24;
    }
}
