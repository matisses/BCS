package co.matisses.bcs.dto;

import co.matisses.bcs.util.ObjectUtils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dbotero
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MailMessageDTO {

    private String templateName;
    private Map<String, String> params;
    private List<String[]> attachments;
    private String from;
    private List<String> to;
    private List<String> cc;
    private List<String> bcc;
    private String subject;

    public MailMessageDTO() {
        to = new ArrayList<>();
        cc = new ArrayList<>();
        bcc = new ArrayList<>();
        attachments = new ArrayList<>();
        params = new HashMap<>();
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public List<String> getTo() {
        return to;
    }

    public void addToAddress(String to) {
        this.to.add(to);
    }

    public List<String> getCc() {
        return cc;
    }

    public void setBcc(List<String> bcc) {
        this.bcc = bcc;
    }

    public void setCc(List<String> cc) {
        this.cc = cc;
    }

    public void setTo(List<String> to) {
        this.to = to;
    }

    public List<String> getBcc() {
        return bcc;
    }
    
    public void addBccAddress(String bcc) {
        this.bcc.add(bcc);
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<String[]> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<String[]> attachments) {
        this.attachments = attachments;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    @Override
    public String toString() {
        return ObjectUtils.toString(this);
    }
}
