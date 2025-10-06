import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainApp {
    private static final DateTimeFormatter TS_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static void uso() {
        System.out.println("Uso:\n" +
                "  java ad.u1.MainApp ejercicio1 Salida.txt file1.txt file2.txt ...\n" +
                "  java ad.u1.MainApp ejercicio2 alumnos.txt baseDir ficherolog.txt\n" +
                "  java ad.u1.MainApp ejercicio3 entrada.txt palabra salida.txt [--case] [--no-boundaries]\n");
    }

    public static void main(String[] args) {
        if (args.length < 1) { uso(); return; }
        try {
            switch (args[0].toLowerCase()) {
                case "ejercicio1": {
                    if (args.length < 3) { uso(); return; }
                    Path salida = Paths.get(args[1]);
                    List<Path> ficheros = new ArrayList<>();
                    for (int i = 2; i < args.length; i++) ficheros.add(Paths.get(args[i]));
                    contarLineasYVolcar(salida, ficheros);
                    break;
                }
                case "ejercicio2": {
                    if (args.length < 4) { uso(); return; }
                    Path entrada = Paths.get(args[1]);
                    Path baseDir = Paths.get(args[2]);
                    Path log = Paths.get(args[3]);
                    crearDirectoriosDesdeFichero(entrada, baseDir, log);
                    break;
                }
                case "ejercicio3": {
                    if (args.length < 4) { uso(); return; }
                    Path entrada = Paths.get(args[1]);
                    String palabra = args[2];
                    Path salida = Paths.get(args[3]);
                    boolean caseSensitive = Arrays.asList(args).contains("--case");
                    boolean boundaries = !Arrays.asList(args).contains("--no-boundaries");
                    contarPalabra(entrada, palabra, salida, caseSensitive, boundaries);
                    break;
                }
                default: uso();
            }
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
        }
    }

    // --------------------------------------------------
    public static void contarLineasYVolcar(Path salida, List<Path> ficheros) {
        OpenOption[] modoSalida = new OpenOption[]{
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING,
                StandardOpenOption.WRITE
        };
        try (BufferedWriter bw = Files.newBufferedWriter(salida, StandardCharsets.UTF_8, modoSalida)) {
            for (Path p : ficheros) {
                if (!p.toString().toLowerCase().endsWith(".txt")) {
                    bw.write(p.getFileName() + ": ERROR - extensión no válida (se espera .txt)");
                    bw.newLine();
                    continue;
                }
                try (BufferedReader br = Files.newBufferedReader(p, StandardCharsets.UTF_8)) {
                    long n = 0;
                    while (br.readLine() != null) n++;
                    bw.write(p.getFileName() + ": " + n + " líneas");
                    bw.newLine();
                } catch (IOException e) {
                    bw.write(p.getFileName() + ": ERROR - " + e.getMessage());
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("ERROR escribiendo Salida: " + e.getMessage());
        }
    }

    // --------------------------------------------------
    public static void crearDirectoriosDesdeFichero(Path ficheroEntrada, Path baseDir, Path ficheroLog) {
        OpenOption[] modoLog = new OpenOption[]{
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
        };
        try (BufferedWriter log = Files.newBufferedWriter(ficheroLog, StandardCharsets.UTF_8, modoLog);
             BufferedReader br = Files.newBufferedReader(ficheroEntrada, StandardCharsets.UTF_8)) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String ts = LocalDateTime.now().format(TS_FMT);
                String[] partes = linea.split("/");
                if (partes.length < 3) {
                    log.write(ts + " | ERROR | línea inválida: " + linea);
                    log.newLine();
                    continue;
                }
                String curso = partes[0].trim();
                String alumno = partes[2].trim();
                Path alumnoDir = baseDir.resolve(curso).resolve(alumno);
                try {
                    Files.createDirectories(alumnoDir);
                    log.write(ts + " | OK    | creado: " + alumnoDir);
                    log.newLine();
                } catch (IOException e) {
                    log.write(ts + " | ERROR | " + alumnoDir + " -> " + e.getMessage());
                    log.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("ERROR procesando: " + e.getMessage());
        }
    }

    // --------------------------------------------------
    public static void contarPalabra(Path ficheroEntrada, String palabra, Path ficheroSalida, boolean caseSensitive, boolean usarDelimitadoresDePalabra) {
        Pattern pattern = construirPatron(palabra, caseSensitive, usarDelimitadoresDePalabra);
        OpenOption[] modoSalida = new OpenOption[]{
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING,
                StandardOpenOption.WRITE
        };
        try (BufferedWriter out = Files.newBufferedWriter(ficheroSalida, StandardCharsets.UTF_8, modoSalida);
             BufferedReader br = Files.newBufferedReader(ficheroEntrada, StandardCharsets.UTF_8)) {
            long total = 0;
            int nLinea = 0;
            String linea;
            while ((linea = br.readLine()) != null) {
                nLinea++;
                long count = contarEnLinea(pattern, linea);
                total += count;
                out.write("Línea " + nLinea + ": " + count + " coincidencias");
                out.newLine();
            }
            out.write("TOTAL: " + total + " coincidencias de '" + palabra + "'");
            out.newLine();
        } catch (IOException e) {
            System.err.println("ERROR escribiendo salida: " + e.getMessage());
        }
    }

    private static Pattern construirPatron(String palabra, boolean caseSensitive, boolean wordBoundaries) {
        String regex = wordBoundaries ? "\\b" + Pattern.quote(palabra) + "\\b" : Pattern.quote(palabra);
        int flags = caseSensitive ? 0 : (Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
        return Pattern.compile(regex, flags);
    }

    private static long contarEnLinea(Pattern p, String linea) {
        long c = 0;
        Matcher m = p.matcher(linea);
        while (m.find()) c++;
        return c;
    }
}
