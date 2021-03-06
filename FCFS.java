// TITLE: 					Assignment1
// COURSE: 					COMP2240
// AUTHOR: 					Moosa Hassan
// STUDENT NUMBER: 			3331532
// DATE: 					13/09/2020 
// DESCRIPTION: 			Calculations and printing results for First Comes First Serve algorithm. Contains most comparable classes too.

// importing java packages
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FCFS
{
    // private attributes
    private double twt;   // total waiting time
    private double tta;   // total turn aroudn time
    private final ArrayList<Process> FCFSList; // cloned arraylist

    // constructor
    public FCFS() 
    {
        this.twt = 0;
        this.tta = 0;
        this.FCFSList = new ArrayList<Process>();
    }

    // methods
    public void feedProcess(final ArrayList<Process> ogList, final int dTime) 
    {
        // deep element arraylist cloning
        for (int i = 0; i < ogList.size(); i++) 
        {
            this.FCFSList.add(ogList.get(i));
        }

        // sorting according to arrival times
        Collections.sort(FCFSList, new sortByArrival());

        // finding completion times
        for (int i = 0; i < FCFSList.size(); i++) 
        {
            // first iteration
            if (i == 0) 
            {
                FCFSList.get(i).setStartsAt(dTime);
                FCFSList.get(i).setCompletion(dTime + FCFSList.get(i).getArrive() + FCFSList.get(i).getExecution());
            } else 
            {
                // arrival time > completion time
                if (FCFSList.get(i).getArrive() > FCFSList.get(i - 1).getCompletion()) 
                {
                    FCFSList.get(i).setStartsAt(FCFSList.get(i - 1).getCompletion() + dTime);
                    FCFSList.get(i).setCompletion(dTime + FCFSList.get(i).getArrive() + FCFSList.get(i).getExecution());
                }
                // arrival time < completion time
                else 
                {
                    FCFSList.get(i).setStartsAt(FCFSList.get(i - 1).getCompletion() + dTime);
                    FCFSList.get(i).setCompletion(
                            dTime + FCFSList.get(i - 1).getCompletion() + FCFSList.get(i).getExecution());
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

    // process id | turnaround time | waiting time
    public void report() 
    {
        Collections.sort(FCFSList, new sortByProcessId());
        System.out.println("Process\tTurnaround Time\tWaiting Time");
        for (int i = 0; i < FCFSList.size(); i++) 
        {
            System.out.println(FCFSList.get(i).getId() + "\t" + FCFSList.get(i).getTurnAround() + "\t\t" + FCFSList.get(i).getWaiting());
        }
        System.out.println();
    }

    // start time | process id | process priority
    public void results() {
        Collections.sort(FCFSList, new sortByStartsAt());
        for (int i = 0; i < FCFSList.size(); i++) 
        {
            System.out.println("T" + FCFSList.get(i).getStartsAt() + ": " + FCFSList.get(i).getId() + "("
                    + FCFSList.get(i).getPriority() + ")");
        }
        System.out.println();
    }

    // calculating averages
    public double getAverageWaitingTime() 
    {
        return this.twt / this.FCFSList.size();
    }
    public double getAverageTurnaroundTime() 
    {
        return this.tta / this.FCFSList.size();
    }
}

// sorting in accordance to arrival
class sortByArrival implements Comparator<Process> 
{
    public int compare(final Process o1, final Process o2) 
    {
        return o1.getArrive() - o2.getArrive();
    }
}

// sorting in accordance to process id
class sortByProcessId implements Comparator<Process> 
{
    public int compare(final Process o1, final Process o2) 
    {
        return o1.getProcessNumber() - o2.getProcessNumber();
    }
}

// sorting in accordance to waiting
class sortByWaiting implements Comparator<Process> 
{
    public int compare(final Process o1, final Process o2) 
    {
        return o1.getWaiting() - o2.getWaiting();
    }
}

// sorting in accordance to starts at time
class sortByStartsAt implements Comparator<Process> 
{
    public int compare(final Process o1, final Process o2)
    {
        return o1.getStartsAt() - o2.getStartsAt();
    }
}