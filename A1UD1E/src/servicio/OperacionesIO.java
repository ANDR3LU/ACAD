package servicio;

import java.io.File;

import excepciones.ArchivoNoExisteException;
import excepciones.NoEsDirectorioException;
import utilidades.Util;

public class OperacionesIO {

    // 1. Metodo visualizarContenido (IO)
    public static void visualizarContenido(String ruta) throws NoEsDirectorioException, ArchivoNoExisteException {
        File dir = new File(ruta);

        Util.comprobarExepciones(dir);
        Util.mostrarDirectorio(dir);
    }
}
