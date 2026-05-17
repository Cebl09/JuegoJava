/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package juego;

import edu.epromero.util.ComportamientoEnemigo;
import edu.epromero.util.Imagen;
import edu.epromero.util.StdRandom;

/**
 *
 * @author carloseduardobadillolara
 */
@ComportamientoEnemigo(tipo="Destructor", resistencia = 1, puntos = 50)
public class Destructor extends NaveEnemiga {
    private static final int BALAPORSEGUNDO = 78;
    private int direccion;
    
    public Destructor() {
    setVelocidad(5);
    setImagen(new Imagen("/resources/Destructor.png"));
    direccion = DERECHA;
}
    @Override
    public void aparecer() {
        setVisible(true);
        int xMin = ((int) getLienzo().pideLimiteXMin());
        int xMax = ((int) getLienzo().pideLimiteXMax());
        setX(xMin + StdRandom.uniforme(xMax - xMin));
        setVidas(getResistencia());
        setY((int)getLienzo().pideLimiteYMax() - getImagen().alto());
    }
    
    @Override
    public void Mueve(Entrada e) {
        setX(getX() + getDireccion() * getVelocidad());
        if(getX() >= 800 - getImagen().ancho()/2) {
            setX(800 - getImagen().ancho()/2);
            setDireccion(IZQUIERDA);
        }
        if(getX() <= getImagen().ancho()/2) {
            setX(getImagen().ancho()/2);
            setDireccion(DERECHA);
        }
        setContadorDisparos(getContadorDisparos() + 1);
    }
    @Override
    public boolean quiereDisparar() {
        boolean dispara = false;
        if(isVisible() && getContadorDisparos() >= BALAPORSEGUNDO) {
            setContadorDisparos(0);
            dispara = true;
        }
        return dispara;
    }

    /**
     * @return the direccion
     */
    public int getDireccion() {
        return direccion;
    }

    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(int direccion) {
        this.direccion = direccion;
    }
}
