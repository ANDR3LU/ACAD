import java.util.Scanner;

import servicio.OperacionesIO;

public class MainApp {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.print("Introduce la ruta a listar: ");
        String ruta = sc.nextLine(); 

        try {
            OperacionesIO.visualizarContenido(ruta);
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }

       System.out.println("\n\n");
       
        try {
            OperacionesIO.recorrerRecursivo(ruta);
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        sc.close();
    }
}
