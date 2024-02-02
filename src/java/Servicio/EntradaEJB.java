package Servicio;

import Entidades.Entrada;
import Entidades.Zona;
import Excepciones.CreateException;
import Excepciones.DeleteException;
import Excepciones.ReadException;
import Excepciones.UpdateException;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Clase EJB con los métodos de la interfaz de entrada
 *
 * @author Diego.
 */
@Stateless
public class EntradaEJB implements EntradaIntefraz {

    /**
     * Método que instancia la persistencia del proyecto
     */
    @PersistenceContext(unitName = "Reto2_G3_ServidorPU")
    private EntityManager em;

    /**
     * Método EJB para crear una entrada
     *
     * @param entrada una entrada
     * @throws CreateException un error
     */
    @Override
    public void createEntrada(Entrada entrada) throws CreateException {
        try {
            em.persist(entrada);
        } catch (Exception e) {
            throw new CreateException(e.getMessage());
        }
    }

    /**
     * Método EJB para modificar una entrada
     *
     * @param entrada una entrada
     * @throws UpdateException un error
     */
    @Override
    public void modifyEntrada(Entrada entrada) throws UpdateException {
        try {
            if (!em.contains(entrada)) {
                em.merge(entrada);
            }
            em.flush();
        } catch (Exception e) {
            throw new UpdateException(e.getMessage());
        }
    }

    /**
     * Método EJB para borrar una entrada
     *
     * @param entrada uan entrada
     * @throws DeleteException un error
     */
    @Override
    public void deleteEntrada(Entrada entrada) throws DeleteException {
        try {
            em.remove(em.merge(entrada));
        } catch (Exception e) {
            throw new DeleteException(e.getMessage());
        }
    }

    /**
     * Método EJB para poder ver todas las entradas
     *
     * @throws ReadException una excepcion
     */
    @Override
    public List<Entrada> viewAllEntradas() throws ReadException {
        List<Entrada> entrada = null;
        try {
            entrada
                    = em.createNamedQuery("verTodasLasEntradas").getResultList();
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
        return entrada;
    }

    /**
     * Método EJB para poder ver las entradas que tengan una fecha en concreto
     *
     * @param fecha una fecha
     * @throws ReadException un error
     */
    @Override
    public List<Entrada> viewEntradaByDate(Date fecha) throws ReadException {
        List<Entrada> entrada = null;
        try {
            entrada
                    = em.createNamedQuery("verEntradasporFecha").setParameter("fecha_entrada", fecha).getResultList();

        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
        return entrada;
    }

    /**
     * Método EJB para poder ver las entradas por el precio
     *
     * @param valor un valor
     * @throws ReadException una excpecion
     */
    @Override
    public List<Entrada> viewEntradaByPrice(Float valor) throws ReadException {
        List<Entrada> entrada = null;
        try {
            entrada
                    = em.createNamedQuery("verEntradasporPrecio").setParameter("precio", valor).getResultList();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return entrada;
    }

    /**
     * Método EJB para poder ver las entradas del cliente
     *
     * @param login el correo
     * @return entrada una entrada
     * @throws ReadException un error
     */
    @Override
    public List<Entrada> viewEntradaDeCliente(String login) throws ReadException {
        List<Entrada> entrada = null;
        try {
            entrada
                    = em.createNamedQuery("verEntradaCliente").setParameter("login", login).getResultList();
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
        return entrada;
    }

    /**
     * Método EJB para filtrar una entrada por su identificador (ID)
     *
     * @param id un id del cliente
     * @throws ReadException un error
     */
    @Override
    public Entrada filtrarEntradaPorID(Integer id) throws ReadException {
        Entrada entrada;
        try {
            entrada = em.find(Entrada.class, id);
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
        return entrada;
    }

}
