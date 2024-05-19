import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    public Login() {
        setTitle("Prijava");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel usernameLabel = new JLabel("Uporabniško ime ali e-pošta:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(usernameLabel, gbc);

        usernameField = new JTextField(10);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 12 ));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Geslo:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(passwordLabel, gbc);

        passwordField = new JPasswordField(10);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 12));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(passwordField, gbc);


        JButton loginButton = new JButton("Prijava");
        loginButton.setPreferredSize(new Dimension(110, 30));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.CENTER;
        panel.add(loginButton, gbc);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String uporabniskoIme = usernameField.getText();
                String geslo = new String (passwordField.getPassword());
                String hashedGeslo = HashPassword.hashPassword(new String(geslo));

                boolean uspesnoPrijavljen = LoginManager.preglej_uporabnika(uporabniskoIme, uporabniskoIme, hashedGeslo);

                if (uspesnoPrijavljen){
                    JOptionPane.showMessageDialog(Login.this, "Uspešno ste prijavljeni.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                } else{
                    JOptionPane.showMessageDialog(Login.this, "Napačno uporabniško ime ali geslo.", "Napaka", JOptionPane.ERROR_MESSAGE);
                }



            }
        });
        loginButton.setPreferredSize(new Dimension(110, 30));



        JButton registrationButton = new JButton("Registracija");
        registrationButton.setPreferredSize(new Dimension(110, 30));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.CENTER;
        panel.add(registrationButton, gbc);


        registrationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openRegistrationForm();
                dispose();
            }
        });
        registrationButton.setPreferredSize(new Dimension(110, 30));


        add(panel);
    }

    private void openRegistrationForm() {
        UserRegistration registrationFrame = new UserRegistration();
        registrationFrame.setSize(500, 500);
        registrationFrame.setTitle("Registracija");
        registrationFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        registrationFrame.setVisible(true);
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Login loginFrame = new Login();
            loginFrame.setVisible(true);
        });
    }
}
