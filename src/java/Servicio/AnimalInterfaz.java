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
 *
 * @author Adrian
 */
public interface AnimalInterfaz {
    
    public void createAnimal(Animal animal) throws CreateException;
    
    public void deleteAnimal(Animal animal) throws DeleteException;
    
    public void updateAnimal(Animal animal) throws UpdateException;
    
    public List<Animal> readAnimal() throws ReadException;
    
    public List<Animal> viewAnimalByEspecie(String especie) throws ReadException;
    
    public List<Animal> viewAnimalByAlimentacion(Alimentacion alimentacion) throws ReadException;
    
    public List<Animal> viewEspecies() throws ReadException;
    
    public List<Animal> viewAnimalesDeUnaZona(Zona zona) throws ReadException;
    
    public Animal filtrarAnimalPorID(Integer id) throws ReadException;
}
