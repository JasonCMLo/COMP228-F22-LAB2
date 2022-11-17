import java.util.Random;

public class BankAccount {

    // Import random number generator functionality to be used for AccountNumber and set parameters for account number max and min
    Random rand = new Random();
    int minAcctNum = 1000;
    int maxAcctNum = 2000;

    // Create an enum to hold all the account types
    public enum accType {
        Chequing,
        Savings,
        RRSP,
        TFSA
    }

    // Declare the variables that the BankAccount class will use
    String accountHolderName;
    int accountPin;
    double accountBal;
    int accountNumber;
    accType accountType;

    // Constructor that takes all possible fields to generate an account object
    public BankAccount(String accountHolderName, int accountPin, double accountBal, accType accountType) {

        this.accountHolderName = accountHolderName;
        this.accountPin = accountPin;
        this.accountBal = accountBal;

        // Calculate a random account number between 1000 and 2000 (parameters set above)
        this.accountNumber = (int)Math.floor(rand.nextFloat() * (maxAcctNum - minAcctNum + 1) + minAcctNum) ;

        this.accountType = accountType;
    }

    // Default constructor that sets all the variables to 0 or null
    public BankAccount() {
        accountHolderName = null;
        accountPin = 0;
        accountBal = 0;
        accountNumber = 0;
        accountType = null;
    }

    // System generated getters and setters

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public int getAccountPin() {
        return accountPin;
    }

    public void setAccountPin(int accountPin) {
        this.accountPin = accountPin;
    }

    public double getAccountBal() {
        return accountBal;
    }

    public void setAccountBal(double accountBal) {
        this.accountBal = accountBal;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public accType getAccountType() {
        return accountType;
    }

    public void setAccountType(accType accountType) {
        this.accountType = accountType;
    }

    // Calculate the maximum loan amount based on provided requirements:
    // Chequing and Savings have a loan amount of 30% of the account balance
    // TFSA has a loan amount of 40% of the account balance
    // RRSP has no loan amount
    public double maxLoanAmt() {

        double maxLoan = 0;

        // Switch case - depending on the type of the account (Chequing, savings, TFSA or RRSP), route the max loan
        // calculation a certain way to calculate the appropriate max loan.

        switch (accountType) {
            case Chequing:
            case Savings:
                maxLoan =  accountBal * 0.3f;
                break;
            case TFSA:
                maxLoan = accountBal * 0.4f;
                break;
            case RRSP:
                break;
            default:
                break;
        }

        // Return the max loan - we are rounding to 2 decimal places here through multiplying by 100 and dividing by 100
        return Math.round(maxLoan * 100.00) / 100.00;
    }

    // Method for the user to deposit funds from an account object.
    // Takes a single argument (amount) which is what we increment the account balance by
    // Displays a success message on completion
    public void depositFunds(double amount) {
        accountBal += amount;

        System.out.println("Deposit of: " + amount + " successful.");
        System.out.println("New Balance: " + accountBal);
    }

    // Method for the user to withdraw funds from an account object.
    // Takes a single argument (amount) which is what we decrement the account balance by
    // Displays a success message on completion
    public void withdrawFunds(double amount) {
        accountBal -= amount;

        System.out.println("Withdrawal of: " + amount + " successful.");
        System.out.println("New Balance: " + accountBal);
    }

    // Method to print all the account details
    public void printDetails() {
        System.out.println("Account holder: " + accountHolderName +
        "\nAccount PIN: " + accountPin +
        "\nAccount Balance: " + accountBal +
        "\nAccount Number: " + accountNumber +
        "\nAccount Type: " + accountType +
        "\nMax Loan Available: " + maxLoanAmt());
    }
}

