package servicio;

import java.io.File;
import excepciones.DirectorioNoExisteException;
import excepciones.NoEsDirectorioException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class OperacionesIO {

    public static void visualizarContenido(String ruta)
            throws DirectorioNoExisteException, NoEsDirectorioException {

        File dir = new File(ruta);
        SimpleDateFormat formatoFecha = new SimpleDateFormat(
                "dd/MM/yyyy HH:mm", Locale.forLanguageTag(
                        "es-ES"));
        System.out.println("\n--- LISTANDO EL DIRECTORIO " + ruta);

        if (!dir.exists()) {
            throw new DirectorioNoExisteException(
                    "La ruta especificada no existe");
        }

        if (!dir.isDirectory()) {
            throw new NoEsDirectorioException(
                    "La ruta especificada no es un directorio");
        }

        File[] hijos = dir.listFiles();
        if (hijos.length == 0) {
            throw new DirectorioNoExisteException(
                    "El directorio esta vacio");
        }

        for (File f : hijos) {
            String tipo = f.isDirectory() ? "<DIR>" : "<FICHERO>";
            String tamKB = f.isFile() ? ((f.length() + 1023) / 1024) + " KB" : "";
            String fecha = formatoFecha.format(new Date(f.lastModified()));

            System.out.println("-| " + f.getName() + "  " + tipo + "  "
                    + tamKB + "  " + fecha);
        }

    }
}
