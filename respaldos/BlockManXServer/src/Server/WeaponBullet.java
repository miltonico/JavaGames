/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Server;

/**
 *
 * @author TIMBERWOLF
 */

public class WeaponBullet {
    
    int jugador,tipo,x,y,dx,dy,subX,subY,speed=40;
    
    public int seccionador=(4/(Tile.TILE_WIDTH/4));//min 1 cuando es 16 el tamaño del tile, max 4 cuando es 4 el tamaño del tile.
    
    public int seccionadorX=4*seccionador,seccionadorY=4*seccionador;
    
    //vida:
    public boolean broken=false,deadDimension=false;
    
    int daño=64;
    
    public WeaponBullet(int jugador,int tipo){//tipo: 1=jugador, 2=enemigo;
        
        this.jugador=jugador;
        this.tipo=tipo;
        
        if(tipo==1) this.deadDimension=ServerJuego.superJugador.get(jugador).deadDimension;
        if(tipo==2) this.deadDimension=ServerJuego.superEnemigo.get(jugador).deadDimension;
        //generar posicion de acuerdo al brazo del jugador.
        if((tipo==1 && ServerJuego.superJugador.get(jugador).direccion==1) || (tipo==2 && ServerJuego.superEnemigo.get(jugador).direccion==1)){
            this.dx=-this.speed;//en subpixeles
            this.dy=0;//en subpixeles
            this.x=(tipo==1)?ServerJuego.superJugador.get(jugador).x+ServerJuego.superJugador.get(jugador).positionParts[Integer.parseInt(ServerJuego.superJugador.get(jugador).brazoDer)][0][1]:ServerJuego.superEnemigo.get(jugador).x+ServerJuego.superEnemigo.get(jugador).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(jugador).brazoIzq)][0][1];
            this.y=(tipo==1)?ServerJuego.superJugador.get(jugador).y+ServerJuego.superJugador.get(jugador).positionParts[Integer.parseInt(ServerJuego.superJugador.get(jugador).brazoDer)][1][1]:ServerJuego.superEnemigo.get(jugador).y+ServerJuego.superEnemigo.get(jugador).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(jugador).brazoIzq)][1][1];
            
        }else{
            this.dx=this.speed;//en subpixeles
            this.dy=0;//en subpixeles
            this.x=(tipo==1)?ServerJuego.superJugador.get(jugador).x+ServerJuego.superJugador.get(jugador).positionParts[Integer.parseInt(ServerJuego.superJugador.get(jugador).brazoDer)][0][1]:ServerJuego.superEnemigo.get(jugador).x+ServerJuego.superEnemigo.get(jugador).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(jugador).brazoDer)][0][1];
            this.y=(tipo==1)?ServerJuego.superJugador.get(jugador).y+ServerJuego.superJugador.get(jugador).positionParts[Integer.parseInt(ServerJuego.superJugador.get(jugador).brazoDer)][1][1]:ServerJuego.superEnemigo.get(jugador).y+ServerJuego.superEnemigo.get(jugador).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(jugador).brazoDer)][1][1];
        }
        this.subX=this.x*seccionadorX;
        this.subY=this.y*seccionadorY;
    }
    
    public void updateBullet(){
        
        if(this.broken==true){
            return;
        }
        //mover en el eje x:
        this.subX+=this.dx;
        //mover en el eje y:
        this.subY+=this.dy;
        
        this.x=subX/seccionadorX;
        this.y=subY/seccionadorY;
        
        int quad_x=(this.x+Tile.TILE_WIDTH/2)/Tile.TILE_WIDTH;
        int quad_y=(this.y+Tile.TILE_HEIGHT/2)/Tile.TILE_HEIGHT;
        
        int map_index=0;
        
        if(quad_x>=ServerJuego.ancho || quad_x<0 || quad_y>=ServerJuego.alto || quad_y<0){
            map_index=1;
            this.subX=0;
            this.subY=0;
            this.x=0;
            this.y=0;
        }
        
        if(map_index==0) map_index=MapaServer.mapaFisico[quad_y][quad_x];
        
        for(int ejeY=-1;ejeY<=1;ejeY++){
            for(int ejeX=-1;ejeX<=1;ejeX++){
                int posX=(quad_x+ejeX)*Tile.TILE_WIDTH;
                int posY=(quad_y+ejeY)*Tile.TILE_HEIGHT;
                if(!((quad_x+ejeX)<ServerJuego.ancho && (quad_x+ejeX)>=0 && (quad_y+ejeY)<ServerJuego.alto && (quad_y+ejeY)>=0) || MapaServer.mapaFisico[quad_y+ejeY][quad_x+ejeX]==1){
                    if((posX)<(this.x+Tile.TILE_WIDTH)
                        && (posX+ Tile.TILE_WIDTH)>(this.x)
                        && (posY)<(this.y+Tile.TILE_HEIGHT)
                        && (posY+ Tile.TILE_HEIGHT)>(this.y)){
                        map_index=1;
                    }
                }
            }
        }
        
        if(map_index==1){
            this.broken=true;
            return;
        }
        
        /*
        for(int i=0;i<Juego.superJugador.size();i++){
            if((this.jugador!=i || this.tipo==2) && (this.deadDimension==Juego.superJugador.get(i).deadDimension)){
                for(int j=1;j<Juego.superJugador.get(i).positionParts.length;j++){
                    if(Math.abs(((Juego.superJugador.get(i).x+Tile.TILE_WIDTH/2+Juego.superJugador.get(i).positionParts[j][0][1])/Tile.TILE_WIDTH)-((this.x+Tile.TILE_WIDTH/2)/Tile.TILE_WIDTH))<Tile.TILE_WIDTH
                       && Math.abs(((Juego.superJugador.get(i).y+Tile.TILE_HEIGHT/2+Juego.superJugador.get(i).positionParts[j][1][1])/Tile.TILE_HEIGHT)-((this.y+Tile.TILE_HEIGHT/2)/Tile.TILE_HEIGHT))<Tile.TILE_HEIGHT){
                        Juego.superJugador.get(i).changeHealth(-this.daño);
                        this.broken=true;
                        return;
                    }
                }
            }
        }
        for(int i=0;i<Juego.superEnemigo.size();i++){
            if((this.tipo!=2 && Juego.superEnemigo.get(i).alive) && (this.deadDimension==Juego.superEnemigo.get(i).deadDimension)){
                for(int j=1;j<Juego.superEnemigo.get(i).positionParts.length;j++){
                    if(Math.abs(((Juego.superEnemigo.get(i).x+Tile.TILE_WIDTH/2+Juego.superEnemigo.get(i).positionParts[j][0][1])/Tile.TILE_WIDTH)-((this.x+Tile.TILE_WIDTH/2)/Tile.TILE_WIDTH))<Tile.TILE_WIDTH
                       && Math.abs(((Juego.superEnemigo.get(i).y+Tile.TILE_HEIGHT/2+Juego.superEnemigo.get(i).positionParts[j][1][1])/Tile.TILE_HEIGHT)-((this.y+Tile.TILE_HEIGHT/2)/Tile.TILE_HEIGHT))<Tile.TILE_HEIGHT){
                        Juego.superEnemigo.get(i).changeHealth(-this.daño);
                        this.broken=true;
                        return;
                    }
                }
            }
        }
        */
        for(int i=0;i<ServerJuego.superJugador.size();i++){
            if((this.jugador!=i || this.tipo==2) && (this.deadDimension==ServerJuego.superJugador.get(i).deadDimension)){
                for(int j=1;j<ServerJuego.superJugador.get(i).positionParts.length;j++){
                    if((ServerJuego.superJugador.get(i).x+ServerJuego.superJugador.get(i).positionParts[j][0][1])<(this.x+Tile.TILE_WIDTH)
                        && (ServerJuego.superJugador.get(i).x+ServerJuego.superJugador.get(i).positionParts[j][0][1]+ Tile.TILE_WIDTH)>(this.x)
                        && (ServerJuego.superJugador.get(i).y+ServerJuego.superJugador.get(i).positionParts[j][1][1])<(this.y+Tile.TILE_HEIGHT)
                        && (ServerJuego.superJugador.get(i).y+ServerJuego.superJugador.get(i).positionParts[j][1][1]+ Tile.TILE_HEIGHT)>(this.y)){
                        ServerJuego.superJugador.get(i).changeHealth(-this.daño);
                        this.broken=true;
                        return;
                    }
                }
            }
        }
        for(int i=0;i<ServerJuego.superEnemigo.size();i++){
            if((this.tipo!=2 && ServerJuego.superEnemigo.get(i).alive) && (this.deadDimension==ServerJuego.superEnemigo.get(i).deadDimension)){
                for(int j=1;j<ServerJuego.superEnemigo.get(i).positionParts.length;j++){
                    if((ServerJuego.superEnemigo.get(i).x+ServerJuego.superEnemigo.get(i).positionParts[j][0][1])<(this.x+Tile.TILE_WIDTH)
                        && (ServerJuego.superEnemigo.get(i).x+ServerJuego.superEnemigo.get(i).positionParts[j][0][1]+ Tile.TILE_WIDTH)>(this.x)
                        && (ServerJuego.superEnemigo.get(i).y+ServerJuego.superEnemigo.get(i).positionParts[j][1][1])<(this.y+Tile.TILE_HEIGHT)
                        && (ServerJuego.superEnemigo.get(i).y+ServerJuego.superEnemigo.get(i).positionParts[j][1][1]+ Tile.TILE_HEIGHT)>(this.y)){
                        ServerJuego.superEnemigo.get(i).changeHealth(-this.daño);
                        this.broken=true;
                        return;
                    }
                }
            }
        }
    }
}
