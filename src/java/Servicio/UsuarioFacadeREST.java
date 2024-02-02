/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicio;

import Entidades.Usuario;
import Excepciones.ReadException;
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
 * Fachada REST del usaurio
 *
 * @author Diego, Ander y Adrian
 */
@Path("entidades.usuario")
public class UsuarioFacadeREST extends AbstractFacade<Usuario> {

    @PersistenceContext(unitName = "Reto2_G3_ServidorPU")
    private EntityManager em;

    @EJB
    private UsuarioInterfaz usuarioInter;

    public UsuarioFacadeREST() {
        super(Usuario.class);
    }

    /**
     * Método POST que creea un usaurio
     *
     * @param usuario un usuario
     */
    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void createUsuario(Usuario usuario) {
        super.create(usuario);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Usuario entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    /**
     * Método GET que muestra usaurios por un correo y contraseña especificos
     *
     * @param login un correo
     * @param contraseña una contraseña
     * @return una lista
     */
    @GET
    @Path("VerUsuariosPorLoginyContra/{login}/{contraseña}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Usuario> find(@PathParam("login") String login, @PathParam("contraseña") String contraseña) {
        try {
            return usuarioInter.viewByLoginContraseña(login, contraseña);
        } catch (ReadException ex) {

            throw new InternalServerErrorException(ex.getMessage());
        }
    }

    /**
     * Método que muestra usaurios por correo especifico
     *
     * @param login un correo
     * @return una lista de usuarios
     */
    @GET
    @Path("VerUsuariosPorLogin/{login}/")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Usuario> findLogin(@PathParam("login") String login) {
        try {
            return usuarioInter.viewByLogin(login);
        } catch (ReadException ex) {

            throw new InternalServerErrorException(ex.getMessage());
        }
    }

    /**
     * Método que muestra todos los usuarios
     *
     * @return una lista
     */
    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Usuario> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Usuario> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    protected EntityManager getEntityManager() {
        return em;
    }

}
