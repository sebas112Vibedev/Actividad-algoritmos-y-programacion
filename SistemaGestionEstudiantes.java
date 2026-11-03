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
                    mostrarTodos();
                    break;
                case 3:
                    mostrarTodosInverso();
                    break;
                case 4:
                    buscarEstudiante();
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

    public static void mostrarTodos() {
        System.out.println();
        System.out.println("--- LISTA DE ESTUDIANTES ---");

        if (totalEstudiantes == 0) {
            System.out.println("No hay estudiantes registrados todavia.");
        } else {
            double sumaNotas = 0; // sumatoria de las notas para sacar el promedio
            int aprobados = 0;    // contador de estudiantes que aprobaron

            // for ascendente: va desde el primer estudiante (0) hasta el ultimo
            for (int i = 0; i < totalEstudiantes; i++) {
                System.out.print((i + 1) + ". ");
                mostrarEstudiante(i);
                sumaNotas = sumaNotas + notas[i];
                if (notas[i] >= NOTA_PARA_APROBAR) {
                    aprobados++;
                }
            }

            double promedio = sumaNotas / totalEstudiantes;
            // redondeo a 2 decimales para que se vea mas ordenado
            promedio = Math.round(promedio * 100) / 100.0;

            System.out.println("----------------------------------------------");
            System.out.println("Total de estudiantes: " + totalEstudiantes);
            System.out.println("Promedio general: " + promedio);
            System.out.println("Aprobados: " + aprobados + " | Reprobados: " + (totalEstudiantes - aprobados));
        }
    }

    public static void mostrarTodosInverso() {
        System.out.println();
        System.out.println("--- LISTA EN ORDEN INVERSO (del ultimo al primero) ---");

        if (totalEstudiantes == 0) {
            System.out.println("No hay estudiantes registrados todavia.");
        } else {
            // for descendente: empieza en el ultimo estudiante y baja hasta 0
            for (int i = totalEstudiantes - 1; i >= 0; i--) {
                System.out.print((i + 1) + ". ");
                mostrarEstudiante(i);
            }
        }
    }

    public static void buscarEstudiante() {
        System.out.println();
        System.out.println("--- BUSCAR ESTUDIANTE POR ID ---");

        if (totalEstudiantes == 0) {
            System.out.println("No hay estudiantes registrados para buscar.");
        } else {
            int idBuscado = leerEntero("Escriba el ID a buscar: ");
            int posicion = buscarPosicionPorId(idBuscado);

            // decision: si la posicion es -1 quiere decir que no se encontro
            if (posicion == -1) {
                System.out.println("No se encontro ningun estudiante con el ID " + idBuscado + ".");
            } else {
                System.out.println("Estudiante encontrado:");
                mostrarEstudiante(posicion);
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

    // Muestra los datos de un solo estudiante en una linea
    public static void mostrarEstudiante(int posicion) {
        System.out.println("ID: " + ids[posicion]
                + " | Nombre: " + nombres[posicion]
                + " | Edad: " + edades[posicion]
                + " | Nota: " + notas[posicion]
                + " | Estado: " + obtenerEstado(notas[posicion]));
    }

    // if anidado: primero miro si aprobo y despues si fue excelente
    public static String obtenerEstado(double nota) {
        String estado;
        if (nota >= NOTA_PARA_APROBAR) {
            if (nota >= 4.5) {
                estado = "Excelente";
            } else {
                estado = "Aprobado";
            }
        } else {
            estado = "Reprobado";
        }
        return estado;
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
