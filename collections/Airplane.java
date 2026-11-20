import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;


public class Airplane {
private final HashMap<String, int[]> PLANE_TYPES = new HashMap<>(Map.of(
        "Regional", new int[] {8, 4, 1, 2}, 
        "Standard", new int[] {20, 6, 3, 4}, 
        "Jumbo", new int[] {30, 6, 5, 4}));
    private final int flightNumber;
    private final String destination;
    private final int distance;
    private final String type;
    private final int rows;
    private final int seatsPerRow;
    private final int vipRows;
    private final int vipSeatsPerRow;
    private final int totalSeats;
    private final boolean[][] seat;
    private final boolean[][] vipSeat;
    private final int vipSeatPrice;
    private final int regularSeatPrice; 
    private final Random rng;

    /**
     * Builds a plane from one of the known types: Regional, Standard or Jumbo.
     */
    public Airplane(int flightNumber, String destination, int distance, String type, Random rng) {
        this.flightNumber = flightNumber;
        this.destination = destination;
        this.distance = distance;
        this.type = type;
        this.rows = PLANE_TYPES.get(type)[0];
        this.seatsPerRow = PLANE_TYPES.get(type)[1];
        this.vipRows = PLANE_TYPES.get(type)[2];
        this.vipSeatsPerRow = PLANE_TYPES.get(type)[3];
        this.totalSeats = rows * seatsPerRow + vipRows * vipSeatsPerRow;
        this.regularSeatPrice = 50 + distance / 10;
        this.vipSeatPrice = regularSeatPrice * 3;
        this.seat = new boolean[rows][seatsPerRow];
        this.vipSeat = new boolean[vipRows][vipSeatsPerRow];
        this.rng = rng;
    }

    public int getFlightNumber() {
        return flightNumber;
    }

    public String getDestination() {
        return destination;
    }

    public int getDistance() {
        return distance;
    }

    public String getType() {
        return type;
    }

    public int getRows() {
        return rows;
    }

    public int getVipRows() {
        return vipRows;
    }

    public int getSeatsPerRow() {
        return seatsPerRow;
    }

    public int getSeatsPerRowVip() {
        return vipSeatsPerRow;
    }

    public int getTotalRows() {
        return vipRows + rows;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public int getRegularSeatPrice() {
        return regularSeatPrice;
    }

    public int getVipSeatPrice() {
        return vipSeatPrice;
    }

    public int[] getPrices() {
        return new int[] {vipSeatPrice, regularSeatPrice, (int)(regularSeatPrice*0.75)};
    }

    public boolean[][] getSeat() {
        return copy(seat);
    }

    public boolean[][] getVipSeat() {
        return copy(vipSeat);
    }




    public Ticket buyChosenSeat(User u, int seatNumber, int col) {
        if (u == null) {
            throw new IllegalArgumentException("A buyer is required");
        }
        if (u.getTicket() != null) {
            throw new IllegalArgumentException("You already have a ticket");
        }
        if (seatNumber < 1 || seatNumber > vipRows + rows) {
            throw new IndexOutOfBoundsException("There is no row " + seatNumber + " on this plane");
        }
        boolean inVip = seatNumber <= vipRows;
        boolean[][] grid = seat;
        int r = seatNumber - vipRows - 1;
        int price = regularSeatPrice;
        if (inVip) {
            grid = vipSeat;
            r = seatNumber - 1;
            price = vipSeatPrice;
        }
        if (col < 0 || col >= grid[r].length) {
            throw new IndexOutOfBoundsException("Row " + seatNumber + " has no such seat letter");
        }
        String label = seatNumber + "" + (char) ('A' + col);
        if (inVip && !u.isVip()) {
            throw new UnsupportedOperationException(
                    "Seat " + label + " is a VIP seat and you are not a VIP");
        }
        if (grid[r][col]) {
            throw new IllegalStateException("Seat " + label + " is already taken");
        }
        boolean rearHalf = !inVip && r >= rows / 2;
        int group = Boarding.groupFor(u.isVip(), false, rearHalf);
        return sell(u, grid, r, col, seatNumber, price, group);
    }

    public Ticket buyRandomSeat(User u) {
        if (u == null) {
            throw new IllegalArgumentException("A buyer is required");
        }
        if (u.getTicket() != null) {
            throw new IllegalArgumentException("You already have a ticket");
        }
        List<int[]> free = new ArrayList<>();
        for (int r = 0; r < seat.length; r++) {
            for (int c = 0; c < seat[r].length; c++) {
                if (!seat[r][c]) {
                    free.add(new int[] {r, c});
                }
            }
        }
        if (free.isEmpty()) {
            throw new NoSuchElementException("No regular seats are left on this flight");
        }
        int[] pick = free.get(rng.nextInt(free.size()));
        int group = Boarding.groupFor(u.isVip(), true, false);
        return sell(u, seat, pick[0], pick[1], vipRows + pick[0] + 1, (int)(regularSeatPrice*0.75), group);
    }

    private Ticket sell(User u, boolean[][] grid, int r, int c, int seatNumber,
                        int price, int group) {
        u.spend(price); // throws before anything changes if the user cannot pay
        grid[r][c] = true;
        Ticket t = new Ticket(this, u.getName(), seatNumber, (char) ('A' + c), group, price);
        u.setTicket(t);
        return t;
    }

    private static boolean[][] copy(boolean[][] grid) {
        boolean[][] out = new boolean[grid.length][];
        for (int i = 0; i < grid.length; i++) {
            out[i] = grid[i].clone();
        }
        return out;
    }
}
