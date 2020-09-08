import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;

public class PP
{
    private ArrayList<Process> PPList;
    private ArrayList<String> tList;
    private ArrayList<String> report;
    private int twt;        // total waiting time
    private int tta;        // total turnaround time

    public PP()
    {
        this.PPList = new ArrayList<Process>();
        this.tList = new ArrayList<String>();
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

                // getting next qualified processes
                ArrayList<Process> compMini2 = new ArrayList<Process>();
                int cpuWatch2 = cpuWatch++;
                for(int i = 0; i < PPList.size(); i++)
                {
                    if((PPList.get(i).getArrive() <= cpuWatch2) && (PPList.get(i).getStatus() != 3))
                    {
                        compMini2.add(PPList.get(i));
                    }
                }
                Collections.sort(compMini2, new sortByPriority());

                // comparing to next qualified process
                if(compMini.get(0) != compMini2.get(0))
                {
                    String feedResult = "T" + compMini.get(0).getStartsAt() + ": " + compMini.get(0).getId() + "(" + compMini.get(0).getPriority() + ")";
                    tList.add(feedResult);
                }


            }
            // RUNNING
            else if(compMini.get(0).getStatus() == 1)
            {
                compMini.get(0).iterateRun();

                // getting next qualified processes
                ArrayList<Process> compMini2 = new ArrayList<Process>();
                int cpuWatch2 = cpuWatch++;
                for(int i = 0; i < PPList.size(); i++)
                {
                    if((PPList.get(i).getArrive() <= cpuWatch2) && (PPList.get(i).getStatus() != 3))
                    {
                        compMini2.add(PPList.get(i));
                    }
                }
                Collections.sort(compMini2, new sortByPriority());

                // comparing to next qualified process
                if(compMini.get(0) != compMini2.get(0))
                {
                    String feedResult = "T" + compMini.get(0).getStartsAt() + ": " + compMini.get(0).getId() + "(" + compMini.get(0).getPriority() + ")";
                    tList.add(feedResult);
                }
            }
            // PAUSED
            else if(compMini.get(0).getStatus() == 2)
            {
                compMini.get(0).setStartsAt(dTime + cpuWatch);
                compMini.get(0).iterateRun();
                compMini.get(0).setStatus(1);
                cpuWatch += dTime;

                // getting next qualified processes
                ArrayList<Process> compMini2 = new ArrayList<Process>();
                int cpuWatch2 = cpuWatch++;
                for(int i = 0; i < PPList.size(); i++)
                {
                    if((PPList.get(i).getArrive() <= cpuWatch2) && (PPList.get(i).getStatus() != 3))
                    {
                        compMini2.add(PPList.get(i));
                    }
                }
                Collections.sort(compMini2, new sortByPriority());

                // comparing to next qualified process
                if(compMini.get(0) != compMini2.get(0))
                {
                    String feedResult = "T" + compMini.get(0).getStartsAt() + ": " + compMini.get(0).getId() + "(" + compMini.get(0).getPriority() + ")";
                    tList.add(feedResult);
                }
            }

            // FINISHED
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
        for(int i = 0; i < tList.size(); i++)
        {
            System.out.println(tList.get(i));
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