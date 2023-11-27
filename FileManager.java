import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileManager {
     public static void loadAllComplaintsFromFile(ArrayList<Complaint> c) {
            try {
                String filePath = "Complaint.txt";
                BufferedReader reader = new BufferedReader(new FileReader(filePath));
                // Skip the header line
                reader.readLine();
        
                String line;
                while ((line = reader.readLine()) != null) {
                    try (Scanner scanner = new Scanner(line)) {
                        int cid = scanner.nextInt();
                        String type = scanner.next();
                        String teacher = scanner.next();
                        String dept = scanner.next();
                        String cdes = scanner.nextLine().trim(); // Read the rest of the line as the description
                        Complaint newComplaint = new Complaint(cid, cdes, String.valueOf(type), teacher, dept);
                        c.add(newComplaint);
                        
                    } catch (NumberFormatException | IllegalStateException e) {
                        // Handle parsing errors or missing elements
                        e.printStackTrace();
                    }
                }
        
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        public static void writeComplaintToFile(int id, String type, String teacher, String dept, String cdes) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Complaint.txt", true))) {
            String complaintLine = String.format("%d\t\t%s\t\t%s\t\t%s\t\t%s", id, type, teacher, dept, cdes);
            writer.newLine();
            writer.write(complaintLine);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
}

 public static int getRecentComplaintId() {
            try (BufferedReader reader = new BufferedReader(new FileReader("Complaint.txt"))) {
                String line;
                reader.readLine(); // Skip header line
                int recentId = 0;
                while ((line = reader.readLine()) != null) {
                    String[] values = line.split("\\s+"); // Split by whitespace
                    int id = Integer.parseInt(values[0]);
                    if (id > recentId) {
                        recentId = id;
                    }
                }
                return recentId;
            } catch (IOException | NumberFormatException e) {
                e.printStackTrace();
                return 0;
            }
        }
}
