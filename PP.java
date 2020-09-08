import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class PP
{
    private ArrayList<Process> PPList;
    private ArrayList<String> tList;
    private ArrayList<Process> fList;
    private int twt;        // total waiting time
    private int tta;        // total turnaround time

    public PP()
    {
        this.PPList = new ArrayList<Process>();
        this.tList = new ArrayList<String>();
        this.fList = new ArrayList<Process>();
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
            //System.out.println("cpuWatch: " + cpuWatch);

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
                compMini.get(0).setOGStart(dTime + cpuWatch);
                compMini.get(0).iterateRun();
                cpuWatch += dTime;

                if(tList.size() == 0)
                {
                    String feed = "T" + compMini.get(0).getStartsAt() + ": " + compMini.get(0).getId() + "(" + compMini.get(0).getPriority() + ")";
                    tList.add(feed);
                }

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

            // FINISHED
            if(compMini.get(0).getRunningTime() == compMini.get(0).getExecution())
            {
                compMini.get(0).setStatus(3);
                compMini.get(0).setCompletion(cpuWatch + compMini.get(0).getArrive());
                fList.add(compMini.get(0));
                PPList.remove(compMini.get(0));
            }

            // selecting current process for next lot
            if(cProcess == null)
            {
                cProcess = compMini.get(0);
            }
            else if(cProcess != compMini.get(0))
            {
                cProcess = compMini.get(0);
                String feed = "T" + cProcess.getStartsAt() + ": " + cProcess.getId() + "(" + cProcess.getPriority() + ")";
                tList.add(feed);
            }
            
            compMini.clear();
            cpuWatch++;
        }

        System.out.println("FINAL CPUWATCH: " + cpuWatch);
        
        Collections.sort(fList, new sortByProcessId());
        for(int i = 0; i < fList.size(); i++)
        {
            // calculating turnaround/waiting times
            fList.get(i).setTurnAround(fList.get(i).getCompletion() - fList.get(i).getArrive());
            fList.get(i).setWaiting(fList.get(i).getTurnAround() - fList.get(i).getExecution());

            // accumulating total turnaround/waiting times
            twt += fList.get(i).getWaiting();
            tta += fList.get(i).getTurnAround();
        }

        // TESTING
        System.out.println("id\tarrive\trun\texec\tcomplet\tstarts\tturnAr\twaiting\tstatus");
        for(int i = 0; i < fList.size(); i++)
        {
            System.out.println(fList.get(i).getId() + "(" + fList.get(i).getPriority() + ")" + "\t" + fList.get(i).getArrive() + "\t" + fList.get(i).getRunningTime() + "\t" + fList.get(i).getExecution() + "\t" + fList.get(i).getCompletion() + "\t" + fList.get(i).getOGStart() + "\t" + fList.get(i).getTurnAround() + "\t" + fList.get(i).getWaiting() + "\t" + fList.get(i).getStatusLine());
        }
        System.out.println("********************************************************************************");
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
        Collections.sort(fList, new sortByProcessId());
        System.out.println("Process\tTurnaround Time\tWaiting Time");
        for(int  i = 0 ; i< fList.size();  i++)
        {
            System.out.println(fList.get(i).getId() + "\t" + fList.get(i).getTurnAround() + "\t\t" + fList.get(i).getWaiting() );
        }
        System.out.println();
    }

    // calculating averages
    public double getAverageWaitingTime()
    {
        return this.twt / this.fList.size();
    }
    public double getAverageTurnaroundTime()
    {
        return this.tta / this.fList.size();
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