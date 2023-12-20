package Servicio;

import Entidades.Entrada;
import Entidades.Usuario;
import Excepciones.CreateException;
import Excepciones.DeleteException;
import Excepciones.ReadException;
import Excepciones.UpdateException;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Diego.
 */
public interface EntradaIntefraz {

    public void createEntrada(Entrada entrada) throws CreateException;

    public void modifyEntrada(Entrada entrada) throws UpdateException;

    public void deleteEntrada(Entrada entrada) throws DeleteException;

    public List<Entrada> viewAllEntradas() throws ReadException;

    public Entrada filtrarEntradaPorID(Integer id) throws ReadException;

    public List<Entrada> viewEntradaByDate(Date fecha) throws ReadException;

    public List<Entrada> viewEntradaByPrice(Float valor) throws ReadException;

    public List<Entrada> viewEntradaDeCliente(Usuario usuario) throws ReadException;

}
