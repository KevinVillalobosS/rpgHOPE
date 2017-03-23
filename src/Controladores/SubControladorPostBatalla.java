/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Modelos.ModeloEAO;
import Modelos.ModeloForoGriego;
import Modelos.ModeloUsuario;
import Modelos.Personaje;
import Vistas.SubVistaPostBatalla;
import java.awt.event.ActionEvent;
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
public class SubControladorPostBatalla implements ActionListener {
    SubVistaPostBatalla subVisPost;
    ModeloEAO modeao;
    Personaje personaje;
    ModeloUsuario modus;
    ArrayList<Integer> enemigos;
    boolean victoria;
    ArrayList<Integer> aliados;
    private Integer idOfrecido;
    
    public SubControladorPostBatalla(ArrayList<Integer> enemigos,ArrayList<Integer> aliados, boolean victoria){
        
        this.subVisPost = new SubVistaPostBatalla(null,true);
        this.modeao = new ModeloEAO();
        this.enemigos = enemigos;
        this.enemigos = enemigos;
        modus = new ModeloUsuario();
        this.personaje = new Personaje();
        this.victoria = victoria;
        this.aliados = aliados;
        this.subVisPost.agregarListeners(this);
        this.escribirMensaje(victoria);
        this.mostrarPostVista();
        
        
    }
    
    
    public void mostrarPostVista() {
        this.subVisPost.setLocationRelativeTo(null);
        this.subVisPost.setVisible(true);
    }
    
    
    private void revisarTraicion(){
        if (this.victoria){
            for (int pj : this.aliados){
                String[] consultaPj = this.personaje.consultar(pj).split("CORTAR");
                //extraigo la traicion
                int traicion = Integer.parseInt(consultaPj[26]);
                if (traicion != 0){
                    int factorTraicion = (int) (Math.random()*(8-1)+1);
                    traicion = traicion + factorTraicion;
                     personaje.modificarTraicion(pj,traicion);

                }
            }
            
        }else{
            for (int pj : this.aliados){
                String[] consultaPj = this.personaje.consultar(pj).split("CORTAR");
                //extraigo la traicion
                int traicion = Integer.parseInt(consultaPj[26]);
                if (traicion != 0){
                    int factorTraicion = (int) (Math.random()*(8-1)+1);
                    traicion = traicion + factorTraicion;
                    if (traicion >=100){
                        JOptionPane.showMessageDialog(null, "OH NO! El personaje "+consultaPj[1]+
                                " Te ha traicionado! \nSi el personaje tenia items, estos no se "
                                        + "devolerán", "TRAICION",  JOptionPane.ERROR_MESSAGE);
                         modus.quitarAliado(ModeloUsuario.idUs, pj);
                        //El personaje traicionará al jugador
                    }else{
                        personaje.modificarTraicion(pj,traicion);
                        //actualizar la traicion del personaje
                    }
                }
            }
        }
        JOptionPane.showMessageDialog(subVisPost, "Batalla finalizada, hasta pronto!", "Hasta luego...",
                JOptionPane.INFORMATION_MESSAGE);
        this.subVisPost.cerrarVista();
        
    }
     private void escribirMensaje(boolean victoria) {
        try {
            if (victoria){
                ModeloUsuario modus = new ModeloUsuario();
                int plata = Integer.parseInt(modus.obtenerDinero(ModeloUsuario.idUs));
                plata = plata + 60000;
                modus.descontarDinero(ModeloUsuario.idUs,plata);
                this.subVisPost.jLabel2.setText("FELICITACIONES AL APROBADO!");
                this.subVisPost.jTextField1.setText("60.000");
                this.subVisPost.jTextField2.setText("15.000");
                this.agregarExp(350);
            }else{
                ModeloUsuario modus = new ModeloUsuario();
                int plata = Integer.parseInt(modus.obtenerDinero(ModeloUsuario.idUs));
                int perdida = plata*(5/100);
                plata = plata-perdida;
                modus.descontarDinero(ModeloUsuario.idUs,plata);
                this.subVisPost.jLabel2.setText("TENDRAS MAS OPORTUNIDADES");
                this.subVisPost.jTextField1.setText("- "+Integer.toString(perdida));
                this.subVisPost.jTextField2.setText("10.000");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Error realizando la solicitud del usuario","Error",
                                JOptionPane.ERROR_MESSAGE); 
            
        }
    }
    
    
    
    private void ofrecerPersonaje(){
        this.subVisPost.jPanel2.setVisible(false);
        this.subVisPost.jPanel3.setVisible(true);
        //Se elige una posicion al azar de la lista de enemigos
        int elecAzar = (int) (Math.random()*(this.enemigos.size()));
        this.idOfrecido = this.enemigos.get(elecAzar);
        String[] pjObtenido = this.personaje.consultar(idOfrecido).split("CORTAR");
        this.subVisPost.mostrarSolicitud(pjObtenido);        
    }
    
    
    private void mostrarItem() {
       this.subVisPost.jPanel1.setVisible(false);
       this.subVisPost.jPanel2.setVisible(true);
       int id_obj = (int) (Math.random()*(65-1)+1);
       if(modeao.comprarItem(ModeloUsuario.idPsj, id_obj)){
           JOptionPane.showMessageDialog(subVisPost, "Item adquirido", "Bien hecho...",
                   JOptionPane.INFORMATION_MESSAGE);
       }

       String[] datos;
        try {
            datos = modeao.buscarItemEspecifico(id_obj).split("Cortar");
            subVisPost.mostrarItemSolicitado(datos);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Error realizando la solicitud del usuario","Error",
                                JOptionPane.ERROR_MESSAGE); 
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.subVisPost.jButton1){
            this.mostrarItem();
        }else if(e.getSource() == this.subVisPost.jButton2){
            this.ofrecerPersonaje();
        }else if(e.getSource() == this.subVisPost.jButton5){
            this.revisarContratos();
            //Rechazar personaje
        
        }else if(e.getSource() == this.subVisPost.jButton6){
            ModeloUsuario modus = new ModeloUsuario();
            boolean seHizo = modus.contratarPj(ModeloUsuario.idUs, this.idOfrecido, 0);
            if (seHizo){
                personaje.modificarTraicion(this.idOfrecido, 0);
                JOptionPane.showMessageDialog(subVisPost, "Amistad confirmada! ", "Bien hecho...",
                        JOptionPane.INFORMATION_MESSAGE);
            }
            
            this.revisarContratos();
            //Aceptar personaje;
        }
        
    }

    private void revisarContratos() {
        for (int id:this.aliados){
            int cantBat = personaje.obtenerCantBat(id,ModeloUsuario.idUs);
            if (cantBat != 0){
                cantBat = cantBat - 1;
                if (cantBat == 0){
                    String[] consulta = personaje.consultar(id).split("CORTAR");
                   JOptionPane.showMessageDialog(subVisPost, "El contrato con "+consulta[1]+ " ha expirado", 
                           "Contrato Expirado",  JOptionPane.ERROR_MESSAGE); 
                   personaje.quitarAliado(ModeloUsuario.idUs,id);
                }else{
                   personaje.modificarCantBat(ModeloUsuario.idUs,id,cantBat);
                }
            }
            
        }
        this.revisarTraicion();
        
    }

    
    public void agregarExp(int idPj, int factor) {
        String[] consulta = personaje.consultar(idPj).split("CORTAR");
        int exp = Integer.parseInt(consulta[28]);
        int lvl = Integer.parseInt(consulta[27]);
        exp = exp + factor;
        personaje.modificarExp(idPj, exp);
        if (exp > lvl*10000){
            JOptionPane.showMessageDialog(null,"Felicidades, subiste de nivel","Bien",JOptionPane.INFORMATION_MESSAGE);
            personaje.modificarNivel(idPj, lvl+1);
        }
    }
    private void agregarExp(int i) {
        
        for (int id:this.aliados){
                this.agregarExp(id,i);
                
            
            
        }
    }

   

    
    
}
