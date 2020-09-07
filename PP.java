import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;

public class PP
{
    private ArrayList<Process> PPList;
    private ArrayList<Process> pList;
    private ArrayList<String> report;
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
        ArrayList<Process> compMini = new ArrayList<Process>();
        sortArrive(PPList);
        Process cProcess = null;

        while(PPList.size() > 0)
        {
            System.out.println("cpuWatch: " + cpuWatch);

            // getting qualified processes
            for(int i = 0; i < PPList.size(); i++)
            {
                if((PPList.get(i).getArrive() <= cpuWatch) && (PPList.get(i).getStatus() != 3))
                {
                    compMini.add(PPList.get(i));
                }
            }

            if(compMini.size() == 0)
            {
                cpuWatch++;
                continue;
            }

            // running the processes
            Collections.sort(compMini, new sortByPriority());

            // NOT STARTED
            if(compMini.get(0).getStatus() == 0)
            {
                compMini.get(0).setStatus(1);
                compMini.get(0).setStartsAt(dTime + cpuWatch);
                compMini.get(0).iterateRun();
                cpuWatch += dTime;

                if(cProcess != null)
                {
                    cProcess.setStatus(2);
                }
            }
            // RUNNING
            else if(compMini.get(0).getStatus() == 1)
            {
                compMini.get(0).iterateRun();
            }
            // PAUSED
            else if(compMini.get(0).getStatus() == 2)
            {
                compMini.get(0).setStartsAt(dTime + cpuWatch);
                compMini.get(0).iterateRun();
                compMini.get(0).setStatus(1);
                cpuWatch += dTime;
            }

            if(compMini.get(0).getRunningTime() == compMini.get(0).getExecution())
            {
                compMini.get(0).setStatus(3);
                PPList.remove(compMini.get(0));
            }

            // TESTING
            System.out.println("id\tarrive\trun\texec\tcomplet\tstarts\tturnAr\twaiting\tstatus");
            for(int i = 0; i < compMini.size(); i++)
            {
                System.out.println(compMini.get(i).getId() + "(" + compMini.get(i).getPriority() + ")" + "\t" + compMini.get(i).getArrive() + "\t" + compMini.get(i).getRunningTime() + "\t" + compMini.get(i).getExecution() + "\t" + compMini.get(i).getCompletion() + "\t" + compMini.get(i).getStartsAt() + "\t" + compMini.get(i).getTurnAround() + "\t" + compMini.get(i).getWaiting() + "\t" +compMini.get(i).getStatusLine());
            }
            System.out.println("********************************************************************************");

            // selecting current process for next loop
            if(cProcess == null)
            {
                cProcess = compMini.get(0);
            }
            else if(cProcess != compMini.get(0))
            {
                cProcess = compMini.get(0);
            }
            
            compMini.clear();
            cpuWatch++;
        }

        System.out.println("FINAL CPUWATCH: " + cpuWatch);
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
        sortStartsAt(PPList);
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
            System.out.println(PPList.get(i).getId() + "\t" + PPList.get(i).getTurnAround() + "\t\t" + PPList.get(i).getWaiting() );
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

    // sorting in accordance to startsAt
    public ArrayList<Process> sortStartsAt(ArrayList<Process> processList)
    {
        Collections.sort(processList, Comparator.comparing(Process::getStartsAt)
            .thenComparing(Process::getStartsAt));
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

// sorting in terms of priority
class sortByPriority implements Comparator<Process>
{
    public int compare(Process o1, Process o2)
    {
        return o1.getPriority() - o2.getPriority();
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