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
public class EaoDAO {

    
    public String obtenerItems(String tipo){
        return "SELECT * FROM ITEMS WHERE TIPO = '"+tipo+"'";
    }
    public String obtenerEspecifico(int id){
        return "SELECT * FROM ITEMS WHERE ID_ITEM="+id;
    }

    public String comprarItem(int idPj, int idObj) {
        return "INSERT INTO INVENTARIO(ID_PERSONAJE,ID_ITEM) VALUES ("+idPj+","+idObj+")";
    }
}
