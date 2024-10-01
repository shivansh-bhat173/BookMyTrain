package Entities;

import java.util.List;
import java.util.Map;

public class Train {
    private String trainId;
    private long trainNo;
    private List<List<Integer>> seats;
    private Map<String, String> stationTimes;
    private List<String> stations;

    public String getTrainId() {
        return trainId;
    }

    public Train(String trainId, long trainNo, List<List<Integer>> seats, Map<String, String> stationTimes, List<String> stations) {
        this.trainId = trainId;
        this.trainNo = trainNo;
        this.seats = seats;
        this.stationTimes = stationTimes;
        this.stations = stations;
    }

    public void setTrainId(String trainId) {
        this.trainId = trainId;
    }

    public long getTrainNo() {
        return trainNo;
    }

    public void setTrainNo(long trainNo) {
        this.trainNo = trainNo;
    }

    public List<List<Integer>> getSeats() {
        return seats;
    }

    public void setSeats(List<List<Integer>> seats) {
        this.seats = seats;
    }

    public Map<String, String > getStationTimes() {
        return stationTimes;
    }

    public void setStationTimes(Map<String, String> stationTimes) {
        this.stationTimes = stationTimes;
    }

    public String getTrainInfo(){
        return String.format("Train ID : %s Train No : %s",trainId,trainNo);
    }

}
