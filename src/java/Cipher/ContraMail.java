package Cipher;

import java.io.File;
import java.io.IOException;

import java.nio.file.Files;
import java.security.SecureRandom;

import java.util.Date;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Clase que recive los parámetros cifrados de forma Simetrica del correo de
 * zoho, crea una contraseña aleatoria ajustada a nuestro patrón y finalmente,
 * envia el correo al usaurio que lo solicite
 *
 * @author Diego, Adrían.
 */
public class ContraMail {

    static String sSalt = "Mensaje super secreto";
    private static byte[] salt = sSalt.getBytes();
    String client = null;
    String contraMail = null;
    Integer caracteresMinimos = 8;
    final String ZOHO_HOST = "smtp.zoho.eu";
    final String TLS_PORT = "465";

    final String SENDER_USERNAME = "2024g3_reto2@zohomail.eu";
    final String SENDER_PASSWORD = "G3_Tartanga";
    private static final Logger LOGGER = java.util.logging.Logger.getLogger("/Cipher/ContraMail");

    /**
     * Método que recive las credenciales descifradas,y envia un correo con las
     * claves generadas de forma aleatoria
     *
     * @param mailUser correo del usuario
     * @return nueva contraseña
     */
    public String sendMail(String mailUser) {
        Simetrico simi = new Simetrico();

        client = simi.descifrarTexto("Clave", "client");
        contraMail = simi.descifrarTexto("Clave", "contra");

        final String recibido = mailUser;
        final String nuevaContra = randomPasswordGenerator(caracteresMinimos);

        Properties props = System.getProperties();
        props.setProperty("mail.smtps.host", ZOHO_HOST);
        props.setProperty("mail.smtp.port", TLS_PORT);
        props.setProperty("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.smtp.starttls.required", "true");
        props.setProperty("mail.smtp.ssl.enable", "true");
        props.setProperty("mail.smtp.ssl.checkserveridentity", "true");
        props.setProperty("mail.smtps.auth", "true");
        props.put("mail.smtps.quitwait", "false");
        props.setProperty("mail.smtp.ssl.trust", "smtp.zoho.eu");
        props.setProperty("mail.debug", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(client, contraMail);
            }
        });

        try {

            final MimeMessage msg = new MimeMessage(session);

            msg.setFrom(new InternetAddress(SENDER_USERNAME));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recibido, false));
            msg.setSubject("Recuperación de contraseña");
            msg.setText("Nos ponemos en contacto contigo para informarte que hemos recibido una solicitud de recuperación de contraseña para tu cuenta en nuestro sistema. Entendemos lo importante que es el acceso seguro a tus datos y estamos aquí para ayudarte en este proceso.\n"
                    + "A continuación, encontrarás tu nueva contraseña temporal: " + nuevaContra + ".\n"
                    + "Por razones de seguridad, te recomendamos cambiar esta contraseña tan pronto como ingreses a tu cuenta. Si tienes alguna pregunta o necesitas asistencia adicional, no dudes en responder a este correo electrónico.\n"
                    + "Si no solicitaste esto o necesitas ayuda, respóndenos.\n"
                    + "Saludos, 2Dam G3 CIFP Tartanga LHII");
            msg.setSentDate(new Date());

            Transport transport = session.getTransport("smtp");

            transport.connect(ZOHO_HOST, SENDER_USERNAME, SENDER_PASSWORD);
            transport.sendMessage(msg, msg.getAllRecipients());

        } catch (MessagingException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return nuevaContra;

    }

    /**
     * Método que genera la contraseña de forma aleatoria
     *
     * @param len numero de digitos
     * @return la nueva contraseña
     */
    public String randomPasswordGenerator(Integer len) {
        final String mayusculas = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ";
        final String minusculas = "abcdefghijklmnñopqrstuvwxyz";
        final String numeros = "0123456789";
        final String caracteres = mayusculas + minusculas + numeros;

        SecureRandom rng = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        // Al menos una mayúscula, una minúscula y un número
        sb.append(mayusculas.charAt(rng.nextInt(mayusculas.length())));
        sb.append(minusculas.charAt(rng.nextInt(minusculas.length())));
        sb.append(numeros.charAt(rng.nextInt(numeros.length())));

        // Resto de caracteres
        for (int i = 3; i < len; i++) {
            int random = rng.nextInt(caracteres.length());
            sb.append(caracteres.charAt(random));
        }

        return sb.toString();
    }

    /**
     * Método que lee ficheros
     *
     * @param path
     * @return
     */
    private static byte[] fileReader(String path) {
        byte ret[] = null;
        File file = new File(path);
        try {
            ret = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }
}
