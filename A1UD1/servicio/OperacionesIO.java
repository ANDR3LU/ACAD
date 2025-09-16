package servicio;

import java.io.File;
import excepciones.DirectorioNoExisteException;
import excepciones.NoEsDirectorioException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class OperacionesIO {

    // Metodo 1 - Imprimir contenido del directorio
    public static void visualizarContenido(String ruta)
            throws DirectorioNoExisteException, NoEsDirectorioException {

        Utilidades.validarRuta(ruta);

        File dir = new File(ruta);
        SimpleDateFormat formatoFecha = new SimpleDateFormat(
                "dd/MM/yyyy HH:mm", Locale.forLanguageTag(
                        "es-ES"));

        System.out.println("\n--- LISTANDO EL DIRECTORIO " + ruta);

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

    // Metodo 2 - Directorios con sangria
    public static void recorrerRecursivo(String ruta)
            throws DirectorioNoExisteException, NoEsDirectorioException {
        Utilidades.validarRuta(ruta);

        File dir = new File(ruta);
        SimpleDateFormat formatoFecha = new SimpleDateFormat(
                "dd/MM/yyyy HH:mm", Locale.forLanguageTag(
                        "es-ES"));

        File[] hijos = dir.listFiles();
        for (File f : hijos) {
            String tipo = f.isDirectory() ? "<DIR>" : "<FICHERO>";
            String tamKB = f.isFile() ? ((f.length() + 1023) / 1024) + " KB" : "";
            String fecha = formatoFecha.format(new Date(f.lastModified()));

            System.out.println("-| " + f.getName() + "  " + tipo + "  "
                    + tamKB + "  " + fecha);

            if (f.isDirectory()) {
                recorrerRecursivo(ruta + "/" + f.getName());
            }

        }
    }

}
