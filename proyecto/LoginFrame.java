import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Ventana de inicio de sesión del sistema
 */
public class LoginFrame extends JFrame {
    
    private JTextField txtUsuario;
    private JPasswordField txtContrasena;
    private JButton btnLogin;
    private JLabel lblMensaje;
    private JLabel lblOlvidarContrasena;

    public LoginFrame() {
        initComponents();
    }

    private void initComponents() {
        // Configuración de la ventana
        setTitle("Sistema Bloquera El Fuerte - Login");
        setSize(500, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Panel principal con fondo
        JPanel panelPrincipal = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, new Color(41, 128, 185), 
                                                     0, getHeight(), new Color(52, 73, 94));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panelPrincipal.setLayout(null);
        
        // Panel de login (blanco con bordes redondeados)
        JPanel panelLogin = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(Color.WHITE);
                g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
                g2d.setColor(new Color(206, 212, 218));
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
            }
        };
        panelLogin.setLayout(null);
        panelLogin.setOpaque(false);
        panelLogin.setBounds(50, 50, 400, 350);
        
        // Título
        JLabel lblTitulo = new JLabel("BLOQUERA EL FUERTE", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(33, 37, 41));
        lblTitulo.setBounds(50, 20, 300, 40);
        panelLogin.add(lblTitulo);
        
        // Subtítulo
        JLabel lblSubtitulo = new JLabel("Sistema de Gestión", SwingConstants.CENTER);
        lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 14));
        lblSubtitulo.setForeground(new Color(73, 80, 87));
        lblSubtitulo.setBounds(50, 55, 300, 25);
        panelLogin.add(lblSubtitulo);
        
        // Etiqueta Usuario
        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setFont(new Font("Arial", Font.BOLD, 14));
        lblUsuario.setForeground(new Color(33, 37, 41));
        lblUsuario.setBounds(50, 100, 300, 25);
        panelLogin.add(lblUsuario);
        
        // Campo de texto Usuario
        txtUsuario = new JTextField();
        txtUsuario.setFont(new Font("Arial", Font.PLAIN, 14));
        txtUsuario.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(206, 212, 218)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        txtUsuario.setBounds(50, 125, 300, 35);
        panelLogin.add(txtUsuario);
        
        // Etiqueta Contraseña
        JLabel lblContrasena = new JLabel("Contraseña:");
        lblContrasena.setFont(new Font("Arial", Font.BOLD, 14));
        lblContrasena.setForeground(new Color(33, 37, 41));
        lblContrasena.setBounds(50, 170, 300, 25);
        panelLogin.add(lblContrasena);
        
        // Campo de contraseña
        txtContrasena = new JPasswordField();
        txtContrasena.setFont(new Font("Arial", Font.PLAIN, 14));
        txtContrasena.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(206, 212, 218)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        txtContrasena.setBounds(50, 195, 300, 35);
        panelLogin.add(txtContrasena);
        
        // Link "¿Olvidaste tu contraseña?"
        lblOlvidarContrasena = new JLabel("¿Olvidaste tu contraseña?", SwingConstants.CENTER);
        lblOlvidarContrasena.setFont(new Font("Arial", Font.PLAIN, 12));
        lblOlvidarContrasena.setForeground(new Color(41, 128, 185));
        lblOlvidarContrasena.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblOlvidarContrasena.setBounds(50, 235, 300, 20);
        
        // Efecto hover en el link
        lblOlvidarContrasena.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                lblOlvidarContrasena.setForeground(new Color(52, 73, 94));
                lblOlvidarContrasena.setText("<html><u>¿Olvidaste tu contraseña?</u></html>");
            }
            public void mouseExited(MouseEvent e) {
                lblOlvidarContrasena.setForeground(new Color(41, 128, 185));
                lblOlvidarContrasena.setText("¿Olvidaste tu contraseña?");
            }
            public void mouseClicked(MouseEvent e) {
                abrirRecuperacionContrasena();
            }
        });
        
        panelLogin.add(lblOlvidarContrasena);
        
        // Botón de login
        btnLogin = new JButton("INICIAR SESIÓN");
        btnLogin.setFont(new Font("Arial", Font.BOLD, 14));
        btnLogin.setForeground(Color.BLACK);
        btnLogin.setBackground(new Color(39, 174, 96));
        btnLogin.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnLogin.setFocusPainted(false);
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogin.setBounds(125, 270, 150, 40);
        
        // Efecto hover en el botón
        btnLogin.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btnLogin.setBackground(new Color(46, 204, 113));
            }
            public void mouseExited(MouseEvent e) {
                btnLogin.setBackground(new Color(39, 174, 96));
            }
        });
        
        panelLogin.add(btnLogin);
        
        // Mensaje de error/info
        lblMensaje = new JLabel("", SwingConstants.CENTER);
        lblMensaje.setFont(new Font("Arial", Font.PLAIN, 12));
        lblMensaje.setForeground(new Color(192, 57, 43));
        lblMensaje.setBounds(0, 410, 500, 25);
        panelPrincipal.add(lblMensaje);
        
        panelPrincipal.add(panelLogin);
        add(panelPrincipal);
        
        // Eventos
        btnLogin.addActionListener(e -> intentarLogin());
        
        // Enter en los campos de texto
        txtUsuario.addActionListener(e -> txtContrasena.requestFocus());
        txtContrasena.addActionListener(e -> intentarLogin());
        
        // Focus inicial
        addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                txtUsuario.requestFocus();
            }
        });
    }

    /**
     * Intenta autenticar al usuario
     */
    private void intentarLogin() {
        String usuario = txtUsuario.getText().trim();
        String contrasena = new String(txtContrasena.getPassword());
        
        if (usuario.isEmpty() || contrasena.isEmpty()) {
            mostrarMensaje("Por favor, complete todos los campos", false);
            return;
        }
        
        Usuario usuarioAutenticado = SistemaBloquera.autenticarUsuario(usuario, contrasena);
        
        if (usuarioAutenticado != null) {
            SistemaBloquera.usuarioActual = usuarioAutenticado;
            mostrarMensaje("¡Bienvenido, " + usuarioAutenticado.getNombreUsuario() + "!", true);
            
            // Abrir ventana principal
            SwingUtilities.invokeLater(() -> {
                new MainFrame().setVisible(true);
                dispose();
            });
        } else {
            mostrarMensaje("Usuario o contraseña incorrectos", false);
            txtContrasena.setText("");
            txtContrasena.requestFocus();
        }
    }

    /**
     * Abre el diálogo de recuperación de contraseña
     */
    private void abrirRecuperacionContrasena() {
        JDialog dialogRecuperacion = new JDialog(this, "Recuperar Contraseña", true);
        dialogRecuperacion.setSize(450, 350);
        dialogRecuperacion.setLocationRelativeTo(this);
        dialogRecuperacion.setResizable(false);
        
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout(10, 10));
        panelPrincipal.setBackground(Color.WHITE);
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Panel de título
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(Color.WHITE);
        panelTitulo.setLayout(new BoxLayout(panelTitulo, BoxLayout.Y_AXIS));
        
        JLabel lblTitulo = new JLabel("Recuperar Contraseña");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(new Color(33, 37, 41));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblInstrucciones = new JLabel("Ingrese su usuario y teléfono registrado");
        lblInstrucciones.setFont(new Font("Arial", Font.PLAIN, 13));
        lblInstrucciones.setForeground(new Color(108, 117, 125));
        lblInstrucciones.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        panelTitulo.add(lblTitulo);
        panelTitulo.add(Box.createRigidArea(new Dimension(0, 10)));
        panelTitulo.add(lblInstrucciones);
        
        panelPrincipal.add(panelTitulo, BorderLayout.NORTH);
        
        // Panel de formulario
        JPanel panelFormulario = new JPanel(new GridLayout(4, 2, 15, 15));
        panelFormulario.setBackground(Color.WHITE);
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        
        JLabel lblUsuarioRec = new JLabel("Usuario:");
        lblUsuarioRec.setFont(new Font("Arial", Font.BOLD, 13));
        lblUsuarioRec.setForeground(new Color(33, 37, 41));
        
        JTextField txtUsuarioRec = new JTextField();
        txtUsuarioRec.setFont(new Font("Arial", Font.PLAIN, 13));
        txtUsuarioRec.setBorder(BorderFactory.createCompoundBorder(
    BorderFactory.createLineBorder(new Color(206, 212, 218)),
    BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        
        JLabel lblTelefonoRec = new JLabel("Teléfono:");
        lblTelefonoRec.setFont(new Font("Arial", Font.BOLD, 13));
        lblTelefonoRec.setForeground(new Color(33, 37, 41));
        
        JTextField txtTelefonoRec = new JTextField();
        txtTelefonoRec.setFont(new Font("Arial", Font.PLAIN, 13));
        txtTelefonoRec.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(206, 212, 218)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        
        panelFormulario.add(lblUsuarioRec);
        panelFormulario.add(txtUsuarioRec);
        panelFormulario.add(lblTelefonoRec);
        panelFormulario.add(txtTelefonoRec);
        
        panelPrincipal.add(panelFormulario, BorderLayout.CENTER);
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelBotones.setBackground(Color.WHITE);
        
        JButton btnVerificar = new JButton("Verificar");
        btnVerificar.setFont(new Font("Arial", Font.BOLD, 13));
        btnVerificar.setBackground(new Color(41, 128, 185));
        btnVerificar.setForeground(Color.BLACK);
        btnVerificar.setFocusPainted(false);
        btnVerificar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVerificar.setPreferredSize(new Dimension(120, 35));
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setFont(new Font("Arial", Font.BOLD, 13));
        btnCancelar.setBackground(new Color(108, 117, 125));
        btnCancelar.setForeground(Color.BLACK);
        btnCancelar.setFocusPainted(false);
        btnCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCancelar.setPreferredSize(new Dimension(120, 35));
        
        // Efectos hover
        btnVerificar.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btnVerificar.setBackground(new Color(52, 152, 219));
            }
            public void mouseExited(MouseEvent e) {
                btnVerificar.setBackground(new Color(41, 128, 185));
            }
        });
        
        btnCancelar.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btnCancelar.setBackground(new Color(127, 140, 141));
            }
            public void mouseExited(MouseEvent e) {
                btnCancelar.setBackground(new Color(108, 117, 125));
            }
        });
        
        panelBotones.add(btnVerificar);
        panelBotones.add(btnCancelar);
        
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        
        // Eventos
        btnVerificar.addActionListener(e -> {
            String usuario = txtUsuarioRec.getText().trim();
            String telefono = txtTelefonoRec.getText().trim();
            
            if (usuario.isEmpty() || telefono.isEmpty()) {
                JOptionPane.showMessageDialog(dialogRecuperacion,
                    "Por favor, complete todos los campos",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Usuario usuarioEncontrado = SistemaBloquera.verificarUsuarioPorTelefono(usuario, telefono);
            
            if (usuarioEncontrado != null) {
                dialogRecuperacion.dispose();
                mostrarDialogoNuevaContrasena(usuarioEncontrado);
            } else {
                JOptionPane.showMessageDialog(dialogRecuperacion,
                    "Usuario o teléfono incorrectos",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        });
        
        btnCancelar.addActionListener(e -> dialogRecuperacion.dispose());
        
        dialogRecuperacion.add(panelPrincipal);
        dialogRecuperacion.setVisible(true);
    }

    /**
     * Muestra el diálogo para establecer una nueva contraseña
     */
    private void mostrarDialogoNuevaContrasena(Usuario usuario) {
        JDialog dialogNuevaContrasena = new JDialog(this, "Nueva Contraseña", true);
        dialogNuevaContrasena.setSize(400, 280);
        dialogNuevaContrasena.setLocationRelativeTo(this);
        dialogNuevaContrasena.setResizable(false);
        
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout(10, 10));
        panelPrincipal.setBackground(Color.WHITE);
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Título
        JLabel lblTitulo = new JLabel("Establecer Nueva Contraseña", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(33, 37, 41));
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);
        
        // Formulario
        JPanel panelFormulario = new JPanel(new GridLayout(2, 2, 10, 15));
        panelFormulario.setBackground(Color.WHITE);
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        
        JLabel lblNuevaContrasena = new JLabel("Nueva contraseña:");
        lblNuevaContrasena.setFont(new Font("Arial", Font.BOLD, 13));
        lblNuevaContrasena.setForeground(new Color(33, 37, 41));
        
        JPasswordField txtNuevaContrasena = new JPasswordField();
        txtNuevaContrasena.setFont(new Font("Arial", Font.PLAIN, 13));
        txtNuevaContrasena.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(206, 212, 218)),
            BorderFactory.createEmptyBorder(0, 10, 0, 10)
        ));
        
        JLabel lblConfirmarContrasena = new JLabel("Confirmar:");
        lblConfirmarContrasena.setFont(new Font("Arial", Font.BOLD, 13));
        lblConfirmarContrasena.setForeground(new Color(33, 37, 41));
        
        JPasswordField txtConfirmarContrasena = new JPasswordField();
        txtConfirmarContrasena.setFont(new Font("Arial", Font.PLAIN, 13));
        txtConfirmarContrasena.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(206, 212, 218)),
            BorderFactory.createEmptyBorder(0, 10, 0, 10)
        ));
        
        panelFormulario.add(lblNuevaContrasena);
        panelFormulario.add(txtNuevaContrasena);
        panelFormulario.add(lblConfirmarContrasena);
        panelFormulario.add(txtConfirmarContrasena);
        
        panelPrincipal.add(panelFormulario, BorderLayout.CENTER);
        
        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelBotones.setBackground(Color.WHITE);
        
        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setFont(new Font("Arial", Font.BOLD, 13));
        btnGuardar.setBackground(new Color(39, 174, 96));
        btnGuardar.setForeground(Color.BLACK);
        btnGuardar.setFocusPainted(false);
        btnGuardar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnGuardar.setPreferredSize(new Dimension(120, 35));
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setFont(new Font("Arial", Font.BOLD, 13));
        btnCancelar.setBackground(new Color(108, 117, 125));
        btnCancelar.setForeground(Color.BLACK);
        btnCancelar.setFocusPainted(false);
        btnCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCancelar.setPreferredSize(new Dimension(120, 35));
        
        // Efectos hover
        btnGuardar.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btnGuardar.setBackground(new Color(46, 204, 113));
            }
            public void mouseExited(MouseEvent e) {
                btnGuardar.setBackground(new Color(39, 174, 96));
            }
        });
        
        btnCancelar.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btnCancelar.setBackground(new Color(127, 140, 141));
            }
            public void mouseExited(MouseEvent e) {
                btnCancelar.setBackground(new Color(108, 117, 125));
            }
        });
        
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        
        // Eventos
        btnGuardar.addActionListener(e -> {
            String nuevaContrasena = new String(txtNuevaContrasena.getPassword());
            String confirmarContrasena = new String(txtConfirmarContrasena.getPassword());
            
            if (nuevaContrasena.isEmpty() || confirmarContrasena.isEmpty()) {
                JOptionPane.showMessageDialog(dialogNuevaContrasena,
                    "Por favor, complete todos los campos",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (!nuevaContrasena.equals(confirmarContrasena)) {
                JOptionPane.showMessageDialog(dialogNuevaContrasena,
                    "Las contraseñas no coinciden",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (nuevaContrasena.length() < 4) {
                JOptionPane.showMessageDialog(dialogNuevaContrasena,
                    "La contraseña debe tener al menos 4 caracteres",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            usuario.setContrasena(nuevaContrasena);
            
            JOptionPane.showMessageDialog(dialogNuevaContrasena,
                "Contraseña actualizada exitosamente\nYa puede iniciar sesión con su nueva contraseña",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE);
            
            dialogNuevaContrasena.dispose();
            mostrarMensaje("Contraseña actualizada. Ingrese con su nueva contraseña", true);
        });
        
        btnCancelar.addActionListener(e -> dialogNuevaContrasena.dispose());
        
        dialogNuevaContrasena.add(panelPrincipal);
        dialogNuevaContrasena.setVisible(true);
    }

    /**
     * Muestra un mensaje en la interfaz
     */
    private void mostrarMensaje(String mensaje, boolean exito) {
        lblMensaje.setText(mensaje);
        lblMensaje.setForeground(exito ? new Color(39, 174, 96) : new Color(192, 57, 43));
    }
}