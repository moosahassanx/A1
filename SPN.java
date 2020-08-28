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

    // constructor
    public SPN() {
        this.listSize = 0;
        this.dispatchTime = 0;
        this.twt = 0;
        this.tta = 0;
    }

    // method
    public void feedProcess(ArrayList<Process> list, int dTime)
    {
        // waiting time is cpu time
        // sort list via execution time
        // iterate thru list 
            // if current process < cpu time
                // run the process
            // else
                // iterate thru loop
                // remove from list
        // flag = has been scheduled as true/false

        System.out.println("****SPN Testing***");

        this.SPNList = list;
        this.dispatchTime = dTime;
        this.listSize = list.size();

        // sort in order of arrival
        sortArrive(SPNList);
        // sort in order of execution
        sortExecution(SPNList);

        int st = 0;
        int tot = 0;

        // finding completion times
        for(int i = 0; i < SPNList.size(); i++)
        {
            // first iteration
            if(i == 0)
            {
                SPNList.get(i).setCompletion(dispatchTime + SPNList.get(i).getArrive() + SPNList.get(i).getExecution());
            }
            else
            {
                // arrival time > completion time
                if(SPNList.get(i).getArrive() > SPNList.get(i-1).getCompletion())
                {
                    SPNList.get(i).setCompletion(dispatchTime + SPNList.get(i).getArrive() + SPNList.get(i).getExecution());
                }
                // arrival time < completion time
                else
                {
                    SPNList.get(i).setCompletion(dispatchTime + SPNList.get(i-1).getCompletion() + SPNList.get(i).getExecution());
                }
            }

            // calculating turnaround time and waiting time
            SPNList.get(i).setTurnAround(SPNList.get(i).getCompletion() - SPNList.get(i).getArrive());
            SPNList.get(i).setWaiting(SPNList.get(i).getTurnAround() - SPNList.get(i).getExecution());
            
            // accumulating results into total turnaround time and total waiting time
            tta += SPNList.get(i).getTurnAround();
            twt += SPNList.get(i).getWaiting();
        }

        // printing results
        System.out.println("Process\tTurnaround Time\tWaiting Time");
        for(int  i = 0 ; i< listSize;  i++)
		{
			System.out.println(SPNList.get(i).getId() + "\t" + SPNList.get(i).getTurnAround() + "\t\t" + SPNList.get(i).getWaiting() ) ;
        }

        System.out.println();

        /*
        this.n = list.size();
        int st = 0;             // system time
        int tot = 0;            // total number of processes
        double avgwt = 0;       // average waiting time
        double avgta = 0;       // average turnaround time

        // initializing arraysize in accordance to input
        pid = new int[n];
        at = new int[n];
        et = new int[n];
        ct = new int[n];
        ta = new int[n];
        wt = new int[n];
        f = new int[n];

        // process id
        for(int i = 0; i < n; i++){
            pid[i] = i + 1;
        }

        // setting arrival time and burst times
        for(int i = 0; i < list.size(); i++)
        {
            at[i] = list.get(i).getArrive();
            et[i] = list.get(i).getExecution();
        }

        boolean a = true;

        while(true)
        {
            int c = n;
            int min = 99;

            // exit the loop when: total number of processes = completed process
            if(tot == n)
            {
                break;
            }
            
            for(int i = 0; i < n; i++)
            {
                // if the i'th process arrival time <= system time AND its flag = 0 AND execution time < min
                    // then that process will be executed first
                
                if((at[i] <= st) && (f[i] == 0) && (et[i] < min))
                {
                    min = et[i];
                    c = i;
                }
            }

            // c value cannot be updated because no process arrival time < system time so we increase system time
            if(c == n){
                st++;
            }
            else
            {
                ct[c] = dTime + st + et[c];
                st += et[c];
                ta[c] = ct[c] - at[c];
                wt[c] = ta[c] - et[c];
                f[c] = 1;
                tot++;
            }
        }

        System.out.println("\npid\tarrival\texecution\tcomplete\tturn\twaiting");
		for(int i = 0; i < n; i++)
		{
			avgwt += wt[i];
			avgta += ta[i];
			System.out.println(pid[i]+"\t"+at[i]+"\t"+et[i]+"\t\t"+ct[i]+"\t\t"+ta[i]+"\t"+wt[i]);
		}
		System.out.println ("\naverage tat is "+ (float)(avgta/n));
        System.out.println ("average wt is "+ (float)(avgwt/n));
        */

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

    //HELPFUL VIDEO
    //https://www.youtube.com/watch?v=e1nee-bE5FA

    /*
        SPN:
        T1: p2(2)
        T3: p4(1)
        T5: p3(4)
        T8: p5(5)
        T14: p1(0)

        Process Turnaround Time Waiting Time
        p1      24              14
        p2      2               1
        p3      7               5
        p4      4               3
        p5      13              8
    */
}