import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

class UserSimTest {

    private Airplane plane;
    private Boarding boarding;

    @BeforeEach
    void setUp() {
        plane = new Airplane(1, "NYC", 100, "Standard", new Random(42));
        boarding = new Boarding();
    }

    /** Verifies constructor accepts valid arguments. */
    @Test
    void testConstructorValid() {
        UserSim sim = new UserSim(5, new Random(1));
        assertNotNull(sim);
    }

    /** Verifies constructor rejects negative seatsToFill. */
    @Test
    void testConstructorNegativeSeats() {
        assertThrows(IllegalArgumentException.class, () -> new UserSim(-1, new Random(1)));
    }

    /** Verifies constructor rejects a null Random. */
    @Test
    void testConstructorNullRng() {
        assertThrows(IllegalArgumentException.class, () -> new UserSim(5, null));
    }

    /** Verifies populate throws when airplane is null. */
    @Test
    void testPopulateNullAirplane() {
        UserSim sim = new UserSim(3, new Random(1));
        assertThrows(IllegalArgumentException.class, () -> sim.populate(null, boarding));
    }

    /** Verifies populate throws when boarding is null. */
    @Test
    void testPopulateNullBoarding() {
        UserSim sim = new UserSim(3, new Random(1));
        assertThrows(IllegalArgumentException.class, () -> sim.populate(plane, null));
    }

    /** Verifies populate adds simulated users to the boarding line. */
    @Test
    void testPopulateAddsUsers() {
        UserSim sim = new UserSim(3, new Random(42));
        sim.populate(plane, boarding);
        assertFalse(boarding.isEmpty());
    }
}   