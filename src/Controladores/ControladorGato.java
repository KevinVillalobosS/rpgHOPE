/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Modelos.ModeloUsuario;
import Modelos.TresEnRaya;
import Vistas.MiniJuegoTresEnRaya;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Kevin
 */
public class ControladorGato implements ActionListener{
    TresEnRaya[][] juego;
    MiniJuegoTresEnRaya gato;
    public ImageIcon X = null;
    public ImageIcon O = null;
    int idJugador;
    
    
    public ControladorGato(int idJgd){
        gato = new MiniJuegoTresEnRaya(null,true);
        gato.jTextField4.setText("0");
        gato.jTextField3.setText("0");
        gato.jTextField2.setText("0");
        gato.jTextField1.setText("0");
        this.idJugador = idJgd;
        gato.agregarListeners(this);
        
        
    }

    
    
    
    public void agregarDinero(int dinero, int idJgd) throws SQLException {
        ModeloUsuario modus = new ModeloUsuario();
        int Dinero = Integer.parseInt(modus.obtenerDinero(idJgd));
        int nvDnro = Dinero + dinero;
        modus.descontarDinero(idJgd, nvDnro);
        
        
    }
        public void jugar(int contVert, int contHor, int contDiag, int contDiag2, int i) {
        int j = 0;
        if (contVert == 8 || contVert == 2 || contVert == 1){
            //jugar en vertical |
            while (!aplicarCambio(i,j,1)){
                j= (j==2) ? 0:j+1;
            }
            
        }else if (contHor == 8 || contHor ==2 || contHor==1){
            //jugar en horizontal ----
            while (!aplicarCambio(i,j,1)){
                i= (i==2) ? 0:i+1;
            }
        }else if (contDiag==8 || contDiag == 2 || contDiag == 1){
            while (!aplicarCambio(i, i, 1)){
                i= (i==2) ? 0:i+1;
            }
            
            
        }else if (contDiag2==8 || contDiag2==2 || contDiag2==1){
            while (!aplicarCambio(i,2-1,1)){
                i = (i==2) ? 0:i+1;
                
            }

        }
                
    }
    public void juegoCPU(){
        int sumatoria = 0;
        String turno = "CPU";
        for (int i=0;i<3;i++){
            int contVert = 0;
            int contHor = 0;
            int contDiag = 0;
            int contDiag2 = 0;
            for (int j=0;j<3;j++){
                sumatoria = sumatoria + juego[i][j].valor;
                contVert = contVert + juego[i][j].valor;
                contHor = contHor + juego[j][i].valor;
                contDiag = contDiag + juego[j][j].valor;
                contDiag2 = contDiag2 + juego[j][2-j].valor;
                
            }
            //primero revisa |, despues revisa |, y despues revisa | -> gato = | | |
            if(contVert == 2 || contHor==2 || contDiag==2 || contDiag2==2){
                jugar(contVert,contHor,contDiag,contDiag2,i);
                pasarTurno(turno);
                revision();
                return;
            }else if (contVert == 8 || contHor==8 || contDiag==8 || contDiag==8){
                jugar(contVert,contHor,contDiag,contDiag2,i);
                pasarTurno(turno);
                revision();
                return;
            }else if(contVert == 1 || contHor==1 || contDiag==1 || contDiag==1){
                jugar(contVert,contHor,contDiag,contDiag2,i);
                pasarTurno(turno);
                revision();
                return;
            }
        }
        if (sumatoria==4){
            Azar();
            pasarTurno(turno);
            } 
    }
    public void Azar() {
        boolean jugado = false;
        while (!jugado){
            int i= (int) (Math.random()*(3));
            int j = (int) (Math.random()*(3));
            jugado = aplicarCambio(i,j,1);
        }
    }

    
    private void finalizarJuego() {
        gato.jPanel1.removeAll();
       
    }
    private void iniciarJuego(){
        gato.jButton1.setVisible(false);
        gato.jTextField5.setText("Usuario");
        juego = new TresEnRaya[3][3];
        for (int i=0;i<3;i++){
            for (int j=0;j<3;j++){
                juego[i][j] = new TresEnRaya();
                juego[i][j].cuadro.setBounds((i*85)+10,(j*70)+10,85,70);
                juego[i][j].cuadro.addActionListener(this);
                gato.jPanel1.add(juego[i][j].cuadro);
                juego[i][j].valor = 0;
                X = new ImageIcon(this.getClass().getResource("/imagenes/X.gato.png"));
                X = new ImageIcon(X.getImage().getScaledInstance(80,65, java.awt.Image.SCALE_DEFAULT));
                O = new ImageIcon(this.getClass().getResource("/imagenes/Overde.png"));
                O = new ImageIcon(O.getImage().getScaledInstance(80,65, java.awt.Image.SCALE_DEFAULT));
            }
                
        }
        gato.jPanel1.repaint();
        
    }
    public boolean aplicarCambio(int i, int j, int valor1){
        if (juego[i][j].valor > 0){
            return false;
        }
        switch (valor1) {
            case 1:
                juego[i][j].cuadro.setIcon(X);
                juego[i][j].valor = valor1;
                juego[i][j].cuadro.removeActionListener(this);
                return true;
            case 4:
                juego[i][j].cuadro.setIcon(O);
                juego[i][j].valor = valor1;
                juego[i][j].cuadro.removeActionListener(this);
                return true;
            default:
                return false;
        }
    }
     private void pasarTurno(String quienJugo) {
        if ("Usuario".equals(quienJugo)){
            gato.jTextField5.setText("CPU");
            juegoCPU();
        }else if("CPU".equals(quienJugo)){
            gato.jTextField5.setText("Usuario");
        }
    }
    
    public String verificarGanador(){
        int sumatoria = 0;
        for (int i=0;i<3;i++){
            int contVert = 0;
            int contHor = 0;
            int contDiag = 0;
            int contDiag2 = 0;
            for (int j=0;j<3;j++){
                sumatoria = sumatoria + juego[i][j].valor;
                contVert = contVert + juego[i][j].valor;
                contHor = contHor + juego[j][i].valor;
                contDiag = contDiag + juego[j][j].valor;
                contDiag2 = contDiag2 + juego[j][2-j].valor;
            }
            if (contVert == 3 || contHor == 3){
                JOptionPane.showMessageDialog(gato, "Derrota", "Derrota", JOptionPane.INFORMATION_MESSAGE);
                return "CPU";
            }else if (contVert == 12 || contHor == 12){
                JOptionPane.showMessageDialog(gato, "Victoria!", "Victoria", JOptionPane.INFORMATION_MESSAGE);
                
                return "Usuario";
            }else if(contDiag == 12 || contDiag2 == 12){
                JOptionPane.showMessageDialog(gato, "Victoria!", "Victoria", JOptionPane.INFORMATION_MESSAGE);
                return "Usuario";
            }else if(contDiag == 3 || contDiag2 == 3){
                JOptionPane.showMessageDialog(gato, "Derrota", "Derrota", JOptionPane.INFORMATION_MESSAGE);
                return "CPU";
            }else if (sumatoria == 24){
                JOptionPane.showMessageDialog(gato, "Has empatado, ¿Nadie gana o nadie pierde?", "Empate", JOptionPane.INFORMATION_MESSAGE);
                return "nadie";
            }
        }
        return "";
    }
    
    public boolean revision(){
        String verificador = verificarGanador();
        if (!verificador.equals("")){
            this.removerListeners();
            switch (verificador) {
                case "Usuario":
                    {
                        int victorias = Integer.parseInt(gato.jTextField1.getText());
                        victorias++;
                        gato.jTextField1.setText(Integer.toString(victorias));
                        int dinero = Integer.parseInt(gato.jTextField4.getText());
                        dinero = dinero + 5000;
                        gato.jTextField4.setText(Integer.toString(dinero));
                        gato.jButton1.setVisible(true);

                        break;
                    }
                case "CPU":
                    {
                        int derrotas = Integer.parseInt(gato.jTextField2.getText());
                        derrotas++;
                        gato.jTextField2.setText(Integer.toString(derrotas));
                        int dinero = Integer.parseInt(gato.jTextField4.getText());
                        dinero = dinero - 3000;  
                        gato.jTextField4.setText(Integer.toString(dinero));
                        gato.jButton1.setVisible(true);
                        break;
                    }
                case "nadie":
                    {
                        int empates = Integer.parseInt(gato.jTextField3.getText());
                        empates++;
                        gato.jTextField3.setText(Integer.toString(empates));
                        int dinero = Integer.parseInt(gato.jTextField4.getText());
                        dinero = dinero + 1500;
                        gato.jTextField4.setText(Integer.toString(dinero));
                        gato.jButton1.setVisible(true);
                        break;
                    }
                default:
                    break;
            }
            return true;
        }
        return false;
    }
    private void removerListeners() {
        for (int i=0;i<3;i++){
            for (int j=0;j<3;j++){
                juego[i][j].cuadro.removeActionListener(this);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if  (e.getSource() == gato.jButton1){
            finalizarJuego();
            iniciarJuego();
        }else if(e.getSource() == gato.jButton2){
            int dinero = Integer.parseInt(gato.jTextField4.getText());
            try {
                this.agregarDinero(dinero, idJugador);
            } catch (SQLException ex) {
               JOptionPane.showMessageDialog(null,"Error realizando la solicitud del usuario","Error",
                                JOptionPane.ERROR_MESSAGE); 
            }gato.salir();
            

        //Por escribir algoritmo para añadir dinero
                 
        }else{
            mainLoop: for (int i=0;i<3;i++){
                for (int j=0;j<3;j++){
                    if (e.getSource() == juego[i][j].cuadro){
                        String turno = gato.jTextField5.getText();
                        juego[i][j].cuadro.removeActionListener(this);
                        if ("Usuario".equals(turno)){
                            aplicarCambio(i,j,4);
                        }
                        if (!revision()){
                            pasarTurno(turno);
                        }else{
                            removerListeners();
                        }

                    }
                }
            }
        }

     }

   

    public void mostrarVista() {
        gato.setLocationRelativeTo(null);
        gato.setVisible(true);
    }
        
    

    
    
}
