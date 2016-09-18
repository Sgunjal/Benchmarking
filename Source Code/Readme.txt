					CS 553 Cloud Computing
					Programming Assignment 1
					Sujay Gunjal (CWID: A20351746)
						Manual
Follow below steps to run program:-
==================================================================

1)	CPU Benchmarking:-
	Below are the steps to execute the program.
	1)	gcc -pthread -o term cpuPerformance.c
	2)	execute term file using below command
		./term

		When you execute the above file you will get below menu.
		Perform CPU Benchmarking on 
		1. 1 Thread
		2. 2 Thread
		3. 4 Thread : 1
	3) Select your choice by entering options.
==================================================================
2)	Disk Benchmarking:-
	1)	Below are the steps to execute the program.
		a.	Javac Disk.java
		b.	Java Disk

	2)	 User will be asked to enter number of threads to create and size of data (in MB) to write and read from file in MB. 

	3)	This will execute the random read, Random write , Sequential write and sequential read operation one after another and will display throughput and latency.

===================================================================
3)	Network Benchmarking:-
1)	Open Server_list.txt file and add the host ip address and port separated by pipe (|) without any extra space in between. 
	For example 192.168.2.153|25003
	Here 192.168.2.153 is host ip address and 25003 is port number.
2)	Open the server and separate terminals or on separate machines and execute the below commands to perform TCP and UDP packet transfer operations.

3)	TCP packet transfer 
	•Server
	i.	Javac Network_Server_TCP.java
	ii.	Java Network_Server_TCP

	•Client
	i.	Javac Network_Client_TCP.java
	ii.	Java Network_Client_TCP


4)	UDP packet transfer 
	•Server
		iii.	Javac Network_Server_UDP.java
		iv.	Java Network_Server_UDP
	•Client
		v.	Javac Network_Client_UDP.java
		vi.	Java Network_Client_UDP

5)	For both UDP and TCP network operations user will be asked to enter number of threads to create and size of data (in MB) to transfer. 
