/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

/**
 *
 * @author Fernando
 */
public class DAORegistro {
        
    public String agregarUsuario(String nombre, String contrasenia, int id_rol, int id_carrera){
        return "insert into JUGADOR(NOMBRE_JUGADOR, ID_ROLJUGADOR, CONTRASENIA_JUGADOR, ID_CARRERA, DINEROJGDR)"
                + " VALUES ('"+nombre+"',"+id_rol+",'"+contrasenia+"',"+id_carrera+","+200000+")";
    }    

    public String insertarPersonajes(int id_pj, int id_mjAmg, int id_jgd) {
        return "update JUGADOR set ID_PERSONAJE="+id_pj+", ID_MJ_AMIGO="+id_mjAmg+" where ID_JUGADOR="+id_jgd;
       
    }
    public String obtenerUltimoRegistro(){
        return "SELECT * FROM ADMO.JUGADOR order by ID_JUGADOR desc FETCH FIRST 1 ROWS ONLY";
    }
}
