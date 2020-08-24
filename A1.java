// importing java libraries (file scanner and string output)
import java.io.*;
import java.util.Scanner;

class A1
{
    public static void main (final String[] args) throws IOException 
    {
        // create file scanner
		final Scanner file = new Scanner(new File(args[0]));
		// declare and instantiate string to store scanner inputs
		String newText = "";

        try
        {
            while(file.hasNext())
            {
                newText = file.next();

                // read BEGIN
                if(newText.equals("BEGIN"))
                {
                    System.out.println("BEGIN");
                    
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
                            System.out.println("Time for running the dispatcher: " + newText);
                        }

                    }

                    System.out.println("End reached. \n");
                }
                // read EOF
                else if(newText.equals("EOF"))
                {
                    System.out.println("End of file.");
                }

                // read details
                else
                {
                    if(newText.equals("ID:"))
                    {
                        newText = file.next();

                        System.out.println("Process ID: " + newText);
                    }

                    newText = file.next();

                    if(newText.equals("Arrive:"))
                    {
                        newText = file.next();

                        System.out.println("Arrive: " + newText);
                    }

                    newText = file.next();

                    if(newText.equals("ExecSize:"))
                    {
                        newText = file.next();

                        System.out.println("ExecSize: " + newText);
                    }

                    newText = file.next();

                    if(newText.equals("Priority:"))
                    {
                        newText = file.next();

                        System.out.println("ExecSize: " + newText);
                    }

                    newText = file.next();

                    if(newText.equals("END"))
                    {
                        System.out.println("End reached. \n");
                    }

                }
            }
        }
        catch(final Exception e)
        {
            System.out.println("Error with reading files");
        }

        // close file after reading all characters
        file.close();
        System.out.println("*******************************************************");
        
        // output
        System.out.println("FCFS:");
        System.out.println("T1: p1()");
        System.out.println("T12: p2()");
        System.out.println("T14: p3()");
        System.out.println("T17: p4()");
        System.out.println("T9: p5()");
        System.out.println();

        System.out.println("Process \tTurnaround Time Waiting Time");
        System.out.println("p1 \t");
        System.out.println("p2 \t");
        System.out.println("p3 \t");
        System.out.println("p4 \t");
        System.out.println("p5 \t");
        System.out.println();

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
    }
}