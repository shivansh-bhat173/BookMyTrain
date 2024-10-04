package Services;

import Entities.Train;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TrainService{
    private ObjectMapper objectMapper = new ObjectMapper();
    private static final String TRAINS_PATH = "app/src/main/java/localDb/trains.json";

    public TrainService() throws IOException{
        loadFromDB();
    }

    public ArrayList<Train> loadFromDB() throws IOException{
        File trainsFile = new File(TRAINS_PATH);
        ArrayList<Train> trainsList = objectMapper.readValue(trainsFile, new TypeReference<List<Train>>(){});
        return trainsList;
    }

    public List<Train> getTrains(String source, String destination) throws IOException {
        ArrayList<Train> trainsList = loadFromDB();
        return trainsList.stream().filter(train-> validTrains(train,source,destination)).collect(Collectors.toList());

    }

    public boolean validTrains(Train train, String source, String destination){
        List<String> stationOrder = train.getStations();

        int sourceIdx = stationOrder.indexOf(source.toLowerCase());
        int destinationIdx = stationOrder.indexOf(source.toLowerCase());

        return sourceIdx!= -1 && destinationIdx!= -1 && sourceIdx < destinationIdx;
    }
}
