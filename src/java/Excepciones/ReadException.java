package Excepciones;

/**
 * Excepcion que se encarga de controlar errores a la hora de leer datos
 *
 * @author Adrian, Diego, Ander
 */
public class ReadException extends Exception {

    public ReadException(String msg) {
        super(msg);
    }
}
