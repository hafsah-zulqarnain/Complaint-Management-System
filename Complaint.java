public class Complaint {
    int cid; // complaint id
    String cdes; // complaint description
    String type; // complaint type - 1) problem 2) requirement of service or equipment
    Teacher t;
    Dept d;

    public Complaint() {
        cid = 0;
        cdes = null;
        type = null;
        t = null;  // or use a default Teacher object
        d = null;  // or use a default Dept object
    }

    public Complaint(int id, String description, String type, String teacherUsername, String deptName) {
        cid = id;
        cdes = description;
        this.type = type;
        t = new Teacher();
        t.username = teacherUsername;
        d = new Dept();
        d.name = deptName;
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
        d.name = deptName;
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
    }

    public void print() {
        System.out.println("Complaint Id: " + cid);
        System.out.println("Complaint Description: " + cdes);
        System.out.println("Type: " + type);
        System.out.println("Department: " + (d != null ? d.name : "null"));
    }
}
