package Control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import Connection.ConnectionFactory;

public class CarrosDAO {
    // Códigos para o banco de dados
    private Connection connection;

    // Construtor
    public CarrosDAO(Connection connection) {
        this.connection = ConnectionFactory.getConnection();
    }

    // Métodos
    public void adicionarCarro(String modelo, String marca, int ano, String cor, String placa, double valor) {
        String sql = "INSERT INTO Carros (placa, modelo, marca, ano, cor, valor) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, placa);
            stmt.setString(2, modelo);
            stmt.setString(3, marca);
            stmt.setInt(4, ano);
            stmt.setString(5, cor);
            stmt.setDouble(6, valor);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir dados no banco de dados.", e);
        } finally {
            ConnectionFactory.closeConnection(this.connection);
        }
    }
    

}
