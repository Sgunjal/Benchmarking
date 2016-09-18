import java.net.*;
import java.io.*;
import java.util.*;
import java.sql.Timestamp;

public class Disk extends Thread              // Extends Thread
{
	BufferedReader br;                                      
	int len,iterations,No_Of_Threads,size;
	int byte_iterations,kb_iterations,mb_iterations;
	String file_name,Threard_Name;
	double startTime=0.0,duration1=0.0;
	static double sum_seq_write_1=0.0,sum_seq_read_1=0.0,sum_rndm_read_1=0.0,sum_rndm_write_1=0.0;	
	static double sum_seq_write_1024=0.0,sum_seq_read_1024=0.0,sum_rndm_read_1024=0.0,sum_rndm_write_1024=0.0;	
	static double sum_seq_write_21024=0.0,sum_seq_read_21024=0.0,sum_rndm_read_21024=0.0,sum_rndm_write_21024=0.0;		
	static double sum_seq_write_final=0.0,sum_seq_read_final=0.0,sum_rndm_read_final=0.0,sum_rndm_write_final=0.0;
	Disk()                                /// declare Default constructor.
	{
	}
	Disk(int No_Of_Threads,String Threard_Name,int size)   /// Parameterized constructor which will take No_Of_Threads , Threard_Name and size of operationi
	{														// as input 					
		this.No_Of_Threads=No_Of_Threads;
		this.Threard_Name=Threard_Name;		
		this.size=size;
	}
	synchronized public void run()                   // Once the thread gets created it will make a call to run method.
	{
		try
		{		
			byte_iterations=(size*1000*1000);        // Decide number of iterations of byte packet send.
			kb_iterations=size*1000;				 // Decide number of iterations of Kilo byte packet send.
			mb_iterations=size;						 // Decide number of iterations of Mega byte packet send.
			if(Threard_Name.equals("Thread1"))       // If loop for Thread1
			{	
				if (No_Of_Threads==1)                 // if number if threads are one the flollow this.
				{
					write_file(1,"one_byte.txt",byte_iterations);      // create one_byte.txt of one byte data.
					sum_seq_write_1=sum_seq_write_1+duration1;
					//System.out.println("one_byte");
					write_file(1000,"kilo_byte.txt",kb_iterations);    // create kilo_byte.txt of one kilo byte data.
					sum_seq_write_1024=sum_seq_write_1024+duration1;
					//System.out.println("kilo_byte");
					write_file(1000*1000,"mega_byte.txt",mb_iterations); // create mega_byte.txt of mega byte data.
					sum_seq_write_21024=sum_seq_write_21024+duration1; 
					//System.out.println("mega_byte");
					
					Sequential_read("one_byte.txt",byte_iterations); 					// Read from one_byte.txt
					sum_seq_read_1=sum_seq_read_1+duration1;
					//System.out.println("one_byte");
					Sequential_read("kilo_byte.txt",kb_iterations);					// Read from kilo_byte.txt
					sum_seq_read_1024=sum_seq_read_1024+duration1;
					//System.out.println("kilo_byte");
					Sequential_read("mega_byte.txt",mb_iterations);					// Read from mega_byte.txt
					sum_seq_read_21024=sum_seq_read_21024+duration1;
				//	System.out.println("mega_byte");
			
					random_write(1,"one_bytew_1.txt",byte_iterations);	// create one_bytew_2.txt of one byte data.
					sum_rndm_write_1=sum_rndm_write_1+duration1;
					//System.out.println("one_bytew_1");
					random_write(1000,"kilo_bytew_1.txt",kb_iterations); // create kilo_bytew_2.txt of one byte data.
					sum_rndm_write_1024=sum_rndm_write_1024+duration1;
					//System.out.println("kilo_bytew_1");
					random_write(1000*1000,"mega_bytew_1.txt",mb_iterations); // create mega_bytew_2.txt of one byte data.					
					sum_rndm_write_21024=sum_rndm_write_21024+duration1;
					//System.out.println("mega_bytew_1");
					
					random_read("one_bytew_1.txt",byte_iterations);    	// Read from one_bytew_2.txt
					sum_rndm_read_1=sum_rndm_read_1+duration1;
					//System.out.println("one_bytew_1");
					random_read("kilo_bytew_1.txt",kb_iterations); 		// Read from kilo_bytew_2.txt
					sum_rndm_read_1024=sum_rndm_read_1024+duration1;
					//System.out.println("kilo_bytew_1");
					random_read("mega_bytew_1.txt",mb_iterations);		// Read from mega_bytew_2.txt
					sum_rndm_read_21024=sum_rndm_read_21024+duration1;
					//System.out.println("mega_bytew_1");
				}
				else
				{
					write_file(1,"one_byte_1.txt",byte_iterations);                            // create one_byte_1.txt of one byte data.
					sum_seq_write_1=sum_seq_write_1+duration1;                                 
					//System.out.println(sum_seq_write_1 +" sum1 :-"+duration1);               
					write_file(1000,"kilo_byte_1.txt",kb_iterations);                          // create kilo_byte_1.txt of one kilo byte data.
					sum_seq_write_1024=sum_seq_write_1024+duration1;                           
					//System.out.println("sum_seq_write_1024 "+sum_seq_write_1024+"duration1 "+duration1);
					write_file(1000*1000,"mega_byte_1.txt",mb_iterations);                      // create mega_byte_1.txt of mega byte data.
					sum_seq_write_21024=sum_seq_write_21024+duration1;                         
			                                                                                   
					Sequential_read("one_byte_1.txt",byte_iterations);                                         // Read from one_byte_1.txt
					sum_seq_read_1=sum_seq_read_1+duration1;                                   
					Sequential_read("kilo_byte_1.txt",kb_iterations);                                        // Read from kilo_byte_1.txt
					sum_seq_read_1024=sum_seq_read_1024+duration1;                             
					//System.out.println("sum_seq_read_1024 "+sum_seq_read_1024+"duration1 "+duration1);
					Sequential_read("mega_byte_1.txt",mb_iterations);                                        // Read from mega_byte_1.txt
					sum_seq_read_21024=sum_seq_read_21024+duration1;                           
			                                                                                   
					random_write(1,"one_bytew_1.txt",byte_iterations);                         // create one_bytew_1.txt of one byte data.
					sum_rndm_write_1=sum_rndm_write_1+duration1;                               
					random_write(1000,"kilo_bytew_1.txt",kb_iterations);                        // create kilo_bytew_1.txt of one byte data.
					sum_rndm_write_1024=sum_rndm_write_1024+duration1;                         
					random_write(1000*1000,"mega_bytew_1.txt",mb_iterations);                  // create mega_bytew_1.txt of one byte data.
					sum_rndm_write_21024=sum_rndm_write_21024+duration1;                      
					                                                                           
					random_read("one_bytew_1.txt",byte_iterations);                            	// Read from one_bytew_1.txt
					sum_rndm_read_1=sum_rndm_read_1+duration1;                                 
					random_read("kilo_bytew_1.txt",kb_iterations);                             // Read from kilo_bytew_1.txt
					sum_rndm_read_1024=sum_rndm_read_1024+duration1;                           
					random_read("mega_bytew_1.txt",mb_iterations);	                           // Read from mega_bytew_1.txt
					sum_rndm_read_21024=sum_rndm_read_21024+duration1;
					//System.out.println("Don1 is complete");
				}
			}
			else if(Threard_Name.equals("Thread2"))
			{	
				if (No_Of_Threads==2)
				{																		
					write_file(1,"one_byte_2.txt",byte_iterations);							  // create one_byte_2.txt of one byte data.
					sum_seq_write_1=sum_seq_write_1+duration1;                                
					//System.out.println(sum_seq_write_1+" sum2 :-"+sum_seq_write_1);         
					write_file(1000,"kilo_byte_2.txt",kb_iterations);                         // create kilo_byte_2.txt of one kilo byte data.
					sum_seq_write_1024=sum_seq_write_1024+duration1;                          
					//System.out.println("sum_seq_write_1024 "+sum_seq_write_1024+"duration1 "+duration1);
					write_file(1000*1000,"mega_byte_2.txt",mb_iterations);                     // create mega_byte_2.txt of mega byte data.
					sum_seq_write_21024=sum_seq_write_21024+duration1;                        
			                                                                                  
					Sequential_read("one_byte_2.txt",byte_iterations);                                        // Read from one_byte_2.txt
					sum_seq_read_1=sum_seq_read_1+duration1;                                  
					Sequential_read("kilo_byte_2.txt",kb_iterations);                                       // Read from kilo_byte_2.txt
					sum_seq_read_1024=sum_seq_read_1024+duration1;                            
					//System.out.println("sum_seq_read_1024 "+sum_seq_read_1024+"duration1 "+duration1);
					Sequential_read("mega_byte_2.txt",mb_iterations);                                       // Read from mega_byte_2.txt
					sum_seq_read_21024=sum_seq_read_21024+duration1;                          
					                                                                          
					random_write(1,"one_bytew_2.txt",byte_iterations);                        // create one_bytew_2.txt of one byte data.
					sum_rndm_write_1=sum_rndm_write_1+duration1;                              
					random_write(1000,"kilo_bytew_2.txt",kb_iterations);                       // create kilo_bytew_2.txt of one byte data.
					sum_rndm_write_1024=sum_rndm_write_1024+duration1;                        
					random_write(1000*1000,"mega_bytew_2.txt",mb_iterations);                 // create mega_bytew_2.txt of one byte data.
					sum_rndm_write_21024=sum_rndm_write_21024+duration1;                     
					                                                                          
					random_read("one_bytew_2.txt",byte_iterations);                           	// Read from one_bytew_2.txt
					sum_rndm_read_1=sum_rndm_read_1+duration1;                                
					random_read("kilo_bytew_2.txt",kb_iterations);                            // Read from kilo_bytew_2.txt
					sum_rndm_read_1024=sum_rndm_read_1024+duration1;                          
					random_read("mega_bytew_2.txt",mb_iterations);						      // Read from mega_bytew_2.txt
					sum_rndm_read_21024=sum_rndm_read_21024+duration1;
					//System.out.println("Don2 is complete");
				}
			}										
		}
		catch(Exception e)
		{
			System.out.println("Error In Run"+e);
		}
	}	
	void Sequential_read(String f_name,int iteration) 								// This method takes file name as input and reads data from file.
	{
			File server_address=new File(f_name);           
			FileReader fr;
			BufferedReader br;
			String rline;
			File sequential_kilo_byte=new File(f_name);
			try
			{
				if(!server_address.exists()) 							// Check if file exist or not
				{
					System.out.println("File does not exist !!!");
				}
				else											
				{
					InputStream insputStream = new FileInputStream(sequential_kilo_byte);	
					int blockSize=(size*1000*1000)/iteration;
					int offset=0;
					long length = sequential_kilo_byte.length();
					byte[] bytes = new byte[blockSize];
					startTime = System.nanoTime();									// Start the timer .
		
					for(int i=0;i<iteration;i++)
					{											
						insputStream.read(bytes, offset,(int)((length-offset)>blockSize?blockSize:(length-offset)));
						offset=offset+blockSize;
					}
					duration1 = System.nanoTime() - startTime;			// Stop timer and compute the duration.
					//System.out.println("Time taken for sequential read operations of "+len+" bytes (Nanoseconds) "+duration1);  					
					//br.close();     									// Clode Buffered reader object.
				}
			}
			catch(Exception e)
			{
			}
	}
	String randomString( int len )										/// This method takes length as input and crates random string of that length.
	{																	// send it back to caller.
		final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder( len );	
		for( int i = 0; i < len; i++ ) 
		sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );				
		return sb.toString();
	}
	void write_file(int len,String fname,int iteration)					// This method takes length, file name and no of operatiosn to perform as parameter.
	{																	// and keep on writing the data of given packet size to file.
		try
		{
		int ARRAY_LENGTH = 1;
		byte[] byteArray;
		String rline;
		File sequential_kilo_byte;
		PrintWriter writer;
		sequential_kilo_byte=new File(fname);			
		writer = new PrintWriter(sequential_kilo_byte);
		writer.print("");
		writer.close();
		//HashMap<Integer,String> cache = new HashMap<Integer,String>();  // Create HashMap object to store random string to save time while writing to the file.
		//for(int i=0;i<iteration;i++)
		//{
			String main_string =randomString(len);								// put random generated string to hashmap.
			//	System.out.println(cache.get(i));
		//}
		if(!sequential_kilo_byte.exists())								// Check if file exists if not exist then create.
		{
    		sequential_kilo_byte.createNewFile();
    	}
		//FileWriter fileWritter = new FileWriter(sequential_kilo_byte.getName(),true);   
    	//BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
		//InputStream insputStream = new FileInputStream(sequential_kilo_byte);	
		int blockSize=(size*1000*1000)/iteration;
		//int offset=0;
		//int length = sequential_kilo_byte.length();
		//te[] bytes = new byte[blockSize];
		BufferedOutputStream bos = null;
			FileOutputStream fos = new FileOutputStream(new File(fname));
			bos = new BufferedOutputStream(fos);			
		byte[] bytez=new byte[blockSize];
		bytez=main_string.getBytes();
		startTime = System.nanoTime();									// Start the timer .
		
		for(int i=0;i<iteration;i++)
		{											
    	  bos.write(bytez);								
		}
		duration1 = System.nanoTime() - startTime;						// Stop timer and compute the time reqired.
		//System.out.println("Time taken for "+iteration+" sequential write operations of "+len+" bytes (Nanoseconds) "+duration1);		
		//bufferWritter.close();			
		}		
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
		
	void random_read(String fname,int iteration)        // This function takes file name and no of iterations to perform as parameter
	{	
		String record = null; 							
		Random r=new Random();
		byte[] main_string_byte=new byte[(size*1000*1000)/iteration];
		int[] random_number= new int[iteration];	
		int position;	
		try 	
		{ 
			RandomAccessFile fileStore = new RandomAccessFile(fname, "rw"); 		//create random accessfile object.
			// moves file pointer to position specified 		
			for(int k=0;k<iteration;k++)											// strore random positions in random_number array.
			{
				random_number[k]=r.nextInt(iteration);
			}
			startTime = System.nanoTime();											// start the timer 
			for(int i=0;i<iteration;i++)
			{	
				fileStore.seek(r.nextInt(size*1000*1000));
				fileStore.read(main_string_byte); 				
				
							 
				//record = fileStore.readLine();
				//System.out.println(record);
				//position=random_number[i];
				//fileStore.skipBytes(position*len); 
			}
			duration1 = System.nanoTime() - startTime;								// Stop the timer and compute the duration			
			fileStore.close();													//	close file.
			//System.out.println("I am at random read");
		} 
		catch (IOException e) 
		{ 
			e.printStackTrace(); 
		} 
		//return record; 
	}
	void random_write(int len,String fname,int iteration)				// This function takes packet size , file name and no of iterations as parameter.
	{
		int position;
		Random r=new Random();	
		long fileLength; 
		int[] random_number=new int[iteration];
		//System.out.println("File Length is "+fileLength);
		try 
		{
			//HashMap<Integer,String> cache = new HashMap<Integer,String>();		// Create HashMap to store random generated strings.
			//for(int i=0;i<iteration;i++)
			//{
				String main_string =randomString(len);									// copy random strigs of given length to HashMap.
				byte[] main_string_byte=main_string.getBytes();
				//	System.out.println(cache.get(i));
			//}
			File file = new File(fname);										// Open file.
			int pointer;
			file.delete();												
			RandomAccessFile fileStore = new RandomAccessFile(fname, "rw"); 
			// moves file pointer to position specified 
			for(int k=0;k<iteration;k++)										// Store positions in array.
			{
				random_number[k]=r.nextInt(iteration);			
			}
			startTime = System.nanoTime();										// Start timer
			for(int j=0;j<iteration;j++)
			{								 
				fileStore.seek(r.nextInt(size*1000*1000));
				fileStore.write(main_string_byte);	
				
				//writing String to RandomAccessFile 
				//fileStore.writeBytes(main_string+'\n'); 						// Write data to file.
				//fileStore.newLine();
				//position=random_number[j];										// get new position.
				//System.out.println("Position"+position);
				//fileStore.skipBytes(position*len); 								// Skip bytes to read next byte.
			}
			duration1 = System.nanoTime() - startTime;							// Stop timer and compute duration.			
			fileStore.close(); 													// close file.	
		} 
		catch (IOException e) 
		{ 
			e.printStackTrace(); 
		} 
	}
	public static void main(String args[]) 										
	{	
		int No_Of_Threads=0;
		int size=20;
		int iteration=0;
		BufferedReader sc_input=new BufferedReader(new InputStreamReader(System.in)); 
		Thread t1,t2;
		try
		{		
			System.out.println("Enter the no of threads to create:-");      		// Take the no if threads to create input from user.
			No_Of_Threads=Integer.parseInt(sc_input.readLine());                     
			
			System.out.println("Enter the size of operation to perform (In MB):-");   // Take size of operation from user in MB.
			size=Integer.parseInt(sc_input.readLine());
			
			if(No_Of_Threads==1)														// if thread 1 
			{
				t1=new Disk(No_Of_Threads,"Thread1",size);								// create thread object. 
				t1.start();																// start thhread and call run method
				Thread[] threads= {t1};
				for (Thread thread : threads) 		
				{
					thread.join();														// wait till finish its execution.
				}
				//System.out.println("Don is here");
			}
			else if (No_Of_Threads==2)									
			{
				t1=new Disk(No_Of_Threads,"Thread1",size);								// create thread object. 
				t1.start();
				t2=new Disk(No_Of_Threads,"Thread2",size);								// create thread object. 
				t2.start();
				Thread[] threads= {t1,t2};
				for (Thread thread : threads) 
				{
					thread.join();														// wait till finish its execution.
				}
				//System.out.println("Don is here");
			}	
			Disk d1=new Disk();															//create Disk object 										
			d1.display(No_Of_Threads,size);												// call display method.
		}
		catch(Exception e)
		{
			System.out.println(e);
		}		
	}
public void display(int No_Of_Threads,int size) // This method is responsibile for computing and displayimg throughput and latency.
{
					//System.out.println("Before final sum :-"+sum_seq_write_final+"   sum_rndm_read_1 "+sum_seq_write_1);
					sum_rndm_read_final=sum_rndm_read_final+sum_rndm_read_1;
					
					sum_rndm_write_final=sum_rndm_write_final+sum_rndm_write_1;
					sum_seq_read_final=sum_seq_read_final+sum_seq_read_1;
					sum_seq_write_final=sum_seq_write_final+sum_seq_write_1;
					//System.out.println("final sum :-"+sum_seq_write_final);
					
					//System.out.println("All below timing is in milliseconds");
					System.out.println();
					System.out.println("Random Read 1Byte Speed MB/Sec "+((No_Of_Threads*1*size)/(sum_rndm_read_final/1000000000))+" MB/Sec"+" Latency "+((sum_rndm_read_final/1000000)/(No_Of_Threads*1*size))+" Milliseconds");
					System.out.println("Random  Write 1Byte Speed MB/Sec "+((No_Of_Threads*1*size)/(sum_rndm_write_final/1000000000))+" MB/Sec"+" Latency "+((sum_rndm_write_final/1000000)/(No_Of_Threads*1*size))+" Milliseconds");
					System.out.println("Sequential Read 1Byte Speed MB/Sec "+((No_Of_Threads*1*size)/(sum_seq_read_final/1000000000))+" MB/Sec"+" Latency "+((sum_seq_read_final/1000000)/(No_Of_Threads*1*size))+" Milliseconds");
					System.out.println("Sequential Write 1Byte Speed MB/Sec "+((No_Of_Threads*1*size)/(sum_seq_write_final/1000000000))+" MB/Sec"+" Latency "+((sum_seq_write_final/1000000)/(No_Of_Threads*1*size))+" Milliseconds");
					sum_rndm_read_final=sum_rndm_write_final=sum_seq_read_final=sum_seq_write_final=0.0;
					
					sum_rndm_read_final=sum_rndm_read_final+sum_rndm_read_1024;
					sum_rndm_write_final=sum_rndm_write_final+sum_rndm_write_1024;
					sum_seq_read_final=sum_seq_read_final+sum_seq_read_1024;					
					sum_seq_write_final=sum_seq_write_final+sum_seq_write_1024;
					//System.out.println("sum_seq_write_final "+sum_seq_write_final+"sum_seq_write_1024 "+sum_seq_write_1024);
					System.out.println("");
					System.out.println("Random Read 1KB Speed MB/Sec "+((No_Of_Threads*size)/(sum_rndm_read_final/1000000000))+" MB/Sec"+" Latency "+((sum_rndm_read_final/1000000)/(No_Of_Threads*1*size))+" Milliseconds");
					System.out.println("Random Write 1KB Speed MB/Sec "+((No_Of_Threads*size)/(sum_rndm_write_final/1000000000))+" MB/Sec"+" Latency "+((sum_rndm_write_final/1000000)/(No_Of_Threads*1*size))+" Milliseconds");
					System.out.println("Sequential Read 1KB Speed MB/Sec "+((No_Of_Threads*size)/(sum_seq_read_final/1000000000))+" MB/Sec"+" Latency "+((sum_seq_read_final/1000000)/(No_Of_Threads*1*size))+" Milliseconds");
					System.out.println("Sequential Write 1KB Speed MB/Sec "+((No_Of_Threads*size)/(sum_seq_write_final/1000000000))+" MB/Sec"+" Latency "+((sum_seq_write_final/1000000)/(No_Of_Threads*1*size))+" Milliseconds");
					sum_rndm_read_final=sum_rndm_write_final=sum_seq_read_final=sum_seq_write_final=0.0;
					
					sum_rndm_read_final=sum_rndm_read_final+sum_rndm_read_21024;
					sum_rndm_write_final=sum_rndm_write_final+sum_rndm_write_21024;
					sum_seq_read_final=sum_seq_read_final+sum_seq_read_21024;
					sum_seq_write_final=sum_seq_write_final+sum_seq_write_21024;
					System.out.println("");
					System.out.println("Random Read 1MB Speed MB/Sec "+((No_Of_Threads*size)/(sum_rndm_read_final/1000000000))+" MB/Sec"+" Latency "+((sum_rndm_read_final/1000000)/(No_Of_Threads*1*size))+" Milliseconds");
					System.out.println("Random  Write 1MB Speed MB/Sec "+((No_Of_Threads*size)/(sum_rndm_write_final/1000000000))+" MB/Sec"+" Latency "+((sum_rndm_write_final/1000000)/(No_Of_Threads*1*size))+" Milliseconds");
					System.out.println("Sequential Read 1MB Speed MB/Sec "+((No_Of_Threads*size)/(sum_seq_read_final/1000000000))+" MB/Sec"+" Latency "+((sum_seq_read_final/1000000)/(No_Of_Threads*1*size))+" Milliseconds");
					System.out.println("Sequential Write 1MB Speed MB/Sec "+((No_Of_Threads*size)/(sum_seq_write_final/1000000000))+" MB/Sec"+" Latency "+((sum_seq_write_final/1000000)/(No_Of_Threads*1*size))+" Milliseconds");
}
}
