public class Complaint {
    int cid; // complaint id
    String cdes; // complaint description
    String type; // complaint type - 1) problem 2) requirement of service or equipment
    Teacher t;
    Dept d;
    State s;

    public Complaint() {
        cid = 0;
        cdes = null;
        type = null;
        t = null;  // or use a default Teacher object
        d = null;  // or use a default Dept object
        d = null;  // or use a default Dept object
        s = null;  // or use a default Dept object
    }

    public Complaint(int id, String description, String type, String teacherUsername, String deptName) {
        cid = id;
        cdes = description;
        this.type = type;
        t = new Teacher();
        t.username = teacherUsername;
        d = new Dept();
        d.setName(deptName);
        s= new New();
        
    }

    public Complaint(int id, String description, String type, Teacher teacher) {
        cid = id;
        cdes = description;
        this.type = type;
        t = teacher;
    }

    public void setComplaint(int id, String description, String type, String teacherUsername, String deptName) {
        cid = id;
        cdes = description;
        this.type = type;
         t = new Teacher();
        t.username = teacherUsername;
        d = new Dept();
        d.setName(deptName);
        s=new New();
    }

    public Complaint(Complaint other) {
        this.cid = other.cid;
        this.cdes = other.cdes;
        this.type = other.type;

        if (other.t != null) {
            this.t = new Teacher(other.t);
        } else {
            this.t = null;
        }

        if (other.d != null) {
            this.d = new Dept(other.d);
        } else {
            this.d = null;
        }

        this.s= other.s;
    }

    public void setState(State state) {
        this.s = state;
    }

    public void process() {
        s.process(this);
    }

    public String getDepartment() {
        if (this.d != null) {
            return this.d.getName();
        } else {
            System.out.println("Department not set for this complaint.");
            return null;
        }
    }
    public void print() {
        System.out.println("Complaint Id: " + cid);
        System.out.println("Complaint Description: " + cdes);
        System.out.println("Type: " + type);
        System.out.println("Department: " + (d != null ? d.getName() : "null"));
    }
}
