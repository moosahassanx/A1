import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FCFS
{
    // private attributes
    private int pid[];                  // process ids
    private int ar[];                   // arrival times
    private int et[];                   // execution times
    private int ct[];                   // completion times
    private int ta[];                   // turn around times
    private int wt[];                   // waiting times

    private double twt = 0;   // total waiting time
    private double tta = 0;   // total turn aroudn time

    private int listSize;
    private int dispatchTime;
    private ArrayList<Process> FCFSList;

    // constructor
    public FCFS()
    {
        listSize = 0;
    }

    // methods
    public void feedProcess(ArrayList<Process> list, int t)
    {
        this.FCFSList = list;
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
        Collections.sort(FCFSList, new sortByArrival());

        // sorting according to arrival times OLD
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

        // finding completion times
        for(int i = 0; i < FCFSList.size(); i++)
        {
            // first iteration
            if(i == 0)
            {
                FCFSList.get(i).setCompletion(dispatchTime + FCFSList.get(i).getArrive() + FCFSList.get(i).getExecution());
            }
            else
            {
                if(FCFSList.get(i).getArrive() > FCFSList.get(i).getCompletion())
                {
                    FCFSList.get(i).setCompletion(c);
                }
            }
        }
        
        // finding completion times OLD
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
			twt += wt[i] ;              // total waiting time
			tta += ta[i] ;              // total turnaround time
        }

    }

    // print the results
    public void report()
    {
        // OFFICIAL OUTPUT
        System.out.println("Process\tTurnaround Time\tWaiting Time");
        for(int  i = 0 ; i< listSize;  i++)
		{
			System.out.println("p" + pid[i] + "\t" + ta[i] + "\t\t" + wt[i] ) ;
        }

        System.out.println();
    }

    public void results()
    {
        for(int i = 0; i < listSize; i++)
        {
            System.out.println("T" + wt[i] + ": " + FCFSList.get(i).getId() + "(" + FCFSList.get(i).getPriority() + ")");
        }

        System.out.println();
    }

    public double getAverageWaitingTime()
    {
        return this.twt / listSize;
    }

    public double getAverageTurnaroundTime()
    {
        return this.tta / listSize;
    }

}

// sorting in accordance to arrival
class sortByArrival implements Comparator<Process>
{
    public int compare(Process o1, Process o2) {
        return o1.getArrive() - o2.getArrive();
    }

}