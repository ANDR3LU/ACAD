package utilidades;

import java.io.File;
import java.sql.Date;
import java.text.SimpleDateFormat;

import excepciones.ArchivoNoExisteException;
import excepciones.NoEsDirectorioException;

public class Util {

    // Metodo para pasar las exepciones a una ruta
    public static void comprobarExepciones(File f)
            throws NoEsDirectorioException, ArchivoNoExisteException {
        if (!f.exists()) {
            throw new ArchivoNoExisteException();
        }

        if (!f.isDirectory()) {
            throw new NoEsDirectorioException();
        }
    }

    // Metodo para recorrer e imprimir un directorio
    public static void mostrarDirectorio(File f) {
        File[] hijos = f.listFiles();
        for (File file : hijos) {
            System.out.print("-| ");
            System.out.print(file.getName());

            if (file.isDirectory()) {
                System.out.print("   <DIR>   ");
            } else {
                System.out.print("   <FICHERO>   ");
            }

            System.out.print(file.length() / 1024 + " Kbytes   ");

            Date fecha = new Date(file.lastModified());
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            System.out.print(sdf.format(fecha));
            System.out.println();
        }
    }
}
