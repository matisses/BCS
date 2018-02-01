package co.matisses.bcs.dto;

import co.matisses.bcs.util.ObjectUtils;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author dbotero
 */
public class SMSServiceResponseDTO {

    private String codigo;
    private String mensaje;
    private String referencia;
    private String status;

    public SMSServiceResponseDTO() {
    }

    public SMSServiceResponseDTO(String codigo, String mensaje, String referencia, String status) {
        this.codigo = codigo;
        this.mensaje = mensaje;
        this.referencia = referencia;
        this.status = status;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    @JsonProperty("estatus")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return ObjectUtils.toString(this);
    }
}
