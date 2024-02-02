package Servicio;

import Entidades.Cliente;
import Excepciones.CreateException;
import Excepciones.ReadException;
import Excepciones.UpdateException;
import java.util.List;

/**
 * Interfaz del cliente
 *
 * @author Diego, Adrian.
 */
public interface ClienteInterfaz {

    /**
     * Método que recupera la contraseña
     *
     * @param cliente un cliente
     * @throws UpdateException devuelve un error
     */
    public void recuperarContra(Cliente cliente) throws UpdateException;

    /**
     * Método que cambia la contraseña
     *
     * @param cliente un cliente
     * @throws UpdateException devuelve un error
     */
    public void cambiarContra(Cliente cliente) throws UpdateException;

    /**
     * Método que crea el cliente
     *
     * @param cliente un cliente
     * @throws CreateException devuelve un error
     */
    public void createClient(Cliente cliente) throws CreateException;

    /**
     * Método que muestra los clientes
     *
     * @return una lista de clientes
     * @throws ReadException devuelve un error
     */
    public List<Cliente> viewAllClientes() throws ReadException;

    /**
     * Método que filtra el cliente por id
     *
     * @param id id del cliente
     * @return un cliente
     * @throws ReadException devuelve un error
     */
    public Cliente filtrarClientePorID(Integer id) throws ReadException;

    /**
     * Método que muestra los clientes con cierta tarjeta y pin
     *
     * @param nTarjeta tarjeta del cliente
     * @param pines pin de seguridad del cliente
     * @return una lista de clientes
     * @throws ReadException devuelve un error
     */
    public List<Cliente> BankCredential(Long nTarjeta, Integer pines) throws ReadException;

}
