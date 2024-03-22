package users;

import java.io.*;
import java.util.*;

import auctions.AllAuctions;
import auctions.Auctions;

public class Auctioneer extends Users {

	private ArrayList<Auctions> auctions = new ArrayList<>();

	public Auctioneer(String username, String password, AllAuctions obj) {
		super(username, password);
		// TODO
		setAuctions(obj);
	}

	public ArrayList<Auctions> getAuctions() {
		return auctions;
	}

	/*
	 * Loop through all auctions and if any belong to the auctioneer add it to the
	 * auctions list
	 */
	public void setAuctions(AllAuctions obj) {
		ArrayList<Auctions> allAuctions = obj.getAllAuctions();
		for (Auctions auction : allAuctions) {
			if (auction.getAuctioneer().equals(this.getUsername())) {
				auctions.add(auction);
			}
		}
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj) {
			return true;
		} else if (!(obj instanceof Auctioneer)) {
			return false;
		} else {
			Auctioneer rhs = (Auctioneer) obj;
			return (this.auctions.equals(rhs.auctions));
		}
	}

	public void addAuctions(Scanner in, AllAuctions obj) {
		System.out.println("Do you want to manually upload an auction? (Y or N)");
		String choice = in.next();
		if (choice.equals("Y") || choice.equals("y")) {
			manualAddition(in, obj);
		} else if (choice.equals("n") || choice.equals("N")) {
			System.out.println("Returning");
		} else {
			System.out.println("Incorrect Input");
		}
	}

	/*
	 * Writes to the Auctions.txt file when a new auctions is added STRING TO WRITE:
	 * obj.getItemName()+","+obj.getCategory()+","+obj.getStartingAmount()+","+
	 * obj.getCurrentBid()+","+obj.getHighestBidder()+","+obj.getAuctionStatus()
	 * +","+obj.getAuctioneer()
	 */
	private void writeToAuctions(Auctions obj) {
		String fileName = "Auctions.txt";
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {
			String line = obj.getName() + "," + obj.getCategory() + "," + obj.getStartingAmounts() + ","
					+ obj.getCurrentBid() + "," + obj.getHighestBidder() + "," + obj.getAuctionStatus() + ","
					+ obj.getAuctioneer();
			bw.write(line);
			bw.newLine();
		} catch (IOException e) {
			System.err.println(e);
		}
	}

	/*
	 * Asks for the Name, category, and starting amount. Create an auction object
	 * Add it to the all auctions list, call the writeToAuctions() method, and add
	 * it to auctioneers auction list
	 */
	private void manualAddition(Scanner in, AllAuctions allAuctionsObj) {
		System.out.println("Enter the item name:");
		String name = in.next();
		System.out.println("Enter the category:");
		String category = in.next();
		System.out.println("Enter the Starting Amount:");
		double startingAmount = in.nextDouble();

		Auctions addNew = new Auctions(name, category, startingAmount, 0, "", "Upcoming", this.getUsername(), null);
		allAuctionsObj.addAuction(addNew);
		writeToAuctions(addNew);
		auctions.add(addNew);
		System.out.println("Auction added successfully.");
	}

	/*
	 * Print the auctioneers auctions and ask which one they would like to start
	 * change the status to "In Progress"
	 */
	public void startAuction(Scanner in, AllAuctions obj) {
		printAuctions(obj);
		System.out.println("Select an auction to start:");
		for (int i = 0; i < auctions.size(); i++) {
			Auctions auction = auctions.get(i);
			if (auction.getAuctionStatus().equals("Upcoming")) {
				System.out.println((i + 1) + ". " + auction.getName());
			}
		}
		int response = in.nextInt();
		Auctions chosen = auctions.get(response - 1);
		chosen.setAuctionStatus("In Progress");
	}

	/*
	 * Print the auctioneers auctions and ask which one they would like to end
	 * change the status to "Completed"
	 */
	public void endAuction(Scanner in, AllAuctions obj) {
		printAuctions(obj);
		System.out.println("Select an auction to end:");
		for (int i = 0; i < auctions.size(); i++) {
			Auctions auction = auctions.get(i);
			if (auction.getAuctionStatus().equals("In Progress")) {
				System.out.println((i + 1) + ". " + auction.getName());
			}
		}
		int response = in.nextInt();
		Auctions chosen = auctions.get(response - 1);
		chosen.setAuctionStatus("Completed");
	}

	public void printAuctions(AllAuctions obj) {
		obj.printAuctions(auctions);
	}

}
