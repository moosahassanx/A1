import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class PP
{
    private ArrayList<Process> PPList;
    private ArrayList<Process> pList;
    private int twt;        // total waiting time
    private int tta;        // total turnaround time

    public PP()
    {
        this.PPList = new ArrayList<Process>();
        this.pList = new ArrayList<Process>();
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

        int cpuWatch = 0;

        // this loop breaks only if ALL processes are marked as complete
        ArrayList<Process> compMini = new ArrayList<Process>();
        while(isComplete() == false)
        {
            System.out.println("cpuWatch: " + cpuWatch);

            if(cpuWatch == 0)
            {
                // selecting the first process
                sortArrive(PPList);
                for(int i = 0; i < PPList.size(); i++)
                {
                    if((PPList.get(i).getArrive() <= cpuWatch) && PPList.get(i).getFlagged() == false)
                    {
                        compMini.add(PPList.get(i));
                    }
                }
                cpuWatch++;
            }

            else if(cpuWatch == 1)
            {
                System.out.println(compMini.get(0).getId());
                compMini.get(0).setStartsAt(cpuWatch);
                compMini.get(0).setStatus(1);
                compMini.get(0).iterateRun();

                pList.add(compMini.get(0));
                cpuWatch++;

                compMini.clear();

                // selecting next process
                for(int i = 0; i < PPList.size(); i++)
                {
                    if((PPList.get(i).getArrive() <= cpuWatch) && PPList.get(i).getStatus() == 0)
                    {
                        compMini.add(PPList.get(i));
                    }
                }
            }

            else
            {
                if(compMini.size() == 0)
                {
                    Collections.sort(pList, new sortByStatus());
                    pList.get(0).iterateRun();

                    if(pList.get(0).getRunningTime() == pList.get(0).getExecution())
                    {
                        pList.get(0).setStatus(2);
                    }
                }
                else
                {
                    System.out.println(compMini.get(0).getId());
                }

                // selecting next process
                for(int i = 0; i < PPList.size(); i++)
                {
                    if((PPList.get(i).getArrive() <= cpuWatch) && PPList.get(i).getStatus() == 0)
                    {
                        compMini.add(PPList.get(i));
                    }
                }
            }

            System.out.println("PARTY LIST");
            System.out.println("id\tarrive\trun\texec\tcomplet\tstarts\tturnAr\twaiting\tstatus");
            for(int i = 0; i < pList.size(); i++)
            {
                System.out.println(pList.get(i).getId() + "\t" + pList.get(i).getArrive() + "\t" + pList.get(i).getRunningTime() + "\t" + pList.get(i).getExecution() + "\t" + pList.get(i).getCompletion() + "\t" + pList.get(i).getStartsAt() + "\t" + pList.get(i).getTurnAround() + "\t" + pList.get(i).getWaiting() + "\t" +pList.get(i).getStatusLine());
            }

            System.out.println("********************************************************************************");
        }

    }

    // mark as incomplete if ANY process is yet to be processed
    public boolean isComplete()
    {
        boolean pDone = false;
        for(int i = 0; i < PPList.size(); i++)
        {
            if(PPList.get(i).getFlagged() == false)
            {
                pDone = false;
                break;
            }
            else
            {
                pDone = true;
            }
        }

        return pDone;
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

    // sorting in accordance to execution
    public ArrayList<Process> sortExecution(ArrayList<Process> processList)
    {
        Collections.sort(processList, Comparator.comparing(Process::getExecution)
            .thenComparing(Process::getExecution));
        return processList;
    }

    // sorting in accordance to arrival
    public ArrayList<Process> sortArrive(ArrayList<Process> processList)
    {
        Collections.sort(processList, Comparator.comparing(Process::getArrive)
            .thenComparing(Process::getArrive));
        return processList;
    }

}

// sorting in terms of status
class sortByStatus implements Comparator<Process>
{
    public int compare(Process o1, Process o2)
    {
        return o1.getStatus() - o2.getStatus();
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