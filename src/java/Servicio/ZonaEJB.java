/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicio;

import Entidades.Zona;
import Excepciones.CreateException;
import Excepciones.DeleteException;
import Excepciones.ReadException;
import Excepciones.UpdateException;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * EJB de Zona
 *
 * @author Ander
 */
@Stateless
public class ZonaEJB implements ZonaInterfaz {

    @PersistenceContext(unitName = "Reto2_G3_ServidorPU")
    private EntityManager em;

    /**
     * Crea una nueva entidad Zona.
     *
     * @param zona La entidad Zona que se va a crear.
     * @throws CreateException Si ocurre un error durante el proceso de
     * creación.
     */
    @Override
    public void createZona(Zona zona) throws CreateException {
        //Metodo para  crear Zonas
        try {
            em.persist(zona);
        } catch (Exception e) {
            throw new CreateException(e.getMessage());
        }
    }

    /**
     * Recupera una entidad Zona mediante su identificador.
     *
     * @param idZona El identificador de la Zona que se va a recuperar.
     * @return La entidad Zona correspondiente al identificador proporcionado.
     * @throws ReadException Si ocurre un error durante el proceso de
     * recuperación.
     */
    @Override
    public Zona viewById(Integer idZona) throws ReadException {
        Zona zona;
        try {
            zona = em.find(Zona.class, idZona);
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
        return zona;
    }

    /**
     * Recupera una lista de todas las entidades Zona.
     *
     * @return Una lista de entidades Zona.
     * @throws ReadException Si ocurre un error durante el proceso de
     * recuperación.
     */
    @Override
    public List<Zona> viewAllZonas() throws ReadException {
        List<Zona> zonas;
        try {
            zonas = em.createNamedQuery("verTodasLasZonas").getResultList();
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
        return zonas;
    }

    /**
     * Recupera una lista de entidades Zona filtradas por nombre.
     *
     * @param nombreZona El nombre de las entidades Zona que se van a recuperar.
     * @return Una lista de entidades Zona que coinciden con el nombre
     * especificado.
     * @throws ReadException Si ocurre un error durante el proceso de
     * recuperación.
     */
    @Override
    public List<Zona> viewByNombre(String nombreZona) throws ReadException {
        List<Zona> zonas;
        try {
            zonas = em.createNamedQuery("filtrarPorZona").setParameter("nombre", nombreZona).getResultList();
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }

        return zonas;

    }

    /**
     * Recupera una lista de entidades Zona filtradas por el tipo de animal.
     *
     * @param tipoAnimal El tipoAnimal de las entidades Zona que se van a
     * recuperar.
     * @return Una lista de entidades Zona que coinciden con el tipoAnimal
     * especificado.
     * @throws ReadException Si ocurre un error durante el proceso de
     * recuperación.
     */
    @Override
    public List<Zona> viewByTipo(String tipoAnimal) throws ReadException {
        List<Zona> zonas;
        try {
            zonas = em.createNamedQuery("filtrarPorTipoAnimal").setParameter("tipo_animal", tipoAnimal).getResultList();
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }

        return zonas;
    }

    /**
     * Visualiza la zona asociada a una entrada específica.
     *
     * @param entrada Entrada de la zona que se desea visualizar.
     * @return La zona correspondiente a la entrada proporcionada.
     * @throws ReadException Si ocurre un error durante el proceso de
     * actualización.
     */
    @Override
    public Zona viewZonaByEntrada(String entrada) throws ReadException {
        Zona zona;
        try {

            zona = em.find(Zona.class, entrada);
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
        return zona;
    }

    /**
     * Actualiza una entidad Zona existente.
     *
     * @param zona La entidad Zona que se va a actualizar.
     * @throws UpdateException Si ocurre un error durante el proceso de
     * actualización.
     */
    @Override
    public void updateZona(Zona zona) throws UpdateException {
        try {
            if (!em.contains(zona)) {
                em.merge(zona);
            }
            em.flush();
        } catch (Exception e) {
            throw new UpdateException(e.getMessage());
        }
    }

    /**
     * Elimina una entidad Zona existente.
     *
     * @param zona La entidad Zona que se va a eliminar.
     * @throws DeleteException Si ocurre un error durante el proceso de
     * eliminación.
     */
    @Override
    public void deleteZona(Zona zona) throws DeleteException {
        try {
            em.remove(em.merge(zona));
        } catch (Exception e) {
            throw new DeleteException(e.getMessage());
        }
    }

}
