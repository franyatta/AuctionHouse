package users;

import java.util.Scanner;

//IMPORT THE JAR FILE
import com.odu.Accounts;

public class Admin extends Users {

	public Admin(String username, String password) {
		super(username, password);
	}

	// ASKS FOR A USERNAME AND PASSWORD for a new auctioneer account. Call the
	// addAccount method from the jar file
	public void createAuctioneer(Scanner in, Accounts obj) {
		System.out.println("Enter username: ");
		String username = in.next();
		System.out.println("Enter password: ");
		String password = in.next();

		obj.addAccount(username, password);

		System.out.println("Auctioneer account created!");
	}
}
