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
@ComportamientoEnemigo(tipo="AveDePresa", resistencia = 3, puntos = 10)
public class AveDePresa extends NaveEnemiga {
    private static final int CICLOS_DISPAROS = 78;
    private static final int VELOCIDAD_AVE = 3;
    
    public AveDePresa() {
        setVelocidad(VELOCIDAD_AVE);
        setImagen(new Imagen ("/resources/avepresa.png"));
    }
    
    @Override
    public void aparecer() {
        setVisible(true);
        
        int xMin = ((int) getLienzo().pideLimiteXMin());
        int xMax = ((int) getLienzo().pideLimiteXMax());
        setX(xMin + StdRandom.uniforme(xMax - xMin));
        
        setVidas(getResistencia());
        setContadorDisparos(0);
        setY((int)getLienzo().pideLimiteYMax() - getImagen().alto());
    }
    
    @Override
    public void Mueve(Entrada e) {
        setY(getY() - getVelocidad());
        if(getY() < getLienzo().pideLimiteYMin()) {
            int xMin = (int) getLienzo().pideLimiteXMin();
            int xMax = (int) getLienzo().pideLimiteXMax();
            setX(xMin + StdRandom.uniforme(xMax - xMin));
            setY((int) getLienzo().pideLimiteYMax() - getImagen().alto());
        }
        setContadorDisparos(getContadorDisparos()+ 1);
    }
    
    public boolean quiereDisparar() {
        boolean dispara = false;
        if(isVisible() && getContadorDisparos() >= CICLOS_DISPAROS) {
            setContadorDisparos(0);
            dispara = true;
        }
        return dispara;
    }
    
}
