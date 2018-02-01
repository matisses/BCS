package co.matisses.bcs.b1ws.client.quotations;

import java.math.BigDecimal;

/**
 *
 * @author ygil
 */
public class QuotationsDocumentLinesDTO {

    private Integer quantity;
    private Integer linea;
    private BigDecimal unitPrice;
    private String itemCode;
    private String warehouseCode;
    private String taxCode;

    public QuotationsDocumentLinesDTO() {
    }

    public QuotationsDocumentLinesDTO(Integer quantity, String itemCode, String warehouseCode) {
        this.quantity = quantity;
        this.itemCode = itemCode;
        this.warehouseCode = warehouseCode;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getLinea() {
        return linea;
    }

    public void setLinea(Integer linea) {
        this.linea = linea;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    @Override
    public String toString() {
        return "QuotationsDocumentLinesDTO{" + "quantity=" + quantity + ", linea=" + linea + ", unitPrice=" + unitPrice + ", itemCode=" + itemCode + ", warehouseCode=" + warehouseCode + ", taxCode=" + taxCode + '}';
    }
}
