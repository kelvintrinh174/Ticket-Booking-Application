package application;

public class Ticket {
	
	   private int ticketID;
	   private String destination;
	   private String departureTime;
	   private String arrivalTime;
	   private String ticketNumber;
	   private String seatNumber;
	   
	  public Ticket(int ticketID, String destination, String departureTime, 
			   String arrivalTime, String ticketNumber,String seatNumber) {
			      this.ticketID = ticketID;
			      this.destination = destination;
			      this.departureTime = departureTime;
			      this.arrivalTime = arrivalTime;
			      this.ticketNumber = ticketNumber;
			      this.seatNumber = seatNumber;
			   }
	  
	  public Ticket() {}

	public int getTicketID() {
		return ticketID;
	}

	public void setTicketID(int ticketID) {
		this.ticketID = ticketID;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}

	public String getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public String getTicketNumber() {
		return ticketNumber;
	}

	public void setTicketNumber(String ticketNumber) {
		this.ticketNumber = ticketNumber;
	}

	public String getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(String seatNumber) {
		this.seatNumber = seatNumber;
	}

	@Override
	public String toString() {
		return "Destination: " + destination + ", TicketNumber=" + ticketNumber;
	}
	   
	
	  
}
