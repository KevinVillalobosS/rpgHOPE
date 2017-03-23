/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import BD.Conexion;

import Controladores.ControladorRegistro;
import DAO.DAOLogIn;
import Modelos.ModeloLogIn;
import Modelos.ModeloUsuario;
import Modelos.Personaje;
import Vistas.VistaLogInFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Fernando
 */
public class ControladorLogIn implements ActionListener  {
    //declaro las variables y/o atributos que se utilizaran en esta clase
    ModeloLogIn logMod;
    VistaLogInFrame  vislog;
    ControladorRegistro conreg;
    Personaje modpj;
    ModeloUsuario modusuario;
    int idUser;
    int idPj;
    FileWriter archivo;
    PrintWriter escribir;
    java.util.Date fecha;
    SimpleDateFormat formato;
    
    public ControladorLogIn() throws IOException{
        vislog = new VistaLogInFrame();
        this.fecha = new java.util.Date();
        this.archivo = new FileWriter("src/rpgHope/prueba.txt",true);
        this.escribir = new PrintWriter(this.archivo);
        this.formato = new SimpleDateFormat("yyyy/MM/dd :: HH : mm : ss");
        this.modusuario = new ModeloUsuario();
 
        vislog.addListenersLgin(this);
    }

    public void mostrarLogin(){
      vislog.setLocationRelativeTo(null);
      vislog.setVisible(true);
    }
    

    
    //prueba desde github
    
    //Funcion encargada de verificar el login del jugador.
    //Recibe: Nick y password
    //Retorna: Booleano indicando si el login es correcto o erroneo.
    public boolean logeoJugador(String nombre, String password){
        //Se verifica que no haya ingresado un nick  y password vacios
        if (nombre.trim().equals("")){
            JOptionPane.showMessageDialog(vislog, "Ingrese un nick", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }else if(password.trim().equals("")){
            JOptionPane.showMessageDialog(vislog, "Ingrese una contraseña", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        //En caso de tener caracteres, se consulta en la BD la existencia del usuario, así como su password.
        }else{
            String[] consulta = (modusuario.consultar(nombre)).split(" ");
            String contrasniaBD =consulta[1];
            //Si el login funciona, se obtiene la ID de usuario que ingresó y su personaje
            //Se cambia la vista, y se muestra el personaje mencionado
            //Si el login es completado, se remueven los listeners    
            if (contrasniaBD.equals(password)){
                //Se extraen las IDS de USUARIO y PERSONAJE
                this.idUser = Integer.parseInt(consulta[3]);
                this.idPj = Integer.parseInt(consulta[2]);
                //Se quitan los listeners y se cambia la vista
                vislog.quitarListenersLgin(this);
                vislog.cambiarVista();
                //Se da comienzo a los controladores de vistaPrincipal
                
                vistaPrincipal();
                ModeloUsuario.idUs = this.idUser;
                ModeloUsuario.idPsj = this.idPj;
                //Se muestra por pantalla el personaje del usuario
                this.imprimirPjs(idUser, idPj);
                return true;
            }else {
                
                return false;
            }
        }
    }
    
    
 //----------------------------------------------------------------------------------------------------------//
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            this.fecha = new java.util.Date();
            //this.archivo = new FileWriter("src/rpgHope/prueba.txt",true);
            String evento = e.getActionCommand();
            //Si el usuario presiona el boton aceptar, se verifica el login
            //this.fecha = new java.util.Date();
            switch (evento){
                case "Aceptar":
                    String nick = vislog.userField.getText();
                    String password = vislog.passField.getText();
                    //Si falla el login, se informa y se resetan las cajas:
                    if(!(logeoJugador(nick,password))){
                        JOptionPane.showMessageDialog(vislog, "Login Fallido", "Error", JOptionPane.ERROR_MESSAGE);
                        vislog.vaciarCajas();
                        this.escribir.println(this.formato.format(fecha)+" = "+nick+" intenta logearse := Logeo incorrecto");
                        this.archivo.close();
                    }else{
                        this.escribir.println(this.formato.format(fecha)+" = "+nick+" intenta logearse := Logeo correcto");
                        this.archivo.close();
                        
                    }
                    break;
                case "Registrarse": //en caso que se presione el boton registrar, se abrira la vista del registro
                    
                    conreg = new ControladorRegistro();
                    conreg.mostrarVista();
                    this.escribir.println(this.formato.format(fecha)+" =  se Ingresa a vistaRegistro := correcto");
                    archivo.close();
                    break;
                case "EAO": //en caso que el usuario haya iniciado sesion y presione la EAO, se abrira dicha vista.
                    this.escribir.println(this.formato.format(fecha)+" =  se Ingresa a EAO := correcto");
                    archivo.close();
                    ControladorEAO conteao = new ControladorEAO(this.idUser, this.idPj);
                    conteao.lanzarVista();
                    //abrir vista EAO
                    break;
                case "ForoGriego": //en caso que el usuario haya iniciado sesion y presione el foro griego, se abrira dicha vista.
                    ControladorForoGriego conforo = new ControladorForoGriego(this.vislog,idUser);
                    this.escribir.println(this.formato.format(fecha)+" =  se Ingresa a Foro griego := correcto");
                    archivo.close();
                    conforo.mostrarVista();
                    break;
                    //ForoGriego
                case "Biblioteca": //en caso que el usuario haya iniciado sesion y presione la biblioteca, se abrira dicha vista.
                    ControladorBiblioteca conbib = new ControladorBiblioteca(this.vislog,idUser);
                    this.escribir.println(this.formato.format(fecha)+" =  se Ingresa a biblioteca central := correcto");
                    archivo.close();
                    conbib.mostrarBiblio();
                    break;
                    //Abrir Biblioteca
                case "Inventario": //en caso que el usuario haya iniciado sesion y presione su inventario, se abrira dicha vista-
                    ControladorInventario coninv = new ControladorInventario(this.vislog, this.idPj);
                    this.escribir.println(this.formato.format(fecha)+" =  se Ingresa a Inventario := correcto");
                    archivo.close();
                    coninv.mostrarInventario();
                    break;
                    //
                case "Batalla": //en caso que el usuario haya iniciado sesion y presione el boton para realizar una batalla, se abrira dicha vista.
                    SubControladorPreBatalla subConBat = new SubControladorPreBatalla(this.vislog,this.idUser, this.idPj);
                    this.escribir.println(this.formato.format(fecha)+" =  se Ingresa a Batalla := correcto");
                    archivo.close();
                    subConBat.mostrarTomaRamos();
                    break;
                case "Malla Curricular":
                    ControladorMallaCurricular conMalla = new ControladorMallaCurricular(this.vislog, this.idUser);
                    this.escribir.println(this.formato.format(fecha)+" =  se Ingresa a malla curricular := correcto");
                    archivo.close();
                    conMalla.mostrarMalla();
                    break;
                default:
                    //do nothing
            }
            
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null,"Error realizando la solicitud del usuario","Error",
                                JOptionPane.ERROR_MESSAGE); 
            
        }
    }

 //----------------------------------------------------------------------------------------------------------//
    //COMIENZO CONTROLADORES VISTA PRINCIPAL//
 //----------------------------------------------------------------------------------------------------------//
    // "Constructor" de la vistaPrincipal, añade los listeners de esta lista.
    public void vistaPrincipal(){
        vislog.addListenersVp(this);
        
    }
    
    
    
    //el metodo imprimirPjs obtiene el id del usuario para asi obtener su personaje y el dinero que le corresponde,
    //y asi poder mostrarlo por pantalla posteriormente cuando el usuario inicie sesion.
    public void imprimirPjs(int idUs, int idPsj) {
            modpj = new Personaje();
            try {
                String[] personaje = modpj.consultar(idPsj).split("CORTAR");
                String dinero = modusuario.obtenerDinero(idUs);
                this.vislog.imprimirPj(personaje, dinero);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null,"Error realizando la solicitud del usuario","Error",
                                JOptionPane.ERROR_MESSAGE); 
            }
    }
    
    
    
       
}
