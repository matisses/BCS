package co.matisses.bcs.encode;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author ygil
 */
public class Encode128 {

    private static final Logger log = Logger.getLogger(Encode128.class.getSimpleName());

    public static String encode(String parameter) {
        if (parameter != null && !parameter.isEmpty()) {
            log.log(Level.INFO, "Convertir parametro a Code 128 {0}", parameter);
            String code128;
            boolean exito = false;
            int mini = 0;

            boolean validData = true;
            for (int i = 0; i < parameter.length(); i++) {
                char c = parameter.charAt(i);
                if (!((c >= 32 && c <= 126) || c == 203)) {
                    validData = false;
                    break;
                }
            }
            code128 = "";
            exito = true;
            if (validData) {
                for (int i = 0; i < parameter.length();) {
                    if (exito) {
                        mini = (i == 0 || i + 3 == parameter.length()) ? 4 : 6;
                        mini = testNum(i, mini, parameter);
                        if (mini < 0) {
                            if (i == 0) {
                                code128 = new Character((char) 205).toString();
                            } else {
                                code128 = new Character((char) 199).toString();
                            }
                            exito = false;
                        } else if (i == 0) {
                            code128 = new Character((char) 204).toString();
                        }
                    }
                    if (!exito) {
                        mini = 2;
                        mini = testNum(i, mini, parameter);
                        if (mini < 0) {
                            char c1 = parameter.charAt(i);
                            char c2 = parameter.charAt(i + 1);
                            int c = Integer.parseInt(c1 + "" + c2);
                            c = (c < 95) ? (c += 32) : (c += 100);
                            code128 += new Character((char) c).toString();
                            i += 2;
                        } else {
                            code128 += new Character((char) 200).toString();
                            exito = true;
                        }
                    }
                    if (exito) {
                        code128 += new Character((char) parameter.charAt(i)).toString();
                        i++;
                    }
                }
                int checkSum = 0;
                for (int i = 0; i < code128.length(); i++) {
                    int c = code128.charAt(i);
                    c = (c < 127) ? (c -= 32) : (c -= 100);
                    if (i == 0) {
                        checkSum = c;
                    }
                    checkSum = (checkSum + ((i + 1) - 1) * c) % 103;
                }
                checkSum = (checkSum < 95) ? (checkSum + 32) : (checkSum + 100);
                code128 += new Character((char) checkSum).toString() + new Character((char) 206).toString();
            }
            log.log(Level.INFO, "Code 128 obtenido [{0}]", code128);
            return code128;
        }
        return null;
    }

    private static int testNum(int i, int mini, String parameter) {
        mini--;
        if (i + mini <= parameter.length()) {
            do {
                char c = parameter.charAt(i + mini);
                if (c < 48 || c > 57) {
                    return mini;
                }
                mini--;
            } while (mini >= 0);
        }
        return mini;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "codificar")
    public String codificar(@WebParam(name = "parameter") String parameter) {
        if (parameter != null && !parameter.isEmpty()) {
            return Encode128.encode(parameter);
        }
        return null;
    }

    @WebMethod(operationName = "codificarLista")
    public List<String> codificarLista(@WebParam(name = "parameters") List<String> parameters) {
        if (parameters != null && !parameters.isEmpty()) {
            return encodeList(parameters);
        }
        return null;
    }

    private List<String> encodeList(List<String> parameters) {
        List<String> retorno = new ArrayList<>();
        for (String p : parameters) {
            retorno.add(Encode128.encode(p));
        }
        return retorno;
    }
}