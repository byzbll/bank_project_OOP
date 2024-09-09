
package main;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

abstract class Account{
  private String ID;
  private double balance;
  private LocalDate openingDate;
  private LocalDate transactionDate;

  public Account(String ID, double balance) {
     this.ID = ID;
     this.balance = balance;
     this.openingDate = LocalDate.now();
}    
    
  public String getID() {
   return ID;
}    
    
  public double getBalance() {
    return balance;
}   

  public void deposit(double amount) {
    balance += amount;
    transactionDate = LocalDate.now();
    System.out.println(amount + " TL deposited into account " + ID);
}
    
  public boolean withdraw(double amount) {
    if (balance >= amount) {
        balance -= amount;
        transactionDate = LocalDate.now();
        System.out.println(amount + " TL withdrawn from account " + ID);
        return true;
    } else {
        System.out.println("Insufficient balance in account " + ID);
        return false;
      }
    }    
    
  public abstract double calculateBenefit();    
   
  public void displayAccountInfo() {
    System.out.println("Account ID: " + ID);
    System.out.println("Balance: " + balance + " TL");
}    
 
}

class ShortTerm extends Account {
    private static final double INTEREST_RATE = 0.17;
    private static final double MIN_BALANCE = 1000;

    public ShortTerm(String ID, double balance) {
        super(ID, balance);
    }

    @Override
    public double calculateBenefit() {
        return getBalance() * INTEREST_RATE / 365;
    }
}

class LongTerm extends Account {
    private static final double INTEREST_RATE = 0.24;
    private static final double MIN_BALANCE = 1500;

    public LongTerm(String ID, double balance) {
        super(ID, balance);
    }

    @Override
    public double calculateBenefit() {
        return getBalance() * INTEREST_RATE / 365;
    }
}

class Special extends Account {
    private static final double INTEREST_RATE = 0.12;

    public Special(String ID, double balance) {
        super(ID, balance);
    }

    @Override
    public double calculateBenefit() {
        return getBalance() * INTEREST_RATE / 365;
    }
}

class Current extends Account {
    public Current(String ID, double balance) {
        super(ID, balance);
    }

    @Override
    public double calculateBenefit() {
        return 0;
    }
}

class Bank{
  private List<Account> accounts;
  private LocalDate currentDate;
 
  public Bank() {
    accounts = new ArrayList<>();
    currentDate = LocalDate.now();
}
  
  public void getAccount() {
    for (Account account : accounts) {
        account.displayAccountInfo();
    }
}
  
  public void deposit(String ID, double cash) {
        Account account = findAccountByID(ID);
        if (account != null) {
            account.deposit(cash);
        } else {
            System.out.println("Account " + ID + " not found.");
        }
    }  
  

  public void withdraw(String ID, double cash) {
        Account account = findAccountByID(ID);
        if (account != null) {
            account.withdraw(cash);
        } else {
            System.out.println("Account " + ID + " not found.");
        }
    }
  
   private Account findAccountByID(String ID) {
        for (Account account : accounts) {
            if (account.getID().equals(ID)) {
                return account;
            }
        }
        return null;
    }
  
   public void sortition() {
        List<Special> specialAccounts = new ArrayList<>();
        int totalPoints = 0;
        for (Account account : accounts) {
            if (account instanceof Special) {
                specialAccounts.add((Special) account);
                totalPoints += account.getBalance() / 2000;
            }
        }

        int winningNumber = (int) (Math.random() * totalPoints);
        int accumulatedPoints = 0;
        Special winner = null;
        for (Special specialAccount : specialAccounts) {
            accumulatedPoints += specialAccount.getBalance() / 2000;
            if (accumulatedPoints >= winningNumber) {
                winner = specialAccount;
                break;
            }
        }

        if (winner != null) {
            winner.deposit(10000);
            System.out.println("Congratulations! Account " + winner.getID() + " has won 10,000 TL.");
        } else {
            System.out.println("No winner in the sortition.");
        }
    }
  
    
}




public class Main {
    private static Bank bank;
    private static Scanner scanner;
    
    public static void main(String[] args) {
       bank = new Bank();
       scanner = new Scanner(System.in); 
        
       boolean exit = false;
       while(!exit){
            System.out.println("\n----- Bank Application Menu -----");
            System.out.println("1. Create Short Term Account");
            System.out.println("2. Create Long Term Account");
            System.out.println("3. Create Special Account");
            System.out.println("4. Create Current Account");
            System.out.println("5. Deposit Money");
            System.out.println("6. Withdraw Money");
            System.out.println("7. Set System Date");
            System.out.println("8. Show Account History");
            System.out.println("9. Show Account IDs");
            System.out.println("10. Perform Sortition");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
       
            int choice = scanner.nextInt();
            scanner.nextLine(); // Bos satiri oku
       
             switch (choice) {
                case 1:
                    createShortTermAccount();
                    break;
                case 2:
                    createLongTermAccount();
                    break;
                case 3:
                    createSpecialAccount();
                    break;
                case 4:
                    createCurrentAccount();
                    break;
                case 5:
                    increaseBalance();
                    break;
                case 6:
                    decreaseBalance();
                    break;
                case 7:
                    setSystemDate();
                    break;
                case 8:
                    showAccountHistory();
                    break;
                case 9:
                    showAccountIDs();
                    break;
                case 10:
                    performSortition();
                    break;
                case 0:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }   
        }
        scanner.close();
        System.out.println("Exiting Bank Application. Goodbye!");    
    }
    
    private static void createShortTermAccount() {
        System.out.print("Enter account ID: ");
        String ID = scanner.nextLine();
        System.out.print("Enter initial balance: ");
        double balance = scanner.nextDouble();
        scanner.nextLine(); // Boş satırı oku

        Account account = new ShortTerm(ID, balance);
        bank.getAccounts().add(account);
        System.out.println("Short Term Account created. Account ID: " + ID);
    }
    private static void createLongTermAccount() {
        System.out.print("Enter account ID: ");
        String ID = scanner.nextLine();
        System.out.print("Enter initial balance: ");
        double balance = scanner.nextDouble();
        scanner.nextLine(); // Boş satırı oku

        Account account = new LongTerm(ID, balance);
        bank.getAccounts().add(account);
        System.out.println("Long Term Account created. Account ID: " + ID);
    }
    private static void createSpecialAccount() {
        System.out.print("Enter account ID: ");
        String ID = scanner.nextLine();
        System.out.print("Enter initial balance: ");
        double balance = scanner.nextDouble();
        scanner.nextLine(); // Boş satırı oku

        Account account = new Special(ID, balance);
        bank.getAccounts().add(account);
        System.out.println("Special Account created. Account ID: " + ID);
    }
    private static void createCurrentAccount() {
        System.out.print("Enter account ID: ");
        String ID = scanner.nextLine();
        System.out.print("Enter initial balance: ");
        double balance = scanner.nextDouble();
        scanner.nextLine(); // Boş satırı oku

        Account account = new Current(ID, balance);
        bank.getAccounts().add(account);
        System.out.println("Current Account created. Account ID: " + ID);
    }
    private static void increaseBalance() {
        System.out.print("Enter account ID: ");
        String ID = scanner.nextLine();
        System.out.print("Enter amount to deposit: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Boş satırı oku

        bank.deposit(ID, amount);
    }
    private static void decreaseBalance() {
        System.out.print("Enter account ID: ");
        String ID = scanner.nextLine();
        System.out.print("Enter amount to withdraw: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Boş satırı oku

        bank.withdraw(ID, amount);
    }
    private static void setSystemDate() {
        System.out.print("Enter system date (dd-mm-yyyy): ");
        String dateStr = scanner.nextLine();
        LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        bank.setCurrentDate(date);
        System.out.println("System date set to: " + dateStr);
    }
    private static void showAccountHistory() {
        bank.getAccount();
    }
    private static void showAccountIDs() {
        System.out.println("Account IDs:");
        for (Account account : bank.getAccounts()) {
            System.out.println(account.getID());
        }
    }
    private static void performSortition() {
        bank.sortition();
    }

}
