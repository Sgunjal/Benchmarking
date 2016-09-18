import java.net.*;
import java.io.*;
import java.util.*;
import java.sql.Timestamp;

public class Network_Client_UDP extends Thread            //  extrends Thread
{
	BufferedReader br;
	int len,iterations,No_Of_Threads;
	String file_name,Threard_Name;			
	String host;
	int port;
	int cnt=0,cnt1=0;
	int size;
	double startTime=0.0,duration1=0.0;
	int byte_iterations,kb_iterations,mb_iterations;
	static double udp_byte_time=0.0,udp_byte_time_2=0.0,udp_kb_time=0.0,udp_64kb_time=0.0,udp_kb_time_2=0.0,udp_64kb_time_2=0.0;
	Network_Client_UDP() 											// Declare default construct Network_Client_UDP
	{
	}
	Network_Client_UDP(int No_Of_Threads,String Threard_Name,int size,String host_name,int portno)  // Declare parameterized ocnstructore which takes
	{																				//No_Of_Threads,Threard_Name,size,host_name and portno as input.
		this.No_Of_Threads=No_Of_Threads;
		this.Threard_Name=Threard_Name;
		this.size=size;	
		host=host_name;
		port=portno;
		byte_iterations=size*1024*1024;												// Compute byte iterations.
		kb_iterations=size*1024;													// Compute Kilobyte iterations.
		mb_iterations=size*16;														// Compute megabyte iterations.
	}
	synchronized public void run()											
	{
		try
		{							
			if(Threard_Name.equals("Thread1"))
			{	
				if (No_Of_Threads==1)
				{						
					sender(1,byte_iterations);									//	call sender method to send 1 byte packet data to UDP server
					udp_byte_time=udp_byte_time+duration1;					
					sender(1024,kb_iterations);									//	call sender method to send 1 Kbyte packet data to UDP server
					udp_kb_time=udp_kb_time+duration1;
					sender(64*1000,mb_iterations);								//	call sender method to send 64 Kbyte packet data to UDP server
					udp_64kb_time=udp_64kb_time+duration1;																	
				}
				else
				{				
					sender(1,byte_iterations);									//	call sender method to send 1 byte packet data to UDP server
					udp_byte_time=udp_byte_time+duration1;					
					sender(1024,kb_iterations);									//	call sender method to send 1 Kbyte packet data to UDP server
					udp_kb_time=udp_kb_time+duration1;
					sender(64*1000,mb_iterations);								//	call sender method to send 64 kbyte packet data to UDP server
					udp_64kb_time=udp_64kb_time+duration1;												
				}
			}
			else if(Threard_Name.equals("Thread2"))
			{					
				if (No_Of_Threads==2)
				{									
					sender(1,byte_iterations);									//	call sender method to send 1 byte packet data to UDP server
					udp_byte_time_2=udp_byte_time_2+duration1;					
					sender(1024,kb_iterations);									//	call sender method to send 1 Kbyte packet data to UDP server
					udp_kb_time_2=udp_kb_time_2+duration1;					
					sender(64*1000,mb_iterations);								//	call sender method to send 1 64 kbyte packet data to UDP server
					udp_64kb_time_2=udp_64kb_time_2+duration1;																							
				}
			}			
		}
		catch(Exception e)
		{
			System.out.println("Error In Run"+e);
		}
	}
	String randomString( int len )											// This method takes length as parameter and generates random string of that lenght.
	{	
		final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder( len );
		for( int i = 0; i < len; i++ ) 
		sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );					
		return sb.toString();
	}
	void sender(int len,int iteration)										// This method takes lenghth and iterations as parameter, send fixed sized packet to UDP server.
	{		
		try
		{			
			String main_string=randomString(len);								// Put random strings to HashMap.					
			InetAddress address1 = InetAddress.getByName(host);			
			Socket socket1 = new Socket(address1, port);					// Create and establish socket connection on port 25003 of given address.
			
			BufferedReader s_input1=new BufferedReader(new InputStreamReader(socket1.getInputStream())); 
			PrintWriter s_out1=new PrintWriter(socket1.getOutputStream(),true); 			
			String str=Integer.toString(len);												// Convert lenghth to string 
			//System.out.println("Len"+str);
			s_out1.println(str);															// send length to server to tell i am sending this size of packets.
			str=Integer.toString(iteration);												// Convert iteratins to string 
			s_out1.println(str);															// Send no if iterations to server.
			//System.out.println(str);	
			socket1.close();																// close socket.			
			String sentence;
			DatagramSocket clientSocket = new DatagramSocket();  							// Create DatagramSocket
			InetAddress IPAddress = InetAddress.getByName(host); 
			//Client Socket is created  
			DatagramPacket sendPacket;														// Create DatagramSocket to send packet
			DatagramPacket receivePacket;													// Create DatagramSocket to receive packet			
			byte[] sendData = new byte[len];  												// Create byte array to send.
			byte[] receiveData = new byte[len];  											// Create byte array to receive.			
			startTime = System.nanoTime();				// Start timer.
			//System.out.println("For before start" );
			//System.out.println("Done1");
			sentence=main_string;
			sendData = sentence.getBytes();
			String modifiedSentence;
			sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 25002);  				// Create packet 
			for(int i=0;i<iteration;i++)			
			{		
			
				clientSocket.send(sendPacket);  												// send it over network.
				/*receivePacket = new DatagramPacket(receiveData, receiveData.length);       
				clientSocket.receive(receivePacket);       
				modifiedSentence = new String(receivePacket.getData());      				*/
			}			
			duration1 = System.nanoTime() - startTime;									// Stop timer and compute time.			
			//System.out.println("Done");
			clientSocket.close();  																// close socket		
		}	
		catch(Exception e)
		{
			System.out.println("HI"+e);
		}		
	}
	public void	display(int No_Of_Threads,int size)								// Compuet throughput,latency and display it.
	{
		System.out.println("Throughput of UDP operations of 1byte packet size "+((No_Of_Threads*size)/((udp_byte_time+udp_byte_time_2)/1000000000))+" (MB/Sec)");				
		System.out.println("Throughput of UDP operations of 1 KB packet size "+((No_Of_Threads*size)/((udp_kb_time+udp_kb_time_2)/1000000000))+" (MB/Sec)");		
		System.out.println("Throughput of UDP operations of 64KB packet size "+((No_Of_Threads*size)/((udp_64kb_time+udp_64kb_time_2)/1000000000))+" (MB/Sec)");		
		System.out.println();
		System.out.println("Latency of UDP operations of 1byte packet size "+(((udp_byte_time_2+udp_byte_time)/1000000)/(No_Of_Threads*size))+" (Milliseconds)");		
		System.out.println("Latency of UDP operations of 1 KB packet size "+(((udp_kb_time+udp_kb_time_2)/1000000)/(No_Of_Threads*size))+" (Milliseconds)");		
		System.out.println("Latency of UDP operations of 64KB packet size "+(((udp_64kb_time+udp_64kb_time_2)/1000000)/(No_Of_Threads*size))+" (Milliseconds)");		
	}
	public static void main(String args[])
	{	
		int No_Of_Threads=0;
		int iteration=20;
		BufferedReader sc_input=new BufferedReader(new InputStreamReader(System.in)); 
		Thread t1,t2;
		int size=0;
		try
		{		
			System.out.println("Enter the no of threads to create:-");					// Get no of thread to create.
			No_Of_Threads=Integer.parseInt(sc_input.readLine());
			
			System.out.println("Enter the size of operations to perform:-");			// Get the size of operation to perform in MB
			size=Integer.parseInt(sc_input.readLine());
						
			FileReader fr=new FileReader("Server_list.txt");					// Open file Server_list to know which server and port to connect.
			BufferedReader br=new BufferedReader(fr);	
			String rline = br.readLine();
			String host_name =rline.substring(0, rline.indexOf('|'));
			String portno =rline.substring(rline.indexOf('|')+1);
			int port=Integer.parseInt(portno);
			System.out.println("Host "+ host_name+ " port "+port);
			if(No_Of_Threads==1)
			{
				t1=new Network_Client_UDP(No_Of_Threads,"Thread1",size,host_name,port);					// Create thread object 
				t1.start();																				// call run method
				Thread[] threads= {t1};
				for (Thread thread : threads) 
				{
					thread.join();																		// Wait till thread complete its execution.
				}
			}
			else if (No_Of_Threads==2)	
			{
				t1=new Network_Client_UDP(No_Of_Threads,"Thread1",size,host_name,port);						// Create thread object 		
				t1.start();                                                                                 // call run method
				t2=new Network_Client_UDP(No_Of_Threads,"Thread2",size,host_name,port);						// Create thread object 
				t2.start();                                                                                 // call run method
				Thread[] threads= {t1,t2};
				for (Thread thread : threads) 
				{
					thread.join();																			// Wait till thread complete its execution.				
				}
			}
			Network_Client_UDP ncu=new Network_Client_UDP();												// create Network_Client_UDP object 
			ncu.display(No_Of_Threads,size);																// Call display method to show throughput and latency.
		}
		catch(Exception e)
		{
			System.out.println(e);
		}		
	}
}
