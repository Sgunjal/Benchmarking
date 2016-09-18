import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.*;
import java.io.*;
import java.util.*;
import java.sql.Timestamp;

public class Network_Server_UDP implements Runnable      // Implement runnable interface
{
   Socket csocket;							
   static int port = 25001;
   ServerSocket serverSocket1;
   Socket sock;
   DatagramSocket serverSocket;
   Network_Server_UDP nsu;
   String host;
   Network_Server_UDP()				//Declare default constructor
   {
   }  
   Network_Server_UDP(int portno, String host) 	//		declare parameterized constructor which takes portno as parameter
   {		
		port=portno;
		this.host=host;
   } 
    Network_Server_UDP(Socket csocket,DatagramSocket serverSocket)    //		Declare parameterized constructor which takes Socket and serverSocket as parameter  
   {
      this.csocket = csocket;
	  this.serverSocket=serverSocket;
   }
   public static void main(String args[]) throws Exception 		
   {      		
		FileReader fr=new FileReader("Server_list.txt");				// Read Server_list.txt file.
		BufferedReader br=new BufferedReader(fr);	
		String rline = br.readLine();
		String host_name =rline.substring(0, rline.indexOf('|'));			//seperate host name and 
		String portno =rline.substring(rline.indexOf('|')+1);				// separate portno
		int port=Integer.parseInt(portno);
		System.out.println("Host "+ host_name+ " port "+port);			
		Network_Server_UDP ns=new Network_Server_UDP(port,host_name);
		ns.server_setup();  											//call server_setup
   }
   public void server_setup() throws Exception							// This method is responsible for seting up server and creating separate thread as request comes. 	
   {
		try
		{		
			serverSocket1 = new ServerSocket(port);						// Create Socket connection.
			serverSocket = new DatagramSocket(25002);  					// Create DatagramSocket
		while (true) 									
		{	  			
			sock = serverSocket1.accept();								// Accept incoming request.
			new Thread(new Network_Server_UDP(sock,serverSocket)).start();			// create separate thread of each inciming request.		
		}
		}
		catch(Exception e)
		{
			 System.out.println("seerver_setup"+e);
		}
   }
   public void run()  {													// This method is called by each new thread
     // System.out.println("New Thread");
	  try 
	  {		 					
			execute(serverSocket,serverSocket1);						//call execute method.
      }
      catch (Exception e) {
         System.out.println("run"+e);
      }
   }
   public void execute(DatagramSocket serverSocket,ServerSocket serverSocket1)  // This method is resonsible for carring out actual send receive operation
	{																			// which takes DatagramSocket and ServerSocket as input parameter.
		try
		{											
			BufferedReader s_input=new BufferedReader(new InputStreamReader(csocket.getInputStream())); 
			PrintWriter s_out=new PrintWriter(csocket.getOutputStream(),true);  
			String str=s_input.readLine();           									// Get the length of packet from client
			//System.out.println("lenghth "+str);
			int len=Integer.parseInt(str);	
			
			str=s_input.readLine();         											// Get the number of iterations from client.  
			//System.out.println(str);
			int iterations=Integer.parseInt(str);
			//System.out.println(iterations);
			csocket.close();															// close socket.
			DatagramPacket receivePacket;
			DatagramPacket sendPacket;	
			InetAddress address1 = InetAddress.getByName(host);	
			DatagramSocket clientSocket = new DatagramSocket(port);  	
			Socket socket1 = new Socket(address1, port);
			byte[] receiveData = new byte[len];  										// create receiveDate byte array.
            byte[] sendData = new byte[len]; 											// Create sendData byte array.
			int counter=iterations;
			InetAddress IPAddress;
			int port1;
			//System.out.println("I am here" + counter);
			//System.out.println("Done1");
			while(counter>0)  
            {  
				 // System.out.println("While start" );
                  receivePacket = new DatagramPacket(receiveData, receiveData.length);  			// create DatagramPacket packet to receive data.
                  serverSocket.receive(receivePacket);  														// receive incoming packet
                  String sentence = new String( receivePacket.getData());  										// convert it to string.
                //  System.out.println("RECEIVED: " + sentence);    					  
				  /*IPAddress = receivePacket.getAddress();                   
				  port1 = receivePacket.getPort();                   
				  //String capitalizedSentence = sentence.toUpperCase();                   
				  sendData = sentence.getBytes();                   
				  sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port1);                   
				  serverSocket.send(sendPacket);  */
				  counter--;	
				  //System.out.println("While end" );
            }  
			//System.out.println("Done");
}
	catch(Exception e)			
	{
		//System.out.println("Hi"+e);
	}
	}	  
}