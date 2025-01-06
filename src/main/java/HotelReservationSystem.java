import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

class Hotel {
    String name;
    int rating;
    int weekdayRateRegular;
    int weekdayRateRewards;
    int weekendRateRegular;
    int weekendRateRewards;
    int specialWeekdayRateRewards;
    int specialWeekendRateRewards;

    public Hotel(String name, int rating, int weekdayRateRegular, int weekdayRateRewards, int weekendRateRegular, int weekendRateRewards, int specialWeekdayRateRewards, int specialWeekendRateRewards) {
        this.name = name;
        this.rating = rating;
        this.weekdayRateRegular = weekdayRateRegular;
        this.weekdayRateRewards = weekdayRateRewards;
        this.weekendRateRegular = weekendRateRegular;
        this.weekendRateRewards = weekendRateRewards;
        this.specialWeekdayRateRewards = specialWeekdayRateRewards;
        this.specialWeekendRateRewards = specialWeekendRateRewards;
    }

   public int getRate(String customerType, Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        if (dayOfWeek >= Calendar.MONDAY && dayOfWeek <= Calendar.FRIDAY) {
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
        hotels.add(new Hotel("Lakewood", 3, 110, 80, 90, 80, 80, 80));
        hotels.add(new Hotel("Bridgewood", 4, 160, 110, 60, 50, 110, 50));
        hotels.add(new Hotel("Ridgewood", 5, 220, 100, 150, 40, 100, 40));
    }

    public void addHotel(String name, int rating, int weekdayRateRegular, int weekdayRateRewards, int weekendRateRegular, int weekendRateRewards) {

        int specialWeekdayRateRewards = weekdayRateRewards;
        int specialWeekendRateRewards = weekendRateRewards;

        Hotel newHotel = new Hotel(name, rating, weekdayRateRegular, weekdayRateRewards, weekendRateRegular, weekendRateRewards, specialWeekdayRateRewards, specialWeekendRateRewards);
        hotels.add(newHotel);
    }


    public String findBestRatedHotel(String customerType, List<Date> dates) {
        Hotel bestHotel = null;
        int highestRating = Integer.MIN_VALUE;
        int lowestCost = Integer.MAX_VALUE;

        for (Hotel hotel : hotels) {
            int totalCost = 0;
            System.out.println("Calculating rates for " + hotel.name);
            for (Date date : dates) {
                int rate = hotel.getRate(customerType, date);
                System.out.printf("Date: %s, Rate: $%d%n", new SimpleDateFormat("ddMMMyyyy").format(date), rate);
                totalCost += rate;
            }
            System.out.printf("Total cost for %s: $%d%n", hotel.name, totalCost);

            if (hotel.rating > highestRating || (hotel.rating == highestRating && totalCost < lowestCost)) {
                bestHotel = hotel;
                highestRating = hotel.rating;
                lowestCost = totalCost;
            }
        }
        return bestHotel != null ? String.format("%s & Total Rates $%d", bestHotel.name, lowestCost) : "No hotels available";
    }
}

public class HotelReservationSystem {
    public static void main(String[] args) throws ParseException{
        System.out.println("Welcome to Hotel Reservation Program");
        HotelReservation reservationSystem = new HotelReservation();


        SimpleDateFormat sdf = new SimpleDateFormat("ddMMMyyyy", Locale.ENGLISH);
        List<Date> dates = new ArrayList<>();
        try {
            dates.add(sdf.parse("11Sep2020"));
            dates.add(sdf.parse("12Sep2020"));
        }catch (ParseException e){
            e.printStackTrace();
        }

        String bestRatedHotel = reservationSystem.findBestRatedHotel("Regular", dates);
        System.out.println(bestRatedHotel);

        String bestRatedHotelForRewards = reservationSystem.findBestRatedHotel("Rewards", dates);
        System.out.println(bestRatedHotelForRewards);
    }
}
