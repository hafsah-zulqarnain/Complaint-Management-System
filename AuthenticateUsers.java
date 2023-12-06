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
                    authenticatedTeacher.setUsername(checkUsername);
                    //authenticatedTeacher.setPassword(checkPassword);
                    
                    System.out.println("Press 1 to view Complaints: ");
                    System.out.println("Press 2 to file a new complaint: ");
                    System.out.println("Press 3 to view your profile: ");
                    System.out.println("Press 4 to view your notifications: ");
                    System.out.println("Press 5 to view your complaint solution: ");
                    String option=scanner.nextLine();
                    
                    if(option.equals("1"))
                    {
                        
                        authenticatedTeacher.loadComplaintsFromFile(username);
                        authenticatedTeacher.displayComplaints();
                    }
                    if(option.equals("2"))
                    {
                        authenticatedTeacher.enterComplaint();
                    }
                    if(option.equals("3"))
                    {
                        authenticatedTeacher.loadTeacherInfo();
                        authenticatedTeacher.print();
                    }
                    if(option.equals("4"))
                    {
                        authenticatedTeacher.viewNotifications();
                    }
                    if(option.equals("5"))
                    {
                        System.out.println("Enter complaint no: ");
                        int complaintno=scanner.nextInt();
                        scanner.nextLine();
                        authenticatedTeacher.ViewAssignedComplaints(complaintno);;
                        System.out.println("Are you satisfied with the solution: ");
                        String satisfied=scanner.nextLine();
                        if(satisfied.equals("yes"))
                        {
                            authenticatedTeacher.CloseComplain(complaintno);
                        }
                        if(satisfied.equals("no"))
                        {
                           authenticatedTeacher.reAssign(complaintno);
                        }
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
                    emp.setUsername(checkUsername);
                    //emp.setPassword(checkPassword);
                    System.out.println("Press 1 to view your profile: ");
                    System.out.println("Press 2 to view your Assignemnts: ");
                    System.out.println("Press 3 to complete an assignemnts: ");
                    String option=scanner.nextLine();
                    if(option.equals("1"))
                    {
                        emp.loadEmployeeInfo();
                        emp.displayEmployeeInfo();
                    }
                     if(option.equals("2"))
                    {
                        emp.loadEmployeeInfo();
                        emp.loadAssignments();
                        emp.DisplayAssignments();
                    }

                    if(option.equals("3"))
                    {
                        emp.loadEmployeeInfo();
                       // emp.loadAssignments();
                        //emp.DisplayAssignments();
                        String dept=emp.getDepartment();

                        System.out.println("Enter complaint no: ");
                        int complaintno=scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("Enter Solution: ");
                        String sol= scanner.nextLine();
                        emp.markAssignmentCompleted(complaintno,dept,sol);
                       
                        
                    }

                }
               
                if (designation.equals("manager")) {
                    Manager m=new Manager();
                    m.setUsername(checkUsername);
                   // m.setPassword(checkPassword);
                    m.loadManagerInfo();
                    System.out.println("Press 1 to view complaints from your department: ");
                    System.out.println("Press 2 to view your profile: ");
                    System.out.println("Press 3 to view employees in your department: ");
                    System.out.println("Press 4 to assign employees complaints: ");
                    System.out.println("Press 5 to view notifications ");
                    System.out.println("Press 6 to view employees assignments solution: ");
                    String option=scanner.nextLine();
                    if(option.equals("1"))
                    {
                      m.loadManagerComplaints();
                      m.DisplayDeptComplaints();
                    }
                    
                    if(option.equals("2"))
                    {
                         m.print();
                    }
                    if(option.equals("3"))
                    {
                         m.loadManagerEmployees();
                         m.DisplayDeptEmployees();
                    }
                    if(option.equals("4"))
                    {
                        m.loadManagerComplaints();
                        m.loadManagerEmployees();
                       // m.DisplayDeptEmployees();
                        boolean complaintExists = false;
                        boolean EmployeeExists = false;
                        System.out.println("Enter complaint id:");
                        int complaintId = Integer.parseInt(scanner.nextLine().trim());
                       
                        
                        System.out.println("Enter employee username:");
                        String employeeUsername = scanner.nextLine().trim();
                        for (Complaint complaint : m.getComplaints()) {
                            if (complaint.getCid()==(complaintId)) {
                                complaintExists = true;
                               
                                break;
                            }
                        }

                         for (Employee e : m.getEmployees()) {
                           // System.out.println(e.getUsername());
                            if (e.getUsername().equals(employeeUsername)) {
                              
                                EmployeeExists = true;
                                break;
                            }
                            //e.displayEmployeeInfo();
                        }
                        
                        if(complaintExists==false)
                        {
                            System.out.println("Complaint doesn't exists in this department");
                        }
                        if(EmployeeExists==false)
                        {
                            System.out.println("No such Employee exists in this department");

                        }
                        if(complaintExists&& EmployeeExists)
                        {
                            Job j=new Job(complaintId, employeeUsername);
                           // boolean s=j.getJobStatus();
                          // System.out.println(s);
                            m.assignComplaint(j);
                            j.addObserver(m);

                        }
                        
                     }
                     if(option.equals("5"))
                     {
                        m.viewNotifications();
                     }
                     if(option.equals("6"))
                     {
                        System.out.println("Enter complaint no: ");
                        int complaintno=scanner.nextInt();
                        scanner.nextLine();
                        m.ViewAssignedComplaints(complaintno);
                        System.out.println("Are you satisfied with the solution: ");
                        String satisfied=scanner.nextLine();
                        //scanner.nextLine();
                        if(satisfied.equals("yes"))
                        {
                            m.ResolveComplain(complaintno);
                        }
                        if(satisfied.equals("no"))
                        {
                           m.reAssign(complaintno);
                        }

                     }
                    
                   
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
