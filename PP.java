import java.util.ArrayList;
import java.util.Collections;

public class PP
{
    private ArrayList<Process> PPList;
    private int twt;        // total waiting time
    private int tta;        // total turnaround time

    public PP()
    {
        this.PPList = new ArrayList<Process>();
        this.twt = 0;
        this.tta = 0;
    }

    public void feedProcess(ArrayList<Process> ogList, int dTime)
    {
        // deep element arraylist cloning
        for(int i = 0; i < ogList.size(); i++)
        {
            this.PPList.add(ogList.get(i));
        }

        Collections.sort(PPList, new sortByArrival());

        System.out.println("id\tarrive\texec\tcomplet\tstarts\tturnAr\twaiting");
        for(int i = 0; i < PPList.size(); i++)
        {
            System.out.println(PPList.get(i).getId() + "\t" + PPList.get(i).getArrive() + "\t" + PPList.get(i).getExecution() + "\t" + PPList.get(i).getCompletion() + "\t" + PPList.get(i).getStartsAt() + "\t" + PPList.get(i).getTurnAround() + "\t" + PPList.get(i).getWaiting());
        }

        System.out.println("*********************************************************");

    }

    // start time | process id | process priority
    public void results()
    {
        Collections.sort(PPList, new sortByStartsAt());
        for(int i = 0; i < PPList.size(); i++)
        {
            System.out.println("T" + PPList.get(i).getStartsAt() + ": " + PPList.get(i).getId() + "(" + PPList.get(i).getPriority() + ")");
        }
        System.out.println();
    }

    // process id | turnaround time | waiting time
    public void report()
    {
        Collections.sort(PPList, new sortByProcessId());
        System.out.println("Process\tTurnaround Time\tWaiting Time");
        for(int  i = 0 ; i< PPList.size();  i++)
        {
            System.out.println(PPList.get(i).getId() + "\t" + PPList.get(i).getTurnAround() + "\t\t" + PPList.get(i).getWaiting() ) ;
        }
        System.out.println();
    }

    // calculating averages
    public double getAverageWaitingTime()
    {
        return this.twt / this.PPList.size();
    }
    public double getAverageTurnaroundTime()
    {
        return this.tta / this.PPList.size();
    }

}

/* COPY THIS FOR TESTING
    System.out.println("id\tarrive\texec\tcomplet\tstarts\tturnAr\twaiting");
    for(int i = 0; i < bList.size(); i++)
    {
        System.out.println(bList.get(i).getId() + "\t" + bList.get(i).getArrive() + "\t" + bList.get(i).getExecution() + "\t" + bList.get(i).getCompletion() + "\t" + bList.get(i).getStartsAt() + "\t" + bList.get(i).getTurnAround() + "\t" + bList.get(i).getWaiting());
    }
*/

// https://www.javatpoint.com/os-preemptive-priority-scheduling
// https://www.codingalpha.com/preemptive-priority-scheduling-algorithm-c-program/