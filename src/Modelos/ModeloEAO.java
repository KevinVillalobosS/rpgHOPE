/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

import BD.Conexion;
import DAO.EaoDAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JButton;

/**
 *
 * @author Kevin
 */
public class ModeloEAO {
    public ArrayList<Integer> ids;
    public JButton icon_itm;
    public int id_obj;
    
    
    public ModeloEAO(){
        
    }
    public ModeloEAO(int a){
        icon_itm = new JButton();
        id_obj = 0;
    }
    
    
    
    public ArrayList buscarItems(String tipo) throws SQLException{
        ids = new ArrayList<>();
        
        Conexion conexion = new Conexion();
        EaoDAO eaodao = new EaoDAO();
        ResultSet resultado = conexion.consultar(eaodao.obtenerItems(tipo));
        while (resultado.next()){
            ids.add((resultado.getInt("ID_ITEM")));
        }
        return ids;
    }

    public String buscarItemEspecifico(Integer idObj) throws SQLException {
        Conexion conexion = new Conexion();
        EaoDAO eaodao = new EaoDAO();
        ResultSet item = conexion.consultar(eaodao.obtenerEspecifico(idObj));
        if (item.next()){
            String nomItm = item.getString("NOM_ITEM");
            String imagen = item.getString("IMAGEN");
            String descripcion = item.getString("DESCRIPCION");
            String boost = item.getString("BOOST");
            String a_boost = item.getString("A_BOOST");
            String tipo = item.getString("TIPO");
            String precio = item.getString("PRECIO");
            
            return nomItm+"Cortar"+imagen+"Cortar"+descripcion+"Cortar"+boost+"Cortar"+a_boost+"Cortar"+tipo+"Cortar"
                    +precio;
        }
        return "error";
    }

    public boolean comprarItem(int idPj, int idObj) {
        Conexion conexion = new Conexion();
        EaoDAO eaodao = new EaoDAO();
        boolean compra = conexion.ejecutar(eaodao.comprarItem(idPj,idObj));
        return compra;
        
    }
    
}
