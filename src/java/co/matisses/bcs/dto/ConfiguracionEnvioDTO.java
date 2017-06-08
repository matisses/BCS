package co.matisses.bcs.dto;

import java.util.List;

/**
 *
 * @author dbotero
 */
public class ConfiguracionEnvioDTO {
    private List<String> items;
    private String shipping;

    public ConfiguracionEnvioDTO() {
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    public String getShipping() {
        return shipping;
    }

    public void setShipping(String shipping) {
        this.shipping = shipping;
    }
}
