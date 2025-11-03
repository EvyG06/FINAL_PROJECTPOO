public class MetodoPago {
    private long id;
    private String tipo;
    private String titular;
    private String numeroEnmascarado;

    public MetodoPago(long id, String tipo, String titular, String numeroEnmascarado) {
        this.id = id;
        this.tipo = tipo;
        this.titular = titular;
        this.numeroEnmascarado = numeroEnmascarado;
    }


    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getTitular() { return titular; }
    public void setTitular(String titular) { this.titular = titular; }
    public String getNumeroEnmascarado() { return numeroEnmascarado; }
    public void setNumeroEnmascarado(String numeroEnmascarado) { this.numeroEnmascarado = numeroEnmascarado; }
}
