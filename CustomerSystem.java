import java.util.Scanner;
import java.util.ArrayList;
public class CustomerSystem{
    public static void printMenu(){
        // print menu
    }

    public static void enterCustomerInfo(){
        // get user input firstname lastname city
    }

    public static void validatePostalCode(){

    }

    public static void reverseCard(){

    }
    
    public static void evenSum(){

    }

    public static void oddSum(){

    }

    public static void validateCreditCard(){

    }

    public static void generateCustomerDataFile(){

    }

    public static void getId(){

    }

    public static void writeFile(){
        // when creating, overwrites file if it alreadty exists
        // when appending, adds to existing file
    }

    //End of Luhn Algorithm functions

    //Start of Benford's Law

    public static String validateSalesFile(){
        return null;
    }

    public static ArrayList createSalesList(){
        return null;
    }

    public static void findFrequencies(){

    }

    public static void calculatePercentage(){

    }

    public static void plotGraph(){

    }

    public static void checkFraud(){

    }
    
    public static void exportResults(){
        
    }

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        String userInput = "";
        String enterCustomerOption = "1";
        String generateCustomerOption = "2";
        String reportSalesOption = "3";
        String checkFraudOption = "4";
        String exitCondition = "9";
        int number = 0;

        // More variables for the main may be declared in the space below

        while (!userInput.equals(exitCondition)){
            printMenu(); // Printing out the main menu
            System.out.println("Enter a number: ");          
            userInput = reader.nextLine(); // User selection from the menu    

            if (userInput.equals(enterCustomerOption)){
                // Only the line below may be editted based on the parameter list and how you design the method return
                // Any necessary variables may be added to this if section, but nowhere else in the code
                enterCustomerInfo();
            }
            else if (userInput.equals(generateCustomerOption)){
                // Only the line below may be editted based on the parameter list and how you design the method return
                generateCustomerDataFile();
            }
            else if (userInput.equals(reportSalesOption)){
                String filePath = validateSalesFile();
                ArrayList<String> salesList = new ArrayList<String>();
                salesList = createSalesList();
            }
            else if (userInput.equals(checkFraudOption)){ 
                checkFraud();
            }
            else{
                System.out.println("Please type in a valid option (A number from 1-9)");
                userInput = reader.nextLine(); 
            }

        // Exits once the user types 
        System.out.println("Program Terminated");
        }
    }
} 
