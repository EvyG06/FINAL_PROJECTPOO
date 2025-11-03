import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Carrito {
    private long id;
    private Date fechaCreacion;
    private List<LineaCarrito> lineasCarrito;

    public Carrito(long id, Date fechaCreacion) {
        this.id = id;
        this.fechaCreacion = fechaCreacion;
        this.lineasCarrito = new ArrayList<>();
    }


    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public Date getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(Date fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    public List<LineaCarrito> getLineasCarrito() { return lineasCarrito; }
    public void setLineasCarrito(List<LineaCarrito> lineasCarrito) { this.lineasCarrito = lineasCarrito; }


    private Cliente cliente;

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
}
