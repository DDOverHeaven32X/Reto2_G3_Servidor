package Servicio;

import Cipher.ContraMail;
import Cipher.HashContra;
import Entidades.Cliente;
import Excepciones.UpdateException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
            abst.edit(cliente);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
            throw new UpdateException(e.getMessage());
        }
    }

}
