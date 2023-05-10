import java.util.*; //Scanner, Arraylist
import java.io.*; //BufferReader

public class CustomerSystem{
    public static void printMenu(){
        // print menu
        System.out.println("Customer and Sales System");
        System.out.println("1. Enter Customer Information");
        System.out.println("2. Generate Customer data");
        System.out.println("3. Report on total Sales");
        System.out.println("4. Check for fraud in sales data");
        System.out.println("9. Quit");
        System.out.println("Enter menu option (1-9)");
    }

    public static String enterCustomerInfo(){
        // get user input firstname lastname city
        Scanner reader = new Scanner(System.in);
        System.out.print("Enter your first name: ");
        String firstName = reader.nextLine();
        System.out.print("Enter your last name: ");
        String lastName = reader.nextLine();
        System.out.print("Enter your city: ");
        String city = reader.nextLine();
        System.out.print("Enter your postal code (3 or more characters): ");
        String postalCode = reader.nextLine();

        while ((postalCode.length() < 3) || (validatePostalCode(postalCode) == false)) { //If the postal code is less than 3 values or did not pass validation
            System.out.println("Invalid postal code");
            System.out.println("Please enter a valid postal code (capitalized and minimum 3 characters): ");
            postalCode = reader.nextLine();
            validatePostalCode(postalCode);
        }
         
        // This loop checks if the credit number is valid, if not then user is reprompted
        System.out.println("Enter your credit card number (9 or more numbers): ");
        int creditCardNum = reader.nextInt();
        while ((postalCode.length() < 3) || (validatePostalCode(postalCode) == false)){
            System.out.println("This postal code is not a valid postal code.");
            System.out.println("Enter your credit card number (9 or more numbers): ");
            creditCardNum = reader.nextInt();
        }
        int id = getId();
        String dataSaver = id + ", " + firstName + ", " + lastName + ", " + city + ", " + postalCode + ", " + creditCardNum + "|";
        return dataSaver;
    }

    public static boolean validatePostalCode(String postalCode) {
        if (postalCode.length() < 3) {
            return false; // This reprompts the user and validates their new input in the loop
        }
        BufferedReader csvReader = null;
        try {
            csvReader = new BufferedReader(new FileReader("postal_codes.csv"));
            String row;
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split("\\|"); //delimiter
                if (data[0].equals(postalCode.substring(0, 3))) { // Checks to see if one of the postal code prefixes match with the first 3 characters of the input
                    return true; // This breaks out of the while loop and continues to the next section of the enterCustomerInfo() function
                }
            }
        } catch (IOException e) { //"catch" id used for error handling IOexceptions, such as file corruption, permissions, accessibility, etc
            e.printStackTrace();
            return false;
        } finally {
            if (csvReader != null) {
                try {
                    csvReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public static long reverseCard(int cardNum){
        String strCardNum = Long.toString(cardNum);
        int[] arr = new int[strCardNum.length()];
        for (int i = 0; i < strCardNum.length(); i++) {
            arr[i] = strCardNum.charAt(i) - '0';
        }
        int[] rev = new int[strCardNum.length()];
        for (int i = 0; i < strCardNum.length(); i++) {
            rev[i] = arr[strCardNum.length() - i - 1];
        }
        long numberConverted = 0;
        for (int number : rev) {
            numberConverted = 10 * numberConverted + number;
        }
        return numberConverted;
    }
    
    public static int evenSum(String reversed, int num, int i){
        int doubleNum = Character.getNumericValue(reversed.charAt(i+1)) * 2;
        int evenVal = 0;

        if (doubleNum > 9){
                String strDoubleNum = Integer.toString(doubleNum);
                int sumTwoDigits = Character.getNumericValue(strDoubleNum.charAt(0)) + Character.getNumericValue(strDoubleNum.charAt(1));
                evenVal = num + sumTwoDigits;
        }
        // If the doubled value is a single digit, I add it directly to evenSum (i.e. sum2)
        else {
                evenVal = num + doubleNum;
        }
        return evenVal;
    }

    public static int oddSum(String reversed, int num, int i){
        int oddVal =  num + Character.getNumericValue(reversed.charAt(i-1));
        return oddVal;
    }

    public static boolean validateCreditCard(int num){
        long reversedNum = reverseCard(num);
        int odd = 0;
        int even = 0;

        String reversedNumString = Long.toString(reversedNum);
        for (int i = 0; i < reversedNumString.length()+1; i++){
            if (i % 2 == 1){
                odd = oddSum(reversedNumString, odd, i);
            }
        }
        for (int j = 0; j < reversedNumString.length()-1; j++){
            if (j % 2 == 0){
                even = evenSum(reversedNumString, even, j);
            }
        }
        int finalSum = odd + even;
        // Checks whether oddSum + evenSum ends with a 0, if so then return True
        if (finalSum % 10 == 0){
            return true;
        }
        // If Luhn Algorithm doesn't satisfy, return False
        else{
            System.out.println("This Credit Card number is not valid.");
            return false;
        }
    }

    public static void generateCustomerDataFile(String totalUsrData){
        Scanner reader = new Scanner(System.in);
        System.out.println("What would you like to call your file? ");
        String fileName;
        String filePath;
        fileName = reader.nextLine();
        System.out.println("path (ex: 'C:\\Users\\username\\Desktop\\Grade 11 Computer Science\\'): ");
        filePath = reader.nextLine();

        try {
            File newFile = new File(filePath + fileName + ".csv");
            if (newFile.createNewFile()){
                System.out.println("File created: " + newFile.getName());
            }
            else {
                System.out.println("File already exists.");
            }
        }
        catch (IOException e) {
            System.out.println("Error");
            e.printStackTrace();
        }

        try {
            FileWriter myWriter = new FileWriter(filePath + fileName + ".csv");
            myWriter.write(totalUsrData);
            myWriter.close();
            System.out.println("sucesfuly wrote");
        }
        catch (IOException e) {
            System.out.println("Error");
            e.printStackTrace();
        }

        reader.close();
    }
    

    public static int getId(){
        int i = 0;
        return i;
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
            String totalUsrData = "";
            printMenu(); // Printing out the main menu
            System.out.println("Enter a number: ");          
            userInput = reader.nextLine(); // User selection from the menu  
             

            if (userInput.equals(enterCustomerOption)){
                // Only the line below may be editted based on the parameter list and how you design the method return
                // Any necessary variables may be added to this if section, but nowhere else in the code
                totalUsrData = enterCustomerInfo();
            }
            else if (userInput.equals(generateCustomerOption)){
                // Only the line below may be editted based on the parameter list and how you design the method return
                generateCustomerDataFile(totalUsrData);
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
        }
        // Exits once the user types
        reader.close(); 
        System.out.println("Program Terminated");
    }
} 
