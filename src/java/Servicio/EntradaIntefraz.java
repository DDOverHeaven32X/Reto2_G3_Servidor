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
     * @param entrada una entrada
     * @throws CreateException un error
     */
    public void createEntrada(Entrada entrada) throws CreateException;

    /**
     * Método CRUD de modificar una entrada
     *
     * @param entrada una entrada
     * @throws UpdateException un error
     */
    public void modifyEntrada(Entrada entrada) throws UpdateException;

    /**
     * Método CRUD de borrar una entrada
     *
     * @param entrada una entrada
     * @throws DeleteException un error
     */
    public void deleteEntrada(Entrada entrada) throws DeleteException;

    /**
     * Método para ver toda la información de las entradas
     *
     * @return entrada una entrada
     * @throws ReadException un error
     */
    public List<Entrada> viewAllEntradas() throws ReadException;

    /**
     * Método que filtra las entradas por su identificador (ID)
     *
     * @param id id del la entrada
     * @return entrada una entrada
     * @throws ReadException excepcion
     */
    public Entrada filtrarEntradaPorID(Integer id) throws ReadException;

    /**
     * Método que filtra las entradas por una fecha en concreto
     *
     * @param fecha una fecha
     * @return entrada una entrada
     * @throws ReadException un error
     */
    public List<Entrada> viewEntradaByDate(Date fecha) throws ReadException;

    /**
     * Método que filtra las entradas por un valor monetario en concreto
     *
     * @param valor un valor
     * @return entrada una entrada
     * @throws ReadException un error
     */
    public List<Entrada> viewEntradaByPrice(Float valor) throws ReadException;

    /**
     * Método que muestra las entradas que ha comprado un cliente
     *
     * @param login un correo
     * @return entrada una entrada
     * @throws ReadException un error
     */
    public List<Entrada> viewEntradaDeCliente(String login) throws ReadException;

}
