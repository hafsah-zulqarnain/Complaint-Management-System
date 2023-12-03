public interface State {
    void process(Complaint complaint);

    String getStateName(); // added to get the state name
    
}
