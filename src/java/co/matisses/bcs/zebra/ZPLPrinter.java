package co.matisses.bcs.zebra;

import co.matisses.bcs.dto.CodigoRevisadoLabelDTO;
import co.matisses.bcs.dto.ItemLabelDTO;
import co.matisses.bcs.dto.ZebraPrintDTO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dbotero
 */
public class ZPLPrinter {

//    public static void main(String[] args) throws PrintException {
//        ZebraPrintDTO dto = new ZebraPrintDTO();
//
//        dto.setColumns(2);
//        dto.setLabelType(LABEL_40X30);
//        dto.setPrinterName("\\\\alfa\\ZEBRA VEGAS GT800");
//
//        List<ItemLabelDTO> items = new ArrayList<>();
//        ItemLabelDTO item = new ItemLabelDTO();
//
//        item.setItemCode("22200000000000000052");
//        item.setItemName("FRUTERO EN PORCELANA BLANCO/NEGRO H.9xD.40CM LOOPS");
//        item.setPrice("55.000");
//        item.setProvCode("PRM830-130x4+B33+B35/TOUCHME600/TOUCHME406/PTCH301");
//        item.setQuantity(2);
//        item.setType("ACTIVO FIJO");
//
//        items.add(item);
//
//        dto.setItems(items);
//
//        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
//        PrintService printService = null;
//        for (PrintService ps : services) {
//            if (ps.getName().equals(dto.getPrinterName())) {
//                printService = ps;
//                break;
//            }
//        }
//        DocPrintJob job = printService.createPrintJob();
//        Doc doc = new SimpleDoc(ZPLPrinter.getPrintData(dto), DocFlavor.BYTE_ARRAY.AUTOSENSE, null);
//        job.print(doc, null);
//    }
    private final static int LABEL_32X15 = 1;
    private final static int LABEL_40X30 = 2;
    private final static int LABEL_32X25 = 3;
    private final static int LABEL_32X15VEGAS = 4;

    private final static String IMAGE = "~DG000.GRF,03840,040,,::::::gT03780,gT03F80,::gT03F,gT03F80,::gT03780,gT03F80,::gT03F,gT03F80,::gT03780U010W010,"
            + "L0HA80I02AA80M0IAJ06FHFEFE3FE0H0HF80I02FF80K0HFE80K0HF80,K05FHFC001FHFE0L07FHFC0H07FJFC1FC007FHFJ07FFE0J07FHFD0J07FHF0,"
            + "K0JFE003FIF80J02FJF8007FJFE3FE007FHFC001FIF80H03FJF80I0JF8,J01F7H7F007F7F7C0J017F7H7C007F7F7F417E007F7F40017F7F40H03F7F7F40I0H7F7C,"
            + "J0BFJF81FJFE80I0LFE007FJFE3FE01FJF803FIFE0H0LFE8003FJF,J0LFE1FKF80H01FLFC07FJFC1FC03FJF807FJFH01FLFC003FJF,I01FLF7FKF80H03FLFC07FJFE3FE03FJF80FKF803FLFE003FJF80,"
            + "I01F7F577F7F757F7C0H0H7F7H7F7C07577D541F603F417F407F017F007F74057F70037C177,I07FF003FIFE00FHFI0HFE800FHF8003F8003FE0FF803F81FE00FE00FFE0H03FF80FF003F,"
            + "I07FC0H07FHFC001FF001FFC0H05FF0H03F8001FC07F001C01FC001C01FFC0H01FF007F001C,I0HF80H07FHFI01FF003FF80H03FF8003F8003FE0FF0H0E01FC001803FF80I0HF80FE0H0E,"
            + "H01770I017F40I0H7H037F0J0H7C003F0H01760770J01F40J07F60J07F407E,H01FF80H03FFC0I03F807FE0J0HFE003F8003FE0FE0J01FC0J07FE0J03FE0FE0I080,"
            + "H01FE0I01FFC0I03FC07FC0J01FC003F8001FC07F0J01FC0J07FC0J01FE07E,H01FE0J0HF80I03F80FF80J01FF003F8003FE0FF80I01FE0J0HF80K0FE0FF,"
            + "H017C0J0H7K01F417F0K01F700378001F607F0J017F0J0H7M07F077,H01FC0J0HF80I03FC0FF80K0HF803F8003FE0FFC0J0HF80I0FE0L0HF07F80,"
            + "H01FC0J07F0J01FC1FE0L07F003F8001FC07FC0J0HF80I0FE0L07F07FC0,H01FC0J07F0J03FE1FE0L07F803F8003FE03FF80I0IFI01FPF83FF8,"
            + "H01740J07F0J01741F40L0H7H03F0H017E017F40I037F0H017F7F7F7F7H701F7C,H01FC0J0FE80I03FC1FE0L0BF803F8003FE00FFE80H03FFE001FPF81FFE80,"
            + "H01FC0J07F0J01FC1FC0L07F803F8001FC007FFC0H01FHFC01FPF807FFC0,H01FC0J07F0J03FE3FE0L03F803F8003FE003FFE0I0IFE03FPF803FFE0,"
            + "H017C0J0H7K01F417C0L03780378001F60017F70I037F601F7F7F7F7F7F001F7F0,H01FC0J0HF80I03FC1FC0L03F803F8003FE0H03FFE0I0IF83FEANA800BFFC,"
            + "H01FC0J07F0J01FC1FC0L07F803F8001FC0H01FFC0I05FFC1FC0Q01FFC,H01FC0J07F0J03FE1FE0L03F803F8003FE0I0IFJ02FFC1FE0R0IF,"
            + "H01F40J07F0J017C1740L07F003F0H01760I01F70J03F617E0R017F,H01FC0J0FE80I03FC1FE0L0HF803F8003FE0J0HF80I03FF0FE0S0HF80,"
            + "H01FC0J07F0J01FC1FE0L07F803F8001FC0J07F80J0HF0FE0S07F80,H01FC0J07F0J03FC1FF0L0HF803F8003FE0J03F80J0HF8FE0L020K03F80,"
            + "H017C0J0H7K01F407F0K01F780378001F60J01F40J07F0770L070K037C0,H01FC0J07F80I03FC0FF80J01FF803F8003FE0J03FC0J0BF8FF80K0FE0J03FC0,"
            + "H01FC0J07F0J01FC07FC0J01FF803F8001FC0J01FC0J07F87F0L0FE0J01FC0,H01FC0J07F0J03FE03FE0J0IF803F8003FE0J03F80J03F87F80J03FE0J03F80,"
            + "H01F40J0H7K017403770J0F7F003F0H017E0J017C040H0H703740J07F60J01F,H01FC0J0FE80I03FC03FF80H03FHF803F8003FE038003F80E0H0HF83FE0J0HFE038003F80,"
            + "H01FC0J07F0J01FC01FFE0H0JF803F8001FC07C007F81F0H0HF01FF0I01FFC0FC007F,H01FC0J07F0J03FE00FHFA23FIF803F8003FE1FE00FF83FE02FE00FFE0H03FF83FE00FF80,"
            + "H017C0J0H7K01F40077F757F7F780378001F617F017F077C017E007F70H07F701F701F7,H01FC0J07F80I03FC003FNF803FEA03FE3FFEFHF8FHFAFFE00FHFA8BFFE03FFEFHF,"
            + "H01FC0J07F0J01FC001FLF7F803FHF01FC1FJFC01FJFC001FLFC01FJFC,H01FC0J07F0J03FE0H0LFE3F803FHF03FE0FJFE03FJF8001FLF800FJFE,"
            + "H01F40J07F0J017C0H0J7F743F003F77017607I7F4017I7F0I0I7F7H7I07F7H7C,H01FC0J0FE80I03FC0I0KF83F803FHF83FE03FIF8003FHFE0I01FJF80H03FIF8,"
            + "H01FC0J07F0J01FC0I05FHFC03F801FHF01FC007FFC0H01FHFC0J07FHFD0J07FFC0,H01FC0J07F0J03FC0J0HFE003F800FHF03FE003FF80I03FF0K03FHF80J03FF80,,::::::::::::::::::::::::";

    public static byte[] getPrintData(ZebraPrintDTO dto) {
        List<Byte> bytes = new ArrayList<>();
        switch (dto.getLabelType()) {
            case LABEL_32X15:
                bytes.addAll(generateData32x15(dto));
                break;
            case LABEL_32X15VEGAS:
                bytes.addAll(generateData32x15Vegas(dto));
                break;
            case LABEL_40X30:
                bytes.addAll(generateData40x30(dto));
                break;
            case LABEL_32X25:
                bytes.addAll(generateData32x25(dto));
                break;
            default:
                break;
        }

        //convert to byte[] and print chars to console
        byte[] data = new byte[bytes.size()];
        for (int i = 0; i < bytes.size(); i++) {
            data[i] = bytes.get(i);
            System.out.print((char) data[i]);
        }
        System.out.println("");

        return data;
    }

    private static List<Byte> generateData32x15(ZebraPrintDTO dto) {
        List<Byte> bytes = new ArrayList<>();
        bytes.addAll(startLine32x15());
        int totalLabels = calculateTotalLabels(dto);
        int col = 1;
        int row = 0;
        for (ItemLabelDTO label : dto.getItems()) {
            for (int i = 0; i < label.getQuantity(); i++) {
                bytes.addAll(addItem32x15(label.getItemCode(), label.getItemName(), label.getPrice(), label.getProvCode(), label.getType(), col));
                if (col < dto.getColumns()) {
                    col++;
                } else {
                    row++;
                    if (row < (totalLabels / dto.getColumns()) + (totalLabels % dto.getColumns())) {
                        bytes.addAll(endLine());
                        bytes.addAll(startLine32x15());
                        col = 1;
                    }
                }
            }
        }
        bytes.addAll(endLine());
        return bytes;
    }

    private static List<Byte> generateData32x15Vegas(ZebraPrintDTO dto) {
        List<Byte> bytes = new ArrayList<>();
        bytes.addAll(startLine32x15Vegas());
        int totalLabels = calculateTotalLabels(dto);
        int col = 1;
        int row = 0;
        for (ItemLabelDTO label : dto.getItems()) {
            for (int i = 0; i < label.getQuantity(); i++) {
                bytes.addAll(addItem32x15Vegas(label.getItemCode(), label.getItemName(), label.getPrice(), label.getProvCode(), label.getType(), col));
                if (col < dto.getColumns()) {
                    col++;
                } else {
                    row++;
                    if (row < (totalLabels / dto.getColumns()) + (totalLabels % dto.getColumns())) {
                        bytes.addAll(endLine());
                        bytes.addAll(startLine32x15Vegas());
                        col = 1;
                    }
                }
            }
        }
        bytes.addAll(endLine());
        return bytes;
    }

    private static List<Byte> generateData40x30(ZebraPrintDTO dto) {
        List<Byte> bytes = new ArrayList<>();
        bytes.addAll(loadImage40x30());
        bytes.addAll(startLine40x30());
        int totalLabels = calculateTotalLabels(dto);
        int col = 1;
        int row = 0;
        for (ItemLabelDTO label : dto.getItems()) {
            for (int i = 0; i < label.getQuantity(); i++) {
                bytes.addAll(addItem40x30(label.getItemCode(), label.getItemName(), label.getPrice(), label.getProvCode(), label.getType(), col));
                if (col < dto.getColumns()) {
                    col++;
                } else {
                    row++;
                    if (row < (totalLabels / dto.getColumns()) + (totalLabels % dto.getColumns())) {
                        bytes.addAll(endLine());
                        bytes.addAll(startLine40x30());
                        col = 1;
                    }
                }
            }
        }
        bytes.addAll(endLine());
        return bytes;
    }

    private static List<Byte> addBarcode32x15(String itemCode, int col) {
        List<Byte> bytes = new ArrayList<>();
        //^BY2,3,86^FT14,119^BCN,,Y,N
        bytes.add((byte) '^');
        bytes.add((byte) 'B');
        bytes.add((byte) 'Y');
        bytes.add((byte) '1');
        bytes.add((byte) ',');
        bytes.add((byte) '3');
        bytes.add((byte) ',');
        bytes.add((byte) '4');
        bytes.add((byte) '9');
        bytes.add((byte) '^');
        bytes.add((byte) 'F');
        bytes.add((byte) 'T');

        int x = 280 * col - 275;// 5+(280*(x-1));
        for (byte b : Integer.toString(x).getBytes()) {
            bytes.add(b);
        }

        bytes.add((byte) ',');
        bytes.add((byte) '5');
        bytes.add((byte) '0');
        bytes.add((byte) '^');
        bytes.add((byte) 'B');
        bytes.add((byte) 'C');
        bytes.add((byte) 'N');
        bytes.add((byte) ',');
        bytes.add((byte) ',');
        bytes.add((byte) 'N');
        bytes.add((byte) ',');
        bytes.add((byte) 'N');

        //^FD>;itemCode^FS
        bytes.add((byte) '^');
        bytes.add((byte) 'F');
        bytes.add((byte) 'D');
        bytes.add((byte) '>');
        bytes.add((byte) ';');
        for (byte b : itemCode.getBytes()) {
            bytes.add(b);
        }
        bytes.add((byte) '^');
        bytes.add((byte) 'F');
        bytes.add((byte) 'S');

        return bytes;
    }

    private static List<Byte> addBarcode32x15Vegas(String itemCode, int col) {
        List<Byte> bytes = new ArrayList<>();
        //^BY2,3,86^FT14,119^BCN,,Y,N
        bytes.add((byte) '^');
        bytes.add((byte) 'B');
        bytes.add((byte) 'Y');
        bytes.add((byte) '1');
        bytes.add((byte) ',');
        bytes.add((byte) '3');
        bytes.add((byte) ',');
        bytes.add((byte) '4');
        bytes.add((byte) '9');
        bytes.add((byte) '^');
        bytes.add((byte) 'F');
        bytes.add((byte) 'T');

        int x = 295 * col - 292;// 5+(280*(x-1));
        for (byte b : Integer.toString(x).getBytes()) {
            bytes.add(b);
        }

        bytes.add((byte) ',');
        bytes.add((byte) '5');
        bytes.add((byte) '5');
        bytes.add((byte) '^');
        bytes.add((byte) 'B');
        bytes.add((byte) 'C');
        bytes.add((byte) 'N');
        bytes.add((byte) ',');
        bytes.add((byte) ',');
        bytes.add((byte) 'N');
        bytes.add((byte) ',');
        bytes.add((byte) 'N');

        //^FD>;itemCode^FS
        bytes.add((byte) '^');
        bytes.add((byte) 'F');
        bytes.add((byte) 'D');
        bytes.add((byte) '>');
        bytes.add((byte) ';');
        for (byte b : itemCode.getBytes()) {
            bytes.add(b);
        }
        bytes.add((byte) '^');
        bytes.add((byte) 'F');
        bytes.add((byte) 'S');

        return bytes;
    }

    private static List<Byte> addBarcode40x30(String itemCode, int col) {
        List<Byte> bytes = new ArrayList<>();
        //^BY2,3,86^FT14,119^BCN,,Y,N
        bytes.add((byte) '^');
        bytes.add((byte) 'B');
        bytes.add((byte) 'Y');
        bytes.add((byte) '2');
        bytes.add((byte) ',');
        bytes.add((byte) '3');
        bytes.add((byte) ',');
        bytes.add((byte) '4');
        bytes.add((byte) '2');
        bytes.add((byte) '^');
        bytes.add((byte) 'F');
        bytes.add((byte) 'T');

        int x = 345 * col - 328;// 17+(345*(x-1));
        for (byte b : Integer.toString(x).getBytes()) {
            bytes.add(b);
        }

        bytes.add((byte) ',');
        bytes.add((byte) '1');
        bytes.add((byte) '2');
        bytes.add((byte) '0');
        bytes.add((byte) '^');
        bytes.add((byte) 'B');
        bytes.add((byte) 'C');
        bytes.add((byte) 'N');
        bytes.add((byte) ',');
        bytes.add((byte) ',');
        bytes.add((byte) 'N');
        bytes.add((byte) ',');
        bytes.add((byte) 'N');

        //^FD>;itemCode^FS
        bytes.add((byte) '^');
        bytes.add((byte) 'F');
        bytes.add((byte) 'D');
        bytes.add((byte) '>');
        bytes.add((byte) ';');
        for (byte b : itemCode.getBytes()) {
            bytes.add(b);
        }
        bytes.add((byte) '^');
        bytes.add((byte) 'F');
        bytes.add((byte) 'S');

        return bytes;
    }

    private static List<Byte> addShortItemCode32x15(String itemCode, int col) {
        List<Byte> bytes = new ArrayList<>();
        //^FB93,1,0,R,0
        //^FT43,107^A0N,17,16^FH\^FDshortItemCode^FS
        bytes.add((byte) '^');
        bytes.add((byte) 'F');
        bytes.add((byte) 'B');
        bytes.add((byte) '9');
        bytes.add((byte) '3');
        bytes.add((byte) ',');
        bytes.add((byte) '1');
        bytes.add((byte) ',');
        bytes.add((byte) '0');
        bytes.add((byte) ',');
        bytes.add((byte) 'C');
        bytes.add((byte) ',');
        bytes.add((byte) '0');

        bytes.add((byte) '^');
        bytes.add((byte) 'F');
        bytes.add((byte) 'T');

        int x = 280 * col - 130;// 150+(280*(x-1));
        for (byte b : Integer.toString(x).getBytes()) {
            bytes.add(b);
        }
        bytes.add((byte) ',');
        bytes.add((byte) '2');
        bytes.add((byte) '1');
        bytes.add((byte) '^');

        bytes.add((byte) 'A');
        bytes.add((byte) '0');
        bytes.add((byte) 'N');
        bytes.add((byte) ',');
        bytes.add((byte) '2');
        bytes.add((byte) '3');
        bytes.add((byte) ',');
        bytes.add((byte) '2');
        bytes.add((byte) '0');

        bytes.add((byte) '^');
        bytes.add((byte) 'F');
        bytes.add((byte) 'D');
        String shortItemCode = itemCode.substring(0, 3).concat("*").concat(itemCode.substring(16));
        for (byte b : shortItemCode.getBytes()) {
            bytes.add(b);
        }
        bytes.add((byte) '^');
        bytes.add((byte) 'F');
        bytes.add((byte) 'S');
        return bytes;
    }

    private static List<Byte> addShortItemCode32x15Vegas(String itemCode, int col) {
        List<Byte> bytes = new ArrayList<>();
        //^FB93,1,0,R,0
        //^FT43,107^A0N,17,16^FH\^FDshortItemCode^FS
        bytes.add((byte) '^');
        bytes.add((byte) 'F');
        bytes.add((byte) 'B');
        bytes.add((byte) '9');
        bytes.add((byte) '3');
        bytes.add((byte) ',');
        bytes.add((byte) '1');
        bytes.add((byte) ',');
        bytes.add((byte) '0');
        bytes.add((byte) ',');
        bytes.add((byte) 'C');
        bytes.add((byte) ',');
        bytes.add((byte) '0');

        bytes.add((byte) '^');
        bytes.add((byte) 'F');
        bytes.add((byte) 'T');

        int x = 295 * col - 150;// 150+(280*(x-1));
        for (byte b : Integer.toString(x).getBytes()) {
            bytes.add(b);
        }
        bytes.add((byte) ',');
        bytes.add((byte) '2');
        bytes.add((byte) '6');
        bytes.add((byte) '^');

        bytes.add((byte) 'A');
        bytes.add((byte) '0');
        bytes.add((byte) 'N');
        bytes.add((byte) ',');
        bytes.add((byte) '2');
        bytes.add((byte) '3');
        bytes.add((byte) ',');
        bytes.add((byte) '2');
        bytes.add((byte) '0');

        bytes.add((byte) '^');
        bytes.add((byte) 'F');
        bytes.add((byte) 'D');
        String shortItemCode = itemCode.substring(0, 3).concat("*").concat(itemCode.substring(16));
        for (byte b : shortItemCode.getBytes()) {
            bytes.add(b);
        }
        bytes.add((byte) '^');
        bytes.add((byte) 'F');
        bytes.add((byte) 'S');
        return bytes;
    }

    private static List<Byte> addShortItemCode40x30(String itemCode, int col) {
        List<Byte> bytes = new ArrayList<>();
        //^FB93,1,0,R,0
        //^FT43,107^A0N,17,16^FH\^FDshortItemCode^FS
        bytes.add((byte) '^');
        bytes.add((byte) 'F');
        bytes.add((byte) 'B');
        bytes.add((byte) '3');
        bytes.add((byte) '0');
        bytes.add((byte) '0');
        bytes.add((byte) ',');
        bytes.add((byte) '1');
        bytes.add((byte) ',');
        bytes.add((byte) '0');
        bytes.add((byte) ',');
        bytes.add((byte) 'C');
        bytes.add((byte) ',');
        bytes.add((byte) '0');

        bytes.add((byte) '^');
        bytes.add((byte) 'F');
        bytes.add((byte) 'T');

        int x = 345 * col - 335;// 10+(345*(x-1));
        for (byte b : Integer.toString(x).getBytes()) {
            bytes.add(b);
        }
        bytes.add((byte) ',');
        bytes.add((byte) '1');
        bytes.add((byte) '4');
        bytes.add((byte) '3');
        bytes.add((byte) '^');

        bytes.add((byte) 'A');
        bytes.add((byte) '0');
        bytes.add((byte) 'N');
        bytes.add((byte) ',');
        bytes.add((byte) '2');
        bytes.add((byte) '3');
        bytes.add((byte) ',');
        bytes.add((byte) '2');
        bytes.add((byte) '4');

        bytes.add((byte) '^');
        bytes.add((byte) 'F');
        bytes.add((byte) 'D');
        String shortItemCode = itemCode.substring(0, 3).concat("*").concat(itemCode.substring(16));
        for (byte b : shortItemCode.getBytes()) {
            bytes.add(b);
        }
        bytes.add((byte) '^');
        bytes.add((byte) 'F');
        bytes.add((byte) 'S');
        return bytes;
    }

    private static List<Byte> addItemName32x15(String itemName, int col) {
        /**
         * ^FB245,2,0,C,0 Crea una caja de 245 puntos de ancho, maximo dos
         * lineas, sin espacio adicional entre lineas, alieneada al centro y sin
         * sangria en la segunda linea
         *
         * ^FT5,80 Ubica el texto a 5 puntos de la izquierda y a 80 puntos del
         * borde superior
         *
         * ^A0N,18,16 Configura la fuente tipo 0, con orientacion normal, 18
         * puntos de alto y 16 puntos de ancho
         *
         * ^FD[itemName]^FS Agrega el texto
         */
        List<Byte> bytes = new ArrayList<>();
        bytes.add((byte) '^');
        bytes.add((byte) 'F');
        bytes.add((byte) 'B');
        bytes.add((byte) '2');
        bytes.add((byte) '4');
        bytes.add((byte) '5');
        bytes.add((byte) ',');
        bytes.add((byte) '2');
        bytes.add((byte) ',');
        bytes.add((byte) '0');
        bytes.add((byte) ',');
        bytes.add((byte) 'C');
        bytes.add((byte) ',');
        bytes.add((byte) '0');

        bytes.add((byte) '^');
        bytes.add((byte) 'F');
        bytes.add((byte) 'T');

        int x = 280 * col - 275;// 150+(280*(x-1));
        for (byte b : Integer.toString(x).getBytes()) {
            bytes.add(b);
        }

        bytes.add((byte) ',');
        bytes.add((byte) '8');
        bytes.add((byte) '3');
        bytes.add((byte) '^');
        bytes.add((byte) 'A');
        bytes.add((byte) '0');
        bytes.add((byte) 'N');
        bytes.add((byte) ',');
        bytes.add((byte) '1');
        bytes.add((byte) '8');
        bytes.add((byte) ',');
        bytes.add((byte) '1');
        bytes.add((byte) '6');
        bytes.add((byte) '^');
        bytes.add((byte) 'F');
        bytes.add((byte) 'D');
        for (byte b : itemName.getBytes()) {
            bytes.add(b);
        }
        bytes.add((byte) '^');
        bytes.add((byte) 'F');
        bytes.add((byte) 'S');
        return bytes;
    }

    private static List<Byte> addItemName32x15Vegas(String itemName, int col) {
        /**
         * ^FB245,2,0,C,0 Crea una caja de 245 puntos de ancho, maximo dos
         * lineas, sin espacio adicional entre lineas, alieneada al centro y sin
         * sangria en la segunda linea
         *
         * ^FT5,80 Ubica el texto a 5 puntos de la izquierda y a 80 puntos del
         * borde superior
         *
         * ^A0N,18,16 Configura la fuente tipo 0, con orientacion normal, 18
         * puntos de alto y 16 puntos de ancho
         *
         * ^FD[itemName]^FS Agrega el texto
         */
        List<Byte> bytes = new ArrayList<>();
        bytes.add((byte) '^');
        bytes.add((byte) 'F');
        bytes.add((byte) 'B');
        bytes.add((byte) '2');
        bytes.add((byte) '4');
        bytes.add((byte) '5');
        bytes.add((byte) ',');
        bytes.add((byte) '2');
        bytes.add((byte) ',');
        bytes.add((byte) '0');
        bytes.add((byte) ',');
        bytes.add((byte) 'C');
        bytes.add((byte) ',');
        bytes.add((byte) '0');

        bytes.add((byte) '^');
        bytes.add((byte) 'F');
        bytes.add((byte) 'T');

        int x = 295 * col - 292;// 150+(280*(x-1));
        for (byte b : Integer.toString(x).getBytes()) {
            bytes.add(b);
        }

        bytes.add((byte) ',');
        bytes.add((byte) '8');
        bytes.add((byte) '8');
        bytes.add((byte) '^');
        bytes.add((byte) 'A');
        bytes.add((byte) '0');
        bytes.add((byte) 'N');
        bytes.add((byte) ',');
        bytes.add((byte) '1');
        bytes.add((byte) '8');
        bytes.add((byte) ',');
        bytes.add((byte) '1');
        bytes.add((byte) '6');
        bytes.add((byte) '^');
        bytes.add((byte) 'F');
        bytes.add((byte) 'D');
        for (byte b : itemName.getBytes()) {
            bytes.add(b);
        }
        bytes.add((byte) '^');
        bytes.add((byte) 'F');
        bytes.add((byte) 'S');
        return bytes;
    }

    private static List<Byte> addItemName40x30(String itemName, int col) {
        /**
         * ^FB245,2,0,C,0 Crea una caja de 245 puntos de ancho, maximo dos
         * lineas, sin espacio adicional entre lineas, alieneada al centro y sin
         * sangria en la segunda linea
         *
         * ^FT5,80 Ubica el texto a 5 puntos de la izquierda y a 80 puntos del
         * borde superior
         *
         * ^A0N,18,16 Configura la fuente tipo 0, con orientacion normal, 18
         * puntos de alto y 16 puntos de ancho
         *
         * ^FD[itemName]^FS Agrega el texto
         */
        List<Byte> bytes = new ArrayList<>();
        bytes.add((byte) '^');
        bytes.add((byte) 'F');
        bytes.add((byte) 'B');
        bytes.add((byte) '3');
        bytes.add((byte) '0');
        bytes.add((byte) '0');
        bytes.add((byte) ',');
        bytes.add((byte) '2');
        bytes.add((byte) ',');
        bytes.add((byte) '0');
        bytes.add((byte) ',');
        bytes.add((byte) 'C');
        bytes.add((byte) ',');
        bytes.add((byte) '0');

        bytes.add((byte) '^');
        bytes.add((byte) 'F');
        bytes.add((byte) 'T');

        int x = 345 * col - 335;// 10+(345*(x-1));
        for (byte b : Integer.toString(x).getBytes()) {
            bytes.add(b);
        }

        bytes.add((byte) ',');
        bytes.add((byte) '1');
        bytes.add((byte) '7');
        bytes.add((byte) '3');
        bytes.add((byte) '^');
        bytes.add((byte) 'A');
        bytes.add((byte) '0');
        bytes.add((byte) 'N');
        bytes.add((byte) ',');
        bytes.add((byte) '1');
        bytes.add((byte) '4');
        bytes.add((byte) ',');
        bytes.add((byte) '1');
        bytes.add((byte) '4');
        bytes.add((byte) '^');
        bytes.add((byte) 'F');
        bytes.add((byte) 'D');
        for (byte b : itemName.getBytes()) {
            bytes.add(b);
        }
        bytes.add((byte) '^');
        bytes.add((byte) 'F');
        bytes.add((byte) 'S');
        return bytes;
    }

    private static List<Byte> addPrice32x15(String price, int col) {
        List<Byte> bytes = new ArrayList<>();
        //^FB93,1,0,R,0
        bytes.add((byte) '^');
        bytes.add((byte) 'F');
        bytes.add((byte) 'B');
        bytes.add((byte) '9');
        bytes.add((byte) '5');
        bytes.add((byte) ',');
        bytes.add((byte) '1');
        bytes.add((byte) ',');
        bytes.add((byte) '0');
        bytes.add((byte) ',');
        bytes.add((byte) 'C');
        bytes.add((byte) ',');
        bytes.add((byte) '0');

        bytes.add((byte) '^');
        bytes.add((byte) 'F');
        bytes.add((byte) 'T');

        int x = 280 * col - 130;// 150+(280*(x-1));
        for (byte b : Integer.toString(x).getBytes()) {
            bytes.add(b);
        }

        bytes.add((byte) ',');
        bytes.add((byte) '4');
        bytes.add((byte) '8');
        bytes.add((byte) '^');
        bytes.add((byte) 'A');
        bytes.add((byte) '0');
        bytes.add((byte) 'N');
        bytes.add((byte) ',');
        bytes.add((byte) '2');
        bytes.add((byte) '0');
        bytes.add((byte) ',');
        bytes.add((byte) '1');
        bytes.add((byte) '8');
        bytes.add((byte) '^');
        bytes.add((byte) 'F');
        bytes.add((byte) 'D');
        for (byte b : price.getBytes()) {
            bytes.add(b);
        }
        bytes.add((byte) '^');
        bytes.add((byte) 'F');
        bytes.add((byte) 'S');
        return bytes;
    }

    private static List<Byte> addPrice32x15Vegas(String price, int col) {
        List<Byte> bytes = new ArrayList<>();
        //^FB93,1,0,R,0
        bytes.add((byte) '^');
        bytes.add((byte) 'F');
        bytes.add((byte) 'B');
        bytes.add((byte) '9');
        bytes.add((byte) '5');
        bytes.add((byte) ',');
        bytes.add((byte) '1');
        bytes.add((byte) ',');
        bytes.add((byte) '0');
        bytes.add((byte) ',');
        bytes.add((byte) 'C');
        bytes.add((byte) ',');
        bytes.add((byte) '0');

        bytes.add((byte) '^');
        bytes.add((byte) 'F');
        bytes.add((byte) 'T');

        int x = 295 * col - 147;// 150+(280*(x-1));
        for (byte b : Integer.toString(x).getBytes()) {
            bytes.add(b);
        }

        bytes.add((byte) ',');
        bytes.add((byte) '5');
        bytes.add((byte) '3');
        bytes.add((byte) '^');
        bytes.add((byte) 'A');
        bytes.add((byte) '0');
        bytes.add((byte) 'N');
        bytes.add((byte) ',');
        bytes.add((byte) '2');
        bytes.add((byte) '0');
        bytes.add((byte) ',');
        bytes.add((byte) '1');
        bytes.add((byte) '8');
        bytes.add((byte) '^');
        bytes.add((byte) 'F');
        bytes.add((byte) 'D');
        for (byte b : price.getBytes()) {
            bytes.add(b);
        }
        bytes.add((byte) '^');
        bytes.add((byte) 'F');
        bytes.add((byte) 'S');
        return bytes;
    }

    private static List<Byte> addPrice40x30(String text, int col) {
        List<Byte> bytes = new ArrayList<>();
        //^FB93,1,0,R,0
        bytes.add((byte) '^');
        bytes.add((byte) 'F');
        bytes.add((byte) 'B');
        bytes.add((byte) '3');
        bytes.add((byte) '0');
        bytes.add((byte) '0');
        bytes.add((byte) ',');
        bytes.add((byte) '1');
        bytes.add((byte) ',');
        bytes.add((byte) '0');
        bytes.add((byte) ',');
        bytes.add((byte) 'C');
        bytes.add((byte) ',');
        bytes.add((byte) '0');

        bytes.add((byte) '^');
        bytes.add((byte) 'F');
        bytes.add((byte) 'T');

        int x = 345 * col - 335;// 10+(345*(x-1));
        for (byte b : Integer.toString(x).getBytes()) {
            bytes.add(b);
        }

        bytes.add((byte) ',');
        bytes.add((byte) '2');
        bytes.add((byte) '3');
        bytes.add((byte) '0');
        bytes.add((byte) '^');
        bytes.add((byte) 'A');
        bytes.add((byte) '0');
        bytes.add((byte) 'N');
        bytes.add((byte) ',');
        bytes.add((byte) '2');
        bytes.add((byte) '9');
        bytes.add((byte) ',');
        bytes.add((byte) '2');
        bytes.add((byte) '8');
        bytes.add((byte) '^');
        bytes.add((byte) 'F');
        bytes.add((byte) 'D');

        for (byte b : text.getBytes()) {
            bytes.add(b);
        }

        bytes.add((byte) '^');
        bytes.add((byte) 'F');
        bytes.add((byte) 'S');
        return bytes;
    }

    private static List<Byte> addProviderCode32x15(String provCode, int col) {
        List<Byte> bytes = new ArrayList<>();
        //^FB245,2,0,C,0
        bytes.add((byte) '^');
        bytes.add((byte) 'F');
        bytes.add((byte) 'B');
        bytes.add((byte) '2');
        bytes.add((byte) '4');
        bytes.add((byte) '5');
        bytes.add((byte) ',');
        bytes.add((byte) '2');
        bytes.add((byte) ',');
        bytes.add((byte) '0');
        bytes.add((byte) ',');
        bytes.add((byte) 'C');
        bytes.add((byte) ',');
        bytes.add((byte) '0');

        bytes.add((byte) '^');
        bytes.add((byte) 'F');
        bytes.add((byte) 'T');

        int x = 280 * col - 275;// 150+(280*(x-1));
        for (byte b : Integer.toString(x).getBytes()) {
            bytes.add(b);
        }

        bytes.add((byte) ',');
        bytes.add((byte) '1');
        bytes.add((byte) '1');
        bytes.add((byte) '2');
        bytes.add((byte) '^');
        bytes.add((byte) 'A');
        bytes.add((byte) '0');
        bytes.add((byte) 'N');
        bytes.add((byte) ',');
        bytes.add((byte) '1');
        bytes.add((byte) '5');
        bytes.add((byte) ',');
        bytes.add((byte) '1');
        bytes.add((byte) '4');
        bytes.add((byte) '^');
        bytes.add((byte) 'F');
        bytes.add((byte) 'D');
        for (byte b : provCode.getBytes()) {
            bytes.add(b);
        }
        bytes.add((byte) '^');
        bytes.add((byte) 'F');
        bytes.add((byte) 'S');
        return bytes;
    }

    private static List<Byte> addProviderCode32x15Vegas(String provCode, int col) {
        List<Byte> bytes = new ArrayList<>();
        //^FB245,2,0,C,0
        bytes.add((byte) '^');
        bytes.add((byte) 'F');
        bytes.add((byte) 'B');
        bytes.add((byte) '2');
        bytes.add((byte) '4');
        bytes.add((byte) '5');
        bytes.add((byte) ',');
        bytes.add((byte) '2');
        bytes.add((byte) ',');
        bytes.add((byte) '0');
        bytes.add((byte) ',');
        bytes.add((byte) 'C');
        bytes.add((byte) ',');
        bytes.add((byte) '0');

        bytes.add((byte) '^');
        bytes.add((byte) 'F');
        bytes.add((byte) 'T');

        int x = 295 * col - 292;// 150+(280*(x-1));
        for (byte b : Integer.toString(x).getBytes()) {
            bytes.add(b);
        }

        bytes.add((byte) ',');
        bytes.add((byte) '1');
        bytes.add((byte) '1');
        bytes.add((byte) '7');
        bytes.add((byte) '^');
        bytes.add((byte) 'A');
        bytes.add((byte) '0');
        bytes.add((byte) 'N');
        bytes.add((byte) ',');
        bytes.add((byte) '1');
        bytes.add((byte) '5');
        bytes.add((byte) ',');
        bytes.add((byte) '1');
        bytes.add((byte) '4');
        bytes.add((byte) '^');
        bytes.add((byte) 'F');
        bytes.add((byte) 'D');
        for (byte b : provCode.getBytes()) {
            bytes.add(b);
        }
        bytes.add((byte) '^');
        bytes.add((byte) 'F');
        bytes.add((byte) 'S');
        return bytes;
    }

    private static List<Byte> addProviderCode40x30(String provCode, int col) {
        List<Byte> bytes = new ArrayList<>();
        //^FB245,2,0,C,0
        bytes.add((byte) '^');
        bytes.add((byte) 'F');
        bytes.add((byte) 'B');
        bytes.add((byte) '3');
        bytes.add((byte) '0');
        bytes.add((byte) '0');
        bytes.add((byte) ',');
        bytes.add((byte) '2');
        bytes.add((byte) ',');
        bytes.add((byte) '0');
        bytes.add((byte) ',');
        bytes.add((byte) 'C');
        bytes.add((byte) ',');
        bytes.add((byte) '0');

        bytes.add((byte) '^');
        bytes.add((byte) 'F');
        bytes.add((byte) 'T');

        int x = 345 * col - 335;// 10+(345*(x-1));
        for (byte b : Integer.toString(x).getBytes()) {
            bytes.add(b);
        }

        bytes.add((byte) ',');
        bytes.add((byte) '2');
        bytes.add((byte) '0');
        bytes.add((byte) '3');
        bytes.add((byte) '^');
        bytes.add((byte) 'A');
        bytes.add((byte) '0');
        bytes.add((byte) 'N');
        bytes.add((byte) ',');
        bytes.add((byte) '1');
        bytes.add((byte) '4');
        bytes.add((byte) ',');
        bytes.add((byte) '1');
        bytes.add((byte) '4');
        bytes.add((byte) '^');
        bytes.add((byte) 'F');
        bytes.add((byte) 'D');
        for (byte b : provCode.getBytes()) {
            bytes.add(b);
        }
        bytes.add((byte) '^');
        bytes.add((byte) 'F');
        bytes.add((byte) 'S');
        return bytes;
    }

    private static List<Byte> addItem32x15(String itemCode, String itemName, String price, String provCode, String type, int col) {
        List<Byte> bytes = new ArrayList<>();
        bytes.addAll(addBarcode32x15(itemCode, col));
        bytes.addAll(addShortItemCode32x15(itemCode, col));
        bytes.addAll(addItemName32x15(itemName, col));
        switch (type) {
            case "VENTAS":
                bytes.addAll(addPrice32x15("$" + price, col));
                break;
            case "DOTACION":
                bytes.addAll(addPrice32x15("DOTACION", col));
                break;
            case "ACTIVO FIJO":
                bytes.addAll(addPrice32x15("A. FIJO", col));
                break;
            default:
                break;
        }
        bytes.addAll(addProviderCode32x15(provCode, col));

        return bytes;
    }

    private static List<Byte> addItem32x15Vegas(String itemCode, String itemName, String price, String provCode, String type, int col) {
        List<Byte> bytes = new ArrayList<>();
        bytes.addAll(addBarcode32x15Vegas(itemCode, col));
        bytes.addAll(addShortItemCode32x15Vegas(itemCode, col));
        bytes.addAll(addItemName32x15Vegas(itemName, col));
        switch (type) {
            case "VENTAS":
                bytes.addAll(addPrice32x15Vegas("$" + price, col));
                break;
            case "DOTACION":
                bytes.addAll(addPrice32x15Vegas("DOTACION", col));
                break;
            case "ACTIVO FIJO":
                bytes.addAll(addPrice32x15Vegas("A. FIJO", col));
                break;
            default:
                break;
        }
        bytes.addAll(addProviderCode32x15Vegas(provCode, col));

        return bytes;
    }

    private static List<Byte> addItem40x30(String itemCode, String itemName, String price, String provCode, String type, int col) {
        List<Byte> bytes = new ArrayList<>();
        bytes.addAll(addImage40x30(col));
        bytes.addAll(addBarcode40x30(itemCode, col));
        bytes.addAll(addShortItemCode40x30(itemCode, col));
        bytes.addAll(addItemName40x30(itemName, col));
        switch (type) {
            case "VENTAS":
                bytes.addAll(addPrice40x30("$" + price, col));
                break;
            case "DOTACION":
                bytes.addAll(addPrice40x30("DOTACION", col));
                break;
            case "ACTIVO FIJO":
                bytes.addAll(addPrice40x30("A. FIJO", col));
                break;
            default:
                break;
        }
        bytes.addAll(addProviderCode40x30(provCode, col));

        return bytes;
    }

    private static int calculateTotalLabels(ZebraPrintDTO dto) {
        int total = 0;
        if (!dto.getItems().isEmpty()) {
            for (ItemLabelDTO label : dto.getItems()) {
                total += label.getQuantity();
            }
        } else if (!dto.getCodigos().isEmpty()) {
            for (CodigoRevisadoLabelDTO label : dto.getCodigos()) {
                total += label.getCantidad();
            }
        }
        return total;
    }

    private static List<Byte> generateData32x25(ZebraPrintDTO dto) {
        List<Byte> bytes = new ArrayList<>();
        bytes.addAll(startLine32x25());
        int totalLabels = calculateTotalLabels(dto);
        int col = 1;
        int row = 0;
        for (CodigoRevisadoLabelDTO label : dto.getCodigos()) {
            if (label.getCodigoRevisado() != null && label.getNombreEmpleado() != null) {
                for (int i = 0; i < label.getCantidad(); i++) {
                    bytes.addAll(addCodigoRevisado32x25(label.getCodigoRevisado(), col));
                    bytes.addAll(addNombreEmpleado32x25(label.getNombreEmpleado(), col));
                    bytes.addAll(addFecha32x25(label.getFecha(), col));
                    if (col < dto.getColumns()) {
                        col++;
                    } else {
                        row++;
                        if (row < (totalLabels / dto.getColumns()) + (totalLabels % dto.getColumns())) {
                            bytes.addAll(endLine());
                            bytes.addAll(startLine32x25());
                            col = 1;
                        }
                    }
                }
            }
        }
        bytes.addAll(endLine());
        return bytes;
    }

    private static List<Byte> addCodigoRevisado32x25(String codigoRevisado, int col) {
        List<Byte> bytes = new ArrayList<>();
        //^FB245,2,0,C,0
        bytes.add((byte) '^');
        bytes.add((byte) 'F');
        bytes.add((byte) 'B');
        bytes.add((byte) '2');
        bytes.add((byte) '3');
        bytes.add((byte) '0');
        bytes.add((byte) ',');
        bytes.add((byte) '1');
        bytes.add((byte) ',');
        bytes.add((byte) '0');
        bytes.add((byte) ',');
        bytes.add((byte) 'C');
        bytes.add((byte) ',');
        bytes.add((byte) '0');
        bytes.add((byte) '^');
        bytes.add((byte) 'F');
        bytes.add((byte) 'T');

        int x = 280 * col - 270;// 10+(205*(x-1));
        for (byte b : Integer.toString(x).getBytes()) {
            bytes.add(b);
        }

        bytes.add((byte) ',');
        bytes.add((byte) '6');
        bytes.add((byte) '7');
        bytes.add((byte) '^');
        bytes.add((byte) 'A');
        bytes.add((byte) '0');
        bytes.add((byte) 'N');
        bytes.add((byte) ',');
        bytes.add((byte) '6');
        bytes.add((byte) '5');
        bytes.add((byte) ',');
        bytes.add((byte) '6');
        bytes.add((byte) '7');
        bytes.add((byte) '^');
        bytes.add((byte) 'F');
        bytes.add((byte) 'D');
        for (byte b : codigoRevisado.getBytes()) {
            bytes.add(b);
        }
        bytes.add((byte) '^');
        bytes.add((byte) 'F');
        bytes.add((byte) 'S');
        return bytes;
    }

    private static List<Byte> addNombreEmpleado32x25(String nombreEmpleado, int col) {
        List<Byte> bytes = new ArrayList<>();
        //^FB245,2,0,C,0
        bytes.add((byte) '^');
        bytes.add((byte) 'F');
        bytes.add((byte) 'B');
        bytes.add((byte) '2');
        bytes.add((byte) '3');
        bytes.add((byte) '0');
        bytes.add((byte) ',');
        bytes.add((byte) '3');
        bytes.add((byte) ',');
        bytes.add((byte) '0');
        bytes.add((byte) ',');
        bytes.add((byte) 'C');
        bytes.add((byte) ',');
        bytes.add((byte) '0');
        bytes.add((byte) '^');
        bytes.add((byte) 'F');
        bytes.add((byte) 'T');

        int x = 280 * col - 270;// 10+(205*(x-1));
        for (byte b : Integer.toString(x).getBytes()) {
            bytes.add(b);
        }

        bytes.add((byte) ',');
        bytes.add((byte) '1');
        bytes.add((byte) '5');
        bytes.add((byte) '0');
        bytes.add((byte) '^');
        bytes.add((byte) 'A');
        bytes.add((byte) '0');
        bytes.add((byte) 'N');
        bytes.add((byte) ',');
        bytes.add((byte) '2');
        bytes.add((byte) '8');
        bytes.add((byte) ',');
        bytes.add((byte) '2');
        bytes.add((byte) '4');
        bytes.add((byte) '^');
        bytes.add((byte) 'F');
        bytes.add((byte) 'D');
        for (byte b : nombreEmpleado.getBytes()) {
            bytes.add(b);
        }
        bytes.add((byte) '^');
        bytes.add((byte) 'F');
        bytes.add((byte) 'S');
        return bytes;
    }

    private static List<Byte> addFecha32x25(String fecha, int col) {
        List<Byte> bytes = new ArrayList<>();
        //^FB245,2,0,C,0
        bytes.add((byte) '^');
        bytes.add((byte) 'F');
        bytes.add((byte) 'B');
        bytes.add((byte) '2');
        bytes.add((byte) '3');
        bytes.add((byte) '0');
        bytes.add((byte) ',');
        bytes.add((byte) '1');
        bytes.add((byte) ',');
        bytes.add((byte) '0');
        bytes.add((byte) ',');
        bytes.add((byte) 'C');
        bytes.add((byte) ',');
        bytes.add((byte) '0');
        bytes.add((byte) '^');
        bytes.add((byte) 'F');
        bytes.add((byte) 'T');

        int x = 280 * col - 270;// 10+(205*(x-1));
        for (byte b : Integer.toString(x).getBytes()) {
            bytes.add(b);
        }

        bytes.add((byte) ',');
        bytes.add((byte) '1');
        bytes.add((byte) '9');
        bytes.add((byte) '5');
        bytes.add((byte) '^');
        bytes.add((byte) 'A');
        bytes.add((byte) '0');
        bytes.add((byte) 'N');
        bytes.add((byte) ',');
        bytes.add((byte) '2');
        bytes.add((byte) '8');
        bytes.add((byte) ',');
        bytes.add((byte) '2');
        bytes.add((byte) '4');
        bytes.add((byte) '^');
        bytes.add((byte) 'F');
        bytes.add((byte) 'D');
        for (byte b : fecha.getBytes()) {
            bytes.add(b);
        }
        bytes.add((byte) '^');
        bytes.add((byte) 'F');
        bytes.add((byte) 'S');
        return bytes;
    }

    private static List<Byte> loadImage40x30() {
        List<Byte> bytes = new ArrayList<>();
        for (byte b : IMAGE.getBytes()) {
            bytes.add(b);
        }
        return bytes;
    }

    private static List<Byte> addImage40x30(int col) {
        List<Byte> bytes = new ArrayList<>();

        bytes.add((byte) '^');
        bytes.add((byte) 'F');
        bytes.add((byte) 'T');

        int x = 345 * col - 341;// 4+(345*(x-1));
        for (byte b : Integer.toString(x).getBytes()) {
            bytes.add(b);
        }

        bytes.add((byte) ',');
        bytes.add((byte) '6');
        bytes.add((byte) '4');
        bytes.add((byte) '^');
        bytes.add((byte) 'X');
        bytes.add((byte) 'G');
        bytes.add((byte) '0');
        bytes.add((byte) '0');
        bytes.add((byte) '0');
        bytes.add((byte) '.');
        bytes.add((byte) 'G');
        bytes.add((byte) 'R');
        bytes.add((byte) 'F');
        bytes.add((byte) ',');
        bytes.add((byte) '1');
        bytes.add((byte) ',');
        bytes.add((byte) '1');

        bytes.add((byte) '^');
        bytes.add((byte) 'F');
        bytes.add((byte) 'S');
        return bytes;
    }

    private static List<Byte> startLine32x15() {
        List<Byte> bytes = new ArrayList<>();
        //^XA
        bytes.add((byte) '^');
        bytes.add((byte) 'X');
        bytes.add((byte) 'A');

        //^PW655
        bytes.add((byte) '^');
        bytes.add((byte) 'P');
        bytes.add((byte) 'W');
        bytes.add((byte) '8');
        bytes.add((byte) '1');
        bytes.add((byte) '5');
        return bytes;
    }

    private static List<Byte> startLine32x15Vegas() {
        List<Byte> bytes = new ArrayList<>();
        //^XA
        bytes.add((byte) '^');
        bytes.add((byte) 'X');
        bytes.add((byte) 'A');

        //^PW655
        bytes.add((byte) '^');
        bytes.add((byte) 'P');
        bytes.add((byte) 'W');
        bytes.add((byte) '8');
        bytes.add((byte) '4');
        bytes.add((byte) '0');
        return bytes;
    }

    private static List<Byte> startLine40x30() {
        List<Byte> bytes = new ArrayList<>();
        //^XA
        bytes.add((byte) '^');
        bytes.add((byte) 'X');
        bytes.add((byte) 'A');

        //^PW655
        bytes.add((byte) '^');
        bytes.add((byte) 'P');
        bytes.add((byte) 'W');
        bytes.add((byte) '6');
        bytes.add((byte) '7');
        bytes.add((byte) '9');
        return bytes;
    }

    private static List<Byte> startLine32x25() {
        List<Byte> bytes = new ArrayList<>();
        //^XA
        bytes.add((byte) '^');
        bytes.add((byte) 'X');
        bytes.add((byte) 'A');

        //^PW655
        bytes.add((byte) '^');
        bytes.add((byte) 'P');
        bytes.add((byte) 'W');
        bytes.add((byte) '8');
        bytes.add((byte) '1');
        bytes.add((byte) '5');
        return bytes;
    }

    private static List<Byte> endLine() {
        List<Byte> bytes = new ArrayList<>();
        //^PQ1,0,1,Y^XZ
        bytes.add((byte) '^');
        bytes.add((byte) 'P');
        bytes.add((byte) 'Q');
        bytes.add((byte) '1');
        bytes.add((byte) ',');
        bytes.add((byte) '0');
        bytes.add((byte) ',');
        bytes.add((byte) '1');
        bytes.add((byte) ',');
        bytes.add((byte) 'Y');
        bytes.add((byte) '^');
        bytes.add((byte) 'X');
        bytes.add((byte) 'Z');
        return bytes;
    }
}
