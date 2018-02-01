package co.matisses.bcs.dto;

/**
 *
 * @author ygil
 */
public class MetodosEnvioDTO {

    private Integer code;
    private Double price;
    private String name;
    private String description;

    public MetodosEnvioDTO() {
    }

    public MetodosEnvioDTO(Integer code, Double price, String name, String description) {
        this.code = code;
        this.price = price;
        this.name = name;
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
