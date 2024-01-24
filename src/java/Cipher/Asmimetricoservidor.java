package Cipher;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import javax.crypto.Cipher;
import org.apache.xml.security.utils.Base64;

/**
 *
 * @author Diego.
 */
public class Asmimetricoservidor {

    //private static final String ENCRYPTED_DATA_PATH = "c:\\Cifrado\\UserCredentialC.properties";
    private static final String PRIVATE_KEY_PATH = "C:\\Cifrado\\privateKey.der";  // Ruta de la clave privada generada por GenerarClaves

    private PrivateKey loadPrivateKey() {
        // Load Private Key from file
        try {
            byte[] keyBytes = Files.readAllBytes(Paths.get(PRIVATE_KEY_PATH));
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePrivate(spec);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void receiveAndDecryptMessage(byte[] encryptedData, PrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decryptedData = cipher.doFinal(encryptedData);

            String decryptedMessage = new String(decryptedData);
            System.out.println("Mensaje descifrado en el servidor: " + decryptedMessage);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private byte[] fileReader(String path) {
        byte[] ret = null;
        try {
            ret = Files.readAllBytes(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static void main(String[] args) {
        Asmimetricoservidor asimetricS = new Asmimetricoservidor();

        // Load Private Key
        PrivateKey privateKey = asimetricS.loadPrivateKey(); // Asegúrate de tener la clave privada generada por GenerarClaves

        if (privateKey != null) {
            // Leer datos cifrados desde el cliente
            if (args.length > 0) {

                byte[] encryptedData = args[0].getBytes();

                asimetricS.receiveAndDecryptMessage(encryptedData, privateKey);
            } else {
                System.out.println("Error: Falta el parámetro del mensaje cifrado");
            }

        } else {
            System.out.println("Error: Clave privada no encontrada");
        }
    }
}
