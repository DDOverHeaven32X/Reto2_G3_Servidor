package Cipher;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
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

public class Simetrico {

    private static final byte[] salt = generateSalt();

    private static byte[] generateSalt() {
        // Genera la sal de manera segura
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    public String cifrarTexto(String clave, String texto, String nombreArchivo) {
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

            byte[] encodedMessage = cipher.doFinal(texto.getBytes());
            byte[] iv = cipher.getIV();
            byte[] combined = concatArrays(iv, encodedMessage);
            fileWriter("c:\\claves\\cifrado.dat", combined);
            ret = Base64.getEncoder().encodeToString(encodedMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public String descifrarTexto(String clave, String nombreArchivo) {
        String ret = null;
        byte[] fileContent = fileReader("c:\\claves\\cifrado.dat");

        KeySpec keySpec = null;
        SecretKeyFactory secretKeyFactory = null;

        try {
            keySpec = new PBEKeySpec(clave.toCharArray(), salt, 65536, 128);
            secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

            byte[] key = secretKeyFactory.generateSecret(keySpec).getEncoded();
            SecretKey privateKey = new SecretKeySpec(key, 0, key.length, "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec ivParam = new IvParameterSpec(Arrays.copyOfRange(fileContent, 0, 16));

            cipher.init(Cipher.DECRYPT_MODE, privateKey, ivParam);
            byte[] decodedMessage = cipher.doFinal(Arrays.copyOfRange(fileContent, 16, fileContent.length));

            ret = new String(decodedMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    private byte[] concatArrays(byte[] array1, byte[] array2) {
        byte[] ret = new byte[array1.length + array2.length];
        System.arraycopy(array1, 0, ret, 0, array1.length);
        System.arraycopy(array2, 0, ret, array1.length, array2.length);
        return ret;
    }

    private void fileWriter(String path, byte[] text) {
        try (FileOutputStream fos = new FileOutputStream(path)) {
            fos.write(text);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
        final ResourceBundle bundle = ResourceBundle.getBundle("Cipher.emailCreedentials");
        Simetrico sim = new Simetrico();
        KeyGenerator cla = new KeyGenerator();
        final String email = bundle.getString("EMAIL");
        final String contra = bundle.getString("CONTRASEÑA");
        cla.keyGenerator();

        System.out.println("-----------");
        String mensajeCifradoEmail = sim.cifrarTexto("Clave", email, "email");
        System.out.println("Descifrado Email -> " + sim.descifrarTexto("Clave", "email"));
        String mensajeCifradoContra = sim.cifrarTexto("Clave", contra, "contraseña");
        System.out.println("Descifrado Contraseña -> " + sim.descifrarTexto("Clave", "contraseña"));

        System.out.println("Cifrado Email -> " + mensajeCifradoEmail);
        System.out.println("Cifrado Contraseña -> " + mensajeCifradoContra);
        System.out.println("-----------");

    }
}