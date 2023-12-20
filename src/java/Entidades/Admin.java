package Entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Entidad POJO de admin, hereda de la entidad USUARIO
 *
 * @author Diego.
 */
@Entity
@DiscriminatorValue("ADMIN")
@XmlRootElement
public class Admin extends Usuario {

    private static final long serialVersionUID = 1L;

    @Temporal(TemporalType.DATE)
    private Date fecha_creacion;
    @OneToMany(mappedBy = "admin")
    private Set<Animal> listaAnimales;
    @OneToMany(mappedBy = "admin")
    private Set<Zona> listaZonas;
    @OneToMany(mappedBy = "admin")
    private Set<Entrada> listaEntrada;

    public Date getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(Date fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    @XmlTransient
    public Set<Animal> getListaAnimales() {
        return listaAnimales;
    }

    public void setListaAnimales(Set<Animal> listaAnimales) {
        this.listaAnimales = listaAnimales;
    }

    @XmlTransient
    public Set<Zona> getListaZonas() {
        return listaZonas;
    }

    public void setListaZonas(Set<Zona> listaZonas) {
        this.listaZonas = listaZonas;
    }

    @XmlTransient
    public Set<Entrada> getListaEntrada() {
        return listaEntrada;
    }

    public void setListaEntrada(Set<Entrada> listaEntrada) {
        this.listaEntrada = listaEntrada;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fecha_creacion != null ? fecha_creacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Admin)) {
            return false;
        }
        Admin other = (Admin) object;
        if ((this.fecha_creacion == null && other.fecha_creacion != null) || (this.fecha_creacion != null && !this.fecha_creacion.equals(other.fecha_creacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Admin[ id=" + fecha_creacion + " ]";
    }

}
