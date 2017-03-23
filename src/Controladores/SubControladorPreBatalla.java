/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Modelos.ModeloMallaCurricular;
import Modelos.ModeloUsuario;
import Modelos.Personaje;
import Vistas.SubVistaSeleccionRamo;
import Vistas.VistaLogInFrame;
import java.awt.event.ActionEvent;
//import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Kevin
 */
public class SubControladorPreBatalla implements ActionListener{
    SubVistaSeleccionRamo subram;
    public ModeloUsuario modUsuario;
    public Personaje personaje;
    ModeloMallaCurricular modmalla;
    public int idUs;
    public int idPj;
    public String carrera;
    
    public SubControladorPreBatalla(VistaLogInFrame vislog, int idUs, int idPj){
        this.subram = new SubVistaSeleccionRamo(vislog, true);
        this.subram.setLocationRelativeTo(null);
        this.subram.agregarListeners(this);
        this.modmalla = new ModeloMallaCurricular();
        this.idPj = idPj;
        this.modUsuario = new ModeloUsuario();
        this.personaje = new Personaje();
        this.idUs = idUs;
        this.extraerAliados();
        this.agregarRamos();
    }
    
    public void extraerAliados(){
        String[] usuario = modUsuario.consultar(this.idUs).split("CORTAR");
        if (usuario[4].equals("1")){
            this.carrera = "ASIGNATURAS_EJECUCION";
        }else if(usuario[4].equals("2")){
            this.carrera = "ASIGNATURAS_CIVIL";
        }
        try {
            ArrayList<Integer> aliados = modUsuario.obtenerAliados(this.idUs);
            //en el for se extraerá el nombre y rol de cada personaje.
            //se pintarán para la jcombbox1 y la id a la jcombobox3
            for (int idPsj:aliados){
                String[] pjAliado = personaje.consultar(idPsj).split("CORTAR");
                subram.jComboBox1.addItem(pjAliado[1]+" - "+pjAliado[3]);
                subram.jComboBox3.addItem(Integer.toString(idPsj));
            }
            
            
        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null,"Error realizando la solicitud del usuario","Error",
                                JOptionPane.ERROR_MESSAGE); 
        }
        
    
        
    }
    
    private void agregarRamos(){
        ArrayList<Integer> avance = modmalla.obtenerAvance(this.idUs);
        int verifNivel = 0;
        int Nivel = 1;
        boolean terminado = false;
        while (!terminado){
            ArrayList<Integer> ramosNivel = modmalla.obtenerNivel(carrera, Nivel);
            for (int i:ramosNivel){
                if (!(avance.contains(i))){
                    verifNivel++;
                    String ramo = modmalla.StringConsultarRamo(carrera, i);
                    subram.jComboBox5.addItem(ramo);
                    subram.jComboBox6.addItem(Integer.toString(i));
                }
            }
            if (verifNivel == 0){
                JOptionPane.showMessageDialog(subram, "Bien! Desbloqueaste nuevos ramos",
                        "Felicitaciones", JOptionPane.INFORMATION_MESSAGE);
                Nivel++;
            }else{
                terminado = true;
            }
        }
    
        
        
    }
    
    private void agregarPersonaje() {
        if (subram.jComboBox2.getItemCount() >=4){
            JOptionPane.showMessageDialog(null,"Solo se puede ingresar con 4 aliados", "ERROR",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }else if(subram.jComboBox1.getItemCount() == 0){
            JOptionPane.showMessageDialog(null,"no quedan amigos para agregar", "ERROR",
                    JOptionPane.ERROR_MESSAGE);
            return;
            
        }
        int pos = subram.jComboBox1.getSelectedIndex();
        int idPsj = Integer.parseInt(subram.jComboBox3.getItemAt(pos));
        String[] pjAliado = personaje.consultar(idPsj).split("CORTAR");
        subram.jComboBox2.addItem(pjAliado[1]+" - "+pjAliado[3]);
        subram.jComboBox4.addItem(Integer.toString(idPsj));
        subram.jComboBox1.removeItemAt(pos);
        subram.jComboBox3.removeItemAt(pos);
        
        
    }
    private void removerPersonaje() {
        if (subram.jComboBox2.getItemCount() == 0){
            JOptionPane.showMessageDialog(null,"no quedan amigos para eliminar", "ERROR",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        int pos = subram.jComboBox2.getSelectedIndex();
        int idPsj = Integer.parseInt(subram.jComboBox4.getItemAt(pos));
        String[] pjAliado = personaje.consultar(idPsj).split("CORTAR");
        subram.jComboBox1.addItem(pjAliado[1]+" - "+pjAliado[3]);
        subram.jComboBox3.addItem(Integer.toString(idPsj));
        subram.jComboBox2.removeItemAt(pos);
        subram.jComboBox4.removeItemAt(pos);
        
        
    }
    
    

    public void mostrarTomaRamos(){
        this.subram.setLocationRelativeTo(null);
        this.subram.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String accion = e.getActionCommand();
        switch (accion){
            case "Agregar":
                this.agregarPersonaje();
                //se agrega el personaje seleccionado
                break;
            case "Quitar":
                this.removerPersonaje();
                //se quita el personaje seleccionado
                break;
            case "Jugar nivel":
                this.jugarNivel();
                
                
                //se juega el nivel seleccionado
                break;
                
        }
        
    }

    private void jugarNivel() {
        ArrayList<Integer> idsAliados = new ArrayList<>();
        idsAliados.add(this.idPj);
        for (int i=0;i<subram.jComboBox2.getItemCount();i++){
            idsAliados.add(Integer.parseInt(subram.jComboBox4.getItemAt(i)));
        }
        int pos = subram.jComboBox5.getSelectedIndex();
        int idRamo = Integer.parseInt(subram.jComboBox6.getItemAt(pos));
        this.subram.cerrar();
        ControladorTablero contab = new ControladorTablero(idsAliados, this.idPj, idRamo);
        contab.mostrarTablero();
        
        
    }
    
    
    
    

   
    
}
