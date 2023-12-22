package Servicio;

import Entidades.Entrada;
import Entidades.Usuario;
import Excepciones.CreateException;
import Excepciones.DeleteException;
import Excepciones.ReadException;
import Excepciones.UpdateException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

/**
 * Fachada RESTful del lado servidor de la entidad Entrada
 *
 * @author Diego.
 */
@Path("entidades.entrada")
public class EntradaFacadeREST {

    /**
     * LLamada a la persistencia y al Entity manager
     */
    @PersistenceContext(unitName = "Reto2_G3_ServidorPU")
    private EntityManager em;

    private static final Logger LOGGER = Logger.getLogger("/Servicio/EntradaFacadeREST");
    /**
     * Llamada a la interfaz de entrada con su anotación EJB
     */
    @EJB
    private EntradaIntefraz entInter;

    /**
     * Constructor vacio de la fachada RESTful
     */
    public EntradaFacadeREST() {

    }

    /**
     * Método POST de entrada
     *
     * @param entrada
     */
    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Entrada entrada) {
        try {
            entInter.createEntrada(entrada);
        } catch (CreateException e) {
            throw new InternalServerErrorException(e.getMessage());
        }

    }

    /**
     * Método PUT de entrada
     *
     * @param id
     * @param entrada
     */
    @PUT
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Entrada entrada) {
        try {
            entInter.modifyEntrada(entrada);
        } catch (UpdateException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    /**
     * Método DELETE de entrada
     *
     * @param id
     */
    @DELETE
    @Path("borrarEntrada/{id}")
    public void remove(@PathParam("id") Integer id) {
        try {
            entInter.deleteEntrada(entInter.filtrarEntradaPorID(id));
        } catch (DeleteException | ReadException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    /**
     * Método GET para filtrar entradas por su identificador (ID)
     *
     * @param id
     * @return Entrada
     */
    @GET
    @Path("buscarEntradaPorId/{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Entrada find(@PathParam("id") Integer id) {
        try {
            return entInter.filtrarEntradaPorID(id);
        } catch (ReadException e) {
            throw new InternalServerErrorException(e.getMessage());

        }
    }

    /**
     * Método GET para ver todas las entradas
     *
     * @return Entrada
     */
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Entrada> findAll() {
        LOGGER.info("Mostrando todas las entradas");
        try {
            return entInter.viewAllEntradas();
        } catch (ReadException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    /**
     * Método GET para ver las entradas por una fecha en concreto
     *
     * @param fechaCon
     * @return Entrada
     */
    @GET
    @Path("verEntradasporFecha/{fechaCon}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Entrada> filtrarEntradaPorFecha(@PathParam("fechaCon") String fechaCon) {

        LOGGER.info("Mostrando fecha por usuario");

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = formatter.parse(fechaCon);
            return entInter.viewEntradaByDate(date);

        } catch (ReadException | ParseException e) {
            System.out.println(e.getMessage());
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    /**
     * Método GET para ver las entradas por un precio en especifico
     *
     * @param precio
     * @return Entrada
     */
    @GET
    @Path("verEntradasporPrecio/{precio}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Entrada> filtrarEntradaPorPrecio(@PathParam("precio") Float precio) {

        LOGGER.info("Mostrando entradas por precio");
        try {
            return entInter.viewEntradaByPrice(precio);
        } catch (ReadException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    /**
     * Método GET que muestra las entradas que han sido compradas por un cliente
     *
     * @param login
     * @return
     */
    @GET
    @Path("verEntradaCliente/{login}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Entrada> filtrarEntradaDeUsuario(@PathParam("login") String login) {

        LOGGER.info("Mostrando entradas de usuario");
        try {
            return entInter.viewEntradaDeCliente(login);
        } catch (ReadException e) {
            System.out.println(e.getMessage());
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    protected EntityManager getEntityManager() {
        return em;
    }

}
