package Servicio;

import Entidades.Entrada;
import Entidades.Usuario;
import Excepciones.CreateException;
import Excepciones.DeleteException;
import Excepciones.ReadException;
import Excepciones.UpdateException;
import java.util.Date;
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
public class EntradaEJB implements EntradaIntefraz {

    @PersistenceContext(unitName = "Reto2_G3_ServidorPU")
    private EntityManager em;

    @Override
    public void createEntrada(Entrada entrada) throws CreateException {
        try {
            em.persist(entrada);
        } catch (Exception e) {
            throw new CreateException(e.getMessage());
        }
    }

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

    @Override
    public void deleteEntrada(Entrada entrada) throws DeleteException {
        try {
            em.remove(em.merge(entrada));
        } catch (Exception e) {
            throw new DeleteException(e.getMessage());
        }
    }

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

    @Override
    public List<Entrada> viewEntradaDeCliente(Usuario usuario) throws ReadException {
        List<Entrada> entrada = null;
        try {
            entrada
                    = em.createNamedQuery("verEntradaCliente").setParameter("login", usuario).getResultList();
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
        return entrada;
    }

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
