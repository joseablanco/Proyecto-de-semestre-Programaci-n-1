package proyectosemestre;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;

/**
 * Main Menu window.
 *
 * Contains a "Management" menu with the options:
 *  - User Maintenance
 *  - Password Reset
 *  - Log Out  (returns to Login)
 *
 * Also includes a "Help" menu with "About".
 */
public class MenuPrincipal extends JFrame {

    private final String currentUser;

    public MenuPrincipal(String currentUser) {
        this.currentUser = currentUser;
        initComponents();
    }

    private void initComponents() {
        setTitle("Main Menu - Semester Project");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(720, 480);
        setLocationRelativeTo(null);

        // Menu bar.
        JMenuBar menuBar = new JMenuBar();

        // Management menu.
        JMenu menuManagement = new JMenu("Management");
        menuManagement.setMnemonic(KeyEvent.VK_M);

        JMenuItem itemUserMaintenance = new JMenuItem("User Maintenance");
        itemUserMaintenance.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.CTRL_DOWN_MASK));

        JMenuItem itemPasswordReset = new JMenuItem("Password Reset");
        itemPasswordReset.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK));

        JMenuItem itemLogOut = new JMenuItem("Log Out");
        itemLogOut.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_DOWN_MASK));

        menuManagement.add(itemUserMaintenance);
        menuManagement.add(itemPasswordReset);
        menuManagement.addSeparator();
        menuManagement.add(itemLogOut);

        // Help menu.
        JMenu menuHelp = new JMenu("Help");
        menuHelp.setMnemonic(KeyEvent.VK_H);
        JMenuItem itemAbout = new JMenuItem("About");
        menuHelp.add(itemAbout);

        menuBar.add(menuManagement);
        menuBar.add(menuHelp);
        setJMenuBar(menuBar);

        // Central panel (work area).
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(new Color(245, 247, 250));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblWelcome = new JLabel(
                "<html><div style='text-align: center;'>"
                + "<h1>Semester Project</h1>"
                + "<p>Signed in as: <b>" + currentUser + "</b></p>"
                + "<p>Use the <b>Management</b> menu to open the modules.</p>"
                + "</div></html>",
                SwingConstants.CENTER);
        lblWelcome.setFont(new Font("SansSerif", Font.PLAIN, 14));

        centerPanel.add(lblWelcome, BorderLayout.CENTER);

        // Status bar.
        JLabel lblStatus = new JLabel("  User: " + currentUser + "   |   Ready.");
        lblStatus.setBorder(BorderFactory.createLoweredBevelBorder());

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(centerPanel, BorderLayout.CENTER);
        getContentPane().add(lblStatus, BorderLayout.SOUTH);

        // Events.
        itemUserMaintenance.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openUserMaintenance();
            }
        });

        itemPasswordReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openPasswordReset();
            }
        });

        itemLogOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logOut();
            }
        });

        itemAbout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(MenuPrincipal.this,
                        "Semester Project\n"
                        + "Java Desktop Application (Swing)\n"
                        + "NetBeans IDE",
                        "About",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    // Placeholder: to be implemented in the next delivery.
    private void openUserMaintenance() {
        JOptionPane.showMessageDialog(this,
                "'User Maintenance' module - pending implementation.",
                "User Maintenance",
                JOptionPane.INFORMATION_MESSAGE);
        // When implemented:
        // new UserMaintenance().setVisible(true);
    }

    // Placeholder: to be implemented in the next delivery.
    private void openPasswordReset() {
        JOptionPane.showMessageDialog(this,
                "'Password Reset' module - pending implementation.",
                "Password Reset",
                JOptionPane.INFORMATION_MESSAGE);
        // When implemented:
        // new PasswordReset().setVisible(true);
    }

    /**
     * Logs out: returns to the Login screen.
     */
    private void logOut() {
        int option = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to log out?",
                "Log Out",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (option == JOptionPane.YES_OPTION) {
            this.dispose();
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new Login().setVisible(true);
                }
            });
        }
    }
}
