package Entidades;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.util.Date;
import static javax.persistence.CascadeType.ALL;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entidad POJO de Compra, tabla con relacion N/M con atributos
 *
 * @author Diego, Ander, Adrian.
 */
@Entity
@Table(name = "Compra", schema = "parquedb")
@NamedQueries({
    @NamedQuery(name = "VerTodasLasCompras", query = "SELECT c FROM Compra c ORDER BY c.compraId ASC")

})
@XmlRootElement
public class Compra implements Serializable {

    /**
     * Atributos de la clase Compra con sus respectivas anotaciones
     */
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    private CompraId compraId;
    @Temporal(TemporalType.DATE)
    @JsonSerialize(as = Date.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Date fecha_compra;
    @MapsId("id_user")
    @ManyToOne
    private Cliente cliente;
    @MapsId("id_entrada")
    @ManyToOne(cascade = ALL)
    private Entrada entrada;

    public CompraId getCompraId() {
        return compraId;
    }

    public void setCompraId(CompraId compraId) {
        this.compraId = compraId;
    }

    public Date getFecha_compra() {
        return fecha_compra;
    }

    public void setFecha_compra(Date fecha_compra) {
        this.fecha_compra = fecha_compra;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Entrada getEntrada() {
        return entrada;
    }

    public void setEntrada(Entrada entrada) {
        this.entrada = entrada;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fecha_compra != null ? fecha_compra.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Compra)) {
            return false;
        }
        Compra other = (Compra) object;
        if ((this.fecha_compra == null && other.fecha_compra != null) || (this.fecha_compra != null && !this.fecha_compra.equals(other.fecha_compra))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Compra[ id=" + fecha_compra + " ]";
    }

}
