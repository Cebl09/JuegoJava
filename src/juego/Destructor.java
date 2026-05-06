/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package juego;

import edu.epromero.util.ComportamientoEnemigo;
import edu.epromero.util.Imagen;

/**
 *
 * @author carloseduardobadillolara
 */
@ComportamientoEnemigo(tipo="Destructor", resistencia = 1, puntos = 50)
public class Destructor extends NaveEnemiga {
    private int direccion;
    private Imagen imagen;
    
    public Destructor() {
        setVelocidad(5);
        imagen = new Imagen("Destructor.png");
        direccion = DERECHA;
    }
    @Override
    public void aparecer() {
        setVisible(true);
        setX((int)(getLienzo().pideLimiteXMin() + 
            Math.random() * (getLienzo().pideLimiteXMax() - getLienzo().pideLimiteXMin())));
        setVidas(1);
        setY((int)getLienzo().pideLimiteYMin());
    }
    @Override
    public void Mueve(Entrada e) {
        setX(getX() + getDireccion() *getVelocidad());
        if(getX() >= getLienzo().pideLimiteXMax()) {
            setDireccion(IZQUIERDA);
        }
        if(getX() <= getLienzo().pideLimiteXMin()) {
            setDireccion(DERECHA);
        }
            setContadorDisparos(getContadorDisparos() +1);
    }
    @Override
    public boolean quiereDisparar() {
        boolean dispara = false;
        if(getContadorDisparos() >= 180) {
            setContadorDisparos(0);
            dispara = true;
        }
        return dispara;
    }
    public Imagen getImagen() {
        return imagen;
    }

    /**
     * @return the direccion
     */
    public int getDireccion() {
        return direccion;
    }

    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(int direccion) {
        this.direccion = direccion;
    }

    /**
     * @param imagen the imagen to set
     */
    public void setImagen(Imagen imagen) {
        this.imagen = imagen;
    }
}
