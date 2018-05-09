package co.matisses.bcs.rest;

import co.matisses.b1ws.dto.SalesDocumentDTO;
import co.matisses.b1ws.dto.SalesDocumentLineDTO;
import co.matisses.b1ws.dto.SalesEmployeeDTO;
import co.matisses.bcs.b1ws.client.creditnotes.CreditNotesREST;
import co.matisses.bcs.b1ws.client.goodsissue.GoodsIssueDTO;
import co.matisses.bcs.b1ws.client.goodsissue.GoodsIssueDetailDTO;
import co.matisses.bcs.b1ws.client.goodsissue.GoodsIssueLocationsDTO;
import co.matisses.bcs.b1ws.client.goodsissue.GoodsIssueREST;
import co.matisses.bcs.b1ws.client.journalentries.JournalEntriesServiceConnector;
import co.matisses.bcs.b1ws.client.journalentries.JournalEntryDTO;
import co.matisses.bcs.b1ws.client.journalentries.JournalEntryLineDTO;
import co.matisses.bcs.b1ws.client.payments.IncomingPaymentREST;
import co.matisses.bcs.dto.AgregarProductoListaResponseDTO;
import co.matisses.bcs.dto.CategoriaListaRegalosDTO;
import co.matisses.bcs.dto.CompraListaRegalosDTO;
import co.matisses.bcs.dto.ConsultaCompraListaRegalosDTO;
import co.matisses.bcs.dto.ConsultaListasDTO;
import co.matisses.bcs.dto.ConsultaListasResponseDTO;
import co.matisses.bcs.dto.ConsultaProductosListaDTO;
import co.matisses.bcs.dto.CrearInvitadoResponseDTO;
import co.matisses.bcs.dto.CrearListaResponseDTO;
import co.matisses.bcs.dto.EstadoListaRegalosDTO;
import co.matisses.bcs.dto.InvitadoDTO;
import co.matisses.bcs.dto.ListaRegalosDTO;
import co.matisses.bcs.dto.MailMessageDTO;
import co.matisses.bcs.dto.PrintReportDTO;
import co.matisses.bcs.dto.ProductoListaRegalosDTO;
import co.matisses.bcs.dto.ProductoListaRegalosInvitadoDTO;
import co.matisses.bcs.dto.ResponseDTO;
import co.matisses.bcs.dto.SesionListaRegalosDTO;
import co.matisses.bcs.dto.TipoEventoDTO;
import co.matisses.persistence.sap.entity.ItemInventario;
import co.matisses.persistence.sap.entity.SaldoItemInventario;
import co.matisses.persistence.sap.entity.SocioDeNegocios;
import co.matisses.persistence.sap.facade.ItemInventarioFacade;
import co.matisses.persistence.sap.facade.SocioDeNegociosFacade;
import co.matisses.persistence.web.entity.CategoriaListaRegalos;
import co.matisses.persistence.web.entity.EstadoListaRegalos;
import co.matisses.persistence.web.entity.ListaRegalos;
import co.matisses.persistence.web.entity.ProductoListaRegalos;
import co.matisses.persistence.web.entity.TipoEvento;
import co.matisses.persistence.web.facade.ListaRegalosFacade;
import co.matisses.persistence.web.facade.ProductoListaRegalosFacade;
import co.matisses.bcs.mbean.BCSApplicationMBean;
import co.matisses.bcs.mbean.BCSGenericMBean;
import co.matisses.bcs.mbean.ImagenProductoMBean;
import co.matisses.bcs.mbean.SAPB1WSBean;
import co.matisses.bcs.poi.ExcelReaderInvitados;
import co.matisses.persistence.sap.entity.BaruDecoradores;
import co.matisses.persistence.sap.entity.DetalleDevolucionSAP;
import co.matisses.persistence.sap.entity.DetalleFacturaSAP;
import co.matisses.persistence.sap.entity.DevolucionSAP;
import co.matisses.persistence.sap.entity.FacturaSAP;
import co.matisses.persistence.sap.entity.ReciboCaja;
import co.matisses.persistence.sap.entity.SaldoUbicacion;
import co.matisses.persistence.sap.facade.BaruDecoradoresFacade;
import co.matisses.persistence.sap.facade.DetalleDevolucionSAPFacade;
import co.matisses.persistence.sap.facade.DetalleFacturaSAPFacade;
import co.matisses.persistence.sap.facade.DevolucionSAPFacade;
import co.matisses.persistence.sap.facade.FacturaSAPFacade;
import co.matisses.persistence.sap.facade.ReciboCajaFacade;
import co.matisses.persistence.sap.facade.SaldoItemInventarioFacade;
import co.matisses.persistence.sap.facade.SaldoUbicacionFacade;
import co.matisses.persistence.sap.facade.SalesPersonFacade;
import co.matisses.persistence.sap.facade.SerieFacade;
import co.matisses.persistence.web.entity.CompraListaRegalos;
import co.matisses.persistence.web.entity.ListaRegaloInvitado;
import co.matisses.persistence.web.entity.UsuarioPagina;
import co.matisses.persistence.web.facade.CompraListaRegalosFacade;
import co.matisses.persistence.web.facade.ListaRegaloInvitadoFacade;
import co.matisses.persistence.web.facade.TipoEventoFacade;
import co.matisses.persistence.web.facade.UsuarioPaginaFacade;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

/**
 *
 * @author jguisao
 */
@Stateless
@Path("listaregalos")
public class ListaRegalosREST {

    @Inject
    private BCSApplicationMBean applicationBean;
    @Inject
    private SAPB1WSBean sapB1WSBean;
    @Inject
    private ImagenProductoMBean imagenProductoMBean;
    @Inject
    private BCSGenericMBean genericMBean;
    private static final Logger CONSOLE = Logger.getLogger(ListaRegalosREST.class.getSimpleName());
    private String[] meses;
    @EJB
    private SendHtmlEmailREST emailREST;
    @EJB
    private SocioDeNegociosFacade clienteFacade;
    @EJB
    private ListaRegalosFacade listaRegalosFacade;
    @EJB
    private ProductoListaRegalosFacade productoListaFacade;
    @EJB
    private SaldoItemInventarioFacade saldoItemFacade;
    @EJB
    private ItemInventarioFacade itemFacade;
    @EJB
    private UsuarioPaginaFacade usuarioPaginaFacade;
    @EJB
    private CompraListaRegalosFacade compraListaRegalosFacade;
    @EJB
    private ListaRegaloInvitadoFacade ListaRegaloInvitado;
    @EJB
    private FacturaSAPFacade facturaSAPFacade;
    @EJB
    private SalesPersonFacade salesPersonFacade;
    @EJB
    private SerieFacade serieFacade;
    @EJB
    private DetalleFacturaSAPFacade detalleFacturaSAPFacade;
    @EJB
    private SaldoItemInventarioFacade saldoItemInventarioFacade;
    @EJB
    private CreditNotesREST creditNotesREST;
    @EJB
    private GoodsIssueREST goodsIssueREST;
    @EJB
    private DevolucionSAPFacade devolucionSAPFacade;
    @EJB
    private SaldoUbicacionFacade saldoUbicacionFacade;
    @EJB
    private DetalleDevolucionSAPFacade detalleDevolucionSAPFacade;
    @EJB
    private PrintReportREST reportREST;
    @EJB
    private ReciboCajaFacade reciboCajaFacade;
    @EJB
    private IncomingPaymentREST incomingPaymentREST;
    @EJB
    private TipoEventoFacade tipoEventoFacade;
    @EJB
    private BaruDecoradoresFacade baruDecoradoresFacade;

    @PostConstruct
    protected void initialize() {
        meses = new String[]{"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
    }

    private ListaRegalosDTO entity2Dto(ListaRegalos entity, List<ProductoListaRegalos> productosEntity) {
        ListaRegalosDTO dto = new ListaRegalosDTO();

        dto.setAceptaBonos(entity.getAceptaBonos());
        dto.setCategoria(new CategoriaListaRegalosDTO(entity.getCategoria().getIdCategoria(), entity.getCategoria().getNombre(), entity.getCategoria().getValorMinimo()));
        dto.setCedulaCocreador(entity.getCedulaCocreador());
        dto.setCedulaCreador(entity.getCedulaCreador());
        dto.setCodigo(entity.getCodigo());
        dto.setEstado(new EstadoListaRegalosDTO(entity.getEstado().getIdEstado(), entity.getEstado().getNombre()));
        dto.setFechaCreacion(entity.getFechaCreacion());
        dto.setFechaEvento(entity.getFechaEvento());
        dto.setIdLista(entity.getIdLista());
        dto.setInvitados(entity.getInvitados());
        dto.setListaPrivada(entity.getListaPrivada());
        dto.setMensajeAgradecimiento(entity.getMensajeAgradecimiento());
        dto.setMensajeBienvenida(entity.getMensajeBienvenida());
        dto.setNombre(entity.getNombre());
        dto.setApellidoCocreador(entity.getApellidoCocreador());
        dto.setApellidoCreador(entity.getApellidoCreador());
        dto.setNombreCocreador(entity.getNombreCocreador());
        dto.setNombreCreador(entity.getNombreCreador());
        dto.setNotificacionCambioCategoriaCreador(entity.getNotificacionCambioCategoriaCreador());
        dto.setNotificacionDiariaCreador(entity.getNotificacionDiariaCreador());
        dto.setNotificacionInmediataCreador(entity.getNotificacionInmediataCreador());
        dto.setNotificacionSemanalCreador(entity.getNotificacionSemanalCreador());
        dto.setNotificacionCambioCategoriaCocreador(entity.getNotificacionCambioCategoriaCocreador());
        dto.setNotificacionDiariaCocreador(entity.getNotificacionDiariaCocreador());
        dto.setNotificacionInmediataCocreador(entity.getNotificacionInmediataCocreador());
        dto.setNotificacionSemanalCocreador(entity.getNotificacionSemanalCocreador());
        dto.setRutaImagenPerfil(entity.getRutaImagenPerfil());
        dto.setRutaImagenPortada(entity.getRutaImagenPortada());
        dto.setPermitirEntregaPersonal(entity.getPermitirEntregaPersonal());
        dto.setTipoEvento(new TipoEventoDTO(entity.getTipoEvento().getIdTipoEvento(), entity.getTipoEvento().getNombre()));
        dto.setValorMinimoBono(entity.getValorMinimoBono());

        if (productosEntity == null) {
            return dto;
        }

        for (ProductoListaRegalos prodEntity : productosEntity) {
            dto.agregarProducto(productoEntity2Dto(prodEntity));
        }

        CONSOLE.log(Level.INFO, "Se agregaron {0} productos a la lista", dto.getProductos().size());
        return dto;
    }

    private ListaRegalosDTO entity2Dto(ListaRegalos entity, List<ProductoListaRegalos> productosEntity, String formatearFechaEvento, Integer contadorDias) {
        ListaRegalosDTO dto = new ListaRegalosDTO();

        dto.setAceptaBonos(entity.getAceptaBonos());
        dto.setCategoria(new CategoriaListaRegalosDTO(entity.getCategoria().getIdCategoria(), entity.getCategoria().getNombre(), entity.getCategoria().getValorMinimo()));
        dto.setCedulaCocreador(entity.getCedulaCocreador());
        dto.setCedulaCreador(entity.getCedulaCreador());
        dto.setCodigo(entity.getCodigo());
        dto.setEstado(new EstadoListaRegalosDTO(entity.getEstado().getIdEstado(), entity.getEstado().getNombre()));
        dto.setFechaCreacion(entity.getFechaCreacion());
        dto.setFechaEvento(entity.getFechaEvento());
        dto.setIdLista(entity.getIdLista());
        dto.setInvitados(entity.getInvitados());
        dto.setListaPrivada(entity.getListaPrivada());
        dto.setMensajeAgradecimiento(entity.getMensajeAgradecimiento());
        dto.setMensajeBienvenida(entity.getMensajeBienvenida());
        dto.setNombre(entity.getNombre());
        dto.setApellidoCocreador(obtenerPrimerApellido(entity.getApellidoCocreador()));
        dto.setApellidoCreador(obtenerPrimerApellido(entity.getApellidoCreador()));
        dto.setNombreCocreador(obtenerPrimerNombre(entity.getNombreCocreador()));
        dto.setNombreCreador(obtenerPrimerNombre(entity.getNombreCreador()));
        dto.setNotificacionCambioCategoriaCreador(entity.getNotificacionCambioCategoriaCreador());
        dto.setNotificacionDiariaCreador(entity.getNotificacionDiariaCreador());
        dto.setNotificacionInmediataCreador(entity.getNotificacionInmediataCreador());
        dto.setNotificacionSemanalCreador(entity.getNotificacionSemanalCreador());
        dto.setNotificacionCambioCategoriaCocreador(entity.getNotificacionCambioCategoriaCocreador());
        dto.setNotificacionDiariaCocreador(entity.getNotificacionDiariaCocreador());
        dto.setNotificacionInmediataCocreador(entity.getNotificacionInmediataCocreador());
        dto.setNotificacionSemanalCocreador(entity.getNotificacionSemanalCocreador());
        dto.setRutaImagenPerfil(entity.getRutaImagenPerfil());
        dto.setRutaImagenPortada(entity.getRutaImagenPortada());
        dto.setPermitirEntregaPersonal(entity.getPermitirEntregaPersonal());
        dto.setTipoEvento(new TipoEventoDTO(entity.getTipoEvento().getIdTipoEvento(), entity.getTipoEvento().getNombre()));
        dto.setValorMinimoBono(entity.getValorMinimoBono());
        dto.setFormatoFechaEvento(formatearFechaEvento);
        dto.setContadorDias(contadorDias);
        dto.setFormatoFechaEntrega(new SimpleDateFormat("dd | MM | yyyy").format(entity.getFechaEntrega()));

        if (productosEntity == null) {
            return dto;
        }

        for (ProductoListaRegalos prodEntity : productosEntity) {
            dto.agregarProducto(productoEntity2Dto(prodEntity));
        }

        CONSOLE.log(Level.INFO, "Se agregaron {0} productos a la lista", dto.getProductos().size());
        return dto;
    }

    private ProductoListaRegalosDTO productoEntity2Dto(ProductoListaRegalos prodEntity) {
        ProductoListaRegalosDTO prodDto = new ProductoListaRegalosDTO();

        prodDto.setCantidadComprada(prodEntity.getCantidadComprada());
        prodDto.setCantidadElegida(prodEntity.getCantidadElegida());
        prodDto.setCantidadEntregada(prodEntity.getCantidadEntregada());
        prodDto.setDescripcionProducto(prodEntity.getDescripcionProducto());
        prodDto.setFavorito(prodEntity.getFavorito());
        prodDto.setIdLista(prodEntity.getLista().getIdLista());
        prodDto.setIdProductoLista(prodEntity.getIdProductoLista());
        prodDto.setMensajeAgradecimiento(prodEntity.getMensajeAgradecimiento());
        prodDto.setReferencia(prodEntity.getReferencia().substring(0, 3).concat(prodEntity.getReferencia().substring(16)));
        prodDto.setPrecioTotal(prodEntity.getPrecio());
        prodDto.setPrecio(prodEntity.getPrecio());
        if (prodEntity.getCantidadComprada() > 0) {
            prodDto.setPrecioTotal(prodEntity.getPrecio() * prodEntity.getCantidadComprada());
        }

        return prodDto;
    }

    private CompraListaRegalosDTO compraEntity2Dto(CompraListaRegalos prodEntity) {
        CompraListaRegalosDTO dto = new CompraListaRegalosDTO();

        dto.setIdLista(prodEntity.getLista().getIdLista());
        dto.setFactura(prodEntity.getFactura());
        dto.setQuienCompra(prodEntity.getQuienCompra());
        dto.setMensaje(prodEntity.getMensaje());
        dto.setDevuelto(prodEntity.getDevuelto());
        dto.setTotal(prodEntity.getTotal());
        dto.setCantidad(prodEntity.getCantidad());
        dto.setIdCompra(prodEntity.getIdCompra());
        dto.setIdProductoLista(prodEntity.getProducto().getIdProductoLista());

        return dto;
    }

    @POST
    @Path("crear/")
    @Consumes({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response crearLista(final ListaRegalosDTO lista, @Context HttpServletRequest req) throws ParseException {
        CONSOLE.log(Level.INFO, "Se recibio solicitud para crear nueva lista de regalos desde {0}", req.getRemoteAddr());
        if (lista.getCedulaCreador() == null || lista.getCedulaCreador().trim().isEmpty()) {
            CONSOLE.log(Level.SEVERE, "Debes ingresar los datos del creador de la lista");
            return Response.ok(new CrearListaResponseDTO(-1, "Debes ingresar los datos del creador de la lista.")).build();
        }
        if (lista.getFormatoFechaEvento() == null) {
            CONSOLE.log(Level.SEVERE, "Debes ingresar la fecha del evento");
            return Response.ok(new CrearListaResponseDTO(-1, "Debes ingresar la fecha del evento.")).build();
        }
        if (lista.getFormatoFechaEntrega() == null) {
            CONSOLE.log(Level.SEVERE, "Debes ingresar la fecha de entrega");
            return Response.ok(new CrearListaResponseDTO(-1, "Debes ingresar la fecha de entrega.")).build();
        }
        try {
            if (new SimpleDateFormat("yyyy-MM-dd").parse(lista.getFormatoFechaEvento()).before(new Date())) {
                CONSOLE.log(Level.SEVERE, "La fecha del evento no puede ser anterior a la fecha actual");
                return Response.ok(new CrearListaResponseDTO(-1, "La fecha del evento no puede ser anterior a la fecha actual.")).build();
            }
            if (new SimpleDateFormat("yyyy-MM-dd").parse(lista.getFormatoFechaEntrega()).before(new Date())) {
                CONSOLE.log(Level.SEVERE, "La fecha de entrega no puede ser anterior a la fecha actual");
                return Response.ok(new CrearListaResponseDTO(-1, "La fecha de entrega no puede ser anterior a la fecha actual.")).build();
            }
//            if (new SimpleDateFormat("yyyy-MM-dd").parse(lista.getFormatoFechaEntrega()).before(new SimpleDateFormat("yyyy-MM-dd").parse(lista.getFormatoFechaEvento()))) {
//                CONSOLE.log(Level.SEVERE, "La fecha de entrega no puede ser anterior a la fecha del evento");
//                return Response.ok(new CrearListaResponseDTO(-1, "La fecha de entrega no puede ser anterior a la fecha del evento.")).build();
//            }
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al formatear la fecha de evento o fecha de entrega ", e);
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_YEAR, Integer.parseInt(applicationBean.obtenerValorPropiedad("dias.tentativa.novias")));
        Date fechaTentativa = calendar.getTime();

        Long rango = ((fechaTentativa.getTime() / 86400000) - (new SimpleDateFormat("yyyy-MM-dd").parse(lista.getFormatoFechaEntrega()).getTime() / 86400000));
        if (rango > 0) {
            return Response.ok(new CrearListaResponseDTO(-1, "La fecha de entrega esta fuera del rango permitido.")).build();
        }

        String nombreEvento;
        if (lista.getTipoEvento() == null || lista.getTipoEvento().getIdTipoEvento() == null) {
            CONSOLE.log(Level.SEVERE, "Debes seleccionar un tipo de evento");
            return Response.ok(new CrearListaResponseDTO(-1, "Debes seleccionar un tipo de evento.")).build();
        } else if (lista.getTipoEvento().getIdTipoEvento() == 4) {
            nombreEvento = "Boda";
        } else {
            nombreEvento = tipoEventoFacade.obtenerNombreEvento(lista.getTipoEvento().getIdTipoEvento());
        }

        if (!lista.isAceptaTerminos()) {
            CONSOLE.log(Level.SEVERE, "Debes aceptar los terminos y condiciones");
            return Response.ok(new CrearListaResponseDTO(-1, "Debes aceptar los términos y condiciones.")).build();
        }

        CONSOLE.log(Level.INFO, "Datos lista a crear: {0}", lista.toString());
        try {
            Date fechaEvento = new SimpleDateFormat("yyyy-MM-dd").parse(lista.getFormatoFechaEvento());
            Date fechaEntrega = new SimpleDateFormat("yyyy-MM-dd").parse(lista.getFormatoFechaEntrega());
            ListaRegalos entity = new ListaRegalos();
            UsuarioPagina entityUsuario = new UsuarioPagina();
            entity.setActiva(true);
            entity.setAceptaBonos(lista.isAceptaTerminos());
            entity.setApellidoCreador(lista.getApellidoCreador());
            entity.setApellidoCocreador(lista.getApellidoCocreador());
            entity.setCategoria(new CategoriaListaRegalos(1L));
            entity.setCedulaCocreador(lista.getCedulaCocreador());
            entity.setCedulaCreador(lista.getCedulaCreador());
            entity.setCodigo(generarCodigoListaRegalo(lista.getNombreCreador(), lista.getApellidoCreador(), (new SimpleDateFormat("yyyyMMdd").format(fechaEvento))));
            entity.setEstado(new EstadoListaRegalos(1L));
            entity.setFechaCreacion(new Date());
            entity.setFechaEvento(fechaEvento);
            entity.setFechaEntrega(fechaEntrega);
            entity.setTipoEvento(new TipoEvento(lista.getTipoEvento().getIdTipoEvento(), lista.getTipoEvento().getNombre()));
            entity.setInvitados(lista.getInvitados());
            entity.setListaPrivada(lista.isListaPrivada());
            entity.setMensajeAgradecimiento(lista.getMensajeAgradecimiento());
            entity.setMensajeBienvenida(lista.getMensajeBienvenida());
            entity.setNombre(nombreEvento + " " + obtenerPrimerNombre(lista.getNombreCreador()) + (lista.getNombreCocreador().isEmpty() ? "" : " & " + obtenerPrimerNombre(lista.getNombreCocreador())));
            entity.setNombreCreador(lista.getNombreCreador());
            entity.setNombreCocreador(lista.getNombreCocreador());
            entity.setNotificacionCambioCategoriaCreador(lista.getNotificacionCambioCategoriaCreador());
            entity.setNotificacionInmediataMailCreador(lista.isNotificacionInmediataMailCreador());
            entity.setNotificacionInmediataSmsCreador(lista.isNotificacionInmediataSmsCreador());
            entity.setNotificacionDiariaMailCreador(lista.isNotificacionDiariaMailCreador());
            entity.setNotificacionDiariaSmsCreador(lista.isNotificacionDiariaSmsCreador());
            entity.setNotificacionSemanalMailCreador(lista.isNotificacionSemanalMailCreador());
            entity.setNotificacionSemanalSmsCreador(lista.isNotificacionSemanalSmsCreador());
            entity.setNotificacionInmediataMailCocreador(lista.isNotificacionInmediataMailCocreador());
            entity.setNotificacionInmediataSmsCocreador(lista.isNotificacionInmediataSmsCocreador());
            entity.setNotificacionDiariaMailCocreador(lista.isNotificacionDiariaMailCocreador());
            entity.setNotificacionDiariaSmsCocreador(lista.isNotificacionDiariaSmsCocreador());
            entity.setNotificacionSemanalMailCocreador(lista.isNotificacionSemanalMailCocreador());
            entity.setNotificacionSemanalSmsCocreador(lista.isNotificacionSemanalSmsCocreador());
            entity.setCelebracion(lista.getCelebracion());
            entity.setLugar(lista.getLugar());
            entity.setNotificacionCambioCategoriaCocreador(lista.getNotificacionCambioCategoriaCocreador());
            entity.setIdDecorador(lista.getIdDecorador());
            entity.setRutaImagenPerfil(lista.getRutaImagenPerfil());
            entity.setRutaImagenPortada(lista.getRutaImagenPortada());
            entity.setPermitirEntregaPersonal(lista.isPermitirEntregaPersonal());
            entity.setValorMinimoBono(lista.getValorMinimoBono());
            entity.setCorreoCreador(lista.getCorreoCreador());
            entity.setCorreoCocreador(lista.getCorreoCocreador());
            entity.setTelefonoCreador(lista.getTelefonoCreador());
            entity.setDireccionCreador(lista.getDireccionCreador());
            entity.setCiudadCreador(lista.getCiudadCreador());
            entity.setTelefonoCocreador(lista.getTelefonoCocreador());
            entity.setDireccionCocreador(lista.getDireccionCocreador());
            entity.setCiudadCocreador(lista.getCiudadCocreador());
            entity.setAceptaTerminos(true);
            entity.setUsarDatosCreador(lista.isUsarDatosCreador());
            entity.setUsarDatosCocreador(lista.isUsarDatosCocreador());
            entity.setTiendaContacto(lista.getTiendaContacto());

            try {
                listaRegalosFacade.create(entity);

                crearCodigoQR(entity.getCodigo());

                StringBuilder sb = new StringBuilder();

                sb.append("<tr><td><b style=\"font-size: 20px;\">");
                sb.append(convertirNombreLista(entity.getNombre().toLowerCase()));
                sb.append("</b><br/>");
                sb.append(meses[Integer.parseInt(new SimpleDateFormat("MM").format(entity.getFechaEvento())) - 1]);
                sb.append(" ");
                sb.append(new SimpleDateFormat("dd").format(entity.getFechaEvento()));
                sb.append(" de ");
                sb.append(new SimpleDateFormat("yyyy").format(entity.getFechaEvento()));
                sb.append("<br/><br/><b style=\"font-size: 30px; color: #E07D7D;\">");
                sb.append("<img src=\"");
                sb.append(applicationBean.obtenerValorPropiedad("listaregalos.url.web.document")).append("qr/").append(entity.getCodigo());
                sb.append(".png\"/><br/>");
                sb.append(entity.getCodigo());
                sb.append("</b><br/>*Comparte el <b>c&oacute;digo</b> con tus invitados para que todos tengan acceso a tu lista");
                sb.append("<br/><br/></td></tr>");

                Map<String, String> params = armarCorreo("Matisses te da la bienvenida a tu lista de regalos, a continuaci&oacute;n los datos de tu lista", sb.toString(),
                        entity.getNombreCreador().toUpperCase(), entity.getCodigo(), "", 0L);
                enviarCorreo("lista_regalos", "Lista Regalos Matisses <listaideal@matisses.co>", "Lista Regalos Matisses", entity.getCorreoCreador(),
                        applicationBean.getDestinatariosPlantillaEmail().get("lista_regalos").getTo().get(0), null, params);
            } catch (Exception e) {
                CONSOLE.log(Level.SEVERE, "Ocurrio un error armando correo de creacion de lista regalo o generando codigo QR ", e);
            }

            entityUsuario.setNombreUsuario(lista.getCorreoCreador());
            entityUsuario.setEsNuevo(true);
            entityUsuario.setIdListaRegalos(new ListaRegalos(entity.getIdLista()));
            entityUsuario.setEsLista(true);
            entityUsuario.setFechaRegistro(new Date());
            entityUsuario.setEstado(true);
            String passEncripted = DigestUtils.sha1Hex(lista.getCedulaCreador());
            entityUsuario.setPassword(passEncripted);
            entityUsuario.setNombre(lista.getNombreCreador() + " " + lista.getApellidoCreador());
            entityUsuario.setDocumento(lista.getCedulaCreador());

            try {
                //Creando usuario pagina
                usuarioPaginaFacade.create(entityUsuario);

                String documento = "terminosCondiciones";
                List<String[]> adjuntos = new ArrayList<>();
                String nombreDocumento = generarDocumento(1, documento, entity.getCodigo());

                if (nombreDocumento != null && !nombreDocumento.isEmpty()) {
                    String[] adjunto = new String[2];
                    adjunto = new String[]{applicationBean.obtenerValorPropiedad("url.archivo.listaRegalos") + documento + File.separator + nombreDocumento, "Terminos_y_Condiciones.pdf"};
                    adjuntos.add(adjunto);
                }

                StringBuilder sb = new StringBuilder();

                sb.append("<tr><td style=\"text-align: left;\"><b style=\"font-size: 20px;\">Usuario: </b>");
                sb.append(entityUsuario.getNombreUsuario());
                sb.append("<br/><br/><b style=\"font-size: 20px;\">Clave: </b>");
                sb.append(lista.getCedulaCreador());
                sb.append("<br/>*Se te pedirá el cambio de tu contrase&ntilde;a al ingresar por primera vez<br/><br/>");
                sb.append("<b style=\"font-size: 20px;\">C&oacute;digo: </b>");
                sb.append(entity.getCodigo());
                sb.append("<br/>*Comparte el <b>c&oacute;digo</b> con tus invitados para que todos tengan acceso a tu lista");
                sb.append("<br/><br/></td></tr>");
                sb.append("<tr><td style=\"text-align: center;\">");
                sb.append("<img src=\"");
                sb.append(applicationBean.obtenerValorPropiedad("listaregalos.url.web.document")).append("qr/").append(entity.getCodigo());
                sb.append(".png\"/></td></tr><br/>");

                Map<String, String> params = armarCorreo("Matisses te da la bienvenida a tu lista de regalos, a continuaci&oacute;n los datos de ingreso a tu lista", sb.toString(),
                        entity.getNombreCreador().toUpperCase(), entity.getCodigo(), "", 0L);
                enviarCorreo("lista_regalos", "Lista Regalos Matisses <listaideal@matisses.co>", "Lista Regalos Matisses", entity.getCorreoCreador(),
                        applicationBean.getDestinatariosPlantillaEmail().get("lista_regalos").getTo().get(0), adjuntos, params);
            } catch (Exception e) {
                CONSOLE.log(Level.SEVERE, "Ocurrio creando el usuario lista de regalo. ", e);
            }

            CONSOLE.log(Level.INFO, "Se creo la lista {1} con codigo {0}", new Object[]{entity.getCodigo(), entity.getIdLista()});
            return Response.ok(new CrearListaResponseDTO(0, entity.getCodigo(), entity.getIdLista().toString())).build();
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al crear la lista. ", e);
            return Response.serverError().build();
        }
    }

    @POST
    @Path("crearinvitado/")
    @Consumes({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response crearInvitado(final InvitadoDTO invitado, @Context HttpServletRequest req) {
        CONSOLE.log(Level.INFO, "Se recibio solicitud para crear invitado {0}", req.getRemoteAddr());
        if (invitado.getNombreInvitado() == null || invitado.getNombreInvitado().trim().isEmpty()) {
            CONSOLE.log(Level.SEVERE, "Debes ingresar el nombre del invitado");
            return Response.ok(new CrearInvitadoResponseDTO(-1, "Debes ingresar el nombre del invitado.")).build();
        }
        if (invitado.getApellidosInvitado() == null || invitado.getApellidosInvitado().trim().isEmpty()) {
            CONSOLE.log(Level.SEVERE, "Debe ingresar el apellido del invitado");
            return Response.ok(new CrearInvitadoResponseDTO(-1, "Debe ingresar el apellido del invitado.")).build();
        }
        if (invitado.getCorreoInvitado() == null || invitado.getCorreoInvitado().trim().isEmpty()) {
            CONSOLE.log(Level.SEVERE, "Debe ingresar el correo del invitado");
            return Response.ok(new CrearInvitadoResponseDTO(-1, "Debe ingresar el correo del invitado.")).build();
        }
        if (invitado.getTelefonoInvitado() == null || invitado.getTelefonoInvitado().trim().isEmpty()) {
            CONSOLE.log(Level.SEVERE, "Debe ingresar el telefono del invitado");
            return Response.ok(new CrearInvitadoResponseDTO(-1, "Debe ingresar el teléfono del invitado.")).build();
        }

        CONSOLE.log(Level.INFO, "Datos invitado a crear: {0}", invitado.toString());

        /*validar si el invitado esta duplicado*/
        if (!ListaRegaloInvitado.validarInvitadoDuplicado(invitado.getIdLista(), invitado.getNombreInvitado(), invitado.getApellidosInvitado(), invitado.getCorreoInvitado(), invitado.getTelefonoInvitado())) {
            return Response.ok(new CrearInvitadoResponseDTO(-1, "El invitado ya esta registrado.")).build();
        }

        ListaRegaloInvitado entity = new ListaRegaloInvitado();

        entity.setIdLista(invitado.getIdLista());
        entity.setNombreInvitado(invitado.getNombreInvitado());
        entity.setApellidosInvitado(invitado.getApellidosInvitado());
        entity.setCorreoInvitado(invitado.getCorreoInvitado());
        entity.setTelefonoInvitado(invitado.getTelefonoInvitado());
        entity.setAsistencia(invitado.getAsistencia());

        try {
            ListaRegaloInvitado.create(entity);
            /*Se envia el correo al invitado*/
            ListaRegalos lista = listaRegalosFacade.consultarListaPorId(invitado.getIdLista());

            if (lista != null && lista.getIdLista() != null && lista.getIdLista() != 0) {
                StringBuilder sb = new StringBuilder();

                sb.append("Has sido invitad@ a la boda de ");
                sb.append("<br/><table style=\"width: 100%;\"><tr><td style=\"text-align: center; font-size: 30px;\">");
                sb.append(lista.getNombre().replace("Boda ", "").replace("&", "<span style=\"color: #E07D7D;\">&</span>"));
                sb.append("</td></tr></table>");

                Map<String, String> params = armarCorreo(sb.toString(),
                        "<tr><td style=\"text-align: justify;\">Puedes entrar a la lista de regalos y darle lo que más gustes. El envió corre por cuenta nuestra.</td></tr>",
                        invitado.getNombreInvitado(), lista.getCodigo(), "", entity.getIdInvitado());

                enviarCorreo("lista_regalos", "Lista Regalos Matisses <listaideal@matisses.co>", "Lista Regalos Matisses", invitado.getCorreoInvitado(),
                        applicationBean.getDestinatariosPlantillaEmail().get("lista_regalos").getTo().get(0), null, params);
            }

            return Response.ok(new CrearInvitadoResponseDTO(0, entity.getIdInvitado().toString())).build();
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al crear el invitado. ", e);
            return Response.serverError().build();
        }
    }

    @GET
    @Path("consultarinvitados/{idLista}")
    @Produces({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response consultarInvitados(@PathParam("idLista") long idLista) {
        CONSOLE.log(Level.SEVERE, "consultando invitados de la lista ", idLista);

        List<ListaRegaloInvitado> listaInvitados = ListaRegaloInvitado.consultarInvitados(idLista);
        if (listaInvitados.isEmpty()) {
            CONSOLE.log(Level.INFO, "No se encontraron invitados con los datos ingresados");
            return Response.ok(new ConsultaListasResponseDTO(-1, "No se encontraron invitados con los datos ingresados.")).build();
        }
        return Response.ok(listaInvitados).build();
    }

    private String generarCodigoListaRegalo(String nombre, String apellido, String fechaEvento) {
        Integer aux = 1;
        if (nombre != null && !nombre.isEmpty() && apellido != null && !apellido.isEmpty() && fechaEvento != null && !fechaEvento.isEmpty()) {
            String codigoLista = StringUtils.left(nombre, 1) + StringUtils.left(apellido, 1) + fechaEvento;
            for (int i = 0; i < aux; i++) {
                Long contCodigo = listaRegalosFacade.contarCodigoLista(codigoLista);
                if (contCodigo <= 0) {
                    return codigoLista;
                } else {
                    char[] caracteres = apellido.toCharArray();
                    codigoLista = StringUtils.left(nombre, 1) + caracteres[aux] + fechaEvento;
                    aux = aux + 1;
                }
            }
            return codigoLista;
        }
        return null;
    }

    private String obtenerPrimerNombre(String nombre) {
        if (nombre != null && !nombre.isEmpty()) {
            String[] s = nombre.split(" ");

            if (s.length > 0) {
                return s[0];
            }
        }

        return "";
    }

    private String obtenerPrimerApellido(String apellidos) {
        if (apellidos != null && !apellidos.isEmpty()) {
            String[] s = apellidos.split(" ");

            if (s.length > 0) {
                return s[0];
            }
        }

        return "";
    }

    private String convertirNombreLista(String nombre) {
        if (nombre != null && !nombre.isEmpty()) {
            nombre = nombre.substring(0, 1).toUpperCase() + nombre.substring(1);
            char[] caracteres = nombre.toCharArray();

            for (int i = 0; i < nombre.length() - 2; i++) {
                if (caracteres[i] == ' ' || caracteres[i] == '.' || caracteres[i] == ',') {
                    caracteres[i + 1] = Character.toUpperCase(caracteres[i + 1]);
                }
            }

            return new String(caracteres);
        }
        return "";
    }

    private Map<String, String> armarCorreo(String comentario, String mensaje, String cliente, String nombreLista, String tabla, Long idInvitado) {
        Map<String, String> params = new HashMap<>();

        params.put("comentario", comentario);
        params.put("mensaje", mensaje);
        params.put("tabla", tabla);
        params.put("cliente", cliente);
        if (idInvitado == 0L) {
            params.put("link", applicationBean.obtenerValorPropiedad("listaregalos.url.web") + nombreLista);
        } else {
            params.put("link", applicationBean.obtenerValorPropiedad("listaregalos.url.web.invitado") + nombreLista + "/" + idInvitado);
        }
        return params;
    }

    private void enviarCorreo(String template, String from, String subject, String toAddress, String bccAddress, List<String[]> adjuntos, Map<String, String> params) {
        MailMessageDTO dtoMail = new MailMessageDTO();

        dtoMail.setTemplateName(template);
        dtoMail.setParams(params);
        dtoMail.setAttachments(adjuntos);
        dtoMail.setFrom(from);
        dtoMail.setSubject(subject);

        dtoMail.addToAddress(toAddress);
        dtoMail.addBccAddress(bccAddress);

        try {
            emailREST.sendMail360(dtoMail);
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al enviar la notificacion", e);
        }
    }

    private String generarDocumento(Integer copias, String documento, String dirigido) {
        PrintReportDTO print = new PrintReportDTO();

        print.setDocumento(documento);
        print.setDirigido(dirigido);
        print.setCopias(copias);

        try {
            ResponseDTO res = reportREST.generarReporte(print);

            if (new File(res.getMensaje()).exists()) {
                return dirigido + ".pdf";
            } else {
                CONSOLE.log(Level.SEVERE, "No se pudo generar el documento");
                return null;
            }
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al generar el documento para {0}. Error {1}", new Object[]{documento.toUpperCase(), e.getMessage()});
            return null;
        }
    }

    @POST
    @Path("consultarlistas/")
    @Consumes({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response consultarListas(final ConsultaListasDTO consulta) throws ParseException {
        if (consulta == null) {
            CONSOLE.log(Level.INFO, "No se recibieron datos para ejecutar la consulta");
            return Response.ok(new ConsultaListasResponseDTO(-1, "No se recibieron datos para ejecutar la consulta.")).build();
        }
        CONSOLE.log(Level.INFO, "Consultando listas {0}", consulta.toString());
        List<ListaRegalos> listas = listaRegalosFacade.consultarListas(consulta.getNombre(), consulta.getApellido(), consulta.getCodigo());
        if (listas.isEmpty()) {
            CONSOLE.log(Level.INFO, "No se encontraron listas con los datos ingresados");
            return Response.ok(new ConsultaListasResponseDTO(-1, "No se encontraron listas con los datos ingresados.")).build();
        }
        ArrayList<ListaRegalosDTO> dtos = new ArrayList<>();
        for (ListaRegalos entidad : listas) {

            Date actual = new SimpleDateFormat("yyyy-MM-dd").parse(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            Date evento = new SimpleDateFormat("yyyy-MM-dd").parse(entidad.getFechaEvento().toString());

            dtos.add(entity2Dto(entidad, null, new SimpleDateFormat("dd | MM | yyyy").format(entidad.getFechaEvento()), (int) ((evento.getTime() - actual.getTime()) / 86400000)/*Milisegundos*/));
        }
        return Response.ok(dtos).build();
    }

    @POST
    @Path("modificar/")
    @Consumes({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response modificarLista(final ListaRegalosDTO lista, @Context HttpServletRequest req) {
        CONSOLE.log(Level.INFO, "Se recibio solicitud para modificar una lista de regalos desde {0}", req.getRemoteAddr());
        if (lista.getNombre() == null || lista.getNombre().trim().isEmpty()) {
            CONSOLE.log(Level.SEVERE, "Debes ingresar el nombre de la lista");
            return Response.ok(new CrearListaResponseDTO(-1, "Debes ingresar el nombre de la lista")).build();
        }
        if (lista.getCedulaCreador() == null || lista.getCedulaCreador().trim().isEmpty()) {
            CONSOLE.log(Level.SEVERE, "Debes ingresar los datos del creador de la lista");
            return Response.ok(new CrearListaResponseDTO(-1, "Debes ingresar los datos del creador de la lista")).build();
        }
        if (lista.getFechaEvento() == null) {
            CONSOLE.log(Level.SEVERE, "Debes ingresar la fecha del evento");
            return Response.ok(new CrearListaResponseDTO(-1, "Debes ingresar la fecha del evento")).build();
        }
        if (lista.getFechaEvento().before(new Date())) {
            CONSOLE.log(Level.SEVERE, "La fecha del evento debe ser en el futuro");
            return Response.ok(new CrearListaResponseDTO(-1, "La fecha del evento debe ser en el futuro")).build();
        }
        if (lista.getTipoEvento() == null || lista.getTipoEvento().getIdTipoEvento() == null) {
            CONSOLE.log(Level.SEVERE, "Debes seleccionar un tipo de evento");
            return Response.ok(new CrearListaResponseDTO(-1, "Debes seleccionar un tipo de evento")).build();
        }
        CONSOLE.log(Level.INFO, "Datos lista a crear: {0}", lista.toString());

        try {
            ListaRegalos entity = listaRegalosFacade.find(lista.getIdLista());

            entity.setAceptaBonos(lista.isAceptaBonos());
            entity.setApellidoCreador(lista.getApellidoCreador());
            entity.setApellidoCocreador(lista.getApellidoCocreador());
            entity.setCategoria(new CategoriaListaRegalos(1L));
            entity.setCedulaCocreador(lista.getCedulaCocreador());
            entity.setCedulaCreador(lista.getCedulaCreador());
            entity.setEstado(new EstadoListaRegalos(1L));
            entity.setFechaCreacion(new Date());
            entity.setFechaEvento(lista.getFechaEvento());
            entity.setTipoEvento(new TipoEvento(lista.getTipoEvento().getIdTipoEvento(), lista.getTipoEvento().getNombre()));
            entity.setInvitados(lista.getInvitados());
            entity.setListaPrivada(lista.isListaPrivada());
            entity.setMensajeAgradecimiento(lista.getMensajeAgradecimiento());
            entity.setMensajeBienvenida(lista.getMensajeBienvenida());
            entity.setNombre(lista.getNombre());
            entity.setNombreCreador(lista.getNombreCreador());
            entity.setNombreCocreador(lista.getNombreCocreador());
            entity.setNotificacionCambioCategoriaCreador(lista.getNotificacionCambioCategoriaCreador());
            entity.setNotificacionDiariaCreador(lista.getNotificacionDiariaCreador());
            entity.setNotificacionInmediataCreador(lista.getNotificacionInmediataCreador());
            entity.setNotificacionSemanalCreador(lista.getNotificacionSemanalCreador());
            entity.setNotificacionCambioCategoriaCocreador(lista.getNotificacionCambioCategoriaCocreador());
            entity.setNotificacionDiariaCocreador(lista.getNotificacionDiariaCocreador());
            entity.setNotificacionInmediataCocreador(lista.getNotificacionInmediataCocreador());
            entity.setNotificacionSemanalCocreador(lista.getNotificacionSemanalCocreador());
            entity.setRutaImagenPerfil(lista.getRutaImagenPerfil());
            entity.setRutaImagenPortada(lista.getRutaImagenPortada());
            entity.setPermitirEntregaPersonal(lista.isPermitirEntregaPersonal());
            entity.setValorMinimoBono(lista.getValorMinimoBono());

            try {
                listaRegalosFacade.edit(entity);
            } catch (Exception e) {
            }

            CONSOLE.log(Level.INFO, "Se modifico la lista {0}", entity.getCodigo());
            return Response.ok(new CrearListaResponseDTO(0, entity.getCodigo())).build();
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al crear la lista. ", e);
            return Response.serverError().build();
        }
    }

    @POST
    @Path("agregarproducto/")
    @Consumes({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response agregarProducto(ProductoListaRegalosDTO producto) {
        CONSOLE.log(Level.INFO, "Se recibio solicitud para agregar un producto a una lista");
        if (producto == null) {
            CONSOLE.log(Level.SEVERE, "No se recibio un producto para agregar.");
            return Response.ok(new AgregarProductoListaResponseDTO(-1, "No se recibio un producto para agregar.")).build();
        }
        if (producto.getReferencia() == null || producto.getReferencia().trim().isEmpty()) {
            CONSOLE.log(Level.SEVERE, "Debes seleccionar una referencia.");
            return Response.ok(new AgregarProductoListaResponseDTO(-1, "Debes seleccionar una referencia.")).build();
        }
        if (producto.getCantidadElegida() == null || producto.getCantidadElegida() <= 0) {
            CONSOLE.log(Level.SEVERE, "La cantidad debe ser mayor que cero.");
            return Response.ok(new AgregarProductoListaResponseDTO(-1, "La cantidad debe ser mayor que cero.")).build();
        }
        //consultar si la lista ya tiene agregada esa referencia
        if (!productoListaFacade.productoPuedeAgregarse(producto.getIdLista(), producto.getReferencia())) {
            CONSOLE.log(Level.SEVERE, "El producto ya se encuentra en la lista.");
            return Response.ok(new AgregarProductoListaResponseDTO(-1, "El producto ya se encuentra en la lista.")).build();
        }
        int saldoDisponible = consultarSaldoVentaItem(producto.getReferencia());
        if (saldoDisponible < producto.getCantidadElegida()) {
            CONSOLE.log(Level.SEVERE, "La cantidad elegida es mayor que la cantidad disponible E={0} D={1}.", new Object[]{producto.getCantidadElegida(), saldoDisponible});
            return Response.ok(new AgregarProductoListaResponseDTO(-1, "La cantidad elegida es mayor que la cantidad disponible. (Disponible: " + saldoDisponible + "). ")).build();
        }

        try {
            Object[] priceAndTax = itemFacade.getItemPriceAndTax(producto.getReferencia(), false);
            ProductoListaRegalos entity = new ProductoListaRegalos();

            entity.setCantidadElegida(producto.getCantidadElegida());
            entity.setCantidadComprada(0);
            entity.setCantidadEntregada(0);
            entity.setDescripcionProducto(producto.getDescripcionProducto());
            entity.setFavorito(producto.getFavorito());
            entity.setLista(new ListaRegalos(producto.getIdLista()));
            entity.setMensajeAgradecimiento(producto.getMensajeAgradecimiento());
            entity.setPrecio((Integer) priceAndTax[0]);
            entity.setImpuesto((Integer) priceAndTax[1]);
            entity.setReferencia(producto.getReferencia());
            entity.setActivo(true);

            try {
                productoListaFacade.create(entity);
            } catch (Exception e) {
            }

            return Response.ok(new AgregarProductoListaResponseDTO(0, null)).build();
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al agregar el producto a la lista. ", e);
            return Response.serverError().build();
        }
    }

    @POST
    @Path("modificarproducto/")
    @Consumes({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response modificarProducto(ProductoListaRegalosDTO producto) {
        CONSOLE.log(Level.INFO, "Se recibio solicitud para modificar un producto de una lista");
        if (producto == null) {
            CONSOLE.log(Level.SEVERE, "No se recibio un producto para modificar.");
            return Response.ok(new AgregarProductoListaResponseDTO(-1, "No se recibio un producto para modificar.")).build();
        }
        if (producto.getReferencia() == null || producto.getReferencia().trim().isEmpty()) {
            CONSOLE.log(Level.SEVERE, "Debes seleccionar una referencia.");
            return Response.ok(new AgregarProductoListaResponseDTO(-1, "Debes seleccionar una referencia.")).build();
        }
        //consultar si la lista tiene agregada esa referencia
        if (productoListaFacade.productoPuedeAgregarse(producto.getIdLista(), producto.getReferencia())) {
            CONSOLE.log(Level.SEVERE, "El producto no se encuentra en la lista y por lo tanto no se puede modificar.");
            return Response.ok(new AgregarProductoListaResponseDTO(-1, "El producto no se encuentra en la lista y por lo tanto no se puede modificar.")).build();
        }

        int saldoDisponible = consultarSaldoVentaItem(producto.getReferencia());
        if (saldoDisponible < producto.getCantidadElegida()) {
            CONSOLE.log(Level.SEVERE, "La cantidad elegida es mayor que la cantidad disponible E={0} D={1}.", new Object[]{producto.getCantidadElegida(), saldoDisponible});
            return Response.ok(new AgregarProductoListaResponseDTO(-1, "La cantidad elegida es mayor que la cantidad disponible. (Disponible: " + saldoDisponible + "). ")).build();
        }

        ProductoListaRegalos entidad = productoListaFacade.consultarPorListaReferencia(producto.getIdLista(), producto.getReferencia());
        if (entidad == null) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al consultar el producto para procesarlo");
            return Response.ok(new AgregarProductoListaResponseDTO(-1, "Ocurrio un error al consultar el producto para procesarlo.")).build();
        }
        //Si la cantidad enviada es cero, se elimina el producto
        if (producto.getCantidadElegida() == null || producto.getCantidadElegida() <= 0) {
            try {
                productoListaFacade.remove(entidad);
            } catch (Exception e) {
                CONSOLE.log(Level.SEVERE, "Ocurrio un error al eliminar el registro");
                return Response.ok(new AgregarProductoListaResponseDTO(-1, "Ocurrio un error al eliminar el registro.")).build();
            }
        } else {
            entidad.setCantidadElegida(producto.getCantidadElegida());
            entidad.setFavorito(producto.getFavorito());
            entidad.setMensajeAgradecimiento(producto.getMensajeAgradecimiento());

            try {
                productoListaFacade.edit(entidad);
                CONSOLE.log(Level.INFO, "La modificacion de la lista se realizo satisfactoriamente. ");
            } catch (Exception e) {
                CONSOLE.log(Level.SEVERE, "Ocurrio un error al modificar el registro");
                return Response.ok(new AgregarProductoListaResponseDTO(-1, "Ocurrio un error al modificar el registro.")).build();
            }
        }
        return Response.ok(new AgregarProductoListaResponseDTO(0, null)).build();
    }

    @POST
    @Path("consultarlistasusuario/")
    @Consumes({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response consultarListasUsuario(final ConsultaListasDTO consulta) {
        if (consulta == null) {
            return Response.ok(new ConsultaListasResponseDTO(-1, "No se recibieron datos para ejecutar la consulta")).build();
        }

        SesionListaRegalosDTO sesion = applicationBean.obtenerSesionListaRegalos(consulta.getIdSesion());
        if (!sesion.isSesionValida() || sesion.getTipo().equals(SesionListaRegalosDTO.TipoSesion.INVITADO)) {
            //TODO: parametrizar ruta de redireccion
            return Response.temporaryRedirect(URI.create(applicationBean.obtenerValorPropiedad("listaregalos.url.web.inicio"))).build();
        }

        CONSOLE.log(Level.INFO, "Consultando listas {0}", consulta.toString());
        List<ListaRegalos> listas;
        ArrayList<ListaRegalosDTO> dtos = new ArrayList<>();
        SocioDeNegocios cliente = clienteFacade.findByCardCode(consulta.getCedula());
        if (sesion.getTipo().equals(SesionListaRegalosDTO.TipoSesion.ADMINISTRADOR)) {
            //Consulta listas por administrador
            listas = listaRegalosFacade.consultarListasAdministrador(consulta.getPaginacion().getPagina(), consulta.getPaginacion().getRegistrosPagina(), consulta.getPaginacion().getOrderBy());
            if (listas.isEmpty()) {
                return Response.ok(new ConsultaListasResponseDTO(-1, "No se encontró ninguna lista")).build();
            }

            for (ListaRegalos entidad : listas) {
                dtos.add(entity2Dto(entidad, null));
            }
        } else {
            //Consulta listas por propietario
            listas = listaRegalosFacade.consultarListasUsuario(consulta.getCedula(), consulta.getPaginacion().getPagina(), consulta.getPaginacion().getRegistrosPagina(), consulta.getPaginacion().getOrderBy());
            if (listas.isEmpty()) {
                return Response.ok(new ConsultaListasResponseDTO(-1, "El usuario no tiene ninguna lista creada", cliente)).build();
            }

            for (ListaRegalos entidad : listas) {
                dtos.add(entity2Dto(entidad, null));
            }
        }

        return Response.ok(new ConsultaListasResponseDTO(0, cliente, dtos)).build();
    }

    @POST
    @Path("comprarbono/{codigoLista}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response agregarBonoRegaloCarrito(@PathParam("codigoLista") String codigoLista, SesionListaRegalosDTO sesion) {
        CONSOLE.log(Level.INFO, "Se recibio solicitud para agregar un bono al carrito de compra");
        if (sesion == null) {
            return Response.ok(new AgregarProductoListaResponseDTO(-1, "No se recibieron datos para continuar")).build();
        }
        if (sesion.getIdSession() == null || sesion.getIdSession().trim().isEmpty()) {
            return Response.ok(new AgregarProductoListaResponseDTO(-1, "No se recibió un código de sesión a la cual asociar el producto")).build();
        }

        SesionListaRegalosDTO sesionDto = applicationBean.obtenerSesionListaRegalos(sesion.getIdSession());
        if (sesionDto == null) {
            return Response.ok(new AgregarProductoListaResponseDTO(-1, "La sesion no es valida")).build();
        }

        ListaRegalos lista = listaRegalosFacade.consultarListaPorCodigo(codigoLista);
        if (lista == null) {
            return Response.ok(new AgregarProductoListaResponseDTO(-1, "No se encontro una lista con el codigo enviado")).build();
        }
        if (sesion.getValorBono() == 0 && sesion.getValorBono() < lista.getValorMinimoBono()) {
            return Response.ok(new AgregarProductoListaResponseDTO(-1, "El valor del bono debe ser superior al valor minimo")).build();
        }

        sesionDto.setValorBono(sesion.getValorBono());
        return Response.ok(new AgregarProductoListaResponseDTO(0, null)).build();
    }

    @POST
    @Path("nocomprarbono/")
    @Consumes({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response quitarBonoRegaloCarrito(SesionListaRegalosDTO sesion) {
        CONSOLE.log(Level.INFO, "Se recibio solicitud para quitar un bono del carrito de compras");
        if (sesion == null) {
            return Response.ok(new AgregarProductoListaResponseDTO(-1, "No se recibieron datos para continuar")).build();
        }
        if (sesion.getIdSession() == null || sesion.getIdSession().trim().isEmpty()) {
            return Response.ok(new AgregarProductoListaResponseDTO(-1, "No se recibió un código de sesión a la cual asociar el producto")).build();
        }

        SesionListaRegalosDTO sesionDto = applicationBean.obtenerSesionListaRegalos(sesion.getIdSession());
        if (sesionDto == null) {
            return Response.ok(new AgregarProductoListaResponseDTO(-1, "La sesion no es valida")).build();
        }

        sesionDto.setValorBono(0);
        return Response.ok(new AgregarProductoListaResponseDTO(0, null)).build();
    }

    @POST
    @Path("comprarproducto/{codigoLista}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response agregarProductoCarrito(SesionListaRegalosDTO sesion, @PathParam("codigoLista") String codigoLista) {
        CONSOLE.log(Level.INFO, "Agregando producto de una lista al carrito de compras {0}", sesion);
        if (sesion == null) {
            return Response.ok(new AgregarProductoListaResponseDTO(-1, "No se recibieron datos para continuar")).build();
        }
        if (sesion.getIdSession() == null || sesion.getIdSession().trim().isEmpty()) {
            return Response.ok(new AgregarProductoListaResponseDTO(-1, "No se recibió un código de sesión a la cual asociar el producto")).build();
        }
        if (sesion.getProductos().isEmpty()) {
            return Response.ok(new AgregarProductoListaResponseDTO(-1, "No se recibió información del producto")).build();
        }
        if (sesion.getProductos().get(0) == null) {
            return Response.ok(new AgregarProductoListaResponseDTO(-1, "No se recibió información del producto")).build();
        }

        ProductoListaRegalosInvitadoDTO producto = sesion.getProductos().get(0);
        if (producto == null || producto.getReferencia() == null || producto.getReferencia().trim().isEmpty() || producto.getCantidad() <= 0) {
            return Response.ok(new AgregarProductoListaResponseDTO(-1, "No se recibió información del producto")).build();
        }
        CONSOLE.log(Level.INFO, sesion.toString());
        //Validar que la sesion exista
        SesionListaRegalosDTO sesionDto = applicationBean.obtenerSesionListaRegalos(sesion.getIdSession());
        if (sesionDto == null) {
            return Response.ok(new AgregarProductoListaResponseDTO(-1, "La sesion no es valida")).build();
        }

        ListaRegalos lista = listaRegalosFacade.consultarListaPorCodigo(codigoLista);
        ProductoListaRegalos productoLista = productoListaFacade.consultarPorListaProducto(lista.getIdLista(), producto.getIdProductoLista());
        if ((productoLista.getCantidadElegida() - productoLista.getCantidadComprada()) < producto.getCantidad()) {
            return Response.ok(new AgregarProductoListaResponseDTO(-1, "La cantidad a comprar es mayor que la cantidad elegida")).build();
        }

        sesionDto.agregarProducto(producto);
        return Response.ok(new AgregarProductoListaResponseDTO(0, null)).build();
    }

    @POST
    @Path("nocomprarproducto/")
    @Consumes({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response retirarProductoCarrito(SesionListaRegalosDTO sesion) {
        CONSOLE.log(Level.INFO, "Se recibio solicitud para retirar un producto de una lista del carrito de compras");
        if (sesion == null) {
            return Response.ok(new AgregarProductoListaResponseDTO(-1, "No se recibieron datos para continuar")).build();
        }
        if (sesion.getIdSession() == null || sesion.getIdSession().trim().isEmpty()) {
            return Response.ok(new AgregarProductoListaResponseDTO(-1, "No se recibió un código de sesión a la cual asociar el producto")).build();
        }
        if (sesion.getProductos().isEmpty()) {
            return Response.ok(new AgregarProductoListaResponseDTO(-1, "No se recibió información del producto")).build();
        }

        ProductoListaRegalosInvitadoDTO producto = sesion.getProductos().get(0);
        if (producto == null || producto.getReferencia() == null || producto.getReferencia().trim().isEmpty() || producto.getCantidad() <= 0) {
            return Response.ok(new AgregarProductoListaResponseDTO(-1, "No se recibió información del producto")).build();
        }
        CONSOLE.log(Level.INFO, sesion.toString());
        //Validar que la sesion exista
        SesionListaRegalosDTO sesionDto = applicationBean.obtenerSesionListaRegalos(sesion.getIdSession());
        if (sesionDto == null) {
            return Response.ok(new AgregarProductoListaResponseDTO(-1, "La sesion no es valida")).build();
        }

        sesionDto.eliminarProducto(producto);
        return Response.ok(new AgregarProductoListaResponseDTO(0, null)).build();
    }

    @POST
    @Path("consultarproductos/sinpaginar/")
    @Consumes({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response consultarProductosListaSinPaginar(ConsultaProductosListaDTO consulta) {
        List<ProductoListaRegalos> entidades;
        int productosValidos;
        List<ProductoListaRegalosDTO> dtos = new ArrayList<>();
        do {
            dtos = new ArrayList<>();
            productosValidos = 0;
            entidades = productoListaFacade.consultarPorLista(consulta.getIdLista());
            //Valida que el saldo de cada producto sea suficiente para la cantidad pendiente (solicitada-comprada)
            //Si un producto no cumple esta condicion, lo desactiva de la lista, notifica al propietario de la misma y 
            //vuelve a realizar la consulta
            for (ProductoListaRegalos entidad : entidades) {
                Integer saldo = saldoItemFacade.obtenerTotalSaldoVenta(entidad.getReferencia());

                if (entidad.getCantidadElegida() - entidad.getCantidadComprada() <= saldo) {
                    productosValidos++;
                    dtos.add(productoEntity2Dto(entidad));
                } else {
                    //TODO: notificar al propietario de la lista
                    CONSOLE.log(Level.WARNING, "El producto {0} no tiene saldo suficiente para satisfacer la cantidad solicitada. Req: {1}, Disp: {2}. Se desactiva el producto y se notifica al propietario de la lista",
                            new Object[]{entidad.getReferencia(), entidad.getCantidadElegida() - entidad.getCantidadComprada(), saldo});
                    //TODO: notificar al administrador de la lista para que se contacte con el cliente
                    try {
                        entidad.setActivo(false);
                        productoListaFacade.edit(entidad);
                    } catch (Exception e) {
                        return Response.ok(dtos).build();
                    }
                    break;
                }
            }
        } while (productosValidos != entidades.size());
        return Response.ok(dtos).build();
    }

    @POST
    @Path("consultarproductos/")
    @Consumes({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response consultarProductosLista(ConsultaProductosListaDTO consulta) {
        CONSOLE.log(Level.INFO, "entro en el servicio de consultarProductos");
        CONSOLE.log(Level.INFO, "id de la lista {0} ", consulta.getIdLista());

        List<ProductoListaRegalosDTO> dtos = new ArrayList();
        int productosValidos;

        List<ProductoListaRegalos> entidades;
        do {
            dtos = new ArrayList();
            productosValidos = 0;
            entidades = this.productoListaFacade.consultarPorListaPaginacion(consulta.getIdLista(), consulta
                    .getPagina(), consulta.getRegistrosPagina(), consulta.getOrderBy(), consulta.getKeywords());

            for (ProductoListaRegalos entidad : entidades) {
                //CONSOLE.log(Level.INFO, "producto lista {0} ", entidad.getDescripcionProducto());
                Integer saldo = this.saldoItemFacade.obtenerTotalSaldoVenta(entidad.getReferencia());

                if (entidad.getCantidadElegida() - entidad.getCantidadComprada() <= saldo) {
                    productosValidos++;
                    //if (entidad.getCantidadElegida() > entidad.getCantidadComprada()) {
                    dtos.add(productoEntity2Dto(entidad));
                    //}
                } else {
                    CONSOLE.log(Level.WARNING,
                            "El producto {0} no tiene saldo suficiente para satisfacer la cantidad solicitada. Req: {1}, Disp: {2}. Se desactiva el producto y se notifica al propietario de la lista",
                            new Object[]{entidad.getReferencia(), entidad.getCantidadElegida() - entidad.getCantidadComprada(), saldo});
                    try {
                        entidad.setActivo(false);
                        this.productoListaFacade.edit(entidad);
                    } catch (Exception e) {
                        CONSOLE.log(Level.SEVERE, "Ocurrio un error inesperado ", e);
                        return Response.ok(dtos).build();
                    }
                }
            }
        } while (productosValidos != entidades.size());

        return Response.ok(dtos).build();
    }

    @POST
    @Path("subirimagen/")
    @Produces(MediaType.TEXT_PLAIN)
    //@Consumes(MediaType.MULTIPART_FORM_DATA)
    @Consumes("multipart/form-data")
    public Response subirImagen(MultipartFormDataInput input) {
        Map<String, List<InputPart>> formParts = input.getFormDataMap();

        try {
            String codigo = formParts.get("codigo").get(0).getBody(String.class, null);
            CONSOLE.log(Level.INFO, "Codigo={0}", codigo);
            InputStream istream = formParts.get("file").get(0).getBody(InputStream.class, null);
            String fileName = parseFileName(formParts.get("file").get(0).getHeaders(), codigo, "01");
            String fullFilePath = applicationBean.obtenerValorPropiedad("url.archivo.listaRegalos") + "imagenPerfil" + File.separator + fileName;
            String fullWebPath = applicationBean.obtenerValorPropiedad("url.archivo.listaRegalos") + "imagenPerfil" + File.separator + fileName;
            CONSOLE.log(Level.INFO, "Guardando imagen de {0}", fileName);
            saveFile(istream, fullFilePath);
            try {
                ListaRegalos lista = listaRegalosFacade.consultarListaPorCodigo(codigo);
                if (lista != null) {
                    lista.setRutaImagenPortada(fullFilePath);
                } else {
                    lista.setRutaImagenPerfil(fullFilePath);
                }
                listaRegalosFacade.edit(lista);
            } catch (Exception e) {
                CONSOLE.log(Level.SEVERE, "Ocurrio un error al actualizar la ruta de la imagen de portada. ", e);
            }

            return Response.ok("OK").build();
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al recibir la imagen. ", e);
            return Response.ok("ERROR").build();
        }
        //TODO: parametrizar ruta de redireccion

    }

    @GET
    @Path("modificarcantidadelegida/{codigoLista}/{referencia}/{cantidad}")
    @Produces({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response modificarCantidadElegida(@PathParam("codigoLista") String codigoLista, @PathParam("referencia") String referencia, @PathParam("cantidad") Integer cantidad) {
        if (codigoLista != null || !codigoLista.isEmpty() || referencia != null || !referencia.isEmpty()) {
            ListaRegalos lista = listaRegalosFacade.consultarListaPorCodigo(codigoLista);

            if (lista != null) {
                Boolean modifico = productoListaFacade.actualizarCantidadElegida(lista.getIdLista(), referencia, cantidad);
                if (modifico) {
                    return Response.ok(true).build();
                }
            }
        }
        return Response.ok(false).build();
    }

    @GET
    @Path("generardocumento/{documento}/{codigoLista}")
    @Produces({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response generarDocumento(@PathParam("documento") String documento, @PathParam("codigoLista") String codigoLista) {
        String nombreDocumento = generarDocumento(1, documento, codigoLista);

        if (nombreDocumento != null && !nombreDocumento.isEmpty()) {
            return Response.ok(URI.create(applicationBean.obtenerValorPropiedad("listaregalos.url.web.document") + documento + "/" + nombreDocumento)).build();
        } else {
            return Response.ok(false).build();
        }
    }

    @GET
    @Path("cambiarfavorito/{idProducto}")
    @Produces({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response cambiarValorFavoritoProduco(@PathParam("idProducto") Long idProducto) {
        productoListaFacade.cambiarEstadoFavorito(idProducto);
        return Response.ok().build();
    }

    @GET
    @Path("desactivar/{codigoLista}")
    @Produces({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response desactivarLista(@PathParam("codigoLista") String codigoLista) {
        CONSOLE.log(Level.INFO, "Desactivando la lista {0}", codigoLista);
        listaRegalosFacade.desactivarLista(codigoLista);
        return Response.ok().build();
    }

    @GET
    @Path("consultartotalproductos/{idLista}")
    @Produces({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response consultarTotalProductosLista(@PathParam("idLista") Long idLista) {
        return Response.ok(productoListaFacade.consultarTotalProductosLista(idLista)).build();
    }

    @GET
    @Path("consultartotallistasusuario/{cedula}")
    @Produces({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response consultarTotalListasUsuario(@PathParam("cedula") String cedula) {
        return Response.ok(listaRegalosFacade.consultarTotalListasUsuario(cedula)).build();
    }

    @GET
    @Path("consultartotallistasadmin/")
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response consultarTotalListasUsuario() {
        return Response.ok(listaRegalosFacade.consultarTotalListasUsuario(null)).build();
    }

    @GET
    @Path("cambiarimagen/{codigoLista}/{nombre}/{tipo}")
    @Produces(MediaType.MULTIPART_FORM_DATA)
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response configurarImagenPredefinida(@PathParam("codigoLista") String codigoLista, @PathParam("nombre") String nombre, @PathParam("tipo") String tipo) {
        CONSOLE.log(Level.INFO, "Configurando la imagen {0} como {1} para la lista {2}", new Object[]{nombre, tipo, codigoLista});
        if (nombre == null || nombre.trim().isEmpty() || tipo == null || tipo.trim().isEmpty()
                || codigoLista == null || codigoLista.trim().isEmpty()) {
            CONSOLE.log(Level.SEVERE, "Los parametros enviados no son validos. No se configurara una nueva imagen");
            return Response.ok().build();
        }

        try {
            ListaRegalos lista = listaRegalosFacade.consultarListaPorCodigo(codigoLista);
            String rutaImagenes = applicationBean.obtenerValorPropiedad("url.archivo.listaRegalos");
            InputStream isStream = new FileInputStream(new File(rutaImagenes + "imagenes-predisenadas" + File.separator + lista.getTipoEvento().getIdTipoEvento() + File.separator + tipo + File.separator + nombre));
            String extension = nombre.substring(nombre.lastIndexOf("."));

            if (tipo.equals("portada")) {
                String fullFilePath = applicationBean.obtenerValorPropiedad("url.archivo.listaRegalos") + codigoLista + "_P" + extension;
                CONSOLE.log(Level.INFO, "Guardando imagen {0}", fullFilePath);
                saveFile(isStream, fullFilePath);
                lista.setRutaImagenPortada(applicationBean.obtenerValorPropiedad("listaregalos.url.web.document") + codigoLista + "_P" + extension);
            } else if (tipo.equals("perfil")) {
                String fullFilePath = applicationBean.obtenerValorPropiedad("url.archivo.listaRegalos") + codigoLista + extension;
                CONSOLE.log(Level.INFO, "Guardando imagen {0}", fullFilePath);
                saveFile(isStream, fullFilePath);
                lista.setRutaImagenPerfil(applicationBean.obtenerValorPropiedad("listaregalos.url.web.document") + codigoLista + extension);
            }

            listaRegalosFacade.edit(lista);
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al configurar la nueva imagen. ", e);
        }

        return Response.ok().build();
    }

    @GET
    @Path("notificacion/semanal")
    @Produces({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response ejecutarNotificacionesSemanales() {
        return Response.ok().build();
    }

    @GET
    @Path("consultareferencia/{idLista}/{ref}")
    @Produces({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response consultarItemAutocompletar(@PathParam("idLista") Long idLista, @PathParam("ref") String itemCode) {
        CONSOLE.log(Level.INFO, "Consultando item (autocompletar) con itemcode={0}", itemCode);
        if (itemCode == null || itemCode.isEmpty()) {
            return Response.ok().build();
        }
        if (itemCode.length() > 20 || itemCode.length() < 5) {
            return Response.ok().build();
        }
        if (itemCode.length() < 20) {
            itemCode = itemCode.replaceAll("\\.", "");
            itemCode = itemCode.replaceAll("\\*", "");
            itemCode = StringUtils.rightPad(itemCode.substring(0, 3), 20 - (itemCode.length() - 3), "0") + itemCode.substring(3);
        }

        try {
            ItemInventario entidad = itemFacade.getItemByItemCodeWithStock(itemCode);

            ProductoListaRegalosDTO dto = new ProductoListaRegalosDTO();
            dto.setDescripcionProducto(entidad.getItemName());
            dto.setReferencia(entidad.getItemCode());

            ProductoListaRegalos productoLista = productoListaFacade.consultarPorListaReferencia(idLista, dto.getReferencia());
            if (productoLista != null) {
                dto.setIdProductoLista(productoLista.getIdProductoLista());
                dto.setCantidadComprada(productoLista.getCantidadComprada());
                dto.setCantidadElegida(productoLista.getCantidadElegida());
                dto.setCantidadEntregada(productoLista.getCantidadEntregada());
                dto.setFavorito(productoLista.getFavorito());
                dto.setIdLista(productoLista.getLista().getIdLista());
            }

            return Response.ok(dto).build();
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al consultar el item. ", e);
        }
        return Response.ok().build();
    }

    private int consultarSaldoVentaItem(String referencia) {
        int saldoTotal = 0;

        try {
            List<SaldoItemInventario> saldos = saldoItemFacade.findWithParameters(referencia, "0", true);

            for (SaldoItemInventario saldo : saldos) {
                saldoTotal += saldo.getOnHand().intValue();
            }
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al consultar el saldo para el item " + referencia + ". ", e);
        }
        return saldoTotal;
    }

    private void crearCodigoQR(String codigoLista) {
        String myCodeText = applicationBean.obtenerValorPropiedad("listaregalos.url.web") + codigoLista;
        String filePath = applicationBean.obtenerValorPropiedad("url.archivo.listaRegalos") + "qr" + File.separator + codigoLista + ".png";

        CONSOLE.log(Level.INFO, "Creando codigo QR {0}", filePath);
        int size = 250;
        String fileType = "png";
        File myFile = new File(filePath);

        try {
            Map<EncodeHintType, Object> hintMap = new EnumMap<>(EncodeHintType.class);
            hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            // Now with zxing version 3.2.1 you could change border size (white border size to just 1)
            hintMap.put(EncodeHintType.MARGIN, 1);
            /* default = 4 */
            hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix byteMatrix = qrCodeWriter.encode(myCodeText, BarcodeFormat.QR_CODE, size, size, hintMap);
            int width = byteMatrix.getWidth();
            BufferedImage image = new BufferedImage(width, width, BufferedImage.TYPE_INT_RGB);
            image.createGraphics();

            Graphics2D graphics = (Graphics2D) image.getGraphics();
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, width, width);
            graphics.setColor(Color.BLACK);
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < width; j++) {
                    if (byteMatrix.get(i, j)) {
                        graphics.fillRect(i, j, 1, 1);
                    }
                }
            }
            ImageIO.write(image, fileType, myFile);
        } catch (WriterException | IOException e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al crear el codigo QR", e);
        }
    }

    private String parseFileName(MultivaluedMap<String, String> headers, String codigoLista, String tipo) {
        String[] contentDispositionHeader = headers.getFirst("Content-Disposition").split(";");
        String fileName = "";
        for (String name : contentDispositionHeader) {
            if ((name.trim().startsWith("filename"))) {
                String[] tmp = name.split("=");
                fileName = tmp[1].trim().replaceAll("\"", "");
                break;
            }
        }
        fileName = codigoLista + (tipo.equals("portada") ? "_P" : "") + fileName.substring(fileName.lastIndexOf("."));
        CONSOLE.log(Level.INFO, "Nombre del archivo {0}", fileName);
        return fileName;
    }

    private void saveFile(InputStream uploadedInputStream, String serverLocation) {
        try {
            OutputStream outpuStream = new FileOutputStream(new File(serverLocation));
            int read;
            byte[] bytes = new byte[1024];
            while ((read = uploadedInputStream.read(bytes)) != -1) {
                outpuStream.write(bytes, 0, read);
            }
            outpuStream.flush();
            outpuStream.close();
        } catch (IOException e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al guardar el archivo. ", e);
        }
    }

    private boolean crearInvitadosMasivos(List<Object[]> invitado, String codigoLista) {
        try {
            ListaRegalos lista = listaRegalosFacade.consultarListaPorCodigo(codigoLista);
            if (lista != null && !lista.toString().isEmpty() && invitado.size() > 0) {
                for (Object[] objs : invitado) {
                    if ((String) objs[0] != null && (String) objs[1] != null && (String) objs[2] != null && (String) objs[3] != null) {
                        if (ListaRegaloInvitado.validarInvitadoDuplicado(lista.getIdLista(), (String) objs[0], (String) objs[1], (String) objs[2], (String) objs[3])) {
                            ListaRegaloInvitado entity = new ListaRegaloInvitado();

                            entity.setIdLista(lista.getIdLista());
                            entity.setNombreInvitado((String) objs[0].toString().toUpperCase());
                            entity.setApellidosInvitado((String) objs[1].toString().toUpperCase());
                            entity.setCorreoInvitado((String) objs[2].toString().toUpperCase());
                            entity.setTelefonoInvitado((String) objs[3]);
                            entity.setAsistencia(false);

                            ListaRegaloInvitado.create(entity);

                            StringBuilder sb = new StringBuilder();
                            sb.append("Has sido invitad@ a la boda de ");
                            sb.append("<br/><table style=\"width: 100%;\"><tr><td style=\"text-align: center; font-size: 30px;\">");
                            sb.append(lista.getNombre().replace("Boda ", "").replace("&", "<span style=\"color: #E07D7D;\">&</span>"));
                            sb.append("</td></tr></table>");

                            Map<String, String> params = armarCorreo(sb.toString(),
                                    "<tr><td style=\"text-align: justify;\">Puedes entrar a la lista de regalos y darle lo que más gustes. El envió corre por cuenta nuestra.</td></tr>",
                                    (String) objs[0], lista.getCodigo(), "", entity.getIdInvitado());

                            enviarCorreo("lista_regalos", "Lista Regalos Matisses <listaideal@matisses.co>", "Lista Regalos Matisses", (String) objs[2],
                                    applicationBean.getDestinatariosPlantillaEmail().get("lista_regalos").getTo().get(0), null, params);
                        }
                    }
                }
            }
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error creando invitado masivo ", e);
            return false;
        }
        return true;
    }

    @GET
    @Path("eliminaritemlista/{idLista}/{ref}")
    @Produces({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response eliminarItemLista(@PathParam("idLista") Long idLista, @PathParam("ref") String itemCode) {

        CONSOLE.log(Level.INFO, "eliminar producto de la lista {0}", itemCode);

        if ((itemCode == null) || (itemCode.isEmpty())) {
            return Response.serverError().build();
        }

        if ((itemCode.length() > 20) || (itemCode.length() < 5)) {
            return Response.ok("item invalido").build();
        }

        if (itemCode.length() < 20) {
            itemCode = itemCode.replaceAll("\\.", "");
            itemCode = itemCode.replaceAll("\\*", "");
            itemCode = StringUtils.rightPad(itemCode.substring(0, 3), 20 - (itemCode.length() - 3), "0") + itemCode.substring(3);
        }

        try {
            ProductoListaRegalos producto = this.productoListaFacade.consultarPorListaReferencia(idLista, itemCode);
            if ((producto.getCantidadComprada() < producto.getCantidadElegida()) || (producto.getCantidadEntregada() < producto.getCantidadElegida())) {
                this.productoListaFacade.eliminarProductoLista(idLista, itemCode);
            }
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al consultar el item. ", e);
        }
        return Response.ok('1').build();
    }

    @POST
    @Path("modificarmsjagradecimiento/")
    @Consumes({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response modificarMsjAgradecimiento(ListaRegalosDTO listaRegalo) {
        if (listaRegalo.getCodigo() != null || !listaRegalo.getCodigo().isEmpty()) {
            ListaRegalos lista = listaRegalosFacade.consultarListaPorCodigo(listaRegalo.getCodigo());

            if (lista != null) {
                boolean modifico = listaRegalosFacade.actualizarMensajeAgradecimiento(lista.getIdLista(), listaRegalo.getMensajeAgradecimiento());
                if (modifico) {
                    return Response.ok(true).build();
                }
            }
        }
        return Response.ok(false).build();
    }

    @POST
    @Path("consultarcomprados/")
    @Consumes({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response consultarProductosComprados(ConsultaProductosListaDTO consulta) {
        CONSOLE.log(Level.INFO, "entro en el servicio de consultarProductos");
        CONSOLE.log(Level.INFO, "id de la lista {0} ", consulta.getIdLista());

        List<ProductoListaRegalosDTO> dtos = new ArrayList();
        int productosValidos;

        List<ProductoListaRegalos> entidades;
        do {
            dtos = new ArrayList();
            productosValidos = 0;
            entidades = this.productoListaFacade.consultarPorListaComprados(consulta.getIdLista(), consulta
                    .getPagina(), consulta.getRegistrosPagina(), consulta.getOrderBy(), consulta.getKeywords());

            for (ProductoListaRegalos entidad : entidades) {
                CONSOLE.log(Level.INFO, "producto lista {0} ", entidad.getDescripcionProducto());
                Integer saldo = this.saldoItemFacade.obtenerTotalSaldoVenta(entidad.getReferencia());

                if (entidad.getCantidadElegida() - entidad.getCantidadComprada() <= saldo) {
                    productosValidos++;

                    dtos.add(productoEntity2Dto(entidad));

                } else {
                    CONSOLE.log(Level.WARNING,
                            "El producto {0} no tiene saldo suficiente para satisfacer la cantidad solicitada. Req: {1}, Disp: {2}. Se desactiva el producto y se notifica al propietario de la lista",
                            new Object[]{entidad.getReferencia(), entidad.getCantidadElegida() - entidad.getCantidadComprada(), saldo});
                    try {
                        entidad.setActivo(false);
                        this.productoListaFacade.edit(entidad);
                    } catch (Exception e) {
                        CONSOLE.log(Level.SEVERE, "Ocurrio un error inesperado ", e);
                        return Response.ok(dtos).build();
                    }
                }
            }
        } while (productosValidos != entidades.size());

        return Response.ok(dtos).build();
    }

    @GET
    @Path("updateasistenciainvitado/{idLista}/{idInvitado}")
    @Produces({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response updateConfirmarAsistencia(@PathParam("idLista") String codigoLista, @PathParam("idInvitado") Long idInvitado) {
        CONSOLE.log(Level.INFO, "confirmando asistencia de la lista {0}", codigoLista);
        //Consultar codigo lista
        Long idLista = listaRegalosFacade.consultarCodigoLista(codigoLista);
        if (idLista != 0L) {
            Boolean confirmarAsistencia = ListaRegaloInvitado.confirmarAsistencia(idLista, idInvitado);
            return Response.ok(confirmarAsistencia).build();
        }
        return Response.ok(false).build();
    }

    @POST
    @Path("updatefechaentrega/")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response updateFechaEntrega(ListaRegalosDTO datos) {
        CONSOLE.log(Level.INFO, "lee el json  [{0}]", datos.getIdLista());
        if (datos == null) {
            return Response.ok(new ConsultaListasResponseDTO(-1, "No se recibieron datos para actualizar la fecha de entrega.")).build();
        }

        if (datos.getFechaEntrega() == null) {
            return Response.ok(new ConsultaListasResponseDTO(-1, "Debe programar una fecha de entrega.")).build();
        }

        if (datos.getFechaEntrega().before(new Date())) {
            return Response.ok(new ConsultaListasResponseDTO(-1, "La fecha de entrega no puede ser anterior a la fecha actual.")).build();
        }

//        if (datos.getFechaEntrega().before(datos.getFechaEvento())) {
//            CONSOLE.log(Level.SEVERE, "La fecha de entrega no puede ser anterior a la fecha del evento");
//            return Response.ok(new CrearListaResponseDTO(-1, "La fecha de entrega no puede ser anterior a la fecha del evento.")).build();
//        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_YEAR, Integer.parseInt(applicationBean.obtenerValorPropiedad("dias.tentativa.novias")));
        Date fechaTentativa = calendar.getTime();

        Long rango = ((fechaTentativa.getTime() / 86400000) - (datos.getFechaEntrega().getTime() / 86400000));
        if (rango > 0) {
            return Response.ok(new ConsultaListasResponseDTO(-1, "La fecha de entrega esta fuera del rango permitido.")).build();
        }

        boolean actualizo = listaRegalosFacade.actualizarFechaEntrega(datos.getIdLista(), new SimpleDateFormat("yyyy-MM-dd").format(datos.getFechaEntrega()));
        if (!actualizo) {
            return Response.ok(new ConsultaListasResponseDTO(-1, "Ocurrio un error actualizando la fecha de entrega.")).build();
        } else {
            try {
                ListaRegalos lista = listaRegalosFacade.consultarListaPorId(datos.getIdLista());
                if (lista != null && lista.getIdLista() != null && lista.getIdLista() != 0) {
                    StringBuilder sb = new StringBuilder();

                    sb.append("<table style=\"width: 100%;\"><tr><td style=\"text-align: center;\">La entrega para la lista de regalos con código <span style=\"color: #E07D7D;\"> ");
                    sb.append("<br/><table style=\"width: 100%;\"><tr><td style=\"text-align: center; font-size: 30px;\">");
                    sb.append(lista.getCodigo());
                    sb.append("</span> quedo programada para la fecha<br/><span style=\"color: #E07D7D; font-size: 30px;\">");
                    sb.append(new SimpleDateFormat("dd").format(datos.getFechaEntrega()));
                    sb.append(" de ");
                    sb.append(meses[Integer.parseInt(new SimpleDateFormat("MM").format(datos.getFechaEntrega())) - 1]);
                    sb.append(" de ");
                    sb.append(new SimpleDateFormat("yyyy").format(datos.getFechaEntrega()));
                    sb.append("</span></td></tr></table>");

                    StringBuilder sb1 = new StringBuilder();

                    sb1.append("<tr><td style=\"text-align: justify;\"><br/>Estos son tus datos de contacto</td></tr><tr><td style=\"text-align: justify;\"><b>Nombre: </b>");
                    sb1.append(lista.getNombreCreador()).append(" ").append(lista.getApellidoCreador());
                    sb1.append("</td></tr><tr><td style=\"text-align: justify;\"><b>Celular: </b>");
                    sb1.append(lista.getTelefonoCreador());
                    sb1.append("</td></tr><tr><td style=\"text-align: justify;\"><b>Direccion: </b>");
                    sb1.append(lista.getDireccionCreador());
                    sb1.append("</td></tr><tr><td style=\"text-align: justify;\"><br/>*En caso de que no estés presente el día de la entrega, envía un correo a ");
                    sb1.append("<b>servicioalcliente@matisses.co</b> o envía un mensaje a los números <b>3148904124</b> o <b>3146285988</b> con los datos de la persona que autorizas para recibir los regalos.</td></tr>");

                    Map<String, String> params = armarCorreo(sb.toString(), sb1.toString(), lista.getNombreCreador(), lista.getCodigo(), "", 0L);
                    /*Se envia el correo con la programacion de entrega*/
                    enviarCorreo("lista_regalos", "Lista Regalos Matisses <listaideal@matisses.co>", "Lista Regalos Matisses", lista.getCorreoCreador(),
                            applicationBean.getDestinatariosPlantillaEmail().get("lista_regalos").getTo().get(0), null, params);
                }
            } catch (Exception e) {
                CONSOLE.log(Level.SEVERE, "Ocurrio un error enviando la notificacion de programacion de fecha de entrega. ", e);
                return Response.ok(new ConsultaListasResponseDTO(-1, "Ocurrio un error enviando la notificación de programación de fecha de entrega.")).build();
            }
            return Response.ok(new ConsultaListasResponseDTO(0, "Lista actualizada correctamente")).build();
        }
    }

    @POST
    @Path("consultarcomprap/")
    @Consumes({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response consultarCompraP(ConsultaCompraListaRegalosDTO consulta) {
        CONSOLE.log(Level.INFO, "entro en el servicio de consultarcomprap");
        CONSOLE.log(Level.INFO, "id de la lista {0} ", consulta.getIdLista());

        List<CompraListaRegalosDTO> dtos = new ArrayList();

        List<CompraListaRegalos> entidades;

        ProductoListaRegalos productoListaRegalos = productoListaFacade.consultarPorListaReferencia(consulta.getIdLista(), consulta.getReferencia());

        if (productoListaRegalos != null) {
            entidades = this.compraListaRegalosFacade.consultarCompraProducto(consulta.getIdLista(), productoListaRegalos.getIdProductoLista());

            for (CompraListaRegalos entidad : entidades) {
                // if (entidad.getDevuelto()) {

                dtos.add(compraEntity2Dto(entidad));
                //}
            }
            return Response.ok(dtos).build();
        } else {

        }
        return Response.ok(dtos).build();
    }

    /*Metodo de devolucion*/
    @POST
    @Path("devolucion/{idlista}/{factura}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response crearNotaCredito(@PathParam("idlista") Integer idLista, @PathParam("factura") String factura, SalesDocumentLineDTO line) {
        CONSOLE.log(Level.INFO, "entra en el servicio lista={0}", idLista);
        FacturaSAP fac = facturaSAPFacade.findByDocNum(Integer.parseInt(factura));

        if (fac != null && fac.getDocEntry() != null && fac.getDocEntry() != 0) {
            Long docNum = crearNotaCredito(idLista, fac.getDocEntry().longValue(), fac.getCardCode(), String.valueOf(fac.getDocNum()), fac.getGroupNum().toString(), fac.getUWuid(), line);
            if (docNum > 0) {
                return Response.ok(docNum).build();
            } else {
                return Response.ok(-1).build();
            }
        }
        return Response.ok(-1).build();
    }

    @POST
    @Path("subirarchivo/")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes("multipart/form-data")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response subirArchivoMasivo(MultipartFormDataInput input) throws IOException {
        Map<String, List<InputPart>> formParts = input.getFormDataMap();
        String codigoLista = formParts.get("codigo").get(0).getBody(String.class, null);

        if (codigoLista == null || codigoLista.trim().isEmpty()) {
            return Response.ok(new CrearInvitadoResponseDTO(-1, "No se obtuvo el código de la lista.")).build();
        }

        try {
            InputStream istream = formParts.get("file").get(0).getBody(InputStream.class, null);
            String fileName = parseFileName(formParts.get("file").get(0).getHeaders(), codigoLista, "01");
            saveFile(istream, applicationBean.obtenerValorPropiedad("url.archivo.listaRegalos") + "\\invitados\\cargaMasiva\\" + fileName);

            InputStream stream2 = new FileInputStream(new File(applicationBean.obtenerValorPropiedad("url.archivo.listaRegalos") + "\\invitados\\cargaMasiva\\" + fileName));
            List<Object[]> invitados = recibirExcel(stream2, fileName);

            boolean resp = crearInvitadosMasivos(invitados, codigoLista);
            if (resp) {
                return Response.ok(true).build();
            } else {
                return Response.ok(false).build();
            }
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al recibir el archivo ", e);
            return Response.ok(false).build();
        }
    }

    private List<Object[]> recibirExcel(InputStream is, String nombre) {
        ExcelReaderInvitados excel = new ExcelReaderInvitados();
        excel.setInputStream(is);
        excel.setNombre(nombre);

        return excel.interpretarExcel();
    }

    private Long crearNotaCredito(Integer idLista, Long docEntry, String cardCode, String docNum, String groupNum, String wuid, SalesDocumentLineDTO line) {
        SalesDocumentDTO document = new SalesDocumentDTO();
        CONSOLE.log(Level.INFO, "crearNotaCredito={0}", cardCode);
        if (docEntry != null && docEntry != 0) {
            FacturaSAP factura = facturaSAPFacade.find(docEntry.intValue());

            if (factura != null && factura.getDocEntry() != null && factura.getDocEntry() != 0) {
                document.setSalesPerson(factura.getSlpCode().longValue());

                if (factura.getuVendedor1() != null && !factura.getuVendedor1().isEmpty()) {
                    SalesEmployeeDTO dto = new SalesEmployeeDTO(Double.parseDouble(factura.getuComVend1()), factura.getuVendedor1(), salesPersonFacade.obtenerSlpCode(factura.getuVendedor1()).toString());
                    document.getSalesEmployees().add(dto);
                }
                if (factura.getuVendedor2() != null && !factura.getuVendedor2().isEmpty()) {
                    SalesEmployeeDTO dto = new SalesEmployeeDTO(Double.parseDouble(factura.getuComVend1()), factura.getuVendedor2(), salesPersonFacade.obtenerSlpCode(factura.getuVendedor2()).toString());
                    document.getSalesEmployees().add(dto);
                }
                if (factura.getuVendedor3() != null && !factura.getuVendedor3().isEmpty()) {
                    SalesEmployeeDTO dto = new SalesEmployeeDTO(Double.parseDouble(factura.getuComVend1()), factura.getuVendedor3(), salesPersonFacade.obtenerSlpCode(factura.getuVendedor3()).toString());
                    document.getSalesEmployees().add(dto);
                }
                if (factura.getuVendedor4() != null && !factura.getuVendedor4().isEmpty()) {
                    SalesEmployeeDTO dto = new SalesEmployeeDTO(Double.parseDouble(factura.getuComVend1()), factura.getuVendedor4(), salesPersonFacade.obtenerSlpCode(factura.getuVendedor4()).toString());
                    document.getSalesEmployees().add(dto);
                }
                if (factura.getuVendedor5() != null && !factura.getuVendedor5().isEmpty()) {
                    SalesEmployeeDTO dto = new SalesEmployeeDTO(Double.parseDouble(factura.getuComVend1()), factura.getuVendedor5(), salesPersonFacade.obtenerSlpCode(factura.getuVendedor5()).toString());
                    document.getSalesEmployees().add(dto);
                }
            }
        }
        document.setDocEntry(docEntry);
        document.setRefDocnum(docNum);
        document.setCardCode(cardCode);
        document.setComments("Devolución generada por BCS ");
        if (!docNum.isEmpty()) {
            document.setComments(document.getComments() + " para la factura " + docNum);
        }
        document.setComments(document.getComments() + ". Realizada desde Lista de regalos");
        document.setPaymentGroupCode(groupNum);
        document.setSource("L");
        document.setSeriesCode(serieFacade.obtenerSerieNotaCredito().getSeries().toString());
        document.setWuid(wuid);
        document.setCreditNoteType("D");

        List<SalesDocumentLineDTO> detalle = new ArrayList<>();
        List<DetalleFacturaSAP> detFac = detalleFacturaSAPFacade.obtenerDetalleFactura(docEntry.doubleValue());

        int linea = 0;
        int cantidad = line.getQuantity();
        if (docEntry > 0) {
            for (DetalleFacturaSAP d : detFac) {
                document.setSalesCostingCode(d.getOcrCode2());

                line.setWhsCode("9803");
                line.setLineNum(linea);

                if (d.getItemCode().equals(line.getItemCode()) && d.getQuantity().intValue() > 0) {
                    line.setPrice(d.getPriceAfVAT());
                    line.setShippingStatus(d.getUEstadoP());
                    line.setTaxCode(d.getTaxCode());
                    document.setShippingStatus(d.getUEstadoP());

                    if (d.getDiscPrcnt() != null && d.getDiscPrcnt().intValue() > 0) {
                        line.setDiscountPercent(d.getDiscPrcnt().doubleValue());
                    }

                    List<SaldoItemInventario> s = saldoItemInventarioFacade.obtenerSaldoAlmacenItemCode(line.getWhsCode(), line.getItemCode());

                    if (s != null && s.isEmpty()) {
                        if (d.getQuantity().intValue() >= line.getQuantity()) {
                            line.setGrossBuyPrice(facturaSAPFacade.obtenerCostoReferencia(docEntry.intValue(), line.getItemCode(), d.getWhsCode()));
                            d.setQuantity(new BigDecimal(d.getQuantity().intValue() - line.getQuantity()));
                            saldoItemInventarioFacade.alterarCostoAlmacen(line.getGrossBuyPrice(), d.getItemCode(), "9803");

                            detalle.add(new SalesDocumentLineDTO(line));
                            cantidad = 0;
                            linea++;
                            break;
                        } else if (d.getQuantity().intValue() < line.getQuantity()) {
                            line.setGrossBuyPrice(facturaSAPFacade.obtenerCostoReferencia(docEntry.intValue(), line.getItemCode(), d.getWhsCode()));
                            d.setQuantity(new BigDecimal(0));
                            saldoItemInventarioFacade.alterarCostoAlmacen(line.getGrossBuyPrice(), d.getItemCode(), "9803");

                            detalle.add(new SalesDocumentLineDTO(line));
                            cantidad -= d.getQuantity().intValue();
                            linea++;
                        }
                    } else {
                        return 0L;
                    }
                }
            }
        }

        if (detalle != null && !detalle.isEmpty()) {
            document.setDocumentLines(detalle);

            try {
                ResponseDTO res = creditNotesREST.createCreditNote(document);

                if (res.getEstado() == 0) {
                    return 0L;
                } else {
                    DevolucionSAP dev = devolucionSAPFacade.find(res.getValor());
                    //crearSalida(dev.getDocNum(), docNum); Por solicitud de logistica no hacer la salida
                    //validarTipoNotaCredito(docNum, line);
                    CONSOLE.log(Level.INFO, "else de la devolucion={0}", dev.getDocNum());
                    ListaRegalos lista = listaRegalosFacade.consultarListaPorId(idLista.longValue());

                    Long idEntradaDiario = crearAsientoAjusteSaldoFavor(dev.getDocNum().toString(), dev.getDocTotal().longValue(), cardCode, lista.getCedulaCreador());
                    CONSOLE.log(Level.INFO, "si crea el asiento contable con id={0}", idEntradaDiario);
                    if (lista != null && lista.getIdLista() != null && lista.getIdLista() != 0) {
                        StringBuilder sb = new StringBuilder();
                        ProductoListaRegalos productoLista = productoListaFacade.consultarPorListaReferencia(idLista.longValue(), line.getItemCode());
                        if (productoLista != null) {
                            ArrayList<CompraListaRegalos> listaCompra = (ArrayList<CompraListaRegalos>) compraListaRegalosFacade.consultarCompraProducto(idLista.longValue(), productoLista.getIdProductoLista());

                            for (CompraListaRegalos compraListaRegalos : listaCompra) {
                                compraListaRegalos.setDevuelto(true);
                                compraListaRegalosFacade.edit(compraListaRegalos);
                            }
                        }

                        sb.append("<table style=\"width: 100%;\"><tr><td style=\"text-align: justify;\">Acabas de realizar una devolución de un regalo. ");
                        sb.append("El monto de este regalo será recargado en tu tarjeta de <b>Regalos Matisses</b>.</td></tr></table>");

                        StringBuilder sb1 = new StringBuilder();

                        sb1.append("<table style=\"width: 100%; border: 1px solid #DDDDDD; margin-top: 10px; border-collapse: collapse;\">");
                        sb1.append("<tr>");
                        sb1.append("<th style=\"border: 1px solid #DDDDDD;\">Imagen</th>");
                        sb1.append("<th style=\"border: 1px solid #DDDDDD; margin: 10px;\">PRODUCTO</th>");
                        sb1.append("<th style=\"border: 1px solid #DDDDDD; margin: 10px;\">MONTO</th>");
                        sb1.append("<th style=\"border: 1px solid #DDDDDD; margin: 10px;\">CANTIDAD</th>");
                        sb1.append("</tr>");
                        sb1.append("<tr>");
                        sb1.append("<td style=\"border: 1px solid #DDDDDD; text-align: center; margin: 10px;\"><img src=\"");
                        sb1.append(imagenProductoMBean.obtenerUrlProducto(line.getItemCode(), true));
                        sb1.append("\"></img></td>");
                        sb1.append("<td style=\"border: 1px solid #DDDDDD; margin: 10px;\">");
                        sb1.append(genericMBean.obtenerNombreReferencia(line.getItemCode()));
                        sb1.append("<br/>Ref: ");
                        sb1.append(genericMBean.convertirARefCorta(line.getItemCode()).replace("*", ""));
                        sb1.append("</td>");
                        sb1.append("<td style=\"border: 1px solid #DDDDDD; margin: 10px; text-align: right;\">$ ");
                        sb1.append(genericMBean.getValorFormateado("Double", dev.getDocTotal().toString(), 0));
                        sb1.append("</td>");
                        sb1.append("<td style=\"border: 1px solid #DDDDDD; margin: 10px; text-align: right;\">");
                        sb1.append(line.getQuantity());
                        sb1.append("</td>");
                        sb1.append("</tr>");
                        sb1.append("</table>");

                        Map<String, String> params = armarCorreo(sb.toString(), "", lista.getNombreCreador(), lista.getCodigo(), sb1.toString(), 0L);

                        enviarCorreo("lista_regalos", "Devolución Lista Regalos Matisses <listaideal@matisses.co>", "Devolución Lista Regalos Matisses", lista.getCorreoCreador(),
                                applicationBean.getDestinatariosPlantillaEmail().get("lista_regalos").getTo().get(0), null, params);
                    }

                    return dev.getDocNum().longValue();
                }
            } catch (Exception e) {
            }
        }
        return 0L;
    }

    private void crearSalida(Integer notaCredito, String docNum) {
        GoodsIssueDTO document = new GoodsIssueDTO();

        document.setComments("Doc. creado con B1WS Segun Nota Credito #" + notaCredito + " (DocEntry) para Factura #" + docNum);
        document.setJournalMemo("Salida de mercancia de clientes");
        document.setSeries("26");
        document.setGroupNum(String.valueOf(-1L));
        document.setInvoiceNumber(docNum);
        document.setuOrigen("T");

        DevolucionSAP dev = devolucionSAPFacade.obtenerDevolucion(notaCredito);

        if (dev != null && dev.getDocEntry() != null && dev.getDocEntry() != 0) {
            List<DetalleDevolucionSAP> det = detalleDevolucionSAPFacade.obtenerDetalleDevolucion(dev.getDocEntry());

            if (det != null && !det.isEmpty()) {
                for (DetalleDevolucionSAP d : det) {
                    if (d.getuEstadoP().equals("P") || d.getuEstadoP().equals("G")) {
                        GoodsIssueDetailDTO dto = new GoodsIssueDetailDTO();

                        dto.setAccountCode("91051001");
                        dto.setItemCode(d.getItemCode());
                        dto.setLineNum(String.valueOf(d.getDetalleDevolucionSAPPK().getLineNum()));
                        dto.setQuantity(String.valueOf(d.getQuantity()));

                        List<SaldoUbicacion> saldos = saldoUbicacionFacade.obtenerSaldoCliente(d.getItemCode());

                        if (saldos != null && !saldos.isEmpty()) {
                            for (SaldoUbicacion s : saldos) {
                                GoodsIssueLocationsDTO loc = new GoodsIssueLocationsDTO();

                                if (s.getOnHandQty().intValue() >= d.getQuantity().intValue()) {
                                    loc.setBinAbs(s.getUbicacion().getAbsEntry().toString());
                                    loc.setQuantity(String.valueOf(d.getQuantity().intValue()));
                                    d.setQuantity(new BigDecimal(0));
                                } else {
                                    loc.setBinAbs(s.getUbicacion().getAbsEntry().toString());
                                    loc.setQuantity(String.valueOf(s.getOnHandQty().intValue()));
                                    d.setQuantity(new BigDecimal(d.getQuantity().intValue() - s.getOnHandQty().intValue()));
                                }

                                dto.addLocation(loc);

                                if ((dto.getWhsCode() != null && !s.getWhsCode().equals(dto.getWhsCode())) || d.getQuantity().intValue() == 0) {
                                    dto.setWhsCode(s.getWhsCode());
                                    document.addDetail(dto);
                                    break;
                                } else {
                                    dto.setWhsCode(s.getWhsCode());
                                }
                            }
                        }
                    }
                }

                try {
                    ResponseDTO res = goodsIssueREST.createGoodsIssue(document);

                    if (res.getEstado() > 0) {
                        CONSOLE.log(Level.INFO, "Se creo salida de mercancia numero {0}", res.getValor());
                    } else {
                        CONSOLE.log(Level.SEVERE, "Ocurrio un error al crear la salida de mercancia para la nota credito. {0}", res.getMensaje());
                    }
                } catch (Exception e) {
                }
            }
        }
    }

    private void validarTipoNotaCredito(String docNum, SalesDocumentLineDTO line) {
        FacturaSAP factura = facturaSAPFacade.findByDocNum(Integer.parseInt(docNum));

        if (factura != null && factura.getDocEntry() != null && factura.getDocEntry() != 0) {
            List<DetalleFacturaSAP> detalle = detalleFacturaSAPFacade.obtenerDetalleFactura(factura.getDocEntry().doubleValue());

            if (detalle != null && !detalle.isEmpty() && detalle.size() == 1) {
                if (detalle.get(0).getItemCode().equals(line.getItemCode()) && detalle.get(0).getQuantity().intValue() == line.getQuantity()) {
                    ReciboCaja recibo = reciboCajaFacade.obtenerReciboCaja(docNum);

                    if (recibo == null || recibo.getDocEntry() == null || recibo.getDocEntry() == 0) {
                        CONSOLE.log(Level.SEVERE, "No se encontraron recibos de cajas relacionados con la FV {0}", docNum);
                        return;
                    }

                    try {
                        ResponseDTO res = incomingPaymentREST.cancelIncomingPayment(recibo.getDocEntry().longValue());

                        if (res.getEstado() == 1) {
                            CONSOLE.log(Level.INFO, "Se cancelo el recibo de caja con ID {0}", recibo.getDocEntry());
                        } else {
                            CONSOLE.log(Level.SEVERE, "Ocurrio un error al cancelar el recibo de caja");
                        }
                    } catch (Exception e) {
                        CONSOLE.log(Level.SEVERE, "Ocurrio un error al cancelar el recibo de caja. ", e);
                    }
                }
            }
        }
    }

    private Long crearAsientoAjusteSaldoFavor(String docNumRC, Long valorBono, String cedulaPagador, String cedulaPropietario) throws Exception {
        if (!cedulaPagador.toUpperCase().contains("CL")) {
            cedulaPagador = cedulaPagador + "CL";
        }
        if (!cedulaPropietario.toUpperCase().contains("CL")) {
            cedulaPropietario = cedulaPropietario + "CL";
        }

        String sesionSAP = sapB1WSBean.obtenerSesionSAP();
        CONSOLE.log(Level.INFO, "entra a crear el asiento {0}", docNumRC);
        JournalEntryDTO journalEntryHeader = new JournalEntryDTO();
        journalEntryHeader.setMemo("BONO LISTA DE REGALOS");
        journalEntryHeader.setRef1(docNumRC);
        journalEntryHeader.setTransactionCode("BONO");//TODO: parametrizar codigo de asiento
        journalEntryHeader.setDueDate(new Date());
        journalEntryHeader.setRefDate(new Date());
        journalEntryHeader.setTaxDate(new Date());

        JournalEntryLineDTO line1 = new JournalEntryLineDTO();
        line1.setLineId(0L);
        line1.setCredit(0D);
        line1.setDebit(valorBono.doubleValue());
        line1.setShortName(cedulaPagador);
        line1.setContraAccount(cedulaPropietario);
        line1.setInfoCo01(cedulaPagador);

        JournalEntryLineDTO line2 = new JournalEntryLineDTO();
        line2.setLineId(1L);
        line2.setCredit(valorBono.doubleValue());
        line2.setDebit(0D);
        line2.setShortName(cedulaPropietario);
        line2.setContraAccount(cedulaPagador);
        line2.setInfoCo01(cedulaPropietario);

        journalEntryHeader.addLine(line1);
        journalEntryHeader.addLine(line2);
        JournalEntriesServiceConnector jesc = sapB1WSBean.getJournalEntriesServiceConnectorInstance(sesionSAP);
        Long idEntradaDiario = jesc.createJournalEntry(journalEntryHeader);
        CONSOLE.log(Level.INFO, "Se creo el asiento contable con id={0}", idEntradaDiario);
        return idEntradaDiario;
    }

    @GET
    @Path("consultartotalcomprado/{idLista}")
    @Produces({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response consultarTotalCompradoLista(@PathParam("idLista") String codigoLista) {
        CONSOLE.log(Level.INFO, "confirmando asistencia de la lista {0}", codigoLista);
        //Consultar codigo lista
        Long idLista = listaRegalosFacade.consultarCodigoLista(codigoLista);
        if (idLista != 0L) {
            Long csumTotal = compraListaRegalosFacade.consultarTotalCompradoLista(idLista);
            return Response.ok(csumTotal).build();
        }
        return Response.ok("-1").build();
    }

    @GET
    @Path("consultarplanificadores")
    @Produces({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response obtenerListadoPlanificadoresActivos() {
        List<BaruDecoradores> planificador = baruDecoradoresFacade.obtenerPlanificadoresActivos();
        return Response.ok(planificador).build();
    }

//        @POST
//    @Path("consultarlistasactivas/")
//    @Consumes({MediaType.APPLICATION_JSON + ";charset=utf-8"})
//    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
//    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
//    public Response consultarListasActivas() {
//       
//
//        List<ListaRegalosDTO> dtos = new ArrayList();
//        int productosValidos;
//
//        List<ListaRegalos> entidades;
//        do {
//            dtos = new ArrayList();
//            productosValidos = 0;
//            entidades = this.listaRegalosFacade.consultarListasActivas();
//
//            for (ListaRegalos entidad : entidades) {
//                
//
//                if (entidad.getCantidadElegida() - entidad.getCantidadComprada() <= saldo) {
//                    productosValidos++;
//
//                    dtos.add(productoEntity2Dto(entidad));
//
//                } else {
//                    CONSOLE.log(Level.WARNING,
//                            "El producto {0} no tiene saldo suficiente para satisfacer la cantidad solicitada. Req: {1}, Disp: {2}. Se desactiva el producto y se notifica al propietario de la lista",
//                            new Object[]{entidad.getReferencia(), entidad.getCantidadElegida() - entidad.getCantidadComprada(), saldo});
//                    try {
//                        entidad.setActivo(false);
//                        this.productoListaFacade.edit(entidad);
//                    } catch (Exception e) {
//                        CONSOLE.log(Level.SEVERE, "Ocurrio un error inesperado ", e);
//                        return Response.ok(dtos).build();
//                    }
//                }
//            }
//        } while (productosValidos != entidades.size());
//
//        return Response.ok(dtos).build();
//    }
}
