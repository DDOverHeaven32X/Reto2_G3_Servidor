/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicio;

import Entidades.Alimentacion;
import Entidades.Animal;
import Entidades.Zona;
import Excepciones.CreateException;
import Excepciones.DeleteException;
import Excepciones.ReadException;
import Excepciones.UpdateException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.NotFoundException;

/**
 * Clase EJB con los métodos de la interfaz de animal
 *
 * @author Adrian
 */
@Stateless
public class AnimalEJB implements AnimalInterfaz {

     /**
     * Instanciamos la persistencia del proyecto
     */
    @PersistenceContext(unitName = "Reto2_G3_ServidorPU")
    private EntityManager em;

    private static final Logger LOGGER = java.util.logging.Logger.getLogger("/Servicio/AnimalEJB");

    /**
     * Metodo EJB para crear un animal
     * 
     * @param animal (objeto animal)
     * @throws CreateException (Excepcion al ocurrir un error en la creacion de un animal)
     */
    @Override
    public void createAnimal(Animal animal) throws CreateException {
        try {
            em.persist(animal);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
            throw new CreateException(e.getMessage());
        }
    }

    /**
     * Metodo EJB para eliminar un animal
     * 
     * @param animal (objeto animal)
     * @throws DeleteException (Excepcion al ocurrir un error en el borrado de un animal)
     */
    @Override
    public void deleteAnimal(Animal animal) throws DeleteException {
        try {
            Animal managedAnimal = em.find(Animal.class, animal.getId_animal());
            if (managedAnimal != null) {
                em.remove(managedAnimal);
            } else {
                throw new NotFoundException("El animal no existe en la base de datos.");
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
            throw new DeleteException(e.getMessage());
        }
    }

    /**
     * Metodo EJB para editar unn animal
     * 
     * @param animal (objeto animal)
     * @throws UpdateException (Excepcion al ocurrir un error en la edicion de un animal)
     */
    @Override
    public void updateAnimal(Animal animal) throws UpdateException {
        try {
            if (!em.contains(animal)) {
                em.merge(animal);
            }
            em.flush();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
            throw new UpdateException(e.getMessage());
        }
    }

    /**
     * Metodo EJB para leer todos los animales
     * 
     * @return animal (Objeto animal)
     * @throws ReadException (Excepcion al ocurrir un error en la lectura de un animal)
     */
    @Override
    public List<Animal> readAnimal() throws ReadException {
        List<Animal> animales = null;
        try {
            animales = em.createNamedQuery("visualizarAnimales").getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
            throw new ReadException(e.getMessage());
        }
        return animales;
    }

    /**
     * Metodo EJB para leer los animales de una especie
     * 
     * @param especie (Atributo de animal)
     * @return animal (Objeto animal)
     * @throws ReadException (Excepcion al ocurrir un error en la lectura de un animal)
     */
    @Override
    public List<Animal> viewAnimalByEspecie(String especie) throws ReadException {
        List<Animal> animales = null;
        try {
            animales = em.createNamedQuery("visualizarAnimalesPorEspecie").setParameter("especie", especie).getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
            throw new ReadException(e.getMessage());
        }
        return animales;
    }

    /**
     * Metodo EJB para leer los animales de un tipo de alimentacion
     * 
     * @param alimentacion (Atributo de animal)
     * @return animal (Objeto animal)
     * @throws ReadException (Excepcion al ocurrir un error en la lectura de un animal)
     */
    @Override
    public List<Animal> viewAnimalByAlimentacion(Alimentacion alimentacion) throws ReadException {
        List<Animal> animales = null;
        try {
            animales = em.createNamedQuery("visualizarAnimalesPorAlimentacion").setParameter("alimentacion", alimentacion).getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
            throw new ReadException(e.getMessage());
        }
        return animales;
    }

    /**
     * Metodo EJB para listar todas las especies diferentes de la BD
     * 
     * @return especies (coleccion de especies)
     * @throws ReadException (Excepcion al ocurrir un error en la lectura de una especie)
     */
    @Override
    public List<String> viewEspecies() throws ReadException {
        List<String> especies = null;
        try {
            especies = em.createNamedQuery("listarEspecies").getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
            throw new ReadException(e.getMessage());
        }
        return especies;
    }

    /**
     * Metodo EJB para listar los animales de una zona
     * 
     * @param zona (Objeto zona)
     * @return animal (devuelve una coleccion de animales)
     * @throws ReadException (Excepcion al ocurrir un error en la lectura de un animal)
     */
    @Override
    public List<Animal> viewAnimalesDeUnaZona(Zona zona) throws ReadException {
        List<Animal> animales = null;
        try {
            animales = em.createNamedQuery("visualizarAnimalesDeUnaZona").setParameter("zona", zona).getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
            throw new ReadException(e.getMessage());
        }
        return animales;
    }

    /**
     * Metodo EJB para filtrar los animales por id
     * 
     * @param id (atirbuto primario de animal)
     * @return animal (devuelve una coleccion de animales)
     * @throws ReadException (Excepcion al ocurrir un error en la lectura de un animal)
     */
    @Override
    public Animal filtrarAnimalPorID(Integer id) throws ReadException {
        Animal animal;
        try {
            animal = em.find(Animal.class, id);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
            throw new ReadException(e.getMessage());
        }
        return animal;
    }

}
