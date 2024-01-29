package Servicio;

import Cipher.ContraMail;
import Chiper.HashContra;
import Cipher.Asimetricoservidor;
import Entidades.Cliente;
import Excepciones.CreateException;
import Excepciones.ReadException;
import Excepciones.UpdateException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.InternalServerErrorException;

/**
 *
 * @author Diego, Adrian.
 */
@Stateless
public class ClienteEJB implements ClienteInterfaz {

    @PersistenceContext(unitName = "Reto2_G3_ServidorPU")
    private EntityManager em;

    private static final Logger LOGGER = Logger.getLogger("/Servicio/ClienteEJB");

    private AbstractFacade abst;

    @Override
    public void recuperarContra(Cliente cliente) throws UpdateException {
        String nuevaContra = null;
        ContraMail email = new ContraMail();
        try {
            nuevaContra = email.sendMail(cliente.getLogin());
            System.out.println(nuevaContra);
            nuevaContra = HashContra.hashContra(nuevaContra);
            cliente.setContraseña(nuevaContra);
            if (!em.contains(cliente)) {
                em.merge(cliente);
            }
            em.flush();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
            throw new UpdateException(e.getMessage());
        }
    }

    /**
     * Método para crear un cliente
     *
     * @param cliente
     * @throws CreateException
     */
    @Override
    public void createClient(Cliente cliente) throws CreateException {
        String contra = null;
        String hash = null;
        String contra_desc = null;

        try {
            // Cargar la clave privada desde el archivo después de haberse generado
            PrivateKey privateKey = loadPrivateKeyFromFile("C:\\Cifrado\\privateKey.der");

            // Obtener la contraseña del cliente
            contra = cliente.getContraseña();

            // Descifrar la contraseña utilizando la clave privada
            Asimetricoservidor asi = new Asimetricoservidor();
            contra_desc = asi.receiveAndDecryptMessage(contra, privateKey);

            // Aplicar el hash a la contraseña descifrada
            hash = HashContra.hashContra(contra_desc);
            cliente.setContraseña(hash);

            // Persistir el cliente en la base de datos
            em.persist(cliente);
        } catch (Exception e) {
            throw new CreateException(e.getMessage());
        }
    }

    @Override
    public List<Cliente> viewAllClientes() throws ReadException {
        List<Cliente> cliente = null;
        try {
            cliente
                    = em.createNamedQuery("verTodosLosClientes").getResultList();
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
        return cliente;
    }

    @Override
    public List<Cliente> BankCredential(Long nTarjeta, Integer pines) throws ReadException {
        List<Cliente> cliente = null;
        try {
            cliente
                    = em.createNamedQuery("VerUsuarioPorCuentaBancaria").setParameter("n_tarjeta", nTarjeta).setParameter("pin", pines).getResultList();
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
        return cliente;
    }

    @Override
    public Cliente filtrarClientePorID(Integer id) throws ReadException {
        Cliente cliente;
        try {
            cliente = em.find(Cliente.class, id);
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
        return cliente;
    }

    @Override
    public void cambiarContra(Cliente cliente) throws UpdateException {
        String contra = null;
        String hash = null;
        String contra_desc = null;
        try {
            PrivateKey privateKey = loadPrivateKeyFromFile("C:\\Cifrado\\privateKey.der");

            // Obtener la contraseña del cliente
            contra = cliente.getContraseña();

            // Descifrar la contraseña utilizando la clave privada
            Asimetricoservidor asi = new Asimetricoservidor();
            contra_desc = asi.receiveAndDecryptMessage(contra, privateKey);

            // Aplicar el hash a la contraseña descifrada
            hash = HashContra.hashContra(contra_desc);
            cliente.setContraseña(hash);

            if (!em.contains(cliente)) {
                em.merge(cliente);
            }
            em.flush();
        } catch (Exception e) {
            throw new UpdateException(e.getMessage());
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
}
