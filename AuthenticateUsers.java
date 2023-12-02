import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class AuthenticateUsers {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            // Loading all the complaints 
            ArrayList<Complaint> c = new ArrayList<>(); // Initialize the ArrayList
            FileManager.loadAllComplaintsFromFile(c);
            

            boolean check = false;
            System.out.println("Username: ");
            String checkUsername = scanner.nextLine();
            System.out.println("Password: ");
            String checkPassword = scanner.nextLine();
            String filePath = "Authen.txt";
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            // Skip the header line
            reader.readLine();
           // ...

            String username = null;
            String password = null;
            String designation = null;
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
                    check = true;
                    break;
                }
            }
            if (check == false) {
                System.out.println("Incorrect Username or password");
            }
            if (check == true) {
                if (designation.equals("teacher")) {
                    // will make objects and load data from files
                    // also show different menus to different users
                    Teacher authenticatedTeacher = new Teacher();
                    authenticatedTeacher.username=checkUsername;
                    System.out.println("Press 1 to view Complaints: ");
                    System.out.println("Press 2 to file a new complaint: ");
                    String option=scanner.nextLine();
                    
                    if(option.equals("1"))
                    {
                        authenticatedTeacher.loadComplaintsFromFile(username,c);
                        authenticatedTeacher.displayComplaints();
                    }
                    if(option.equals("2"))
                    {
                        authenticatedTeacher.enterComplaint();
                    }
                }
                if (designation.equals("admin")) {
                    Admin a=new Admin();
                    System.out.println("Press 1 to view all Users: ");
                    System.out.println("Press 2 to add new employee: ");
                    System.out.println("Press 3 to delete employee: ");
              
                    String option=scanner.nextLine();
                    if(option.equals("1"))
                    {
                        a.displayAllUsers();
                    }
                    if(option.equals("2"))
                    {
                        a.addUser();
                    }
                    if(option.equals("3"))
                    {
                       a.removeUser();
                    }
                   
                }
                if (designation.equals("campusDirector")) {

                }
                if (designation.equals("employee")) {
                    Employee emp = new Employee();
                    emp.username=checkUsername;
                }
                if (designation.equals("manager")) {
                    Manager m=new Manager();
                    m.username=checkUsername;
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            scanner.close();
        }
    }

    private static boolean authenticateUser(String username, String password, String u, String p) {

        if (username.equals(u) && password.equals(p)) {
            return true;
        }
        return false;
    }
    
    
   
}
