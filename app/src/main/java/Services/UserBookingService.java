package Services;

import Entities.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class UserBookingService {

    private User user;

    private List<User> userList;

    // to map object to the json Object values
    private ObjectMapper objectMapper = new ObjectMapper();

    private static final String USERS_PATH = "../localDb/user.json";

    public UserBookingService(User user) throws IOException {
        this.user = user;
        File users = new File(USERS_PATH);
        // this deserializes users into list of users
        userList = objectMapper.readValue(users, new TypeReference<List<User>>(){});
    }


}
