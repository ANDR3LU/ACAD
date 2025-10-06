package excepciones;

public class NoEsDirectorioException extends Exception {
    public NoEsDirectorioException(String mensaje) {
        super(mensaje);
    }

    public NoEsDirectorioException() {
        super("La ruta especificada no es un directorio");
    }
}
