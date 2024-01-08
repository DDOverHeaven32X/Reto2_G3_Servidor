package Servicio;

import Entidades.Entrada;
import Excepciones.CreateException;
import Excepciones.DeleteException;
import Excepciones.ReadException;
import Excepciones.UpdateException;
import java.util.Date;
import java.util.List;

/**
 * Interfaz que trata todos los casos de uso de la entidad Entrada
 *
 * @author Diego.
 */
public interface EntradaIntefraz {

    /**
     * Método CRUD de crear una entrada
     *
     * @param entrada, instancia del objeto Entrada
     * @throws CreateException, Exepcion que salta si ocurre un error a la hora
     * de crear una entrada
     */
    public void createEntrada(Entrada entrada) throws CreateException;

    /**
     * Método CRUD de modificar una entrada
     *
     * @param entrada, instancia del objeto Entrada
     * @throws UpdateException, Exepcion que salta si ocurre un error a la hora
     * de modificar una entrada
     */
    public void modifyEntrada(Entrada entrada) throws UpdateException;

    /**
     * Método CRUD de borrar una entrada
     *
     * @param entrada, instancia del objeto Entrada
     * @throws DeleteException, Exepcion que salta si ocurre un error a la hora
     * de borrar una entrada
     */
    public void deleteEntrada(Entrada entrada) throws DeleteException;

    /**
     * Método para ver toda la información de las entradas
     *
     * @return entrada, devuelve una colecion de entradas
     * @throws ReadException, Exepcion que salta si ocurre un error a la hora de
     * leer una entrada
     */
    public List<Entrada> viewAllEntradas() throws ReadException;

    /**
     * Método que filtra las entradas por su identificador (ID)
     *
     * @param id, atributo del objeto Entrada
     * @return entrada, devuelve una colecion de entradas
     * @throws ReadException, Exepcion que salta si ocurre un error a la hora de
     * leer una entrada
     */
    public Entrada filtrarEntradaPorID(Integer id) throws ReadException;

    /**
     * Método que filtra las entradas por una fecha en concreto
     *
     * @param fecha, atributo del objeto Entrada
     * @return entrada, devuelve una colecion de entradas
     * @throws ReadException, Exepcion que salta si ocurre un error a la hora de
     * leer una entrada
     */
    public List<Entrada> viewEntradaByDate(Date fecha) throws ReadException;

    /**
     * Método que filtra las entradas por un valor monetario en concreto
     *
     * @param valor, atributo del objeto Entrada
     * @return entrada, devuelve una colecion de entradas
     * @throws ReadException, Exepcion que salta si ocurre un error a la hora de
     * leer una entrada
     */
    public List<Entrada> viewEntradaByPrice(Float valor) throws ReadException;

    /**
     * Método que muestra las entradas que ha comprado un cliente
     *
     * @param login, atributo del objeto Entrada
     * @return entrada, devuelve una colecion de entradas
     * @throws ReadException, Exepcion que salta si ocurre un error a la hora de
     * leer una entrada
     */
    public List<Entrada> viewEntradaDeCliente(String login) throws ReadException;

}
