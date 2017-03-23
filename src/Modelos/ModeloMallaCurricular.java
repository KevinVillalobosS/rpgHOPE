/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

import BD.Conexion;
import DAO.DAOMallaCurricular;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 *
 * @author Fernando
 */
public class ModeloMallaCurricular {
    
    public JButton ramo;
    public int idRamo;
    
    
    public ModeloMallaCurricular(){
        this.ramo = new JButton();
        this.idRamo =0;
    }
    
    public boolean aprobarRamo(int idUs, int idRamo, int idCarrera){
        Conexion conexion = new Conexion();
        DAOMallaCurricular malladao = new DAOMallaCurricular();
        return(conexion.ejecutar(malladao.aprobarRamo(idUs, idRamo, idCarrera)));
    }
    
    public ArrayList<Integer> obtenerNivel(String carrera, int nivel){
        Conexion conexion = new Conexion();
        DAOMallaCurricular malladao = new DAOMallaCurricular();
        ResultSet consulta = conexion.consultar(malladao.consultarNivel(carrera,nivel));
        ArrayList<Integer> ramos = new ArrayList<>();
        try {
            while (consulta.next()){
                int ramoCarrera = consulta.getInt("ID_RAMO");
                ramos.add(ramoCarrera);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Error realizando la solicitud del usuario","Error",
                                JOptionPane.ERROR_MESSAGE); 
        }
        return ramos;
    }
    
 
    public ArrayList<Integer> obtenerAvance(int idUs){
        Conexion conexion = new Conexion();
        DAOMallaCurricular malladao = new DAOMallaCurricular();
        ResultSet consulta = conexion.consultar(malladao.obtenerAvance(idUs));
        ArrayList<Integer> ramos = new ArrayList<>();
        try {
            while (consulta.next()){
                int ramo = consulta.getInt("ID_RAMO");
                ramos.add(ramo);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Error realizando la solicitud del usuario","Error",
                                JOptionPane.ERROR_MESSAGE); 
        }
        return ramos;
    }
    
    public String StringConsultarRamo(String carrera, int ramo){
        Conexion conexion = new Conexion();
        DAOMallaCurricular malladao = new DAOMallaCurricular();
        ResultSet consulta = conexion.consultar(malladao.consultarRamo(carrera, ramo));
        try {
            if (consulta.next()){
                String nombreRamo = consulta.getString("NOMBRE_RAMO");
                return nombreRamo;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Error realizando la solicitud del usuario","Error",
                                JOptionPane.ERROR_MESSAGE); 
        }
        return null;
    }
    

    
    
    
    
}
    
/*
[17:53, 17/11/2016] KevinV [U]: Así rapidamente pensandolo                        
[17:54, 17/11/2016] KevinV [U]: Haria un contador                        
[17:54, 17/11/2016] KevinV [U]: Y este SI lo empezaria de 1                        
[17:54, 17/11/2016] KevinV [U]: Entonces en el loop                        
[17:54, 17/11/2016] KevinV [U]: ArrayList<Integer> ramosNivel = bd.getRamosDeNivel(Contador)                        
[17:54, 17/11/2016] KevinV [U]: Cada vez que entre al loop, solicitaré una lista con las ids de los ramos del nivel que tenga el contador                        
[17:55, 17/11/2016] KevinV [U]: Entonces parto pidiendo getRamosNivel(1)                        
[17:55, 17/11/2016] KevinV [U]: Y un segundo contador, para recorrer esa lista                        
[17:55, 17/11/2016] KevinV [U]: entonces dsps si(segundoContador == ramosNivel.size(){
break                        
[17:56, 17/11/2016] KevinV [U]: Saldrá del primer for, volverá al for principal, subirá en uno al contador1                        
[17:56, 17/11/2016] KevinV [U]: Entonces ahorar pediras ramos del segundo nivel                        
[17:56, 17/11/2016] KevinV [U]: Llenaras con tantos botones como ramos tengas                        
[17:56, 17/11/2016] KevinV [U]: Saldrá del loop, llegará al loop de afuera, pedirá ramos nivel 3                        
[17:56, 17/11/2016] KevinV [U]: Y así
 */