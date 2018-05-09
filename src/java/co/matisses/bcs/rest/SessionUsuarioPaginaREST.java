package co.matisses.bcs.rest;

import co.matisses.bcs.b1ws.client.B1WSServiceUnavailableException;
import co.matisses.bcs.b1ws.client.businesspartners.BusinessPartnerREST;
import co.matisses.bcs.b1ws.client.serviceCalls.ServiceCallsREST;
import co.matisses.bcs.b1ws.ws.businessparters.BusinessPartner;
import co.matisses.bcs.b1ws.ws.serviceCalls.ServiceCall;
import co.matisses.bcs.dto.CategoriaListaRegalosDTO;
import co.matisses.bcs.dto.ClienteSAPDTO;
import co.matisses.bcs.dto.ConsultaUsuarioResponseDTO;
import co.matisses.bcs.dto.DetallePedidoDTO;
import co.matisses.bcs.dto.EstadoListaRegalosDTO;
import co.matisses.bcs.dto.ListaNoviosDecoradorDTO;
import co.matisses.bcs.dto.ListaRegalosDTO;
import co.matisses.bcs.dto.MailMessageDTO;
import co.matisses.bcs.dto.PedidoDTO;
import co.matisses.bcs.dto.ProductoListaRegalosDTO;
import co.matisses.bcs.dto.PuntosWpDTO;
import co.matisses.bcs.dto.ResponseDTO;
import co.matisses.bcs.dto.TipoEventoDTO;
import co.matisses.bcs.dto.UsuarioPaginaDTO;
import co.matisses.bcs.dto.UsuarioPaginaRedencionDTO;
import co.matisses.bcs.dto.VentaDTO;
import co.matisses.bcs.mbean.BCSApplicationMBean;
import co.matisses.bcs.mbean.BCSGenericMBean;
import co.matisses.bcs.mbean.ImagenProductoMBean;
import co.matisses.bcs.mbean.JWTTokenManager;
import co.matisses.persistence.sap.entity.DetalleDevolucionSAP;
import co.matisses.persistence.sap.entity.DetalleFacturaSAP;
import co.matisses.persistence.sap.entity.DevolucionSAP;
import co.matisses.persistence.sap.entity.FacturaSAP;
import co.matisses.persistence.sap.entity.BaruAverias;
import co.matisses.persistence.sap.entity.BaruMateriales;
import co.matisses.persistence.sap.facade.BaruAveriasFacade;
import co.matisses.persistence.sap.facade.BaruDecoradoresFacade;
import co.matisses.persistence.sap.facade.DevolucionSAPFacade;
import co.matisses.persistence.sap.facade.BaruMaterialesFacade;
import co.matisses.persistence.sap.facade.DetalleFacturaSAPFacade;
import co.matisses.persistence.sap.facade.FacturaSAPFacade;
import co.matisses.persistence.web.entity.ListaRegalos;
import co.matisses.persistence.web.entity.ProductoListaRegalos;
import co.matisses.persistence.web.entity.ProveedoresExterior;
import co.matisses.persistence.web.entity.UsuarioPagina;
import co.matisses.persistence.web.entity.UsuarioPaginaRedencion;
import co.matisses.persistence.web.facade.ComisionDecoradorPorcentajeFacade;
import co.matisses.persistence.web.facade.CompraListaRegalosFacade;
import co.matisses.persistence.web.facade.ListaRegalosFacade;
import co.matisses.persistence.web.facade.ProductoListaRegalosFacade;
import co.matisses.persistence.web.facade.ProveedoresExteriorFacade;
import co.matisses.persistence.web.facade.UsuarioPaginaFacade;
import co.matisses.persistence.web.facade.UsuarioPaginaRedencionFacade;
import co.matisses.persistence.web.facade.WpPuntosFacade;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import org.apache.commons.codec.digest.DigestUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

/**
 *
 * @author mperdomo
 */
@Stateless
@Path("sessionusuario")
public class SessionUsuarioPaginaREST {

    private static final Logger CONSOLE = Logger.getLogger(SessionUsuarioPaginaREST.class.getSimpleName());
    @Inject
    private BCSApplicationMBean applicationMBean;
    @Inject
    private BusinessPartnerREST businessPartnerREST;
    @Inject
    private ServiceCallsREST serviceCallsREST;
    @Inject
    private SendHtmlEmailREST emailREST;
    @Inject
    private JWTTokenManager tokenManager;
    @Inject
    private ImagenProductoMBean imagenProductoMBean;
    @Inject
    private BCSGenericMBean genericMBean;
    @EJB
    private UsuarioPaginaFacade usuarioFacade;
    @EJB
    private ListaRegalosFacade listaRegalosFacade;
    @EJB
    private ProductoListaRegalosFacade productoListaRegalosFacade;
    @EJB
    private FacturaSAPFacade facturaSAPFacade;
    @EJB
    private BaruDecoradoresFacade baruDecoradoresFacade;
    @EJB
    private WpPuntosFacade wpPuntosFacade;
    @EJB
    private DevolucionSAPFacade devolucionSAPFacade;
    @EJB
    private BaruMaterialesFacade baruMaterialesFacade;
    @EJB
    private BaruAveriasFacade baruAveriasFacade;
    @EJB
    private ComisionDecoradorPorcentajeFacade comisionDecoradorPorcentajeFacade;
    @EJB
    private ProveedoresExteriorFacade proveedoresExteriorFacade;

    @EJB
    private CompraListaRegalosFacade compraListaRegalosFacade;
    @EJB
    private DetalleFacturaSAPFacade detalleFacturaSAPFacade;
    @EJB
    private UsuarioPaginaRedencionFacade usuarioRedencionFacade;

    @POST
    @Path("login")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response loginListaRegalos(final UsuarioPaginaDTO datos) {
        if (datos == null) {
            return Response.ok(new ConsultaUsuarioResponseDTO(-1, "No se recibieron datos para ejecutar la consulta")).build();
        }

        String passEncripted = DigestUtils.sha1Hex(datos.getPassword());
        UsuarioPagina usuario = usuarioFacade.consultarUsuario(datos.getNombreUsuario(), passEncripted);

        if (usuario == null) {
            return Response.ok(new ConsultaUsuarioResponseDTO(-1, "No se encontraron usuarios con los datos ingresados")).build();
        }

        UsuarioPaginaDTO usuarioDTO = new UsuarioPaginaDTO();
        usuarioDTO.setNombreUsuario(usuario.getNombreUsuario());
        usuarioDTO.setPassword(usuario.getPassword());
        usuarioDTO.setEsLista(usuario.getEsLista());
        usuarioDTO.setEsNuevo(usuario.getEsNuevo());
        usuarioDTO.setEstado(usuario.getEstado());
        usuarioDTO.setFechaRegistro(usuario.getFechaRegistro());
        usuarioDTO.setNombre(usuario.getNombre());
        usuarioDTO.setUsuarioId(usuario.getUsuarioId());
        usuarioDTO.setToken(tokenManager.tokenizeDataWeb(usuarioDTO.getNombreUsuario(), true));
        usuarioDTO.setDocumento(usuario.getDocumento());
        usuarioDTO.setEsAdmin(usuario.getEsAdmin());
        usuarioDTO.setEsDecorador(usuario.getEsDecorador());
        usuarioDTO.setEsPlanificador(usuario.getEsPlanificador());

        if (usuario.getIdListaRegalos() != null) {
            ListaRegalos listaUsuario = listaRegalosFacade.consultarListaPorId(usuario.getIdListaRegalos().getIdLista());

            if (listaUsuario != null) {
                ListaRegalosDTO listaUsuarioDTO = listaRegalosToDTO(listaUsuario);
                usuarioDTO.setIdListaRegalos(listaUsuarioDTO);
                return Response.ok(usuarioDTO).build();
            } else {
                CONSOLE.log(Level.INFO, "lee el json  [{0}]", datos.getUsuarioId());
                return Response.ok(new ConsultaUsuarioResponseDTO(-1, "No se pudo logear el usuario")).build();
            }
        } else {
            return Response.ok(usuarioDTO).build();
        }
    }

    private ListaRegalosDTO listaRegalosToDTO(ListaRegalos lista) {
        if (lista == null) {
            return null;
        }

        ListaRegalosDTO dto = new ListaRegalosDTO();

        dto.setAceptaBonos(lista.getAceptaBonos());
        dto.setCategoria(new CategoriaListaRegalosDTO(lista.getCategoria().getIdCategoria(), lista.getCategoria().getNombre(), lista.getCategoria().getValorMinimo()));
        dto.setCedulaCocreador(lista.getCedulaCocreador());
        dto.setCedulaCreador(lista.getCedulaCreador());
        dto.setCodigo(lista.getCodigo());
        dto.setEstado(new EstadoListaRegalosDTO(lista.getEstado().getIdEstado(), lista.getEstado().getNombre()));
        dto.setFechaCreacion(lista.getFechaCreacion());
        dto.setFechaEvento(lista.getFechaEvento());
        dto.setIdLista(lista.getIdLista());
        dto.setInvitados(lista.getInvitados());
        dto.setListaPrivada(lista.getListaPrivada());
        dto.setMensajeAgradecimiento(lista.getMensajeAgradecimiento());
        dto.setMensajeBienvenida(lista.getMensajeBienvenida());
        dto.setNombre(lista.getNombre());
        dto.setApellidoCocreador(lista.getApellidoCocreador());
        dto.setApellidoCreador(lista.getApellidoCreador());
        dto.setNombreCocreador(lista.getNombreCocreador());
        dto.setNombreCreador(lista.getNombreCreador());
        dto.setNotificacionCambioCategoriaCreador(lista.getNotificacionCambioCategoriaCreador());
        dto.setNotificacionDiariaCreador(lista.getNotificacionDiariaCreador());
        dto.setNotificacionInmediataCreador(lista.getNotificacionInmediataCreador());
        dto.setNotificacionSemanalCreador(lista.getNotificacionSemanalCreador());
        dto.setNotificacionCambioCategoriaCocreador(lista.getNotificacionCambioCategoriaCocreador());
        dto.setNotificacionDiariaCocreador(lista.getNotificacionDiariaCocreador());
        dto.setNotificacionInmediataCocreador(lista.getNotificacionInmediataCocreador());
        dto.setNotificacionSemanalCocreador(lista.getNotificacionSemanalCocreador());
        dto.setRutaImagenPerfil(lista.getRutaImagenPerfil());
        dto.setRutaImagenPortada(lista.getRutaImagenPortada());
        dto.setPermitirEntregaPersonal(lista.getPermitirEntregaPersonal());
        dto.setTipoEvento(new TipoEventoDTO(lista.getTipoEvento().getIdTipoEvento(), lista.getTipoEvento().getNombre()));
        dto.setValorMinimoBono(lista.getValorMinimoBono());
        dto.setFechaEntrega(lista.getFechaEntrega());
        dto.setFormatoFechaEvento(new SimpleDateFormat("dd | MM | yyyy").format(lista.getFechaEvento()));
        dto.setFormatoFechaEntrega(new SimpleDateFormat("dd | MM | yyyy").format(lista.getFechaEntrega()));

        ArrayList<ProductoListaRegalos> productosLista = (ArrayList<ProductoListaRegalos>) productoListaRegalosFacade.consultarPorLista(lista.getIdLista());
        if (productosLista != null) {
            for (ProductoListaRegalos prodEntity : productosLista) {
                dto.agregarProducto(productoEntity2Dto(prodEntity));
            }
        }

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
        prodDto.setReferencia(prodEntity.getReferencia());

        return prodDto;
    }

    @POST
    @Path("updatepass")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response updatePass(final UsuarioPaginaDTO datos) {
        CONSOLE.log(Level.INFO, "lee el json  [{0}]", datos.getUsuarioId());
        if (datos == null) {
            return Response.ok(new ConsultaUsuarioResponseDTO(-1, "No se pudo actualizar el password")).build();
        }

        try {
            String passEncripted = DigestUtils.sha1Hex(datos.getPassword());
            usuarioFacade.actualizarPassUsuarioPagina(passEncripted, datos.getUsuarioId());

            if (datos.getIdListaRegalos() != null && datos.getIdListaRegalos().getIdLista() != null && datos.getIdListaRegalos().getIdLista() != 0) {
                ListaRegalos lista = listaRegalosFacade.consultarListaPorId(datos.getIdListaRegalos().getIdLista());

                if (lista != null && lista.getIdLista() != null && lista.getIdLista() != 0) {
                    StringBuilder sb = new StringBuilder();

                    sb.append("<tr><td style=\"text-align: left;\"><b>Tu nueva contrase&ntilde;a</b>: <span style=\"color: #E07D7D;\">");
                    sb.append(datos.getPassword());
                    sb.append("</span></td></tr>");

                    Map<String, String> params = armarCorreo("El cambio de contrase&ntilde;a se realizó correctamente", sb.toString(), "", lista.getNombreCreador(), lista.getCodigo(), false);
                    enviarCorreo("lista_regalos", "Lista Regalos Matisses <listaideal@matisses.co>", "Lista Regalos Matisses", lista.getCorreoCreador(),
                            applicationMBean.getDestinatariosPlantillaEmail().get("lista_regalos").getTo().get(0), null, params);
                }
            }

            return Response.ok(new ConsultaUsuarioResponseDTO(0, "Contraseña actualizada correctamente")).build();
        } catch (Exception e) {
        }
        return Response.ok(new ConsultaUsuarioResponseDTO(-1, "No se actualizo la contraseña")).build();
    }

    private Map<String, String> armarCorreo(String comentario, String mensaje, String tabla, String cliente, String nombreLista, boolean llamadaServicio) {
        Map<String, String> params = new HashMap<>();

        params.put("comentario", comentario);
        params.put("mensaje", mensaje);
        params.put("tabla", tabla);
        params.put("cliente", cliente);
        if (nombreLista == null || nombreLista.isEmpty()) {
            if (llamadaServicio) {
                params.put("link", applicationMBean.obtenerValorPropiedad("url.servidor") + "mi-cuenta");
            } else {
                params.put("link", applicationMBean.obtenerValorPropiedad("url.servidor") + "login");
            }
        } else {
            params.put("link", applicationMBean.obtenerValorPropiedad("listaregalos.url.web") + nombreLista);
        }

        return params;
    }

    private Map<String, String> armarCorreoConfirm(String comentario, String mensaje, String cliente, String nombreUsuario) {
        Map<String, String> params = new HashMap<>();

        params.put("comentario", comentario);
        params.put("mensaje", mensaje);
        params.put("tabla", "");
        params.put("cliente", cliente);

        params.put("link", applicationMBean.obtenerValorPropiedad("micuenta.url.web.usuario") + nombreUsuario);

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

    private String obtenerPrimerNombre(String nombre) {
        if (nombre != null && !nombre.isEmpty()) {
            String[] s = nombre.split(" ");

            if (s.length > 2) {
                return s[2];
            } else {
                return s[1];
            }
        }

        return "";
    }

    @GET
    @Path("consultarusuariopagina/{nombreUsuario}/{documento}")
    @Produces({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response consultarUsuarioPagina(@PathParam("nombreUsuario") String nombreUsuario, @PathParam("documento") String documento) {
        if (usuarioFacade.consultarNombreUsuario((nombreUsuario != null && !nombreUsuario.isEmpty() ? nombreUsuario.toUpperCase() : ""), documento)) {
            return Response.ok(new ResponseDTO(0, "El mail ya se encuentra registrado.")).build();
        } else {
            return Response.ok(new ResponseDTO(-1, "Mail no registrado.")).build();
        }
    }

    @GET
    @Path("recuperar/{nombreUsuario}")
    @Produces({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response recuperarCuenta(@PathParam("nombreUsuario") String nombreUsuario) {
        if (nombreUsuario != null || !nombreUsuario.trim().isEmpty()) {
            List<UsuarioPagina> lista = usuarioFacade.recuperarCuenta(nombreUsuario);
            if (lista != null || !lista.isEmpty()) {
                for (UsuarioPagina usuario : lista) {
                    String passEncripted = DigestUtils.sha1Hex(usuario.getDocumento().trim());
                    try {
                        usuarioFacade.actualizarPassUsuarioPagina(passEncripted, usuario.getUsuarioId());

                        StringBuilder sb = new StringBuilder();

                        sb.append("<tr><td style=\"text-align: left;\"><b>Tu nueva contrase&ntilde;a</b>: <span style=\"color: #6D9197;\">");
                        sb.append(usuario.getDocumento().trim());
                        sb.append("</span></td></tr>");

                        Map<String, String> params = armarCorreo("La recuperación de contrase&ntilde;a se realizó correctamente.", sb.toString(), "", usuario.getNombre(), "", false);
                        enviarCorreo("usuario_web", "Matisses <info@matisses.co>", "Recuperar Cuenta", nombreUsuario,
                                applicationMBean.getDestinatariosPlantillaEmail().get("usuario_web").getTo().get(0), null, params);

                        return Response.ok(new ResponseDTO(0, "Se envio un mail de recuperación de contraseña.")).build();
                    } catch (Exception e) {
                        CONSOLE.log(Level.SEVERE, "Ocurrio un error actualizando la contrasena de usuario {0}", e);
                        return Response.ok(new ResponseDTO(-1, "Ocurrio un error actualizando la contraseña de usuario.")).build();
                    }
                }
            }
        }
        return Response.ok(new ResponseDTO(-1, "No se encontraron datos para actualizar contraseña.")).build();
    }

    @POST
    @Path("crear/")
    @Consumes({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response crearUsuarioPagina(UsuarioPaginaDTO usuarioDto) {
        if (usuarioDto == null) {
            return Response.ok(new ResponseDTO(-1, "No se recibieron datos para crear el usuario.")).build();
        }

        if (usuarioFacade.consultarNombreUsuario(usuarioDto.getNombreUsuario(), usuarioDto.getDocumento())) {
            return Response.ok(new ResponseDTO(-1, "El mail ya se encuentra registrado.")).build();
        }

        if (!usuarioDto.getAceptaTerminos()) {
            return Response.ok(new ResponseDTO(-1, "Debe aceptar los términos y condiciones.")).build();
        }

        String passEncripted = DigestUtils.sha1Hex(usuarioDto.getPassword().trim());
        UsuarioPagina entidadUsuario = new UsuarioPagina();

        entidadUsuario.setNombreUsuario(usuarioDto.getNombreUsuario().trim().toUpperCase());
        entidadUsuario.setNombre(usuarioDto.getNombre().trim().toUpperCase());
        entidadUsuario.setPassword(passEncripted);
        entidadUsuario.setEsNuevo(usuarioDto.getEsNuevo());
        entidadUsuario.setFechaRegistro(new Date());
        entidadUsuario.setEstado(false);
        entidadUsuario.setEsLista(usuarioDto.getEsLista());
        entidadUsuario.setAceptaTerminos(usuarioDto.getAceptaTerminos());
        entidadUsuario.setSuscripcionNotificaciones(usuarioDto.getSuscripcionNotificaciones());
        entidadUsuario.setDocumento(usuarioDto.getDocumento());
        entidadUsuario.setEsDecorador(usuarioDto.getEsDecorador());
        entidadUsuario.setEsPlanificador(usuarioDto.getEsPlanificador());
        entidadUsuario.setPendienteAprobacionDecorador(usuarioDto.getPendienteAprobacionDecorador());
        entidadUsuario.setPendienteAprobacionPlanificador(usuarioDto.getPendienteAprobacionPlanificador());

        try {
            usuarioFacade.create(entidadUsuario);
            CONSOLE.log(Level.INFO, "Se creo el usuario de pagina exitosamente");

            StringBuilder sb = new StringBuilder();

            sb.append("<tr><td style=\"text-align: left;\"><b>Usuario</b>: <span style=\"color: #6D9197;\">");
            sb.append(usuarioDto.getNombreUsuario());
            sb.append("</span></td></tr>");
            sb.append("<tr><td style=\"text-align: left;\"><b>Contrase&ntilde;a</b>: <span style=\"color: #6D9197;\">");
            sb.append(usuarioDto.getPassword().trim());
            sb.append("</span></td></tr>");

            Map<String, String> params = armarCorreoConfirm("La creación de cuenta se realizó correctamente. Debe activarla accionando el botón Activar Cuenta", sb.toString(), entidadUsuario.getNombre(), entidadUsuario.getNombreUsuario());

            enviarCorreo("usuario_web", "Matisses <info@matisses.co>", "Mi Cuenta", entidadUsuario.getNombreUsuario(),
                    applicationMBean.getDestinatariosPlantillaEmail().get("usuario_web").getTo().get(0), null, params);

            return Response.ok(new ResponseDTO(0, "Se creo el usuario de página exitosamente.")).build();

        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al crear el usuario ", e);
            return Response.ok(new ResponseDTO(-1, "Ocurrio un error al crear el usuario.")).build();
        }
    }

    @GET
    @Path("cargarcliente/{nombreUsuario}")
    @Produces({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response obtenerCliente(@PathParam("nombreUsuario") String nombreUsuario) {
        if (nombreUsuario == null || nombreUsuario.trim().isEmpty()) {
            return Response.ok(new ResponseDTO(-1, "No se recibieron datos para obtener cliente.")).build();
        }

        String documento = usuarioFacade.obtenerDocumento(nombreUsuario);
        if (documento == null || documento.isEmpty()) {
            return Response.ok(new ResponseDTO(-1, "No se encontro documento relacionado.")).build();
        }

        //Consultar datos del cliente en sap
        try {
            Response res = businessPartnerREST.findClient(documento + "CL");
            BusinessPartner socio = (BusinessPartner) res.getEntity();
            return Response.ok(socio).build();
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al consultar o procesar los datos del cliente " + documento, e);
            return Response.ok(new ResponseDTO(-1, "Ocurrio un error al consultar o procesar los datos del cliente.")).build();
        }
    }

    @POST
    @Path("editar/")
    @Consumes({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response editarSocioNegocio(BusinessPartner businessPartner) {
        if (businessPartner == null) {
            return Response.ok(new ResponseDTO(-1, "No se encontraron datos para editar.")).build();
        }

        try {
            businessPartnerREST.editClient(businessPartner);
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al modificar el cliente ", e);
            return Response.ok(new ResponseDTO(-1, "Ocurrio un error al actualizar el cliente.")).build();
        }
        return Response.ok(new ResponseDTO(0, "Cliente actualizado correctamente.")).build();
    }

    @POST
    @Path("reclamargarantia")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response crearLlamadaServicio(ServiceCall serviceCall) throws B1WSServiceUnavailableException {
        if (serviceCall == null) {
            return Response.ok(new ResponseDTO(-1, "No se recibieron datos.")).build();
        }
        try {
            serviceCall.setOrigin(-1L);
            serviceCall.setStatus(-3L);
            serviceCall.setCallType(1L);
            serviceCall.setProblemType(5L);
            serviceCall.setBelongsToAQueue("tNO");
            serviceCall.setLocation(-1L);
            serviceCall.setUTipoServ("03");
            serviceCall.setUNWRRMA("No");
            serviceCall.setUNWRRMAType(0L);
            serviceCall.setServiceBPType("srvcSales");

            Response res = serviceCallsREST.create(serviceCall);
            ResponseDTO dto = (ResponseDTO) res.getEntity();

            try {
                StringBuilder sb = new StringBuilder();

                sb.append("<table style=\"width: 100%;\"><tr><td style=\"text-align: justify;\">Acabas de realizar una reclamación de garantía. ");
                sb.append("Muy pronto nuestra línea de servicio al cliente se pondrá en contacto.</td></tr></table>");

                StringBuilder sb1 = new StringBuilder();

                sb1.append("<table style=\"width: 100%; border: 1px solid #DDDDDD; margin-top: 10px; border-collapse: collapse;\">");
                sb1.append("<tr>");
                sb1.append("<th style=\"border: 1px solid #DDDDDD;\">PRODUCTO</th>");
                sb1.append("<th style=\"border: 1px solid #DDDDDD; margin: 10px;\">DESCRIPCIÓN</th>");
                sb1.append("<th style=\"border: 1px solid #DDDDDD; margin: 10px;\">PEDIDO</th>");
                sb1.append("<th style=\"border: 1px solid #DDDDDD; margin: 10px;\">SERVICIO</th>");
                sb1.append("</tr>");
                sb1.append("<tr>");
                sb1.append("<td style=\"border: 1px solid #DDDDDD; text-align: center; margin: 10px;\"><img src=\"");
                sb1.append(imagenProductoMBean.obtenerUrlProducto(serviceCall.getItemCode(), true));
                sb1.append("\"></img></td>");
                sb1.append("<td style=\"border: 1px solid #DDDDDD; margin: 10px;\">");
                sb1.append(genericMBean.obtenerNombreReferencia(serviceCall.getItemCode()));
                sb1.append("<br/><b>Ref:</b> ");
                sb1.append(genericMBean.convertirARefCorta(serviceCall.getItemCode()).replace("*", ""));
                sb1.append("</td>");
                sb1.append("<td style=\"border: 1px solid #DDDDDD; margin: 10px; text-align: right;\">");
                sb1.append(serviceCall.getUNumFactura());
                sb1.append("</td>");
                sb1.append("<td style=\"border: 1px solid #DDDDDD; margin: 10px; text-align: right;\"><a href=\"http://360.matisses.co:8080/360/servicio/llamada/\">");
                sb1.append(dto.getContent());
                sb1.append("</a></td>");
                sb1.append("</tr>");
                sb1.append("</table>");

                Map<String, String> params = armarCorreo(sb.toString(), "", sb1.toString(), obtenerPrimerNombre(serviceCall.getCustomerName()), "", true);

                enviarCorreo("llamada_servicio_web", "Matisses <info@matisses.co>", "Mi Llamada de Servicio", serviceCall.getbPeMail(),
                        applicationMBean.getDestinatariosPlantillaEmail().get("llamada_servicio_web").getTo().get(0), null, params);

                return Response.ok(new ResponseDTO(0, res.getEntity())).build();
            } catch (Exception e) {
                CONSOLE.log(Level.SEVERE, "Ocurrio un error enviando la notificacion de la llamada de servicio. ", e);
                return Response.ok(new ResponseDTO(-1, "Ocurrio un error enviando la notificación de la llamada de servicio.")).build();
            }
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al crear la llamada de servicio. ", e);
            return Response.ok(new ResponseDTO(-1, "Ocurrio un error al crear la llamada de servicio.")).build();
        }
    }

    @GET
    @Path("consultargarantia/{codigoLlamada}")
    @Produces({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response consultarGarantia(@PathParam("codigoLlamada") Integer codigoLlamada) throws B1WSServiceUnavailableException {
        if (codigoLlamada == null) {
            return Response.ok(new ResponseDTO(-1, "No se recibieron datos.")).build();
        }
        try {
            Response res = serviceCallsREST.find(codigoLlamada);
            return Response.ok(res.getEntity()).build();
        } catch (Exception e) {
            return Response.ok(new ResponseDTO(-1, "Ocurrio un error al crear la llamada de servicio.")).build();
        }
    }

    @GET
    @Path("mispedidos/{documento}")
    @Produces({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response cargarMisPedidos(@PathParam("documento") String documento) {
        if (documento == null || documento.trim().isEmpty()) {
            return Response.ok(new ResponseDTO(-1, "No se recibieron datos para cargar pedidos.")).build();
        }
        List<Object[]> pedidos = facturaSAPFacade.obtenerPedidos(documento + "CL");

        if (pedidos == null || pedidos.size() <= 0) {
            return Response.ok(new ResponseDTO(-1, "No se encontraron pedidos relacionados.")).build();
        }
        ArrayList<PedidoDTO> lista = new ArrayList<>();
        pedidos.stream().forEach((_item) -> {
            lista.add(new PedidoDTO(_item));
        });

        return Response.ok(lista).build();
    }

    @GET
    @Path("detallepedido/{pedido}")
    @Produces({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response cargarDetallePedido(@PathParam("pedido") String pedido) {
        if (pedido == null) {
            return Response.ok(new ResponseDTO(-1, "No se recibieron datos para cargar detalle.")).build();
        }
        List<Object[]> resultado = facturaSAPFacade.obtenerDetallePedido(pedido, null);

        if (resultado == null || resultado.size() <= 0) {
            return Response.ok(new ResponseDTO(-1, "No se encontro detalle del pedido.")).build();
        }
        ArrayList<DetallePedidoDTO> detallePedido = new ArrayList<>();
        resultado.stream().forEach((_detalle) -> {
            detallePedido.add(new DetallePedidoDTO(_detalle));
        });

        return Response.ok(detallePedido).build();
    }

    @GET
    @Path("misnovios/{documento}")
    @Produces({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response cargarMisNovios(@PathParam("documento") String documento) {
        if (documento == null || documento.isEmpty()) {
            return Response.ok(new ResponseDTO(-1, "No se recibieron datos para cargar mis novios.")).build();
        }

        String codigoDecorador = baruDecoradoresFacade.consultarCodigoDecorador(documento, "P");

        if (codigoDecorador == null || codigoDecorador.isEmpty()) {
            return Response.ok(new ResponseDTO(-1, "No esta registrado como planificador o esta inactivo.")).build();
        }

        List<Object[]> novios = listaRegalosFacade.consultarListaPorDecorador(codigoDecorador, Integer.parseInt(applicationMBean.obtenerValorPropiedad("unit.value")));

        if (novios == null) {
            return Response.ok(new ResponseDTO(-1, "No se encontraron lista de novios asociados al decorador con código " + codigoDecorador)).build();
        }

        ArrayList<ListaNoviosDecoradorDTO> listaNovios = new ArrayList<>();
        novios.stream().forEach((_novios) -> {
            listaNovios.add(new ListaNoviosDecoradorDTO(_novios));
        });

        return Response.ok(listaNovios).build();
    }

    @POST
    @Path("updateclave")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response updateClave(final UsuarioPaginaDTO datos) {
        if (datos == null) {
            return Response.ok(new ConsultaUsuarioResponseDTO(-1, "No se pudo actualizar el password")).build();
        }
        List<UsuarioPagina> lista = usuarioFacade.recuperarCuenta(datos.getNombreUsuario());
        if (lista != null || !lista.isEmpty()) {
            for (UsuarioPagina usuario : lista) {
                String passEncripted = DigestUtils.sha1Hex(datos.getPassword().trim());
                try {
                    usuarioFacade.actualizarPassUsuarioPagina(passEncripted, usuario.getUsuarioId());

                    StringBuilder sb = new StringBuilder();

                    sb.append("<tr><td style=\"text-align: left;\"><b>Tu nueva contrase&ntilde;a</b>: <span style=\"color: #6D9197;\">");
                    sb.append(datos.getPassword());
                    sb.append("</span></td></tr>");

                    Map<String, String> params = armarCorreo("La recuperación de contrase&ntilde;a se realizó correctamente.", sb.toString(), "", usuario.getNombre(), "", false);
                    enviarCorreo("usuario_web", "Matisses <info@matisses.co>", "Recuperar Cuenta", datos.getNombreUsuario(),
                            applicationMBean.getDestinatariosPlantillaEmail().get("usuario_web").getTo().get(0), null, params);

                    return Response.ok(new ResponseDTO(0, "Se envio un mail de recuperación de contraseña.")).build();
                } catch (Exception e) {
                    CONSOLE.log(Level.SEVERE, "Ocurrio un error actualizando la contrasena de usuario {0}", e);
                    return Response.ok(new ResponseDTO(-1, "Ocurrio un error actualizando la contraseña de usuario.")).build();
                }
            }
        }

        return Response.ok(new ConsultaUsuarioResponseDTO(-1, "No se actualizo la contraseña.")).build();
    }

    @GET
    @Path("cargarmontoacumulado/{documento}")
    @Produces({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response cargarMontoAcumulado(@PathParam("documento") String documento) {
        List<Integer> docNums = new ArrayList<>();
        List<VentaDTO> facturas = new ArrayList<>();
        BigDecimal acumCompraSinIva = new BigDecimal(0);

        if (documento == null || documento.isEmpty()) {
            return Response.ok(new ResponseDTO(-1, "No se recibieron datos para cargar monto acumulado.")).build();
        }
        String planificador = baruDecoradoresFacade.consultarCodigoDecorador(documento, "P");

        if (planificador == null || planificador.isEmpty()) {
            return Response.ok(new ResponseDTO(-1, "No esta registrado como planificador o esta inactivo.")).build();
        }
        List<Integer> fac = compraListaRegalosFacade.obtenerFacturasPlanificador(planificador);

        if (fac != null || !fac.isEmpty()) {
            for (Integer factura : fac) {
                FacturaSAP facturaSAP = facturaSAPFacade.findByDocNum(factura);

                List<DetalleFacturaSAP> detalleFactura = detalleFacturaSAPFacade.obtenerDetalleFactura(facturaSAP.getDocEntry().doubleValue());

                if (detalleFactura != null || !detalleFactura.isEmpty()) {
                    List<DevolucionSAP> devolucion = devolucionSAPFacade.obtenerDevolucionesFactura(factura);

                    if (devolucion != null && !devolucion.isEmpty()) {
                        for (DevolucionSAP d : devolucion) {
                            for (DetalleDevolucionSAP dd : d.getDetalle()) {
                                for (int i = 0; i < detalleFactura.size(); i++) {
                                    DetalleFacturaSAP df = detalleFactura.get(i);

                                    if (df.getItemCode().equals(dd.getItemCode()) && df.getQuantity().intValue() > 0) {
                                        if (df.getQuantity().intValue() <= dd.getQuantity().intValue()) {
                                            df.setQuantity(new BigDecimal(0));
                                            detalleFactura.remove(i);
                                            i--;
                                        } else {
                                            facturaSAP.setDocTotal(facturaSAP.getDocTotal().subtract(new BigDecimal(df.getPriceAfVAT().intValue() * dd.getQuantity().intValue())));
                                            df.setQuantity(df.getQuantity().subtract(dd.getQuantity()));
                                        }
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    if (detalleFactura != null && !detalleFactura.isEmpty()) {
                        List<Object[]> pagos = facturaSAPFacade.obtenerPagosFacturaV2(facturaSAP.getDocNum());

                        if (pagos != null && !pagos.isEmpty()) {
                            docNums.add(facturaSAP.getDocNum());
                            facturas.add(0, new VentaDTO(facturaSAP.getDocNum(), new SimpleDateFormat("yyyy-MM-dd").format(facturaSAP.getDocDate()), facturaSAP.getVatSum()));
                            facturas.get(0).setValor(new BigDecimal(0));
                            facturas.get(0).setPuntos(facturaSAP.getDocTotal().subtract(facturaSAP.getVatSum()).divide(new BigDecimal(applicationMBean.obtenerValorPropiedad("unit.value"))).intValue());

                            for (Object[] o : pagos) {
                                facturas.get(0).getPagos().add(0, new VentaDTO.PagosRecibidos((Integer) o[0], (Integer) o[3], (BigDecimal) o[1], (BigDecimal) o[4], (BigDecimal) o[5],
                                        (BigDecimal) o[6], (BigDecimal) o[7], (BigDecimal) o[8], (BigDecimal) o[9], (BigDecimal) o[10], (BigDecimal) o[12], (BigDecimal) o[13],
                                        (BigDecimal) o[14], (BigDecimal) o[15], (BigDecimal) o[17], (Date) o[2], (Date) o[11], (Date) o[16], (BigDecimal) o[18]));
                            }

                            for (DetalleFacturaSAP df : detalleFactura) {
                                facturas.get(0).setValor(facturas.get(0).getValor().add(new BigDecimal(df.getPriceAfVAT().intValue() * df.getQuantity().intValue())));
                                facturas.get(0).getDetalle().add(new VentaDTO.DetalleVenta(df.getQuantity().intValue(), df.getItemCode(), df.getPriceAfVAT()));
                            }
                            /**
                             * Acumular puntos solo a facturas que esten pagas
                             * en su totalidad
                             */
                            if (facturas.get(0).getPagos().get(0).getTotalRecibo().toBigInteger().intValueExact() <= facturas.get(0).getPagos().get(0).getTotalRecibido().toBigInteger().intValueExact()) {
                                acumCompraSinIva = acumCompraSinIva.add(facturaSAP.getDocTotal().subtract(facturaSAP.getVatSum()));
                            }
                        }
                    }
                }
            }
        }
        //Cargando clasificación de puntos
        List<Object[]> puntosWp = wpPuntosFacade.cargarClasificacionPuntos();
        List<PuntosWpDTO.Puntos> puntosDefinidos = new ArrayList<>();

        if (puntosWp != null || puntosWp.size() > 0) {
            for (Object[] object : puntosWp) {
                puntosDefinidos.add(new PuntosWpDTO.Puntos((String) object[0], (String) object[1]));
            }
        }

        BigDecimal puntoAcumulado = acumCompraSinIva.divide(new BigDecimal(applicationMBean.obtenerValorPropiedad("unit.value")));
        BigDecimal porcRedimeEfectivo = wpPuntosFacade.obtenerPorcentajePuntos(puntoAcumulado.toBigInteger().intValueExact(), 1);
        BigDecimal porcRedimeBono = wpPuntosFacade.obtenerPorcentajePuntos(puntoAcumulado.toBigInteger().intValueExact(), 2);

        return Response.ok(new PuntosWpDTO(puntoAcumulado.toBigInteger().intValueExact(), puntoAcumulado.multiply(porcRedimeEfectivo), puntoAcumulado.multiply(porcRedimeBono),
                puntosDefinidos, facturas)).build();
    }

    @GET
    @Path("cargar-clientes-decorador/{decorador}")
    @Produces({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response cargarClientesDecorador(@PathParam("decorador") String decorador) {
        if (decorador != null && !decorador.isEmpty()) {
            List<ClienteSAPDTO> clientes = new ArrayList<ClienteSAPDTO>();
            List<String> clientesCC = new ArrayList<>();
            String codigoDecorador = baruDecoradoresFacade.consultarCodigoDecorador(decorador, "D");
            List<FacturaSAP> fac = facturaSAPFacade.obtenerFacturasDecorador(codigoDecorador, null);

            for (FacturaSAP f : fac) {
                List<DevolucionSAP> dev = devolucionSAPFacade.obtenerDevolucionesFactura(f.getDocNum());

                if (dev != null && !dev.isEmpty()) {
                    for (DevolucionSAP d : dev) {
                        for (DetalleDevolucionSAP dd : d.getDetalle()) {
                            for (int i = 0; i < f.getDetalle().size(); i++) {
                                DetalleFacturaSAP df = f.getDetalle().get(i);

                                if (df.getItemCode().equals(dd.getItemCode()) && df.getQuantity().intValue() > 0) {
                                    if (df.getQuantity().intValue() <= dd.getQuantity().intValue()) {
                                        df.setQuantity(new BigDecimal(0));
                                        f.getDetalle().remove(i);
                                        i--;
                                    } else {
                                        df.setQuantity(df.getQuantity().subtract(dd.getQuantity()));
                                    }
                                    break;
                                }
                            }
                        }
                    }
                }
                ClienteSAPDTO cliente = new ClienteSAPDTO();
                cliente.setNombres(f.getCardName());
                cliente.setNit(f.getCardCode());
                if (f.getDetalle() != null && !f.getDetalle().isEmpty() && !clientesCC.contains(f.getCardCode())) {
                    clientesCC.add(f.getCardCode());
                    clientes.add(cliente);

                }
            }

            //Collections.sort(clientes);
            return Response.ok(clientes).build();
        }

        return Response.ok(new ResponseDTO(-1, "Ocurrio un error al obtener los clientes por decorador.")).build();
    }

    @GET
    @Path("cargar-facturas-clientes-decorador/{decorador}/{cliente}")
    @Produces({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response cargarFacturasClientesDecorador(@PathParam("decorador") String decorador, @PathParam("cliente") String cliente) {
        List<Integer> docNums = new ArrayList<>();
        List<VentaDTO> facturas = new ArrayList<>();
        String codigoDecorador = baruDecoradoresFacade.consultarCodigoDecorador(decorador, "D");
        List<FacturaSAP> fac = facturaSAPFacade.obtenerFacturasDecorador(codigoDecorador, cliente);

        if (decorador != null && !decorador.isEmpty() && cliente != null && !cliente.isEmpty()) {
            for (FacturaSAP f : fac) {
                List<DevolucionSAP> dev = devolucionSAPFacade.obtenerDevolucionesFactura(f.getDocNum());

                if (dev != null && !dev.isEmpty()) {
                    for (DevolucionSAP d : dev) {
                        for (DetalleDevolucionSAP dd : d.getDetalle()) {
                            for (int i = 0; i < f.getDetalle().size(); i++) {
                                DetalleFacturaSAP df = f.getDetalle().get(i);

                                if (df.getItemCode().equals(dd.getItemCode()) && df.getQuantity().intValue() > 0) {
                                    if (df.getQuantity().intValue() <= dd.getQuantity().intValue()) {
                                        df.setQuantity(new BigDecimal(0));
                                        f.getDetalle().remove(i);
                                        i--;
                                    } else {
                                        f.setDocTotal(f.getDocTotal().subtract(new BigDecimal(df.getPriceAfVAT().intValue() * dd.getQuantity().intValue())));
                                        df.setQuantity(df.getQuantity().subtract(dd.getQuantity()));
                                    }
                                    break;
                                }
                            }
                        }
                    }
                }

                if (f.getDetalle() != null && !f.getDetalle().isEmpty()) {
                    docNums.add(f.getDocNum());
                    facturas.add(0, new VentaDTO(f.getDocNum(), new SimpleDateFormat("yyyy-MM-dd").format(f.getDocDate()), f.getDocTotal()));
                    facturas.get(0).setValor(new BigDecimal(0));

                    for (DetalleFacturaSAP df : f.getDetalle()) {
                        ProveedoresExterior prov = proveedoresExteriorFacade.find(df.getItemCode().substring(0, 3));

                        if (prov != null && prov.getProveedor() != null && !prov.getProveedor().isEmpty()) {
                            if (!prov.getComplemento()) {
                                facturas.get(0).setValor(facturas.get(0).getValor().add(new BigDecimal(df.getPriceAfVAT().intValue() * df.getQuantity().intValue())));
                                facturas.get(0).getDetalle().add(new VentaDTO.DetalleVenta(df.getQuantity().intValue(), df.getItemCode(), df.getPriceAfVAT()));
                            }
                        } else {
                            facturas.get(0).setValor(facturas.get(0).getValor().add(new BigDecimal(df.getPriceAfVAT().intValue() * df.getQuantity().intValue())));
                            facturas.get(0).getDetalle().add(new VentaDTO.DetalleVenta(df.getQuantity().intValue(), df.getItemCode(), df.getPriceAfVAT()));
                        }
                    }

                    List<Object[]> pagos = facturaSAPFacade.obtenerPagosFacturaV2(f.getDocNum());

                    if (pagos != null && !pagos.isEmpty()) {
                        for (Object[] o : pagos) {
                            facturas.get(0).getPagos().add(0, new VentaDTO.PagosRecibidos((Integer) o[0], (Integer) o[3], (BigDecimal) o[1], (BigDecimal) o[4], (BigDecimal) o[5],
                                    (BigDecimal) o[6], (BigDecimal) o[7], (BigDecimal) o[8], (BigDecimal) o[9], (BigDecimal) o[10], (BigDecimal) o[12], (BigDecimal) o[13],
                                    (BigDecimal) o[14], (BigDecimal) o[15], (BigDecimal) o[17], (Date) o[2], (Date) o[11], (Date) o[16], (BigDecimal) o[18]));
                        }
                    } else {
                        facturas.remove(0);
                    }
                }
            }
            Integer mesesVencidos = facturaSAPFacade.obtenerMesesSinComisionarDecorador(codigoDecorador, docNums);

            for (VentaDTO v : facturas) {
                v.setMesesVencidos(mesesVencidos);

                /**
                 * Se aplicara la siguiente formula para calcular la comision de
                 * una forma mas proporcional
                 */
                BigDecimal comision = new BigDecimal(0);

                for (VentaDTO.PagosRecibidos p : v.getPagos()) {
                    if (p.getEfectivo().doubleValue() > 0) {
                        comision = comision.add(new BigDecimal(((((p.getTotalRecibido().doubleValue() / p.getTotalRecibo().doubleValue()) * p.getEfectivo().doubleValue())
                                * ((v.getValor().doubleValue() / v.getTotalIVA().doubleValue()) / 100) * comisionDecoradorPorcentajeFacade.obtenerPorcentajeComision(mesesVencidos > 6 ? 7 : 0, "efectivo"))) / 1.19));
                    } else if (p.getCheques().doubleValue() > 0) {
                        comision = comision.add(new BigDecimal(((((p.getTotalRecibido().doubleValue() / p.getTotalRecibo().doubleValue()) * p.getCheques().doubleValue())
                                * ((v.getValor().doubleValue() / v.getTotalIVA().doubleValue()) / 100) * comisionDecoradorPorcentajeFacade.obtenerPorcentajeComision(mesesVencidos > 6 ? 7 : 0, "cheques"))) / 1.19));
                    } else if (p.getTarjetasCredito().doubleValue() > 0) {
                        comision = comision.add(new BigDecimal(((((p.getTotalRecibido().doubleValue() / p.getTotalRecibo().doubleValue()) * p.getTarjetasCredito().doubleValue())
                                * ((v.getValor().doubleValue() / v.getTotalIVA().doubleValue()) / 100) * comisionDecoradorPorcentajeFacade.obtenerPorcentajeComision(mesesVencidos > 6 ? 7 : 0, "tarjetas"))) / 1.19));
                    }
                }

                v.setComision(comision);
            }
        }

        return Response.ok(facturas).build();
    }

    @GET
    @Path("cargarmateriales/{item}")
    @Produces({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response cargarMateriales(@PathParam("item") String item) {
        if (item == null || item.trim().isEmpty()) {
            return Response.ok(new ResponseDTO(-1, "No se obtuvo item para cargar materiales.")).build();
        }
        List<BaruMateriales> materiales = baruMaterialesFacade.obtenerMaterialesArticulo(item);

        if (materiales == null || materiales.isEmpty()) {
            return Response.ok(new ResponseDTO(-1, "No se encontaron materiales asociados.")).build();
        }

        return Response.ok(materiales).build();
    }

    @GET
    @Path("cargaraverias/{material}")
    @Produces({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response cargarAveriasPorMaterial(@PathParam("material") String codigoMaterial) {
        if (codigoMaterial == null || codigoMaterial.trim().isEmpty()) {
            return Response.ok(new ResponseDTO(-1, "No se obtuvo material para cargar las averias.")).build();
        }
        List<BaruAverias> averias = baruAveriasFacade.obtenerAveriaPorMaterial(codigoMaterial);

        if (averias == null) {
            return Response.ok(new ResponseDTO(-1, "No se encontaron averias asociadas.")).build();
        }

        return Response.ok(averias).build();
    }

    @POST
    @Path("subirimagen/")
    @Produces({MediaType.TEXT_PLAIN})
    //@Consumes(MediaType.MULTIPART_FORM_DATA)
    @Consumes("multipart/form-data")
    public Response subirImagen(MultipartFormDataInput input) {
        Map<String, List<InputPart>> formParts = input.getFormDataMap();

        try {
            String codigo = formParts.get("codigo").get(0).getBody(String.class, null);
            String nombrearchivo = formParts.get("nombrearchivo").get(0).getBody(String.class, null);

            String decorador = formParts.get("decorador").get(0).getBody(String.class, null);
            String planificador = formParts.get("planificador").get(0).getBody(String.class, null);
            CONSOLE.log(Level.INFO, "Codigo={0}", codigo);
            InputStream istream = formParts.get("file").get(0).getBody(InputStream.class, null);
            String fileName = parseFileName(formParts.get("file").get(0).getHeaders(), nombrearchivo, "01");
            String fullFilePath = "";
            if (decorador != null && !decorador.isEmpty()) {
                File directorio = new File(applicationMBean.obtenerValorPropiedad("url.archivo.adjuntos.decorador") + File.separator + codigo + File.separator + decorador + File.separator);
                directorio.mkdirs();
                fullFilePath = applicationMBean.obtenerValorPropiedad("url.archivo.adjuntos.decorador") + File.separator + codigo + File.separator + decorador + File.separator + fileName;
            }
            if (planificador != null && !planificador.isEmpty()) {
                File directorio1 = new File(applicationMBean.obtenerValorPropiedad("url.archivo.adjuntos.decorador") + File.separator + codigo + File.separator + decorador + File.separator);
                directorio1.mkdirs();
                fullFilePath = applicationMBean.obtenerValorPropiedad("url.archivo.adjuntos.decorador") + File.separator + codigo + File.separator + planificador + File.separator + fileName;
            }

            CONSOLE.log(Level.INFO, "Guardando imagen de {0}", fileName);
            saveFile(istream, fullFilePath);
            try {

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

    // Parse Content-Disposition header to get the original file name
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

    // save uploaded file to a defined location on the server
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

    @GET
    @Path("activarusuario/{nombreUsuario}")
    @Produces({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response activarUsuario(@PathParam("nombreUsuario") String nombreUsuario) {
        CONSOLE.log(Level.INFO, "confirmando activacion de usuario {0}", nombreUsuario);
        //Consultar codigo lista

        if (nombreUsuario != null && !nombreUsuario.isEmpty()) {
            Boolean confirmarAsistencia = usuarioFacade.confirmarUsuario(nombreUsuario);
            return Response.ok(confirmarAsistencia).build();
        }

        return Response.ok(false).build();
    }

    @POST
    @Path("solicitaredencion/")
    @Consumes({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response solicitarRedencion(UsuarioPaginaRedencionDTO usuarioRedencionDto) {
        if (usuarioRedencionDto == null) {
            return Response.ok(new ResponseDTO(-1, "No se recibieron datos para crear el usuario.")).build();
        }

        UsuarioPagina usuarioPagina = usuarioFacade.consultarUsuarioId(usuarioRedencionDto.getIdUsuarioPaginaDTO().getUsuarioId());
        UsuarioPaginaRedencion entidadUsuario = new UsuarioPaginaRedencion();

        entidadUsuario.setIdUsuarioPagina(usuarioPagina);
        entidadUsuario.setFecha(new Date());
        entidadUsuario.setTipo(usuarioRedencionDto.getTipo());
        entidadUsuario.setEstado("PE");
        entidadUsuario.setMonto(usuarioRedencionDto.getMonto());
        entidadUsuario.setModo(usuarioRedencionDto.getModo());

        try {
            usuarioRedencionFacade.create(entidadUsuario);
            CONSOLE.log(Level.INFO, "Se creo la solicitud de redencion {0}", entidadUsuario.getIdRedencion());

//            StringBuilder sb = new StringBuilder();
//
//            sb.append("<tr><td style=\"text-align: left;\"><b>Usuario</b>: <span style=\"color: #6D9197;\">");
//            sb.append(usuarioDto.getNombreUsuario());
//            sb.append("</span></td></tr>");
//            sb.append("<tr><td style=\"text-align: left;\"><b>Contrase&ntilde;a</b>: <span style=\"color: #6D9197;\">");
//            sb.append(usuarioDto.getPassword().trim());
//            sb.append("</span></td></tr>");
//
//            Map<String, String> params = armarCorreoConfirm("La creación de cuenta se realizó correctamente. Debe activarla accionando el botón Activar Cuenta", sb.toString(), entidadUsuario.getNombre(), entidadUsuario.getNombreUsuario());
//
//            enviarCorreo("usuario_web", "Matisses <info@matisses.co>", "Mi Cuenta", entidadUsuario.getNombreUsuario(),
//                    applicationMBean.getDestinatariosPlantillaEmail().get("usuario_web").getTo().get(0), null, params);
            return Response.ok(new ResponseDTO(0, entidadUsuario.getIdRedencion())).build();

        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al crear el usuario ", e);
            return Response.ok(new ResponseDTO(-1, "Ocurrio un error al crear el usuario.")).build();
        }
    }

    @POST
    @Path("subirredencion/")
    @Produces({MediaType.TEXT_PLAIN})
    //@Consumes(MediaType.MULTIPART_FORM_DATA)
    @Consumes("multipart/form-data")
    public Response subirArchivosRedencion(MultipartFormDataInput input) {
        Map<String, List<InputPart>> formParts = input.getFormDataMap();

        try {
            String codigo = formParts.get("idSolicitud").get(0).getBody(String.class, null);
            String nombrearchivo = formParts.get("nombrearchivo").get(0).getBody(String.class, null);

            CONSOLE.log(Level.INFO, "Codigo={0}", codigo);
            InputStream istream = formParts.get("file").get(0).getBody(InputStream.class, null);
            String fileName = parseFileName(formParts.get("file").get(0).getHeaders(), nombrearchivo, "01");
            String fullFilePath = "";
            if (codigo != null && !codigo.isEmpty()) {
                File directorio = new File(applicationMBean.obtenerValorPropiedad("url.archivo.adjuntos.redencion") + File.separator + codigo + File.separator);
                directorio.mkdirs();
                fullFilePath = applicationMBean.obtenerValorPropiedad("url.archivo.adjuntos.redencion") + File.separator + codigo + File.separator + fileName;
            }

            CONSOLE.log(Level.INFO, "Guardando imagen de {0}", fileName);
            saveFile(istream, fullFilePath);
            try {

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
}