import java.text.ParseException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;

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

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setWeekdayRates(int weekdayRateRegular, int weekdayRateRewards){
        this.weekdayRateRegular = weekdayRateRewards;
        this.weekdayRateRewards = weekdayRateRewards;
    }

    public void setWeekendRates(int weekendRateRegular, int weekendRateRewards){
        this.weekendRateRegular = weekendRateRegular;
        this.weekendRateRewards = weekendRateRewards;
    }

   public int getRate(String customerType, LocalDate date){
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        if (dayOfWeek.getValue() >= 1 && dayOfWeek.getValue() <=5) {
            return customerType.equals("Regular") ? weekdayRateRegular : specialWeekdayRateRewards;
        }else {
            return customerType.equals("Regular") ? weekendRateRegular : specialWeekendRateRewards;
        }
    }
}
class HotelReservation {
    List<Hotel> hotels;

    public HotelReservation() {
        hotels = new ArrayList<>(List.of(
                new Hotel("Lakewood", 3, 110, 80, 90, 80, 80, 80),
                new Hotel("Bridgewood", 4, 160, 110, 60, 50, 110, 50),
                new Hotel("Ridgewood", 5, 220, 100, 150, 40, 100, 40)
        ));
    }

    public void addHotel(String name, int rating, int weekdayRateRegular, int weekdayRateRewards, int weekendRateRegular, int weekendRateRewards,int specialWeekdayRateRewards, int specialWeekendRateRewards) {
        hotels.add(new Hotel(name, rating, weekdayRateRegular, weekdayRateRewards, weekendRateRegular, weekendRateRewards, specialWeekdayRateRewards, specialWeekendRateRewards));
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

    public String findCheapestBestRatedHotel(String customerType, List<LocalDate> dates) throws InvalidInputException {
        if (!customerType.equals("Regular") && !customerType.equals("Rewards")) {
            throw new InvalidInputException("Invalid customer type. Must be 'Regular' or 'Rewards'.");
        }
        return hotels.stream()
                .map(hotel -> {
            int totalCost = dates.stream().mapToInt(date -> hotel.getRate(customerType, date)).sum();
            return new AbstractMap.SimpleEntry<>(hotel, totalCost);
        })
                .filter(entry -> entry.getValue() > 0)
                .sorted(Comparator.comparing((AbstractMap.SimpleEntry<Hotel, Integer> entry) -> -entry.getKey().rating)
                        .thenComparingInt(AbstractMap.SimpleEntry::getValue))
                .findFirst().map(entry -> String.format("%s, Rating: %d and Total Rates: $%d", entry.getKey().name, entry.getKey().rating, entry.getValue()))
                .orElse("No hotels found");
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
    private static final String REGEX_DATE = "^[0-9]{2}[A-Z][a-z]{2}[0-9]{4}$";
    public static void main(String[] args) throws ParseException{
        System.out.println("Welcome to Hotel Reservation Program");
        HotelReservation reservationSystem = new HotelReservation();


        reservationSystem.setHotelRates("Lakewood", 110, 80, 80, 80);
        reservationSystem.setHotelRates("Bridgewood", 160, 110, 110, 50);
        reservationSystem.setHotelRates("Ridgewood", 220, 100, 100, 40);

        reservationSystem.setHotelRating("Lakewood", 3);
        reservationSystem.setHotelRating("Bridgewood", 4);
        reservationSystem.setHotelRating("Ridgewood", 5);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the date range in the format ddMMMyyyy");
        String input = scanner.nextLine();
        String[] dateStrings = input.split(",");

        List<LocalDate> dates = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMMyyyy", Locale.ENGLISH);

        try {
            for (String dateString : dateStrings) {
                dateString = dateString.trim();
                if (!Pattern.matches(REGEX_DATE, dateString)) {
                    throw new InvalidInputException("Invalid date format: " + dateString);
                }
                dates.add(LocalDate.parse(dateString, formatter));
            }
            String bestRatedHotel = reservationSystem.findCheapestBestRatedHotel("Regular", dates);
        System.out.println(bestRatedHotel);
    } catch (InvalidInputException e) {
        System.out.println(e.getMessage());
    } catch (Exception e) {
            e.printStackTrace();
        }finally {
            scanner.close();
        }
    }
}
