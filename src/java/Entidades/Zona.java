package Entidades;

import java.io.Serializable;
import java.util.Set;
import static javax.persistence.CascadeType.ALL;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Ander.
 */
@Entity
@Table(name = "Zona", schema = "parquedb")
@NamedQueries({
    /**
     * Query que lista todas las zonas disponibles.
     */
    @NamedQuery(name = "verTodasLasZonas", query = "SELECT z FROM Zona z ORDER BY z.id_zona ASC")
    ,
    /**
     * Query que filtra las zonas por nombre.
     */
    @NamedQuery(name = "filtrarPorZona", query = "SELECT z FROM Zona z WHERE z.nombre = :nombre")
    ,
    /**
     * Query que filtra las zonas por el tipo animal.
     */
    @NamedQuery(name = "filtrarPorTipoAnimal",
            query = "SELECT z FROM Zona z WHERE z.tipo_animal = :tipo_animal")

})

@XmlRootElement
public class Zona implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    // Identificador único de la zona
    private Integer id_zona;
    //Atributos
    @Column(nullable = false)
    private String nombre;
    private String tipo_animal;
    private String descripcion;
    @OneToMany(cascade = ALL, mappedBy = "zona")
    private Set<Animal> listaAnimales;
    @ManyToOne
    private Admin admin;
    @ManyToMany(fetch = FetchType.EAGER, cascade = ALL)
    @JoinTable(name = "pertenece", schema = "parquedb")
    private Set<Entrada> listaEntradas;

    public Integer getId_zona() {
        return id_zona;
    }

    public void setId_zona(Integer id_zona) {
        this.id_zona = id_zona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo_animal() {
        return tipo_animal;
    }

    public void setTipo_animal(String tipo_animal) {
        this.tipo_animal = tipo_animal;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public Set<Animal> getListaAnimales() {
        return listaAnimales;
    }

    public void setListaAnimales(Set<Animal> listaAnimales) {
        this.listaAnimales = listaAnimales;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    @XmlTransient
    public Set<Entrada> getListaEntradas() {
        return listaEntradas;
    }

    public void setListaEntradas(Set<Entrada> listaEntradas) {
        this.listaEntradas = listaEntradas;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id_zona != null ? id_zona.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Zona)) {
            return false;
        }
        Zona other = (Zona) object;
        if ((this.id_zona == null && other.id_zona != null) || (this.id_zona != null && !this.id_zona.equals(other.id_zona))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Zona[ id=" + id_zona + " ]";
    }

}
