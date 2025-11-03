
import java.util.*;

public class Main {


    private static List<Usuario> usuarios = new ArrayList<>();
    private static List<Categoria> categorias = new ArrayList<>();
    private static List<Producto> productos = new ArrayList<>();
    private static List<Compra> compras = new ArrayList<>();
    private static List<Carrito> carritos = new ArrayList<>();
    private static List<MetodoPago> metodosPago = new ArrayList<>();
    private static Fabrica fabrica;
    private static List<TrabajadorEsclavizado> trabajadores = new ArrayList<>();
    private static RegistroEsclavos registroEsclavos;
    private static ConsejoSombrio consejoSombrio;


    private static final Scanner scanner = new Scanner(System.in);


    private static Usuario usuarioActual = null;

    public static void main(String[] args) {
        Database.crearTablas();
        cargarDatos();
        inicializarDatosEjemplo();
        menuPrincipal();
        guardarDatos();
    }


    private static void cargarDatos() {
        categorias = Persistencia.cargarCategorias();
        usuarios = Persistencia.cargarUsuarios();
        List<Cliente> clientes = usuarios.stream()
                .filter(u -> u instanceof Cliente)
                .map(u -> (Cliente) u)
                .toList();
        productos = Persistencia.cargarProductos(categorias);
        compras = Persistencia.cargarCompras(clientes, productos);
        carritos = Persistencia.cargarCarritos(clientes, productos);
        metodosPago = Persistencia.cargarMetodosPago();
        fabrica = Persistencia.cargarFabrica();
        trabajadores = Persistencia.cargarTrabajadores();
        registroEsclavos = Persistencia.cargarRegistroEsclavos();
        consejoSombrio = Persistencia.cargarConsejoSombrio();

        System.out.println("Datos cargados desde SQLite.");
    }


    private static void guardarDatos() {
        for (Usuario u : usuarios) Persistencia.guardarUsuario(u);
        for (Categoria c : categorias) Persistencia.guardarCategoria(c);
        for (Producto p : productos) Persistencia.guardarProducto(p);
        for (Compra c : compras) Persistencia.guardarCompra(c);
        for (Carrito c : carritos) Persistencia.guardarCarrito(c);
        for (MetodoPago m : metodosPago) Persistencia.guardarMetodoPago(m);
        Persistencia.guardarFabrica(fabrica);
        for (TrabajadorEsclavizado t : trabajadores) Persistencia.guardarTrabajador(t);
        Persistencia.guardarRegistroEsclavos(registroEsclavos);
        Persistencia.guardarConsejoSombrio(consejoSombrio);

        System.out.println("Datos guardados en SQLite.");
    }


    private static void inicializarDatosEjemplo() {
        if (categorias.isEmpty()) {
            Categoria cat1 = new Categoria(1, "Electrónica", "Dispositivos electrónicos");
            Categoria cat2 = new Categoria(2, "Ropa", "Prendas de vestir");
            categorias.add(cat1);
            categorias.add(cat2);
            Persistencia.guardarCategoria(cat1);
            Persistencia.guardarCategoria(cat2);
        }

        if (usuarios.isEmpty()) {
            Cliente cliente = new Cliente(1, "Ana López", "ana@mail.com", "123hash", "Cliente",
                    new Date(), "Activa", "Calle 123, CDMX", "555-1234");
            usuarios.add(cliente);
            Persistencia.guardarUsuario(cliente);
        }

        if (productos.isEmpty()) {
            Categoria cat = categorias.get(0);
            Producto p = new Producto(1, "Laptop X1", "Potente laptop", 15000.0, 10, new Date(), cat);
            productos.add(p);
            Persistencia.guardarProducto(p);
        }

        if (fabrica == null) {
            fabrica = new Fabrica(1, "China", "Shenzhen", 1000, "Alta");
            Persistencia.guardarFabrica(fabrica);
        }

        if (consejoSombrio == null) {
            consejoSombrio = new ConsejoSombrio(1, "Oscuridad Eterna");
            Persistencia.guardarConsejoSombrio(consejoSombrio);
        }
    }


    private static void menuPrincipal() {
        while (true) {
            System.out.println("\n=== E-COMMERCE ===");
            System.out.println("1. Registrarse");
            System.out.println("2. Iniciar sesión");
            System.out.println("3. Salir");
            System.out.print("Opción: ");
            int op = scanner.nextInt();
            scanner.nextLine();

            switch (op) {
                case 1 -> registrarse();
                case 2 -> iniciarSesion();
                case 3 -> {
                    System.out.println("¡Gracias por usar el sistema!");
                    return;
                }
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    private static void registrarse() {
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Contraseña: ");
        String pass = scanner.nextLine();

        long id = usuarios.stream().mapToLong(Usuario::getId).max().orElse(0) + 1;
        Cliente cliente = new Cliente(id, nombre, email, hash(pass), "Cliente",
                new Date(), "Activa", "", "");
        usuarios.add(cliente);
        Persistencia.guardarUsuario(cliente);
        System.out.println("¡Registro exitoso!");
    }

    private static void iniciarSesion() {
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Contraseña: ");
        String pass = scanner.nextLine();

        usuarioActual = usuarios.stream()
                .filter(u -> u.getEmail().equals(email) && u.getPasswordHash().equals(hash(pass)))
                .findFirst().orElse(null);

        if (usuarioActual != null) {
            System.out.println("¡Bienvenido, " + usuarioActual.getNombre() + "!");
            menuUsuario();
        } else {
            System.out.println("Credenciales incorrectas.");
        }
    }

    private static void menuUsuario() {
        while (true) {
            System.out.println("\n--- Menú de " + usuarioActual.getNombre() + " ---");
            System.out.println("1. Ver productos");
            System.out.println("2. Ver carrito");
            System.out.println("3. Cerrar sesión");
            System.out.print("Opción: ");
            int op = scanner.nextInt();
            scanner.nextLine();

            switch (op) {
                case 1 -> verProductos();
                case 2 -> verCarrito();
                case 3 -> {
                    usuarioActual = null;
                    return;
                }
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    private static void verProductos() {
        System.out.println("\n=== PRODUCTOS ===");
        for (Producto p : productos) {
            System.out.printf("%d. %s - $%.2f (%d en stock)\n", p.getId(), p.getNombre(), p.getPrecio(), p.getStock());
        }
    }

    private static void verCarrito() {
        Cliente cliente = (Cliente) usuarioActual;
        Carrito carrito = carritos.stream()
                .filter(c -> c.getCliente() != null && c.getCliente().getId() == cliente.getId())
                .findFirst().orElse(null);

        if (carrito == null || carrito.getLineasCarrito().isEmpty()) {
            System.out.println("Tu carrito está vacío.");
            return;
        }

        System.out.println("\n=== TU CARRITO ===");
        double total = 0;
        for (LineaCarrito lc : carrito.getLineasCarrito()) {
            System.out.printf("%s x%d = $%.2f\n", lc.getProducto().getNombre(), lc.getCantidad(), lc.getSubtotal());
            total += lc.getSubtotal();
        }
        System.out.printf("TOTAL: $%.2f\n", total);
    }


    private static String hash(String pass) {
        return Integer.toHexString(pass.hashCode());
    }

}