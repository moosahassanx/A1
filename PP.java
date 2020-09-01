import java.util.ArrayList;

public class PP
{
    private ArrayList<Process> PPList;
    private int twt;        // total waiting time
    private int tta;        // total turnaround time

    public PP()
    {
        this.PPList = new ArrayList<Process>();
        this.twt = 0;
        this.tta = 0;
    }

    public void feedProcess(ArrayList<Process> ogList, int dTime)
    {
        for(int i = 0; i < ogList.size(); i++)
        {
            this.PPList.add(ogList.get(i));
        }

        /*
        System.out.println("id\tarrive\texec\tcomplet\tstarts\tturnAr\twaiting");
        for(int i = 0; i < PPList.size(); i++)
        {
            System.out.println(PPList.get(i).getId() + "\t" + PPList.get(i).getArrive() + "\t" + PPList.get(i).getExecution() + "\t" + PPList.get(i).getCompletion() + "\t" + PPList.get(i).getStartsAt() + "\t" + PPList.get(i).getTurnAround() + "\t" + PPList.get(i).getWaiting());
        }

        System.out.println("*********************************************************");
        */
    }

    public void results()
    {
        System.out.println("results() method has not been made. \n");
    }

    public void report()
    {
        System.out.println("report() method has not been made. \n");
    }

    // calculating averages
    public double getAverageWaitingTime()
    {
        return this.twt / this.PPList.size();
    }
    public double getAverageTurnaroundTime()
    {
        return this.tta / this.PPList.size();
    }

}

/* COPY THIS FOR TESTING
    System.out.println("id\tarrive\texec\tcomplet\tstarts\tturnAr\twaiting");
    for(int i = 0; i < bList.size(); i++)
    {
        System.out.println(bList.get(i).getId() + "\t" + bList.get(i).getArrive() + "\t" + bList.get(i).getExecution() + "\t" + bList.get(i).getCompletion() + "\t" + bList.get(i).getStartsAt() + "\t" + bList.get(i).getTurnAround() + "\t" + bList.get(i).getWaiting());
    }
*/