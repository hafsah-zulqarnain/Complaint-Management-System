import java.util.Scanner;

public class Teacher extends User{
    int teacherId;
    String subject;
    String joinDate;
    Dept d;
    Complaint c;// This will be an array
    Teacher()
    {
        teacherId=0;
        subject =null;
        joinDate= null;
        c=null;
    }
    Teacher(int tid,String s,String jd)
    {
        teacherId=tid;
        subject =s;
        joinDate= jd;
        c=null;
    }
    // Copy constructor
    public Teacher(Teacher t) {
        this.teacherId = t.teacherId;
        this.subject = t.subject;
        this.joinDate=t.joinDate;
        c=null;
    }
    // Add complaint
    void addComplaint(int id, String d,String t)
    {
        c=new Complaint();
        c.setComplaint(id,d,t);
        c.print();
    }
    void enterComplaint()
    {
        c=new Complaint();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Complaint id: ");
        int id =scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter Complaint type: ");
        String t =scanner.nextLine();
        System.out.println("Enter Complaint Description: ");
        String d =scanner.nextLine();
        c.setComplaint(id,d,t);
        c.print();

        scanner.close();
    }
    void print()
    {
        System.out.println("Teacher id: "+teacherId);
        System.out.println("Subject : "+subject);
        System.out.println("Join date: "+joinDate);
    }
}