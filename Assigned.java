public class Assigned implements State{
    @Override
    public void process(Complaint complaint) {
        System.out.println("Processing a complaint in the Assigned state.");
        // Additional logic for the Assigned state, if needed
    }

    @Override
    public String getStateName() {
        return "assigned";
    }
}
