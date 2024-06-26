import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Admin extends User {
    static ArrayList<User> AllUsers;

    public Admin() {
        // Load all users into the allUsers ArrayList
        AllUsers = FileManager.loadAllUsersFromFile();
    }

    public void addUser() {

        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the new user details:");
            System.out.print("Username: ");
            String newUsername = scanner.nextLine();
            if (usernameExists(newUsername)) {
                System.out.println(ConsoleInterface.ANSI_RED+"User with the given username already exists. Cannot add duplicate users.");
                return;
            }
            System.out.print("Password: ");
            String newPassword = scanner.nextLine();
            System.out.print("Designation: ");
            String newDesignation = scanner.nextLine();
            if (newDesignation.equals("teacher")) {
                Teacher t = new Teacher();
                t.setUsername(newUsername);
                t.setPassword(newPassword);
                t.setDesignation(newDesignation);

                System.out.print("Name: ");
                String name = scanner.nextLine();
                t.setName(name);
                System.out.print("Subject: ");
                String subject = scanner.nextLine();
                t.setSubject(subject);
                System.out.print("Enter Relevant Department: ");
                String d = scanner.nextLine(); // eneter teacher department
                t.setTdept(d);
                FileManager.writeTeacherToFile(t, "Teachers.txt");
                AllUsers.add(t);
                FileManager.writeUserToFile(t, "Authen.txt"); 
                System.out.println(ConsoleInterface.GREEN_BOLD+"User added successfully."+ConsoleInterface.GREEN_BOLD);

            }


            if(newDesignation.equals("employee"))
            {
                Employee e=new Employee();
                e.setUsername(newUsername);
                e.setPassword(newPassword);
                e.setDesignation(newDesignation);
                

                System.out.print("Name: ");
                String name = scanner.nextLine();
                e.setName(name);
                System.out.print("Employee Department: ");
                String deptName = scanner.nextLine();
                
                if(!(deptName.equals("it") || deptName.equals("admin") || deptName.equals("accounts")))
                {
                    while (!(deptName.equals("it") || deptName.equals("admin") || deptName.equals("accounts"))) {
                        System.out.print("No such department exists .Enter Employee Department again: ");
                        deptName = scanner.nextLine();
                    }
                }
                e.setDepartment(deptName);
                FileManager.writeEmployeeToFile(e, "Employees.txt");
                AllUsers.add(e);
                FileManager.writeUserToFile(e, "Authen.txt");
                 System.out.println(ConsoleInterface.GREEN_BOLD+"User added successfully.");

            }

            if(newDesignation.equals("manager"))
            {
                Manager m=new Manager();
                m.setUsername(newUsername);
                m.setPassword(newPassword);
                m.setDesignation(newDesignation);
                

                System.out.print("Name: ");
                String name = scanner.nextLine();
                m.setName(name);
                System.out.print("Department: ");
                String deptName = scanner.nextLine();
                
                if(!(deptName.equals("it") || deptName.equals("admin") || deptName.equals("accounts")))
                {
                    while (deptName.equals("it") || deptName.equals("admin") || deptName.equals("accounts")) {
                        System.out.print("No such department exists .Enter Employee Department again: ");
                        deptName = scanner.nextLine();
                    }
                }
                m.setDepartment(deptName);
                File file = new File("Managers.txt");

                if (file.exists()) {
                ArrayList<Manager> allManagers=new ArrayList<>(FileManager.loadManagersFromFile("Managers.txt"));
                boolean check=hasManager(allManagers,deptName);
                 if(check == false)
                {
                     FileManager.writeManagerToFile(m, "Managers.txt");
                     FileManager.writeUserToFile(m, "Authen.txt");
                     AllUsers.add(m);
                     System.out.println(ConsoleInterface.GREEN_BOLD+"User added successfully.");
                }
                else{
                    System.out.println(ConsoleInterface.RED_BOLD+"Department already has a manager");
                }
                }
                else{
              
                     FileManager.writeManagerToFile(m, "Managers.txt");
                     FileManager.writeUserToFile(m, "Authen.txt");
                     AllUsers.add(m);
                     System.out.println("User added successfully.");
                }
                
            }

            // Create a new User object

            // Add the new user to the allUsers ArrayList

            // Append the new user details to the Authen.txt file

           
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the username to remove: ");
        String usernameToRemove = scanner.nextLine();

        // Find the user in the allUsers ArrayList
        User userToRemove = null;
        for (User user : AllUsers) {
            if (user.getUsername().equals(usernameToRemove)) {
                userToRemove = user;
                break;
            }
        }

        if (userToRemove != null) {
            // Remove the user from the allUsers ArrayList
            AllUsers.remove(userToRemove);

            // Remove the user from the Authen.txt file
            FileManager.writeUsersToFile(AllUsers, "Authen.txt");

            // If the user is a teacher, remove them from the Teachers.txt file
            if (userToRemove.designation.equals("teacher")) {
                
                FileManager.removeTeacherFromFile(usernameToRemove, "Teachers.txt");
            }

            if (userToRemove.designation.equals("employee")) {
               
                FileManager.removeEmployeeFromFile(usernameToRemove, "Employees.txt");
            }

            if (userToRemove.designation.equals("manager")) {
               
                FileManager.removeEmployeeFromFile(usernameToRemove, "Managers.txt");
            }
    
    

            System.out.println(ConsoleInterface.ANSI_GREEN+"User removed successfully.");
        } else {
            System.out.println("User not found.");
        }
    }

    public static void displayAllUsers() {
        System.out.println("All Users in the System:");
        System.out.println("Username\tPassword\tDesignation");
        for (User user : AllUsers) {
            System.out.println(ConsoleInterface.ANSI_CYAN+"__________________________________________________________"+ConsoleInterface.ANSI_CYAN);
            System.out.println("");
            System.out.println(ConsoleInterface.WHITE+user.getUsername() + "\t\t" + user.getPassword() + "\t\t" + user.getDesignation());
            System.out.println(ConsoleInterface.ANSI_CYAN+"__________________________________________________________"+ConsoleInterface.ANSI_CYAN);
        }

    }

    // Helper method to check if a username already exists
    private boolean usernameExists(String username) {
        return AllUsers.stream().anyMatch(user -> user.getUsername().equals(username));
    }

    
    private boolean hasManager(ArrayList<Manager> managers,String department)
    {
        for (Manager manager : managers) {
            if (manager.getDepartment().equalsIgnoreCase(department)) {
                return true; // Department already has a manager
            }
        }
        return false; // Department does not have a manager

    }

}
