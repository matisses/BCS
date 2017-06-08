package co.matisses.bcs.dto;

import co.matisses.bcs.util.ObjectUtils;
import java.util.Objects;

/**
 *
 * @author ygil
 */
public class CaptacionClienteDTO {

    private int codigoError = 1;
    private Integer idCaptacion;
    private String codigo;
    private String mensajeError;
    private String nombres;
    private String apellidos;
    private String correo;
    private String celular;
    private String restaurante;
    private boolean conoceMatisses;
    private boolean permitirUsoDatos = false;

    public CaptacionClienteDTO() {
    }

    public CaptacionClienteDTO(int codigoError, String mensajeError) {
        this.codigoError = codigoError;
        this.mensajeError = mensajeError;
    }

    public CaptacionClienteDTO(Integer idCaptacion, String codigo, String mensajeError, String nombres, String apellidos, String correo, String celular, String restaurante, boolean conoceMatisses) {
        this.idCaptacion = idCaptacion;
        this.codigo = codigo;
        this.mensajeError = mensajeError;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.correo = correo;
        this.celular = celular;
        this.restaurante = restaurante;
        this.conoceMatisses = conoceMatisses;
    }

    public int getCodigoError() {
        return codigoError;
    }

    public void setCodigoError(int codigoError) {
        this.codigoError = codigoError;
    }

    public Integer getIdCaptacion() {
        return idCaptacion;
    }

    public void setIdCaptacion(Integer idCaptacion) {
        this.idCaptacion = idCaptacion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
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

    public String getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(String restaurante) {
        this.restaurante = restaurante;
    }

    public boolean isConoceMatisses() {
        return conoceMatisses;
    }

    public void setConoceMatisses(boolean conoceMatisses) {
        this.conoceMatisses = conoceMatisses;
    }

    public boolean isPermitirUsoDatos() {
        return permitirUsoDatos;
    }

    public void setPermitirUsoDatos(boolean permitirUsoDatos) {
        this.permitirUsoDatos = permitirUsoDatos;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + Objects.hashCode(this.idCaptacion);
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
        final CaptacionClienteDTO other = (CaptacionClienteDTO) obj;
        if (!Objects.equals(this.idCaptacion, other.idCaptacion)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return ObjectUtils.toString(this);
    }
}
