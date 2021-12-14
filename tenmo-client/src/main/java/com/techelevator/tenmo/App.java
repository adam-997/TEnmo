package com.techelevator.tenmo;

import com.techelevator.tenmo.model.*;
import com.techelevator.tenmo.services.*;
import com.techelevator.view.ConsoleService;

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
		Transfer[] transfers = restTransferService.getTransfersByUserId(currentUser, currentUser.getUser().getId());
		System.out.println("-------------------------------");
		System.out.println("Transfers");
		System.out.println("ID     From/To          Amount");
		System.out.println("-------------------------------");
		for(Transfer transfer: transfers) {
			printTransferDetail(currentUser ,transfer);
		}

		int transferIdChoice = console.getUserInputInteger("\nPlease enter transfer ID to view details (0 to cancel)");
		Transfer transferChoice = validateTransferIdChoice(transferIdChoice, transfers, currentUser);
		if(transferChoice != null) {
			printTransferDetails(currentUser, transferChoice);
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
	}

	private void requestBucks() {
		System.out.println("---------------------------------------------------");
		System.out.println("Users ");
		System.out.println("ID	Name");
		System.out.println("---------------------------------------------------");
		User[] users = userService.getAllUsers(currentUser);
		for (User user : users) {
			System.out.println(user.getId() + "     " + user.getUsername());
		}
		int choice = console.getUserInputInteger("Enter user id of the person you would like to receive money from: ");
			if (userIdChoice(choice,users, currentUser)){

				String amount = console.getUserInput("Please enter the amount you would like to request: ");
				makeTransfer(choice, amount, "Request", "Pending");
			}

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

		if (choice != 0) {
				if(choice == currentUser.getUser().getId()) {
					return false;
				}
			for (User user: users) {
				if (user.getId() == choice){
					return true;
				}

			}

		}
		return false;

}
	private Transfer makeTransfer(int choice, String amount, String transferType, String transferStatus) {
		int transferTypeId = restTransferTypesServices.getTransferType(currentUser, transferType).getTransferTypeId();
		int transferStatusId = restTransferStatusService.getTransferStatus(currentUser, transferStatus).getTransferStatusId();
		int accountFromId;
		int accountToId;
		if (transferType.equals("Send")){
	        accountToId = restAccountService.getAccountByUserId(currentUser, choice).getAccountId();
			accountFromId = restAccountService.getAccountByUserId(currentUser, currentUser.getUser().getId()).getAccountId();
		} else{
			accountToId = restAccountService.getAccountByUserId(currentUser, currentUser.getUser().getId()).getAccountId();
			accountFromId = restAccountService.getAccountByUserId(currentUser, choice).getAccountId();
		}
		double transferAmount = Double.parseDouble(amount);
		Transfer transfer = new Transfer();
//		transfer.setTransferId(getMaxIdPlusOne());
		transfer.setTransferTypeId(transferTypeId);
		transfer.setTransferStatusId(transferStatusId);
		transfer.setAccountFrom(accountFromId);
		transfer.setAccountTo(accountToId);
		transfer.setAmount(transferAmount);

		restTransferService.makeTransfer(currentUser, transfer);
		return transfer;
	}

	private Transfer validateTransferIdChoice(int transferIdChoice, Transfer[] transfers, AuthenticatedUser currentUser) {
		Transfer transferChoice = null;
		if(transferIdChoice != 0) {
			try {
				boolean validTransferIdChoice = false;
				for (Transfer transfer : transfers) {
					if (transfer.getTransferId() == transferIdChoice) {
						validTransferIdChoice = true;
						transferChoice = transfer;
						break;
					}
				}
				if (!validTransferIdChoice) {
				}
			} catch ( RuntimeException e){

			}
		}
		return transferChoice;
	}
private void printTransferDetail(AuthenticatedUser authenticatedUser, Transfer transfer){
		String fromTo;
		int accountFrom = transfer.getAccountFrom();
		int accountTo = transfer.getAccountTo();
		if (restAccountService.getAccountByAccountId(currentUser, accountTo).getUserId() == authenticatedUser.getUser().getId()){
			int accountFromUserId = restAccountService.getAccountByAccountId(currentUser, accountFrom).getUserId();
			String userFromName = userService.getUserByUserId(currentUser, accountFromUserId).getUsername();
			fromTo = "From: " + userFromName;
		} else {
				int userId = restAccountService.getAccountByAccountId(currentUser, accountTo).getUserId();
				String userToName = userService.getUserByUserId(currentUser, userId).getUsername();
				fromTo = "To: " + userToName;
		}
	System.out.println(transfer.getTransferId() + "     " + fromTo + "     " + "$ " + transfer.getAmount());
}


	private void printTransferDetails(AuthenticatedUser authenticatedUser, Transfer transferChoice) {
//		int id = transferChoice.getTransferId();
//		double amount = transferChoice.getAmount();
//		int fromAccount = transferChoice.getAccountFrom();
//		int toAccount = transferChoice.getAccountTo();
//		int transactionTypeId = transferChoice.getTransferTypeId();
//		int transactionStatusId = transferChoice.getTransferStatusId();
		TransferTypes transferTypes = restTransferTypesServices.getTransferTypeFromId(currentUser, transferChoice.getTransferTypeId());
		TransferStatuses transferStatuses = restTransferStatusService.getTransferStatusById(currentUser, transferChoice.getTransferStatusId());


		Account fromAccount = restAccountService.getAccountByAccountId(currentUser, transferChoice.getAccountFrom());
		User fromUser = userService.getUserByUserId(currentUser, fromAccount.getUserId());
		Account toAccount = restAccountService.getAccountByAccountId(currentUser, transferChoice.getAccountTo());
		User toUser = userService.getUserByUserId(currentUser, toAccount.getUserId());

		int fromUserId = fromAccount.getUserId();
		String fromUserName = fromUser.getUsername();
		int toUserId = toAccount.getUserId();

		String toUserName = toUser.getUsername();
		String transactionType = transferTypes.getTransferTypeDesc();
		String transactionStatus = transferStatuses.getDescription();

		console.printTransferDetails(transferChoice.getTransferId(), fromUserName, toUserName, transactionType, transactionStatus, transferChoice.getAmount());
	}

}
