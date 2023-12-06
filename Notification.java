import java.util.Date;

public class Notification {
    private String message;
    private Date timestamp;
    String name;

    public Notification(String m, Date timestamp2, String message2) {
        name=m;
        this.message = message2;
        this.timestamp = new Date();
    }

   
    public String getMessage() {
        return message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    void print()
    {
        System.out.println("name: "+name+" TimeStamp: "+timestamp+" Notification: "+message);
    }
}
