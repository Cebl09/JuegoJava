/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package juego;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author carloseduardobadillolara
 */
public class GestorPuntajes {
    private static final String NOMBRE_ARCHIVO = "puntajes.txt";
    private static final int TOP_MAXIMO = 10;
    
    private Puntaje[] topPuntajes;
    
    public GestorPuntajes() {
        topPuntajes = new Puntaje[TOP_MAXIMO];
        cargarPuntajes();
    }
    
    public void cargarPuntajes() {
        try {
            FileReader fr = new FileReader(NOMBRE_ARCHIVO);
            BufferedReader br = new BufferedReader(fr);
            
            int i = 0;
            String linea = br.readLine();
            while(linea != null && i < TOP_MAXIMO) {
                String[] partes = linea.split(",");
                String nombre = partes [0];
                int puntos = Integer.parseInt(partes[1]);
                topPuntajes[i] = new Puntaje(nombre, puntos);
                i++;
                linea = br.readLine();
            }
            br.close();
        } catch (IOException ex) {
            System.out.println("No hay puntajes guardados aun");
        }
    }
    
    public void guardarPuntajes() {
        try {
            FileWriter fw = new FileWriter(NOMBRE_ARCHIVO);
            BufferedWriter bw = new BufferedWriter(fw);
            int i = 0;
            while(i < TOP_MAXIMO && topPuntajes[i] != null) {
                bw.write(topPuntajes[i].toString());
                bw.newLine();
                i++;
            }
            bw.close();
        } catch(IOException ex) {
            System.out.println("Error al guardar: "+ex.getMessage());
        }
    }
    public boolean califica(int puntos) {
        boolean entra = false;
        if(topPuntajes[TOP_MAXIMO -1] == null) {
            entra = true;
        } else if(puntos > topPuntajes[TOP_MAXIMO - 1].getPuntos()) {
            entra = true;
        }
        return entra;
    }
    public void insertar(String nombre, int puntos) {
        Puntaje nuevo = new Puntaje(nombre, puntos);
        int posicion = TOP_MAXIMO;
        int i = 0;
        while(i < TOP_MAXIMO && posicion == TOP_MAXIMO) {
            if(topPuntajes[i] == null || puntos > topPuntajes[i].getPuntos()) {
                posicion = i;
            }
            i++;
        }
        int j = TOP_MAXIMO -1;
        while(j > posicion) {
            topPuntajes[j] = topPuntajes[j - 1];
            j--;
        }
        topPuntajes[posicion] = nuevo;
        guardarPuntajes();
    }
    public Puntaje[] getTop() {
        return topPuntajes;
    }
}
