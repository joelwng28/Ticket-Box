// insert header here
package assignment6;

import java.util.ArrayList;

public class Theater {
	/*
	 * Represents a seat in the theater
	 * A1, A2, A3, ... B1, B2, B3 ...
	 */
	static class Seat {
		private int rowNum;
		private int seatNum;

		public Seat(int rowNum, int seatNum) {
			this.rowNum = rowNum;
			this.seatNum = seatNum;
		}

		public int getSeatNum() {
			return seatNum;
		}

		public int getRowNum() {
			return rowNum;
		}

		@Override
		public String toString() {
			String result = "";
			int temp = rowNum;
			result = (char)( 65 + temp % 26) + result;
			temp /= 26;
			while (temp > 0){
				result = (char)( 64 + temp % 26) + result;
				temp /= 26;
			}
			result += seatNum;
			return result;
		}
	}

  /*
	 * Represents a ticket purchased by a client
	 */
	static class Ticket {
		private String show;
		private String boxOfficeId;
		private Seat seat;
	  private int client;

		public Ticket(String show, String boxOfficeId, Seat seat, int client) {
			this.show = show;
			this.boxOfficeId = boxOfficeId;
			this.seat = seat;
			this.client = client;
		}

		public Seat getSeat() {
			return seat;
		}

		public String getShow() {
			return show;
		}

		public String getBoxOfficeId() {
			return boxOfficeId;
		}

		public int getClient() {
			return client;
		}

		@Override
		public String toString() {
			String ticket = "";
			ticket += "\n-------------------------------";
			int len = ticket.length();
			String temp = "\n| Show: " + show;
			while(temp.length() < len - 1){
				temp += " ";
			}
			ticket += temp + "|";
			temp = "\n| Box Office ID: " + boxOfficeId;
			while(temp.length() < len - 1){
				temp += " ";
			}
			ticket += temp + "|";
			temp = "\n| Seat: " + seat.toString();
			while(temp.length() < len - 1){
				temp += " ";
			}
			ticket += temp + "|";
			temp = "\n| Client: " + client;
			while(temp.length() < len - 1){
				temp += " ";
			}
			ticket += temp + "|";
			ticket += "\n-------------------------------\n";
			return ticket;
		}
	}
	private String show;
	private int seatsPerRow;
	private int totalSeats;
	private ArrayList<Ticket> purchaseLog = new ArrayList<Ticket>();
	private boolean soldOut;
	public Theater(int numRows, int seatsPerRow, String show) {
		this.show = show;
		this.seatsPerRow = seatsPerRow;
		this.totalSeats = numRows * seatsPerRow;
		this.soldOut = false;
	}
	public boolean soldOut(){
		return this.soldOut;
	}
	public void lastTicket(){
		this.soldOut = true;
	}
	public int getTotalSeats(){
		return this.totalSeats;
	}

	/*
	 * Calculates the best seat not yet reserved
	 *
 	 * @return the best seat or null if theater is full
   */
	public Seat bestAvailableSeat() {
		int bestSeat = purchaseLog.size();
		if(bestSeat < totalSeats){
			int seat = bestSeat % seatsPerRow + 1;
			int row = bestSeat / seatsPerRow;
			Seat newSeat = new Seat(row, seat);
			return newSeat;
		}
		return null;
	}

	/*
	 * Prints a ticket for the client after they reserve a seat
   * Also prints the ticket to the console
	 *
   * @param seat a particular seat in the theater
   * @return a ticket or null if a box office failed to reserve the seat
   */
	public Ticket printTicket(String boxOfficeId, Seat seat, int client) {
		Ticket temp = new Ticket(this.show,boxOfficeId,seat,client);
		for(Ticket t : purchaseLog){
			if(t.getSeat().getRowNum() == seat.getRowNum()){
				if(t.getSeat().getSeatNum() == seat.getSeatNum()){
					return null;
				}
			}
		}
		this.purchaseLog.add(temp);
		return temp;
	}

	/*
	 * Lists all tickets sold for this theater in order of purchase
	 *
   * @return list of tickets sold
   */
	public ArrayList<Ticket> getTransactionLog() {
		ArrayList<Ticket> temp = new ArrayList<Ticket>(purchaseLog);
		return temp;
	}
}
