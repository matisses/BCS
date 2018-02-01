package co.matisses.bcs.mailchimp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author ygil
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MailChimpSubscribeDTO {

    private String emailAddress;
    private String status;
    private MergeFields mergeFields;
    private String language;

    public MailChimpSubscribeDTO() {
    }

    @JsonProperty("email_address")
    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("merge_fields")
    public MergeFields getMergeFields() {
        return mergeFields;
    }

    public void setMergeFields(MergeFields mergeFields) {
        this.mergeFields = mergeFields;
    }

    public void setMergeFields(String fname, String lname) {
        this.mergeFields = new MergeFields(fname, lname);
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public class MergeFields {

        private String fname;
        private String lname;

        public MergeFields() {
        }

        public MergeFields(String fname, String lname) {
            this.fname = fname;
            this.lname = lname;
        }

        @JsonProperty("FNAME")
        public String getFname() {
            return fname;
        }

        public void setFname(String fname) {
            this.fname = fname;
        }

        @JsonProperty("LNAME")
        public String getLname() {
            return lname;
        }

        public void setLname(String lname) {
            this.lname = lname;
        }

        @Override
        public String toString() {
            return "MergeFields{" + "fname=" + fname + ", lname=" + lname + '}';
        }
    }

    @Override
    public String toString() {
        return "MailChimpSubscribeDTO{" + "email_address=" + emailAddress + ", status=" + status + ", mergeFields=" + mergeFields + ", language=" + language + '}';
    }
}
