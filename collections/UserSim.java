import java.util.Random;

public class UserSim {
    private static final String[] FIRST = {
        "Alex", "Sam", "Jordan", "Taylor", "Morgan", "Casey", "Riley", "Jamie",
        "Avery", "Quinn", "Drew", "Parker", "Reese", "Skyler", "Devon", "Rowan"
    };
    private static final String[] LAST = {
        "Smith", "Lee", "Garcia", "Patel", "Nguyen", "Brown", "Kim", "Lopez",
        "Walker", "Young", "Hall", "Allen", "Scott", "Adams", "Baker", "Clark"
    };

    private final int seatsToFill;
    private final Random rng;

    public UserSim(int seatsToFill, Random rng) {
        if (seatsToFill < 0) {
            throw new IllegalArgumentException("Seats to fill cannot be negative");
        }
        if (rng == null) {
            throw new IllegalArgumentException("A random source is required");
        }
        this.seatsToFill = seatsToFill;
        this.rng = rng;
    }

    /** Buys up to seatsToFill seats and queues each sim in boarding. */
    public void populate(Airplane airplane, Boarding boarding) {
        if (airplane == null || boarding == null) {
            throw new IllegalArgumentException("An airplane and a boarding line are required");
        }
        for (int i = 0; i < seatsToFill; i++) {
            if (freeRegularSeats(airplane) <= 1) {
                return; // keep one regular seat open for the player
            }
            boolean vip = rng.nextDouble() < 0.1;
            String name = FIRST[rng.nextInt(FIRST.length)] + " " + LAST[rng.nextInt(LAST.length)];
            User sim = new User(name, 5000, vip, true, false);
            boolean bought = rng.nextDouble() < 0.7 && tryChosenSeat(airplane, sim);
            if (!bought) {
                airplane.buyRandomSeat(sim);
            }
            boarding.addToLine(sim);
        }
    }

    private boolean tryChosenSeat(Airplane airplane, User sim) {
        boolean[][] vipSeats = airplane.getVipSeat();
        boolean[][] seats = airplane.getSeat();
        int totalRows = vipSeats.length + seats.length;
        for (int attempt = 0; attempt < 5; attempt++) {
            int row = 1 + rng.nextInt(totalRows);
            int width = seats[0].length;
            if (row <= vipSeats.length) {
                width = vipSeats[0].length;
            }
            try {
                airplane.buyChosenSeat(sim, row, rng.nextInt(width));
                return true;
            } catch (IllegalStateException | UnsupportedOperationException e) {
                // taken, or a VIP seat for a non-VIP: try another pick
            }
        }
        return false;
    }

    private int freeRegularSeats(Airplane airplane) {
        int free = 0;
        boolean[][] seats = airplane.getSeat();
        for (int r = 0; r < seats.length; r++) {
            for (int c = 0; c < seats[r].length; c++) {
                if (!seats[r][c]) {
                    free++;
                }
            }
        }
        return free;
    }
}
