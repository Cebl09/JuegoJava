/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package juego;

import edu.epromero.util.Imagen;
import edu.epromero.util.Lienzo;
/**
 *
 * @author carloseduardobadillolara
 */
public class Heroe extends ElementoGrafico {
    public static final int MITAD = 2;
    public static final int DENTRO = 10;
    private int vida;
    private int velocidad;
    
    public Heroe(Lienzo l) {
        vida = 3;
        velocidad = 10;
        setImagen(new Imagen("/resources/Heroe.png"));
        setLienzo(l);  
    }
    public int getVida() {
        return vida;
    }
    public void setVida(int vida) {
        this.vida = vida;
    }
    //Acaban los getter y setters
    public void perderVida() {
        setVida(getVida() - 1);
        if(getVida() <= 0) {
            setVisible(false);
        }
    }
    public void inicia() {
        setVida(3);
        setVisible(true);
    }
    @Override
    public void aparecer() {
        setVisible(true);
        setX((int)(getLienzo().pideLimiteXMax() + getLienzo().pideLimiteXMin())/MITAD);
        setY((int)getLienzo().pideLimiteYMin() + (getImagen().alto()/MITAD) + DENTRO);
    }
    @Override
    public void Mueve(Entrada e) {
       if(e.izquierda() || e.ratonIzquierda() ) {
           if(getX() > getLienzo().pideLimiteXMin()) {
               setX(getX() - getVelocidad());
           }
       }
       if(e.derecha() || e.ratonDerecha()) {
           if(getX() < getLienzo().pideLimiteXMax()) {
               setX(getX() + getVelocidad());
           }
       }
    }

    /**
     * @return the velocidad
     */
    public int getVelocidad() {
        return velocidad;
    }

    /**
     * @param velocidad the velocidad to set
     */
    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }
}
  
