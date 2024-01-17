package Cipher;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase que contiene el cifrado asimetrico
 *
 * @author Diego.
 */
public class Simetrico {

    private static byte[] salt = "esta es la salt!".getBytes();

    private static final Logger LOGGER = java.util.logging.Logger.getLogger("/Cipher/Simetrico");

    public String cifrarTexto(String clave, String mensaje) {
        String ret = null;
        KeySpec derivedKey = null;
        SecretKeyFactory secretKeyFactory = null;
        try {

            derivedKey = new PBEKeySpec(clave.toCharArray(), salt, 65536, 128);

            secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

            byte[] derivedKeyPBK = secretKeyFactory.generateSecret(derivedKey).getEncoded();

            SecretKey derivedKeyPBK_AES = new SecretKeySpec(derivedKeyPBK, 0, derivedKeyPBK.length, "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, derivedKeyPBK_AES);
            byte[] encodedMessage = cipher.doFinal(mensaje.getBytes());
            byte[] iv = cipher.getIV();

            byte[] combined = concatArrays(iv, encodedMessage);

            fileWriter("C:\\claves\\EjemploAES.dat", combined);

            ret = new String(encodedMessage);

        } catch (InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {

        }
        return ret;
    }

    public String descifrarTexto(String clave, String mensajeCifrado) throws IOException {
        String ret = null;
        byte[] fileContent = null;

        if (mensajeCifrado.equals("client")) {
            InputStream is = getClass().getResourceAsStream("C:\\claves\\mail.dat");
            fileContent = new byte[is.available()];
            is.read(fileContent, 0, is.available());
        } else {
            InputStream is = getClass().getResourceAsStream("C:\\claves\\contra.dat");
            fileContent = new byte[is.available()];
            is.read(fileContent, 0, is.available());
        }

        KeySpec kys = null;
        SecretKeyFactory skf = null;

        try {

            kys = new PBEKeySpec(clave.toCharArray(), salt, 65536, 128);

            skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

            byte[] key = skf.generateSecret(kys).getEncoded();

            SecretKey privateKey = new SecretKeySpec(key, 0, key.length, "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            IvParameterSpec ivParam = new IvParameterSpec(Arrays.copyOfRange(fileContent, 0, 16));

            cipher.init(Cipher.DECRYPT_MODE, privateKey, ivParam);

            byte[] decodedMessage = cipher.doFinal(Arrays.copyOfRange(fileContent, 16, fileContent.length));

            ret = new String(decodedMessage);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
        return ret;
    }

    private byte[] concatArrays(byte[] array1, byte[] array2) {
        byte[] ret = new byte[array1.length + array2.length];
        System.arraycopy(array1, 0, ret, 0, array1.length);
        System.arraycopy(array2, 0, ret, array1.length, array2.length);
        return ret;
    }

    /**
     * Escribe un fichero
     *
     * @param path Path del fichero
     * @param text Texto a escibir
     */
    private void fileWriter(String path, byte[] text) {
        try (FileOutputStream fos = new FileOutputStream(path)) {
            fos.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retorna el contenido de un fichero
     *
     * @param path Path del fichero
     * @return El texto del fichero
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

    public static void main(String[] args) {
        Simetrico ejemploAES = new Simetrico();
        KeyGenerator key = new KeyGenerator();
        key.keyGenerator();
        String mensajeCifrado = ejemploAES.cifrarTexto("Clave", "Mensaje super secreto");
        System.out.println("Cifrado! -> " + mensajeCifrado);
        System.out.println("-----------");
        try {
            System.out.println("Descifrado! -> " + ejemploAES.descifrarTexto("Clave", mensajeCifrado));
        } catch (IOException ex) {
            Logger.getLogger(Simetrico.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("-----------");
    }
}
