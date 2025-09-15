package excepciones;

public class DirectorioVacioException extends Exception {
    public DirectorioVacioException (){
        super();
    }

    public DirectorioVacioException(String msg) {
        super(msg);
    }
}
