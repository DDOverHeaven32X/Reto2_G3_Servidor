package Entidades;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import static javax.persistence.CascadeType.ALL;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Entidad POJO de entrada
 *
 * @author Diego.
 */
@Entity
@Table(name = "Entrada", schema = "parquedb")

/**
 * Lista de las distintas queris de Entrada
 *
 * @author Diego.
 */
@NamedQueries({
    /**
     * Query que busca una entrada por una fecha de entrada especifica
     */
    @NamedQuery(name = "verEntradasporFecha", query = "SELECT e FROM Entrada e WHERE e.fecha_entrada = :fecha_entrada")
    ,
    /**
     * Query que busca una entrada por un precio especifico
     */
    @NamedQuery(name = "verEntradasporPrecio", query = "SELECT e FROM Entrada e WHERE e.precio = :precio")
    ,
    /**
     * Query que busca una entrada de un cliente especifico
     */
    @NamedQuery(name = "verEntradaCliente", query = "SELECT e FROM Entrada e JOIN e.listaCompras c JOIN c.cliente u WHERE u.login = :login")
    ,
    /**
     * Query que muestra todas las entradas y las ordena de forma ascendiente
     */
    @NamedQuery(name = "verTodasLasEntradas", query = "SELECT e FROM Entrada e ORDER BY e.id_entrada ASC")
    ,
    /**
     * Query que muestra las entradas por un id espeicifco
     */
    @NamedQuery(name = "filtrarEntradaPorId", query = "SELECT e FROM Entrada e ")
})

@XmlRootElement
public class Entrada implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * Atributos de la clase Entrada con sus respectivas anotaciones
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id_entrada;
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    @JsonSerialize(as = Date.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Date fecha_entrada;
    @Column(nullable = false)
    private String tipo_entrada;
    @Column(nullable = false)
    private Float precio;
    @OneToMany(mappedBy = "entrada", cascade = ALL)
    private Set<Compra> listaCompras;
    @Column(nullable = false)
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "listaEntradas", cascade = ALL)
    private Set<Zona> listaZonas;
    @ManyToOne
    private Admin admin;

    /**
     * Getter del id de Entrada
     *
     * @return id_entrada
     */
    public Integer getId_entrada() {
        return id_entrada;
    }

    /**
     * Setter del id de Entrada
     *
     * @param id_entrada
     */
    public void setId_entrada(Integer id_entrada) {
        this.id_entrada = id_entrada;
    }

    /**
     * Getter de la fecha de entrada
     *
     * @return fecha_entrada
     */
    public Date getFecha_entrada() {
        return fecha_entrada;
    }

    /**
     * Setter de la fecha de la entrada
     *
     * @param fecha_entrada
     */
    public void setFecha_entrada(Date fecha_entrada) {
        this.fecha_entrada = fecha_entrada;
    }

    /**
     * Getter del precio de la entrada
     *
     * @return precio
     */
    public Float getPrecio() {
        return precio;
    }

    /**
     * Setter del precio de la entrada
     *
     * @param precio
     */
    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    /**
     * Getter de la lista de compras
     *
     * @return listaCompras
     */
    @XmlTransient
    public Set<Compra> getListaCompras() {
        return listaCompras;
    }

    /**
     * Setter de la lista de compras
     *
     * @param listaCompras
     */
    public void setListaCompras(Set<Compra> listaCompras) {
        this.listaCompras = listaCompras;
    }

    /**
     * Getter de la lista de zonas
     *
     * @return listaZonas
     */
    @XmlTransient
    public Set<Zona> getListaZonas() {
        return listaZonas;
    }

    /**
     * Setter de la lista de Zonas
     *
     * @param listaZonas
     */
    public void setListaZonas(Set<Zona> listaZonas) {
        this.listaZonas = listaZonas;
    }

    /**
     * Getter de tipo entrada
     *
     * @return tipo_entrada
     */
    public String getTipo_entrada() {
        return tipo_entrada;
    }

    /**
     * Setter de tipo entrada
     *
     * @param tipo_entrada
     */
    public void setTipo_entrada(String tipo_entrada) {
        this.tipo_entrada = tipo_entrada;
    }

    /**
     * Getter de admin
     *
     * @return admin
     */
    public Admin getAdmin() {
        return admin;
    }

    /**
     * Getter de admin
     *
     * @param admin
     */
    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    /**
     * Metodo hasCode autogenerado
     *
     * @return hash
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id_entrada != null ? id_entrada.hashCode() : 0);
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
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Entrada)) {
            return false;
        }
        Entrada other = (Entrada) object;
        if ((this.id_entrada == null && other.id_entrada != null) || (this.id_entrada != null && !this.id_entrada.equals(other.id_entrada))) {
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
        return "Entidades.Entrada[ id=" + id_entrada + " ]";
    }

}
