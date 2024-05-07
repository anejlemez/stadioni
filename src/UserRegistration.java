import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;


public class UserRegistration extends JFrame {

    private static JLabel naslov = new JLabel("REGISTRACIJA");
    private static JLabel napotkiLabel = new JLabel("Ime:");
    private static JTextField imeTextBox = new JTextField(25);

    private static JLabel napotki2Label = new JLabel("Priimek:");
    private static JTextField priimekTextBox = new JTextField(25);

    private static JLabel napotki3Label = new JLabel("Uporabniško ime:");
    private static JTextField uporabniskoimeTextBox = new JTextField(25);

    private static JLabel napotki4Label = new JLabel("Geslo:");
    private static JPasswordField gesloTextBox = new JPasswordField(25);

    private static JLabel napotki5Label = new JLabel("Ponovite geslo:");
    private static JPasswordField ponovitegesloTextBox = new JPasswordField(25);

    private static JLabel napotki6Label = new JLabel("Telefonska številka:");
    private static JTextField telefonskaTextBox = new JTextField(25);

    private static JLabel napotki7Label = new JLabel("E-mail:");
    private static JTextField emailTextBox = new JTextField(25);

    private static JLabel napotki8Label = new JLabel("Naslov:");
    private static JTextField naslovTextBox = new JTextField(25);

    private static JLabel krajLabel = new JLabel("Kraj:");
    private static JTextField krajTextBox = new JTextField(25);

    private static JLabel izpisLabel = new JLabel();
    private static JButton gumb = new JButton("Registriraj se");

    public UserRegistration() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        naslov.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(naslov, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(napotkiLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        add(imeTextBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(napotki2Label, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        add(priimekTextBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(napotki3Label, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        add(uporabniskoimeTextBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(napotki4Label, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        add(gesloTextBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        add(napotki5Label, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        add(ponovitegesloTextBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        add(napotki6Label, gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        add(telefonskaTextBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        add(napotki7Label, gbc);

        gbc.gridx = 1;
        gbc.gridy = 7;
        add(emailTextBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        add(napotki8Label, gbc);

        gbc.gridx = 1;
        gbc.gridy = 8;
        add(naslovTextBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 9;
        add(krajLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 9;
        add(krajTextBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(gumb, gbc);

        gbc.gridx = 0;
        gbc.gridy = 11;
        gbc.gridwidth = 2;
        add(izpisLabel, gbc);

        gumb.addActionListener(new MyActionListener());
    }


    public static boolean VsePravilno() {
        if (imeTextBox.getText().isEmpty() ||
                priimekTextBox.getText().isEmpty() ||
                uporabniskoimeTextBox.getText().isEmpty() ||
                gesloTextBox.getPassword().length == 0 ||
                ponovitegesloTextBox.getPassword().length == 0 ||
                telefonskaTextBox.getText().isEmpty() ||
                emailTextBox.getText().isEmpty() ||
                naslovTextBox.getText().isEmpty() ||
                krajTextBox.getText().isEmpty()) {
            return false;
        }
        return true;
    }

    public static class MyActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (VsePravilno()) {
                buttonClicked(e);
                JOptionPane.showMessageDialog(null, "Uspešno ste se registrirali.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Napaka! Vnesite pravilne podatke.", "Napaka", JOptionPane.ERROR_MESSAGE);
            }
        }

        public void buttonClicked(ActionEvent e) {
            String ime = imeTextBox.getText();
            String priimek = priimekTextBox.getText();
            String uporabniskoime = uporabniskoimeTextBox.getText();
            char[] geslo = gesloTextBox.getPassword();
            char[] ponoviteGeslo = ponovitegesloTextBox.getPassword();
            String telefonska = telefonskaTextBox.getText();
            String email = emailTextBox.getText();
            String kraj = krajTextBox.getText();
            String naslov = naslovTextBox.getText();

            // Preverite, ali se gesli ujemata
            if (!gesloMatches(geslo, ponoviteGeslo)) {
                JOptionPane.showMessageDialog(null, "Gesli se ne ujemata.", "Napaka", JOptionPane.ERROR_MESSAGE);
                return;
            }

            vstaviUporabnikaVDatabase(uporabniskoime, new String(geslo), ime, priimek, email, telefonska, naslov, kraj);
        }

        // Metoda za preverjanje, ali se gesli ujemata
        private boolean gesloMatches(char[] geslo1, char[] geslo2) {
            return java.util.Arrays.equals(geslo1, geslo2);
        }

        private void vstaviUporabnikaVDatabase(String uporabniskoime, String geslo, String ime ,
                                               String priimek, String email, String telefonska, String naslov, String kraj) {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            try (Connection connection = databaseConnection.getConnection()) {
                String sql = "SELECT dodaj_uporabnika14(?, ?, ?, ?, ?, ?, ?, ?)";
                try (CallableStatement statement = databaseConnection.prepareCall(sql, connection)) {
                    statement.setString(1, uporabniskoime);
                    statement.setString(2, geslo);
                    statement.setString(3, ime);
                    statement.setString(4, priimek);
                    statement.setString(5, email);
                    statement.setInt(6, Integer.parseInt(telefonska));
                    statement.setString(7, naslov);
                    statement.setString(8, kraj);

                    statement.executeQuery();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UserRegistration window = new UserRegistration();
            window.setSize(500, 500);
            window.setTitle("Registracija");
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setVisible(true);
        });
    }

}
