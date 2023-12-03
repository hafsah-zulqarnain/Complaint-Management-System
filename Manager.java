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
    public void print(){
        System.out.println("Username: "+ getUsername());
        System.out.println("Name: "+ getName());
        System.out.println("Department: "+ getDepartment());
    }
}
