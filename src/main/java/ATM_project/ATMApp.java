package ATM_project;

import java.util.Scanner;

public class ATMApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();
        System.out.print("Enter PIN: ");
        int pin = scanner.nextInt();

        try {
            User user = ATM.validateUser(accountNumber, pin);
            if (user == null) {
                System.out.println("Invalid account number or PIN.");
                return;
            }

            while (true) {
                System.out.println("\n1. Check Balance");
                System.out.println("2. Withdraw");
                System.out.println("3. Deposit");
                System.out.println("4. Exit");
                System.out.print("Choose an option: ");
                int option = scanner.nextInt();

                switch (option) {
                    case 1:
					double balance = 0;
					try {
						balance = ATM.checkBalance(user.getUserID());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                        System.out.println("Current Balance: " + balance);
                        break;
                    case 2:
                        System.out.print("Enter amount to withdraw: ");
                        double withdrawAmount = scanner.nextDouble();
                        if (ATM.withdraw(user.getUserID(), withdrawAmount)) {
                            System.out.println("Withdrawal successful!");
                        } else {
                            System.out.println("Insufficient balance!");
                        }
                        break;
                    case 3:
                        System.out.print("Enter amount to deposit: ");
                        double depositAmount = scanner.nextDouble();
                        ATM.deposit(user.getUserID(), depositAmount);
                        System.out.println("Deposit successful!");
                        break;
                    case 4:
                        System.out.println("Thank you for using our ATM. Goodbye!");
                        return;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
