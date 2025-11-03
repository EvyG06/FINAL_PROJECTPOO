public class LineaCompra {
    private int cantidad;
    private double precioUnit;
    private double subtotal;
    private Producto producto;
    private Compra compra;

    public LineaCompra(int cantidad, double precioUnit, double subtotal, Producto producto, Compra compra) {
        this.cantidad = cantidad;
        this.precioUnit = precioUnit;
        this.subtotal = subtotal;
        this.producto = producto;
        this.compra = compra;
    }


    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public double getPrecioUnit() { return precioUnit; }
    public void setPrecioUnit(double precioUnit) { this.precioUnit = precioUnit; }
    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }
    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }
    public Compra getCompra() { return compra; }
    public void setCompra(Compra compra) { this.compra = compra; }
}