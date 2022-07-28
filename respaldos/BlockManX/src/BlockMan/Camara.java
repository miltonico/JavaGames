
package BlockMan;

import java.awt.Dimension;
import java.awt.Toolkit;

public class Camara {
    public static int[] posCamX=new int[4];
    public static int[] posCamY=new int[4];
    public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public static double width = screenSize.getWidth();
    public static double height = screenSize.getHeight();
    public static int anchoCamara=Juego.camaraGrande?((int)width/Tile.TILE_WIDTH-((7*16)/Tile.TILE_WIDTH)):((int)(width/Tile.TILE_WIDTH-((10*16)/Tile.TILE_WIDTH))/2);//30
    public static int altoCamara=Juego.camaraGrande?((int)height/Tile.TILE_HEIGHT-((5*16)/Tile.TILE_WIDTH)):((int)(height/Tile.TILE_HEIGHT-((5*16)/Tile.TILE_WIDTH))/(Teclas.jugadores<3?1:2));//20
    
    Personaje personaje=new Personaje();
    
    
    public void moverCamara(int jugador){
        posCamX[jugador]=personaje.getX(jugador)-((anchoCamara-1)/2-1);
        posCamY[jugador]=personaje.getY(jugador)-((altoCamara)/2-2);
        if((personaje.getX(jugador)-(anchoCamara-1)/2)<0){
            posCamX[jugador]=0;
        }
        if((personaje.getX(jugador)-(anchoCamara-1)/2-1)>=Juego.ancho-26){
            posCamX[jugador]=Juego.ancho-anchoCamara;
        }
        if((personaje.getY(jugador)-((altoCamara)/2-2))<0){
            posCamY[jugador]=0;
        }
        if((personaje.getY(jugador)-((altoCamara)/2)-2)>=Juego.alto-23){
            posCamY[jugador]=Juego.alto-altoCamara;
        }
    }
    public void moverSuperCamara(){
        for(int j=0;j<Juego.superJugador.size();j++){
            Juego.superJugador.get(j).posCamX=Juego.superJugador.get(j).x-((anchoCamara-5)*Tile.TILE_WIDTH/2);
            Juego.superJugador.get(j).posCamY=Juego.superJugador.get(j).y-(((altoCamara-5)*Tile.TILE_HEIGHT/2));
            if((Juego.superJugador.get(j).x-(anchoCamara-5)*Tile.TILE_WIDTH/2)<0){
                Juego.superJugador.get(j).posCamX=0;
            }
            if((Juego.superJugador.get(j).x-(anchoCamara-5)*Tile.TILE_WIDTH/2)>=(Juego.ancho-anchoCamara)*Tile.TILE_WIDTH){
                Juego.superJugador.get(j).posCamX=(Juego.ancho-anchoCamara)*Tile.TILE_WIDTH;
            }
            if((Juego.superJugador.get(j).y-((altoCamara-4)*Tile.TILE_HEIGHT/2))<0){
                Juego.superJugador.get(j).posCamY=0;
            }
            if((Juego.superJugador.get(j).y-((altoCamara-4)*Tile.TILE_HEIGHT/2))>=(Juego.alto-altoCamara)*Tile.TILE_HEIGHT){
                Juego.superJugador.get(j).posCamY=(Juego.alto-altoCamara)*Tile.TILE_HEIGHT;
            }
            //System.out.println("posX"+Juego.superJugador.get(j).posCamX);
        }
    }
}
