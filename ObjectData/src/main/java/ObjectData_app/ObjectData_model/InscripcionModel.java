package ObjectData_app.ObjectData_model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ObjectData_app.ObjectData_model.ObjectData_Hibernate.InscripcionHib;
import ObjectData_app.ObjectData_model.ObjectData_Hibernate.SocioEstandarHib;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

public class InscripcionModel {
    // Propiedades de Hibernate
    static SessionFactory sessionFactory;
    static Session session;

    // Se crea una lista estática para almacenar objetos InscripcionModel.
    static ArrayList<InscripcionModel> inscripciones = new ArrayList<>();

    // Se crea una lista estática para almacenar objetos ExcursionModel.
    static ArrayList<ExcursionModel> excursiones = new ArrayList<>();

    private int numeroInscripcion;
    private int numeroSocio;
    private int numeroExcursion;
    private Date fechaInscripcion;

    // Constructor
    public InscripcionModel(int numeroInscripcion, int numeroSocio, int numeroExcursion, Date fechaInscripcion) {
        this.numeroInscripcion = numeroInscripcion;
        this.numeroSocio = numeroSocio;
        this.numeroExcursion = numeroExcursion;
        this.fechaInscripcion = fechaInscripcion;
    }

    // Getters
    public int getNumeroInscripcion() {
        return numeroInscripcion;
    }

    public int getNumeroSocio() {
        return numeroSocio;
    }

    public int getNumeroExcursion() {
        return numeroExcursion;
    }

    public Date getFechaInscripcion() {
        return fechaInscripcion;
    }

    // Setters
    public void setNumeroInscripcion(int numeroInscripcion) {
        this.numeroInscripcion = numeroInscripcion;
    }

    public void setNumeroSocio(int numeroSocio) {
        this.numeroSocio = numeroSocio;
    }

    public void setNumeroExcursion(int numeroExcursion) {
        this.numeroExcursion = numeroExcursion;
    }

    public void setFechaInscripcion(Date fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    // Método toString
    @Override
    public String toString() {
        return "ID Inscripción: " + numeroInscripcion +
                " | ID Socio: " + numeroSocio +
                " | ID Escursión: " + numeroExcursion +
                " | Fecha Inscripción: " + fechaInscripcion;
    }

    // Metodo para crear la session de hibernate
    private static void crearSessionHib() {
        sessionFactory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(SocioEstandarHib.class)
                .buildSessionFactory();
        session = sessionFactory.openSession();
    }

    public static String[] listarInscripcionesFecha(Date fechaI, Date fechaF) {
        crearSessionHib();
        Transaction transaction = null;
        StringBuilder listado = new StringBuilder();
        int contador = 0;

        try {
            transaction = session.beginTransaction();

            // Consulta SQL nativa para obtener las inscripciones dentro del rango de fechas
            String sql = "SELECT * FROM InscripcionModel WHERE fechaInscripcion BETWEEN :fechaI AND :fechaF";
            Query<InscripcionModel> query = session.createNativeQuery(sql, InscripcionModel.class);
            query.setParameter("fechaI", fechaI);
            query.setParameter("fechaF", fechaF);

            List<InscripcionModel> insc = query.getResultList();
            for (InscripcionModel inscripcion : insc) {
                String nombreExcursion = ExcursionModel
                        .obtenerNombreExcursionPorNumeroExcursion(inscripcion.getNumeroExcursion());
                String tipoSocio = SocioModel.obtenerTipoSocioPorNumeroSocio(inscripcion.getNumeroSocio());
                double precio = ExcursionModel.obtenerPrecioExcursion(inscripcion.getNumeroExcursion());
                String nombreSocio = "";
                double precioTotal = precio;
                String cadenaDescuento = "";

                if (tipoSocio.equals("Federado")) {
                    // buscar el nombre de socio
                    SocioFederadoModel Socio = SocioFederadoModel
                            .getSocioPorNumeroSocio(inscripcion.getNumeroSocio());
                    nombreSocio = Socio.getNombre();
                    double descuento = precio * 0.1;
                    precioTotal -= descuento;
                    cadenaDescuento = "Se ha aplicado un 10% de descuento en la excursión. Precio real de la inscripción: "
                            + precioTotal + "\n";
                } else if (tipoSocio.equals("Estandar")) {
                    // buscar socio
                    SocioEstandarModel Socio = SocioEstandarModel
                            .getSocioPorNumeroSocio(inscripcion.getNumeroSocio());
                    nombreSocio = Socio.getNombre();
                    double precioSeguro = SocioEstandarModel
                            .obtenerPrecioSeguroPorNumeroSocio(inscripcion.getNumeroSocio());
                    precioTotal = precio + precioSeguro;
                    cadenaDescuento = "Precio del seguro contratado: " + precioSeguro + "\n"
                            + "Precio total de la inscripción: " + precioTotal;
                } else if (tipoSocio.equals("Infantil")) {
                    // buscar socio
                    SocioInfantilModel socioInfantil = SocioInfantilModel
                            .getSocioPorNumeroSocio(inscripcion.getNumeroSocio());
                    if (socioInfantil != null) {
                        nombreSocio = socioInfantil.getNombre();
                    } else {
                        nombreSocio = "Socio no encontrado"; // Manejar el caso de que el socio no exista
                    }
                    cadenaDescuento = "El socio no tiene descuentos a aplicar.\n";

                }

                contador++;
                listado.append("\n- ").append(contador).append(". Nombre del socio: ").append(nombreSocio)
                        .append(" | Identificador de inscripción: ").append(inscripcion.getNumeroInscripcion())
                        .append(" | Excursión: ").append(nombreExcursion)
                        .append(" | Fecha de inscripción: ").append(inscripcion.getFechaInscripcion())
                        .append(" | Precio de la inscripción: ").append(precio)
                        .append("\n").append(cadenaDescuento);
            }

            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }

        if (contador == 0) {
            listado.append("\n  - Sin datos.");
        }

        return new String[] { listado.toString(), String.valueOf(contador) };
    }

    public static boolean eliminarInscripcionNumero(int numeroInscripcion) {
        Transaction transaction = null;

        try {
            crearSessionHib(); // Crear la sesión de Hibernate

            transaction = session.beginTransaction();

            // Obtener la InscripcionModel por su clave primaria
            InscripcionModel inscripcion = session.get(InscripcionModel.class, numeroInscripcion);

            if (inscripcion != null) {
                // Verificar si la fecha de la excursión es futura
                ExcursionModel excursion = session.get(ExcursionModel.class, numeroInscripcion);
                if (excursion != null && excursion.getFecha().after(new Date())) {
                    session.remove(inscripcion);
                    transaction.commit();
                    return true;
                }
            }

            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }

        return false;
    }

    // Metodo para crear inscripcion
    public static String crearInscripcion(InscripcionModel inscripcion) {
        crearSessionHib();
        try {
            InscripcionHib ins = new InscripcionHib(
                    inscripcion.getNumeroInscripcion(), // Obtenemos el numero de socio desde el objeto socioEstandar
                                                        // //Columna numeroSocio
                    inscripcion.getNumeroSocio(), // Obtenemos el nombre de socio desde el objeto socioEstandar
                                                  // //Columna nombre
                    inscripcion.getNumeroExcursion(), // Obtenemos el NIF de socio desde el objeto socioEstandar
                                                      // //Columna NIF
                    inscripcion.getFechaInscripcion() // Obtenemos el nombre del tipo de seguro del seguro del objeto de
                                                      // socioEstandar //Columna seguro
            );
            session.beginTransaction();
            session.persist(ins);
            session.getTransaction().commit();
            return "La inscripción se guardo correctamente!";
        } catch (HibernateException e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
            return "No se ha podido crear la inscripción: " + e.getMessage();
        } finally {
            session.close();
        }
    }

    public static String obtenerListadoInscripciones() {
        Session session = null;
        try {
            crearSessionHib(); // Crear la sesión de Hibernate
            session = sessionFactory.openSession();

            // Consulta HQL para obtener todas las inscripciones
            String hql = "FROM InscripcionHib";
            Query<InscripcionHib> query = session.createQuery(hql, InscripcionHib.class);
            List<InscripcionHib> inscripciones = query.getResultList();

            // Atributos
            int contador = 0;
            String listado = "Listado de Inscripciones:\n"; // No se añade StringBuilder por la simplicidad del output

            for (InscripcionHib inscripcion : inscripciones) {
                contador++;
                listado += "\n   -" + contador + ". " + inscripcion.toString();
            }

            if (contador == 0) {
                listado += ("- Sin datos.");
            }

            return listado;
        } catch (HibernateException e) {
            e.printStackTrace();
            return "No se ha podido obtener el listado de inscripciones: " + e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    // Metodo para obtener inscripciones de un socio mediante numeroSocio
    public static String[] obtenerInscripcionesByNumSocio(int numeroSocio) {
        Session session = null;
        // Se llama al DAO para obtener las inscripciones desde MySQL
        try {
            crearSessionHib(); // Crear la sesión de Hibernate
            session = sessionFactory.openSession();
            String hql = "FROM InscripcionHib WHERE numerosocio = :numeroSocio";
            Query<InscripcionHib> query = session.createQuery(hql, InscripcionHib.class);
            List<InscripcionHib> inscripciones = query.getResultList();
            double total = 0.0;
            StringBuilder listado = new StringBuilder("\n    - Lista de inscripciones del socio: ");
            int contador = 0;
            for (InscripcionHib inscripcion : inscripciones) {
                contador++;
                Double precioExcursion = ExcursionModel
                        .obtenerExcursionPorNumeroExcursion(inscripcion.getNumeroExcursion())
                        .getPrecioInscripcion();
                String descripcionExcursion = ExcursionModel
                        .obtenerExcursionPorNumeroExcursion(inscripcion.getNumeroExcursion()).getDescripcion();
                listado.append("\n- ").append(contador).append(". ID Inscripción: ")
                        .append(inscripcion.getNumeroInscripcion())
                        .append(" | Precio excursion: ").append(precioExcursion)
                        .append(" | Descripcion excursion: ").append(descripcionExcursion);
                total += precioExcursion;
            }
            if (contador == 0) {
                listado.append("\n - Sin inscripciones.");
            }
            return new String[] { listado.toString(), String.valueOf(total) };

        } catch (Exception e) {
            // Implementar logica para devolver el error.
            throw e;
        }

    }

    // Metodo para comprobar si un usuario tiene inscripciones
    public static boolean comprobarSocioInscrito(int numeroSocio) {
        // Se llama al DAO para obtener las inscripciones desde MySQL
        try {

        } catch (Exception e) {
            // Implementar logica para devolver el error.
            throw e;
        }
        for (InscripcionModel inscripcion : inscripciones) {
            if (inscripcion.getNumeroSocio() == numeroSocio) {
                // Devuelve true si el socio esta inscrito en una excursión
                return true;
            }
        }
        return false;
    }
}
