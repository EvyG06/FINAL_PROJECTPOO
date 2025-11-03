
import java.util.Date;

public class DesarrolladorProducto extends Usuario {
    private String especialidad;

    public DesarrolladorProducto(long id, String nombre, String email, String passwordHash, String rol, Date fechaRegistro, String estadoCuenta,
                                 String especialidad) {
        super(id, nombre, email, passwordHash, rol, fechaRegistro, estadoCuenta);
        this.especialidad = especialidad;
    }
    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }
}
