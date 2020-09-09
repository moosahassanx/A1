import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class PRR
{
    // attributes
    private ArrayList<Process> PRRList;
    private ArrayList<String> tList;
    private ArrayList<Process> fList;
    private int twt;        // total waiting time
    private int tta;        // total turnaround time

    // constructor
    public PRR()
    {
        this.PRRList = new ArrayList<Process>();
        this.tList = new ArrayList<String>();
        this.fList = new ArrayList<Process>();
        this.twt = 0;
        this.tta = 0;
    }

    // methods
    public void feedProcess(ArrayList<Process> ogList, int dTime)
    {
        // deep element arraylist cloning
        for(int i = 0; i < ogList.size(); i++)
        {
            this.PRRList.add(ogList.get(i));
        }
        Collections.sort(PRRList, new sortByArrival());

        int cpuWatch = 0;
        ArrayList<Process> compMini = new ArrayList<Process>();
        sortArrive(PRRList);
        Process cProcess = null;

        while(PRRList.size() > 0)
        {
            // getting qualified processes
            for(int i = 0; i < PRRList.size(); i++)
            {
                if((PRRList.get(i).getArrive() <= cpuWatch) && (PRRList.get(i).getStatus() != 3))
                {
                    compMini.add(PRRList.get(i));
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
                compMini.get(0).setCompletion(cpuWatch + dTime);
                fList.add(compMini.get(0));
                PRRList.remove(compMini.get(0));
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

        /*
        // TESTING
        System.out.println("id\tarrive\trun\texec\tcomplet\tstarts\tturnAr\twaiting\tstatus");
        for(int i = 0; i < fList.size(); i++)
        {
            System.out.println(fList.get(i).getId() + "(" + fList.get(i).getPriority() + ")" + "\t" + fList.get(i).getArrive() + "\t" + fList.get(i).getRunningTime() + "\t" + fList.get(i).getExecution() + "\t" + fList.get(i).getCompletion() + "\t" + fList.get(i).getOGStart() + "\t" + fList.get(i).getTurnAround() + "\t" + fList.get(i).getWaiting() + "\t" + fList.get(i).getStatusLine());
        }
        System.out.println("********************************************************************************");
        */

    }

    // sorting in accordance to arrival
    public ArrayList<Process> sortArrive(ArrayList<Process> processList)
    {
        Collections.sort(processList, Comparator.comparing(Process::getArrive)
            .thenComparing(Process::getArrive));
        return processList;
    }

    // start time | process id | process priority
    public void results()
    {
        System.out.println("results() method is yet to be made");
    }

    // process id | turnaround time | waiting time
    public void report()
    {
        System.out.println("Process\tTurnaround Time\tWaiting Time");
        System.out.println("NEEDS DATA");
    }

    // TODO: needs to be finished
    public double getAverageTurnaroundTime()
    {
        return 0;
    }

    // TODO: needs to be finished
    public double getAverageWaitingTime()
    {
        return 0;
    }
}