package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class DB {
    public static Connection connect() throws SQLException {
        String URL = "jdbc:mysql://172.80.237.54:3306/db_s2_ETU003290?serverTimezone=UTC"; 
        Properties properties = new Properties();
        properties.setProperty("user", "ETU003290");
        properties.setProperty("password", "s0nypdtx");
    
        Connection connection = null;
    
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            connection = DriverManager.getConnection(URL, properties);
        } catch (SQLException e) {
            System.err.println("Erreur de connexion : " + e.getMessage());
            throw e;
        } catch (ClassNotFoundException e) {
            System.err.println("Pilote JDBC non trouvé : " + e.getMessage());
            throw new SQLException("Pilote JDBC non trouvé", e);
        }
    
        return connection;
    }
    
}