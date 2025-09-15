package excepciones;

public class DirectorioNoExisteException extends Exception {
    public DirectorioNoExisteException() {
        super();
    }

    public DirectorioNoExisteException(String msg) {
        super(msg);
    }
}
