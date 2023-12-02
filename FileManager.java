import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Iterator;
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
                    String type = scanner.next();
                    String teacher = scanner.next();
                    String dept = scanner.next();
                    String cdes = scanner.nextLine().trim(); // Read the rest of the line as the description
                    Complaint newComplaint = new Complaint(cid, cdes, String.valueOf(type), teacher, dept);
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

    public static void writeComplaintToFile(int id, String type, String teacher, String dept, String cdes) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Complaint.txt", true))) {
            String complaintLine = String.format("%d\t\t%s\t\t%s\t\t%s\t\t%s", id, type, teacher, dept, cdes);
            writer.newLine();
            writer.write(complaintLine);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static int getRecentComplaintId() {
        try (BufferedReader reader = new BufferedReader(new FileReader("Complaint.txt"))) {
            String line;
            reader.readLine(); // Skip header line
            int recentId = 0;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split("\\s+"); // Split by whitespace
                int id = Integer.parseInt(values[0]);
                if (id > recentId) {
                    recentId = id;
                }
            }
            return recentId;
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

<<<<<<< HEAD
        
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
      
       
=======
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
>>>>>>> 06accc44f0807202129fd2360b272dc38247e10a

        return allUsers;
    }

    public static void writeUserToFile(User user, String fileName) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));

        writer.newLine();
        writer.write(user.getUsername() + "\t\t" + user.getPassword() + "\t\t" + user.getDesignation());
        writer.close();
    }

    public static void writeTeacherToFile(Teacher teacher, String fileName) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
        writer.newLine();
        writer.write(teacher.getUsername() + "\t\t" + teacher.getName() + "\t\t" + teacher.getSubject() + "\t\t"
                + teacher.getTdept());
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
<<<<<<< HEAD
 
=======

    // Load teachers from a file
>>>>>>> 06accc44f0807202129fd2360b272dc38247e10a
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
<<<<<<< HEAD
                } else {
                    // Handle the case when there are not enough elements in the array
                    System.out.println("Invalid data format in line: " + line);
                }
            }
            
=======

                }
            }
            for (Teacher t : teachers) {
                t.print();
            }
>>>>>>> 06accc44f0807202129fd2360b272dc38247e10a
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

    // Remove a teacher from the Teachers.txt file
    public static void removeTeacherFromFile(Teacher teacher, String fileName) {
        ArrayList<Teacher> teachers = loadTeachersFromFile(fileName);
        teachers.removeIf(t -> t.getUsername().equals(teacher.getUsername()));
        writeTeachersToFile(teachers, fileName);
        // Move the cursor to the end of the file

<<<<<<< HEAD
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
=======
    }

    // ... existing code ...

    // Write teachers to a file
    // Write teachers to a file
    public static void writeTeachersToFile(ArrayList<Teacher> teachers, String fileName) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            writer.println("Username\t\tName\t\tSubject\t\tDepartment");
            for (Teacher teacher : teachers) {
                writer.println(String.format("%s\t%s\t%s\t%s",
                        teacher.getUsername(), teacher.getPassword(),
                        teacher.getDesignation(), teacher.getTdept()));
            }
        } catch (IOException e) {
            e.printStackTrace();
>>>>>>> 06accc44f0807202129fd2360b272dc38247e10a
        }
    }

<<<<<<< HEAD
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
=======
>>>>>>> 06accc44f0807202129fd2360b272dc38247e10a
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
}

