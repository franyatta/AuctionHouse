package auctions;

import java.util.*;
import java.io.*;
import java.time.format.DateTimeFormatter;

public class AllAuctions {

	private ArrayList<Auctions> allAuctions = new ArrayList<>();

	public AllAuctions() {
		setAllAuctions();
	}

	/*
	 * Create Auctions.txt if it does not already exist. Can use new
	 * File("Auctions.txt").createNewFile(); Read auctions line by line from the
	 * file and store them into allAuctions ArrayList.
	 * 
	 */

	private void setAllAuctions() {
		try {
			File file = new File("Auctions.txt");
			if (!file.exists()) {
				file.createNewFile();
			}
			Scanner in = new Scanner(file);
			while (in.hasNextLine()) {
				String s = in.nextLine();
				String[] a = s.split(",");
				String name = a[0];
				String category = a[1];
				double startAmt = Double.valueOf(a[2]).doubleValue();
				double currentBid = Double.valueOf(a[3]).doubleValue();
				String highestBidder = a[4];
				String status = a[5];
				String auctioneer = a[6];

				Auctions auction = new Auctions(name, category, startAmt, currentBid, highestBidder, status, auctioneer,
						null);
				allAuctions.add(auction);
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addAuction(Auctions obj) {
		allAuctions.add(obj);
	}

	public ArrayList<Auctions> getAllAuctions() {
		return allAuctions;
	}

	/*
	 * Takes in an ArrayList of Auctions, prints the auctions in format shown in
	 * instructions
	 */
	public void printAuctions(ArrayList<Auctions> obj) {

		if (allAuctions == null) {
			System.out.println("No auctions!");
			return;
		} else {
			// For enhanced visibility, format the time posted
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");

			// Print header
			System.out.printf("%-12s%-15s%-15s%-15s%-15s%-15s%-15s%n", "Number", "Status", "Item Name", "Category",
					"Current Bid", "Top Bidder", " Time Posted");
			System.out.println("-".repeat(114));

			// Print each auction item
			for (int i = 0; i < obj.size(); i++) {
				Auctions item = obj.get(i);
				System.out.printf("%-12d%-15s%-15s%-15s$%-15.2f%-15s%-15s%n", i + 1, item.getAuctionStatus(),
						item.getName(), item.getCategory(), item.getCurrentBid(), item.getHighestBidder(),
						item.getTimePosted().format(formatter));
			}
		}
	}

	public void printFilteredAuctions(Scanner in, ArrayList<Auctions> obj) {
		ArrayList<Auctions> filteredAuctions = new ArrayList<>();
		if (allAuctions == null) {
			System.out.println("No auctions!");
			return;
		}
		System.out.println("What Category Do you want to filter");

		String choice = in.next();

		for (Auctions x : obj) {
			if (x.getCategory().equals(choice)) {
				filteredAuctions.add(x);
			}
		}

		printAuctions(filteredAuctions);
	}

}
