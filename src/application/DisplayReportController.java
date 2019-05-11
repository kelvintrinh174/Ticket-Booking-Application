package application;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

public class DisplayReportController {
	
	@FXML
    private TextArea txtAInfo2;
	
	public void populateData(Ticket myTicket) 
	{
		txtAInfo2.setText(
				   String.format
				   (						    
						     "Ticket number is %s  "
						   + "%nThe destination is %s"
						   + "%nThe price is %s"
						   + "%nThe departure time is %s"
						   + "%nThe Seat Number is %s"
						   ,
						     myTicket.getTicketNumber(),
						     myTicket.getDestination(),
						     myTicket.getDepartureTime(),
						     myTicket.getArrivalTime(),
						     myTicket.getSeatNumber()
				   )
		   );	 
		
		
	}
	
	
	
}
