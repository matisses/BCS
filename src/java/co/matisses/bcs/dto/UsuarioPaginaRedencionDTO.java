package co.matisses.bcs.dto;

import co.matisses.bcs.util.ObjectUtils;
import co.matisses.persistence.web.entity.UsuarioPagina;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author mperdomo
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UsuarioPaginaRedencionDTO {

   
    private Integer idRedencion;
   
    private Date fecha;
   
    private String tipo;
    
    private BigDecimal monto;
   
    private String modo;
    
    private String estado;
    
    private UsuarioPaginaDTO idUsuarioPagina;
    
        public Integer getIdRedencion() {
        return idRedencion;
    }

    public void setIdRedencion(Integer idRedencion) {
        this.idRedencion = idRedencion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public String getModo() {
        return modo;
    }

    public void setModo(String modo) {
        this.modo = modo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public UsuarioPaginaDTO getIdUsuarioPaginaDTO() {
        return idUsuarioPagina;
    }

    public void setIdUsuarioPaginaDTO(UsuarioPaginaDTO idUsuarioPagina) {
        this.idUsuarioPagina = idUsuarioPagina;
    }

    @Override
    public String toString() {
        return ObjectUtils.toString(this);
    }
}