import java.util.NoSuchElementException;
import java.util.Scanner;

public class Menu {

    private final Scanner in = new Scanner(System.in);
    public String ask(String prompt) {
        System.out.print(prompt);
        try {
            return in.nextLine();
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("No more input");
        }
    }
    public void showMessage(String message) {
        System.out.println(message);
    }

    public void showError(String message) {
        System.out.println("! " + message);
    }

    public void showMainMenu(String name, int money, int miles) {
        System.out.println();
        System.out.println("=== LIFT OFF ===  " + name + "  |  $" + money + "  |  " + miles + " miles");
        System.out.println("  1. Choose a flight");
        System.out.println("  2. Buy a ticket");
        System.out.println("  3. Quit");
    }

    public void showFlights(Airplane[] planes) {
        System.out.println();
        System.out.println("Flights:");
        for (int i = 0; i < planes.length; i++) {
            System.out.println("  " + (i + 1) + ". Flight " + planes[i].getFlightNumber() + " to " + planes[i].getDestination() + " (" + planes[i].getDistance() + " miles)");
        }
    }

    public void showSeatMap(boolean[][] vipSeats, boolean[][] seats, int[] prices) {
        System.out.println();
        int width = seats[0].length;
        if (vipSeats.length > 0 && vipSeats[0].length > width) {
            width = vipSeats[0].length;
        }
        StringBuilder header = new StringBuilder("          ");
        for (int c = 0; c < width; c++) {
            header.append(" ").append((char) ('A' + c)).append("  ");
        }
        System.out.println(header);
        int row = 1;
        for (int i = 0; i < vipSeats.length; i++) {
            System.out.println(seatRow("VIP", row, vipSeats[i]));
            row++;
        }
        for (int i = 0; i < seats.length; i++) {
            System.out.println(seatRow("   ", row, seats[i]));
            row++;
        }
        System.out.println("  [X] = taken, [ ] = free");
        System.out.println("  Prices - VIP seat $" + prices[0] + ", regular seat $" + prices[1]
                + ", random seat $" + prices[2]);
    }

    public void showTicket(String name, String destination, int seatNumber, char seatPos,
                           int group, int price) {
        System.out.println();
        System.out.println("+----------------------------------+");
        System.out.println("|           BOARDING PASS          |");
        System.out.println("+----------------------------------+");
        System.out.println("  Passenger - " + name);
        System.out.println("  To        - " + destination);
        System.out.println("  Seat      - " + seatNumber + seatPos);
        System.out.println("  Boarding  - " + groupName(group));
        System.out.println("  Paid      - $" + price);
        System.out.println("+----------------------------------+");
    }

private String seatRow(String section, int row, boolean[] taken) {
        StringBuilder sb = new StringBuilder();
        sb.append(section).append(" ").append(String.format("%3d", row)).append("   ");
        for (int i = 0; i < taken.length; i++) {
            if (taken[i]) {
                sb.append("[X]");
            } else {
                sb.append("[ ]");
            }
            sb.append(" ");
        }
        return sb.toString();
    }

    private String groupName(int group) {
        if (group == 0) {
            return "Priority";
        }
        return "Group " + group;
    }
}