import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class PRR
{
    // attributes
    private ArrayList<Process> PRRList;
    private ArrayList<String> tList;
    private ArrayList<Process> fList;
    private int twt;        // total waiting time
    private int tta;        // total turnaround time

    // constructor
    public PRR()
    {
        this.PRRList = new ArrayList<Process>();
        this.tList = new ArrayList<String>();
        this.fList = new ArrayList<Process>();
        this.twt = 0;
        this.tta = 0;
    }

    // methods
    public void feedProcess(ArrayList<Process> ogList, int dTime)
    {
        // deep element arraylist cloning
        for(int i = 0; i < ogList.size(); i++)
        {
            this.PRRList.add(ogList.get(i));
        }
        Collections.sort(PRRList, new sortByArrival());

        int cpuWatch = 0;
        ArrayList<Process> compMini = new ArrayList<Process>();
        sortArrive(PRRList);
        Process cProcess = null;

        while(PRRList.size() > 0)
        {
            // getting qualified processes
            for(int i = 0; i < PRRList.size(); i++)
            {
                if((PRRList.get(i).getArrive() <= cpuWatch) && (PRRList.get(i).getStatus() != 3))
                {
                    compMini.add(PRRList.get(i));
                }
            }

            if(compMini.size() == 0)
            {
                cpuWatch++;
                continue;
            }

            // running the processes
            Collections.sort(compMini, new sortByPriority());

            // NOT STARTED
            if(compMini.get(0).getStatus() == 0)
            {
                cpuWatch += dTime;

                compMini.get(0).setStatus(1);
                compMini.get(0).setStartsAt(cpuWatch);
                compMini.get(0).setOGStart(cpuWatch);

                if(compMini.get(0).isHPC())
                {
                    for(int i = 0; i < 4; i++)
                    {
                        if(compMini.get(0).getRunningTime() != compMini.get(0).getExecution())
                        {
                            compMini.get(0).iterateRun();
                            cpuWatch++;
                        }
                        else
                        {
                            break;
                        }
                    }
                }
                else
                {
                    for(int i = 0; i < 2; i++)
                    {
                        if(compMini.get(0).getRunningTime() != compMini.get(0).getExecution())
                        {
                            compMini.get(0).iterateRun();
                            cpuWatch++;
                        }
                        else
                        {
                            break;
                        }
                    }
                }

                if(tList.size() == 0)
                {
                    String feed = "T" + compMini.get(0).getStartsAt() + ": " + compMini.get(0).getId() + "(" + compMini.get(0).getPriority() + ")";
                    tList.add(feed);
                }

                if(cProcess != null)
                {
                    cProcess.setStatus(2);
                }

            }
            // RUNNING
            else if(compMini.get(0).getStatus() == 1)
            {
                compMini.get(0).iterateRun();
            }
            // PAUSED
            else if(compMini.get(0).getStatus() == 2)
            {
                cpuWatch += dTime;
                compMini.get(0).setStartsAt(cpuWatch);
                
                if(compMini.get(0).isHPC())
                {
                    for(int i = 0; i < 4; i++)
                    {
                        if(compMini.get(0).getRunningTime() != compMini.get(0).getExecution())
                        {
                            compMini.get(0).iterateRun();
                            cpuWatch++;
                        }
                        else
                        {
                            break;
                        }
                    }
                }
                else
                {
                    for(int i = 0; i < 2; i++)
                    {
                        if(compMini.get(0).getRunningTime() != compMini.get(0).getExecution())
                        {
                            compMini.get(0).iterateRun();
                            cpuWatch++;
                        }
                        else
                        {
                            break;
                        }
                    }
                }

                compMini.get(0).setStatus(1);
            }

            // FINISHED
            if(compMini.get(0).getRunningTime() == compMini.get(0).getExecution())
            {
                compMini.get(0).setStatus(3);
                compMini.get(0).setCompletion(cpuWatch);
                fList.add(compMini.get(0));
                PRRList.remove(compMini.get(0));
            }

            // selecting current process for next lot
            if(cProcess == null)
            {
                cProcess = compMini.get(0);
            }
            else if(cProcess != compMini.get(0))
            {
                cProcess = compMini.get(0);
                String feed = "T" + cProcess.getStartsAt() + ": " + cProcess.getId() + "(" + cProcess.getPriority() + ")";
                tList.add(feed);
            }
            
            compMini.clear();
        }

        Collections.sort(fList, new sortByProcessId());
        for(int i = 0; i < fList.size(); i++)
        {
            // calculating turnaround/waiting times
            fList.get(i).setTurnAround(fList.get(i).getCompletion() - fList.get(i).getArrive());
            fList.get(i).setWaiting(fList.get(i).getTurnAround() - fList.get(i).getExecution());

            // accumulating total turnaround/waiting times
            tta += fList.get(i).getTurnAround();
            twt += fList.get(i).getWaiting();
        }

        // TESTING
        System.out.println("id\tarrive\trun\texec\tcomplet\tstarts\tturnAr\twaiting\tstatus");
        for(int i = 0; i < fList.size(); i++)
        {
            System.out.println(fList.get(i).getId() + "(" + fList.get(i).getPriority() + ")" + "\t" + fList.get(i).getArrive() + "\t" + fList.get(i).getRunningTime() + "\t" + fList.get(i).getExecution() + "\t" + fList.get(i).getCompletion() + "\t" + fList.get(i).getOGStart() + "\t" + fList.get(i).getTurnAround() + "\t" + fList.get(i).getWaiting() + "\t" + fList.get(i).getStatusLine());
        }
        System.out.println("********************************************************************************");

    }

    // sorting in accordance to arrival
    public ArrayList<Process> sortArrive(ArrayList<Process> processList)
    {
        Collections.sort(processList, Comparator.comparing(Process::getArrive)
            .thenComparing(Process::getArrive));
        return processList;
    }

    // start time | process id | process priority
    public void results()
    {
        for(int i = 0; i < tList.size(); i++)
        {
            System.out.println(tList.get(i));
        }
        System.out.println();
    }

    // process id | turnaround time | waiting time
    public void report()
    {
        Collections.sort(fList, new sortByProcessId());
        System.out.println("Process\tTurnaround Time\tWaiting Time");
        for(int  i = 0 ; i< fList.size();  i++)
        {
            System.out.println(fList.get(i).getId() + "\t" + fList.get(i).getTurnAround() + "\t\t" + fList.get(i).getWaiting() );
        }
        System.out.println();
    }

    // TODO: needs to be finished
    public double getAverageTurnaroundTime()
    {
        return (double)this.tta / this.fList.size();
    }

    // TODO: needs to be finished
    public double getAverageWaitingTime()
    {
        return (double)this.twt / this.fList.size();
    }
}