package BricksWar;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

public class MapaTiles {
	Personaje personaje=new Personaje();
	Balas balas=new Balas();
	public static int map[][];
	public static int lava[][];
	public static int jugadores[][];
	public static int objetos[][];
	public static boolean revisado[][];
	boolean cae[];
	boolean flota[];
	private BufferedImage tile;
	private BufferedImage tile2;
	private boolean buscarObjetos=true,buscarLagos=true;
	private int tiempo=-1;
	private int alturaLava;
	private int numeroDeObjetos=0,numeroDeLagos=0,contadorRetardo=0,contador=0, binario=0,flotaX=12,flotaY=34,tiempoLava1=0,tiempoLava2=0,tiempoLava3=0;
	private boolean lavaLista=false;
	int[] contador2=new int[10];//maximo 10 contadores...(lagos de lava).
	int[] altura=new int[10];//maximo 10 alturas de crecimiento para 10 lagos de lava diferentes.
	int[] demora=new int[10];//maximo 10 demoras para 10 lagos diferentes.
	boolean[] sube=new boolean[10];
	boolean[] hayLavaDebajo;
	boolean[] puedeFlotar;
	int[]azar=null;
	int[]limites=null;
	int dormir=0;
	int contadorObjetos=0;
	int eliminarLava=0;
	boolean buscandoLava=false;
	Random r=new Random();
	
	public MapaTiles(int[][] existingMap) {
		
		map = new int[existingMap.length][existingMap[0].length];
		for (int y = 0; y < map.length; y++) {
			for (int x = 0; x < map[y].length; x++) {
				map[y][x] = existingMap[y][x];
			}
		}
		
		/*
		if(map.length==20){
			tile = CargarTile("C:/Users/milton/workspace/TetrisMan/tile.png");
			tile2 = CargarTile("tile2.png");
			Tile.TILE_WIDTH=16;
			Tile.TILE_HEIGHT=16;
		}else{
			tile = CargarTile("C:/Users/milton/workspace/TetrisMan/miniTile.png");
			tile2 = CargarTile("C:/Users/milton/workspace/TetrisMan/miniTile2.png");
			Tile.TILE_WIDTH=8;
			Tile.TILE_HEIGHT=8;
		}
		*/
		tile = CargarTile("C:/tile.png");
		//tile2 = CargarTile("tile2.png");
		Tile.TILE_WIDTH=16;
		Tile.TILE_HEIGHT=16;
	}
	public MapaTiles(int width,int height){
		map = new int[height][width];
	}
	
	public static MapaTiles DesdeArchivo(String fileName){
		MapaTiles layer = null;
		ArrayList<ArrayList<Integer>> tempLayout = new ArrayList<>();
		try(BufferedReader br = new BufferedReader(new FileReader(fileName))){
			
			String currentLine;
			
			while((currentLine = br.readLine()) != null){
				if(currentLine.isEmpty()){
					continue;
				}
				ArrayList<Integer> row = new ArrayList<>();
				
				String[] values = currentLine.trim().split(" ");
				
				for(String string : values){
					if(!string.isEmpty()){
						int id = Integer.parseInt(string);
						row.add(id);
					}
				}
				tempLayout.add(row);
			}
		}catch(IOException e){
			
		}
		
		int width = tempLayout.get(0).size();
		int height = tempLayout.size();
		
		layer = new MapaTiles(width,height);
		
		lava = new int[width][height];//la matriz de lava.
		revisado = new boolean[width][height]; 
		jugadores = new int[width][height];
		objetos = new int[width][height];
		
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				layer.map[y][x] = tempLayout.get(y).get(x);
				lava[y][x]=10;
				revisado[y][x]=false;
				jugadores[y][x]=0;
				objetos[y][x]=0;
			}
		}
		
		
		/*
		if(height==20){
			layer.tile = layer.CargarTile("C:/Users/milton/workspace/TetrisMan/tile.png");
			layer.tile2 = layer.CargarTile("C:/Users/milton/workspace/TetrisMan/tile2.png");
			Tile.TILE_WIDTH=16;
			Tile.TILE_HEIGHT=16;
		}else{
			layer.tile = layer.CargarTile("C:/Users/milton/workspace/TetrisMan/miniTile.png");
			layer.tile2 = layer.CargarTile("C:/Users/milton/workspace/TetrisMan/miniTile2.png");
			Tile.TILE_WIDTH=8;
			Tile.TILE_HEIGHT=8;
		}
		*/
		
		layer.tile = layer.CargarTile("C:/tile.png");
		//layer.tile2 = layer.CargarTile("C:/Users/milton/workspace/TetrisMan/tile2.png");
		Tile.TILE_WIDTH=16;
		Tile.TILE_HEIGHT=16;
		
		return layer;
		
	}
	
	public BufferedImage CargarTile(String fileName){
		BufferedImage img = null;
		
		try{
			img = ImageIO.read(new File(fileName));
		}catch(IOException e){
			System.out.println("Could not load image");
		}
		
		return img;
	}

	public void mostrarMatriz(){
		for(int i=0;i<40;i++){
			for(int j=0;j<40;j++){
				if(lava[i][j]==10) {
					System.out.print(" X");
				}else{
					System.out.print(" "+lava[i][j]);
				}
			}
			System.out.println("");
		}
	}
	
	public void DibujarCapa(Graphics g) {
		
		
		dormir++;
		
		if(lavaLista==false){
			tiempoLava1++;
			if(tiempoLava1>=10){
				tiempoLava1=0;
				tiempoLava2++;
				if(tiempoLava2>=10){
					lavaLista=true;
				}
				/*
				if(tiempoLava2>=10){
					tiempoLava2=0;
					tiempoLava3++;
					if(tiempoLava3>=10){
						lavaLista=true;
					}
				}
				*/
			}
		}
		contadorObjetos++;
		if(contadorObjetos==4){
			contadorObjetos=0;
			DibujarObjetos();
		}
		for (int y = 0; y < map.length; y++) {
			for (int x = 0; x < map[0].length; x++) {
				int index = map[y][x];
				if(index==1 || jugadores[y][x]==1){
					g.drawImage(tile,2*x+x*Tile.TILE_WIDTH,2*y+y*Tile.TILE_HEIGHT,null);
				}
			}
		}
	}
	/*
	public void ActualizarFlotador(){
		if(map[flotaY+2][flotaX-1]==2 && map[flotaY+2][flotaX+16]==2){
			boolean flota=true;
			for(int i=0;i<16;i++){
				if(map[flotaY-1][flotaX+i]==1 || map[flotaY-1][flotaX+i]==3){
					flota=false;
				}
			}
			if(flota==true){
				
				for(int i=0;i<16;i++){
					map[flotaY+2][flotaX+i]=2;
					map[flotaY][flotaX+i]=0;
				}
				flotaY=flotaY-1;
				for(int i=0;i<16;i++){
					map[flotaY][flotaX+i]=1;
					map[flotaY+2][flotaX+i]=1;
				}
				map[flotaY+1][flotaX]=1;
				map[flotaY+1][flotaX+15]=1;
			}
		}
	}
	*/
	
	public void DibujarObjetos(){
		if(buscarObjetos==true){
			buscarObjetos=false;
			int indice=0;
			for(int i=0;i<40;i++){
				for(int j=0;j<40;j++){
					if(map[i][j]==3 && objetos[i][j]==0){
						indice++;
						numeroDeObjetos++;
						CrearObjeto(i,j,indice);
					}
				}
			}
			cae=new boolean[numeroDeObjetos+1];
			flota=new boolean[numeroDeObjetos+1];
			hayLavaDebajo=new boolean[numeroDeObjetos+1];
			puedeFlotar=new boolean[numeroDeObjetos+1];
			for(int i=1;i<=numeroDeObjetos;i++){
				puedeFlotar[i]=true;
			}
		}
		//caida:
		for(int i=1;i<=numeroDeObjetos;i++){
			puedeFlotar[i]=true;
			cae[i]=true;
			flota[i]=false;
		}
		for(int i=0;i<40;i++){
			for(int j=0;j<40;j++){
				if(objetos[i][j]!=0){
					if(map[i-1][j]==1 || jugadores[i-1][j]==1){
						puedeFlotar[objetos[i][j]]=false;
					}
					if(map[i+1][j]==1 && objetos[i+1][j]==0 && lava[i+1][j]==10){
						cae[objetos[i][j]]=false;
						//System.out.println("hola");
					}
					if(lava[i+1][j]!=10){
						//System.out.println(lava[i+1][j]);
						flota[objetos[i][j]]=true;
						cae[objetos[i][j]]=false;
						//System.out.println("llegue");
					}
				}
			}
		}
		//System.out.println(flota[1]);
		for(int i=1;i<=numeroDeObjetos;i++){
			if(cae[i]==true){
				puedeFlotar[i]=true;//modificar cuando hayan mas cosas encima.
				for(int j=39;j>=0;j--){
					for(int k=0;k<40;k++){
						if(objetos[j][k]==i){
							objetos[j+1][k]=i;
							objetos[j][k]=0;
							map[j+1][k]=1;
							map[j][k]=0;
						}
					}
				}
			}
		}
		
	}
	
	public void CrearObjeto(int i, int j, int indice){
		
		objetos[i][j]=indice;
		map[i][j]=1;//el cuadro de este objeto se almacena en una matriz exclusiva y ademas equivale a 1 en el mapa.
		//lava[i][j]=indice;//indice=ya reviso este cuadrado para el correspondiente lago de lava.
		//revisado[i][j]=true;
		
		//if(i<39 && map[i+1][j]==2) map[i+1][j]=4;//la lava normal se transforma en lava que crece.
		if(i<39 && map[i+1][j]==3 && objetos[i+1][j]==0){//abajo.
			CrearObjeto(i+1,j,indice);
		}
		//if(j>0 && map[i][j-1]==2) map[i][j-1]=4;
		if(i<39 && map[i][j-1]==3 && objetos[i][j-1]==0){//izquierda.
			CrearObjeto(i,j-1,indice);
		}
		//if(j<39 && map[i][j+1]==2) map[i][j+1]=4;
		if(i<39 && map[i][j+1]==3 && objetos[i][j+1]==0){//derecha.
			CrearObjeto(i,j+1,indice);
		}
		//if(i>0 && map[i-1][j]==2) map[i-1][j]=4;
		if(i<39 && map[i-1][j]==3 && objetos[i-1][j]==0){//arriba.
			CrearObjeto(i-1,j,indice);
		}
	}
	
	public void flotarObjetos(int indice){
		for(int i=1;i<=numeroDeObjetos;i++){
			boolean coincide=false;//si corresponde la lava al objeto.
			//System.out.println(indice);
			if(flota[i]==true){
				//System.out.println(indice);
				for(int j=39;j>=0;j--){
					for(int k=0;k<40;k++){
						if(objetos[j][k]==i){
							//System.out.println(lava[j+1][k]+"="+indice);
							if(lava[j+1][k]==indice){
								//System.out.println("hola");
								coincide=true;
								j=-1;
								k=40;
							}
						}
					}
				}
			}
			//System.out.println(coincide);
			if(coincide==true){
				hayLavaDebajo[i]=true;
			}
		}
		
	}
	
	public void revisarSiFlota(int indice){
		
		for(int i=1;i<=numeroDeObjetos;i++){
			if(hayLavaDebajo[i]==true && puedeFlotar[i]==true){
				hayLavaDebajo[i]=false;
				if(sube[indice]==true){//revisa si la lava sube.
					//System.out.println(altura[indice]);
					for(int x=0;x<40;x++){//recorre el eje x.
						if(objetos[altura[indice]-1][x]==i){//revisa si esta el objeto correspondiente sobre la altura en la que la lava deberia subir).
							for(int j=0;j<40;j++){
								for(int k=0;k<40;k++){
									if(objetos[j][k]==i){
										objetos[j-1][k]=i;
										objetos[j][k]=0;
										map[j-1][k]=1;
										map[j][k]=4;
									}
								}
							}
							x=40;
						}
					}
				}
			}
		}
		
	}
	
	public void buscarLagos(){
		//System.out.println("hola");
		buscandoLava=true;
		numeroDeLagos=0;
		int indice=0;
		for(int i=39;i>=0;i--){
			for(int j=0;j<40;j++){
				if(map[i][j]==2){
					lava[i][j]=0;
				}
				if(map[i][j]>=4 && map[i][j]<=7 && lava[i][j]>=numeroDeLagos){
					numeroDeLagos++;
					indice++;//parte en 1 porque el 0 es para la lava normal (2 ene le mapa).
					CrecerLava(i,j,indice);
					
				}
			}
		}
		//System.out.println(numeroDeLagos);
		buscandoLava=false;
		indice=0;
	}
	
	public void DibujarCapa2(Graphics g) {
		
		if(lavaLista==true){// retardo para el comienzo del avance de la lava.

			if(buscarLagos==true){
				buscarLagos=false;
				buscarLagos();
			}
		}
		
		contadorRetardo++;
		
		if(contadorRetardo==8){
			
			contadorRetardo=0;
			for(int i=1;i<=numeroDeLagos;i++){
				/*
				if(dormir>=100){
					dormir=0;
					mostrarMatriz();
				}
				if(contador2[i]==2){
					contador2[i]=0;
					LagosDeLava(i);
					CrecerLava2(i);
					ActualizarLava(i);
				}
				*/
				
				contador2[i]++;
				//System.out.println(demora[i]);
				if(contador2[i]>=(demora[i])){//demora = retardo de avance del acido (de acuerdo a cantidad de cuadros avanzados).
					contador2[i]=0;
					revisarSiFlota(i);
					ActualizarLava(i);
					//mostrarMatriz();
					
				}
				if(contador2[i]==0){
					LagosDeLava(i);
					flotarObjetos(i);//debe ir justo aqui (aqui el acido debajo del objeto tiene un indice especifico).
					//si hay acido debajo, antes de actualizar el acido, revisa si este sube en la correspondiente altura.
					CrecerLava2(i);
				}
			}
			
		}
		for (int y = 0; y < map.length; y++) {
			for (int x = 0; x < map[0].length; x++) {
				int index = lava[y][x];//no se usa este (porque la lava avanzaria instantaneamente).
				if(map[y][x]==2 || map[y][x]==4){//si es mayor o igual a 0 y menor que 10 (es lava).
					if(alterna(y,x)==true){
						g.drawImage(tile,2*x+x*Tile.TILE_WIDTH,2*y+y*Tile.TILE_HEIGHT,null);
					}
				}
				
			}
		}
		
		contador++;
		if(contador==4) {
			contador=0;
			binario++;
			if(binario==2) binario=0;
		}
		
	}
	
	public void acido(){
		
	}
	
	/*
	public boolean seDibuja(int i){
		
		if(tiempo==-1){
			contador2++;
		}else{
			if(limites==null){
				limites=new int[contador2];
				azar=new int[contador2];
				for(int j=0;j<contador2;j++){
					limites[j]=0;
					azar[j]=0;
				}
			}
			if(azar[i]==limites[i] || azar[i]>=16){//aumentar el segundo para aumentar la espera de revisado si no salio premiado.
				int azar1=(r.nextInt(128));//azar para posibilidad (1/128/4).
				int azar2=8;//mayor que cualquier limite (limite).
				if(azar1==0) azar2=0;
				if(azar1==7) azar2=1;
				if(azar1==15) azar2=2;
				if(azar1==23) azar2=3;
				azar[i]=azar2;
				limites[i]=azar2+4;
				
			}
			if(tiempo==azar[i] && azar[i]<limites[i]){//el parpadeo dura 4 frames (120 milisegundos(?)).
				azar[i]++;//azar2 generado (4) + el tiempo de parpadeo (4) = tiempo (8).
				return false;
			}else{
				if(azar[i]>=8){//limite (mayor o igual al tiempo).
					azar[i]++;
				}
			}
		}
		
		return true;
		
		
	}
	*/
	public boolean alterna(int y, int x){
		return (x+y)%2==binario;
	}
	public void limpiarJugadores(){
		for (int y = 0; y < map.length; y++) {
			for (int x = 0; x < map[0].length; x++) {
				int index = map[y][x];//no se usa.
				if(jugadores[y][x]==1){
					jugadores[y][x]=0;
				}
			}
		}
	}
	public void DibujaJugador(int jugador){
		for(int i=0;i<4;i++){
			for(int j=0;j<3;j++){
				if(personaje.personaje[jugador][i][j]==1){
					jugadores[i+personaje.getY(jugador)][j+personaje.getX(jugador)]=1;
				}
			}
		}
	}
	public void borrarJugador(int jugador){
		for(int i=0;i<4;i++){
			for(int j=0;j<3;j++){
				if(personaje.personaje[jugador][i][j]==1){
					jugadores[i+personaje.getY(jugador)][j+personaje.getX(jugador)]=0;
				}
			}
		}
	}
	
	public void DibujarBalas(Graphics g){
		for(int j=0;j<2;j++){
			for(int i=0;i<10;i++){
				if(balas.bala[j][i][0]!=0){
					g.drawImage(tile,2*(balas.bala[j][i][1])+(balas.bala[j][i][1])*Tile.TILE_WIDTH,2*(balas.bala[j][i][2])+(balas.bala[j][i][2])*Tile.TILE_HEIGHT,null);
				}
			}
		}
	}
	
	public void DibujarNumeros(Graphics g){
		for(int k=0;k<2;k++){
			for(int i=0;i<5;i++){
				for(int j=0;j<3;j++){
					if(personaje.numeros[Personaje.puntos[k]][i][j]==1){
						if(k==0) g.drawImage(tile,2*(j+3)+(j+3)*Tile.TILE_WIDTH,2*(i+31)+(i+31)*Tile.TILE_HEIGHT,null);
						if(k==1) g.drawImage(tile,2*(j+34)+(j+34)*Tile.TILE_WIDTH,2*(i+31)+(i+31)*Tile.TILE_HEIGHT,null);
					}
				}
			}
		}
	}
	
	public void ActualizarLava(int indice){//para transformar los 5 en 4 y limpiar la matriz de cada lago de lava respecto al indice.
		//boolean floto=false;
		if(demora[indice]!=0){
			for(int i=39;i>=0;i--){
				for(int j=0;j<40;j++){
					if(map[i][j]==6 || map[i][j]==7){
						//lava[i][j]=indice;//para que funcione el metodo flotarObjetos.
						if(lava[i][j]==indice){
							map[i][j]=4;
						}
					}
					revisado[i][j]=false;
					//if(lava[i][j]==indice){
					//	lava[i][j]=10;
					//}
				}
			}
			demora[indice]=0;
		}
	}
	
	public void LagosDeLava(int indice){//crecimiento recursivo.
		
		
		for(int i=39;i>=0;i--){
			for(int j=0;j<40;j++){
				if(lava[i][j]==indice){
					CrecerLava(i,j,indice);
					j=40;
					i=-1;
				}
			}
		}
		
	}
	
	public void CrecerLava(int i, int j, int indice){//calcula donde debe crecer.
		
		if(lava[i][j]!=10 && lava[i][j]!=indice && buscandoLava==false){
			//eliminarLava=lava[i][j];
			buscarLagos();//actualiza los lagos de lava.
		}
		if(buscandoLava==true){
			map[i][j]=4;
			lava[i][j]=10;
			if(map[i+1][j]>=4 && map[i+1][j]<=7){
				map[i+1][j]=4;
			}
			if(map[i-1][j]>=4 && map[i-1][j]<=7){
				map[i-1][j]=4;
			}
			if(map[i][j+1]>=4 && map[i][j+1]<=7){
				map[i][j+1]=4;
			}
			if(map[i][j-1]>=4 && map[i][j-1]<=7){
				map[i][j-1]=4;
			}
		}
		//System.out.println(map[i][j+1]+"-"+i+"-"+(j+1));
		lava[i][j]=indice;//indice=ya reviso este cuadrado para el correspondiente lago de lava.
		revisado[i][j]=true;
		
		if(i<39 && map[i+1][j]==2) map[i+1][j]=4;//la lava normal se transforma en lava que crece.
		if(i<39 && (map[i+1][j]==4 || map[i+1][j]==0) && revisado[i+1][j]==false){//abajo.
			if(map[i+1][j]==0){
				map[i][j]=5;
			}else{
				CrecerLava(i+1,j,indice);
			}
		}
		if(j>0 && map[i][j-1]==2) map[i][j-1]=4;
		if(j>0 && (map[i][j-1]==4 || map[i][j-1]==0) && revisado[i][j-1]==false){//izquierda.
			if(map[i][j-1]==0){
				map[i][j]=5;
			}else{
				CrecerLava(i,j-1,indice);
			}
		}
		if(j<39 && map[i][j+1]==2) map[i][j+1]=4;
		if(j<39 && (map[i][j+1]==4 || map[i][j+1]==0) && revisado[i][j+1]==false){//derecha.
			if(map[i][j+1]==0){
				map[i][j]=5;
			}else{
				CrecerLava(i,j+1,indice);
			}
		}
		if(i>0 && map[i-1][j]==2) map[i-1][j]=4;
		if(i>0 && (map[i-1][j]==4 || map[i-1][j]==0) && revisado[i-1][j]==false){//arriba.
			if(map[i-1][j]==0){
				map[i][j]=5;
			}else{
				CrecerLava(i-1,j,indice);
			}
		}
		/*
		if(altura[indice]!=0 && i<altura[indice]){// a mas altura es mas abajo en el mapa (y esta al reves).
			map[i][j]=4;
		}else{
			if(map[i][j]==5){
				altura[indice]=i;
				//System.out.println(altura[indice]);
			}
		}
		*/
	}
	
	public void CrecerLava2(int indice){//aumenta la lava donde debe y calcula la demora, ademas de devolver la lava a 4.
		boolean encontro;
		altura[indice]=1;//se resetea el valor de la altura donde sube la lava a 1 por si se le resta para que no sea -1 como indice.
		for(int i=0;i<40;i++){
			encontro=false;
			sube[indice]=true;
			
			for(int j=0;j<40;j++){
				
				if(map[i][j]==5){
					if(lava[i][j]==indice){
						
						map[i][j]=4;
						
						encontro=true;
						
						if(i<39 && map[i+1][j]==0){//abajo.
							map[i+1][j]=6;
							lava[i+1][j]=indice;
							demora[indice]++;
							sube[indice]=false;
						}else{
							//izquierda o derecha:
							if((j>0 && map[i][j-1]==0) || (j<39 && map[i][j+1]==0)){
								if(j>0 && map[i][j-1]==0){//izquierda.
									map[i][j-1]=6;
									if(map[i+1][j]==1){
										map[i][j-1]=7;//si hay un bloque debajo avanza de todas formas.
									}
									lava[i][j-1]=indice;
									demora[indice]++;
									sube[indice]=false;
								}
								if(j<39 && map[i][j+1]==0){//derecha.
									map[i][j+1]=6;
									if(map[i+1][j]==1){
										map[i][j+1]=7;//si hay un bloque debajo avanza de todas formas.
										//j++;//para que no avance todos los que pueda hacia la derecha.
									}
									lava[i][j+1]=indice;
									demora[indice]++;
									sube[indice]=false;
								}
							}else{
								if(i>0 && (map[i-1][j]==0)){// || (objetos[i-1][j]!=0 && puedeFlotar[objetos[i-1][j]]==true))){// arriba (si hay un objeto la lava pasa para hacer que flote).
									
									if(objetos[i-1][j]!=0){
										lava[i-1][j]=indice;
									}else{
										lava[i-1][j]=indice;
										map[i-1][j]=6;
									}
									
									demora[indice]++;
								}
							}
						}
					}
				}
				
				
				
				/*
				if(map[altura[indice]][j]==5){
					if(lava[altura[indice]][j]==indice){
						map[altura[indice]][j]=4;
						if(altura[indice]<39 && map[altura[indice]+1][j]==0){//abajo.
							map[altura[indice]+1][j]=5;
							lava[altura[indice]+1][j]=indice;
							demora[indice]++;
						}else{
							//izquierda o derecha:
							if((j>0 && map[altura[indice]][j-1]==0) || (j<39 && map[altura[indice]][j+1]==0)){
								if(j>0 && map[altura[indice]][j-1]==0){//izquierda.
									map[altura[indice]][j-1]=5;
									lava[altura[indice]][j-1]=indice;
									demora[indice]++;
								}
								if(j<39 && map[altura[indice]][j+1]==0){//derecha.
									map[altura[indice]][j+1]=5;
									lava[altura[indice]][j+1]=indice;
									demora[indice]++;
								}
							}else{
								if(altura[indice]>0 && map[altura[indice]-1][j]==0){//arriba.
									map[altura[indice]-1][j]=5;
									lava[altura[indice]-1][j]=indice;
									demora[indice]++;
								}
							}
						}
					}
				}
				*/
			}
			if(sube[indice]==false){
				for(int j=0;j<40;j++){
					if(map[i-1][j]==6 && lava[i-1][j]==indice){
						map[i-1][j]=0;
						lava[i-1][j]=10;
						demora[indice]--;
					}
				}
			}else{
				if(encontro==true){
					altura[indice]=i;//altura donde sube la lava.
				}
			}
		}
	}
	/*
	public void CrecerLava1(){
		if(demora!=0){//por si ya crecio al maximo.
			demora=0;
			boolean paso1=true;
			for(int i=39;i>=0;i--){
				boolean paso2=true;
				for(int j=0;j<40;j++){
					if(map[i][j]==4){
						if(i<39 && map[i+1][j]!=1 && map[i+1][j]!=4 && map[i+1][j]!=7){
							map[i+1][j]=5;
							demora++;
							paso1=false;
						}else{
							if(j>0 && map[i][j-1]!=1 && map[i][j-1]!=4 && map[i][j-1]!=7){
								paso1=false;
								paso2=false;
								map[i][j-1]=5;
								demora++;
							}
							if(j<39 && map[i][j+1]!=1 && map[i][j+1]!=4 && map[i][j+1]!=7){
								paso1=false;
								paso2=false;
								map[i][j+1]=5;
								demora++;
							}
						}
					}
				}
				if(paso2==true){
					for(int j=0;j<40;j++){
						if(map[i][j]==2){
							if(i>0 && map[i-1][j]!=1 && map[i-1][j]!=4 && map[i-1][j]!=6){
								map[i-1][j]=5;
								demora++;
								paso1=false;
							}
						}
					}
				}
				
				if(paso1==false){
					i=-1;
				}
			}
		}
	}
	*/
	public void DibujarVida(Graphics g){
		int puntos1=personaje.vidas[0];
		int puntos2=personaje.vidas[1];
		for(int i=0;i<5;i++){
			if(i<(puntos1)){
				g.drawImage(tile,2*(2+i)+(2+i)*Tile.TILE_WIDTH,2*(37)+(37)*Tile.TILE_HEIGHT,null);
			}
			if(i>(5-puntos2-1)){
				g.drawImage(tile,2*(33+i)+(33+i)*Tile.TILE_WIDTH,2*(37)+(37)*Tile.TILE_HEIGHT,null);
			}
		}
	}
	
	
	
}