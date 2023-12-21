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
 *
 * @author 2dam
 */
@Path("entidades.zona")
public class ZonaFacadeREST {

    @PersistenceContext(unitName = "Reto2_G3_ServidorPU")
    private EntityManager em;

    @EJB
    private ZonaInterfaz zonaInter;

    Zona zona = new Zona();

    public ZonaFacadeREST() {
        // super(Usuario.class);
    }

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
    protected EntityManager getEntityManager() {
        return em;
    }

}
