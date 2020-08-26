public class Process
{
    // class attributes
    private String processId;
    private int arrive;
    private int execution;
    private int priority;

    // constructor
    public Process()
    {
        this.processId = "test";
    }

    public Process(String pID, int a, int e, int p)
    {
        this.processId = pID;
        this.arrive = a;
        this.execution = e;
        this.priority = p;
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

}