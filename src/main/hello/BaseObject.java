package main;

import utils.DB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public abstract class BaseObject {
    protected int id;
    
    // Getter et Setter pour l'id
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    
    public abstract void save() throws Exception;
    public abstract List<?> findAll() throws Exception;
    public abstract BaseObject findById(int id) throws Exception;
    public abstract void update() throws Exception;
    public abstract void delete() throws Exception;
}