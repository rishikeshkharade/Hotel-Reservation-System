import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

class InvalidInputException extends Exception {
    public InvalidInputException(String message) {
        super(message);
    }
}

class Hotel {
    String name;
    int rating;
    int weekdayRateRegular;
    int weekdayRateRewards;
    int weekendRateRegular;
    int weekendRateRewards;

    public Hotel(String name, int rating, int weekdayRateRegular, int weekdayRateRewards, int weekendRateRegular, int weekendRateRewards) {
        this.name = name;
        this.rating = rating;
        this.weekdayRateRegular = weekdayRateRegular;
        this.weekdayRateRewards = weekdayRateRewards;
        this.weekendRateRegular = weekendRateRegular;
        this.weekendRateRewards = weekendRateRewards;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setWeekdayRates(int weekdayRateRegular, int weekdayRateRewards){
        this.weekdayRateRegular = weekdayRateRegular;
        this.weekdayRateRewards = weekdayRateRewards;
    }

    public void setWeekendRates(int weekendRateRegular, int weekendRateRewards){
        this.weekendRateRegular = weekendRateRegular;
        this.weekendRateRewards = weekendRateRewards;
    }

   public int getRate(String customerType, Date date){
        int dayOfWeek = date.getDay();
        if (dayOfWeek >= 1 && dayOfWeek <=5){
            return customerType.equals("Regular") ? weekdayRateRegular : weekdayRateRewards;
        }else {
            return customerType.equals("Regular") ? weekendRateRegular : weekendRateRewards;
        }
    }
}
class HotelReservation {
    List<Hotel> hotels;

    public HotelReservation() {
        hotels = new ArrayList<>();
        hotels.add(new Hotel("Lakewood", 3, 110, 80, 90, 80));
        hotels.add(new Hotel("Bridgewood", 4, 160, 110, 60, 50));
        hotels.add(new Hotel("Ridgewood", 5, 220, 100, 150, 40));
    }

    public void addHotel(String name, int rating, int weekdayRateRegular, int weekdayRateRewards, int weekendRateRegular, int weekendRateRewards) {
        hotels.add(new Hotel(name, rating, weekdayRateRegular, weekdayRateRewards, weekendRateRegular, weekendRateRewards));
    }

    public void setHotelRates(String hotelName, int weekdayRateRegular, int weekdayRateRewards, int weekendRateRegular, int weekendRateRewards) {
        for (Hotel hotel : hotels) {
            if (hotel.name.equals(hotelName)) {
                hotel.setWeekdayRates(weekdayRateRegular, weekdayRateRewards);
                hotel.setWeekendRates(weekendRateRegular, weekendRateRewards);
                break;
            }
        }
    }

    public void setHotelRating(String hotelName, int rating) {
        for (Hotel hotel : hotels) {
            if (hotel.name.equals(hotelName)) {
                hotel.setRating(rating);
                break;
            }
        }
    }

    public String findBestRatedHotel(String customerType, List<Date> dates) throws InvalidInputException {

        if (!customerType.equals("Regular") && !customerType.equals("Rewards")) {
            throw new InvalidInputException("Invalid customer type. Must be 'Regular' or 'Rewards'.");
        }

        if (dates == null || dates.size() < 2) {
            throw new InvalidInputException("Invalid date range. Must have at least 2 dates.");
        }

        Hotel bestHotel = null;
        int highestRating = Integer.MIN_VALUE;
        int lowestCost = Integer.MAX_VALUE;

        for (Hotel hotel : hotels) {
            int totalCost = 0;
            for (Date date : dates) {
                totalCost += hotel.getRate(customerType, date);
            }

            if (hotel.rating > highestRating || (hotel.rating == highestRating && totalCost < lowestCost)) {
                bestHotel = hotel;
                highestRating = hotel.rating;
                lowestCost = totalCost;
            }
        }

        return bestHotel != null ? String.format("%s, Rating: %d and Total Rates: $%d", bestHotel.name, bestHotel.rating, lowestCost) : "No hotels found";
    }

    private int getHotelRating(String hotelName) {
        for (Hotel hotel : hotels) {
            if (hotel.name.equals(hotelName)) {
                return hotel.rating;
            }
        }
        return 0;
    }
}
public class HotelReservationSystem {
    public static void main(String[] args) throws ParseException{
        System.out.println("Welcome to Hotel Reservation Program");
        HotelReservation reservationSystem = new HotelReservation();


        reservationSystem.setHotelRates("Lakewood", 110, 80, 90, 80);
        reservationSystem.setHotelRates("Bridgewood", 150, 100, 50, 60);
        reservationSystem.setHotelRates("Ridgewood", 220, 100, 150, 40);

        reservationSystem.setHotelRating("Lakewood", 3);
        reservationSystem.setHotelRating("Bridgewood", 4);
        reservationSystem.setHotelRating("Ridgewood", 5);

        SimpleDateFormat sdf = new SimpleDateFormat("ddMMMyyyy", Locale.ENGLISH);
        List<Date> dates = new ArrayList<>();
        try {
            dates.add(sdf.parse("11Sep2020"));
            dates.add(sdf.parse("12Sep2020"));
        }catch (ParseException e){
            e.printStackTrace();
        }

        try {
        String bestRatedHotel = reservationSystem.findBestRatedHotel("Rewards", dates);
        System.out.println(bestRatedHotel);
    } catch (InvalidInputException e) {
        System.out.println(e.getMessage());
    }
    }
}
