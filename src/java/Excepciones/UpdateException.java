package Excepciones;

/**
 * Excepcion que se encarga de controlar errores a la hora de actualizar datos
 *
 * @author Adrian, Diego, Ander
 */
public class UpdateException extends Exception {

    public UpdateException(String msg) {
        super(msg);
    }
}
