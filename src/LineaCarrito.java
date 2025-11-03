public class LineaCarrito {
    private int cantidad;
    private double subtotal;
    private Producto producto;
    private Carrito carrito;

    public LineaCarrito(int cantidad, double subtotal, Producto producto, Carrito carrito) {
        this.cantidad = cantidad;
        this.subtotal = subtotal;
        this.producto = producto;
        this.carrito = carrito;
    }


    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }
    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }
    public Carrito getCarrito() { return carrito; }
    public void setCarrito(Carrito carrito) { this.carrito = carrito; }
}
