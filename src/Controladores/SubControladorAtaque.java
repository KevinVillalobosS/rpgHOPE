/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Modelos.Personaje;
import Vistas.SubVistaAttCorDst;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JOptionPane;
import javax.swing.ListModel;

/**
 *
 * @author Kevin
 */
public class SubControladorAtaque implements ActionListener {
    SubVistaAttCorDst subcor;
    ControladorTablero contab;
    Personaje personaje;
    String rolPJ;
    String rolCPU;
    String[] datAtac;
    String[] datAtacado;
    private boolean seguir;
    
    int[][] reglaNivel ={
            {2,2,3,3,4,4,5,5,6,6},
            {2,2,2,3,3,4,4,5,5,6},
            {1,2,2,2,3,3,4,4,5,5},
            {1,1,2,2,2,3,3,4,4,5,5},
            {1,1,1,2,2,2,3,3,4,4},
            {0,1,1,1,2,2,2,3,3,4},
            {0,0,1,1,1,2,2,2,3,3},
            {0,0,0,1,1,1,2,2,2,3},
            {0,0,0,0,1,1,1,2,2,2},
            {0,0,0,0,0,1,1,1,2,2}
    };
    
    //Se planea modificar a parametro personaje de la clase Jugador
    //Por cercania de hito 3.2 se mantiene String
    //public SubControladorAtaque(Personaje atacante, Personaje atacado, ControladorTablero contab){}

    public SubControladorAtaque(String[] consultAtacante, String[] consultAtacado, ControladorTablero contab){
        subcor = new SubVistaAttCorDst(null,true);
        subcor.agregarListeners(this);
        this.contab = contab;
        this.datAtac = consultAtacante;
        this.datAtacado = consultAtacado;
        this.rolPJ = consultAtacante[3];
        this.personaje = new Personaje();
        this.rolCPU = consultAtacado[3];
        this.eleccionCPU(rolCPU);
        this.agrgAtaqCombobox(rolPJ);
        
    }
    
    
    
    
    public void mostrarDialog(){
        this.subcor.setLocationRelativeTo(null);
        this.subcor.setVisible(true);
    }
    
    public void eleccionCPU(String rolCPU){
        ArrayList<String> cpu = new ArrayList<>();
        cpu.add("Alto");
        cpu.add("Alto");
        cpu.add("Medio");
        cpu.add("Medio");
        cpu.add("Bajo");
        cpu.add("Bajo");
        switch (rolCPU){
            case "Mago":
                cpu.add("Especial");
                break;
            case "Ninja":
                cpu.add("Especial");
                cpu.add("Especial");
                break;
            case "Arquero":
                cpu.add("Especial");
                break;
            case "Guerrero":
                cpu.add("Especial");
                cpu.add("Especial");
                cpu.add("Especial");
                break;
        }
        
        for (int i=0;i<7;i++){
            int num = (int) (Math.random()*(cpu.size()));
            subcor.jComboBox3.addItem(cpu.get(num));
            cpu.remove(cpu.get(num));
        }
        
        this.mostrarCPU();
    
    }
    
    public void agrgAtaqCombobox(String tipoPj){
        //El stock definido por el grupo será de 1 ataques de cada tipo
        //además dee 1 ataque especial en caso de que el personaje
        //sea mago o arquero, 2 ataques especiales si es ninja, y 3
        //ataques especiales si es guerrero
        subcor.jComboBox1.addItem("Alto");
        subcor.jComboBox1.addItem("Alto");
        subcor.jComboBox1.addItem("Medio");
        subcor.jComboBox1.addItem("Medio");
        subcor.jComboBox1.addItem("Bajo");
        subcor.jComboBox1.addItem("Bajo");
        switch (tipoPj){
            case "Mago":
                subcor.jComboBox1.addItem("Especial");
                break;
            case "Arquero":
                subcor.jComboBox1.addItem("Especial");
                break;
            case "Ninja":
                subcor.jComboBox1.addItem("Especial");
                subcor.jComboBox1.addItem("Especial");
                break;
            case "Guerrero":
                subcor.jComboBox1.addItem("Especial");
                subcor.jComboBox1.addItem("Especial");
                subcor.jComboBox1.addItem("Especial");
                break;
        }
    
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == subcor.jButton10){
            String ataque = subcor.jComboBox1.getSelectedItem().toString();
            subcor.jComboBox2.addItem(ataque);
            subcor.jComboBox1.removeItem(ataque);
        }else if(e.getSource() == subcor.jButton3){
            String quitar = subcor.jComboBox2.getSelectedItem().toString();
            subcor.jComboBox1.addItem(quitar);
            subcor.jComboBox2.removeItem(quitar);
        }else if(e.getSource() == subcor.jButton1){
            this.ataque();
            
            //atacar
        }else if(e.getSource() == subcor.jButton1){
            //salir
        }
    }

    private void ataque() {
        int cont = subcor.jComboBox2.getItemCount();
        if (cont!=7){
            JOptionPane.showMessageDialog(subcor, "Porfavor, seleccione 7 ataques",
                    "ERROR", JOptionPane.ERROR_MESSAGE);
        }else{
            this.seguir=true;
            for (int i=0;i<7;i++){
                String ataqueUS = subcor.jComboBox2.getItemAt(i);
                String ataqueCPU = subcor.jComboBox3.getItemAt(i);
                this.obtenerResultado(ataqueUS, ataqueCPU);
            }
 
        }
    }
    
    private void aplicarResultados(String perdedor){
        if (!this.seguir){
            return;
        }
        if (perdedor.equals("Usuario")){
            int daño = Integer.parseInt(this.datAtac[6]);
            int defensa = Integer.parseInt(this.datAtacado[5]);
            int vida = Integer.parseInt(this.datAtacado[4]);
            daño = daño - defensa;
            vida = vida - daño;
            if (daño<0){
            JOptionPane.showMessageDialog(null, "El personaje "+this.datAtacado[1]+" se defendió completamente del ataque rival.",
                     "Defensa correcta", JOptionPane.INFORMATION_MESSAGE); 
            return;
            }
            if (vida <=0){
                this.datAtacado[4] = Integer.toString(vida);
                JOptionPane.showMessageDialog(subcor, "Gana Usuario. El personaje "+this.datAtacado[1]+" Ha muerto",
                        "No lloren por el...", JOptionPane.INFORMATION_MESSAGE);
                contab.reponerVida(this.datAtacado);
                contab.agregarExp(Integer.parseInt(datAtac[0]),500);
                this.subcor.matar();
                this.seguir=false;
                contab.pasarTurno();
                //contab.verificarTurno();
                return;
            }else{
                this.datAtacado[4] = Integer.toString(vida);
                JOptionPane.showMessageDialog(subcor, "Gana Usuario. Nueva vida CPU:"+this.datAtacado[4],
                        "No lloren por el...", JOptionPane.INFORMATION_MESSAGE);
                personaje.modificarVida(vida, Integer.parseInt(this.datAtacado[0]));
                
                
            }
            //idPj daña a idCPU
        }else if(perdedor.equals("CPU")){
            int daño = Integer.parseInt(this.datAtacado[6]);
            int defensa = Integer.parseInt(this.datAtac[5]);
            int vida = Integer.parseInt(this.datAtac[4]);
            daño = daño - defensa;
            vida = vida - daño;
            if (vida <=0){
                this.datAtacado[4] = Integer.toString(vida);
                JOptionPane.showMessageDialog(subcor, "Gana CPU. El personaje "+this.datAtac[1]+" Ha muerto",
                        "No lloren por el...", JOptionPane.INFORMATION_MESSAGE);
                contab.agregarExp(Integer.parseInt(datAtacado[0]),500);
                contab.reponerVida(this.datAtac);
                this.subcor.matar();
                this.seguir=false;
                contab.pasarTurno();
                return;

            }else{
                this.datAtac[4] = Integer.toString(vida);
                JOptionPane.showMessageDialog(subcor, "Gana CPU. Nueva vida Usuario:"+this.datAtac[4],
                        "No lloren el...", JOptionPane.INFORMATION_MESSAGE);
                personaje.modificarVida(vida, Integer.parseInt(this.datAtac[0]));
                

            }
            //idCPU daña a idPj
        }else if(perdedor.equals("Empate")){
            JOptionPane.showMessageDialog(subcor,"Ambos son muy fuertes, EMPATE!","Empate",
                    JOptionPane.INFORMATION_MESSAGE);
            //No hay tabla
        }
    
    }

    private void obtenerResultado(String ataqueUS, String ataqueCPU) {
        switch (ataqueUS){
            case "Bajo":
                if("Bajo".equals(ataqueCPU)){
                    aplicarResultados("Empate");
                }else if("Medio".equals(ataqueCPU)){
                    aplicarResultados("CPU");
                }else if("Alto".equals(ataqueCPU)){
                    aplicarResultados("Usuario");
                    
                }else if("Especial".equals(ataqueCPU)){
                    aplicarResultados("CPU");
                    
                }
                break;
            case "Medio":
                if("Bajo".equals(ataqueCPU)){
                    aplicarResultados("Usuario");
                    
                }else if("Medio".equals(ataqueCPU)){
                    aplicarResultados("Empate");
                    
                }else if("Alto".equals(ataqueCPU)){
                    aplicarResultados("CPU");
                    
                    
                }else if("Especial".equals(ataqueCPU)){
                    aplicarResultados("CPU");
                    
                }
                break;
            case "Alto":
                if("Bajo".equals(ataqueCPU)){
                    aplicarResultados("CPU");
                }else if("Medio".equals(ataqueCPU)){
                    aplicarResultados("Usuario");
                    
                }else if("Alto".equals(ataqueCPU)){
                    aplicarResultados("Empate");
                    
                }else if("Especial".equals(ataqueCPU)){
                    aplicarResultados("CPU");
                    
                }
                break;
            case "Especial":
                if("Bajo".equals(ataqueCPU)){
                    aplicarResultados("Usuario");
                }else if("Medio".equals(ataqueCPU)){
                    aplicarResultados("Usuario");
                }else if("Alto".equals(ataqueCPU)){
                    aplicarResultados("Usuario");
                }else if("Especial".equals(ataqueCPU)){
                    aplicarResultados("Empate");
                    
                }
                break;
        }
    }

    private void mostrarCPU() {
        int lvlUS = Integer.parseInt(this.datAtac[27]);
        int lvlCPU = Integer.parseInt(this.datAtacado[27]);         
        int cantMostrar = this.reglaNivel[lvlUS-1][lvlCPU-1];
        for (int i=0; i< cantMostrar; i++){
            String ataque = this.subcor.jComboBox3.getItemAt(i);
            
            if (i==0){
                this.subcor.jTextField1.setText(ataque);
            }else if(i==1){
                this.subcor.jTextField2.setText(ataque);
            }else if(i==2){
                this.subcor.jTextField3.setText(ataque);
            }else if(i==3){
                this.subcor.jTextField4.setText(ataque);
            }else if(i==4){
                this.subcor.jTextField5.setText(ataque);
            }else if(i==5){
                this.subcor.jTextField6.setText(ataque);
            }
        }

    }
    
}
