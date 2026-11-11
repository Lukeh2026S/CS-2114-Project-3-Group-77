import java.util.HashMap;


public class Airplane {

	final String destination;
	final int distance;
	final Hashmap<String, int[]> planeType = Map.of("747", {2, 2, 20, 3, 300, 200});
	private boolean vip[][];
	private boolean seat[][];
	private int vipSeatPrice;
	private int seatPrice;
	private int rows;
	private int vipRows;
	private int numOfSeats;
	private int numOfVipSeats;

	public Airplane(String destination, int distance, String type) {
		this.destination = destination;
		this.distance = distance;
		this.rows = planeType.get(type)[2];
		this.vipRows = planeType.get(type)[0];
		this.numOfSeats = planeType.get(type)[3];
		this.numOfVipSeats = planeType.get(type)[1];
		this.vipSeatPrice = planeType.get(type)[4];
		this.seatPrice = planeType.get(type)[5];
		this.vip = new boolean[vipRows][numOfVipSeats];
		this.seat = new boolean[rows][numOfSeats];

	}
	
	public void DisplaySeatChart() {}

	public int getRows() {
		return planeType.get(type)[2];
	}

	public int getVipRows() {
		return planeType.get(type)[0];
	}

	public int getSeatsPerRows() {
		return planeType.get(type)[3];
	}

	public int getSeatsPerVipRows() {
		return planeType.get(type)[1];
	}

	public int getSeatPrice(int row, char seat) {
			
	}
	public boolean getSeatFree(int row, char seat) {
		int seatNum = seat - "a";
		if (row - 1 < vipRows) {
			if (seatNum > numOfVipSeats) {
				throw IndexOutOfBoundsException;
			}
			return vip[row - 1][seatNum];
		} else if (row - 1 < rows) {
			if (seatNum > numOfSeats) {
				throw IndexOutOfBoundsException;
			}
			return seat[row - 1][seatNum];

		} else {
			throw IndexOutOfBoundsException;
		}

	}
}
