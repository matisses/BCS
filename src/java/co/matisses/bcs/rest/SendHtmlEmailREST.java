package co.matisses.bcs.rest;

import co.matisses.bcs.dto.MailMessageDTO;
import co.matisses.bcs.dto.ResponseDTO;
import co.matisses.bcs.mbean.BCSApplicationMBean;
import co.matisses.bcs.mbean.BCSGenericMBean;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.text.StrSubstitutor;

/**
 *
 * @author dbotero
 */
@Stateless
@Path("email")
public class SendHtmlEmailREST {

    @Inject
    private BCSApplicationMBean applicationMBean;
    @Inject
    private BCSGenericMBean bcsGenericMBean;
    private static final Logger log = Logger.getLogger(SendHtmlEmailREST.class.getSimpleName());
    private String username;
    private String password;
    private String host;
    private String templatesFolder;
    private Properties propsConn;

    public static enum MessageTemplate {
        lista_regalos_codigo_ingreso, lista_regalos_pago_vencido, sonda_mercadolibre, error_sonda_documentos
    }

    @PostConstruct
    protected void initialize() {
        username = applicationMBean.obtenerValorPropiedad("mail.username");
        password = applicationMBean.obtenerValorPropiedad("mail.password");
        host = applicationMBean.obtenerValorPropiedad("mail.host");
        templatesFolder = applicationMBean.obtenerValorPropiedad("mail.templates");
        inicializarParametros();
    }

    private void inicializarParametros() {
        propsConn = new Properties();
        propsConn.put("mail.smtp.auth", "true");
        propsConn.put("mail.smtp.starttls.enable", "false");
        propsConn.put("mail.smtp.host", host);
        propsConn.put("mail.smtp.port", "25");
    }

    @POST
    @Path("enviarEmail")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    public Response sendMail(MailMessageDTO mailMessage) {
        if (username == null || password == null || host == null || templatesFolder == null) {
            log.log(Level.SEVERE, "No fue posible cargar los valores de configuracion del archivo bcs.properties, por lo tanto no es posible enviar el mensaje {0}. ",
                    mailMessage.toString());
            return Response.serverError().build();
        }
        log.log(Level.INFO, "Iniciando sesion en servidor de correo");
        // Get the Session object.
        Session session = Session.getInstance(propsConn, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        log.log(Level.INFO, "Sesion iniciada correctamente en servidor de correo");

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(mailMessage.getFrom()));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(getAsList(mailMessage.getTo())));
            message.addRecipients(Message.RecipientType.CC, InternetAddress.parse(getAsList(mailMessage.getCc())));
            message.addRecipients(Message.RecipientType.BCC, InternetAddress.parse(getAsList(mailMessage.getBcc())));
            message.setSubject(mailMessage.getSubject());

            //Valida que la plantilla exista
            String fullTemplateName = templatesFolder + mailMessage.getTemplateName() + ".html";
            log.log(Level.INFO, "Buscando plantilla {0}", fullTemplateName);
            if (!new File(fullTemplateName).exists()) {
                log.log(Level.SEVERE, "No fue posible enviar el mensaje. La plantilla {0} no existe.", fullTemplateName);
                return Response.serverError().build();
            }

            Multipart multipart = new MimeMultipart();
            BodyPart contentBodyPart = new MimeBodyPart();
            //Agrega el cuerpo del mensaje a partir de una plantilla
            try {
                String templateContent = new String(Files.readAllBytes(Paths.get(fullTemplateName)), StandardCharsets.UTF_8);
                contentBodyPart.setContent(StrSubstitutor.replace(templateContent, mailMessage.getParams()), "text/html");
                multipart.addBodyPart(contentBodyPart);
            } catch (IOException | MessagingException e) {
                log.log(Level.SEVERE, "No fue posible cargar la plantilla del mensaje. ", e);
                return Response.serverError().build();
            }

            //Agrega los archivos adjuntos
            try {
                if (mailMessage.getAttachments() != null) {
                    for (String[] filename : mailMessage.getAttachments()) {
                        BodyPart attachmentBodyPart = new MimeBodyPart();
                        DataSource source = new FileDataSource(filename[0]);
                        attachmentBodyPart.setDataHandler(new DataHandler(source));
                        attachmentBodyPart.setFileName(filename[1]);
                        multipart.addBodyPart(attachmentBodyPart);
                    }
                }
            } catch (Exception e) {
                log.log(Level.WARNING, "No fue posible procesar los archivos adjuntos. ", e);
            }
            message.setContent(multipart);
            Transport.send(message);
        } catch (MessagingException e) {
            log.log(Level.SEVERE, "No fue posible enviar el mensaje de correo. ", e);
            return Response.serverError().build();
        }
        return Response.ok().build();
    }

    @POST
    @Path("enviaremail360")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    public ResponseDTO sendMail360(MailMessageDTO mailMessage) {
        if (username == null || password == null || host == null || templatesFolder == null) {
            log.log(Level.SEVERE, "No fue posible cargar los valores de configuracion del archivo bcs.properties, por lo tanto no es posible enviar el mensaje {0}. ", mailMessage.toString());
            return new ResponseDTO(0, "No fue posible cargar los valores de configuracion del archivo bcs.properties, por lo tanto no es posible enviar el mensaje " + mailMessage.toString());
        }
        log.log(Level.INFO, "Iniciando sesion en servidor de correo");
        // Get the Session object.
        Session session = Session.getInstance(propsConn, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        log.log(Level.INFO, "Sesion iniciada correctamente en servidor de correo");

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(mailMessage.getFrom()));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(getAsList(mailMessage.getTo())));
            message.addRecipients(Message.RecipientType.CC, InternetAddress.parse(getAsList(mailMessage.getCc())));
            message.addRecipients(Message.RecipientType.BCC, InternetAddress.parse(getAsList(mailMessage.getBcc())));
            message.setSubject(mailMessage.getSubject());

            //Valida que la plantilla exista
            String fullTemplateName = templatesFolder + mailMessage.getTemplateName() + ".html";
            log.log(Level.INFO, "Buscando plantilla {0}", fullTemplateName);
            if (!new File(fullTemplateName).exists()) {
                log.log(Level.SEVERE, "No fue posible enviar el mensaje. La plantilla {0} no existe.", fullTemplateName);
                return new ResponseDTO(0, "No fue posible enviar el mensaje. La plantilla " + fullTemplateName + " no existe.");
            }

            Multipart multipart = new MimeMultipart();
            BodyPart contentBodyPart = new MimeBodyPart();
            //Agrega el cuerpo del mensaje a partir de una plantilla
            try {
                String templateContent = new String(Files.readAllBytes(Paths.get(fullTemplateName)), StandardCharsets.UTF_8);
                contentBodyPart.setContent(bcsGenericMBean.convertirCaracteresEspeciales(StrSubstitutor.replace(templateContent, mailMessage.getParams())), "text/html");
                multipart.addBodyPart(contentBodyPart);
            } catch (IOException | MessagingException e) {
                log.log(Level.SEVERE, "No fue posible cargar la plantilla del mensaje. ", e);
                return new ResponseDTO(0, "No fue posible cargar la plantilla del mensaje. " + e);
            }

            //Agrega los archivos adjuntos
            try {
                if (mailMessage.getAttachments() != null) {
                    for (String[] filename : mailMessage.getAttachments()) {
                        BodyPart attachmentBodyPart = new MimeBodyPart();
                        DataSource source = new FileDataSource(filename[0]);
                        attachmentBodyPart.setDataHandler(new DataHandler(source));
                        attachmentBodyPart.setFileName(filename[1]);
                        multipart.addBodyPart(attachmentBodyPart);
                    }
                }
            } catch (Exception e) {
                log.log(Level.WARNING, "No fue posible procesar los archivos adjuntos. ", e);
            }
            message.setContent(multipart);
            Transport.send(message);
        } catch (MessagingException e) {
            log.log(Level.SEVERE, "No fue posible enviar el mensaje de correo. ", e);
            return new ResponseDTO(0, "No fue posible enviar el mensaje de correo. " + e);
        }
        return new ResponseDTO(1, "Se envio el correo correctamente.");
    }

    @POST
    @Path("enviaremailsoporte")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    public ResponseDTO sendMailSoporte(MailMessageDTO mailMessage) {
        if (username == null || password == null || host == null || templatesFolder == null) {
            log.log(Level.SEVERE, "No fue posible cargar los valores de configuracion del archivo bcs.properties, por lo tanto no es posible enviar el mensaje {0}. ", mailMessage.toString());
            return new ResponseDTO(0, "No fue posible cargar los valores de configuracion del archivo bcs.properties, por lo tanto no es posible enviar el mensaje " + mailMessage.toString());
        }
        log.log(Level.INFO, "Iniciando sesion en servidor de correo");
        // Get the Session object.
        Session session = Session.getInstance(propsConn, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        log.log(Level.INFO, "Sesion iniciada correctamente en servidor de correo");

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(mailMessage.getFrom()));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(getAsList(mailMessage.getTo())));
            message.addRecipients(Message.RecipientType.CC, InternetAddress.parse(getAsList(mailMessage.getCc())));
            message.addRecipients(Message.RecipientType.BCC, InternetAddress.parse(getAsList(mailMessage.getBcc())));
            message.setSubject(mailMessage.getSubject());

            //Valida que la plantilla exista
            String fullTemplateName = templatesFolder + mailMessage.getTemplateName() + ".html";
            log.log(Level.INFO, "Buscando plantilla {0}", fullTemplateName);
            if (!new File(fullTemplateName).exists()) {
                log.log(Level.SEVERE, "No fue posible enviar el mensaje. La plantilla {0} no existe.", fullTemplateName);
                return new ResponseDTO(0, "No fue posible enviar el mensaje. La plantilla " + fullTemplateName + " no existe.");
            }

            Multipart multipart = new MimeMultipart();
            BodyPart contentBodyPart = new MimeBodyPart();
            //Agrega el cuerpo del mensaje a partir de una plantilla
            try {
                String templateContent = new String(Files.readAllBytes(Paths.get(fullTemplateName)), StandardCharsets.UTF_8);
                contentBodyPart.setContent(bcsGenericMBean.convertirCaracteresEspeciales(StrSubstitutor.replace(templateContent, mailMessage.getParams())), "text/html");
                multipart.addBodyPart(contentBodyPart);
            } catch (IOException | MessagingException e) {
                log.log(Level.SEVERE, "No fue posible cargar la plantilla del mensaje. ", e);
                return new ResponseDTO(0, "No fue posible cargar la plantilla del mensaje. " + e);
            }

            //Agrega los archivos adjuntos
            try {
                if (mailMessage.getAttachments() != null) {
                    for (String[] filename : mailMessage.getAttachments()) {
                        File file = new File(filename[1]);
                        FileUtils.write(file, filename[0]);

                        BodyPart attachmentBodyPart = new MimeBodyPart();
                        DataSource source = new FileDataSource(file);
                        attachmentBodyPart.setDataHandler(new DataHandler(source));
                        attachmentBodyPart.setFileName(filename[1]);
                        multipart.addBodyPart(attachmentBodyPart);
                    }
                }
            } catch (IOException | MessagingException e) {
                log.log(Level.WARNING, "No fue posible procesar los archivos adjuntos. ", e);
            }
            message.setContent(multipart);
            Transport.send(message);
        } catch (MessagingException e) {
            log.log(Level.SEVERE, "No fue posible enviar el mensaje de correo. ", e);
            return new ResponseDTO(0, "No fue posible enviar el mensaje de correo. " + e);
        }
        return new ResponseDTO(1, "Se envio el correo correctamente.");
    }

    private String getAsList(List<String> addresses) {
        StringBuilder sb = new StringBuilder();
        for (String address : addresses) {
            if (address != null) {
                if (sb.length() > 0) {
                    sb.append(",");
                }
                sb.append(address);
            }
        }
        return sb.toString();
    }
}
