package co.matisses.bcs.dto;

import co.matisses.bcs.util.ObjectUtils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author jguisao
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ListaRegalosDTO {

    private Long idLista;
    private Integer invitados;
    private Integer valorMinimoBono;
    private Integer idDecorador;
    private Integer contadorDias;
    private String codigo;
    private String nombre;
    private String cedulaCreador;
    private String nombreCreador;
    private String apellidoCreador;
    private String correoCreador;
    private String telefonoCreador;
    private String direccionCreador;
    private String ciudadCreador;
    private String cedulaCocreador;
    private String nombreCocreador;
    private String apellidoCocreador;
    private String telefonoCocreador;
    private String direccionCocreador;
    private String ciudadCocreador;
    private String correoCocreador;
    private String rutaImagenPerfil;
    private String rutaImagenPortada;
    private String mensajeBienvenida;
    private String mensajeAgradecimiento;
    private String notificacionInmediataCreador;
    private String notificacionDiariaCreador;
    private String notificacionSemanalCreador;
    private String notificacionCambioCategoriaCreador;
    private String notificacionInmediataCocreador;
    private String notificacionDiariaCocreador;
    private String notificacionSemanalCocreador;
    private String notificacionCambioCategoriaCocreador;
    private String emailCreador;
    private String emailCocreador;
    private String celebracion;
    private String lugar;
    private String tiendaContacto;
    private String formatoFechaEvento;
    private boolean listaPrivada;
    private boolean aceptaBonos;
    private boolean permitirEntregaPersonal;
    private boolean activa;
    private boolean notificacionInmediataMailCreador;
    private boolean notificacionInmediataSmsCreador;
    private boolean notificacionDiariaMailCreador;
    private boolean notificacionDiariaSmsCreador;
    private boolean notificacionSemanalMailCreador;
    private boolean notificacionSemanalSmsCreador;
    private boolean notificacionInmediataMailCocreador;
    private boolean notificacionInmediataSmsCocreador;
    private boolean notificacionDiariaMailCocreador;
    private boolean notificacionDiariaSmsCocreador;
    private boolean notificacionSemanalMailCocreador;
    private boolean notificacionSemanalSmsCocreador;
    private boolean usarDatosCreador;
    private boolean usarDatosCocreador;
    private boolean aceptaTerminos;
    private Date fechaCreacion;
    private Date fechaEvento;
    private EstadoListaRegalosDTO estado;
    private TipoEventoDTO tipoEvento;
    private CategoriaListaRegalosDTO categoria;
    private ArrayList<ProductoListaRegalosDTO> productos;
    private String formatoFechaEntrega;
    private Date fechaEntrega;

    public ListaRegalosDTO() {
        productos = new ArrayList<>();
    }

    public ListaRegalosDTO(Long idLista, String codigo, String nombre, String nombreCreador, String apellidoCreador, String nombreCocreador, String apellidoCocreador) {
        this.idLista = idLista;
        this.codigo = codigo;
        this.nombre = nombre;
        this.nombreCreador = nombreCreador;
        this.apellidoCreador = apellidoCreador;
        this.nombreCocreador = nombreCocreador;
        this.apellidoCocreador = apellidoCocreador;
    }

    public Long getIdLista() {
        return idLista;
    }

    public void setIdLista(Long idLista) {
        this.idLista = idLista;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoCocreador() {
        return apellidoCocreador;
    }

    public void setApellidoCocreador(String apellidoCocreador) {
        this.apellidoCocreador = apellidoCocreador;
    }

    public String getApellidoCreador() {
        return apellidoCreador;
    }

    public void setApellidoCreador(String apellidoCreador) {
        this.apellidoCreador = apellidoCreador;
    }

    public String getNombreCocreador() {
        return nombreCocreador;
    }

    public void setNombreCocreador(String nombreCocreador) {
        this.nombreCocreador = nombreCocreador;
    }

    public String getNombreCreador() {
        return nombreCreador;
    }

    public void setNombreCreador(String nombreCreador) {
        this.nombreCreador = nombreCreador;
    }

    public String getCedulaCreador() {
        return cedulaCreador;
    }

    public void setCedulaCreador(String cedulaCreador) {
        this.cedulaCreador = cedulaCreador;
    }

    public String getCedulaCocreador() {
        return cedulaCocreador;
    }

    public void setCedulaCocreador(String cedulaCocreador) {
        this.cedulaCocreador = cedulaCocreador;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(Date fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public String getFechaEventoUTC() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ");
        return sdf.format(fechaEvento);
    }

    public EstadoListaRegalosDTO getEstado() {
        return estado;
    }

    public void setEstado(EstadoListaRegalosDTO estado) {
        this.estado = estado;
    }

    public TipoEventoDTO getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(TipoEventoDTO tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public String getRutaImagenPerfil() {
        return rutaImagenPerfil;
    }

    public void setRutaImagenPerfil(String rutaImagenPerfil) {
        this.rutaImagenPerfil = rutaImagenPerfil;
    }

    public String getRutaImagenPortada() {
        return rutaImagenPortada;
    }

    public void setRutaImagenPortada(String rutaImagenPortada) {
        this.rutaImagenPortada = rutaImagenPortada;
    }

    public String getMensajeBienvenida() {
        return mensajeBienvenida;
    }

    public void setMensajeBienvenida(String mensajeBienvenida) {
        this.mensajeBienvenida = mensajeBienvenida;
    }

    public String getMensajeAgradecimiento() {
        return mensajeAgradecimiento;
    }

    public void setMensajeAgradecimiento(String mensajeAgradecimiento) {
        this.mensajeAgradecimiento = mensajeAgradecimiento;
    }

    public Integer getInvitados() {
        return invitados;
    }

    public void setInvitados(Integer invitados) {
        this.invitados = invitados;
    }

    public Integer getValorMinimoBono() {
        return valorMinimoBono;
    }

    public void setValorMinimoBono(Integer valorMinimoBono) {
        this.valorMinimoBono = valorMinimoBono;
    }

    public String getNotificacionInmediataCreador() {
        return notificacionInmediataCreador;
    }

    public void setNotificacionInmediataCreador(String notificacionInmediataCreador) {
        this.notificacionInmediataCreador = notificacionInmediataCreador;
    }

    public String getNotificacionDiariaCreador() {
        return notificacionDiariaCreador;
    }

    public void setNotificacionDiariaCreador(String notificacionDiariaCreador) {
        this.notificacionDiariaCreador = notificacionDiariaCreador;
    }

    public String getNotificacionSemanalCreador() {
        return notificacionSemanalCreador;
    }

    public void setNotificacionSemanalCreador(String notificacionSemanalCreador) {
        this.notificacionSemanalCreador = notificacionSemanalCreador;
    }

    public String getNotificacionCambioCategoriaCreador() {
        return notificacionCambioCategoriaCreador;
    }

    public void setNotificacionCambioCategoriaCreador(String notificacionCambioCategoriaCreador) {
        this.notificacionCambioCategoriaCreador = notificacionCambioCategoriaCreador;
    }

    public String getNotificacionInmediataCocreador() {
        return notificacionInmediataCocreador;
    }

    public void setNotificacionInmediataCocreador(String notificacionInmediataCocreador) {
        this.notificacionInmediataCocreador = notificacionInmediataCocreador;
    }

    public String getNotificacionDiariaCocreador() {
        return notificacionDiariaCocreador;
    }

    public void setNotificacionDiariaCocreador(String notificacionDiariaCocreador) {
        this.notificacionDiariaCocreador = notificacionDiariaCocreador;
    }

    public String getNotificacionSemanalCocreador() {
        return notificacionSemanalCocreador;
    }

    public void setNotificacionSemanalCocreador(String notificacionSemanalCocreador) {
        this.notificacionSemanalCocreador = notificacionSemanalCocreador;
    }

    public String getNotificacionCambioCategoriaCocreador() {
        return notificacionCambioCategoriaCocreador;
    }

    public void setNotificacionCambioCategoriaCocreador(String notificacionCambioCategoriaCocreador) {
        this.notificacionCambioCategoriaCocreador = notificacionCambioCategoriaCocreador;
    }

    public CategoriaListaRegalosDTO getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaListaRegalosDTO categoria) {
        this.categoria = categoria;
    }

    public ArrayList<ProductoListaRegalosDTO> getProductos() {
        return productos;
    }

    public void setProductos(ArrayList<ProductoListaRegalosDTO> productos) {
        this.productos = productos;
    }

    public void agregarProducto(ProductoListaRegalosDTO producto) {
        this.productos.add(producto);
    }

    public String getEmailCreador() {
        return emailCreador;
    }

    public void setEmailCreador(String emailCreador) {
        this.emailCreador = emailCreador;
    }

    public String getEmailCocreador() {
        return emailCocreador;
    }

    public void setEmailCocreador(String emailCocreador) {
        this.emailCocreador = emailCocreador;
    }

    public Integer getIdDecorador() {
        return idDecorador;
    }

    public void setIdDecorador(Integer idDecorador) {
        this.idDecorador = idDecorador;
    }

    public String getCorreoCreador() {
        return correoCreador;
    }

    public void setCorreoCreador(String correoCreador) {
        this.correoCreador = correoCreador;
    }

    public String getCorreoCocreador() {
        return correoCocreador;
    }

    public void setCorreoCocreador(String correoCocreador) {
        this.correoCocreador = correoCocreador;
    }

    public String getCelebracion() {
        return celebracion;
    }

    public void setCelebracion(String celebracion) {
        this.celebracion = celebracion;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getTelefonoCreador() {
        return telefonoCreador;
    }

    public void setTelefonoCreador(String telefonoCreador) {
        this.telefonoCreador = telefonoCreador;
    }

    public String getDireccionCreador() {
        return direccionCreador;
    }

    public void setDireccionCreador(String direccionCreador) {
        this.direccionCreador = direccionCreador;
    }

    public String getCiudadCreador() {
        return ciudadCreador;
    }

    public void setCiudadCreador(String ciudadCreador) {
        this.ciudadCreador = ciudadCreador;
    }

    public String getTelefonoCocreador() {
        return telefonoCocreador;
    }

    public void setTelefonoCocreador(String telefonoCocreador) {
        this.telefonoCocreador = telefonoCocreador;
    }

    public String getDireccionCocreador() {
        return direccionCocreador;
    }

    public void setDireccionCocreador(String direccionCocreador) {
        this.direccionCocreador = direccionCocreador;
    }

    public String getCiudadCocreador() {
        return ciudadCocreador;
    }

    public void setCiudadCocreador(String ciudadCocreador) {
        this.ciudadCocreador = ciudadCocreador;
    }

    public String getTiendaContacto() {
        return tiendaContacto;
    }

    public void setTiendaContacto(String tiendaContacto) {
        this.tiendaContacto = tiendaContacto;
    }

    public String getFormatoFechaEvento() {
        return formatoFechaEvento;
    }

    public void setFormatoFechaEvento(String formatoFechaEvento) {
        this.formatoFechaEvento = formatoFechaEvento;
    }

    public String getFormatoFechaEntrega() {
        return formatoFechaEntrega;
    }

    public void setFormatoFechaEntrega(String formatoFechaEntrega) {
        this.formatoFechaEntrega = formatoFechaEntrega;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaCreacion) {
        this.fechaEntrega = fechaCreacion;
    }

    public Integer getContadorDias() {
        return contadorDias;
    }

    public void setContadorDias(Integer contadorDias) {
        this.contadorDias = contadorDias;
    }

    public boolean isListaPrivada() {
        return listaPrivada;
    }

    public void setListaPrivada(boolean listaPrivada) {
        this.listaPrivada = listaPrivada;
    }

    public boolean isAceptaBonos() {
        return aceptaBonos;
    }

    public void setAceptaBonos(boolean aceptaBonos) {
        this.aceptaBonos = aceptaBonos;
    }

    public boolean isPermitirEntregaPersonal() {
        return permitirEntregaPersonal;
    }

    public void setPermitirEntregaPersonal(boolean permitirEntregaPersonal) {
        this.permitirEntregaPersonal = permitirEntregaPersonal;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    public boolean isNotificacionInmediataMailCreador() {
        return notificacionInmediataMailCreador;
    }

    public void setNotificacionInmediataMailCreador(boolean notificacionInmediataMailCreador) {
        this.notificacionInmediataMailCreador = notificacionInmediataMailCreador;
    }

    public boolean isNotificacionInmediataSmsCreador() {
        return notificacionInmediataSmsCreador;
    }

    public void setNotificacionInmediataSmsCreador(boolean notificacionInmediataSmsCreador) {
        this.notificacionInmediataSmsCreador = notificacionInmediataSmsCreador;
    }

    public boolean isNotificacionDiariaMailCreador() {
        return notificacionDiariaMailCreador;
    }

    public void setNotificacionDiariaMailCreador(boolean notificacionDiariaMailCreador) {
        this.notificacionDiariaMailCreador = notificacionDiariaMailCreador;
    }

    public boolean isNotificacionDiariaSmsCreador() {
        return notificacionDiariaSmsCreador;
    }

    public void setNotificacionDiariaSmsCreador(boolean notificacionDiariaSmsCreador) {
        this.notificacionDiariaSmsCreador = notificacionDiariaSmsCreador;
    }

    public boolean isNotificacionSemanalMailCreador() {
        return notificacionSemanalMailCreador;
    }

    public void setNotificacionSemanalMailCreador(boolean notificacionSemanalMailCreador) {
        this.notificacionSemanalMailCreador = notificacionSemanalMailCreador;
    }

    public boolean isNotificacionSemanalSmsCreador() {
        return notificacionSemanalSmsCreador;
    }

    public void setNotificacionSemanalSmsCreador(boolean notificacionSemanalSmsCreador) {
        this.notificacionSemanalSmsCreador = notificacionSemanalSmsCreador;
    }

    public boolean isNotificacionInmediataMailCocreador() {
        return notificacionInmediataMailCocreador;
    }

    public void setNotificacionInmediataMailCocreador(boolean notificacionInmediataMailCocreador) {
        this.notificacionInmediataMailCocreador = notificacionInmediataMailCocreador;
    }

    public boolean isNotificacionInmediataSmsCocreador() {
        return notificacionInmediataSmsCocreador;
    }

    public void setNotificacionInmediataSmsCocreador(boolean notificacionInmediataSmsCocreador) {
        this.notificacionInmediataSmsCocreador = notificacionInmediataSmsCocreador;
    }

    public boolean isNotificacionDiariaMailCocreador() {
        return notificacionDiariaMailCocreador;
    }

    public void setNotificacionDiariaMailCocreador(boolean notificacionDiariaMailCocreador) {
        this.notificacionDiariaMailCocreador = notificacionDiariaMailCocreador;
    }

    public boolean isNotificacionDiariaSmsCocreador() {
        return notificacionDiariaSmsCocreador;
    }

    public void setNotificacionDiariaSmsCocreador(boolean notificacionDiariaSmsCocreador) {
        this.notificacionDiariaSmsCocreador = notificacionDiariaSmsCocreador;
    }

    public boolean isNotificacionSemanalMailCocreador() {
        return notificacionSemanalMailCocreador;
    }

    public void setNotificacionSemanalMailCocreador(boolean notificacionSemanalMailCocreador) {
        this.notificacionSemanalMailCocreador = notificacionSemanalMailCocreador;
    }

    public boolean isNotificacionSemanalSmsCocreador() {
        return notificacionSemanalSmsCocreador;
    }

    public void setNotificacionSemanalSmsCocreador(boolean notificacionSemanalSmsCocreador) {
        this.notificacionSemanalSmsCocreador = notificacionSemanalSmsCocreador;
    }

    public boolean isUsarDatosCreador() {
        return usarDatosCreador;
    }

    public void setUsarDatosCreador(boolean usarDatosCreador) {
        this.usarDatosCreador = usarDatosCreador;
    }

    public boolean isUsarDatosCocreador() {
        return usarDatosCocreador;
    }

    public void setUsarDatosCocreador(boolean usarDatosCocreador) {
        this.usarDatosCocreador = usarDatosCocreador;
    }

    public boolean isAceptaTerminos() {
        return aceptaTerminos;
    }

    public void setAceptaTerminos(boolean aceptaTerminos) {
        this.aceptaTerminos = aceptaTerminos;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + Objects.hashCode(this.idLista);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ListaRegalosDTO other = (ListaRegalosDTO) obj;
        return Objects.equals(this.idLista, other.idLista);
    }

    @Override
    public String toString() {
        return ObjectUtils.toString(this);
    }
}