import javax.swing.*;
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Login loginFrame = new Login();
            loginFrame.setSize(500, 500);
            loginFrame.setTitle("Prijava");
            loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            loginFrame.setVisible(true);
        });
    }
}