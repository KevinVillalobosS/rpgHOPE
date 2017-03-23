/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Modelos.ModeloEAO;
import Modelos.ModeloItem;
import Modelos.ModeloUsuario;
import Vistas.SubVistaMejora;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Kevin
 */
public class SubControladorMejora  implements ActionListener{
    ModeloItem moditem;
    ModeloEAO modeao;
    SubVistaMejora subvismej;
    ModeloUsuario modus;
    public String[] itmAMejorar;
    public String[] Mejorado;
    int aMejorar;
    private ControladorInventario coninv;
    int itmMejorado;
    int idUs;
    
    
    public SubControladorMejora(int idAMejorar, ControladorInventario coninv){
        this.coninv = coninv;
        this.subvismej = new SubVistaMejora(null,true);
        this.aMejorar = idAMejorar;
        this.modeao = new ModeloEAO();
        this.modus = new ModeloUsuario();
        subvismej.addListeners(this);
        this.moditem = new ModeloItem();
        obtenerItemMejorado();
    }
    
    public void obtenerItemMejorado(){
       
        this.itmMejorado = moditem.obtenerMejora(this.aMejorar);
        ModeloEAO modeao = new ModeloEAO();
        try {
            this.itmAMejorar = modeao.buscarItemEspecifico(this.aMejorar).split("Cortar");
            this.Mejorado = modeao.buscarItemEspecifico(this.itmMejorado).split("Cortar");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Error realizando la solicitud del usuario","Error",
                                JOptionPane.ERROR_MESSAGE); 
        }
        subvismej.jLabel3.setText((Mejorado[0]));
        subvismej.jLabel2.setText((itmAMejorar[0]));
        subvismej.jTextArea1.append(this.itmAMejorar[2]);
        subvismej.jTextArea2.append(this.Mejorado[2]);
        
    }
    
    
    
    public void mostrarVista(){
        this.subvismej.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == subvismej.jButton1){
            try {
                int dinero = Integer.parseInt(modus.obtenerDinero(modus.getIdUs()));
                int precio = Integer.parseInt(this.Mejorado[6]);
                if (dinero>precio){
                    
                    coninv.quitarDeInventario(this.aMejorar);
                    modeao.comprarItem(ModeloUsuario.idPsj, this.itmMejorado);
                    modus.descontarDinero(ModeloUsuario.idUs, dinero-precio);
                    //Quitar del inventario el primer item
                    //Agregar al inventario el segundo item
                }else{
                    JOptionPane.showMessageDialog(subvismej, "Error, no hay suficiente dinero", "Error",
                             JOptionPane.ERROR_MESSAGE);
                }
                //Se mejora el obj
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null,"Error realizando la solicitud del usuario","Error",
                                JOptionPane.ERROR_MESSAGE); 
            }
        }else if(e.getSource() == subvismej.jButton2){
            //se cancela la mejora
            
        }
    }
    
}
