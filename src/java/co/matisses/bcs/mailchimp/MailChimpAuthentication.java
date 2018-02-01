package co.matisses.bcs.mailchimp;

import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ygil
 */
public class MailChimpAuthentication {

    private static final Logger CONSOLE = Logger.getLogger(MailChimpAuthentication.class.getSimpleName());
    private String username;
    private String apiKey;

    public MailChimpAuthentication(String username, String apiKey) {
        this.username = username;
        this.apiKey = apiKey;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getToken() {
        String tmpToken = Base64.getEncoder().encodeToString((username + ":" + apiKey).getBytes());
        CONSOLE.log(Level.INFO, "Retornando token para mailchimp [{0}]", tmpToken);
        return tmpToken;
    }
}
