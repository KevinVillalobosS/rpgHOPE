/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

/**
 *
 * @author Kevin
 */
public class JugadorDAO {
    
    
    public String contratar(int idJgd, int idPj, int cantBatt){
        return "insert into FOROGRIEGO (ID_JUGADOR, IDPJ, CANTBATLL) values ("+idJgd+","+idPj+","+cantBatt+")";
    }
    
    public String consultarUsario(String nombre){
        return "select * from JUGADOR where NOMBRE_JUGADOR='"+nombre+"'";
        
    } public String consultarUsario(int idUs){
        return "select * from JUGADOR where ID_JUGADOR="+idUs;
        
    } 
    
    public String actualizarDinero(int id_jgd, int nuevoDinero) {
        return "update JUGADOR set DINEROJGDR="+nuevoDinero+"where ID_JUGADOR="+id_jgd;
       
    }
    public String obtenerDinero(int id_jgd){
        return "SELECT DINEROJGDR FROM JUGADOR WHERE ID_JUGADOR="+id_jgd;
    }
    public String consultarContrasenia(String nombre){
        return "select CONTRASENIA_JUGADOR from JUGADOR where NOMBRE_JUGADOR="+nombre;
  
    }

    public String obtenerAliados(int idJgd) {
        return "select * from FOROGRIEGO where ID_JUGADOR="+idJgd;
    }

    public String terminarContrato(int idJgd, int idPj) {
        return "DELETE FROM FOROGRIEGO WHERE ID_JUGADOR ="+idJgd+" AND IDPJ ="+idPj;
    }
    
    
    
    
}
