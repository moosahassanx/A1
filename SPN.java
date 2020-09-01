import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SPN {
    // attributes
    private double twt;   // total waiting time
    private double tta;   // total turn aroudn time

    private ArrayList<Process> SPNList;
    private ArrayList<Process> bList;

    // constructor
    public SPN() {
        this.twt = 0;       // total waiting time
        this.tta = 0;       // total turnaround time
        this.bList = new ArrayList<Process>();
        this.SPNList = new ArrayList<Process>();
    }

    // method
    public void feedProcess(ArrayList<Process> ogList, int dTime)
    {
        for(int i = 0; i < ogList.size(); i++)
        {
            this.SPNList.add(ogList.get(i));
        }

        // sort in order of arrival
        sortArrive(SPNList);

        int c = 0;
        int cpuWatch = 0;
        while(c < SPNList.size())
        {
            // first iteration
            if(c == 0){
                SPNList.get(c).setStartsAt(dTime);
                SPNList.get(c).setCompletion(dTime + SPNList.get(c).getArrive() + SPNList.get(c).getExecution());
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
            bList.get(c).setStartsAt(dTime + bList.get(c-1).getCompletion());
            bList.get(c).setCompletion(bList.get(c).getStartsAt() + bList.get(c).getExecution());
            bList.get(c).setFlag(true);
            cpuWatch = bList.get(c).getCompletion();

            compMini.clear();       // refresh this for next loop
            c++;
        }

        // final calculations
        Collections.sort(bList, new sortByProcessId());
        for(int i = 0; i < bList.size(); i++)
        {
            // calculating turnaround/waiting times
            bList.get(i).setTurnAround(bList.get(i).getCompletion() - bList.get(i).getArrive());
            bList.get(i).setWaiting(bList.get(i).getTurnAround() - bList.get(i).getExecution());

            // accumulating total turnaround/waiting times
            twt += bList.get(i).getWaiting();
            tta += bList.get(i).getTurnAround();
        }

    }

    // process id | turnaround time | waiting time
    public void report()
    {
        Collections.sort(bList, new sortByProcessId());
        System.out.println("Process\tTurnaround Time\tWaiting Time");
        for(int  i = 0 ; i< bList.size();  i++)
		{
			System.out.println(bList.get(i).getId() + "\t" + bList.get(i).getTurnAround() + "\t\t" + bList.get(i).getWaiting() ) ;
        }
        System.out.println();
    }

    // start time | process id | process priority
    public void results()
    {
        Collections.sort(bList, new sortByStartsAt());
        for(int i = 0; i < bList.size(); i++)
        {
            System.out.println("T" + bList.get(i).getStartsAt() + ": " + bList.get(i).getId() + "(" + bList.get(i).getPriority() + ")");
        }
        System.out.println();
    }

    // calcualtion averages
    public double getAverageWaitingTime()
    {
        return this.twt / this.bList.size();
    }
    public double getAverageTurnaroundTime()
    {
        return this.tta / this.bList.size();
    }

    // sorting in accordance to arrival
    public ArrayList<Process> sortArrive(ArrayList<Process> processList)
    {
        Collections.sort(processList, Comparator.comparing(Process::getArrive)
            .thenComparing(Process::getArrive));
        return processList;
    }

    // sorting in accordance to execution
    public ArrayList<Process> sortExecution(ArrayList<Process> processList)
    {
        Collections.sort(processList, Comparator.comparing(Process::getExecution)
            .thenComparing(Process::getExecution));
        return processList;
    }

}