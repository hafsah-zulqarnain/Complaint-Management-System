import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;


public class Manager extends User implements Observer{
    private String name;
    Dept department;
    private List<Notification> notifications;

    public Manager(){
        this.notifications = new ArrayList<>();
        this.department = new Dept(); 
        setDesignation("manager"); // Set the designation to "manager"
    }
    public Manager(String name, String department) {
        this.name = name;
        this.department = new Dept(); 
        this.department.setName(department); // Set the department's name after initialization
        setDesignation("manager"); // Set the designation to "manager"
        this.notifications = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department.getName();
    }

    public void update(int cid) {
        Date currentDate = new Date();
        // Add a notification when the update method is called
        String note="Complaint " + cid + " in your department has been completed. Please review.";
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

    public void setDepartment(String department) {
        if (this.department == null) {
            this.department = new Dept();
        }
        this.department.setName(department);
    }

    public void loadManagerInfo() {
        FileManager.loadManagerInfoFromFile(this);
    }

  
     public void setComplaints(ArrayList<Complaint> complaints) {
        this.department.setComplaints(complaints);
    }

    public ArrayList<Complaint> getComplaints() {
        return this.department.getComplaints();
    }

    public ArrayList<Employee> getEmployees() {
        return this.department.getEmployees();
    }

    public void loadManagerComplaints() {
        // Load manager info from Managers.txt
       // FileManager.loadManagerInfoFromFile(this);

        // Assuming the manager's department is already set in the Manager object
        //System.out.println("Dept: "+ department.getName());
        department.setComplaints(FileManager.loadManagerComplaintsFromFile(department));
        //DisplayDeptComplaints();
    }

    public void loadManagerEmployees() {
        // Load manager info from Managers.txt
       // FileManager.loadManagerInfoFromFile(this);
        // Assuming the manager's department is already set in the Manager object
        //System.out.println("Dept: "+ department.getName());
        department.setEmployees(FileManager.loadManagerEmployeesFromFile(department));
        //DisplayDeptComplaints();
    }

    public void DisplayDeptComplaints()
    {
        for ( Complaint c : department.getComplaints())
        {
            c.print();
        }
    }

    
    public void DisplayDeptEmployees()
    {
        for (Employee e : department.getEmployees())
        {
            e.displayEmployeeInfo();
        }
    }

    public void assignComplaint(Job j) {
        // Check if the complaint and employee are valid
        

        // Update the complaint's state to "assigned"
        //complaint.setState(new Assigned());

        // Set the assigned employee for the complaint
        

        // Store the assignment information in a file
        
        LocalDate currentDate = LocalDate.now();
        FileManager.writeStateChangesToFile(j.getId(), "assigned", currentDate);
        //boolean sttus=j.getJobStatus();
        System.out.println("");
        FileManager.writeAssignmentsToFile(j);
        FileManager.updateComplaintStateInFile(j.getId(), "assigned");
        System.out.println("Complaint assigned successfully.");
    }

    public void reAssign(int id) {

        // Store the assignment information in a file
        LocalDate currentDate = LocalDate.now();
        Complaint co=new Complaint();
        co=FileManager.loadComplaintById(id);
        co.setState(new Assigned());
        FileManager.writeStateChangesToFile(id, co.s.getStateName(), currentDate);
        //boolean sttus=j.getJobStatus();
        System.out.println("");
        FileManager.updateAssignment(id);
        FileManager.updateComplaintStateInFile(id,co.s.getStateName());
        System.out.println("Complaint reassigned successfully.");
    }

    public void ResolveComplain(int cid) {
        LocalDate currentDate = LocalDate.now();
        System.out.println("");
        Complaint co=new Complaint();
        co=FileManager.loadComplaintById(cid);
        co.setState(new Resolved());
        FileManager.writeStateChangesToFile(cid, co.s.getStateName(), currentDate);
        FileManager.updateComplaintStateInFile(cid, co.s.getStateName());
        String teacherUsername = FileManager.findTeacherForComplaint(cid);
        //System.out.println(teacherUsername);
        if (teacherUsername != null) {
            Teacher t = FileManager.findTeacherByUsername(teacherUsername);
            System.out.println(t.getSubject());
            ArrayList<Complaint> complaints = new ArrayList<>();
            FileManager.loadAllComplaintsFromFile(complaints);
   
            for (Complaint c : complaints) {
                if (c.getCid() == cid) {
                    c.addObserver(t);
                    c.markResolved(cid);
                }
            }
   
            System.out.println("Complaint resolved successfully.");
        } else {
            System.out.println("Teacher username not found for the given complaint ID.");
        }
    }
    public void ViewAssignedComplaints(int cid)
    {
        String sol=FileManager.viewAssignedComplaintSolutions(cid);
        System.out.println(sol);
    }
    public void print(){
        System.out.println("Username: "+ getUsername());
        System.out.println("Name: "+ getName());
        System.out.println("Department: "+ getDepartment());
    }
}
