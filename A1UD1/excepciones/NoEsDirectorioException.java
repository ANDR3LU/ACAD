package excepciones;

public class NoEsDirectorioException extends Exception{
    public NoEsDirectorioException() {
        super();
    }

    public NoEsDirectorioException(String msg) {
        super(msg);
    }

}
