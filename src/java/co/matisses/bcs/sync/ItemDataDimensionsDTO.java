package co.matisses.bcs.sync;

import org.bson.Document;

/**
 *
 * @author dbotero
 */
public class ItemDataDimensionsDTO extends MongoDataDTO<ItemDataDimensionsDTO> {

    private Integer depth;
    private Integer width;
    private Integer height;

    public ItemDataDimensionsDTO() {
        super(ItemDataDimensionsDTO.class);
    }

    public ItemDataDimensionsDTO(Integer depth, Integer width, Integer height) {
        super(ItemDataDimensionsDTO.class);
        this.depth = depth;
        this.width = width;
        this.height = height;
    }

    public ItemDataDimensionsDTO(Document doc) {
        super(ItemDataDimensionsDTO.class);
        this.depth = doc.get("depth", Integer.class);
        this.height = doc.get("height", Integer.class);
        this.width = doc.get("width", Integer.class);
    }

    public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    @Override
    public String toString() {
        return "ItemDataDimensions{" + "depth=" + depth + ", width=" + width + ", height=" + height + '}';
    }
}
