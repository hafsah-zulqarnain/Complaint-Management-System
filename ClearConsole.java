public class ClearConsole {
    public static void clearScreen() {
        // Clear console command for Windows, Linux, and macOS
        try {
            final String os = System.getProperty("os.name").toLowerCase();

            if (os.contains("win")) {
                // For Windows
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // For Linux and macOS
                new ProcessBuilder("bash", "-c", "clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Your program continues here after clearing the console
        //System.out.println("Console cleared!");
    }
}
 