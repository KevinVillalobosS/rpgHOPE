/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;




/**
 *
 * @author Kevin
 */
public class personajesDAO {
    public String nuevoUsuario(String nick, String tipo, String rol, int vida, int defensa,
            int ataque, int movimiento,int velocidad, int magia, int nivelTraicion, String imagen, String icono,
            String imgTablero, String gif){
         
            return "insert into PERSONAJES (NICK,TIPO,ROL,VIDA,DEFENSA,ATAQUE,MOVIMIENTO,VELOCIDAD,MAGIA,"
                    + "NIVELTRAICION,IMAGEN,ICONO,IMGTABLERO,GIF) values ('"+nick+"','"
                    +tipo+"','"+rol+"',"+vida+","+defensa+","+ataque+","+movimiento+","+velocidad+","+magia+","
                    +nivelTraicion+",'"+imagen+"','"+icono+"','"+imgTablero+"','"+gif+"')";
            
    }   
    
    public String consulta(int id){
        return "select * from PERSONAJES where id="+id;
    }
    public String actualizar(int id_pj, String cambio, int nuevoValor){
        return "update PERSONAJES set"+cambio+"='"+nuevoValor+"' where id="+id_pj;
  
    }

    public String inventario(int idPj) {
        return "select * from INVENTARIO where ID_PERSONAJE="+idPj;
    }

    public String equipar(int idPj, int idObj, String tipoObjeto) {
        return "update PERSONAJES set "+tipoObjeto+"="+idObj+"where ID="+idPj;
    }

    public String modifPos(int x, int y, int z, int idPj) {
        return "update PERSONAJES set POSICIONX ="+x+",POSICIONY ="+y+", POSICIONZ="+z
                +"where ID="+idPj;

    }

    public String modMov(int movimiento, int idPj) {
        return "update PERSONAJES set MOVIMIENTO ="+movimiento+" where ID="+idPj; 
    }
    
    public String modVida(int vida, int idPj) {
        return "update PERSONAJES set VIDA ="+vida+" where ID="+idPj;
    }

    public String modificarBd(String donde, int nuevoValor, int idPj) {
        return "update PERSONAJES set "+donde+" ="+nuevoValor+" where ID="+idPj;
    }
    
    public String quitarObjeto(int idPj, int idItm){
        return "DELETE FROM INVENTARIO WHERE ID_PERSONAJE="+idPj+" AND ID_ITEM ="+idItm;
    }

    public String modTraicion(int pj, int traicion) {
        return "UPDATE PERSONAJES SET NIVELTRAICION ="+traicion+" WHERE ID="+pj;
    }

    public String modExp(int pj, int exp) {
        return "UPDATE PERSONAJES SET EXPERIENCIA ="+exp+" WHERE ID="+pj;
    }

    public String modNivel(int pj, int exp) {
        return "UPDATE PERSONAJES SET NIVEL ="+exp+" WHERE ID="+pj;
    }

    public String obtCantBatt(int idPj, int idUs) {
        return "SELECT CANTBATLL FROM FOROGRIEGO WHERE ID_JUGADOR ="+idUs+" AND IDPJ="+idPj;
    }
    
    public String quitarAliado(int idUs, int idPsj){
        return "DELETE FROM FOROGRIEGO WHERE ID_JUGADOR="+idUs+" AND IDPJ= "+idPsj;
    }

    public String modCantBat(int idUs, int id, int cantBat) {
        return "UPDATE FOROGRIEGO SET CANTBATLL ="+cantBat+" WHERE ID_JUGADOR="+idUs+" AND IDPJ ="+id;
    }

    public String usarItem(String lugar, int idPsj) {
        return "UPDATE PERSONAJES SET "+lugar+" = 0 WHERE ID ="+idPsj;
    }


    
}