public class Process
{
    // class attributes
    private String processId;
    private double arrive;
    private double execution;
    private int priority;

    // constructor
    public Process()
    {
        this.processId = "test";
    }

    public Process(String pID, double a, double e, int p)
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

    public void setArrive(double a)
    {
        this.arrive = a;
    }

    public void setExecution(double e)
    {
        this.execution = e;
    }

    public void setPriority(int p)
    {
        this.priority = p;
    }

    // accessorsa
    public String getId()
    {
        return this.processId;
    }

    public double getArrive()
    {
        return this.arrive;
    }

    public double getExecution()
    {
        return this.execution;
    }

    public int getPriority()
    {
        return this.priority;
    }

}