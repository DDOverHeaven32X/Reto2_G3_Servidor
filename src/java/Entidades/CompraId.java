/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;

/**
 * Clase enbeddable de Compra, alamcena las FK/PK de la clase compra
 *
 * @author Diego, Ander, Adrían
 */
@Embeddable
public class CompraId implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * Atributos de la subclase CompraId
     */
    private Integer id_user;
    private Integer id_entrada;

    public Integer getId_user() {
        return id_user;
    }

    public void setId_user(Integer id_user) {
        this.id_user = id_user;
    }

    public Integer getId_entrada() {
        return id_entrada;
    }

    public void setId_entrada(Integer id_entrada) {
        this.id_entrada = id_entrada;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.id_user);
        hash = 89 * hash + Objects.hashCode(this.id_entrada);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CompraId other = (CompraId) obj;
        if (!Objects.equals(this.id_user, other.id_user)) {
            return false;
        }
        if (!Objects.equals(this.id_entrada, other.id_entrada)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CompraID{" + "n_tarjeta=" + id_user + ", id_entrada=" + id_entrada + '}';
    }

}
