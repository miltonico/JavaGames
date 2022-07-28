package BricksWar;

public class Balas {
	
	static int[] numeroBala=new int[2];
	int contador=0;
	static int bala[][][]=new int[2][10][4];
	Personaje personaje=new Personaje();
	//2=jugadores
	
	//10=cantidad de balas
	
	//4=caracteristicas:
	//0=direccion (0=no existe, 1=izq, 2=der)
	//1=x
	//2=y
	//3=distancia
	public void nuevaBala(int jugador, int direccion, int x, int y){
		if(bala[jugador][0][0]==0){//si ya no esta se crea la nueva bala
			bala[jugador][numeroBala[jugador]][0]=direccion;
			if(direccion==1){
				bala[jugador][numeroBala[jugador]][1]=x;
				if(personaje.getAccion(jugador)==6){
					bala[jugador][numeroBala[jugador]][2]=y+2;
				}else{
					bala[jugador][numeroBala[jugador]][2]=y+1;
				}
			}
			if(direccion==2){
				bala[jugador][numeroBala[jugador]][1]=x+2;
				if(personaje.getAccion(jugador)==6){
					bala[jugador][numeroBala[jugador]][2]=y+2;
				}else{
					bala[jugador][numeroBala[jugador]][2]=y+1;
				}
			}
			bala[jugador][numeroBala[jugador]][3]=0;
			numeroBala[jugador]++;
			if(numeroBala[jugador]==1){
				numeroBala[jugador]=0;
			}
		}
	}
	
	public void actualizar(){
		if(contador==2) contador=0;
		for(int k=0;k<2;k++){//jugadores
			for(int i=0;i<1;i++){//balas
				
				if(bala[k][i][0]!=0 && contador==0) {
					//if(bala[k][i][2]>0 && bala[k][i][1]>0 && bala[k][i][2]<(MapaTiles.map.length-1) && bala[k][i][1]<(MapaTiles.map[0].length-1)){
					//	
					//}
					if(bala[k][i][0]==1){
						bala[k][i][1]=bala[k][i][1]-1;
					}
					if(bala[k][i][0]==2){
						bala[k][i][1]=bala[k][i][1]+1;
					}
					if(MapaTiles.map[bala[k][i][2]][bala[k][i][1]]==1){
						if(bala[k][i][3]>=80){
							bala[k][i][0]=0;
						}else{
							if(bala[k][i][0]==1){
								bala[k][i][1]++;
								bala[k][i][0]=2;
							}else{
								bala[k][i][1]--;
								bala[k][i][0]=1;
							}
						}
						
					}else{
						//si la bala es del propio jugador y es igual a 0 su distancia, no le hace daño.
						//(primero se revisa si esta en el area del jugador para que no se salga de sus limites).
						boolean esPropia=false;
						if((bala[k][i][2]-personaje.getY(k))>=0 && (bala[k][i][2]-personaje.getY(k))<4 && (bala[k][i][1]-personaje.getX(k))>=0 && (bala[k][i][1]-personaje.getX(k))<3){
							if(personaje.personaje[k][bala[k][i][2]-personaje.getY(k)][bala[k][i][1]-personaje.getX(k)]==1){
								esPropia=true;
							}
						}
						//comprobacion de las balas en los personajes:
						if(MapaTiles.jugadores[bala[k][i][2]][bala[k][i][1]]==1 && (bala[k][i][3]>0 || (bala[k][i][3]==0 && esPropia==false))){
							
							if(bala[k][i][2]-personaje.getY(0)>=0 && bala[k][i][2]-personaje.getY(0)<4 && bala[k][i][1]-personaje.getX(0)>=0 && bala[k][i][1]-personaje.getX(0)<3){
								if(personaje.personaje[0][bala[k][i][2]-personaje.getY(0)][bala[k][i][1]-personaje.getX(0)]==1){
									if(personaje.getAccion(0)==5){
										if(bala[k][i][0]==1){
											bala[k][i][1]++;
											bala[k][i][0]=2;
										}else{
											bala[k][i][1]--;
											bala[k][i][0]=1;
										}
										personaje.vidas[0]--;
									}else{
										personaje.vidas[0]=personaje.vidas[0]-2;
										bala[k][i][0]=0;
									}
								}else{
									if(personaje.getAccion(1)==5){
										if(bala[k][i][0]==1){
											bala[k][i][1]++;
											bala[k][i][0]=2;
										}else{
											bala[k][i][1]--;
											bala[k][i][0]=1;
										}
										personaje.vidas[1]--;
									}else{
										personaje.vidas[1]=personaje.vidas[1]-2;
										bala[k][i][0]=0;
									}
								}
							}else{
								if(personaje.getAccion(1)==5){
									if(bala[k][i][0]==1){
										bala[k][i][1]++;
										bala[k][i][0]=2;
									}else{
										bala[k][i][1]--;
										bala[k][i][0]=1;
									}
									personaje.vidas[1]--;
								}else{
									personaje.vidas[1]=personaje.vidas[1]-2;
									bala[k][i][0]=0;
								}
							}
							bala[k][i][3]=0;
						}
					}
					bala[k][i][3]++;
				}
				
			}
		}
		contador++;
	}
	
}
