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
    int ch,i;
    double timer;
    void *res;
    printf("Perform CPU Benchmarking on \n1. 1 Thread\n2. 2 Thread\n3. 4 Thread : ");
    scanf("%d",&ch);
 
   printf("\nTotal  operations to be performed are %lf\n",OPERATION_COUNT);   

  if(ch>0&&ch<4){
    	pthread_t thread[ch];	
	if(ch==3)
		ch=4;
        double *val=(double *)malloc(sizeof(double)*ch*2);
	for(i=0;i<ch;i++){
		pthread_create(&thread[i], NULL, cpuPerformance,(void *) (val+(i*2)));			
	}
	for(i=0;i<ch;i++)
	        pthread_join(thread[i], NULL);

	double avgFLOPstimer=0,averageIOPStimer=0;
	for(i=0;i<ch;i++)
        {
	        printf("\nThread %d => FLOPS: %f",i,*(val+(i*2)));
	        printf(", IOPS: %f",*(val+(i*2)+1));
			
		avgFLOPstimer=avgFLOPstimer+*(val+(i*2));
		averageIOPStimer=averageIOPStimer+*(val+(i*2)+1);
	}
	avgFLOPstimer=avgFLOPstimer/ch;
	averageIOPStimer=averageIOPStimer/ch;

	//Printing Values for FLOPS and Giga FLOPS
	printf("\n\nFloating point operations per second (FLOPS) performed are : %lf ", (avgFLOPstimer));
	double number_of_FLOPS = OPERATION_COUNT / avgFLOPstimer;
	printf("\n FLOPS performed per sec      : %lf",number_of_FLOPS);
	double number_of_GFLOPS = number_of_FLOPS / 1000000000 ;
	printf("\n Giga FLOPS performed per sec : %.10lf\n",number_of_GFLOPS);

	printf("\nInteger operations per second (IOPS) performed are : %f ", (averageIOPStimer));
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
	clock_t startflops_timer,startiops_timer;        
	double i=0;
	double itimer,ftimer,sum=0.0,sum2=0.0,sum3=0.0;
	double isum=0,isum2=0,isum3=0;	
        FILE *fp;
	para=(double*)t;
	startflops_timer = clock();
	int timer=1;int previous=0;

	for(i=0;i<OPERATION_COUNT;i++){
		sum=(sum3+1.99)*(1.90+sum3);
		sum2=sum2*sum3*100000;
		sum3=sum*sum2*sum3;
	}
	//printf("diff timer= %lf",(double)(clock()-flops_timer)/(double)CLOCKS_PER_SEC);
	//printf("clockpersec = %lf",(double)CLOCKS_PER_SEC);

	ftimer=(double)(clock()-startflops_timer)/(double)CLOCKS_PER_SEC;
	(*para)=ftimer;
//	printf("\n%d FLOPS timer: %f",ip,ftimer);

	timer=1;previous=0;
	startiops_timer = clock();
	for(i=0;i<OPERATION_COUNT;i++){
		isum=(isum3+1.99)*(1.90+isum3);
		isum2=isum2*isum3*100000;
		isum3=isum*isum2*isum3;
	}
	itimer=(double)(clock()-startiops_timer)/(double)CLOCKS_PER_SEC;
	(*(para+1))=itimer;
//	printf("\tMAIN %d FLOPS timer: %f IOPS timer: %f\n",ip,ftimer,itimer);
	ip++;


	return NULL;
}

