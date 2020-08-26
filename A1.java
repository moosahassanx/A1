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
                            // System.out.println("Time for running the dispatcher: " + newText);
                        }

                    }

                    // System.out.println("End reached. \n");
                }
                // read EOF
                else if(newText.equals("EOF"))
                {
                    // System.out.println("End of file.");
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

                        // System.out.println("Process ID: " + processId);
                    }

                    newText = file.next();

                    if(newText.equals("Arrive:"))
                    {
                        newText = file.next();

                        arriveTime = Integer.parseInt(newText);

                        // System.out.println("Arrive: " + arriveTime);
                    }

                    newText = file.next();

                    if(newText.equals("ExecSize:"))
                    {
                        newText = file.next();

                        executionSize = Integer.parseInt(newText);

                        // System.out.println("ExecSize: " + executionSize);
                    }

                    newText = file.next();

                    if(newText.equals("Priority:"))
                    {
                        newText = file.next();

                        priorityLevel = Integer.parseInt(newText);

                        // System.out.println("Priority: " + newText);
                    }

                    newText = file.next();

                    if(newText.equals("END"))
                    {
                        // System.out.println("End reached.");
                    }

                    // make new object
                    Process processObject = new Process(processId, arriveTime, executionSize, priorityLevel);

                    // add to arraylist
                    processList.add(processObject);
                    // System.out.println("process \"" + processObject.getId() + "\" has been added. \n");
                    
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
        FirstCome.feedProcess(processList);

        // System.out.println("*******************************************************");
        
        // output
        System.out.println("FCFS:");
        System.out.println("T1: p1()");
        System.out.println("T12: p2()");
        System.out.println("T14: p3()");
        System.out.println("T17: p4()");
        System.out.println("T9: p5()");
        System.out.println();

        FirstCome.report();

        System.out.println("Process \tTurnaround Time Waiting Time");
        System.out.println("p1 \t");
        System.out.println("p2 \t");
        System.out.println("p3 \t");
        System.out.println("p4 \t");
        System.out.println("p5 \t");
        System.out.println();

        /*
        System.out.println("SPN:");
        System.out.println("T1: p2()");
        System.out.println("T3: p4()");
        System.out.println("T5: p3()");
        System.out.println("T8: p5()");
        System.out.println("T14: p1()");
        System.out.println();

        System.out.println("Process \tTurnaround Time Waiting Time");
        System.out.println("p1 \t");
        System.out.println("p2 \t");
        System.out.println("p3 \t");
        System.out.println("p4 \t");
        System.out.println("p5 \t");
        System.out.println();

        System.out.println("PP:");
        System.out.println("T1: p1()");
        System.out.println("T12: p4()");
        System.out.println("T14: p2()");
        System.out.println("T16: p3()");
        System.out.println("T19: p5()");
        System.out.println();

        System.out.println("Process \tTurnaround Time Waiting Time");
        System.out.println("p1 \t");
        System.out.println("p2 \t");
        System.out.println("p3 \t");
        System.out.println("p4 \t");
        System.out.println("p5 \t");
        System.out.println();

        System.out.println("PRR:");
        System.out.println("T1: p1()");
        System.out.println("T6: p2()");
        System.out.println("T8: p3()");
        System.out.println("T11: p4()");
        System.out.println("T13: p5()");
        System.out.println("T16: p1()");
        System.out.println("T21: p5()");
        System.out.println("T24: p1()");
        System.out.println("T27: p5()");
        System.out.println();

        System.out.println("Process \tTurnaround Time Waiting Time");
        System.out.println("p1 \t");
        System.out.println("p2 \t");
        System.out.println("p3 \t");
        System.out.println("p4 \t");
        System.out.println("p5 \t");
        System.out.println();

        System.out.println("Summary");
        System.out.println("Algorithm \t Average Turnaround Time \t Average Waiting Time");
        System.out.println("FCFS \t ");
        System.out.println("SPN \t ");
        System.out.println("PP \t ");
        System.out.println("PRR \t ");
        System.out.println();
        */
    }
}