/*
 * Click to change this license
 * Click to edit this template
 */
package juego;

import edu.epromero.util.FabricaAudio;
import edu.epromero.util.Lienzo;
import static java.lang.Thread.sleep;
import java.awt.Font;
import static java.awt.event.KeyEvent.VK_A;
import static java.awt.event.KeyEvent.VK_BACK_SPACE;
import static java.awt.event.KeyEvent.VK_ENTER;
import static java.awt.event.KeyEvent.VK_Z;
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
    public static final int CAPTURANDO = 3;
    public static final int HUD_PUNTOS_X = 50;
    public static final int HUD_VIDA_X = 200;
    public static final int HUD_NIVEL_X = 340;
    public static final int HUD_Y = 600;
    public static final int OFFSET_TITULO = 100;
    public static final int OFFSET_LINEA_1 = 50;
    public static final int OFFSET_LINEA_2 = 80;
    public static final int GO_TITULO = 200;
    public static final int GO_PUNTAJE = 150;
    public static final int GO_TOP_INICIO = 110;
    public static final int GO_REINICIAR = 160;
    public static final int GO_SALTO = 22;
    public static final int CAP_TITULO = 50;
    public static final int CAP_NOMBRE = 50;
    public static final int CAP_ENTER = 100;
    public static final int HEROE_BALA_OFFSET_Y = 15;
    public static final int HEROE_BALA_VEL_Y = -20;
    public static final int NAVE_BALA_OFFSET_X = 62;
    public static final int NAVE_BALA_OFFSET_Y = 30;
    public static final int DESTRUCTOR_BALA_VEL = 5;
    public static final int NAVE_BALA_VEL_Y = 10;
    public static final int BALA_VEL_CERO = 0;
    public static final int MAX_BALAS_DESTRUCTOR = 2;

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
    private String nombreJugador;
    private boolean teclaLetraPulsada;
    private GestorPuntajes gestorPuntajes;
    
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
        nombreJugador = "";
        teclaLetraPulsada= false;
        gestorPuntajes = new GestorPuntajes();
    }
    public void dibujar() {
        if (estado == INICIO) {
            dibujarPantallaInicio();
        } else if (estado == JUGANDO) {
            dibujarPartida();
        } else if (estado == GAMEOVER) {
            dibujarGameOver();
        } else if(estado == CAPTURANDO) {
            dibujarCaptura();
        }
        getLienzo().mostrar(0);
    }
    public void dibujarPantallaInicio() {
        lienzo.ponFuente(fuenteTitulo);
        lienzo.texto(CENTRO_X, CENTRO_Y + OFFSET_TITULO, "INVASION GALACTICA");
        lienzo.texto(CENTRO_X, CENTRO_Y, "Presiona F2 para empezar");
        lienzo.texto(CENTRO_X, CENTRO_Y - OFFSET_LINEA_1, "Flechas ó A/D para moverse");
        lienzo.texto(CENTRO_X, CENTRO_Y - OFFSET_LINEA_2, "Espacio: disparar");
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
        lienzo.texto(HUD_PUNTOS_X, HUD_Y, "PUNTOS: "+ puntos);
        lienzo.texto(HUD_VIDA_X, HUD_Y, "VIDA: "+ heroe.getVida());
        lienzo.texto(HUD_NIVEL_X, HUD_Y, "NIVEL: 1");
    }
    public void dibujarGameOver() {
        lienzo.ponFuente(fuenteTitulo);
        lienzo.texto(CENTRO_X, CENTRO_Y + GO_TITULO, "GAME OVER");
        lienzo.texto(CENTRO_X, CENTRO_Y + GO_PUNTAJE, "Puntaje Final: "+puntos);
        
        Puntaje[] tabla = gestorPuntajes.getTop();
        for(int i = 0; i < tabla.length; i++) {
            if(tabla[i] != null) {
                int y = CENTRO_Y + GO_TOP_INICIO - (i * GO_SALTO);
                lienzo.texto(CENTRO_X, y,(i+1) + "." + tabla[i].toString());
            }
        }
        lienzo.texto(CENTRO_X, CENTRO_Y - GO_REINICIAR, "Presiona F2 para reniciar");
    }
    
    public void dibujarCaptura() {
        lienzo.ponFuente(fuenteTitulo);
        lienzo.texto(CENTRO_X, CENTRO_Y + CAP_TITULO, "NUEVO RECORD");
        lienzo.ponFuente(fuenteHUD);
        lienzo.texto(CENTRO_X, CENTRO_Y, "Escribe tu nombre:");
        lienzo.texto(CENTRO_X, CENTRO_Y - CAP_NOMBRE, nombreJugador);
        lienzo.texto(CENTRO_X, CENTRO_Y - CAP_ENTER, "Enter para confirmar");
    }
    
    public void mover() {
        if(estado == INICIO) {
            moverPantallaInicio();
        } else if (estado == JUGANDO) {
            moverPartida();
        } else if(estado == GAMEOVER) {
            moverGameOver();
        } else if(estado == CAPTURANDO) {
            moverCaptura();
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
                    balas[i].activar(heroe.getX(), heroe.getY() - HEROE_BALA_OFFSET_Y , BALA_VEL_CERO, HEROE_BALA_VEL_Y, true);
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
            while(i < balas.length && balasActivadas < MAX_BALAS_DESTRUCTOR) {
                if(balas[i].getEstado() == Bala.INACTIVA) {
                    if(balasActivadas == 0) {
                        balas[i].activar(getNaves()[0].getX() - NAVE_BALA_OFFSET_X, getNaves()[0].getY() - NAVE_BALA_OFFSET_Y, DESTRUCTOR_BALA_VEL, DESTRUCTOR_BALA_VEL, false);
                    } else {
                        balas[i].activar(getNaves()[0].getX() + NAVE_BALA_OFFSET_X, getNaves()[0].getY() - NAVE_BALA_OFFSET_Y, DESTRUCTOR_BALA_VEL, DESTRUCTOR_BALA_VEL, false);
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
                    balas[i].activar(getNaves()[1].getX(), getNaves()[1].getY() - NAVE_BALA_OFFSET_Y, BALA_VEL_CERO, NAVE_BALA_VEL_Y, false);
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
                    balas[i].activar(getNaves()[2].getX(), getNaves()[2].getY() - NAVE_BALA_OFFSET_Y, BALA_VEL_CERO, NAVE_BALA_VEL_Y, false);
                    activada = true;
                }
                i++;
            }
        }
        if(heroe.getVida() <= 0) {
            if(gestorPuntajes.califica(puntos)) {
                estado = CAPTURANDO;
                teclaLetraPulsada = true;
            } else {
                estado = GAMEOVER;
            }
        }
    }
    
    public void moverGameOver() {
        if(teclado.iniciar()) {
            reiniciarJuego();
            estado = JUGANDO;
        }
    }
    
    public void moverCaptura() {
        boolean algunaPulsada = false;
        for (int tecla = VK_A; tecla <= VK_Z; tecla++) {
            if (lienzo.fuePulsadaTecla(tecla)) {
                algunaPulsada = true;
                if(!teclaLetraPulsada) {
                    char letra = (char) tecla;
                    nombreJugador = nombreJugador + letra;
                    teclaLetraPulsada = true;
                }
            }
        }
        if(!algunaPulsada) {
            teclaLetraPulsada = false;
        }
        if(lienzo.fuePulsadaTecla(VK_BACK_SPACE) && !teclaLetraPulsada) {
            if(nombreJugador.length() > 0) {
                nombreJugador = nombreJugador.substring(0, nombreJugador.length() -1);
                teclaLetraPulsada = true;
            }
        }
        
        if(lienzo.fuePulsadaTecla(VK_ENTER)) {
            gestorPuntajes.insertar(nombreJugador, puntos);
            estado = GAMEOVER;
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
        nombreJugador = "";
        teclaLetraPulsada = false;
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
