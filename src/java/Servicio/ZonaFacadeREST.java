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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Fachada REST de Zona
 *
 * @author Ander
 */
@Path("entidades.zona")
public class ZonaFacadeREST {

    @PersistenceContext(unitName = "Reto2_G3_ServidorPU")
    private EntityManager em;

    @EJB
    private ZonaInterfaz zonaInter;

    public ZonaFacadeREST() {
        // super(Usuario.class);
    }

    /**
     * Crea una nueva zona a partir de datos en formato XML o JSON.
     *
     * @param zona La zona que se va a crear.
     * @throws InternalServerErrorException Si ocurre un error interno del
     * servidor durante la creación.
     *
     */
    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Zona zona) {
        try {
            zonaInter.createZona(zona);
        } catch (CreateException ex) {
            System.out.println(ex.getMessage());
            throw new InternalServerErrorException(ex.getMessage());
        }
    }

    /**
     * Actualiza una zona en formato XML o JSON a partir de un indentificador.
     *
     * @param idZona La id de la zona que se va a actualizar.
     * @param zona La zona que se va a actualizar.
     * @throws InternalServerErrorException Si ocurre un error interno del
     * servidor durante la creación.
     *
     */
    @PUT
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id_zona") Integer idZona, Zona zona) {
        try {
            zonaInter.updateZona(zona);
        } catch (UpdateException ex) {
            System.out.println(ex.getMessage());
            throw new InternalServerErrorException(ex.getMessage());
        }
    }

    /**
     * Elimina una zona en formato XML o JSON a partir de un identificardor.
     *
     * @param idZona La id de la zona que se va a eliminar.
     * @throws InternalServerErrorException Si ocurre un error interno del
     * servidor durante la creación.
     *
     */
    @DELETE
    @Path("deleteZona/{id_zona}")
    public void remove(@PathParam("id_zona") Integer idZona) {
        try {
            zonaInter.deleteZona(zonaInter.viewById(idZona));
        } catch (DeleteException ex) {
            throw new InternalServerErrorException(ex.getMessage());
        } catch (ReadException ex) {
            Logger.getLogger(ZonaFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Recupera una zona por su identificador.
     *
     * @param idZona Identificador de la zona.
     * @return La zona correspondiente al identificador.
     * @throws InternalServerErrorException Si hay un error interno del servidor
     * durante la operación.
     */
    @GET
    @Path("getId_zona/{id_zona}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Zona findById(@PathParam("id_zona") Integer idZona) {
        Zona zonas;
        try {
            return zonas = zonaInter.viewById(idZona);
        } catch (ReadException ex) {
            System.out.println(ex.getMessage());
            throw new InternalServerErrorException(ex.getMessage());
        }
    }

    /**
     * Recupera una lista de zonas por su nombre.
     *
     * @param nombreZona Nombre de la zona.
     * @return Lista de zonas con el nombre proporcionado.
     * @throws InternalServerErrorException Si hay un error interno del servidor
     * durante la operación.
     */
    @GET
    @Path("getNombre/{nombre}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Zona> findByNombre(@PathParam("nombre") String nombreZona) {
        List<Zona> zonas;
        try {
            return zonas = zonaInter.viewByNombre(nombreZona);
        } catch (ReadException ex) {
            System.out.println(ex.getMessage());
            throw new InternalServerErrorException(ex.getMessage());
        }
    }

    /**
     * Recupera una lista de zonas por el tipo de animal.
     *
     * @param tipoAnimal Tipo de animal de la zona.
     * @return Lista de zonas del tipo de animal especificado.
     * @throws InternalServerErrorException Si hay un error interno del servidor
     * durante la operación.
     */
    @GET
    @Path("getTipo_animal/{tipo_animal}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Zona> findByTipo(@PathParam("tipo_animal") String tipoAnimal) {
        List<Zona> zonas;
        try {
            return zonas = zonaInter.viewByTipo(tipoAnimal);
        } catch (ReadException ex) {
            System.out.println(ex.getMessage());
            throw new InternalServerErrorException(ex.getMessage());
        }
    }

    /**
     * Recupera todas las zonas disponibles.
     *
     * @return Lista de todas las zonas.
     * @throws InternalServerErrorException Si hay un error interno del servidor
     * durante la operación.
     */
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Zona> findAll() {
        try {
            return zonaInter.viewAllZonas();
        } catch (ReadException ex) {
            System.out.println(ex.getMessage());
            throw new InternalServerErrorException(ex.getMessage());
        }

    }

    /*

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Zona> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }*/
    /**
     * Es un getter que devuelve un objeto EnitityManager
     *
     * @return Devuelve un objeto EntityManager
     */
    protected EntityManager getEntityManager() {
        return em;
    }

}
