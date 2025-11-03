
import java.util.ArrayList;
import java.util.List;

public class Cliente extends Usuario {
    private String direccionEnvio;
    private String telefono;
    private List<MetodoPago> metodosPago;

    public Cliente(long id, String nombre, String email, String passwordHash, String rol, java.util.Date fechaRegistro, String estadoCuenta,
                   String direccionEnvio, String telefono) {
        super(id, nombre, email, passwordHash, rol, fechaRegistro, estadoCuenta);
        this.direccionEnvio = direccionEnvio;
        this.telefono = telefono;
        this.metodosPago = new ArrayList<>();
    }


    public String getDireccionEnvio() { return direccionEnvio; }
    public void setDireccionEnvio(String direccionEnvio) { this.direccionEnvio = direccionEnvio; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public List<MetodoPago> getMetodosPago() { return metodosPago; }
    public void setMetodosPago(List<MetodoPago> metodosPago) { this.metodosPago = metodosPago; }
}
