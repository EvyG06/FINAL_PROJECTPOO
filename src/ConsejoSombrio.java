public class ConsejoSombrio {
    private long id;
    private String nombreClave;

    public ConsejoSombrio(long id, String nombreClave) {
        this.id = id;
        this.nombreClave = nombreClave;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getNombreClave() { return nombreClave; }
    public void setNombreClave(String nombreClave) { this.nombreClave = nombreClave; }
}
