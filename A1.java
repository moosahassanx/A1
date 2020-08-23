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
                            System.out.println("Display: " + newText);
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

                        System.out.println("Identification: " + newText);
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
    }
}