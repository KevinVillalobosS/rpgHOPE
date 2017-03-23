/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpgHope;

import Controladores.ControladorLogIn;
import java.io.IOException;

/**
 *
 * @author Kevin
 */
public class rpgHope {
    public static void main(String[] args) throws IOException {
        ControladorLogIn conlog = new ControladorLogIn();
        conlog.mostrarLogin();
    }
}
