/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicio;

import Entidades.Compra;
import Entidades.CompraId;
import Excepciones.CreateException;
import Excepciones.ReadException;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
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
import javax.ws.rs.core.PathSegment;

/**
 * Fachada REST de compra
 *
 * @author Diego
 */
@Path("entidades.compra")
public class CompraFacadeREST {

    @PersistenceContext(unitName = "Reto2_G3_ServidorPU")
    private EntityManager em;

    @EJB
    private CompraInterfaz comInter;

    private static final Logger LOGGER = Logger.getLogger("/Servicio/CompraFacadeREST");

    private CompraId getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;id_user=id_userValue;id_entrada=id_entradaValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        Entidades.CompraId key = new Entidades.CompraId();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> id_user = map.get("id_user");
        if (id_user != null && !id_user.isEmpty()) {
            key.setId_user(new java.lang.Integer(id_user.get(0)));
        }
        java.util.List<String> id_entrada = map.get("id_entrada");
        if (id_entrada != null && !id_entrada.isEmpty()) {
            key.setId_entrada(new java.lang.Integer(id_entrada.get(0)));
        }
        return key;
    }

    public CompraFacadeREST() {
        //super(Compra.class);
    }

    /**
     * Método que permite crear una compra
     *
     * @param compra una compra
     */
    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Compra compra) {
        //super.create(entity);
        try {
            comInter.createEntrada(compra);
        } catch (CreateException e) {
            throw new InternalServerErrorException(e.getMessage());
        }

    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") PathSegment id, Compra entity) {
        //super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        Entidades.CompraId key = getPrimaryKey(id);
        //super.remove(super.find(key));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Compra find(@PathParam("id") PathSegment id) {
        Entidades.CompraId key = getPrimaryKey(id);
        //return super.find(key);
        return null;
    }

    /**
     * Método que muestra todas las compras realizadas en la app
     *
     * @return una lista
     */
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Compra> findAll() {
        LOGGER.info("Mostrando todas las compras");
        try {
            return comInter.viewAllCompras();
        } catch (ReadException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Compra> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        //return super.findRange(new int[]{from, to});
        return null;
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        //return String.valueOf(super.count());
        return null;
    }

    protected EntityManager getEntityManager() {
        return em;
    }

}
