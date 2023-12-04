import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class FileManager {
     public static void loadAllComplaintsFromFile(ArrayList<Complaint> c) {
            try {
                String filePath = "Complaint.txt";
                BufferedReader reader = new BufferedReader(new FileReader(filePath));
                // Skip the header line
                reader.readLine();
        
                String line;
                while ((line = reader.readLine()) != null) {
                    try (Scanner scanner = new Scanner(line)) {
                        int cid = scanner.nextInt();
                        String s=scanner.next();
                        String type = scanner.next();
                        String teacher = scanner.next();
                        String dept = scanner.next();
                        String des = scanner.nextLine().trim(); // Read the rest of the line as the description
                        Complaint newComplaint = new Complaint(cid, des, String.valueOf(type), teacher, dept);

                        switch (s.toLowerCase()) {
                            case "new":
                                newComplaint.setState(new New());
                                break;
                            case "assigned":
                                newComplaint.setState(new Assigned());
                                break;
                            case "resolved":
                                newComplaint.setState(new Resolved());
                                break;
                            case "closed":
                                newComplaint.setState(new Closed());
                                break;
                            default:
                                // Handle unrecognized states
                                break;
                        }
                        c.add(newComplaint);
                        
                    } catch (NumberFormatException | IllegalStateException e) {
                        // Handle parsing errors or missing elements
                        e.printStackTrace();
                    }
                }
        
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        public static void writeComplaintToFile(int id,String state ,String type, String teacher, String dept, String cdes) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Complaint.txt", true))) {
            String complaintLine = String.format("%d\t\t%s\t\t%s\t\t%s\t\t%s\t\t%s", id, state ,type, teacher, dept, cdes);
            writer.newLine();
            writer.write(complaintLine);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
       
public static int getRecentComplaintId() {
    try (BufferedReader reader = new BufferedReader(new FileReader("Complaint.txt"))) {
        String line;
        int recentId = 0;
        reader.readLine();
        while ((line = reader.readLine()) != null) {
            String[] values = line.split("\\s+"); // Split by whitespace

            // Skip the line if it doesn't start with an integer
            if (values.length == 0) {
                continue;
            }

            int id = Integer.parseInt(values[0]);

            if (id > recentId) {
                recentId = id;
            }
        }

        // If recentId is still 0, it means there are no existing complaints
        // In that case, return 1 as the next available ID
        return (recentId == 0) ? 1 : (recentId + 1);
    } catch (IOException | NumberFormatException e) {
        e.printStackTrace();
        return 0;
    }
}


        public static ArrayList<User> loadAllUsersFromFile() {
            ArrayList<User> allUsers = new ArrayList<>();
            try {
                String filePath = "Authen.txt";
                BufferedReader reader = new BufferedReader(new FileReader(filePath));
    
                // Skip the header line
                reader.readLine();
    
                // Read and process each subsequent line
                String line;
                while ((line = reader.readLine()) != null) {
                    // Split the line into username, password, and designation
                    String[] values = line.split("\t\t");
    
                    String username = values[0];
                    String password = values[1];
                    String designation = values[2];
    
                    // Create a User object and add it to the ArrayList
                    User user = new User(username, password, designation);
                    allUsers.add(user);
                }
    
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    
            return allUsers;
        }
         public static void writeUserToFile(User user, String fileName) throws IOException {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));

        
            writer.write(user.getUsername() + "\t\t" + user.getPassword() + "\t\t" + user.getDesignation());
             writer.newLine();
            writer.close();
        }
        
        public static void writeTeacherToFile(Teacher teacher, String fileName) throws IOException {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
            writer.write(teacher.getUsername() + "\t\t" + teacher.getName() + "\t\t" + teacher.getSubject() + "\t\t" + teacher.getTdept());
            writer.newLine();
            writer.close();
        }
      
       

        // for removing
        public static ArrayList<User> loadUsersFromFile(String fileName) {
        ArrayList<User> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\t");
                if (data.length == 3) {
                    User user = new User();
                    user.setUsername(data[0]);
                    user.setPassword(data[1]);
                    user.setDesignation(data[2]);
                    users.add(user);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    // Write users to a file
    // Write users to a file
public static void writeUsersToFile(ArrayList<User> users, String fileName) {
    try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
        // Write the header line
        writer.println("Username\tPassword\tDesignation");

        // Write user data
        for (User user : users) {
            if (user instanceof Teacher) {
                Teacher teacher = (Teacher) user;
                writer.println(String.format("%s\t\t%s\t\t%s\t\t%s",
                        teacher.getUsername(), teacher.getPassword(),
                        teacher.getDesignation(), teacher.getTdept()));
            } else {
                writer.println(String.format("%s\t\t%s\t\t%s",
                        user.getUsername(), user.getPassword(), user.getDesignation()));
            }
        }
        
    } catch (IOException e) {
        e.printStackTrace();
    }
}

    // Remove a teacher from a file
 
    public static ArrayList<Teacher> loadTeachersFromFile(String fileName) {
        ArrayList<Teacher> teachers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            reader.readLine(); // Skip header line
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\t\t"); // Use two tabs as the delimiter
                //System.out.println(data.length);
    
                // Ensure that the array has enough elements before accessing
                if (data.length >= 4) {
                    Teacher teacher = new Teacher();
                    teacher.setUsername(data[0]);
                    teacher.setName(data[1]);
                    teacher.setSubject(data[2]);
                    teacher.setTdept(data[3]);
                    teachers.add(teacher);
                } else {
                    // Handle the case when there are not enough elements in the array
                    System.out.println("Invalid data format in line: " + line);
                }
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        return teachers;
    }
    

   

   public static void removeUserFromFile(User user, String fileName) {
    ArrayList<User> users = loadUsersFromFile(fileName);
    
    // Find and remove the user
    users.removeIf(u -> u.getUsername().equals(user.getUsername()));
    
    // Write the updated content back to the file
    try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
        writer.println("Username\tPassword\tDesignation");
        
        for (User u : users) {
            if (u instanceof Teacher) {
                Teacher teacher = (Teacher) u;
                writer.println(String.format("%s\t\t%s\t\t%s\t\t%s",
                        teacher.getUsername(), teacher.getPassword(),
                        teacher.getDesignation(), teacher.getTdept()));
            } else {
                writer.println(String.format("%s\t\t%s\t\t%s",
                        u.getUsername(), u.getPassword(), u.getDesignation()));
            }

        }
        
    } catch (IOException e) {
        e.printStackTrace();
    }
}


public static void removeTeacherFromFile(String usernameToRemove, String fileName) {
    ArrayList<Teacher> teachers = new ArrayList<>(loadTeachersFromFile(fileName));
    for (Teacher t: teachers)
    {
        t.print();
    }
    Iterator<Teacher> iterator = teachers.iterator();
    while (iterator.hasNext()) {
        Teacher t = iterator.next();
        if (t.getUsername().equals(usernameToRemove)) {
            
            iterator.remove();
            // teacherToRemove = t;
            break;
        }
    }
    System.out.println("removing");

    // if(teacherToRemove != null)
    // {
       // teachers.remove(teacherToRemove);
    writeTeachersToFile(teachers, fileName);
    //}
}


// ... existing code ...

private static Object trgetNamee() {
    return null;
}
// Write teachers to a file
public static void writeTeachersToFile(ArrayList<Teacher> teachers, String fileName) {
    try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
        writer.println("Username\t\tName\t\tSubject\t\tDepartment");
        for (Teacher teacher : teachers) {
            writer.println(String.format("%s\t\t%s\t\t%s\t\t%s",
                    teacher.getUsername(),teacher.getName(),teacher.getSubject(),teacher.getTdept()));
        }
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(fileName, "rw")) {
        randomAccessFile.seek(randomAccessFile.length());
    } catch (IOException e) {
        e.printStackTrace();
    }
    } catch (IOException e) {
        e.printStackTrace();
    }
}

// Writing Employees to file
 public static void writeEmployeeToFile(Employee emp, String fileName) throws IOException {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
            writer.write(emp.getUsername() + "\t\t" + emp.getName() + "\t\t" +  emp.getDepartment());
            writer.newLine();
            writer.close();
        }

    // Load all employees
 public static ArrayList<Employee> loadEmployeesFromFile(String fileName) {
        ArrayList<Employee> employees = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            reader.readLine(); // Skip header line
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\t\t"); // Use two tabs as the delimiter
                //System.out.println(data.length);
    
                // Ensure that the array has enough elements before accessing
                if (data.length >= 3) {
                    Employee emp = new Employee();
                    emp.setUsername(data[0]);
                    emp.setName(data[1]);
                    emp.setDepartment(data[2]);
                    
                    employees.add(emp);
                } else {
                    // Handle the case when there are not enough elements in the array
                    System.out.println("Invalid data format in line: " + line);
                }
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        return employees;
    }
    
    public static void writeEmployeesToFile(ArrayList<Employee> employees, String fileName) {
    try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
        writer.println("Username\t\tName\t\tDepartment");
        for (Employee e : employees) {
            writer.println(String.format("%s\t\t%s\t\t%s",
                    e.getUsername(),e.getName(),e.getDepartment()));
        }
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(fileName, "rw")) {
        randomAccessFile.seek(randomAccessFile.length());
    } catch (IOException e) {
        e.printStackTrace();
    }
    } catch (IOException e) {
        e.printStackTrace();
    }
}

public static void removeEmployeeFromFile(String usernameToRemove, String fileName) {
    ArrayList<Employee> employees = new ArrayList<>(loadEmployeesFromFile(fileName));
    Iterator<Employee> iterator = employees.iterator();
    while (iterator.hasNext()) {
        Employee e = iterator.next();
        if (e.getUsername().equals(usernameToRemove)) {
            
            iterator.remove();
            
            break;
        }
    }
    
    writeEmployeesToFile(employees, fileName);
  
}


    public static ArrayList<Manager> loadManagersFromFile(String fileName) {
        ArrayList<Manager> m = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            reader.readLine(); // Skip header line
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\t\t"); // Use two tabs as the delimiter
                //System.out.println(data.length);
    
                // Ensure that the array has enough elements before accessing
                if (data.length >= 3) {
                    Manager manager = new Manager();
                    manager.setUsername(data[0]);
                    manager.setName(data[1]);
                    manager.setDepartment(data[2]);
                    m.add(manager);
                } else {
                    // Handle the case when there are not enough elements in the array
                    System.out.println("Invalid data format in line: " + line);
                }
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        return m;
    }
    
// Writing Manager to file
 public static void writeManagerToFile(Manager manager, String fileName) throws IOException {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
            writer.write(manager.getUsername() + "\t\t" + manager.getName() + "\t\t" +  manager.getDepartment());
            writer.newLine();
            writer.close();
        }


    public static void writeManagersToFile(ArrayList<Manager> managers, String fileName) {
    try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
        writer.println("Username\t\tName\t\tDepartment");
        for (Manager m : managers) {
            writer.println(String.format("%s\t\t%s\t\t%s",
                    m.getUsername(),m.getName(),m.getDepartment()));
        }
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(fileName, "rw")) {
        randomAccessFile.seek(randomAccessFile.length());
    } catch (IOException e) {
        e.printStackTrace();
    }
    } catch (IOException e) {
        e.printStackTrace();
    }
    }

// Remove a manager
public static void removeManagerFromFile(String usernameToRemove, String fileName) {
    ArrayList<Manager> managers = new ArrayList<>(loadManagersFromFile(fileName));
    Iterator<Manager> iterator = managers.iterator();
    while (iterator.hasNext()) {
        Manager m = iterator.next();
        if (m.getUsername().equals(usernameToRemove)) {
            
            iterator.remove();
            
            break;
        }
    }

    
    writeManagersToFile(managers, fileName);
  
}


public static void loadTeacherInfoFromFile(Teacher teacher) {
    try (BufferedReader reader = new BufferedReader(new FileReader("Teachers.txt"))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] values = line.split("\\s+"); // Assuming values are separated by whitespace

            // Assuming the username is in the first column of the file
            String fileUsername = values[0];

            if (fileUsername.equals(teacher.getUsername())) {
                // Assuming the structure of the file is: Username Name Subject Department
                teacher.setUsername(values[0]);
                teacher.setName(values[1]);
                teacher.setSubject(values[2]);
                teacher.setTdept(values[3]);
                break; // Stop reading after finding the matching username
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}

public static void loadManagerInfoFromFile(Manager manager) {
    try (BufferedReader reader = new BufferedReader(new FileReader("Managers.txt"))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] values = line.split("\\s+"); // Assuming values are separated by whitespace

            // Assuming the username is in the first column of the file
            String fileUsername = values[0];

            if (fileUsername.equals(manager.getUsername())) {
                // Assuming the structure of the file is: Username Name Department
                manager.setUsername(values[0]);
                manager.setName(values[1]);
                manager.setDepartment(values[2]);
                break; // Stop reading after finding the matching username
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}
public static void loadEmployeeInfoFromFile(Employee employee) {
    try (BufferedReader reader = new BufferedReader(new FileReader("Employees.txt"))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] values = line.split("\t\t"); // Assuming values are separated by tabs

            // Assuming the username is in the first column of the file
            String fileUsername = values[0];

            if (fileUsername.equals(employee.getUsername())) {
                // Assuming the structure of the file is: Username Name Department
                employee.setName(values[1]);
                employee.setDepartment(values[2]);
                break; // Stop reading after finding the matching username
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}

public static ArrayList<Complaint> loadManagerComplaintsFromFile(Dept department) {
    ArrayList<Complaint> managerComplaints = new ArrayList<>();

    try {
        String filePath = "Complaint.txt";
        File file = new File(filePath);
        
        if (!file.exists()) {
            System.out.println("File not found: " + filePath);
            return managerComplaints; // Return an empty list if the file is not found
        }
        
        BufferedReader reader = new BufferedReader(new FileReader(file));
        // Skip the header line
        reader.readLine();

        String line;
        while ((line = reader.readLine()) != null) {
            try (Scanner scanner = new Scanner(line)) {
                int cid = scanner.nextInt();
                String s = scanner.next();
                String type = scanner.next();
                String teacher = scanner.next();
                String dept = scanner.next();
                String des = scanner.nextLine().trim(); // Read the rest of the line as the description

                //System.out.println(dept);
                // Check if the complaint belongs to the manager's department
                if (dept.equalsIgnoreCase(department.getName())) {
                    Complaint newComplaint = new Complaint(cid, des, type, teacher, dept);

                    //System.out.println("Hello");
                    switch (s.toLowerCase()) {
                        case "new":
                            newComplaint.setState(new New());
                            break;
                        case "assigned":
                            newComplaint.setState(new Assigned());
                            break;
                        case "resolved":
                            newComplaint.setState(new Resolved());
                            break;
                        case "closed":
                            newComplaint.setState(new Closed());
                            break;
                        default:
                            // Handle unrecognized states
                            break;
                    }

                    managerComplaints.add(newComplaint);
                }
            } catch (NumberFormatException | IllegalStateException e) {
                // Handle parsing errors or missing elements
                e.printStackTrace();
            }
        }

        reader.close();
    } catch (IOException e) {
        e.printStackTrace();
    }

    return managerComplaints;
}

public static ArrayList<Employee> loadManagerEmployeesFromFile(Dept department) {
    ArrayList<Employee> managerEmployees = new ArrayList<>();

    try {
        String filePath = "Employees.txt";
        File file = new File(filePath);
        
        if (!file.exists()) {
            System.out.println("File not found: " + filePath);
            return managerEmployees; // Return an empty list if the file is not found
        }
        
        BufferedReader reader = new BufferedReader(new FileReader(file));
        // Skip the header line
        reader.readLine();

        String line;
        while ((line = reader.readLine()) != null) {
            try (Scanner scanner = new Scanner(line)) {
                String username = scanner.next();
                String name = scanner.next();
                String dept = scanner.next();
                

                //System.out.println(dept);
                // Check if the complaint belongs to the manager's department
                if (dept.equalsIgnoreCase(department.getName())) {
                    Employee e = new Employee(username,name,dept);


                    managerEmployees.add(e);
                }
            } catch (NumberFormatException | IllegalStateException e) {
                // Handle parsing errors or missing elements
                e.printStackTrace();
            }
        }

        reader.close();
    } catch (IOException e) {
        e.printStackTrace();
    }

    return managerEmployees;
}
public static void writeStateChangesToFile(int id, String state, LocalDate currentDate) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("StateChanges.txt", true))) {
            String complaintLine = String.format("%d\t\t%s\t\t%s", id, state ,currentDate);
            writer.newLine();
            writer.write(complaintLine);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
}

public static void writeAssignmentsToFile(Object object,String empname) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Assignments.txt", true))) {
            String complaintLine = String.format("%d\t\t%s", object, empname);
            writer.newLine();
            writer.write(complaintLine);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
}

public static void updateComplaintStateInFile(int complaintId, String newState) {
    try {
        // Read all lines from the original complaints file
        List<String> lines = Files.readAllLines(Paths.get("Complaint.txt"));

        // Iterate through lines and update the state if the complaint ID matches
        for (int i = 1; i < lines.size(); i++) {
            String[] values = lines.get(i).split("\\s+");
            int id = Integer.parseInt(values[0]);

            if (id == complaintId) {
                // Update the state (assuming the state is at index 1)
                values[1] = newState;
                lines.set(i, String.join("\t\t", values));
                break;
            }
        }

        // Write the updated lines back to the file
        Files.write(Paths.get("Complaint.txt"), lines);
    } catch (IOException e) {
        e.printStackTrace();
    }
}

}

