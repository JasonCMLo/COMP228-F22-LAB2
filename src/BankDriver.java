import java.util.Random;
import java.util.Scanner;

public class BankDriver {

    public static void main(String[] args) {

        // Declare variables shared across switch cases
        int pinLookup;
        int pinFound;

        // Create welcome message array
        BankAccount[] accounts = new BankAccount[5];
        String[] welcomeMessages = {
                "Welcome to Centennial Bank",
                "How can we help you today?",
                "How can we serve your needs today",
                "Welcome to your own developed Bank"
        };

        // Declare random and scanner objects for subsequent use
        Random rand = new Random();
        Scanner input = new Scanner(System.in);

        // Create 5 accounts based on user input. this is achieved via a loop that will execute the following tasks:
        //  - Request information
        //  - Call Bank Account constructor
        //  - repeat * 5

        for (int i = 0; i < accounts.length ; i++) {

            // Declare and fill variables based on user inputs.
            // Note: an extra input.nextLine() appears to be necessary to handle the /n the user
            // creates as they enter the nextInt and nextDouble functions

            System.out.println("Enter the account holder name: ") ;
            String actHolderName = input.nextLine();

            System.out.println("Set a PIN number to access the account: ");
            int actPin = input.nextInt();
            input.nextLine();

            System.out.println("Enter a starting balance: ");
            double actBal = input.nextDouble();
            input.nextLine();

            // Note: For account type we must convert the string input into the AccType enum in Bank Account
            // This can be achieved through a straight cast of the user input, but expecting the user to input
            // a case-sensitive string is prone to error.
            // Numeric options make it easier to error triage.

            System.out.println("""
                                Enter the account type:
                                1. Chequing
                                2. Savings
                                3. TFSA
                                4. RRSP""") ;

            String accTypeInput = input.nextLine();

            BankAccount.accType accTypeEnum;

            switch (accTypeInput) {
                case "1":
                    accTypeEnum = BankAccount.accType.Chequing;
                    break;
                case "2":
                    accTypeEnum = BankAccount.accType.Savings;
                    break;
                case "3":
                    accTypeEnum = BankAccount.accType.TFSA;
                    break;
                case "4":
                    accTypeEnum = BankAccount.accType.RRSP;
                    break;
                default:
                    // Error handling : if an invalid account type is entered, display a message and
                    // make the account a Chequing account

                    System.out.println("ERROR : Invalid entry - DEFAULTING TO CHEQUING");
                    accTypeEnum = BankAccount.accType.Chequing;

            }

            // Generate the new Bank Account object and add it to the array
            accounts[i] = new BankAccount(actHolderName, actPin, actBal, accTypeEnum);
        }

        // Generate random number to send a random welcome message to the user

        int welcomeMessage = rand.nextInt(welcomeMessages.length);
        System.out.println(welcomeMessages[welcomeMessage]);

        // Initiate loop that will display the main menu and execute functionality until the user requests to end the loop
        // The bankMenu variable will remain true until the user selects the option to exit. Therefore, the menu will keep looping
        // at the end of the switch/case block.

        boolean bankMenu = true;
        while (bankMenu) {

            // Display the Main menu

            System.out.println(
                    """
                            Please select one of the following options
                            1. Deposit Amount to an account
                            2. Withdraw an Amount from an account
                            3. Print Account details
                            4. Print All Account Details
                            5. Print Max Loan Amount Details
                            6. Exit the Application"""
            );

            // Based on the input the user selects the switch / case block will route the application to the
            // relevant functionality.

            // Note: The case statement is using strings because our input is using a string
            // This would also work with int userSelection = input.nextInt(); and with all the case statements
            // using an integer

            String userSelection = input.nextLine();

            switch (userSelection) {
                case "1":
                    System.out.println("Deposit selected");
                    System.out.println("Please enter the PIN of the account you wish to deposit funds into: ");

                    pinLookup = input.nextInt();
                    input.nextLine();

                    // pinAuthentication will return the index value of the pinLookup argument within the accounts
                    // array argument

                    pinFound = pinAuthentication(pinLookup, accounts);

                    // If the account has been found (i.e. pinAuthentication has returned a value greater than 0 ),
                    // then we proceed to processing the deposit by accessing the depositFunds method in the Bank
                    // Account class using the index found earlier. Otherwise, an error message is shown.

                    if (pinFound >= 0) {
                        System.out.println("Please enter the amount to be deposited: ");

                        double depositAmount = input.nextDouble();
                        input.nextLine();

                        accounts[pinFound].depositFunds(depositAmount);
                    } else {
                        System.out.println("ERROR: PIN not found. Returning to main menu");
                    }

                    break;

                case "2":
                    System.out.println("Withdrawal selected");
                    System.out.println("Please enter the PIN of the account you wish to withdraw funds from: ");

                    pinLookup = input.nextInt();
                    input.nextLine();

                    // pinAuthentication will return the index value of the pinLookup argument within the accounts
                    // array argument

                    pinFound = pinAuthentication(pinLookup, accounts);

                    // If the account has been found (i.e. pinAuthentication has returned a value greater than 0 ),
                    // then we proceed to processing the deposit by accessing the withdrawFunds method in the Bank
                    // Account class using the index found earlier. Otherwise, an error message is shown.

                    if (pinFound >= 0) {
                        System.out.println("Please enter the amount to be withdrawn: ");

                        double withdrawalAmount = input.nextDouble();
                        input.nextLine();

                        // Check if the account balance is high enough to support the withdrawal amount. If not,
                        // display an error message.
                        if (accounts[pinFound].accountBal > withdrawalAmount ) {
                            accounts[pinFound].withdrawFunds(withdrawalAmount);
                        } else {
                            System.out.println("ERROR: Account Balance is less than withdrawal amount");
                            System.out.println("Account Balance: " + accounts[pinFound].getAccountBal());
                            System.out.println("Withdrawal Amount: " + withdrawalAmount);
                        }

                    } else {
                        System.out.println("ERROR: PIN not found. Returning to main menu");
                    }

                    break;

                case "3":
                    System.out.println("Print Account Details");
                    System.out.println("Please enter the PIN of the account you wish to print details for: ");

                    pinLookup = input.nextInt();
                    input.nextLine();

                    // pinAuthentication will return the index value of the pinLookup argument within the accounts
                    // array argument

                    pinFound = pinAuthentication(pinLookup, accounts);

                    // If the account has been found (i.e. pinAuthentication has returned a value greater than 0 ),
                    // then we proceed to processing the deposit by accessing the withdrawFunds method in the Bank
                    // Account class using the index found earlier. Otherwise, an error message is shown.

                    if (pinFound >= 0) {
                        accounts[pinFound].printDetails();

                    } else {
                        System.out.println("ERROR: PIN not found. Returning to main menu");
                    }

                    break;

                case "4":
                    System.out.println("Printing All Account Details");
                    System.out.println("***************");
                    for (BankAccount acct : accounts) {
                        acct.printDetails();
                        System.out.println("***************");
                    }
                    break;
                case "5":
                    System.out.println("Print Max Loan Amount Details");
                    System.out.println("Please enter the PIN of the account you wish to see the Max Loan amount for: ");

                    pinLookup = input.nextInt();
                    input.nextLine();

                    // pinAuthentication will return the index value of the pinLookup argument within the accounts
                    // array argument

                    pinFound = pinAuthentication(pinLookup, accounts);

                    // If the account has been found (i.e. pinAuthentication has returned a value greater than 0 ),
                    // then we proceed to processing the deposit by accessing the withdrawFunds method in the Bank
                    // Account class using the index found earlier. Otherwise, an error message is shown.

                    if (pinFound >= 0) {
                        System.out.println("The max loan for this account is: " + accounts[pinFound].maxLoanAmt());

                    } else {
                        System.out.println("ERROR: PIN not found. Returning to main menu");
                    }

                    break;
                case "6":
                    System.out.println("Thank You! Exiting Application... ");
                    bankMenu = false;
                    break;
                default:
                    System.out.println("Input not recognized. Please review the menu options and try again.");
                    break;
            }

            // Print blank spacers to add clarity between loop executions
            System.out.println("\n**************************************************************\n");

        }
    }

    public static int pinAuthentication(int pinNumber, BankAccount[] accounts) {


        // Loop through the accounts array to find the index of the appropriate account.
        // If the account has been found (i.e. accFound is true), then we can return the i value for use in the
        // main class.

        for (int i = 0 ; i < accounts.length; i++) {
            if (accounts[i].accountPin == pinNumber) {
                return i;
            }
        }

        // if the Loop doesn't find any matching pin, then we know the pin is an error. Return a number that will
        // never be returned if the pin exists: e.g. negative number.
        return -1;

    }
}
