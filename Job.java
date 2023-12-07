import java.util.ArrayList;

public class Job implements Subject {
    private ArrayList<Observer> observers = new ArrayList<>();
    private boolean isCompleted;
    private int id;
    private String employeename;
    private String solution;
    

    public Job(int id, String empname) {
        this.id = id;
        employeename = empname;
        this.isCompleted = false;
        this.solution="No Solution";
    }

    public Job(int id) {
        this.id = id;
    }

    public void markCompleted(int cid) {
        isCompleted = true;
        notifyObservers(cid);
    }

    void print()
    {
        System.out.println(ConsoleInterface.WHITE_BOLD+"cid: "+id);
        System.out.println("complaint status: "+isCompleted);
    }

    public String getEmployeename() {
        return employeename;
    }
    public int getId() {
        return id;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }
    public String getSolution() {
        return solution;
    }
    public void setCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }
    public boolean getJobStatus() {
        return isCompleted;
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
}
