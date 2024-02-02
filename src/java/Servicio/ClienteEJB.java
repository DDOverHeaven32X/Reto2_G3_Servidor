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
 * Clase EJB del cliente
 *
 * @author Diego, Adrian.
 */
@Stateless
public class ClienteEJB implements ClienteInterfaz {

    @PersistenceContext(unitName = "Reto2_G3_ServidorPU")
    private EntityManager em;

    private static final Logger LOGGER = Logger.getLogger("/Servicio/ClienteEJB");

    private AbstractFacade abst;

    /**
     * Método que se encarga de la recuperación de la contraseña y el envio de
     * correo
     *
     * @param cliente devuelve un cliente
     * @throws UpdateException devuelve un error
     */
    @Override
    public void recuperarContra(Cliente cliente) throws UpdateException {
        String nuevaContra = null;
        ContraMail email = new ContraMail();
        try {

            nuevaContra = email.sendMail(cliente.getLogin());
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
     * Método para crear un cliente, cifra la contraseña dada y la hasea, luego
     * crea el cliente nuevo
     *
     * @param cliente devuelve un cliente
     * @throws CreateException devuelve un error
     */
    @Override
    public void createClient(Cliente cliente) throws CreateException {
        String contra = null;
        String hash = null;
        String contra_desc = null;
        Asimetricoservidor asi = new Asimetricoservidor();

        try {
            PrivateKey privateKey;
            // Cargar la clave privada desde el archivo después de haberse generado
            privateKey = asi.loadPrivateKey();

            // Obtener la contraseña del cliente
            contra = cliente.getContraseña();

            // Descifrar la contraseña utilizando la clave privada
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

    /**
     * Método que muestra todos los clientes
     *
     * @return cliente
     * @throws ReadException devuelve un error
     */
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

    /**
     * Método que muestra a los clientes que tengan el nTarjeta y Pin
     * introducidos
     *
     * @param nTarjeta tareja del cliente
     * @param pines pin de seguridad del cliente
     * @return cliente
     * @throws ReadException devuelve un error
     */
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

    /**
     * Método que filtra un cliente por Id
     *
     * @param id id del cliente
     * @return cliente
     * @throws ReadException devuelve un error
     */
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

    /**
     * Método que permite el cambio de contraseña, la cifra, hasea y la
     * reestablece en la base de datos
     *
     * @param cliente devuelve un cliente
     * @throws UpdateException devuelve un error
     */
    @Override
    public void cambiarContra(Cliente cliente) throws UpdateException {
        String contra = null;
        String hash = null;
        String contra_desc = null;
        Asimetricoservidor asi = new Asimetricoservidor();
        try {

            PrivateKey privateKey;

            // Cargar la clave privada desde el archivo después de haberse generado
            privateKey = asi.loadPrivateKey();

            // Obtener la contraseña del cliente
            contra = cliente.getContraseña();

            // Descifrar la contraseña utilizando la clave privada
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

    /**
     * Método que carga la clave privada desde una archivo
     *
     * @param filePath ruta de la clave
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
}
