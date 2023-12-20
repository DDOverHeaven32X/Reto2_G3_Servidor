package Servicio;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author Jason.
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(Servicio.AdminFacadeREST.class);
        resources.add(Servicio.AnimalFacadeREST.class);
        resources.add(Servicio.ClienteFacadeREST.class);
        resources.add(Servicio.CompraFacadeREST.class);
        resources.add(Servicio.EntradaFacadeREST.class);
        resources.add(Servicio.UsuarioFacadeREST.class);
        resources.add(Servicio.ZonaFacadeREST.class);
    }

}
