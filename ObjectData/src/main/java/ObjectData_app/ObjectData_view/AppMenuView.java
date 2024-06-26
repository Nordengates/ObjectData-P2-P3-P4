package ObjectData_app.ObjectData_view;

import java.util.Scanner;

public class AppMenuView {
    // Teclado, colores de texto y limpieza de consola.
    static Scanner teclado = new Scanner(System.in);
    static String h1 = "\033[33m";
    static String h2 = "\033[38;5;36m";
    static String p = "\u001B[0m";
    static String p2 = "\033[36m";
    static String error = "\033[31m";
    static String limpiezaConsola = "\n\n\n\n\n\n\n\n\n\n\n";

    // Propiedades de clase
    public void menuInicioView() {
        System.out.println(limpiezaConsola + h1 + "---MENU PRINCIPAL DE LA APLICACION---" + p);
        System.out.println("    1. Gestión Excursiones");
        System.out.println("    2. Gestión de Socios");
        System.out.println("    3. Gestión de Inscripciones");
        System.out.println("    4. Salir de la aplicación");
    }

    public void menuGestionExcursionesView() {
        System.out.println(limpiezaConsola + h2 + "---GESTIÓN DE EXCURSIONES---" + p);
        System.out.println("    1. Añadir Excursión");
        System.out.println("    2. Mostrar Excursiones con filtro entre fechas");
        System.out.println("    3. Volver al menu principal");
    }

    public void menuGestionSociosView() {
        System.out.println(limpiezaConsola + h2 + "---GESTIÓN DE SOCIOS---" + p);
        System.out.println("    1. Añadir nuevo socio.");
        System.out.println("    2. Modificar tipo de seguro de un socio estándar.");
        System.out.println("    3. Eliminar socio.");
        System.out.println("    4. Mostrar Socios.");
        System.out.println("    5. Mostrar Factura mensual por socio.");
        System.out.println("    6. Volver al menu principal");
    }

    public void menuGestionInscripcionesView() {
        System.out.println(limpiezaConsola + h2 + "---GESTIÓN DE INSCRIPCIONES---" + p);
        System.out.println("    1. Añadir Inscripción");
        System.out.println("    2. Eliminar Inscripción");
        System.out.println("    3. Mostrar inscripciones con las opciones de filtrar por socio y/o fechas");
        System.out.println("    4. Volver al menu principal");
    }

    public int getOpcionView(int value) {
        try {
            System.out.print(p2 + "Elije una opción válida (1 - " + value + "): " + p);
            int opcion = Integer.parseInt(teclado.nextLine());
            if (opcion < 1 || opcion > value) {
                System.out.println(error + "Opción no válida." + p);
                return getOpcionView(value);
            }
            return opcion;
        } catch (NumberFormatException e) {
            System.out.println(error + "Opción no válida. Debe ingresar un número." + p);
            return getOpcionView(value);
        }
    }

    // Este metodo se usa para devolver respuestas del controlador, tipo: "Fallo al
    // guardar, Guardado Correcto, los objetos o lo que sea..., es decir los datos
    // almacenados entre otros mensajes."
    public void respuestaControllerView(String respuesta) {
        System.out.println(respuesta);
        System.out.println("\nPulsa un tecla para continuar...");
        teclado.nextLine();
    }
}
