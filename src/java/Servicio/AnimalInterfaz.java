/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicio;

import Entidades.Alimentacion;
import Entidades.Animal;
import Entidades.Zona;
import Excepciones.CreateException;
import Excepciones.DeleteException;
import Excepciones.ReadException;
import Excepciones.UpdateException;
import java.util.List;

/**
 * Interfaz que trata todos los casos de uso de la entidad entrada
 * 
 * @author Adrian
 */
public interface AnimalInterfaz {
    
    /**
     * Metodo CRUD para crear un animal
     * 
     * @param animal (Instancia del objeto Animal)
     * @throws CreateException (Excepcion al ocurrir un error en la creacion de un animal)
     */
    public void createAnimal(Animal animal) throws CreateException;
    
    /**
     * Metodo CRUD para borrar un animal
     * 
     * @param animal (Instancia del objeto Animal)
     * @throws DeleteException (Excepcion al ocurrir un error en el borrado de un animal)
     */
    public void deleteAnimal(Animal animal) throws DeleteException;
    
    /**
     * Metodo CRUD para editar un animal
     * 
     * @param animal (Instancia del objeto Animal)
     * @throws UpdateException (Excepcion al ocurrir un error al editar un animal)
     */
    public void updateAnimal(Animal animal) throws UpdateException;
    
    /**
     * Metodo CRUD para la lectura de todos los animales
     * 
     * @return animal (devuelve una coleccion de animales)
     * @throws ReadException (Excepcion al ocurrir un error en la lectura de un animal)
     */
    public List<Animal> readAnimal() throws ReadException;
    
    /**
     * Metodo para visualizar animales por la especie
     * 
     * @param especie (atributo de animal)
     * @return animal (devuelve una coleccion de animales)
     * @throws ReadException (Excepcion al ocurrir un error en la lectura de un animal)
     */
    public List<Animal> viewAnimalByEspecie(String especie) throws ReadException;
    
    /**
     * Metodo para visualizar animales por el tipo de alimentacion
     * 
     * @param alimentacion (atributo de animal)
     * @return animal (devuelve una coleccion de animales)
     * @throws ReadException (Excepcion al ocurrir un error en la lectura de un animal)
     */
    public List<Animal> viewAnimalByAlimentacion(Alimentacion alimentacion) throws ReadException;
    
    /**
     * Metodo para visulizar las distintas especies
     * 
     * @return especies (devuelve una coleccion de epecies)
     * @throws ReadException (Excepcion al ocurrir un error en la lectura de las especies)
     */
    public List<String> viewEspecies() throws ReadException;
    
    /**
     * Metodo para visualizar los animales de una determinada zona
     * 
     * @param zona (Objeto zona)
     * @return animal (devuelve una coleccion de animales)
     * @throws ReadException (Excepcion al ocurrir un error en la lectura de un animal)
     */
    public List<Animal> viewAnimalesDeUnaZona(Zona zona) throws ReadException;
    
    /**
     * Metodo para filtrar los animales por id
     * 
     * @param id (atirbuto primario de animal)
     * @return animal (devuelve una coleccion de animales)
     * @throws ReadException (Excepcion al ocurrir un error en la lectura de un animal)
     */
    public Animal filtrarAnimalPorID(Integer id) throws ReadException;
}
