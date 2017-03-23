/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

import BD.Conexion;
import DAO.DAOLogIn;
import DAO.JugadorDAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Fernando
 */
public class ModeloLogIn {
    /*String nombre;
    String password;
    String idPj;
    String idJgd;*/
    
    
    
    /*public String consultar(String nombre){
        Conexion conexion = new Conexion();
        JugadorDAO jugadordao = new JugadorDAO();
        ResultSet resultado = conexion.consultar(jugadordao.consultarUsario(nombre));
        try {
            if (resultado.next()){
                this.idJgd = resultado.getString("ID_JUGADOR");
                this.idPj = resultado.getString("ID_PERSONAJE");
                this.nombre = resultado.getString("NOMBRE_JUGADOR");
                this.password = resultado.getString("CONTRASENIA_JUGADOR");
                return nombre+" "+password+" "+idPj+" "+idJgd;
            }else{
                JOptionPane.showMessageDialog(null, "Error, usuario no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                return "Error. Usuario no encontrado";
            }
        }catch (SQLException e){
            e.printStackTrace();
            return "Error";
        }
        
    }*/
}
