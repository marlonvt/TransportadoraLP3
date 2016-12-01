package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    
    public static Connection createConnection() {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/transportadora", "postgres", "admin");
        } catch (ClassNotFoundException ex) {
            System.err.println("Falha ao conectar com banco de dados: " + ex.getMessage());
        } catch (SQLException ex) {
            System.err.println("Falha ao conectar com banco de dados: " + ex.getMessage());
            ex.printStackTrace();
        }
        return connection;
    }
    
    public static void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException ex) {
            System.err.println("Falha ao fechar conexao com banco de dados: " + ex.getMessage());
        }
    }
}
