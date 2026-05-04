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
    
    public Entrada(Lienzo l) {
        lienzo = l;
    }
    public boolean izquierda() {
        return lienzo.fuePulsadaTecla(VK_LEFT) || lienzo.fuePulsadaTecla(VK_A);
    }
    public boolean derecha() {
        return lienzo.fuePulsadaTecla(VK_RIGHT) || lienzo.fuePulsadaTecla(VK_D);
    }
    public boolean disparar() {
        return lienzo.fuePulsadaTecla(VK_SPACE);
    }
    public boolean iniciar() {
        return lienzo.fuePulsadaTecla(VK_F2);
    }
    public boolean ratonIzquierda() {
        return lienzo.esRatonPresionado() && lienzo.ratonX() < lienzo.pideLimiteXMax() / 2;
    }
    public boolean ratonDerecha() {
        return lienzo.esRatonPresionado() && lienzo.ratonX() > lienzo.pideLimiteXMax() / 2;
    }
}
