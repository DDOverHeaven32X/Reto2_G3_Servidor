/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicio;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Esta es una clase simple de Transferencia de Datos (DTO) que encapsula la
 * información sobre las especies.
 *
 * @author Adrian
 */
@XmlRootElement
public class EspeciesDTO {

    private List<String> especies;

    public EspeciesDTO(List<String> especies) {
        this.especies = especies;
    }

    public EspeciesDTO() {
    }

    public List<String> getEspecies() {
        return especies;
    }

    public void setEspecies(List<String> especies) {
        this.especies = especies;
    }

}
