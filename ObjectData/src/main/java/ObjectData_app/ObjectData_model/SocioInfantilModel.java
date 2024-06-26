package ObjectData_app.ObjectData_model;

public class SocioInfantilModel extends SocioModel {
    private int numeroSocioPadreMadre;

    // Constructor
    public SocioInfantilModel(int numeroSocio, String nombre, int numeroSocioPadreMadre) {
        super(numeroSocio, nombre);
        this.numeroSocioPadreMadre = numeroSocioPadreMadre;
    }

    // Getter y Setter
    public int getNumeroSocioPadreMadre() {
        return numeroSocioPadreMadre;
    }

    public void setNumeroSocioPadreMadre(int numeroSocioPadreMadre) {
        this.numeroSocioPadreMadre = numeroSocioPadreMadre;
    }

    // Método toString
    @Override
    public String toString() {
        return "SocioInfantil{" +
                "numeroSocio=" + getNumeroSocio() +
                ", nombre='" + getNombre() + '\'' +
                ", numeroSocioPadreMadre=" + numeroSocioPadreMadre +
                '}';
    }

    // Metodos propios
    // Crear socio infantil
    public String crearSocioInfantil(Datos BBDD, SocioInfantilModel socio) {
        try {
            BBDD.socioInfantil.add(socio);
            return "Se guardo correctamente!";
        } catch (Exception error) {
            return "Fallo al guardar: " + error;
        }
    }
    public SocioInfantilModel getSocioInfantil(Datos BBDD, int numSocio) {
        for(SocioInfantilModel socio : BBDD.socioInfantil){
            if(numSocio == socio.getNumeroSocio()){
                return socio;
            }
        }
        return null;
    }

        // Método para eliminar socio infantil de la base de datos
        public static boolean eliminarSocioModel(Datos BBDD, int numSocio) {
            for (SocioInfantilModel socio : BBDD.socioInfantil) {
                if (socio.getNumeroSocio() == numSocio) {
                    BBDD.socioInfantil.remove(socio);
                    return true; // Socio eliminado
                }
            }
            return false; // Socio no encontrado
        }
}
