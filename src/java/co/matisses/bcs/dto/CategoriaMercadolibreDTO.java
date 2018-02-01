package co.matisses.bcs.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dbotero
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoriaMercadolibreDTO {

    private String id;
    private String name;
    private ConfiguracionCategoriaMercadolibreDTO settings;
    private List<SubcategoriaMercadolibreDTO> childrenCategories;
    private List<Path> pathFromRoot;

    public CategoriaMercadolibreDTO() {
        childrenCategories = new ArrayList<>();
        pathFromRoot = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("path_from_root")
    public List<Path> getPathFromRoot() {
        return pathFromRoot;
    }

    public void setPathFromRoot(List<Path> pathFromRoot) {
        this.pathFromRoot = pathFromRoot;
    }

    @JsonProperty("children_categories")
    public List<SubcategoriaMercadolibreDTO> getChildrenCategories() {
        return childrenCategories;
    }

    public void setChildrenCategories(List<SubcategoriaMercadolibreDTO> childrenCategories) {
        this.childrenCategories = childrenCategories;
    }

    public ConfiguracionCategoriaMercadolibreDTO getSettings() {
        return settings;
    }

    public void setSettings(ConfiguracionCategoriaMercadolibreDTO settings) {
        this.settings = settings;
    }

    @Override
    public String toString() {
        return "CategoriaMercadolibreDTO{" + "id=" + id + ", name=" + name + ", settings=" + settings + ", childrenCategories=" + childrenCategories + ", pathFromRoot=" + pathFromRoot + '}';
    }
    
    public String pathFromRoot(){
        StringBuilder sb = new StringBuilder();
        for(Path p : pathFromRoot){
            sb.append(p.getName());
            sb.append(">");
        }
        sb.append(this.name);
        return sb.toString();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SubcategoriaMercadolibreDTO {

        private String id;
        private String name;

        public SubcategoriaMercadolibreDTO() {
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "SubcategoriaMercadolibreDTO{" + "id=" + id + ", name=" + name + '}';
        }

    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ConfiguracionCategoriaMercadolibreDTO {

        private List<String> shippingModes;

        public ConfiguracionCategoriaMercadolibreDTO() {
            this.shippingModes = new ArrayList<>();
        }

        @JsonProperty("shipping_modes")
        public List<String> getShippingModes() {
            return shippingModes;
        }

        public void setShippingModes(List<String> shippingModes) {
            this.shippingModes = shippingModes;
        }

        @Override
        public String toString() {
            return "ConfiguracionCategoriaMercadolibreDTO{" + "shippingModes=" + shippingModes + '}';
        }
    }

    public static class Path {

        private String id;
        private String name;

        public Path() {
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Path{" + "id=" + id + ", name=" + name + '}';
        }
    }
}
