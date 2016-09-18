import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.*;
import java.io.*;
import java.util.*;
import java.sql.Timestamp;

public class Network_Server_TCP implements Runnable     // implement Runnable interface
{
   Socket csocket;
   static int port = 25001;
   Socket sock;
   ServerSocket serverSocket;
   BufferedReader s_input;
   PrintWriter s_out;
   Network_Server_TCP(int port)							// Create parameterized constructor with port as parameter.
   {
		this.port=port;
   }
   Network_Server_TCP(Socket csocket) 					// Create parameterized constructor with Socket as parameter.
   {
      this.csocket = csocket;
   }
   
   public static void main(String args[]) throws Exception 
   {     
		FileReader fr=new FileReader("Server_list.txt");		// Open Server_list.txt file.
		BufferedReader br=new BufferedReader(fr);	
		String rline = br.readLine();
		String host_name =rline.substring(0, rline.indexOf('|'));		// Read host_name to connect.
		String portno =rline.substring(rline.indexOf('|')+1);			// Read port to connect.
		int port=Integer.parseInt(portno);
		System.out.println("Host "+ host_name+ " port "+port);	
		Network_Server_TCP ns=new Network_Server_TCP(port);				// Create Network_Server_TCP object.
		ns.server_setup();  											// Call server_setup method.
   }
   public void server_setup()									// This method is responsible for making connection with client.
   {
		try
		{
		serverSocket = new ServerSocket(port);					// Create serverSocket object.
		System.out.println("Listening");	
		
		while (true) 
		{	  
			sock = serverSocket.accept();						// accepts incoming sockets from  client
			System.out.println("Connected");
			new Thread(new Network_Server_TCP(sock)).start();	// Create sepaate thread of each incoming socket
		}
		}
		catch(Exception e)
		{
			 System.out.println(e);
		}
   }
   public void run() {												// This method is called by each thread. 
      try 
	  {
		 
		 String rline;         
		 
		 s_input=new BufferedReader(new InputStreamReader(csocket.getInputStream()));    // Create BufferedReader 
		 s_out=new PrintWriter(csocket.getOutputStream(),true);  							// Create PrintWriter object 
		
		 while(!((rline= s_input.readLine()).equals("END")))							// Read till the rline != "END";
		 {
			s_out.println(rline);
			//System.out.println(rline);
		 }
         //System.out.println(rline);		        
         csocket.close();																	// Close socket
      }
      catch (IOException e) {
         System.out.println(e);
      }
   }
}
