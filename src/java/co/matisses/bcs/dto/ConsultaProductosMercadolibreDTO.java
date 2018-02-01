package co.matisses.bcs.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dbotero
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConsultaProductosMercadolibreDTO {
    private List<ProductoMercadoLibreDTO> results;

    public ConsultaProductosMercadolibreDTO() {
        results = new ArrayList<>();
    }

    public List<ProductoMercadoLibreDTO> getResults() {
        return results;
    }

    public void setResults(List<ProductoMercadoLibreDTO> results) {
        this.results = results;
    }
}
