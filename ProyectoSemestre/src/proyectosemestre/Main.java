package proyectosemestre;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Entry point. Launches the Login window.
 */
public class Main {

    public static void main(String[] args) {
        // Try to use the system look & feel for a native appearance.
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            // If it fails, continue with the default look & feel.
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Login().setVisible(true);
            }
        });
    }
}
