
import java.sql.*;

public class Database {
    private static final String URL = "jdbc:sqlite:ecommerce.db";

    public static Connection connect() {
        try {
            return DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.out.println("Error de conexión: " + e.getMessage());
            return null;
        }
    }

    public static void crearTablas() {
        String[] tablas = {

                """
            CREATE TABLE IF NOT EXISTS usuarios (
                id INTEGER PRIMARY KEY,
                tipo TEXT NOT NULL,
                nombre TEXT,
                email TEXT,
                passwordHash TEXT,
                rol TEXT,
                fechaRegistro INTEGER,
                estadoCuenta TEXT,
                direccionEnvio TEXT,
                telefono TEXT,
                permisosEdicion TEXT,
                nivelAcceso TEXT,
                claveMaestra TEXT,
                fechaCoronacion INTEGER,
                especialidad TEXT
            )
            """,

                """
            CREATE TABLE IF NOT EXISTS categorias (
                id INTEGER PRIMARY KEY,
                nombre TEXT,
                descripcion TEXT
            )
            """,

                """
            CREATE TABLE IF NOT EXISTS productos (
                id INTEGER PRIMARY KEY,
                nombre TEXT,
                descripcion TEXT,
                precio REAL,
                stock INTEGER,
                fechaLanzamiento INTEGER,
                categoriaId INTEGER
            )
            """,

                """
            CREATE TABLE IF NOT EXISTS compras (
                id INTEGER PRIMARY KEY,
                fecha INTEGER,
                total REAL,
                estado TEXT,
                clienteId INTEGER
            )
            """,

                """
            CREATE TABLE IF NOT EXISTS lineas_compra (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                compraId INTEGER,
                productoId INTEGER,
                cantidad INTEGER,
                precioUnit REAL,
                subtotal REAL
            )
            """,

                """
            CREATE TABLE IF NOT EXISTS carritos (
                id INTEGER PRIMARY KEY,
                fechaCreacion INTEGER,
                clienteId INTEGER
            )
            """,

                """
            CREATE TABLE IF NOT EXISTS lineas_carrito (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                carritoId INTEGER,
                productoId INTEGER,
                cantidad INTEGER,
                subtotal REAL
            )
            """,

                """
            CREATE TABLE IF NOT EXISTS metodos_pago (
                id INTEGER PRIMARY KEY,
                tipo TEXT,
                titular TEXT,
                numeroEnmascarado TEXT
            )
            """,

                """
            CREATE TABLE IF NOT EXISTS fabrica (
                id INTEGER PRIMARY KEY,
                pais TEXT,
                ciudad TEXT,
                capacidad INTEGER,
                nivelAutomatizacion TEXT
            )
            """,

                """
            CREATE TABLE IF NOT EXISTS trabajadores (
                id INTEGER PRIMARY KEY,
                nombre TEXT,
                paisOrigen TEXT,
                edad INTEGER,
                fechaCaptura INTEGER,
                salud TEXT,
                asignadoA TEXT
            )
            """,

                """
            CREATE TABLE IF NOT EXISTS registro_esclavos (
                id INTEGER PRIMARY KEY,
                ultimoAcceso INTEGER,
                nivelCifrado TEXT
            )
            """,

                """
            CREATE TABLE IF NOT EXISTS consejo_sombrio (
                id INTEGER PRIMARY KEY,
                nombreClave TEXT
            )
            """
        };

        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            for (String sql : tablas) {
                stmt.execute(sql);
            }
            System.out.println("Base de datos inicializada.");
        } catch (SQLException e) {
            System.out.println("Error creando tablas: " + e.getMessage());
        }
    }
}
