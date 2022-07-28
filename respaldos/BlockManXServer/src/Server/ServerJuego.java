package Server;

import java.util.ArrayList;

class ServerJuego implements Runnable {
	/**
	 * hecho por miltiton
	 */
    
    
        public static ServerJuego juego=null;
        
        public static boolean primeraVez=true;
        
        public static ArrayList<SuperJugador> superJugador=new ArrayList<>();
        public static ArrayList<JugadoresTotales> jugadoresTotales=new ArrayList<>();
        public static ArrayList<WeaponAxe> weaponAxe=new ArrayList<>();
        public static ArrayList<WeaponBullet> weaponBullet=new ArrayList<>();
        
        public static ArrayList<SuperEnemigo> superEnemigo=new ArrayList<>();
        public static ArrayList<WeaponBall> weaponBall=new ArrayList<>();
        
        public static boolean permiso=true;
        public static boolean cargoPosiciones=false;
        public static boolean estaCerrada=false;
	public static boolean yaTermino=false;
	public static boolean espera=false;
        
        
        //variables de control:
        public static boolean nivelCamuflaje=false;
	public static boolean computadora=false;
        public static boolean camaraOficial=false;
        public static boolean camaraGrande=false;
        public static boolean friendlyFireOficial=(Server.propiedades[1][Server.indexFriendlyFire].equals("true"));
        public static boolean graficos=false;
        public static boolean megaman=(Server.propiedades[1][Server.indexMegamanSkin].equals("true"));
        public static boolean renderizadoInteligente=true;
        public static boolean renderizadoCamara=true;
        public static boolean tetrisSkin=(Server.propiedades[1][Server.indexTetrisSkin].equals("true"));
        
        public static double porcentajeGenerador=0.1;//0.1 = una posibilidad de 1000 de generar un item cada 1 frame.
        
        //variables del loop y dibujo:
        public static int retraso=25;//miliseconds
        public static int contadorDibuja=0;//contador de ciclos del juego.
        public static int divisorDibuja=1;//cada cuantos ciclos se dibuja.
        
        //variables de conexion:
        public static boolean server=false;
        public static boolean client=false;
        public static boolean client_connection_down=true;//falla en la conexion luego de 10 intentos.
        public static String udpData="";//enviado
        public static String s="";//recibido
        public static int contadorCon=0;//recibido
        public static boolean termino_conexion=false,primerCiclo=true,online=true;
        public static int idCliente=0,puertoUDP=(Integer.parseInt(Server.propiedades[1][Server.indexUDPPort])),puertoTCP=(Integer.parseInt(Server.propiedades[1][Server.indexTCPPort]));
        public static String infoElementos="";//proyectiles y cosas.
        
        public static int ancho=0;//ancho
        public static int alto=0;//alto
        
        public static int anchoTile;
        public static int altoTile;
        
        
        public static boolean camara;
        public static boolean friendlyFire;
        
        
	long tiempo=System.currentTimeMillis();
        long tiempo2=System.currentTimeMillis();
	MapaServer mapa;
	
	//numero de niveles (documentos de texto):
	int niveles=10;
        int contPelotas=0;
        int contBloques=0;
        int contGravedadItems=0;
	private int contador=0;
	public boolean seDibuja=true;
	public static boolean corriendo=false;
        public static boolean perdieron=false;
        public static boolean dibujarMapa=true;
        
	public static int nivel=1,nivelServer=1,contadorFrames=0;//nivel de partida.
        
        
        public static String modo="A";
	public static Personaje personaje=new Personaje();//cambiar si falla de static a normal?
	
        
	Enemigo enemigo=new Enemigo();
        
        Camuflaje cam=new Camuflaje();
        
        Camara camera=new Camara();
	
        Actualizaciones act=new Actualizaciones();
        
	public void run(){
            
            
            //config ini:
            this.ancho=0;
            this.alto=0;

            if(this.ancho==0 || this.alto==0){
                if(Teclas.modoJuego==1){
                    this.ancho=160;//40;//160;
                    this.alto=160;//40;//160;
                    if(Teclas.resolucion!=0){
                        camara=true;//false;//true;
                    }
                }else{
                    this.ancho=40;
                    this.alto=40;
                    //si la camara es true entonces no se cambia, se deja tal cual...
                }
            }

            //if(camera.anchoCamara>Juego.ancho) camera.anchoCamara=Juego.ancho;
            //if(camera.altoCamara>Juego.alto) camera.altoCamara=Juego.alto;
            
            anchoTile=Tile.TILE_WIDTH;
            altoTile=Tile.TILE_HEIGHT;
            
            
            
            if(primeraVez==true){
                primeraVez=false;
                for(int i=0;i<Teclas.jugadores;i++){
                    //superJugador.add(new SuperJugador(personaje.posIniX[i],personaje.posIniY[i],i));
                }
            }
	    
	    while(nivel<=niveles){
                //System.out.println("hola1");
	    /*
	    //reemplazar si es bot.
	    if(computadora==false && Menu.ordenDispositivos[1]==-1){
                this.addKeyListener(j2); // Direccionamos la captura a nuestro Pj
	    }
	    
            if(Menu.ordenDispositivos[2]==-1){ 
                this.addKeyListener(j3);
            }
            
            if(Menu.ordenDispositivos[3]==-1){
                this.addKeyListener(j4);
            }
            */
            if(Teclas.modoJuego==1){
                this.modo="A";
                //this.friendlyFire=false;
            }else{
                this.modo="B";
                this.friendlyFire=true;
            }
            //fin config ini
                
                

                permiso=true;
                corriendo=true;
                act.iniciarNivel();//restaura variables que se deben restaurar al iniciar otro nivel.
                
                //se resetean los arraylist.
                MapaServer.items.clear();
                MapaServer.punto.clear();
                MapaServer.generadores.clear();
                
                
                
                
                this.superEnemigo.clear();
                if((nivelCamuflaje==true || nivel>=5) && Teclas.modoJuego==2){
                    mapa = MapaServer.DesdeArchivo("Mapas/map40_B5.txt");
                    cam.iniciarMapa();
                    cam.contadorInicio=0;
                    cam.contadorMover=0;
                    cam.movimientoActual=0;
                }else{
                    //mapa = MapaTiles.DesdeArchivo("Mapas/map160_A3.txt");
                    mapa = MapaServer.DesdeArchivo("Mapas/map"+Integer.toString(ancho)+"_"+modo+Integer.toString(nivel)+".txt");//este es el primero para los bots.3
                    //mapa = MapaTiles.DesdeArchivo("Mapas/map40_Cancha.txt");
                }
	    	if(mapa==null){
                    System.exit(0);
                    permiso=false;
                    nivel=niveles+1;
                }
		int idClienteDistinto=0;
                int jugActual=0;
                for(int j=0;j<superJugador.size();j++){
                    if(idClienteDistinto!=superJugador.get(j).idCliente){
                        jugActual=0;
                        idClienteDistinto=superJugador.get(j).idCliente;
                    }
                    superJugador.get(j).inicioX=Personaje.posIniX[jugActual]*Tile.TILE_WIDTH;
                    superJugador.get(j).inicioY=Personaje.posIniY[jugActual]*Tile.TILE_HEIGHT;
                    jugActual++;
                }
                
                enemigo.iniciar();
                boolean yaEntro=false;
		    while(corriendo==true && permiso==true) // BUCLE INFINITO DEL JUEGO
		    {
                        if (System.currentTimeMillis()-tiempo>retraso) { // actualizamos cada 25 milisegundos.
                            
                            tiempo=System.currentTimeMillis();
                            
                            if(!yaEntro){
                                for(int j=0;j<superJugador.size();j++){
                                    superJugador.get(j).reaparecer();
                                }
                            }
                            
                            yaEntro=true;
                            
                            if(this.perdieron==true){
                                this.perdieron=false;
                                revivirEnemigos();
                                act.iniciarNivel();
                            }

                            if(cargoPosiciones){
                                
                                if(camara==true){
                                    camera.moverSuperCamara();//actualiza la posicion de la camara de cada jugador;
                                }
                                for(int i=0;i<this.superEnemigo.size();i++){
                                    this.superEnemigo.get(i).actualizar();
                                }
                                
                                if(corriendo==false && Teclas.modoJuego==2){
                                    corriendo=true;
                                    tiempo2=System.currentTimeMillis();
                                    espera=true;
                                }
                                if(espera==true && System.currentTimeMillis()-tiempo2>4000){
                                    espera=false;
                                    corriendo=false;
                                }

                            }

                            if(nivelCamuflaje==true || nivel>=5){
                                cam.moverMapa();
                            }
                            
                            //poner aqui las balas rapidas:
                            for(int p=0;p<weaponBall.size();p++){
                                if(weaponBall.get(p).broken){
                                    weaponBall.remove(p);
                                    p--;//se reduce el indice:
                                }else{
                                    weaponBall.get(p).updateBall();
                                }
                            }
                            boolean revivir=true;
                            int vivos=0;
                            for(int j=0;j<superJugador.size();j++){
                                superJugador.get(j).actualizar();
                                if(superJugador.get(j).alive){
                                    vivos++;
                                    revivir=false;
                                }
                            }
                            if(revivir && superJugador.size()>0){
                                this.corriendo=false;
                                this.nivel--;//se resetea el nivel...
                                /*
                                for(int j=0;j<superJugador.size();j++){
                                    superJugador.get(j).reaparecer();
                                }
                                */
                            }
                            
                            if(vivos<=1 && Teclas.modoJuego==2 && superJugador.size()>0){
                                this.corriendo=false;
                            }
                            
                            for(int j=0;j<weaponAxe.size();j++){
                                if(weaponAxe.get(j).broken){
                                    weaponAxe.remove(j);
                                    j--;//se reduce el indice:
                                }else{
                                    weaponAxe.get(j).updateAxe();
                                }
                            }
                            
                            for(int j=0;j<weaponBullet.size();j++){
                                if(weaponBullet.get(j).broken){
                                    weaponBullet.remove(j);
                                    j--;//se reduce el indice:
                                }else{
                                    weaponBullet.get(j).updateBullet();
                                }
                            }
                            for(int j=0;j<MapaServer.items.size();j++){
                                if(MapaServer.items.get(j).tomado){
                                    MapaServer.items.remove(j);
                                    j--;//se reduce el indice:
                                }else{
                                    MapaServer.items.get(j).actualizar();
                                }
                            }
                            for(int j=0;j<MapaServer.punto.size();j++){
                                MapaServer.punto.get(j).updateCheckPoint();
                            }
                            
                            mapa.generarItems(porcentajeGenerador);
                        }
                        if(nivelServer!=nivel){
                            corriendo=false;
                        }
                    }
                    //para que no se dibujen los jugadores hasta que se carge el mapa y se especifique la posicion de cada 1:
                    cargoPosiciones=false;
                    nivel++;
                    if(nivelServer<nivel){
                        nivelServer=nivel;
                    }
                    for(int j=0;j<superJugador.size();j++){
                        superJugador.get(j).reaparecer();
                    }
                    try{//leve retraso para que el servidor alcance a dejar de procesar datos (para q no se interrumpa drasticamente).
                        Thread.sleep(500);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    superEnemigo.clear();
                    weaponAxe.clear();
                    weaponBall.clear();
                    weaponBullet.clear();
	    }
            termino_conexion=true;
            this.reiniciarVariables();
            
	}
        
        public void matarEnemigos(){
            for(int i=0;i<enemigo.cantidadEnemigos;i++){
                enemigo.vidaEnemigo[i]=0;
                enemigo.estaVivo[i]=false;
            }
        }
        
        public void revivirEnemigos(){
            for(int i=0;i<enemigo.cantidadEnemigos;i++){
                enemigo.estaVivo[i]=true;
                enemigo.vidaEnemigo[i]=enemigo.limiteVidaEnemigo;
            }
        }
	public static void actualizarNivel(int n){
		nivel=n;
	}
	public static void actualizarCorriendo(boolean estado){
		corriendo=estado;
	}
        public void reiniciarVariables(){
            
            nivel=1;//se le suma 1...
            
        }
}