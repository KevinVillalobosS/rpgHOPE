/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Vistas.VistaSudoku;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 *
 * @author nixett
 */
public class controladorSudoku {
    
    private VistaSudoku principal;
    
  
    public controladorSudoku(){
        this.principal = new VistaSudoku();
        
        
    }
    
    public void mostrarVista(){
        this.principal.setSize(800, 600);
        this.principal.setLocationRelativeTo(null);
        this.principal.setVisible(true);
    }
    
    
    

    /*public void iniciar_vistaSudoku(){
        this.principal.jButton1.addActionListener(this);
        
        
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        Object boton = ae.getSource();
         
         
        if (boton == this.principal.jButton1){
            principal.setVisible(false);
        }
        
        
    }*/

    
    
   
    
    
}

    
