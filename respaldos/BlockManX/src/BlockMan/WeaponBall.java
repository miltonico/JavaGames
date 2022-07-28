/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package BlockMan;

/**
 *
 * @author TIMBERWOLF
 */
public class WeaponBall {
    
    public int x,y,inicioX,inicioY,dx,dy,limite,jugador,subX,subY,seccionador,health=128;
    public boolean dirigida,impulso,broken;//dirigida: sigue al jugador,impulso: el jugador la potencia con solo tocarla.
    
    public WeaponBall(int x, int y, int jugador){
        this.inicioX=x+(Juego.superJugador.get(jugador).direccion==1?-1:1);
        this.inicioY=y;
        this.seccionador=(4/(Tile.TILE_WIDTH/4));//min 1 cuando es 16 el tamaño del tile, max 4 cuando es 4 el tamaño del tile.;
        this.seccionador*=4;//multiplicado por 4...
        this.x=x+(Juego.superJugador.get(jugador).direccion==1?-1:1);
        this.y=y;
        this.subX=x*seccionador+(Juego.superJugador.get(jugador).direccion==1?-1:1)*this.seccionador;
        this.subY=y*seccionador;
        this.dx=(Juego.superJugador.get(jugador).x-Juego.superJugador.get(jugador).x_anterior)*Tile.TILE_WIDTH;
        if(this.dx==0){
            this.dx=(Juego.superJugador.get(jugador).direccion==1?-1:1)*this.seccionador;
        }
        this.dy=0;
        this.limite=seccionador*Tile.TILE_WIDTH/2;
        this.jugador=jugador;//0 a 3.
        this.dirigida=false;
        this.impulso=false;
        this.broken=false;
    }
    
    public void updateBall(){
        if(this.x+Tile.TILE_WIDTH>Juego.ancho*Tile.TILE_WIDTH){
            this.x=Juego.ancho*Tile.TILE_WIDTH-Tile.TILE_WIDTH;
            this.subX=(Juego.ancho*Tile.TILE_WIDTH-Tile.TILE_WIDTH)*seccionador;
            this.dx=-dx;
        }
        if(this.y+Tile.TILE_HEIGHT>Juego.alto*Tile.TILE_HEIGHT){
            this.y=Juego.alto*Tile.TILE_HEIGHT-Tile.TILE_HEIGHT;
            this.subY=(Juego.alto*Tile.TILE_HEIGHT-Tile.TILE_HEIGHT)*seccionador;
            this.dy=-dy;
        }
        if(this.x<0){
            this.x=0;
            this.subX=0;
            this.dx=-dx;
        }
        if(this.y<0){
            this.y=0;
            this.subY=0;
            this.dy=-dy;
        }
        
        for(int i=0;i<Juego.superJugador.size();i++){
            for(int j=1;j<Juego.superJugador.get(i).positionParts.length;j++){
                if((Juego.superJugador.get(i).x+Juego.superJugador.get(i).positionParts[j][0][1])<(this.x+Tile.TILE_WIDTH)
                    && (Juego.superJugador.get(i).x+Juego.superJugador.get(i).positionParts[j][0][1]+ Tile.TILE_WIDTH)>(this.x)
                    && (Juego.superJugador.get(i).y+Juego.superJugador.get(i).positionParts[j][1][1])<(this.y+Tile.TILE_HEIGHT)
                    && (Juego.superJugador.get(i).y+Juego.superJugador.get(i).positionParts[j][1][1]+ Tile.TILE_HEIGHT)>(this.y)){
                    if(this.jugador!=i){
                        int damage=(Math.abs(this.dx)+Math.abs(this.dy))*4/this.seccionador;
                        this.health-=damage;
                        if(this.health<=0){
                            damage+=health;//si se pasa se le quita.
                            this.broken=true;
                            Juego.superJugador.get(i).changeHealth(-damage);
                            return;
                        }else{
                            Juego.superJugador.get(i).changeHealth(-damage);
                        }
                    }
                    int dx_jugador=(Juego.superJugador.get(i).x-Juego.superJugador.get(i).x_anterior)*seccionador*Tile.TILE_WIDTH/4;
                    int m=1;
                    if(dx_jugador<0){
                        m=-1;
                    }
                    if(dx_jugador!=0) dx_jugador=m*((int)(Math.random()*dx_jugador+1));
                    //this.dx=-this.dx;
                    if((this.x)<(Juego.superJugador.get(i).x+Juego.superJugador.get(i).positionParts[Integer.parseInt(Juego.superJugador.get(i).cabeza)][0][1])){
                        this.dx=-(Math.abs(this.dx)+dx_jugador);
                    }else{
                        this.dx=(Math.abs(this.dx)+dx_jugador);
                    }
                    //this.dx+=dx_jugador;
                    this.dy=-(Math.abs(this.dy)+(dx_jugador!=0?this.seccionador:0));
                    if(this.dx>this.limite) this.dx=this.limite;
                    if(this.dy>this.limite) this.dy=this.limite;
                    if(this.dx<(-this.limite)) this.dx=(-this.limite);
                    if(this.dy<(-this.limite)) this.dy=(-this.limite);
                    break;//para que solo dañe una vez al jugador.
                }
            }
        }
        for(int i=0;i<Juego.superEnemigo.size();i++){
            for(int j=1;j<Juego.superEnemigo.get(i).positionParts.length;j++){
                if(!Juego.superEnemigo.get(i).deadDimension){
                    if((Juego.superEnemigo.get(i).x+Juego.superEnemigo.get(i).positionParts[j][0][1])<(this.x+Tile.TILE_WIDTH)
                        && (Juego.superEnemigo.get(i).x+Juego.superEnemigo.get(i).positionParts[j][0][1]+ Tile.TILE_WIDTH)>(this.x)
                        && (Juego.superEnemigo.get(i).y+Juego.superEnemigo.get(i).positionParts[j][1][1])<(this.y+Tile.TILE_HEIGHT)
                        && (Juego.superEnemigo.get(i).y+Juego.superEnemigo.get(i).positionParts[j][1][1]+ Tile.TILE_HEIGHT)>(this.y)){
                        
                        int damage=(Math.abs(this.dx)+Math.abs(this.dy))*4/this.seccionador;
                        this.health-=damage;
                        if(this.health<=0){
                            damage+=health;//si se pasa se le quita.
                            this.broken=true;
                            Juego.superEnemigo.get(i).changeHealth(-damage);
                            return;
                        }else{
                            Juego.superEnemigo.get(i).changeHealth(-damage);
                        }

                        int dx_jugador=(Juego.superEnemigo.get(i).x-Juego.superEnemigo.get(i).x_anterior)*seccionador*Tile.TILE_WIDTH/4;
                        int m=1;
                        if(dx_jugador<0){
                            m=-1;
                        }
                        if(dx_jugador!=0) dx_jugador=m*((int)(Math.random()*dx_jugador+1));
                        //this.dx=-this.dx;
                        if((Juego.superEnemigo.get(i).x+Juego.superEnemigo.get(i).positionParts[Integer.parseInt(Juego.superEnemigo.get(i).cabeza)][0][1])>=(this.x)){
                            this.dx=-(Math.abs(this.dx)-dx_jugador);
                        }else{
                            this.dx=(Math.abs(this.dx)+dx_jugador);
                        }
                        //this.dx+=dx_jugador;
                        this.dy=-(Math.abs(this.dy)+(dx_jugador!=0?this.seccionador:0));
                        if(this.dx>this.limite) this.dx=this.limite;
                        if(this.dy>this.limite) this.dy=this.limite;
                        if(this.dx<(-this.limite)) this.dx=(-this.limite);
                        if(this.dy<(-this.limite)) this.dy=(-this.limite);
                        
                        break;//para que solo dañe una vez al enemigo.
                    }
                }
            }
        }
        
        
        //posicion aproximada.
        int quad_x=(int)Math.floor((this.x+(Tile.TILE_WIDTH/2))/Tile.TILE_WIDTH);
        int quad_y=(int)Math.floor((this.y+(Tile.TILE_HEIGHT/2))/Tile.TILE_HEIGHT);
        
        //System.out.println("qx="+(((double)this.x+((double)Tile.TILE_WIDTH/(double)2))/(double)Tile.TILE_WIDTH-(double)1));
        //System.out.println("qy="+(((double)this.y+((double)Tile.TILE_HEIGHT/(double)2))/(double)Tile.TILE_HEIGHT-(double)1));
        
        int direccion_x=0;
        int direccion_y=0;
        
        //colisiones.
        int[][] espacios=new int[3][3];//y,x
        
        espacios[0][0]=(quad_x-1>0 && quad_y-1>0)?MapaTiles.mapaFisico[quad_y-1][quad_x-1]:1;
        espacios[0][1]=((quad_y-1)>0)?MapaTiles.mapaFisico[quad_y-1][quad_x]:1;
        espacios[0][2]=(quad_x+1<Juego.ancho && quad_y-1>0)?MapaTiles.mapaFisico[quad_y-1][quad_x+1]:1;
        espacios[1][0]=(quad_x-1>0)?MapaTiles.mapaFisico[quad_y][quad_x-1]:1;
        espacios[1][1]=MapaTiles.mapaFisico[quad_y][quad_x];
        espacios[1][2]=(quad_x+1<Juego.ancho)?MapaTiles.mapaFisico[quad_y][quad_x+1]:1;
        espacios[2][0]=(quad_x-1>0 && quad_y+1<Juego.alto)?MapaTiles.mapaFisico[quad_y+1][quad_x-1]:1;
        espacios[2][1]=(quad_y+1<Juego.alto)?MapaTiles.mapaFisico[quad_y+1][quad_x]:1;
        espacios[2][2]=(quad_x+1<Juego.ancho && quad_y+1<Juego.alto)?MapaTiles.mapaFisico[quad_y+1][quad_x+1]:1;
        int jugador_centro=0;//MapaTiles.jugadoresDibujados[quad_y][quad_x];//es el neo de jugador que esta bloqueando la pelota, si es 0 es por que no hay.
        
        /*
        if(((quad_x-1>0 && quad_y-1>0)?MapaTiles.jugadores[quad_y-1][quad_x-1]:0)!=0)espacios[0][0]=2;
        if(((quad_y-1>0)?MapaTiles.jugadores[quad_y-1][quad_x]:0)!=0)espacios[0][1]=2;
        if(((quad_x+1<Juego.ancho && quad_y-1>0)?MapaTiles.jugadores[quad_y-1][quad_x+1]:0)!=0)espacios[0][2]=2;
        if(((quad_x-1>0)?MapaTiles.jugadores[quad_y][quad_x-1]:0)!=0)espacios[1][0]=2;
        if((MapaTiles.jugadores[quad_y][quad_x])!=0)espacios[1][1]=2;
        if(((quad_x+1<Juego.ancho)?MapaTiles.jugadores[quad_y][quad_x+1]:0)!=0)espacios[1][2]=2;
        if(((quad_x-1>0 && quad_y+1<Juego.alto)?MapaTiles.jugadores[quad_y+1][quad_x-1]:0)!=0)espacios[2][0]=2;
        if(((quad_y+1<Juego.alto)?MapaTiles.jugadores[quad_y+1][quad_x]:0)!=0)espacios[2][1]=2;
        if(((quad_x+1<Juego.ancho && quad_y+1<Juego.alto)?MapaTiles.jugadores[quad_y+1][quad_x+1]:0)!=0)espacios[2][2]=2;
        /*
        
        */
        /*
        System.out.println("["+espacios[0][0]+","+espacios[0][1]+","+espacios[0][2]+"]");
        System.out.println("["+espacios[1][0]+","+espacios[1][1]+","+espacios[1][2]+"]");
        System.out.println("["+espacios[2][0]+","+espacios[2][1]+","+espacios[2][2]+"]");
        System.out.println("");
        */
        int pos_quad_x=(this.x+(Tile.TILE_WIDTH)/2)%Tile.TILE_WIDTH;//de 0 a 15.
        int pos_quad_y=(this.y+(Tile.TILE_HEIGHT)/2)%Tile.TILE_HEIGHT;//de 0 a 15.
        
        int espacio1=0;
        int espacio2=0;
        int espacio3=0;
        
        int invertir=0;//0=nada,1=x,2=y,3=x e y.
        
        int suma_x=0;
        int suma_y=0;
        
        //diferencia q se produce en el eje y al ajustar la pelota a la superficie del bloque.
        int diferencia=0;
        int diferencia_x=0;
        
        boolean en_el_piso=false;
        
        if(pos_quad_x<(Tile.TILE_WIDTH/2)){
            if(pos_quad_y==(Tile.TILE_HEIGHT/2)){
                if(espacios[1][0]==1 || espacios[1][0]==1){
                    //System.out.println("holi");
                    direccion_x=2;
                    direccion_y=0;
                    /*
                    if((espacios[1][0]==2 && this.impulso==true) || this.dx==0){
                        suma_x=1;
                        if(this.impulso==true) suma_y=1;
                    }
                    */
                    en_el_piso=true;
                }
            }
            if(pos_quad_y<(Tile.TILE_HEIGHT/2)){
                espacio1=espacios[0][0];//esq
                espacio2=espacios[0][1];//der esq
                espacio3=espacios[1][0];//aba esq
                if((espacio1==1 || espacio1==1) && pos_quad_x!=(Tile.TILE_WIDTH/2)){
                    //la linea perpendicular a la diagonal formada por hacia donde apunta la esquina es lineaX.
                    //si la pelota incide por el frente de esta linea, rebota en el angulo correspondiente.
                    //si rebota por detras, no se calcula por que la pelota esta en el centro de los nueve bloques.
                    //por ahora se invierten ambos ejes.
                    /*
                    if((espacio1==2 && this.impulso==true) || this.dx==0){
                        suma_x=1;
                        if(this.impulso==true) suma_y=1;
                    }
                    */
                    invertir=3;
                    direccion_x=2;//derecha.
                    direccion_y=2;//abajo.
                }
                if(espacio2==1 || espacio2==1){
                    invertir=2;
                    direccion_x=0;//se mantiene.
                    direccion_y=2;//abajo.
                    if((espacio2==2 && this.impulso==true)){
                        suma_y=1;
                    }
                }
                if((espacio3==1 || espacio3==1) && pos_quad_x!=(Tile.TILE_WIDTH/2)){
                    invertir=1;
                    direccion_x=2;//derecha.
                    direccion_y=0;//se mantiene.
                    /*
                    if((espacio3==2 && this.impulso==true) || this.dx==0){
                        suma_x=1;
                        if(this.impulso==true) suma_y=1;//siempre salta
                    }
                    */
                }
                if((espacio2==1 || espacio2==1) && (espacio3==1 || espacio3==1) && pos_quad_x!=(Tile.TILE_WIDTH/2)){
                    invertir=3;
                    direccion_x=2;//derecha.
                    direccion_y=2;//abajo.
                    /*
                    if((espacio2==2 && this.impulso==true) || this.dx==0){
                        suma_y=1;
                    }
                    if((espacio3==2 && this.impulso==true) || this.dx==0){
                        suma_x=1;
                        suma_y=1;//siempre salta
                    }
                    */
                }
                //diferencia_x=this.x-(quad_x*Tile.TILE_WIDTH);
                //this.x=quad_x*Tile.TILE_WIDTH;//alinear la pelota en el eje x.
            }
            if(pos_quad_y>(Tile.TILE_HEIGHT/2)){
                espacio1=espacios[2][0];//esq
                espacio2=espacios[1][0];//arr esq
                espacio3=espacios[2][1];//der esq
                if((espacio1==1 || espacio1==1) && pos_quad_x!=(Tile.TILE_WIDTH/2)){
                    //la linea perpendicular a la diagonal formada por hacia donde apunta la esquina es lineaX.
                    //si la pelota incide por el frente de esta linea, rebota en el angulo correspondiente.
                    //si rebota por detras, no se calcula por que la perlota esta en el centro de los nueve bloques.
                    //por ahora se invierten ambos ejes.
                    invertir=3;
                    direccion_x=2;//derecha.
                    direccion_y=1;//arriba.
                    /*
                    if((espacio1==2 && this.impulso==true) || this.dx==0){
                        suma_x=1;
                        if(this.impulso==true) suma_y=1;
                    }
                    */
                }
                if((espacio2==1 || espacio2==1) && pos_quad_x!=(Tile.TILE_WIDTH/2)){
                    //System.out.println("hola");
                    invertir=1;
                    direccion_x=2;//derecha.
                    direccion_y=0;//se mantiene.
                    /*
                    if((espacio2==2 && this.impulso==true) || this.dx==0){
                        suma_x=1;
                        if(this.impulso==true) suma_y=1;//siempre salta
                    }
                    */
                }
                if(espacio3==1 || espacio3==1){
                    invertir=2;
                    direccion_x=0;//se mantiene.
                    direccion_y=1;//arriba.
                    /*
                    if((espacio3==2 && this.impulso==true)){
                        suma_y=1;
                    }else{
                        if(this.dx!=0) suma_x=-1;
                        //if(this.dy!=0) suma_y=-1;
                    }
                    */
                    diferencia=this.y-(quad_y*Tile.TILE_HEIGHT);
                    //this.subY=quad_y*Tile.TILE_HEIGHT*this.seccionador;//poner la pelota sobre el cuadrado que esta traspasando.
                    en_el_piso=true;
                    /*
                    if(Personaje.estaVivo[this.jugador]==true && this.dirigida==true){
                        if((Personaje.y[this.jugador]+1)*Tile.TILE_HEIGHT<this.y){
                            //direccion_y=2;
                        }else{
                            direccion_y=1;
                            suma_y=1;
                        }
                        
                    }
                    */
                }
                if((espacio2==1 || espacio2==1) && pos_quad_x!=(Tile.TILE_WIDTH/2) && (espacio3==1 || espacio3==1)){
                    invertir=3;
                    direccion_x=2;//derecha.
                    direccion_y=1;//arriba.
                    /*
                    if(espacio2==2 && this.impulso==true){
                        suma_y=1;
                    }
                    if((espacio3==2 && this.impulso==true) || this.dx==0){
                        suma_x=1;
                        if(this.impulso==true) suma_y=1;//siempre salta
                    }
                    */
                }
                //diferencia_x=this.x-(quad_x*Tile.TILE_WIDTH);
                //this.x=quad_x*Tile.TILE_WIDTH;//alinear la pelota en el eje x.
            }
        }
        if(pos_quad_x>(Tile.TILE_WIDTH/2)){
            if(pos_quad_y==(Tile.TILE_HEIGHT/2)){
                if(espacios[1][2]==1 || espacios[1][2]==1){
                    direccion_x=1;
                    direccion_y=0;
                    /*
                    if((espacios[1][2]==2 && this.impulso==true) || this.dx==0){
                        suma_x=1;
                        if(this.impulso==true) suma_y=1;
                    }
                    */
                    en_el_piso=true;
                }
            }
            if(pos_quad_y<(Tile.TILE_HEIGHT/2)){
                espacio1=espacios[0][2];//esq
                espacio2=espacios[0][1];//izq esq
                espacio3=espacios[1][2];//aba esq
                if((espacio1==1 || espacio1==1) && pos_quad_x!=(Tile.TILE_WIDTH/2)){
                    //la linea perpendicular a la diagonal formada por hacia donde apunta la esquina es lineaX.
                    //si la pelota incide por el frente de esta linea, rebota en el angulo correspondiente.
                    //si rebota por detras, no se calcula por que la perlota esta en el centro de los nueve bloques.
                    //por ahora se invierten ambos ejes.
                    invertir=3;
                    direccion_x=1;//izquierda.
                    direccion_y=2;//abajo.
                    /*
                    if((espacio1==2 && this.impulso==true) || this.dx==0){
                        suma_x=1;
                        if(this.impulso==true) suma_y=1;
                    }
                    */
                }
                if(espacio2==1 || espacio2==1){
                    invertir=2;
                    direccion_x=0;//se mantiene.
                    direccion_y=2;//abajo.
                    /*
                    if(espacio2==2 && this.impulso==true){
                        suma_y=1;
                    }
                    */
                }
                if((espacio3==1 || espacio3==1) && pos_quad_x!=(Tile.TILE_WIDTH/2)){
                    invertir=1;
                    direccion_x=1;//izquierda.
                    direccion_y=0;//se mantiene.
                    /*
                    if((espacio3==2 && this.impulso==true) || this.dx==0){
                        suma_x=1;
                        if(this.impulso==true) suma_y=1;//siempre salta
                    }
                    */
                }
                if((espacio2==1 || espacio2==1) && (espacio3==1 || espacio3==1) && pos_quad_x!=(Tile.TILE_WIDTH/2)){
                    invertir=3;
                    direccion_x=1;//izquierda.
                    direccion_y=2;//abajo.
                    /*
                    if(espacio2==2 && this.impulso==true){
                        suma_y=1;
                    }
                    if((espacio3==2 && this.impulso==true) || this.dx==0){
                        suma_x=1;
                        if(this.impulso==true) suma_y=1;//siempre salta
                    }
                    */
                }
                //diferencia_x=this.x-(quad_x*Tile.TILE_WIDTH);
                //this.x=quad_x*Tile.TILE_WIDTH;//alinear la pelota en el eje x.
            }
            if(pos_quad_y>(Tile.TILE_HEIGHT/2)){
                espacio1=espacios[2][2];//esq
                espacio2=espacios[1][2];//arr esq
                espacio3=espacios[2][1];//izq esq
                if((espacio1==1 || espacio1==1) && pos_quad_x!=(Tile.TILE_WIDTH/2)){
                    //la linea perpendicular a la diagonal formada por hacia donde apunta la esquina es lineaX.
                    //si la pelota incide por el frente de esta linea, rebota en el angulo correspondiente.
                    //si rebota por detras, no se calcula por que la perlota esta en el centro de los nueve bloques.
                    //por ahora se invierten ambos ejes.
                    invertir=3;
                    direccion_x=1;//izquierda.
                    direccion_y=1;//arriba.
                    /*
                    System.out.println("["+espacios[0][0]+","+espacios[0][1]+","+espacios[0][2]+"]");
                    System.out.println("["+espacios[1][0]+","+espacios[1][1]+","+espacios[1][2]+"]");
                    System.out.println("["+espacios[2][0]+","+espacios[2][1]+","+espacios[2][2]+"]");
                    System.out.println("");
                    */
                    /*
                    if((espacio1==2 && this.impulso==true) || this.dx==0){
                        suma_x=1;
                        if(this.impulso==true) suma_y=1;
                    }
                    */
                }
                if((espacio2==1 || espacio2==1) && pos_quad_x!=(Tile.TILE_WIDTH/2)){
                    invertir=2;
                    direccion_x=1;//izquierda.
                    direccion_y=0;//se mantiene.
                    /*
                    if((espacio2==2 && this.impulso==true) || this.dx==0){
                        suma_x=1;
                        if(this.impulso==true) suma_y=1;//siempre salta
                    }
                    */
                }
                if(espacio3==1 || espacio3==1){
                    invertir=1;
                    direccion_x=0;//se mantiene.
                    direccion_y=1;//arriba.
                    /*
                    if(espacio3==2 && this.impulso==true){
                        suma_y=1;
                    }else{
                        if(this.dx!=0) suma_x=-1;
                        //if(this.dy!=0) suma_y=-1;
                    }
                    */
                    diferencia=this.y-(quad_y*Tile.TILE_HEIGHT);
                    //this.subY=quad_y*Tile.TILE_HEIGHT*this.seccionador;//poner la pelota sobre el cuadrado que esta traspasando.
                    en_el_piso=true;
                    /*
                    if(Personaje.estaVivo[this.jugador]==true && this.dirigida==true){
                        if((Personaje.y[this.jugador]+1)*Tile.TILE_HEIGHT<this.y){
                            //direccion_y=2;
                        }else{
                            direccion_y=1;
                            suma_y=1;
                        }
                        
                    }
                    */
                }
                if((espacio2==1 || espacio2==1) && pos_quad_x!=(Tile.TILE_WIDTH/2) && (espacio3==1 || espacio3==1)){
                    invertir=3;
                    direccion_x=1;//izquierda.
                    direccion_y=1;//arriba.
                    /*
                    if(espacio2==2 && this.impulso==true){
                        suma_y=1;
                    }
                    if((espacio3==2 && this.impulso==true) || this.dx==0){
                        suma_x=1;
                        if(this.impulso==true) suma_y=1;//siempre salta
                    }
                    */
                }
                //diferencia_x=this.x-(quad_x*Tile.TILE_WIDTH);
                //this.x=quad_x*Tile.TILE_WIDTH;//alinear la pelota en el eje x.
            }
        }
        
        if(pos_quad_x==(Tile.TILE_HEIGHT/2)){
            if(pos_quad_y==(Tile.TILE_HEIGHT/2)){
                //se mantiene:
                direccion_x=0;
                direccion_y=0;
                en_el_piso=true;
            }
            if(pos_quad_y<(Tile.TILE_HEIGHT/2)){
                if(espacios[0][1]==1 || espacios[0][1]==1){
                    direccion_x=0;
                    direccion_y=2;
                    /*
                    if((espacios[0][1]==2 && this.impulso==true) || this.dx==0){
                        suma_x=1;
                        if(this.impulso==true) suma_y=1;
                    }
                    */
                }
            }
            if(pos_quad_y>(Tile.TILE_HEIGHT/2)){
                if(espacios[2][1]==1 || espacios[2][1]==1){
                    direccion_x=0;
                    direccion_y=1;
                    /*
                    if((espacios[2][1]==2 && this.impulso==true) || this.dx==0){
                        suma_x=1;
                        if(this.impulso==true) suma_y=1;
                    }else{
                        if(this.dx!=0) suma_x=-1;
                        //if(this.dy!=0) suma_y=-1;
                    }
                    */
                    diferencia=this.y-(quad_y*Tile.TILE_HEIGHT);
                    //this.subY=quad_y*Tile.TILE_HEIGHT*this.seccionador;//poner la pelota sobre el cuadrado que esta traspasando.
                    en_el_piso=true;
                }
            }
        }
        
        //si es bloque normal y esta bloqueando a la pelota, esta sigue al jugador.
        
        if(espacios[1][1]==1){
            direccion_y=1;
            this.dy=limite;
            //System.out.println("ocupado 1");
            if(Personaje.estaVivo[this.jugador]==true && this.dirigida==true){
                
                if((Personaje.x[this.jugador]+2)*Tile.TILE_WIDTH>this.x){
                    direccion_x=2;
                }else{
                    direccion_x=1;
                }
                
                if((Personaje.y[this.jugador]+1)*Tile.TILE_HEIGHT>this.y){
                    direccion_y=2;
                }else{
                    direccion_y=1;
                }
                if(direccion_x==1) this.dx=-limite;
                if(direccion_x==2) this.dx=limite;
                if(direccion_y==1) this.dy=-limite;
                if(direccion_y==2) this.dy=limite;
                
            }else{
                direccion_y=1;
                this.dy=limite;
            }
        }
        
        /*
        if(Personaje.estaVivo[this.jugador]==true && this.dirigida==true && direccion_x!=0 && direccion_y!=0){
            System.out.println("desocupado 1");
            if((Personaje.x[this.jugador]+2)*Tile.TILE_WIDTH>this.x){
                direccion_x=2;
            }else{
                direccion_x=1;
            }
            suma_x=1;
        }else{
            System.out.println("desocupado 2");
        }
        */
        //si es un jugador y esta bloqueando a la pelota, esta va hacia arriba.
        /*
        if(espacios[1][1]==2){
            direccion_y=1;
            //System.out.println("ocupado 2");
            
            if(jugador_centro>=1 && jugador_centro<=4) direccion_x=Personaje.direccion[jugador_centro-1];
            this.dy=-limite;
            if(direccion_x==1) this.dx=-limite;
            if(direccion_x==2) this.dx=limite;
            
        }
        */
        if(direccion_x==1 && this.dx>0) this.dx=-this.dx;
        else if(direccion_x==2 && this.dx<0) this.dx=-this.dx;
        
        if(direccion_y==1 && this.dy>0) this.dy=-this.dy;
        else if(direccion_y==2 && this.dy<0) this.dy=-this.dy;
        
        //if(this.dy<0)
        //if(!((espacios[2][1]==1 || espacios[2][1]==2) && pos_quad_y>=(Tile.TILE_HEIGHT/2) && this.dy==0)){//gravedad
        if(!en_el_piso){
            this.dy+=2;
        }else{
            if(dx!=0) suma_x=-1;
            if(dy!=0) suma_y=-1;
        }
        
        
        if(this.dx>0 || direccion_x==2) this.dx+=suma_x;
        else if(this.dx<0 || direccion_x==1) this.dx-=suma_x;
        
        if(this.dy>0 ||direccion_y==2) this.dy+=suma_y;
        else if(this.dy<0 || direccion_y==1) this.dy-=suma_y;
        
        if(this.dx>this.limite) this.dx=this.limite;
        if(this.dy>this.limite) this.dy=this.limite;
        if(this.dx<(-this.limite)) this.dx=(-this.limite);
        if(this.dy<(-this.limite)) this.dy=(-this.limite);
        
        //mover pelota:
        this.subX+=this.dx;
        this.subY+=(this.dy);//-diferencia
        
        this.x=this.subX/this.seccionador;
        this.y=this.subY/this.seccionador;
        
    }
}