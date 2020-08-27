import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SPN {
    // attributes
    private int pid[];
    private int at[]; // arrival time
    private int et[]; // execution time
    private int ct[]; // completion time
    private int ta[]; // turnaround time
    private int wt[]; // waiting time
    private int f[]; // flagged - check if process if completed or nah

    private int n; // arraylist size

    // constructor
    public SPN() {
        //
    }

    // method
    public void feedProcess(ArrayList<Process> list, int dTime)
    {
        // awaiting time is cpu time
        // sort list via execution time
        // iterate thru list 
            // if current process < cpu time
                // run the process
            // else
                // iterate thru loop
                // remove from list
        // flag = has been scheduled as true/false








        // initializing arraysize in accordance to input
        pid = new int[list.size()];
        at = new int[list.size()];
        et = new int[list.size()];
        ct = new int[list.size()];
        ta = new int[list.size()];
        wt = new int[list.size()];
        f = new int[list.size()];

        // setting process ID's
        for(int i = 0; i < list.size(); i++)
        {
            pid[i] = i + 1;
        }

        // sort in order of arrival
        sortArrive(list);
        // sort in order of execution
        sortExecution(list);

        // print processes
        for(int i = 0; i < list.size(); i++)
        {
            System.out.println("p" + pid[i] + ": " + list.get(i).getExecution());
        }

        System.out.println();

        // transferring arrival time and burst time into arrays
        for(int i = 0; i < list.size(); i++)
        {
            at[i] = list.get(i).getArrive();
            et[i] = list.get(i).getExecution();
        }

        for(int i = 0; i < list.size(); i++)
        {
            //
            System.out.println("p" + pid[i] + ": \t" + at[i] + "\t" + et[i]);
        }

        // calculating turn around time
        boolean a = true;
        for(int i = 0; i < list.size(); i++)
        {
            if(a == true)
            {
                //
                ta[i] = dTime + et[i];
                a = false;
            }

            // this turnaround time = previous process's turn around time + dTime + this process's execution time
        }

        // turnaround time testing
        for(int i = 0; i < list.size(); i++)
        {
            //
            System.out.println("p" + pid[i] + ": " + ta[i]);
        }


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

        System.out.println();
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