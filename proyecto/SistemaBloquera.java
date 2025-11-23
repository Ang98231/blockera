import java.util.ArrayList;
import java.util.Date;
import javax.swing.*;


/**
 * Clase principal del Sistema de Gestión de Bloquera
 * Contiene la lógica del sistema y los datos
 */
public class SistemaBloquera {
    
    // --- Base de Datos del Sistema ---
    public static ArrayList<Usuario> listaUsuarios = new ArrayList<>();
    public static ArrayList<Cliente> listaClientes = new ArrayList<>();
    public static ArrayList<MateriaPrima> inventario = new ArrayList<>();
    public static ArrayList<Venta> listaVentas = new ArrayList<>();
    public static ArrayList<HistorialProducto> historialProduccion = new ArrayList<>();
    
    // --- Sesión Actual ---
    public static Usuario usuarioActual = null;

    /**
     * Método principal que inicia el sistema
     */
    public static void main(String[] args) {
        // Configurar Look and Feel del sistema
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Inicializar datos del sistema
        inicializarSistema();
        
        // Mostrar ventana de login
        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }

    /**
     * Inicializa el sistema con datos de ejemplo
     */
    public static void inicializarSistema() {
        // Crear usuarios del sistema con teléfonos
        listaUsuarios.add(new Usuario("admin", "admin123", 3, "2221234567")); // Administrador
        listaUsuarios.add(new Usuario("dueno", "dueno123", 2, "2227654321")); // Dueño
        listaUsuarios.add(new Usuario("secretaria", "sec123", 1, "2229876543")); // Secretaria
        
        // Cargar materias primas
        inventario.add(new MateriaPrima("Arena", 1000, 150.0, "m³"));
        inventario.add(new MateriaPrima("Cemento", 500, 3000.0, "ton"));
        inventario.add(new MateriaPrima("Grava", 800, 200.0, "m³"));
        inventario.add(new MateriaPrima("Agua", 2000, 10.0, "lt"));
        
        // Cargar productos terminados
        inventario.add(new Producto("Bloque Estándar", 2000, 2.5, "pza", 10.0));
        inventario.add(new Producto("Bovedilla", 1500, 5.0, "pza", 25.0));
        inventario.add(new Producto("Tabique", 3000, 1.5, "pza", 8.0));
        inventario.add(new Producto("Adocreto", 500, 8.0, "pza", 35.0));
        
        // Cargar clientes de ejemplo
        listaClientes.add(new Cliente("Juan Pérez", "2221234567", "Calle Falsa 123, Puebla"));
        listaClientes.add(new Cliente("Constructora SA", "2227654321", "Av. Reforma 456, Puebla"));
        listaClientes.add(new Cliente("María González", "2229876543", "Colonia Centro, Puebla"));
    }

    /**
     * Verifica las credenciales de un usuario
     */
    public static Usuario autenticarUsuario(String nombreUsuario, String contrasena) {
        for (Usuario u : listaUsuarios) {
            if (u.getNombreUsuario().equals(nombreUsuario) && 
                u.getContrasena().equals(contrasena)) {
                return u;
            }
        }
        return null;
    }

    /**
     * Verifica un usuario por nombre y teléfono para recuperación de contraseña
     */
    public static Usuario verificarUsuarioPorTelefono(String nombreUsuario, String telefono) {
        for (Usuario u : listaUsuarios) {
            if (u.getNombreUsuario().equals(nombreUsuario) && 
                u.getTelefono().equals(telefono)) {
                return u;
            }
        }
        return null;
    }

    /**
     * Verifica si el usuario actual tiene el nivel de permiso requerido
     */
    public static boolean verificarPermiso(int nivelRequerido) {
        return usuarioActual != null && usuarioActual.getNivel() >= nivelRequerido;
    }

    /**
     * Busca un cliente por nombre
     */
    public static Cliente buscarClientePorNombre(String nombre) {
        for (Cliente c : listaClientes) {
            if (c.getNombre().equalsIgnoreCase(nombre)) {
                return c;
            }
        }
        return null;
    }

    /**
     * Busca un producto por nombre
     */
    public static Producto buscarProductoPorNombre(String nombre) {
        for (MateriaPrima item : inventario) {
            if (item instanceof Producto && item.getNombre().equalsIgnoreCase(nombre)) {
                return (Producto) item;
            }
        }
        return null;
    }

    /**
     * Busca un item del inventario por nombre
     */
    public static MateriaPrima buscarItemInventarioPorNombre(String nombre) {
        for (MateriaPrima item : inventario) {
            if (item.getNombre().equalsIgnoreCase(nombre)) {
                return item;
            }
        }
        return null;
    }

    /**
     * Obtiene solo los productos del inventario
     */
    public static ArrayList<Producto> obtenerProductos() {
        ArrayList<Producto> productos = new ArrayList<>();
        for (MateriaPrima item : inventario) {
            if (item instanceof Producto) {
                productos.add((Producto) item);
            }
        }
        return productos;
    }

    /**
     * Obtiene solo las materias primas del inventario
     */
    public static ArrayList<MateriaPrima> obtenerMateriasPrimas() {
        ArrayList<MateriaPrima> materias = new ArrayList<>();
        for (MateriaPrima item : inventario) {
            if (!(item instanceof Producto)) {
                materias.add(item);
            }
        }
        return materias;
    }

    /**
     * Obtiene la descripción del nivel de usuario
     */
    public static String obtenerDescripcionNivel(int nivel) {
        switch (nivel) {
            case 1: return "Secretaria";
            case 2: return "Dueño";
            case 3: return "Administrador";
            default: return "Desconocido";
        }
    }

    /**
     * Cierra la sesión actual
     */
    public static void cerrarSesion() {
        usuarioActual = null;
    }
}


// ======================================================================
//                          CLASES DEL MODELO
// ======================================================================

/**
 * Clase Cliente - Representa a los clientes de la bloquera
 */
class Cliente {
    private String nombre;
    private String telefono;
    private String direccion;

    public Cliente(String nombre, String telefono, String direccion) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    public String getNombre() { return nombre; }
    public String getTelefono() { return telefono; }
    public String getDireccion() { return direccion; }
    
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    
    @Override
    public String toString() {
        return nombre;
    }
}


/**
 * Clase Usuario - Representa a los usuarios del sistema
 */
class Usuario {
    private String nombreUsuario;
    private String contrasena;
    private int nivel; // 1: Secretaria, 2: Dueño, 3: Admin
    private String telefono;

    public Usuario(String nombreUsuario, String contrasena, int nivel, String telefono) {
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.nivel = nivel;
        this.telefono = telefono;
    }

    public String getNombreUsuario() { return nombreUsuario; }
    public String getContrasena() { return contrasena; }
    public int getNivel() { return nivel; }
    public String getTelefono() { return telefono; }
    
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
    public void setNivel(int nivel) { this.nivel = nivel; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
}


/**
 * Clase MateriaPrima - Representa materias primas del inventario
 */
class MateriaPrima {
    private String nombre;
    private int cantidad;
    private double precio;
    private String unidad;

    public MateriaPrima(String nombre, int cantidad, double precio, String unidad) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
        this.unidad = unidad;
    }

    public String getNombre() { return nombre; }
    public int getCantidad() { return cantidad; }
    public double getPrecio() { return precio; }
    public String getUnidad() { return unidad; }
    
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public void setPrecio(double precio) { this.precio = precio; }
    public void setUnidad(String unidad) { this.unidad = unidad; }
}


/**
 * Clase Producto - Representa productos terminados (hereda de MateriaPrima)
 */
class Producto extends MateriaPrima {
    private double costoVenta;

    public Producto(String nombre, int cantidad, double precioBase, String unidad, double costoVenta) {
        super(nombre, cantidad, precioBase, unidad);
        this.costoVenta = costoVenta;
    }

    public double getCostoVenta() { return costoVenta; }
    public void setCostoVenta(double costoVenta) { this.costoVenta = costoVenta; }

    public boolean consultarDisponibilidad(int cantidadSolicitada) {
        return this.getCantidad() >= cantidadSolicitada;
    }
}


/**
 * Clase Venta - Representa una transacción de venta
 */
class Venta {
    private Date fecha;
    private double total;
    private String estado;
    private Cliente cliente;
    private Usuario vendedor;
    private ArrayList<DetalleVenta> detalles;

    public Venta(Date fecha, String estado, Cliente cliente, Usuario vendedor) {
        this.fecha = fecha;
        this.total = 0.0;
        this.estado = estado;
        this.cliente = cliente;
        this.vendedor = vendedor;
        this.detalles = new ArrayList<>();
    }

    public void agregarDetalle(DetalleVenta detalle) {
        this.detalles.add(detalle);
        calcularTotal();
    }
    
    private void calcularTotal() {
        this.total = 0.0;
        for (DetalleVenta d : detalles) {
            this.total += d.calcularTotal();
        }
    }

    public Date getFecha() { return fecha; }
    public double getTotal() { return total; }
    public String getEstado() { return estado; }
    public Cliente getCliente() { return cliente; }
    public Usuario getVendedor() { return vendedor; }
    public ArrayList<DetalleVenta> getDetalles() { return detalles; }
    
    public void setEstado(String estado) { this.estado = estado; }
}


/**
 * Clase DetalleVenta - Representa una línea de producto en una venta
 */
class DetalleVenta {
    private int cantidad;
    private Producto producto;

    public DetalleVenta(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public double calcularTotal() {
        return this.producto.getCostoVenta() * this.cantidad;
    }

    public int getCantidad() { return cantidad; }
    public Producto getProducto() { return producto; }
}


/**
 * Clase HistorialProducto - Registra la producción de productos
 */
class HistorialProducto {
    private Date fecha;
    private String nombreProducto;
    private int cantidadProducida;

    public HistorialProducto(Date fecha, String nombreProducto, int cantidadProducida) {
        this.fecha = fecha;
        this.nombreProducto = nombreProducto;
        this.cantidadProducida = cantidadProducida;
    }

    public Date getFecha() { return fecha; }
    public String getNombreProducto() { return nombreProducto; }
    public int getCantidadProducida() { return cantidadProducida; }
}