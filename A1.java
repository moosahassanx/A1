// TITLE: 					Assignment1
// COURSE: 					COMP2240
// AUTHOR: 					Moosa Hassan
// STUDENT NUMBER: 			3331532
// DATE: 					13/09/2020 
// DESCRIPTION: 			Main file - reads data text files and sends it to each algorithm class

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
        final ArrayList<Process> FCFSList = new ArrayList<Process>();
        final ArrayList<Process> SPNList = new ArrayList<Process>();
        final ArrayList<Process> PPList = new ArrayList<Process>();
        final ArrayList<Process> PRRList = new ArrayList<Process>();
        int dTime = 0;

        try 
        {
            while (file.hasNext()) 
            {
                newText = file.next();

                // read BEGIN
                if (newText.equals("BEGIN")) 
                {
                    newText = file.next();

                    // read END
                    while (!newText.equals("END"))
                    {
                        newText = file.next();

                        if (newText.equals("END")) 
                        {
                            break;
                        } else 
                        {
                            dTime = Integer.parseInt(newText);
                        }
                    }

                }

                // read EOF
                else if (newText.equals("EOF")) 
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

                    if (newText.equals("ID:")) 
                    {
                        processId = file.next();
                    }

                    newText = file.next();
                    if (newText.equals("Arrive:")) 
                    {
                        newText = file.next();

                        arriveTime = Integer.parseInt(newText);
                    }

                    newText = file.next();
                    if (newText.equals("ExecSize:")) 
                    {
                        newText = file.next();

                        executionSize = Integer.parseInt(newText);
                    }

                    newText = file.next();
                    if (newText.equals("Priority:")) 
                    {
                        newText = file.next();

                        priorityLevel = Integer.parseInt(newText);
                    }

                    newText = file.next();
                    if (newText.equals("END")) 
                    {
                        //
                    }

                    // make new object
                    final Process FCFSElement = new Process(processId, arriveTime, executionSize, priorityLevel);
                    final Process SPNElement = new Process(processId, arriveTime, executionSize, priorityLevel);
                    final Process PPElement = new Process(processId, arriveTime, executionSize, priorityLevel);
                    final Process PRRElement = new Process(processId, arriveTime, executionSize, priorityLevel);

                    // add to arraylist
                    FCFSList.add(FCFSElement);
                    SPNList.add(SPNElement);
                    PPList.add(PPElement);
                    PRRList.add(PRRElement);
                }
            }
        }

        catch (final Exception e) 
        {
            System.out.println("Error with reading files");
        }

        // close file after reading all characters
        file.close();

        // creating algorithms
        final FCFS FirstCome = new FCFS();
        final SPN ShortProcess = new SPN();
        final PP Preemptive = new PP();
        final PRR RoundRobin = new PRR();

        // running algorithms
        FirstCome.feedProcess(FCFSList, dTime);
        ShortProcess.feedProcess(SPNList, dTime);
        Preemptive.feedProcess(PPList, dTime);
        RoundRobin.feedProcess(PRRList, dTime);

        // printing results  
        System.out.println("FCFS:");
        FirstCome.results();
        FirstCome.report();

        System.out.println("SPN:");
        ShortProcess.results();
        ShortProcess.report();

        System.out.println("PP:");
        Preemptive.results();
        Preemptive.report();

        System.out.println("PRR:");
        RoundRobin.results();
        RoundRobin.report();

        // final summary
        System.out.println("Summary:");
        System.out.println("Algorithm\tAverage Turnaround Time\tAverage Waiting Time");
        System.out.println("FCFS: \t\t" + FirstCome.getAverageTurnaroundTime() + "\t\t\t" + FirstCome.getAverageWaitingTime());     // FCFS
        System.out.println("SPN: \t\t" + ShortProcess.getAverageTurnaroundTime() + "\t\t\t" + ShortProcess.getAverageWaitingTime() );  // SPN
        System.out.println("PP: \t\t" + Preemptive.getAverageTurnaroundTime() + "\t\t\t" + Preemptive.getAverageWaitingTime());     // PP
        System.out.println("PRR: \t\t" + RoundRobin.getAverageTurnaroundTime() + "\t\t\t" + RoundRobin.getAverageWaitingTime());    // PRR
    }
}