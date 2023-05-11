import java.util.*; //Scanner, Arraylist
import java.io.*; //BufferReader

public class CustomerSystem{
    public static void printMenu(){
        // print menu
        System.out.println("Customer and Sales System");
        System.out.println("1. Enter Customer Information");
        System.out.println("2. Generate Customer data");
        System.out.println("3. Append Customer Data to existing File");
        System.out.println("4. Report on total Sales");
        System.out.println("5. Check for fraud in sales data");
        System.out.println("9. Quit");
        System.out.println("Enter menu option (1-9)");
    }

    public static ArrayList<String> enterCustomerInfo(int id, ArrayList<String> dataSaver){
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
        long creditCardNum = reader.nextLong();
        String strCard = Long.toString(creditCardNum);
        while ((validateCreditCard(creditCardNum) == false) || (strCard.length() < 9)){
            System.out.println("This credit card number is not valid.");
            System.out.println("Enter your credit card number (9 or more numbers): ");
            creditCardNum = reader.nextLong();
            strCard = Long.toString(creditCardNum);
        }
        System.out.println("Successful!");

        dataSaver.add(id + ", " + firstName + ", " + lastName + ", " + city + ", " + postalCode + ", " + creditCardNum); 
        reader.close();
        return dataSaver;
    }

    public static boolean validatePostalCode(String postalCode) {
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

    public static long reverseCard(long cardNum){
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

    public static boolean validateCreditCard(long num){
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
            return false;
        }
    }

    public static void generateCustomerDataFile(int id, ArrayList<String> data){
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
            FileWriter myWriter = new FileWriter(filePath + "\\" + fileName + ".csv");
            for (int i = 0; i < id; i++) {
                myWriter.write(data.get(i) + "\n");
            }
            myWriter.close();
            System.out.println("successfully wrote");
        }
        catch (IOException e) {
            System.out.println("Error");
            e.printStackTrace();
        }

        reader.close();
    }
    
    public static void appendCustomerFile(int id,  ArrayList<String> data){
        Scanner reader = new Scanner(System.in);
        System.out.println("What is the name and path of the existing file? ");
        String file = reader.nextLine();
        int newId = getId(data, file);
        String holder = "";
        try {
            FileWriter csvwriter = new FileWriter(file, true);
            for (int i = 0; i < id; i++) {
                holder = data.get(i);
                char charId = (char)(newId + '0');
                csvwriter.append(holder.replace(holder.charAt(0), charId) + "\n");
                newId = newId + 1;
            }
            csvwriter.close();
        }
        catch (IOException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
        reader.close();
    }

    public static int getId(ArrayList<String> usrData, String fileName){
        BufferedReader reader = null;
        String row;
        String holder;
        int num = 0;
        try {
            reader = new BufferedReader(new FileReader(fileName));
            while ((row = reader.readLine()) != null) {
                num = num + 1;
            }
        }
        catch (IOException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
        num = num + 1;
        return num;
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
        String createCustomerFile = "2";
        String appendFile = "3";
        String reportSalesOption = "4";
        String checkFraudOption = "5";
        String exitCondition = "9";

        // More variables for the main may be declared in the space below

        ArrayList<String> totalUsrData = new ArrayList<String>();
        ArrayList<String> editedData = new ArrayList<String>();
        int id = 0;
        while (!userInput.equals(exitCondition)){
            printMenu(); // Printing out the main menu
            System.out.println("Enter a number: ");         
            userInput = reader.nextLine(); // User selection from the menu    
            if (userInput.equals(enterCustomerOption)){
                // Only the line below may be editted based on the parameter list and how you design the method return
                // Any necessary variables may be added to this if section, but nowhere else in the code
                id = id + 1;
                totalUsrData = enterCustomerInfo(id, totalUsrData);
            }
            else if (userInput.equals(createCustomerFile)){
                // Only the line below may be editted based on the parameter list and how you design the method return
                System.out.println(totalUsrData);
                generateCustomerDataFile(id, totalUsrData);
                totalUsrData.clear();
            }
            else if (userInput.equals(appendFile)){
                appendCustomerFile(id, totalUsrData);
                totalUsrData.clear();
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
        System.out.println("Program Terminated");
        reader.close();
    }
} 
