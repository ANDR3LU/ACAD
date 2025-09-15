package excepciones;

public class ArchivoNoExisteException extends Exception{
    public ArchivoNoExisteException() {
        super();
    }

    public ArchivoNoExisteException(String msg) {
        super(msg);
    }
}
