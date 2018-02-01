package co.matisses.bcs.rest;

import co.matisses.bcs.dto.MensajeTextoDTO;
import co.matisses.bcs.dto.SMSServiceResponseDTO;
import co.matisses.persistence.web.entity.SmsEnviado;
import co.matisses.persistence.web.facade.SmsEnviadoFacade;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.Normalizer;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author dbotero
 */
@Stateless
@Path("sms")
public class SMSServiceREST {

    private static final Logger log = Logger.getLogger(SMSServiceREST.class.getSimpleName());
    private ExecutorService mtf = null;
    @EJB
    private SmsEnviadoFacade smsEnviadoFacade;

    @PostConstruct
    protected void init() {
        mtf = Executors.newFixedThreadPool(1);
    }

    @PreDestroy
    protected void destroy() {
        mtf.shutdownNow();
    }

    private SMSServiceResponseDTO enviar(final MensajeTextoDTO sms) {
        try {
            //TODO: parametrizar url y apikey (appbean o properties)
            String urlRequest = "http://panel.smasivos.com/api.envio.new.php";
            String urlParametros = "apikey=" + URLEncoder.encode("d4b4d68828633964808035cd81bcbe0041d03476", "UTF-8")
                    + "&mensaje=" + URLEncoder.encode(Normalizer.normalize(sms.getMensaje(), 
                            Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", ""), "UTF-8")
                    + "&numcelular=" + URLEncoder.encode(sms.getDestino(), "UTF-8")
                    + "&numregion=" + URLEncoder.encode(sms.getCodigoPais(), "UTF-8")
                    + (sms.isPruebas() ? "&sandbox=1" : "");
            URL url;
            HttpURLConnection conexion = null;
            try {
                //-----------------------crear conexion----------------------------
                url = new URL(urlRequest);
                conexion = (HttpURLConnection) url.openConnection();
                conexion.setRequestMethod("POST");
                conexion.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conexion.setRequestProperty("Content-Length", "" + Integer.toString(urlParametros.getBytes().length));
                conexion.setRequestProperty("Content-Language", "en-US");
                conexion.setUseCaches(false);
                conexion.setDoInput(true);
                conexion.setDoOutput(true);
                //--------------------Enviar Peticion------------------------------
                DataOutputStream wr = new DataOutputStream(conexion.getOutputStream());
                wr.writeBytes(urlParametros);
                wr.flush();
                wr.close();
                //-----------------------Obtener Respuesta-------------------------
                InputStream is = conexion.getInputStream();
                BufferedReader rd = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                String line;
                StringBuilder responsee = new StringBuilder();
                while ((line = rd.readLine()) != null) {
                    responsee.append(line);
                    responsee.append('\r');
                }
                rd.close();
                String respuesta = responsee.toString();
                log.log(Level.INFO, respuesta);
                //-------------Decodificar JSON con libreria org.json--------------
                try {
                    SMSServiceResponseDTO response = new ObjectMapper().readValue(respuesta, SMSServiceResponseDTO.class);
                    log.log(Level.INFO, "Finalizo envio de SMS. {0}", response);
                    return response;
                } catch (Exception e) {
                    log.log(Level.SEVERE, "Ocurrio un error al procesar la respuesta. ", e);
                    return new SMSServiceResponseDTO("-1", "Ocurrio un error al procesar la respuesta. " + e.getMessage(), null, null);
                }

                //JSONObject jsonObject = new JSONObject(respuesta);
                //SMSServiceResponseDTO response = new SMSServiceResponseDTO();
                //try {
//                    response.setCodigo(jsonObject.getString("codigo"));
//                } catch (Exception e) {
//                }
//                try {
//                    response.setMensaje(jsonObject.getString("mensaje"));
//                } catch (Exception e) {
//                }
//                try {
//                    response.setReferencia(jsonObject.getString("referencia"));
//                } catch (Exception e) {
//                }
//                try {
//                    response.setStatus(jsonObject.getString("estatus"));
//                } catch (Exception e) {
//                }
//                return response;
            } catch (Exception e) {
                log.log(Level.SEVERE, "Ocurrio un error al enviar el SMS. ", e);
                return new SMSServiceResponseDTO(null, e.getMessage(), null, "ERROR");
            } finally {
                if (conexion != null) {
                    conexion.disconnect();
                }
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al enviar el SMS. ", e);
            return new SMSServiceResponseDTO(null, e.getMessage(), null, "ERROR");
        }
    }

    @POST
    @Path("enviarSMSAsync/")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public void enviarSMSAsync(@Suspended final AsyncResponse response, final MensajeTextoDTO sms) {
        log.log(Level.INFO, "Enviando mensaje de texto {0}", sms);
        response.setTimeout(15, TimeUnit.SECONDS);

        try {
            mtf.execute(new Runnable() {
                @Override
                public void run() {
                    log.log(Level.INFO, "Iniciando ejecucion del metodo. {0}", sms.toString());

                    SMSServiceResponseDTO dto = enviar(sms);

                    if (dto != null) {
                        crearRegistroBD(sms, dto);
                    }
                    response.resume("OK");
                }
            });
        } catch (Exception e) {
            response.resume("ERROR. " + e.getMessage());
        }
    }

    private void crearRegistroBD(MensajeTextoDTO sms, SMSServiceResponseDTO dto) {
        SmsEnviado smsEnviado = new SmsEnviado();

        smsEnviado.setCelular(sms.getDestino());
        smsEnviado.setCodigoPais(Integer.parseInt(sms.getCodigoPais()));
        smsEnviado.setEstadoRespuesta(dto.getStatus());
        smsEnviado.setFecha(new Date());
        smsEnviado.setIp(sms.getIp());
        smsEnviado.setMensaje(sms.getMensaje());
        smsEnviado.setMensajeRespuesta(dto.getMensaje());
        smsEnviado.setReferenciaRespuesta(dto.getReferencia());
        smsEnviado.setUsuario(sms.getUsuario());

        try {
            smsEnviadoFacade.create(smsEnviado);
            log.log(Level.INFO, "Se hizo el registro del mensaje enviado con id {0}", smsEnviado.getIdSMSEnviado());
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al hacer el registro en base de datos del mensaje enviado", e);
        }
    }

    @POST
    @Path("enviarSMS/")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response enviarSMS(final MensajeTextoDTO sms) {
        log.log(Level.INFO, "Enviando mensaje de texto {0}", sms);
        SMSServiceResponseDTO dto = enviar(sms);
        crearRegistroBD(sms, dto);
        return Response.ok().build();
    }

    public static void main(String[] args) {
        String input = "Árvíztűrő tükörfúrñÑáógép";
        System.out.println("Input: " + input);
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        System.out.println("Normalized: " + normalized);
        String accentRemoved = normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        System.out.println("Result: " + accentRemoved);
    }
}
