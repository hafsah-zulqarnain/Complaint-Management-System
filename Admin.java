import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Admin extends User {
    static ArrayList<User> AllUsers;

    public Admin() {
        // Load all users into the allUsers ArrayList
        AllUsers = FileManager.loadAllUsersFromFile();
    }

    public void addUser() {

        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the new user details:");
            System.out.print("Username: ");
            String newUsername = scanner.nextLine();
            if (usernameExists(newUsername)) {
                System.out.println("User with the given username already exists. Cannot add duplicate users.");
                return;
            }
            System.out.print("Password: ");
            String newPassword = scanner.nextLine();
            System.out.print("Designation: ");
            String newDesignation = scanner.nextLine();
            if (newDesignation.equals("teacher")) {
                Teacher t = new Teacher();
                t.setUsername(newUsername);
                t.setPassword(newPassword);
                t.setDesignation(newDesignation);

                System.out.print("Name: ");
                String name = scanner.nextLine();
                t.setName(name);
                System.out.print("Subject: ");
                String subject = scanner.nextLine();
                t.setSubject(subject);
                System.out.print("Enter Relevant Department: ");
                String d = scanner.nextLine(); // eneter teacher department
                t.setTdept(d);
                FileManager.writeTeacherToFile(t, "Teachers.txt");
                AllUsers.add(t);
                FileManager.writeUserToFile(t, "Authen.txt");

            }

            // Create a new User object

            // Add the new user to the allUsers ArrayList

            // Append the new user details to the Authen.txt file

            System.out.println("User added successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the username to remove: ");
        String usernameToRemove = scanner.nextLine();

        // Find the user in the allUsers ArrayList
        User userToRemove = null;
        for (User user : AllUsers) {
            if (user.getUsername().equals(usernameToRemove)) {
                userToRemove = user;
                break;
            }
        }

        if (userToRemove != null) {
            // Remove the user from the allUsers ArrayList
            AllUsers.remove(userToRemove);

            // Remove the user from the Authen.txt file
            FileManager.writeUsersToFile(AllUsers, "Authen.txt");

            // If the user is a teacher, remove them from the Teachers.txt file
            if (userToRemove instanceof Teacher) {
                System.out.println("Hello");
                Teacher teacherToRemove = (Teacher) userToRemove;
                FileManager.removeTeacherFromFile(teacherToRemove, "Teachers.txt");
            }

            System.out.println("User removed successfully.");
        } else {
            System.out.println("User not found.");
        }
    }

    public static void displayAllUsers() {
        System.out.println("All Users in the System:");
        System.out.println("Username\tPassword\tDesignation");
        for (User user : AllUsers) {
            System.out.println(user.getUsername() + "\t\t" + user.getPassword() + "\t\t" + user.getDesignation());
        }

    }

    // Helper method to check if a username already exists
    private boolean usernameExists(String username) {
        return AllUsers.stream().anyMatch(user -> user.getUsername().equals(username));
    }

}
