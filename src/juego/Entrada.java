/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package juego;

import edu.epromero.util.Lienzo;
import static java.awt.event.KeyEvent.VK_A;
import static java.awt.event.KeyEvent.VK_D;
import static java.awt.event.KeyEvent.VK_F2;
import static java.awt.event.KeyEvent.VK_LEFT;
import static java.awt.event.KeyEvent.VK_RIGHT;
import static java.awt.event.KeyEvent.VK_SPACE;

/**
 *
 * @author carloseduardobadillolara
 */
public class Entrada {
    private Lienzo lienzo;
    private boolean espacioPulsado;
    private boolean f2Pulsado;
    
    public Entrada(Lienzo l) {
        lienzo = l;
        espacioPulsado = false;
        f2Pulsado = false;
    }
    public boolean izquierda() {
        return getLienzo().fuePulsadaTecla(VK_LEFT) || getLienzo().fuePulsadaTecla(VK_A);
    }
    public boolean derecha() {
        return getLienzo().fuePulsadaTecla(VK_RIGHT) || getLienzo().fuePulsadaTecla(VK_D);
    }
    public boolean disparar() {
        boolean resultado = false;
        if(lienzo.fuePulsadaTecla(VK_SPACE)) {
            if(!espacioPulsado) {
                resultado = true;
                espacioPulsado = true;
            }
        } else {
                espacioPulsado = false;
        }        
        return resultado;
    }
    public boolean iniciar() {
        boolean resultado = false;
        if(lienzo.fuePulsadaTecla(VK_F2)) {
            if(!f2Pulsado) {
                resultado = true;
                f2Pulsado = true;
            } 
        } else {
            f2Pulsado = false;
        }
        return resultado;
    }
    public boolean ratonIzquierda() {
        return getLienzo().esRatonPresionado() && getLienzo().ratonX() < getLienzo().pideLimiteXMax() / 2;
    }
    public boolean ratonDerecha() {
        return getLienzo().esRatonPresionado() && getLienzo().ratonX() > getLienzo().pideLimiteXMax() / 2;
    }

    /**
     * @return the lienzo
     */
    public Lienzo getLienzo() {
        return lienzo;
    }

    /**
     * @param lienzo the lienzo to set
     */
    public void setLienzo(Lienzo lienzo) {
        this.lienzo = lienzo;
    }

    /**
     * @return the espacioPulsado
     */
    public boolean isEspacioPulsado() {
        return espacioPulsado;
    }

    /**
     * @param espacioPulsado the espacioPulsado to set
     */
    public void setEspacioPulsado(boolean espacioPulsado) {
        this.espacioPulsado = espacioPulsado;
    }

    /**
     * @return the f2Pulsado
     */
    public boolean isF2Pulsado() {
        return f2Pulsado;
    }

    /**
     * @param f2Pulsado the f2Pulsado to set
     */
    public void setF2Pulsado(boolean f2Pulsado) {
        this.f2Pulsado = f2Pulsado;
    }
}
