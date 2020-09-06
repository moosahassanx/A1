public class Process
{
    // class attributes
    private String processId;
    private int processNumber;
    private int arrive;
    private int execution;
    private int turnaround;
    private int waiting;
    private int completion;
    private int priority;
    private boolean isProcessed;
    private int startsAt;
    private int status;     // 0 = NOT STARTED,     1 = RUNNING,    2 = PAUSED,     3 = FINISHED
    private int runningTime;

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
    }
    public Process(String pID, int a, int e, int p)
    {
        this.processId = pID;
        int test = processId.charAt(1);
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
    }

    // mutators
    public void setId(String i)
    {
        this.processId = i;
    }

    public void setArrive(int a)
    {
        this.arrive = a;
    }

    public void setExecution(int e)
    {
        this.execution = e;
    }

    public void setPriority(int p)
    {
        this.priority = p;
    }

    public void setTurnAround(int t)
    {
        this.turnaround = t;
    }

    public void setWaiting(int w)
    {
        this.waiting = w;
    }

    public void setCompletion(int c)
    {
        this.completion = c;
    }

    public void setFlag(boolean f)
    {
        this.isProcessed = f;
    }

    public void clearCalculations()
    {
        this.turnaround = 0;
        this.waiting = 0;
        this.completion = 0;
    }
    
    public void setStartsAt(int s)
    {
        this.startsAt = s;
    }

    public void setStatus(int s)
    {
        this.status = s;
    }

    public void setRunningTime(int r)
    {
        this.runningTime = r;
    }
    public void iterateRun()
    {
        this.runningTime++;
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

    public int getStartsAt() {
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

    // deep element cloning
    @Override
    protected Object clone() throws CloneNotSupportedException {
        Process clone = null;
        try
        {
            clone = (Process) super.clone();
        } 
        catch (CloneNotSupportedException e) 
        {
            throw new RuntimeException(e);
        }
        return clone;
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