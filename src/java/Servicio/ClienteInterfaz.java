package Servicio;

import Entidades.Cliente;
import Excepciones.CreateException;
import Excepciones.ReadException;
import Excepciones.UpdateException;
import java.util.List;

/**
 *
 * @author Diego, Adrian.
 */
public interface ClienteInterfaz {

    public void recuperarContra(Cliente cliente) throws UpdateException;

    public void createEntrada(Cliente cliente) throws CreateException;

    public List<Cliente> viewAllClientes() throws ReadException;

    public List<Cliente> BankCredential(Long nTarjeta, Integer pines) throws ReadException;

}
