package Servicio;

import Entidades.Cliente;
import Entidades.Compra;
import Entidades.Entrada;
import Excepciones.CreateException;
import Excepciones.ReadException;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Diego.
 */
@Stateless
public class CompraEJB implements CompraInterfaz {

    @PersistenceContext(unitName = "Reto2_G3_ServidorPU")
    private EntityManager em;

    private static final Logger LOGGER = Logger.getLogger("/Servicio/CompraEJB");

    /**
     * Método que crea una nueva compra, añade el cliente y la entrada en el
     * entorno de persistencia para realizar la insercion
     *
     * @param compra
     * @throws CreateException
     */
    @Override
    public void createEntrada(Compra compra) throws CreateException {
        try {
            Cliente cliente = em.find(Cliente.class, compra.getCompraId().getId_user());
            if (cliente == null) {
                cliente = em.merge(compra.getCliente());
            }
            Entrada entrada = em.find(Entrada.class, compra.getCompraId().getId_entrada());
            if (entrada == null) {
                entrada = em.merge(compra.getEntrada());
            }
            compra.setCliente(cliente);
            compra.setEntrada(entrada);
            em.persist(compra);
        } catch (Exception e) {
            throw new CreateException(e.getMessage());
        }
    }


    @Override
    public List<Compra> viewAllCompras() throws ReadException {
        List<Compra> compra = null;
        try {
            compra
                    = em.createNamedQuery("VerTodasLasCompras").getResultList();
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
        return compra;
    }

}
