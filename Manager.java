import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;

public class Manager extends User {
    private String name;
    Dept department;

    public Manager(){

    }
    public Manager(String name, String department) {
        this.name = name;
        this.department.setName(department);
        setDesignation("manager"); // Set the designation to "manager"
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

    public void assignComplaint(int complaintid, String employee) {
        // Check if the complaint and employee are valid
        

        // Update the complaint's state to "assigned"
        //complaint.setState(new Assigned());

        // Set the assigned employee for the complaint
        

        // Store the assignment information in a file
        LocalDate currentDate = LocalDate.now();
        FileManager.writeStateChangesToFile(complaintid, "assigned", currentDate);
        FileManager.writeAssignmentsToFile(complaintid, employee);
        FileManager.updateComplaintStateInFile(complaintid, "assigned");
        System.out.println("Complaint assigned successfully.");
    }

    public void print(){
        System.out.println("Username: "+ getUsername());
        System.out.println("Name: "+ getName());
        System.out.println("Department: "+ getDepartment());
    }
}
