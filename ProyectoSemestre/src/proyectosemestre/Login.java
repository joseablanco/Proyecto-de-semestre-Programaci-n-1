package proyectosemestre;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;


public class Login extends JFrame {

   
    private static final String VALID_USERNAME = "Alejandro";
    private static final String VALID_PASSWORD = "Alejandro123";
    private static final int MAX_ATTEMPTS = 3;

    
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnSignIn;
    private JButton btnExit;
    private JLabel lblMessage;

    private int attempts = 0;

    public Login() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Login");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(380, 260);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

       
        JLabel lblTitle = new JLabel("Welcome", SwingConstants.CENTER);
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(lblTitle, gbc);

    
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("User:"), gbc);

        txtUsername = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(txtUsername, gbc);

       
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Password:"), gbc);

        txtPassword = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(txtPassword, gbc);

      
        KeyAdapter enterKey = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    validateCredentials();
                }
            }
        };
        txtUsername.addKeyListener(enterKey);
        txtPassword.addKeyListener(enterKey);

       
        JPanel buttonsPanel = new JPanel();
        btnSignIn = new JButton("Sign In");
        btnExit = new JButton("Exit");
        buttonsPanel.add(btnSignIn);
        buttonsPanel.add(btnExit);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(buttonsPanel, gbc);

    
        lblMessage = new JLabel(" ", SwingConstants.CENTER);
        lblMessage.setForeground(Color.RED);
        lblMessage.setPreferredSize(new Dimension(300, 20));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panel.add(lblMessage, gbc);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panel, BorderLayout.CENTER);

        // Button events.
        btnSignIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validateCredentials();
            }
        });

        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

   
    private void validateCredentials() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            lblMessage.setText("Please enter user and password.");
            return;
        }

        if (VALID_USERNAME.equals(username) && VALID_PASSWORD.equals(password)) {
            // Success: open the Main Menu.
            JOptionPane.showMessageDialog(this,
                    "Welcome, " + username + ".",
                    "Access granted",
                    JOptionPane.INFORMATION_MESSAGE);

            MenuPrincipal menu = new MenuPrincipal(username);
            menu.setVisible(true);
            this.dispose();
        } else {
            attempts++;
            int remaining = MAX_ATTEMPTS - attempts;

            if (attempts >= MAX_ATTEMPTS) {
                JOptionPane.showMessageDialog(this,
                        "You have exceeded the maximum number of attempts.\n"
                        + "The application will close.",
                        "Access blocked",
                        JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            } else {
                lblMessage.setText("Invalid credentials. Remaining attempts: " + remaining);
                txtPassword.setText("");
                txtPassword.requestFocus();
            }
        }
    }
}
