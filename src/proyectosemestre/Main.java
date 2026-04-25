package proyectosemestre;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;


public class Main {

    private static List<User> usuarios = new ArrayList<>();


    private static int intentosRestantes = 3;

    public static void main(String[] args) {
    
        usuarios.add(new User("Alejandro", "Alejandro 123", true));
        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
        });
    }

    static class User {
        String username;
        String password;
        boolean activo;

        User(String username, String password, boolean activo) {
            this.username = username;
            this.password = password;
            this.activo = activo;
        }
    }

   
    static class LoginFrame extends JFrame {
        private static final long serialVersionUID = 1L;
        private JTextField txtUsuario;
        private JPasswordField txtClave;
        private JLabel lblMensaje;

        LoginFrame() {
            setTitle("Login");
            setSize(300, 200);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            initComponents();
        }

        private void initComponents() {
            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(4, 2));

            panel.add(new JLabel("Username:"));
            txtUsuario = new JTextField();
            panel.add(txtUsuario);

            panel.add(new JLabel("Password:"));
            txtClave = new JPasswordField();
            panel.add(txtClave);

            JButton btnIngresar = new JButton("Login");
            btnIngresar.addActionListener(e -> ingresar());
            panel.add(btnIngresar);

            JButton btnSalir = new JButton("Exit");
            btnSalir.addActionListener(e -> System.exit(0));
            panel.add(btnSalir);

            lblMensaje = new JLabel(" ");
            lblMensaje.setForeground(Color.RED);
            panel.add(lblMensaje);

            add(panel);
        }

      
        private void ingresar() {
            String usuario = txtUsuario.getText().trim();
            String clave = new String(txtClave.getPassword());

            boolean autenticado = false;
            for (User u : usuarios) {
                if (u.username.equals(usuario) && u.password.equals(clave) && u.activo) {
                    autenticado = true;
                    break;
                }
            }

            if (autenticado) {
               
                intentosRestantes = 3;
                dispose();
                new MainMenuFrame().setVisible(true);
            } else {
                intentosRestantes--;
                if (intentosRestantes <= 0) {
                    JOptionPane.showMessageDialog(this, "Account locked due to multiple incorrect attempts.");
                    System.exit(0);
                } else {
                    lblMensaje.setText("Incorrect credentials. Attempts left: " + intentosRestantes);
                }
            }
        }
    }

  
    static class MainMenuFrame extends JFrame {
        private static final long serialVersionUID = 1L;
        MainMenuFrame() {
            setTitle("Main Menu");
            setSize(400, 300);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            initComponents();
        }

        private void initComponents() {
            JMenuBar menuBar = new JMenuBar();

            JMenu gestionMenu = new JMenu("Management");
            JMenuItem mantenimientoItem = new JMenuItem("User Management");
            mantenimientoItem.addActionListener(e -> {
                UserManagementFrame m = new UserManagementFrame();
                m.setVisible(true);
            });
            gestionMenu.add(mantenimientoItem);

            JMenuItem reinicioItem = new JMenuItem("Reset Password");
            reinicioItem.addActionListener(e -> {
                PasswordResetFrame r = new PasswordResetFrame();
                r.setVisible(true);
            });
            gestionMenu.add(reinicioItem);

            menuBar.add(gestionMenu);

            JMenu sesionMenu = new JMenu("Session");
            JMenuItem cerrarItem = new JMenuItem("Logout");
            cerrarItem.addActionListener(e -> {
                dispose();
                new LoginFrame().setVisible(true);
            });
            sesionMenu.add(cerrarItem);
            menuBar.add(sesionMenu);

            setJMenuBar(menuBar);

            JLabel lblBienvenida = new JLabel("Welcome to the system", SwingConstants.CENTER);
            add(lblBienvenida);
        }
    }

 
    static class UserManagementFrame extends JFrame {
        private static final long serialVersionUID = 1L;
        private DefaultTableModel model;
        private JTable table;

        UserManagementFrame() {
            setTitle("User Management");
            setSize(500, 300);
            setLocationRelativeTo(null);
            initComponents();
        }

        private void initComponents() {
            String[] columnNames = {"Username", "Active"};
            model = new DefaultTableModel(columnNames, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            table = new JTable(model);
            cargarUsuarios();

            JScrollPane scrollPane = new JScrollPane(table);
            add(scrollPane, BorderLayout.CENTER);

            JPanel botones = new JPanel();
            JButton btnAgregar = new JButton("Add");
            btnAgregar.addActionListener(e -> agregarUsuario());
            botones.add(btnAgregar);

            JButton btnModificar = new JButton("Edit");
            btnModificar.addActionListener(e -> modificarUsuario());
            botones.add(btnModificar);

            JButton btnInactivar = new JButton("Deactivate");
            btnInactivar.addActionListener(e -> inactivarUsuario());
            botones.add(btnInactivar);
            add(botones, BorderLayout.SOUTH);
        }

        private void cargarUsuarios() {
            model.setRowCount(0);
            for (User u : usuarios) {
                model.addRow(new Object[]{u.username, u.activo ? "Yes" : "No"});
            }
        }

        private void agregarUsuario() {
            JTextField txtUsuario = new JTextField();
            JPasswordField txtClave = new JPasswordField();
            JCheckBox chkActivo = new JCheckBox("Active", true);
            Object[] mensaje = {
                    "Username:", txtUsuario,
                    "Password:", txtClave,
                    chkActivo
            };
            int opcion = JOptionPane.showConfirmDialog(this, mensaje, "Add User", JOptionPane.OK_CANCEL_OPTION);
            if (opcion == JOptionPane.OK_OPTION) {
                String usuario = txtUsuario.getText().trim();
                String clave = new String(txtClave.getPassword());
                boolean activo = chkActivo.isSelected();
                if (usuario.isEmpty() || clave.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Fields cannot be empty");
                    return;
                }
                if (buscarUsuario(usuario) != null) {
                    JOptionPane.showMessageDialog(this, "The username already exists");
                    return;
                }
                usuarios.add(new User(usuario, clave, activo));
                cargarUsuarios();
            }
        }

        private void modificarUsuario() {
            int fila = table.getSelectedRow();
            if (fila < 0) {
                JOptionPane.showMessageDialog(this, "Select a user");
                return;
            }
            String userNameSeleccionado = (String) model.getValueAt(fila, 0);
            User usuario = buscarUsuario(userNameSeleccionado);
            if (usuario == null) return;
            JTextField txtUsuario = new JTextField(usuario.username);
            JPasswordField txtClave = new JPasswordField(usuario.password);
            JCheckBox chkActivo = new JCheckBox("Active", usuario.activo);
            Object[] mensaje = {
                    "Username:", txtUsuario,
                    "Password:", txtClave,
                    chkActivo
            };
            int opcion = JOptionPane.showConfirmDialog(this, mensaje, "Edit User", JOptionPane.OK_CANCEL_OPTION);
            if (opcion == JOptionPane.OK_OPTION) {
                String nuevoNombre = txtUsuario.getText().trim();
                String nuevaClave = new String(txtClave.getPassword());
                boolean activo = chkActivo.isSelected();
                if (nuevoNombre.isEmpty() || nuevaClave.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Fields cannot be empty");
                    return;
                }
              
                if (!nuevoNombre.equals(usuario.username) && buscarUsuario(nuevoNombre) != null) {
                    JOptionPane.showMessageDialog(this, "Another user with that name already exists");
                    return;
                }
                usuario.username = nuevoNombre;
                usuario.password = nuevaClave;
                usuario.activo = activo;
                cargarUsuarios();
            }
        }

        private void inactivarUsuario() {
            int fila = table.getSelectedRow();
            if (fila < 0) {
                JOptionPane.showMessageDialog(this, "Select a user");
                return;
            }
            String userNameSeleccionado = (String) model.getValueAt(fila, 0);
            User usuario = buscarUsuario(userNameSeleccionado);
            if (usuario != null) {
                usuario.activo = false;
                cargarUsuarios();
            }
        }

        private User buscarUsuario(String nombre) {
            for (User u : usuarios) {
                if (u.username.equals(nombre)) {
                    return u;
                }
            }
            return null;
        }
    }

  
    static class PasswordResetFrame extends JFrame {
        private static final long serialVersionUID = 1L;
        private JPasswordField txtNueva;
        private JPasswordField txtConfirmar;
        PasswordResetFrame() {
            setTitle("Reset Password");
            setSize(350, 200);
            setLocationRelativeTo(null);
            initComponents();
        }

        private void initComponents() {
            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(4, 2));

            panel.add(new JLabel("New Password:"));
            txtNueva = new JPasswordField();
            panel.add(txtNueva);

            panel.add(new JLabel("Confirm Password:"));
            txtConfirmar = new JPasswordField();
            panel.add(txtConfirmar);

            JButton btnCambiar = new JButton("Change");
            btnCambiar.addActionListener(e -> cambiarClave());
            panel.add(btnCambiar);

            JButton btnCancelar = new JButton("Cancel");
            btnCancelar.addActionListener(e -> dispose());
            panel.add(btnCancelar);

            add(panel);
        }

        private void cambiarClave() {
            String nueva = new String(txtNueva.getPassword());
            String confirmar = new String(txtConfirmar.getPassword());
            if (!nueva.equals(confirmar)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match");
                return;
            }
            if (!validarClave(nueva)) {
                JOptionPane.showMessageDialog(this, "The password must have at least 13 characters, an uppercase letter, and a special character");
                return;
            }
        
            User actual = usuarios.get(0);
            actual.password = nueva;
            JOptionPane.showMessageDialog(this, "Password updated");
            dispose();
        }

        private boolean validarClave(String clave) {
            if (clave.length() < 13) {
                return false;
            }
            boolean tieneMayuscula = false;
            boolean tieneEspecial = false;
            for (char c : clave.toCharArray()) {
                if (Character.isUpperCase(c)) {
                    tieneMayuscula = true;
                }
                if (!Character.isLetterOrDigit(c)) {
                    tieneEspecial = true;
                }
            }
            return tieneMayuscula && tieneEspecial;
        }
    }
}