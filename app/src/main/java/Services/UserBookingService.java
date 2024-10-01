package Services;

import Entities.Ticket;
import Entities.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import util.UserServiceUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class UserBookingService {

    private User user;

    private List<User> userList;

    // to map object to the json Object values
    private ObjectMapper objectMapper = new ObjectMapper();

    private static final String USERS_PATH = "app/src/main/java/localDb/user.json";

    public UserBookingService(User user) throws IOException {
        this.user = user;
        File usersFile = new File(USERS_PATH);
        // this deserializes users into list of users
        userList = objectMapper.readValue(usersFile, new TypeReference<List<User>>(){});

    }

    public Boolean loginUser(){
        // stream has filter (Predicate<T> functional interface) and map (function<T,R> functional interface)
        Optional<User> foundUser = userList.stream().filter((user1->{
             return user1.getName().equalsIgnoreCase(user.getName()) && UserServiceUtil.checkPassword(user.getPassword(),user1.getHashPassword());
        })).findFirst();

        return foundUser.isPresent();
    }

    public Boolean signUp(User user1){
        try {
            userList.add(user1);
            SaveUserListToFile();
            return true;
        }catch (IOException ex){
            return false;
        }
    }

    //JSON --> Object(USer) ---> Deserialization
    //Object --> JSON --> Serializaton

    private void SaveUserListToFile() throws IOException{
        File usersFile = new File(USERS_PATH);
        //Serialization
        objectMapper.writeValue(usersFile,userList);
    }

    //fetch booking

    public void fetchBookings(){
        user.printTickets();
    }

    // cancel bookings

    public Boolean cancelBooking(String ticketId){
         Optional<Ticket> cancelledTicket= user.getTicketsBooked().stream().filter(ticket1 -> {
            return ticket1.getTicketId().equals(ticketId);
        }).findAny();

//        if(!cancelledTicket.isPresent())return Boolean.FALSE;
//        user.getTicketsBooked().remove(cancelledTicket);
//        userList.stream().filter(user1 -> {
//            return user1.getUserId().equalsIgnoreCase(user.getUserId());
//        }).
        try {
            File usersFile = new File(USERS_PATH);
            objectMapper.writeValue(usersFile,userList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Boolean.TRUE;
    }
}
