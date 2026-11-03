import java.util.Scanner;

/*
 * Sistema de Gestion de Estudiantes (SGE) basico
 * Programa de consola que permite agregar estudiantes, mostrarlos,
 * buscarlos por ID y salir del sistema.
 *
 * Autor: Sebastián Alarcón
 */
public class SistemaGestionEstudiantes {

    // Un solo Scanner para todo el programa
    static Scanner teclado = new Scanner(System.in);

    public static void main(String[] args) {
        // Bandera que controla el menu: cuando sea true el programa termina
        boolean banderaSalir = false;
        int opcion;

        System.out.println("==============================================");
        System.out.println("   SISTEMA DE GESTION DE ESTUDIANTES (SGE)");
        System.out.println("==============================================");

        // while (condicion al comienzo): el menu se repite mientras
        // el usuario no elija la opcion de salir
        while (!banderaSalir) {
            mostrarMenu();
            opcion = leerEntero("Elija una opcion: ");

            // switch (decision multiple): cada numero lleva a una funcion distinta
            switch (opcion) {
                case 1:
                    System.out.println("Agregar estudiante: en construccion");
                    break;
                case 2:
                    System.out.println("Mostrar lista: en construccion");
                    break;
                case 3:
                    System.out.println("Mostrar lista inversa: en construccion");
                    break;
                case 4:
                    System.out.println("Buscar por ID: en construccion");
                    break;
                case 5:
                    banderaSalir = true; // con esto el while ya no se repite
                    break;
                default:
                    System.out.println("Opcion no valida. Escriba un numero del 1 al 5.");
            }
        }

        System.out.println();
        System.out.println("Gracias por usar el SGE. Hasta luego!");
        teclado.close();
    }

    // ================= FUNCIONES DEL MENU =================

    public static void mostrarMenu() {
        System.out.println();
        System.out.println("--------------- MENU PRINCIPAL ---------------");
        System.out.println("1. Agregar estudiante");
        System.out.println("2. Mostrar lista de estudiantes");
        System.out.println("3. Mostrar lista en orden inverso");
        System.out.println("4. Buscar estudiante por ID");
        System.out.println("5. Salir");
        System.out.println("----------------------------------------------");
    }

    // ============ FUNCIONES PARA LEER DATOS (VALIDACIONES) ============

    // Pide un numero entero hasta que el usuario escriba uno valido.
    // Uso do-while (condicion al final) porque el dato se pide minimo una vez.
    public static int leerEntero(String mensaje) {
        int numero = 0;
        boolean datoValido; // bandera: true cuando el numero esta bien escrito
        do {
            System.out.print(mensaje);
            String texto = teclado.nextLine().trim();
            try {
                numero = Integer.parseInt(texto);
                datoValido = true;
            } catch (NumberFormatException e) {
                // si escriben letras el programa no se cae, solo avisa
                System.out.println("Error: debe escribir un numero entero.");
                datoValido = false;
            }
        } while (!datoValido);
        return numero;
    }
}
