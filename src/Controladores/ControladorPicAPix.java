/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Modelos.ModeloPicAPix;
import Vistas.VistaPicAPix;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 *
 * @author Kevin
 */
public class ControladorPicAPix implements ActionListener{
    
    ModeloPicAPix[][] tablero;
    JButton[][] instru, instru1;
    VistaPicAPix vispic;
    int contVict;
    int contVert;
    public int[] x = {0,0,0,0,1,1,1,2,3,4,5,6,7,8,9};
    public int[] y = {3,4,7,9,0,1,6,7,6,5,4,3,2,1,0};
    
    public ControladorPicAPix(){
        this.vispic = new VistaPicAPix(null,true);
        inciar();
        
        
        
    }
    public void mostrarVista(){
        this.vispic.setLocationRelativeTo(null);
        this.vispic.setVisible(true);
    }
    
    public void inciar(){
        tablero = new ModeloPicAPix[10][10];
        int cont = 0;
        for (int i=0;i<10;i++){
            for (int j=0;j<10;j++){
                tablero[i][j] = new ModeloPicAPix();
                tablero[i][j].cuadro.setBounds((i*40)+4, (j*25)+3, 40, 25);
                tablero[i][j].cuadro.addActionListener(this);
                tablero[i][j].cuadro.setBackground(Color.white);
                if (i==x[cont] && j==y[cont]){
                   tablero[i][j].esObjetivo = true;
                   cont = (cont==14)? 0:cont+1;
                }
                vispic.jPanel1.add(tablero[i][j].cuadro);
                
            }
        }vispic.jPanel1.repaint();
        contVict = 0;
        crearBloques();
        darInstrucciones();
    }


        
    
    public void crearBloques(){
        instru = new JButton[10][3];
        for (int i=0;i<10;i++){
            for (int j=0;j<3;j++){
                instru[i][j] = new JButton();
                instru[i][j].setBounds((i*41)+3, (j*30)+3, 41, 30);
                instru[i][j].setBackground(Color.white);
                vispic.jPanel3.add(instru[i][j]);
             }
        }
        instru1 = new JButton[3][10];
        for (int i=0;i<3;i++){
            for (int j=0;j<10;j++){
                instru1[i][j] = new JButton();
                instru1[i][j].setBounds((i*41)+3, (j*25)+3, 41,25);
                instru1[i][j].setBackground(Color.white);
                vispic.jPanel2.add(instru1[i][j]);
             }
        }
    }
    
    
    private void darInstrucciones() {
        int contVert1 = 0;
        int contVert2 = 0;
        for (int i=0;i<10;i++){
            contVert =0;
            for (int j=0;j<10;j++){
                if (tablero[i][j].esObjetivo){
                    while (tablero[i][j].esObjetivo){
                        contVert = contVert+1;
                        if (j==9){break;}else{j++;}
                    }
                    
                    if (j<9){
                        instru[i][contVert1].setText(Integer.toString(contVert));
                        contVert1 = (contVert1==2) ? 0:contVert1+1;
                    }else{
                                
                        instru[i][contVert1].setText(Integer.toString(contVert));
                    }
                    contVert = 0;
                
                }
                
            }contVert1=0;
            
        }
        //
        for (int i=0;i<10;i++){
            contVert =0;
            for (int j=0;j<10;j++){
                if (tablero[j][i].esObjetivo){
                    while (tablero[j][i].esObjetivo){
                        contVert = contVert+1;
                        if (j==9){break;}else{j++;}
                    } if (j<9){
                        instru1[contVert2][i].setText(Integer.toString(contVert));
                        contVert2 = (contVert2==2) ? 0:contVert2+1;
                    }else{
                        instru1[contVert2][i].setText(Integer.toString(contVert));
                    
                    }contVert = 0;
                    
                }
            }contVert2=0; 

        }
    }

    
    
    
    private void revisionVictoria(int i, int j) {
        if (tablero[i][j].esObjetivo){
            contVict = contVict +1;
        }
        if (contVict == (15)){
            JOptionPane.showMessageDialog(null, "Victoria", "Leyeenda", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    
    
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vispic.botonSalir){
            vispic.setVisible(false);
        }else
            {for (int i=0;i<10;i++){
              for (int j=0;j<10;j++){
                 if (e.getSource() == tablero[i][j].cuadro){
                    Color color = tablero[i][j].cuadro.getBackground();
                    if (color.equals(Color.white)){
                        tablero[i][j].cuadro.setBackground(Color.black);
                    }else{
                        tablero[i][j].cuadro.setBackground(Color.white);
                    }//end if
                    revisionVictoria(i,j);
                }//end main if
            }//end for
        }//end main for
    }
    }
}


