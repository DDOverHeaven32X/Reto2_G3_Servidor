/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicio;

import Entidades.Cliente;
import Excepciones.CreateException;
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
 * Fachada REST del cliente
 *
 * @author Diego, Ander, Adrian
 */
@Path("entidades.cliente")
public class ClienteFacadeREST {

    @PersistenceContext(unitName = "Reto2_G3_ServidorPU")
    private EntityManager em;

    private static final Logger LOGGER = Logger.getLogger("/Servicio/ClienteFacadeREST");

    @EJB
    private ClienteInterfaz clieEJB;

    public ClienteFacadeREST() {

    }

    /**
     * Método POST de crear un cliente nuevo
     *
     * @param cliente un cliente
     */
    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Cliente cliente) {

        try {
            clieEJB.createClient(cliente);
        } catch (CreateException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Cliente entity) {
        //super.edit(entity);
    }

    /**
     * Método PUT para modificar la contraseña del cliente
     *
     * @param cliente un cliente
     */
    @PUT
    @Path("recuperarContra")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void RecuperarContra(Cliente cliente) {
        try {
            clieEJB.recuperarContra(cliente);
        } catch (UpdateException e) {
            LOGGER.log(Level.SEVERE, null, e);
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    /**
     * Método PUT encargado del cambio de contraseña
     *
     * @param cliente un cliente
     */
    @PUT
    @Path("cambiarContra")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void cambiarContra(Cliente cliente) {
        try {
            clieEJB.cambiarContra(cliente);
        } catch (UpdateException e) {
            LOGGER.log(Level.SEVERE, null, e);
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        //super.remove(super.find(id));
    }

    /**
     * Método GET que busca un cliente por ID
     *
     * @param id id del cliente
     * @return un cliente
     */
    @GET
    @Path("buscarClientePorId/{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Cliente find(@PathParam("id") Integer id) {
        try {
            return clieEJB.filtrarClientePorID(id);
        } catch (ReadException e) {
            throw new InternalServerErrorException(e.getMessage());

        }
    }

    /**
     * Método GET que muestra todos los clientes
     *
     * @return un cliente
     */
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Cliente> findAll() {
        LOGGER.info("Mostrando todas las clientes");
        try {
            return clieEJB.viewAllClientes();
        } catch (ReadException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    /**
     * Método GET que muestra los clientes con tarjeta y pin especificos
     *
     * @param nTarjeta tarjeta del cliente
     * @param pines pin de seguridad del cliente
     * @return una lista de clientes
     */
    @GET
    @Path("verClientesPorBanco/{n_tarjeta}/{pin}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Cliente> filtrarPorTarjeta(@PathParam("n_tarjeta") Long nTarjeta, @PathParam("pin") Integer pines) {

        LOGGER.info("Mostrando clientes por credencial bancaria");
        try {
            return clieEJB.BankCredential(nTarjeta, pines);
        } catch (ReadException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Cliente> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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

    /**
     * Instancia entity manager
     *
     * @return em
     */
    protected EntityManager getEntityManager() {
        return em;
    }

}
