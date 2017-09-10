// Insert header here
package assignment6;

import java.util.Map;
import java.lang.Thread;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

public class BookingClient {
	
	public static void main(String args[]){  		
		Map<String, Integer> offices = new HashMap<String, Integer>();
 		offices.put("BX1", 3);
 		offices.put("BX3", 3);
 		offices.put("BX2", 4);
 		offices.put("BX5", 3);
 		offices.put("BX4", 3);
 		Theater theater = new Theater(3, 5, "Ouija");
		BookingClient bookingClient = new BookingClient(offices, theater);
		bookingClient.simulate();
	  }  
 
	private Map<String, Integer> office;
	private Theater theater;
  /*
   * @param office maps box office id to number of customers in line
   * @param theater the theater where the show is playing
   */
	public BookingClient(Map<String, Integer> office, Theater theater) {
		this.office = office;
		this.theater = theater;
	}

  /*
   * Starts the box office simulation by creating (and starting) threads
   * for each box office to sell tickets for the given theater
   *
   * @return list of threads used in the simulation,
   *         should have as many threads as there are box offices
   */
	public List<Thread> simulate() {
		ArrayList<Thread> threads = new ArrayList<Thread>();
		for (String key : office.keySet()) {
			Thread temp = new Thread(new Client(key, this));
			temp.start();
			threads.add(temp);	
		}
		return threads;
	}
	public class Client implements Runnable{
		private String name;
		private BookingClient client;
		    
		public Client(String nameO, BookingClient client) {
		    name = nameO;
			this.client = client;
		    }
		public void run() {
			for(int i = client.office.get(name); i > 0; i--){
				synchronized(client.theater){
					Theater.Seat best = client.theater.bestAvailableSeat();
				    if(best != null){
				      	int num = client.theater.getTransactionLog().size() + 1;
				      	Theater.Ticket newTicket = client.theater.printTicket(name, best, num);
				      	if(newTicket != null)
				      		System.out.print(newTicket.toString());
				    }
				    if(client.theater.getTransactionLog().size() >= client.theater.getTotalSeats()) {
				    	if(!client.theater.soldOut()){
				    		System.out.println("Sorry, we are sold out!");
				    		client.theater.lastTicket();
				    	}
				    }
			    }
				try {
		      		Thread.sleep(50);
		      	} catch (InterruptedException e) {}
			}
		}
	}
}
