public class Closed implements State {
    @Override
    public void process(Complaint complaint) {
        System.out.println("Processing a complaint in the Closed state.");
        // Additional logic for the Closed state, if needed
    }

    @Override
    public String getStateName() {
        return "closed";
    }
}
