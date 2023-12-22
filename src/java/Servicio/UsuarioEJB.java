/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicio;

import Entidades.Usuario;
import Entidades.Zona;
import Excepciones.CreateException;
import Excepciones.ReadException;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author 2dam
 */
@Stateless
public class UsuarioEJB implements UsuarioInterfaz {

    @PersistenceContext(unitName = "Reto2_G3_ServidorPU")
    private EntityManager em;

    @Override
    public void createUsuario(Usuario usuario) throws CreateException {
        if (usuario.getId_user() == null) {
            em.merge(usuario);
        }
        em.persist(usuario);
    }

    @Override
    public List<Usuario> viewByLoginContraseña(String login, String contraseña) throws ReadException {
        try {
            return em.createNamedQuery("VerUsuariosPorLoginyContra").setParameter("login", login).setParameter("contraseña", contraseña).getResultList();
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
    }

}
