package BricksWar;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;



public class Juego extends Canvas implements KeyListener {
	/**
	 * hecho por miltiton
	 */

    public static boolean estaCerrada=false;
	private static boolean yaTermino=false;
	
	Menu menu;
	
	private Frame ventana=new Frame();
	//private Sprite sprite;
	
	//reemplazando:
	//private PersonajeJugable otroSprite=new PersonajeJugable();
	
	private Personaje sprite=new Personaje();
	
	long tiempo=System.currentTimeMillis();
	MapaTiles mapa;
	
	//numero de niveles (documentos de texto):
	int niveles=3;
	private int contador=0;
	public boolean seDibuja=true;
	public static boolean corriendo=false;
	public static int nivel=1;
	Personaje personaje=new Personaje();
	
	public static boolean computadora=true;
	
	Jugador1 j1=new Jugador1();
	
	Bot bot=new Bot();
	Jugador2 j2=new Jugador2();
	
	Balas balas=new Balas();
	
	public Juego(){
	    //ventana.setSize(330,350);
		
		personaje.iniciar();
		
		ventana.setSize(726,748);
	    ventana.add(this);
	    ventana.setVisible(true);
	    ventana.setResizable(false);
	    this.setBackground(new Color(0,0,0));
	    ventana.addWindowListener(new WindowAdapter(){ public void windowClosing(WindowEvent e){  System.exit(0); } });
	    // ATENCION ATENCION *************************************************
	    //(this.requestFocus();)
	    this.requestFocus(); // Focalizamos hacia nuestro objeto.
	    //reemplazando:
	    //this.addKeyListener(otroSprite); // Direccionamos la captura a nuestro Pj
	    this.addKeyListener(j1); // Direccionamos la captura a nuestro Pj
	    
	    //reemplazar si es bot.
	    if(computadora==false){
	    	this.addKeyListener(j2); // Direccionamos la captura a nuestro Pj
	    }
	    
	    
	    // *******************************************************************
	    while(nivel<=niveles){
	    	corriendo=true;
	    	mapa = MapaTiles.DesdeArchivo("C:/map"+"x"+".txt");//este es el primero para los bots.3
		    //sprite.setSprite("cara.png");
		    //sprite.setY(this.getHeight()/2);
	    	
		    //if(mapa.map.length==20){
		    //	otroSprite.setSprite("C:/Users/milton/workspace/TetrisMan/tile.png");
		    //}else{
		    //	otroSprite.setSprite("C:/Users/milton/workspace/TetrisMan/miniTile.png");
		    //}
	    	
	    	//reemplazando:
	    	//otroSprite.setSprite("C:/tile.png");
		    //otroSprite.setX(1);
		    //otroSprite.setY(1);
	    	
	    	personaje.setX(0,1);
	    	personaje.setY(0,9);
	    	
	    	//personaje.setX(0,1);
	    	//personaje.setY(0,1);
	    	
	    	personaje.setX(1,36);
	    	personaje.setY(1,9);
		    
		    while(corriendo==true) // BUCLE INFINITO DEL JUEGO
		    {      
		    	
		        if (System.currentTimeMillis()-tiempo>30) { // actualizamos cada 20 milisegundos
		        	
		        	
		        	//jugador 1:
		        	
		        	mapa.borrarJugador(0);
		        	//reemplazando:
		        	//otroSprite.actualiza(); // Mueve nuestro Pj
		        	
		            j1.actualiza(); // Mueve nuestro Pj
		            mapa.DibujaJugador(0);
		            
		            
		            //jugador 2:
		            
		            mapa.borrarJugador(1);
		            //bot.
		            
		            if(computadora==false){
		            	j2.actualiza(); // Mueve nuestro Pj
		            }else{
		            	bot.actualiza();
		            }
		            mapa.DibujaJugador(1);
		            
		            
		            //if (sprite.getX()>this.getWidth()) sprite.setX(0);
		            //else sprite.setX(sprite.getX()+1);
		            tiempo=System.currentTimeMillis();
		            dibuja(this.getGraphics());
		            
		        }
		    }
	    }
	}
	public static void actualizarNivel(int n){
		nivel=n;
	}
	public static void actualizarCorriendo(boolean estado){
		corriendo=estado;
	}
	public void dibuja(Graphics grafico)
	{
		
		// El Begin de OpenGL o DirectX
	    BufferedImage pantalla=null;
	    //pantalla=new BufferedImage(320,320, BufferedImage.TYPE_INT_RGB );
	    //ahora tiene 80 mas por los 2 pixeles de separacion entre tile:
	    pantalla=new BufferedImage(720,720, BufferedImage.TYPE_INT_RGB );
	    Graphics2D g = pantalla.createGraphics();
	    g.setColor(new Color(140,150,130));
        g.fillRect(0, 0, pantalla.getWidth(), pantalla.getHeight());
	    g.dispose();
	    
	    
	    
	    
	    
	    //mapa.ActualizarFlotador();
	    
	    //El mapa de tiles
	    mapa.DibujarCapa(pantalla.getGraphics());
	    
	    //la capa de otros elementos
	    mapa.DibujarCapa2(pantalla.getGraphics());
	    
	    //sprite.putSprite(pantalla.getGraphics(), sprite.getX(), sprite.getY());
	    //reemplazando:
	    //otroSprite.putSprite(pantalla.getGraphics(), otroSprite.getX(), otroSprite.getY());
	    
	    
	    mapa.DibujarBalas(pantalla.getGraphics());
	    
	    mapa.DibujarNumeros(pantalla.getGraphics());
	    
	    mapa.DibujarVida(pantalla.getGraphics());
	    
	    // El ENd
	    grafico.drawImage(pantalla, 0, 0, this);
	    
        balas.actualizar();// mueve las balas.
	    
	}
	
	public void dibujarPuntos(Graphics g){
		g.setFont(new Font("DigiFaceWide",1,36));
	    g.drawString(String.valueOf(personaje.puntos[0]), 35,300);
	    g.drawString(String.valueOf(personaje.puntos[1]), 840,300);
	}
	
	public void keyTyped(KeyEvent e) {
	    throw new UnsupportedOperationException("Not supported yet.");
	}
	
	public void keyPressed(KeyEvent e) {
	    throw new UnsupportedOperationException("Not supported yet.");
	}
	
	public void keyReleased(KeyEvent e) {
	    throw new UnsupportedOperationException("Not supported yet.");
	}
	public static void main(String[] args) throws InterruptedException{
		
		Menu menu=new Menu();
		//para que cuando este listo se ejecute:
		while(yaTermino==false){
			Thread.sleep(1000);
			if(estaCerrada==true){
				new Juego();
				yaTermino=true;
			}
		}
		
	}

}
