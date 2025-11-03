import java.util.Date;

public class AdministradorUsuario extends Usuario {
    private String nivelAcceso;

    public AdministradorUsuario(long id, String nombre, String email, String passwordHash, String rol, Date fechaRegistro, String estadoCuenta,
                                String nivelAcceso) {
        super(id, nombre, email, passwordHash, rol, fechaRegistro, estadoCuenta);
        this.nivelAcceso = nivelAcceso;
    }


    public String getNivelAcceso() { return nivelAcceso; }
    public void setNivelAcceso(String nivelAcceso) { this.nivelAcceso = nivelAcceso; }
}
