/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

import BD.Conexion;
import DAO.DAORegistro;
import DAO.JugadorDAO;
import Vistas.VistaRegistro;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Kevin
 */
public class ModeloUsuario {
    String dinero;
    ArrayList<Integer> idsAliados;
    public static int idUs;
    public static int idPsj;
    String nombre;
    String password;
    String idPj;
    String idJgd;
    String carrera;
    String idMjAmg;
    
    DAORegistro dregis = new DAORegistro();
    VistaRegistro visreg = new VistaRegistro();
    String id;
    
    
    public void setIdUs(int id){
        ModeloUsuario.idUs = id;
    }
    public int getIdUs(){
        return ModeloUsuario.idUs;
    }
    public ArrayList obtenerAliados(int idJgd) throws SQLException{
        idsAliados = new ArrayList<>();
        Conexion conexion = new Conexion();
        JugadorDAO jugadordao = new JugadorDAO();
        ResultSet consulta = conexion.consultar(jugadordao.obtenerAliados(idJgd));
        while (consulta.next()){
            idsAliados.add(consulta.getInt("IDPJ"));
        }
        return idsAliados;
    }
    public boolean contratarPj(int idJgd, int idPj, int cantBatt){
        Conexion conexion = new Conexion();
        JugadorDAO jugadordao = new JugadorDAO();
        boolean ejecutar = conexion.ejecutar(jugadordao.contratar(idJgd, idPj, cantBatt));
        return ejecutar;

    }
    public boolean descontarDinero(int idJgd, int nvoDinero){
        Conexion conexion = new Conexion();
        JugadorDAO jugadordao = new JugadorDAO();
        boolean ejecutar = conexion.ejecutar(jugadordao.actualizarDinero(idJgd, nvoDinero));
        return ejecutar;
    }
    
    
    public String obtenerDinero(int idJgd) throws SQLException{
        Conexion conexion = new Conexion();
        JugadorDAO jugadordao = new JugadorDAO();
        ResultSet consulta = conexion.consultar(jugadordao.obtenerDinero(idJgd));
        if (consulta.next()){
            dinero = consulta.getString("DINEROJGDR");
        }
        return dinero;
    }

    
    
   
     public String consultar(int idUs){
        Conexion conexion = new Conexion();
        JugadorDAO jugadordao = new JugadorDAO();
        ResultSet resultado = conexion.consultar(jugadordao.consultarUsario(idUs));
        try {
            if (resultado.next()){
                this.idJgd = resultado.getString("ID_JUGADOR");
                this.idPj = resultado.getString("ID_PERSONAJE");
                this.nombre = resultado.getString("NOMBRE_JUGADOR");
                this.password = resultado.getString("CONTRASENIA_JUGADOR");
                this.carrera = resultado.getString("ID_CARRERA");
                this.idMjAmg = resultado.getString("ID_MJ_AMIGO");
                return nombre+"CORTAR"+password+"CORTAR"+idPj+"CORTAR"+idJgd
                        +"CORTAR"+carrera+"CORTAR"+idMjAmg;
            }else{
                JOptionPane.showMessageDialog(null, "Error, usuario no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                return "Error. Usuario no encontrado";
            }
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null,"Error realizando la solicitud del usuario","Error",
                                JOptionPane.ERROR_MESSAGE); 
            return "Error";
        }
        
    }
    
    public String consultar(String nombre){
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
            JOptionPane.showMessageDialog(null,"Error realizando la solicitud del usuario","Error",
                                JOptionPane.ERROR_MESSAGE); 
            return "Error";
        }
        
    }
    
    
    public String obtenerUltimoRegistro(){
        Conexion conexion = new Conexion();
        ResultSet resultado = conexion.consultar(dregis.obtenerUltimoRegistro());
        try {
            if (resultado.next()){
                id = resultado.getString("ID_JUGADOR");
                return id;
            }else{
                JOptionPane.showMessageDialog(visreg, "Error. Aun no existen usuarios registrados.", "Error", JOptionPane.ERROR_MESSAGE);
                return "Error, aun no hay registros.";
            }
        }catch (SQLException e){
            JOptionPane.showMessageDialog(visreg, "Ocurrio un error durante la ejecucion. Porfavor, intente nuevamente.", "Error", JOptionPane.ERROR_MESSAGE);
            return "Error";
        }
    
    }

    public void quitarAliado(int idJg, int idPj) {
        Conexion conexion = new Conexion();
        JugadorDAO jugdao = new JugadorDAO();
        conexion.ejecutar(jugdao.terminarContrato(idJg, idPj));
        
    }
    
}
