/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Modelos.Personaje;
import Vistas.SubVistaDatosBatalla;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;

/**
 *
 * @author Kevin
 */
public class SubControladorDatos implements ActionListener {

    private final ArrayList<Integer> jugadores;
    private final ArrayList<Integer> aliados;
    private final ArrayList<Integer> enemigos;
    private final SubVistaDatosBatalla subvisdat;
    private final Personaje personaje;
    private ImageIcon foto;
    private int defensa;
    private int ataque;
    private int vida;
    private int idPj;

    public SubControladorDatos(ArrayList<Integer> jugadores, ArrayList<Integer> aliados, ArrayList<Integer> enemigos){
        
        this.jugadores = jugadores;
        this.aliados = aliados;
        this.personaje = new Personaje();
        this.subvisdat = new SubVistaDatosBatalla(null,true);
        this.subvisdat.agregarListeners(this);
        this.enemigos = enemigos;
        this.buscarPersonajes();
        
        this.subvisdat.setVisible(true);
    }
    private void buscarPersonajes(){
        this.subvisdat.jComboBox1.removeAllItems();
        this.subvisdat.jComboBox2.removeAllItems();
        this.subvisdat.jComboBox3.removeAllItems();
        this.subvisdat.jComboBox4.removeAllItems();
        for (int pj:jugadores){
            String[] consulta = personaje.consultar(pj).split("CORTAR");
            if (this.aliados.contains(pj)){
                subvisdat.jComboBox1.addItem(consulta[1]);
                subvisdat.jComboBox2.addItem(Integer.toString(pj));
            }else if(this.enemigos.contains(pj)){
                subvisdat.jComboBox3.addItem(consulta[1]);
                subvisdat.jComboBox4.addItem(Integer.toString(pj));
            }
        }
        
    }
     public void mostrarSolicit(Personaje personaje){
        this.idPj = personaje.getId();
        //Se pone el nick en la vista
        subvisdat.jTextField1.setText(personaje.getNick());
        subvisdat.jTextField2.setText(personaje.getRol());
        subvisdat.jTextField3.setText(personaje.getTipo());
        //se extrae la vida del personaje y se guarda en una variable 
        this.vida = personaje.getVida();
        subvisdat. jProgressBar4.setValue(vida);
        //Se realiza un toque decorativo en relacion a la cantidad de vida
        //si está cerca de morir, la barra de vida se pondrá roja
        if (personaje.getVida()<25){
            subvisdat.jProgressBar4.setForeground(Color.red);
        }
        else{
            subvisdat.jProgressBar4.setForeground(Color.blue);
        }
        subvisdat.jProgressBar4.setStringPainted(true);
        //Ataque
        subvisdat.barraMagia.setValue(personaje.getMagia());
        subvisdat.barraMagia.setStringPainted(true);
        
        this.ataque = personaje.getAtaque();
        subvisdat.barraAtaque.setValue(ataque);
        subvisdat.barraAtaque.setStringPainted(true);
        //Defensa
        this.defensa = personaje.getDefensa();
        subvisdat.barraDefensa.setValue(defensa);
        subvisdat.barraDefensa.setStringPainted(true);
        //Imagen
        this.foto = new ImageIcon(getClass().getResource(personaje.getImagen()));
        subvisdat.jLabel8.setIcon(foto);
        //icono
     }
    
    public void mostrarPj(int B) {
        String[] consulta = personaje.consultar(B).split("CORTAR");
        Personaje jugador = new Personaje();
        jugador.setId(B);
        jugador.setVida(Integer.parseInt(consulta[4]));
        jugador.setNick(consulta[1]);
        jugador.setTipo(consulta[2]);
        jugador.setRol(consulta[3]);
        jugador.setMovimiento(Integer.parseInt(consulta[7]));
        jugador.setImgTablero(consulta[11]);
        jugador.setAtaque(Integer.parseInt(consulta[6]));
        jugador.setDefensa(Integer.parseInt(consulta[5]));
        jugador.setMagia(Integer.parseInt(consulta[8]));
        jugador.setImagen(consulta[9]);
        jugador.setPosicionX(Integer.parseInt(consulta[13]));
        jugador.setPosicionY(Integer.parseInt(consulta[14]));
        
        mostrarSolicit(jugador);
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == subvisdat.jButton1){
            int pos = subvisdat.jComboBox1.getSelectedIndex();
            int id = Integer.parseInt(subvisdat.jComboBox2.getItemAt(pos));
            mostrarPj(id);
            //ver aliado
        }else if(ae.getSource() == subvisdat.jButton2){
            int pos = subvisdat.jComboBox3.getSelectedIndex();
            int id = Integer.parseInt(subvisdat.jComboBox4.getItemAt(pos));
            mostrarPj(id);
            //ver enemigo
        }
    }
    
    
    
    
    
    
}
