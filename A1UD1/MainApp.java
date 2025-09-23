import java.io.IOException;
import java.util.Scanner;

import excepciones.DirectorioNoExisteException;
import excepciones.NoEsDirectorioException;
import servicio.OperacionesNIO;

public class MainApp {
    public static void main(String[] args)
            throws DirectorioNoExisteException, NoEsDirectorioException, IOException {

        Scanner sc = new Scanner(System.in);
        System.out.print("Introduce la ruta a listar: ");
        String ruta = sc.nextLine();

        /*
         * try {
         * OperacionesNIO.visualizarContenido(ruta);
         * } catch (IOException e) {
         * System.out.println("ERROR: " + e.getMessage());
         * }
         * 
         * 
         * try {
         * OperacionesIO.visualizarContenido(ruta);
         * } catch (Exception e) {
         * System.out.println("ERROR: " + e.getMessage());
         * }
         * 
         * System.out.println("\n\n");
         * 
         * try {
         * OperacionesIO.recorrerRecursivo(ruta);
         * } catch (Exception e) {
         * System.out.println("ERROR: " + e.getMessage());
         * }
         * 
         * sc.close();
         */

        System.out.println("Introduce la extension que quieres filtrar: ");
        String extension = sc.nextLine();
        OperacionesNIO.filtrarPorExtensionesYOrdenar(ruta, extension, true);
    }
}
