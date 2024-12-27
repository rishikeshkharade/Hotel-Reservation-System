import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Hotel{
    String name;
    int rating;
    int weekdayRateRegular;
    int weekdayRateRewards;
    int weekendRateRegular;
    int weekendRateRewards;

    public Hotel(String name, int rating, int weekdayRateRegular, int weekdayRateRewards, int weekendRateRegular, int weekendRateRewards){
        this.name = name;
        this.rating = rating;
        this.weekdayRateRegular = weekdayRateRegular;
        this.weekdayRateRewards = weekdayRateRewards;
        this.weekendRateRegular = weekendRateRegular;
        this.weekendRateRewards = weekendRateRewards;
    }
}
class HotelReservation{
    List<Hotel> hotels;

    public HotelReservation(){
        hotels = new ArrayList<>();
    }

    public void addHotel(String name, int rating, int weekdayRateRegular, int weekdayRateReward, int weekendRateRegular, int weekendRateReward){
        Hotel newHotel = new Hotel(name, rating, weekdayRateRegular, weekdayRateReward, weekendRateRegular, weekendRateReward);
        hotels.add(newHotel);
    }
}
public class HotelReservationSystem {
    public static void main(String[] args) {
        System.out.println("Welcome to Hotel Reservation Program");
        Scanner sc = new Scanner(System.in);
        HotelReservation reservationSystem = new HotelReservation();

        System.out.println("Do you want to add a new hotel? (yes/no)");
        String addHotelResponse = sc.nextLine();
        if (addHotelResponse.equalsIgnoreCase("yes")){
            System.out.println("Enter hotel name: ");
            String name = sc.nextLine();
            System.out.println("Enter hotel rating: ");
            int rating = Integer.parseInt(sc.nextLine());
            System.out.println("Enter weekday rate for regular customer: ");
            int weekdayRateRegular = Integer.parseInt(sc.nextLine());
            System.out.println("Enter weekday rate for reward customer: ");
            int weekdayRateReward = Integer.parseInt(sc.nextLine());
            System.out.println("Enter weekend rate for regular customer: ");
            int weekendRateRegular = Integer.parseInt(sc.nextLine());
            System.out.println("Enter weekend rate for reward customer: ");
            int weekendRateReward = Integer.parseInt(sc.nextLine());

            reservationSystem.addHotel(name, rating, weekdayRateRegular, weekdayRateReward, weekendRateRegular, weekendRateReward);
            System.out.println("Hotel added successfully");
        }
    }
}
