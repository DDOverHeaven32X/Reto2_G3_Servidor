package Servicio;

import Entidades.Entrada;
import Entidades.Usuario;
import Excepciones.CreateException;
import Excepciones.DeleteException;
import Excepciones.ReadException;
import Excepciones.UpdateException;
import java.util.Date;
import java.util.List;
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
 *
 * @author Diego.
 */
@Path("entidades.entrada")
public class EntradaFacadeREST {

    @PersistenceContext(unitName = "Reto2_G3_ServidorPU")
    private EntityManager em;

    @EJB
    private EntradaIntefraz entInter;

    public EntradaFacadeREST() {

    }

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Entrada entrada) {
        try {
            entInter.createEntrada(entrada);
        } catch (CreateException e) {
            throw new InternalServerErrorException(e.getMessage());
        }

    }

    @PUT
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Entrada entrada) {
        try {
            entInter.modifyEntrada(entrada);
        } catch (UpdateException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @DELETE
    @Path("borrarEntrada/{id}")
    public void remove(@PathParam("id") Integer id) {
        try {
            entInter.deleteEntrada(entInter.filtrarEntradaPorID(id));
        } catch (DeleteException | ReadException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

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

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Entrada> findAll() {
        try {
            return entInter.viewAllEntradas();
        } catch (ReadException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @GET
    @Path("getFecha_entrada/{fechaCon}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Entrada> filtrarEntradaPorFecha(@PathParam("fechaCon") Date fechaCon) {
        try {
            return entInter.viewEntradaByDate(fechaCon);
        } catch (ReadException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    /*@GET
    @Path("getPrecio/{precio}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Entrada> filtrarEntradaPorPrecio(@PathParam("Float") Float precio) {
        try {
            return entInter.viewEntradaByPrice(precio);
        } catch (ReadException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @GET
    @Path("{login}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Entrada> filtrarEntradaDeUsuario(@PathParam("Usuario") Usuario usuario) {
        try {
            return entInter.viewEntradaDeCliente(usuario);
        } catch (ReadException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }*/
    protected EntityManager getEntityManager() {
        return em;
    }

}
