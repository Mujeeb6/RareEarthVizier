package cwk4; 


/**
 * Details of your team
 * 
 * @author (CS16)
 * @version (25/04/2024)
 */
public class Teamwork
{
    private String[] details = new String[12];
    
    public Teamwork()
    {   // in each line replace the contents of the String 
        // with the details of your team member
        // Please list the member details alphabetically by surname 
        // i.e. the surname of member1 should come alphabetically 
        // before the surname of member 2...etc
        details[0] = "CS16";
        
        details[1] = "Syed";
        details[2] = "Sajjaduddin";
        details[3] = "22019321";

        details[4] = "Syed";
        details[5] = "Abdul Raheem Uddin";
        details[6] = "22019586";

        details[7] = "Butt";
        details[8] = "Muhammad Mujeeb";
        details[9] = "22015855";


        details[10] = "Kumar";
        details[11] = "Neeraj";
        details[12] = "21082965";

    }
    
    public String[] getTeamDetails()
    {
        return details;
    }
    
    public void displayDetails()
    {
        for(String temp:details)
        {

            System.out.println(temp.toString());
        }
    }
}
        
