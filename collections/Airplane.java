import java.util.HashMap;


public class Airplane {

	final String destination;
	final int distance;
	final Hashmap<String, int[]> planeType = Map.of("747", {2, 2, 20, 3, 300, 200});
	private boolean vip[][];
	private boolean seat[][];
	private int vipSeatPrice;
	private int seatPrice;

	public Airplane(String destination, int distance, String type) {
		this.destination = destination;
		this.deistance = distance;
		this.vip = new boolean[planeType.get(type)[0]][planeType.get(type)[1]]
		this.seat = new boolean[planeType.get(type)[2]][planeType.get(type)[3]]
		this.vipSeatPrice = planeType.get(type)[4];
		this.seatPrice = planeType.get(type)[5];

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
}
