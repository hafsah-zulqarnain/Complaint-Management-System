import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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

    void enterComplaint() {
        Complaint newComplaint = new Complaint();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter Complaint id: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter Complaint type: ");
        String t = scanner.nextLine();
        System.out.println("Enter Complaint Description: ");
        String d = scanner.nextLine();

        newComplaint.setComplaint(id, d, t);
        newComplaint.print();
        complaints.add(newComplaint);

        scanner.close();
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
    void print() {
        System.out.println("Teacher id: " + teacherId);
        System.out.println("Subject : " + subject);
        System.out.println("Join date: " + joinDate);
    }
}
