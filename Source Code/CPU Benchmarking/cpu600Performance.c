#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <string.h>
double OPERATION_COUNT=1000000000;
static int fCount=0;
void * cpuPerformance();
static int ip=0;
main()
{
    int choice,i;
    double timer;
    void *res;
    printf("Perform CPU Benchmarking on \n1. 1 Thread\n2. 2 Thread\n3. 4 Thread : ");
    scanf("%d",&choice);
 
   printf("\nTotal  operations to be performed are %lf\n",OPERATION_COUNT);   

  if(choice>0&&choice<4){
    	pthread_t thread[choice];	
	if(choice==3)
		choice=4;
        double *val=(double *)malloc(sizeof(double)*choice*2);
	for(i=0;i<choice;i++){
		pthread_create(&thread[i], NULL, cpuPerformance,(void *) (val+(i*2)));			
	}
	for(i=0;i<choice;i++)
	        pthread_join(thread[i], NULL);

	double averageFLOPstimer=0,averageIOPStimer=0;
	for(i=0;i<choice;i++)
        {
	        printf("\nThread %d => FLOPS: %f",i,*(val+(i*2)));
	        printf(", IOPS: %f",*(val+(i*2)+1));
			
		averageFLOPstimer=averageFLOPstimer+*(val+(i*2));
		averageIOPStimer=averageIOPStimer+*(val+(i*2)+1);
	}
	averageFLOPstimer=averageFLOPstimer/choice;
	averageIOPStimer=averageIOPStimer/choice;



	//Printing Values for FLOPS and Giga FLOPS
	printf("\n\nFloating point operations per second (FLOPS) performed are   : %lf ", (averageFLOPstimer/OPERATION_COUNT));
	double number_of_FLOPS = OPERATION_COUNT / averageFLOPstimer;
	printf("\n FLOPS performed per sec      : %lf",number_of_FLOPS);
	double number_of_GFLOPS = number_of_FLOPS / 1000000000 ;
	printf("\n Giga FLOPS performed per sec : %.10lf\n",number_of_GFLOPS);

	printf("\nInteger operations per second (IOPS) performed are          : %f ", (averageIOPStimer/OPERATION_COUNT));
	double number_of_IOPS = OPERATION_COUNT / averageIOPStimer;
	printf("\n IOPS performed per sec      : %f",number_of_IOPS);
	double number_of_GIOPS = number_of_IOPS / 1000000000 ;
	printf("\n Giga IOPS performed per sec : %.10lf",number_of_GIOPS);



    }else
    {
     	printf("Invalid choice...!!!");
    }
     exit(EXIT_SUCCESS);
}

void* cpuPerformance(void *t){
	double *para;
	clock_t startflops_timer,flops_timer,iops_timer,startiops_timer;        
	double i=0;
	double itimer,ftimer,sum=0.0,sum2=0.0,sum3=0.0;
	double isum=0,isum2=0,isum3=0;	
	int flopsData[600],iopsData[600];
        FILE *fp;
	para=(double*)t;
	startflops_timer = clock();
	flops_timer=clock();
	int timer=1;int previous=0;

	for(i=0;i<OPERATION_COUNT&&timer<600;i++){
		sum=(sum3+1.99)*(1.90+sum3);
		sum2=sum2*sum3*100000;
		sum3=sum*sum2*sum3;
		if(timer<600){
			if(((double)(clock()-flops_timer)/(double)CLOCKS_PER_SEC)>=1){
				flopsData[timer-1]=i;
	
				timer++;
				flops_timer = clock();
				previous=i;
			}
		}
	}


	ftimer=(double)(clock()-startflops_timer)/(double)CLOCKS_PER_SEC;
	(*para)=ftimer;


	timer=1;previous=0;
	startiops_timer = clock();
	iops_timer= clock();
	for(i=0;i<OPERATION_COUNT&&timer<600;i++){
		isum=(isum3+1.99)*(1.90+isum3);
		isum2=isum2*isum3*100000;
		isum3=isum*isum2*isum3;
		if(timer<600){
			if(((double)(clock()-iops_timer)/(double)CLOCKS_PER_SEC)>=1){
				
				iopsData[timer-1]=i;
				timer++;
				iops_timer = clock();
				previous=i;				
			}
		}

	}
	itimer=(double)(clock()-startiops_timer)/(double)CLOCKS_PER_SEC;
	(*(para+1))=itimer;
//	printf("\tMAIN %d FLOPS timer: %f IOPS timer: %f\n",ip,ftimer,itimer);
	ip++;

	char filename1[10],filename2[10];
	switch(fCount){
		case 0: strcpy(filename1,"flops1.txt");
			strcpy(filename2,"iops1.txt");
			break;
		case 1:strcpy(filename1,"flops2.txt");
			strcpy(filename2,"iops2.txt");
			break;
		case 2:strcpy(filename1,"flops3.txt");
			strcpy(filename2,"iosps3.txt");
			break;
		case 3:strcpy(filename1,"flops4.txt");
			strcpy(filename2,"iops4.txt");
			break;
		default: printf("Error");
			break;
	}
	fCount++;
	
	  if((fp=fopen(filename1, "ab+"))==NULL) {
	    printf("Cannot open file.\n");
	  }
	   int digit;
	   for (digit = 0; digit < timer-1; ++digit){
		char arr[sizeof(flopsData[digit])];
		snprintf(arr, 12, "%d", flopsData[digit]);
	        fputs(arr,fp);
	        fputs("\n",fp);

	   }
	 fclose(fp);


	  if((fp=fopen(filename2, "ab+"))==NULL) {
	    printf("Cannot open file.\n");
	  }
	   for (digit = 0; digit < timer-1; ++digit)
	   {
		char arr[sizeof(iopsData[digit])];
		snprintf(arr, 12, "%d", iopsData[digit]);
	        fputs(arr,fp);
	        fputs("\n",fp);
	   }
	 fclose(fp);



	return NULL;
}

