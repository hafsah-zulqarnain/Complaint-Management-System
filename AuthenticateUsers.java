import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class AuthenticateUsers {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String WHITE = "\u001B[37m";
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
   
        try {
            // Loading all the complaints 
            ClearConsole.clearScreen();
            ConsoleInterface.drawBorder();
            boolean check = false;
            System.out.println(ConsoleInterface.WHITE+"Username: " );
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
                    System.out.println(ANSI_GREEN+"Authentication successful for user"+ANSI_GREEN);
                    check = true;
                    break;
                }
            }
            if (check == false) {
                System.out.println(ConsoleInterface.RED_BOLD+"Incorrect Username or password"+ConsoleInterface.RED_BOLD);
            }
            if (check == true) {
                if (designation.equals("teacher")) {
                        // will make objects and load data from files
                        // also show different menus to different users
                        boolean ex=true;
                        while (ex) {
                            
                        Teacher authenticatedTeacher = new Teacher();
                        authenticatedTeacher.setUsername(checkUsername);
                        //authenticatedTeacher.setPassword(checkPassword);
                        String option=scanner.nextLine();
                        ClearConsole.clearScreen();
                        System.out.println(ConsoleInterface.WHITE+"Press 1 to view Complaints: ");
                        System.out.println("Press 2 to file a new complaint: ");
                        System.out.println("Press 3 to view your profile: ");
                        System.out.println("Press 4 to view your notifications: ");
                        System.out.println("Press 5 to view your complaint solution: ");
                        System.out.println("Press 6 to logout: ");
                        
                        if(option.equals("1"))
                        {
                            
                            authenticatedTeacher.loadComplaintsFromFile(username);
                            System.out.println(ConsoleInterface.ANSI_CYAN+"__________________________________________________________"+ConsoleInterface.ANSI_CYAN);
                            System.out.println("");
                            authenticatedTeacher.displayComplaints();
                            System.out.println(ConsoleInterface.ANSI_CYAN+"__________________________________________________________"+ConsoleInterface.ANSI_CYAN);
                       
                        }
                        if(option.equals("2"))
                        {
                            authenticatedTeacher.enterComplaint();
                            scanner.nextLine();
                        }
                        if(option.equals("3"))
                        {
                            authenticatedTeacher.loadTeacherInfo();
                            System.out.println(ConsoleInterface.ANSI_CYAN+"__________________________________________________________"+ConsoleInterface.ANSI_CYAN);
                            System.out.println("");
                            authenticatedTeacher.print();
                            System.out.println(ConsoleInterface.ANSI_CYAN+"__________________________________________________________"+ConsoleInterface.ANSI_CYAN);

                        }
                        if(option.equals("4"))
                        {
                            System.out.println(ConsoleInterface.ANSI_CYAN+"__________________________________________________________"+ConsoleInterface.ANSI_CYAN);
                            System.out.println("");
                            authenticatedTeacher.viewNotifications();
                            System.out.println(ConsoleInterface.ANSI_CYAN+"__________________________________________________________"+ConsoleInterface.ANSI_CYAN);
                        }
                        if(option.equals("5"))
                        {
                            scanner.nextLine();
                            ClearConsole.clearScreen();
                            System.out.println("Enter complaint no: ");
                            int complaintno=scanner.nextInt();
                            scanner.nextLine();
                            System.out.println(ConsoleInterface.ANSI_CYAN+"__________________________________________________________"+ConsoleInterface.ANSI_CYAN);
                            System.out.println("");
                            authenticatedTeacher.ViewAssignedComplaints(complaintno);;
                            System.out.println(ConsoleInterface.ANSI_CYAN+"__________________________________________________________"+ConsoleInterface.ANSI_CYAN);
                            System.out.println(ConsoleInterface.WHITE+"Are you satisfied with the solution: ");
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
                        if(option.equals("6"))
                        {
                            ex=false;
                        }
                    }
                }
                if (designation.equals("admin")) {
                    boolean ex=true;
                    while (ex) {
                        
                    Admin a=new Admin();
                    scanner.nextLine();
                    ClearConsole.clearScreen();
                    System.out.println(ConsoleInterface.WHITE_BOLD+"Press 1 to view all Users: ");
                    System.out.println("Press 2 to add new employee: ");
                    System.out.println("Press 3 to delete employee: ");
                    System.out.println("Press 4 to logout: ");
                    String option=scanner.nextLine();
                    ClearConsole.clearScreen();
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
                        if(option.equals("4"))
                        {
                            ex=false;
                        }
                    }
                }
                if (designation.equals("campusDirector")) {

                }
                if (designation.equals("employee")) {

                    boolean ex=true;
                    while (ex) {
                    Employee emp = new Employee();
                    emp.setUsername(checkUsername);
                    //emp.setPassword(checkPassword);
                    scanner.nextLine();
                    ClearConsole.clearScreen();
                    System.out.println(ConsoleInterface.WHITE_BOLD+"Press 1 to view your profile: ");
                    System.out.println("Press 2 to view your Assignemnts: ");
                    System.out.println("Press 3 to complete an assignemnts: ");
                    System.out.println("Press 4 to logout: ");
                    String option=scanner.nextLine();
                    if(option.equals("1"))
                    {
                        emp.loadEmployeeInfo();
                        System.out.println(ConsoleInterface.ANSI_CYAN+"__________________________________________________________"+ConsoleInterface.ANSI_CYAN);
                        System.out.println("");
                        emp.displayEmployeeInfo();
                        System.out.println(ConsoleInterface.ANSI_CYAN+"__________________________________________________________"+ConsoleInterface.ANSI_CYAN);

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
                        ClearConsole.clearScreen();
                        System.out.println(ConsoleInterface.WHITE+"Enter complaint no: ");
                        int complaintno=scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("Enter Solution: ");
                        String sol= scanner.nextLine();
                        emp.markAssignmentCompleted(complaintno,dept,sol);
                       
                        
                    }
                    if(option.equals("4"))
                    {
                        ex=false;
                    }
                }

                }
               
                if (designation.equals("manager")) {
                    boolean ex=true;
                    while (ex) {
                        Manager m=new Manager();
                        
                    
                    m.setUsername(checkUsername);
                   // m.setPassword(checkPassword);
                    m.loadManagerInfo();
                    scanner.nextLine();
                    ClearConsole.clearScreen();
                    System.out.println(WHITE+"Press 1 to view complaints from your department: "+WHITE);
                    System.out.println(WHITE+"Press 2 to view your profile: "+WHITE);
                    System.out.println(WHITE+"Press 3 to view employees in your department: "+WHITE);
                    System.out.println(WHITE+"Press 4 to assign employees complaints: "+WHITE);
                    System.out.println(WHITE+"Press 5 to view notifications "+WHITE);
                    System.out.println(WHITE+"Press 6 to view employees assignments solution: "+WHITE);
                    System.out.println(WHITE+"Press 7 Logout: "+WHITE);
                    String option=scanner.nextLine();
                    if(option.equals("1"))
                    {
                      
                      System.out.println("");
                      m.loadManagerComplaints();
                      m.DisplayDeptComplaints();
                    }
                    
                    if(option.equals("2"))
                    {
                        System.out.println(ConsoleInterface.ANSI_CYAN+"__________________________________________________________"+ConsoleInterface.ANSI_CYAN);
                        System.out.println("");
                         m.print();
                        System.out.println(ConsoleInterface.ANSI_CYAN+"__________________________________________________________"+ConsoleInterface.ANSI_CYAN);
                        scanner.nextLine();

                    }
                    if(option.equals("3"))
                    {
                         m.loadManagerEmployees();
                         m.DisplayDeptEmployees();
                         scanner.nextLine();
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
                            System.out.println(ConsoleInterface.ANSI_RED+"Complaint doesn't exists in this department"+ConsoleInterface.ANSI_RED);
                        }
                        if(EmployeeExists==false)
                        {
                            System.out.println(ConsoleInterface.ANSI_RED+"No such Employee exists in this department"+ConsoleInterface.ANSI_RED);

                        }
                        if(complaintExists&& EmployeeExists)
                        {
                            Job j=new Job(complaintId, employeeUsername);
                           // boolean s=j.getJobStatus();
                          // System.out.println(s);
                            m.assignComplaint(j);
                            j.addObserver(m);

                        }
                        scanner.nextLine();
                        
                     }
                     if(option.equals("5"))
                     {
                        m.viewNotifications();
                     }
                     if(option.equals("6"))
                     {
                        scanner.nextLine();
                        ClearConsole.clearScreen();
                        System.out.println("Enter complaint no: ");
                        int complaintno=scanner.nextInt();
                        scanner.nextLine();
                        m.ViewAssignedComplaints(complaintno);
                        System.out.println(ConsoleInterface.WHITE+"Are you satisfied with the solution: ");
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
                     if(option.equals("7"))
                     {
                        ex=false;
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

    private static void ConsoleInterfacedrawBorder() {
    }

    private static boolean authenticateUser(String username, String password, String u, String p) {

        if (username.equals(u) && password.equals(p)) {
            return true;
        }
        return false;
    }
    
    
   
}
