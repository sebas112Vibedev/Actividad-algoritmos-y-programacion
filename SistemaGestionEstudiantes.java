import java.util.Scanner;

/*
 * Sistema de Gestion de Estudiantes (SGE) basico
 * Programa de consola que permite agregar estudiantes, mostrarlos,
 * buscarlos por ID y salir del sistema.
 *
 * Autor: Sebastián Alarcón
 */
public class SistemaGestionEstudiantes {

    // Cantidad maxima de estudiantes. La pongo como constante para no
    // escribir el 50 en varias partes y poder cambiarlo en un solo lugar.
    static final int MAX_ESTUDIANTES = 50;

    // Nota minima para aprobar (la escala es de 0.0 a 5.0)
    static final double NOTA_PARA_APROBAR = 3.0;

    // Arreglos paralelos: en la misma posicion de cada arreglo
    // estan los datos de un mismo estudiante.
    static int[] ids = new int[MAX_ESTUDIANTES];
    static String[] nombres = new String[MAX_ESTUDIANTES];
    static int[] edades = new int[MAX_ESTUDIANTES];
    static double[] notas = new double[MAX_ESTUDIANTES];

    // Contador de estudiantes guardados. Tambien me sirve como la
    // posicion libre donde se va a guardar el siguiente estudiante.
    static int totalEstudiantes = 0;

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
                    agregarEstudiante();
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
        System.out.println("Estudiantes registrados en esta sesion: " + totalEstudiantes);
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
        System.out.println("Estudiantes registrados: " + totalEstudiantes + " de " + MAX_ESTUDIANTES);
    }

    public static void agregarEstudiante() {
        System.out.println();
        System.out.println("--- AGREGAR ESTUDIANTE ---");

        // Primero reviso que quede espacio, porque el arreglo tiene tamano fijo
        if (totalEstudiantes >= MAX_ESTUDIANTES) {
            System.out.println("No se pueden agregar mas estudiantes. El sistema esta lleno ("
                    + MAX_ESTUDIANTES + " de " + MAX_ESTUDIANTES + ").");
        } else {
            int id = leerEnteroEnRango("ID del estudiante: ", 1, 999999);

            // if anidado: no dejo guardar dos estudiantes con el mismo ID
            if (buscarPosicionPorId(id) != -1) {
                System.out.println("Error: ya existe un estudiante con el ID " + id + ".");
            } else {
                String nombre = leerTexto("Nombre completo: ");
                int edad = leerEnteroEnRango("Edad: ", 15, 99);
                double nota = leerNota("Nota final (0.0 a 5.0): ");

                // Guardo los datos en la posicion libre, que es igual al contador
                ids[totalEstudiantes] = id;
                nombres[totalEstudiantes] = nombre;
                edades[totalEstudiantes] = edad;
                notas[totalEstudiantes] = nota;
                totalEstudiantes++; // aumento el contador

                System.out.println("Estudiante agregado correctamente. Total: " + totalEstudiantes);
            }
        }
    }

    // ================= FUNCIONES DE AYUDA =================

    // Busqueda lineal: revisa uno por uno desde la posicion 0.
    // Devuelve la posicion donde esta el ID, o -1 si no existe.
    // La uso en agregar (para no repetir IDs) y en buscar, asi no repito codigo.
    public static int buscarPosicionPorId(int idBuscado) {
        for (int i = 0; i < totalEstudiantes; i++) {
            if (ids[i] == idBuscado) {
                return i; // ya lo encontre, no hace falta seguir buscando
            }
        }
        return -1;
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

    // Igual que leerEntero pero ademas revisa que el numero este en un rango
    public static int leerEnteroEnRango(String mensaje, int minimo, int maximo) {
        int numero;
        do {
            numero = leerEntero(mensaje);
            if (numero < minimo || numero > maximo) {
                System.out.println("Error: el numero debe estar entre " + minimo + " y " + maximo + ".");
            }
        } while (numero < minimo || numero > maximo);
        return numero;
    }

    // Pide un texto y no deja que quede vacio
    public static String leerTexto(String mensaje) {
        String texto;
        do {
            System.out.print(mensaje);
            texto = teclado.nextLine().trim();
            if (texto.isEmpty()) {
                System.out.println("Error: este dato no puede quedar vacio.");
            }
        } while (texto.isEmpty());
        return texto;
    }

    // Pide la nota hasta que sea un numero entre 0.0 y 5.0
    public static double leerNota(String mensaje) {
        double nota = 0;
        boolean datoValido;
        do {
            System.out.print(mensaje);
            // cambio la coma por punto porque mucha gente escribe la nota asi: 4,5
            String texto = teclado.nextLine().trim().replace(",", ".");
            try {
                nota = Double.parseDouble(texto);
                if (nota >= 0.0 && nota <= 5.0) {
                    datoValido = true;
                } else {
                    System.out.println("Error: la nota debe estar entre 0.0 y 5.0.");
                    datoValido = false;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: debe escribir un numero, por ejemplo 3.5");
                datoValido = false;
            }
        } while (!datoValido);
        return nota;
    }
}
