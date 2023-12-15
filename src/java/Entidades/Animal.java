package Entidades;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Adrian.
 */
@Entity
@Table(name = "Animal", schema = "parquedb")
@NamedQueries({
    @NamedQuery(name = "visualizarAnimalesPorEspecie", query = "SELECT a FROM Animal a WHERE a.especie = :especie")
    ,
    @NamedQuery(name = "visualizarAnimalesPorAlimentacion", query = "SELECT a FROM Animal a WHERE a.alimentacion = :alimentacion")
    ,
    @NamedQuery(name = "listarEspecies", query = "SELECT DISTINCT a.especie FROM Animal a")
    ,
    @NamedQuery(name = "visualizarAnimalesDeUnaZona", query = "SELECT a FROM Animal a WHERE a.zona = :zona")
})
@XmlRootElement
public class Animal implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id_animal;
    private String nombre;
    private String genero;
    private Integer edad;
    private Float peso;
    private Float altura;
    private String especie;
    @Enumerated(EnumType.STRING)
    @Column(name = "alimentacion", insertable = false, updatable = false)
    private Alimentacion alimentacion;
    @Enumerated(EnumType.STRING)
    @Column(name = "salud", insertable = false, updatable = false)
    private Salud salud;

    @ManyToOne
    private Admin admin;
    @ManyToOne
    private Zona zona;

    public Integer getId_animal() {
        return id_animal;
    }

    public void setId_animal(Integer id_animal) {
        this.id_animal = id_animal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public Float getPeso() {
        return peso;
    }

    public void setPeso(Float peso) {
        this.peso = peso;
    }

    public Float getAltura() {
        return altura;
    }

    public void setAltura(Float altura) {
        this.altura = altura;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public Alimentacion getAlimentacion() {
        return alimentacion;
    }

    public void setAlimentacion(Alimentacion alimentacion) {
        this.alimentacion = alimentacion;
    }

    public Salud getSalud() {
        return salud;
    }

    public void setSalud(Salud salud) {
        this.salud = salud;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public Zona getZona() {
        return zona;
    }

    public void setZona(Zona zona) {
        this.zona = zona;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id_animal != null ? id_animal.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id_animal fields are not set
        if (!(object instanceof Animal)) {
            return false;
        }
        Animal other = (Animal) object;
        if ((this.id_animal == null && other.id_animal != null) || (this.id_animal != null && !this.id_animal.equals(other.id_animal))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Animal[ id=" + id_animal + " ]";
    }

}
