
package Server;
public class Items {
    public boolean tomado;
    public int subX,subY,posicionItemX=0,posicionItemY=0,tipoItem=0,velocidad,seccionador;//no es estatico para que puedan haber muchas instancias de esta clase...
    public Items(int item,int x, int y){
        this.seccionador=(4/(Tile.TILE_WIDTH/4));//min 1 cuando es 16 el tamaño del tile, max 4 cuando es 4 el tamaño del tile.
        this.tipoItem=item;
        this.posicionItemX=x*Tile.TILE_WIDTH;
        this.posicionItemY=y*Tile.TILE_HEIGHT;
        this.subX=this.posicionItemX*seccionador;
        this.subY=this.posicionItemY*seccionador;
        this.tomado=false;
        this.velocidad=4;
    }
    public void actualizar(){
        if(this.tomado==true){
            return;
        }
        int quad_x=(this.subX/Tile.TILE_WIDTH)/seccionador;
        int quad_y=((this.subY/Tile.TILE_HEIGHT)/seccionador)+1;
        int resto=(this.subY/this.seccionador)%Tile.TILE_HEIGHT;
        boolean bloqueado=false;//bloque abajo.
        if(quad_y<ServerJuego.alto && quad_x<ServerJuego.ancho && quad_y>=0 && quad_x>=0){
            if(MapaServer.mapaFisico[quad_y][quad_x]==1){
                bloqueado=true;
            }
        }else{
            bloqueado=true;
        }
        if(!bloqueado){
            this.subY+=this.velocidad;
        }else{
            if(resto!=0){
                this.subY=((this.subY/this.seccionador)/Tile.TILE_HEIGHT)*Tile.TILE_HEIGHT*this.seccionador;
            }
        }
        this.posicionItemY=this.subY/this.seccionador;
        for(int i=0;i<ServerJuego.superJugador.size();i++){
            if(ServerJuego.superJugador.get(i).deadDimension==false){
                for(int j=1;j<ServerJuego.superJugador.get(i).positionParts.length;j++){
                    if((ServerJuego.superJugador.get(i).x+Tile.TILE_WIDTH/2+ServerJuego.superJugador.get(i).positionParts[j][0][1])/Tile.TILE_WIDTH==this.posicionItemX/Tile.TILE_WIDTH
                       && (ServerJuego.superJugador.get(i).y+Tile.TILE_HEIGHT/2+ServerJuego.superJugador.get(i).positionParts[j][1][1])/Tile.TILE_HEIGHT==this.posicionItemY/Tile.TILE_HEIGHT){
                        switch(this.tipoItem){
                            case 37://capsula curativa.
                                ServerJuego.superJugador.get(i).changeHealth((ServerJuego.superJugador.get(i).healthMultiplier*ServerJuego.superJugador.get(i).baseHealth)/2);
                                break;
                            case 44://mejora de vida.
                                ServerJuego.superJugador.get(i).baseHealth++;
                                ServerJuego.superJugador.get(i).healthLimit=ServerJuego.superJugador.get(i).healthMultiplier*ServerJuego.superJugador.get(i).baseHealth;
                                ServerJuego.superJugador.get(i).health=ServerJuego.superJugador.get(i).healthMultiplier*ServerJuego.superJugador.get(i).baseHealth;
                                break;
                            case 49://municion de hacha.
                                ServerJuego.superJugador.get(i).changeAxeMunition(5);
                                break;
                            case 26://municion de pelota.
                                ServerJuego.superJugador.get(i).changeBallMunition(5);
                                break;
                        }
                        this.tomado=true;
                        return;
                    }
                }
            }
        }
    }
}
