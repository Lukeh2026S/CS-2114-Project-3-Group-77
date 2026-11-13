import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;


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

    /**
     * Builds a plane from one of the known types: Regional, Standard or Jumbo.
     */
    public Airplane(int flightNumber, String destination, int distance, String type) {
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

    public boolean isVipRow(int row) {
        return row >= 1 && row <= vipRows;
    }

    private int seatsInRow(int row) {
        if(isVipRow(row)) {
            return vipSeatsPerRow;
        }
            return seatsPerRow;
    }

    public boolean isValidSeat(int row, char seatLetter) {
        if (row < 1 || row > getTotalRows()) {
            return false;
        }
        int col = Character.toUpperCase(seatLetter) - 'A';
        return col >= 0 && col < seatsInRow(row);
    }

    private boolean[][] sectionFor(int row) {
        if (isVipRow(row)){
            return vipSeat;
        }
        return seat;
    }

    private int rowIndex(int row) {
        if (isVipRow(row)){
            return row - 1;
        }
        return row - vipRows - 1;
    }

    public boolean isSeatTaken(int row, char seatLetter) {
        if (!isValidSeat(row, seatLetter)) {
            throw new IllegalArgumentException("Seat " + row + Character.toUpperCase(seatLetter)
                    + " does not exist on this plane.");
        }
        return sectionFor(row)[rowIndex(row)][Character.toUpperCase(seatLetter) - 'A'];
    }

    public int getSeatPrice(int row, char seatLetter) {
        if (!isValidSeat(row, seatLetter)) {
            throw new IllegalArgumentException("Seat " + row + Character.toUpperCase(seatLetter)
                    + " does not exist on this plane.");
        }
        if(isVipRow(row)) {
            return vipSeatPrice;
        }
        return regularSeatPrice;
    }

    public int getBoardingGroupForRow(int row) {
        if (row < 1 || row > getTotalRows()) {
            throw new IllegalArgumentException("Row " + row + " does not exist on this plane.");
        }
        if (isVipRow(row)) {
            return 0;
        }
        return 3 - (rowIndex(row) * 3 / rows);
    }

    public Ticket buyChosenSeat(String name, int row, char seatLetter) {
        if (isSeatTaken(row, seatLetter)) {
            throw new IllegalStateException("Seat " + row + Character.toUpperCase(seatLetter) + " is already taken.");
        }
        sectionFor(row)[rowIndex(row)][Character.toUpperCase(seatLetter) - 'A'] = true;
        return new Ticket(this, name, row, seatLetter, getBoardingGroupForRow(row));
    }

    public List<String> getAvailableSeats(boolean vip) {
        List<String> open = new ArrayList<>();
        int firstRow;
        int lastRow;
        if (vip) {
            firstRow = 1; 
            lastRow = vipRows;
        } else {
            firstRow = vipRows + 1;
            lastRow = getTotalRows();
        }
        for (int row = firstRow; row <= lastRow; row++) {
            for (int col = 0; col < seatsInRow(row); col++) {
                if (!sectionFor(row)[rowIndex(row)][col]) {
                    open.add(row + "" + (char) ('A' + col));
                }
            }
        }
        return open;
    }

    public List<String> getAvailableSeats() {
        List<String> open = getAvailableSeats(true);
        open.addAll(getAvailableSeats(false));
        return open;
    }

    public int getAvailableSeatCount() {
        return getAvailableSeats().size();
    }

    public int getOccupiedSeatCount() {
        return totalSeats - getAvailableSeatCount();
    }

    public boolean isFull() {
        return getAvailableSeatCount() == 0;
    }

    public static int rowOf(String seatLabel) {
        return Integer.parseInt(seatLabel.substring(0, seatLabel.length() - 1));
    }

    public static char letterOf(String seatLabel) {
        return Character.toUpperCase(seatLabel.charAt(seatLabel.length() - 1));
    }

    public String getSeatChart() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Flight %d to %s (%s), %d of %d seats open\n%8s",
                flightNumber, destination, type, getAvailableSeatCount(), totalSeats, ""));
        for (int c = 0; c < seatsPerRow; c++) {
            if (seatsPerRow >= 4 && c == seatsPerRow / 2) {
                sb.append(" ");
            }
            sb.append(' ').append((char) ('A' + c)).append(' ');
        }
        sb.append("\n");
        for (int row = 1; row <= getTotalRows(); row++) {
            if (row == 1 && vipRows > 0) {
                sb.append(String.format("VIP / First class ($%d)\n", vipSeatPrice));
            }
            if (row == vipRows + 1) {
                sb.append(String.format("Regular ($%d)\n", regularSeatPrice));
            }
            sb.append(String.format("Row %2d  ", row));
            int n = seatsInRow(row);
            for (int c = 0; c < n; c++) {
                if (n >= 4 && c == n / 2) {
                    sb.append(" ");
                }
                if (sectionFor(row)[rowIndex(row)][c]) {
                    sb.append("[X]");
                } else {
                    sb.append("[ ]");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public void displaySeatChart() {
        System.out.print(getSeatChart());
    }
}
