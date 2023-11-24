import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CampusDirector extends User {

    public static void viewComplaintSummary() {
        Map<String, Integer> departmentComplaintCount = new HashMap<>();
        String fileName = "Complaint.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            reader.readLine(); // discard the header line

            // Read each line of the file
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\\s+");

                if (data.length > 3) {
                    String department = data[3]; // department is at index 3
                    // Update the count for the department
                    departmentComplaintCount.put(department, departmentComplaintCount.getOrDefault(department, 0) + 1);
                } else {
                    System.out.println("Invalid data format in line: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Display the summary
        System.out.println("Complaint Summary:");
        for (Map.Entry<String, Integer> entry : departmentComplaintCount.entrySet()) {
            System.out.println("Department: " + entry.getKey() + ", Complaints Received: " + entry.getValue());
        }
    }

    public static void main(String[] args) {
        // Provide the path to your complaint file
        viewComplaintSummary();
    }
}
