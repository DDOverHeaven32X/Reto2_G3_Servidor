/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicio;

import Entidades.Alimentacion;
import Entidades.Animal;
import Excepciones.CreateException;
import Excepciones.DeleteException;
import Excepciones.ReadException;
import Excepciones.UpdateException;
import java.util.List;
import java.util.logging.Level;
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
import javax.ws.rs.NotFoundException;

/**
 * Fachada RESTful del lado servidor de la entidad Animal
 *
 * @author Adrian
 */
@Path("entidades.animal")
public class AnimalFacadeREST {

    /**
     * Llamada a la persistencia y Entity Manager
     */
    @PersistenceContext(unitName = "Reto2_G3_ServidorPU")
    private EntityManager em;

    private static final Logger LOGGER = Logger.getLogger("/Servicio/AnimalFacadeREST");

    /**
     * Llamada a la interfaz de animal con su anotacion EJB
     */
    @EJB
    private AnimalInterfaz ainter;
    /**
     * Llamada a la interfaz de zona con su anotacion EJB
     */
    @EJB
    private ZonaInterfaz zinter;

    /**
     * Constructor vacio
     */
    public AnimalFacadeREST() {

    }

    /**
     * Metodo para insertar animales a la base de datos
     *
     * @param animal (Objeto animal)
     */
    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Animal animal) {
        try {
            ainter.createAnimal(animal);
        } catch (CreateException e) {
            LOGGER.log(Level.SEVERE, null, e);
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    /**
     * Metodo para modificar los datos de una animal de la base de datos
     *
     * @param id (Atributo primario de animal)
     * @param animal (Objeto Animal)
     */
    @PUT
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id_animal") Integer id, Animal animal) {
        try {
            ainter.updateAnimal(animal);
        } catch (UpdateException e) {
            LOGGER.log(Level.SEVERE, null, e);
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    /**
     * Metodo para eliminar animales de la base de datos
     *
     * @param id (Atributo primario de animal)
     */
    @DELETE
    @Path("borrar/{id_animal}")
    public void remove(@PathParam("id_animal") Integer id) {
        try {
            Animal animalToDelete = ainter.filtrarAnimalPorID(id);
            if (animalToDelete != null) {
                ainter.deleteAnimal(animalToDelete);
            } else {
                throw new NotFoundException("Animal con ID " + id + " no encontrado.");
            }
        } catch (DeleteException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            throw new InternalServerErrorException(ex.getMessage());
        } catch (ReadException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            throw new InternalServerErrorException(ex.getMessage());
        }
    }

    /**
     * Metodo para buscar los animales por el ID en la base de datos
     *
     * @param id (atributo primario de animal)
     * @return Animal (Objeto animal)
     */
    @GET
    @Path("buscarPorID/{id_animal}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Animal find(@PathParam("id_animal") Integer id) {
        try {
            return ainter.filtrarAnimalPorID(id);
        } catch (ReadException e) {
            LOGGER.log(Level.SEVERE, null, e);
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    /**
     * Metodo que te devuelve todos los animales de la base de datos
     *
     * @return Animal (Objeto animal)
     */
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Animal> findAll() {
        try {
            return ainter.readAnimal();
        } catch (ReadException e) {
            LOGGER.log(Level.SEVERE, null, e);
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    /**
     * Metodo para filtrar los animales por la especie
     *
     * @param especie (Atributo de animal)
     * @return Animal (Objeto animal)
     */
    @GET
    @Path("visualizarAnimalesPorEspecie/{especie}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Animal> findAnimalsBySpecies(@PathParam("especie") String especie) {
        try {
            return ainter.viewAnimalByEspecie(especie);
        } catch (ReadException e) {
            LOGGER.log(Level.SEVERE, null, e);
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    /**
     * Metodo para filtrar los animales por el tipo de alimentacion
     *
     * @param alimentacion (Atributo de animal)
     * @return Animal (Objeto animal)
     */
    @GET
    @Path("visualizarAnimalesPorAlimentacion/{alimentacion}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Animal> findAnimalsByFeeding(@PathParam("alimentacion") Alimentacion alimentacion) {
        try {
            return ainter.viewAnimalByAlimentacion(alimentacion);
        } catch (ReadException e) {
            LOGGER.log(Level.SEVERE, null, e);
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    /**
     * Metodo para listar todas las especies distintas que hay registradas en la
     * BD
     *
     * En lugar de devolver directamente objetos Animal desde el servicio,
     * creamos un DTO (Data Transfer Object) específico para la lista de
     * especies. Esto puede ayudar a evitar problemas de serialización.
     *
     * @return dto (Data Tranfer Object para listar especies de animales)
     */
    @GET
    @Path("listarEspecies")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public EspeciesDTO findSpecies() {
        try {
            List<String> especies = ainter.viewEspecies();
            EspeciesDTO dto = new EspeciesDTO(especies);
            return dto;
        } catch (ReadException e) {
            LOGGER.log(Level.SEVERE, null, e);
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    /**
     * Metodo para ver todos los animales de una zona seleccionada.
     *
     * Para la ejecucion del metodo es nacesario acceder a los metodos de zona
     * para saber cual ha sido seleccionada
     *
     * @param zona (Atributo zona del objeto Animal)
     * @return Animal (Objeto animal)
     */
    @GET
    @Path("visualizarAnimalesDeUnaZona/{zona}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Animal> findAnimalsInAnArea(@PathParam("zona") Integer zona) {
        try {
            return ainter.viewAnimalesDeUnaZona(zinter.viewById(zona));
        } catch (ReadException e) {
            LOGGER.log(Level.SEVERE, null, e);
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    /**
     * Getter del Entity Manager
     * 
     * @return em (devuelve un objeto Entity Manager) 
     */
    protected EntityManager getEntityManager() {
        return em;
    }

}
