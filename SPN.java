import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SPN {
    // attributes
    private double twt;   // total waiting time
    private double tta;   // total turn aroudn time

    private int listSize;                   // arraylist size
    private int dispatchTime;
    private ArrayList<Process> FCFSList;

    // constructor
    public SPN() {
        this.listSize = 0;
        this.dispatchTime = 0;
        this.twt = 0;
        this.tta = 0;
    }

    // method
    public void feedProcess(ArrayList<Process> list, int dTime)
    {
        this.FCFSList = list;
        this.dispatchTime = dTime;
        this.listSize = list.size();

        // sort in order of arrival
        sortArrive(FCFSList);
        // sort in order of execution
        sortExecution(FCFSList);

        /*
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
                // arrival time > completion time
                if(FCFSList.get(i).getArrive() > FCFSList.get(i-1).getCompletion())
                {
                    FCFSList.get(i).setCompletion(dispatchTime + FCFSList.get(i).getArrive() + FCFSList.get(i).getExecution());
                }
                // arrival time < completion time
                else
                {
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
        */
    }

    public void report()
    {
        // OFFICIAL OUTPUT
        Collections.sort(FCFSList, new sortByProcessId());

        // printing results
        System.out.println("Process\tTurnaround Time\tWaiting Time");
        for(int  i = 0 ; i< listSize;  i++)
		{
			System.out.println(FCFSList.get(i).getId() + "\t" + FCFSList.get(i).getTurnAround() + "\t\t" + FCFSList.get(i).getWaiting() ) ;
        }

        System.out.println();
    }

    public void results()
    {
        for(int i = 0; i < listSize; i++)
        {
            System.out.println("T" + FCFSList.get(i).getWaiting() + ": " + FCFSList.get(i).getId() + "(" + FCFSList.get(i).getPriority() + ")");
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

    public ArrayList<Process> sortArrive(ArrayList<Process> processList)
    {
        Collections.sort(processList, Comparator.comparing(Process::getArrive)
            .thenComparing(Process::getArrive));
        return processList;
    }

    public ArrayList<Process> sortExecution(ArrayList<Process> processList)
    {
        Collections.sort(processList, Comparator.comparing(Process::getExecution)
            .thenComparing(Process::getExecution));
        return processList;
    }

    //HELPFUL VIDEO
    //https://www.youtube.com/watch?v=e1nee-bE5FA

    /*
        SPN:
        T1: p2(2)
        T3: p4(1)
        T5: p3(4)
        T8: p5(5)
        T14: p1(0)

        Process Turnaround Time Waiting Time
        p1      24              14
        p2      2               1
        p3      7               5
        p4      4               3
        p5      13              8
    */
}