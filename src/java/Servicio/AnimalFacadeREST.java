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
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.InternalServerErrorException;

/**
 *
 * @author Adrian
 */
@Path("entidades.animal")
public class AnimalFacadeREST {

    @PersistenceContext(unitName = "Reto2_G3_ServidorPU")
    private EntityManager em;
    
    private static final Logger LOGGER = Logger.getLogger("/Servicio/AnimalFacadeREST");

    @EJB
    private AnimalInterfaz ainter;
    
    public AnimalFacadeREST() {
        
    }

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Animal animal) {
        try {
            ainter.createAnimal(animal);
        } catch (CreateException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @PUT
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Animal animal) {
        try {
            ainter.updateAnimal(animal);
        } catch (UpdateException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @DELETE
    @Path("borrar/{id}")
    public void remove(@PathParam("id") Integer id) {
        try {
            ainter.deleteAnimal(ainter.filtrarAnimalPorID(id));
        } catch (DeleteException | ReadException e) {
            LOGGER.info(e.getMessage());
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @GET
    @Path("buscarPorID/{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Animal find(@PathParam("id") Integer id) {
        try {
            return ainter.filtrarAnimalPorID(id);
        } catch (ReadException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Animal> findAll() {
        try {
            return ainter.readAnimal();
        } catch (ReadException e) {
            System.out.println(e.getMessage());
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @GET
    @Path("visualizarAnimalesPorEspecie/{especie}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Animal> findAnimalsBySpecies(@PathParam("especie") String especie) {
        try {
            return ainter.viewAnimalByEspecie(especie);
        } catch (ReadException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }
    
    @GET
    @Path("visualizarAnimalesPorAlimentacion/{alimentacion}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Animal> findAnimalsByFeeding(@PathParam("alimentacion") Alimentacion alimentacion) {
        try {
            return ainter.viewAnimalByAlimentacion(alimentacion);
        } catch (ReadException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }
    
    @GET
    @Path("listarEspecies")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Animal> findSpecies() {
        try {
            return ainter.viewEspecies();
        } catch (ReadException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }
    
    @GET
    @Path("visualizarAnimalesDeUnaZona/{zona}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Animal> findAnimalsInAnArea(@PathParam("zona") Integer zona) {
        try {
            return ainter.viewAnimalesDeUnaZona(zona);
        } catch (ReadException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }
    
    /*
    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Animal> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        
    }
    */

    protected EntityManager getEntityManager() {
        return em;
    }
    
}