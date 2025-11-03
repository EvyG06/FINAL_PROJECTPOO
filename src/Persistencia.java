
import java.sql.*;
import java.util.*;
import java.util.Date;

public class Persistencia {


    public static void guardarUsuario(Usuario u) {
        String sql = "INSERT OR REPLACE INTO usuarios (id, tipo, nombre, email, passwordHash, rol, fechaRegistro, estadoCuenta, " +
                "direccionEnvio, telefono, permisosEdicion, nivelAcceso, claveMaestra, fechaCoronacion, especialidad) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Database.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, u.getId());
            pstmt.setString(2, u.getClass().getSimpleName());
            pstmt.setString(3, u.getNombre());
            pstmt.setString(4, u.getEmail());
            pstmt.setString(5, u.getPasswordHash());
            pstmt.setString(6, u.getRol());
            pstmt.setLong(7, u.getFechaRegistro().getTime());
            pstmt.setString(8, u.getEstadoCuenta());

            if (u instanceof Cliente c) {
                pstmt.setString(9, c.getDireccionEnvio());
                pstmt.setString(10, c.getTelefono());
            } else {
                pstmt.setString(9, null);
                pstmt.setString(10, null);
            }

            if (u instanceof AdministradorContenido ac) {
                pstmt.setString(11, ac.getPermisosEdicion());
            } else {
                pstmt.setString(11, null);
            }

            if (u instanceof AdministradorUsuario au) {
                pstmt.setString(12, au.getNivelAcceso());
            } else {
                pstmt.setString(12, null);
            }

            if (u instanceof Dueña d) {
                pstmt.setString(13, d.getClaveMaestra());
                pstmt.setLong(14, d.getFechaCoronacion().getTime());
            } else {
                pstmt.setString(13, null);
                pstmt.setObject(14, null);
            }

            if (u instanceof DesarrolladorProducto dp) {
                pstmt.setString(15, dp.getEspecialidad());
            } else {
                pstmt.setString(15, null);
            }

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error guardando usuario: " + e.getMessage());
        }
    }

    public static List<Usuario> cargarUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";

        try (Connection conn = Database.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String tipo = rs.getString("tipo");
                long id = rs.getLong("id");

                Usuario u = switch (tipo) {
                    case "Cliente" -> new Cliente(
                            id, rs.getString("nombre"), rs.getString("email"), rs.getString("passwordHash"),
                            rs.getString("rol"), new Date(rs.getLong("fechaRegistro")), rs.getString("estadoCuenta"),
                            rs.getString("direccionEnvio"), rs.getString("telefono")
                    );
                    case "AdministradorContenido" -> new AdministradorContenido(
                            id, rs.getString("nombre"), rs.getString("email"), rs.getString("passwordHash"),
                            rs.getString("rol"), new Date(rs.getLong("fechaRegistro")), rs.getString("estadoCuenta"),
                            rs.getString("permisosEdicion")
                    );
                    case "AdministradorUsuario" -> new AdministradorUsuario(
                            id, rs.getString("nombre"), rs.getString("email"), rs.getString("passwordHash"),
                            rs.getString("rol"), new Date(rs.getLong("fechaRegistro")), rs.getString("estadoCuenta"),
                            rs.getString("nivelAcceso")
                    );
                    case "Dueña" -> new Dueña(
                            id, rs.getString("nombre"), rs.getString("email"), rs.getString("passwordHash"),
                            rs.getString("rol"), new Date(rs.getLong("fechaRegistro")), rs.getString("estadoCuenta"),
                            rs.getString("claveMaestra"), new Date(rs.getLong("fechaCoronacion"))
                    );
                    case "DesarrolladorProducto" -> new DesarrolladorProducto(
                            id, rs.getString("nombre"), rs.getString("email"), rs.getString("passwordHash"),
                            rs.getString("rol"), new Date(rs.getLong("fechaRegistro")), rs.getString("estadoCuenta"),
                            rs.getString("especialidad")
                    );
                    default -> null;
                };
                if (u != null) usuarios.add(u);
            }
        } catch (SQLException e) {
            System.out.println("Error cargando usuarios: " + e.getMessage());
        }
        return usuarios;
    }


    public static void guardarCategoria(Categoria c) {
        String sql = "INSERT OR REPLACE INTO categorias VALUES (?, ?, ?)";
        try (Connection conn = Database.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, c.getId());
            pstmt.setString(2, c.getNombre());
            pstmt.setString(3, c.getDescripcion());
            pstmt.executeUpdate();
        } catch (SQLException e) { System.out.println("Error categoría: " + e.getMessage()); }
    }

    public static List<Categoria> cargarCategorias() {
        List<Categoria> categorias = new ArrayList<>();
        String sql = "SELECT * FROM categorias";
        try (Connection conn = Database.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                categorias.add(new Categoria(rs.getLong("id"), rs.getString("nombre"), rs.getString("descripcion")));
            }
        } catch (SQLException e) { System.out.println("Error categorías: " + e.getMessage()); }
        return categorias;
    }


    public static void guardarProducto(Producto p) {
        String sql = "INSERT OR REPLACE INTO productos VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Database.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, p.getId());
            pstmt.setString(2, p.getNombre());
            pstmt.setString(3, p.getDescripcion());
            pstmt.setDouble(4, p.getPrecio());
            pstmt.setInt(5, p.getStock());
            pstmt.setLong(6, p.getFechaLanzamiento().getTime());
            pstmt.setLong(7, p.getCategoria().getId());
            pstmt.executeUpdate();
        } catch (SQLException e) { System.out.println("Error producto: " + e.getMessage()); }
    }

    public static List<Producto> cargarProductos(List<Categoria> categorias) {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM productos";
        try (Connection conn = Database.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Categoria cat = categorias.stream().filter(c -> {
                    try {
                        return c.getId() == rs.getLong("categoriaId");
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }).findFirst().orElse(null);
                if (cat != null) {
                    productos.add(new Producto(
                            rs.getLong("id"), rs.getString("nombre"), rs.getString("descripcion"),
                            rs.getDouble("precio"), rs.getInt("stock"),
                            new Date(rs.getLong("fechaLanzamiento")), cat
                    ));
                }
            }
        } catch (SQLException e) { System.out.println("Error productos: " + e.getMessage()); }
        return productos;
    }
    // === COMPRA ===
    public static void guardarCompra(Compra c) {
        String sql = "INSERT OR REPLACE INTO compras VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Database.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, c.getId());
            pstmt.setLong(2, c.getFecha().getTime());
            pstmt.setDouble(3, c.getTotal());
            pstmt.setString(4, c.getEstado());
            pstmt.setLong(5, c.getCliente().getId());
            pstmt.executeUpdate();


            for (LineaCompra lc : c.getLineasCompra()) {
                String sqlLinea = "INSERT INTO lineas_compra (compraId, productoId, cantidad, precioUnit, subtotal) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement p = conn.prepareStatement(sqlLinea)) {
                    p.setLong(1, c.getId());
                    p.setLong(2, lc.getProducto().getId());
                    p.setInt(3, lc.getCantidad());
                    p.setDouble(4, lc.getPrecioUnit());
                    p.setDouble(5, lc.getSubtotal());
                    p.executeUpdate();
                }
            }
        } catch (SQLException e) { System.out.println("Error compra: " + e.getMessage()); }
    }

    public static List<Compra> cargarCompras(List<Cliente> clientes, List<Producto> productos) {
        List<Compra> list = new ArrayList<>();
        String sql = "SELECT * FROM compras";
        try (Connection conn = Database.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Cliente cli = clientes.stream().filter(c -> {
                    try {
                        return c.getId() == rs.getLong("clienteId");
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }).findFirst().orElse(null);
                if (cli == null) continue;
                Compra c = new Compra(rs.getLong("id"), new Date(rs.getLong("fecha")), rs.getDouble("total"), rs.getString("estado"));
                c.setCliente(cli);

                String sqlLinea = "SELECT * FROM lineas_compra WHERE compraId = ?";
                try (PreparedStatement p = conn.prepareStatement(sqlLinea)) {
                    p.setLong(1, c.getId());
                    try (ResultSet rsLinea = p.executeQuery()) {
                        while (rsLinea.next()) {
                            Producto prod = productos.stream().filter(pr -> {
                                try {
                                    return pr.getId() == rsLinea.getLong("productoId");
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                            }).findFirst().orElse(null);
                            if (prod != null) {
                                LineaCompra lc = new LineaCompra(rsLinea.getInt("cantidad"), rsLinea.getDouble("precioUnit"),
                                        rsLinea.getDouble("subtotal"), prod, c);
                                c.getLineasCompra().add(lc);
                            }
                        }
                    }
                }
                list.add(c);
            }
        } catch (SQLException e) { System.out.println("Error compras: " + e.getMessage()); }
        return list;
    }


    public static void guardarCarrito(Carrito c) {
        String sql = "INSERT OR REPLACE INTO carritos VALUES (?, ?, ?)";
        try (Connection conn = Database.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, c.getId());
            pstmt.setLong(2, c.getFechaCreacion().getTime());
            pstmt.setLong(3, c.getCliente() != null ? c.getCliente().getId() : 0);
            pstmt.executeUpdate();

            String deleteLineas = "DELETE FROM lineas_carrito WHERE carritoId = ?";
            try (PreparedStatement p = conn.prepareStatement(deleteLineas)) {
                p.setLong(1, c.getId());
                p.executeUpdate();
            }

            for (LineaCarrito lc : c.getLineasCarrito()) {
                String sqlLinea = "INSERT INTO lineas_carrito (carritoId, productoId, cantidad, subtotal) VALUES (?, ?, ?, ?)";
                try (PreparedStatement p = conn.prepareStatement(sqlLinea)) {
                    p.setLong(1, c.getId());
                    p.setLong(2, lc.getProducto().getId());
                    p.setInt(3, lc.getCantidad());
                    p.setDouble(4, lc.getSubtotal());
                    p.executeUpdate();
                }
            }
        } catch (SQLException e) { System.out.println("Error carrito: " + e.getMessage()); }
    }

    public static List<Carrito> cargarCarritos(List<Cliente> clientes, List<Producto> productos) {
        List<Carrito> list = new ArrayList<>();
        String sql = "SELECT * FROM carritos";
        try (Connection conn = Database.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                long clienteId = rs.getLong("clienteId");
                Cliente cli = clientes.stream().filter(c -> c.getId() == clienteId).findFirst().orElse(null);
                Carrito c = new Carrito(rs.getLong("id"), new Date(rs.getLong("fechaCreacion")));
                if (cli != null) c.setCliente(cli);

                String sqlLinea = "SELECT * FROM lineas_carrito WHERE carritoId = ?";
                try (PreparedStatement p = conn.prepareStatement(sqlLinea)) {
                    p.setLong(1, c.getId());
                    try (ResultSet rsLinea = p.executeQuery()) {
                        while (rsLinea.next()) {
                            Producto prod = productos.stream().filter(pr -> {
                                try {
                                    return pr.getId() == rsLinea.getLong("productoId");
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                            }).findFirst().orElse(null);
                            if (prod != null) {
                                LineaCarrito lc = new LineaCarrito(rsLinea.getInt("cantidad"), rsLinea.getDouble("subtotal"), prod, c);
                                c.getLineasCarrito().add(lc);
                            }
                        }
                    }
                }
                list.add(c);
            }
        } catch (SQLException e) { System.out.println("Error carritos: " + e.getMessage()); }
        return list;
    }

    public static void guardarMetodoPago(MetodoPago m) {
        String sql = "INSERT OR REPLACE INTO metodos_pago VALUES (?, ?, ?, ?)";
        try (Connection conn = Database.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, m.getId());
            pstmt.setString(2, m.getTipo());
            pstmt.setString(3, m.getTitular());
            pstmt.setString(4, m.getNumeroEnmascarado());
            pstmt.executeUpdate();
        } catch (SQLException e) { System.out.println("Error método pago: " + e.getMessage()); }
    }

    public static List<MetodoPago> cargarMetodosPago() {
        List<MetodoPago> list = new ArrayList<>();
        String sql = "SELECT * FROM metodos_pago";
        try (Connection conn = Database.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new MetodoPago(
                        rs.getLong("id"),
                        rs.getString("tipo"),
                        rs.getString("titular"),
                        rs.getString("numeroEnmascarado")
                ));
            }
        } catch (SQLException e) { System.out.println("Error métodos pago: " + e.getMessage()); }
        return list;
    }


    public static void guardarFabrica(Fabrica f) {
        String sql = "INSERT OR REPLACE INTO fabrica VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Database.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, f.getId());
            pstmt.setString(2, f.getPais());
            pstmt.setString(3, f.getCiudad());
            pstmt.setInt(4, f.getCapacidad());
            pstmt.setString(5, f.getNivelAutomatizacion());
            pstmt.executeUpdate();
        } catch (SQLException e) { System.out.println("Error fábrica: " + e.getMessage()); }
    }

    public static Fabrica cargarFabrica() {
        String sql = "SELECT * FROM fabrica LIMIT 1";
        try (Connection conn = Database.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return new Fabrica(
                        rs.getLong("id"),
                        rs.getString("pais"),
                        rs.getString("ciudad"),
                        rs.getInt("capacidad"),
                        rs.getString("nivelAutomatizacion")
                );
            }
        } catch (SQLException e) { System.out.println("Error fábrica: " + e.getMessage()); }
        return null;
    }


    public static void guardarTrabajador(TrabajadorEsclavizado t) {
        String sql = "INSERT OR REPLACE INTO trabajadores VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Database.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, t.getId());
            pstmt.setString(2, t.getNombre());
            pstmt.setString(3, t.getPaisOrigen());
            pstmt.setInt(4, t.getEdad());
            pstmt.setLong(5, t.getFechaCaptura().getTime());
            pstmt.setString(6, t.getSalud());
            pstmt.setString(7, t.getAsignadoA());
            pstmt.executeUpdate();
        } catch (SQLException e) { System.out.println("Error trabajador: " + e.getMessage()); }
    }

    public static List<TrabajadorEsclavizado> cargarTrabajadores() {
        List<TrabajadorEsclavizado> list = new ArrayList<>();
        String sql = "SELECT * FROM trabajadores";
        try (Connection conn = Database.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new TrabajadorEsclavizado(
                        rs.getLong("id"),
                        rs.getString("nombre"),
                        rs.getString("paisOrigen"),
                        rs.getInt("edad"),
                        new Date(rs.getLong("fechaCaptura")),
                        rs.getString("salud"),
                        rs.getString("asignadoA")
                ));
            }
        } catch (SQLException e) { System.out.println("Error trabajadores: " + e.getMessage()); }
        return list;
    }


    public static void guardarRegistroEsclavos(RegistroEsclavos r) {
        String sql = "INSERT OR REPLACE INTO registro_esclavos VALUES (?, ?, ?)";
        try (Connection conn = Database.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, r.getId());
            pstmt.setLong(2, r.getUltimoAcceso().getTime());
            pstmt.setString(3, r.getNivelCifrado());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error registro: " + e.getMessage());
        }
    }

    public static RegistroEsclavos cargarRegistroEsclavos() {
        String sql = "SELECT * FROM registro_esclavos LIMIT 1";
        try (Connection conn = Database.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return new RegistroEsclavos(
                        rs.getLong("id"),
                        new Date(rs.getLong("ultimoAcceso")),
                        rs.getString("nivelCifrado")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error registro: " + e.getMessage());
        }
        return null;
    }


    public static void guardarConsejoSombrio(ConsejoSombrio c) {
        String sql = "INSERT OR REPLACE INTO consejo_sombrio VALUES (?, ?)";
        try (Connection conn = Database.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, c.getId());
            pstmt.setString(2, c.getNombreClave());
            pstmt.executeUpdate();
        } catch (SQLException e) { System.out.println("Error consejo: " + e.getMessage()); }
    }

    public static ConsejoSombrio cargarConsejoSombrio() {
        String sql = "SELECT * FROM consejo_sombrio LIMIT 1";
        try (Connection conn = Database.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return new ConsejoSombrio(
                        rs.getLong("id"),
                        rs.getString("nombreClave")
                );
            }
        } catch (SQLException e) { System.out.println("Error consejo: " + e.getMessage()); }
        return null;
    }




}