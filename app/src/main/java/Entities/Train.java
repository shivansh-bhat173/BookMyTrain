package Entities;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class Train {

    private String trainId;
    private long trainNo;
    private String source;
    private String destination;
    private List<List<Integer>> seats;
    private Map<String, Date> stationTimes;
}
