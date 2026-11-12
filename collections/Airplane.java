import java.util.HashMap;
import java.util.Map;


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
}
