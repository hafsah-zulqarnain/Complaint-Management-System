import java.util.ArrayList;

public class Complaint implements Subject{
    int cid; // complaint id
    String cdes; // complaint description
    String type; // complaint type - 1) problem 2) requirement of service or equipment
    Teacher t;
    Dept d;
    State s;
    private ArrayList<Observer> observers = new ArrayList<>();

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

    public void markResolved(int cid) {
       
        notifyObservers(cid);
    }
    public void setComplaint(Complaint other)
    {
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

        this.s=new New();
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

    public String getCdes() {
        return cdes;
    }

    public String getType() {
        return type;
    }

    public Teacher getT() {
        return t;
    }

    public Dept getD() {
        return d;
    }

    public State getS() {
        return s;
    }
    public void print() {
        System.out.println(ConsoleInterface.WHITE+"COMPAINT ID: "+ ConsoleInterface.WHITE + cid+ConsoleInterface.WHITE);
        System.out.println(ConsoleInterface.WHITE+"CCOMPLAINT DESCCRIPTION: "+ConsoleInterface.WHITE+ ConsoleInterface.WHITE + cdes+ ConsoleInterface.WHITE);
        System.out.println(ConsoleInterface.WHITE+"COMPLAINT STATUS: " + ConsoleInterface.WHITE+ ConsoleInterface.WHITE +s.getStateName()+ConsoleInterface.WHITE);
        System.out.println(ConsoleInterface.WHITE+"TYPE: "+ConsoleInterface.WHITE+ ConsoleInterface.WHITE + type+ConsoleInterface.WHITE);
        System.out.println(ConsoleInterface.WHITE+"DEPARTMENT: " +ConsoleInterface.WHITE+  ConsoleInterface.WHITE+(d != null ? d.getName() : "null")+ ConsoleInterface.WHITE);
    }

    
    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(int cid) {
        for (Observer observer : observers) {
            observer.update(cid);
        }
    }

    public int getCid() {
        return cid;
    }
}
