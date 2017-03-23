/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

import BD.Conexion;
import DAO.DAOLogIn;
import DAO.personajesDAO;
import Vistas.VistaRegistro;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kevin
 */
public class Personaje {
    int id, vida, defensa, ataque, movimiento, magia, nivelTraicion, velocidad;
    String imagen, icono, imgTablero, gif;
    int posicionX, posicionY, posicionZ;
    int arma1, arma2;
    int casco, pechera, pantalon, botas;
    int extra1, extra2, extra3;
    VistaRegistro visreg;
    personajesDAO pjdao;
    String nick, tipo, rol;
    private int nivel;
    private int exp;
    
    public Personaje(){
        pjdao = new personajesDAO();
        visreg = new VistaRegistro();
    }
    
    public boolean quitarItem(int idPj, int item) {
        Conexion conexion = new Conexion();
        boolean seHizo = conexion.ejecutar(pjdao.quitarObjeto(idPj, item));
        return seHizo;
    }
    
    
     public boolean quitarAliado(int idUs, int id) {
        Conexion conexion = new Conexion();
        return (conexion.ejecutar(pjdao.quitarAliado(idUs, id)));
    }

    public boolean modificarCantBat(int idUs, int id, int cantBat) {
        Conexion conexion = new Conexion();
        return (conexion.ejecutar(pjdao.modCantBat(idUs, id, cantBat)));
    }
    public int obtenerCantBat(int idPj, int idUs) {
        Conexion conexion = new Conexion();
        ResultSet consulta = conexion.consultar(pjdao.obtCantBatt(idPj, idUs));
        try {
            if (consulta.next()){
                int cantBat = consulta.getInt("CANTBATLL");
                return cantBat;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Error realizando la solicitud del usuario","Error",
                                JOptionPane.ERROR_MESSAGE); 
        }
        return 0;
    }
    
    public boolean modificarExp(int pj, int exp) {
        Conexion conexion = new Conexion();
        return(conexion.ejecutar(pjdao.modExp(pj,exp)));
        
    }
    public boolean modificarNivel(int pj, int nivel) {
        Conexion conexion = new Conexion();
        return(conexion.ejecutar(pjdao.modNivel(pj,exp)));
        
    }
    
    public boolean modificarTraicion(int pj, int traicion) {
        Conexion conexion = new Conexion();
        return(conexion.ejecutar(pjdao.modTraicion(pj,traicion)));
        
    }
     
    public void modificarMovimiento(int movimiento, int idPj){
        Conexion conexion = new Conexion();
        conexion.ejecutar(pjdao.modMov(movimiento, idPj));
    }
    public void modificarVida(int vida, int idPj){
        Conexion conexion = new Conexion();
        conexion.ejecutar(pjdao.modVida(vida, idPj));
    }
    
    public void modificarBd(String donde, int nuevoValor, int idPj){
        Conexion conexion = new Conexion();
        conexion.ejecutar(pjdao.modificarBd(donde,nuevoValor,idPj));
    }
    
    public void modificarPosicion(int x, int y, int z, int idPj){
        Conexion conexion = new Conexion();
        conexion.ejecutar(pjdao.modifPos(x,y,z, idPj));
    }
    
    public boolean equipar(int idPj, int idObj, String tipObj){
        Conexion conexion = new Conexion();
        boolean seEquipo = conexion.ejecutar(pjdao.equipar(idPj, idObj, tipObj));
        return seEquipo;
    }
    
    
    public void agregarABD(){
        Conexion conexionA = new Conexion();
        conexionA.ejecutar(pjdao.nuevoUsuario(this.nick, this.tipo, this.rol, this.vida, this.defensa, this.ataque, this.movimiento, 
                this.velocidad, this.magia, this.nivelTraicion, this.imagen, this.icono, this.imgTablero, this.gif));
    }
    
    public ArrayList<Integer> consultarInventario(int idPj){
        ArrayList<Integer> itemsPj = new ArrayList<>();
        Conexion conexion = new Conexion();
        ResultSet resultado = conexion.consultar(pjdao.inventario(idPj));
        try {
            while (resultado.next()){
                itemsPj.add(resultado.getInt("ID_ITEM"));
            }
            return itemsPj;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Error realizando la solicitud del usuario","Error",
                                JOptionPane.ERROR_MESSAGE); 
            return null;
        }
        
    }
    
    public String consultar(int id){
        Conexion conexion = new Conexion();
        ResultSet resultado = conexion.consultar(pjdao.consulta(id));
        try {
            if (resultado.next()){
                this.nick = resultado.getString("NICK");
                this.vida = Integer.parseInt(resultado.getString("VIDA"));
                this.defensa =Integer.parseInt(resultado.getString("DEFENSA"));
                this.ataque = Integer.parseInt(resultado.getString("ATAQUE"));
                this.movimiento = Integer.parseInt(resultado.getString("MOVIMIENTO"));
                this.magia = Integer.parseInt(resultado.getString("MAGIA"));
                this.nivelTraicion = Integer.parseInt(resultado.getString("NIVELTRAICION"));
                this.imagen = resultado.getString("IMAGEN");
                this.icono = resultado.getString("ICONO");
                this.imgTablero = resultado.getString("IMGTABLERO");
                this.gif = resultado.getString("GIF");
                this.rol = resultado.getString("ROL");
                this.tipo = resultado.getString("TIPO");
                this.velocidad = Integer.parseInt(resultado.getString("VELOCIDAD"));
                
                this.posicionX = Integer.parseInt(resultado.getString("POSICIONX"));
                this.posicionY = Integer.parseInt(resultado.getString("POSICIONY"));
                this.posicionZ =Integer.parseInt(resultado.getString("POSICIONZ"));
                this.arma1 = Integer.parseInt(resultado.getString("ID_ARMA1"));
                this.arma2 =Integer.parseInt(resultado.getString("ID_ARMA2"));
                this.casco = Integer.parseInt(resultado.getString("ID_CASCO"));
                this.pechera = Integer.parseInt(resultado.getString("ID_PECHERA"));
                this.pantalon = Integer.parseInt(resultado.getString("ID_PANTALON"));
                this.botas = Integer.parseInt(resultado.getString("ID_BOTAS"));
                this.extra1 = Integer.parseInt(resultado.getString("EXTRA1"));
                this.extra2 = Integer.parseInt(resultado.getString("EXTRA2"));
                this.extra3 = Integer.parseInt(resultado.getString("EXTRA3"));
                this.nivel = Integer.parseInt(resultado.getString("NIVEL"));
                this.exp = Integer.parseInt(resultado.getString("EXPERIENCIA"));
                return id+"CORTAR"+nick+"CORTAR"+tipo+"CORTAR"+rol+"CORTAR"+vida+"CORTAR"+defensa+"CORTAR"+ataque+
                        "CORTAR"+movimiento+"CORTAR"+magia+"CORTAR"+imagen+"CORTAR"+icono+"CORTAR"+imgTablero+
                        "CORTAR"+gif+"CORTAR"+posicionX+"CORTAR"+posicionY+"CORTAR"+posicionZ+"CORTAR"+casco
                        +"CORTAR"+pechera+"CORTAR"+pantalon+"CORTAR"+botas+"CORTAR"+arma1+"CORTAR"+
                        arma2+"CORTAR"+extra1+"CORTAR"+extra2+"CORTAR"+extra3+"CORTAR"+velocidad+"CORTAR"+
                        nivelTraicion+"CORTAR"+nivel+"CORTAR"+exp;
            }else{
                JOptionPane.showMessageDialog(visreg, "Error, usuario no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                return "Error. Usuario no encontrado";
            }
        }catch (SQLException e){
            JOptionPane.showMessageDialog(visreg, "Ocurrió un error durante la ejecucion. Porfavor, intente nuevamente", "Error", JOptionPane.ERROR_MESSAGE);
            return "Error";
        }
        
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    
    public void setVelocidad(int velocidad){
        this.velocidad = velocidad;
    }
    
    public int getVelocidad(){
        return this.velocidad;
    }


    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public int getDefensa() {
        return defensa;
    }

    public void setDefensa(int defensa) {
        this.defensa = defensa;
    }

    public int getAtaque() {
        return ataque;
    }

    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }

    public int getMovimiento() {
        return movimiento;
    }

    public void setMovimiento(int movimiento) {
        this.movimiento = movimiento;
    }

    public int getMagia() {
        return magia;
    }

    public void setMagia(int magia) {
        this.magia = magia;
    }

    public int getNivelTraicion() {
        return nivelTraicion;
    }

    public void setNivelTraicion(int nivelTraicion) {
        this.nivelTraicion = nivelTraicion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }

    public String getImgTablero() {
        return imgTablero;
    }

    public void setImgTablero(String imgTablero) {
        this.imgTablero = imgTablero;
    }

    public String getGif() {
        return gif;
    }

    public void setGif(String gif) {
        this.gif = gif;
    }

    public int getPosicionX() {
        return posicionX;
    }

    public void setPosicionX(int posicionX) {
        this.posicionX = posicionX;
    }

    public int getPosicionY() {
        return posicionY;
    }

    public void setPosicionY(int posicionY) {
        this.posicionY = posicionY;
    }

    public int getPosicionZ() {
        return posicionZ;
    }

    public void setPosicionZ(int posicionZ) {
        this.posicionZ = posicionZ;
    }

    public int getArma1() {
        return arma1;
    }

    public void setArma1(int arma1) {
        this.arma1 = arma1;
    }

    public int getArma2() {
        return arma2;
    }

    public void setArma2(int arma2) {
        this.arma2 = arma2;
    }

    public int getCasco() {
        return casco;
    }

    public void setCasco(int casco) {
        this.casco = casco;
    }

    public int getPechera() {
        return pechera;
    }

    public void setPechera(int pechera) {
        this.pechera = pechera;
    }

    public int getPantalon() {
        return pantalon;
    }

    public void setPantalon(int pantalon) {
        this.pantalon = pantalon;
    }

    public int getBotas() {
        return botas;
    }

    public void setBotas(int botas) {
        this.botas = botas;
    }

    public int getExtra1() {
        return extra1;
    }

    public void setExtra1(int extra1) {
        this.extra1 = extra1;
    }

    public int getExtra2() {
        return extra2;
    }

    public void setExtra2(int extra2) {
        this.extra2 = extra2;
    }

    public int getExtra3() {
        return extra3;
    }

    public void setExtra3(int extra3) {
        this.extra3 = extra3;
    }

    public boolean usarItem(String lugar, int idPsj) {
        Conexion conexion = new Conexion();
        return(conexion.ejecutar(pjdao.usarItem(lugar,idPsj)));
    }

   

    

    

   
    
    
}