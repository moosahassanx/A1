import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.SpinnerListModel;

public class FCFS
{
    // private attributes
    private double twt;   // total waiting time
    private double tta;   // total turn aroudn time

    private int listSize;                   // arraylist size
    private int dispatchTime;
    private ArrayList<Process> FCFSList;

    // constructor
    public FCFS()
    {
        this.listSize = 0;
        this.dispatchTime = 0;
        this.twt = 0;
        this.tta = 0;
    }

    // methods
    public void feedProcess(ArrayList<Process> ogList, int t)
    {
        this.FCFSList = new ArrayList<Process>();
        for(int i = 0; i < ogList.size(); i++)
        {
            this.FCFSList.add(ogList.get(i));
        }

        this.dispatchTime = t;
        this.listSize = FCFSList.size();

        // sorting according to arrival times
        Collections.sort(FCFSList, new sortByArrival());

        // finding completion times
        for(int i = 0; i < FCFSList.size(); i++)
        {
            // first iteration
            if(i == 0)
            {
                FCFSList.get(i).setStartsAt(dispatchTime);
                FCFSList.get(i).setCompletion(dispatchTime + FCFSList.get(i).getArrive() + FCFSList.get(i).getExecution());
            }
            else
            {
                // arrival time > completion time
                if(FCFSList.get(i).getArrive() > FCFSList.get(i-1).getCompletion())
                {
                    FCFSList.get(i).setStartsAt( FCFSList.get(i-1).getCompletion() + dispatchTime);
                    FCFSList.get(i).setCompletion(dispatchTime + FCFSList.get(i).getArrive() + FCFSList.get(i).getExecution());
                }
                // arrival time < completion time
                else
                {
                    FCFSList.get(i).setStartsAt( FCFSList.get(i-1).getCompletion() + dispatchTime);
                    FCFSList.get(i).setCompletion(dispatchTime + FCFSList.get(i-1).getCompletion() + FCFSList.get(i).getExecution());
                }
            }

            // calculating turnaround time and waiting time
            FCFSList.get(i).setTurnAround(FCFSList.get(i).getCompletion() - FCFSList.get(i).getArrive());
            FCFSList.get(i).setWaiting(FCFSList.get(i).getTurnAround() - FCFSList.get(i).getExecution());
            
            // accumulating results into total turnaround time and total waiting time
            tta += FCFSList.get(i).getTurnAround();
            twt += FCFSList.get(i).getWaiting();
        }
    }

    public void report()
    {
        // OFFICIAL OUTPUT
        Collections.sort(FCFSList, new sortByProcessId());

        System.out.println("Process\tTurnaround Time\tWaiting Time");
        for(int  i = 0 ; i< listSize;  i++)
		{
			System.out.println(FCFSList.get(i).getId() + "\t" + FCFSList.get(i).getTurnAround() + "\t\t" + FCFSList.get(i).getWaiting() ) ;
        }

        System.out.println();
    }

    public void results()
    {
        Collections.sort(FCFSList, new sortByStartsAt());
        for(int i = 0; i < listSize; i++)
        {
            System.out.println("T" + FCFSList.get(i).getStartsAt() + ": " + FCFSList.get(i).getId() + "(" + FCFSList.get(i).getPriority() + ")");
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
    public int compare(Process o1, Process o2) 
    {
        return o1.getArrive() - o2.getArrive();
    }

}

// sorting in accordance to process id
class sortByProcessId implements Comparator<Process>
{
    public int compare(Process o1, Process o2)
    {
        return o1.getProcessNumber() - o2.getProcessNumber();
    }
}

// sorting in accordance to waiting
class sortByWaiting implements Comparator<Process>
{
    public int compare(Process o1, Process o2)
    {
        return o1.getWaiting() - o2.getWaiting();
    }
}

// sorting in accordance to starts at time
class sortByStartsAt implements Comparator<Process>
{
    public int compare(Process o1, Process o2)
    {
        return o1.getStartsAt() - o2.getStartsAt();
    }
}