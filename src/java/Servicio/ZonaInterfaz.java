/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicio;

import Entidades.Zona;
import Excepciones.CreateException;
import Excepciones.DeleteException;
import Excepciones.ReadException;
import Excepciones.UpdateException;
import java.util.List;

/**
 *
 * @author Ander
 */
public interface ZonaInterfaz {

    /**
     * Crea una nueva zona a partir de datos en formato XML o JSON.
     *
     * @param zona Objeto de la zona que se va a crear.
     * @throws CreateException
     */
    public void createZona(Zona zona) throws CreateException;

    /**
     * Visualiza una zona por su identificador.
     *
     * @param idZona Identificador de la zona que se quiere mostrar.
     * @return Zona cuyo identificador sea el introducido.
     * @throws ReadException
     */
    public Zona viewById(Integer idZona) throws ReadException;

    /**
     * Visualiza todas las zonas disponibles.
     *
     * @return Lista de todas las zonas existentes.
     * @throws ReadException
     */
    public List<Zona> viewAllZonas() throws ReadException;

    /**
     * Visualiza una lista de zonas por su nombre.
     *
     * @param nombreZona Nombre de la zona que se desea buscar.
     * @return Lista de zonas con el nombre especificado.
     * @throws ReadException
     */
    public List<Zona> viewByNombre(String nombreZona) throws ReadException;

    /**
     * Visualiza una lista de zonas por el tipo de animal.
     *
     * @param tipoAnimal Tipo de animal de las zonas que se buscan.
     * @return Lista de zonas del tipo de animal especificado.
     * @throws ReadException
     */
    public List<Zona> viewByTipo(String tipoAnimal) throws ReadException;

    /**
     * Visualiza una zona por su entrada.
     *
     * @param entrada Entrada de la zona que se desea buscar.
     * @return La zona asociada a la entrada proporcionada.
     * @throws ReadException
     */
    public Zona viewZonaByEntrada(String entrada) throws ReadException;

    /**
     * Actualiza la información de una zona existente.
     *
     * @param zona Zona con la información actualizada.
     * @throws UpdateException
     */
    public void updateZona(Zona zona) throws UpdateException;

    /**
     * Elimina una zona existente.
     *
     * @param zona Zona que se desea eliminar.
     * @throws DeleteException
     */
    public void deleteZona(Zona zona) throws DeleteException;
}
