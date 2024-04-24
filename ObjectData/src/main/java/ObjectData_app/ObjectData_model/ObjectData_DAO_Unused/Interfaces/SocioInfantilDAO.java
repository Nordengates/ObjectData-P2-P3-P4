package ObjectData_app.ObjectData_model.ObjectData_DAO_Unused.Interfaces;
import ObjectData_app.ObjectData_model.SocioInfantilModel;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SocioInfantilDAO {
    ArrayList<SocioInfantilModel> obtenerTodosSocioInfantil() throws SQLException;
    SocioInfantilModel obtenerPorNumeroSocio(int numeroSocio) throws SQLException;
    void crearSocioInfantil(SocioInfantilModel socio) throws SQLException;
    void eliminarSocioInfantil(int numeroSocio) throws SQLException;
}