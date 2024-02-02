package Excepciones;

/**
 * Excepcion que se encarga de controlar errores a la hora de borrar
 *
 * @author Adrian, Diego, Ander
 */
public class DeleteException extends Exception {

    public DeleteException(String msg) {
        super(msg);
    }
}
