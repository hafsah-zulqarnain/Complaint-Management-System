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

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

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

    // Load teachers from a file
    public static ArrayList<Teacher> loadTeachersFromFile(String fileName) {
        ArrayList<Teacher> teachers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\t");
                System.out.println(data.length);
                if (data.length == 4) {
                    Teacher teacher = new Teacher();
                    teacher.setUsername(data[0]);
                    teacher.setPassword(data[1]);
                    teacher.setDesignation(data[2]);
                    teacher.setTdept(data[3]);
                    teachers.add(teacher);

                }
            }
            for (Teacher t : teachers) {
                t.print();
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

    // Remove a teacher from the Teachers.txt file
    public static void removeTeacherFromFile(Teacher teacher, String fileName) {
        ArrayList<Teacher> teachers = loadTeachersFromFile(fileName);
        teachers.removeIf(t -> t.getUsername().equals(teacher.getUsername()));
        writeTeachersToFile(teachers, fileName);
        // Move the cursor to the end of the file

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
        }
    }

}
