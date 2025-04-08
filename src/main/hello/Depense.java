package main;

import utils.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class Depense extends BaseObject {
    private int idCredit;
    private double montant;
    
    // Constructeurs
    public Depense() {
       
    }
    
    public Depense(double montant) {
        this.montant = montant;
    }

    public Depense(double montant,int idCredit) {
        this.montant = montant;
        this.idCredit = idCredit;
    }

    public Depense(int idCredit, double montant) {
        this.idCredit = idCredit;
        this.montant = montant;
    }
    
    public Depense(int id, int idCredit, Double montant) {
        super.setId(id);
        this.idCredit = idCredit;
        this.montant = montant;
    }
    
    // Getters et Setters
    public double getMontant() {
        return montant;
    }
    
    public void setMontant(double montant) {
        this.montant = montant;
    }

    public int getidCredit() {
        return idCredit;
    }
    
    public void setidCredit(int id) {
        this.idCredit = id;
    }
    
    // Méthode toString
    @Override
    public String toString() {
        return "Depense{id=" + id + ", idCredit= " + idCredit + ", montant='" + montant + "'}";
    }
    
    // Implémentation des méthodes CRUD
    @Override
    public void save() throws Exception {
        String request = "INSERT INTO Depense(credit_id, montant) VALUES (?,?)";
        try (Connection connection = DB.connect()) {
            try (PreparedStatement ps = connection.prepareStatement(request, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, idCredit);
                ps.setDouble(2, montant);
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
    public List<Depense> findAll() throws Exception {
        List<Depense> list = new ArrayList<Depense>();
        String request = "SELECT * FROM Depense";
        try (Connection connection = DB.connect()) {
            try (Statement s = connection.createStatement()) {
                try (ResultSet rs = s.executeQuery(request)) {
                    while (rs.next()) {
                        list.add(new Depense(
                            rs.getInt("id_depense"),
                            rs.getInt("credit_id"),
                            rs.getDouble("montant"))
                        );
                    }
                }
            }
        }
        return list;
    }

    
    
    @Override
    public Depense findById(int id) throws Exception {
        Depense dep = null;
        String request = "SELECT * FROM Depense WHERE id_depense=?";
        try (Connection connection = DB.connect()) {
            try (PreparedStatement ps = connection.prepareStatement(request)) {
                ps.setInt(1, id);  
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        dep = new Depense(
                            rs.getInt("id_depense"),
                            rs.getInt("credit_id"),
                            rs.getDouble("montant")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Une erreur SQL est survenue : " + e.getMessage());
            throw e;  
        }
        return dep;
    }
    
    @Override
    public void update() throws Exception {
        String request = "UPDATE Depense SET montant=? WHERE id_depense=?";
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
        String request = "DELETE FROM Depense WHERE id_depense=?";
        try (Connection connection = DB.connect()) {
            try (PreparedStatement ps = connection.prepareStatement(request)) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }
        }
    }

    
    public Depense getMontantTotal(int idCredit) throws Exception {
        Depense dep = null;
        String request = "SELECT credit_id, SUM(montant) AS total_montant FROM depense WHERE credit_id = ? GROUP BY credit_id";
    
        try (Connection connection = DB.connect()) {
            try (PreparedStatement ps = connection.prepareStatement(request)) {
                ps.setInt(1, idCredit);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        dep = new Depense(
                            rs.getInt("credit_id"),
                            rs.getDouble("total_montant")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Une erreur SQL est survenue : " + e.getMessage());
            throw e;
        }
    
        return dep;
    }
    
}