/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package juego;

import edu.epromero.util.ComportamientoEnemigo;
import edu.epromero.util.Destruible;
import edu.epromero.util.Imagen;
import edu.epromero.util.Lienzo;

/**
 *
 * @author carloseduardobadillolara
 */
public abstract class NaveEnemiga extends ElementoGrafico implements Destruible{
    private int vidas;
    private Imagen imagen;
    private Lienzo lienzo;
    private int contadorDisparos;
    private int velocidad;
    
    public NaveEnemiga() {
 
    }
    public abstract void aparecer();
    public abstract void Mueve(Entrada e);
    public abstract boolean quiereDisparar();
    
    public boolean recibirDanio() {
        boolean murio = false;
        setVidas(getVidas() - 1);
        if(getVidas() == 0) {
            setVisible(false);
            murio= true;
        }
        return murio;
    }
    public int getPuntos() {
        int puntos = 0;
        ComportamientoEnemigo anotacion = 
                this.getClass().getAnnotation(ComportamientoEnemigo.class);
        if(anotacion != null) {
            puntos = anotacion.puntos();
        }
        return puntos;
    }
    @Override
    public int getVidasActuales() {
        return vidas;
    }
    /**
     * @return the vidas
     */
    public int getVidas() {
        return vidas;
    }

    /**
     * @param vidas the vidas to set
     */
    public void setVidas(int vidas) {
        this.vidas = vidas;
    }

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

    /**
     * @return the contadorDisparos
     */
    public int getContadorDisparos() {
        return contadorDisparos;
    }

    /**
     * @param contadorDisparos the contadorDisparos to set
     */
    public void setContadorDisparos(int contadorDisparos) {
        this.contadorDisparos = contadorDisparos;
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
