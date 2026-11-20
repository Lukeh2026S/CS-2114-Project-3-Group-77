import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

class AirplaneTest {

    private Airplane plane;
    private User user;

    @BeforeEach
    void setUp() {
        plane = new Airplane(200, "Chicago", 100, "Standard", new Random(42));
        user = new User("Alice", 5000, false);
    }

    /** Verifies getFlightNumber returns the constructor value. */
    @Test
    void testGetFlightNumber() {
        assertEquals(200, plane.getFlightNumber());
    }

    /** Verifies getDestination returns the constructor value. */
    @Test
    void testGetDestination() {
        assertEquals("Chicago", plane.getDestination());
    }

    /** Verifies getDistance returns the constructor value. */
    @Test
    void testGetDistance() {
        assertEquals(100, plane.getDistance());
    }

    /** Verifies getType returns the constructor value. */
    @Test
    void testGetType() {
        assertEquals("Standard", plane.getType());
    }

    /** Verifies getRows matches the Standard type (20). */
    @Test
    void testGetRows() {
        assertEquals(20, plane.getRows());
    }

    /** Verifies getVipRows matches the Standard type (3). */
    @Test
    void testGetVipRows() {
        assertEquals(3, plane.getVipRows());
    }

    /** Verifies getSeatsPerRow matches the Standard type (6). */
    @Test
    void testGetSeatsPerRow() {
        assertEquals(6, plane.getSeatsPerRow());
    }

    /** Verifies getSeatsPerRowVip matches the Standard type (4). */
    @Test
    void testGetSeatsPerRowVip() {
        assertEquals(4, plane.getSeatsPerRowVip());
    }

    /** Verifies getTotalRows is vipRows + rows (23). */
    @Test
    void testGetTotalRows() {
        assertEquals(23, plane.getTotalRows());
    }

    /** Verifies getTotalSeats is 20*6 + 3*4 = 132. */
    @Test
    void testGetTotalSeats() {
        assertEquals(132, plane.getTotalSeats());
    }

    /** Verifies regular seat price is 50 + distance/10. */
    @Test
    void testGetRegularSeatPrice() {
        assertEquals(60, plane.getRegularSeatPrice());
    }

    /** Verifies VIP seat price is triple the regular price. */
    @Test
    void testGetVipSeatPrice() {
        assertEquals(180, plane.getVipSeatPrice());
    }

    /** Verifies getPrices returns {vip, regular, 75%}. */
    @Test
    void testGetPrices() {
        int[] prices = plane.getPrices();
        assertArrayEquals(new int[]{180, 60, 45}, prices);
    }

    /** Verifies getSeat returns a grid with all false initially. */
    @Test
    void testGetSeatInitiallyEmpty() {
        boolean[][] seats = plane.getSeat();
        assertFalse(seats[0][0]);
    }

    /** Verifies getVipSeat returns a grid with all false initially. */
    @Test
    void testGetVipSeatInitiallyEmpty() {
        boolean[][] vip = plane.getVipSeat();
        assertFalse(vip[0][0]);
    }

    /** Verifies buyChosenSeat assigns a regular seat and deducts money. */
    @Test
    void testBuyChosenSeatRegular() {
        Ticket t = plane.buyChosenSeat(user, 4, 0);
        assertEquals(4, t.getSeatNum());
    }

    /** Verifies buyChosenSeat assigns a VIP seat for a VIP user. */
    @Test
    void testBuyChosenSeatVip() {
        User vip = new User("Vip", 5000, true);
        Ticket t = plane.buyChosenSeat(vip, 1, 0);
        assertEquals(1, t.getSeatNum());
    }

    /** Verifies buyChosenSeat throws for a null user. */
    @Test
    void testBuyChosenSeatNullUser() {
        assertThrows(IllegalArgumentException.class, () -> plane.buyChosenSeat(null, 4, 0));
    }

    /** Verifies buyChosenSeat throws if user already has a ticket. */
    @Test
    void testBuyChosenSeatAlreadyHasTicket() {
        plane.buyChosenSeat(user, 4, 0);
        assertThrows(IllegalArgumentException.class, () -> plane.buyChosenSeat(user, 5, 0));
    }

    /** Verifies buyChosenSeat throws for an out-of-range row. */
    @Test
    void testBuyChosenSeatInvalidRow() {
        assertThrows(IndexOutOfBoundsException.class, () -> plane.buyChosenSeat(user, 99, 0));
    }

    /** Verifies buyChosenSeat throws for an out-of-range column. */
    @Test
    void testBuyChosenSeatInvalidCol() {
        assertThrows(IndexOutOfBoundsException.class, () -> plane.buyChosenSeat(user, 4, 99));
    }

    /** Verifies buyChosenSeat throws when non-VIP picks a VIP seat. */
    @Test
    void testBuyChosenSeatNonVipVipSeat() {
        assertThrows(UnsupportedOperationException.class, () -> plane.buyChosenSeat(user, 1, 0));
    }

    /** Verifies buyChosenSeat throws when the seat is already taken. */
    @Test
    void testBuyChosenSeatAlreadyTaken() {
        plane.buyChosenSeat(user, 4, 0);
        User u2 = new User("Bob", 5000, false);
        assertThrows(IllegalStateException.class, () -> plane.buyChosenSeat(u2, 4, 0));
    }

    /** Verifies buyRandomSeat assigns a regular seat at a discount. */
    @Test
    void testBuyRandomSeat() {
        Ticket t = plane.buyRandomSeat(user);
        assertEquals(45, t.getPrice());
    }

    /** Verifies buyRandomSeat throws for a null user. */
    @Test
    void testBuyRandomSeatNullUser() {
        assertThrows(IllegalArgumentException.class, () -> plane.buyRandomSeat(null));
    }

    /** Verifies buyRandomSeat throws if user already has a ticket. */
    @Test
    void testBuyRandomSeatAlreadyHasTicket() {
        plane.buyRandomSeat(user);
        assertThrows(IllegalArgumentException.class, () -> plane.buyRandomSeat(user));
    }

    /** Verifies buyRandomSeat throws when no regular seats remain. */
    @Test
    void testBuyRandomSeatNoSeatsLeft() {
        Airplane small = new Airplane(1, "X", 100, "Regional", new Random(1));
        User[] users = new User[32];
        for (int i = 0; i < 32; i++) {
            users[i] = new User("U" + i, 5000, false);
            small.buyRandomSeat(users[i]);
        }
        assertThrows(java.util.NoSuchElementException.class, () -> small.buyRandomSeat(new User("Last", 5000, false)));
    }
}   