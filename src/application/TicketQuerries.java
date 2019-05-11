package application;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class TicketQuerries {
	   private static final String URL = "jdbc:derby:ticketbooking";
	   private static final String USERNAME = "kelvin";
	   private static final String PASSWORD = "kelvin";
	   
	   private Connection connection; // manages connection
	   private PreparedStatement insertNewTicket;    
	   private PreparedStatement selectAllTicket;  
	   private PreparedStatement deleteTicket;
	   private PreparedStatement updateTicket;
	   private PreparedStatement selectTicketByDestination;
	   private PreparedStatement countTicketRecords;
	   private PreparedStatement displayRecord;
	   
	   
	   // constructor
	   public TicketQuerries() {
	      try {
	         connection = 
	            DriverManager.getConnection(URL, USERNAME, PASSWORD);    
	         
	         // create query that selects all entries in the ticket booking
	         selectAllTicket = connection.prepareStatement(
	                 "SELECT * FROM Tickets ORDER BY DESTINATION");
	         
	         // create insert that adds a new entry into the database
	         insertNewTicket = connection.prepareStatement(         
	            "INSERT INTO Tickets " +                           
	            "(DESTINATION, DEPARTURETIME, ARRIVALTIME,TICKETNUMBER,SEATNUMBER) " +     
	            "VALUES (?, ?, ?,?,?)");       
	         
	         //create query that delete a record from the database
	        deleteTicket =connection.prepareStatement("DELETE from Tickets WHERE TICKETID = ?");
	        
	        //create query that update a record in the database
	        updateTicket = connection.prepareStatement("UPDATE Tickets SET DESTINATION = ?, DEPARTURETIME = ?,ARRIVALTIME =?, TICKETNUMBER =?, SEATNUMBER=? WHERE TICKETID = ?");
	        
	        // create query that selects ticket with its destination 	         
	        selectTicketByDestination = connection.prepareStatement(        
	            "SELECT * FROM Tickets WHERE DESTINATION LIKE ? " + 
	            "ORDER BY DESTINATION");     
	        
	        //create query that return the number of records in the current database
	        countTicketRecords = connection.prepareStatement("select count(*) as count from tickets"); 
	        

	        //create query that return a record from database        
	        displayRecord = connection.prepareStatement("select * from tickets where ticketID = ?");
	        
	      } 
	      catch (SQLException sqlException) {
	         sqlException.printStackTrace();
	         System.exit(1);
	      } 
	   } 
	   
	   //Initialize records automatically
	   public void populateTicket() {		  
		   addTicket("Seoul","12:00pm 25/03/2019","01:00pm25/03/2019","AIRCAN001","01A");
		   addTicket("Ottawa","02:00pm 20/03/2019","04:00pm25/03/2019","UNA002","12A");
		   addTicket("Vancouver","02:00pm 20/03/2019","05:00pm25/03/2019","UNA003","13A");
		   addTicket("Toronto","03:00pm 20/03/2019","04:00pm25/03/2019","UNA004","12C");
		   addTicket("Edmonton","02:00pm 20/03/2019","04:00pm25/03/2019","UNA005","15D");			   
	   }
	   	   
	   // select all of the addresses in the database
	   public List<Ticket> getAllTicket() {
	      // executeQuery returns ResultSet containing matching entries
	      try (ResultSet resultSet = selectAllTicket.executeQuery()) {
	         List<Ticket> results = new ArrayList<Ticket>();
	         
	         while (resultSet.next()) {
	            results.add(new Ticket(
	               resultSet.getInt("ticketID"),
	               resultSet.getString("destination"),
	               resultSet.getString("departureTime"),
	               resultSet.getString("arrivalTime"),
	               resultSet.getString("ticketNumber"),
	               resultSet.getString("seatNumber")));
	         } 
	              
	         return results;
	      }
	      catch (SQLException sqlException) {
	         sqlException.printStackTrace();         
	      }
	      
	      return null;
	   }
	     
	   // add an entry
	   public int addTicket(String destination, String departureTime, 
	      String arrivalTime, String ticketNumber,String seatNumber) 
	   {	      
	      // insert the new entry; returns # of rows updated
	      try 
	      {
	         // set parameters
	    	  insertNewTicket.setString(1, destination);
	    	  insertNewTicket.setString(2, departureTime);
	    	  insertNewTicket.setString(3, arrivalTime);
	    	  insertNewTicket.setString(4, ticketNumber); 
	    	  insertNewTicket.setString(5, seatNumber); 
	         
	          return insertNewTicket.executeUpdate();
	          
	      }
	      catch (SQLException sqlException) {
	         sqlException.printStackTrace();
	         return 0;
	      }
	   }
	   
	   //delete a record
	    public void deleteTicket(int ticketID) {
		   try {
			   
		          //set parameters
			      deleteTicket.setString(1, Integer.toString(ticketID));
		          deleteTicket.executeUpdate();     
		          
		      }
		      catch (SQLException sqlException) {
		         sqlException.printStackTrace();
		         
		      }
	   }
	    
	   //update a record
	   public void updateTicket(String destination, String departureTime, 
			      String arrivalTime, String ticketNumber,String seatNumber,int ticketID) {
		   try {
			       //set parameters
				   updateTicket.setString(1, destination);
				   updateTicket.setString(2, departureTime);
				   updateTicket.setString(3, arrivalTime);
				   updateTicket.setString(4, ticketNumber); 
				   updateTicket.setString(5, seatNumber); 
				   updateTicket.setString(6, Integer.toString(ticketID));
				   updateTicket.executeUpdate();         
		      }
		      catch (SQLException sqlException) {
		         sqlException.printStackTrace();
		         
		      }
	   }
	   
	   // selects ticket with its destination
	   public List<Ticket> getTicketsByDestination(String destination) 
	   {
	      try 
	      {
	    	  selectTicketByDestination.setString(1, destination); // set destination
	      }
	      catch (SQLException sqlException) {
	         sqlException.printStackTrace();
	         return null;
	      }

	      // executeQuery returns ResultSet containing matching entries
	      try (ResultSet resultSet = selectTicketByDestination.executeQuery()) {
	         List<Ticket> results = new ArrayList<Ticket>();

	         while (resultSet.next()) {
	            results.add(new Ticket(
	               resultSet.getInt("ticketID"),
	               resultSet.getString("destination"),
	               resultSet.getString("departureTime"),
	               resultSet.getString("arrivalTime"),
	               resultSet.getString("ticketNumber"),
	               resultSet.getString("seatNumber")));
	         } 

	         return results;
	      }
	      catch (SQLException sqlException) {
	         sqlException.printStackTrace();
	         return null;
	      } 
	      
	   }
	   
	   //count current records
	   public int countTicketRecords() {
		   
		   try (ResultSet resultSet = countTicketRecords.executeQuery()){
			   while (resultSet.next())
			   {
				   return resultSet.getInt("count");
			   }
		   
		   }
		   catch (SQLException sqlException) {
		         sqlException.printStackTrace();         
		   }
		   return 0;
	   }
	   
	   //display a ticket	   
	   public Ticket displayARecord(int ticketID)
	   {
		   try 
		      {
			     displayRecord.setString(1, Integer.toString(ticketID)); // set destination
		      }
		      catch (SQLException sqlException) {
		         sqlException.printStackTrace();
		         return null;
		      }
		   
		      // executeQuery returns ResultSet containing matching entries
		      try (ResultSet resultSet = displayRecord.executeQuery()) 
		      {		 
		    	  Ticket results = new Ticket();
		    	  
		    	  while (resultSet.next()) {
		    		    results = new Ticket(resultSet.getInt("ticketID"),
				               resultSet.getString("destination"),
				               resultSet.getString("departureTime"),
				               resultSet.getString("arrivalTime"),
				               resultSet.getString("ticketNumber"),
				               resultSet.getString("seatNumber"));               
		    	  }
		               	    	      
		    	  return results;	               
		       } 		         		      
		      catch (SQLException sqlException) {
		         sqlException.printStackTrace();
		         return null;
		      } 

	   }
	   
	   // close the database connection
	   public void close() {
		      try {
		         connection.close();
		      } 
		      catch (SQLException sqlException) {
		         sqlException.printStackTrace();
		      } 
		   }
	   

}
