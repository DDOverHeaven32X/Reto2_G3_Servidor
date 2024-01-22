package Servicio;

import Entidades.Compra;
import Excepciones.CreateException;
import Excepciones.ReadException;
import java.util.List;

/**
 * Interfaz de Compra, la usaremos para poder comprar una entrada
 *
 * @author Diego.
 */
public interface CompraInterfaz {

    /**
     * Método para comprar una nueva entrada
     *
     * @param compra
     * @throws CreateException
     */
    public void createEntrada(Compra compra) throws CreateException;

    /**
     * Método que muestra todas las compras realizadas en la app
     *
     * @return
     * @throws ReadException
     */
    public List<Compra> viewAllCompras() throws ReadException;

}
