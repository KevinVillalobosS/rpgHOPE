/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//Se define el paquete de la clase
package Controladores;

//Se realizan las importaciones necesarias
import Vistas.MiniJuegosJDialog;
import Vistas.VistaLogInFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Kevin
 */
class ControladorBiblioteca implements ActionListener{
//Se definen los atributos de la clase
    MiniJuegosJDialog visbib;
    int idUser;
    
//Se define el constructor de la clase, que recibe como parámetro de entrada un objeto de la clase 
//VistaLogInFrame y la id del usuario
    ControladorBiblioteca(VistaLogInFrame vislog, int idUsr) {
        this.idUser = idUsr;
        this.visbib = new MiniJuegosJDialog(vislog,true,idUser);
        
        
    }

//Método que hace visible la vista biblioteca    
    void mostrarBiblio() {
        visbib.setLocationRelativeTo(null);
        visbib.setVisible(true);        
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
