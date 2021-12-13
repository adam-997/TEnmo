package com.techelevator.tenmo;

import com.techelevator.tenmo.model.*;
import com.techelevator.tenmo.services.*;
import com.techelevator.view.ConsoleService;

import java.util.Arrays;
import java.util.Objects;

public class App {

	private static final String API_BASE_URL = "http://localhost:8080/";

	private static final String MENU_OPTION_EXIT = "Exit";
	private static final String LOGIN_MENU_OPTION_REGISTER = "Register";
	private static final String LOGIN_MENU_OPTION_LOGIN = "Login";
	private static final String[] LOGIN_MENU_OPTIONS = {LOGIN_MENU_OPTION_REGISTER, LOGIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT};
	private static final String MAIN_MENU_OPTION_VIEW_BALANCE = "View your current balance";
	private static final String MAIN_MENU_OPTION_SEND_BUCKS = "Send TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS = "View your past transfers";
	private static final String MAIN_MENU_OPTION_REQUEST_BUCKS = "Request TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS = "View your pending requests";
	private static final String MAIN_MENU_OPTION_LOGIN = "Login as different user";
	private static final String[] MAIN_MENU_OPTIONS = {MAIN_MENU_OPTION_VIEW_BALANCE, MAIN_MENU_OPTION_SEND_BUCKS, MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS, MAIN_MENU_OPTION_REQUEST_BUCKS, MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS, MAIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT};

	public AccountService restAccountService;
	public TransferService restTransferService;
	public TransferTypeService restTransferTypesServices;
	public TransferStatusService restTransferStatusService;
	public AuthenticatedUser currentUser;
	public ConsoleService console;
	public AuthenticationService authenticationService;
	public UserService userService;

	public static void main(String[] args) {
		App app = new App(new ConsoleService(System.in, System.out), new AuthenticationService(API_BASE_URL));
		app.run();
	}

	public App(ConsoleService console, AuthenticationService authenticationService) {
		this.console = console;
		this.authenticationService = authenticationService;
		this.restAccountService = new AccountService();
		this.restTransferService = new TransferService();
		this.restTransferStatusService = new TransferStatusService();
		this.restTransferTypesServices = new TransferTypeService();
		this.userService = new UserService();
	}

	public void run() {
		System.out.println("*********************");
		System.out.println("* Welcome to TEnmo! *");
		System.out.println("*********************");

		registerAndLogin();
		mainMenu();
	}

	private void mainMenu() {
		while (true) {
			String choice = (String) console.getChoiceFromOptions(MAIN_MENU_OPTIONS);
			if (MAIN_MENU_OPTION_VIEW_BALANCE.equals(choice)) {
				viewCurrentBalance();
			} else if (MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS.equals(choice)) {
				viewTransferHistory();
			} else if (MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS.equals(choice)) {
				viewPendingRequests();
			} else if (MAIN_MENU_OPTION_SEND_BUCKS.equals(choice)) {
				sendBucks();
			} else if (MAIN_MENU_OPTION_REQUEST_BUCKS.equals(choice)) {
				requestBucks();
			} else if (MAIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else {
				// the only other option on the main menu is to exit
				exitProgram();
			}
		}
	}

	private void viewCurrentBalance() {
		Balance balance = restAccountService.getBalanceByUser(currentUser);
		System.out.println("Your current balance is: $" + balance.getBalance());

	}

	private void viewTransferHistory() {
		System.out.println("---------------------------------------------------");
		System.out.println("Transfers");
		System.out.println("ID	From/To	Amount");
		System.out.println("---------------------------------------------------");
		Transfer[] transfers = restTransferService.getTransfersByUserId(currentUser, currentUser.getUser().getId());
		for (Transfer transfer : transfers) {
			System.out.println(transfer);
			System.out.println("---------------------------------------------------");
		}
	}

	private void viewPendingRequests() {
		System.out.println("---------------------------------------------------");
		System.out.println("Pending Transfers");
		System.out.println("ID");
		System.out.println("---------------------------------------------------");
		// TODO Auto-generated method stub

	}

	private void sendBucks() {
		System.out.println("---------------------------------------------------");
		System.out.println("Users ");
		System.out.println("ID	Name");
		System.out.println("---------------------------------------------------");
		User[] users = userService.getAllUsers(currentUser);
		for (User user : users) {
			System.out.println(user.getId() + "     " + user.getUsername());
		}
		int choice = console.getUserInputInteger("Enter user id of the person you would like to transfer money to: ");
			if (userIdChoice(choice, users, currentUser)) {

				String amount = console.getUserInput("Please enter the amount you would like to transfer: ");
				makeTransfer(choice, amount, "Send", "Approved");
		}
		// TODO Auto-generated method stub

	}

	private void requestBucks() {
		// TODO Auto-generated method stub

	}

	private void exitProgram() {
		System.exit(0);
	}

	private void registerAndLogin() {
		while (!isAuthenticated()) {
			String choice = (String) console.getChoiceFromOptions(LOGIN_MENU_OPTIONS);
			if (LOGIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else if (LOGIN_MENU_OPTION_REGISTER.equals(choice)) {
				register();
			} else {
				// the only other option on the login menu is to exit
				exitProgram();
			}
		}
	}

	private boolean isAuthenticated() {
		return currentUser != null;
	}

	private void register() {
		System.out.println("Please register a new user account");
		boolean isRegistered = false;
		while (!isRegistered) //will keep looping until user is registered
		{
			UserCredentials credentials = collectUserCredentials();
			try {
				authenticationService.register(credentials);
				isRegistered = true;
				System.out.println("Registration successful. You can now login.");
			} catch (AuthenticationServiceException e) {
				System.out.println("REGISTRATION ERROR: " + e.getMessage());
				System.out.println("Please attempt to register again.");
			}
		}
	}

	private void login() {
		System.out.println("Please log in");
		currentUser = null;
		while (currentUser == null) //will keep looping until user is logged in
		{
			UserCredentials credentials = collectUserCredentials();
			try {
				currentUser = authenticationService.login(credentials);
			} catch (AuthenticationServiceException e) {
				System.out.println("LOGIN ERROR: " + e.getMessage());
				System.out.println("Please attempt to login again.");
			}
		}
	}

	private UserCredentials collectUserCredentials() {
		String username = console.getUserInput("Username");
		String password = console.getUserInput("Password");
		return new UserCredentials(username, password);
	}

	private boolean userIdChoice(int choice, User[] users, AuthenticatedUser currentUser) {
		users = userService.getAllUsers(currentUser);
		if (choice != 0) {
				if(choice == currentUser.getUser().getId()) {
					return false;
				}
				if(Arrays.asList(users).contains(userService.getUserByUserId(currentUser, choice))) {
					return true;
				}

			}
		return false;

}
	private Transfer makeTransfer(int choice, String amount, String transferType, String transferStatus) {
		int transferTypeId = restTransferTypesServices.getTransferTypeByDesc(currentUser, transferType).getTransferTypeId();
		int transferStatusId = restTransferStatusService.getTransferStatusByDesc(currentUser, transferStatus).getTransferStatusId();
		int accountFromId;
		int accountToId;
		if (Objects.equals(transferType, "Send")){
	        accountToId = restAccountService.getAccountByUserId(currentUser, choice).getAccountId();
			accountFromId = restAccountService.getAccountByUserId(currentUser, currentUser.getUser().getId()).getAccountId();
		} else{
			accountToId = restAccountService.getAccountByUserId(currentUser, currentUser.getUser().getId()).getAccountId();
			accountFromId = restAccountService.getAccountByUserId(currentUser, choice).getUserId();
		}
		double transferAmount = Double.parseDouble(amount);
		Transfer transfer = new Transfer();
		transfer.setTransferId(getMaxIdPlusOne());
		transfer.setAmount(transferAmount);
		transfer.setTransferTypeId(transferTypeId);
		transfer.setTransferStatusId(transferStatusId);
		transfer.setAccountFrom(accountFromId);
		transfer.setAccountTo(accountToId);
		restTransferService.createTransfer(currentUser, transfer);
		return transfer;
	}
	private int getMaxID() {
		int maxID = 0;
		for (Transfer r : restTransferService.getAllTransfers(currentUser)) {
			if (r.getTransferId() > maxID) {
				maxID = r.getTransferId();
			}
		}
		return maxID;
	}
	private int getMaxIdPlusOne() {
		return getMaxID() + 1;
	}

}
