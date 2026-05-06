/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package juego;

import edu.epromero.util.Imagen;
import edu.epromero.util.Lienzo;

public class Bala extends ElementoGrafico{
    public static final int INACTIVA = 0;
    public static final int VUELO = 1;
    public static final int EXPLOTANDO = 2;
    
    private int estado;
    private int dx;
    private int dy;
    private Imagen imagen;
    private Lienzo lienzo;
    private boolean esDelHeroe;
    private int cronometroExplosion;
    
    public Bala(Lienzo l) {
        lienzo = l;
        estado = INACTIVA;
        imagen = new Imagen("bala.png");
        cronometroExplosion = 0;
    }
    public void activar(int x, int y, int dx, int dy, boolean esDelHeroe) {
        setX(x);
        setY(y);
        this.dx = dx;
        this.dy = dy;
        this.setEsDelHeroe(esDelHeroe);
        estado = VUELO;
        setVisible(true);
    }
    public void aparecer() {
        setVisible(false);
        estado = INACTIVA;
    }
    @Override
    public boolean isVisible() {
        return estado != INACTIVA;
    }
    @Override
    public void Mueve(Entrada e) {
        if(estado == VUELO) {
            setX(getX() + dx);
            setY(getY() + dy);
            if(getY() < lienzo.pideLimiteYMin() ||
                    getY() > lienzo.pideLimiteYMax() ||
                    getX() < lienzo.pideLimiteXMin() ||
                    getX() > lienzo.pideLimiteXMax()) {
                estado = INACTIVA;
                setVisible(false);
            }
        }
            if(estado == EXPLOTANDO) {
                setCronometroExplosion(getCronometroExplosion() + 1);
                if(getCronometroExplosion() >= 3) {
                    estado = INACTIVA;
                    setVisible(false);
                }
            }
    }
    public int getEstado() {
        return estado;
    }
    public void setEstado(int e) {
        if(e >= 0 && e <= 2) {
            estado = e;
            if(e == EXPLOTANDO) {
                setCronometroExplosion(0);
            } 
    } else {
        throw new IllegalArgumentException("Error");
        }
    }

    /**
     * @param imagen the imagen to set
     */
    public void setImagen(Imagen imagen) {
        this.imagen = imagen;
    }

    /**
     * @param lienzo the lienzo to set
     */
    public void setLienzo(Lienzo lienzo) {
        this.lienzo = lienzo;
    }

    /**
     * @return the esDelHeroe
     */
    public boolean isEsDelHeroe() {
        return esDelHeroe;
    }

    /**
     * @param esDelHeroe the esDelHeroe to set
     */
    public void setEsDelHeroe(boolean esDelHeroe) {
        this.esDelHeroe = esDelHeroe;
    }

    /**
     * @return the cronometroExplosion
     */
    public int getCronometroExplosion() {
        return cronometroExplosion;
    }

    /**
     * @param cronometroExplosion the cronometroExplosion to set
     */
    public void setCronometroExplosion(int cronometroExplosion) {
        this.cronometroExplosion = cronometroExplosion;
    }
    
}
