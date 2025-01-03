import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

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

    public void setWeekdayRates(int weekdayRateRegular, int weekdayRateRewards){
        this.weekdayRateRegular = weekdayRateRegular;
        this.weekdayRateRewards = weekdayRateRewards;
    }

    public void setWeekendRates(int weekendRateRegular, int weekendRateRewards){
        this.weekendRateRegular = weekendRateRegular;
        this.weekendRateRewards = weekendRateRewards;
    }

    public void setRating(int rating){
        this.rating = rating;
    }

   public int getRate(String customerType, Date date){
        int dayOfWeek = date.getDay();
        if (dayOfWeek >= 1 && dayOfWeek <=5){
            return customerType.equals("Regular") ? weekendRateRegular : weekdayRateRewards;
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
    public void addHotel(String name, int rating, int weekdayRateRegular, int weekdayRateRewards, int weekendRateRegular, int weekendRateRewards){
        hotels.add(new Hotel(name, rating, weekdayRateRegular, weekdayRateRewards, weekendRateRegular, weekendRateRewards));
    }

    public void setHotelRates(String hotelName, int weekdayRateRegular, int weekdayRateRewards, int weekendRateRegular, int weekendRateRewards){
        for (Hotel hotel : hotels) {
            if (hotel.name.equals(hotelName)){
                hotel.setWeekdayRates(weekdayRateRegular, weekdayRateRewards);
                hotel.setWeekendRates(weekendRateRegular, weekendRateRewards);
                break;
            }
        }
    }

    public void setHotelRating(String hotelName, int rating){
        for (Hotel hotel : hotels) {
            if (hotel.name.equals(hotelName)){
                hotel.setRating(rating);
                break;
            }
        }
    }

    public String findCheapestHotel(String customerType, List<Date> dates) {
        List<String> cheapestHotels = new ArrayList<>();
        int cheapestCost = Integer.MAX_VALUE;

        for (Hotel hotel : hotels) {
            int totalCost = 0;
            for (Date date : dates) {
                totalCost += hotel.getRate(customerType, date);
            }

            if (totalCost < cheapestCost) {
                cheapestCost = totalCost;
                cheapestHotels.clear();
                cheapestHotels.add(hotel.name);
            }else if (totalCost == cheapestCost){
                cheapestHotels.add(hotel.name);
            }
        }

        StringBuilder result = new StringBuilder();
        for (String hotelName : cheapestHotels) {
            result.append(hotelName).append(" ");
        }
        result.append("with Total Rates: $").append(cheapestCost);
        return result.toString();
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
    public static void main(String[] args) {
        System.out.println("Welcome to Hotel Reservation Program");
        Scanner sc = new Scanner(System.in);
        HotelReservation reservationSystem = new HotelReservation();

        reservationSystem.setHotelRates("Lakewood", 110, 80, 90, 80);
        reservationSystem.setHotelRates("Bridgewood", 150, 100, 50, 60);
        reservationSystem.setHotelRates("Ridgewood", 220, 100, 150, 40);

        reservationSystem.setHotelRating("Lakewood", 3);
        reservationSystem.setHotelRating("Bridgewood", 4);
        reservationSystem.setHotelRating("Ridgewood", 5);

        SimpleDateFormat sdf = new SimpleDateFormat("ddMMMyyyy");
        List<Date> dates = new ArrayList<>();
        try {
                dates.add(sdf.parse("03Jan2025"));
                dates.add(sdf.parse("04Jan2025"));
        }catch (ParseException e){
            e.printStackTrace();
        }

        String cheapestHotels = reservationSystem.findCheapestHotel("Regular", dates);
        System.out.println(cheapestHotels);
    }
}
