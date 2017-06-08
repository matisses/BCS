package co.matisses.bcs.google;

/**
 *
 * @author dbotero
 */
public class URLShortenerRequestDTO {

    private String longUrl;

    public URLShortenerRequestDTO() {
    }

    public URLShortenerRequestDTO(String longUrl) {
        this.longUrl = longUrl;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }
}
