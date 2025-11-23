import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Ventana principal del sistema con todas las funcionalidades
 */
public class MainFrame extends JFrame {
    
    private JPanel panelContenido;
    private JLabel lblUsuario, lblNivel;
    
    // Colores del tema mejorados
    private final Color COLOR_PRIMARIO = new Color(41, 128, 185);      // Azul más oscuro
    private final Color COLOR_EXITO = new Color(39, 174, 96);          // Verde más oscuro
    private final Color COLOR_PELIGRO = new Color(192, 57, 43);        // Rojo más oscuro
    private final Color COLOR_ADVERTENCIA = new Color(211, 84, 0);     // Naranja más oscuro
    private final Color COLOR_INFO = new Color(52, 73, 94);            // Gris azulado oscuro
    private final Color COLOR_FONDO_CLARO = new Color(245, 246, 247);  // Fondo claro
    private final Color COLOR_TEXTO_OSCURO = new Color(33, 37, 41);    // Negro casi puro
    private final Color COLOR_MENU_LATERAL = new Color(34, 47, 62);    // Azul oscuro profundo
    private final Color COLOR_MENU_HOVER = new Color(52, 73, 94);      // Azul gris para hover

    public MainFrame() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Sistema Bloquera El Fuerte - Panel Principal");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel lateral (menú)
        JPanel panelLateral = crearPanelLateral();
        add(panelLateral, BorderLayout.WEST);

        // Panel superior (barra de información)
        JPanel panelSuperior = crearPanelSuperior();
        add(panelSuperior, BorderLayout.NORTH);

        // Panel de contenido (cambia según la opción seleccionada)
        panelContenido = new JPanel(new BorderLayout());
        panelContenido.setBackground(COLOR_FONDO_CLARO);
        mostrarPanelBienvenida();
        add(panelContenido, BorderLayout.CENTER);
    }

    /**
     * Crea el panel lateral con el menú
     */
    private JPanel crearPanelLateral() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(COLOR_MENU_LATERAL);
        panel.setPreferredSize(new Dimension(250, 0));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        // Logo/Título
        JLabel lblTitulo = new JLabel("BLOQUERA", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitulo.setMaximumSize(new Dimension(230, 40));
        panel.add(lblTitulo);

        JLabel lblSubtitulo = new JLabel("EL FUERTE", SwingConstants.CENTER);
        lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 15));
        lblSubtitulo.setForeground(new Color(189, 195, 199));
        lblSubtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblSubtitulo.setMaximumSize(new Dimension(230, 30));
        panel.add(lblSubtitulo);

        panel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Botones del menú
        agregarBotonMenu(panel, "Inicio", e -> mostrarPanelBienvenida());
        agregarBotonMenu(panel, "Gestión de Ventas", e -> mostrarPanelVentas());
        agregarBotonMenu(panel, "Consultar Inventario", e -> mostrarPanelInventario());
        agregarBotonMenu(panel, "Historial Producción", e -> mostrarPanelHistorialProduccion());
        
        if (SistemaBloquera.verificarPermiso(2)) {
            agregarSeparador(panel);
            agregarBotonMenu(panel, "Gestión Inventario", e -> mostrarPanelGestionInventario());
            agregarBotonMenu(panel, "Registrar Producción", e -> mostrarPanelRegistrarProduccion());
        }
        
        if (SistemaBloquera.verificarPermiso(3)) {
            agregarSeparador(panel);
            agregarBotonMenu(panel, "Gestión Usuarios", e -> mostrarPanelUsuarios());
        }

        panel.add(Box.createVerticalGlue());
        
        agregarSeparador(panel);
        JButton btnCerrarSesion = crearBotonMenu("Cerrar Sesión");
        btnCerrarSesion.setBackground(COLOR_PELIGRO);
        btnCerrarSesion.addActionListener(e -> cerrarSesion());
        panel.add(btnCerrarSesion);

        return panel;
    }

    /**
     * Crea el panel superior con información del usuario
     */
    private JPanel crearPanelSuperior() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(206, 212, 218)),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));

        // Info del usuario
        JPanel panelInfo = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelInfo.setOpaque(false);

        lblUsuario = new JLabel("Usuario: " + SistemaBloquera.usuarioActual.getNombreUsuario());
        lblUsuario.setFont(new Font("Arial", Font.BOLD, 14));
        lblUsuario.setForeground(COLOR_TEXTO_OSCURO);
        panelInfo.add(lblUsuario);

        panelInfo.add(new JLabel("  |  "));

        String nivel = SistemaBloquera.obtenerDescripcionNivel(SistemaBloquera.usuarioActual.getNivel());
        lblNivel = new JLabel("Nivel: " + nivel);
        lblNivel.setFont(new Font("Arial", Font.PLAIN, 14));
        lblNivel.setForeground(new Color(73, 80, 87));
        panelInfo.add(lblNivel);

        panel.add(panelInfo, BorderLayout.EAST);

        return panel;
    }

    /**
     * Agrega un botón al menú lateral
     */
    private void agregarBotonMenu(JPanel panel, String texto, ActionListener listener) {
        JButton btn = crearBotonMenu(texto);
        btn.addActionListener(listener);
        panel.add(btn);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
    }

    /**
     * Crea un botón estilizado para el menú
     */
    private JButton crearBotonMenu(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Arial", Font.PLAIN, 14));
        btn.setForeground(Color.BLACK);
        btn.setBackground(COLOR_MENU_LATERAL);
        btn.setBorder(BorderFactory.createEmptyBorder(12, 15, 12, 15));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setMaximumSize(new Dimension(230, 45));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setHorizontalAlignment(SwingConstants.LEFT);

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(COLOR_MENU_HOVER);
            }
            public void mouseExited(MouseEvent e) {
                btn.setBackground(COLOR_MENU_LATERAL);
            }
        });

        return btn;
    }

    /**
     * Agrega un separador visual al menú
     */
    private void agregarSeparador(JPanel panel) {
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        JSeparator sep = new JSeparator();
        sep.setMaximumSize(new Dimension(230, 1));
        sep.setForeground(new Color(73, 80, 87));
        panel.add(sep);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
    }

    // ============================================================
    //                    PANELES DE CONTENIDO
    // ============================================================

    /**
     * Muestra el panel de bienvenida
     */
    private void mostrarPanelBienvenida() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(COLOR_FONDO_CLARO);

        JPanel panelCentro = new JPanel();
        panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));
        panelCentro.setBackground(Color.WHITE);
        panelCentro.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(206, 212, 218)),
            BorderFactory.createEmptyBorder(40, 40, 40, 40)
        ));

        JLabel lblBienvenida = new JLabel("¡Bienvenido al Sistema!");
        lblBienvenida.setFont(new Font("Arial", Font.BOLD, 32));
        lblBienvenida.setForeground(COLOR_TEXTO_OSCURO);
        lblBienvenida.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelCentro.add(lblBienvenida);

        panelCentro.add(Box.createRigidArea(new Dimension(0, 20)));

        JLabel lblUsuario = new JLabel("Usuario: " + SistemaBloquera.usuarioActual.getNombreUsuario());
        lblUsuario.setFont(new Font("Arial", Font.PLAIN, 18));
        lblUsuario.setForeground(new Color(73, 80, 87));
        lblUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelCentro.add(lblUsuario);

        String nivel = SistemaBloquera.obtenerDescripcionNivel(SistemaBloquera.usuarioActual.getNivel());
        JLabel lblNivel = new JLabel("Nivel: " + nivel);
        lblNivel.setFont(new Font("Arial", Font.PLAIN, 18));
        lblNivel.setForeground(new Color(73, 80, 87));
        lblNivel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelCentro.add(lblNivel);

        panelCentro.add(Box.createRigidArea(new Dimension(0, 30)));

        JLabel lblInstrucciones = new JLabel("Seleccione una opción del menú lateral");
        lblInstrucciones.setFont(new Font("Arial", Font.ITALIC, 14));
        lblInstrucciones.setForeground(new Color(108, 117, 125));
        lblInstrucciones.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelCentro.add(lblInstrucciones);

        panel.add(panelCentro);
        cambiarPanel(panel);
    }

    /**
     * Muestra el panel de gestión de ventas
     */
    private void mostrarPanelVentas() {
        if (!SistemaBloquera.verificarPermiso(1)) {
            mostrarMensajeError("No tiene permisos para acceder a esta función");
            return;
        }

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(COLOR_FONDO_CLARO);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título
        JLabel lblTitulo = new JLabel("Gestión de Ventas");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(COLOR_TEXTO_OSCURO);
        panel.add(lblTitulo, BorderLayout.NORTH);

        // Panel de nueva venta
        JPanel panelNuevaVenta = new JPanel(new BorderLayout(10, 10));
        panelNuevaVenta.setBackground(Color.WHITE);
        panelNuevaVenta.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(206, 212, 218)),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        JButton btnNuevaVenta = new JButton("Nueva Venta");
        btnNuevaVenta.setFont(new Font("Arial", Font.BOLD, 16));
        btnNuevaVenta.setBackground(COLOR_EXITO);
        btnNuevaVenta.setForeground(Color.BLACK);
        btnNuevaVenta.setFocusPainted(false);
        btnNuevaVenta.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnNuevaVenta.addActionListener(e -> abrirDialogoNuevaVenta());

        panelNuevaVenta.add(btnNuevaVenta, BorderLayout.NORTH);

        // Tabla de ventas
        String[] columnas = {"Fecha", "Cliente", "Total", "Estado", "Vendedor"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        for (Venta v : SistemaBloquera.listaVentas) {
            modelo.addRow(new Object[]{
                sdf.format(v.getFecha()),
                v.getCliente().getNombre(),
                String.format("$%.2f", v.getTotal()),
                v.getEstado(),
                v.getVendedor().getNombreUsuario()
            });
        }

        JTable tabla = new JTable(modelo);
        tabla.setRowHeight(30);
        tabla.setFont(new Font("Arial", Font.PLAIN, 13));
        tabla.setForeground(COLOR_TEXTO_OSCURO);
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tabla.getTableHeader().setForeground(COLOR_TEXTO_OSCURO);
        JScrollPane scroll = new JScrollPane(tabla);

        panelNuevaVenta.add(scroll, BorderLayout.CENTER);
        panel.add(panelNuevaVenta, BorderLayout.CENTER);

        cambiarPanel(panel);
    }

    /**
     * Abre el diálogo para crear una nueva venta
     */
    private void abrirDialogoNuevaVenta() {
        JDialog dialog = new JDialog(this, "Nueva Venta", true);
        dialog.setSize(800, 600);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout(10, 10));

        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel cliente
        JPanel panelCliente = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelCliente.setBorder(BorderFactory.createTitledBorder("Cliente"));
        
        JComboBox<Cliente> cmbClientes = new JComboBox<>();
        cmbClientes.setPreferredSize(new Dimension(200, 30));
        for (Cliente c : SistemaBloquera.listaClientes) {
            cmbClientes.addItem(c);
        }
        
        JButton btnNuevoCliente = new JButton("Nuevo Cliente");
        btnNuevoCliente.setBackground(COLOR_PRIMARIO);
        btnNuevoCliente.setForeground(Color.BLACK);
        btnNuevoCliente.setFocusPainted(false);
        btnNuevoCliente.addActionListener(e -> {
            Cliente nuevoCliente = crearNuevoCliente();
            if (nuevoCliente != null) {
                cmbClientes.addItem(nuevoCliente);
                cmbClientes.setSelectedItem(nuevoCliente);
            }
        });
        
        panelCliente.add(new JLabel("Seleccionar:"));
        panelCliente.add(cmbClientes);
        panelCliente.add(btnNuevoCliente);
        
        panelPrincipal.add(panelCliente, BorderLayout.NORTH);

        // Tabla de productos en la venta
        String[] columnas = {"Producto", "Cantidad", "Precio Unit.", "Subtotal"};
        DefaultTableModel modeloVenta = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable tablaVenta = new JTable(modeloVenta);
        tablaVenta.setRowHeight(25);
        tablaVenta.setFont(new Font("Arial", Font.PLAIN, 13));
        tablaVenta.setForeground(COLOR_TEXTO_OSCURO);
        JScrollPane scrollVenta = new JScrollPane(tablaVenta);
        scrollVenta.setBorder(BorderFactory.createTitledBorder("Productos en la venta"));
        
        panelPrincipal.add(scrollVenta, BorderLayout.CENTER);

        // Panel de productos disponibles y botones
        JPanel panelInferior = new JPanel(new BorderLayout(10, 10));
        
        // Productos disponibles
        JPanel panelProductos = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelProductos.setBorder(BorderFactory.createTitledBorder("Agregar Producto"));
        
        JComboBox<Producto> cmbProductos = new JComboBox<>();
        cmbProductos.setPreferredSize(new Dimension(200, 30));
        for (Producto p : SistemaBloquera.obtenerProductos()) {
            cmbProductos.addItem(p);
        }
        cmbProductos.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, 
                    int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Producto) {
                    Producto p = (Producto) value;
                    setText(p.getNombre() + " (Stock: " + p.getCantidad() + ")");
                }
                return this;
            }
        });
        
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1, 1, 10000, 1);
        JSpinner spnCantidad = new JSpinner(spinnerModel);
        spnCantidad.setPreferredSize(new Dimension(80, 30));
        
        JButton btnAgregar = new JButton("Agregar");
        btnAgregar.setBackground(COLOR_PRIMARIO);
        btnAgregar.setForeground(Color.WHITE);
        btnAgregar.setFocusPainted(false);
        
        panelProductos.add(new JLabel("Producto:"));
        panelProductos.add(cmbProductos);
        panelProductos.add(new JLabel("Cantidad:"));
        panelProductos.add(spnCantidad);
        panelProductos.add(btnAgregar);
        
        panelInferior.add(panelProductos, BorderLayout.NORTH);

        // Panel de total y botones
        JPanel panelBotones = new JPanel(new BorderLayout());
        
        JLabel lblTotal = new JLabel("TOTAL: $0.00", SwingConstants.RIGHT);
        lblTotal.setFont(new Font("Arial", Font.BOLD, 20));
        lblTotal.setForeground(COLOR_EXITO);
        panelBotones.add(lblTotal, BorderLayout.NORTH);
        
        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        
        JButton btnEliminar = new JButton("Eliminar Seleccionado");
        btnEliminar.setBackground(COLOR_PELIGRO);
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setFocusPainted(false);
        
        JButton btnFinalizar = new JButton("Finalizar Venta");
        btnFinalizar.setBackground(COLOR_EXITO);
        btnFinalizar.setForeground(Color.WHITE);
        btnFinalizar.setFocusPainted(false);
        btnFinalizar.setFont(new Font("Arial", Font.BOLD, 14));
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(COLOR_INFO);
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setFocusPainted(false);
        
        panelAcciones.add(btnEliminar);
        panelAcciones.add(btnCancelar);
        panelAcciones.add(btnFinalizar);
        
        panelBotones.add(panelAcciones, BorderLayout.SOUTH);
        panelInferior.add(panelBotones, BorderLayout.SOUTH);
        
        panelPrincipal.add(panelInferior, BorderLayout.SOUTH);
        dialog.add(panelPrincipal);

        // Eventos
        btnAgregar.addActionListener(e -> {
            Producto productoSeleccionado = (Producto) cmbProductos.getSelectedItem();
            int cantidad = (int) spnCantidad.getValue();
            
            if (productoSeleccionado != null) {
                if (productoSeleccionado.consultarDisponibilidad(cantidad)) {
                    double subtotal = productoSeleccionado.getCostoVenta() * cantidad;
                    modeloVenta.addRow(new Object[]{
                        productoSeleccionado.getNombre(),
                        cantidad,
                        String.format("$%.2f", productoSeleccionado.getCostoVenta()),
                        String.format("$%.2f", subtotal)
                    });
                    
                    // Actualizar total
                    double total = 0;
                    for (int i = 0; i < modeloVenta.getRowCount(); i++) {
                        String strSubtotal = modeloVenta.getValueAt(i, 3).toString().replace("$", "");
                        total += Double.parseDouble(strSubtotal);
                    }
                    lblTotal.setText(String.format("TOTAL: $%.2f", total));
                    
                    spnCantidad.setValue(1);
                } else {
                    JOptionPane.showMessageDialog(dialog,
                        "Stock insuficiente. Disponible: " + productoSeleccionado.getCantidad(),
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnEliminar.addActionListener(e -> {
            int filaSeleccionada = tablaVenta.getSelectedRow();
            if (filaSeleccionada >= 0) {
                modeloVenta.removeRow(filaSeleccionada);
                
                // Recalcular total
                double total = 0;
                for (int i = 0; i < modeloVenta.getRowCount(); i++) {
                    String strSubtotal = modeloVenta.getValueAt(i, 3).toString().replace("$", "");
                    total += Double.parseDouble(strSubtotal);
                }
                lblTotal.setText(String.format("TOTAL: $%.2f", total));
            }
        });

        btnFinalizar.addActionListener(e -> {
            if (modeloVenta.getRowCount() == 0) {
                JOptionPane.showMessageDialog(dialog, 
                    "Debe agregar al menos un producto", 
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            Cliente clienteSeleccionado = (Cliente) cmbClientes.getSelectedItem();
            if (clienteSeleccionado == null) {
                JOptionPane.showMessageDialog(dialog, 
                    "Debe seleccionar un cliente", 
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Crear la venta
            Venta nuevaVenta = new Venta(new Date(), "Completada", 
                clienteSeleccionado, SistemaBloquera.usuarioActual);
            
            // Agregar detalles y actualizar stock
            for (int i = 0; i < modeloVenta.getRowCount(); i++) {
                String nombreProducto = modeloVenta.getValueAt(i, 0).toString();
                int cantidad = Integer.parseInt(modeloVenta.getValueAt(i, 1).toString());
                
                Producto producto = SistemaBloquera.buscarProductoPorNombre(nombreProducto);
                if (producto != null) {
                    DetalleVenta detalle = new DetalleVenta(producto, cantidad);
                    nuevaVenta.agregarDetalle(detalle);
                    producto.setCantidad(producto.getCantidad() - cantidad);
                }
            }
            
            SistemaBloquera.listaVentas.add(nuevaVenta);
            
            JOptionPane.showMessageDialog(dialog, 
                String.format("Venta registrada exitosamente\nTotal: $%.2f", nuevaVenta.getTotal()),
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
            
            dialog.dispose();
            mostrarPanelVentas(); // Refrescar
        });

        btnCancelar.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }

    /**
     * Crea un nuevo cliente
     */
    private Cliente crearNuevoCliente() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JTextField txtNombre = new JTextField();
        JTextField txtTelefono = new JTextField();
        JTextField txtDireccion = new JTextField();
        
        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);
        panel.add(new JLabel("Teléfono:"));
        panel.add(txtTelefono);
        panel.add(new JLabel("Dirección:"));
        panel.add(txtDireccion);
        
        int result = JOptionPane.showConfirmDialog(this, panel, 
            "Nuevo Cliente", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            String nombre = txtNombre.getText().trim();
            String telefono = txtTelefono.getText().trim();
            String direccion = txtDireccion.getText().trim();
            
            if (!nombre.isEmpty()) {
                Cliente nuevoCliente = new Cliente(nombre, telefono, direccion);
                SistemaBloquera.listaClientes.add(nuevoCliente);
                return nuevoCliente;
            }
        }
        
        return null;
    }

    /**
     * Muestra el panel de consulta de inventario
     */
    private void mostrarPanelInventario() {
        if (!SistemaBloquera.verificarPermiso(1)) {
            mostrarMensajeError("No tiene permisos para acceder a esta función");
            return;
        }

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(COLOR_FONDO_CLARO);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitulo = new JLabel("Consultar Inventario");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(COLOR_TEXTO_OSCURO);
        panel.add(lblTitulo, BorderLayout.NORTH);

        // Pestañas para productos y materias primas
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 14));

        // Tabla de productos
        String[] columnasProductos = {"Nombre", "Cantidad", "Unidad", "Precio Venta"};
        DefaultTableModel modeloProductos = new DefaultTableModel(columnasProductos, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (Producto p : SistemaBloquera.obtenerProductos()) {
            modeloProductos.addRow(new Object[]{
                p.getNombre(),
                p.getCantidad(),
                p.getUnidad(),
                String.format("$%.2f", p.getCostoVenta())
            });
        }

        JTable tablaProductos = new JTable(modeloProductos);
        tablaProductos.setRowHeight(30);
        tablaProductos.setFont(new Font("Arial", Font.PLAIN, 13));
        tablaProductos.setForeground(COLOR_TEXTO_OSCURO);
        tablaProductos.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tablaProductos.getTableHeader().setForeground(COLOR_TEXTO_OSCURO);
        JScrollPane scrollProductos = new JScrollPane(tablaProductos);

        // Tabla de materias primas
        String[] columnasMaterias = {"Nombre", "Cantidad", "Unidad", "Precio Compra"};
        DefaultTableModel modeloMaterias = new DefaultTableModel(columnasMaterias, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (MateriaPrima m : SistemaBloquera.obtenerMateriasPrimas()) {
            modeloMaterias.addRow(new Object[]{
                m.getNombre(),
                m.getCantidad(),
                m.getUnidad(),
                String.format("$%.2f", m.getPrecio())
            });
        }

        JTable tablaMaterias = new JTable(modeloMaterias);
        tablaMaterias.setRowHeight(30);
        tablaMaterias.setFont(new Font("Arial", Font.PLAIN, 13));
        tablaMaterias.setForeground(COLOR_TEXTO_OSCURO);
        tablaMaterias.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tablaMaterias.getTableHeader().setForeground(COLOR_TEXTO_OSCURO);
        JScrollPane scrollMaterias = new JScrollPane(tablaMaterias);

        tabbedPane.addTab("Productos Terminados", scrollProductos);
        tabbedPane.addTab("Materias Primas", scrollMaterias);

        panel.add(tabbedPane, BorderLayout.CENTER);
        cambiarPanel(panel);
    }

    /**
     * Muestra el panel de historial de producción
     */
    private void mostrarPanelHistorialProduccion() {
        if (!SistemaBloquera.verificarPermiso(1)) {
            mostrarMensajeError("No tiene permisos para acceder a esta función");
            return;
        }

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(COLOR_FONDO_CLARO);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitulo = new JLabel("Historial de Producción");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(COLOR_TEXTO_OSCURO);
        panel.add(lblTitulo, BorderLayout.NORTH);

        // Tabla de historial
        String[] columnas = {"Fecha", "Producto", "Cantidad Producida"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        for (HistorialProducto h : SistemaBloquera.historialProduccion) {
            modelo.addRow(new Object[]{
                sdf.format(h.getFecha()),
                h.getNombreProducto(),
                h.getCantidadProducida()
            });
        }

        JTable tabla = new JTable(modelo);
        tabla.setRowHeight(30);
        tabla.setFont(new Font("Arial", Font.PLAIN, 13));
        tabla.setForeground(COLOR_TEXTO_OSCURO);
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tabla.getTableHeader().setForeground(COLOR_TEXTO_OSCURO);
        
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBackground(Color.WHITE);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(206, 212, 218)));

        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.setBackground(Color.WHITE);
        panelTabla.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelTabla.add(scroll, BorderLayout.CENTER);

        panel.add(panelTabla, BorderLayout.CENTER);
        cambiarPanel(panel);
    }

    /**
     * Muestra el panel de gestión de inventario (Nivel 2+)
     */
    private void mostrarPanelGestionInventario() {
        if (!SistemaBloquera.verificarPermiso(2)) {
            mostrarMensajeError("No tiene permisos para acceder a esta función");
            return;
        }

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(COLOR_FONDO_CLARO);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitulo = new JLabel("Gestión de Inventario");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(COLOR_TEXTO_OSCURO);
        panel.add(lblTitulo, BorderLayout.NORTH);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelBotones.setBackground(Color.WHITE);
        panelBotones.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(206, 212, 218)),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JButton btnNuevaMateria = new JButton("Nueva Materia Prima");
        btnNuevaMateria.setFont(new Font("Arial", Font.BOLD, 14));
        btnNuevaMateria.setBackground(COLOR_PRIMARIO);
        btnNuevaMateria.setForeground(Color.BLACK);
        btnNuevaMateria.setFocusPainted(false);
        btnNuevaMateria.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnNuevaMateria.addActionListener(e -> agregarNuevaMateriaPrima());

        JButton btnNuevoProducto = new JButton("Nuevo Tipo de Producto");
        btnNuevoProducto.setFont(new Font("Arial", Font.BOLD, 14));
        btnNuevoProducto.setBackground(COLOR_EXITO);
        btnNuevoProducto.setForeground(Color.BLACK);
        btnNuevoProducto.setFocusPainted(false);
        btnNuevoProducto.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnNuevoProducto.addActionListener(e -> agregarNuevoProducto());

        JButton btnActualizarStock = new JButton("Actualizar Stock");
        btnActualizarStock.setFont(new Font("Arial", Font.BOLD, 14));
        btnActualizarStock.setBackground(COLOR_ADVERTENCIA);
        btnActualizarStock.setForeground(Color.BLACK);
        btnActualizarStock.setFocusPainted(false);
        btnActualizarStock.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnActualizarStock.addActionListener(e -> actualizarStock());

        panelBotones.add(btnNuevaMateria);
        panelBotones.add(btnNuevoProducto);
        panelBotones.add(btnActualizarStock);

        panel.add(panelBotones, BorderLayout.SOUTH);

        // Mostrar inventario actual
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 14));

        // Productos
        String[] columnasProductos = {"Nombre", "Stock", "Unidad", "P. Base", "P. Venta"};
        DefaultTableModel modeloProductos = new DefaultTableModel(columnasProductos, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (Producto p : SistemaBloquera.obtenerProductos()) {
            modeloProductos.addRow(new Object[]{
                p.getNombre(),
                p.getCantidad(),
                p.getUnidad(),
                String.format("$%.2f", p.getPrecio()),
                String.format("$%.2f", p.getCostoVenta())
            });
        }

        JTable tablaProductos = new JTable(modeloProductos);
        tablaProductos.setRowHeight(30);
        tablaProductos.setFont(new Font("Arial", Font.PLAIN, 13));
        tablaProductos.setForeground(COLOR_TEXTO_OSCURO);
        tablaProductos.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tablaProductos.getTableHeader().setForeground(COLOR_TEXTO_OSCURO);
        JScrollPane scrollProductos = new JScrollPane(tablaProductos);

        // Materias primas
        String[] columnasMaterias = {"Nombre", "Cantidad", "Unidad", "Precio"};
        DefaultTableModel modeloMaterias = new DefaultTableModel(columnasMaterias, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (MateriaPrima m : SistemaBloquera.obtenerMateriasPrimas()) {
            modeloMaterias.addRow(new Object[]{
                m.getNombre(),
                m.getCantidad(),
                m.getUnidad(),
                String.format("$%.2f", m.getPrecio())
            });
        }

        JTable tablaMaterias = new JTable(modeloMaterias);
        tablaMaterias.setRowHeight(30);
        tablaMaterias.setFont(new Font("Arial", Font.PLAIN, 13));
        tablaMaterias.setForeground(COLOR_TEXTO_OSCURO);
        tablaMaterias.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tablaMaterias.getTableHeader().setForeground(COLOR_TEXTO_OSCURO);
        JScrollPane scrollMaterias = new JScrollPane(tablaMaterias);

        tabbedPane.addTab("Productos", scrollProductos);
        tabbedPane.addTab("Materias Primas", scrollMaterias);

        panel.add(tabbedPane, BorderLayout.CENTER);
        cambiarPanel(panel);
    }

    /**
     * Agregar nueva materia prima
     */
    private void agregarNuevaMateriaPrima() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField txtNombre = new JTextField();
        JTextField txtCantidad = new JTextField();
        JTextField txtPrecio = new JTextField();
        JTextField txtUnidad = new JTextField();

        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);
        panel.add(new JLabel("Cantidad Inicial:"));
        panel.add(txtCantidad);
        panel.add(new JLabel("Precio de Compra:"));
        panel.add(txtPrecio);
        panel.add(new JLabel("Unidad (ej. m³, ton):"));
        panel.add(txtUnidad);

        int result = JOptionPane.showConfirmDialog(this, panel,
            "Nueva Materia Prima", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String nombre = txtNombre.getText().trim();
                int cantidad = Integer.parseInt(txtCantidad.getText().trim());
                double precio = Double.parseDouble(txtPrecio.getText().trim());
                String unidad = txtUnidad.getText().trim();

                if (nombre.isEmpty() || unidad.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios",
                        "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                MateriaPrima nueva = new MateriaPrima(nombre, cantidad, precio, unidad);
                SistemaBloquera.inventario.add(nueva);

                JOptionPane.showMessageDialog(this, "Materia prima agregada exitosamente",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
                mostrarPanelGestionInventario(); // Refrescar

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Error en los datos numéricos",
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Agregar nuevo producto
     */
    private void agregarNuevoProducto() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField txtNombre = new JTextField();
        JTextField txtCantidad = new JTextField();
        JTextField txtPrecioBase = new JTextField();
        JTextField txtUnidad = new JTextField();
        JTextField txtPrecioVenta = new JTextField();

        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);
        panel.add(new JLabel("Stock Inicial:"));
        panel.add(txtCantidad);
        panel.add(new JLabel("Precio Base (costo):"));
        panel.add(txtPrecioBase);
        panel.add(new JLabel("Unidad (ej. pza):"));
        panel.add(txtUnidad);
        panel.add(new JLabel("Precio de Venta:"));
        panel.add(txtPrecioVenta);

        int result = JOptionPane.showConfirmDialog(this, panel,
            "Nuevo Tipo de Producto", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String nombre = txtNombre.getText().trim();
                int cantidad = Integer.parseInt(txtCantidad.getText().trim());
                double precioBase = Double.parseDouble(txtPrecioBase.getText().trim());
                String unidad = txtUnidad.getText().trim();
                double precioVenta = Double.parseDouble(txtPrecioVenta.getText().trim());

                if (nombre.isEmpty() || unidad.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios",
                        "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Producto nuevo = new Producto(nombre, cantidad, precioBase, unidad, precioVenta);
                SistemaBloquera.inventario.add(nuevo);

                JOptionPane.showMessageDialog(this, "Producto agregado exitosamente",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
                mostrarPanelGestionInventario(); // Refrescar

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Error en los datos numéricos",
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Actualizar stock de un item
     */
    private void actualizarStock() {
        // Crear lista de todos los items
        Object[] items = new Object[SistemaBloquera.inventario.size()];
        for (int i = 0; i < SistemaBloquera.inventario.size(); i++) {
            MateriaPrima item = SistemaBloquera.inventario.get(i);
            items[i] = item.getNombre() + " (Stock actual: " + item.getCantidad() + ")";
        }

        String seleccion = (String) JOptionPane.showInputDialog(this,
            "Seleccione el item a actualizar:",
            "Actualizar Stock",
            JOptionPane.QUESTION_MESSAGE,
            null,
            items,
            items[0]);

        if (seleccion != null) {
            String nombreItem = seleccion.substring(0, seleccion.indexOf(" ("));
            MateriaPrima item = SistemaBloquera.buscarItemInventarioPorNombre(nombreItem);

            if (item != null) {
                String ajuste = JOptionPane.showInputDialog(this,
                    "Stock actual: " + item.getCantidad() + "\n" +
                    "Ingrese el ajuste (positivo para añadir, negativo para restar):",
                    "0");

                if (ajuste != null) {
                    try {
                        int cantidad = Integer.parseInt(ajuste.trim());
                        int nuevoStock = item.getCantidad() + cantidad;

                        if (nuevoStock < 0) {
                            JOptionPane.showMessageDialog(this,
                                "El stock no puede ser negativo",
                                "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        item.setCantidad(nuevoStock);
                        JOptionPane.showMessageDialog(this,
                            "Stock actualizado\n" +
                            "Nuevo stock de " + item.getNombre() + ": " + nuevoStock,
                            "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        mostrarPanelGestionInventario(); // Refrescar

                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(this, "Valor numérico inválido",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }

    /**
     * Muestra el panel de registro de producción (Nivel 2+)
     */
    private void mostrarPanelRegistrarProduccion() {
        if (!SistemaBloquera.verificarPermiso(2)) {
            mostrarMensajeError("No tiene permisos para acceder a esta función");
            return;
        }

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(COLOR_FONDO_CLARO);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitulo = new JLabel("Registrar Nueva Producción");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(COLOR_TEXTO_OSCURO);
        panel.add(lblTitulo, BorderLayout.NORTH);

        // Panel de formulario
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(Color.WHITE);
        panelFormulario.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(206, 212, 218)),
            BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Seleccionar producto
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel lblProducto = new JLabel("Producto a Fabricar:");
        lblProducto.setFont(new Font("Arial", Font.BOLD, 14));
        lblProducto.setForeground(COLOR_TEXTO_OSCURO);
        panelFormulario.add(lblProducto, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        JComboBox<Producto> cmbProductos = new JComboBox<>();
        cmbProductos.setPreferredSize(new Dimension(300, 30));
        for (Producto p : SistemaBloquera.obtenerProductos()) {
            cmbProductos.addItem(p);
        }
        cmbProductos.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                    int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Producto) {
                    Producto p = (Producto) value;
                    setText(p.getNombre() + " (Stock actual: " + p.getCantidad() + ")");
                }
                return this;
            }
        });
        panelFormulario.add(cmbProductos, gbc);

        // Cantidad producida
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel lblCantidad = new JLabel("Cantidad Fabricada:");
        lblCantidad.setFont(new Font("Arial", Font.BOLD, 14));
        lblCantidad.setForeground(COLOR_TEXTO_OSCURO);
        panelFormulario.add(lblCantidad, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1, 1, 100000, 1);
        JSpinner spnCantidad = new JSpinner(spinnerModel);
        spnCantidad.setPreferredSize(new Dimension(150, 30));
        ((JSpinner.DefaultEditor) spnCantidad.getEditor()).getTextField().setFont(new Font("Arial", Font.PLAIN, 14));
        panelFormulario.add(spnCantidad, gbc);

        // Botón registrar
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton btnRegistrar = new JButton("Registrar Producción");
        btnRegistrar.setFont(new Font("Arial", Font.BOLD, 16));
        btnRegistrar.setBackground(COLOR_EXITO);
        btnRegistrar.setForeground(Color.BLACK);
        btnRegistrar.setFocusPainted(false);
        btnRegistrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRegistrar.setPreferredSize(new Dimension(250, 45));

        btnRegistrar.addActionListener(e -> {
            Producto productoSeleccionado = (Producto) cmbProductos.getSelectedItem();
            int cantidadProducida = (int) spnCantidad.getValue();

            if (productoSeleccionado != null && cantidadProducida > 0) {
                // Actualizar stock
                productoSeleccionado.setCantidad(productoSeleccionado.getCantidad() + cantidadProducida);

                // Registrar en historial
                HistorialProducto registro = new HistorialProducto(new Date(),
                    productoSeleccionado.getNombre(), cantidadProducida);
                SistemaBloquera.historialProduccion.add(registro);

                JOptionPane.showMessageDialog(this,
                    "Producción registrada exitosamente\n" +
                    "Producto: " + productoSeleccionado.getNombre() + "\n" +
                    "Cantidad: " + cantidadProducida + "\n" +
                    "Nuevo stock: " + productoSeleccionado.getCantidad(),
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);

                spnCantidad.setValue(1);
                cmbProductos.updateUI(); // Actualizar display
            }
        });

        panelFormulario.add(btnRegistrar, gbc);

        panel.add(panelFormulario, BorderLayout.CENTER);
        cambiarPanel(panel);
    }

    /**
     * Muestra el panel de gestión de usuarios (Nivel 3)
     */
    private void mostrarPanelUsuarios() {
        if (!SistemaBloquera.verificarPermiso(3)) {
            mostrarMensajeError("No tiene permisos para acceder a esta función");
            return;
        }

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(COLOR_FONDO_CLARO);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitulo = new JLabel("Gestión de Usuarios");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(COLOR_TEXTO_OSCURO);
        panel.add(lblTitulo, BorderLayout.NORTH);

        // Panel con botón para nuevo usuario
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSuperior.setBackground(Color.WHITE);
        panelSuperior.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(206, 212, 218)),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JButton btnNuevoUsuario = new JButton("Crear Nuevo Usuario");
        btnNuevoUsuario.setFont(new Font("Arial", Font.BOLD, 14));
        btnNuevoUsuario.setBackground(COLOR_PRIMARIO);
        btnNuevoUsuario.setForeground(Color.BLACK);
        btnNuevoUsuario.setFocusPainted(false);
        btnNuevoUsuario.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnNuevoUsuario.addActionListener(e -> crearNuevoUsuario());

        panelSuperior.add(btnNuevoUsuario);
        panel.add(panelSuperior, BorderLayout.SOUTH);

        // Tabla de usuarios
        String[] columnas = {"Usuario", "Nivel", "Descripción"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (Usuario u : SistemaBloquera.listaUsuarios) {
            String descripcion = SistemaBloquera.obtenerDescripcionNivel(u.getNivel());
            modelo.addRow(new Object[]{
                u.getNombreUsuario(),
                u.getNivel(),
                descripcion
            });
        }

        JTable tabla = new JTable(modelo);
        tabla.setRowHeight(30);
        tabla.setFont(new Font("Arial", Font.PLAIN, 13));
        tabla.setForeground(COLOR_TEXTO_OSCURO);
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tabla.getTableHeader().setForeground(COLOR_TEXTO_OSCURO);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBackground(Color.WHITE);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(206, 212, 218)));

        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.setBackground(Color.WHITE);
        panelTabla.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelTabla.add(scroll, BorderLayout.CENTER);

        panel.add(panelTabla, BorderLayout.CENTER);
        cambiarPanel(panel);
    }

    /**
     * Crear nuevo usuario
     */
    private void crearNuevoUsuario() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField txtUsuario = new JTextField();
        JPasswordField txtContrasena = new JPasswordField();
        
        String[] niveles = {"1 - Secretaria", "2 - Dueño", "3 - Administrador"};
        JComboBox<String> cmbNivel = new JComboBox<>(niveles);

        panel.add(new JLabel("Nombre de Usuario:"));
        panel.add(txtUsuario);
        panel.add(new JLabel("Contraseña:"));
        panel.add(txtContrasena);
        panel.add(new JLabel("Nivel:"));
        panel.add(cmbNivel);

        int result = JOptionPane.showConfirmDialog(this, panel,
            "Crear Nuevo Usuario", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String nombreUsuario = txtUsuario.getText().trim();
            String contrasena = new String(txtContrasena.getPassword());
            int nivel = cmbNivel.getSelectedIndex() + 1;

            if (nombreUsuario.isEmpty() || contrasena.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios",
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Verificar si el usuario ya existe
            for (Usuario u : SistemaBloquera.listaUsuarios) {
                if (u.getNombreUsuario().equals(nombreUsuario)) {
                    JOptionPane.showMessageDialog(this, "El nombre de usuario ya existe",
                        "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            Usuario nuevoUsuario = new Usuario(nombreUsuario, contrasena, nivel);
            SistemaBloquera.listaUsuarios.add(nuevoUsuario);

            JOptionPane.showMessageDialog(this,
                "Usuario creado exitosamente\n" +
                "Usuario: " + nombreUsuario + "\n" +
                "Nivel: " + SistemaBloquera.obtenerDescripcionNivel(nivel),
                "Éxito", JOptionPane.INFORMATION_MESSAGE);

            mostrarPanelUsuarios(); // Refrescar
        }
    }

    // ============================================================
    //                    MÉTODOS AUXILIARES
    // ============================================================

    /**
     * Cambia el panel de contenido
     */
    private void cambiarPanel(JPanel nuevoPanel) {
        panelContenido.removeAll();
        panelContenido.add(nuevoPanel, BorderLayout.CENTER);
        panelContenido.revalidate();
        panelContenido.repaint();
    }

    /**
     * Muestra un mensaje de error
     */
    private void mostrarMensajeError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
        mostrarPanelBienvenida();
    }

    /**
     * Cierra la sesión actual
     */
    private void cerrarSesion() {
        int confirmacion = JOptionPane.showConfirmDialog(this,
            "¿Está seguro que desea cerrar sesión?",
            "Confirmar",
            JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            SistemaBloquera.cerrarSesion();
            dispose();
            SwingUtilities.invokeLater(() -> {
                new LoginFrame().setVisible(true);
            });
        }
    }
}