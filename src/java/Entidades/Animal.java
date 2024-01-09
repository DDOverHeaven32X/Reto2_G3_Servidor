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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entidad POJO de Animal
 * 
 * @author Adrian.
 */
@Entity
@Table(name = "Animal", schema = "parquedb")
/**
 * Lista de las distintas queris de Animal
 *
 */
@NamedQueries({
    /**
     * Query para visualizar Animales
     */
    @NamedQuery(name = "visualizarAnimales", query = "SELECT a FROM Animal a ORDER BY a.id_animal ASC")
    ,
    /**
     * Query para visualizar animales filtrados por especie 
     */
    @NamedQuery(name = "visualizarAnimalesPorEspecie", query = "SELECT a FROM Animal a WHERE a.especie = :especie")
    ,
    /**
     * Query para visualizar animales filtrados por alimentacion
     */
    @NamedQuery(name = "visualizarAnimalesPorAlimentacion", query = "SELECT a FROM Animal a WHERE a.alimentacion = :alimentacion")
    ,
    /**
     * Query para listar todas las especies diferentes que hayan en la BD
     */
    @NamedQuery(name = "listarEspecies", query = "SELECT DISTINCT a.especie FROM Animal a")
    ,
    /**
     * Query para visualizar animales de una zona 
     */
    @NamedQuery(name = "visualizarAnimalesDeUnaZona", query = "SELECT a FROM Animal a WHERE a.zona = :zona")
})
@XmlRootElement
public class Animal implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * Atributos de la clase Animal con sus respectivas anotaciones
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id_animal;
    private String nombre;
    @Column(nullable = true)
    private String genero;
    private Integer edad;
    private Float peso;
    private Float altura;
    private String especie;
    @Enumerated(EnumType.STRING)
    @Column(name = "alimentacion", nullable = false)
    private Alimentacion alimentacion;
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "salud", nullable = false)
    private Salud salud;

    @ManyToOne
    private Admin admin;
    @NotNull
    @ManyToOne
    private Zona zona;

    /**
     * Getter del id del animal
     * 
     * @return id_animal
     */
    public Integer getId_animal() {
        return id_animal;
    }

    /**
     * Setter del id del animal
     * 
     * @param id_animal 
     */
    public void setId_animal(Integer id_animal) {
        this.id_animal = id_animal;
    }

    /**
     * Getter del nombre de animal
     * 
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Setter del nombre de animal
     * 
     * @param nombre 
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Getter del genero de animal
     * 
     * @return genero
     */
    public String getGenero() {
        return genero;
    }

    /**
     * Setter del genero de animal
     * 
     * @param genero 
     */
    public void setGenero(String genero) {
        this.genero = genero;
    }

    /**
     * Getter de edad de animal
     * 
     * @return edad
     */
    public Integer getEdad() {
        return edad;
    }

    /**
     * Setter de edad de animal
     * 
     * @param edad 
     */
    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    /**
     * Getter de peso de animal
     * 
     * @return peso
     */
    public Float getPeso() {
        return peso;
    }

    /**
     * Setter de peso de animal
     * 
     * @param peso 
     */
    public void setPeso(Float peso) {
        this.peso = peso;
    }

    /**
     * Getter de altura de animal
     * 
     * @return altura
     */
    public Float getAltura() {
        return altura;
    }

    /**
     * Setter de altura de animal
     * 
     * @param altura 
     */
    public void setAltura(Float altura) {
        this.altura = altura;
    }

    /**
     * Getter de especie de animal
     * 
     * @return especie
     */
    public String getEspecie() {
        return especie;
    }

    /**
     * Setter de especie de animal
     * 
     * @param especie 
     */
    public void setEspecie(String especie) {
        this.especie = especie;
    }

    /**
     * Getter de alimentacion de animal
     * 
     * @return alimentacion 
     */
    public Alimentacion getAlimentacion() {
        return alimentacion;
    }

    /**
     * Setter de alimentcacion de animal
     * 
     * @param alimentacion 
     */
    public void setAlimentacion(Alimentacion alimentacion) {
        this.alimentacion = alimentacion;
    }

    /**
     * Getter de salud de animal
     * 
     * @return salud 
     */
    public Salud getSalud() {
        return salud;
    }

    /**
     * Setter de salud de animal
     * 
     * @param salud 
     */
    public void setSalud(Salud salud) {
        this.salud = salud;
    }

    /**
     * Getter de admin de animal
     * 
     * @return admin
     */
    public Admin getAdmin() {
        return admin;
    }

    /**
     * Setter de admin de animal
     * 
     * @param admin 
     */
    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    /**
     * Getter de zona de animal
     * 
     * @return zona
     */
    public Zona getZona() {
        return zona;
    }

    /**
     * Setter de zona de animal
     * 
     * @param zona 
     */
    public void setZona(Zona zona) {
        this.zona = zona;
    }

    /**
     * Metodo hasCode autogenerado
     *
     * @return hash
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id_animal != null ? id_animal.hashCode() : 0);
        return hash;
    }

     /**
     * Metodo equals autogenerado
     *
     * @param object
     * @return true
     */
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

     /**
     * Método toString autogenerado
     *
     * @return Entidades.Entrada
     */
    @Override
    public String toString() {
        return "Entidades.Animal[ id=" + id_animal + " ]";
    }

}
