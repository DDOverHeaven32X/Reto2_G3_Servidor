/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicio;

import Entidades.Usuario;
import Excepciones.CreateException;
import Excepciones.ReadException;
import java.util.List;

/**
 *
 * @author Ander
 */
public interface UsuarioInterfaz {

    public void createUsuario(Usuario usuario) throws CreateException;

    public List<Usuario> viewByLoginContraseña(String login, String contraseña) throws ReadException;

    public List<Usuario> viewByLogin(String login) throws ReadException;
}
