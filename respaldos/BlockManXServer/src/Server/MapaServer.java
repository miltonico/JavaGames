package Server;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MapaServer {
    public static ArrayList<Generador> generadores=new ArrayList<>();
    public static ArrayList<Items> items=new ArrayList<>();
    public static ArrayList<CheckPoint> punto=new ArrayList<>();
    public static Personaje personaje=new Personaje();
    public static Actualizaciones act=new Actualizaciones();
    Enemigo enemigo=new Enemigo();
    public static int map[][];
    public static int mapI[][];
    public static int mapaFisico[][];//para que puedan haber mas bloques de colores con masa (0=nada, 1=bloque, 2=lava).

    public MapaServer(int[][] existingMap) {

        map = new int[existingMap.length][existingMap[0].length];
        mapI = new int[existingMap.length][existingMap[0].length];
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                map[y][x] = existingMap[y][x];
            }
        }

    }
    public MapaServer(int width,int height){
            map = new int[height][width];
            mapI = new int[height][width];
    }

    public static MapaServer DesdeArchivo(String fileName){
        MapaServer layer = null;

        ArrayList<ArrayList<Integer>> tempLayout = new ArrayList<>();
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(MapaServer.class.getClassLoader().getResourceAsStream(fileName)));
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
        }catch(Exception e){
            System.out.println("Could not load map: "+fileName);
            return null;
        }

        int width = tempLayout.get(0).size();
        int height = tempLayout.size();

        layer = new MapaServer(width,height);

        mapaFisico = new int[height][width];
        for(int i=0;i<Teclas.jugadores;i++){
            if(Teclas.modoJuego==1){
                switch(i){
                    case 0:
                        Personaje.posIniX[i]=1;
                        break;
                    case 1:
                        Personaje.posIniX[i]=5;
                        break;
                    case 2:
                        Personaje.posIniX[i]=9;
                        break;
                    case 3:
                        Personaje.posIniX[i]=13;
                        break;
                }
                Personaje.posIniY[i]=1;
            }else{
                switch(i){
                    case 0:
                        Personaje.posIniX[i]=1;
                        break;
                    case 1:
                        Personaje.posIniX[i]=ServerJuego.ancho-4;
                        break;
                    case 2:
                        Personaje.posIniX[i]=11;
                        break;
                    case 3:
                        Personaje.posIniX[i]=ServerJuego.ancho-14;
                        break;

                }
                Personaje.posIniY[i]=1;
            }

        }
        Teclas.estrellaX=-1;
        Teclas.estrellaY=-1;
        int contEnemigo=0;
        int contSuperEnemigo=0;
        int indicePunto=1;
        int indiceGenerador=0;
        for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                        layer.map[y][x] = tempLayout.get(y).get(x);
                        if(layer.map[y][x]>=100){
                            String cadenaMapa=Integer.toString(layer.map[y][x]);
                            int jugador=Integer.parseInt(cadenaMapa.substring(0,cadenaMapa.length()-2));
                            layer.map[y][x]=Integer.parseInt(Integer.toString(layer.map[y][x]).substring(cadenaMapa.length()-2,cadenaMapa.length()));
                            if(jugador-1<4){//maxima cantidad de jugadores por cliente=4.
                                Personaje.posIniX[jugador-1]=x;
                                Personaje.posIniY[jugador-1]=y;
                            }
                            if(jugador==5){//estrella
                                Teclas.estrellaX=x;
                                Teclas.estrellaY=y;
                            }
                            if(jugador==8){//enemigo
                                ServerJuego.superEnemigo.add(new SuperEnemigo(x,y,contSuperEnemigo));
                                contSuperEnemigo++;
                            }
                            if(jugador==9){//super enemigo
                                //Juego.superEnemigo.add(new SuperEnemigo(contSuperEnemigo,x,y));
                                //contSuperEnemigo++;
                            }
                            if(jugador>=10 && jugador<45){//items
                                items.add(new Items(jugador,x,y));//jugador en verdad es un item en este caso.
                            }
                            if(jugador==48){//check point.
                                punto.add(new CheckPoint(indicePunto,x,y));
                                //System.out.println("x="+x+", y="+y);
                                indicePunto++;
                            }
                            if(jugador==49 || jugador==50 || jugador==51 || jugador==52){//generadores.
                                generadores.add(new Generador(jugador,x,y));//jugador en realidad es el tipo de item del generador.
                                indiceGenerador++;
                            }
                        }
                        mapaFisico[y][x]=obtenerPropiedadBloque(layer.map[y][x]);
                }

        }
        ServerJuego.cargoPosiciones=true;
        Enemigo.cantidadEnemigos=contEnemigo;

        return layer;

    }

    public static int obtenerPropiedadBloque(int index){

        int propiedad=0;

        switch(index){
            case 0:
                propiedad=1;
                break;
            case 1:
                propiedad=1;
                break;
            case 2:
                propiedad=1;
                break;
            case 3:
                propiedad=1;
                break;
            case 4:
                propiedad=1;
                break;
            case 5:
                propiedad=1;
                break;
            case 6:
                propiedad=1;
                break;
            case 7:
                propiedad=1;
                break;
            case 8:
                propiedad=1;
                break;
            case 9:
                propiedad=1;
                break;
            case 10:
                propiedad=1;
                break;
            case 11:
                propiedad=1;
                break;
            case 12:
                propiedad=0;
                break;
            case 13:
                propiedad=0;
                break;
            case 14:
                propiedad=1;
                break;
            case 15:
                propiedad=0;
                break;
            case 16:
                propiedad=0;
                break;
            case 17:
                propiedad=2;//lava
                break;
            case 18:
                propiedad=3;//curacion
                break;
            case 19:
                propiedad=1;
                break;
            case 20:
                propiedad=0;
                break;
            case 21:
                propiedad=4;//agua
                break;
            case 22:
                propiedad=1;
                break;
            case 23:
                propiedad=5;//barro(agua oscura).
                break;
            case 26://bounce
                propiedad=1;
                break;
            case 27://bounce
                propiedad=1;
                break;
            case 28://bounce
                propiedad=1;
                break;
            case 29:
                propiedad=1;
                break;
            case 30:
                propiedad=1;
                break;
            case 31:
                propiedad=1;
                break;
            case 32:
                propiedad=1;
                break;
            case 33:
                propiedad=1;
                break;
            case 34:
                propiedad=1;
                break;
            case 35:
                propiedad=1;
                break;
            case 36:
                propiedad=1;
                break;
            case 37:
                propiedad=1;
                break;
            case 38:
                propiedad=1;
                break;
            case 39:
                propiedad=1;
                break;
            case 40:
                propiedad=1;
                break;
            case 41:
                propiedad=1;
                break;
            case 42:
                propiedad=1;
                break;
            case 43:
                propiedad=1;
                break;
            case 44:
                propiedad=1;
                break;
            case 45://marco dorado de seleccion.
                propiedad=0;
                break;
            case 46:
                propiedad=1;//brazo arma(descargada).
                break;
            case 47:
                propiedad=1;//brazo arma(cargada).
                break;
            case 48:
                propiedad=1;//bullet.
                break;
            case 49:
                propiedad=1;//axe enemigo.
                break;
            case 50:
                propiedad=1;//axe amarilla
                break;
            case 51:
                propiedad=1;//axe roja
                break;
            case 52:
                propiedad=1;//axe verde
                break;
            case 53:
                propiedad=1;//axe azul
                break;
        }

        return propiedad;
    }


    public void generarItems(double porcentaje){//porcentaje/100 posibilidades de generar un item...
        porcentaje*=10;
        for(int i=0;i<generadores.size();i++){
            int numero=(int)(Math.random()*1000+1);//genera un numero del 1 al 1000...
            if(numero<=porcentaje){
                int tipo=0;
                switch(generadores.get(i).tipo){
                    case 49:
                        tipo=37;//curaciones
                        break;
                    case 50:
                        tipo=49;//hachas
                        break;
                }
                if(tipo!=0){
                    items.add(new Items(tipo,generadores.get(i).x,generadores.get(i).y));
                }
            }
        }

    }
    
    public static String TransformarDatosJugadores(int idCliente){
        String texto="";
        for(int j=0;j<ServerJuego.superJugador.size();j++){
            if(ServerJuego.superJugador.get(j).idCliente==idCliente){
                texto+=ServerJuego.superJugador.get(j).health+","+ServerJuego.superJugador.get(j).healthLimit+","+ServerJuego.superJugador.get(j).vidas+";";//separador entre datos.
            }
        }
        return texto+"e";//separador entre elementos.
    }
    
    public static String TransformarCamaras(int idCliente){
        String texto="";
        for(int j=0;j<ServerJuego.superJugador.size();j++){
            if(ServerJuego.superJugador.get(j).idCliente==idCliente){
                texto+=ServerJuego.superJugador.get(j).posCamX+","+ServerJuego.superJugador.get(j).posCamY+";";//separador entre camaras.
            }
        }
        return texto+"e";//separador entre elementos.
    }

    public static String TransformarElementos(int idCliente){
        String elementos="";
        for(int j=0;j<ServerJuego.superJugador.size();j++){
            if(ServerJuego.superJugador.get(j).megaman==true){
                int eslabones=3;
                int x1=ServerJuego.superJugador.get(j).x+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).cintura)][0][1];
                int y1=ServerJuego.superJugador.get(j).y+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).cintura)][1][1];
                int x2=ServerJuego.superJugador.get(j).x+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).pieDer)][0][1];
                int y2=ServerJuego.superJugador.get(j).y+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).pieDer)][1][1];

                //pierna derecha:
                elementos+=TransformaCadena(ServerJuego.superJugador.get(j).direccion,1,eslabones,x1,y1,x2,y2);


                x1=ServerJuego.superJugador.get(j).x+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).torso)][0][1];
                y1=ServerJuego.superJugador.get(j).y+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).torso)][1][1];
                x2=ServerJuego.superJugador.get(j).x+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).brazoDer)][0][1];
                y2=ServerJuego.superJugador.get(j).y+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).brazoDer)][1][1];

                eslabones=2;
                //brazo derecho:
                elementos+=TransformaCadena(ServerJuego.superJugador.get(j).direccion,3,eslabones,x1,y1,x2,y2);


                //partes:
                if(ServerJuego.superJugador.get(j).direccion==1){//izq
                    elementos+=TransformaTile(ServerJuego.superJugador.get(j).x+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).pieDer)][0][1],ServerJuego.superJugador.get(j).y+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).pieDer)][1][1],74);
                }else{//der
                    elementos+=TransformaTile(ServerJuego.superJugador.get(j).x+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).pieDer)][0][1],ServerJuego.superJugador.get(j).y+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).pieDer)][1][1],73);
                }
                if(ServerJuego.superJugador.get(j).direccion==1){//izq
                    if(ServerJuego.superJugador.get(j).disparando==true){
                        elementos+=TransformaTile(ServerJuego.superJugador.get(j).x+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).brazoDer)][0][1],ServerJuego.superJugador.get(j).y+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).brazoDer)][1][1],75);
                    }else{
                        elementos+=TransformaTile(ServerJuego.superJugador.get(j).x+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).brazoDer)][0][1],ServerJuego.superJugador.get(j).y+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).brazoDer)][1][1],68);
                    }
                }else{//der
                    if(ServerJuego.superJugador.get(j).disparando==true){
                        elementos+=TransformaTile(ServerJuego.superJugador.get(j).x+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).brazoDer)][0][1],ServerJuego.superJugador.get(j).y+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).brazoDer)][1][1],76);
                    }else{
                        elementos+=TransformaTile(ServerJuego.superJugador.get(j).x+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).brazoDer)][0][1],ServerJuego.superJugador.get(j).y+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).brazoDer)][1][1],67);
                    }
                }
                elementos+=TransformaTile(ServerJuego.superJugador.get(j).x+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).cintura)][0][1],ServerJuego.superJugador.get(j).y+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).cintura)][1][1],66);
                if(ServerJuego.superJugador.get(j).direccion==1){//izq
                    elementos+=TransformaTile(ServerJuego.superJugador.get(j).x+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).torso)][0][1],ServerJuego.superJugador.get(j).y+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).torso)][1][1],65);
                }else{//der
                    elementos+=TransformaTile(ServerJuego.superJugador.get(j).x+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).torso)][0][1],ServerJuego.superJugador.get(j).y+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).torso)][1][1],64);
                }

                x1=ServerJuego.superJugador.get(j).x+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).cintura)][0][1];
                y1=ServerJuego.superJugador.get(j).y+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).cintura)][1][1];
                x2=ServerJuego.superJugador.get(j).x+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).pieIzq)][0][1];
                y2=ServerJuego.superJugador.get(j).y+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).pieIzq)][1][1];

                eslabones=3;
                //pierna izquierda:
                elementos+=TransformaCadena(ServerJuego.superJugador.get(j).direccion,2,eslabones,x1,y1,x2,y2);

                x1=ServerJuego.superJugador.get(j).x+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).torso)][0][1];
                y1=ServerJuego.superJugador.get(j).y+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).torso)][1][1];
                x2=ServerJuego.superJugador.get(j).x+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).brazoIzq)][0][1];
                y2=ServerJuego.superJugador.get(j).y+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).brazoIzq)][1][1];

                eslabones=2;
                //brazo izquierdo:
                elementos+=TransformaCadena(ServerJuego.superJugador.get(j).direccion,4,eslabones,x1,y1,x2,y2);

                elementos+=TransformaTile(ServerJuego.superJugador.get(j).x+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).pieIzq)][0][1],ServerJuego.superJugador.get(j).y+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).pieIzq)][1][1],72);
                if(ServerJuego.superJugador.get(j).direccion==1){//izq
                    elementos+=TransformaTile(ServerJuego.superJugador.get(j).x+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).brazoIzq)][0][1],ServerJuego.superJugador.get(j).y+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).brazoIzq)][1][1],70);
                }else{//der
                    elementos+=TransformaTile(ServerJuego.superJugador.get(j).x+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).brazoIzq)][0][1],ServerJuego.superJugador.get(j).y+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).brazoIzq)][1][1],69);
                }

                if(ServerJuego.superJugador.get(j).direccion==1){//izq
                    elementos+=TransformaTile(ServerJuego.superJugador.get(j).x+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).cabeza)][0][1],ServerJuego.superJugador.get(j).y+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).cabeza)][1][1],62);
                }else{//der
                    elementos+=TransformaTile(ServerJuego.superJugador.get(j).x+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).cabeza)][0][1],ServerJuego.superJugador.get(j).y+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).cabeza)][1][1],61);
                }
            }else{
                

                if(map[(ServerJuego.superJugador.get(j).y+Tile.TILE_HEIGHT/2+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).pieIzq)][1][1])/Tile.TILE_HEIGHT][(ServerJuego.superJugador.get(j).x+Tile.TILE_WIDTH/2+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).pieIzq)][0][1])/Tile.TILE_WIDTH]==25)
                    elementos+=TransformaTile(ServerJuego.superJugador.get(j).x+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).pieIzq)][0][1],ServerJuego.superJugador.get(j).y+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).pieIzq)][1][1],24);
                else elementos+=TransformaTile(ServerJuego.superJugador.get(j).x+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).pieIzq)][0][1],ServerJuego.superJugador.get(j).y+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).pieIzq)][1][1],ServerJuego.superJugador.get(j).index);
                if(map[(ServerJuego.superJugador.get(j).y+Tile.TILE_HEIGHT/2+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).brazoIzq)][1][1])/Tile.TILE_HEIGHT][(ServerJuego.superJugador.get(j).x+Tile.TILE_WIDTH/2+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).brazoIzq)][0][1])/Tile.TILE_WIDTH]==25) 
                    elementos+=TransformaTile(ServerJuego.superJugador.get(j).x+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).brazoIzq)][0][1],ServerJuego.superJugador.get(j).y+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).brazoIzq)][1][1],24);
                else elementos+=TransformaTile(ServerJuego.superJugador.get(j).x+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).brazoIzq)][0][1],ServerJuego.superJugador.get(j).y+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).brazoIzq)][1][1],ServerJuego.superJugador.get(j).index);
                if(map[(ServerJuego.superJugador.get(j).y+Tile.TILE_HEIGHT/2+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).cintura)][1][1])/Tile.TILE_HEIGHT][(ServerJuego.superJugador.get(j).x+Tile.TILE_WIDTH/2+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).cintura)][0][1])/Tile.TILE_WIDTH]==25)
                    elementos+=TransformaTile(ServerJuego.superJugador.get(j).x+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).cintura)][0][1],ServerJuego.superJugador.get(j).y+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).cintura)][1][1],24);
                else elementos+=TransformaTile(ServerJuego.superJugador.get(j).x+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).cintura)][0][1],ServerJuego.superJugador.get(j).y+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).cintura)][1][1],ServerJuego.superJugador.get(j).index);
                if(map[(ServerJuego.superJugador.get(j).y+Tile.TILE_HEIGHT/2+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).torso)][1][1])/Tile.TILE_HEIGHT][(ServerJuego.superJugador.get(j).x+Tile.TILE_WIDTH/2+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).torso)][0][1])/Tile.TILE_WIDTH]==25)
                    elementos+=TransformaTile(ServerJuego.superJugador.get(j).x+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).torso)][0][1],ServerJuego.superJugador.get(j).y+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).torso)][1][1],24);
                else elementos+=TransformaTile(ServerJuego.superJugador.get(j).x+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).torso)][0][1],ServerJuego.superJugador.get(j).y+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).torso)][1][1],ServerJuego.superJugador.get(j).index);
                if(ServerJuego.superJugador.get(j).posicion!=11){//si no es un cuadradito:
                    if(map[(ServerJuego.superJugador.get(j).y+Tile.TILE_HEIGHT/2+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).cabeza)][1][1])/Tile.TILE_HEIGHT][(ServerJuego.superJugador.get(j).x+Tile.TILE_WIDTH/2+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).cabeza)][0][1])/Tile.TILE_WIDTH]==25){
                        elementos+=TransformaTile(ServerJuego.superJugador.get(j).x+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).cabeza)][0][1],ServerJuego.superJugador.get(j).y+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).cabeza)][1][1],24);
                    }else{elementos+=TransformaTile(ServerJuego.superJugador.get(j).x+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).cabeza)][0][1],ServerJuego.superJugador.get(j).y+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).cabeza)][1][1],ServerJuego.superJugador.get(j).index);
                        if(map[(ServerJuego.superJugador.get(j).y+Tile.TILE_HEIGHT/2+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).cabeza)][1][1])/Tile.TILE_HEIGHT][(ServerJuego.superJugador.get(j).x+Tile.TILE_WIDTH/2+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).cabeza)][0][1])/Tile.TILE_WIDTH]==25){
                            if(ServerJuego.tetrisSkin==false && ServerJuego.superJugador.get(j).deadDimension==false) elementos+=TransformaTile(ServerJuego.superJugador.get(j).x+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).cabeza)][0][1],ServerJuego.superJugador.get(j).y+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).cabeza)][1][1],54);
                        }else{
                            if(ServerJuego.tetrisSkin==false && ServerJuego.superJugador.get(j).deadDimension==false) elementos+=TransformaTile(ServerJuego.superJugador.get(j).x+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).cabeza)][0][1],ServerJuego.superJugador.get(j).y+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).cabeza)][1][1],54);
                        }
                    }
                }
                if(map[(ServerJuego.superJugador.get(j).y+Tile.TILE_HEIGHT/2+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).pieDer)][1][1])/Tile.TILE_HEIGHT][(ServerJuego.superJugador.get(j).x+Tile.TILE_WIDTH/2+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).pieDer)][0][1])/Tile.TILE_WIDTH]==25)
                    elementos+=TransformaTile(ServerJuego.superJugador.get(j).x+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).pieDer)][0][1],ServerJuego.superJugador.get(j).y+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).pieDer)][1][1],24);
                else elementos+=TransformaTile(ServerJuego.superJugador.get(j).x+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).pieDer)][0][1],ServerJuego.superJugador.get(j).y+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).pieDer)][1][1],ServerJuego.superJugador.get(j).index);
                if(map[(ServerJuego.superJugador.get(j).y+Tile.TILE_HEIGHT/2+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).brazoDer)][1][1])/Tile.TILE_HEIGHT][(ServerJuego.superJugador.get(j).x+Tile.TILE_WIDTH/2+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).brazoDer)][0][1])/Tile.TILE_WIDTH]==25)
                    elementos+=TransformaTile(ServerJuego.superJugador.get(j).x+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).brazoDer)][0][1],ServerJuego.superJugador.get(j).y+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).brazoDer)][1][1],24);
                else elementos+=TransformaTile(ServerJuego.superJugador.get(j).x+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).brazoDer)][0][1],ServerJuego.superJugador.get(j).y+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).brazoDer)][1][1],(ServerJuego.superJugador.get(j).energy>=ServerJuego.superJugador.get(j).energyLimit?47:46));
                if(ServerJuego.superJugador.get(j).posicion==11){//si es un cuadradito:
                    if(map[(ServerJuego.superJugador.get(j).y+Tile.TILE_HEIGHT/2+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).cabeza)][1][1])/Tile.TILE_HEIGHT][(ServerJuego.superJugador.get(j).x+Tile.TILE_WIDTH/2+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).cabeza)][0][1])/Tile.TILE_WIDTH]==25){
                        elementos+=TransformaTile(ServerJuego.superJugador.get(j).x+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).cabeza)][0][1],ServerJuego.superJugador.get(j).y+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).cabeza)][1][1],24);
                    }else{elementos+=TransformaTile(ServerJuego.superJugador.get(j).x+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).cabeza)][0][1],ServerJuego.superJugador.get(j).y+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).cabeza)][1][1],ServerJuego.superJugador.get(j).index);
                        if(map[(ServerJuego.superJugador.get(j).y+Tile.TILE_HEIGHT/2+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).cabeza)][1][1])/Tile.TILE_HEIGHT][(ServerJuego.superJugador.get(j).x+Tile.TILE_WIDTH/2+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).cabeza)][0][1])/Tile.TILE_WIDTH]==25){
                            if(ServerJuego.tetrisSkin==false && ServerJuego.superJugador.get(j).deadDimension==false) elementos+=TransformaTile(ServerJuego.superJugador.get(j).x+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).cabeza)][0][1],ServerJuego.superJugador.get(j).y+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).cabeza)][1][1],54);
                        }else{
                            if(ServerJuego.tetrisSkin==false && ServerJuego.superJugador.get(j).deadDimension==false) elementos+=TransformaTile(ServerJuego.superJugador.get(j).x+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).cabeza)][0][1],ServerJuego.superJugador.get(j).y+ServerJuego.superJugador.get(j).positionParts[Integer.parseInt(ServerJuego.superJugador.get(j).cabeza)][1][1],54);
                        }
                    }
                }
            }
        }
        for(int j=0;j<ServerJuego.superEnemigo.size();j++){
            //dibujar cuerpo de cada enemigo:
            if(ServerJuego.camara){
                //si estan en la misma dimension:
                if(!ServerJuego.superEnemigo.get(j).deadDimension){//ServerJuego.superJugador.get(i).deadDimension==ServerJuego.superEnemigo.get(j).deadDimension
                    if(ServerJuego.superEnemigo.get(j).direccion==1){
                        if(map[(ServerJuego.superEnemigo.get(j).y+Tile.TILE_HEIGHT/2+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).pieIzq)][1][1])/Tile.TILE_HEIGHT][(ServerJuego.superEnemigo.get(j).x+Tile.TILE_WIDTH/2+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).pieIzq)][0][1])/Tile.TILE_WIDTH]==25)
                            elementos+=TransformaTile(ServerJuego.superEnemigo.get(j).x+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).pieIzq)][0][1],ServerJuego.superEnemigo.get(j).y+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).pieIzq)][1][1],24);
                        else elementos+=TransformaTile(ServerJuego.superEnemigo.get(j).x+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).pieIzq)][0][1],ServerJuego.superEnemigo.get(j).y+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).pieIzq)][1][1],11);
                        if(map[(ServerJuego.superEnemigo.get(j).y+Tile.TILE_HEIGHT/2+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).brazoIzq)][1][1])/Tile.TILE_HEIGHT][(ServerJuego.superEnemigo.get(j).x+Tile.TILE_WIDTH/2+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).brazoIzq)][0][1])/Tile.TILE_WIDTH]==25) 
                            elementos+=TransformaTile(ServerJuego.superEnemigo.get(j).x+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).brazoIzq)][0][1],ServerJuego.superEnemigo.get(j).y+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).brazoIzq)][1][1],24);
                        else elementos+=TransformaTile(ServerJuego.superEnemigo.get(j).x+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).brazoIzq)][0][1],ServerJuego.superEnemigo.get(j).y+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).brazoIzq)][1][1],11);
                        if(map[(ServerJuego.superEnemigo.get(j).y+Tile.TILE_HEIGHT/2+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).cintura)][1][1])/Tile.TILE_HEIGHT][(ServerJuego.superEnemigo.get(j).x+Tile.TILE_WIDTH/2+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).cintura)][0][1])/Tile.TILE_WIDTH]==25)
                            elementos+=TransformaTile(ServerJuego.superEnemigo.get(j).x+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).cintura)][0][1],ServerJuego.superEnemigo.get(j).y+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).cintura)][1][1],24);
                        else elementos+=TransformaTile(ServerJuego.superEnemigo.get(j).x+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).cintura)][0][1],ServerJuego.superEnemigo.get(j).y+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).cintura)][1][1],11);
                        if(map[(ServerJuego.superEnemigo.get(j).y+Tile.TILE_HEIGHT/2+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).torso)][1][1])/Tile.TILE_HEIGHT][(ServerJuego.superEnemigo.get(j).x+Tile.TILE_WIDTH/2+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).torso)][0][1])/Tile.TILE_WIDTH]==25)
                            elementos+=TransformaTile(ServerJuego.superEnemigo.get(j).x+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).torso)][0][1],ServerJuego.superEnemigo.get(j).y+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).torso)][1][1],24);
                        else elementos+=TransformaTile(ServerJuego.superEnemigo.get(j).x+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).torso)][0][1],ServerJuego.superEnemigo.get(j).y+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).torso)][1][1],11);
                        if(map[(ServerJuego.superEnemigo.get(j).y+Tile.TILE_HEIGHT/2+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).cabeza)][1][1])/Tile.TILE_HEIGHT][(ServerJuego.superEnemigo.get(j).x+Tile.TILE_WIDTH/2+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).cabeza)][0][1])/Tile.TILE_WIDTH]==25){
                            elementos+=TransformaTile(ServerJuego.superEnemigo.get(j).x+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).cabeza)][0][1],ServerJuego.superEnemigo.get(j).y+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).cabeza)][1][1],24);
                        }else{elementos+=TransformaTile(ServerJuego.superEnemigo.get(j).x+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).cabeza)][0][1],ServerJuego.superEnemigo.get(j).y+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).cabeza)][1][1],11);
                            if(map[(ServerJuego.superEnemigo.get(j).y+Tile.TILE_HEIGHT/2+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).cabeza)][1][1])/Tile.TILE_HEIGHT][(ServerJuego.superEnemigo.get(j).x+Tile.TILE_WIDTH/2+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).cabeza)][0][1])/Tile.TILE_WIDTH]==25){
                                if(ServerJuego.tetrisSkin==false && ServerJuego.superEnemigo.get(j).deadDimension==false) elementos+=TransformaTile(ServerJuego.superEnemigo.get(j).x+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).cabeza)][0][1],ServerJuego.superEnemigo.get(j).y+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).cabeza)][1][1],54);
                            }else{
                                if(ServerJuego.tetrisSkin==false && ServerJuego.superEnemigo.get(j).deadDimension==false) elementos+=TransformaTile(ServerJuego.superEnemigo.get(j).x+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).cabeza)][0][1],ServerJuego.superEnemigo.get(j).y+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).cabeza)][1][1],54);
                            }
                        }
                        if(map[(ServerJuego.superEnemigo.get(j).y+Tile.TILE_HEIGHT/2+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).pieDer)][1][1])/Tile.TILE_HEIGHT][(ServerJuego.superEnemigo.get(j).x+Tile.TILE_WIDTH/2+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).pieDer)][0][1])/Tile.TILE_WIDTH]==25)
                            elementos+=TransformaTile(ServerJuego.superEnemigo.get(j).x+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).pieDer)][0][1],ServerJuego.superEnemigo.get(j).y+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).pieDer)][1][1],24);
                        else elementos+=TransformaTile(ServerJuego.superEnemigo.get(j).x+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).pieDer)][0][1],ServerJuego.superEnemigo.get(j).y+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).pieDer)][1][1],11);
                        if(map[(ServerJuego.superEnemigo.get(j).y+Tile.TILE_HEIGHT/2+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).brazoDer)][1][1])/Tile.TILE_HEIGHT][(ServerJuego.superEnemigo.get(j).x+Tile.TILE_WIDTH/2+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).brazoDer)][0][1])/Tile.TILE_WIDTH]==25)
                            elementos+=TransformaTile(ServerJuego.superEnemigo.get(j).x+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).brazoDer)][0][1],ServerJuego.superEnemigo.get(j).y+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).brazoDer)][1][1],24);
                        else elementos+=TransformaTile(ServerJuego.superEnemigo.get(j).x+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).brazoDer)][0][1],ServerJuego.superEnemigo.get(j).y+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).brazoDer)][1][1],11);
                    }else{
                        if(map[(ServerJuego.superEnemigo.get(j).y+Tile.TILE_HEIGHT/2+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).pieDer)][1][1])/Tile.TILE_HEIGHT][(ServerJuego.superEnemigo.get(j).x+Tile.TILE_WIDTH/2+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).pieDer)][0][1])/Tile.TILE_WIDTH]==25)
                            elementos+=TransformaTile(ServerJuego.superEnemigo.get(j).x+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).pieDer)][0][1],ServerJuego.superEnemigo.get(j).y+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).pieDer)][1][1],24);
                        else elementos+=TransformaTile(ServerJuego.superEnemigo.get(j).x+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).pieDer)][0][1],ServerJuego.superEnemigo.get(j).y+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).pieDer)][1][1],11);
                        if(map[(ServerJuego.superEnemigo.get(j).y+Tile.TILE_HEIGHT/2+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).brazoDer)][1][1])/Tile.TILE_HEIGHT][(ServerJuego.superEnemigo.get(j).x+Tile.TILE_WIDTH/2+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).brazoDer)][0][1])/Tile.TILE_WIDTH]==25)
                            elementos+=TransformaTile(ServerJuego.superEnemigo.get(j).x+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).brazoDer)][0][1],ServerJuego.superEnemigo.get(j).y+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).brazoDer)][1][1],24);
                        else elementos+=TransformaTile(ServerJuego.superEnemigo.get(j).x+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).brazoDer)][0][1],ServerJuego.superEnemigo.get(j).y+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).brazoDer)][1][1],11);
                        if(map[(ServerJuego.superEnemigo.get(j).y+Tile.TILE_HEIGHT/2+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).cintura)][1][1])/Tile.TILE_HEIGHT][(ServerJuego.superEnemigo.get(j).x+Tile.TILE_WIDTH/2+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).cintura)][0][1])/Tile.TILE_WIDTH]==25)
                            elementos+=TransformaTile(ServerJuego.superEnemigo.get(j).x+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).cintura)][0][1],ServerJuego.superEnemigo.get(j).y+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).cintura)][1][1],24);
                        else elementos+=TransformaTile(ServerJuego.superEnemigo.get(j).x+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).cintura)][0][1],ServerJuego.superEnemigo.get(j).y+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).cintura)][1][1],11);
                        if(map[(ServerJuego.superEnemigo.get(j).y+Tile.TILE_HEIGHT/2+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).torso)][1][1])/Tile.TILE_HEIGHT][(ServerJuego.superEnemigo.get(j).x+Tile.TILE_WIDTH/2+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).torso)][0][1])/Tile.TILE_WIDTH]==25)
                            elementos+=TransformaTile(ServerJuego.superEnemigo.get(j).x+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).torso)][0][1],ServerJuego.superEnemigo.get(j).y+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).torso)][1][1],24);
                        else elementos+=TransformaTile(ServerJuego.superEnemigo.get(j).x+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).torso)][0][1],ServerJuego.superEnemigo.get(j).y+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).torso)][1][1],11);
                        if(map[(ServerJuego.superEnemigo.get(j).y+Tile.TILE_HEIGHT/2+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).cabeza)][1][1])/Tile.TILE_HEIGHT][(ServerJuego.superEnemigo.get(j).x+Tile.TILE_WIDTH/2+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).cabeza)][0][1])/Tile.TILE_WIDTH]==25){
                            elementos+=TransformaTile(ServerJuego.superEnemigo.get(j).x+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).cabeza)][0][1],ServerJuego.superEnemigo.get(j).y+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).cabeza)][1][1],24);
                        }else{elementos+=TransformaTile(ServerJuego.superEnemigo.get(j).x+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).cabeza)][0][1],ServerJuego.superEnemigo.get(j).y+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).cabeza)][1][1],11);
                            if(map[(ServerJuego.superEnemigo.get(j).y+Tile.TILE_HEIGHT/2+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).cabeza)][1][1])/Tile.TILE_HEIGHT][(ServerJuego.superEnemigo.get(j).x+Tile.TILE_WIDTH/2+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).cabeza)][0][1])/Tile.TILE_WIDTH]==25){
                                if(ServerJuego.tetrisSkin==false && ServerJuego.superEnemigo.get(j).deadDimension==false) elementos+=TransformaTile(ServerJuego.superEnemigo.get(j).x+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).cabeza)][0][1],ServerJuego.superEnemigo.get(j).y+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).cabeza)][1][1],54);
                            }else{
                                if(ServerJuego.tetrisSkin==false && ServerJuego.superEnemigo.get(j).deadDimension==false) elementos+=TransformaTile(ServerJuego.superEnemigo.get(j).x+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).cabeza)][0][1],ServerJuego.superEnemigo.get(j).y+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).cabeza)][1][1],54);
                            }
                        }
                        if(map[(ServerJuego.superEnemigo.get(j).y+Tile.TILE_HEIGHT/2+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).pieIzq)][1][1])/Tile.TILE_HEIGHT][(ServerJuego.superEnemigo.get(j).x+Tile.TILE_WIDTH/2+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).pieIzq)][0][1])/Tile.TILE_WIDTH]==25)
                            elementos+=TransformaTile(ServerJuego.superEnemigo.get(j).x+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).pieIzq)][0][1],ServerJuego.superEnemigo.get(j).y+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).pieIzq)][1][1],24);
                        else elementos+=TransformaTile(ServerJuego.superEnemigo.get(j).x+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).pieIzq)][0][1],ServerJuego.superEnemigo.get(j).y+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).pieIzq)][1][1],11);
                        if(map[(ServerJuego.superEnemigo.get(j).y+Tile.TILE_HEIGHT/2+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).brazoIzq)][1][1])/Tile.TILE_HEIGHT][(ServerJuego.superEnemigo.get(j).x+Tile.TILE_WIDTH/2+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).brazoIzq)][0][1])/Tile.TILE_WIDTH]==25) 
                            elementos+=TransformaTile(ServerJuego.superEnemigo.get(j).x+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).brazoIzq)][0][1],ServerJuego.superEnemigo.get(j).y+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).brazoIzq)][1][1],24);
                        else elementos+=TransformaTile(ServerJuego.superEnemigo.get(j).x+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).brazoIzq)][0][1],ServerJuego.superEnemigo.get(j).y+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).brazoIzq)][1][1],11);
                    }
                }
            }else{
                if(ServerJuego.superEnemigo.get(j).alive){
                    if(ServerJuego.superEnemigo.get(j).direccion==1){
                        elementos+=TransformaTile(ServerJuego.superEnemigo.get(j).x+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).pieIzq)][0][1],ServerJuego.superEnemigo.get(j).y+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).pieIzq)][1][1],11);
                        elementos+=TransformaTile(ServerJuego.superEnemigo.get(j).x+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).brazoIzq)][0][1],ServerJuego.superEnemigo.get(j).y+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).brazoIzq)][1][1],11);
                        elementos+=TransformaTile(ServerJuego.superEnemigo.get(j).x+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).cintura)][0][1],ServerJuego.superEnemigo.get(j).y+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).cintura)][1][1],11);
                        elementos+=TransformaTile(ServerJuego.superEnemigo.get(j).x+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).torso)][0][1],ServerJuego.superEnemigo.get(j).y+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).torso)][1][1],11);
                        elementos+=TransformaTile(ServerJuego.superEnemigo.get(j).x+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).cabeza)][0][1],ServerJuego.superEnemigo.get(j).y+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).cabeza)][1][1],11);
                        if(ServerJuego.tetrisSkin==false) elementos+=TransformaTile(ServerJuego.superEnemigo.get(j).x+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).cabeza)][0][1],ServerJuego.superEnemigo.get(j).y+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).cabeza)][1][1],54);
                        elementos+=TransformaTile(ServerJuego.superEnemigo.get(j).x+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).pieDer)][0][1],ServerJuego.superEnemigo.get(j).y+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).pieDer)][1][1],11);
                        elementos+=TransformaTile(ServerJuego.superEnemigo.get(j).x+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).brazoDer)][0][1],ServerJuego.superEnemigo.get(j).y+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).brazoDer)][1][1],11);
                    }else{
                        elementos+=TransformaTile(ServerJuego.superEnemigo.get(j).x+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).pieDer)][0][1],ServerJuego.superEnemigo.get(j).y+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).pieDer)][1][1],11);
                        elementos+=TransformaTile(ServerJuego.superEnemigo.get(j).x+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).brazoDer)][0][1],ServerJuego.superEnemigo.get(j).y+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).brazoDer)][1][1],11);
                        elementos+=TransformaTile(ServerJuego.superEnemigo.get(j).x+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).cintura)][0][1],ServerJuego.superEnemigo.get(j).y+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).cintura)][1][1],11);
                        elementos+=TransformaTile(ServerJuego.superEnemigo.get(j).x+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).torso)][0][1],ServerJuego.superEnemigo.get(j).y+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).torso)][1][1],11);
                        elementos+=TransformaTile(ServerJuego.superEnemigo.get(j).x+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).cabeza)][0][1],ServerJuego.superEnemigo.get(j).y+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).cabeza)][1][1],11);
                        if(ServerJuego.tetrisSkin==false) elementos+=TransformaTile(ServerJuego.superEnemigo.get(j).x+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).cabeza)][0][1],ServerJuego.superEnemigo.get(j).y+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).cabeza)][1][1],54);
                        elementos+=TransformaTile(ServerJuego.superEnemigo.get(j).x+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).pieIzq)][0][1],ServerJuego.superEnemigo.get(j).y+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).pieIzq)][1][1],11);
                        elementos+=TransformaTile(ServerJuego.superEnemigo.get(j).x+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).brazoIzq)][0][1],ServerJuego.superEnemigo.get(j).y+ServerJuego.superEnemigo.get(j).positionParts[Integer.parseInt(ServerJuego.superEnemigo.get(j).brazoIzq)][1][1],11);
                    }
                }
            }
        }
        try{
            for(int j=0;j<ServerJuego.weaponAxe.size();j++){
                //System.out.println("i="+i+"j="+j);
                if(!ServerJuego.weaponAxe.get(j).deadDimension){//(ServerJuego.camara && ServerJuego.superJugador.get(i).deadDimension==ServerJuego.weaponAxe.get(j).deadDimension) || (!ServerJuego.camara && !ServerJuego.weaponAxe.get(j).deadDimension)
                    for(int c=0;c<(ServerJuego.weaponAxe.get(j).positionParts.length-1);c++){
                        if(ServerJuego.weaponAxe.get(j).visibleParts[c+1]==true){
                            if(ServerJuego.camara==true){
                                //si esta en area tetris se dibuja estilo tetris:
                                if(map[(ServerJuego.weaponAxe.get(j).y+Tile.TILE_HEIGHT/2+ServerJuego.weaponAxe.get(j).positionParts[c+1][1][1])/Tile.TILE_HEIGHT][(ServerJuego.weaponAxe.get(j).x+Tile.TILE_WIDTH/2+ServerJuego.weaponAxe.get(j).positionParts[c+1][0][1])/Tile.TILE_WIDTH]==25){
                                    elementos+=TransformaTile(ServerJuego.weaponAxe.get(j).x+ServerJuego.weaponAxe.get(j).positionParts[c+1][0][1],ServerJuego.weaponAxe.get(j).y+ServerJuego.weaponAxe.get(j).positionParts[c+1][1][1],24);
                                }else{
                                    elementos+=TransformaTile(ServerJuego.weaponAxe.get(j).x+ServerJuego.weaponAxe.get(j).positionParts[c+1][0][1],ServerJuego.weaponAxe.get(j).y+ServerJuego.weaponAxe.get(j).positionParts[c+1][1][1],(ServerJuego.weaponAxe.get(j).tipo==1?ServerJuego.superJugador.get(ServerJuego.weaponAxe.get(j).jugador).jugador+50:49));
                                }
                            }else{
                                elementos+=TransformaTile(ServerJuego.weaponAxe.get(j).x+ServerJuego.weaponAxe.get(j).positionParts[c+1][0][1],ServerJuego.weaponAxe.get(j).y+ServerJuego.weaponAxe.get(j).positionParts[c+1][1][1],(ServerJuego.weaponAxe.get(j).tipo==1?ServerJuego.superJugador.get(ServerJuego.weaponAxe.get(j).jugador).jugador+50:49));//,ServerJuego.weaponAxe.get(j).jugador
                            }
                        }
                    }
                }
            }
        }catch(Exception e){
            System.out.println("Error al transformar a texto un hacha, este problema es de tiempos de ejecucion de threads, es irrelevante.");
        }
        try{
            for(int j=0;j<ServerJuego.weaponBullet.size();j++){
                if(!ServerJuego.weaponBullet.get(j).deadDimension){//(ServerJuego.camara && ServerJuego.superJugador.get(i).deadDimension==ServerJuego.weaponBullet.get(j).deadDimension) || (!ServerJuego.camara && !ServerJuego.weaponBullet.get(j).deadDimension)
                    if(ServerJuego.camara==true){
                        //si esta en area tetris se dibuja estilo tetris:
                        if(map[(Tile.TILE_HEIGHT/2+ServerJuego.weaponBullet.get(j).y)/Tile.TILE_HEIGHT][(Tile.TILE_WIDTH/2+ServerJuego.weaponBullet.get(j).x)/Tile.TILE_WIDTH]==25){
                            elementos+=TransformaTile(ServerJuego.weaponBullet.get(j).x,ServerJuego.weaponBullet.get(j).y,24);
                        }else{
                            elementos+=TransformaTile(ServerJuego.weaponBullet.get(j).x,ServerJuego.weaponBullet.get(j).y,(ServerJuego.superJugador.get(ServerJuego.weaponBullet.get(j).jugador).megaman==true?77:48));
                        }
                    }else{
                        elementos+=TransformaTile(ServerJuego.weaponBullet.get(j).x,ServerJuego.weaponBullet.get(j).y,(ServerJuego.superJugador.get(ServerJuego.weaponBullet.get(j).jugador).megaman==true?77:48));//,ServerJuego.weaponBullet.get(j).jugador
                    }
                }
            }   
        }catch(Exception e){
            System.out.println("Error al transformar a texto una bala, este problema es de tiempos de ejecucion de threads, es irrelevante.");
        }
        try{
            for(int j=0;j<ServerJuego.weaponBall.size();j++){
                if(ServerJuego.camara==true){
                    elementos+=TransformaTile(ServerJuego.weaponBall.get(j).x,ServerJuego.weaponBall.get(j).y,26);
                }else{
                    elementos+=TransformaTile(ServerJuego.weaponBall.get(j).x,ServerJuego.weaponBall.get(j).y,26);
                }
            }
        }catch(Exception e){
            System.out.println("Error al transformar a texto una pelota, este problema es de tiempos de ejecucion de threads, es irrelevante.");
        }
        try{
            for(int j=0;j<items.size();j++){
                if(ServerJuego.camara==true){
                    if(true){//ServerJuego.superJugador.get(i).deadDimension==false
                        elementos+=TransformaTile(items.get(j).posicionItemX,items.get(j).posicionItemY,items.get(j).tipoItem);
                    }
                }else{
                    elementos+=TransformaTile(items.get(j).posicionItemX,items.get(j).posicionItemY,items.get(j).tipoItem);
                }
            }
        }catch(Exception e){
            System.out.println("Error al transformar a texto un item, este problema es de tiempos de ejecucion de threads, es irrelevante.");
        }
        //System.out.println(elementos);
        for(int j=0;j<punto.size();j++){
            if(ServerJuego.camara==true){
                if(true){//ServerJuego.superJugador.get(i).deadDimension==false
                    elementos+=TransformaTile(punto.get(j).posicionPuntoX,punto.get(j).posicionPuntoY,34);
                }
            }else{
                elementos+=TransformaTile(punto.get(j).posicionPuntoX,punto.get(j).posicionPuntoY,34);
            }
        }
        //System.out.println(elementos);
        return FiltrarElementos(elementos,idCliente)+"e";
    }

    public static String FiltrarElementos(String texto,int idCliente){
        ArrayList<int[]> camaras=new ArrayList<>();
        for(int j=0;j<ServerJuego.superJugador.size();j++){
            if(ServerJuego.superJugador.get(j).idCliente==idCliente){
                int[] cam=new int[4];
                cam[0]=(ServerJuego.superJugador.get(j).posCamX);
                cam[1]=(ServerJuego.superJugador.get(j).posCamY);
                cam[2]=(ServerJuego.superJugador.get(j).anchoCamara);
                cam[3]=(ServerJuego.superJugador.get(j).altoCamara);
                camaras.add(cam);
            }
        }
        String[] elementos=texto.split(";");
        for(int i=0;i<elementos.length;i++){
            String[] elemento=elementos[i].split(",");
            boolean esta=false;
            for(int c=0;c<camaras.size();c++){
                if(((Integer.parseInt(elemento[0])+Tile.TILE_WIDTH>(camaras.get(c)[0]) && (Integer.parseInt(elemento[0])<(camaras.get(c)[0]+camaras.get(c)[2]*Tile.TILE_WIDTH))) && (Integer.parseInt(elemento[1])+Tile.TILE_HEIGHT>(camaras.get(c)[1]) && (Integer.parseInt(elemento[1])<(camaras.get(c)[1]+camaras.get(c)[3]*Tile.TILE_HEIGHT))))){
                    esta=true;
                }
            }
            if(elemento.length!=3){
                texto=texto.replace(";"+elementos[i]+";",";");
            }else{
                if(!esta){//en caso de no estar en ninguna de las pantallas del cliente, se eimina el tile.
                    //System.out.println((elemento[0]+","+elemento[1]+","+elemento[2]+";"));
                    texto=texto.replace((";"+elemento[0]+","+elemento[1]+","+elemento[2]+";"),";");
                }
            }
        }
        return texto;
    }
    
    public static String TransformaCadena(int direccion, int tipo, int eslabones, int x1, int y1, int x2, int y2){
        String texto="";
        if(tipo==1){//pierna derecha:
            if(direccion==2){
                x1+=Tile.TILE_WIDTH/2;
            }else{

            }
            x2+=Tile.TILE_WIDTH/4;
        }
        if(tipo==2){//pierna izquierda:
            if(direccion==1){
                x1+=Tile.TILE_WIDTH/2;
            }else{

            }
            x2+=Tile.TILE_WIDTH/4;
        }
        if(tipo==3){//brazo derecho:
            if(direccion==2){
                x1+=Tile.TILE_WIDTH/2+Tile.TILE_WIDTH/4;
            }else{
                x1-=Tile.TILE_WIDTH/4;
                x2+=Tile.TILE_WIDTH/2;
            }
            y2+=Tile.TILE_WIDTH/2;
        }
        if(tipo==4){//brazo izquierdo:
            if(direccion==1){
                x1+=Tile.TILE_WIDTH/2+Tile.TILE_WIDTH/4;
                x2+=Tile.TILE_WIDTH/2;
            }else{
                x1-=Tile.TILE_WIDTH/4;
            }
            y2+=Tile.TILE_WIDTH/2;
        }
        for(int a=0;a<eslabones;a++){
            int x=x1+a*((x2-x1)/eslabones);
            int y=y1+a*((y2-y1)/eslabones);
            //System.out.println("x="+x+"|y="+y);
            texto+=x+","+y+","+71+";";
        }
        return texto;
    }
    
    public static String TransformaTile(int x, int y, int index){
        String texto="";
        texto+=x+","+y+","+index+";";
        return texto;
    }
    
    public static void RecibirControles(String texto,int idCliente){
        String[] jugadores=texto.split("j");
        int jugadorActual=0;
        for(int j=0;j<jugadores.length;j++){
            int contadorJugadores=0;
            for(int i=0;i<ServerJuego.superJugador.size();i++){
                if(ServerJuego.superJugador.get(i).idCliente==idCliente){
                    contadorJugadores++;
                    if(jugadorActual<contadorJugadores){
                        String[] jugador=jugadores[j].split(",");
                        ServerJuego.superJugador.get(i).disparo=(jugador[0].equals("1")?true:false);
                        if(!ServerJuego.superJugador.get(i).disparo) ServerJuego.superJugador.get(i).disparado=false;
                        ServerJuego.superJugador.get(i).espacio=(jugador[1].equals("1")?true:false);
                        ServerJuego.superJugador.get(i).izquierda=(jugador[2].equals("1")?true:false);
                        ServerJuego.superJugador.get(i).derecha=(jugador[3].equals("1")?true:false);
                        ServerJuego.superJugador.get(i).arriba=(jugador[4].equals("1")?true:false);
                        ServerJuego.superJugador.get(i).agacharse=(jugador[5].equals("1")?true:false);
                        ServerJuego.superJugador.get(i).corriendo=(jugador[6].equals("1")?true:false);
                        ServerJuego.superJugador.get(i).cambiar=(jugador[7].equals("1")?true:false);
                        if(!ServerJuego.superJugador.get(i).cambiar) ServerJuego.superJugador.get(i).cambiado=false;
                        ServerJuego.superJugador.get(i).megaman=(jugador[8].equals("1")?true:false);
                        jugadorActual++;
                        break;
                    }
                }
            }
            
        }
    }
    
}