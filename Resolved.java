public class Resolved implements State {
    public void process(Complaint complaint) {
        System.out.println("Processing a complaint in the Resolved state.");
        // Additional logic for the Resolved state, if needed
    }

    @Override
    public String getStateName() {
        return "resolved";
    }
}
