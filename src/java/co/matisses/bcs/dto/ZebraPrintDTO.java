package co.matisses.bcs.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringTokenizer;

/**
 *
 * @author dbotero
 */
public class ZebraPrintDTO {

    private String printerName;
    private int columns;
    private int labelType;
    private List<ItemLabelDTO> items;
    private List<CodigoRevisadoLabelDTO> codigos;

    public ZebraPrintDTO() {
        items = new ArrayList<>();
        codigos = new ArrayList<>();
    }

    public List<CodigoRevisadoLabelDTO> getCodigos() {
        return codigos;
    }

    public void setCodigos(List<CodigoRevisadoLabelDTO> codigos) {
        this.codigos = codigos;
    }

    public String getPrinterName() {
        return printerName;
    }

    public void setPrinterName(String printerName) {
        this.printerName = printerName;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public int getLabelType() {
        return labelType;
    }

    public void setLabelType(int labelType) {
        this.labelType = labelType;
    }

    public List<ItemLabelDTO> getItems() {
        return items;
    }

    public void setItems(List<ItemLabelDTO> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "ZebraPrintDTO{" + "printerName=" + printerName + ", columns=" + columns + ", labelType=" + labelType + ", items=" + items + ", codigos=" + codigos + '}';
    }

    public static void main(String[] args) {
        String ref = "PRM830-130x4+B33+B35/TOUCHME600/TOUCHME406/PTCH301";
        StringTokenizer stroTok = new StringTokenizer(ref, ". ,-/");
        while (stroTok.hasMoreElements()) {
            String nextElement = (String) stroTok.nextElement();
            System.out.println(nextElement + "\t" + (nextElement.length() + 1));
        }
        //for (String part : ref.split("-\\., ")) {

        //}
    }
}
