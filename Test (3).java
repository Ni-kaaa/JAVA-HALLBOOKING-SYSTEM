
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



 class Test {

    private static final Scanner scanner = new Scanner(System.in);
    private static String[] Hall = new String[100];
    private static String[] StuId = new String[100];
    private static String[] date = new String[100];
    private static String[] Seats = new String[100];

    static int x  = 0;

    static char[] alphabetArray = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    // Method to initialize the hall with empty seats
    private static void initializeHall(String[][] hall1, String[][] hall2, String[][] hall3, int hrow, int hcol) {
        for (int i = 0; i < hrow; i++) {
            for (int j = 0; j < hcol; j++) {
                hall1[i][j] = "|" + alphabetArray[i] + "-" + (j+1) + "::AV|";
                hall2[i][j] = "|" + alphabetArray[i] + "-" + (j+1) + "::AV|";
                hall3[i][j] = "|" + alphabetArray[i] + "-" + (j+1) + "::AV|";
            }
        }
    }

    // Method to display the current status of the hall
    private static void hall(String[][] hall, int hrow, int hcol, char showtime) {
        System.out.println("-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-");
        System.out.println("Current Hall Status:");
        System.out.println("-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-");
        switch (showtime) {
            case 'A':
            case 'a':
                System.out.println("# Hall - Morning");

                break;
            case 'B':
            case 'b':
                System.out.println("# Hall - Afternoon");

                break;
            case 'C':
            case 'c':
                System.out.println("# Hall - Evening");

                break;
        }

        System.out.println("-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-");
        for (int i = 0; i < hrow; i++) {
            for (int j = 0; j < hcol; j++) {
                System.out.print(hall[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println("-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-");

    }

    // Method to book seat(s) in the hall
    private static void booking(String[][] hall1, String[][] hall2, String[][] hall3, int hrow, int hcol) {
        System.out.println("# Start Booking Process");
        System.out.println("-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-");
        System.out.println("# Showtime Information");
        System.out.println("# A) Morning (10:00AM - 12:30PM)");
        System.out.println("# B) Afternoon (03:00PM - 05:30PM)");
        System.out.println("# C) Night (07:00PM - 09:30PM)");
        System.out.println("-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-");
        System.out.print("> Please Select Showtime (A | B | C): ");
        char op = scanner.nextLine().charAt(0); // Read the entire line
        System.out.println();
        switch (op) {
            case 'A':
            case 'a':
                hall(hall1, hrow, hcol, 'A');
                bookCheckSeats(hall1, hrow, hcol,'A');
                break;
            case 'B':
            case 'b':
                hall(hall2, hrow, hcol, 'B');
                bookCheckSeats(hall2, hrow, hcol,'B');
                break;
            case 'C':
            case 'c':
                hall(hall3, hrow, hcol, 'C');
                bookCheckSeats(hall3, hrow, hcol,'C');
                break;
        }

    }

    private static void bookCheckSeats(String[][] hall, int hrow, int hcol, char hallType){
        System.out.println("-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-");
        System.out.println("# INSTRUCTION");
        System.out.println("# Single: C-1");
        System.out.println("# Multiple (Separate by comma): C-1,C-2");


        System.out.print("> Please Select Available Seat: ");
        String input = scanner.nextLine().toUpperCase();
        System.out.print("Input stu id: ");
        String id = scanner.nextLine();
        String seatBooked = "";

        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy HH:mm");

        // Format the current date and time
        String formattedDateTime = currentDateTime.format(formatter);






        // Validate user input format
        if (!validateInput(input, "^(([A-Z]-\\d+)(\\s*,\\s*[A-Z]-\\d+)*)$")) {
            System.out.println("-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-");
            System.out.println("Invalid input format!");
            return;
        }

        // Split input to get individual seat coordinates
        String[] seatInputs = input.split("\\s*,\\s*");
        for (String seatInput : seatInputs) {
            String[] coordinates = seatInput.split("-");
            if (coordinates.length != 2) {
                System.out.println("Invalid input format for seat coordinates!");
                return;
            }

            char rowChar = coordinates[0].charAt(0);
            int col = Integer.parseInt(coordinates[1]);

            // Convert row character to index
            int row = rowChar - 'A';

            // Validate row and column numbers
            if (row < 0 || row >= hrow || col < 1 || col > hcol) {
                System.out.println("Invalid row or column number for seat " + seatInput);
                continue; // Continue to the next seat if one seat is invalid
            }

            // Check if the seat is already booked
            String seat = "|" + rowChar + "-" + col + "::BO|";
            String seats = "[" + rowChar + "-" + col + "]";

            if (hall[row][col - 1].equals(seat)) {
                System.out.println(seat + " is already booked!");
                continue; // Continue to the next seat if one seat is already booked
            }

            hall[row][col - 1] = seat;
            seatBooked += seats;
            System.out.println(seat + " booked successfully!");



        }

        System.out.print("Are you sure to book? (Y/N): ");
        String choice = scanner.next();
        if(choice.equalsIgnoreCase("Y")){
            addBookedHistory(hallType,seatBooked, id, formattedDateTime);
        } else {
            System.out.println("Thank you! Please come again!");
        }

    }

    private static void addBookedHistory(char hallType,String seatBooked, String id, String formattedDateTime){
        Seats[x] = seatBooked;
        StuId[x] = id;
        date[x] = formattedDateTime;
        if (hallType == 'A') {
            Hall[x] = "HALL A";
        } else if (hallType == 'B') {
            Hall[x] = "HALL B";
        } else if(hallType == 'C'){
            Hall[x] = "HALL C";
        }
        x++;
       // history();
    }

    private static void history(){
        System.out.println("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-");
        System.out.println("# Booking History");
        System.out.println("----------------------------------------------------------");

        for(int i=0; i<x; i++) {
            System.out.println("#NO: " + (i+1));
            System.out.println("SEATS: " + Seats[i]);
            System.out.println("#HALL\t\t\t\t#STU.ID\t\t\t\t#Create At");
            System.out.println(Hall[i] + "\t\t\t" + StuId[i] + "\t\t\t\t\t\t" + date[i]);

        }

    }

    // Method to check showtimes
    private static void showtimes() {
        // Placeholder method
        System.out.println("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-");
        System.out.println("# Daily Showtime of CSTAD Hall:");
        System.out.println("# A) Morning (10:00AM-12:30PM)");
        System.out.println("# B) Afternoon (03:00PM-5:30PM)");
        System.out.println("# C) Night (07:00PM-09:30PM)");
    }

    // Method to facilitate hall rebooting
    private static void reboot(String[][] hall1, String[][] hall2, String[][] hall3, int hrow, int hcol) {
        initializeHall(hall1,hall2, hall3, hrow, hcol);
        for(int i=0; i<x ; i++) {
            Hall[i] = "";
            Seats[i] = "";
            date[i] = "";
            StuId[i]="";
        }
        x=0;

        System.out.println("Showtime has been rebooted successfully!");
    }

    // Method to validate user input using regular expressions
    private static boolean validateInput(String input, String pattern) {
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(input);
        return matcher.matches();
    }

    public static void main(String[] args) {

        System.out.print("Enter the number of rows: ");
        int hrow = scanner.nextInt();
        scanner.nextLine(); // Consume newline character
        System.out.print("Enter the number of columns: ");
        int hcol = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        String[][] hallMorning= new String[hrow][hcol];
        String[][] hallAfternoon = new String[hrow][hcol];
        String[][] hallEvening = new String[hrow][hcol];
        initializeHall(hallMorning,hallAfternoon,hallEvening, hrow, hcol);

        while (true) {
            System.out.println("\n--- HALL BOOKING SYSTEM MENU ---");
            System.out.println("[[Application Menu]]");
            System.out.println("<A> Booking");
            System.out.println("<B> Hall");
            System.out.println("<C> Showtime");
            System.out.println("<D> Reboot Showtime");
            System.out.println("<E> History");
            System.out.println("<F> Exit");

            System.out.print("Please select menu no: ");
            String op = scanner.nextLine(); // Read the entire line
            System.out.println();
            switch (op) {
                case "A":
                case "a":
                    booking(hallMorning,hallAfternoon,hallEvening, hrow, hcol);
                    break;
                case "B":
                case "b":
                    hall(hallMorning, hrow, hcol, 'A');
                    hall(hallAfternoon, hrow, hcol, 'B');
                    hall(hallEvening, hrow, hcol, 'C');
                    break;
                case "C":
                case "c":
                    showtimes();
                    break;
                case "D":
                case "d":
                    reboot(hallMorning, hallAfternoon, hallEvening, hrow, hcol);
                    break;
                case "E":
                case "e":
                    history();
                    break;
                case "F":
                case "f":
                    System.out.println("Exiting...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid Choice! Please Enter Correct Menu No.");
            }
        }
    }
}