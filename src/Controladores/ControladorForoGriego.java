/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//Se define el paquete al cual pertenece la clase
package Controladores;

//Se realizan las importaciones necesarias para el trabajo de esta clase, desde otros paquetes, así 
//como también utilidades de Java, elementos de Java Swing, de Java SQL y Java AWT
import Modelos.ModeloForoGriego;
import Modelos.ModeloUsuario;
import Modelos.Personaje;
import Vistas.VistaForoGriego;
import Vistas.VistaLogInFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Kevin
 */
//Se define la clase y la interfaz que implementa, en este caso, ActionListener
public class ControladorForoGriego implements ActionListener {
//Se definen los atributos de la clase    
    ModeloForoGriego[][] aliadosJdr;
    ModeloForoGriego[][] foroGriego;
    
    Personaje pjdao = new Personaje();
    ModeloUsuario modusuario;
    VistaForoGriego visforo;
    public int idJgd;
    public int alumnos, ayudantes, profesores, guerreros, magos, ninjas, arqueros;
    private FileWriter archivo;
    private final PrintWriter escribir;
    private final SimpleDateFormat formato;
    private java.util.Date fecha;
    
//Se define el contructor de la clase, el que recibirá como parámetros, l    
    public ControladorForoGriego(VistaLogInFrame vislog, int idUsr) throws IOException {
        this.archivo = new FileWriter("src/rpgHope/prueba.txt",true);
        this.escribir = new PrintWriter(this.archivo);
        this.formato = new SimpleDateFormat("yyyy/MM/dd :: HH : mm : ss");
        modusuario = new ModeloUsuario();
        this.idJgd = idUsr;
//Se crea un oobjeto de la clase VistaForo para trabajar como interfaz de usuario, se toma como parámetro
//la id del jugador
        visforo = new VistaForoGriego(vislog,true,idJgd);
//Se invoca el método que pondrá botones en el panel central de la vista del For Griego
        mostrarForo();
//Se invoca el método que le mostrará al usuario que personajes conforman su equipo de batalla
        extraerAliados();
//Se implementan en la vista los Listener para ejecutar las acciones que el usuario desee
        visforo.addListeners(this);
        
    }
//Método void que hace visible la vista del Foro Griego, no tiene parámetro de entrada, además se le da 
//ubicación de centro a esta vista emergente
    public void mostrarVista(){
        visforo.setLocationRelativeTo(null);
        visforo.setVisible(true);
    }

//Método que ejecuta la compra o contrato de personajes a partir del dinero que posea el jugador, sus 
//parámetros son el precio del personaje a contratar, la cantidad de batallas que éste compañará al usuario
//y la id del usuario, además se define la excepción que puede haber en la ejecución, SQLException    
    public void realizarCompra(int precio, int cantidadDeBatallas, int idAContratar, int idJgd) throws SQLException, IOException {
//Se crea un objeto de la clase ModeloUsuario para trabajar y para obtener el dinero que el jugador posee, 
//se invoca desde el objeto modusuario, el método obtenerDinero para lograr esto
          this.fecha = new java.util.Date();
        
        modusuario = new ModeloUsuario();
        int dinero = Integer.parseInt(modusuario.obtenerDinero(idJgd));
//Se realiza el descuento del dinero, al dinero que el usuario tenía, se le resta el precio dle personaje
//a contratar
        int nuevoDinero = dinero - precio;
//Con un if se condiciona que el usuario tenga dinero suficiente para realiar la compra, si esto  es cierto, 
//Se decuenta el dinero y se contrata al personaje deseado
        if (nuevoDinero > 0){
            Personaje personaje = new Personaje();
            String[] consulta = personaje.consultar(idAContratar).split("CORTAR");
            if (consulta[2].equals("Profesor")){
                personaje.modificarTraicion(idAContratar, 0);
            }
            modusuario.descontarDinero(idJgd, nuevoDinero);
            modusuario.contratarPj(idJgd, idAContratar, cantidadDeBatallas);
//Finalmente por medio de un JOptionPane se informa que el contrato fue realizado exitosamente
            JOptionPane.showMessageDialog(null, "Contrato realizado correctamente", "Exito", JOptionPane.INFORMATION_MESSAGE);
            this.escribir.println(this.formato.format(fecha)+" = se intenta contratar un personaje:= completado");
            archivo.close();
//De manera alternativa se le informa que el contrato no se realizó porque el usuario no tiene dinero suficiente
        }else{
            this.escribir.println(this.formato.format(fecha)+" = se intenta contratar un personaje:= incorrecto");
            archivo.close();
            JOptionPane.showMessageDialog(null, "No tines suficiente dinero para este contrato", "Contrato Fallido", JOptionPane.ERROR_MESSAGE);
        }
        
    }

//Método que a partir de la id del jugador, almacena en un ArrayList, los aliados que este posee
 /**
  * 
  * @param idJgd
  * @return 
  */
    public ArrayList obtenerListaAliados(int idJgd) {
//Se instancia un objeto de la clase ModeloUsuario para trabajar
        modusuario = new ModeloUsuario();
//Con un try-catch se obtienen los aliados del jugador en un ArrayList de Integers, los que representan la 
//id de cada prsonaje aliado
        try {
            ArrayList<Integer> aliados = modusuario.obtenerAliados(idJgd);
            
//De funcionar el try se retorna el ArrayList previamente definido            
            return aliados;
//Se define la excepción SQL relacionada al método         
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Error realizando la solicitud del usuario","Error",
                                JOptionPane.ERROR_MESSAGE); 
        }
        return null;
    }

//Método que muestra el Foro Griego como tal, con los botones que tiene los íconos de los personajes
    private void mostrarForo() {
//Se definen contadores de roles y tipos existentes para contabilizar la cantidad de personajes que hay en 
//un momento dado en el Foro y las características de estos
        alumnos = 0; ayudantes = 0; profesores = 0; guerreros = 0; magos = 0; ninjas = 0; arqueros= 0;
//Se remueve todo lo que se tiene en la jLabel12, que es donde se insertan los botones        
        visforo.jLabel2.removeAll();
//Se define una matriz de 10 por 10 que es dónde se colocarán los botones con la cara de los personajes
        foroGriego = new ModeloForoGriego[10][10];
//Con un doble for in entre 0 y 10 se insertan los personajes y se les asigna un ícono característico        
        for (int i=0;i<10;i++){
            for (int j=0;j<10;j++){
                foroGriego[i][j] = new ModeloForoGriego();
                //Se generan ids aleatorias por cada boton
                int id = (int) (Math.random()*(461-1)+1);
                String[] consulta = pjdao.consultar(id).split("CORTAR");
                visforo.aniadirAContadores(consulta);
                //Tratamiento del icono del jugador
                ImageIcon jugador = new ImageIcon(getClass().getResource(consulta[10]));
                jugador = new ImageIcon(jugador.getImage().getScaledInstance(60,46, java.awt.Image.SCALE_DEFAULT));
                //Se configura el boton y se añade el icono
                foroGriego[i][j].lugar.setBounds((i*60)+10,(j*46)+10,60,46);
                foroGriego[i][j].lugar.setIcon(jugador);
                //Se agrega el listener para mostrar el jugador solicitado
                foroGriego[i][j].lugar.addActionListener(this);
                //Se le agrega la id para tenerla almacenada
                foroGriego[i][j].id_lugar = id;
                //Finalmente, se añade al foro
                visforo.jLabel2.add(foroGriego[i][j].lugar);
            }                 
	}visforo.jLabel2.repaint();
    }

//Método que mediante el uso de la utilidad Random pone un precio y la cantidad de batallas que tendrá cada 
//personaje    
    private void ponerPrecio(String rol, String tipo) {
//En este punto se define la cantidad de batallas que varían entre 3 y 5        
        String precio;
        int batallas = (int) (Math.random()*(6-3)+3);
//Para los casos particulares del rol Ninja y el tipo Profesor, las batallas tendrán un valor de 10000
        if (rol.equals("Ninja") || tipo.equals("Profesor")){
            precio = Integer.toString(batallas*10000);
//Para cualquier otro tipo o rol, el precio será estándar de 70000
        }else{
            precio = Integer.toString(batallas*7000);
        }
//Luego se invoca el objeto creado de la clase VistaForo y el método poner precio para fijar en las respectivas 
//jLabels de la vista el precio y las batallas del personaje
        visforo.ponerPrecio(Integer.toString(batallas),precio);
    }
    
    private void extraerAliados() {
        int contador = 0;
        visforo.jLabel23.removeAll();
        ArrayList<Integer> aliados = obtenerListaAliados(idJgd);
        if (aliados.size()>0){
            aliadosJdr = new ModeloForoGriego[10][4];
            mainLoop:for (int i=0;i<10;i++){
                    for (int j=0;j<4;j++){
                        String[] consulta = pjdao.consultar(aliados.get(contador)).split("CORTAR");
                        aliadosJdr[j][i] = new ModeloForoGriego();
                        ImageIcon aliado = new ImageIcon(getClass().getResource(consulta[10]));
                        aliado = new ImageIcon(aliado.getImage().getScaledInstance(40,30, java.awt.Image.SCALE_DEFAULT));
                        aliadosJdr[j][i].lugar.setIcon(aliado);
                        aliadosJdr[j][i].lugar.setBounds((j*37)+3,(i*33)+10,37,33);
                        aliadosJdr[j][i].id_lugar = aliados.get(contador);
                        visforo.jLabel23.add(aliadosJdr[j][i].lugar);
                        contador = contador + 1;
                        if (contador == aliados.size()){
                            break mainLoop;
                        }
                  }                 
            }
        }visforo.jLabel23.repaint();
    }

//Método que le muestra al usuario en la vista del Foro Griego las estadísticas del jugador a partir del 
//objeto pjdao de la clase personajesDAO y además en la jLabel 37 inserta la imagen del personaje que el
//usuario ha seleccionado para ver
    public void mostrarSolicitud(int idPj){
//Se hace la consulta por el personaje        
        String[] pj = pjdao.consultar(idPj).split("CORTAR");
//Se muestran los datos y la imagen        
        visforo.mostrarSolicitud(pj);
//Se asigna el precio y las batallas        
        ponerPrecio(pj[3], pj[2]);
    }
    
    @Override
//Se sobrescriben los métodos para las acciones realizadas en los botones de la vista    
    public void actionPerformed(ActionEvent e) {
        try {
            //Al seleccionar el jButon13 se cambian los personajes mostrados en el foro
            this.archivo = new FileWriter("src/rpgHope/prueba.txt",true);
            
            if (e.getSource() == visforo.jButton13){
                mostrarForo();
//Si se presiona el jButton2 se procedará con la verificación y compra o rechazo de un personaje
            }else if (e.getSource()==visforo.jButton15){
                this.visforo.setVisible(false);
            }else if(e.getSource() == visforo.jButton2){
            //Se obtienen los valores por medio de los getText()
            int idAContratar = Integer.parseInt(visforo.jLabel5.getText());
            int precio = Integer.parseInt(visforo.jTextField26.getText());
            int cantidadDeBatallas = Integer.parseInt(visforo.jTextField25.getText());
            //Luego se intenta realizar la compra con los parámetros id del jugador, la id del personaje a contratar,
            //su precio , la cantidad de batallas que ofree al usuario
            realizarCompra(precio,cantidadDeBatallas,idAContratar,idJgd);
            extraerAliados();
            //Se define la excepción SQL para éste método

//Por último, si se presiona cualquier botón con el ícono de un personaje se mostrarán sus estadísticas y su
//ícono en el panel derecho
            }else{
                for (int i=0;i<10;i++){
                    for (int j=0;j<10;j++){
                        if (e.getSource() == foroGriego[i][j].lugar){
                            mostrarSolicitud(foroGriego[i][j].id_lugar);
                        }
                    }
                }
                
                
                
            }
        } catch (IOException | SQLException ex) {
            JOptionPane.showMessageDialog(null,"Error realizando la solicitud del usuario","Error",
                                JOptionPane.ERROR_MESSAGE); 
        }
    }
    
}
