// importing java libraries (file scanner and string output)
import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

class A1
{
    public static void main (final String[] args) throws IOException 
    {
        // create file scanner
		final Scanner file = new Scanner(new File(args[0]));
		// declare and instantiate string to store scanner inputs
        String newText = "";
        
        ArrayList<Process> processList = new ArrayList<Process>();
        int dTime = 0;

        try
        {
            while(file.hasNext())
            {
                newText = file.next();

                // read BEGIN
                if(newText.equals("BEGIN"))
                {
                    // System.out.println("BEGIN");
                    
                    newText = file.next();

                    // read END
                    while(!newText.equals("END"))
                    {
                        newText = file.next();

                        if(newText.equals("END"))
                        {
                            break;
                        }
                        else
                        {
                            dTime = Integer.parseInt(newText);
                        }

                    }

                }

                // read EOF
                else if(newText.equals("EOF"))
                {
                    //
                }

                // read details
                else
                {
                    // the details
                    String processId = "ID-NOT-SPECIFIED";
                    int arriveTime = 0;
                    int executionSize = 0;
                    int priorityLevel = 0;

                    if(newText.equals("ID:"))
                    {
                        processId = file.next();
                    }

                    newText = file.next();

                    if(newText.equals("Arrive:"))
                    {
                        newText = file.next();

                        arriveTime = Integer.parseInt(newText);
                    }

                    newText = file.next();

                    if(newText.equals("ExecSize:"))
                    {
                        newText = file.next();

                        executionSize = Integer.parseInt(newText);
                    }

                    newText = file.next();

                    if(newText.equals("Priority:"))
                    {
                        newText = file.next();

                        priorityLevel = Integer.parseInt(newText);
                    }

                    newText = file.next();

                    if(newText.equals("END"))
                    {
                        //
                    }

                    // make new object
                    Process processObject = new Process(processId, arriveTime, executionSize, priorityLevel);

                    // add to arraylist
                    processList.add(processObject);

                }
            }
        }

        catch(final Exception e)
        {
            System.out.println("Error with reading files");
        }

        // close file after reading all characters
        file.close();

        // creating algorithms
        FCFS FirstCome = new FCFS();
        SPN ShortProcess = new SPN();
        PP Preemptive = new PP();
        PRR RoundRobin = new PRR();

        // running algorithms
        FirstCome.feedProcess(processList, dTime);
        ShortProcess.feedProcess(processList, dTime);
        
        // output
        System.out.println("FCFS:");
        FirstCome.results();
        FirstCome.report();

        System.out.println("SPN:");



        // final summary
        System.out.println("Summary:");
        System.out.println("Algorithm\tAverage Turnaround Time\tAverage Waiting Time");
        System.out.println("FCFS: \t\t" + FirstCome.getAverageTurnaroundTime() + "\t\t\t" + FirstCome.getAverageWaitingTime());     // FCFS
        System.out.println("SPN: \t\t");
    }
}