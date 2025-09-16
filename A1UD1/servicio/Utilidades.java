package servicio;

import java.io.File;
import excepciones.*;

public class Utilidades extends Exception {
    public static void validarRuta(String ruta) 
        throws DirectorioNoExisteException, NoEsDirectorioException  {
        
        File dir = new File(ruta);

        if (!dir.exists()) {
            throw new DirectorioNoExisteException(
                    "La ruta especificada no existe");
        }

        if (!dir.isDirectory()) {
            throw new NoEsDirectorioException(
                    "La ruta especificada no es un directorio");
        }
    }
}
