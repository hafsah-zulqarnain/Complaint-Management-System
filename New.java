public class New implements State{
    public void process(Complaint complaint) {
        System.out.println("Processing a complaint in the New state.");
        // Additional logic for the New state, if needed
    }

    @Override
    public String getStateName() {
        return "new";
    }
}
