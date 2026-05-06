/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package juego;

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
}
