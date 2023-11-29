import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Teacher extends User{
    String subject;
    String tdept;
    String name;
    ArrayList<Complaint> complaints; // Using ArrayList instead of array

    public Teacher() {
        name =null;
        subject = null;
        tdept=null;
        complaints = new ArrayList<>();
    }


    Teacher(String s ,String de, String n) {
        name= n;
        subject = s;
        tdept = de;
        complaints = new ArrayList<>();
    }

    // Copy constructor
    public Teacher(Teacher t) {
        this.name=t.name;
        this.subject = t.subject;
        this.tdept=t.tdept;
        this.complaints = new ArrayList<>(t.complaints);
    }

   public void setName(String name) {
       this.name = name;
   }
   public void setTdept(String tdept) {
       this.tdept = tdept;
   }
   public void setSubject(String subject) {
       this.subject = subject;
   } 
   public String getName() {
       return name;
   }
   public String getSubject() {
       return subject;
   }
   public String getTdept() {
       return tdept;
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
            if ((complaint.t.getUsername()).equals(username)) {
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
           
            System.out.println("Complaints for Teacher " + username + ":");
            for (Complaint complaint : complaints) {
                complaint.print();
               
            }
            System.out.println("Total Complaints: " + complaints.size());
        }
    }
   
    
    void print() {

        System.out.println("Subject : " + subject);
        System.out.println("Relevant Department : " + tdept);
        System.out.println("Teacher Name: " + name);
    }
}
