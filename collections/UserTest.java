import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User("Bob", 1000, false);
    }

    /** Verifies getName returns the trimmed name. */
    @Test
    void testGetName() {
        assertEquals("Bob", user.getName());
    }

    /** Verifies getMoney returns the initial money. */
    @Test
    void testGetMoney() {
        assertEquals(1000, user.getMoney());
    }

    /** Verifies getMiles starts at zero. */
    @Test
    void testGetMiles() {
        assertEquals(0, user.getMiles());
    }

    /** Verifies isVip reflects the constructor value. */
    @Test
    void testIsVip() {
        assertFalse(user.isVip());
    }

    /** Verifies getTicket is null before assignment. */
    @Test
    void testGetTicketInitiallyNull() {
        assertNull(user.getTicket());
    }

    /** Verifies constructor rejects a null name. */
    @Test
    void testConstructorNullName() {
        assertThrows(IllegalArgumentException.class, () -> new User(null, 100, false));
    }

    /** Verifies constructor rejects a blank name. */
    @Test
    void testConstructorBlankName() {
        assertThrows(IllegalArgumentException.class, () -> new User("   ", 100, false));
    }

    /** Verifies constructor rejects negative money. */
    @Test
    void testConstructorNegativeMoney() {
        assertThrows(IllegalArgumentException.class, () -> new User("Bob", -1, false));
    }

    /** Verifies spend reduces money by the amount. */
    @Test
    void testSpendSuccess() {
        user.spend(300);
        assertEquals(700, user.getMoney());
    }

    /** Verifies spend throws when amount exceeds money. */
    @Test
    void testSpendInsufficient() {
        assertThrows(IllegalArgumentException.class, () -> user.spend(2000));
    }

    /** Verifies addMiles increases the miles field. */
    @Test
    void testAddMiles() {
        user.addMiles(500);
        assertEquals(500, user.getMiles());
    }

    /** Verifies setTicket assigns a ticket to the user. */
    @Test
    void testSetTicket() {
        Airplane plane = new Airplane(1, "LA", 200, "Regional", new java.util.Random(1));
        Ticket t = new Ticket(plane, "Bob", 1, 'A', 2, 70);
        user.setTicket(t);
        assertEquals(t, user.getTicket());
    }
}   