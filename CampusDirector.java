import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CampusDirector extends User {

    public static void viewComplaintSummary() {
        Map<String, Map<String, Integer>> departmentStatusCount = new HashMap<>();
        Map<String, Integer> departmentTotalCount = new HashMap<>();

        String fileName = "Complaint.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\\s+");

                if (data.length > 3) {
                    String department = data[4]; // department is at index 4
                    String status = data[1]; // status is at index 1

                    departmentStatusCount
                            .computeIfAbsent(department, k -> new HashMap<>())
                            .put(status, departmentStatusCount
                                    .get(department)
                                    .getOrDefault(status, 0) + 1);

                    departmentTotalCount.put(department, departmentTotalCount.getOrDefault(department, 0) + 1);
                } else {
                    System.out.println("Invalid data format in line: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Complaints Summary:");
        for (Map.Entry<String, Map<String, Integer>> departmentEntry : departmentStatusCount.entrySet()) {
            String department = departmentEntry.getKey();
            System.out.println("Department name: " + department);

            int totalComplaints = departmentTotalCount.getOrDefault(department, 0);
            System.out.println("Total no of complaints: " + totalComplaints);

            Map<String, Integer> statusCount = departmentEntry.getValue();
            System.out.println("Total no of closed complaints: " + statusCount.getOrDefault("closed", 0));
            System.out.println("Total no of new complaints: " + statusCount.getOrDefault("new", 0));
            System.out.println("Total no of assigned complaints: " + statusCount.getOrDefault("assigned", 0));
            System.out.println("Total no of resolved complaints: " + statusCount.getOrDefault("resolved", 0));

            System.out.println();
        }
    }

    public static void main(String[] args) {
        viewComplaintSummary();
    }
}
