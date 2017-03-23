/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//Se define el paquete de la clase
package Controladores;

//Se hacen las importaciones necesarias para el desarrollo de la clase, desde otros paquetes, desde 
//Java AWT, Java SQL, Java Util y Java Swing
import Modelos.ModeloUsuario;
import Vistas.VistaMiniJuegoAhorcado;
import Modelos.ModeloMiniJuegoAhorcado;
import Vistas.VistaLogInFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Fernando
 */
//Se define la clase y se implementra el ActionListener para escuchar las acciones realizadas por el 
//usuario en la vista
public class ControladorMiniJuegoAhorcado implements ActionListener{
//Se definen los atributos de la clase
//Dado que el Ahorcado será una versión v/s, donde el jugador enfrentará a la máquina, la mayoría de las 
//variables definidas para el usuario están duplicadas para la CPU, el booleano jugar será el que permita
//el juego, tanto del usuario como de la CPU

    private VistaMiniJuegoAhorcado visahor;
    private ModeloMiniJuegoAhorcado modahor;
    private VistaLogInFrame vislog;
    private boolean jugar = false;
    private char[] palabraAAdivinar;
    private char[] palabraAAdivinarUsuario;
    private char[] palabraAAdivinarCPU;
    public char[] palabraUsuario;
    public char[] palabraCPU;
    private String textUsuario;
    private String textCPU;
    private String textDinero;
    public String aplicandoCambios; 
    int intentosUsuario;
    int intentosCPU;
    boolean aplicarCambiosUsuario = false;
    boolean aplicarCambiosCPU = false;

//Se define el constructor de la clase, el que recibe como parámetro la id del jugador, que es un valor int
    public ControladorMiniJuegoAhorcado(int idJgr){
//Se instancian los atributos de la clase, desde el frame que contiene el dialog del minijuego, hasta los 
//componentes que mostrarán la información al usuario
        this.modahor = new ModeloMiniJuegoAhorcado();
        this.vislog = new VistaLogInFrame();        
        this.visahor = new VistaMiniJuegoAhorcado(vislog, true, this);
//Se implementan los Listeners a la vista que utilizará el minijuego
        this.visahor.addListeners(this);                
        this.textUsuario = "????????????????";
        this.textCPU = "????????????????";
        this.aplicandoCambios = "";
        this.textDinero = "0";  
}
//Método que a través de un random selecciona una palabra del diccionario creado en el modelo del minijuego    
    public String eleccionPalabra(){ 
//El booleano que permite el juego pasa a ser verdadero        
        this.jugar = true;
        this.modahor.agregarAlArrayList();   
        int indicePalabra = (int)(Math.random()*(this.modahor.diccionario.length)); 
        this.palabraAAdivinar = modahor.diccionario[indicePalabra].toCharArray();
        this.palabraAAdivinarUsuario = this.palabraAAdivinar;
        this.palabraAAdivinarCPU = this.palabraAAdivinar;
//Se define un ArrayList para almacenar las letras que la CPU ha usado con el fin que no se repitan        
//       this.letrasUsadasCPU = new ArrayList<>();
//Se informa al usuario cuantas letras tiene la palabra que debe adivinar        
        JOptionPane.showMessageDialog(null,"La palabra a adivinar tiene " + this.palabraAAdivinar.length + " letras.");
        this.visahor.textUsuario.setText(aplicandoCambios);
        this.visahor.textCPU.setText(aplicandoCambios); 
//Se oculta la palabra, cambiando cada letra por un "_" para ocultar la palabra        
        for (int i=0; i<=this.palabraAAdivinar.length-1; i++){
          aplicandoCambios += "_";}
//Se crea un Array a partir de la palabra ya oculta        
        this.palabraUsuario = aplicandoCambios.toCharArray();
        this.palabraCPU = aplicandoCambios.toCharArray();
//Tanto para el usuario como para la CPU, el panel de la figura y la label de desaciertos inician sin errores
        this.visahor.labelErroresUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagenesDesaciertos/FiguraDesacierto0.png"))); 
        this.visahor.labelErroresCPU.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagenesDesaciertos/FiguraDesacierto0.png"))); 
        this.visahor.labelFiguraAhorcadoUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagenesPanelAhorcado/FiguraAhorcado0.png"))); 
        this.visahor.labelFiguraAhorcadoCPU.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagenesPanelAhorcado/FiguraAhorcado0.png")));
//Se da el valor 0 a los intentos de la CPU y del usuario
        intentosUsuario = 0;
        intentosCPU = 0; 
//Se retorna el índice de la palabra en el diccionario        
        return this.modahor.diccionario[indicePalabra];
    }       

//Método que obtiene una letra por medio de un random para el juego de la CPU    
    private void obtenerLetra(){
        if(jugar){
        int indiceLetra = (int)(Math.random()*(this.modahor.abecedario.length));
//Inmetiamente después de elegir la palabra, se invoca el método que la revisa
        revisarLetraCPU(this.modahor.abecedario[indiceLetra]);} 
    }
    
    public void revisarLetraUsuario(char letraIngresada){
        if(jugar){
        String adivinandoUsuario="";
        if (this.intentosUsuario==6){
            this.visahor.labelFiguraAhorcadoUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagenesPanelAhorcado/FiguraAhorcado7.png")));
            this.visahor.labelErroresUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagenesDesaciertos/FiguraDesacierto7.png")));
            String respuesta="";
            for(int i=0;i<=this.palabraAAdivinarUsuario.length-1;i++){
            respuesta = respuesta + this.palabraAAdivinarUsuario[i];
            }
             JOptionPane.showMessageDialog(null, "¡Has Perdido! \n La palabra a adivinar era: " +respuesta, "La CPU es más inteligente que tú", 0 , new javax.swing.ImageIcon(getClass().getResource("/ImagenesFinales/CaraPerdedor.png")));
             int recompensa = Integer.parseInt(this.visahor.textDinero.getText());
             recompensa -= 2000;
             this.visahor.textDinero.setText(Integer.toString(recompensa));
             this.visahor.desactivarBotones();
             this.jugar = false;
        }
        else{
            for(int j=0;j<=this.palabraAAdivinarUsuario.length-1;j++){          
            if(this.palabraAAdivinarUsuario[j] == letraIngresada){                
                this.palabraUsuario[j] = letraIngresada;
                this.aplicarCambiosUsuario = true;
             }
             adivinandoUsuario = adivinandoUsuario + this.palabraUsuario[j];
           }

            if(this.aplicarCambiosUsuario == false){
            this.intentosUsuario += 1;             
            this.visahor.labelErroresUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagenesDesaciertos/FiguraDesacierto"+this.intentosUsuario+".png"))); 
            this.visahor.labelFiguraAhorcadoUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagenesPanelAhorcado/FiguraAhorcado"+this.intentosUsuario+".png")));              
            if(this.intentosUsuario<7){
                JOptionPane.showMessageDialog(null, "¡Has Fallado! \n\n\t Pero aún te quedan " + ((7)-this.intentosUsuario) + " intentos.", "Oops!",0, new javax.swing.ImageIcon(getClass().getResource("/ImagenesFinales/Error.png")));
            }             
            }else{
               this.aplicarCambiosUsuario = false;
                JOptionPane.showMessageDialog(null, "¡Has Acertado!", "¡Sigue Así!",0, new javax.swing.ImageIcon(getClass().getResource("/ImagenesFinales/Acierto.png")));                            
            }
            this.visahor.textUsuario.setText(adivinandoUsuario);
            verificarGanadorUsuario();
        }
        }
    }
    
        public void revisarLetraCPU (char letraObtenida){
        if(jugar){
        String adivinandoCPU="";
        if(this.intentosCPU==6){ 
           this.visahor.labelFiguraAhorcadoCPU.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagenesPanelAhorcado/FiguraAhorcado7.png")));
           this.visahor.labelErroresCPU.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagenesDesaciertos/FiguraDesacierto7.png")));
            String respuesta="";
            for(int i=0;i<=this.palabraAAdivinarCPU.length-1;i++){
            respuesta = respuesta + this.palabraAAdivinarCPU[i];
        }      
            JOptionPane.showMessageDialog(null, "¡La CPU ha Perdido!\n La palabra a adivinar era: " +respuesta, "¡Eres más inteligente que la CPU!", 0 , new javax.swing.ImageIcon(getClass().getResource("/ImagenesFinales/CaraPerdedor.png")));
            int recompensa = Integer.parseInt(this.visahor.textDinero.getText());
            recompensa += 5000;
            this.visahor.textDinero.setText(Integer.toString(recompensa));
            this.visahor.desactivarBotones();
            this.jugar = false;
        }  
        else{     
            for(int j=0;j<=this.palabraAAdivinarCPU.length-1;j++){          
                if(this.palabraAAdivinarCPU[j] == letraObtenida){                
                    this.palabraCPU[j] = letraObtenida;
                    this.aplicarCambiosCPU = true;
                }       
             adivinandoCPU = adivinandoCPU + this.palabraCPU[j];
            }

           if(this.aplicarCambiosCPU == false){
            this.intentosCPU += 1;             
            this.visahor.labelErroresCPU.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagenesDesaciertos/FiguraDesacierto"+this.intentosCPU+".png"))); 
            this.visahor.labelFiguraAhorcadoCPU.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagenesPanelAhorcado/FiguraAhorcado"+this.intentosCPU+".png")));              
            if(this.intentosCPU<7){
                JOptionPane.showMessageDialog(null, "¡La CPU Ha Fallado! \n\n\t Pero aún le quedan " + ((7)-this.intentosCPU) + " intentos.", "Oops!",0, new javax.swing.ImageIcon(getClass().getResource("/ImagenesFinales/Error.png")));
            }             
            }else{
               this.aplicarCambiosCPU = false;
                JOptionPane.showMessageDialog(null, "¡La CPU Ha Acertado!", "¡Atento!",0, new javax.swing.ImageIcon(getClass().getResource("/ImagenesFinales/Acierto.png")));
            }
            this.visahor.textCPU.setText(adivinandoCPU);
            verificarGanadorCPU();   
         }        
       }
    }
            
    private void verificarGanadorCPU(){
        boolean CPUGano = false;
        for(int i=0; i<=this.palabraAAdivinarCPU.length-1;i++){
            if(this.palabraCPU[i] == this.palabraAAdivinarCPU[i]){            
                CPUGano = true;
            }else{
                CPUGano = false;
                break;
            }
        }
        if(CPUGano){              
            this.visahor.labelFiguraAhorcadoCPU.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagenesPanelAhorcado/FiguraAhorcadoGanador.png"))); 
            JOptionPane.showMessageDialog(null,"¡La CPU Ha Ganado! con"+"\n"+(this.intentosCPU)+" error(es).", "¡La CPU es más inteligente que tú!", 0 , new javax.swing.ImageIcon(getClass().getResource("/ImagenesFinales/CopaGanador.png")));   
            int recompensa = Integer.parseInt(this.visahor.textDinero.getText());
            recompensa -= 2000;
            visahor.textDinero.setText(Integer.toString(recompensa));
            this.visahor.desactivarBotones();
            this.jugar = false; 
        }       
    } 

    private void verificarGanadorUsuario(){
        boolean usuarioGano = false;
        for(int i=0; i<=this.palabraAAdivinarUsuario.length-1;i++){
            if(this.palabraUsuario[i] == this.palabraAAdivinarUsuario[i]){            
                usuarioGano = true;
            }else{
                usuarioGano = false;
                obtenerLetra();
                break;
            }
        }
        if(usuarioGano){              
            this.visahor.labelFiguraAhorcadoUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagenesPanelAhorcado/FiguraAhorcadoGanador.png"))); 
            JOptionPane.showMessageDialog(null,"¡Has Ganado! con"+"\n"+(this.intentosCPU)+" error(es).", "¡Eres más Inteligente que la CPU!", 0 , new javax.swing.ImageIcon(getClass().getResource("/ImagenesFinales/CopaGanador.png")));
            int recompensa = Integer.parseInt(this.visahor.textDinero.getText());
            recompensa += 5000;
            visahor.textDinero.setText(Integer.toString(recompensa));
            this.visahor.desactivarBotones();
            this.jugar = false; 
        }       
    } 
    public void mostrarVista(){
        visahor.setLocationRelativeTo(null);        
        visahor.setVisible(true);
    }
    
    public void agregarDinero(int dinero, int idJgd) throws SQLException {
        ModeloUsuario modus = new ModeloUsuario();
        int Dinero = Integer.parseInt(modus.obtenerDinero(idJgd));
        int nvDnro = Dinero + dinero;
        modus.descontarDinero(idJgd, nvDnro);
    }

@Override
    public void actionPerformed(ActionEvent ae) {
        if (this.visahor.botonA == ae.getSource()){
            revisarLetraUsuario('A');
            this.visahor.botonA.setEnabled(false);
        }else if (this.visahor.botonB == ae.getSource()){
            revisarLetraUsuario('B');
            this.visahor.botonB.setEnabled(false);
        }else if (this.visahor.botonC == ae.getSource()){        
            revisarLetraUsuario('C');
            this.visahor.botonC.setEnabled(false);
        }else if (this.visahor.botonD == ae.getSource()){        
            revisarLetraUsuario('D');
            this.visahor.botonD.setEnabled(false);
        }else if (this.visahor.botonE == ae.getSource()){
            revisarLetraUsuario('E');
            this.visahor.botonE.setEnabled(false);
        }else if (this.visahor.botonF == ae.getSource()){
            revisarLetraUsuario('F');
            this.visahor.botonF.setEnabled(false);
        }else if (this.visahor.botonG == ae.getSource()){        
            revisarLetraUsuario('G');
            this.visahor.botonG.setEnabled(false);
        }else if (this.visahor.botonH == ae.getSource()){  
            revisarLetraUsuario('H');
            this.visahor.botonH.setEnabled(false);
        }else if (this.visahor.botonI == ae.getSource()){
            revisarLetraUsuario('I');
            this.visahor.botonI.setEnabled(false);
        }else if (this.visahor.botonJ == ae.getSource()){
            revisarLetraUsuario('J');
            this.visahor.botonJ.setEnabled(false);
        }else if (this.visahor.botonK == ae.getSource()){        
            revisarLetraUsuario('K');
            this.visahor.botonK.setEnabled(false);
        }else if (this.visahor.botonL == ae.getSource()){              
            revisarLetraUsuario('L');
            this.visahor.botonL.setEnabled(false);
        }else if (this.visahor.botonM == ae.getSource()){
            revisarLetraUsuario('M');
            this.visahor.botonM.setEnabled(false);
        }else if (this.visahor.botonN == ae.getSource()){
            revisarLetraUsuario('N');
            this.visahor.botonN.setEnabled(false);
        }else if (this.visahor.botonÑ == ae.getSource()){        
            revisarLetraUsuario('Ñ');
            this.visahor.botonÑ.setEnabled(false);
        }else if (this.visahor.botonO == ae.getSource()){ 
            revisarLetraUsuario('O');
            this.visahor.botonO.setEnabled(false);
        }else if (this.visahor.botonP == ae.getSource()){
            revisarLetraUsuario('P');
            this.visahor.botonP.setEnabled(false);
        }else if (this.visahor.botonQ == ae.getSource()){
            revisarLetraUsuario('Q');
            this.visahor.botonQ.setEnabled(false);
        }else if (this.visahor.botonR == ae.getSource()){   
            revisarLetraUsuario('R');
            this.visahor.botonR.setEnabled(false);
        }else if (this.visahor.botonS == ae.getSource()){              
            revisarLetraUsuario('S');
            this.visahor.botonS.setEnabled(false);
        }else if (this.visahor.botonT == ae.getSource()){
            revisarLetraUsuario('T');
            this.visahor.botonT.setEnabled(false);
        }else if (this.visahor.botonU == ae.getSource()){
            revisarLetraUsuario('U');
            this.visahor.botonU.setEnabled(false);
        }else if (this.visahor.botonV == ae.getSource()){    
            revisarLetraUsuario('V');
            this.visahor.botonV.setEnabled(false);
        }else if (this.visahor.botonW == ae.getSource()){              
            revisarLetraUsuario('W');
            this.visahor.botonW.setEnabled(false);
        }else if (this.visahor.botonX == ae.getSource()){
            revisarLetraUsuario('X');
            this.visahor.botonX.setEnabled(false);
        }else if (this.visahor.botonY == ae.getSource()){
            revisarLetraUsuario('Y');
            this.visahor.botonY.setEnabled(false);
        }else if (this.visahor.botonZ == ae.getSource()){                    
            revisarLetraUsuario('Z'); 
            this.visahor.botonZ.setEnabled(false);
        }else if (this.visahor.botonSalir == ae.getSource()){ 
            this.visahor.setVisible(false);
        } 
    }
}
