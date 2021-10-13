//chan in (int);
//
//
//import java.util.Date;
//import java.io.Serializable;
// 
///**
// * Student.java
// * This class represents a Student
// *
// * @author www.codejava.net
// */
//public class  Fichero implements Serializable {
//    private String name;
//    ....
//
//}
//
//===================================
//
//public class Servidor {
// 
//    public static void main(String[] args) {
//
//           ......
//            ObjectOutputStream objectOutput
//                = new ObjectOutputStream(socket.getOutputStream());
//            ) {
// 
//            List<Fichero> listF = new ArrayList<>();
// 
//            listF.add(new Fichero(...));
// 
//   
//            for (Fichero f : listF) {
//                objectOutput.writeObject(f);
//		objectOutput.flush();
//            }
// 
//            ....
//}
//
//public class Cliente {
// 
//    public static void main(String[] args) {
//
//           ......
//            ObjectInputStream objectInput
//                = new ObjectInputStream(socket.getInputStream());
//            ) {
// 
//     
//   
//              Fichero f = (Fichero) objectInput.readObject();
//            
// 
//            ....
//}
//
//================================================================================
//
//
//
//                          C1 --- teclado
//                          OS1
//                          Receptor
//                         
//
//
//            socket
//
//
//   OC1                   Socket
//  Serv                   
//   X500
//   OC2
//   OC3
//     
//
//             Socket
//                         Emisor
//                         X350 IP2
//                         OS2
//                         C2 --- teclado
//                      
//                       
//                        
//			
//  Socket      
//
//
//
//    
//  OS3
//  C3  --- teclado
//
//===================================================================================
//
//
//
//Servidor
//===================================
//
//* tabla usuarios --- (id usuario, fin, fout)
//* tabla informacion --- (id usuario, [f1,f2,....])
//* direccion IP
//* puerto
//* serverSocket
//
//===================================
//
//
//   main ----
//
//     while (true)
//       {
//       s=serverSocket.accept();
//       (new OC(s)).start();
//
//       }
//
//
////OyenteCliente == ServerListener
//OyenteCliente
//===================================
//
//
//run
//-----
//
//while (true)
//  {
//   Mensaje m = (Mensaje) fin.readObject();
//   case m:
//
//     - MENSAJE_CONEXION:
//        * guardar informacion del usuario (en las tablas)
//	* envio mensaje confirmacion conexion fout
//
//     - MENSAJE_LISTA_USARIOS:
//        * crear un mensaje con la informacion de usuarios en sistema
//	* envio mensaje confirmacion lista usuarios fout
//
//     - MENSAJE_CERRAR_CONEXION:
//        * eliminar informacion del usuario (en las tablas)
//	* envio mensaje confirmacion cerrar conexion fout
//
//     - MENSAJE_PEDIR_FICHERO:
//        * buscar usuario que contiene el fichero y obtener fout2
//	* envio mensaje MENSAJE_ EMITIR_FICHERO por fout2
//
//     - MENSAJE_PREPARADO_CLIENTESERVIDOR:
//        * buscar fout1 (flujo del cliente al que hay que enviar la informacion)
//        * envio  fout1 mensaje  MENSAJE_PREPARADO_SERVIDORCLIENTE
//}
//
//
//                                      S          
//
//                     fin1                         fout2                      
//C1                -------------- >  OC1   OC2 -------------- >  C2
//            MENSAJE_PEDIR_FICHERO 3       MENSAJE_ EMITIR_FICHERO 3 a C1  
//                 <---------------            <---------------
//		     fout1                         fin2
//MENSAJE_PREPARADO_SERVIDORCLIENTE         MENSAJE_PREPARADO_CLIENTESERVIDOR
//             (dir ip, puerto)               (C1, dir ip, puerto) serverSocket
//                                     X
//
//
//
//
//
//Cliente -- main
//========
//
//- leer nombre teclado
//- crear socket con servidor
//- crear nuevo thread OyenteServidor para leer el socket
//- enviar MENSAJE_CONEXION
//- establecer menu con usuario:
//   * consultar lista usuarios:
//        enviar MENSAJE_LISTA_USUARIOS
//   * pedir fichero
//        enviar MENSAJE_PEDIR_FICHERO 3
//   * salir
//        enviar MENSAJE_CERRAR_CONEXION
//  
// 
//OyenteServidor
//===============
//
//
//while (true)
//{
//
//  Mensaje m = (Mensaje) fin.readObject()
//  case m:
//  - MENSAJE_CONFIRMACION_CONEXION:
//       imprimir conexion establecida por standard output
//  - MENSAJE_CONFIRMACION_LISTA_USUARIOS
//       imprimir lista usuarios por standard output
//  - MENSAJE_EMITIR_FICHERO
//       (nos llega nombre de cliente C1 e informacion pedida 3)
//        enviar MENSAJE_PREPARADO_CLIENTESERVIDOR a mi oyente
//	crear proceso EMISOR y esperar en accept la conexion
//	
//  - MENSAJE_PREPARADO_SERVIDORCLIENTE
//       (llega direccion Ip y puerto del propietario de fichero)
//       crear proceso RECEPTOR
//
//  - MENSAJE_CONFIRMACION_CERRAR_CONEXION
//       imprimir adios por standard output
//
//
//RECEPTOR
//========
//crear socket
//acceder flujo entrada
//recibir informacion
//
//EMISOR
//======
//crear ServerSocket
//accept
//acceder flujo salida
//escribir informacion por salida
//
//MENSAJE
//=======
//clase abstracta
//lleva informacion sobre tipo de mensaje, origen, ...
