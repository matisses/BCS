package co.matisses.bcs.mailchimp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * @author ygil
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MailChimpSubscribeResponseDTO {

    private String id;
    private String unique_email_id;
    private String email_type;
    private String status;

    public MailChimpSubscribeResponseDTO() {
    }

    public MailChimpSubscribeResponseDTO(String id, String unique_email_id, String email_type, String status) {
        this.id = id;
        this.unique_email_id = unique_email_id;
        this.email_type = email_type;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUnique_email_id() {
        return unique_email_id;
    }

    public void setUnique_email_id(String unique_email_id) {
        this.unique_email_id = unique_email_id;
    }

    public String getEmail_type() {
        return email_type;
    }

    public void setEmail_type(String email_type) {
        this.email_type = email_type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
