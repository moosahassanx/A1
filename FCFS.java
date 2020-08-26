import java.util.ArrayList;

public class FCFS
{
    // private attributes
    private int pid[];                  // process ids
    private int ar[];                   // arrival times
    private int et[];                   // execution times
    private int ct[];                   // completion times
    private int ta[];                   // turn around times
    private int wt[];                   // waiting times

    private double avgwt = 0;   // average waiting time
    private double avgta = 0;   // average turn aroudn time

    private int listSize;

    private int dispatchTime;

    // constructor
    public FCFS()
    {
        listSize = 0;
    }

    // methods
    public void feedProcess(ArrayList<Process> list, int t)
    {
        this.dispatchTime = t;
        this.listSize = list.size();

        pid = new int[list.size()];
        ar = new int[list.size()];
        et  = new int[list.size()];
        ct = new int[list.size()];
        ta = new int[list.size()];
        wt = new int[list.size()];
        
        int temp;

        // setting process Ids
        for(int i = 0; i < list.size(); i++)
        {
            pid[i] = i+1;
        }

        // setting arrival time and burst times
        for(int i = 0; i < list.size(); i++)
        {
            ar[i] = list.get(i).getArrive();
            et[i] = list.get(i).getExecution();
        }

        // sorting according to arrival times
        for(int i = 0 ; i <list.size(); i++)
		{
			for(int  j=0;  j < list.size()-(i+1) ; j++)
			{
				if( ar[j] > ar[j+1] )
				{
					temp = ar[j];
					ar[j] = ar[j+1];
					ar[j+1] = temp;
					temp = et[j];
					et[j] = et[j+1];
					et[j+1] = temp;
					temp = pid[j];
					pid[j] = pid[j+1];
					pid[j+1] = temp;
				}
			}
        }
        
        // finding completion times --> THE SOURCE OF THE PROBLEM IDK
        for(int  i = 0 ; i < list.size(); i++)
		{
            // first iteration
			if( i == 0)
			{
				ct[i] = dispatchTime + ar[i] + et[i];
            }
            
			else
			{
                // arrival time > completion time
				if( ar[i] > ct[i-1])
				{
					ct[i] = dispatchTime + ar[i] + et[i];
                }
                
                // arrival time < completion time
                else
                {
                    ct[i] = dispatchTime + ct[i-1] + et[i];
                }
            }
            
			ta[i] = ct[i] - ar[i] ;     // turnaround time = (completion time) - (arrival time)
			wt[i] = ta[i] - et[i] ;     // waiting time= (turnaround time) - (burst time)
			avgwt += wt[i] ;            // total waiting time
			avgta += ta[i] ;            // total turnaround time
        }

    }

    // print the results
    public void report()
    {
        System.out.println("pid \tarrival \texecution \tcomplete \tturn \twaiting");

		for(int  i = 0 ; i< listSize;  i++)
		{
			System.out.println("p" + pid[i] + "  \t" + ar[i] + "\t\t" + et[i] + "\t\t" + ct[i] + "\t\t" + ta[i] + "\t"  + wt[i] ) ;
        }
        
		System.out.println("\naverage waiting time: "+ (avgwt / listSize));        // printing average waiting time.
        System.out.println("average turnaround time:"+(avgta / listSize));         // printing average turnaround time.
        
        System.out.println();
	}

}