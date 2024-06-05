package ObjectData_app.ObjectData_controller;

//Se importan las librerias
import ObjectData_app.ObjectData_view.AppMenuView;
import ObjectData_app.ObjectData_model.Datos;
import ObjectData_app.ObjectData_model.CargarDatosModel;

public class AppController {
    static AppMenuView View = new AppMenuView();
    static boolean cerrarApp = false;

    public static void cargarDatosController(Datos BBDD) {
        // Se cargan los datos de forma automatica, se podria añadir un case en el
        // switch del menu principal para cargar los datos manualmente pero lo hacemos
        // de este modo para poder ejecutar los test
        CargarDatosModel cargarDatos = new CargarDatosModel();
        cargarDatos.cargarDatos(BBDD);
    }

    // Inicio del menu de la APP.
    public static void inicio(Datos BBDD) {

        // arracamos el menu principal de la aplicación.
        do {
            View.menuInicioView();
            int opcion = View.getOpcionView(4);
            switch (opcion) {
                case 1:
                    gestionExcursiones(BBDD);
                    break;
                case 2:
                    gestionSocios(BBDD);
                    break;
                case 3:
                    gestionInscripciones(BBDD);
                    break;
                case 4:
                    cerrarApp = true;
                    break;
            }
        } while (!cerrarApp);
    }

    // Metodos de control para Excursiones.
    public static void gestionExcursiones(Datos BBDD) {
        View.menuGestionExcursionesView();
        int opcion = View.getOpcionView(3);
        switch (opcion) {
            case 1:
                ExcursionController.crearExcursion(BBDD);
                break;
            case 2:
                ExcursionController.mostrarExcursionFecha(BBDD);
                break;
            case 3:
                inicio(BBDD);
                break;
        }
    }

    // Metodos de control para Socios.
    public static void gestionSocios(Datos BBDD) {
        View.menuGestionSociosView();
        int opcion = View.getOpcionView(6);
        switch (opcion) {
            case 1:
                SocioController.crearNuevoSocio(BBDD);
                break;
            case 2:
                SocioController.modificarSeguroSocioEstandar(BBDD);
                break;
            case 3:
                SocioController.eliminarSocio(BBDD);
                break;
            case 4:
                SocioController.mostrarSocio(BBDD);
                break;
            case 5:
                SocioController.facturaMensualSocio(BBDD);
                break;
            case 6:
                inicio(BBDD);
                break;
        }
    }

    // Metodos de control para Inscripciones.
    public static void gestionInscripciones(Datos BBDD) {
        View.menuGestionInscripcionesView();
        int opcion = View.getOpcionView(4);
        switch (opcion) {
            case 1:
                InscripcionController.crearInscripcion(BBDD);
                break;
            case 2:
                InscripcionController.eliminarInscripcion(BBDD);
                break;
            case 3:
                InscripcionController.mostrarInscripcion(BBDD);
                break;
            case 4:
                inicio(BBDD);
                break;
        }
    }

}