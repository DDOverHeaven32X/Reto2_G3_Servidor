/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicio;

import Chiper.HashContra;
import Cipher.Asimetricoservidor;
import Entidades.Usuario;
import Entidades.Zona;
import Excepciones.CreateException;
import Excepciones.ReadException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * EJB del usuario
 *
 * @author Diego, Ander, Adrian
 */
@Stateless
public class UsuarioEJB implements UsuarioInterfaz {

    @PersistenceContext(unitName = "Reto2_G3_ServidorPU")
    private EntityManager em;

    /**
     * Método que crea un usuario
     *
     * @param usuario un usaurio
     * @throws CreateException una excepcion
     */
    @Override
    public void createUsuario(Usuario usuario) throws CreateException {
        if (usuario.getId_user() == null) {
            em.merge(usuario);
        }
        em.persist(usuario);
    }

    /**
     * Método que muestra los usuarios por el correo y contraseña, cifra la
     * contraseña para hacer al compracion
     *
     * @param login un correo
     * @param contraseña una contraseña
     * @return lista de usuarios
     * @throws ReadException un error
     */
    @Override
    public List<Usuario> viewByLoginContraseña(String login, String contraseña) throws ReadException {
        String contra = null;
        String contra_desc = null;
        String hash = null;
        Asimetricoservidor asi = new Asimetricoservidor();

        try {
            PrivateKey privateKey;
            // Cargar la clave privada desde el archivo después de haberse generado
            privateKey = asi.loadPrivateKey();
            contra_desc = asi.receiveAndDecryptMessage(contraseña, privateKey);

            // Aplicar el hash a la contraseña descifrada
            hash = HashContra.hashContra(contra_desc);

            return em.createNamedQuery("VerUsuariosPorLoginyContra").setParameter("login", login).setParameter("contraseña", hash).getResultList();
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
    }

    /**
     * Método que lee la clave privada
     *
     * @param filePath
     * @return
     */
    private PrivateKey loadPrivateKeyFromFile(String filePath) {
        try {
            byte[] keyBytes = Files.readAllBytes(Paths.get(filePath));
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePrivate(spec);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Método que ve usuarios por correo
     *
     * @param login un login
     * @return una lista
     * @throws ReadException un error
     */
    @Override
    public List<Usuario> viewByLogin(String login) throws ReadException {
        try {
            return em.createNamedQuery("VerUsuariosPorLogin").setParameter("login", login).getResultList();
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
    }

}
