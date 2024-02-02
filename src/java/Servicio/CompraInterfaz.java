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
     * @param compra una compra
     * @throws CreateException una excepcion
     */
    public void createEntrada(Compra compra) throws CreateException;

    /**
     * Método que muestra todas las compras realizadas en la app
     *
     * @return una lista
     * @throws ReadException una excepcion
     */
    public List<Compra> viewAllCompras() throws ReadException;

}
