package Cipher;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import javax.crypto.Cipher;
import javax.xml.bind.DatatypeConverter;

/**
 * Se encarga de descrifrar la contraseña del cliente
 *
 * @author Diego.
 */
public class Asimetricoservidor {

    private static final String PRIVATE_KEY_PATH = "src/cipher/privateKey.der";

    /**
     * Método carga la clave privada generada
     *
     * @return una llave privada
     */
    public PrivateKey loadPrivateKey() {
        // Load Private Key from file
        try {
            byte[] keyBytes = fileReader(getClass().getResource("privateKey.der").getPath());
            // byte[] privateKeyBytes;
            // FileInputStream fis = new FileInputStream(".//src//cipher//privateKey.der");
            //privateKeyBytes = new byte[fis.available()];
            //fis.read(privateKeyBytes);

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            return keyFactory.generatePrivate(spec);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Método que recive los datos cifrados de forma hexadecimal y los descifra
     * usando la clave privada
     *
     * @param encryptedHexData mensaje cifrado
     * @param privateKey llave privada
     * @return mensjae descifrado
     */
    public String receiveAndDecryptMessage(String encryptedHexData, PrivateKey privateKey) {
        String decryptedMessage = null;

        try {
            // Convertir la cadena hexadecimal a un array de bytes
            byte[] encryptedData = DatatypeConverter.parseHexBinary(encryptedHexData);

            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            // Realizar la operación de descifrado
            byte[] decryptedData = cipher.doFinal(encryptedData);

            decryptedMessage = new String(decryptedData);
            System.out.println("Mensaje descifrado en el servidor: " + decryptedMessage);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return decryptedMessage;
    }

    /**
     * Método que lee la clave privada del servidor
     *
     * @param path
     * @return
     */
    private byte[] fileReader(String path) {
        byte[] ret = null;
        File file = new File(path);
        try {
            ret = Files.readAllBytes(file.toPath());
            // InputStream in= getClass().getResourceAsStream("publicKey.der");
            //ret = toByteArray(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }
}
