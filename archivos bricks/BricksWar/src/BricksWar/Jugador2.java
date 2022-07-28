package BricksWar;

import java.awt.event.*;

public class Jugador2 implements KeyListener {
	
	//metodos por jugador:
	//animar,getX,getY,setX,setY.
	
	public static final int teclaIzquierda=Teclas.teclas[1][0];
	public static final int teclaDerecha=Teclas.teclas[1][1];
	public static final int teclaDisparo=Teclas.teclas[1][2];
	public static final int teclaSalto=Teclas.teclas[1][3];
	public static final int teclaAgacharse=Teclas.teclas[1][4];
	public static final int teclaArriba=Teclas.teclas[1][5];
	public static final int teclaAbajo=Teclas.teclas[1][6];
	
	public static boolean gano=false;
	
	private int nivel=1;
	Juego j;
	Balas balas=new Balas();
	Personaje personaje=new Personaje();
	//MapaTiles mapa=MapaTiles.DesdeArchivo("map"+nivel+".txt");
	private boolean disparo=false,estaSaltando=false,estaCayendo=false,arriba=false,arriba2=false,abajo2=false,espacio2=false,izquierda2=false,derecha2=false,agacharse2=false;
	private int contadorQuema=0, sumaY,altoDelSalto=0,accionAnterior=0,poderDeSalto=5,direccion=1,contador2=0;
    private boolean techoAnterior=false,direccionado=false,valido=true,nulo=false,moviendose=false,apretando=false,techo=false,firme=true,salto=false,espacio, abajo, izquierda, derecha,agacharse;
    private int marcaPasos=0,altura=0,contador=0,accionActual=0,auxX=personaje.getX(1),auxY=personaje.getY(1),ancho=40,alto=40;
    
    //Jugador2 j2=new Jugador2();
    
    public void actualiza() {
    	//metodos nuevos:
    	
    	if(contador==0) accionAnterior=accionActual;
    	
    	//restablece el contador, el marca pasos, y la posicion por defecto si no hay movimiento.
    	if(izquierda==false && derecha==false && abajo==false && arriba==false && espacio==false && estaSaltando==false && agacharse==false 
    			&& izquierda2==false && derecha2==false && abajo2==false && arriba2==false && espacio2==false && agacharse2==false && estaBloqueado(0)==true){
    		if(contador==4 || contador==0){
    			contador=-1;
    			marcaPasos=0;
    			estaCayendo=false;
    			accionActual=0;
    			personaje.animar(1,0);
    			if(bloqueado()==true){
    				accionActual=accionAnterior;
    			}
    		}
    	}
    	
    	//si esta saltando pero no moviendose, la posicion por defecto tambien es 0.
    	if(izquierda==false && derecha==false && contador==0){
    		accionActual=0;
    		personaje.animar(1,0);
    		if(bloqueado()==true){
				accionActual=accionAnterior;
			}
    	}
    	
    	if ((izquierda==true || izquierda2==true) && contador==0){
    		auxX=personaje.getX(1)-1;
    		if(marcaPasos==0 || marcaPasos==2){
    			accionActual=3;
    		}else{
    			accionActual=0;
    		}
    	 	if(estaSaltando || estaCayendo){
    	 		accionActual=4;
    		}
    	}
    	 
    	if ((derecha==true || derecha2==true) && contador==0){
    		auxX=personaje.getX(1)+1;
    		if(marcaPasos==0 || marcaPasos==2){
    			accionActual=1;
    		}else{
    			accionActual=0;
    		}
    		if(estaSaltando || estaCayendo){
    			accionActual=2;
    		}
    	}
    	
    	//se pone de lado.
    	if(((izquierda && derecha) || (abajo==true || abajo2==true || arriba==true || arriba2==true)) && contador==0){
    		if(izquierda && derecha){
        		auxX=personaje.getX(1);
    		}
    		accionActual=5;
    	}
    	
    	//se agacha.
    	if((agacharse==true || agacharse2==true) && contador==0){
    		accionActual=6;
    	}
    	
    	//si esta bloqueado o empalado restaura el eje x.
    	if(contador==0){
			personaje.animar(1,accionActual);
    		if(bloqueado()==true || MapaTiles.map[personaje.getY(1)+3][personaje.getX(1)+1]==1 || MapaTiles.jugadores[personaje.getY(1)+3][personaje.getX(1)+1]==1){
    			auxX=personaje.getX(1);
    			//para que no se pege a las paredes (2 y 4 en caso de que no caiga si no se mueve).
        		if(bloqueado()==true || accionActual==4 || accionActual==2){ 
        			personaje.animar(1,accionAnterior);
        			accionActual=accionAnterior;
        		}
    		}else{
    			if((derecha==true || derecha2==true) && izquierda==false && izquierda2==false && accionActual!=5){//si no puede mover el pie hacia la derecha.
    				if((MapaTiles.jugadores[personaje.getY(1)+3][personaje.getX(1)+2]==1 || MapaTiles.jugadores[personaje.getY(1)+3][personaje.getX(1)+2]==1) || (MapaTiles.map[personaje.getY(1)+3][personaje.getX(1)+2]==1 || MapaTiles.map[personaje.getY(1)+3][personaje.getX(1)+2]==1)){
    					auxX=personaje.getX(1);
    					if(bloqueado()==true || accionActual==4 || accionActual==2){ 
    	        			personaje.animar(1,accionAnterior);
    	        			accionActual=accionAnterior;
    	        		}
    				}
    			}
    			if((izquierda==true || izquierda2==true) && derecha==false && derecha2==false && accionActual!=5){//si no puede mover el pie hacia la izquierda.
    				if((MapaTiles.jugadores[personaje.getY(1)+3][personaje.getX(1)]==1 || MapaTiles.jugadores[personaje.getY(1)+3][personaje.getX(1)]==1) || (MapaTiles.map[personaje.getY(1)+3][personaje.getX(1)]==1 || MapaTiles.map[personaje.getY(1)+3][personaje.getX(1)]==1)){
    					auxX=personaje.getX(1);
    					if(bloqueado()==true || accionActual==4 || accionActual==2){ 
    	        			personaje.animar(1,accionAnterior);
    	        			accionActual=accionAnterior;
    	        		}
    				}
    			}
    		}
    	}
    	
    	//salto.
    	if ((espacio==true || espacio2==true || arriba==true || arriba2==true) && contador==0){
    		if((estaBloqueado(0) || (estaCayendo==false)) && estaSaltando==false){
    			altoDelSalto=0;
    			sumaY=1;
    		}
    		if(estaBloqueado(3)){
    			if((izquierda==false && derecha==false) || (estaBloqueado(1) || estaBloqueado(2))){
    				altoDelSalto=5;
    			}else{
    				sumaY=0;
    			}
    		}
    		if(altoDelSalto<5){
    			auxY=personaje.getY(1)-sumaY;
    			altoDelSalto=altoDelSalto+1;
    			if(estaBloqueado(0)==false){
    				estaSaltando=true;
    			}else{
    				estaSaltando=false;
    			}
    		}else{
    			estaSaltando=false;
    			//espacio para agregar estaCayendo=true; si se necesita.
    		}
    		
    	}else{
    		if(contador==0){
    			estaSaltando=false;
    			altoDelSalto=5;
    		}
    	}
    	
    	//caida.
    	if(estaBloqueado(0)==false && estaSaltando==false && contador==0){
    		//agregar a estaCayendo==true: || hayAlgoDePiso()==false para que no se demore en caer cuando salta, tambien agregar al salto: estaCayendo=true; adonde se indica.
    		if(estaAgarrado()==false){
    			if((estaCayendo==true)){
    			auxY=personaje.getY(1)+1;
    			}
    			estaCayendo=true;
    		}else{
    			estaCayendo=false;
    		}
    		
    	}else{
    		if(contador==0){
    			estaCayendo=false;
    		}
    	}
    	
    	if(contador==0){
    		personaje.animar(1,accionActual);
    		//si esta bloqueado restaura el eje y.
    		if(bloqueado()==true){
    			auxY=personaje.getY(1);
    		}
    		//se revisa de nuevo por si la posicion actual esta bloqueada para cambiarla por la anterior.
    		if(bloqueado()==true){
        		accionActual=accionAnterior;
        		personaje.animar(1,accionActual);
        	}
    		personaje.setX(1,auxX);
        	personaje.setY(1,auxY);
    	}
    	
    	if(izquierda==true && derecha==false){
    		direccion=1;
    	}
    	
    	if(derecha==true  && izquierda==false){
    		direccion=2;
    	}
    	
    	if(contador2==0 || contador2==16){
    		contador2=-1;
    	}
    	
    	contador2++;
    	
    	if(contador2==0 && disparo==true && accionActual!=5){
    		balas.nuevaBala(1, direccion,personaje.getX(1),personaje.getY(1));
    		contador2=1;
    	}
    	
    	contador++;
    	
    	if(contador==4){
    		contador=0;
    		marcaPasos++;
    	}
    	
    	if(quemandose()==true){
    		contadorQuema++;
    		if(contadorQuema==8){
    			contadorQuema=0;
    			personaje.vidas[1]--;
    		}
    	}else{
    		contadorQuema=0;
    	}
    	
    	if(contador==0){
    		personaje.animar(1,accionActual);
    		personaje.setAccion(1,accionActual);
    	}
    	
    	if(contador==1){
    		espacio2=false;
    		izquierda2=false;
    		derecha2=false;
    		arriba2=false;
    		abajo2=false;
    		agacharse2=false;
    	}
    	
    	
    	if(marcaPasos==4 || hayAlgoDePiso()==false){
    		marcaPasos=0;
    	}
    	
    	if(gano()){
    		
    	}
    	
    	if(perdio()){
    		reaparecer();
    	}
    	
    }
    
    //nuevos metodos.
    public void cargarNivel(){
    	nivel=nivel+1;
    	j.actualizarNivel(nivel);
    	j.actualizarCorriendo(false);
    }
    
    public boolean gano(){
    	//if(j1.estaVivo()==false) return true;
    	if(gano==true){
    		gano=false;
    		personaje.puntos[1]++;
    		if(personaje.puntos[1]==10) {
    			reaparecer();
    			Juego.corriendo=false;
    			personaje.puntos[0]=0;
	    		personaje.puntos[1]=0;
	    		personaje.vidas[1]=5;
		    	direccion=1;
	    		Balas.bala[1][0][0]=0;
	    		Balas.bala[0][0][0]=0;
    		}
    		return true;
    	}
    	return false;
    	
    }
    
    public boolean perdio(){
    	
    	if(personaje.vidas[1]<=0){
    		Jugador1.gano=true;
    		direccion=1;
    		personaje.vidas[1]=5;
    		return true;
    	}
    	return false;
    	
    }
    
    public boolean quemandose(){
    	if(auxY+3<alto && auxY>=0 && auxX+2<ancho && auxX>=0){
    		for(int i=0;i<4;i++)
    			for(int j=0;j<3;j++)
    				if(personaje.personaje[1][i][j]==1)
    					if(MapaTiles.map[auxY+i][auxX+j]==2 || MapaTiles.map[auxY+i][auxX+j]==4){
    						return true;
    					}
    	}
    	return false;
    }
    
    public void comprobacionX(){//este metodo debe ir en la comprobacion del eje x.
    	for(int i=0;i<4;i++){
    		for(int j=0;j<3;j++){
    			if(Personaje.personaje[1][i][j]==1 && MapaTiles.jugadores[auxY+i][auxX+j]==1){//si esta en contacto con el otro jugador.
    				auxX=personaje.getX(1);
    			}
    		}
    	}
    }
    public void comprobacionY(){//este metodo debe ir en la comprobacion del eje y.
    	for(int i=0;i<4;i++){
    		for(int j=0;j<3;j++){
    			if(Personaje.personaje[1][i][j]==1 && MapaTiles.jugadores[auxY+i][auxX+j]==1){//si esta en contacto con el otro jugador.
    				auxY=personaje.getY(1);
    			}
    		}
    	}
    }
    
    public boolean estaAgarrado(){
    	int accion=accionActual;
    	if(derecha==true){
    		personaje.animar(1,1);
        	if(estaBloqueado(0)==true && bloqueado()==false){
        		accionActual=1;
        		return true;
        	}
        	
    	}
    	if(izquierda==true){
    		personaje.animar(1,3);
        	if(estaBloqueado(0)==true && bloqueado()==false){
        		accionActual=3;
        		return true;
        	}
        	
    	}
    	
    	accionActual=accion;
    	personaje.animar(1,accionActual);
    	return false;
    }
    public boolean bloqueado(){
    	if(estaBloqueado(4)) return true;
    	
    	return false;
    }
    public void desbloquear(){
    	if(techoAnterior==true){
    		auxY=personaje.getY(1);
        	auxX=personaje.getX(1);
    	}else{
    		auxY=(auxY-1);
        	if(bloqueado()==false) return;
        	auxY=(auxY+1);
        	auxY=(auxY+1);
        	if(bloqueado()==false) return;
        	auxY=(auxY-1);
        	auxX=(auxX+1);
        	if(bloqueado()==false) return;
        	auxX=(auxX-1);
        	auxX=(auxX-1);
        	if(bloqueado()==false) return;
        	auxX=(auxX+1);
        	if(bloqueado()==false) return;
        	reaparecer();
    	}
    }
    public boolean semiEncerrado(){
    	personaje.animar(1,0);
    	if(bloqueado()) return true;
    	return false;
    }
    public boolean encerrado(){
    	if((MapaTiles.map[auxY][auxX]==1 || MapaTiles.map[auxY+1][auxX]==1 || MapaTiles.map[auxY+2][auxX]==1
    		|| MapaTiles.map[auxY+3][auxX]==1) && (MapaTiles.map[auxY+1][auxX+2]==1
    		|| MapaTiles.map[auxY+2][auxX+2]==1 || MapaTiles.map[auxY+3][auxX+2]==1 || MapaTiles.map[auxY][auxX+2]==1))
    		return true;
    	return false;
    }
    public void reaparecer(){
    	personaje.setX(1,36);
    	personaje.setY(1,9);
    	auxX=personaje.getX(1);
    	auxY=personaje.getY(1);
    }
    public boolean hayPiso(){
    	if(auxY+4<alto && auxY>=0 && auxX+2<ancho && auxX>=0){
    		if((MapaTiles.map[auxY+4][auxX]==1 || MapaTiles.jugadores[auxY+4][auxX]==1) && (MapaTiles.map[auxY+4][auxX+1]==1 || MapaTiles.jugadores[auxY+4][auxX+1]==1) && (MapaTiles.map[auxY+4][auxX+2]==1 || MapaTiles.jugadores[auxY+4][auxX+2]==1)){
    			return true;
    		}
    	}
    	return false;
    }
    public boolean hayAlgoDePiso(){
    	if(auxY+4<alto && auxY>=0 && auxX+2<ancho && auxX>=0){
    		if(MapaTiles.map[auxY+4][auxX]==1 || MapaTiles.map[auxY+4][auxX+1]==1 || MapaTiles.map[auxY+4][auxX+2]==1){
    			return true;
    		}
    		if(MapaTiles.jugadores[auxY+4][auxX]==1 || MapaTiles.jugadores[auxY+4][auxX+1]==1 || MapaTiles.jugadores[auxY+4][auxX+2]==1){
    			return true;
    		}
    	}
    	return false;
    }
    public boolean estaBloqueado(int direccion){
    	int sumaX=0,sumaY=0;
    	switch(direccion){
    	case 0:
    		sumaY=1;
    		break;
    	case 1:
    		sumaX=1;
    		break;
    	case 2:
    		sumaX=-1;
    		break;
    	case 3:
    		sumaY=-1;
    		break;
    	case 4:
    		break;
    	}
    	if(auxY+sumaY+3<alto && auxY+sumaY>=0 && auxX+sumaX+2<ancho && auxX+sumaX>=0){
    		for(int i=0;i<4;i++)
    			for(int j=0;j<3;j++)
    				if(personaje.personaje[1][i][j]==1)
    					if(MapaTiles.map[auxY+i+sumaY][auxX+j+sumaX]==1 || MapaTiles.jugadores[auxY+i+sumaY][auxX+j+sumaX]==1)
    						return true;
    	}else{
    		return true;
    	}
    	
    	return false;
    }

 
    // tecla sin pulsar
    public void keyReleased(KeyEvent e) {
    	int k=e.getKeyCode();
    	if(k==teclaDisparo){
    		disparo=false;
    	}
    	if(k==teclaSalto){
    		espacio=false;
    	}
    	if(k==teclaIzquierda){
    		izquierda=false;
    	}
    	if(k==teclaDerecha){
    		derecha=false;
    	}
    	if(k==teclaAbajo){
    		abajo=false;
    	}
    	if(k==teclaArriba){
    		arriba=false;
    	}
    	if(k==teclaAgacharse){
    		agacharse=false;
    	}
    }
 
    //tecla presionada
    public void keyPressed(KeyEvent e) {
    	
    	int k=e.getKeyCode();
    	if(k==teclaDisparo){
    		disparo=true;
    	}
    	if(k==teclaSalto){
    		espacio=true;
    		espacio2=true;
    	}
    	if(k==teclaIzquierda){
    		izquierda=true;
    		izquierda2=true;
    	}
    	if(k==teclaDerecha){
    		derecha=true;
    		derecha2=true;
    	}
    	if(k==teclaAbajo){
    		abajo=true;
    		abajo2=true;
    	}
    	if(k==teclaArriba){
    		arriba=true;
    		arriba2=true;
    	}
    	if(k==teclaAgacharse){
    		agacharse=true;
    		agacharse2=true;
    	}
    }
 
    public void keyTyped(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }
}
