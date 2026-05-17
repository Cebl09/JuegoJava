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
public abstract class ElementoGrafico {
    public static final int DERECHA = 1;
    public static final int IZQUIERDA = -1;
    public static final int ABAJO = 1;
    public static final int ARRIBA = -1;
    private int x;
    private int y;
    private boolean visible;
    private Imagen imagen;
    private Lienzo lienzo;
    
    public ElementoGrafico() {
        
    }
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public boolean isVisible() {
        return visible;
    }
    public void setVisible(boolean visible) {
        this.visible = visible;
    }
    public abstract void aparecer();
    public abstract void Mueve(Entrada e);

    /**
     * @return the imagen
     */
    public Imagen getImagen() {
        return imagen;
    }

    /**
     * @param imagen the imagen to set
     */
    public void setImagen(Imagen imagen) {
        this.imagen = imagen;
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
}
