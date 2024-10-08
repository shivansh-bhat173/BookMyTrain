/*
 * This source file was generated by the Gradle 'init' task
 */
package org.example;

import Entities.Train;
import Entities.User;
import Services.TrainService;
import Services.UserBookingService;
import util.UserServiceUtil;

import java.io.IOException;
import java.util.*;

public class App {

    public static void main(String[] args) throws IOException {
        System.out.println("Running the train Booking system...");

        Scanner scn = new Scanner(System.in);
        int option = 0;
        UserBookingService userbookingService = null;
        TrainService trainService = null;
        try{
             userbookingService = new UserBookingService();
             trainService = new TrainService();
        } catch (IOException e) {
            System.out.println("Something wrong.....");
        }
        Train selectedTrain = new Train();
        while(option!=7){
            System.out.println("Choose Any Option...");
            System.out.println("1. SignUp");
            System.out.println("2. Login");
            System.out.println("3. Fetch Bookings");
            System.out.println("4. Search Trains");
            System.out.println("5. Book a seat");
            System.out.println("6. Cancel My Bookings");
            System.out.println("7. Exit the app");
            option = scn.nextInt();

             switch(option){
                 case 1:
                     System.out.println("Enter your name to sign up:");
                     String nameToSignUp = scn.next();
                     System.out.println("Enter the password to signup");
                     String passwordToSignUp = scn.next();
                     User userToSignUp = new User(nameToSignUp,passwordToSignUp,UserServiceUtil.hashPassword(passwordToSignUp),new ArrayList<>(), UUID.randomUUID().toString());
                     // just save to the db and dont initialize it as the user in the service
                     userbookingService.signUp(userToSignUp);
                     System.out.println("Signed Up Successfully!");
                     break;

                     case 2:
                         System.out.println("Enter your name to login");
                         String nameToLogin = scn.next();
                         System.out.println("Enter your password to login");
                         String passwordToLogin = scn.next();
                         User userToLogin = new User(nameToLogin,passwordToLogin,UserServiceUtil.hashPassword(passwordToLogin),new ArrayList<>(), UUID.randomUUID().toString());
                         try{
                         if(!userbookingService.ValidateUser(userToLogin)){
                             System.out.println("User Not found!");
                             break;
                         }else{
                             System.out.println("User Logged IN..");
                         }

                             userbookingService = new UserBookingService(userToLogin);
                         }catch (IOException ex){
                             return;
                         }
                        break;
                 case 3:
                     System.out.println("Fetching Your bookings...");
                     userbookingService.fetchBookings();
                     break;

                 case 4:
                     System.out.println("Enter your Source Station Name");
                     String source =scn.next();
                     System.out.println("Enter your Destination Station Name");
                     String destination = scn.next();
                     System.out.println("Searching Trains...");
                     List<Train> availTrains = userbookingService.searchTrains(source,destination);
                     Map<String,String> TrainsToSelect = new HashMap<String,String>();
                     int index = 1;
                     for (Train t: availTrains){
                         System.out.println(index+" Train id : "+t.getTrainId());
                         for (Map.Entry<String, String> entry: t.getStationTimes().entrySet()) {
                             System.out.println("station " + entry.getKey() + " time: " + entry.getValue());
                         }
                         index++;
                     }
                     System.out.println("Select a train by typing 1,2,3...");
                     selectedTrain = availTrains.get(scn.nextInt()-1);
                     System.out.println("Selected train is " + selectedTrain.getTrainId());
                     break;

                 case 5:
                     System.out.println("Available seats For your Chosen Train "+ selectedTrain.getTrainId());
                     List<List<Integer>> seats = trainService.getSeats(selectedTrain);
                     for(List<Integer> row:seats){
                         for(Integer seat:row){

                             if(seat==0){
                                 System.out.print("[A]" +" ");
                             }else System.out.print("[B]"+" ");
                         }
                         System.out.println();
                 }
                     System.out.println("Select a row between 1-4");
                     int row=scn.nextInt();;
                     System.out.println("Select a seat number between 1-6");
                     int column=scn.nextInt();
                     while(row<1 || row>4 || column<1 || column>6) {
                         System.out.println("Note a valid Seat number");
                         System.out.println("Select a row between 1-4");
                         row = scn.nextInt();
                         System.out.println("Select a seat number between 1-6");
                         column = scn.nextInt();
                     }
                     System.out.println("Booking your Chosen Seat");
                     boolean isBooked = trainService.bookSeat(selectedTrain,row,column);
                     if(isBooked){
//                         userbookingService.addBooking();
                         System.out.println("Your selected seat is booked. Enjoy Your journey!");
                     }else{
                         System.out.println("Your selected seat is alreadyBooked");
                     }
                     break;

                 case 6:
                     System.out.println("Your current train is "+selectedTrain);

                     break;

                 default:
                     break;
             }

        }
    }
}
