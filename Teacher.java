import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Date;
import java.util.List;


public class Teacher extends User implements Observer{
    String subject;
    String tdept;
    String name;
    ArrayList<Complaint> complaints; // Using ArrayList instead of array
    private List<Notification> notifications;

    public Teacher() {
        name =null;
        subject = null;
        tdept=null;
        complaints = new ArrayList<>();
        this.notifications = new ArrayList<>();
    }

    public void update(int cid) {
        Date currentDate = new Date();
        // Add a notification when the update method is called
        String note="Complaint " + cid + "  filed by you has been resolved . Please Review";
        Notification notification = new Notification(this.getUsername(),currentDate,note);
        //notification.print();
        notifications.add(notification);

        // Save the notification to a file
        FileManager.saveNotificationToFile(notification,this.getUsername());
    }

    public void viewNotifications() {
        // Display notifications to the manager
        List<Notification> notifications = FileManager.loadNotificationsFromFile("notifications.txt",this);
        for (Notification notification : notifications) {
            System.out.println("Timestamp: " + notification.getTimestamp() + " - " + notification.getMessage());
        }
        notifications.clear(); // Clear notifications after viewing
    }

    Teacher(String s ,String de, String n) {
        name= n;
        subject = s;
        tdept = de;
        complaints = new ArrayList<>();
        this.notifications = new ArrayList<>();
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
            String fileName = "Complaint.txt";
            File file = new File(fileName);
            int id=0;
            // Check if the file needs to be created
            if (!file.exists()) {
                id = 1;
            }
            else{
            int recentId = FileManager.getRecentComplaintId();
            id = recentId;
            }

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
            String state="new";
            // Print and add complaint to the ArrayList
            newComplaint.setComplaint(id, cdes, t,this.username,dept);
            //newComplaint.print();
            complaints.add(newComplaint);
           
            // Write complaint to file
            FileManager.writeComplaintToFile(newComplaint);
            LocalDate currentDate = LocalDate.now();
            FileManager.writeStateChangesToFile(id, state, currentDate);

        } catch (Exception e) {
            System.out.println("Error reading input. Please make sure to provide valid input.");
        } finally {
           // scanner.close();
        }
    }

    

    public void loadComplaintsFromFile(String username) {
        ArrayList<Complaint> c = new ArrayList<>(); // Initialize the ArrayList
        FileManager.loadAllComplaintsFromFile(c);
        for (Complaint complaint : c) {
            if ((complaint.t.getUsername()).equals(username)) {
                // Initialize the Dept field in the new Complaint object
                
                Complaint newComplaint = new Complaint(complaint);
                System.out.println("");
                this.complaints.add(newComplaint);

            }
        }
    }
    
    
    public void ViewAssignedComplaints(int cid)
    {
        String sol=FileManager.viewAssignedComplaintSolutions(cid);
        System.out.println(sol);
    }

    public void CloseComplain(int cid) {
        Complaint co=new Complaint();
        co=FileManager.loadComplaintById(cid);
        co.setState(new Closed());
        LocalDate currentDate = LocalDate.now();
        System.out.println("");
        FileManager.writeStateChangesToFile(cid, co.s.getStateName(), currentDate);
        FileManager.updateComplaintStateInFile(cid,  co.s.getStateName());
        System.out.println("Complaint resolved successfully.");
    }

    public void reAssign(int id) {

        // Store the assignment information in a file
        Complaint co=new Complaint();
        co=FileManager.loadComplaintById(id);
        co.setState(new New());
        LocalDate currentDate = LocalDate.now();
        FileManager.writeStateChangesToFile(id,co.s.getStateName() ,currentDate);
        //boolean sttus=j.getJobStatus();
        System.out.println("");
        FileManager.updateAssignment(id);
        FileManager.updateComplaintStateInFile(id, co.s.getStateName());
        System.out.println("Complaint solution rejected");
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
   
    public void loadTeacherInfo() {
        FileManager.loadTeacherInfoFromFile(this);
    }
    
    public void print() {

        System.out.println("Username: " + username);
        System.out.println("Teacher Name: " + name);
        System.out.println("Subject : " + subject);
        System.out.println("Relevant Department : " + tdept);
    }
}
