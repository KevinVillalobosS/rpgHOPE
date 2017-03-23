/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

import static Vistas.VistaSudoku.*;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JTextArea;

/**
 *
 * @author nixett
 */
public class ModeloSudoku {
    
    
    private boolean encontrado;
    private boolean eliminar;
    private int numero = 0;
    private ArrayList<Integer> candidatos;
    private JTextArea jText;
    
    
    private int f;
    private int c;
    private int num;
    private int tipo;
    ArrayList<ModeloSudoku>vol = vol =  new ArrayList<>();;  
    ArrayList<Integer> can = null;
    
    
    ArrayList <Integer> temNum =new ArrayList<>();    
    boolean estaCuadr;
    boolean estaFil;
    boolean estaCol;
    
    
    ArrayList <Integer>  numeroBuscar = new ArrayList<>();    
    ArrayList <Integer> temp; 
    int psiF;
    int psiC;
    int bese;
    boolean salir;
    
    
    //int sudoku[][] = new int[9][9];
    //private static Sudoku[][] matrizSudo2 = new Sudoku[9][9];
    
    
    public ModeloSudoku(){
        
    }
    
    
    public void crearMatriz(){
        matrizSudoku[0][0] = new ModeloSudoku(false, 0,c1);
        matrizSudoku[0][1] = new ModeloSudoku(false, 0,c2);
        matrizSudoku[0][2] = new ModeloSudoku(false, 0,c3);
        matrizSudoku[0][3] = new ModeloSudoku(false, 0,c4);
        matrizSudoku[0][4] = new ModeloSudoku(false, 0,c5);
        matrizSudoku[0][5] = new ModeloSudoku(false, 0,c6);
        matrizSudoku[0][6] = new ModeloSudoku(false, 0,c7);
        matrizSudoku[0][7] = new ModeloSudoku(false, 0,c8);
        matrizSudoku[0][8] = new ModeloSudoku(false, 0,c9);

        matrizSudoku[1][0] = new ModeloSudoku(false, 0,c10);
        matrizSudoku[1][1] = new ModeloSudoku(false, 0,c11);
        matrizSudoku[1][2] = new ModeloSudoku(false, 0,c12);
        matrizSudoku[1][3] = new ModeloSudoku(false, 0,c13);
        matrizSudoku[1][4] = new ModeloSudoku(false, 0,c14);
        matrizSudoku[1][5] = new ModeloSudoku(false, 0,c15);
        matrizSudoku[1][6] = new ModeloSudoku(false, 0,c16);
        matrizSudoku[1][7] = new ModeloSudoku(false, 0,c17);
        matrizSudoku[1][8] = new ModeloSudoku(false, 0,c18);
         
        matrizSudoku[2][0] = new ModeloSudoku(false, 0,c19);
        matrizSudoku[2][1] = new ModeloSudoku(false, 0,c20);
        matrizSudoku[2][2] = new ModeloSudoku(false, 0,c21);
        matrizSudoku[2][3] = new ModeloSudoku(false, 0,c22);
        matrizSudoku[2][4] = new ModeloSudoku(false, 0,c23);
        matrizSudoku[2][5] = new ModeloSudoku(false, 0,c24);
        matrizSudoku[2][6] = new ModeloSudoku(false, 0,c25);
        matrizSudoku[2][7] = new ModeloSudoku(false, 0,c26);
        matrizSudoku[2][8] = new ModeloSudoku(false, 0,c27);
         
        matrizSudoku[3][0] = new ModeloSudoku(false, 0,c28);
        matrizSudoku[3][1] = new ModeloSudoku(false, 0,c29);
        matrizSudoku[3][2] = new ModeloSudoku(false, 0,c30);
        matrizSudoku[3][3] = new ModeloSudoku(false, 0,c31);
        matrizSudoku[3][4] = new ModeloSudoku(false, 0,c32);
        matrizSudoku[3][5] = new ModeloSudoku(false, 0,c33);
        matrizSudoku[3][6] = new ModeloSudoku(false, 0,c34);
        matrizSudoku[3][7] = new ModeloSudoku(false, 0,c35);
        matrizSudoku[3][8] = new ModeloSudoku(false, 0,c36);
        
        matrizSudoku[4][0] = new ModeloSudoku(false, 0,c37);
        matrizSudoku[4][1] = new ModeloSudoku(false, 0,c38);
        matrizSudoku[4][2] = new ModeloSudoku(false, 0,c39);
        matrizSudoku[4][3] = new ModeloSudoku(false, 0,c40);
        matrizSudoku[4][4] = new ModeloSudoku(false, 0,c41);
        matrizSudoku[4][5] = new ModeloSudoku(false, 0,c42);
        matrizSudoku[4][6] = new ModeloSudoku(false, 0,c43);
        matrizSudoku[4][7] = new ModeloSudoku(false, 0,c44);
        matrizSudoku[4][8] = new ModeloSudoku(false, 0,c45);
         
        matrizSudoku[5][0] = new ModeloSudoku(false, 0,c46);
        matrizSudoku[5][1] = new ModeloSudoku(false, 0,c47);
        matrizSudoku[5][2] = new ModeloSudoku(false, 0,c48);
        matrizSudoku[5][3] = new ModeloSudoku(false, 0,c49);
        matrizSudoku[5][4] = new ModeloSudoku(false, 0,c50);
        matrizSudoku[5][5] = new ModeloSudoku(false, 0,c51);
        matrizSudoku[5][6] = new ModeloSudoku(false, 0,c52);
        matrizSudoku[5][7] = new ModeloSudoku(false, 0,c53);
        matrizSudoku[5][8] = new ModeloSudoku(false, 0,c54);
         
        matrizSudoku[6][0] = new ModeloSudoku(false, 0,c55);
        matrizSudoku[6][1] = new ModeloSudoku(false, 0,c56);
        matrizSudoku[6][2] = new ModeloSudoku(false, 0,c57);
        matrizSudoku[6][3] = new ModeloSudoku(false, 0,c58);
        matrizSudoku[6][4] = new ModeloSudoku(false, 0,c59);
        matrizSudoku[6][5] = new ModeloSudoku(false, 0,c60);
        matrizSudoku[6][6] = new ModeloSudoku(false, 0,c61);
        matrizSudoku[6][7] = new ModeloSudoku(false, 0,c62);
        matrizSudoku[6][8] = new ModeloSudoku(false, 0,c63);
        
        matrizSudoku[7][0] = new ModeloSudoku(false, 0,c64);
        matrizSudoku[7][1] = new ModeloSudoku(false, 0,c65);
        matrizSudoku[7][2] = new ModeloSudoku(false, 0,c66);
        matrizSudoku[7][3] = new ModeloSudoku(false, 0,c67);
        matrizSudoku[7][4] = new ModeloSudoku(false, 0,c68);
        matrizSudoku[7][5] = new ModeloSudoku(false, 0,c69);
        matrizSudoku[7][6] = new ModeloSudoku(false, 0,c70);
        matrizSudoku[7][7] = new ModeloSudoku(false, 0,c71);
        matrizSudoku[7][8] = new ModeloSudoku(false, 0,c72);
         
        matrizSudoku[8][0] = new ModeloSudoku(false, 0,c73);
        matrizSudoku[8][1] = new ModeloSudoku(false, 0,c74);
        matrizSudoku[8][2] = new ModeloSudoku(false, 0,c75);
        matrizSudoku[8][3] = new ModeloSudoku(false, 0,c76);
        matrizSudoku[8][4] = new ModeloSudoku(false, 0,c77);
        matrizSudoku[8][5] = new ModeloSudoku(false, 0,c78);
        matrizSudoku[8][6] = new ModeloSudoku(false, 0,c79);
        matrizSudoku[8][7] = new ModeloSudoku(false, 0,c80);
        matrizSudoku[8][8] = new ModeloSudoku(false, 0,c81);
         
      
        
    }
    
    
    ////////////////////////////////////////////////////////
    
    public ModeloSudoku (boolean encontrado, int numero, JTextArea c) {
        this.encontrado = encontrado;
        this.numero = numero;
        this.candidatos =  new ArrayList<Integer>();
        this.jText = c;
        this.eliminar = true;
    }

    public boolean estaEncontrado() {
        return encontrado;
    }

    public boolean estaEliminado() {
        return eliminar;
    }

    public int getNumero() {
        return numero;
    }

    public ArrayList<Integer> getCandidatos() {
        return candidatos;
    }

    public JTextArea getjText() {
        return jText;
    }

    public void setEncontrado(boolean encontrado) {
        this.encontrado = encontrado;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setCandidato(ArrayList<Integer> candidato) {
        this.candidatos = candidato;
    }

    public void setjText(JTextArea jText) {
        this.jText = jText;
    }

    public void setEliminar(boolean eliminar) {
        this.eliminar = eliminar;
    }
    
    public void addNunm(int num){
        candidatos.add(num);
    }
    public void eliminarCandidato(Integer candidato){
        candidatos.remove(candidato);
    }
    
    ///////////////////////////////////////////////////////
    
    public ModeloSudoku(int f, int c, ArrayList<Integer> can,int num,int tipo) {
        this.f = f;
        this.c = c;
        this.can = can;
        this.num = num;
        this.tipo = tipo;
    }

    public ModeloSudoku(int f, int c, int num) {
        this.f = f;
        this.c = c;
        this.num = num;
    }
    public int getF() {
        return f;
    }
    public int getC() {
        return c;
    }
    public int getNum() {
        return num;
    }

    public ArrayList<ModeloSudoku> getVol() {
        return vol;
    }

    public ArrayList<Integer> getCan() {
        return can;
    }

    public int getTipo() {
        return tipo;
    }

   public void add(ModeloSudoku sudoku){
       vol.add(sudoku);
   } 

    ///////////////////////////////////////////////////////////
   
   public void posibilidadDisponible(){
        for (int i = 0; i < 3; i++) {//Recorremos todos los subCuadros que existen 9
            for (int j = 0; j < 3; j++) {
                buscarCandidatosiDisponibles(i,j);
            }
        }
    }
    private void buscarCandidatosiDisponibles(int posiFil, int posiCol){
        temNum.clear();
        extraerNumFijosSubcuadro(posiFil, posiCol);        
        for (int numero = 1; numero <= 9; numero++) {// numeros posibles        
            for (int k = posiFil*3; k < (posiFil*3)+3; k++) {
                for (int i = posiCol*3; i < (posiCol*3)+3; i++) {
                    if(!matrizSudoku[k][i].estaEncontrado()){
                        estaCuadr = estaNumeroEnSubCuadro(numero);                   
                        if(!estaCuadr){
                            estaFil = existeNumeroFila(k, numero);                      
                            if(!estaFil){
                                estaCol = existeNumeroColumna(i, numero);
                                if(!estaCol){                                     
                                    matrizSudoku[k][i].addNunm(numero);
                                    
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    private void extraerNumFijosSubcuadro(int posiFil, int posiCol){
      for (int fil = posiFil*3; fil <(posiFil*3+3); fil++) {// extraemos los números de cada subCuadro
            for (int col = posiCol*3; col <posiCol*3+3; col++) {
                if(matrizSudoku[fil][col].estaEncontrado()){
                    temNum.add(matrizSudoku[fil][col].getNumero());
               } 
            }
        }   
    }
    private boolean estaNumeroEnSubCuadro(int num){// comprobamos si esta en el subCuadro
        for (int i = 0; i < temNum.size(); i++) {
           if(num == temNum.get(i)){
               return true;
           } 
        }
        return false;
    }
    private boolean existeNumeroFila(int fil, int num){ // comprobamos si existe en la fila      
        for (int col =0; col < 9; col++) {
              if( matrizSudoku[fil][col].getNumero()==num){
                 return true; 
              }
        }
        return false;
    }
    private boolean existeNumeroColumna(int col, int num){ // comprobamos si existe en la columna
       for (int fil = 0; fil < 9; fil++) {
              if( matrizSudoku[fil][col].getNumero()==num){
                 return true; 
              }
        }
        return false;        
    }

    ////////////////////////////////////////////////////////////////////
    
    public static void eliminarFila(int num, int fila, ModeloSudoku m[][]){     
        ArrayList tem = null;
        for (int c = 0; c < 9; c++) {
            tem = m[fila][c].getCandidatos();
            for (int j = 0; j < tem.size(); j++) {
                if(num == (int)tem.get(j)&& !m[fila][c].estaEncontrado()){                  
                   m[fila][c].eliminarCandidato((Integer) tem.get(j));
                   break;
                }
            }
        }
    }
   public static void eliminarColumna(int num, int columna, ModeloSudoku m[][]){
        ArrayList tem = null;
        for (int f =0; f < 9; f++) {
            tem = m[f][columna].getCandidatos();
            for (int j = 0; j < tem.size(); j++) {
                if(num == (int)tem.get(j) && !m[f][columna].estaEncontrado()){                     
                    m[f][columna].eliminarCandidato((Integer) tem.get(j));                    
                    break;
                }
            }
        }
   }
    public static void eliminarRegion(int num, int fila,int columna, ModeloSudoku m[][]){
        for (int f = (fila/3)*3; f < (fila/3)*3+3; f++) {
            for (int c = (columna/3)*3; c < (columna/3)*3+3; c++) {
                if(!m[f][c].estaEncontrado()){
                    ArrayList<Integer>tem ;
                    tem = m[f][c].getCandidatos();
                    for (int i = 0; i < tem.size(); i++) {
                        if(tem.get(i)== num){                            
                            m[f][c].eliminarCandidato((Integer) tem.get(i)); 
                          break;
                        }
                    }
                }
            }
        }
    }
    
    //////////////////////////////////////////////////////////////////////////
    
    public  void buscarSencillo(){ 
       boolean tem=false;
        for (int fil = 0; fil < 9; fil++) {
            for (int col = 0; col < 9; col++) {
                if( !matrizSudoku[fil][col].estaEncontrado() && matrizSudoku[fil][col].getCandidatos().size() == 1){
                   int n =matrizSudoku[fil][col].getCandidatos().get(0);
                    if(!pista){                     
                     matrizSudoku[fil][col].setNumero(n);
                     matrizSudoku[fil][col].setEncontrado(true);
//                    
                         matrizSudoku[fil][col].getjText().setFont(new java.awt.Font("Monospaced", 1, 24));                        
                         matrizSudoku[fil][col].getjText().setBackground(new Color(217,254,217));
//                    
                     eliminarCandidatoCol(col, n);
                     eliminarCandidatoFil(fil, n);
                     eliminarCandidatoCuadro(fil, col, n);
//                    
                        matrizSudoku[fil][col].getjText().setText(" "+n);
                     fil=0;
                     col=0;
                     celdaCompleta++;                     
                   }
                    else{
                        matrizSudoku[fil][col].getjText().setBackground(new Color(253,253,174));
                         matrizSudoku[fil][col].getjText().setBorder(javax.swing.BorderFactory.createLineBorder(Color.GREEN,2));
                       tem=true;
                       fil=9;
                       info.setText("Sencillo al descubierto");                       
                       
                       break;                       
                   }                     
                }
            }
        }
        jLabel2.setText("Celdas Completadas "+celdaCompleta);
        if(celdaCompleta < 81 && !tem) new ModeloSudoku().buscarSencilloOculto();
        else if(celdaCompleta == 81){ 
            
            info.setText("FIN DEL JUEGO");
            info.setBackground(new Color(86,250,118));
        }
        
    }
   void eliminarCandidatoCuadro(int fila, int columna, int num){
        for (int f = ((int) fila/3)*3; f <((int) fila/3)*3+3; f++) {
            for (int c =((int) columna/3)*3; c <((int) columna/3)*3+3; c++) {
                for (int i = 0; i < matrizSudoku[f][c].getCandidatos().size(); i++) {
                    if(num == matrizSudoku[f][c].getCandidatos().get(i)){
                        matrizSudoku[f][c].eliminarCandidato((Integer)matrizSudoku[f][c].getCandidatos().get(i));
                        
                   }
               }
           }
       }
   }
    void eliminarCandidatoFil(int fil, int num){
        ArrayList tem = null;
        for (int i =0; i < 9; i++) {
            tem = matrizSudoku[fil][i].getCandidatos();
            for (int j = 0; j < tem.size(); j++) {
                if(num == (int)tem.get(j)){
                   matrizSudoku[fil][i].eliminarCandidato((Integer) tem.get(j));
                    
                }
            }
        }
    }       
    public void eliminarCandidatoCol(int col, int num){
       ArrayList tem = null;
        for (int i =0; i < 9; i++) {
            tem = matrizSudoku[i][col].getCandidatos();
            for (int j = 0; j < tem.size(); j++) {
                if(num == (int)tem.get(j)){
                   matrizSudoku[i][col].eliminarCandidato((Integer) tem.get(j));
                    
                }
            }
        }
    }
    
    public void imprimirCandidatos(int fil, int col){
           if(!matrizSudoku[fil][col].estaEncontrado())
               matrizSudoku[fil][col].getjText().setText(null);
            for (int i = 0; i <   matrizSudoku[fil][col].getCandidatos().size(); i++) {
                matrizSudoku[fil][col].getjText().append(" "+matrizSudoku[fil][col].getCandidatos().get(i));            
        }           
    }
    
    /////////////////////////////////////////////////////////////////////////
    
    public void buscarSencilloOculto(){
        salir=false;
        for (int fila = 0; fila < 3; fila++) {
            for (int columna = 0; columna < 3; columna++) {
                sencillo(fila, columna);
                if(salir){
                    fila=9;
                    break;
                }
            }
 
        }
        if(salir && !pista)   new ModeloSudoku().buscarSencillo();
        
    }
    void sencillo(int posiFil, int posiCol){        
        numerosExistentes(posiFil, posiCol);        
         for (int numOcul = 1; numOcul <= 9; numOcul++) {           
             bese=0;
             if(!existeNumero(numOcul)){
                 for (int k = posiFil*3; k < (posiFil*3)+3; k++) {
                     for (int h = posiCol*3; h < (posiCol*3)+3; h++) {
                         if(!matrizSudoku[k][h].estaEncontrado()){
                         temp = matrizSudoku[k][h].getCandidatos();
                         for (int j = 0; j < temp.size(); j++) {
                             if(temp.get(j) == numOcul){  
                                psiF=k;
                                psiC=h;
                                bese++;                          
                             }
                         }                         
                     } 
                     }
                }
                if(bese>1)   numeroBuscar.add(numOcul);
                if(bese==1){
                     if(!pista){
                        matrizSudoku[psiF][psiC].setNumero(numOcul);
                        matrizSudoku[psiF][psiC].setEncontrado(true);
//                        
                             matrizSudoku[psiF][psiC].getjText().setFont(new java.awt.Font("Monospaced", 1, 24));                            
                             matrizSudoku[psiF][psiC].getjText().setText(" "+numOcul);
                              
//                        
                         matrizSudoku[psiF][psiC].getCandidatos().clear();
                         matrizSudoku[psiF][psiC].addNunm(numOcul);

                         eliminarNumCol(psiC, numOcul);
                         eliminarNumFil(psiF, numOcul);
                         eliminarCandidatoCuadro2(psiF, psiC, numOcul);                     
                         celdaCompleta++;
//                         
                        matrizSudoku[psiF][psiC].getjText().setBackground(new Color(217,254,217));
                        jLabel2.setText("Celda Completadas "+celdaCompleta);                  
                             
//                         
                     }                     
                     else { 
                        matrizSudoku[psiF][psiC].getjText().setBackground(new Color(253,253,174));
                         matrizSudoku[psiF][psiC].getjText().setBorder(javax.swing.BorderFactory.createLineBorder(Color.GREEN,2));
                         info.setText("Sencillo Oculto");
                        
                         
                     }
                     salir=true;
                     break;   
                     
                 }                 
             }
         }
    }
    void numerosExistentes(int posiFil, int posiCol){
        numeroBuscar.clear();// contiene los numeros que estan fijos 
        for (int k = posiFil*3; k < (posiFil*3)+3; k++) {
                for (int i = posiCol*3; i < (posiCol*3)+3; i++) {
                    if(matrizSudoku[k][i].estaEncontrado()){
                        numeroBuscar.add(matrizSudoku[k][i].getNumero());
                    }
                }
        }      
    }
    boolean existeNumero(int num){
        for (int i = 0; i < numeroBuscar.size(); i++) {
            if((int)numeroBuscar.get(i )==num){
                return true;
            }
        }
        return false;  
    }
    void eliminarNumFil(int fil, int num){
        ArrayList tem = null;
        for (int i =0; i < 9; i++) {
           tem = matrizSudoku[fil][i].getCandidatos();
            for (int j = 0; j < tem.size(); j++) {
                if(num == (int)tem.get(j) ){
                   matrizSudoku[fil][i].eliminarCandidato( (Integer) tem.get(j));
                    
                }
            }
        }
    }
        
    public void eliminarNumCol(int col, int num){
       ArrayList tem = null;
        for (int i =0; i < 9; i++) {
           tem = matrizSudoku[i][col].getCandidatos();
            for (int j = 0; j < tem.size(); j++) {
                if(num == (int)tem.get(j)){
                   matrizSudoku[i][col].eliminarCandidato((Integer) tem.get(j));
                    
                }
            }
        }
    }
    void eliminarCandidatoCuadro2(int fila, int columna, int num){
       for (int f = ((int) fila/3)*3; f <((int) fila/3)*3+3; f++) {
           for (int c =((int) columna/3)*3; c <((int) columna/3)*3+3; c++) {
               for (int i = 0; i < matrizSudoku[f][c].getCandidatos().size(); i++) {
                    if(num == matrizSudoku[f][c].getCandidatos().get(i)){
                        matrizSudoku[f][c].eliminarCandidato((Integer)matrizSudoku[f][c].getCandidatos().get(i));
                                                   
                            
                    }    
                }
            }
        }
    }
    
    /////////////////////////////////
    
    
    
    
}
    

