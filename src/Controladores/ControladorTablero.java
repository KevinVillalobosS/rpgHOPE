
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;
import Modelos.ModeloAsignatura;
import Modelos.ModeloEAO;
import Modelos.ModeloItem;
import Modelos.ModeloMallaCurricular;
import Modelos.ModeloUsuario;
import Modelos.Personaje;
import Modelos.Tablero;
import Vistas.SubVistaAttCorDst;
import Vistas.VistaLogInFrame;
import Vistas.VistaTableroDialog;
import java.awt.Color;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author kevin
 */
public class ControladorTablero implements ActionListener{
    boolean transicion ;
    boolean transicion1;
    boolean invalido;
    int recX;
    int contK = 0;

    ArrayList<Integer> posX = new ArrayList<>();
    ArrayList<Integer> posY = new ArrayList<>();
    ArrayList<Integer> cerrada = new ArrayList<>();
    ArrayList<Integer> listFinX = new ArrayList<>();
    ArrayList<Integer> listFinY = new ArrayList<>();

    int recY, dndX, dndY, dndIX, dndIY;
   
    //Timer tiempo = new Timer();
    public int idPj, idJgdr, contador, contadorA;
    public VistaLogInFrame vislog;
    public ModeloAsignatura ramo;
    public VistaTableroDialog vistab;
    public ModeloMallaCurricular modmalla;
   // public VistaTablero vistab;
    ArrayList<Integer> movFinX;
    ArrayList<Integer> movFinY;
    private ArrayList<Integer> idsAliados;
    private ArrayList<Integer> idsEnemmigos;
    public Tablero tab;
    private int idRamo;
    public Personaje personaje;
    public Personaje jugador;
    private ArrayList<Integer> velocidades;
    private ArrayList<Integer> movimientos;
    private ArrayList<Integer> vidas;
    private ArrayList<Integer> turnos;
    public int contadorTurno;
    private boolean usarItem;
    //------------------//
    public Tablero[][] tablero ;
    //ControladorTablero conTab;
    private int posicionX;
    private int posicionY;
    private Timer tiempo = new Timer();
    private int iterador = 0;
    private int vida, defensa, ataque, movimiento;
    private String quienJuega;
    private ImageIcon icono = null;
    private ImageIcon foto = null;
    private int cont = 1;
    private int contador5 = 0;
    private Random random = new Random();
    

    
    
    public ControladorTablero(ArrayList<Integer> aliados, int idPj, int idRamo){
        this.vislog = new VistaLogInFrame();
        this.modmalla = new ModeloMallaCurricular();
        this.ramo = new ModeloAsignatura();
        this.idPj = idPj;
        this.vistab  = new VistaTableroDialog(this.vislog,true,this);
        this.idRamo = idRamo;
        this.idsAliados = aliados;
        this.idsEnemmigos = new ArrayList<>();
        this.personaje = new Personaje();
        this.tab = new Tablero();
        turnos = new ArrayList<>();
        //----------------//
        this.crearTablero(idRamo);
        
        vistab.agregarListeners(this);
        vistab.jProgressBar1.setMinimum(0);
        vistab.jProgressBar1.setMaximum(100);
        vistab.jProgressBar2.setMinimum(0);
        vistab.jProgressBar2.setMaximum(100);
        vistab.jProgressBar3.setMinimum(0);
        vistab.jProgressBar3.setMaximum(100);
        vistab.jProgressBar1.setStringPainted(true);
        vistab.jProgressBar2.setStringPainted(true);
        vistab.jProgressBar3.setStringPainted(true);
        vistab.jButton12.setVisible(false);
        vistab.jButton13.setVisible(false);
        this.usarItem = false;
        vistab.jButton14.setVisible(false);
        vistab.jButton15.setVisible(false);
        vistab.jComboBox1.setVisible(false);
        vistab.jComboBox2.setVisible(false);
        vistab.panel1.repaint();
        //////----------------------//
        
        this.armarBatalla(this.idPj);
        cambiarEstadoBotones();
        //----------------------//
        
        
       
        
        
    }
    
    private void crearMBI(){
        //asignaturas del modulo basico: 3:1 tierra agua
        //468 tierras, 156 aguas
        int numeroAzar;
        ImageIcon iconoTierra = new ImageIcon(getClass().getResource("/ImagenesTablero/Pasto0.png"));
        ImageIcon iconoAgua = new ImageIcon(this.getClass().getResource("/ImagenesTablero/Agua1.png"));
        tablero = new Tablero[25][25];
        ImageIcon boton = new ImageIcon(this.getClass().getResource("/Imagenes2/arriba.png"));
        vistab.jButton3.setIcon(boton);
        for (int i=0;i<25;i++){
            for (int j=0;j<25;j++){
                numeroAzar = (int) (Math.random()*(101));
                tablero[i][j] = new Tablero();
                tablero[i][j].A.setBounds((i*40)+5,(j*20)+5,40,20);
                if (numeroAzar<40){
                    tablero[i][j].A.setIcon(iconoAgua);
                    tablero[i][j].B = 1;
                }else{
                    tablero[i][j].A.setIcon(iconoTierra);
                    tablero[i][j].B = 0;
                }

                vistab.jPanel2.add(tablero[i][j].A);
            }
            
                
        }
        zonasSeguras();
        alturas("Pasto");
        revisionDeAltura("Pasto");
        revisionDeMapa(1);
                

        vistab.jPanel2.revalidate();
        vistab.jPanel2.repaint();
        
    }
    private void crearEconomia(){
        //asignaturas de economia: 4:2:1  tierra rio montaña
        //357 tierra, 178 aguas, 89 montañas
        //es el mismo algoritmo del modulo basico, pero al finalizar 
        //se debe aniadir montania. Esta no tiene grandes restricciones
        int numeroAzar;
        ImageIcon iconoTierra = new ImageIcon(getClass().getResource("/ImagenesTablero/Pasto0.png"));
        ImageIcon iconoAgua = new ImageIcon(this.getClass().getResource("/ImagenesTablero/Agua1.png"));
        ImageIcon botron = new ImageIcon(this.getClass().getResource("/Imagenes2/arriba.png"));
        tablero = new Tablero[25][25];
        vistab.jButton3.setIcon(botron);
        for (int i=0;i<25;i++){
            for (int j=0;j<25;j++){
                numeroAzar = (int) (random.nextFloat()*100);
                tablero[i][j] = new Tablero();
                tablero[i][j].A.setBounds((i*40)+5,(j*20)+5,40,20);
                
                //agua
               if(numeroAzar<37){
                    //agua
                    tablero[i][j].A.setIcon(iconoAgua);
                    tablero[i][j].B = 1;
                }else{
                    tablero[i][j].A.setIcon(iconoTierra);
                    tablero[i][j].B = 0;
                }

                vistab.jPanel2.add(tablero[i][j].A);
            }
            
                
        }
        zonasSeguras();
        alturas("Pasto");
        revisionDeAltura("Pasto");
        revisionDeMapa(1);
        this.aniadirMontanas(5);

        
        //alturas("Montaña");
        //revisionDeAltura("Montaña");
        
        vistab.jPanel2.revalidate();
        vistab.jPanel2.repaint();
        
    }
    
    private void crearEspecialidad(){
        //asignaturas de  especialidad: 4:2:1:3 tierra rio montaña bosque
        //                             248:125:62:187
        int numeroAzar;
        ImageIcon iconoTierra = new ImageIcon(getClass().getResource("/ImagenesTablero/Pasto0.png"));
        ImageIcon iconoAgua = new ImageIcon(this.getClass().getResource("/ImagenesTablero/Agua1.png"));
        ImageIcon iconoBosque = new ImageIcon(this.getClass().getResource("/ImagenesTablero/BosqueBase.png"));
        tablero = new Tablero[25][25];
        ImageIcon botron = new ImageIcon(this.getClass().getResource("/Imagenes2/arriba.png"));
        vistab.jButton3.setIcon(botron);
        
                  
        for (int i=0;i<25;i++){
            for (int j=0;j<25;j++){
                numeroAzar = (int) (Math.random()*101);
                tablero[i][j] = new Tablero();
                tablero[i][j].A.setBounds((i*40)+5,(j*20)+5,40,20);
                if (numeroAzar<25){
                    tablero[i][j].A.setIcon(iconoAgua);
                    tablero[i][j].B = 1;
                }else if(numeroAzar<55){
                    tablero[i][j].A.setIcon(iconoBosque);
                    tablero[i][j].B = 3;
                }else{
                    tablero[i][j].A.setIcon(iconoTierra);
                    tablero[i][j].B = 0;
                }

                vistab.jPanel2.add(tablero[i][j].A);
            }
            
                
        }
        

        alturas("Pasto");
        revisionDeAltura("Pasto");
        zonasSeguras();
        contador();
        revisionDeMapa(3);
        contador();
        revisionDeMapa(1);
        contador();
        this.aniadirMontanas(8);
        vistab.jPanel2.revalidate();
        vistab.jPanel2.repaint();
        
    }
    
    
  
    
    public void mostrarTablero(){
        vistab.setLocationRelativeTo(null);
        vistab.setVisible(true);
    }
    
    public void bloquearBotones() {
        vistab.jButton1.setEnabled(false);
        vistab.jButton2.setEnabled(false);
        vistab.jButton3.setEnabled(false);
        vistab.jButton4.setEnabled(false);
        vistab.jButton5.setEnabled(false);
        vistab.jButton6.setEnabled(false);
        vistab.jButton7.setEnabled(false);
        vistab.jButton8.setEnabled(false);
        vistab.jButton9.setEnabled(false);
        vistab.jButton10.setEnabled(false);
        vistab.jButton11.setEnabled(false);
        vistab.jButton12.setEnabled(false);
        vistab.jButton13.setEnabled(false);
        vistab.jComboBox1.setEnabled(false);
        vistab.jComboBox2.setEnabled(false);
        
    }
    
    

    public void HabilitarBotones() {
        vistab.jButton1.setEnabled(true);
        vistab.jButton2.setEnabled(true);
        vistab.jButton3.setEnabled(true);
        vistab.jButton4.setEnabled(true);
        vistab.jButton5.setEnabled(true);
        vistab.jButton6.setEnabled(true);
        vistab.jButton7.setEnabled(true);
        vistab.jButton8.setEnabled(true);
        vistab.jButton9.setEnabled(true);
        vistab.jButton10.setEnabled(true);
        vistab.jButton11.setEnabled(true);
        vistab.jButton12.setEnabled(true);
        vistab.jButton13.setEnabled(true);
        vistab.jComboBox1.setEnabled(true);
        vistab.jComboBox2.setEnabled(true);
       
        
        
    }
    
    
    

    
    
    //Funcion encargada de trazar el mejor camino hacia el jugador con menos vida
    //Recibe como parametros MOMENTANEOS la coordenada de cada elemento mencionado
    //Una vez implementado, se buscará cambiar a que reciba los strings[] de cada jugador
    
        
    public void buscar(int x, int y){
        for (int hb=x-1;hb<=x+1;hb++){
            for (int cb=y-1;cb<=y+1;cb++){
                try{
                    if (tablero[hb][cb].B != 1){
                        if (Math.abs(tablero[hb][cb].C - tablero[x][y].C) <= 2){
                            if (hb != x && cb != y){
                                if (listFinX.isEmpty()){
                                    posX.add(hb);
                                    posY.add(cb);
                                }else{
                                    if (listFinX.contains(hb) && listFinY.contains(cb)){
                                        if (!(listFinX.indexOf(hb) == listFinY.indexOf(cb))){
                                            posX.add(hb);
                                            posY.add(cb);
                                        }
                                    }else{
                                        posX.add(hb);
                                        posY.add(cb);
                                    }

                                }
                            }
                        }
                    }//end if
                }catch (ArrayIndexOutOfBoundsException e){
                        //do nothing;
                }
            }//end for
        }//end main for
    }
    public void decidir(int x, int y, int xFn, int yFn, String quienJuega){
        posX.clear();
        posY.clear();
        
        //For creado unicamente para encontrar los posibles movimientos.
        this.buscar(x, y);
        
        int helperSH = 0;
        //Se asignan valores por defecto para ser comparados a lo largo
        //de una iteracion
        //definiciones varias
        
        if (posX.isEmpty()){
            JOptionPane.showMessageDialog(vislog, "No pasa nada","Sin movimiento", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        int mejorX = posX.get(0);
        int mejorY = posY.get(0);
        int valor = Math.abs(xFn-mejorX) +  Math.abs(yFn-mejorY);
        int valorPivt = 0;
        //se realiza ciclo donde se decidirá que movimiento utilizar
        if (posX.size() == 1){
            //implicaria que solo se puede mover a un lugar nuevo y a la posicion desde donde viene
            //en tal caso, siempre se moverá al nuevo lugar
                mejorX = posX.get(0);
                mejorY = posY.get(0);
            
        }else{
            while (helperSH < posX.size()){
                valorPivt = Math.abs(xFn-posX.get(helperSH)) +  Math.abs(yFn-posY.get(helperSH));
                if (valorPivt <= valor ){
                    mejorX = posX.get(helperSH);
                    mejorY = posY.get(helperSH);
                    valor = Math.abs(mejorX-xFn) +  Math.abs(mejorY-yFn);
                }
                helperSH++;

            
             }
        }
        this.recX = mejorX;
        this.recY = mejorY;
        this.listFinX.add(recX);
        this.listFinY.add(recY);
        //vistab.Tiro(mejorX, mejorY, quienJuega);
        
        
    }
            
    
    
    
    
    public boolean armarCamino(int i, int j, String quienJuega){
        invalido = false;
        recX = this.jugador.getPosicionX();
        recY = this.jugador.getPosicionY();
        
        //Se realizará el proceso 20 veces maximo, esto es, buscará 
        //20 movimientos, y realizará los N primeros dependiendo de la cantidad
        //de movimiento de cada personaje
        for (int h=0;h<25;h++){
            //Primero buscará acercarse en el eje X
            if (recX < i){
                //Si la posicion en la que está es menor a donde debe llegar
                //Se aumenta la posicion actual y se verifica si es que se puede mover a la
                //nueva posicion
                recX++;
                if (tablero[recX][recY].B==1){ 
                    //en caso de no poderse mover, activará un
                    //booleano para posteriormente invocar otro algoritmo
                    //para decidir
                    invalido=true;
                    recX--;
                }else if(Math.abs(tablero[recX-1][recY].C - tablero[recX][recY].C) > 2){
                    invalido=true;
                    recX--;
                }
                
            }else if(recX>i){
                recX--;
                if (tablero[recX][recY].B==1){
                    invalido=true;
                    recX++;
                }else if(Math.abs(tablero[recX+1][recY].C - tablero[recX][recY].C) > 2){
                    invalido=true;
                    recX++;
                }
            }
            if (invalido){
                //try
                decidir(recX,recY,i,j, quienJuega);
                if (posX.isEmpty()){
                    return false;
                }
                invalido = false;

            }else{
                this.listFinX.add(recX);
                this.listFinY.add(recY);
                //vistab.Tiro(recX,recY,quienJuega);
            }
            if (recX == i  && recY == j){
                return true;
            }
            
            //se verifica inmeditamente la posicion J.
            
            if (recY < j){
                recY++;
                if (tablero[recX][recY].B==1){ 
                    invalido=true;
                    recY--;
                }else if(Math.abs(tablero[recX][recY-1].C - tablero[recX][recY].C) > 2){
                    invalido=true;
                    recX--;
                }
            }else if(recY > j){
                recY--;
                if (tablero[recX][recY].B==1){ 
                    invalido=true;
                    recY++;
                }else if(Math.abs(tablero[recX][recY+1].C - tablero[recX][recY].C) > 2){
                    invalido=true;
                    recX++;
                }
            }
            
            if (invalido){
                decidir(recX,recY,i,j, quienJuega);
                if (posX.isEmpty()){
                    return false;
                }
                invalido = false;
           
            }else{
                this.listFinX.add(recX);
                this.listFinY.add(recY);
                //vistab.Tiro(recX,recY,quienJuega);
                              
            }
            if (recY == j && recX == j){
                return true;
            }

        }
                
        return true;
        
        
    }
    
    
     
    
   // public void pause() {
   //     this.tiempo.cancel();
   // }

   // public void resume() {
   //     this.tiempo = new Timer();
       // this.tiempo.schedule( caminarY, 0, 1000 );
    //}
        
 
   
    
    
    
    
    
    
    
    public void batalla(ArrayList<Integer> lista, int contador){
        
        this.idJgdr = lista.get(contador);
        String[] quienJuega = personaje.consultar(idJgdr).split("CORTAR");
        jugador = new Personaje();
        jugador.setId(idJgdr);
        jugador.setVida(Integer.parseInt(quienJuega[4]));
        jugador.setNick(quienJuega[1]);
        jugador.setMovimiento(Integer.parseInt(quienJuega[7]));
        jugador.setImgTablero(quienJuega[11]);
        jugador.setAtaque(Integer.parseInt(quienJuega[6]));
        jugador.setDefensa(Integer.parseInt(quienJuega[5]));
        jugador.setImagen(quienJuega[9]);
        jugador.setPosicionX(Integer.parseInt(quienJuega[13]));
        jugador.setPosicionY(Integer.parseInt(quienJuega[14]));
        JOptionPane.showMessageDialog(vistab, "Turno de: "+quienJuega[1], "Informacion",
                JOptionPane.INFORMATION_MESSAGE);
        escritura(jugador);
        //En caso de que el turno sea de la CPU, se decide por quien ir;
        if (this.idsEnemmigos.contains(idJgdr)){
            vistab.jButton17.setVisible(false);
            this.listFinX.clear();
            this.listFinY.clear();
            this.bloquearBotones();
            vistab.jTextArea1.append("Turno del personaje enemigo: "+quienJuega[1]+"\n" );
            this.juegoCPU();
            
            //busquedaCPU(jugador.getPosicionX(),jugador.getPosicionY(),8,24);
            
        }else{
            //tiempo.cancel();
           // this.pause();
           this.HabilitarBotones();
           JOptionPane.showMessageDialog(vislog, "Turno del jugador", "Informacion", JOptionPane.INFORMATION_MESSAGE);
         vistab.jTextArea1.append("Turno del personaje aliado: "+quienJuega[1]+"\n");
            vistab.jButton17.setVisible(true);
         
        }
        this.contadorTurno=0;
        
        
    }
    public void batalla(int idJgd){
        String[] quienJuega = personaje.consultar(idJgd).split("CORTAR");
        jugador = new Personaje();
        jugador.setId(idJgd);
        jugador.setVida(Integer.parseInt(quienJuega[4]));
        jugador.setNick(quienJuega[1]);
        jugador.setMovimiento(Integer.parseInt(quienJuega[7]));
        jugador.setImgTablero(quienJuega[11]);
        jugador.setAtaque(Integer.parseInt(quienJuega[6]));
        jugador.setDefensa(Integer.parseInt(quienJuega[5]));
        jugador.setImagen(quienJuega[9]);
        jugador.setPosicionX(Integer.parseInt(quienJuega[13]));
        jugador.setPosicionY(Integer.parseInt(quienJuega[14]));
       /* if (this.idsEnemmigos.contains(idJgdr)){
            String[] buscVida = personaje.consultar(this.idsAliados.get(0)).split("CORTAR");
            int vida = 0;
            //se define una vida pivote para comparar las demas
            int vidaPivote = Integer.parseInt(buscVida[4]);
            int idPivote = Integer.parseInt(buscVida[0]);
            for (int id: this.idsAliados){
                 buscVida = personaje.consultar(idJgdr).split("CORTAR");
                 vida = Integer.parseInt(buscVida[4]);
                 if (vida<vidaPivote){
                    vidaPivote = vida;
                    idPivote = id;
                 }
                
            }
            String[] perseguir = personaje.consultar(idPivote).split("CORTAR");
            this.transicion(Integer.parseInt(perseguir[13]), Integer.parseInt(perseguir[14]), jugador.getNick());
        }
        */
            
        escritura(jugador);
        
        
    }
    private void comenzarBatalla(ArrayList<Integer> lista) {
        for (int id:lista){
            String[] consulta = personaje.consultar(id).split("CORTAR");
            tab.aniadirVida(Integer.parseInt(consulta[4]));
            tab.aniadirMov(Integer.parseInt(consulta[7]));
        }
        this.contadorA = 0;
        batalla(lista,contadorA);
    }
    
    public void ponerPersonajes(ArrayList<Integer> lista){
        //Se asignan posiciones por defecto desde donde comenzará la batalla
        
        for (int i=0;i<this.idsAliados.size();i++){
           personaje.modificarPosicion((i*5+2),24,0,idsAliados.get(i));
        }for (int pj:idsAliados){
            String[] consulta = personaje.consultar(pj).split("CORTAR");
            mostrarPersonaje(consulta);
        }
        for (int i=0;i<this.idsEnemmigos.size();i++){
           personaje.modificarPosicion((i*2),0,0,idsEnemmigos.get(i));
        }for (int pj:this.idsEnemmigos){
            String[] consulta = personaje.consultar(pj).split("CORTAR");
            mostrarPersonaje(consulta);
        }
        
        //se da comienzo a la batalla, siguiendo el orden establecido
    
        comenzarBatalla(lista);
        
        
    }
    
    
    public void reponerVida(String[] datAtac) {
            int helper = this.turnos.indexOf(Integer.parseInt(datAtac[0]));
            personaje.modificarVida(vidas.get(helper), Integer.parseInt(datAtac[0]));
            personaje.modificarMovimiento(movimientos.get(this.contadorA), this.idJgdr);

        
            int x = Integer.parseInt(datAtac[13]);
            int y = Integer.parseInt(datAtac[14]);
            borrar(x,y);
            this.turnos.remove(helper);
            this.vidas.remove(helper);
            this.movimientos.remove(helper);
   
        
        
        
    }
    
    private void prepararBatalla(ArrayList<Integer> listaPjs) {
        velocidades = new ArrayList<>();
        movimientos = new ArrayList<>();
        vidas = new ArrayList<>();
        ArrayList<Integer> movimientos2 = new ArrayList<>();
        ArrayList<Integer> vida1 = new ArrayList<>();
        //Se obtiene el valor de velocidad y movimiento de cada uno de los personajes que jugarán
        for (int id:listaPjs){
            String[] datosPj = this.personaje.consultar(id).split("CORTAR");
            int velocidad = Integer.parseInt(datosPj[25]);
            int movimiento = Integer.parseInt(datosPj[7]);
            int vida = Integer.parseInt(datosPj[4]);
            velocidades.add(velocidad);
            movimientos2.add(movimiento);
            vida1.add(vida);
        }
       //Una vez obtenidos todos los valores, se procede a determinar el orden de los turnos
       ArrayList<Integer> velCopy = (ArrayList) velocidades.clone();  
       for (int i=0;i<velocidades.size();i++){
           int velMax = Collections.max(velCopy);
           int posicion = velCopy.indexOf(velMax);
           turnos.add(listaPjs.get(posicion));
           movimientos.add(movimientos2.get(posicion));
           vidas.add(vida1.get(posicion));
           velCopy.remove(posicion);
           listaPjs.remove(posicion);
           movimientos2.remove(posicion);
           vida1.remove(posicion);
       
       }
       this.ponerPersonajes(turnos);
       //Una vez que fueron ordenados segun la velocidad, se re-ordenan 
       //los personajes empatados segun su movimiento
       
       
       
    }
    
    
    public void armarBatalla(int idPj){
        ArrayList<Integer> listaPjs = new ArrayList <>();
        ArrayList<Integer> todsAliados = new ArrayList <>();
        ModeloUsuario modus = new ModeloUsuario();
        try {
            todsAliados = modus.obtenerAliados(this.idJgdr);
        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null,"Error realizando la solicitud del usuario","Error",
                                JOptionPane.ERROR_MESSAGE); 
        }
        //Se aniaden 5 personajes de prueba. Se espera que llegue una lista de arrayList desde
        //la vista previa al tablero
        //Por tanto esto es solo momentaneo
        for (int id:this.idsAliados){
            listaPjs.add(id);
        }
        int j;
        j = this.ramo.getCantidadEnemigos();
        
        for (int i=0;i<j;i++){
            int idRnd = (int) (Math.random()*(461-1)+1);
            if (todsAliados.contains(idRnd)){
                i--;
            }else{
                this.idsEnemmigos.add(idRnd);
                listaPjs.add(idRnd);
            }
            
        }
        
        this.prepararBatalla(listaPjs);
        
        
    }
    
    //Funcion encargada de verificar ataques a corta distancia.
    //recibe como parametros la ubicacion de un personaje
    //retorna las IDS de los personajes que estén dentro del radio de ataque
    //radio de ataque a corta distancia: 2 cuadros de distancia
    public void buscarCortaDist(int x, int y, int z){
        vistab.jComboBox1.removeAllItems();
        for (int hb=x-2;hb<=x+2;hb++){
            for (int  cb=y-2;cb<=y+2;cb++){
                try{
                    if (tablero[hb][cb].B != this.jugador.getId()){
                    
                        if ((Math.abs(tablero[hb][cb].C - tablero[x][y].C ) > 2)){
                            continue;
                        }
                        if (tablero[hb][cb].B>4){
                            this.agregarOpcion(tablero[hb][cb].B);

                        }//end if
                    }
                }catch (ArrayIndexOutOfBoundsException e){
                        //
                    
                }
            }//end for
        }//end  main for
    }

    //Funcion encargada de verificar ataques a larga distancia.
    //recibe como parametros la ubicacion de un personaje
    //retorna las IDS de los personajes que estén dentro del radio de ataque
    //radio de ataque a corta distancia: 2 cuadros de distancia
    public void buscarLargDist(int x, int y, int z){
        vistab.jComboBox1.removeAllItems();
        vistab.jComboBox2.removeAllItems();
        for (int hb=x-8;hb<=x+8;hb++){
                        for (int  cb=y-8;cb<=y+8;cb++){
                            try{
                                if (tablero[hb][cb].B != this.jugador.getId()){

                                        if (tablero[hb][cb].B>1){
                                            if ((Math.abs(tablero[hb][cb].C - tablero[x][y].C ) > 2)){
                                                continue;
                                            }
                                            if (Math.abs(hb-x) >= 5 || Math.abs(cb-y) >= 5){
                                                this.agregarOpcion(tablero[hb][cb].B);
                                            }

                                        }//end if
                                }
                             }catch (ArrayIndexOutOfBoundsException e){
                                    //
                                
                            }
                        }//end for
                    }//end  main for
    }
    
    
    
    
    
    public Integer movimiento(int movimiento){
        movimiento = movimiento -1;
        return movimiento;
    }
    public Integer arriba(int y){
        y=y-1;
        return y;        
    }public Integer abajo(int y){
        y=y+1;
        return y;        
    }public Integer derecha(int x){
        x+=1;
        return x;        
    }public Integer izquierda(int x){
        x=x-1;
        return x;    
    }

    //funcion encargada de restar los pasos que el personaje realiza
    //recibe como parametro los pasos que posee el personaje, así como
    //su actual posicion X e Y.
    public boolean verificarMovimiento(int movimiento, int x, int y) {
        String[] C = personaje.consultar(this.idJgdr).split("CORTAR");
        movimiento = Integer.parseInt(C[7]) - 1 ;
        if (movimiento > 0){
            personaje.modificarMovimiento(movimiento,this.idJgdr);
            personaje.modificarPosicion(x, y, 0, this.idJgdr);
            batalla(idJgdr);
            return true;
        }else if (movimiento <= 0){
            personaje.modificarMovimiento(movimiento,this.idJgdr);
            personaje.modificarPosicion(x, y, 0, this.idJgdr);
            batalla(idJgdr);
            JOptionPane.showMessageDialog(vistab, "Este personaje ya no puede moverse", "Error",
                     JOptionPane.ERROR_MESSAGE);
            personaje.modificarMovimiento(this.movimientos.get(this.contadorA), this.idJgdr);
            //this.pasarTurno();
            //this.verificarTurno();
            return false;
        }
        
        return false;
    }

    public void ataqueLargo(int idPj, int aAtacar) {
        String[] consultAtacante = personaje.consultar(idPj).split("CORTAR");
        String[] consultAtacado = personaje.consultar(aAtacar).split("CORTAR");
        int life = Integer.parseInt(consultAtacado[4]);
        int defense = Integer.parseInt(consultAtacado[5]);
        int daño = Integer.parseInt(consultAtacante[6]);
        daño = daño - defense;
        if (daño<0){
           JOptionPane.showMessageDialog(null, "El personaje "+consultAtacado[1]+" se defendió completamente del ataque rival.",
                    "Defensa correcta", JOptionPane.INFORMATION_MESSAGE); 
           return;
        }
        life = life - daño;
        if (life <= 0){
        //se murio
            JOptionPane.showMessageDialog(null, "El personaje "+consultAtacado[1]+" Ha muerto",
                    "No lloren por el...", JOptionPane.INFORMATION_MESSAGE);
            vistab.jTextArea1.append("El personaje "+consultAtacado[1]+" Ha muerto"+"\n");
            this.agregarExp(idPj,500);
            this.reponerVida(consultAtacado);
        }else{
            personaje.modificarVida(life, Integer.parseInt(consultAtacado[0]));
            JOptionPane.showMessageDialog(vistab, "El personaje "+consultAtacante[1]+" ha atacado a "+
                    consultAtacado[1]+". La nueva vida del personaje atacado es: "+life, "Exito",  JOptionPane.INFORMATION_MESSAGE);
            vistab.jTextArea1.append("El personaje "+consultAtacante[1]+" ha atacado a "+
                    consultAtacado[1]+". La nueva vida del personaje atacado es: "+life+"\n");
            //this.batalla(Integer.parseInt(consultAtacado[0]));
            this.pasarTurno();
        }
        vistab.jButton10.setEnabled(false);
        //his.verificarTurno();
        
    }
    
    public void pasarTurno(){
        int cantAliados = 0;
        int cantEnem = 0;
        for (int pj: this.turnos){
            if (this.idsAliados.contains(pj)){
                cantAliados++;
            }else if(this.idsEnemmigos.contains(pj)){
                cantEnem++;
            }
        }
        if (cantAliados==0){
            JOptionPane.showMessageDialog(vislog, "REPROBADO! Tu y tus aliados están muertos",
                    "Derrota", JOptionPane.ERROR_MESSAGE);
            this.tiempo.cancel();
            this.reponerDatos();
            vistab.cerrarVista();
            
            SubControladorPostBatalla subConPost = new 
                                    SubControladorPostBatalla(this.idsEnemmigos,this.idsAliados, false);
        }else if(cantEnem == 0){
            JOptionPane.showMessageDialog(vislog, "APROBADO! Felicidades, destruiste a todos los enemigos",
                    "Victoria", JOptionPane.INFORMATION_MESSAGE); 
            this.aprobarRamo();
            this.tiempo.cancel();
            this.reponerDatos();
             vistab.cerrarVista();
            SubControladorPostBatalla subConPost = new 
                                    SubControladorPostBatalla(this.idsEnemmigos,this.idsAliados, true);
        }else{
            personaje.modificarMovimiento(this.movimientos.get(this.contadorA), this.idJgdr);
            this.contadorA = (this.contadorA < this.turnos.size()-1) ? this.contadorA + 1:0;
            
            batalla(this.turnos,this.contadorA);
        }
    }
    
    public void ataque(int idPj, int aAtacar) {
        if (aAtacar>1){
            String[] consultAtacante = personaje.consultar(idPj).split("CORTAR");
            String[] consultAtacado = personaje.consultar(aAtacar).split("CORTAR");

            SubControladorAtaque subat = new SubControladorAtaque(consultAtacante, consultAtacado, this);
            subat.mostrarDialog();
        }else{
            JOptionPane.showMessageDialog(vistab, "Nada seleccionado", "Error",  JOptionPane.ERROR_MESSAGE);
        }
    }
    private void agregarOpcion(int B) {
        //Se verifica si la ID del actual jugador pertenece a los enemigos
        //de ser así, se almacenan los datos a nivel de codigo, y no son mostrados
        if (this.idsEnemmigos.contains(jugador.getId())){
            if (this.idsAliados.contains(B)){
                this.cerrada.add(B);
                
            }
            //está jugando la cpu
        }else{
            //está jugando el jugador, y se muestran en la comboBox
            String[] consulta = personaje.consultar(B).split("CORTAR");
            vistab.jComboBox1.addItem(consulta[1]);
            vistab.jComboBox2.addItem(Integer.toString(B));
        }
        
    }

  

    /*public void verificarTurno() {
        this.contadorTurno = contadorTurno + 1;
        if (contadorTurno==2){
        //con esto se pasa de turno:
            this.contador = (this.contador==4) ? 0:this.contador + 1;
            batalla(this.turnos,this.contador);
        }
        
    }*/

    public boolean seMovio(int movReal) {
        int movInc = this.movimientos.get(contadorA);
        if (movInc != movReal){
            //verificarTurno();
            return true;
            //ya se movió, y por tanto se puede termina
        }else{
            return false;
            //no se movió
        }
    }
    
    
    private void procesarLista(){
        int contE=0;
        int pivoX = 0;
        int pivoY = 0;
        movFinX = new ArrayList<>();
        movFinY = new ArrayList<>();
        while (contE < listFinX.size()){
            pivoX = this.listFinX.get(contE);
            pivoY = this.listFinY.get(contE);
            int iter = contE+1;
            boolean agreg = true;
            while (iter<this.listFinX.size()){
                int testX = this.listFinX.get(iter);
                int testY = this.listFinY.get(iter);
                if (testX == pivoX && testY == pivoY){
                    agreg = false;
                    break;
                }
                iter++;
            }
            if (agreg){
                movFinX.add(pivoX);
                movFinY.add(pivoY);
            }
            contE++;
        }
        
        movCpu(jugador.getNick());
    }
    
    //Funcion encargada de la segunda parte del movimiento de la CPU
    //En primer lugar, verificará si puede atacar cuerpo a cuerpo.
    //En caso de no hacerlo, buscará atacar a distancia
    //Una vez realizada cualquiera de las dos acciones, se pasará el turno
    //De no poder realizar ninguna accion anterior, simplemente se pasará turno
    
    private void atacarCPU(int x, int y, int z){
        this.cerrada.clear();
        buscarCortaDist(x,y,z);
        if (!(this.cerrada.isEmpty())){
            int aAtacar = this.obtenerMenorVida(this.cerrada);
            this.ataque(aAtacar, this.jugador.getId());
            
            return;
            //atacar a corta distancia
        }
        this.cerrada.clear();
        this.buscarLargDist(x, y, z);
        if (!(this.cerrada.isEmpty())){
            int aAtacar = this.obtenerMenorVida(this.cerrada);
            this.ataqueLargo(this.jugador.getId(), aAtacar);
            return;
            //atacar larga distancia
        }
        //Si no
        JOptionPane.showMessageDialog(vislog, "Nadie a quien atacar", "Ya llegará tu hora...",
                JOptionPane.INFORMATION_MESSAGE);

        
    }
    public void movCpu(String quienJuega){
        contK = 0;
        TimerTask caminarX = new TimerTask(){
            public void run(){
               if(contK == movFinX.size()-1){
                    cancel();
                    atacarCPU(movFinX.get(contK),movFinY.get(contK),0);
                    return;
                }
                borrar(jugador.getPosicionX(), jugador.getPosicionY());
                Tiro(movFinX.get(contK), movFinY.get(contK), quienJuega);
                if(!(verificarMovimiento(0,movFinX.get(contK),movFinY.get(contK)))){
                    cancel();
                    atacarCPU(movFinX.get(contK),movFinY.get(contK),0);
                    return;
                }
                contK++;
                
                
                
                
            }
        };
        tiempo.schedule(caminarX, 1000,1000);
        
    }
    
    
    private int obtenerMenorVida(ArrayList<Integer> posiblesAAtacar){
        ArrayList<Integer> posibles = new ArrayList();
        for (int id:this.turnos){
            if (this.idsAliados.contains(id)){
                posibles.add(id);
            }
        }
        String[] buscVida = personaje.consultar(posibles.get(0)).split("CORTAR");
        int vida = 0;
        //se define una vida pivote para comparar las demas
        int vidaPivote = Integer.parseInt(buscVida[4]);
        int idPivote = Integer.parseInt(buscVida[0]);
        for (int id: posiblesAAtacar){
             buscVida = personaje.consultar(id).split("CORTAR");
             vida = Integer.parseInt(buscVida[4]);
             if (vida<vidaPivote){
                vidaPivote = vida;
                idPivote = id;
             }

        }
        return idPivote;

    }
    private int obtenerMenorVida(){
        String[] buscVida = personaje.consultar(this.idsAliados.get(0)).split("CORTAR");
        int vida = 0;
        ArrayList<Integer> posibles = new ArrayList();
        for (int id:this.turnos){
            if (this.idsAliados.contains(id)){
                posibles.add(id);
            }
        }
        //se define una vida pivote para comparar las demas
        int vidaPivote = Integer.parseInt(buscVida[4]);
        int idPivote = Integer.parseInt(buscVida[0]);
        for (int id: posibles){
             buscVida = personaje.consultar(id).split("CORTAR");
             vida = Integer.parseInt(buscVida[4]);
             if (vida<vidaPivote){
                vidaPivote = vida;
                idPivote = id;
             }

        }
        return idPivote;

    }
    private void juegoCPU() {
        //this.resume();
        int menorId = obtenerMenorVida();
        String[] perseguir = personaje.consultar(menorId).split("CORTAR");
        if (armarCamino(Integer.parseInt(perseguir[13]), Integer.parseInt(perseguir[14]), jugador.getNick())){
            this.procesarLista();
        }else{
           this.pasarTurno();
        }
        //movCpu(jugador.getNick());
        
    }
    

 
    
  //------------------------------------------------------------------------------------------------//
  //------------------------------------------------------------------------------------------------//

    
public void mostrarPersonaje(String[] personaje) {
        ImageIcon iconPj = new ImageIcon(getClass().getResource(personaje[11]));
        int x = Integer.parseInt(personaje[13]);
        int y = Integer.parseInt(personaje[14]);
        //por utilizar cuando sea implementada la altura
        //int z = Integer.parseInt(personaje[16]);
        tablero[x][y].A.setIcon(iconPj);
        //tablero[x][y].A.addActionListener(this);
        tablero[x][y].B = Integer.parseInt(personaje[0]);
        tablero[x][y].A.repaint();
    } 
    public void mostrarEnemigos(String[] personaje) {
        ImageIcon iconPj = new ImageIcon(getClass().getResource(personaje[11]));
        int x = Integer.parseInt(personaje[13]);
        int y = Integer.parseInt(personaje[14]);
        //por utilizar cuando sea implementada la altura
        //int z = Integer.parseInt(personaje[16]);
        tablero[x][y].A.setIcon(iconPj);
       // tablero[x][y].A.addActionListener(this);
        tablero[x][y].B = Integer.parseInt(personaje[0]);
    }
    
    public void cambiarEstadoBotones(){
        vistab.jButton1.setVisible(!vistab.jButton1.isVisible());
        vistab.jButton2.setVisible(!vistab.jButton2.isVisible());
        vistab.jButton3.setVisible(!vistab.jButton3.isVisible());
        vistab.jButton4.setVisible(!vistab.jButton4.isVisible());
        vistab.jButton5.setVisible(!vistab.jButton5.isVisible());
        vistab.jButton6.setVisible(!vistab.jButton6.isVisible());
        vistab.jButton7.setVisible(!vistab.jButton7.isVisible());
        vistab.jButton8.setVisible(!vistab.jButton8.isVisible());
        
    }
    public void esTurno(String quienJuega){
        //ir cambiando los turnos
        
    }
    public void contador(){
       int contadorAgua = 0;
       int contadorTierra =0;
       int contadorBosque =0;
       for (int i=0;i<25;i++){
           for (int j=0;j<25;j++){
               if (tablero[i][j].B == 1){
                   contadorAgua++;
               }else if (tablero[i][j].B == 0){
                   contadorTierra++;
               }else if(tablero[i][j].B == 3){
                   contadorBosque++;
               }
           }
       }
   }
    
   public void zonasSeguras(){
       for (int i=0;i<25;i++){
           for (int j=0;j<25;j++){
                if (i==0 || j==0){
                    borrar(i,j);
                 }else if(i==24 || j==24){
                     borrar(i,j);
                 }else if(i==12 || j==12){
                     borrar(i,j);
                 }else if(i==j || i == 24-j){
                     borrar(i,j);
                 }
           }
       }
   
   }
   public void revisionDeMapa(int idTerreno){
       for (int i=0;i<25;i++){
           for (int j=0;j<25;j++){
                if (tablero[i][j].B==idTerreno){
                    mainLoop:for (int hb=i-1;hb<=i+1;hb++){
                        for (int  cb=j-1;cb<=j+1;cb++){
                            if (hb!=i && cb!=j){
                                try{
                                    if (tablero[hb][cb].B==1 || tablero[hb][cb].B == 3){
                                        break mainLoop;
                                    }else if(hb==i+1 && cb == j+1){
                                        
                                        borrar(i,j);
                                    }//end if
                                }catch (ArrayIndexOutOfBoundsException e){
                                    //do nothing
                                }
                            }
                        }//end for
                    
                     }//end  main for
                }//end main if
                
            //vuelve a segundo for    
            }
            //end segundo for
        //vuelve a primer for
       }//end main for
   }
   

   public void revisionDeAltura(String tipoCuadro){
        switch (tipoCuadro) {
            case "Bosque":
                break;
            default:
                for (int i=0;i<25;i++){
                    for (int j=0;j<25;j++){
                        if (tablero[i][j].B==0){
                            //tablero[i][j].A.addActionListener(this);
                            mainLoop:for (int hb=i-1;hb<=i+1;hb++){
                                for (int  cb=j-1;cb<=j+1;cb++){
                                    if (hb!=i && cb!=j){
                                        try{
                                            if (tablero[hb][cb].B != 1 && tablero[hb][cb].B != 2){
                                                if (Math.abs(tablero[hb][cb].C-tablero[i][j].C) > 2){
                                                    if (tablero[hb][cb].C > tablero[i][j].C){
                                                        tablero[hb][cb].C = tablero[i][j].C + 2;
                                                        if (tablero[hb][cb].B == 0){
                                                            String FotAlt = "Pasto"+tablero[hb][cb].C;
                                                            ImageIcon ImgAlt =
                                                                new ImageIcon(getClass().getResource("/ImagenesTablero/"+FotAlt+".png"));
                                                             tablero[hb][cb].A.setIcon(ImgAlt);
                                                        }else if(tablero[hb][cb].B == 3){
                                                           
                                                            String FotAlt = "Bosque"+tablero[hb][cb].C;
                                                            ImageIcon ImgAlt =
                                                                    new ImageIcon(getClass().getResource("/ImagenesTablero/"+FotAlt+".png"));
                                                            tablero[hb][cb].A.setIcon(ImgAlt);
                                                        }
                                                       
                                                    }else{
                                                        tablero[hb][cb].C = tablero[i][j].C - 2;
                                                        if (tablero[hb][cb].B == 0){
                                                            String FotAlt = "Pasto"+tablero[hb][cb].C;
                                                            ImageIcon ImgAlt =
                                                                new ImageIcon(getClass().getResource("/ImagenesTablero/"+FotAlt+".png"));
                                                             tablero[hb][cb].A.setIcon(ImgAlt);
                                                        }else if(tablero[hb][cb].B == 3){
                                                           
                                                            String FotAlt = "Bosque"+tablero[hb][cb].C;
                                                            ImageIcon ImgAlt =
                                                                    new ImageIcon(getClass().getResource("/ImagenesTablero/"+FotAlt+".png"));
                                                            tablero[hb][cb].A.setIcon(ImgAlt);
                                                        }
                                                    }
                                                }
                                            }
                                        }catch (ArrayIndexOutOfBoundsException e){    
                                            //do nothing
                                        }
                                        
                                    }
                                }//end for
                                
                            }//end  main for
                        }//end main if
                        
                        //vuelve a segundo for
                    }
                    //end segundo for
                    //vuelve a primer for
                }//end main for
                vistab.jPanel2.repaint();
                break;
        }
   }
   
   
   
   
   public void escritura(Personaje personaje){
        vistab.jButton9.setEnabled(true);
        vistab.jButton10.setEnabled(true);
        this.idPj = personaje.getId();
        //Se pone el nick en la vista
        vistab.jLabel6.setText(personaje.getNick());
        //se extrae la vida del personaje y se guarda en una variable 
        this.vida = personaje.getVida();
        vistab.jProgressBar1.setValue(vida);
        //Se realiza un toque decorativo en relacion a la cantidad de vida
        //si está cerca de morir, la barra de vida se pondrá roja
        if (personaje.getVida()<25){
            vistab.jProgressBar1.setForeground(Color.red);
        }
        else{
            vistab.jProgressBar1.setForeground(Color.blue);
        }
        //Movmiento
        this.movimiento = personaje.getMovimiento();
        vistab.jLabel7.setText(Integer.toString(movimiento));
        //Ataque
        this.ataque = personaje.getAtaque();
        vistab.jProgressBar2.setValue(ataque);
        //Defensa
        this.defensa = personaje.getDefensa();
        vistab.jProgressBar3.setValue(defensa);
        //Posiciones
        this.posicionX = personaje.getPosicionX();
        this.posicionY = personaje.getPosicionY();
        //Imagen
        this.foto = new ImageIcon(getClass().getResource(personaje.getImagen()));
        vistab.jLabel1.setIcon(foto);
        //icono
        this.icono = new ImageIcon(getClass().getResource(personaje.getImgTablero()));
        vistab.jPanel2.repaint();
        
      
    
    
      
    }
    public void borrar(int posicionX, int posicionY){
        //borrando antigua posicion
        int i = posicionX;
        int j = posicionY;
        /*
        ImageIcon iconoPasto = new ImageIcon(this.getClass().getResource("/ImagenesTablero/Pasto5.png"));
        tablero[posicionX][posicionY].A.setIcon(iconoPasto);
        tablero[posicionX][posicionY].A.removeActionListener(this);
        tablero[posicionX][posicionY].B = 0;
        
        tablero[posicionX][posicionY].A.repaint();
        */
        
        ImageIcon Altura = null;
        int altura = tablero[i][j].C;
        switch (altura){
            case 5:
                tablero[i][j].C=altura;
                Altura = new ImageIcon(getClass().getResource("/ImagenesTablero/Pasto5.png"));
                tablero[i][j].A.setIcon(Altura);
                break;
            case 4:
                tablero[i][j].C=altura;
                Altura = new ImageIcon(getClass().getResource("/ImagenesTablero/Pasto4.png"));
                tablero[i][j].A.setIcon(Altura);
                break;
            case 3:
                tablero[i][j].C=altura;
                Altura = new ImageIcon(getClass().getResource("/ImagenesTablero/Pasto3.png"));
                tablero[i][j].A.setIcon(Altura);
                break;
            case 2:
                tablero[i][j].C=altura;
                Altura = new ImageIcon(getClass().getResource("/ImagenesTablero/Pasto2.png"));
                tablero[i][j].A.setIcon(Altura);
                break;
            case 1:
                tablero[i][j].C=altura;
                Altura = new ImageIcon(getClass().getResource("/ImagenesTablero/Pasto1.png"));
                tablero[i][j].A.setIcon(Altura);
                break;
            case 0:
                tablero[i][j].C=altura;
                Altura = new ImageIcon(getClass().getResource("/ImagenesTablero/Pasto0.png"));
                tablero[i][j].A.setIcon(Altura);
                break;
            default:
                tablero[i][j].C=altura;
                Altura = new ImageIcon(getClass().getResource("/ImagenesTablero/Pasto2.png"));
                tablero[i][j].A.setIcon(Altura);
                break;
                
        }
        this.tablero[i][j].B = 0;
        tablero[i][j].A.repaint();
    }
    public boolean Tiro(int i, int j, String quienJuega){
        //Cambio a nueva posicion
        contador+=1;
        try {
            if (tablero[i][j].B == 1 ){
                //textArea1.append("Mis programadores aun no me enseñan a nadar :c \n");
                return false;
            }
            tablero[i][j].A.setIcon(this.icono);
            //tablero[i][j].A.addActionListener(this);
            tablero[i][j].B = this.idPj;
            return true;
        }catch (java.lang.ArrayIndexOutOfBoundsException e ){
            //textArea1.append("Hey! No quiero chocar contra la pared! \n");
            return false;
        }


        
    }
    
    private void alturas(String tipoRamo) {
        int alturaMax =0;
        int alturaMin = 0;
        int idDeRamo = 0;
        switch (tipoRamo){
            case "Pasto":
                alturaMax = 5;
                idDeRamo = 0;
                alturaMin = 0;
                break;
            case "Bosque":
                idDeRamo = 3;
                alturaMax = 10;
                alturaMin = 0;
                break;
        }
        
        for (int i=0;i<25;i++){
            for (int j=0;j<25;j++){
                if (tablero[i][j].B == idDeRamo){
                    ImageIcon Altura = null;
                    int altura = (int) (Math.random()*(alturaMax+1-alturaMin)+alturaMin);
                    String imagenAltura = tipoRamo+altura+".png";
                    tablero[i][j].C=altura;
                    Altura = new ImageIcon(getClass().getResource("/ImagenesTablero/"+imagenAltura));
                    tablero[i][j].A.setIcon(Altura);
                    
                }

            }
        }
    }
    private void sacarItem(int pos){
        pos = pos+1;
        String lugar = "EXTRA"+(pos);
        if(personaje.usarItem(lugar, this.jugador.getId())){
            JOptionPane.showMessageDialog(vislog, "Item utilizado correctamente.", "Correcto",
                             JOptionPane.INFORMATION_MESSAGE);
        }
            
    }
    
     private boolean afectarBD(int iditm){
        ModeloEAO modeao = new ModeloEAO();
        String[] pj  = personaje.consultar(this.jugador.getId()).split("CORTAR");
        int atack = Integer.parseInt(pj[6]);
        int defense = Integer.parseInt(pj[5]);
        int movment = Integer.parseInt(pj[8]);
        int life = Integer.parseInt(pj[4]);
        int boost=0;
        String tipo = null;
        try {
            String[] item = modeao.buscarItemEspecifico(iditm).split("Cortar");
            String name = item[0];
            String[] nameI = name.split(" ");
            tipo = nameI[2];
            boost = Integer.parseInt(item[3]);
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Error realizando la solicitud del usuario","Error",
                                JOptionPane.ERROR_MESSAGE); 
        }
        
        switch (tipo){
            case "movimiento":
                movment = movment + (boost);
                if (movment > 100){
                    JOptionPane.showMessageDialog(vislog, "No puedes superar los 100 puntos!", "Error",
                             JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                personaje.modificarBd("MOVIMIENTO", movment, idPj);
                //se aniadie a defensa
                break;
            case "vida":
                life = life + (boost);
                if (life > 100){
                    JOptionPane.showMessageDialog(vislog, "No puedes superar los 100 puntos!", "Error",
                             JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                personaje.modificarBd("VIDA", life, idPj);
                 //se aniadie a defensa
                break;
            case "defensa":
                defense = defense + (boost);
                if (defense > 100){
                    JOptionPane.showMessageDialog(vislog, "No puedes superar los 100 puntos!", "Error",
                             JOptionPane.ERROR_MESSAGE);
                    return false;
               }
                personaje.modificarBd("DEFENSA", defense, idPj);
                //se aniadie a defensa
                break;
            case "Fuerza":
                atack = atack + (boost);
                if (atack > 100){
                    JOptionPane.showMessageDialog(vislog, "No puedes superar los 100 puntos!", "Error",
                             JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                personaje.modificarBd("ATAQUE", atack, idPj);
                //se aniadie a defensa
                break;
             
            
          
        } 
        this.batalla(this.jugador.getId());
        return true;

        
    }
   
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vistab.jButton3){
            if (this.movimiento >0 ){
                if (Math.abs(tablero[posicionX][posicionY].C - tablero[posicionX][posicionY-1].C) > 2){
                    JOptionPane.showMessageDialog(vislog, "No te puedes mover por la diferencia de alturas",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                borrar(posicionX,posicionY);
                
                posicionY = arriba(posicionY);
                boolean jugada = Tiro(posicionX,posicionY,quienJuega);
                if (!jugada){
                    posicionY = abajo(posicionY);
                    Tiro(posicionX,posicionY,quienJuega);
                }else{
                    if(!verificarMovimiento(this.movimiento, posicionX, posicionY)){
                        this.cambiarEstadoBotones();
                    }
                }
            }else{
            JOptionPane.showMessageDialog(null,"Ya no tienes mas movimientos", "Error",
                JOptionPane.INFORMATION_MESSAGE);
            }
        }else if(e.getSource() == vistab.jButton4){                                      
            // TODO add your handling code here:
            if (this.movimiento >0 ){
                if (Math.abs(tablero[posicionX][posicionY].C - tablero[posicionX-1][posicionY].C) > 2){
                    JOptionPane.showMessageDialog(vislog, "No te puedes mover por la diferencia de alturas",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                borrar(posicionX,posicionY);
                posicionX = izquierda(posicionX);
                boolean jugada = Tiro(posicionX,posicionY,quienJuega);
                if (!jugada){
                    posicionX = derecha(posicionX);
                    Tiro(posicionX,posicionY,quienJuega);
                }else{
                    if(!verificarMovimiento(this.movimiento, posicionX, posicionY)){
                        this.cambiarEstadoBotones();
                    }
                }
            }else{
                JOptionPane.showMessageDialog(null,"Ya no tienes mas movimientos", "Error",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        }else if(e.getSource() == vistab.jButton5){                                      

            // TODO add your handling code here:
            if (this.movimiento >0 ){
                if (Math.abs(tablero[posicionX][posicionY].C - tablero[posicionX+1][posicionY].C) > 2){
                    JOptionPane.showMessageDialog(vislog, "No te puedes mover por la diferencia de alturas",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                borrar(posicionX,posicionY);
                posicionX = derecha(posicionX);
                boolean jugada = Tiro(posicionX,posicionY,quienJuega);
                if (!jugada){
                    posicionX = izquierda(posicionX);
                    Tiro(posicionX,posicionY,quienJuega);
                }else{
                    if(!verificarMovimiento(this.movimiento, posicionX, posicionY)){
                        this.cambiarEstadoBotones();
                    }
                }
            }else{
                JOptionPane.showMessageDialog(null,"Ya no tienes mas movimientos", "Error",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        }else if(e.getSource() == vistab.jButton1){                                        

            // TODO add your handling code here:
            if (this.movimiento >0 ){
                if (Math.abs(tablero[posicionX][posicionY].C - tablero[posicionX-1][posicionY-1].C) > 2){
                    JOptionPane.showMessageDialog(vislog, "No te puedes mover por la diferencia de alturas",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                borrar(posicionX,posicionY);
                posicionX = izquierda(posicionX);
                posicionY = arriba(posicionY);
                boolean jugada = Tiro(posicionX,posicionY,quienJuega);
                if (!jugada){
                    posicionX = derecha(posicionX);
                    posicionY = abajo(posicionY);
                    Tiro(posicionX,posicionY,quienJuega);
                }else{
                    if(!verificarMovimiento(this.movimiento, posicionX, posicionY)){
                        this.cambiarEstadoBotones();
                    }
                }
            }else{
                JOptionPane.showMessageDialog(null,"Ya no tienes mas movimientos", "Error",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        }else if(e.getSource() == vistab.jButton2){                                        

            // TODO add your handling code here:
            if (this.movimiento >0 ){
                if (Math.abs(tablero[posicionX][posicionY].C - tablero[posicionX+1][posicionY-1].C) > 2){
                    JOptionPane.showMessageDialog(vislog, "No te puedes mover por la diferencia de alturas",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                borrar(posicionX,posicionY);
                posicionX = derecha(posicionX);
                posicionY = arriba(posicionY);
                boolean jugada = Tiro(posicionX,posicionY,quienJuega);
                if (!jugada){
                    posicionX = izquierda(posicionX);
                    posicionY = abajo(posicionY);
                    Tiro(posicionX,posicionY,quienJuega);
                }else{
                    if(!verificarMovimiento(this.movimiento, posicionX, posicionY)){
                        this.cambiarEstadoBotones();
                    }
                }
            }else{
                JOptionPane.showMessageDialog(null,"Ya no tienes mas movimientos", "Error",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        }else if(e.getSource() == vistab.jButton7){                                  

            // TODO add your handling code here:
            if (this.movimiento >0 ){
                if (Math.abs(tablero[posicionX][posicionY].C - tablero[posicionX+1][posicionY+1].C) > 2){
                    JOptionPane.showMessageDialog(vislog, "No te puedes mover por la diferencia de alturas",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                borrar(posicionX,posicionY);
                posicionX = derecha(posicionX);
                posicionY = abajo(posicionY);

                boolean jugada = Tiro(posicionX,posicionY,quienJuega);
                if (!jugada){
                    posicionX = izquierda(posicionX);
                    posicionY = arriba(posicionY);
                    Tiro(posicionX,posicionY,quienJuega);
                }else{
                    if(!verificarMovimiento(this.movimiento, posicionX, posicionY)){
                        this.cambiarEstadoBotones();
                    }
                }
            }else{
                JOptionPane.showMessageDialog(null,"Ya no tienes mas movimientos", "Error",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        }else if(e.getSource() == vistab.jButton8){                                        
            // TODO add your handling code here:
            if (this.movimiento >0 ){
                if (Math.abs(tablero[posicionX][posicionY].C - tablero[posicionX-1][posicionY+1].C) > 2){
                    JOptionPane.showMessageDialog(vislog, "No te puedes mover por la diferencia de alturas",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                borrar(posicionX,posicionY);
                posicionX = izquierda(posicionX);
                posicionY = abajo(posicionY);
                boolean jugada = Tiro(posicionX,posicionY,quienJuega);
                if (!jugada){
                    posicionX = derecha(posicionX);
                    posicionY = arriba(posicionY);
                    Tiro(posicionX,posicionY,quienJuega);
                }else{
                    if(!verificarMovimiento(this.movimiento, posicionX, posicionY)){
                        this.cambiarEstadoBotones();
                    }
                }
            }else{
                JOptionPane.showMessageDialog(null,"Ya no tienes mas movimientos", "Error",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        }else if(e.getSource() == vistab.jButton6){

            // TODO add your handling code here:
            if (this.movimiento>0){
                if (Math.abs(tablero[posicionX][posicionY].C - tablero[posicionX][posicionY+1].C) > 2){
                    JOptionPane.showMessageDialog(vislog, "No te puedes mover por la diferencia de alturas",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                borrar(posicionX,posicionY);
                posicionY = abajo(posicionY);
                boolean jugada = Tiro(posicionX,posicionY,quienJuega);
                if (!jugada ){
                    posicionY = arriba(posicionY);
                    Tiro(posicionX,posicionY,quienJuega);
                }else{
                    if(!verificarMovimiento(this.movimiento, posicionX, posicionY)){
                        this.cambiarEstadoBotones();
                    }
                }
            }else{
                JOptionPane.showMessageDialog(null,"Ya no tienes mas movimientos", "Error",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        }else if(e.getSource() == vistab.jButton9)   {                                    

            // TODO add your handling code here:
            this.cambiarEstadoBotones();
            if(seMovio(Integer.parseInt(vistab.jLabel7.getText()))){
                vistab.jButton9.setEnabled(false);
            }

        }else if(e.getSource() == vistab.jButton10){                                

            // TODO add your handling code here:
            if (vistab.jButton3.isEnabled()){
                this.cambiarEstadoBotones();
            }
            vistab.jButton10.setVisible(false);
            vistab.jButton11.setVisible(false);
            vistab.jButton9.setVisible(false);
            vistab.jButton12.setVisible(true);
            vistab.jButton13.setVisible(true);
            vistab.jButton14.setVisible(true);
            vistab.jButton15.setVisible(true);
        }else if(e.getSource() == vistab.jButton11){
            //usar item
            if (!(this.usarItem)){
                ModeloEAO moditem = new ModeloEAO();
                String[] items = personaje.consultar(this.idJgdr).split("CORTAR");
                vistab.jComboBox1.setVisible(true);
                vistab.jComboBox1.removeAllItems();
                vistab.jComboBox1.removeAllItems();
                ArrayList<Integer> itemsUs = new ArrayList<>();
                itemsUs.clear();
                for (int i=0;i<3;i++){
                    itemsUs.add(Integer.parseInt(items[22+i]));
                }
                for (int itm:itemsUs){
                    try {
                        if (itm!=0){
                            String [] datItm = moditem.buscarItemEspecifico(itm).split("Cortar");
                            vistab.jComboBox1.addItem(datItm[0]);
                            vistab.jComboBox2.addItem(Integer.toString(itm));
                        }
                    } catch (SQLException ex) {
                     JOptionPane.showMessageDialog(null,"Error realizando la solicitud del usuario","Error",
                                JOptionPane.ERROR_MESSAGE); 
                    }
                }
                this.usarItem = true;
            }else if(this.usarItem){
                int pos = vistab.jComboBox1.getSelectedIndex();
                int idItm = Integer.parseInt(vistab.jComboBox2.getItemAt(pos));
                if(this.afectarBD(idItm)){
                    this.sacarItem(pos);

                }
                this.usarItem = false;
                
            }
           
        }else if(e.getSource() == vistab.jButton12){                                     

            // TODO add your handling code here:
            vistab.jLabel10.setText("");
            vistab.jLabel10.setText("2");
            vistab.jComboBox1.setVisible(true);
            buscarLargDist(posicionX, posicionY, 0);
        }else if(e.getSource() == vistab.jButton13){                                      

            // TODO add your handling code here:
            vistab.jLabel10.setText("");
            vistab.jLabel10.setText("1");
            vistab.jComboBox1.setVisible(true);
            buscarCortaDist(posicionX, posicionY, 0);
        }else if(e.getSource() == vistab.jButton14){                                        

            // TODO add your handling code here:
            vistab.cancelar();

        }else if(e.getSource() == vistab.jButton15){                                         

            // TODO add your handling code here:
            vistab.cancelar();
            if ("1".equals(vistab.jLabel10.getText())){
                //Ataque a corta distancia, se inicia secuencia de batalla triangular
                int pos = vistab.jComboBox1.getSelectedIndex();
                if (vistab.jComboBox2.getItemCount()>0){
                    int aAtacar = Integer.parseInt(vistab.jComboBox2.getItemAt(pos));
                    ataque(this.idPj, aAtacar);
                }else{
                    JOptionPane.showMessageDialog(null, "Nada seleccionado", "Error",  JOptionPane.ERROR_MESSAGE);
                }
            }else if ("2".equals(vistab.jLabel10.getText())){
                //larga distancia, daño directo
                if (vistab.jComboBox1.getItemCount()>0){
                    int pos = vistab.jComboBox1.getSelectedIndex();
                    int aAtacar = Integer.parseInt(vistab.jComboBox2.getItemAt(pos));
                    ataqueLargo(this.idPj, aAtacar);
                }else{
                    JOptionPane.showMessageDialog(null, "Nada seleccionado", "Error",  JOptionPane.ERROR_MESSAGE);

                }
            }
        }else if(e.getSource() == vistab.jButton16)   {                                      

            // TODO add your handling code here:
            pasarTurno();
        }else if(e.getSource() == vistab.jButton17){
            SubControladorDatos subcondat = new SubControladorDatos(this.turnos, this.idsAliados, this.idsEnemmigos);
        }
   }   

    private void aprobarRamo() {
        ModeloMallaCurricular modmalla = new ModeloMallaCurricular();
        ModeloUsuario modus = new ModeloUsuario();
        String[] usuario =  modus.consultar(ModeloUsuario.idUs).split("CORTAR");
        int carrera = Integer.parseInt(usuario[4]);
        modmalla.aprobarRamo(ModeloUsuario.idUs, this.idRamo, carrera);
       
    }

    private void reponerDatos() {
        int contE = 0;
        for (int pj:this.turnos){
            personaje.modificarMovimiento(this.movimientos.get(contE), pj);
            personaje.modificarVida(this.vidas.get(contE), pj);
            contE++;
        }
    }

    public void agregarExp(int idPj, int factor) {
        String[] consulta = personaje.consultar(idPj).split("CORTAR");
        int exp = Integer.parseInt(consulta[28]);
        int lvl = Integer.parseInt(consulta[27]);
        exp = exp + factor;
        personaje.modificarExp(idPj, exp);
        if (exp > lvl*10000){
            JOptionPane.showMessageDialog(null,"Felicidades, subiste de nivel","Bien",JOptionPane.INFORMATION_MESSAGE);
            personaje.modificarNivel(idPj, lvl+1);
        }
    }

    private void aniadirMontanas(int porcentaje) {
        ImageIcon iconMontaña = new ImageIcon(this.getClass().getResource("/ImagenesTablero/Montaña10.png"));
        ImageIcon monta9 = new ImageIcon(this.getClass().getResource("/ImagenesTablero/Montaña9.png"));
        ImageIcon monta8 = new ImageIcon(this.getClass().getResource("/ImagenesTablero/Montaña8.png"));
        ImageIcon monta7 = new ImageIcon(this.getClass().getResource("/ImagenesTablero/Montaña7.png"));
        ImageIcon monta6 = new ImageIcon(this.getClass().getResource("/ImagenesTablero/Montaña6.png"));
        ImageIcon past5 = new ImageIcon(this.getClass().getResource("/ImagenesTablero/Pasto5.png"));
        ImageIcon past3 = new ImageIcon(this.getClass().getResource("/ImagenesTablero/Pasto3.png"));
        ImageIcon past1 = new ImageIcon(this.getClass().getResource("/ImagenesTablero/Pasto1.png"));
        
        contador = 0;
        
        int[] listaA = {7,3,3,9,15,20,20,16};
        int[] listaB = {3,9,16,19,19,16,9,3};
        
        while (contador < listaA.length){
            int a = listaA[contador];
            int b = listaB[contador];
            tablero[a][b].C = 10;
            tablero[a][b].B = 2;
            tablero[a][b].A.setIcon(iconMontaña);
            //-----------------------/
            tablero[a][b-1].C = 10;
            tablero[a][b].B = 2;
            tablero[a][b-1].A.setIcon(iconMontaña);
            //----------------
            tablero[a-1][b-1].C = 10;
            tablero[a][b].B = 2;
            tablero[a-1][b-1].A.setIcon(monta9);
            //----------------
            tablero[a-1][b].C = 9;
            tablero[a][b].B = 2;
            tablero[a-1][b].A.setIcon(monta9);
            //----------------
            tablero[a-1][b+1].C = 9;
            tablero[a][b].B = 2;
            tablero[a-1][b+1].A.setIcon(monta8);
            //----------------
            tablero[a][b+1].C = 9;
            tablero[a][b].B = 0;
            tablero[a][b].B = 2;
            tablero[a][b+1].A.setIcon(monta8);
            //----------------
            tablero[a+1][b+1].C = 8;
            tablero[a][b].B = 0;
            tablero[a+1][b+1].A.setIcon(monta7);
            //----------------
            tablero[a+1][b].C = 8;
            tablero[a][b].B = 0;
            tablero[a+1][b].A.setIcon(monta7);
            //----------------
            tablero[a+1][b-1].C = 8;
            tablero[a][b].B = 0;
            tablero[a+1][b-1].A.setIcon(monta6);
            //----------------
            tablero[a+1][b-2].C = 7;
            tablero[a][b].B = 0;
            tablero[a+1][b-2].A.setIcon(monta6);
            //----------------
            tablero[a][b-2].C = 7;
            tablero[a][b].B = 0;
            tablero[a][b-2].A.setIcon(monta6);
            //----------------
            tablero[a-1][b-2].C = 7;
            tablero[a][b].B = 0;
            tablero[a-1][b-2].A.setIcon(monta6);
            //----------------
            tablero[a-2][b-2].C = 6;
            tablero[a][b].B = 0;
            tablero[a-2][b-2].A.setIcon(monta6);
            //----------------
            tablero[a-2][b-1].C = 6;
            tablero[a][b].B = 0;
            tablero[a-2][b-1].A.setIcon(monta6);
            //----------------
            tablero[a-2][b].C = 6;
            tablero[a][b].B = 0;
            tablero[a-2][b].A.setIcon(monta6);
            //----------------
            tablero[a-2][b+2].C = 5;
            tablero[a][b].B = 0;
            tablero[a-2][b+2].A.setIcon(past5);
            //----------------
            tablero[a-1][b+2].C = 3;
            tablero[a][b].B = 0;
            tablero[a-1][b+2].A.setIcon(past3);
            //----------------
            tablero[a][b+2].C = 1;
            tablero[a][b].B = 0;
            tablero[a][b+2].A.setIcon(past1);
            
            int az = (int) (Math.random()*(10));
            if (az < porcentaje){
                contador = contador + 2;
            }else {
                contador++;

            }
            
        }
        vistab.jPanel2.revalidate();
        vistab.jPanel2.repaint();
        
    
    }

    private void crearTablero(int idRamo) {
        this.ramo.setIdRamo(idRamo);
        if (idRamo > 13){
            this.ramo.setCantidadEnemigos(5);
            this.ramo.setAreaAsignatura(1);
            this.crearMBI();
        }else if(idRamo<=13){
            this.ramo.setCantidadEnemigos(10);
            this.ramo.setAreaAsignatura(3);
            this.crearEspecialidad();
           
        }else{
             this.ramo.setCantidadEnemigos(7);
            this.ramo.setAreaAsignatura(2);
            this.crearEconomia();
            
        }
    }
    

    
}
