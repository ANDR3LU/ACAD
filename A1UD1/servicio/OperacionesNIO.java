package servicio;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import excepciones.DirectorioNoExisteException;
import excepciones.NoEsDirectorioException;

public class OperacionesNIO {

    // Metodo 1 - Imprimir contenido del directorio
    public static void visualizarContenido(String ruta) throws IOException {

        Path dir = Paths.get(ruta);
        List<Path> archivos;

        try (Stream<Path> s = Files.walk(dir)) {
            archivos = s.map(Path::getFileName).collect(Collectors.toList());
        }

        for (Path p : archivos) {
            String type = Files.isRegularFile(p) ? "<DIR>" : "<FICHERO>";

            System.out.println("-| " + type + p.getFileName() + Files.size(p.toAbsolutePath()));
        }

    }

    // Metodo 3 - Filtrar por extensiones
    public static void filtrarPorExtensiones(String ruta, String extension)
            throws DirectorioNoExisteException, NoEsDirectorioException, IOException {
        Path dir = Paths.get(ruta);
        List<Path> archivos;

        Utilidades.validarRuta(ruta);
        Files.list(dir);

        try (Stream<Path> s = Files.walk(dir)) {
            archivos = s.map(Path::getFileName).collect(Collectors.toList());
        }

        Boolean seEncontro = false;
        for (Path p : archivos) {
            Path nombre = p.getFileName();

            if (nombre.toString().endsWith("." + extension)) {
                System.out.println("-| " + p.getFileName());
                seEncontro = true;
            }
        }

        if (!seEncontro) {
            System.out.println("\nNo se encontro ningun archivo con la extesion: " + extension);
        }
    }

    // Metodo 4 - Filtrar por extensiones recursivo y ordenando
    public static void filtrarPorExtensionesYOrdenar(String ruta, String extension,
            boolean descendente)
            throws DirectorioNoExisteException, NoEsDirectorioException, IOException {

        Path dir = Paths.get(ruta);
        List<Path> archivos;

        Utilidades.validarRuta(ruta);
        Files.list(dir);

        try (Stream<Path> s = Files.walk(dir)) {
            archivos = s.map(Path::getFileName).collect(Collectors.toList());
        }

        archivos.sort(Comparator.comparing(a -> dir.getFileName()));

        Boolean seEncontro = false;
        for (Path p : archivos) {
            
            Path nombre = p.getFileName();

            if (nombre.toString().endsWith("." + extension)) {
                System.out.println("-| " + p.getFileName());
                seEncontro = true;
            }
        }

        if (!seEncontro) {
            System.out.println("\nNo se encontro ningun archivo con la extesion: " + extension);
        }
    }

}