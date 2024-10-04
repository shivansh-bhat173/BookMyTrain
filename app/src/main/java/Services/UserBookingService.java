package Services;

import Entities.Train;
import Entities.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import util.UserServiceUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserBookingService {

    private User user;

    private List<User> userList;

    // to map object to the json Object values
    private ObjectMapper objectMapper = new ObjectMapper();

    private static final String USERS_PATH = "localDb/users.json";

    private static final String TRAINS_PATH = "app/src/main/java/localDb/trains.json";

    public UserBookingService() throws IOException {
       loadUsers();
    }
    public UserBookingService(User user) throws IOException {
        this.user = user;
        loadUsers();
    }
    // loads users from the JSOn format file
    public List<User> loadUsers() throws IOException,FileNotFoundException{
            File usersFile = new File(USERS_PATH);
        // this deserializes users into list of users
        userList = objectMapper.readValue(usersFile, new TypeReference<List<User>>(){});
        return userList;
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
            return Boolean.TRUE;
        }catch (IOException ex){
            return Boolean.FALSE;
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
        System.out.println(user.getTicketsBooked());
        user.printTickets();
    }

    // cancel bookingss

    public Boolean cancelBooking(String ticketId){
       boolean removed =  user.getTicketsBooked().removeIf(ticket ->ticketId.equalsIgnoreCase(ticket.getTicketId()));
        if(removed){
            return Boolean.TRUE;
        }else return Boolean.FALSE;
    }

    public List<Train> searchTrains(String source, String destination) {
        try{
            TrainService trainService = new TrainService();
            return trainService.getTrains(source, destination);
        }catch(IOException ex){
            return new ArrayList<>();
        }
    }

    public Boolean ValidateUser(User user1){
        if(user1==null){
            System.out.println("No user logged in!");
            return false;
        }
        Optional<User> validUser = userList.stream().filter(user -> user.getName().equalsIgnoreCase(user1.getName())
                && user.getPassword().equalsIgnoreCase(user1.getPassword())).findAny();

        if(validUser.isPresent()){
            return true;
        }else return false;
    }



}
