/*
 clase que almacena la informacion de cada uno de los jugadores del servidor.
 */
package BlockMan;

/**
 *
 * @author TIMBERWOLF
 */
public class JugadoresTotales {
    public int x=0,y=0,index=0,idCliente;//index=tipo tile.
    public int[][][] positionParts=new int[8][2][2];
    public JugadoresTotales(int x,int y,int index,int idCliente,int p1x,int p1y,int p2x,int p2y,int p3x,int p3y,int p4x,int p4y,int p5x,int p5y,int p6x,int p6y,int p7x,int p7y){
        this.x=x;
        this.y=y;
        this.index=index;
        this.idCliente=idCliente;
    }
}
