/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BD;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author Fernando
 */
public class Conexion {
    private Statement statement;
    private Connection conexion;
    private String jdbc;
    private String ruta;
    private String usuario;
    private String contrasenia;
    
    public Conexion(){
        this.conexion = null;
        this.statement = null;
        this.jdbc = "com.mysql.jdbc.Driver";
        this.ruta = "jdbc:derby://localhost:1527/hope-batalla";
        this.usuario = "admo";
        this.contrasenia = "admo";
        
    }
    private void conectarse(){
        try{
            Class.forName(this.jdbc);
            this.conexion = DriverManager.getConnection(
            this.ruta, this.usuario, this.contrasenia);
            this.statement = this.conexion.createStatement();
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Ocurrió un error durante la ejecucion. Porfavor, intente nuevamente", "Error", JOptionPane.ERROR_MESSAGE);
        }catch (ClassNotFoundException e){
            JOptionPane.showMessageDialog(null, "Ocurrió un error durante la ejecucion. Porfavor, intente nuevamente", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public boolean ejecutar(String sentencia){
        try{
            this.conectarse();
            this.statement.executeUpdate(sentencia);
            return true;
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Ocurrió un error durante la ejecucion. Porfavor, intente nuevamente", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    public ResultSet consultar(String sentencia){
        ResultSet resultado =null;
        try{
            this.conectarse();
            resultado=statement.executeQuery(sentencia);
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Ocurrió un error durante la ejecucion. Porfavor, intente nuevamente", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return resultado;
    }
    
}
