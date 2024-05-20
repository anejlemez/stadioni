import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.Statement;


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
    private static JComboBox krajComboBox = new JComboBox<>();
    private static JLabel izpisLabel = new JLabel();
    private static JButton gumb = new JButton("Registriraj se");
    private static JButton nazajGumb = new JButton("Nazaj na prijavo");

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

        DatabaseConnection databaseConnection = new DatabaseConnection();
        try (Connection connection = databaseConnection.getConnection()) {

            String sql = "SELECT ime FROM kraji";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                while (resultSet.next()) {
                    krajComboBox.addItem(resultSet.getString("ime"));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        gbc.gridx = 1;
        gbc.gridy = 9;
        add(krajComboBox, gbc);


        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 1;
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.CENTER;
        add(gumb, gbc);

        gbc.gridy = 11;
        add(nazajGumb, gbc);

        gbc.insets = new Insets(10, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 12;
        gbc.gridwidth = 2;
        add(izpisLabel, gbc);

        gumb.addActionListener(new MyActionListener());
        nazajGumb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();


                Login loginFrame = new Login();
                loginFrame.setSize(500, 500);
                loginFrame.setTitle("Prijava");
                loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                loginFrame.setVisible(true);
            }
        });

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
                krajComboBox.getSelectedItem() == null) {
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
            String kraj = (String) krajComboBox.getSelectedItem();
            String naslov = naslovTextBox.getText();


            if (!gesloMatches(geslo, ponoviteGeslo)) {
                JOptionPane.showMessageDialog(null, "Gesli se ne ujemata.", "Napaka", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String hashedGeslo = HashPassword.hashPassword(new String(geslo));

            vstaviUporabnikaVDatabase(uporabniskoime, hashedGeslo, ime, priimek, email, telefonska, naslov, kraj);
        }


        private boolean gesloMatches(char[] geslo1, char[] geslo2) {
            return java.util.Arrays.equals(geslo1, geslo2);
        }



        private void vstaviUporabnikaVDatabase(String uporabniskoime, String geslo, String ime ,
                                               String priimek, String email, String telefonska, String naslov, String kraj) {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            try (Connection connection = databaseConnection.getConnection()) {
                String sql = "SELECT dodaj_uporabnika14(?, ?, ?, ?, ?, ?, ?, ?)";
                try (CallableStatement statement = databaseConnection.prepareCall(sql, connection)) {
                    statement.setString(1, ime);
                    statement.setString(2, priimek);
                    statement.setString(3, uporabniskoime);
                    statement.setString(4, geslo);
                    statement.setInt(5, Integer.parseInt(telefonska));
                    statement.setString(6, email);
                    statement.setString(7, kraj);
                    statement.setString(8, naslov);

                    statement.execute();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            UserRegistration registrationFrame = new UserRegistration();
            registrationFrame.setSize(500, 500);
            registrationFrame.setTitle("Registracija");
            registrationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            registrationFrame.setVisible(true);

        });

    }

}
