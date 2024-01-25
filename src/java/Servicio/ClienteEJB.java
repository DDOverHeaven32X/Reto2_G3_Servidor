package Servicio;

import Cipher.ContraMail;
import Chiper.HashContra;
import Entidades.Cliente;
import Excepciones.CreateException;
import Excepciones.ReadException;
import Excepciones.UpdateException;
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
            nuevaContra = HashContra.hashContra(nuevaContra.getBytes());
            cliente.setLogin(nuevaContra);
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
        try {
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

}
