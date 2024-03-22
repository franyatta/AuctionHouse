package users;

import java.util.ArrayList;
import java.util.Scanner;

import auctions.AllAuctions;
import auctions.Auctions;

public class Customer extends Users {

	private float accountBalance;
	private ArrayList<Auctions> bids = new ArrayList<>();
	private ArrayList<Auctions> watchList = new ArrayList<>();

	public Customer(String username, String password, AllAuctions obj) {
		// Uncomment once constructor for user is created
		super(username, password);
		accountBalance = 0;
		generateBids(obj);
	}

	/*
	 * If AllAuctions arraylist is not null, store all auctions that belong to the
	 * user into bids arrayList
	 */
	private void generateBids(AllAuctions obj) {
		ArrayList<Auctions> allAuctions = obj.getAllAuctions();

		if (allAuctions != null) {
			for (Auctions auction : allAuctions) {
				if (auction.getAuctioneer().equals(this.username)) {
					bids.add(auction);
				}
			}
		}
	}

	/*
	 * Ask how much they want to add to the account. Add that amount to account.
	 */
	public void addToAccountBalance(Scanner in) {

		try {
			System.out.println("Enter the amount you want to add to your balance:");
			double amountToAdd = in.nextDouble();

			if (amountToAdd < 0) {
				System.err.println("Invalid amount.");
				return;
			}
			accountBalance += amountToAdd;
			System.out.println("Your updated account balance is: " + accountBalance);
		} catch (NumberFormatException e) {
			System.err.println("Amount must be a number");
		}
	}

	private void subtractFromAccountBalance(float subtraction) {
		accountBalance = accountBalance - subtraction;
	}

	/*
	 * This method is called when a bid is made. It credits the previous top bidder
	 * with the amount that they bidded.
	 */
	private void addAfterFailedBid(float addition, String previousTopBidder, ArrayList<Customer> customers) {

		for (Customer customer : customers) {
			if (customer.getUsername().equals(previousTopBidder)) {
				double currentBalance = customer.getAccountBalance();
				float newBalance = (float) (currentBalance + addition);
				customer.setAccountBalance(newBalance);
				System.out.println(
						"Previous top bidder " + previousTopBidder + " added back with updated balance: " + newBalance);
				break;
			}
		}
	}

	private void addToWatchList(Auctions obj) {
		watchList.add(obj);
		System.out.println("Auction added to watchlist.");
	}

	private void removeFromWatchList(Auctions obj) {
		watchList.remove(obj);
		System.out.println("Auction removed from watchlist.");
	}

	public void printBids(AllAuctions obj) {
		obj.printAuctions(bids);
	}

	public void printWatchList(AllAuctions obj) {
		if (watchList.isEmpty()) {
			System.err.println("Your watchlist is empty.");
		} else {
			obj.printAuctions(watchList);
		}
	}

	/*
	 * Used as the driver to make bids. Prints all the auctions, asks which auction
	 * they want to bid on and how much. Create a auction object of the selected
	 * auction Check if the bid is valid. If it is call the following methods
	 * [addAfterFailedBid() from this class,setCurrentBid() for the Auction Class,
	 * and subtractFromAccountBalance() from this class] Add it to the customers
	 * bids list
	 */
	public void makeBid(Scanner in, AllAuctions obj, ArrayList<Customer> customers) {

		obj.printAuctions(obj.getAllAuctions());
		System.out.println("Enter the number of the auction you want to bid on:");
		int auctionID = in.nextInt();

		// Doesn't allow user to bid on "Upcoming" or "Completed" auction
		if ((obj.getAllAuctions().get(auctionID - 1).getAuctionStatus() == "In Progress")) {
			System.out.println("Enter your bid amount:");
			float bidAmount = in.nextFloat();

			Auctions selectedAuction = obj.getAllAuctions().get(auctionID - 1);
			// Check to make sure the bid amount is higher than current bid and that the
			// user has enough in account balance
			if (bidAmount > selectedAuction.getCurrentBid() && bidAmount <= accountBalance) {
				String previousTopBidder = selectedAuction.getHighestBidder();
				float previousTopBid = (float) selectedAuction.getCurrentBid();

				addAfterFailedBid(previousTopBid, previousTopBidder, customers);

				selectedAuction.setCurrentBid(bidAmount);
				selectedAuction.setHighestBidder(this.username);

				// Remove bid amount from account balance
				subtractFromAccountBalance(bidAmount);

				bids.add(selectedAuction);
				System.out.println("Bid successful!");
			} else {
				System.err.println("Invalid bid amount.");
			}
		} else {
			System.err.println("Auction needs to be active in order to bid.");
		}
	}

	/*
	 * Used as the driver to add and delete auctions from watch list. Ask if the
	 * customer wants to add or remove from watch list. IF ADDING Print All the
	 * Auctions and ask which one the want to add. add that auction to watchlist
	 * with addToWatchList() IF removing Print Watch List and ask which one they
	 * want to remove Remove it from the watchlist with removeFromWatchList()
	 */
	public void controlWatchList(AllAuctions obj, Scanner in) {
		System.out.println("Enter 1 to add an auction to watchlist or 2 to remove an auction:");
		int choice = in.nextInt();

		if (choice == 1) {
			obj.printAuctions(obj.getAllAuctions());
			System.out.println("Enter the number of the auction you want to add to watchlist:");
			int auctionID = in.nextInt();
			Auctions selectedAuction = obj.getAllAuctions().get(auctionID - 1);
			addToWatchList(selectedAuction);

		} else if (choice == 2) {
			if (watchList.isEmpty()) {
				System.err.println("Your watchlist is empty.");
			} else {
				printWatchList(obj);
				System.out.println("Enter the number of the auction you want to remove from watchlist:");
				int auctionIndex = in.nextInt();
				Auctions selectedAuction = watchList.get(auctionIndex - 1);
				removeFromWatchList(selectedAuction);
			}
		} else {
			System.out.println("Invalid choice.");
		}
	}

	public float getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(float accountBalance) {
		this.accountBalance = accountBalance;
	}

	public ArrayList<Auctions> getBids() {
		return bids;
	}

	public void setBids(ArrayList<Auctions> bids) {
		this.bids = bids;
	}

	public ArrayList<Auctions> getWatchList() {
		return watchList;
	}

	public void setWatchList(ArrayList<Auctions> watchList) {
		this.watchList = watchList;
	}

}
