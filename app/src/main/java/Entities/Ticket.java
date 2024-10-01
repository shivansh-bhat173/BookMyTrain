package Entities;

import java.util.Date;

public class Ticket {

    private Train train;
    private String source;
    private String destination;
    private Date dateOfTravel;
    private String ticketId;
    private String userId;

    public Ticket(Train train, String source, String destination, Date dateOfTravel, String ticketId, String userId) {
        this.train = train;
        this.source = source;
        this.destination = destination;
        this.dateOfTravel = dateOfTravel;
        this.ticketId = ticketId;
        this.userId = userId;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Date getDateOfTravel() {
        return dateOfTravel;
    }

    public void setDateOfTravel(Date dateOfTravel) {
        this.dateOfTravel = dateOfTravel;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        userId = userId;
    }

    public String getTicketInfo(){
        return String.format("Ticket ID: %s belongs to USer %s from %s to %s on %s",ticketId,userId,source,destination,dateOfTravel);
    }
}
