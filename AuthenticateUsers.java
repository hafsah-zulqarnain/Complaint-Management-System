import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class AuthenticateUsers {
    public static void main(String[] args) {
        try {
            
            boolean check=false;
            Scanner scanner = new Scanner(System.in);
            System.out.println("Username: ");
            String checkUsername= scanner.nextLine();
            System.out.println("Password: ");
            String checkPassword= scanner.nextLine();
            String filePath = "Users.txt";
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            scanner.close();
            // Skip the header line
            reader.readLine();
            String username=null;
            String password=null;
            String designation=null;
            // Read and process each subsequent line
            String line;
            while ((line = reader.readLine()) != null) {
                // Split the line into username, password, and designation
                String[] values = line.split("\t\t");
                
                
                username = values[0];
                password = values[1];
                designation = values[2];

               
                if (authenticateUser(username, password, checkUsername, checkPassword)) {
                    System.out.println("Authentication successful for user");
                    check=true;
                    break;
                } 
            }
            if (check==false)
            {
                System.out.println("Authentication unsuccessful for user");
            }
            if(check==true)
            {
                if(designation.equals("teacher"))
                {
                    //will make objects and load data from files 
                    //also show different menus to different users
                }
                if(designation.equals("admin"))
                {
                    
                }
                if(designation.equals("campusDirector"))
                {
                    
                }
                if(designation.equals("Employee"))
                {
                    
                }
                if(designation.equals("Manager"))
                {
                    
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    private static boolean authenticateUser(String username, String password, String u, String p) {
        
        if (username.equals(u) && password.equals(p)) {
            return true;
        }
        return false;
    }
    
}