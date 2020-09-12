// TITLE: 					Assignment1
// COURSE: 					COMP2240
// AUTHOR: 					Moosa Hassan
// STUDENT NUMBER: 			3331532
// DATE: 					13/09/2020 
// DESCRIPTION: 			Basically a storage file for each process.

public class Process
{
    // class attributes
    private String processId;
    private final int processNumber;
    private int arrive;
    private int execution;
    private int turnaround;
    private int waiting;
    private int completion;
    private int priority;
    private boolean isProcessed;
    private int startsAt;
    private int status; // 0 = NOT STARTED, 1 = RUNNING, 2 = PAUSED, 3 = FINISHED
    private int runningTime;
    private int ogStart;
    private boolean HPC;

    // constructors
    public Process() 
    {
        this.processId = "";
        this.processNumber = 0;
        this.arrive = 0;
        this.execution = 0;
        this.turnaround = 0;
        this.waiting = 0;
        this.priority = 0;
        this.completion = 0;
        this.isProcessed = false;
        this.startsAt = 0;
        this.status = 0;
        this.runningTime = 0;
        this.ogStart = 0;
        this.HPC = false;
    }

    public Process(final String pID, final int a, final int e, final int p) 
    {
        this.processId = pID;
        final int test = processId.charAt(1);
        this.processNumber = test;
        this.arrive = a;
        this.execution = e;
        this.priority = p;
        this.turnaround = 0;
        this.waiting = 0;
        this.completion = 0;
        this.isProcessed = false;
        this.startsAt = 0;
        this.status = 0;
        this.runningTime = 0;
        this.ogStart = 0;

        // is High Priority Class
        if (priority < 3) 
        {
            this.HPC = true;
        } 
        else 
        {
            this.HPC = false;
        }
    }

    // mutators
    public void setId(final String i) 
    {
        this.processId = i;
    }

    public void setArrive(final int a) 
    {
        this.arrive = a;
    }

    public void setExecution(final int e) 
    {
        this.execution = e;
    }

    public void setPriority(final int p) 
    {
        this.priority = p;
    }

    public void setPriorityClass(final boolean c) 
    {
        this.HPC = c;
    }

    public void setTurnAround(final int t) 
    {
        this.turnaround = t;
    }

    public void setWaiting(final int w) 
    {
        this.waiting = w;
    }

    public void setCompletion(final int c) 
    {
        this.completion = c;
    }

    public void setFlag(final boolean f) 
    {
        this.isProcessed = f;
    }

    public void clearCalculations() 
    {
        this.turnaround = 0;
        this.waiting = 0;
        this.completion = 0;
    }

    public void setStartsAt(final int s) 
    {
        this.startsAt = s;
    }

    public void setStatus(final int s) 
    {
        this.status = s;
    }

    public void setRunningTime(final int r) 
    {
        this.runningTime = r;
    }

    public void iterateRun() 
    {
        this.runningTime++;
    }

    public void setOGStart(final int o)
    {
        this.ogStart = o;
    }

    // accessors
    public String getId()
    {
        return this.processId;
    }

    public int getArrive()
    {
        return this.arrive;
    }

    public int getExecution()
    {
        return this.execution;
    }

    public int getPriority()
    {
        return this.priority;
    }

    public boolean isHPC()
    {
        return this.HPC;
    }

    public int getTurnAround()
    {
        return this.turnaround;
    }

    public int getWaiting()
    {
        return this.waiting;
    }

    public int getCompletion()
    {
        return this.completion;
    }

    public int getProcessNumber()
    {
        return this.processNumber;
    }

    public boolean getFlagged()
    {
        return this.isProcessed;
    }

    public int getStartsAt() 
    {
        return this.startsAt;
    }

    public int getStatus()
    {
        return this.status;
    }

    public int getRunningTime()
    {
        return this.runningTime;
    }

    public int getOGStart()
    {
        return this.ogStart;
    }

    // returning status as string
    public String getStatusLine()
    {
        if(this.status == 0)
        {
            return "NOT STARTED";
        }
        else if(this.status == 1)
        {
            return "RUNNING";
        }
        else if(this.status == 2)
        {
            return "PAUSED";
        }
        else
        {
            return "FINISHED";
        }
    }
}