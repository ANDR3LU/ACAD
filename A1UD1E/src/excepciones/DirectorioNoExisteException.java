package excepciones;

public class DirectorioNoExisteException extends Exception {
    public DirectorioNoExisteException(String mensaje) {
        super(mensaje);
    }

    public DirectorioNoExisteException() {
        super("El directorio especificado no existe");
    }
}
