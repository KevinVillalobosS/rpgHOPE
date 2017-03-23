/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import BD.Conexion;
import DAO.DAORegistro;
import DAO.personajesDAO;
import Modelos.ModeloForoGriego;
import Modelos.ModeloRegistro;
import Modelos.ModeloUsuario;
import Modelos.Personaje;
import Vistas.VistaRegistro;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author Fernando */

public class ControladorRegistro implements ActionListener{
    //declaramos las variables para cada clase que se utilizará en el ControladorRegistro
    private VistaRegistro visreg;
    public Conexion conexion;
    public DAORegistro dregis;
    public personajesDAO DAOpj;
    public Personaje personaje;
    public ModeloRegistro registro;
    
    public ControladorRegistro(){
        //se instancia el constructor de cada clase definida anteriormente
        conexion = new Conexion();
        dregis = new DAORegistro();
        registro = new ModeloRegistro();
        DAOpj = new personajesDAO();
        personaje = new Personaje();
        visreg = new VistaRegistro(new javax.swing.JFrame(), true, this, personaje);
        this.visreg.addListener(this);
    }
    
    public void mostrarVista(){ //método para hacer visible la ventana del registro
        visreg.setLocationRelativeTo(null);
        visreg.setVisible(true);
    }
    
    //el metodo obtenerDatos va a tomar los datos que ingresa el usuario en la vista registro y verifica los distintos if
    //que posee el metodo, en caso que no exista un error, se procede a registrar el usuario en la base de datos.
    public boolean obtenerDatos (String nombre, int id_rol, String pass, String pass2, int id_carrera){
        
        if(nombre.trim().equals("")){
            JOptionPane.showMessageDialog(visreg, "Ingrese un nick.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }else if(nombre.contains(" ")){
            JOptionPane.showMessageDialog(visreg, "El nombre de usuario no puede contener espacios.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }else if(pass.trim().equals("")){
            JOptionPane.showMessageDialog(visreg, "La contraseña no puede quedar en blanco.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }else if(pass.contains(" ")){
            JOptionPane.showMessageDialog(visreg, "La contraseña no puede contener espacios.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }else if (!pass.equals(pass2)){
            JOptionPane.showMessageDialog(visreg, "Las contraseñas indicadas no coinciden.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }else{
            boolean seHizo = conexion.ejecutar(dregis.agregarUsuario(nombre, pass, id_rol, id_carrera)); // si es true, se guarda en la BD, si es false, arroja un error.
            if (seHizo){
                JOptionPane.showMessageDialog(visreg, "Registro completado", "Exito", JOptionPane.INFORMATION_MESSAGE);
            }
            return seHizo;
        }
    }
    
    //el metodo insertarPersonaje llama al DAORegistro y posteriormente almacena en la BD el personaje principal y su
    //mejor amigo, tomando en cuenta tambien el id del jugador que se ha registrado.
    public boolean insertarPersonaje(int id_pj, int id_mjAmg, int id_jgd){
        boolean completado = conexion.ejecutar(dregis.insertarPersonajes(id_pj, id_mjAmg, id_jgd));
        ModeloUsuario modus = new ModeloUsuario();
        modus.contratarPj(id_jgd, id_pj,0);
        JOptionPane.showMessageDialog(visreg, "Personaje añadido correctamente", "Exito", JOptionPane.INFORMATION_MESSAGE);
        return completado;
    }
    
    
    //el metodo buscarPersonaje busca el personaje que ha aparecido al usuario como random. Cada vez que el usuario
    //solicite otro personaje, actuará este metodo para mostrar un personaje que sea al azar.
    public String buscarPersonaje(String tipo) {
        boolean listo = false;
        String elementos = null;
        while (!listo){
            int id = (int) (Math.random()*(461-1)+1);
            elementos =  personaje.consultar(id); 
            if (elementos.split("CORTAR")[3].equals(tipo)){
                listo = true;
            }else if(tipo.equals("none")){
                listo=true;
            }
//los elementos corresponden a las distintas caracteristicas
//correspondientes al personaje ligado a su id en la BD
        }
        return elementos;
    }
    
    /*public String buscarPersonaje(int id){
        String elementos =  personaje.consultar(id);
        return elementos;
    }*/

    
    //el metodo obtenerUltimaID obtiene el id del ultimo usuario registrado en la BD, para luego poder utilizarla,
    //o bien no, en otro metodo que requiere esta id.
    public int obtenerUltimaID() {
        ModeloUsuario registro = new ModeloUsuario();
        int idC = Integer.parseInt(registro.obtenerUltimoRegistro());
        return idC;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        

    }

  

    
    
}
