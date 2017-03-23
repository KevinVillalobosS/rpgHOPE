/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

import java.util.ArrayList;
import javax.swing.JButton;
/**
 *
 * @author kevin
 */
public class Tablero {
    public JButton A;
    public int B;
    public int C;
    public ArrayList<Integer> vidaPjs;
    public ArrayList<Integer> movimientoPjs;
    
    
    public Tablero(){
         A = new JButton();
         B =0;
         C = 0;
         this.vidaPjs = new ArrayList<>();
        this.movimientoPjs = new ArrayList<>();
    }
    public Tablero(int a){
        this.vidaPjs = new ArrayList<>();
        this.movimientoPjs = new ArrayList<>();
    }
    public void aniadirVida(int vida){
        this.vidaPjs.add(vida);        
    }
    
    public ArrayList<Integer> obtenerVidas(){
        return this.vidaPjs;
    }
    public void aniadirMov(int mov){
        this.movimientoPjs.add(mov);
    }
    public ArrayList<Integer> obtenerMovs(){
        return this.movimientoPjs;
    }
    
   

   
    
        
        
        
            
        
    
    
}
