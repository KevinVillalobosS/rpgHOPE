/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

import BD.Conexion;
import DAO.EaoDAO;
import java.sql.ResultSet;
import javax.swing.JButton;

/**
 *
 * @author Kevin
 */
public class ModeloInventario {
    EaoDAO eaodao;
    public JButton cuadro;
    public int idCuadro;
    public Conexion conexion;
    public Personaje personaje;
    
    public ModeloInventario(){
        this.cuadro = new JButton();
        this.idCuadro = 0;
        eaodao = new EaoDAO();
    }
    
    
    
    
    
    
    
    
    
}
