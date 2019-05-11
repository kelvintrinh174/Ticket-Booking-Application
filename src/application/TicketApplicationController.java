package application;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TicketApplicationController {
		
	   @FXML private ListView<Ticket> listView; // displays destination and ticket number
	   
	   @FXML private TextField destinationTextField;
	   @FXML private TextField departureTimeTextField;
	   @FXML private TextField arrivalTimeTextField;
	   @FXML private TextField ticketNumberTextField;
	   @FXML private TextField seatNumberTextField;
	   @FXML private TextField findByDestinationTextField;
	   
	   // interacts with the database
	   private final TicketQuerries ticketQueries = new TicketQuerries();
	   
	   //this object is used for delete and update a record, display report
	   private Ticket currentTicket = new Ticket();
	   
	   // stores list of ticket objects that results from a database query
	   private final ObservableList<Ticket> InfoList = FXCollections.observableArrayList();
	   
	   // get all the entries from the database to populate InfoList
	   private void getAllEntries() {
		   InfoList.setAll(ticketQueries.getAllTicket()); 
	       selectFirstEntry();
	   }

	   // select first item in listView
	   private void selectFirstEntry() {
	      listView.getSelectionModel().selectFirst();          
	   }
	   
	   // populate listView and set up listener for selection events
	   public void initialize() 
	   {
		   
	      listView.setItems(InfoList); // bind to InfoList
	       	      
	      if(ticketQueries.countTicketRecords() < 5)
	    	  ticketQueries.populateTicket();
	      
	      // populates InfoList, which updates listView 
	      getAllEntries();
	      
	      // when ListView selection changes, display selected person's data
	      listView.getSelectionModel().selectedItemProperty().addListener(
	         (observableValue, oldValue, newValue) -> {
	            displayTicket(newValue);            
	            currentTicket = newValue;            
	         }
	      );     
	   }

	   // display contact information
	   private void displayTicket(Ticket ticket) {
	      if (ticket != null) 
	      {
	    	  destinationTextField.setText(ticket.getDestination());
	    	  departureTimeTextField.setText(ticket.getDepartureTime());
	    	  arrivalTimeTextField.setText(ticket.getArrivalTime());
	    	  ticketNumberTextField.setText(ticket.getTicketNumber());
	    	  seatNumberTextField.setText(ticket.getSeatNumber());
	      }
	      else 
	      {
	    	  destinationTextField.clear();
	    	  departureTimeTextField.clear();
	    	  arrivalTimeTextField.clear();
	    	  ticketNumberTextField.clear();
	    	  seatNumberTextField.clear();
	      }
	   }
	   	   
	   // add a new entry
	   @FXML
	   private void insertButtonPressed(ActionEvent event) {
	      int result = ticketQueries.addTicket(
	    		  destinationTextField.getText(), departureTimeTextField.getText(), 
	    		  arrivalTimeTextField.getText(), ticketNumberTextField.getText(),seatNumberTextField.getText());                                     
	      
	      if (result == 1) {
	         displayAlert(AlertType.INFORMATION, "New ticket Added", 
	            "New ticket successfully added.");
	      }
	      else {
	         displayAlert(AlertType.ERROR, "Ticket Not Added", 
	            "Unable to add ticket");
	      }
	      
	      getAllEntries();
	   }
	   
	   //delete a ticket
	   @FXML
	   private void deleteButtonPressed(ActionEvent event) {		   
		   ticketQueries.deleteTicket(currentTicket.getTicketID());			         		   
		   getAllEntries();			         
	   }
	   
	   //update a ticket
	   @FXML
	   private void updateButtonPressed(ActionEvent event) {
		   
		   ticketQueries.updateTicket(destinationTextField.getText(), departureTimeTextField.getText(), 
		    		  arrivalTimeTextField.getText(), ticketNumberTextField.getText(),seatNumberTextField.getText(),currentTicket.getTicketID());
			         		   
		   getAllEntries();
			         
	   }
	   
	   // find entries with the specified last name
	   @FXML
	   void findButtonPressed(ActionEvent event) {
	      List<Ticket> ticketDestination = ticketQueries.getTicketsByDestination(
	    		  findByDestinationTextField.getText() + "%");

	      if (ticketDestination.size() > 0) { // display all entries
	    	  InfoList.setAll(ticketDestination);
	          selectFirstEntry();
	      }
	      else {
	         displayAlert(AlertType.INFORMATION, "Ticket Not Found", 
	            "There are no entries with the specified destination");
	      }
	   }
	   
	   // browse all the entries
	   @FXML
	   void browseAllButtonPressed(ActionEvent event) {
	      getAllEntries();
	   }
	   
	   //clear button
	   @FXML
	   private void clearButtonPressed(ActionEvent event) {
		   destinationTextField.setText("");
	    	  departureTimeTextField.setText("");
	    	  arrivalTimeTextField.setText("");
	    	  ticketNumberTextField.setText("");
	    	  seatNumberTextField.setText("");
	    	  findByDestinationTextField.setText("");
	   }
	   
	   //display reports
	   @FXML
	   private void displayInfoPressed(ActionEvent event) {
		   
			try { 
				
				Stage stage = new Stage();			
				String fxmlFileName = "DisplayReports.fxml";
				FXMLLoader loader = new  FXMLLoader(getClass().getResource(fxmlFileName));
				Parent root = loader.load();
				Scene scene = new Scene(root);
				stage.setScene(scene);
				//DisplayReportController controller = loader.<DisplayReportController>getController();
			    
				//controller.populateData(ticketQueries.displayARecord(currentTicket.getTicketID()));			
				//controller.populateData(currentTicket);
				stage.show();
				
			} 
			catch (IOException e) 
			{		
				e.printStackTrace();
			}
	   }
	      
	   // display an Alert dialog
	   private void displayAlert(
	      AlertType type, String title, String message) {
	      Alert alert = new Alert(type);
	      alert.setTitle(title);
	      alert.setContentText(message);
	      alert.showAndWait();
	   }
	 
	   
}
