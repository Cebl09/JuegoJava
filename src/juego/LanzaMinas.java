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
@ComportamientoEnemigo(tipo = "LanzaMinas",resistencia = 4, puntos = 100)
public class LanzaMinas extends NaveEnemiga{
    
    private static final int CICLOS_DISPAROS = 130;
    private static final int VELOCIDAD_PERSIGUE = 15;
    private static final int ALTURA_APARECER = 100;
    private static final int MARGEN = 20;
    
    private Heroe heroeReferencia;

    public LanzaMinas() {
        setVelocidad(VELOCIDAD_PERSIGUE);
        setImagen(new Imagen("/resources/LanzaMinas.png"));
    }
    
    @Override
    public void aparecer() {
        setVisible(true);
        int xMin = (int) getLienzo().pideLimiteXMin();
        int xMax = (int) getLienzo().pideLimiteXMax();
        setX(xMin + StdRandom.uniforme(xMax - xMin));
        
        int yMax = (int) getLienzo().pideLimiteYMax();
        setY(yMax - ALTURA_APARECER);
        
        setVidas(getResistencia());
        setContadorDisparos(0);
    }
    
    @Override
        public void Mueve(Entrada e) {
        int diferenciaX = heroeReferencia.getX() - getX();
    
        if(diferenciaX > MARGEN) {
            setX(getX() + getVelocidad());
        } else if (diferenciaX < -MARGEN) {
            setX(getX() - getVelocidad());
        }
        setContadorDisparos(getContadorDisparos() + 1);
}
    
    @Override
    public boolean quiereDisparar() {
        boolean dispara = false;
        if(isVisible() && getContadorDisparos() >= CICLOS_DISPAROS) {
            setContadorDisparos(0);
            dispara = true;
        }
        return dispara;
    }
    
    
    
    
    
    /**
     * @return the heroeeReferencia
     */
    public Heroe getHeroeReferencia() {
        return heroeReferencia;
    }

    /**
     * @param heroeReferencia the heoreReferencia to set
     */
    public void setHeroeReferencia(Heroe heroeReferencia) {
        this.heroeReferencia = heroeReferencia;
    }
    
    
}
