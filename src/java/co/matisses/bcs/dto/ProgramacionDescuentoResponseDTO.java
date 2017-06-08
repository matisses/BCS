package co.matisses.bcs.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dbotero
 */
public class ProgramacionDescuentoResponseDTO {

    private Integer codigo;
    private String mensaje;
    private Integer precio;
    private List<ProgramacionDescuentoDTO> descuentos;

    public ProgramacionDescuentoResponseDTO() {
        descuentos = new ArrayList<>();
    }

    public ProgramacionDescuentoResponseDTO(Integer codigo, String mensaje) {
        this.codigo = codigo;
        this.mensaje = mensaje;
        descuentos = new ArrayList<>();
    }

    public ProgramacionDescuentoResponseDTO(Integer precio) {
        this.codigo = 0;
        this.precio = precio;
        descuentos = new ArrayList<>();
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public List<ProgramacionDescuentoDTO> getDescuentos() {
        return descuentos;
    }

    public void setDescuentos(List<ProgramacionDescuentoDTO> descuentos) {
        this.descuentos = descuentos;
    }

    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

}
