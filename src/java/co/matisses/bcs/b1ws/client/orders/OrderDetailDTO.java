package co.matisses.bcs.b1ws.client.orders;

/**
 *
 * @author dbotero
 */
public class OrderDetailDTO {

    private Long lineNum;
    private Long uLineNumFv;
    private String itemCode;
    private Double quantity;
    private Double price;
    private Double discount;
    private String warehouseCode;
    private String estado;

    public OrderDetailDTO() {
    }

    public Long getLineNum() {
        return lineNum;
    }

    public void setLineNum(Long lineNum) {
        this.lineNum = lineNum;
    }

    public Long getuLineNumFv() {
        return uLineNumFv;
    }

    public void setuLineNumFv(Long uLineNumFv) {
        this.uLineNumFv = uLineNumFv;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "OrderDetailDTO{" + "lineNum=" + lineNum + ", itemCode=" + itemCode + ", quantity=" + quantity + ", warehouseCode=" + warehouseCode + ", estado=" + estado + '}';
    }
}
