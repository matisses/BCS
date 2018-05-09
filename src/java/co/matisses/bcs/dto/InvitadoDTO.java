package co.matisses.bcs.dto;

import co.matisses.bcs.util.ObjectUtils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * @author jguisao
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class InvitadoDTO {

    private Long idInvitado;
    private Long idLista;
    private String nombreInvitado;
    private String apellidosInvitado;
    private String correoInvitado;
    private String telefonoInvitado;
    private Boolean asistencia;

    public InvitadoDTO() {
    }

    public InvitadoDTO(Long idInvitado, Long idLista, String nombreInvitado, String apellidosInvitado, String correoInvitado, String telefonoInvitado, Boolean asistencia) {
        this.idInvitado = idInvitado;
        this.idLista = idLista;
        this.nombreInvitado = nombreInvitado;
        this.apellidosInvitado = apellidosInvitado;
        this.correoInvitado = correoInvitado;
        this.telefonoInvitado = telefonoInvitado;
        this.asistencia = asistencia;
    }

    public InvitadoDTO(Long idLista, Object[] parametros) {
        this.idLista = idLista;
        this.nombreInvitado = (String) parametros[2];
        this.apellidosInvitado = (String) parametros[3];
        this.correoInvitado = (String) parametros[4];
        this.telefonoInvitado = (String) parametros[5];
        this.asistencia = (boolean) parametros[6];
    }

    public Long getIdInvitado() {
        return idInvitado;
    }

    public void setIdInvitado(Long idInvitado) {
        this.idInvitado = idInvitado;
    }

    public Long getIdLista() {
        return idLista;
    }

    public void setIdLista(Long idLista) {
        this.idLista = idLista;
    }

    public String getNombreInvitado() {
        return nombreInvitado;
    }

    public void setNombreInvitado(String nombreInvitado) {
        this.nombreInvitado = nombreInvitado;
    }

    public String getApellidosInvitado() {
        return apellidosInvitado;
    }

    public void setApellidosInvitado(String apellidosInvitado) {
        this.apellidosInvitado = apellidosInvitado;
    }

    public String getCorreoInvitado() {
        return correoInvitado;
    }

    public void setCorreoInvitado(String correoInvitado) {
        this.correoInvitado = correoInvitado;
    }

    public String getTelefonoInvitado() {
        return telefonoInvitado;
    }

    public void setTelefonoInvitado(String telefonoInvitado) {
        this.telefonoInvitado = telefonoInvitado;
    }

    public Boolean getAsistencia() {
        return asistencia;
    }

    public void setAsistencia(Boolean asistencia) {
        this.asistencia = asistencia;
    }

    @Override
    public String toString() {
        return ObjectUtils.toString(this);
    }
}
