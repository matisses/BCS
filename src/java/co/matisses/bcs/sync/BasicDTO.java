package co.matisses.bcs.sync;

/**
 *
 * @author dbotero
 */
public class BasicDTO extends MongoDataDTO<BasicDTO> {

    private String code;
    private String name;

    public BasicDTO() {
        super(BasicDTO.class);
    }

    public BasicDTO(String code, String name) {
        super(BasicDTO.class);
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "BasicDTO{" + "code=" + code + ", name=" + name + '}';
    }

}
