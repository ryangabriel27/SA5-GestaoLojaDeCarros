package Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.management.RuntimeErrorException;

public class ConnectionFactory {
    // Atributos
    private static final String url = "jdbc:postgresql://localhost:5432/postgres";
    private static final String usuario = "postgres"; // nome do ADM do banco
    private static final String senha = "postgres";

    // métodos
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(url, usuario, senha);
        } catch (Exception e) {
            throw new RuntimeErrorException(null, "Erro ao obter conexão com o banco de dados");
        }
    }

    public static void closeConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}
