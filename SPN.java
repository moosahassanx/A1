import java.util.ArrayList;

public class SPN
{
    // attributes
    private int dispatchTime;
    private ArrayList processList;

    private int pid[];
    private int ar[];
    private int et[];
    private int ct[];
    private int ta[];
    private int wt[];
    private int f[];
    
    private int st;
    private int tot;

    private double twt;
    private double tta;

    // constructor
    public SPN()
    {
        this.st = 0;
        this.tot = 0;

        this.twt = 0;
        this.tta = 0;
    }

    // method
    public void feedProcess(ArrayList<Process> list, int t)
    {
        this.dispatchTime = t;
        this.processList = list;

        pid = new int[list.size()];
        ar = new int[list.size()];
        et = new int[list.size()];
        ct = new int[list.size()];      // completion time - where in the timeline the process finishes
        ta = new int[list.size()];      // turn around time = completion time - arrival time
        wt = new int[list.size()];      // waiting time = turn around time - execution time
        f = new int[list.size()];

        // setting process Ids
        for(int i = 0; i < list.size(); i++)
        {
            pid[i] = i+1;
        }

        // setting arrival time and burst times
        for(int i = 0; i < list.size(); i++)
        {
            ar[i] = list.get(i).getArrive();
            et[i] = list.get(i).getExecution();
        }

        boolean a = true;

        while(true)
		{
			int c = list.size(), min = 999;
			if (tot == list.size())     // total no of process = completed process loop will be terminated
				break;
			
			for (int i = 0; i < list.size(); i++)
			{
				/*
				 * If i'th process arrival time <= system time and its flag = 0 and burst < min
				 * That process will be executed first 
				 */ 
				if ((ar[i] <= st) && (f[i] == 0) && (et[i] < min))
				{
					min = et[i];
					c = i;
				}
			}
			
			/* If c==n means c value can not updated because no process arrival time< system time so we increase the system time */
            if (c == list.size())
            {
				st++;
            }
            else
			{
				ct[c] = dispatchTime + st + et[c];
				st += et[c];
				ta[c] = ct[c] - ar[c];
				wt[c] = ta[c] - et[c];
				f[c] = 1;
				tot++;
			}
        }
        
        // results
        System.out.println("\npid  arrival burst  complete turn waiting");

		for(int i = 0; i < list.size(); i++)
		{
			twt += wt[i];
			tta += ta[i];
			System.out.println("p" + pid[i] + "\t" + ar[i] + "\t" + et[i] + "\t" + ct[i] + "\t" + ta[i] + "\t" + wt[i]);
        }
        
		System.out.println ("\naverage tat is "+ (float)(tta / list.size()));
        System.out.println ("average wt is "+ (float)(twt / list.size()));
        
        System.out.println();
    }

    //HELPFUL VIDEO
    //https://www.youtube.com/watch?v=TeC5AgWz4Hk

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