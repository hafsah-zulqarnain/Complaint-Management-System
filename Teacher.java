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
    //setComplaint(int id, String description, String type, String teacherUsername, String deptName
    void addComplaint(int id, String d, String t,String u, String dept) {
        Complaint newComplaint = new Complaint();
        newComplaint.setComplaint(id, d, t,u, dept);
        newComplaint.print();
        complaints.add(newComplaint);
    }

    public void enterComplaint() {
        Complaint newComplaint = new Complaint();
        Scanner scanner = new Scanner(System.in);

        try {
            // Read the recent complaint ID from the file
            int recentId = FileManager.getRecentComplaintId();
            int id = recentId + 1;

            System.out.println("Enter Complaint type: ");
            System.out.println("Problem or equipment/service");
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
            newComplaint.setComplaint(id, cdes, t,this.username,dept);
            //newComplaint.print();
            complaints.add(newComplaint);

            // Write complaint to file
            FileManager.writeComplaintToFile(id, t, teacher, dept, cdes);
        } catch (Exception e) {
            System.out.println("Error reading input. Please make sure to provide valid input.");
        } finally {
            scanner.close();
        }
    }

    

    public void loadComplaintsFromFile(String username, ArrayList<Complaint> c) {
        for (Complaint complaint : c) {
            if ((complaint.t.username).equals(username)) {
                // Initialize the Dept field in the new Complaint object
                
                Complaint newComplaint = new Complaint(complaint);
                this.complaints.add(newComplaint);
            }
        }
    }
    
    
    public void displayComplaints() {
        if (complaints.isEmpty()) {
            System.out.println("No complaints to display for this teacher.");
        } else {
            int totalComplaints=0;
            System.out.println("Complaints for Teacher " + username + ":");
            for (Complaint complaint : complaints) {
                complaint.print();
                totalComplaints++;
            }
            System.out.println("Total Complaints: " + totalComplaints);
        }
    }
   
    
    void print() {
        System.out.println("Teacher id: " + teacherId);
        System.out.println("Subject : " + subject);
        System.out.println("Join date: " + joinDate);
    }
}
