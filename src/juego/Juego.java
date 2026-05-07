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
    private Destructor destructor;
    private Bala balas[];
    private int puntos;
    
    public Juego() {
        lienzo = new Lienzo();
        lienzo.ponTamanioLienzo(800,600);
        lienzo.ponEscalaX(0,800);
        lienzo.ponEscalaY(0,600);
        teclado = new Entrada(getLienzo());
        heroe = new Heroe(getLienzo());
        heroe.aparecer();
        destructor = new Destructor();
        destructor.setLienzo(lienzo);
        destructor.aparecer();
        balas = new Bala[20];
        for(int i = 0; i < balas.length; i++) {
            balas[i] = new Bala(lienzo);
        }
        puntos = 0;
    }
    //porque funciona el 0 en mostrar
    public void dibujar() {
        if(heroe.isVisible()) {
            lienzo.dibujo(heroe.getX(), heroe.getY(), heroe.getImagen());
        }
        if(getDestructor().isVisible()) {
            lienzo.dibujo(destructor.getX(), destructor.getY(), destructor.getImagen());
        }
        for(int i = 0; i < balas.length; i++) {
            if(balas[i].isVisible()) {
                lienzo.dibujo(balas[i].getX(), balas[i].getY(), balas[i].getImagen());
            }
        }
        lienzo.texto(10, 600, "PUNTOS: "+ puntos);
        getLienzo().mostrar(0);
    }
    
    public void mover() {
        heroe.Mueve(teclado);
        destructor.Mueve(teclado);
        for(int i = 0; i < balas.length; i++) {
            balas[i].Mueve(teclado);
        }
        if(teclado.disparar()) {
            boolean activada = false;
            int i = 0;
            while(i < balas.length && !activada) {
                if(balas[i].getEstado() == Bala.INACTIVA) {
                    balas[i].activar(heroe.getX(), heroe.getY() - 30 , 0, -10, true);
                    activada = true;
                }
                i++;
            }
        }
        if (destructor.quiereDisparar()) {
           int balasActivadas = 0;
           int i = 0;
           while (i < balas.length && balasActivadas < 2) {
               if (balas[i].getEstado() == Bala.INACTIVA) {
                   if (balasActivadas == 0) {
                       balas[i].activar(destructor.getX() - 62, destructor.getY() - 30, 5, 5, false);
                   } else {
                       balas[i].activar(destructor.getX() + 62, destructor.getY() - 30, 5, 5, false);
                   }
                   balasActivadas++;
               }
               i++;
           }
       }
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
            miJuego.detectarColisiones();
            } catch (InterruptedException ex) {
                    System.out.println("Error: " + ex.getMessage());
            }
        }
    }
    public void detectarColisiones() {
        for(int i = 0; i < balas.length; i++) {
            if(balas[i].isVisible()) {
                if(balas[i].isEsDelHeroe() && destructor.isVisible()) {
                    if(estanCerca(balas[i], destructor)) {
                        boolean murio = destructor.recibirDanio();
                        if(murio) {
                            puntos = puntos + destructor.getPuntos();
                        }
                        balas[i].setEstado(Bala.EXPLOTANDO);
                    }
                }
                if(!balas[i].isEsDelHeroe() && heroe.isVisible()) {
                    if(estanCerca(balas[i], heroe)) {
                        heroe.perderVida();
                        balas[i].setEstado(Bala.EXPLOTANDO);
                    }
                }
            }
        }
    }
        
    private boolean estanCerca(ElementoGrafico a, ElementoGrafico b) {
        int distanciaX = Math.abs(a.getX() - b.getX());
        int distanciaY = Math.abs(a.getY() - b.getY());
        return distanciaX < 30 && distanciaY < 30;
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

    /**
     * @return the destructor
     */
    public Destructor getDestructor() {
        return destructor;
    }

    /**
     * @param destructor the destructor to set
     */
    public void setDestructor(Destructor destructor) {
        this.destructor = destructor;
    }

    /**
     * @return the balas
     */
    public Bala[] getBalas() {
        return balas;
    }

    /**
     * @param balas the balas to set
     */
    public void setBalas(Bala[] balas) {
        this.balas = balas;
    }

    /**
     * @return the puntos
     */
    public int getPuntos() {
        return puntos;
    }

    /**
     * @param puntos the puntos to set
     */
    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }
}
