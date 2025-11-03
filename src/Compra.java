import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Compra {
    private long id;
    private Date fecha;
    private double total;
    private String estado;
    private List<LineaCompra> lineasCompra;

    public Compra(long id, Date fecha, double total, String estado) {
        this.id = id;
        this.fecha = fecha;
        this.total = total;
        this.estado = estado;
        this.lineasCompra = new ArrayList<>();
    }


    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }
    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public List<LineaCompra> getLineasCompra() { return lineasCompra; }
    public void setLineasCompra(List<LineaCompra> lineasCompra) { this.lineasCompra = lineasCompra; }


    private Cliente cliente;

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
}
