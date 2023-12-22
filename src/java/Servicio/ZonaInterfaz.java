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

    public void createZona(Zona zona) throws CreateException;

    public Zona viewById(Integer idZona) throws ReadException;

    public List<Zona> viewAllZonas() throws ReadException;

    public List<Zona> viewByNombre(String nombreZona) throws ReadException;

    public List<Zona> viewByTipo(String tipoAnimal) throws ReadException;

    public Zona viewZonaByEntrada(String entrada) throws ReadException;

    public void updateZona(Zona zona) throws UpdateException;

    public void deleteZona(Zona zona) throws DeleteException;
}
