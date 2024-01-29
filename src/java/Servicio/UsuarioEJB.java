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
 *
 * @author 2dam
 */
@Stateless
public class UsuarioEJB implements UsuarioInterfaz {

    @PersistenceContext(unitName = "Reto2_G3_ServidorPU")
    private EntityManager em;

    @Override
    public void createUsuario(Usuario usuario) throws CreateException {
        if (usuario.getId_user() == null) {
            em.merge(usuario);
        }
        em.persist(usuario);
    }

    @Override
    public List<Usuario> viewByLoginContraseña(String login, String contraseña) throws ReadException {
        String contra = null;
        String contra_desc = null;
        String hash = null;
        try {
            PrivateKey privateKey = loadPrivateKeyFromFile("C:\\Cifrado\\privateKey.der");

            // Descifrar la contraseña utilizando la clave privada
            Asimetricoservidor asi = new Asimetricoservidor();
            contra_desc = asi.receiveAndDecryptMessage(contraseña, privateKey);

            // Aplicar el hash a la contraseña descifrada
            hash = HashContra.hashContra(contra_desc);

            return em.createNamedQuery("VerUsuariosPorLoginyContra").setParameter("login", login).setParameter("contraseña", hash).getResultList();
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
    }

    // Método para cargar la clave privada desde un archivo
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

    @Override
    public List<Usuario> viewByLogin(String login) throws ReadException {
        try {
            return em.createNamedQuery("VerUsuariosPorLogin").setParameter("login", login).getResultList();
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
    }

}
