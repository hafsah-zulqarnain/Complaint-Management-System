public class Complaint {
    int cid; // complaint id
    String cdes; // complain description
    String type; // complaint type - 1) problem 2) requirement of service or equipment
    Teacher t;
    Dept d;

    Complaint() {
        cid = 0;
        cdes = null;
        type = null;
        t = null;
    }

    Complaint(int id, String des, String ty, Teacher t1) {
        cid = id;
        cdes = des;
        type = ty;
        t = t1;
    }

    void setComplaint(int id, String d, String t) {
        cid = id;
        cdes = d;
        type = t;
    }
    
    
    void print() {
        System.out.println("Complaint Id: " + cid);
        System.out.println("Complaint Description: " + cdes);
        System.out.println("Type: " + type);
    }
}
