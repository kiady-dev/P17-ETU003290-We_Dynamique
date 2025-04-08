package main;

import utils.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class Credit extends BaseObject {
    private String libelle;
    private double solde;
    
    
    // Constructeurs
    public Credit() {
       
    }
    
    public Credit(String libelle) {
        this.libelle = libelle;
    }
    
    public Credit(int id, String libelle) {
        super.setId(id);
        this.libelle = libelle;
    }
    
    public Credit(int id, String libelle, double solde) {
        super.setId(id);
        this.libelle = libelle;
        this.solde = solde;
    }

    public Credit(String libelle, double solde) {
        this.libelle = libelle;
        this.solde = solde;
    }

    public Credit(double solde, int id) {
        this.solde = solde;
        super.setId(id);
    }

    // Getters et Setters
    public String getLibelle() {
        return libelle;
    }
    
    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public double getSolde() {
        return solde;
    }
    
    public void setSolde(double solde) {
        this.solde = solde;
    }
    
    // Méthode toString
    @Override
    public String toString() {
        return "Credit{id=" + id + ", libelle= " + libelle + ", solde='" + solde + "'}";
    }
    
    // Implémentation des méthodes CRUD
    @Override
    public void save() throws SQLException {
        String request = "INSERT INTO credit(libelle, solde) VALUES (?,?)";
        try (Connection connection = DB.connect()) {
            try (PreparedStatement ps = connection.prepareStatement(request, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, libelle);
                ps.setDouble(2, solde);
                ps.executeUpdate();
                
                // Récupération de l'ID généré
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        this.id = rs.getInt(1);
                    }
                }
                catch (SQLException e) {
                    throw e;
                }
            }
        }
    }
    
    @Override
    public List<Credit> findAll() throws Exception {
        List<Credit> list = new ArrayList<Credit>();
        String request = "SELECT * FROM credit";
        try (Connection connection = DB.connect()) {
            try (Statement s = connection.createStatement()) {
                try (ResultSet rs = s.executeQuery(request)) {
                    while (rs.next()) {
                        list.add(new Credit(
                            rs.getInt("id_credit"),
                            rs.getString("libelle"),
                            rs.getDouble("solde"))
                        );
                    }
                }
            }
        }
        return list;
    }
    
    @Override
    public Credit findById(int id) throws Exception {
        Credit typeMedicament = null;
        String request = "SELECT * FROM credit WHERE id_credit=?";
        try (Connection connection = DB.connect()) {
            try (PreparedStatement ps = connection.prepareStatement(request)) {
                ps.setInt(1, id);  
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        typeMedicament = new Credit(
                            rs.getInt("id_credit"),
                            rs.getString("libelle"),
                            rs.getDouble("solde")

                        );
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Une erreur SQL est survenue : " + e.getMessage());
            throw e;  
        }
        return typeMedicament;
    }
    
    @Override
    public void update() throws Exception {
        String request = "UPDATE credit SET solde = solde - ? WHERE id_credit=?";
        try (Connection connection = DB.connect()) {
            try (PreparedStatement ps = connection.prepareStatement(request)) {
                ps.setDouble(1, solde);
                ps.setInt(2, id);
                ps.executeUpdate();
            }
        }
    }

    public void updateSolde(double montant) throws Exception {
        String request = "UPDATE credit SET solde = solde - ? WHERE id_credit=?";
        try (Connection connection = DB.connect()) {
            try (PreparedStatement ps = connection.prepareStatement(request)) {
                ps.setDouble(1, montant);
                ps.setInt(2, id);
                ps.executeUpdate();
            }
        }
    }
    
    @Override
    public void delete() throws Exception {
        String request = "DELETE FROM credit WHERE id_credit=?";
        try (Connection connection = DB.connect()) {
            try (PreparedStatement ps = connection.prepareStatement(request)) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }
        }
    }


    
}