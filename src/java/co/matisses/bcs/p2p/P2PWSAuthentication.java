package co.matisses.bcs.p2p;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Locale;

public class P2PWSAuthentication {

    /**
     * Example of use, in your code you can ignore this function
     *
     * @param args
     * @throws NoSuchAlgorithmException
     */
    public static void main(String[] args) throws NoSuchAlgorithmException {
        String login = "cae610895dd2d726eff2bfe810065379";
        String tranKey = "0xoLWzKu";

        P2PWSAuthentication auth = new P2PWSAuthentication(login, tranKey);

        // Example of the values to use. YOU NEED TO CHANGE FOR YOUR OWN LOGIN AND TRANKEY
        System.out.println("Login: " + auth.getLogin());
        System.out.println("TranKey: " + auth.getTranKey());
        System.out.println("Seed: " + auth.getSeed());
        System.out.println("Nonce: " + auth.getNonce());
    }

    protected String login;
    protected String tranKey;
    protected String seed;
    protected String nonce;

    public P2PWSAuthentication(String login, String tranKey) {
        this.login = login;
        this.tranKey = tranKey;
        this.generate();
    }

    /**
     * Invoque this function each time that you use this class to generate a WS
     * call this will save the need to construct a new class every time
     *
     * @return
     */
    public final P2PWSAuthentication generate() {
        this.nonce = new BigInteger(130, new SecureRandom()).toString();
        this.seed = (new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ", Locale.getDefault())).format(new Date());
        return this;
    }

    /**
     * Returns the login to use on the authentication structure
     *
     * @return String
     */
    public String getLogin() {
        return this.login;
    }

    /**
     * Constructs the password digest String to use on the authentication
     * structure
     *
     * @return String
     */
    public String getTranKey() {
        try {
            return base64(sha1(nonce + seed + tranKey));
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    /**
     * Returns the seed on which the password digest has been calculated
     *
     * @return
     */
    public String getSeed() {
        return this.seed;
    }

    /**
     * Returns the Base64 encoded nonce used to generate the password digest
     *
     * @return
     */
    public String getNonce() {
        return base64(nonce.getBytes());
    }

    static byte[] sha1(String input) throws NoSuchAlgorithmException {
        MessageDigest mDigest = MessageDigest.getInstance("SHA1");
        return mDigest.digest(input.getBytes());
    }

    static String base64(byte[] input) {
        byte[] encodedBytes = (Base64.getEncoder()).encode(input);
        return new String(encodedBytes);
    }

}
