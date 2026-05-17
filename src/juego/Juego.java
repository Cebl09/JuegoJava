/*
 * Click to change this license
 * Click to edit this template
 */
package juego;

import edu.epromero.util.FabricaAudio;
import edu.epromero.util.Lienzo;
import static java.lang.Thread.sleep;
import java.awt.Font;
import java.io.File;
import java.io.InputStream;
import java.net.URL;

/**
 *
 * @author carloseduardobadillolara
 */
public class Juego {
    public static final int INICIO = 0;
    public static final int JUGANDO = 1;
    public static final int GAMEOVER = 2;
    public static final int CENTRO_X = 400;
    public static final int CENTRO_Y = 300;
    public static final int ANCHO_VENTANA = 800;
    public static final int ALTO_VENTANA = 600;
    public static final int ORIGEN_ESCALA = 0;
    public static final int POS_VENTANA_X = 300;
    public static final int POS_VENTANA_Y = 100;

    private Lienzo lienzo;
    private Heroe heroe;
    private NaveEnemiga[] naves;
    private Entrada teclado;
    private Bala balas[];
    private int puntos;
    private FabricaAudio fabricaAudio;
    private int estado;
    private Font fuenteTitulo;
    private Font fuenteHUD;
    
    public Juego() {
        lienzo = new Lienzo();
        lienzo.ponTamanioLienzo(ANCHO_VENTANA, ALTO_VENTANA);
        lienzo.ponEscalaX(ORIGEN_ESCALA, ANCHO_VENTANA);
        lienzo.ponEscalaY(ORIGEN_ESCALA, ALTO_VENTANA);
        lienzo.estaVentana().setLocation(POS_VENTANA_X, POS_VENTANA_Y);
        cargarFuente();
        teclado = new Entrada(lienzo);
        fabricaAudio = new FabricaAudio();
        heroe = new Heroe(lienzo);
        heroe.aparecer();
        naves = new NaveEnemiga[3];
        naves[0] = new Destructor();
        naves[1] = new AveDePresa();
        naves[2] = new LanzaMinas();
        for(int i = 0; i < naves.length; i++) {
            naves[i].setLienzo(lienzo);
            naves[i].aparecer();
        }
        ((LanzaMinas) naves[2]).setHeroeReferencia(heroe);
        balas = new Bala[20];
        for(int i = 0; i < balas.length; i++) {
            balas[i] = new Bala(lienzo);
        }
        puntos = 0;
        estado = INICIO;
    }
    public void dibujar() {
        if (estado == INICIO) {
            dibujarPantallaInicio();
        } else if (estado == JUGANDO) {
            dibujarPartida();
        } else if (estado == GAMEOVER) {
            dibujarGameOver();
        }
        getLienzo().mostrar(0);
    }
    public void dibujarPantallaInicio() {
        lienzo.ponFuente(fuenteTitulo);
        lienzo.texto(CENTRO_X, CENTRO_Y + 100, "INVASION GALACTICA");
        lienzo.texto(CENTRO_X, CENTRO_Y, "Presiona F2 para empezar");
        lienzo.texto(CENTRO_X, CENTRO_Y - 50, "Flechas ó A/D para moverse");
        lienzo.texto(CENTRO_X, CENTRO_Y - 80, "Espacio: disparar");
    }
    public void dibujarPartida() {
        if(heroe.isVisible()) {
            lienzo.dibujo(heroe.getX(), heroe.getY(), heroe.getImagen());
        }
        for(int i = 0; i < getNaves().length; i++) {
            if(getNaves()[i].isVisible()) {
                lienzo.dibujo(getNaves()[i].getX(), getNaves()[i].getY(), getNaves()[i].getImagen());
            }
        }
        for (int i = 0; i < balas.length; i++) {
            if(balas[i].isVisible()) {
                lienzo.dibujo(balas[i].getX(), balas[i].getY(), balas[i].getImagen());
            }
        }
        lienzo.ponFuente(fuenteHUD);
        lienzo.texto(50, 600, "PUNTOS: "+ puntos);
        lienzo.texto(200, 600, "VIDA: "+ heroe.getVida());
        lienzo.texto(340, 600, "NIVEL: 1");
    }
    public void dibujarGameOver() {
        lienzo.ponFuente(fuenteTitulo);
        lienzo.texto(CENTRO_X, CENTRO_Y +100, "GAME OVER");
        lienzo.texto(CENTRO_X, CENTRO_Y, "Puntaje Final: "+puntos);
        lienzo.texto(CENTRO_X, CENTRO_Y -100, "Presiona F2 para reniciar");
    }
    
    public void mover() {
        if(estado == INICIO) {
            moverPantallaInicio();
        } else if (estado == JUGANDO) {
            moverPartida();
        } else if(estado == GAMEOVER) {
            moverGameOver();
        }
    }
    public void moverPantallaInicio() {
        if(teclado.iniciar()) {
            estado = JUGANDO;
        }
    }
    
    public void moverPartida() {
        heroe.Mueve(teclado); 
        for(int i = 0; i < getNaves().length; i++) {
            if(!naves[i].isVisible()) {
                getNaves()[i].aparecer();
            }
            getNaves()[i].Mueve(teclado);
        }
        for (int i = 0; i < balas.length; i++) {
            balas[i].Mueve(teclado);
        }
        if(teclado.disparar()) {
            boolean activada = false;
            int i = 0;
            while(i < balas.length && !activada) {
                if(balas[i].getEstado() == Bala.INACTIVA) {
                    balas[i].activar(heroe.getX(), heroe.getY() -15 , 0, -20, true);
                    activada = true;
                    URL urlSonido = getClass().getResource("/resources/disparo.wav");
                    getFabricaAudio().reproducir(urlSonido);
                }
                i++;
            }
        }
        if(getNaves()[0].quiereDisparar()) {
            int balasActivadas = 0;
            int i = 0;
            while(i < balas.length && balasActivadas < 2) {
                if(balas[i].getEstado() == Bala.INACTIVA) {
                    if(balasActivadas == 0) {
                        balas[i].activar(getNaves()[0].getX() - 62, getNaves()[0].getY() - 30, 5, 5, false);
                    } else {
                        balas[i].activar(getNaves()[0].getX() + 62, getNaves()[0].getY() - 30, 5, 5, false);
                    }
                    balasActivadas++;
                }
                i++;
            }
        }
        if(getNaves()[1].quiereDisparar()) {
            boolean activada = false;
            int i = 0;
            while(i < balas.length && !activada) {
                if(balas[i].getEstado() == Bala.INACTIVA) {
                    balas[i].activar(getNaves()[1].getX(), getNaves()[1].getY() - 30, 0, 10, false);
                    activada = true;
                }
                i++;
            }
        }
        if(getNaves()[2].quiereDisparar()) {
            boolean activada = false;
            int i = 0;
            while(i < balas.length && !activada) {
                if(balas[i].getEstado() == Bala.INACTIVA) {
                    balas[i].activar(getNaves()[2].getX(), getNaves()[2].getY() - 30, 0, 10, false);
                    activada = true;
                }
                i++;
            }
        }
        if(heroe.getVida() <= 0) {
            estado = GAMEOVER;
        }
    }
    
    public void moverGameOver() {
        if(teclado.iniciar()) {
            reiniciarJuego();
            estado = JUGANDO;
        }
    }
    public void reiniciarJuego() {
        heroe.inicia();
        heroe.aparecer();
        
        for(int i = 0; i < naves.length; i++) {
            naves[i].aparecer();
        }
        for(int i = 0; i < balas.length; i++) {
            balas[i].setEstado(Bala.INACTIVA);
            balas[i].setVisible(false);
        }
        puntos = 0;
    }
    public void limpiar() {
        getLienzo().limpia();
    }
    public static void main(String[] args) {
        Juego miJuego = new Juego();
        while(true) {
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
            if(balas[i].getEstado() == Bala.VUELO) {
        for(int j = 0; j < getNaves().length; j++) {
           if(balas[i].isEsDelHeroe() && getNaves()[j].isVisible()) {
               if(estanCerca(balas[i], getNaves()[j])) {
                   boolean murio = getNaves()[j].recibirDanio();
                   if(murio) {
                       puntos = puntos + getNaves()[j].getPuntos();
                                URL urlSonido = getClass().getResource("/resources/Explosion.wav");
                                getFabricaAudio().reproducir(urlSonido);
                   }
                   balas[i].setEstado(Bala.EXPLOTANDO);
               }
           }
       }
        if(!balas[i].isEsDelHeroe() && heroe.isVisible()) {
            if(estanCerca(balas[i], heroe)) {
                heroe.perderVida();
                if(heroe.getVida() <= 0) {
                            URL urlSonido = getClass().getResource("/resources/game_over.wav");
                            getFabricaAudio().reproducir(urlSonido);
                } else {
                        URL urlSonido = getClass().getResource("/resources/Herida.wav");
                        getFabricaAudio().reproducir(urlSonido);
                }
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
    
    private void cargarFuente() {
        try {
            InputStream streamFuente = getClass().getResourceAsStream("/resources/PressStart2P-Regular.ttf");
            Font base = Font.createFont(Font.TRUETYPE_FONT, streamFuente);
            fuenteTitulo = base.deriveFont(20f);
            fuenteHUD = base.deriveFont(12f);
        } catch (Exception ex) {
            System.out.println("Error al cargar la fuente "+ex.getMessage());
            fuenteTitulo = new Font("Monospaced", Font.BOLD, 32);
            fuenteHUD = new Font("Monospaced", Font.BOLD, 12);
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

    /**
     * @return the naves
     */
    public NaveEnemiga[] getNaves() {
        return naves;
    }

    /**
     * @param naves the naves to set
     */
    public void setNaves(NaveEnemiga[] naves) {
        this.naves = naves;
    }

    /**
     * @return the fabricaAudio
     */
    public FabricaAudio getFabricaAudio() {
        return fabricaAudio;
    }

    /**
     * @param fabricaAudio the fabricaAudio to set
     */
    public void setFabricaAudio(FabricaAudio fabricaAudio) {
        this.fabricaAudio = fabricaAudio;
    }

    
}
