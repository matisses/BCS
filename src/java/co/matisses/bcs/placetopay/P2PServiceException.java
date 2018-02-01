package co.matisses.bcs.placetopay;

/**
 *
 * @author dbotero
 */
public class P2PServiceException extends Exception {

    public P2PServiceException() {
    }

    public P2PServiceException(String message) {
        super(message);
    }

    public P2PServiceException(String message, Throwable cause) {
        super(message, cause);
    }

}
