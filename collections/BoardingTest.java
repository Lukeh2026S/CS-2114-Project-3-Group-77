import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Random;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.*;

class BoardingTest {

    private Boarding boarding;
    private Airplane plane;

    @BeforeEach
    void setUp() {
        boarding = new Boarding();
        plane = new Airplane(1, "NYC", 100, "Standard", new Random(1));
    }

    /** Verifies addFirst places the user at the front of the queue. */
    @Test
    void testQueueAddFirst() {
        Boarding.Queue q = new Boarding.Queue();
        User u1 = new User("A", 100, false);
        User u2 = new User("B", 100, false);
        q.addLast(u1);
        q.addFirst(u2);
        assertEquals(u2, q.peekFirst());
    }

    /** Verifies addLast places the user at the end of the queue. */
    @Test
    void testQueueAddLast() {
        Boarding.Queue q = new Boarding.Queue();
        User u1 = new User("A", 100, false);
        User u2 = new User("B", 100, false);
        q.addLast(u1);
        q.addLast(u2);
        assertEquals(u2, q.peekLast());
    }

    /** Verifies removeFirst returns the front user and shrinks the queue. */
    @Test
    void testQueueRemoveFirst() {
        Boarding.Queue q = new Boarding.Queue();
        User u = new User("A", 100, false);
        q.addLast(u);
        assertEquals(u, q.removeFirst());
        assertEquals(0, q.size());
    }

    /** Verifies removeFirst returns null on an empty queue. */
    @Test
    void testQueueRemoveFirstEmpty() {
        Boarding.Queue q = new Boarding.Queue();
        assertNull(q.removeFirst());
    }

    /** Verifies peekFirst returns the front user without removing it. */
    @Test
    void testQueuePeekFirst() {
        Boarding.Queue q = new Boarding.Queue();
        User u = new User("A", 100, false);
        q.addLast(u);
        assertEquals(u, q.peekFirst());
    }

    /** Verifies peekFirst returns null on an empty queue. */
    @Test
    void testQueuePeekFirstEmpty() {
        Boarding.Queue q = new Boarding.Queue();
        assertNull(q.peekFirst());
    }

    /** Verifies peekLast returns the last user without removing it. */
    @Test
    void testQueuePeekLast() {
        Boarding.Queue q = new Boarding.Queue();
        User u = new User("A", 100, false);
        q.addLast(u);
        assertEquals(u, q.peekLast());
    }

    /** Verifies peekLast returns null on an empty queue. */
    @Test
    void testQueuePeekLastEmpty() {
        Boarding.Queue q = new Boarding.Queue();
        assertNull(q.peekLast());
    }

    /** Verifies indexOf returns the correct position. */
    @Test
    void testQueueIndexOf() {
        Boarding.Queue q = new Boarding.Queue();
        User u1 = new User("A", 100, false);
        User u2 = new User("B", 100, false);
        q.addLast(u1);
        q.addLast(u2);
        assertEquals(1, q.indexOf(u2));
    }

    /** Verifies indexOf returns -1 when user is not in the queue. */
    @Test
    void testQueueIndexOfNotFound() {
        Boarding.Queue q = new Boarding.Queue();
        User u = new User("A", 100, false);
        assertEquals(-1, q.indexOf(u));
    }

    /** Verifies size tracks the number of elements. */
    @Test
    void testQueueSize() {
        Boarding.Queue q = new Boarding.Queue();
        q.addLast(new User("A", 100, false));
        q.addLast(new User("B", 100, false));
        assertEquals(2, q.size());
    }

    /** Verifies isEmpty is true for a new queue. */
    @Test
    void testQueueIsEmpty() {
        Boarding.Queue q = new Boarding.Queue();
        assertTrue(q.isEmpty());
    }

    /** Verifies contains returns true for a user in the queue. */
    @Test
    void testQueueContains() {
        Boarding.Queue q = new Boarding.Queue();
        User u = new User("A", 100, false);
        q.addLast(u);
        assertTrue(q.contains(u));
    }

    /** Verifies getQueue returns the correct group queue. */
    @Test
    void testGetQueue() {
        Boarding.Queue q = boarding.getQueue(0);
        assertNotNull(q);
    }

    /** Verifies addToLine places a ticketed user in the correct group. */
    @Test
    void testAddToLine() {
        User u = new User("A", 5000, false);
        plane.buyChosenSeat(u, 4, 0);
        boarding.addToLine(u);
        assertTrue(boarding.getQueue(2).contains(u));
    }

    /** Verifies addToLine throws if the user is already queued. */
    @Test
    void testAddToLineDuplicate() {
        User u = new User("A", 5000, false);
        plane.buyChosenSeat(u, 4, 0);
        boarding.addToLine(u);
        assertThrows(IllegalArgumentException.class, () -> boarding.addToLine(u));
    }

    /** Verifies addToLineFirst places the user at the front of the group. */
    @Test
    void testAddToLineFirst() {
        User u1 = new User("A", 5000, false);
        User u2 = new User("B", 5000, false);
        plane.buyChosenSeat(u1, 4, 0);
        plane.buyChosenSeat(u2, 5, 0);
        boarding.addToLine(u1);
        boarding.addToLineFirst(u2);
        assertEquals(u2, boarding.getQueue(2).peekFirst());
    }

    /** Verifies dequeue returns the first user in group order. */
    @Test
    void testDequeue() {
        User u = new User("A", 5000, false);
        plane.buyChosenSeat(u, 4, 0);
        boarding.addToLine(u);
        assertEquals(u, boarding.dequeue());
    }

    /** Verifies dequeue throws when all queues are empty. */
    @Test
    void testDequeueEmpty() {
        assertThrows(NoSuchElementException.class, () -> boarding.dequeue());
    }

    /** Verifies peek returns the next user without removing them. */
    @Test
    void testPeek() {
        User u = new User("A", 5000, false);
        plane.buyChosenSeat(u, 4, 0);
        boarding.addToLine(u);
        assertEquals(u, boarding.peek());
    }

    /** Verifies peek throws when all queues are empty. */
    @Test
    void testPeekEmpty() {
        assertThrows(NoSuchElementException.class, () -> boarding.peek());
    }

    /** Verifies isEmpty is true when no queues have users. */
    @Test
    void testIsEmpty() {
        assertTrue(boarding.isEmpty());
    }

    /** Verifies groupFor returns 0 for a VIP. */
    @Test
    void testGroupForVip() {
        assertEquals(0, Boarding.groupFor(true, false, false));
    }

    /** Verifies groupFor returns 3 for a random seat. */
    @Test
    void testGroupForRandom() {
        assertEquals(3, Boarding.groupFor(false, true, false));
    }

    /** Verifies groupFor returns 1 for rear half, non-VIP, chosen seat. */
    @Test
    void testGroupForRearHalf() {
        assertEquals(1, Boarding.groupFor(false, false, true));
    }

    /** Verifies groupFor returns 2 for front half, non-VIP, chosen seat. */
    @Test
    void testGroupForDefault() {
        assertEquals(2, Boarding.groupFor(false, false, false));
    }
}   