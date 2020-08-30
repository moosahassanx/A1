import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SPN {
    // attributes
    private double twt;   // total waiting time
    private double tta;   // total turn aroudn time

    private int listSize;                   // arraylist size
    private int dispatchTime;
    private ArrayList<Process> SPNList;

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
        this.SPNList = list;
        this.dispatchTime = dTime;
        this.listSize = list.size();

        // clearing previous data
        for(int i = 0; i < SPNList.size(); i++)
        {
            SPNList.get(i).clearCalculations();
        }

        // sort in order of arrival
        sortArrive(SPNList);
        // sort in order of execution
        sortExecution(SPNList);

        /*
        // finding completion times
        for(int i = 0; i < SPNList.size(); i++)
        {
            // first iteration
            if(i == 0)
            {
                SPNList.get(i).setCompletion(dispatchTime + SPNList.get(i).getArrive() + SPNList.get(i).getExecution());
            }
            else
            {
                // arrival time > completion time
                if(SPNList.get(i).getArrive() > SPNList.get(i-1).getCompletion())
                {
                    SPNList.get(i).setCompletion(dispatchTime + SPNList.get(i).getArrive() + SPNList.get(i).getExecution());
                }
                // arrival time < completion time
                else
                {
                    SPNList.get(i).setCompletion(dispatchTime + SPNList.get(i-1).getCompletion() + SPNList.get(i).getExecution());
                }
            }
            

            // calculating turnaround time and waiting time
            SPNList.get(i).setTurnAround(SPNList.get(i).getCompletion() - SPNList.get(i).getArrive());
            SPNList.get(i).setWaiting(SPNList.get(i).getTurnAround() - SPNList.get(i).getExecution());
            
            // accumulating results into total turnaround time and total waiting time
            tta += SPNList.get(i).getTurnAround();
            twt += SPNList.get(i).getWaiting();
            
        }
        */
        
    }

    public void report()
    {
        // OFFICIAL OUTPUT
        Collections.sort(SPNList, new sortByProcessId());

        // printing results
        System.out.println("Process\tTurnaround Time\tWaiting Time");
        for(int  i = 0 ; i< listSize;  i++)
		{
			System.out.println(SPNList.get(i).getId() + "\t" + SPNList.get(i).getTurnAround() + "\t\t" + SPNList.get(i).getWaiting() ) ;
        }

        System.out.println();
    }

    public void results()
    {
        for(int i = 0; i < listSize; i++)
        {
            System.out.println("T" + SPNList.get(i).getWaiting() + ": " + SPNList.get(i).getId() + "(" + SPNList.get(i).getPriority() + ")");
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