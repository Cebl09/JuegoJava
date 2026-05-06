/*
 * Click to change this license
 * Click to edit this template
 */
package juego;

import edu.epromero.util.Lienzo;
import static java.lang.Thread.sleep;

/**
 *
 * @author carloseduardobadillolara
 */
public class Juego {

    private Lienzo lienzo;
    private Heroe heroe;
    private Entrada teclado;
    
    public Juego() {
        lienzo = new Lienzo();
        lienzo.ponTamanioLienzo(800,600);
        lienzo.ponEscalaX(0,800);
        lienzo.ponEscalaY(0,600);
        teclado = new Entrada(getLienzo());
        heroe = new Heroe(getLienzo());
        heroe.aparecer();
    }
    //porque funciona el 0 en mostrar
    public void dibujar() {
        getLienzo().dibujo(getHeroe().getX(), getHeroe().getY(), getHeroe().getImagen());
        getLienzo().mostrar(0);
    }
    public void mover() {
        getHeroe().Mueve(getTeclado());
    }
    public void limpiar() {
        getLienzo().limpia();
    }
    public static void main(String[] args) {
        Juego miJuego = new Juego();
        while(miJuego.getHeroe().isVisible()) {
            try {
            miJuego.dibujar();
            sleep(38);
            miJuego.limpiar();
            miJuego.mover(); 
            } catch (InterruptedException ex) {
                    System.out.println("Error: " + ex.getMessage());
            }
        }
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
     * @return the heroe
     */
    public Heroe getHeroe() {
        return heroe;
    }

    /**
     * @param heroe the heroe to set
     */
    public void setHeroe(Heroe heroe) {
        this.heroe = heroe;
    }

    /**
     * @return the teclado
     */
    public Entrada getTeclado() {
        return teclado;
    }

    /**
     * @param teclado the teclado to set
     */
    public void setTeclado(Entrada teclado) {
        this.teclado = teclado;
    }
}
