import java.net.*;
import java.io.*;
import java.util.*;
import java.sql.Timestamp;

public class Network_Client_TCP extends Thread 					// Extends Thread class
{
	BufferedReader br;											// Variables declarations
	int len,iterations,No_Of_Threads;
	String file_name,Threard_Name;
	InetAddress address1,address2,address3;
	Socket socket1,socket2,socket3;
	OutputStream os1,os2,os3;
	OutputStreamWriter osw1,osw2,osw3;
	BufferedWriter bw1,bw2,bw3;
	BufferedReader s_input1,s_input2,s_input3;
	PrintWriter s_out1,s_out2,s_out3;
	String host = "localhost";
	int port = 25001;
	int size;
	double startTime=0.0,duration1=0.0;
	int byte_iterations,kb_iterations,mb_iterations;
	int cnt=0,cnt1=0;
	static double tcp_byte_time=0.0,tcp_byte_time_2=0.0,tcp_kb_time=0.0,tcp_64kb_time=0.0,tcp_kb_time_2=0.0,tcp_64kb_time_2=0.0;
	Network_Client_TCP(int No_Of_Threads,String Threard_Name,int size,String host_name,int port)			// Create parameterized constructor which takes
	{																									// No_Of_Threads,Threard_Name,size ,host_name and port as parameter
		this.No_Of_Threads=No_Of_Threads;
		this.Threard_Name=Threard_Name;
		this.size=size;	
		host=host_name;
		this.port=port;
		byte_iterations=size*1000*1000;										// decide byte iterations
		kb_iterations=size*1000;											// decide Kilo byte iterations
		mb_iterations=size*16;												// decide 64 Kilo byte iterations
	}
	Network_Client_TCP()													// Create default constructor
	{
	}
	synchronized public void run()											// This method is called by every thread.
	{
		try
		{							
			if(Threard_Name.equals("Thread1"))								// If thread name is Thread1 then do this
			{	
				if (No_Of_Threads==1)							
				{	
					address1 = InetAddress.getByName(host);												// get host address.
					socket1 = new Socket(address1, port);												// Create socket
			
					s_input1=new BufferedReader(new InputStreamReader(socket1.getInputStream())); 		// Create BufferedReader object
					s_out1=new PrintWriter(socket1.getOutputStream(),true);  							// Create PrintWriter object
					
					sender(1,byte_iterations,s_out1,s_input1);													// Call sender method for byte operations
					tcp_byte_time=tcp_byte_time+duration1;					
					sender(1000,kb_iterations,s_out1,s_input1);													 // Call sender method for kilo byte operations
					tcp_kb_time=tcp_kb_time+duration1;
					sender(64*1000,mb_iterations,s_out1,s_input1);												 // Call sender method for mega byte operations
					tcp_64kb_time=tcp_64kb_time+duration1;
					s_out1.println("END");	
					socket1.close();																	  // close socket.		
					
				}
				else													
				{
					address2 = InetAddress.getByName(host);													// get host address.
					socket2 = new Socket(address2, port);					                            	// Create socket
					s_input2=new BufferedReader(new InputStreamReader(socket2.getInputStream()));       	// Create BufferedReader object
					s_out2=new PrintWriter(socket2.getOutputStream(),true);                             	// Create PrintWriter object
					                                                                                    	
					sender(1,byte_iterations,s_out2,s_input2);					                                	// Call sender method for byte operations
					                                                                                    	
					tcp_byte_time=tcp_byte_time+duration1;                                              
																											
					sender(1000,kb_iterations,s_out2,s_input2);                                                  	// Call sender method for mega byte operations
					///System.out.println("before time1 "+tcp_kb_time);                                 
					tcp_kb_time=tcp_kb_time+duration1;                                                  
					//System.out.println("time1 "+tcp_kb_time);                                         
					sender(64*1000,mb_iterations,s_out2,s_input2);                                               	 // Call sender method for mega byte operations
					tcp_64kb_time=tcp_64kb_time+duration1;
					
					s_out2.println("END");	
					socket2.close();																		 // close socket.		
				}
			}
			else if(Threard_Name.equals("Thread2"))
			{	
				//cnt1=0;
				if (No_Of_Threads==2)
				{
					address3 = InetAddress.getByName(host);														// get host address.
					socket3 = new Socket(address3, port);			                                            // Create socket
					                                                                                            
					s_input3=new BufferedReader(new InputStreamReader(socket3.getInputStream()));               // Create BufferedReader object
					s_out3=new PrintWriter(socket3.getOutputStream(),true);                                     // Create PrintWriter object
					                                                                                            
					sender(1,byte_iterations,s_out3,s_input3);                                                           // Call sender method for byte operations
					                                                                                            
					tcp_byte_time_2=tcp_byte_time_2+duration1;                                                   
					                                                                                            
					sender(1000,kb_iterations,s_out3,s_input3);                                                           // Call sender method for kilo byte operations
					//System.out.println("before time2 "+tcp_kb_time_2);                                         
					tcp_kb_time_2=tcp_kb_time_2+duration1;                                                      
					//System.out.println("time2 "+tcp_kb_time_2);                                                
					sender(64*1000,mb_iterations,s_out3,s_input3);	                                                     // Call sender method for mega byte operations 
					tcp_64kb_time_2=tcp_64kb_time_2+duration1;                                                    
					
					s_out3.println("END");	
					socket3.close();																			  // close socket.		
				}
			}			
		}
		catch(Exception e)
		{
			System.out.println("Error In Run"+e);
		}
	}
	String randomString( int len )												// This method is responsible for making random string of given length
	{	
		final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder( len );
		for( int i = 0; i < len; i++ ) 
		sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );		
		return sb.toString();
	}
	void sender(int len,int iteration,PrintWriter s_out,BufferedReader s_input)						// This method responsible for communication with server. It takes length, iterationa and PrintWriter
	{																			// as parameter.
		//HashMap<Integer,String> cache = new HashMap<Integer,String>();			// Create HashMap datastructure 
		//for(int i=0;i<iteration;i++)		
		//{
			String main_string =randomString(len);										// Put generated string as HashMap.		
			//	System.out.println(cache.get(i));
		//}
		//System.out.println("Hi");
		String str;
		try
		{
			startTime = System.nanoTime();										// Start timer
			for(int i=0;i<iteration;i++)
			{					
				s_out.println(main_string);									// send packet to server.
				s_input.readLine();
			}							
			duration1 = System.nanoTime() - startTime;							// Stop timer and caluclate duration.
		}		
		catch(Exception e)
		{
			System.out.println(e);
		}
	}		
	public static void main(String args[])	
	{	
		int No_Of_Threads=0;
		int iteration=20;
		int size;
		BufferedReader sc_input=new BufferedReader(new InputStreamReader(System.in)); 
		Thread t1,t2;
		try
		{		
			System.out.println("Enter the no of threads to create:-");					// Take no threads to create input from user
			No_Of_Threads=Integer.parseInt(sc_input.readLine());
			
			System.out.println("Enter the size of operation to perform in MB:-");		// Take size of data transfer from user in MB
			size=Integer.parseInt(sc_input.readLine());
			
			FileReader fr=new FileReader("Server_list.txt");							// read Server_list.txt 
			BufferedReader br=new BufferedReader(fr);	
			String rline = br.readLine();	
			String host_name =rline.substring(0, rline.indexOf('|'));					// separate host name to connect.
			String portno =rline.substring(rline.indexOf('|')+1);						// read port from file to connect.
			int port=Integer.parseInt(portno);
			System.out.println("Host "+ host_name+ " port "+port);
			
			if(No_Of_Threads==1)
			{
				t1=new Network_Client_TCP(No_Of_Threads,"Thread1",size,host_name,port);		// create thread object 
				t1.start();																	// call run method 
				Thread[] threads= {t1};		
				for (Thread thread : threads) 												
				{
					thread.join();															// wait till thread completes its execution.
				}
			}
			else if (No_Of_Threads==2)									
			{
				t1=new Network_Client_TCP(No_Of_Threads,"Thread1",size,host_name,port);		// create thread object 	
				t1.start();                                                                 // call run method 
				t2=new Network_Client_TCP(No_Of_Threads,"Thread2",size,host_name,port);		// create thread object 
				t2.start();                                                                 // call run method 
				Thread[] threads= {t1,t2};
				for (Thread thread : threads) 
				{
					thread.join();															// wait till thread completes its execution.
				}
			}	
			Network_Client_TCP nct=new Network_Client_TCP();								// create Network_Client_TCP object
			nct.display(No_Of_Threads,size);												// call Display method 
		}	
		catch(Exception e)
		{
			System.out.println(e);
		}		
	}
	public void display(int No_Of_Threads,int size)											// This method is responsible for calculating throughput and latency and displaying it to user.
	{
		System.out.println("Throughput of TCP operations of 1byte packet size "+((No_Of_Threads*size*2)/((tcp_byte_time+tcp_byte_time_2)/1000000000))+" (MB/Sec)");				
		System.out.println("Throughput of TCP operations of 1 KB packet size "+((No_Of_Threads*size*2)/((tcp_kb_time+tcp_kb_time_2)/1000000000))+" (MB/Sec)");		
		//System.out.println("time2 "+(tcp_kb_time+tcp_64kb_time_2));
		System.out.println("Throughput of TCP operations of 64KB packet size "+((No_Of_Threads*size*2)/((tcp_64kb_time+tcp_64kb_time_2)/1000000000))+" (MB/Sec)");		
		System.out.println();
		System.out.println("Latency of TCP operations of 1byte packet size "+(((tcp_byte_time_2+tcp_byte_time)/1000000)/(No_Of_Threads*size*2))+" (Milliseconds)");		
		System.out.println("Latency of TCP operations of 1 KB packet size "+(((tcp_kb_time+tcp_kb_time_2)/1000000)/(No_Of_Threads*size*2))+" (Milliseconds)");		
		System.out.println("Latency of TCP operations of 64KB packet size "+(((tcp_64kb_time+tcp_64kb_time_2)/1000000)/(No_Of_Threads*size*2))+" (Milliseconds)");		
		
	}
}
