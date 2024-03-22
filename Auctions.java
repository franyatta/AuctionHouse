package auctions;

import java.time.LocalDateTime;

public class Auctions {
	private String name;
	private String category;
	private double startingAmounts;
	private double currentBid;
	private String highestBidder;
	private String auctionStatus;
	private String auctioneer;
	private LocalDateTime timePosted; // The time it was posted

	/*
	 * Auctions created in two way: Previous auctions from a txt file or new
	 * auctions being created from an auctioneer.
	 * 
	 * This means that for auctionStatus, highestBidder, and timePosted needs a
	 * conditional statement. See below for example
	 */
	public Auctions(String name, String category, double startingAmounts, double currentBid, String highestBidder,
			String auctionStatus, String auctioneer, LocalDateTime timePosted) {

		if (timePosted != null) {
			this.timePosted = timePosted;
		} else {
			setPostedTime();
		}

		if (auctionStatus != null) {
			this.auctionStatus = auctionStatus;
		} else {
			auctionStatus = "";
		}

		if (highestBidder != null) {
			this.highestBidder = highestBidder;
		} else {
			highestBidder = "";
		}

		this.name = name;
		this.category = category;
		this.startingAmounts = startingAmounts;
		this.currentBid = currentBid;
		this.auctioneer = auctioneer;
	}

	public Auctions() {
		name = "Buzz Lightyear";
		category = "Toys";
		startingAmounts = 0.0;
		currentBid = 0.0;
		highestBidder = "Andy";
		auctionStatus = "Upcoming";
		auctioneer = "Mr. Potato Head";
	}

	// DO NOT EDIT, SETS THE TIME FOR A NEW AUCTION
	private void setPostedTime() {
		this.timePosted = LocalDateTime.now();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public double getStartingAmounts() {
		return startingAmounts;
	}

	public void setStartingAmounts(double startingAmounts) {
		this.startingAmounts = startingAmounts;
	}

	public double getCurrentBid() {
		return currentBid;
	}

	public void setCurrentBid(double currentBid) {
		if (currentBid > this.currentBid) {
			this.currentBid = currentBid;
		}
	}

	public String getHighestBidder() {
		return highestBidder;
	}

	public void setHighestBidder(String highestBidder) {
		this.highestBidder = highestBidder;
	}

	public String getAuctionStatus() {
		return auctionStatus;
	}

	public void setAuctionStatus(String auctionStatus) {
		this.auctionStatus = auctionStatus;
	}

	public String getAuctioneer() {
		return auctioneer;
	}

	public void setAuctioneer(String auctioneer) {
		this.auctioneer = auctioneer;
	}

	public LocalDateTime getTimePosted() {
		return timePosted;
	}

	public void setTimePosted(LocalDateTime timePosted) {
		this.timePosted = timePosted;
	}

}
