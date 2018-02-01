package co.matisses.bcs.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

/**
 *
 * @author jguisao
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MercadolibrePublicacionDTO {

    private List<MercadolibrePublicarItemDTO> results;

    public MercadolibrePublicacionDTO() {
    }

    public MercadolibrePublicacionDTO(List<MercadolibrePublicarItemDTO> results) {
        this.results = results;
    }

    public List<MercadolibrePublicarItemDTO> getResults() {
        return results;
    }

    public void setResults(List<MercadolibrePublicarItemDTO> results) {
        this.results = results;
    }
}