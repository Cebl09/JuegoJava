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
    private int vida;
    private int velocidad;
    private Imagen imagen;
    private Lienzo lienzo;
    
    public Heroe(Lienzo l) {
        vida = 3;
        velocidad = 10;
        imagen = new Imagen("Heroe.png");
        lienzo = l;
    }
    public int getVida() {
        return vida;
    }
    public void setVida(int vida) {
        this.vida = vida;
    }
    public Imagen getImagen() {
        return imagen;
    }
    //Acaban los getter y setters
    public void perderVida() {
        vida = vida-1;
        if(vida == 0) {
            setVisible(false);
        }
    }
    public void inicia() {
        vida = 3;
        setVisible(true);
    }
    @Override
    public void aparecer() {
        setVisible(true);
        setX((int)(lienzo.pideLimiteXMax() + lienzo.pideLimiteXMin())/2);
        setY((int)lienzo.pideLimiteYMin() + (imagen.alto()/2) + 10);    }
    @Override
    public void Mueve(Entrada e) {
       if(e.izquierda() || e.ratonIzquierda() ) {
           if(getX() > lienzo.pideLimiteXMin()) {
               setX(getX() - velocidad);
           }
       }
       if(e.derecha() || e.ratonDerecha()) {
           if(getX() < lienzo.pideLimiteXMax()) {
               setX(getX() + velocidad);
           }
       }
    }
}
