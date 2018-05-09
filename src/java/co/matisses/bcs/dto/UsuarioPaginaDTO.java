package co.matisses.bcs.dto;

import co.matisses.bcs.util.ObjectUtils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;

/**
 *
 * @author mperdomo
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UsuarioPaginaDTO {

    private Long usuarioId;
    private String nombreUsuario;
    private String password;
    private String documento;
    private String token;
    private String nombre;
    private Boolean esLista;
    private Boolean estado;
    private Boolean esNuevo;
    private Boolean aceptaTerminos;
    private Boolean suscripcionNotificaciones;
    private Date fechaRegistro;
    private ListaRegalosDTO idListaRegalos;
     private Boolean esAdmin;
     private Boolean esDecorador;
     private Boolean esPlanificador;
     private Boolean pendienteAprobacionDecorador;
     private Boolean pendienteAprobacionPlanificador;

    public UsuarioPaginaDTO() {
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEsLista() {
        return esLista;
    }

    public void setEsLista(Boolean esLista) {
        this.esLista = esLista;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Boolean getEsNuevo() {
        return esNuevo;
    }

    public void setEsNuevo(Boolean esNuevo) {
        this.esNuevo = esNuevo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ListaRegalosDTO getIdListaRegalos() {
        return idListaRegalos;
    }

    public void setIdListaRegalos(ListaRegalosDTO listaRegalosDTO) {
        this.idListaRegalos = listaRegalosDTO;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public Boolean getAceptaTerminos() {
        return aceptaTerminos;
    }

    public void setAceptaTerminos(Boolean aceptaTerminos) {
        this.aceptaTerminos = aceptaTerminos;
    }

    public Boolean getSuscripcionNotificaciones() {
        return suscripcionNotificaciones;
    }

    public void setSuscripcionNotificaciones(Boolean suscripcionNotificaciones) {
        this.suscripcionNotificaciones = suscripcionNotificaciones;
    }
    
     public Boolean getEsAdmin() {
        return esLista;
    }

    public void setEsAdmin(Boolean esAdmin) {
        this.esAdmin = esAdmin;
    }
    
     public Boolean getEsPlanificador() {
        return esPlanificador;
    }

    public void setEsPlanificador(Boolean esAdmin) {
        this.esPlanificador = esAdmin;
    }
    
     public Boolean getEsDecorador() {
        return esDecorador;
    }

    public void setEsDecorador(Boolean esAdmin) {
        this.esDecorador = esAdmin;
    }
    
    public Boolean getPendienteAprobacionDecorador() {
        return pendienteAprobacionDecorador;
    }

    public void setPendienteAprobacionDecorador(Boolean pendienteAprobacionDecorador) {
        this.pendienteAprobacionDecorador = estado;
    }
    
    public Boolean getPendienteAprobacionPlanificador() {
        return pendienteAprobacionPlanificador;
    }

    public void setPendienteAprobacionPlanificador(Boolean pendienteAprobacionPlanificador) {
        this.pendienteAprobacionPlanificador = estado;
    }

    @Override
    public String toString() {
        return ObjectUtils.toString(this);
    }
}