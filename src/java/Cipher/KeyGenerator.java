package Cipher;

import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * Clase que genera las claves públicas y privadas
 *
 * @author Diego.
 */
public class KeyGenerator {

    public void keyGenerator() {
        try {

            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");

            keyPairGenerator.initialize(2048);

            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            PublicKey publicKeyAndMore = keyPair.getPublic();
            byte[] publicKeyBytes = publicKeyAndMore.getEncoded();

            try (FileOutputStream publicKeyFile = new FileOutputStream("C:\\claves\\publicKey.der")) {
                publicKeyFile.write(publicKeyBytes);
            }

            PrivateKey privateKey = keyPair.getPrivate();
            byte[] privateKeyBytes = privateKey.getEncoded();
            try (FileOutputStream privateKeyFile = new FileOutputStream("C:\\claves\\privateKey.der")) {
                privateKeyFile.write(privateKeyBytes);
            }

            System.out.println("Ficheros de Clave Generados!");
        } catch (IOException | NoSuchAlgorithmException e) {
        }
    }
}
