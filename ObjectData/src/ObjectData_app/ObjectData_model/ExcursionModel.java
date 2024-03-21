package ObjectData_app.ObjectData_model;

import java.util.Date;

public class ExcursionModel {
    // Propiedades de clase
    String codigo;
    String descripcion;
    Date fecha;
    int numeroDias;
    double precioInscripcion;

    // Constructor
    public ExcursionModel(String codigo, String descripcion, Date fecha, int numeroDias, double precioInscripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.numeroDias = numeroDias;
        this.precioInscripcion = precioInscripcion;
    }

    // Métodos
    public static boolean buscarExcursion(Datos BBDD, String nombre)
    {
        boolean encontrado=false;
        // Comprobar en la lista de socios estándar
        for (ExcursionModel excursion : BBDD.excursion) {
            if (excursion.codigo.equals(nombre)) {
                encontrado=true;
            }
        }
        return encontrado;

    }
    public String crearExcursionModel(Datos BBDD, ExcursionModel excursion) {
        try {
            BBDD.excursion.add(excursion);
            return "¡Se ha guardado correctamente!";
        } catch (Exception error) {
            return "Fallo al guardar: " + error;
        }
    }

    public static String mostrarExcursiones(Datos BBDD, Date fechaInicio, Date fechaFin) {
        //Primero comprueba que haya excursiones dentro del ArrayList
        try {
            if (BBDD.excursion.isEmpty()) {
                return "¡No hay excursiones para mostrar!";
            }
            for (ExcursionModel excursion : BBDD.excursion) {
                // Comprueba si la fecha de la excursión está dentro del rango introducido e imprime la info de la misma
                if (!excursion.fecha.before(fechaInicio) && !excursion.fecha.after(fechaFin)) {
                    return "Código: " + excursion.codigo + ", Descripción: " + excursion.descripcion + ", Fecha: " + excursion.fecha + ", Número de días: " + excursion.numeroDias + ", Precio de inscripción: " + excursion.precioInscripcion;
                }else{
                    //Si no hay escursion entonces esto:
                    return "No hay excursiones disponibles entre las fechas proporcionadas.";
                }
            }
        } catch (Exception error) {
            return "Fallo al mostrar las excursiones: " + error;
        }
        return null;
    }
}