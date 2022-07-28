
package Server;

import java.awt.Dimension;
import java.awt.Toolkit;

public class Camara {
    public static int[] posCamX=new int[4];
    public static int[] posCamY=new int[4];
    public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public static double width = screenSize.getWidth();
    public static double height = screenSize.getHeight();
    public static int anchoCamara=ServerJuego.camaraGrande?((int)width/Tile.TILE_WIDTH-((7*16)/Tile.TILE_WIDTH)):((int)(width/Tile.TILE_WIDTH-((10*16)/Tile.TILE_WIDTH))/2);//30
    public static int altoCamara=ServerJuego.camaraGrande?((int)height/Tile.TILE_HEIGHT-((5*16)/Tile.TILE_WIDTH)):((int)(height/Tile.TILE_HEIGHT-((5*16)/Tile.TILE_WIDTH))/(Teclas.jugadores<3?1:2));//20
    
    Personaje personaje=new Personaje();
    
    
    public void moverCamara(int jugador){
        posCamX[jugador]=personaje.getX(jugador)-((anchoCamara-1)/2-1);
        posCamY[jugador]=personaje.getY(jugador)-((altoCamara)/2-2);
        if((personaje.getX(jugador)-(anchoCamara-1)/2)<0){
            posCamX[jugador]=0;
        }
        if((personaje.getX(jugador)-(anchoCamara-1)/2-1)>=ServerJuego.ancho-26){
            posCamX[jugador]=ServerJuego.ancho-anchoCamara;
        }
        if((personaje.getY(jugador)-((altoCamara)/2-2))<0){
            posCamY[jugador]=0;
        }
        if((personaje.getY(jugador)-((altoCamara)/2)-2)>=ServerJuego.alto-23){
            posCamY[jugador]=ServerJuego.alto-altoCamara;
        }
    }
    public void moverSuperCamara(){
        for(int j=0;j<ServerJuego.superJugador.size();j++){
            int anchoCamara=ServerJuego.superJugador.get(j).anchoCamara;
            int altoCamara=ServerJuego.superJugador.get(j).altoCamara;
            
            boolean listoX=false,listoY=false;//debido a que se encuentra en un thread aparte, puede interrumpirse el servidor si se envia una posicion erronea de la camara.
            // era poco probable, pero ocurre al azar.
            if((ServerJuego.superJugador.get(j).x-(anchoCamara-5)*Tile.TILE_WIDTH/2)<0){
                ServerJuego.superJugador.get(j).posCamX=0;
                listoX=true;
            }
            if((ServerJuego.superJugador.get(j).x-(anchoCamara-5)*Tile.TILE_WIDTH/2)>=(ServerJuego.ancho-anchoCamara)*Tile.TILE_WIDTH){
                ServerJuego.superJugador.get(j).posCamX=(ServerJuego.ancho-anchoCamara)*Tile.TILE_WIDTH;
                listoX=true;
            }
            if((ServerJuego.superJugador.get(j).y-((altoCamara-4)*Tile.TILE_HEIGHT/2))<0){
                ServerJuego.superJugador.get(j).posCamY=0;
                listoY=true;
            }
            if((ServerJuego.superJugador.get(j).y-((altoCamara-4)*Tile.TILE_HEIGHT/2))>=(ServerJuego.alto-altoCamara)*Tile.TILE_HEIGHT){
                ServerJuego.superJugador.get(j).posCamY=(ServerJuego.alto-altoCamara)*Tile.TILE_HEIGHT;
                listoY=true;
            }
            if(!listoX){
                ServerJuego.superJugador.get(j).posCamX=ServerJuego.superJugador.get(j).x-((anchoCamara-5)*Tile.TILE_WIDTH/2);
            }
            if(!listoY){
                ServerJuego.superJugador.get(j).posCamY=ServerJuego.superJugador.get(j).y-(((altoCamara-5)*Tile.TILE_HEIGHT/2));
            }
            //System.out.println("posX"+Juego.superJugador.get(j).posCamX);
        }
    }
}
