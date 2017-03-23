/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import BD.Conexion;
import DAO.DAOMallaCurricular;
import Vistas.VistaMallaCurricular;
import Modelos.ModeloMallaCurricular;
import Modelos.ModeloUsuario;
import Vistas.VistaLogInFrame;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 *
 * @author Fernando
 */

public class ControladorMallaCurricular implements MouseListener{
    public int idJugador;
    public int contador; 
    public VistaMallaCurricular visMalla;
    public ModeloMallaCurricular[][] malla;
//   public ModeloMallaCurricular[][] mallaCi;
    public Conexion conexion;
    public ModeloMallaCurricular modMalla;
    public DAOMallaCurricular DAOMalla;
    public int idCarrera; 
    
    
    public ControladorMallaCurricular(VistaLogInFrame vislog,int idUs){
        this.idJugador =idUs;
        conexion = new Conexion();
        this.malla = new ModeloMallaCurricular[12][7]; 
//        this.mallaCi = new ModeloMallaCurricular[8][7]; 
        modMalla = new ModeloMallaCurricular();
        visMalla = new VistaMallaCurricular(vislog,true,idCarrera);
        this.visMalla.addListeners(this);
        //this.mostrarMallaEjecucion(1);
        //this.mostrarMallaCivil(2);
        this.imprimirMallaCurricular();
        this.mostrarAvance();
    }
    
    private void imprimirMallaCurricular(){
        ModeloUsuario modus = new ModeloUsuario();
        String[] consultaUsuario = modus.consultar(idJugador).split("CORTAR");
        int id_Carrera = Integer.parseInt(consultaUsuario[4]);
        String nombreCarrera = null;
        switch (id_Carrera){
            case 1:
                nombreCarrera = "ASIGNATURAS_EJECUCION";
                break;
            case 2:
                nombreCarrera = "ASIGNATURAS_CIVIL";
                break;
        }
        this.mostrarMallaCurricular(nombreCarrera);
        
    }
    
    private void mostrarAvance(){
        ArrayList<Integer> ramosAprobados = modMalla.obtenerAvance(idJugador);
        if (!(ramosAprobados.isEmpty())){
            for (int ramoAprob:ramosAprobados){
                 mainLoop:for (int i=0;i<8;i++){
                   for (int j =0;j<8;j++){
                        if (this.malla[i][j].idRamo == ramoAprob){
                            this.malla[i][j].ramo.setEnabled(false);
                            break mainLoop;
                        }
                    } 
                }
            }
        }
        

    }

    public void mostrarMallaCurricular(String nombreCarrera){
        this.contador = 0;
        //try    
            for (int i=0;i<12;i++){
                this.contador++;
                ArrayList<Integer> ramos = modMalla.obtenerNivel(nombreCarrera,this.contador);
                for (int j=0;j<7;j++){
                    if (j >= ramos.size()){
                        this.malla[i][j]= new ModeloMallaCurricular();
                        this.malla[i][j].ramo.setBounds(i*120+10, j*80+10, 120, 80);
                        this.malla[i][j].ramo.setVisible(false);
                    }else{
                        this.malla[i][j]= new ModeloMallaCurricular();
                        this.malla[i][j].ramo.setBounds(i*120+10, j*80+10, 120, 80);
                        this.malla[i][j].ramo.addMouseListener(this);
                        this.malla[i][j].idRamo= ramos.get(j);
                        this.malla[i][j].ramo.setText(this.modMalla.StringConsultarRamo(nombreCarrera, ramos.get(j)));
                        this.malla[i][j].ramo.setToolTipText(this.modMalla.StringConsultarRamo(nombreCarrera, ramos.get(j)));
                        this.visMalla.jLabel2.add(this.malla[i][j].ramo);
  
                    }

               } 

            }
   }
    
        public void mostrarMalla(){
        visMalla.setLocationRelativeTo(null);
        visMalla.setVisible(true);
        }
    
    



    @Override
    public void mouseClicked(MouseEvent me) {
   //     throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent me) {
   //     throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent me) {
   //     throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        if (me.getSource() == visMalla.botonSalir){
       //     this.visMalla.dispose();
        /*}else{
            this.contador = 1;      
                for (int i=0;i<8;i++){
                    ArrayList<Integer> ramos = modMalla.obtenerNivel("ASIGNATURAS_EJECUCION",this.contador);  
                    for (int j=0;j<7;j++){
                        if (me.getSource() == malla[i][j].ramo){
                            System.out.println((this.modMalla.StringConsultarRamo(carrera, ramos.get(j))));
                            System.out.println(this.malla[i][j].idRamo);
                        }
                    }
                    contador++;}
        */
        }
   
    }

    @Override
    public void mouseExited(MouseEvent me) {
  //      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
