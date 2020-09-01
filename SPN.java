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
    private ArrayList<Process> bList;

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
        this.bList = new ArrayList<Process>();
        this.SPNList = new ArrayList<Process>();
        for(int i = 0; i < ogList.size(); i++)
        {
            this.SPNList.add(ogList.get(i));
        }

        this.dispatchTime = dTime;
        this.listSize = SPNList.size();

        // sort in order of arrival
        sortArrive(SPNList);

        int c = 0;
        int cpuWatch = 0;
        while(c < SPNList.size())
        {
            if(c == 0){
                SPNList.get(c).setStartsAt(dispatchTime);
                SPNList.get(c).setCompletion(dispatchTime + SPNList.get(c).getArrive() + SPNList.get(c).getExecution());
                SPNList.get(c).setFlag(true);
                cpuWatch = SPNList.get(c).getCompletion();
                this.bList.add(SPNList.get(c));
                c++;
            }

            // selecting the next process
            sortExecution(SPNList);
            ArrayList<Process> compMini = new ArrayList<Process>();
            for(int i = 0; i < SPNList.size(); i++)
            {
                if((SPNList.get(i).getArrive() < cpuWatch) && SPNList.get(i).getFlagged() == false)
                {
                    compMini.add(SPNList.get(i));
                }
            }
            sortExecution(compMini);

            // calculations
            bList.add(compMini.get(0));
            bList.get(c).setStartsAt(dispatchTime + bList.get(c-1).getCompletion());
            bList.get(c).setCompletion(bList.get(c).getStartsAt() + bList.get(c).getExecution());
            bList.get(c).setFlag(true);
            cpuWatch = bList.get(c).getCompletion();

            compMini.clear();       // refresh this for next loop
            c++;
        }

        // calculating turnaround / waiting times
        Collections.sort(bList, new sortByProcessId());
        for(int i = 0; i < bList.size(); i++)
        {
            bList.get(i).setTurnAround(bList.get(i).getCompletion() - bList.get(i).getArrive());
            bList.get(i).setWaiting(bList.get(i).getTurnAround() - bList.get(i).getExecution());

            twt += bList.get(i).getWaiting();
            tta += bList.get(i).getTurnAround();
        }

    }

    public void report()
    {
        // OFFICIAL OUTPUT
        Collections.sort(bList, new sortByProcessId());

        // printing results
        System.out.println("Process\tTurnaround Time\tWaiting Time");
        for(int  i = 0 ; i< bList.size();  i++)
		{
			System.out.println(bList.get(i).getId() + "\t" + bList.get(i).getTurnAround() + "\t\t" + bList.get(i).getWaiting() ) ;
        }

        System.out.println();
    }

    public void results()
    {
        Collections.sort(bList, new sortByStartsAt());
        for(int i = 0; i < bList.size(); i++)
        {
            System.out.println("T" + bList.get(i).getStartsAt() + ": " + bList.get(i).getId() + "(" + bList.get(i).getPriority() + ")");
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

}