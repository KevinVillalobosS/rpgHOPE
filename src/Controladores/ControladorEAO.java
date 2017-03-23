/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Vistas.VistaEAO;
import Modelos.ModeloEAO;
import Modelos.ModeloUsuario;
import Modelos.Personaje;
import Vistas.VistaLogInFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Kevin
 */
public class ControladorEAO implements ActionListener {
    public ModeloEAO[][] tienda =  new ModeloEAO[20][6];
    public ModeloEAO[][] inventarioc = new ModeloEAO[5][10];

    public Personaje modpj;
    public VistaLogInFrame vp = new VistaLogInFrame();
    private final VistaEAO viseao;
    private final ModeloEAO modeao;
    private final ModeloUsuario modus;
    public int idUser;
    public int idPj;
    private java.util.Date fecha;
    private  FileWriter archivo;
    private final  PrintWriter escribir;
    private final  SimpleDateFormat formato;
   
    public ControladorEAO(int idUsr,int idPsj) throws IOException{
        this.archivo = new FileWriter("src/rpgHope/prueba.txt",true);
        this.escribir = new PrintWriter(this.archivo);
        this.formato = new SimpleDateFormat("yyyy/MM/dd :: HH : mm : ss");
        this.viseao = new VistaEAO(vp,true);
        this.modeao = new ModeloEAO();
        this.modus = new ModeloUsuario();
        this.modpj = new Personaje();
        this.idUser = idUsr;
        this.idPj = idPsj;
        viseao.agregarListeners(this);
        mostrarInventarioJgdr();
    }
    
    
    
    //Por implementar
    public void mostrarInventarioJgdr(){
        viseao.jLabel3.removeAll();
	ArrayList<Integer> idsItem = modpj.consultarInventario(idPj);
        int contadorItems = 0;
        for (int i=0;i<5;i++){
            for (int j=0;j<10;j++){
                this.inventarioc[i][j] = new ModeloEAO(0);
                this.inventarioc[i][j].icon_itm.setBounds((i*52)+10,(j*52)+40,52,52);
                if (contadorItems<idsItem.size()){
                    try {
                        String[] item = modeao.buscarItemEspecifico(idsItem.get(contadorItems)).split("Cortar");
                        ImageIcon imgItm = new ImageIcon(getClass().getResource(item[1]));
                        imgItm = new ImageIcon(imgItm.getImage().getScaledInstance(52, 52, java.awt.Image.SCALE_DEFAULT));
                        this.inventarioc[i][j].icon_itm.setIcon(imgItm);
                        this.inventarioc[i][j].id_obj = idsItem.get(contadorItems);

                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null,"Error realizando la solicitud del usuario","Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                    
                contadorItems = contadorItems + 1;
                }viseao.jLabel3.add(this.inventarioc[i][j].icon_itm);             
                
            }
            
        }viseao.jLabel3.repaint();
    }

    public void mostrarEAO(String tipo){
        try {
            ArrayList<Integer> ids = modeao.buscarItems(tipo);
            if (ids.isEmpty()){
                JOptionPane.showMessageDialog(viseao, "Aun no tenemos articulos en venta","Error",JOptionPane.ERROR_MESSAGE);
                return;
            }
            viseao.jPanel2.removeAll();
            int contador = 0;
            mainLoop:for (int i=0;i<20;i++){
                for (int j=0;j<6;j++){
                    String [] datos = modeao.buscarItemEspecifico(ids.get(contador)).split("Cortar");
                    ImageIcon imgItm = new ImageIcon(getClass().getResource(datos[1]));
                    imgItm = new ImageIcon(imgItm.getImage().getScaledInstance(55, 55, java.awt.Image.SCALE_DEFAULT));
                    tienda[j][i] = new ModeloEAO(0);
                    tienda[j][i].icon_itm.setBounds((j*55)+15, (i*55)+15, 55, 55);
                    tienda[j][i].id_obj = ids.get(contador);
                    tienda[j][i].icon_itm.setIcon(imgItm);
                    tienda[j][i].icon_itm.addActionListener(this);
                    viseao.jPanel2.add(tienda[j][i].icon_itm);
                    contador = contador + 1;
                    if (contador==ids.size()){
                        break mainLoop;
                    }   
                }
            
            }viseao.jPanel2.repaint();
            
            
        } catch (SQLException | java.lang.NullPointerException ex) {
            Logger.getLogger(ControladorEAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(Arrays.toString(ex.getStackTrace()));
            JOptionPane.showMessageDialog(null,"Error realizando la solicitud del usuario","Error",
                                JOptionPane.ERROR_MESSAGE); 
        }
        
    }
    
    public void comprarItem(int id) {
        
    }
    
    public void lanzarVista(){
        viseao.setLocationRelativeTo(null);
        viseao.setVisible(true);
    }

    private void mostrarSolicitud(int id_obj) throws SQLException {
       String[] datos = modeao.buscarItemEspecifico(id_obj).split("Cortar");
       viseao.mostrarItemSolicitado(datos, id_obj);
       
       
        
        
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            //this.archivo = new FileWriter("src/rpgHope/prueba.txt",true);
            this.fecha = new java.util.Date();
            if (e.getSource() == viseao.jButton1){
                String tipo = viseao.jComboBox2.getSelectedItem().toString();
                this.mostrarEAO(tipo);
            }else if (e.getSource() == viseao.jButton2){
                try {
                    int precioItm = Integer.parseInt(viseao.jLabel7.getText());
                    int dinero = Integer.parseInt(modus.obtenerDinero(ModeloUsuario.idUs));
                    if (dinero-precioItm>0){
                        modus.descontarDinero(idUser,dinero-precioItm);
                        if(modeao.comprarItem(this.idPj,Integer.parseInt(viseao.jLabel2.getText()))){
                            JOptionPane.showMessageDialog(viseao, "Compra realizada", "Exito", JOptionPane.INFORMATION_MESSAGE);
                            this.escribir.println(this.formato.format(fecha)+" = se intenta comprar un objeto := completado");
                            archivo.close();
                        }
                    }else{
                        JOptionPane.showMessageDialog(viseao, "Dinero insuficiente", "Error", JOptionPane.ERROR_MESSAGE);
                        this.escribir.println(this.formato.format(fecha)+" = se intenta comprar un objeto := incorrecto");
                        archivo.close();
                    }
                } catch (SQLException ex) {
                    System.out.println("A");
                        JOptionPane.showMessageDialog(null,"Error realizando la solicitud del usuario","Error",
                                JOptionPane.ERROR_MESSAGE);               }
                this.mostrarInventarioJgdr();
                
                
            }else if(e.getSource() == viseao.jButton15){
                //Boton salir
                
                
                
            }else{
                mainLoop:for (int i=0;i<20;i++){
                    for (int j=0;j<6;j++){
                        if (e.getSource() == tienda[j][i].icon_itm){
                            try {
                                mostrarSolicitud(tienda[j][i].id_obj);
                            } catch (SQLException ex) {
                                JOptionPane.showMessageDialog(null,"Error realizando la solicitud del usuario","Error",
                                JOptionPane.ERROR_MESSAGE);
                            }
                            break mainLoop;
                        }
                    }
                }
            }
        } catch (IOException ex) {
            //do nothing
            JOptionPane.showMessageDialog(null,"Error con el archivo de escritura","Error",
                                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    
}
