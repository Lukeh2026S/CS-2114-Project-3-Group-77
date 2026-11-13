import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QueueTests {




    // addFirst: head == null
    @Test
    void addFirst_emptyList() {
        Boarding.TicketLine b = new Boarding.TicketLine();
        Ticket t = new Ticket(new Airplane(101, "Atlanta", 400, "Standard"), "Test", 12, "A".charAt(0), 1);
        b.addFirst(t);
        assertEquals(1, b.size());
        assertSame(t, b.peekFirst());
        assertSame(t, b.peekLast());
    }

    // addFirst: head != null
    @Test
    void addFirst_nonEmptyList() {
        Boarding.TicketLine b = new Boarding.TicketLine();
        Ticket first = new Ticket(new Airplane(101, "Atlanta", 400, "Standard"), "Test", 12, "A".charAt(0), 1);
        Ticket second = new Ticket(new Airplane(101, "Atlanta", 400, "Standard"), "Test", 12, "A".charAt(0), 1);
        b.addFirst(first);
        b.addFirst(second);
        assertEquals(2, b.size());
        assertSame(second, b.peekFirst());
        assertSame(first, b.peekLast());
    }

    // addLast: tail == null
    @Test
    void addLast_emptyList() {
        Boarding.TicketLine b = new Boarding.TicketLine();
        Ticket t = new Ticket(new Airplane(101, "Atlanta", 400, "Standard"), "Test", 12, "A".charAt(0), 1);
        b.addLast(t);
        assertEquals(1, b.size());
        assertSame(t, b.peekFirst());
        assertSame(t, b.peekLast());
    }

    // addLast: tail != null
    @Test
    void addLast_nonEmptyList() {
        Boarding.TicketLine b = new Boarding.TicketLine();
        Ticket first = new Ticket(new Airplane(101, "Atlanta", 400, "Standard"), "Test", 12, "A".charAt(0), 1);
        Ticket second = new Ticket(new Airplane(101, "Atlanta", 400, "Standard"), "Test", 12, "A".charAt(0), 1);
        b.addLast(first);
        b.addLast(second);
        assertEquals(2, b.size());
        assertSame(first, b.peekFirst());
        assertSame(second, b.peekLast());
    }

    // removeFirst: head == null
    @Test
    void removeFirst_emptyList() {
        Boarding.TicketLine b = new Boarding.TicketLine();
        assertNull(b.removeFirst());
    }

    // removeFirst: head != null, single element (head becomes null)
    @Test
    void removeFirst_singleElement() {
        Boarding.TicketLine b = new Boarding.TicketLine();
        Ticket t = new Ticket(new Airplane(101, "Atlanta", 400, "Standard"), "Test", 12, "A".charAt(0), 1);
        b.addLast(t);
        assertSame(t, b.removeFirst());
        assertEquals(0, b.size());
        assertNull(b.peekFirst());
        assertNull(b.peekLast());
    }

    // removeFirst: head != null, multiple elements (head != null after removal)
    @Test
    void removeFirst_multipleElements() {
        Boarding.TicketLine b = new Boarding.TicketLine();
        Ticket first = new Ticket(new Airplane(101, "Atlanta", 400, "Standard"), "Test", 12, "A".charAt(0), 1);
        Ticket second = new Ticket(new Airplane(101, "Atlanta", 400, "Standard"), "Test", 12, "A".charAt(0), 1);
        b.addLast(first);
        b.addLast(second);
        assertSame(first, b.removeFirst());
        assertEquals(1, b.size());
        assertSame(second, b.peekFirst());
    }

    // peekFirst: head == null
    @Test
    void peekFirst_emptyList() {
        Boarding.TicketLine b = new Boarding.TicketLine();
        assertNull(b.peekFirst());
    }

    // peekFirst: head != null
    @Test
    void peekFirst_nonEmptyList() {
        Boarding.TicketLine b = new Boarding.TicketLine();
        Ticket t = new Ticket(new Airplane(101, "Atlanta", 400, "Standard"), "Test", 12, "A".charAt(0), 1);
        b.addLast(t);
        assertSame(t, b.peekFirst());
    }

    // peekLast: tail == null
    @Test
    void peekLast_emptyList() {
        Boarding.TicketLine b = new Boarding.TicketLine();
        assertNull(b.peekLast());
    }

    // peekLast: tail != null
    @Test
    void peekLast_nonEmptyList() {
        Boarding.TicketLine b = new Boarding.TicketLine();
        Ticket t = new Ticket(new Airplane(101, "Atlanta", 400, "Standard"), "Test", 12, "A".charAt(0), 1);
        b.addLast(t);
        assertSame(t, b.peekLast());
    }

    // indexOf: ticket found
    @Test
    void indexOf_ticketFound() {
        Boarding.TicketLine b = new Boarding.TicketLine();
        Ticket first = new Ticket(new Airplane(101, "Atlanta", 400, "Standard"), "Test", 12, "A".charAt(0), 1);
        Ticket second = new Ticket(new Airplane(101, "Atlanta", 400, "Standard"), "Test", 12, "A".charAt(0), 1);
        b.addLast(first);
        b.addLast(second);
        assertEquals(1, b.indexOf(second));
    }

    // indexOf: ticket not found
    @Test
    void indexOf_ticketNotFound() {
        Boarding.TicketLine b = new Boarding.TicketLine();
        Ticket t = new Ticket(new Airplane(101, "Atlanta", 400, "Standard"), "Test", 12, "A".charAt(0), 1);
        b.addLast(t);
        assertEquals(-1, b.indexOf(new Ticket(new Airplane(101, "Atlanta", 400, "Standard"), "Test", 12, "A".charAt(0), 1)));
    }
}
