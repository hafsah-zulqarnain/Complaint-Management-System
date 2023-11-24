import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Teacher extends User {
    int teacherId;
    String subject;
    String joinDate;
    Dept d;
    ArrayList<Complaint> complaints; // Using ArrayList instead of array

    public Teacher() {

        teacherId = 0;
        subject = null;
        joinDate = null;
        complaints = new ArrayList<>();
    }

    Teacher(int tid, String s, String jd) {
        teacherId = tid;
        subject = s;
        joinDate = jd;
        complaints = new ArrayList<>();
    }

    // Copy constructor
    public Teacher(Teacher t) {
        this.teacherId = t.teacherId;
        this.subject = t.subject;
        this.joinDate = t.joinDate;
        this.complaints = new ArrayList<>(t.complaints);
    }

    // Add complaint
    void addComplaint(int id, String d, String t) {
        Complaint newComplaint = new Complaint();
        newComplaint.setComplaint(id, d, t);
        newComplaint.print();
        complaints.add(newComplaint);
    }

    public void enterComplaint() {
        Complaint newComplaint = new Complaint();
        Scanner scanner = new Scanner(System.in);

        try {
            // Read the recent complaint ID from the file
            int recentId = getRecentComplaintId();
            int id = recentId + 1;

            System.out.println("Enter Complaint type: ");
            System.out.println("Press 1 for a problem: ");
            System.out.println("Enter 2 for equipment/service Complaint ");
            String t = scanner.nextLine();

            // Assuming the teacher's username is the current authenticated username
            String teacher = this.username;

            System.out.println("Enter Complaint Description: ");
            String cdes = scanner.nextLine();

            // Assuming the department is not available in the Teacher class
            System.out.println("Select Department: ");
            System.out.println("IT");
            System.out.println("Accounts");
            System.out.println("Admin");
            String dept = scanner.nextLine();

            // Print and add complaint to the ArrayList
            newComplaint.setComplaint(id, cdes, t);
            newComplaint.print();
            complaints.add(newComplaint);

            // Write complaint to file
            writeComplaintToFile(id, t, teacher, dept, cdes);
        } catch (Exception e) {
            System.out.println("Error reading input. Please make sure to provide valid input.");
        } finally {
            scanner.close();
        }
    }

    
private void writeComplaintToFile(int id, String type, String teacher, String dept, String cdes) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Complaint.txt", true))) {
            String complaintLine = String.format("%d\t\t%s\t\t%s\t\t%s\t\t%s", id, type, teacher, dept, cdes);
            writer.newLine();
            writer.write(complaintLine);
        } catch (IOException e) {
            e.printStackTrace();
        }
}
public void loadComplaintsFromFile(String username) {
        try {
            String filePath = "Complaint.txt";
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            // Skip the header line
            reader.readLine();
    
            String line;
            while ((line = reader.readLine()) != null) {
                try (Scanner scanner = new Scanner(line)) {
                    int cid = scanner.nextInt();
                    int type = scanner.nextInt();
                    String teacher = scanner.next();
                    String dept = scanner.next();
                    String cdes = scanner.nextLine().trim(); // Read the rest of the line as the description
    
                    if (teacher.equals(username)) {
                        Complaint newComplaint = new Complaint(cid, cdes, String.valueOf(type), this);
                        this.complaints.add(newComplaint);
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
    }
    
    public void displayComplaints() {
        if (complaints.isEmpty()) {
            System.out.println("No complaints to display for this teacher.");
        } else {
            System.out.println("Complaints for Teacher " + teacherId + ":");
            for (Complaint complaint : complaints) {
                complaint.print();
            }
        }
    }
    private int getRecentComplaintId() {
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
    
    void print() {
        System.out.println("Teacher id: " + teacherId);
        System.out.println("Subject : " + subject);
        System.out.println("Join date: " + joinDate);
    }
}
