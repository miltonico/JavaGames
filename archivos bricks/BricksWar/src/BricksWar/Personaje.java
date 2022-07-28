package BricksWar;

public class Personaje {
	public static int[][][] personaje=new int[2][4][3];//(jugadores),(alto),(ancho).
	public static int[] x=new int[2];
	public static int[] y=new int[2];
	public static int[] vidas=new int[2];
	public static int[] accion=new int[2];
	public static int[] puntos=new int[2];
	public static int[][][] numeros=new int[10][5][3];//numero,y,x.
	//public static int[][] direccion=new int[2][2];//(jugador1=0,jugador2=1)(x=0,y=1).
	public void iniciar(){
		vidas[0]=5;
		vidas[1]=5;
		puntos[0]=0;
		puntos[1]=0;
		
		//numero 0:
		numeros[0][0][0] = 1;
		numeros[0][0][1] = 1;
		numeros[0][0][2] = 1;
		numeros[0][1][0] = 1;
		numeros[0][1][1] = 0;
		numeros[0][1][2] = 1;
		numeros[0][2][0] = 1;
		numeros[0][2][1] = 0;
		numeros[0][2][2] = 1;
		numeros[0][3][0] = 1;
		numeros[0][3][1] = 0;
		numeros[0][3][2] = 1;
		numeros[0][4][0] = 1;
		numeros[0][4][1] = 1;
		numeros[0][4][2] = 1;

		// numero 1:
		numeros[1][0][0] = 0;
		numeros[1][0][1] = 1;
		numeros[1][0][2] = 0;
		numeros[1][1][0] = 1;
		numeros[1][1][1] = 1;
		numeros[1][1][2] = 0;
		numeros[1][2][0] = 0;
		numeros[1][2][1] = 1;
		numeros[1][2][2] = 0;
		numeros[1][3][0] = 0;
		numeros[1][3][1] = 1;
		numeros[1][3][2] = 0;
		numeros[1][4][0] = 1;
		numeros[1][4][1] = 1;
		numeros[1][4][2] = 1;

		// numero 2:
		numeros[2][0][0] = 1;
		numeros[2][0][1] = 1;
		numeros[2][0][2] = 1;
		numeros[2][1][0] = 0;
		numeros[2][1][1] = 0;
		numeros[2][1][2] = 1;
		numeros[2][2][0] = 1;
		numeros[2][2][1] = 1;
		numeros[2][2][2] = 1;
		numeros[2][3][0] = 1;
		numeros[2][3][1] = 0;
		numeros[2][3][2] = 0;
		numeros[2][4][0] = 1;
		numeros[2][4][1] = 1;
		numeros[2][4][2] = 1;

		// numero 3:
		numeros[3][0][0] = 1;
		numeros[3][0][1] = 1;
		numeros[3][0][2] = 1;
		numeros[3][1][0] = 0;
		numeros[3][1][1] = 0;
		numeros[3][1][2] = 1;
		numeros[3][2][0] = 1;
		numeros[3][2][1] = 1;
		numeros[3][2][2] = 1;
		numeros[3][3][0] = 0;
		numeros[3][3][1] = 0;
		numeros[3][3][2] = 1;
		numeros[3][4][0] = 1;
		numeros[3][4][1] = 1;
		numeros[3][4][2] = 1;

		// numero 4:
		numeros[4][0][0] = 1;
		numeros[4][0][1] = 0;
		numeros[4][0][2] = 1;
		numeros[4][1][0] = 1;
		numeros[4][1][1] = 0;
		numeros[4][1][2] = 1;
		numeros[4][2][0] = 1;
		numeros[4][2][1] = 1;
		numeros[4][2][2] = 1;
		numeros[4][3][0] = 0;
		numeros[4][3][1] = 0;
		numeros[4][3][2] = 1;
		numeros[4][4][0] = 0;
		numeros[4][4][1] = 0;
		numeros[4][4][2] = 1;

		// numero 5:
		numeros[5][0][0] = 1;
		numeros[5][0][1] = 1;
		numeros[5][0][2] = 1;
		numeros[5][1][0] = 1;
		numeros[5][1][1] = 0;
		numeros[5][1][2] = 0;
		numeros[5][2][0] = 1;
		numeros[5][2][1] = 1;
		numeros[5][2][2] = 1;
		numeros[5][3][0] = 0;
		numeros[5][3][1] = 0;
		numeros[5][3][2] = 1;
		numeros[5][4][0] = 1;
		numeros[5][4][1] = 1;
		numeros[5][4][2] = 1;

		// numero 6:
		numeros[6][0][0] = 1;
		numeros[6][0][1] = 1;
		numeros[6][0][2] = 1;
		numeros[6][1][0] = 1;
		numeros[6][1][1] = 0;
		numeros[6][1][2] = 0;
		numeros[6][2][0] = 1;
		numeros[6][2][1] = 1;
		numeros[6][2][2] = 1;
		numeros[6][3][0] = 1;
		numeros[6][3][1] = 0;
		numeros[6][3][2] = 1;
		numeros[6][4][0] = 1;
		numeros[6][4][1] = 1;
		numeros[6][4][2] = 1;

		// numero 7:
		numeros[7][0][0] = 1;
		numeros[7][0][1] = 1;
		numeros[7][0][2] = 1;
		numeros[7][1][0] = 0;
		numeros[7][1][1] = 0;
		numeros[7][1][2] = 1;
		numeros[7][2][0] = 0;
		numeros[7][2][1] = 0;
		numeros[7][2][2] = 1;
		numeros[7][3][0] = 0;
		numeros[7][3][1] = 0;
		numeros[7][3][2] = 1;
		numeros[7][4][0] = 0;
		numeros[7][4][1] = 0;
		numeros[7][4][2] = 1;

		// numero 8:
		numeros[8][0][0] = 1;
		numeros[8][0][1] = 1;
		numeros[8][0][2] = 1;
		numeros[8][1][0] = 1;
		numeros[8][1][1] = 0;
		numeros[8][1][2] = 1;
		numeros[8][2][0] = 1;
		numeros[8][2][1] = 1;
		numeros[8][2][2] = 1;
		numeros[8][3][0] = 1;
		numeros[8][3][1] = 0;
		numeros[8][3][2] = 1;
		numeros[8][4][0] = 1;
		numeros[8][4][1] = 1;
		numeros[8][4][2] = 1;

		// numero 9:
		numeros[9][0][0] = 1;
		numeros[9][0][1] = 1;
		numeros[9][0][2] = 1;
		numeros[9][1][0] = 1;
		numeros[9][1][1] = 0;
		numeros[9][1][2] = 1;
		numeros[9][2][0] = 1;
		numeros[9][2][1] = 1;
		numeros[9][2][2] = 1;
		numeros[9][3][0] = 0;
		numeros[9][3][1] = 0;
		numeros[9][3][2] = 1;
		numeros[9][4][0] = 0;
		numeros[9][4][1] = 0;
		numeros[9][4][2] = 1;
				
	}
	public void animar(int jugador, int accion){
    	switch(accion){
    	case 0://normal
    		personaje[jugador][0][0]=0;
    		personaje[jugador][0][1]=1;
    		personaje[jugador][0][2]=0;
    		personaje[jugador][1][0]=1;
    		personaje[jugador][1][1]=1;
    		personaje[jugador][1][2]=1;
    		personaje[jugador][2][0]=0;
    		personaje[jugador][2][1]=1;
    		personaje[jugador][2][2]=0;
    		personaje[jugador][3][0]=1;
    		personaje[jugador][3][1]=0;
    		personaje[jugador][3][2]=1;
    		break;
    	case 1://derecha
    		personaje[jugador][0][0]=0;
    		personaje[jugador][0][1]=1;
    		personaje[jugador][0][2]=0;
    		personaje[jugador][1][0]=0;
    		personaje[jugador][1][1]=1;
    		personaje[jugador][1][2]=1;
    		personaje[jugador][2][0]=0;
    		personaje[jugador][2][1]=1;
    		personaje[jugador][2][2]=0;
    		personaje[jugador][3][0]=0;
    		personaje[jugador][3][1]=1;
    		personaje[jugador][3][2]=0;
    		break;
    	case 2://salto derecha
    		personaje[jugador][0][0]=0;
    		personaje[jugador][0][1]=1;
    		personaje[jugador][0][2]=0;
    		personaje[jugador][1][0]=0;
    		personaje[jugador][1][1]=1;
    		personaje[jugador][1][2]=1;
    		personaje[jugador][2][0]=0;
    		personaje[jugador][2][1]=1;
    		personaje[jugador][2][2]=0;
    		personaje[jugador][3][0]=1;
    		personaje[jugador][3][1]=1;
    		personaje[jugador][3][2]=0;
    		break;
    	case 3://izquierda
    		personaje[jugador][0][0]=0;
    		personaje[jugador][0][1]=1;
    		personaje[jugador][0][2]=0;
    		personaje[jugador][1][0]=1;
    		personaje[jugador][1][1]=1;
    		personaje[jugador][1][2]=0;
    		personaje[jugador][2][0]=0;
    		personaje[jugador][2][1]=1;
    		personaje[jugador][2][2]=0;
    		personaje[jugador][3][0]=0;
    		personaje[jugador][3][1]=1;
    		personaje[jugador][3][2]=0;
    		break;
    	case 4://salto izquierda
    		personaje[jugador][0][0]=0;
    		personaje[jugador][0][1]=1;
    		personaje[jugador][0][2]=0;
    		personaje[jugador][1][0]=1;
    		personaje[jugador][1][1]=1;
    		personaje[jugador][1][2]=0;
    		personaje[jugador][2][0]=0;
    		personaje[jugador][2][1]=1;
    		personaje[jugador][2][2]=0;
    		personaje[jugador][3][0]=0;
    		personaje[jugador][3][1]=1;
    		personaje[jugador][3][2]=1;
    		break;
    	case 5://paleta(antiguo abajo)
    		personaje[jugador][0][0]=0;
    		personaje[jugador][0][1]=1;
    		personaje[jugador][0][2]=0;
    		personaje[jugador][1][0]=0;
    		personaje[jugador][1][1]=1;
    		personaje[jugador][1][2]=0;
    		personaje[jugador][2][0]=0;
    		personaje[jugador][2][1]=1;
    		personaje[jugador][2][2]=0;
    		personaje[jugador][3][0]=0;
    		personaje[jugador][3][1]=1;
    		personaje[jugador][3][2]=0;
    		break;
    	case 6://agacharse
    		personaje[jugador][0][0]=0;
    		personaje[jugador][0][1]=0;
    		personaje[jugador][0][2]=0;
    		personaje[jugador][1][0]=0;
    		personaje[jugador][1][1]=1;
    		personaje[jugador][1][2]=0;
    		personaje[jugador][2][0]=1;
    		personaje[jugador][2][1]=1;
    		personaje[jugador][2][2]=1;
    		personaje[jugador][3][0]=1;
    		personaje[jugador][3][1]=1;
    		personaje[jugador][3][2]=1;
    		break;
    	}
    }
	
	public int getAccion(int jugador) // Obtenemos la coordenada horizontal actual del cuadrado
    {
        return accion[jugador];
    }
	
	public void setAccion(int jugador, int accion)//se determina la posicion o accion del jugador
    {
        this.accion[jugador]=accion;
    }
 
 
	public int getX(int jugador) // Obtenemos la coordenada horizontal actual del cuadrado
    {
        return x[jugador];
    }
 
    public void setX(int jugador, int valor) //Asignamos la coordenada horizontal actual del cuadrado
    {
    	x[jugador]=valor;
    }
 
    public int getY(int jugador) // Obtenemos la coordenada vertical actual del cuadrado
    {
        return y[jugador];
    }
 
    public void setY(int jugador, int valor) // Asignamos la coordenada vertical actual del cuadrado
    {
        y[jugador]=valor;
    }
    
}
