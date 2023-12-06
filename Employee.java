import java.util.ArrayList;

public class Employee extends User {
    private String name;
    private Dept department;
    private ArrayList<Job> jobs;
    // Constructors
    public Employee() {
        // Default constructor
    }

    public Employee(Employee e) {

        // Default constructor
        this.name = e.name;
        this.department = e.department;
        this.jobs=new ArrayList<>();
    }
    public Employee(String name, Dept department) {
    

        this.name = name;
        this.department = department;
        this.jobs=new ArrayList<>();
    }
    public Employee(String user,String name, String d) {
    
        this.username=user;
        this.name = name;
        this.department=new Dept();
        this.department.setName(d);
        this.jobs=new ArrayList<>();
    }

    void setEmployee(Employee e)
    {
        this.name = e.name;
        this.department = e.department;
    }
    // Getter and Setter methods
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department.getName();
    }

    public void setDepartment(String deptname) {
        if (this.department == null) {
            this.department = new Dept();
        }
        this.department.setName(deptname);
    }

    public void loadEmployeeInfo() {
        FileManager.loadEmployeeInfoFromFile(this);
    }
    // Other methods as needed
    // For example, you might want to add a method to display employee information

    
    public void displayEmployeeInfo() {
       
        System.out.println("Username: " + getUsername());
        System.out.println("Name: " + name);
        System.out.println("Department: " + department.getName()); // Assuming Dept has a getName() method
    }

    public void loadAssignments() {
        System.out.println("");
        jobs=FileManager.loadAssignmentsForEmployee(getUsername());
    }

    public void markAssignmentCompleted(int assignmentId,String dept) {
        // Load the employee's assignments
        ArrayList<Job> assignments = FileManager.loadAssignmentsForEmployee(getUsername());
        Manager m=new Manager();
        FileManager.loadManagerDeptFromFile(m,dept);
        System.out.println(m.getDepartment());
        // Find the assignment with the given ID
        for (Job assignment : assignments) {
            if (assignment.getId() == assignmentId) {
                // Mark the assignment as completed
                assignment.addObserver(m);
                assignment.markCompleted();

                // Update the status in the Assignments.txt file
                FileManager.updateAssignmentStatus(assignment);

                System.out.println("Assignment marked as completed successfully.");
                return;
            }
        }

        
        System.out.println("Assignment not found with ID: " + assignmentId);
    }
    public void DisplayAssignments()
    {
        for(Job j:jobs)
        {
            j.print();
        }
    }
}
