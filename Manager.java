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

    void print(){
        System.out.println("Username: "+ getUsername());
        System.out.println("Name: "+ getName());
        System.out.println("Username: "+ getDepartment());
    }
}
