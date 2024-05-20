import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Vector;

public class UserInterface extends JFrame {

    private int loggedInUserId;
    private JTable table;
    private JButton addButton, editButton, deleteButton;
    private DefaultTableModel tableModel;

    public UserInterface(int userId) {
        loggedInUserId = userId;
        setTitle("Urejanje stadionov");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        addButton = new JButton("Dodaj");
        editButton = new JButton("Uredi");
        deleteButton = new JButton("Izbriši");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        naloziPodatke();

        addButton.addActionListener(e -> dodajStadion());
        editButton.addActionListener(e -> urediStadion());
        deleteButton.addActionListener(e -> izbrisiStadion());
    }


    private void naloziPodatke() {

        tableModel.setRowCount(0);

        try (Connection conn = new DatabaseConnection().getConnection()) {
            String sql = "SELECT * FROM stadioni";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            Vector<String> columnNames = new Vector<>();
            for (int i = 1; i <= columnCount; i++) {
                columnNames.add(metaData.getColumnName(i));
            }
            tableModel.setColumnIdentifiers(columnNames);

            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.add(rs.getObject(i));
                }
                tableModel.addRow(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void dodajStadion() {
        JTextField imeField = new JTextField();
        JTextField kapacitetaField = new JTextField();
        JTextField letoUstanovitveField = new JTextField();
        JTextField uporabnikField = new JTextField(Login.LoggedInUporabniskoime);
        JTextField krajField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(5, 2));
        panel.add(new JLabel("Ime: "));
        panel.add(imeField);
        panel.add(new JLabel("Kapaciteta: "));
        panel.add(kapacitetaField);
        panel.add(new JLabel("Leto ustanovitve: "));
        panel.add(letoUstanovitveField);
        panel.add(new JLabel("Uporabnik: "));
        panel.add(uporabnikField);
        panel.add(new JLabel("Kraj: "));
        panel.add(krajField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Dodajanje stadiona",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            int id = loggedInUserId;
            String ime = imeField.getText();
            int kapaciteta = Integer.parseInt(kapacitetaField.getText());
            int letoUstanovitve = Integer.parseInt(letoUstanovitveField.getText());
            String uporabnik = uporabnikField.getText();
            String kraj = krajField.getText();

            try (Connection conn = new DatabaseConnection().getConnection()) {
                String sql = "{call dodaj_stadion1(?, ?, ?, ?, ?)}";
                CallableStatement stmt = conn.prepareCall(sql);
                stmt.setString(1, ime);
                stmt.setInt(2, kapaciteta);
                stmt.setInt(3, letoUstanovitve);
                stmt.setString(4, uporabnik);
                stmt.setString(5, kraj);
                stmt.execute();

                // Osveži podatke v tabeli
                naloziPodatke();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void urediStadion() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Prosimo, izberite stadion za urejanje.");
            return;
        }

        Object imeObject = tableModel.getValueAt(selectedRow, 1);
        String ime = imeObject != null ? imeObject.toString() : "";
        int kapaciteta = ((Number) tableModel.getValueAt(selectedRow, 2)).intValue();
        int letoUstanovitve = ((Number) tableModel.getValueAt(selectedRow, 3)).intValue();

        Object uporabnikObject = tableModel.getValueAt(selectedRow, 4);
        String uporabnik = uporabnikObject != null ? uporabnikObject.toString() : "";


        JTextField imeField = new JTextField(ime);
        JTextField kapacitetaField = new JTextField(String.valueOf(kapaciteta));
        JTextField letoUstanovitveField = new JTextField(String.valueOf(letoUstanovitve));
        JTextField uporabnikField = new JTextField(Login.LoggedInUporabniskoime);

        Object krajObject = tableModel.getValueAt(selectedRow, 5);
        String kraj = krajObject != null ? krajObject.toString() : "";

        JTextField krajField = new JTextField(kraj);

        JPanel panel = new JPanel(new GridLayout(6, 2));
        panel.add(new JLabel("Ime: "));
        panel.add(imeField);
        panel.add(new JLabel("Kapaciteta: "));
        panel.add(kapacitetaField);
        panel.add(new JLabel("Leto ustanovitve: "));
        panel.add(letoUstanovitveField);
        panel.add(new JLabel("Uporabnik: "));
        panel.add(uporabnikField);
        panel.add(new JLabel("Kraj: "));
        panel.add(krajField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Urejanje stadiona",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            ime = imeField.getText();
            kapaciteta = Integer.parseInt(kapacitetaField.getText());
            letoUstanovitve = Integer.parseInt(letoUstanovitveField.getText());
            uporabnik = uporabnikField.getText();
            kraj = krajField.getText();

            try (Connection conn = new DatabaseConnection().getConnection()) {
                String sql = "{call uredi_stadion5(?, ?, ?, ?, ?)}";
                CallableStatement stmt = conn.prepareCall(sql);
                stmt.setString(1, ime);
                stmt.setInt(2, kapaciteta);
                stmt.setInt(3, letoUstanovitve);
                stmt.setString(4, uporabnik);
                stmt.setString(5, kraj);
                stmt.execute();

                naloziPodatke();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void izbrisiStadion() {
        int[] selectedRows = table.getSelectedRows();
        if (selectedRows.length > 0) {
            try (Connection conn = new DatabaseConnection().getConnection()) {
                String sql = "{call izbrisi_stadion(?)}";
                CallableStatement stmt = conn.prepareCall(sql);

                for (int selectedRow : selectedRows) {
                    String stadiumName = (String) tableModel.getValueAt(selectedRow, 1);
                    stmt.setString(1, stadiumName);
                    stmt.addBatch();
                }

                stmt.executeBatch();


                tableModel.setRowCount(0);
                naloziPodatke();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Prosimo, izberite vsaj en stadion za izbris.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UserInterface frame = new UserInterface(Login.LoggedInUserId);
            frame.setVisible(true);
        });
    }
}
