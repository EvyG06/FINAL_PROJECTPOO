public class Fabrica {
    private long id;
    private String pais;
    private String ciudad;
    private int capacidad;
    private String nivelAutomatizacion;

    public Fabrica(long id, String pais, String ciudad, int capacidad, String nivelAutomatizacion) {
        this.id = id;
        this.pais = pais;
        this.ciudad = ciudad;
        this.capacidad = capacidad;
        this.nivelAutomatizacion = nivelAutomatizacion;
    }
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getPais() { return pais; }
    public void setPais(String pais) { this.pais = pais; }
    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }
    public int getCapacidad() { return capacidad; }
    public void setCapacidad(int capacidad) { this.capacidad = capacidad; }
    public String getNivelAutomatizacion() { return nivelAutomatizacion; }
    public void setNivelAutomatizacion(String nivelAutomatizacion) { this.nivelAutomatizacion = nivelAutomatizacion; }
}