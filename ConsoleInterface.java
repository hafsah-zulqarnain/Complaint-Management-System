public class ConsoleInterface {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";


    public static final String BLACK_BOLD = "\u001B[1;30m";
    public static final String RED_BOLD = "\u001B[1;31m";
    public static final String GREEN_BOLD = "\u001B[1;32m";
    public static final String YELLOW_BOLD = "\u001B[1;33m";
    public static final String BLUE_BOLD = "\u001B[1;34m";
    public static final String PURPLE_BOLD = "\u001B[1;35m";
    public static final String CYAN_BOLD = "\u001B[1;36m";
    public static final String WHITE_BOLD = "\u001B[1;37m";
    private static void displayColoredText() {
        System.out.println(ANSI_RED + "Error: Something went wrong!" + ANSI_RESET);
        System.out.println(ANSI_GREEN + "Success: Operation completed successfully." + ANSI_RESET);
        System.out.println(ANSI_YELLOW + "Warning: Proceed with caution." + ANSI_RESET);
        System.out.println(ANSI_CYAN + "Info: Additional information." + ANSI_RESET);
    }

    public static void drawBorder() {
        System.out.println(BLUE_BOLD+"                                                ╔════════════════════════════════╗");
        System.out.println("                                                ║  COMPLAINT MANAGEMENT SYSTEM   ║");
        System.out.println("                                                ╚════════════════════════════════╝"+BLUE_BOLD);
    }

    private static void showProgressBar() {
        System.out.print("Progress: [");
        for (int i = 0; i <= 20; i++) {
            System.out.print("=");
            try {
                Thread.sleep(100); // Simulate a task
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("] Done!");
    }
}
