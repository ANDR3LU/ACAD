package excepciones;

public class ArchivoNoExisteException extends Exception {
    public ArchivoNoExisteException(String mensaje) {
        super(mensaje);
    }

    public ArchivoNoExisteException() {
        super("El archivo especificado no existe");
    }
}
