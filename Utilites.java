package utility;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import auctions.AllAuctions;
import auctions.Auctions;
import users.*;

//NEED TO IMPORT THE ACCOUNTS JAR, refer to instructions
import com.odu.Accounts;

public class Utilites {

	private Accounts customers = new Accounts("C");
	private Accounts auctioneers = new Accounts("A");
	private Accounts admin = new Accounts("ADMIN");

	private AllAuctions all = new AllAuctions();
	private ArrayList<Customer> customerList = new ArrayList<>();
	private ArrayList<Auctioneer> auctioneerList = new ArrayList<>();

	/*
	 * Create all the menu methods, refer to the instructions to see specifics
	 */
	public void viewMainMenu() {
		try {
			while (true) {

				Scanner in = new Scanner(System.in);
				System.out.println("Main Menu:\n\t1. Login\n\t2. Create an account\n\t0. Exit");
				System.out.print("Enter your choice: ");
				int choice = in.nextInt();

				switch (choice) {
				case 1:
					login(in);
					break;
				case 2:
					accountCreation(in);
					break;
				case 0:
					System.out.println("Goodbye!");
					System.exit(0);
				default:
					System.err.println("Invalid choice. Please try again.");
				}
			}
		} catch (InputMismatchException e) {
			System.err.println("Invalid input.");
		}
	}

	private void viewCustomerMenu(String username, String password, Scanner in) {

		// MAKE SURE TO ADD THE CUSTOMER TO THE customerList if the first time being
		// signed in
		Customer customer = null;
		for (Customer c : customerList) {
			if (c.getUsername().equals(username) && c.getPassword().equals(password)) {
				customer = c;
				break;
			}
		}

		if (customer == null) {
			customer = new Customer(username, password, all);
			customerList.add(customer);
		}

		while (true) {

			System.out.println(
					"Customer Menu:\n\t1. View all auctions\n\t2. Filter auctions\n\t3. View Watch List\n\t4. Add/Remove from Watch List\n\t5. View your Bids\n\t6. Make A Bid\n\t7. View My Balance\n\t8. Add to Balance\n\t0. Sign Out");
			System.out.print("Enter your choice: ");
			int choice = in.nextInt();

			switch (choice) {
			case 1:
				all.printAuctions(all.getAllAuctions());
				break;
			case 2:
				all.printFilteredAuctions(in, all.getAllAuctions());
				break;
			case 3:
				customer.printWatchList(all);
				break;
			case 4:
				customer.controlWatchList(all, in);
				break;
			case 5:
				customer.printBids(all);
				break;
			case 6:
				customer.makeBid(in, all, customerList);
				break;
			case 7:
				System.out.println("Your balance: $" + customer.getAccountBalance());
				break;
			case 8:
				customer.addToAccountBalance(in);
				break;
			case 0:
				System.out.println("Goodbye!");
				return;
			default:
				System.err.println("Invalid input. Try again");
			}
		}
	}

	private void viewAdminMenu(String username, Scanner in) {

		System.out.println("Would you like to create an Auctioneer account? (Y or N)");
		String choice = in.next();

		if (choice.equalsIgnoreCase("Y")) {
			System.out.println("Enter username:");
			String auctioneerUser = in.next();
			System.out.println("Enter password:");
			String auctioneerPass = in.next();

			auctioneers.addAccount(auctioneerUser, auctioneerPass);
			System.out.println("Auctioneer account added successfully.");

		} else {
			System.err.println("Returning to main menu..");
		}
	}

	private void viewAuctioneerMenu(String username, String password, Scanner in) {
		// TODO
		// MAKE SURE TO ADD THE CUSTOMER TO THE auctioneerList if the first time being
		// signed in
		Auctioneer auctioneer = null;
		for (Auctioneer a : auctioneerList) {
			if (a.getUsername().equals(username)) {
				auctioneer = a;
				break;
			}
		}

		if (auctioneer == null) {
			auctioneer = new Auctioneer(username, password, all);
			auctioneerList.add(auctioneer);
		}

		while (true) {
			System.out.println(
					"Auctioneer Menu:\n\t1. Create an auction\n\t2. View your Auctions\n\t3. Start an Auction\n\t4. End an Auction\n\t0. Sign Out");
			System.out.print("Enter your choice: ");
			int choice = in.nextInt();

			switch (choice) {
			case 1:
				auctioneer.addAuctions(in, all);
				writeToAuctions();
				break;
			case 2:
				auctioneer.printAuctions(all);
				break;
			case 3:
				auctioneer.startAuction(in, all);
				break;
			case 4:
				auctioneer.endAuction(in, all);
				break;
			case 0:
				System.err.println("Signing out..");
				return;
			default:
				System.err.println("Invalid input");
			}
		}

	}

	// Login method allows user to access different login information for associated
	// user types
	private void login(Scanner in) {

		String userType;

		while (true) {
			System.out.println("What type of account are you logging into: C(customer), A(auctioneer), ADMIN");
			in.nextLine();
			userType = in.next();
			if (userType.equals("C") || userType.equals("A") || userType.equals("ADMIN")) {
				signIn(in, userType);
				break;
			} else {
				System.err.println("Incorrect Input");
			}
		}

	}

	/*
	 * Ask for the username and password. Depending on the type of user passed in
	 * test it against either customer.signIn(), auctioneers.signIn(), or
	 * admin.signIn() If true call the correct user menu method.
	 */
	private void signIn(Scanner in, String userType) {

		System.out.println("Enter username:");
		String username = in.next();
		System.out.println("Enter password:");
		String password = in.next();

		switch (userType) {
		case "C":
			if (customers.signIn(username, password)) {
				viewCustomerMenu(username, password, in);
			} else {
				System.err.println("Invalid user and/or password");
			}
			break;
		case "A":
			if (auctioneers.signIn(username, password)) {
				viewAuctioneerMenu(username, password, in);
			} else {
				System.err.println("Invalid user and/or password");
			}
			break;
		case "ADMIN":
			if (admin.signIn(username, password)) {
				viewAdminMenu(username, in);
			} else {
				System.err.println("Invalid user and/or password");
			}
			break;
		default:
			System.err.println("Invalid user type. Try again");
		}

	}

	/*
	 * Ask for a username and password. call the addAccount method from the jar file
	 * for customers
	 */
	private void accountCreation(Scanner in) {
		System.out.println(
				"What type of account would you like to create?\n 1. Customer Account\n 2. Auctioneer Account");
		int choice = in.nextInt();
		switch (choice) {
		case 1:
			System.out.println("Enter username:");
			String customerUser = in.next();

			System.out.println("Enter password:");
			String customerPass = in.next();

			try {
				customers.addAccount(customerUser, customerPass);
			} catch (IllegalArgumentException e) {
				System.err.println("Failed to create account");
			}
			break;
		case 2:
			System.out.println("Enter username:");
			String username = in.next();

			System.out.println("Enter password:");
			String password = in.next();

			try {
				auctioneers.addAccount(username, password);
			} catch (IllegalArgumentException e) {
				System.err.println("Failed to create account");
			}
			break;
		default:
			System.err.println("Invalid input");
			return;
		}
	}

	/*
	 * Gets called at the end of main menu. Write over the Auction.txt file with the
	 * current state of all auctions list String per line:
	 * x.getItemName()+","+x.getCategory()+","+x.getPostedTime()+","+x.
	 * getStartingAmount()+","+x.getCurrentBid()+","+x.getHighestBidder()+","+x.
	 * getAuctionStatus()+","+x.getAuctioneer()
	 */
	private void writeToAuctions() {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter("Auction.txt"))) {
			for (Auctions auction : all.getAllAuctions()) {
				String line = auction.getName() + "," + auction.getCategory() + "," + auction.getTimePosted() + ","
						+ auction.getStartingAmounts() + "," + auction.getCurrentBid() + ","
						+ auction.getHighestBidder() + "," + auction.getAuctionStatus() + "," + auction.getAuctioneer();
				bw.write(line);
				bw.newLine();
			}
		} catch (IOException e) {
			System.err.print(e);
		}
	}
}
