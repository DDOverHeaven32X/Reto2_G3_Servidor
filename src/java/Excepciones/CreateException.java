package Excepciones;

/**
 * Excepcion que se encarga de controlar errores a la hora de crear
 *
 * @author Adrian, Diego, Ander
 */
public class CreateException extends Exception {

    public CreateException(String msg) {
        super(msg);
    }
}
