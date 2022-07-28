package Server;

import java.net.*;
import java.util.ArrayList;

class UDPServer implements Runnable{
    
    public static ArrayList<Integer> idCliente = new ArrayList<>();//Id de cada cliente conectado al servidor, es asignado por el servidor y cada uno es un juego ejecutandose.
    public static ArrayList<DatagramPacket> packetCliente = new ArrayList<>();
    public static ArrayList<String> mensajeCliente = new ArrayList<>();//mensajes personalizados para cada cliente.
    public static int retraso=0,idClienteActual=0,puerto=7777,bytesPaquete=4096;
    public static String mensajeCompleto="";
    public boolean detener=false;
    public static int maxPlayers=(Integer.parseInt(Server.propiedades[1][Server.indexMaxPlayers]));
    public static int maxClients=(Integer.parseInt(Server.propiedades[1][Server.indexMaxClients]));
    
    public void run(){
            try{
                try{
                    this.puerto=Integer.parseInt(Server.puerto.getText());
                }catch(Exception e){
                    Server.agregarInformacion("Error: puerto ingresado no valido!");
                    return;
                }
                long tiempo=System.currentTimeMillis();
                DatagramSocket serverSocket = new DatagramSocket(puerto);
                byte[] receiveData;
                byte[] sendData;
                Server.agregarInformacion("Servidor iniciado...");
                while(true){
                    receiveData = new byte[bytesPaquete];
                    sendData = new byte[bytesPaquete];
                    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

                    serverSocket.receive(receivePacket);

                    String mensaje = (new String(receivePacket.getData())).trim();//mensaje original posee un largo definido por eso se traspasa a otro string.

                    //System.out.println("RECEIVED: " + mensaje);
                    int identificador=Integer.parseInt(mensaje.split(";")[0]);
                    String respuesta="";
                    if(identificador==0){//nuevo cliente.
                        
                        String configuracion=(mensaje.split(";")[1]);
                        
                        int jugadores=Integer.parseInt(configuracion.split(",")[0]);
                        int anchoCamara=Integer.parseInt(configuracion.split(",")[1]);
                        int altoCamara=Integer.parseInt(configuracion.split(",")[2]);
                        
                        if(ServerJuego.superJugador.size()+jugadores<=maxPlayers || maxPlayers==0){
                            
                            if((idClienteActual+1)<=maxClients || maxClients==0){
                                idClienteActual++;
                                idCliente.add(idClienteActual);
                                mensajeCliente.add("");
                                Server.agregarInformacion("Nueva conexion (id cliente "+idClienteActual+").");

                                respuesta=Integer.toString(idClienteActual)+"c"+ServerJuego.ancho+"c"+ServerJuego.alto;//c=separador de configuracion inicial.

                                for(int j=0;j<jugadores;j++){
                                    ServerJuego.superJugador.add(new SuperJugador(ServerJuego.personaje.posIniX[j],ServerJuego.personaje.posIniY[j],ServerJuego.superJugador.size(),idClienteActual,anchoCamara,altoCamara));
                                }
                            }else{
                                respuesta="0c2c"+Integer.toString(maxClients);//idCliente=0(fallo la conexion),1=tipo error(limite de clientes superado),detalle error(superado limite de x clientes).
                                Server.agregarInformacion("Conexion fallida por limite de clientes superado ("+maxClients+").");
                            }
                            
                        }else{
                            respuesta="0c1c"+Integer.toString(maxPlayers);//idCliente=0(fallo la conexion),1=tipo error(limite de jugadores superado),detalle error(superado limite de x jugadores).
                            Server.agregarInformacion("Conexion fallida por limite de jugadores superado ("+maxPlayers+").");
                        }
                        
                        
                    }else{//ya esta en la lista de clientes.
                        
                        MapaServer.RecibirControles(mensaje.split(";")[1],identificador);
                        if(ServerJuego.corriendo && ServerJuego.permiso){//se puede interrumpir la ejecucion de este thread si se borran drasticamente los arraylist de los proyectiles.
                            respuesta=ServerJuego.nivel+"e"+MapaServer.TransformarDatosJugadores(identificador)+MapaServer.TransformarCamaras(identificador)+MapaServer.TransformarElementos(identificador);//falta hacerlo inteligentemente(mas eficiente).
                        }else{
                            respuesta=ServerJuego.nivel+"e"+"e"+"e"+"e";//los elementos van vacios
                        }
                        //System.out.println(respuesta);
                        //System.out.println("mensaje="+mensaje);
                        /*
                        int cont=0;
                        for(int m=0;m<idCliente.size();m++){
                            if(idCliente.get(m)==identificador){
                                mensajeCliente.set(m, mensaje);
                                cont++;
                            }else{
                                //respuesta+=mensajeCliente.get(m)+"=";
                            }

                            respuesta=respuesta+(mensajeCliente.get(m)+"=");
                        }
                        */
                    }
                    sendData = respuesta.getBytes();
                    InetAddress IPAddress = receivePacket.getAddress();
                    System.out.println(IPAddress);
                    int port = receivePacket.getPort();
                    DatagramPacket sendPacket =
                    new DatagramPacket(sendData, sendData.length, IPAddress, port);
                    serverSocket.send(sendPacket);

                }
                
            }catch(Exception e){
                Server.agregarInformacion(e.getMessage());
                e.printStackTrace();
            }
    }
    public boolean isInteger(String str){
        try{
            Integer.parseInt(str);
            return true;
        }catch(Exception e){
            return false;
        }
    }
    public String procesarMensaje(String mensaje){//agregar elementos al servidor(no jugadores).
        return mensaje;//caracter separador entre clientes.
    }
}