// TITLE: 					Assignment1
// COURSE: 					COMP2240
// AUTHOR: 					Moosa Hassan
// STUDENT NUMBER: 			3331532
// DATE: 					13/09/2020 
// DESCRIPTION: 			Calculations and printing results for Pre-emptive Priority algorithm. Has status and priority sorting classes too.

// importing java packages
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class PP
{
    private final ArrayList<Process> PPList;
    private final ArrayList<String> tList;
    private final ArrayList<Process> fList;
    private int twt; // total waiting time
    private int tta; // total turnaround time

    public PP() 
    {
        this.PPList = new ArrayList<Process>();
        this.tList = new ArrayList<String>();
        this.fList = new ArrayList<Process>();
        this.twt = 0;
        this.tta = 0;
    }

    public void feedProcess(final ArrayList<Process> ogList, final int dTime) 
    {
        // deep element arraylist cloning
        for (int i = 0; i < ogList.size(); i++) 
        {
            this.PPList.add(ogList.get(i));
        }
        Collections.sort(PPList, new sortByArrival());

        int cpuWatch = 0;
        final ArrayList<Process> compMini = new ArrayList<Process>();
        sortArrive(PPList);
        Process cProcess = null;

        while (PPList.size() > 0)
        {
            // getting qualified processes
            for (int i = 0; i < PPList.size(); i++) 
            {
                if ((PPList.get(i).getArrive() <= cpuWatch) && (PPList.get(i).getStatus() != 3)) 
                {
                    compMini.add(PPList.get(i));
                }
            }

            if (compMini.size() == 0) 
            {
                cpuWatch++;
                continue;
            }

            // running the processes
            Collections.sort(compMini, new sortByPriority());

            // NOT STARTED
            if (compMini.get(0).getStatus() == 0) 
            {
                compMini.get(0).setStatus(1);
                compMini.get(0).setStartsAt(dTime + cpuWatch);
                compMini.get(0).setOGStart(dTime + cpuWatch);
                compMini.get(0).iterateRun();
                cpuWatch += dTime;

                // first add to list
                if (tList.size() == 0) 
                {
                    final String feed = "T" + compMini.get(0).getStartsAt() + ": " + compMini.get(0).getId() + "("
                            + compMini.get(0).getPriority() + ")";
                    tList.add(feed);
                }

                if (cProcess != null) 
                {
                    cProcess.setStatus(2);
                }

            }
            // RUNNING
            else if (compMini.get(0).getStatus() == 1) 
            {
                compMini.get(0).iterateRun();
            }
            // PAUSED
            else if (compMini.get(0).getStatus() == 2) 
            {
                compMini.get(0).setStartsAt(dTime + cpuWatch);
                compMini.get(0).iterateRun();
                compMini.get(0).setStatus(1);
                cpuWatch += dTime;
            }

            // process is finished executing
            if (compMini.get(0).getRunningTime() == compMini.get(0).getExecution())
            {
                compMini.get(0).setStatus(3);
                compMini.get(0).setCompletion(cpuWatch + dTime);
                fList.add(compMini.get(0));
                PPList.remove(compMini.get(0));
            }

            // selecting current process for next lot
            if (cProcess == null)
            {
                cProcess = compMini.get(0);
            }
            else if (cProcess != compMini.get(0))
            {
                cProcess = compMini.get(0);
                final String feed = "T" + cProcess.getStartsAt() + ": " + cProcess.getId() + "("
                        + cProcess.getPriority() + ")";
                tList.add(feed);
            }

            compMini.clear();
            cpuWatch++;
        }

        Collections.sort(fList, new sortByProcessId());
        for (int i = 0; i < fList.size(); i++) 
        {
            // calculating turnaround/waiting times
            fList.get(i).setTurnAround(fList.get(i).getCompletion() - fList.get(i).getArrive());
            fList.get(i).setWaiting(fList.get(i).getTurnAround() - fList.get(i).getExecution());

            // accumulating total turnaround/waiting times
            tta += fList.get(i).getTurnAround();
            twt += fList.get(i).getWaiting();
        }

    }

    // start time | process id | process priority
    public void results() 
    {
        for (int i = 0; i < tList.size(); i++) 
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
        for (int i = 0; i < fList.size(); i++) 
        {
            System.out.println(
                    fList.get(i).getId() + "\t" + fList.get(i).getTurnAround() + "\t\t" + fList.get(i).getWaiting());
        }
        System.out.println();
    }

    // calculating averages
    public double getAverageTurnaroundTime() 
    {
        return (double) this.tta / this.fList.size();
    }
    public double getAverageWaitingTime() 
    {
        return (double) this.twt / this.fList.size();
    }

    // sorting in accordance to execution
    public ArrayList<Process> sortExecution(final ArrayList<Process> processList) 
    {
        Collections.sort(processList, Comparator.comparing(Process::getExecution).thenComparing(Process::getExecution));
        return processList;
    }

    // sorting in accordance to arrival
    public ArrayList<Process> sortArrive(final ArrayList<Process> processList) 
    {
        Collections.sort(processList, Comparator.comparing(Process::getArrive).thenComparing(Process::getArrive));
        return processList;
    }

    // sorting in accordance to startsAt
    public ArrayList<Process> sortStartsAt(final ArrayList<Process> processList) 
    {
        Collections.sort(processList, Comparator.comparing(Process::getStartsAt).thenComparing(Process::getStartsAt));
        return processList;
    }

}

// sorting in terms of status
class sortByStatus implements Comparator<Process> 
{
    public int compare(final Process o1, final Process o2) 
    {
        return o1.getStatus() - o2.getStatus();
    }
}

// sorting in terms of priority
class sortByPriority implements Comparator<Process> 
{
    public int compare(final Process o1, final Process o2)
    {
        return o1.getPriority() - o2.getPriority();
    }
}