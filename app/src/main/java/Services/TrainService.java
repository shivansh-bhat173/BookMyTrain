package Services;

import Entities.Train;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class TrainService{
    Train train;

    private ObjectMapper objectMapper = new ObjectMapper();
    private static final String TRAINS_PATH = "localDb/trains.json";

    public TrainService(Train train) throws IOException{
        this.train = train;
        loadFromDB();
    }
    public TrainService() throws IOException{
        loadFromDB();
    }
    public List<List<Integer>> getSeats(Train train){
        return train.getSeats();
    }
    public List<Train> loadFromDB() throws IOException{
        File trainsFile = new File(TRAINS_PATH);
        List<Train> trainsList = objectMapper.readValue(trainsFile, new TypeReference<List<Train>>(){});
        return trainsList;
    }
    public boolean bookSeat(Train train,int row,int column){
        row--;
        column--;
        List<List<Integer>> seats = train.getSeats();
         boolean canBook = seats.get(row).get(column)==0?true:false;
        if(canBook){
            seats.get(row).set(column,1);
            return true;
        } else return false;
    }
    public List<Train> getTrains(String source, String destination) throws IOException {
        List<Train> trainsList = loadFromDB();
        return trainsList.stream().filter(train-> validTrains(train,source,destination)).collect(Collectors.toList());

    }

    public boolean validTrains(Train train, String source, String destination){
        List<String> stationOrder = train.getStations();

        int sourceIdx = stationOrder.indexOf(source.toLowerCase());
        int destinationIdx = stationOrder.indexOf(destination.toLowerCase());

        return sourceIdx!= -1 && destinationIdx!= -1 && sourceIdx < destinationIdx;
    }
}
