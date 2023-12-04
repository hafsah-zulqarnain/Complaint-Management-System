public class Employee extends User {
    private String name;
    private Dept department;

    // Constructors
    public Employee() {
        // Default constructor
    }

    public Employee(Employee e) {

        // Default constructor
        this.name = e.name;
        this.department = e.department;
    }
    public Employee(String name, Dept department) {
    

        this.name = name;
        this.department = department;
    }
    public Employee(String user,String name, String d) {
    
        this.username=user;
        this.name = name;
        this.department=new Dept();
        this.department.setName(d);
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
}
