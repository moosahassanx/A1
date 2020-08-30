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
    public void feedProcess(ArrayList<Process> ogList, int dTime)
    {
        this.SPNList = new ArrayList<Process>();
        for(int i = 0; i < ogList.size(); i++)
        {
            this.SPNList.add(ogList.get(i));
        }

        this.dispatchTime = dTime;
        this.listSize = SPNList.size();

        // sort in order of arrival
        sortArrive(SPNList);

        // select the process with the least arrival time AND least burst time
        sortExecution(SPNList);

        // calculate the process completion time, waiting time, turn around time
        SPNList.get(0).setCompletion( SPNList.get(0).getExecution() );
        SPNList.get(0).setTurnAround( SPNList.get(0).getCompletion() - SPNList.get(0).getArrive() );
        SPNList.get(0).setWaiting( SPNList.get(0).getTurnAround() - SPNList.get(0).getExecution() );
        SPNList.get(0).setFlag(true);

        // after this, make a pool of all the processes which after till the completion of previous process
        

        // in that pool, select the process with the least burst time... repeat

        /*
        // sort in order of execution
        sortExecution(SPNList);

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
        T1: p1(4)
        T12: p2(2)
        T14: p4(3)
        T16: p3(3)
        T19: p5(1)

        Process Turnaround Time Waiting Time
        p1      11              1
        p2      11              10
        p3      12              10
        p4      5               4
        p5      10              5
    */
}