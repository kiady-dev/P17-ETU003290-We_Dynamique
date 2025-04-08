package main;

import utils.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class Client extends BaseObject {
    private String email;
    private String mdp;

    
    // Constructeurs
    public Client() {
       
    }

    public Client(int id) {
        super.setId(id);
    }
    
    public Client(String email) {
        this.email = email;
    }

    public Client(String email,String mdp) {
        this.email = email;
        this.mdp = mdp;
    }

    // public Client(String email,int idDept) {
    //     this.email = email;
    //     this.idDept = idDept;
    // }
    
    // public Client(int id, int idDept, String email) {
    //     super.setId(id);
    //     this.idDept = idDept;
    //     this.email = email;
    // }


    public Client(int id, String email) {
        super.setId(id);
        this.email = email;
    }

    public Client(int id, String email, String mdp) {
        super.setId(id);
        this.email = email;
        this.mdp = mdp;
    }
    
    // Getters et Setters
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    public String getMdp() {
        return mdp;
    }
    
    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    // public int getidDept() {
    //     return idDept;
    // }
    
    // public void setidDept(int id) {
    //     this.idDept = id;
    // }
    
    // Méthode toString
    // @Override
    // public String toString() {
    //     return "Client{id=" + id + ", idDept= " + idDept + ", email='" + email + "'}";
    // }

    @Override
    public String toString() {
        return "Client{id=" + id + ", email= " + email + ", mdp='" + mdp + "'}";
    }
    
    
    // Implémentation des méthodes CRUD
    @Override
    public void save() throws Exception {
        String request = "INSERT INTO Client(email,mot_de_passe) VALUES (?,?)";
        try (Connection connection = DB.connect()) {
            try (PreparedStatement ps = connection.prepareStatement(request, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, email);
                ps.setString(2, mdp);
                ps.executeUpdate();
                
                // Récupération de l'ID généré
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        this.id = rs.getInt(1);
                    }
                }
            }
        }
    }
    

    @Override
    public List<Client> findAll() throws Exception {
        List<Client> list = new ArrayList<Client>();
        String request = "SELECT * FROM Client";
        try (Connection connection = DB.connect()) {
            try (Statement s = connection.createStatement()) {
                try (ResultSet rs = s.executeQuery(request)) {
                    while (rs.next()) {
                        list.add(new Client(
                            rs.getInt("id"),
                            rs.getString("email"),
                            rs.getString("mot_de_passe"))
                        );
                    }
                }
            }
        }
        return list;
    }

    
    
    
    @Override
    public Client findById(int id_client) throws Exception {
        Client client = null;
        String request = "SELECT * FROM Client WHERE id=?";
        try (Connection connection = DB.connect()) {
            try (PreparedStatement ps = connection.prepareStatement(request)) {
                ps.setInt(1, id_client);  
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        client = new Client(
                            rs.getInt("id"),
                            rs.getString("email"),
                            rs.getString("mot_de_passe")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Une erreur SQL est survenue : " + e.getMessage());
            throw e;  
        }
        return client;
    }
    
    @Override
    public void update() throws Exception {
        String request = "UPDATE Client SET email=? WHERE id=?";
        try (Connection connection = DB.connect()) {
            try (PreparedStatement ps = connection.prepareStatement(request)) {
                ps.setString(1, email);
                ps.setInt(2, id);
                ps.executeUpdate();
            }
        }
    }
    
    @Override
    public void delete() throws Exception {
        String request = "DELETE FROM Client WHERE id=?";
        try (Connection connection = DB.connect()) {
            try (PreparedStatement ps = connection.prepareStatement(request)) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }
        }
    }


    public Client getId(String Email, String mot_de_passe) throws Exception {
        Client client = null;
        String request = "SELECT id FROM client WHERE email = ? AND mot_de_passe = ?";
        try (Connection connection = DB.connect()) {
            try (PreparedStatement ps = connection.prepareStatement(request)) {
                ps.setString(1, Email);
                ps.setString(2, mot_de_passe);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        client = new Client(
                            rs.getInt("id")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Une erreur SQL est survenue : " + e.getMessage());
            throw e;  
        }
        return client;
    }

}