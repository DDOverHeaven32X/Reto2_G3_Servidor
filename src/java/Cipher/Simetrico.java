package Cipher;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.ResourceBundle;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Clase que se encarga de cifrar la calve, el correo y la contraseña del correo
 * zoho para el envio del correo
 *
 * @author Diego, Adrian
 */
public class Simetrico {

    private static final byte[] salt = generateSalt();

    /**
     * Método que genera una salt aleatoria
     *
     * @return
     */
    private static byte[] generateSalt() {
        // Genera la sal de manera segura
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    /**
     * Método que cifra todos los campos usando el algoritmo AES
     *
     * @param clave clave salt
     * @param email correo
     * @param contrasena contraseña
     * @param nombreArchivo nombre del archivo
     * @return mensaje cifrado
     */
    public String cifrarTexto(String clave, String email, String contrasena, String nombreArchivo) {
        String ret = null;
        KeySpec derivedKey = null;
        SecretKeyFactory secretKeyFactory = null;

        try {
            // Combinar email y contraseña
            String textoAEncriptar = email + " , " + contrasena;

            derivedKey = new PBEKeySpec(clave.toCharArray(), salt, 65536, 128);
            secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

            byte[] derivedKeyPBK = secretKeyFactory.generateSecret(derivedKey).getEncoded();
            SecretKey derivedKeyPBK_AES = new SecretKeySpec(derivedKeyPBK, 0, derivedKeyPBK.length, "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            cipher.init(Cipher.ENCRYPT_MODE, derivedKeyPBK_AES);

            byte[] encodedMessage = cipher.doFinal(textoAEncriptar.getBytes());
            byte[] iv = cipher.getIV();
            byte[] combined = concatArrays(iv, encodedMessage);

            fileWriter(getClass().getResource("privateKeySimetric.der").getPath(), iv);
            fileWriter(getClass().getResource("credential.properties").getPath(), combined);
            ret = new String(encodedMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * Método que descifra los parametros del correo usando la clave
     *
     * @param clave clave salt
     * @param nombreArchivo nombre del archivo
     * @return mensaje descifrado
     */
    public String descifrarTexto(String clave, String nombreArchivo) {
        String ret = null;

        byte[] fileKey = fileReader(getClass().getResource("privateKeySimetric.der").getPath());
        byte[] fileContent = fileReader(getClass().getResource("credential.properties").getPath());
        KeySpec keySpec = null;
        SecretKeyFactory secretKeyFactory = null;

        try {
            keySpec = new PBEKeySpec(clave.toCharArray(), salt, 65536, 128);
            secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

            byte[] key = secretKeyFactory.generateSecret(keySpec).getEncoded();
            SecretKey privateKey = new SecretKeySpec(key, 0, key.length, "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec ivParam = new IvParameterSpec(Arrays.copyOfRange(fileKey, 0, 16));

            cipher.init(Cipher.DECRYPT_MODE, privateKey, ivParam);
            byte[] decodedMessage = cipher.doFinal(Arrays.copyOfRange(fileContent, 16, fileContent.length));

            // Separar email y contraseña después de descifrar
            String[] partes = new String(decodedMessage).split(" , ");
            String email = partes[0];
            String contrasena = partes[1];

            ret = "Email: " + email + ", Contraseña: " + contrasena;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * Método que lee el array de datos cifrados
     *
     * @param array1
     * @param array2
     * @return
     */
    private byte[] concatArrays(byte[] array1, byte[] array2) {
        byte[] ret = new byte[array1.length + array2.length];
        System.arraycopy(array1, 0, ret, 0, array1.length);
        System.arraycopy(array2, 0, ret, array1.length, array2.length);
        return ret;
    }

    /**
     * Método que permite la creacion de la clave privada simetrica
     *
     * @param path
     * @param text
     */
    private void fileWriter(String path, byte[] text) {
        try (FileOutputStream fos = new FileOutputStream(path)) {
            fos.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método que lee la clave privada simetrica
     *
     * @param path
     * @return
     */
    private byte[] fileReader(String path) {
        byte ret[] = null;
        File file = new File(path);
        try {
            ret = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * Método main que comienza el cifrado asimetrico del servidor
     *
     * @param args main
     */
    public static void main(String[] args) {

        Simetrico sim = new Simetrico();

        String mensajeCifrado = sim.cifrarTexto("clave", "2024g3_reto2@zohomail.eu", "G3_Tartanga", "email");
        System.out.println("Cifrado -> " + mensajeCifrado);

        String mensajeDescifrado = sim.descifrarTexto("clave", "email");
        System.out.println("Descifrado -> " + mensajeDescifrado);
    }
}
