import java.util.Date;

public class TrabajadorEsclavizado {
    private long id;
    private String nombre;
    private String paisOrigen;
    private int edad;
    private Date fechaCaptura;
    private String salud;
    private String asignadoA; // fábrica

    public TrabajadorEsclavizado(long id, String nombre, String paisOrigen, int edad, Date fechaCaptura, String salud, String asignadoA) {
        this.id = id;
        this.nombre = nombre;
        this.paisOrigen = paisOrigen;
        this.edad = edad;
        this.fechaCaptura = fechaCaptura;
        this.salud = salud;
        this.asignadoA = asignadoA;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getPaisOrigen() { return paisOrigen; }
    public void setPaisOrigen(String paisOrigen) { this.paisOrigen = paisOrigen; }
    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }
    public Date getFechaCaptura() { return fechaCaptura; }
    public void setFechaCaptura(Date fechaCaptura) { this.fechaCaptura = fechaCaptura; }
    public String getSalud() { return salud; }
    public void setSalud(String salud) { this.salud = salud; }
    public String getAsignadoA() { return asignadoA; }
    public void setAsignadoA(String asignadoA) { this.asignadoA = asignadoA; }
}
