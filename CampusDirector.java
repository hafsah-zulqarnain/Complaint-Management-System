import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CampusDirector extends User {

    // ------------------------Overall Summary---------------------
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

    // ------------------------complain wise detail---------------------

    public static void viewComplaintDetails(int complaintId) {

        Map<String, String> managersMap = getManager("Managers.txt");
        Map<Integer, String> stateChangesMap = findStateAndDate("StateChanges.txt");
        String[] complaintDetails = readComplaintDetailsFromFile("Complaint.txt", complaintId);
        String[] assignmentDetails = readAssignmentDetailsFromFile("Assignments.txt", complaintId);

        if (complaintDetails != null) {
            String department = complaintDetails[0];
            String teacher = complaintDetails[1];
            String description = complaintDetails[2];

            System.out.println("Complaint ID: " + complaintId);
            System.out.println("Department: " + department);
            System.out.println("Teacher: " + teacher);
            System.out.println("Description: " + description);

            String manager = managersMap.get(department);
            if (manager != null) {
                System.out.println("Manager: " + manager);
            }

            String stateChange = stateChangesMap.get(complaintId);
            if (stateChange != null) {
                System.out.println("Date of Job Assignment: " + stateChange);
            }
            if (assignmentDetails != null) {
                String employeeName = assignmentDetails[0];
                System.out.println("Assigned Employee: " + employeeName);
            }
        } else {
            System.out.println("Complaint with ID " + complaintId + " not found.");
        }
    }

    private static String[] readAssignmentDetailsFromFile(String fileName, int complaintId) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            reader.readLine(); // Discard the header line

            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\\s+");
                if (data.length > 3) {
                    int cid = Integer.parseInt(data[0]);
                    if (cid == complaintId) {
                        String employeeName = data[1];

                        return new String[] { employeeName };
                    }
                } else {
                    System.out.println("Invalid data format in line: " + line);
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Map<String, String> getManager(String fileName) {
        Map<String, String> managersMap = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            reader.readLine(); // Discard the header line

            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\\s+");
                if (data.length > 2) {
                    String username = data[0];
                    String name = data[1];
                    String department = data[2];

                    managersMap.put(department, name);
                } else {
                    System.out.println("Invalid data format in line: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return managersMap;
    }

    private static Map<Integer, String> findStateAndDate(String fileName) {
        Map<Integer, String> stateChangesMap = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            reader.readLine(); // Discard the header line

            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\\s+");
                if (data.length > 2) {
                    int cid = Integer.parseInt(data[0]);
                    String state = data[1];
                    String date = data[2];

                    stateChangesMap.put(cid, date);
                } else {
                    System.out.println("Invalid data format in line: " + line);
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return stateChangesMap;
    }

    private static String[] readComplaintDetailsFromFile(String fileName, int complaintId) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            reader.readLine(); // Discard the header line

            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\\s+");
                if (data.length > 5) {
                    int cid = Integer.parseInt(data[0]);
                    if (cid == complaintId) {
                        String department = data[4];
                        String teacher = data[3];
                        String description = data[5].trim();

                        return new String[] { department, teacher, description };
                    }
                } else {
                    System.out.println("Invalid data format in line: " + line);
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        viewComplaintSummary();
        viewComplaintDetails(1);
    }
}
