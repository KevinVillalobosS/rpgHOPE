/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

import javax.swing.JButton;

/**
 *
 * @author Kevin
 */
public class ModeloPicAPix {
    public JButton cuadro;
    public int valor;
    public boolean esObjetivo;
    
    public ModeloPicAPix(){
        cuadro = new JButton();
        valor =  0;
        esObjetivo = false;
    }
    
}
