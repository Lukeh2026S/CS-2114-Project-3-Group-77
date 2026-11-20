import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TicketTest {

    private Ticket ticket;

    @BeforeEach
    void setUp() {
        Airplane plane = new Airplane(101, "NYC", 300, "Regional", new java.util.Random(1));
        ticket = new Ticket(plane, "Alice", 5, 'C', 2, 80);
    }

    /** Verifies getPlane returns the constructor plane. */
    @Test
    void testGetPlane() {
        assertNotNull(ticket.getPlane());
        assertEquals(101, ticket.getPlane().getFlightNumber());
    }

    /** Verifies getName returns the constructor name. */
    @Test
    void testGetName() {
        assertEquals("Alice", ticket.getName());
    }

    /** Verifies getSeatNum returns the constructor seat number. */
    @Test
    void testGetSeatNum() {
        assertEquals(5, ticket.getSeatNum());
    }

    /** Verifies getSeatChar returns the constructor seat character. */
    @Test
    void testGetSeatChar() {
        assertEquals('C', ticket.getSeatChar());
    }

    /** Verifies getBoardingGroup returns the constructor group. */
    @Test
    void testGetBoardingGroup() {
        assertEquals(2, ticket.getBoardingGroup());
    }

    /** Verifies getPrice returns the constructor price. */
    @Test
    void testGetPrice() {
        assertEquals(80, ticket.getPrice());
    }
}   