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
    private Boolean listaPrivada;
    private Boolean aceptaBonos;
    private Boolean permitirEntregaPersonal;
    private Boolean activa;
    private Boolean notificacionInmediataMailCreador;
    private Boolean notificacionInmediataSmsCreador;
    private Boolean notificacionDiariaMailCreador;
    private Boolean notificacionDiariaSmsCreador;
    private Boolean notificacionSemanalMailCreador;
    private Boolean notificacionSemanalSmsCreador;
    private Boolean notificacionInmediataMailCocreador;
    private Boolean notificacionInmediataSmsCocreador;
    private Boolean notificacionDiariaMailCocreador;
    private Boolean notificacionDiariaSmsCocreador;
    private Boolean notificacionSemanalMailCocreador;
    private Boolean notificacionSemanalSmsCocreador;
    private Boolean usarDatosCreador;
    private Boolean usarDatosCocreador;
    private Boolean aceptaTerminos;
    private Date fechaCreacion;
    private Date fechaEvento;
    private EstadoListaRegalosDTO estado;
    private TipoEventoDTO tipoEvento;
    private CategoriaListaRegalosDTO categoria;
    private ArrayList<ProductoListaRegalosDTO> productos;

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

    public Boolean getListaPrivada() {
        return listaPrivada;
    }

    public void setListaPrivada(Boolean listaPrivada) {
        this.listaPrivada = listaPrivada;
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

    public Boolean getAceptaBonos() {
        return aceptaBonos;
    }

    public void setAceptaBonos(Boolean aceptaBonos) {
        this.aceptaBonos = aceptaBonos;
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

    public Boolean getPermitirEntregaPersonal() {
        return permitirEntregaPersonal;
    }

    public void setPermitirEntregaPersonal(Boolean permitirEntregaPersonal) {
        this.permitirEntregaPersonal = permitirEntregaPersonal;
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

    public Boolean getActiva() {
        return activa;
    }

    public void setActiva(Boolean activa) {
        this.activa = activa;
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

    public Boolean getNotificacionInmediataMailCreador() {
        return notificacionInmediataMailCreador;
    }

    public Boolean getNotificacionInmediataSmsCreador() {
        return notificacionInmediataSmsCreador;
    }

    public Boolean getNotificacionDiariaMailCreador() {
        return notificacionDiariaMailCreador;
    }

    public Boolean getNotificacionDiariaSmsCreador() {
        return notificacionDiariaSmsCreador;
    }

    public Boolean getNotificacionSemanalMailCreador() {
        return notificacionSemanalMailCreador;
    }

    public Boolean getNotificacionSemanalSmsCreador() {
        return notificacionSemanalSmsCreador;
    }

    public Boolean getNotificacionInmediataMailCocreador() {
        return notificacionInmediataMailCocreador;
    }

    public Boolean getNotificacionInmediataSmsCocreador() {
        return notificacionInmediataSmsCocreador;
    }

    public Boolean getNotificacionDiariaMailCocreador() {
        return notificacionDiariaMailCocreador;
    }

    public Boolean getNotificacionDiariaSmsCocreador() {
        return notificacionDiariaSmsCocreador;
    }

    public Boolean getNotificacionSemanalMailCocreador() {
        return notificacionSemanalMailCocreador;
    }

    public Boolean getNotificacionSemanalSmsCocreador() {
        return notificacionSemanalSmsCocreador;
    }

    public void setIdDecorador(Integer idDecorador) {
        this.idDecorador = idDecorador;
    }

    public void setNotificacionInmediataMailCreador(Boolean notificacionInmediataMailCreador) {
        this.notificacionInmediataMailCreador = notificacionInmediataMailCreador;
    }

    public void setNotificacionInmediataSmsCreador(Boolean notificacionInmediataSmsCreador) {
        this.notificacionInmediataSmsCreador = notificacionInmediataSmsCreador;
    }

    public void setNotificacionDiariaMailCreador(Boolean notificacionDiariaMailCreador) {
        this.notificacionDiariaMailCreador = notificacionDiariaMailCreador;
    }

    public void setNotificacionDiariaSmsCreador(Boolean notificacionDiariaSmsCreador) {
        this.notificacionDiariaSmsCreador = notificacionDiariaSmsCreador;
    }

    public void setNotificacionSemanalMailCreador(Boolean notificacionSemanalMailCreador) {
        this.notificacionSemanalMailCreador = notificacionSemanalMailCreador;
    }

    public void setNotificacionSemanalSmsCreador(Boolean notificacionSemanalSmsCreador) {
        this.notificacionSemanalSmsCreador = notificacionSemanalSmsCreador;
    }

    public void setNotificacionInmediataMailCocreador(Boolean notificacionInmediataMailCocreador) {
        this.notificacionInmediataMailCocreador = notificacionInmediataMailCocreador;
    }

    public void setNotificacionInmediataSmsCocreador(Boolean notificacionInmediataSmsCocreador) {
        this.notificacionInmediataSmsCocreador = notificacionInmediataSmsCocreador;
    }

    public void setNotificacionDiariaMailCocreador(Boolean notificacionDiariaMailCocreador) {
        this.notificacionDiariaMailCocreador = notificacionDiariaMailCocreador;
    }

    public void setNotificacionDiariaSmsCocreador(Boolean notificacionDiariaSmsCocreador) {
        this.notificacionDiariaSmsCocreador = notificacionDiariaSmsCocreador;
    }

    public void setNotificacionSemanalMailCocreador(Boolean notificacionSemanalMailCocreador) {
        this.notificacionSemanalMailCocreador = notificacionSemanalMailCocreador;
    }

    public void setNotificacionSemanalSmsCocreador(Boolean notificacionSemanalSmsCocreador) {
        this.notificacionSemanalSmsCocreador = notificacionSemanalSmsCocreador;
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

    public Boolean getUsarDatosCreador() {
        return usarDatosCreador;
    }

    public void setUsarDatosCreador(Boolean usarDatosCreador) {
        this.usarDatosCreador = usarDatosCreador;
    }

    public Boolean getUsarDatosCocreador() {
        return usarDatosCocreador;
    }

    public void setUsarDatosCocreador(Boolean usarDatosCocreador) {
        this.usarDatosCocreador = usarDatosCocreador;
    }

    public Boolean getAceptaTerminos() {
        return aceptaTerminos;
    }

    public void setAceptaTerminos(Boolean aceptaTerminos) {
        this.aceptaTerminos = aceptaTerminos;
    }

    public String getFormatoFechaEvento() {
        return formatoFechaEvento;
    }

    public void setFormatoFechaEvento(String formatoFechaEvento) {
        this.formatoFechaEvento = formatoFechaEvento;
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