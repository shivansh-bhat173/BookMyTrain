/*
 * This source file was generated by the Gradle 'init' task
 */
package org.example;

import Entities.Train;
import Entities.User;
import Services.UserBookingService;
import util.UserServiceUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class App {

    public static void main(String[] args) throws IOException {
        System.out.println("Running the train Booking system...");

        Scanner scn = new Scanner(System.in);
        int option = 0;
        UserBookingService userbookingService = null;
        try{
             userbookingService = new UserBookingService();
        } catch (IOException e) {
            System.out.println("Something wrong.....");
        }
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
                     for(Train train : availTrains) System.out.println(train.getTrainInfo());
                     break;

                 case 5:
                     System.out.println("Book a seat...");
                     userbookingService.bookSeat();
                     break;

                 case 6:
                     System.out.println("Cancel my booking");
                     break;

                 default:
                     break;
             }

        }
    }
}
