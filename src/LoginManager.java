import java.sql.*;

public class LoginManager {

    public static boolean preglej_uporabnika(String uporabniskoime, String email, String geslo) {

        DatabaseConnection databaseConnection = new DatabaseConnection();
        try (Connection connection = databaseConnection.getConnection()) {
            String sql = "{ ? = call preglej_uporabnika7(?, ?, ?) }";
            try (CallableStatement statement = databaseConnection.prepareCall(sql, connection)) {
                statement.registerOutParameter(1, Types.BOOLEAN);
                statement.setString(2, uporabniskoime);
                statement.setString(3, email);
                statement.setString(4, geslo);
                statement.execute();
                return statement.getBoolean(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }


}
