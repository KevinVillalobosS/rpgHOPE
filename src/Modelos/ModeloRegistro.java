/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

import BD.Conexion;
import DAO.DAORegistro;
import Vistas.VistaRegistro;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
/**
 *
 * @author Fernando
 */

public class ModeloRegistro {
  
    
    
    /*public static void Registrar(String usuario,String clave,Integer rol){
        Conexion conexion = new Conexion();
         try
         {
            boolean resultado = conexion.conectar();
            if(resultado)
            {   
                Statement stm= conexion.crearConsulta();
                ResultSet rst=stm.executeQuery("SELECT ID_JUGADOR FROM JUGADOR");
                String id="0";
                while(rst.next())
                    {
                    id=rst.getString(1);
                }                
                Integer idJ=new Integer(id)+1;
                String usuarioAReg= "INSERT INTO JUGADOR (ID_JUGADOR, NOMBRE_JUGADOR, ID_ROLJUGADOR, CONTRASENA_JUGADOR) VALUES ("+idJ+",'"+usuario+"',0,'"+clave+"')";
                stm.execute(usuarioAReg); 
                conexion.desconectar();
            }
         }
         catch(SQLException sqle)
         {
             System.out.println("No es posible conectarse a " + Conexion.URL_CONEXION + " con excepcion : " + sqle.toString());
         }
    }*/

 } 