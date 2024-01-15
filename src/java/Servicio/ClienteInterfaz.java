package Servicio;

import Entidades.Cliente;
import Excepciones.UpdateException;

/**
 *
 * @author Diego, Adrian.
 */
public interface ClienteInterfaz {

    public void recuperarContra(Cliente cliente) throws UpdateException;

}
