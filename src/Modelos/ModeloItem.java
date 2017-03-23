/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

import BD.Conexion;
import DAO.DAOItem;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Fernando
 */
public class ModeloItem {
    int idPj;
    Personaje personaje;
    int id_item, boost, a_boost, precio;
    String nom_item, imagen, descripcion, tipo;
    
   
    
    
    public int obtenerMejora(int idItm){
        Conexion conexion = new Conexion();
        DAOItem itemdao = new DAOItem();
        ResultSet consulta = conexion.consultar(itemdao.consultarMejora(idItm));
        try {
            if (consulta.next()){
                int mejorado = consulta.getInt("ID_MEJORA");
                return mejorado;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al intentar acceder a la base de datos. ", "ERROR", JOptionPane.ERROR_MESSAGE);
            //Logger.getLogger(ModeloItem.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    public boolean puedeMejorarsse(int idAMejorar){
        Conexion conexion = new Conexion();
        DAOItem itemdao = new DAOItem();
        ResultSet consulta = conexion.consultar(itemdao.consultarMejora(idAMejorar));
        try {
            return consulta.next(); 
        } catch (SQLException ex) {
            //JOptionPane.showMessageDialog(null, "Error al intentar acceder a la base de datos. ", "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
            //Logger.getLogger(ModeloItem.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

    
    
    
    
    
    
    
    
    
    
    
    public int getId_item() {
        return id_item;
    }

    public void setId_item(int id_item) {
        this.id_item = id_item;
    }

    public int getBoost() {
        return boost;
    }

    public void setBoost(int boost) {
        this.boost = boost;
    }

    public int getA_boost() {
        return a_boost;
    }

    public void setA_boost(int a_boost) {
        this.a_boost = a_boost;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public String getNom_item() {
        return nom_item;
    }

    public void setNom_item(String nom_item) {
        this.nom_item = nom_item;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    
    
}

