package servicio;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OperacionesNIO {
    
    // Metodo 1 - Imprimir contenido del directorio
    public static void visualizarContenido(String ruta) throws IOException {
        
        Path dir = Paths.get(ruta);
        List<Path> archivos;

        try (Stream<Path> s = Files.walk(dir)) {
            archivos = s.map(Path::getFileName).collect(Collectors.toList());
        }
        
        for (Path p : archivos) {
            String type = Files.isRegularFile(p)? "<DIR>" : "<FICHERO>";

            System.out.println("-| " + type + p.getFileName() + Files.size(p.toAbsolutePath()));    
        }
        

    }

}