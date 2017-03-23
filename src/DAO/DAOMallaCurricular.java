/*+
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

/**
 *
 * @author Fernando
 */
public class DAOMallaCurricular {
    
    public String consultarRamo(String carrera, int id){
        return "SELECT * FROM "+carrera+" WHERE ID_RAMO="+id;
        }
    
    public String consultarNivel(String carrera, int idNivel){
        return "SELECT * FROM "+carrera+" WHERE NIVEL_RAMO = "+idNivel;
    }
    
    public String aprobarRamo(int idUs, int idRamo, int idCarr){
        return "INSERT INTO AVANCE(ID_USUARIO,ID_RAMO,ID_CARRERA) VALUES ("+idUs+","+idRamo+","+
                idCarr+")";
    }
    
    public String obtenerAvance(int idUs){
        return "SELECT * FROM AVANCE WHERE ID_USUARIO="+idUs;
    }
}
