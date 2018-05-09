package co.matisses.bcs.rest;

import co.matisses.bcs.dto.MailMessageDTO;
import co.matisses.bcs.dto.ResponseDTO;
import co.matisses.bcs.mbean.BCSApplicationMBean;
import co.matisses.bcs.mbean.BCSGenericMBean;
import co.matisses.bcs.mbean.CalculoEstadistico;
import co.matisses.persistence.dwb.entity.EstadoInforme;
import co.matisses.persistence.dwb.entity.FiltrosInforme;
import co.matisses.persistence.dwb.entity.InformeRotacion;
import co.matisses.persistence.dwb.entity.Rotacion;
import co.matisses.persistence.dwb.facade.EstadoInformeFacade;
import co.matisses.persistence.dwb.facade.FiltrosInformeFacade;
import co.matisses.persistence.dwb.facade.InformeRotacionFacade;
import co.matisses.persistence.dwb.facade.RotacionFacade;
import co.matisses.persistence.dwb.facade.RotacionImportacionFacade;
import co.matisses.persistence.sap.dto.FiltroDTO;
import co.matisses.persistence.sap.entity.BaruSubgrupo;
import co.matisses.persistence.sap.entity.GrupoItems;
import co.matisses.persistence.sap.entity.ItemInventario;
import co.matisses.persistence.sap.entity.SaldoItemInventario;
import co.matisses.persistence.sap.facade.BaruSubgrupoFacade;
import co.matisses.persistence.sap.facade.GrupoItemsFacade;
import co.matisses.persistence.sap.facade.ItemInventarioFacade;
import co.matisses.persistence.sap.facade.PrecioVentaItemFacade;
import co.matisses.persistence.sap.facade.SaldoItemInventarioFacade;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author ygil
 */
@Stateless
@Path("informerotacion")
public class InformeRotacionREST implements Serializable {

    @Inject
    private BCSGenericMBean genericMBean;
    @Inject
    private BCSApplicationMBean applicationMBean;
    private static final Logger CONSOLE = Logger.getLogger(InformeRotacionREST.class.getSimpleName());
    @EJB
    private InformeRotacionFacade informeRotacionFacade;
    @EJB
    private FiltrosInformeFacade filtrosInformeFacade;
    @EJB
    private RotacionFacade rotacionFacade;
    @EJB
    private RotacionImportacionFacade rotacionImportacionFacade;
    @EJB
    private SaldoItemInventarioFacade saldoItemInventarioFacade;
    @EJB
    private ItemInventarioFacade itemInventarioFacade;
    @EJB
    private PrecioVentaItemFacade precioVentaItemFacade;
    @EJB
    private EstadoInformeFacade estadoInformeFacade;
    @EJB
    private SendHtmlEmailREST emailREST;
    @EJB
    private BaruSubgrupoFacade baruSubgrupoFacade;
    @EJB
    private GrupoItemsFacade grupoItemsFacade;

    @GET
    @Path("procesarinformerotacion")
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response procesarInformeRotacion() {
        InformeRotacion informe = informeRotacionFacade.obtenerInformeProcesar();

        if (informe != null && informe.getIdInforme() != null && informe.getIdInforme() != 0) {
            /*Se marca el informe como en proceso*/
            informe.setEstado(new EstadoInforme(2));

            try {
                informeRotacionFacade.edit(informe);
                CONSOLE.log(Level.INFO, "Se marco el informe de rotacion con id {0}, como EN PROCESO", informe.getIdInforme());
            } catch (Exception e) {
            }

            List<FiltrosInforme> filtros = filtrosInformeFacade.obtenerInforme(informe.getIdInforme());
            HashMap<FiltroDTO, List<String>> mapaFiltros = validarFiltros(filtros);

            //Invocar creacion de la fachada
            List<Object> datos = rotacionFacade.crearReporte(mapaFiltros);

            for (Object dato : datos) {
                int columna = 0;
                Object[] columnas = (Object[]) dato;

                String referencia = (String) columnas[columna++];
                Integer comprado = (Integer) columnas[columna++];
                Integer entrada = (Integer) columnas[columna++];
                Integer entradaTaller = (Integer) columnas[columna++];
                Integer otraEntrada = (Integer) columnas[columna++];
                Date fechaCompra = (Date) columnas[columna++];
                Date fechaCierre = (Date) columnas[columna++];
                Integer diasStock = (Integer) columnas[columna++];
                Integer maxDiasStock = (Integer) columnas[columna++];
                Integer pendiente = (Integer) columnas[columna++];
                Integer vendido = (Integer) columnas[columna++];
                Integer baja = (Integer) columnas[columna++];
                Date fechaUltimaVenta = (Date) columnas[columna++];
                BigDecimal utilidadMinima = (BigDecimal) columnas[columna++];
                BigDecimal utilidadPromedio = (BigDecimal) columnas[columna++];
                Integer totalComprado = (Integer) columnas[columna++];

                Rotacion rotacion = new Rotacion();

                rotacion.setReferencia(referencia);
                rotacion.setRefCorta(genericMBean.convertirARefCorta(referencia));
                rotacion.setCantidadComprada(comprado);
                rotacion.setCantidadVendida(vendido);
                rotacion.setCantidadEntradaNeta(totalComprado - baja);
                rotacion.setCantidadSalida(baja);
                rotacion.setFechaUltimaVenta(fechaUltimaVenta);
                rotacion.setRotacionPorcentaje(new BigDecimal(((float) vendido) / ((float) comprado + entrada + entradaTaller + otraEntrada)));
                rotacion.setEntradasTaller(entradaTaller);

                if (vendido > 0 && diasStock >= maxDiasStock) {
                    float valorRotacion = (float) diasStock / (float) vendido;
                    if ((int) (valorRotacion * 100) == 0) {
                        valorRotacion = 0;
                    }

                    rotacion.setRotacionPromedioDias(new BigDecimal(valorRotacion));

                    List<Float> diasRotacionPromedioReferencia = rotacionImportacionFacade.obtenerDiasRotacionPromedioReferencia(referencia);
                    CalculoEstadistico promedioAvanzado = new CalculoEstadistico(diasRotacionPromedioReferencia);
                    promedioAvanzado.calcularValoresBoxPlot();

                    rotacion.setRotacionPromedioDiasAvanz(new BigDecimal(promedioAvanzado.getPromedioInterno()));
                } else {
                    rotacion.setRotacionPromedioDias(new BigDecimal(maxDiasStock));
                    rotacion.setRotacionPromedioDiasAvanz(rotacion.getRotacionPromedioDias());
                }

                rotacion.setSaldoVenta(pendiente);
                rotacion.setTotalComprado(totalComprado);
                rotacion.setUtilidadMinima(utilidadMinima);
                rotacion.setUtilidadPromedio(utilidadPromedio);

                List<Float> utilidadPromedioReferencia = rotacionImportacionFacade.obtenerUtilidadPromedioReferencia(referencia);
                CalculoEstadistico utilidadAvanzada = new CalculoEstadistico(utilidadPromedioReferencia);
                utilidadAvanzada.calcularValoresBoxPlot();
                rotacion.setUtilidadAvanzada(new BigDecimal(utilidadAvanzada.getPromedioInterno()));

                //Consulta en SAP los saldos para los almacenes tipo taller, suma las cantidades y las agrega a reporte
                List<SaldoItemInventario> saldosTaller = saldoItemInventarioFacade.findWithParameters(referencia, "99", false);
                int saldoTaller = 0;
                for (SaldoItemInventario saldo : saldosTaller) {
                    saldoTaller += saldo.getOnHand().intValue();
                }
                rotacion.setSaldoTaller(saldoTaller);

                //Consulta en SAP los saldos para los almacenes de garantia (taller clientes), suma las cantidades y las agrega a reporte
                List<SaldoItemInventario> saldosGarantia = saldoItemInventarioFacade.findWithParameters(referencia, "CL99", false);
                int saldoGarantia = 0;
                for (SaldoItemInventario saldo : saldosGarantia) {
                    saldoGarantia += saldo.getOnHand().intValue();
                }
                rotacion.setSaldoGarantia(saldoGarantia);
                //Consulta en la BD de SAP la informacion completa del articulo para setear los valores correspondientes
                ItemInventario item = itemInventarioFacade.find(referencia);

                if (item == null) {
                    CONSOLE.log(Level.SEVERE, "La referencia {0} no se incluye porque el item no existe, no tiene departamento o subgrupo", referencia);
                    continue;
                }

                GrupoItems grupo = grupoItemsFacade.obtenerGrupo(item.getItmsGrpCod().toString());

                if (grupo == null) {
                    CONSOLE.log(Level.SEVERE, "La referencia {0} no se incluye porque el item no tiene grupo", referencia);
                    continue;
                }

                BaruSubgrupo subgrupo = baruSubgrupoFacade.obtenerSubgrupo(item.getUSubGrupo());

                if (subgrupo == null) {
                    CONSOLE.log(Level.SEVERE, "La referencia {0} no se incluye porque el item no tiene subgrupo", referencia);
                    continue;
                }

                rotacion.setCodDpto(grupo.getItmsGrpCod());
                rotacion.setNomDpto(grupo.getItmsGrpNam());
                rotacion.setCodGrupo(subgrupo.getUCodGrupo());
                rotacion.setNomGrupo(subgrupo.getUNomGrupo());
                rotacion.setCodSubGrupo(subgrupo.getCode());
                rotacion.setNomSubGrupo(subgrupo.getName());
                rotacion.setRefProv(item.getUURefPro());
                rotacion.setRefAduana(item.getUURefAduana());
                rotacion.setDescripcion(item.getItemName());
                rotacion.setModelo(item.getUmodelo());
                rotacion.setNombreWeb(item.getUUNomWeb());
                rotacion.setProveedor(referencia.substring(0, 3));

                rotacion.setIdInforme(informe.getIdInforme());

                //Consulta el precio del articulo de la lista de precios #1, sin IVA
                Integer precio = precioVentaItemFacade.findByItemCode(referencia, false).intValue();
                rotacion.setPrecioSinIva(precio);

                /*
                 *  [0] referencia, 
                 *  [1] sum(cantidad_compra) comprado, 
                 *  [2] min(fecha_compra) fecha_compra, 
                 *  [3] case when max(isnull(fecha_cierre,CONVERT(DATE,'31/12/2199',103))) = CONVERT(DATE,'31/12/2199',103) then NULL else max(isnull(fecha_cierre,CONVERT(DATE,'31/12/2199',103))) end fecha_cierre, 
                 *  [4] avg(dias_stock) promedio_dias, 
                 *  [5] sum(saldo_pendiente) pendiente, 
                 *  [6] sum(cantidad_venta) vendido, 
                 *  [7] sum(cantidad_baja) bajas, 
                 *  [8] max(fecha_ultima_venta) ultima_venta
                 *  [9] (select SUM(cantidad_compra) from rotacionimportacion r where r.referencia = rotacion.referencia) total_comprado (historico)
                 * [10] ISNULL(MIN(utilidad_minima),0) utilidad_minima
                 * [11] ISNULL(AVG(utilidad_promedio),0) utilidad_promedio 
                 * 
                 */
                CONSOLE.log(Level.INFO, rotacion.toString());
                rotacionFacade.create(rotacion);
            }

            //marcar informe como finalizado
            EstadoInforme estado = estadoInformeFacade.find(3);
            informe.setEstado(estado);

            informeRotacionFacade.edit(informe);
            //enviar email de notificacion
            enviarNotificacion(informe);

            return Response.ok(new ResponseDTO(0, "Se finalizo el informe de rotacion con id " + informe.getIdInforme())).build();
        }

        return Response.ok(new ResponseDTO(1, "No se pudo procesar ningun informe de rotacion")).build();
    }

    private HashMap<FiltroDTO, List<String>> validarFiltros(List<FiltrosInforme> filtros) {
        HashMap<FiltroDTO, List<String>> mapa = new HashMap<>();

        filtros.stream().forEach((f) -> {
            FiltroDTO dto = new FiltroDTO();

            dto.setCodigoColumna(f.getFiltro().getCodigoColumna());
            dto.setFachada(f.getFiltro().getFachada());
            dto.setFormato(f.getFiltro().getFormato());
            dto.setIdFiltro(f.getFiltro().getIdFiltro());
            dto.setMetodo(f.getFiltro().getMetodo());
            dto.setMultiplesValores(f.getFiltro().isMultiplesValores());
            dto.setNombre(f.getFiltro().getNombre());
            dto.setSufijo(f.getFiltro().getSufijo());
            dto.setTamanoMaximo(f.getFiltro().getTamanoMaximo());
            dto.setTamanoMinimo(f.getFiltro().getTamanoMinimo());
            dto.setTipo(f.getFiltro().getTipo());
            dto.setTipoReferencia(f.getFiltro().isTipoReferencia());
            dto.setTipoRetorno(f.getFiltro().getTipoRetorno());

            if (mapa.containsKey(dto)) {
                mapa.get(dto).add(f.getValor());
            } else {
                List<String> primerValor = new ArrayList<>();

                primerValor.add(f.getValor());
                mapa.put(dto, primerValor);
            }
        });

        return mapa;
    }

    private void enviarNotificacion(InformeRotacion informe) {
        MailMessageDTO msg = new MailMessageDTO();

        msg.setFrom("Informe rotación <notificaciones@matisses.co>");
        msg.setSubject("Informe rotación #" + informe.getIdInforme());
        msg.setTemplateName("informe_rotacion");
        msg.addToAddress(informe.getAutor() + "@matisses.co");

        Map<String, String> params = new HashMap<>();

        params.put("idInforme", informe.getIdInforme().toString());
        msg.setParams(params);

        emailREST.sendMail360(msg);
    }
}
