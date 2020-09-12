import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

public class PRR
{
    // attributes
    private final ArrayList<Process> PRRList;
    private final ArrayList<String> tList;
    private final ArrayList<Process> fList;
    Queue<Process> q;
    private int twt; // total waiting time
    private int tta; // total turnaround time

    // constructor
    public PRR() 
    {
        this.PRRList = new ArrayList<Process>();
        this.tList = new ArrayList<String>();
        this.fList = new ArrayList<Process>();
        q = new LinkedList<Process>();
        this.twt = 0;
        this.tta = 0;
    }

    // methods
    public void feedProcess(final ArrayList<Process> ogList, final int dTime) 
    {
        // deep element arraylist cloning
        for (int i = 0; i < ogList.size(); i++) 
        {
            this.PRRList.add(ogList.get(i));
        }
        Collections.sort(PRRList, new sortByArrival());

        int cpuWatch = 0;
        sortArrive(PRRList);
        Process cProcess = null;

        // getting qualified processes
        for (int i = 0; i < PRRList.size(); i++) 
        {
            if ((PRRList.get(i).getArrive() <= cpuWatch) && (PRRList.get(i).getStatus() != 3)) 
            {
                q.add(PRRList.get(i));
            }
        }

        cpuWatch += dTime;

        String feed = "T" + cpuWatch + ": " + q.peek().getId() + "(" + q.peek().getPriority() + ")";
        tList.add(feed);

        // business
        while (PRRList.size() > 0) 
        {
            cProcess = q.poll();

            // determining iteration
            int progress = 0;
            if (cProcess.isHPC() == true) 
            {
                progress = 4;
            } else 
            {
                progress = 2;
            }
            // running process depending on iteration
            for (int i = 0; i < progress; i++) {
                if (cProcess.getExecution() != cProcess.getRunningTime()) 
                {
                    cProcess.iterateRun();
                    cpuWatch++;
                }
            }

            // process is finished
            if (cProcess.getExecution() == cProcess.getRunningTime()) 
            {
                cProcess.setStatus(3);
                cProcess.setCompletion(cpuWatch);
                fList.add(cProcess);
                PRRList.remove(cProcess);
            }

            // check qualified process
            for (int i = 0; i < PRRList.size(); i++) 
            {
                // queues everything except cProcess
                if ((PRRList.get(i).getArrive() <= cpuWatch) && (!q.contains(PRRList.get(i)))
                        && (PRRList.get(i) != cProcess)) 
                        {
                    q.add(PRRList.get(i));
                }
            }

            if ((q.contains(cProcess) == false) && (PRRList.contains(cProcess) == true)) 
            {
                q.add(cProcess);
            }

            // selecting current process for next lot
            if ((cProcess != q.peek()) && (q.peek() != null)) 
            {
                cpuWatch += dTime;
                feed = "T" + cpuWatch + ": " + q.peek().getId() + "(" + q.peek().getPriority() + ")";
                tList.add(feed);
            }
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

    // sorting in accordance to arrival
    public ArrayList<Process> sortArrive(final ArrayList<Process> processList)
    {
        Collections.sort(processList, Comparator.comparing(Process::getArrive)
            .thenComparing(Process::getArrive));
        return processList;
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

    public double getAverageTurnaroundTime()
    {
        return (double)this.tta / this.fList.size();
    }

    public double getAverageWaitingTime()
    {
        return (double)this.twt / this.fList.size();
    }
}