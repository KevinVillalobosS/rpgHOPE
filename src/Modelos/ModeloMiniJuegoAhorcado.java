/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

import java.util.ArrayList;

/**
 *
 * @author Fernando
 */
public class ModeloMiniJuegoAhorcado {
    public String[] diccionario = {"UNIVERSIDAD", "SANTIAGO", "CHILE", "FACULTAD",
        "INGENIERIA", "DEPARTAMENTO", "COMPUTACION", "INFORMATICA", "METODOS", "PROGRAMACION", "SEGUNDO", "SEMESTRE",
        "HITO", "TERCERO", "ORIENTADA", "OBJETOS", "SIMILITUD", "DEBIL", "FUERTE", "RESOLUCION", "PROBLEMAS", 
        "DIVISION", "CONQUISTA", "PRUEBA", "TABLERO", "BATALLA", "CASCO", "ARMADURA", "ITEM", "ESCUELA", "ARTES", "OFICIO", 
        "FORO", "GRIEGO", "BIBLIOTECA", "CENTRAL", "MALLA", "CURRICULAR", "JAVA", "NETBEANS", "PARADIGMA", "EJECUCION",
        "CIVIL", "MONTAÑA", "USUARIO", "DISEÑADOR", "ANALISTA", "PROGRAMADOR", "TESTER", "FELIZ", "GRUPO", "EQUIPO", 
        "ALIADOS", "CALCULO", "ALGEBRA", "FISICA", "ELECTRICIDAD", "MAGNETISMO", "INTEGRALES", "DERIVADAS", "LIMITE", "LABORATORIO", 
        "MATEMATICAS", "CALCULO", "ALGEBRA", "DICCIONARIO", "BIBLIOTECA", "COCINA","PELICULA","COMERCIAL","GRANDE","PEQUEÑO",
        "AHORCADO", "GATO", "SUDOKU", "CELULAR", "VIRTUAL", "PROFESOR", "ALUMNO", "AYUDANTE", "FUNDAMENTOS", "ESTUDIANTE",
        "MEJOR", "AMIGO", "TEXTURA", "PIZARRA", "POCION", "INTENTO", "VIERNES", "COMUNICACION", "RESISTENCIA", "LUNES", "MARTES", 
        "POLERA", "MARTES", "SUEÑO", "MARCHA", "AMPLIADO", "ASAMBLEA", "PROYECTO", "INTERNET", "EJECUTAR", "RETRASO", "SELLO", 
        "APROBACION", "SISTEMAS", "ECUACIONES", "LINEAL", "TANGENTE", "SENO", "COSENO", "SECANTE", "COSECANTE", "MOCHILA", "MORRAL", 
        "NOTEBOOK", "HERRAMIENTAS", "ESTUDIO", "CAMARA", "AUDIFONO", "NOTAS", "APROBAR", "REPROBAR", "CUATRO", "LIDER", 
        "MOUSE", "PERA", "GESTION", "SOFTWARE", "HUMANIDADES", "QUIMICA", "FARMACIA", "ALMUERZO", "SALUDO", "PRESENTACION", 
        "DIFERENCIALES", "TALLER", "DESARROLLO", "PERSONAL", "COMUNICACION", "EFECTIVA", "ADIVINAR", "PALABRA", "PATIO", "CONTROL", 
        "TECLADO"}; 
    
    public char[] abecedario = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 
        'Ñ', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    
    public ArrayList<Character> abecedarioList;
    
    public void agregarAlArrayList (){
        abecedarioList = new ArrayList<>();
        for(char c:abecedario){
            abecedarioList.add(c);
        } 
  //      abecedarioCPU = new ArrayList<>();
    //    for(char c:abecedario){
    //        abecedarioCPU.add(c);
    //    } 
}
}