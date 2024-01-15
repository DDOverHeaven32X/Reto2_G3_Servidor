package Cipher;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Diego,Adrian.
 */
public class HashContra {

    private static final Logger LOGGER = java.util.logging.Logger.getLogger("/Cipher/HashContra");

    public static String hashContra(byte[] texto) {
        MessageDigest md;
        String encriptacion = "SHA";
        String msj = null;

        try {
            md = MessageDigest.getInstance(encriptacion);
            md.update(texto);
            byte[] digest = md.digest();
            msj = Arrays.toString(digest);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.log(Level.SEVERE, null, e);
        }

        return msj;
    }
}
