import java.util.Date;


public class RegistroEsclavos {
    private long id;
    private Date ultimoAcceso;
    private String nivelCifrado;

    public RegistroEsclavos(long id, Date ultimoAcceso, String nivelCifrado) {
        this.id = id;
        this.ultimoAcceso = ultimoAcceso;
        this.nivelCifrado = nivelCifrado;
    }


    public long getId() { return id; }
    public Date getUltimoAcceso() { return ultimoAcceso; }
    public String getNivelCifrado() { return nivelCifrado; }


    public void setId(long id) { this.id = id; }
    public void setUltimoAcceso(Date ultimoAcceso) { this.ultimoAcceso = ultimoAcceso; }
    public void setNivelCifrado(String nivelCifrado) { this.nivelCifrado = nivelCifrado; }
}
