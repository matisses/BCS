package co.matisses.bcs.dto;

/**
 *
 * @author dbotero
 */
public class ProgramacionDescuentoDTO {

    private String referencia;
    private String canal;
    private Double porcentaje;

    public ProgramacionDescuentoDTO() {
    }

    public ProgramacionDescuentoDTO(String referencia, Double porcentaje, String canal) {
        this.referencia = referencia;
        this.porcentaje = porcentaje;
        setCanal(canal);
    }

    public Double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(Double porcentaje) {
        this.porcentaje = porcentaje;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getCanal() {
        return canal;
    }

    public final void setCanal(String canal) {
        if (canal == null) {
            return;
        }

        switch (canal) {
            case "PO":
                this.canal = "Tienda Física (Complementos)";
                break;
            case "SA":
                this.canal = "Tienda Física (SAP)";
                break;
            case "WE":
                this.canal = "Página WEB";
                break;
            default:
                this.canal = canal;
                break;
        }
    }
}
