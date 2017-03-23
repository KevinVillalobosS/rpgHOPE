/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

/**
 *
 * @author Fernando
 */
public class DAOItem {

    public String agregarABD(String nom_item, String imagen, String descripcion, int boost, int a_boost, String tipo, int precio) {
        return "INSERT INTO ITEMS(nom_item,imagen,descripcion,boost,a_boost,tipo,precio) VALUES ('"+nom_item+"','"+imagen+"','"+descripcion+"',"
                    +boost+","+a_boost+",'"+tipo+"',"+precio+")";
    }
    
    public String consultarMejora(int id){
        return "SELECT * FROM RELMEJORAS WHERE ID_ITEM ="+id;
    }
    
}
