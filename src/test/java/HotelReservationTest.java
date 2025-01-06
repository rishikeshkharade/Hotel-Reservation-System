import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HotelReservationTest {

    @Test
    void testAddHotel(){
        HotelReservation reservationSystem = new HotelReservation();
        reservationSystem.addHotel("Test Hotel",4,150,100,80,60);

        assertEquals(4, reservationSystem.hotels.size());
        assertEquals("Test Hotel", reservationSystem.hotels.get(3).name);
        assertEquals(4, reservationSystem.hotels.get(3).rating);
    }
}
