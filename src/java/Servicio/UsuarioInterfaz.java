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
 * Interfaz del usuario
 *
 * @author Ander
 */
public interface UsuarioInterfaz {

    /**
     * Método que crea usuarios
     *
     * @param usuario un usuario
     * @throws CreateException una excepcion
     */
    public void createUsuario(Usuario usuario) throws CreateException;

    /**
     * Método que muestra usuarios por correo y contraseña
     *
     * @param login un correo
     * @param contraseña una contraseña
     * @return una lista
     * @throws ReadException un error
     */
    public List<Usuario> viewByLoginContraseña(String login, String contraseña) throws ReadException;

    /**
     * Método que muestra clientes por correo
     *
     * @param login un correo
     * @return una lista
     * @throws ReadException una excepcion
     */
    public List<Usuario> viewByLogin(String login) throws ReadException;
}
