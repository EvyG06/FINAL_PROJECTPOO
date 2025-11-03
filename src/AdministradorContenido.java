import java.util.Date;

public class AdministradorContenido extends Usuario {
    private String permisosEdicion;

    public AdministradorContenido(long id, String nombre, String email, String passwordHash, String rol, Date fechaRegistro, String estadoCuenta,
                                  String permisosEdicion) {
        super(id, nombre, email, passwordHash, rol, fechaRegistro, estadoCuenta);
        this.permisosEdicion = permisosEdicion;
    }

    public String getPermisosEdicion() { return permisosEdicion; }
    public void setPermisosEdicion(String permisosEdicion) { this.permisosEdicion = permisosEdicion; }
}