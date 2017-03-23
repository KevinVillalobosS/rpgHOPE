/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vistas;

import Modelos.ModeloPicAPix;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 *
 * @author Kevin
 */
public class VistaPicAPix extends javax.swing.JDialog implements ActionListener {
    ModeloPicAPix[][] tablero;
    JButton[][] instru, instru1;
    int contVict;
    int contVert;
    public int[] x = {0,0,0,0,1,1,1,2,3,4,5,6,7,8,9};
    public int[] y = {3,4,7,9,0,1,6,7,6,5,4,3,2,1,0};
   

    /**
     * Creates new form VistaPicAPix
     * @param parent
     * @param modal
     */
    public VistaPicAPix(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        tablero = new ModeloPicAPix[10][10];
        
        int cont = 0;
        for (int i=0;i<10;i++){
            for (int j=0;j<10;j++){
                tablero[i][j] = new ModeloPicAPix();
                tablero[i][j].cuadro.setBounds((i*41)+4, (j*26)+3, 41, 26);
                tablero[i][j].cuadro.addActionListener(this);
                tablero[i][j].cuadro.setBackground(Color.white);
                if (i==x[cont] && j==y[cont]){
                   tablero[i][j].esObjetivo = true;
                   cont = (cont==14)? 0:cont+1;
                   tablero[i][j].cuadro.setBackground(Color.yellow);
                }
                jPanel1.add(tablero[i][j].cuadro);
                
            }
        }jPanel1.repaint();
        contVict = 0;
        crearBloques();
        darInstrucciones();
        
    }
    public void crearBloques(){
        instru = new JButton[10][3];
        for (int i=0;i<10;i++){
            for (int j=0;j<3;j++){
                instru[i][j] = new JButton();
                instru[i][j].setBounds((i*41)+3, (j*25)+3, 41, 25);
                instru[i][j].setBackground(Color.white);
                jPanel3.add(instru[i][j]);
             }
        }
        instru1 = new JButton[3][10];
        for (int i=0;i<3;i++){
            for (int j=0;j<10;j++){
                instru1[i][j] = new JButton();
                instru1[i][j].setBounds((i*41)+3, (j*26)+3, 41, 26);
                instru1[i][j].setBackground(Color.white);
                jPanel2.add(instru1[i][j]);
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
            JOptionPane.showMessageDialog(this, "Victoria", "Leyeenda", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    
    
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i=0;i<10;i++){
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
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jLabelTitulo = new javax.swing.JLabel();
        botonSalir = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Pic A Pix");
        setMaximumSize(new java.awt.Dimension(1024, 700));
        setMinimumSize(new java.awt.Dimension(1024, 700));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 416, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 256, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 310, 420, 260));

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 126, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 256, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 310, 130, 260));

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 416, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 76, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 220, 420, 80));

        jButton1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagenesFinales/FondoAzul.png"))); // NOI18N
        jButton1.setText("Volver A Jugar");
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 590, 200, 30));

        jLabelTitulo.setBackground(new java.awt.Color(0, 0, 0));
        jLabelTitulo.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        jLabelTitulo.setForeground(new java.awt.Color(255, 255, 255));
        jLabelTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTitulo.setText("Pic A Pix");
        jLabelTitulo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jLabelTitulo.setOpaque(true);
        getContentPane().add(jLabelTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 80, 370, 50));

        botonSalir.setBackground(new java.awt.Color(255, 0, 0));
        botonSalir.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        botonSalir.setForeground(new java.awt.Color(255, 255, 255));
        botonSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/FondoRojo.png"))); // NOI18N
        botonSalir.setText("Salir");
        botonSalir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonSalirActionPerformed(evt);
            }
        });
        getContentPane().add(botonSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 610, 100, 30));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes2/Pic2.jpg"))); // NOI18N
        jLabel1.setText("jLabel1");
        jLabel1.setMaximumSize(new java.awt.Dimension(1024, 700));
        jLabel1.setMinimumSize(new java.awt.Dimension(1024, 700));
        jLabel1.setOpaque(true);
        jLabel1.setPreferredSize(new java.awt.Dimension(1024, 700));
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1024, 700));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonSalirActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_botonSalirActionPerformed

    /**
     * @param args the command line arguments
     */
   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton botonSalir;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabelTitulo;
    public javax.swing.JPanel jPanel1;
    public javax.swing.JPanel jPanel2;
    public javax.swing.JPanel jPanel3;
    // End of variables declaration//GEN-END:variables

    
    

    
}
