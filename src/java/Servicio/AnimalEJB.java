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
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Adrian
 */
@Stateless
public class AnimalEJB implements AnimalInterfaz{

    @PersistenceContext(unitName = "Reto2_G3_ServidorPU")
    private EntityManager em;
    
    @Override
    public void createAnimal(Animal animal) throws CreateException {
        try {
            em.persist(animal);
        } catch(Exception e) {
            throw new CreateException(e.getMessage());
        }
    }

    @Override
    public void deleteAnimal(Animal animal) throws DeleteException {
        try {
            em.remove(em.merge(animal));
        } catch (Exception e) {
            throw new DeleteException(e.getMessage());
        }
    }

    @Override
    public void updateAnimal(Animal animal) throws UpdateException {
        try {
            if(!em.contains(animal))
                em.merge(animal);
            em.flush();
        } catch (Exception e) {
            throw new UpdateException(e.getMessage());
        }
    }

    @Override
    public List<Animal> readAnimal() throws ReadException {
        List<Animal> animales=null;
        try {
            animales = em.createNamedQuery("visualizarAnimales").getResultList();
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
        return animales;
    }

    @Override
    public List<Animal> viewAnimalByEspecie(String especie) throws ReadException {
        List<Animal> animales=null;
        try {
            animales = em.createNamedQuery("visualizarAnimalesPorEspecie").setParameter("especie", especie).getResultList();
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
        return animales;
    }

    @Override
    public List<Animal> viewAnimalByAlimentacion(Alimentacion alimentacion) throws ReadException {
        List<Animal> animales=null;
        try {
            animales = em.createNamedQuery("visualizarAnimalesPorAlimentacion").setParameter("alimentacion", alimentacion).getResultList();
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
        return animales;}

    @Override
    public List<Animal> viewEspecies() throws ReadException {
        List<Animal> animales=null;
        try {
            animales = em.createNamedQuery("listarEspecies").getResultList();
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
        return animales;
    }

    @Override
    public List<Animal> viewAnimalesDeUnaZona(Integer zona) throws ReadException {
        List<Animal> animales=null;
        try {
            animales = em.createNamedQuery("visualizarAnimalesDeUnaZona").setParameter("zona", zona).getResultList();
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
        return animales;
    }

    @Override
    public Animal filtrarAnimalPorID(Integer id) throws ReadException {
        Animal animal=null;
        try {
            animal = em.find(Animal.class, id);
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
        return animal;
    }
    
}
