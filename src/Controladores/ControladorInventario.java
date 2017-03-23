
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import DAO.personajesDAO;
import Modelos.ModeloItem;
import Modelos.ModeloEAO;
import Modelos.ModeloInventario;
import Modelos.ModeloUsuario;
import Modelos.Personaje;
import Vistas.VistaInventario;
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
class ControladorInventario implements ActionListener {
    //declaro las variables a utilizar en la clase
    ModeloInventario[][] inventario;
    ModeloInventario[][] equipamiento;
    Personaje personaje;
    ModeloEAO modeao;
    ModeloItem modItem;
    VistaInventario visinv;
    int idPj;
    private final FileWriter archivo;
    private final PrintWriter escribir;
    private final SimpleDateFormat formato;
    private java.util.Date fecha;
    

    //en este constructor se asignan valores a distintas variables y se instancian algunas clases a utilizar.
    public ControladorInventario(VistaLogInFrame vislog, int Id) throws IOException{
        this.visinv = new VistaInventario(vislog, true);
        this.idPj = Id;
        inventario = new ModeloInventario[5][10];
        this.archivo = new FileWriter("src/rpgHope/prueba.txt",true);
        this.escribir = new PrintWriter(this.archivo);
        this.formato = new SimpleDateFormat("yyyy/MM/dd :: HH : mm : ss");
        equipamiento = new ModeloInventario[2][5];
        personaje = new Personaje();
        modeao = new ModeloEAO();
        visinv.addListeners(this);
        imprimirInv(idPj);
        mostrarEquipo();
        mostrarDatosPj();
        agregarAliados();
    }

    
    
    private void afectarBD(String tipo, int factor, int boost){
        String[] pj = personaje.consultar(idPj).split("CORTAR");
        int ataque = Integer.parseInt(pj[6]);
        int defensa = Integer.parseInt(pj[5]);
        int magia = Integer.parseInt(pj[8]);
        switch (tipo){
            case "Cascos":
                defensa = defensa + (boost*factor);
                personaje.modificarBd("DEFENSA", defensa, idPj);
                //se aniadie a defensa
                break;
            case "Pecheras":
                defensa = defensa + (boost*factor);
                personaje.modificarBd("DEFENSA", defensa, idPj);
                 //se aniadie a defensa
                break;
            case "Pantalones":
                defensa = defensa + (boost*factor);
                personaje.modificarBd("DEFENSA", defensa, idPj);
                //se aniadie a defensa
                break;
            case "Botas":
                defensa = defensa + (boost*factor);
                personaje.modificarBd("DEFENSA", defensa, idPj);
                //se aniadie a defensa
                break;
            case "Armas":
                ataque = ataque + (boost*factor);
                personaje.modificarBd("ATAQUE", ataque, idPj);
                break;
            case "Escudos":
                defensa = defensa + (boost*factor);
                personaje.modificarBd("DEFENSA", defensa, idPj);
                break;
        }       
        

        
    }
    
    //el metodo mostrarItem lo que hace es que el usuario al presionar un item, este le mostrará sus distintas características
    //ya sea el nombre dl item, descripción, su imagen, entre otros.
    private void mostrarItem(int idCuadro) {
        String[] datos;
        try {
            datos = modeao.buscarItemEspecifico(idCuadro).split("Cortar");
            visinv.mostrarItemSolicitado(datos, idCuadro);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Error realizando la solicitud del usuario","Error",
                                JOptionPane.ERROR_MESSAGE); 
        }
        
    }
    
    //el metodo mostrarDatosPj, obtiene los datos del personaje que posee el usuario para mostrarlos por pantalla.
    private void mostrarDatosPj(){
        String[] pj = personaje.consultar(idPj).split("CORTAR");
        visinv.mostrarPersonaje(pj);
    }
    
    //el metodo mostrarEquipo, mostrará por la pantalla los items que el jugador ha seleccionado o equipado para poder  
    // usarlos posteriormente durante una batalla.
    private void mostrarEquipo(){
        visinv.jLabel4.removeAll();
        String[] equipment = personaje.consultar(idPj).split("CORTAR"); //obtengo los datos del personaje segun su id
        int contador = 16;
        for (int i=0;i<2;i++){
            for (int j=0;j<5;j++){
                this.equipamiento[i][j] = new ModeloInventario();
                equipamiento[i][j].cuadro.setBounds((i*100)+1, (j*80)+15, 70, 70);
                if(i==0 && j==4){
                        break;
                }
                
                int extra = Integer.parseInt(equipment[contador]);
                if(extra>0){
                    String[] item;
                    try {
                        item = modeao.buscarItemEspecifico(extra).split("Cortar"); //obtengo el item seleccionado segun sus caracteristicas, buscando y haciendo un llamado en la BD
                        ImageIcon imgItm = new ImageIcon(getClass().getResource(item[1]));
                        imgItm = new ImageIcon(imgItm.getImage().getScaledInstance(65, 65, java.awt.Image.SCALE_DEFAULT));
                        this.equipamiento[i][j].cuadro.setIcon(imgItm);
                        this.equipamiento[i][j].idCuadro = extra;
                        this.equipamiento[i][j].cuadro.addActionListener(this);
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null,"Error realizando la solicitud del usuario","Error",
                                JOptionPane.ERROR_MESSAGE); 
                    }
                }
                contador = contador +1;
                visinv.jLabel4.add(this.equipamiento[i][j].cuadro);
            }
        }visinv.jLabel4.repaint();
        
       
    }
    
    //el metodo imprimirInv, muestra la usuario el inventario que tiene el usuario, es decir muestra todos los items
    //que ha comprado en la EAO, para luego poder utilizarlos.
    public void imprimirInv(int idPj){
        visinv.jLabel16.removeAll();
        ArrayList<Integer> idsItem = personaje.consultarInventario(ModeloUsuario.idPsj);
        int contadorItems = 0;
        for (int i=0;i<5;i++){
            for (int j=0;j<10;j++){
                this.inventario[i][j] = new ModeloInventario();
                this.inventario[i][j].cuadro.setBounds((i*50)+10,(j*50)+100,50,50);
                if (contadorItems<idsItem.size()){
                    try {
                        String[] item = modeao.buscarItemEspecifico(idsItem.get(contadorItems)).split("Cortar");
                        ImageIcon imgItm = new ImageIcon(getClass().getResource(item[1]));
                        imgItm = new ImageIcon(imgItm.getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_DEFAULT));
                        this.inventario[i][j].cuadro.setIcon(imgItm);
                        this.inventario[i][j].idCuadro = idsItem.get(contadorItems);
                        this.inventario[i][j].cuadro.addActionListener(this);

                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null,"Error realizando la solicitud del usuario","Error",
                                JOptionPane.ERROR_MESSAGE); 
                    }
                    
                }contadorItems = contadorItems + 1;
                visinv.jLabel16.add(this.inventario[i][j].cuadro);             
                
            }
        }visinv.jLabel16.repaint();
    }
    
    //el metodo equiparObjeto, equipa el objeto seleccionado por el usuario, en la BD
    public boolean equiparObjeto(String tipo, int idObj){
        //this.fecha = new java.util.Date();
        switch (tipo){
            case "Cascos":
                if (this.equipamiento[0][0].idCuadro != 0){
                    JOptionPane.showMessageDialog(visinv, "Ya no tienes espacio", "Error"
                                   , JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                personaje.equipar(this.idPj, idObj, "ID_CASCO");
                mostrarEquipo();
                return true;
                //this.escribir.println(this.formato.format(fecha)+" = se intenta equipar un item := completado");
            case "Pecheras":
                 if (this.equipamiento[0][1].idCuadro != 0){
                    JOptionPane.showMessageDialog(visinv, "Ya no tienes espacio", "Error"
                                   , JOptionPane.ERROR_MESSAGE);
                    return false;
                 }
                //this.escribir.println(this.formato.format(fecha)+" = se intenta equipar un item := completado");
                personaje.equipar(this.idPj, idObj, "ID_PECHERA");
                mostrarEquipo();
                return true;
            case "Pantalones":
                if (this.equipamiento[0][2].idCuadro != 0){
                    JOptionPane.showMessageDialog(visinv, "Ya no tienes espacio", "Error"
                                   , JOptionPane.ERROR_MESSAGE);
                    return false;
                 }
               // this.escribir.println(this.formato.format(fecha)+" = se intenta equipar un item := completado");
                personaje.equipar(this.idPj, idObj, "ID_PANTALON");
                mostrarEquipo();
                return true;
            case "Botas":
                if (this.equipamiento[0][3].idCuadro != 0){
                    JOptionPane.showMessageDialog(visinv, "Ya no tienes espacio", "Error"
                                   , JOptionPane.ERROR_MESSAGE);
                    return false;
                 }
                //this.escribir.println(this.formato.format(fecha)+" = se intenta equipar un item := completado");
                personaje.equipar(this.idPj, idObj, "ID_BOTAS");
                mostrarEquipo();
                return true;
            case "Armas":
                
                String[] pj = personaje.consultar(this.idPj).split("CORTAR");
                if (idObj == 0){
                    int buscar = Integer.parseInt(visinv.jLabel5.getText());
                    for (int i=0;i<2;i++){
                        String bd = "ID_ARMA";
                       if (this.equipamiento[1][i].idCuadro == buscar){
                           bd = bd+(i+1);
                           personaje.equipar(this.idPj, idObj, bd);
                           mostrarEquipo();
                 //           this.escribir.println(this.formato.format(fecha)+" = se intenta equipar un item := completado");
                           return true;
                       }
                       
                    }
                   // this.escribir.println(this.formato.format(fecha)+" = se intenta equipar un item := error");
                    JOptionPane.showMessageDialog(visinv, "No tienes objetos equipados", "ERROR",
                            JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                if (pj[20].equals("0") | idObj == 0){
                    personaje.equipar(this.idPj, idObj, "ID_ARMA1");
                  //  this.escribir.println(this.formato.format(fecha)+" = se intenta equipar un item := completado");

                    //equipar acá
                }else if(pj[21].equals("0") | idObj == 0){
                    personaje.equipar(this.idPj, idObj, "ID_ARMA2");
                    //this.escribir.println(this.formato.format(fecha)+" = se intenta equipar un item := completado");
                    //equipar acá
                }else{
                    JOptionPane.showMessageDialog(visinv, "Ya no tienes espacio", "Error"
                                   , JOptionPane.ERROR_MESSAGE);
                    return false;
                //this.escribir.println(this.formato.format(fecha)+" = se intenta equipar un item := error");

                }
                mostrarEquipo();
                break;
            case "Escudos":
                pj = personaje.consultar(this.idPj).split("CORTAR");
                if (idObj == 0){
                    int buscar = Integer.parseInt(visinv.jLabel5.getText());
                    for (int i=0;i<2;i++){
                        String bd = "ID_ARMA";
                       if (this.equipamiento[1][i].idCuadro == buscar){
                           bd = bd+(i+1);
                           personaje.equipar(this.idPj, idObj, bd);
                           mostrarEquipo();
                        //    this.escribir.println(this.formato.format(fecha)+" = se intenta equipar un item := completado");
                           return true;
                       }
                       
                    }
                    //this.escribir.println(this.formato.format(fecha)+" = se intenta equipar un item := error");
                    JOptionPane.showMessageDialog(visinv, "No tienes objetos equipados", "ERROR",
                            JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                if (pj[20].equals("0") | idObj == 0){
                    personaje.equipar(this.idPj, idObj, "ID_ARMA1");
               //     this.escribir.println(this.formato.format(fecha)+" = se intenta equipar un item := completado");


                    //equipar acá
                }else if(pj[21].equals("0") | idObj == 0){
                    personaje.equipar(this.idPj, idObj, "ID_ARMA2");
               //      this.escribir.println(this.formato.format(fecha)+" = se intenta equipar un item := completado");
                    //equipar acá
                }else{
                    // this.escribir.println(this.formato.format(fecha)+" = se intenta equipar un item := error");
                    
                     JOptionPane.showMessageDialog(visinv, "Ya no tienes espacio", "Error"
                            , JOptionPane.ERROR_MESSAGE);
                     return false;
                }
                mostrarEquipo();
                break;
            case "Pociones":
                pj = personaje.consultar(this.idPj).split("CORTAR");
                if (idObj == 0){
                    int buscar = Integer.parseInt(visinv.jLabel5.getText());
                    for (int i=2;i<5;i++){
                        String bd = "EXTRA";
                       if (this.equipamiento[1][i].idCuadro == buscar){
                           bd = bd+(i-1);
                           personaje.equipar(this.idPj, idObj, bd);
                           mostrarEquipo();
                   //        this.escribir.println(this.formato.format(fecha)+" = se intenta equipar un item := completado");
                           return true;
                       }
                       
                    }
                //    this.escribir.println(this.formato.format(fecha)+" = se intenta equipar un item := error");
                    JOptionPane.showMessageDialog(visinv, "No tienes objetos equipados", "ERROR",
                            JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                if (pj[22].equals("0")){
                    personaje.equipar(this.idPj, idObj, "EXTRA1");
              //    this.escribir.println(this.formato.format(fecha)+" = se intenta equipar un item := completado");
                    //equipar acá
                }else if(pj[23].equals("0")){
                    personaje.equipar(this.idPj, idObj, "EXTRA2");
            //       this.escribir.println(this.formato.format(fecha)+" = se intenta equipar un item := completado");
                    //equipar acá
                }else if(pj[24].equals("0")){
                    personaje.equipar(this.idPj, idObj, "EXTRA3");
            //       this.escribir.println(this.formato.format(fecha)+" = se intenta equipar un item := completado");
                    //equipar acá
                }else{
             //       this.escribir.println(this.formato.format(fecha)+" = se intenta equipar un item := error");
                    JOptionPane.showMessageDialog(visinv, "Ya no tienes espacio", "Error"
                            , JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                mostrarEquipo();
                break;
                
        }
        return true;
        
    }
    
    public void devolverAInventario(int item){
        modeao.comprarItem(ModeloUsuario.idPsj, item);
        this.imprimirInv(this.idPj);
        
    }
    public void quitarDeInventario(int item){
        personaje.quitarItem(ModeloUsuario.idPsj, item);
        this.imprimirInv(this.idPj);
        
    }
    
    private void agregarAliados(){
        ArrayList<Integer> aliados =  obtenerListaAliados();
        aliados.add(this.idPj);
        for (int id:aliados){
            String[] consulta = personaje.consultar(id).split("CORTAR");
            visinv.jComboBox1.addItem(Integer.toString(id));
            visinv.comboBoxEquipo.addItem(consulta[1]);
        }
    }
    
   public ArrayList obtenerListaAliados(){
        ModeloUsuario modusuario = new ModeloUsuario();
        try {
            ArrayList<Integer> aliados = modusuario.obtenerAliados(ModeloUsuario.idUs);
            return aliados;
        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null,"Error realizando la solicitud del usuario","Error",
                                JOptionPane.ERROR_MESSAGE); 
        }
        return null;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        this.fecha = new java.util.Date();
        if (e.getSource() == visinv.jButton15){
            this.visinv.setVisible(false);
        }
        String accion = e.getActionCommand();
        switch (accion){
            case "InvEquip":
                int pos = visinv.comboBoxEquipo.getSelectedIndex();
                int pj = Integer.parseInt(visinv.jComboBox1.getItemAt(pos));
                this.idPj = pj;
                this.mostrarEquipo();
                break;
                //ver inventario de equipo
            case "Equipar": 
                //al presionar el boton de equipar, se equipará el
                //item seleccionado en sus items a utilizar para la batalla
                try {
                    int id = Integer.parseInt(visinv.jLabel20.getText());
                    String[] item = modeao.buscarItemEspecifico(id).split("Cortar");
                    String tipo = item[5];
                    if(equiparObjeto(tipo,id)){
                         this.escribir.println(this.formato.format(fecha)+" =  se equipa el objeto "+id+" := correcto");
                        try {
                            archivo.close();
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(null,"Error equipando", "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                        this.afectarBD(tipo, 1, Integer.parseInt(item[3]));
                        this.quitarDeInventario(id);
                        this.mostrarDatosPj();
                        visinv.jLabel20.setText("");
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(visinv, "\"Error con la base de datos, porfavor intente nuevamente", "Error" , JOptionPane.ERROR_MESSAGE);
                } catch(java.lang.NumberFormatException a){
                    JOptionPane.showMessageDialog(visinv, "Porfavor, seleccione un item", "Error" , JOptionPane.ERROR_MESSAGE);
                }
                break;
            case "Desequipar": 
                //al presionar este boton, se desequipará el item y volverá al inventario del jugador
                try {
                    int id = Integer.parseInt(visinv.jLabel5.getText());
                    String[] item = modeao.buscarItemEspecifico(id).split("Cortar");
                    String tipo = item[5];
                    if (equiparObjeto(tipo,0)){
                        this.escribir.println(this.formato.format(fecha)+" =  se desequipa item id "+id+" := correcto");
                        archivo.close();

                        this.afectarBD(tipo, -1, Integer.parseInt(item[3]));
                        this.devolverAInventario(id);
                        this.mostrarDatosPj();
                        visinv.jLabel5.setText("");
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(visinv, "Error con la base de datos, porfavor intente nuevamente", "Error" , JOptionPane.ERROR_MESSAGE);
                } catch(java.lang.NumberFormatException a){
                    JOptionPane.showMessageDialog(visinv, "Porfavor, seleccione un item", "Error" , JOptionPane.ERROR_MESSAGE);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(visinv, "Error con el archivo", "Error", JOptionPane.ERROR_MESSAGE);
        }
                break;
            case "Mejorar":
                if (visinv.jLabel20.getText().equals("")){
                    JOptionPane.showMessageDialog(visinv, "Porfavor, seleccione un item", "Error" , JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int idAMejorar = Integer.parseInt(visinv.jLabel20.getText());
                modItem = new ModeloItem();
                if(modItem.puedeMejorarsse(idAMejorar)){
                    SubControladorMejora submejora = new SubControladorMejora(idAMejorar, this);
                    submejora.mostrarVista();
                    //El item si se puede mejorar
                    //Se instancia un subControlador y la subVista
                }else{
                    JOptionPane.showMessageDialog(null, "El item seleccionado no puede mejorarse", "Error", JOptionPane.ERROR_MESSAGE);

                }
                break;
                
                
                //mejorar objeto
            default: 
                //Si no fue ningun boton fijo, se busca en los tableros 
                for (int i=0;i<5;i++){
                    for (int j=0;j<10;j++){
                        if (e.getSource() == this.inventario[i][j].cuadro){
                            mostrarItem(this.inventario[i][j].idCuadro);
                            visinv.jLabel5.setText("");
                            visinv.jLabel20.setText(Integer.toString(this.inventario[i][j].idCuadro));
                        }else if(i<2 && j<5){
                            if (e.getSource()== this.equipamiento[i][j].cuadro){
                                mostrarItem(this.equipamiento[i][j].idCuadro);
                                visinv.jLabel20.setText("");
                                visinv.jLabel5.setText(Integer.toString(equipamiento[i][j].idCuadro));
                            }
                        }
                    }
                }
                break;
        }
        
        
    }
    
    void mostrarInventario() { //metodo utilizado para hacer visible la vista del inventario, en caso de que se llame a este metodo.
        visinv.setLocationRelativeTo(null);
        visinv.setVisible(true);
    }

    
   
    
}
