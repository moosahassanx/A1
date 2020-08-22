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
                    while(newText.equals("END"))
                    {
                        //
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