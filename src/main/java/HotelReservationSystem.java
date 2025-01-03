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

    public void setWeekdayRates(int regularRate, int rewardsRate){
        this.weekdayRateRegular = regularRate;
        this.weekdayRateRewards = rewardsRate;
    }

    public void setWeekendRates(int regularRate, int rewardsRate) {
        this.weekendRateRegular = regularRate;
        this.weekendRateRewards = rewardsRate;
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
    public String findCheapestHotel(String customerType, List<Date> dates) {
        String cheapestHotel = null;
        int cheapestCost = Integer.MAX_VALUE;

        for (Hotel hotel : hotels) {
            int totalCost = 0;
            for (Date date : dates) {
                totalCost += hotel.getRate(customerType, date);
            }

            if (totalCost < cheapestCost || (totalCost == cheapestCost && (cheapestHotel == null || hotel.rating > getHotelRating(cheapestHotel)))){
                cheapestHotel = hotel.name;
                cheapestCost = totalCost;
            }
        }   

        return cheapestHotel + ", Total Rates: $" + cheapestCost;
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

        System.out.println("Enter the date range (e.g., 10Sep2020, 11Sep2020): ");
        String inputDates = sc.nextLine();
        String[] dateStrings = inputDates.split(", ");
        List<Date> dates = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMMyyyy");

        try{
            for (String dateString : dateStrings) {
                dates.add(sdf.parse(dateString.trim()));
            }
        }catch (ParseException e){
            System.out.println("Invalid date Format. Please use ddMMMyyyy format.");
            return;
        }

        System.out.println("Enter customer type (Regular/Rewards): ");
        String customerType = sc.nextLine().trim();

        String result = reservationSystem.findCheapestHotel(customerType, dates);
        System.out.println(result);
    }
}
