import java.util.ArrayList;
import java.util.List;

public class Dept {
    private String name;
    private ArrayList<Complaint> complaints;
    private Manager manager; // Only one manager per department
    private ArrayList<Employee> employees;
   
    public Dept() {
        // Default constructor
        complaints = new ArrayList<>();
    }

    public Dept(String name) {
        this.name = name;
        this.complaints = new ArrayList<>();
        this.employees = new ArrayList<>();
    }

    public Dept(Dept d) {
        this.name = d.name;
        // Deep copy of the complaints ArrayList
        this.complaints = new ArrayList<>(d.complaints.size());
        for (Complaint complaint : d.complaints) {
            this.complaints.add(new Complaint(complaint));
        }
         this.employees = new ArrayList<>(d.employees.size());
        for (Employee employee : d.employees) {
            this.employees.add(new Employee(employee));
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Complaint> getComplaints() {
        return complaints;
    }

    public void setComplaints(ArrayList<Complaint> complaints) {
        this.complaints = complaints;
    }

    
    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public ArrayList<Employee> getEmployees() {
        return employees;
    }

     public void setEmployees(ArrayList<Employee> employees) {
        this.employees = employees;
    }
    // Methods to add and retrieve complaints

    public void addComplaint(Complaint complaint) {
        this.complaints.add(complaint);
    }

    public Complaint getComplaint(int index) {
        if (index >= 0 && index < complaints.size()) {
            return complaints.get(index);
        } else {
            System.out.println("Invalid index for retrieving complaint.");
            return null;
        }
    }

    public void loadManagerComplaints(Manager manager) {
        ArrayList<Complaint> managerComplaints = new ArrayList<>();

        for (Complaint complaint : complaints) {
            // Check if the complaint belongs to the manager's department
            if (complaint.getDepartment().equalsIgnoreCase(this.name)) {
                managerComplaints.add(complaint);
            }
        }

        manager.setComplaints(managerComplaints);
    }
}
