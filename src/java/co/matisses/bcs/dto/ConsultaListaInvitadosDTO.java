package co.matisses.bcs.dto;

import co.matisses.bcs.util.ObjectUtils;

/**
 *
 * @author jguisao
 */
public class ConsultaListaInvitadosDTO {

    private Long idLista;
    private String nombre;
    private String apellidos;
    private String correo;
    private String celular;
    private String asistencia;
    private PaginacionConsultaListasUsuarioDTO paginacion;

    public ConsultaListaInvitadosDTO() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getAsistencia() {
        return asistencia;
    }

    public void setAsistencia(String asistencia) {
        this.asistencia = asistencia;
    }

    public PaginacionConsultaListasUsuarioDTO getPaginacion() {
        return paginacion;
    }

    public void setPaginacion(PaginacionConsultaListasUsuarioDTO paginacion) {
        this.paginacion = paginacion;
    }

    public Long getIdLista() {
        return idLista;
    }

    public void setIdLista(Long idLista) {
        this.idLista = idLista;
    }

    @Override
    public String toString() {
        return ObjectUtils.toString(this);
    }
}