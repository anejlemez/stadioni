import javax.swing.*;
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UserInterface frame = new UserInterface(Login.LoggedInUserId);
            frame.setVisible(true);
        });
    }
}