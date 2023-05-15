import java.util.*; //Scanner, Arraylist
import java.io.*; //BufferReader
import java.lang.Math;
// Imports for creating graph
import java.lang.reflect.Array;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.stage.Stage;
import javafx.scene.chart.NumberAxis;
import javafx.geometry.Side;
import javafx.scene.chart.XYChart;

// extends Application class so that JavaFX API, methods and properties can be used
public class CustomerSystem extends Application {
    // Array list that can be accessed anywhere in the class
    // Used to pass percentList as a parameter
    private static ArrayList<Double> percentList = new ArrayList<Double>();
    // Overrides existing method in parent class
    @Override
    /**
     * Starts a JavaFX application by creating a bar chart that displays the frequency percentage of digit appearance in a file according to Benford's Law.
     * The X axis displays the first digit of the numbers, and the Y axis displays the frequency percentage which is found through the percentList.
     * @param stage The primary stage for the application, which is used to get the values of percentList
    */
    public void start(Stage stage) {    

        //Defining the axes              
        CategoryAxis xAxis = new CategoryAxis(); 
        xAxis.setCategories(FXCollections.<String>
        observableArrayList(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9")));
        xAxis.setLabel("First Digit");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Frequency Percentage");

        //Creating the Bar chart
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis); 
        barChart.setTitle("Benford's Law Frequency Percentage of Digit Appearance in File");
            
        //Prepare XYChart.Series objects by setting data       
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        Double num1 = percentList.get(0);
        Double num2 = percentList.get(1);
        Double num3 = percentList.get(2);
        Double num4 = percentList.get(3);
        Double num5 = percentList.get(4);
        Double num6 = percentList.get(5);
        Double num7 = percentList.get(6);
        Double num8 = percentList.get(7);
        Double num9 = percentList.get(8);
        // This adds the data into the bar chart
        series1.setName("1 = "+num1+"%\n"+"2 = "+num2+"%\n" +"3 = "+num3+"%\n"+"4 = "+num4+"%\n"+"5 = "+num5+"%\n"+"6 = "+num6+"%\n"+"7 = "+num7+"%\n"+"8 = "+num8+"%\n"+"9 = "+num9+"%");
        series1.getData().add(new XYChart.Data<>("1", num1));
        series1.getData().add(new XYChart.Data<>("2", num2));
        series1.getData().add(new XYChart.Data<>("3", num3));
        series1.getData().add(new XYChart.Data<>("4", num4));
        series1.getData().add(new XYChart.Data<>("5", num5));
        series1.getData().add(new XYChart.Data<>("6", num6));
        series1.getData().add(new XYChart.Data<>("7", num7));
        series1.getData().add(new XYChart.Data<>("8", num8));
        series1.getData().add(new XYChart.Data<>("9", num9));
            
                
        //Setting the data to bar chart       
        barChart.getData().addAll(series1);
        //Creating a Group object 
        Group root = new Group(barChart);
        barChart.setLegendSide(Side.RIGHT);
        
        //Creating a scene object
        Scene scene = new Scene(root, 600, 400);

        //Setting title to the Stage
        stage.setTitle("Bar Chart");
            
        //Adding scene to the stage
        stage.setScene(scene);
            
        //Displaying the contents of the stage
        stage.show();      
    }
    
    /**
     * Displays a menu of options for the customer and sales system.
     * Method is called at the beginning of the program and after a task is completed, while the exit condition is not met 
    */
    public static void printMenu(){

        System.out.println("Customer and Sales System");
        System.out.println("1. Enter Customer Information");
        System.out.println("2. Generate Customer Data");
        System.out.println("3. Append Customer Data to Existing File");
        System.out.println("4. Report on Total Sales");
        System.out.println("5. Check for Fraud in Sales Data");
        System.out.println("9. Quit");
        System.out.println("Enter menu option (1-9)");
    }

    /**
     * Prompts the user to enter customer information in the order of their first name, last name, city, postal code and card number.
     * Validates the postal code and credit card number by calling their respective validation functions (see validatePostalCode() and validateCreditCard()
     * @param id The unique ID of the user. 
     * @param dataSaver list of user datas collected.
     * @return dataSaver A list of strings containing the validated user's data in the following format: "id,firstName,lastName,city,postalCode,creditCardNum"
    */
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

        //If the postal code is less than 3 values or did not pass validation
        while ((postalCode.length() < 3) || (validatePostalCode(postalCode) == false)) { 
            System.out.println("Invalid postal code");
            System.out.println("Please enter a valid postal code (capitalized and minimum 3 characters): ");
            postalCode = reader.nextLine();
            validatePostalCode(postalCode);
        }
         
        // This loop checks if the credit number is valid, if not then user is reprompted

        // We are using the data type long, because int does not support numbers above 9 digits
        long creditCardNum;
        String strCard;

        // do while loop to re prompt user for card number
        do {
            System.out.println("Enter your credit card number (9 or more numbers): ");
            strCard = reader.nextLine();
            // Deletes all non-digits 
            strCard = strCard.replaceAll("[^0-9]", "");
            
            if(!strCard.isEmpty()){
                //Converts string to long data value
                creditCardNum = Long.parseLong(strCard); 
            }
            else{
                //if credit card number is empty, sets the number to 0, which will be rejected
                creditCardNum = 0;
                strCard = "0";
            }
            System.out.println("This credit card number is not valid.");
        } 
        while (strCard.length() < 9 || !validateCreditCard(creditCardNum));
        
        System.out.println("Customer valdiated successfully!");

        // stores and returns customer data into an array
        dataSaver.add(id + "," + firstName + "," + lastName + "," + city + "," + postalCode + "," + creditCardNum); 
        return dataSaver;
    }

    /**
     * Determines whether an inputted postal code is valid by checking whether it is at least three characters long and exists in postal_codes.csv. 
     * If the postal code does not pass validation, the function returns "false" 
     * If the postal code is validated, the function returns "true"
     * @param postalCode The postal code to be validated.
     * @return True if the postal code is valid, False otherwise.
    */
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
        }
        catch (IOException e) { //"catch" id used for error handling IOexceptions, such as file corruption, permissions, accessibility, etc
            e.printStackTrace();
            return false;
        } 
        // Used for error handling
        finally {
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

    /**
     * Reverses credit card number and returns it as a long data type
     * @param cardNum inputted credit card number. Is stored as long as it can be greater than 9 digits
     * @return numberConverted reversed digits stored as a long
    */
    public static long reverseCard(long cardNum){

        // converts cardNum into a string
        String strCardNum = Long.toString(cardNum);
        int[] arr = new int[strCardNum.length()];
        // converts the string into an array
        for (int i = 0; i < strCardNum.length(); i++) {
            arr[i] = strCardNum.charAt(i) - '0';
        }
        // reverses the array and saves it to another array
        int[] rev = new int[strCardNum.length()];
        for (int i = 0; i < strCardNum.length(); i++) {
            rev[i] = arr[strCardNum.length() - i - 1];
        }
        // converts the reversed array of integers back into a long
        long numberConverted = 0;
        for (int number : rev) {
            numberConverted = 10 * numberConverted + number;
        }
        return numberConverted;
    }
    
    /**
     * Calculates the sum of all even digits in a reversed string, where each even digit is doubled 
     * and the sum of the digits in the resulting number is taken if the resulting number is a two-digit number.
     * @param reversed a string in reverse order containing the even digits to be summed
     * @param num an integer representing the current sum of even digits
     * @param i an integer representing the current index of the digit being processed in the string
     * @return evenVal: the int sum of all even digits
    */
    public static int evenSum(String reversed, int num, int i){

        int doubleNum = Character.getNumericValue(reversed.charAt(i+1)) * 2;
        int evenVal = 0;
        // Checks if double num is double digit
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

    /**
     * Calculates the sum of all odd digits in a reversed string
     * @param reversed a string in reverse order containing the odd digits to be summed
     * @param num an integer representing the current sum of odd digits
     * @param i an integer representing the current index of the digit being processed in the string
     * @return oddVal the int sum of all odd digits
    */
    public static int oddSum(String reversed, int num, int i){

        // adds every odd number into oddVal
        int oddVal =  num + Character.getNumericValue(reversed.charAt(i-1));
        return oddVal;
    }

    /**
     * Validates the given credit card number using the Luhn Algorithm.
     * @param num a long integer representing the credit card number to be validated
     * @return True if the credit card number is valid, false otherwise
    */
    public static boolean validateCreditCard(long num){

        long reversedNum = reverseCard(num);
        int odd = 0;
        int even = 0;

        String reversedNumString = Long.toString(reversedNum);
        // calls oddSum for every odd number 
        for (int i = 0; i < reversedNumString.length()+1; i++){
            if (i % 2 == 1){
                odd = oddSum(reversedNumString, odd, i);
            }
        }
        // calls evenSum for every even number 
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

    /**
     * Generates a new CSV file with customer data.
     * @param id The number of customers in the file.
     * @param data The customer data to write to the file.
     * @throws IOException If an error occurs while creating or writing to the file.
    */
    public static void generateCustomerDataFile(int id, ArrayList<String> data){

        Scanner reader = new Scanner(System.in);
        // takes the file name and path for the file the user wants to generate
        System.out.println("Enter new csv file name (without extension): ");
        String fileName = reader.nextLine();
        System.out.println("Enter file location (ex: 'C:\\Users\\username\\Desktop\\'): ");
        String filePath = reader.nextLine();
        File newFile = new File(filePath + fileName + ".csv");

        // checks if the file already exists.
        // if it doesn't, creates the new file
        // if the file already exists, it tells the user 
        try {
            if (newFile.createNewFile()){
                System.out.println("File successfully created: " + newFile.getName());
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
            FileWriter myWriter = new FileWriter(newFile);
            myWriter.write("ID,First Name,Last Name,City,Postal Code,Card Number\n");
            for (int i = 0; i < id; i++) {
                myWriter.write(data.get(i) + "\n");
            }
            myWriter.close();
        }
        catch (IOException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
    }
    
    /**
     * Appends customer data to an existing CSV file.
     * @param id the number of customer data entries to append to the file.
     * @param data an ArrayList of Strings containing customer data in the format
     * @throws IOException if an I/O error occurs while opening or appending to the file.
    */
    public static void appendCustomerFile(int id,  ArrayList<String> data){

        Scanner reader = new Scanner(System.in);
        System.out.println("Enter existing file name (without extensions): ");
        String fileName = reader.nextLine();
        System.out.println("Enter path of existing file (ex: 'C:\\Users\\username\\Desktop\\'): ");
        String fileLocation = reader.nextLine();
        String filePath = fileLocation + fileName + ".csv";
        int newId = getId(data, filePath);
        String holder = "";
        // this generates the IDs for all the new customer info and appends it to the file
        try {
            FileWriter csvwriter = new FileWriter(filePath, true);
            for (int i = 0; i < id; i++) {
                holder = data.get(i);
                char charId = (char)(newId + '0');
                // appends new id as the first element in the row, along with the rest of the customer id
                csvwriter.append(holder.replace(holder.charAt(0), charId) + "\n");
                newId = newId + 1;
            }
            System.out.println("Data successfully appended to " + filePath);
            csvwriter.close();
        }
        catch (IOException e) {
            System.out.println("Error");
            e.printStackTrace(); //prints all issues
        }
    }

    /**
     * Gets the ID for the customer information by counting the number of rows in the existing file. The ID is the number of rows in the file plus one.
     * @return num: An integer representing the ID for the new customer data
    */
    public static int getId(ArrayList<String> usrData, String fileName){

        BufferedReader reader = null;
        String row;
        String holder;
        int num = 0;
        // finds the ID for the customer information by looping through the existing file
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
        return num; //returns new id for customer data
    }

    //End of Luhn Algorithm Functions

    //Start of Benford's Law Functions (graph plotting done at top)

    /**
     * Loops and prompts the user to enter the name and location of a valid sales file. 
     * If the user enters 'D', the default sales file 'sales.csv' is used.
     * @return strFilePath: the file path of the valid sales file as a string.
    */
    public static String validateSalesFile(){

        Scanner reader = new Scanner(System.in);

        Boolean fileFound = false;
        File filePath;
        String fileLocation;
        String strFilePath = "";

        // while loop to reprompt user until fileFound is true
        // fileFound becomes true if the file exists and is valid
        while(fileFound == false){
                System.out.println("Enter the file name (without extension) or enter 'D' to load the default sales file: ");
                String fileName = reader.nextLine();
                if (!fileName.equals("D")){
                    System.out.println("Enter the file location (C:\\Users\\username\\Desktop\\Grade 11 Computer Science\\): ");
                    fileLocation = reader.nextLine();
                    filePath = new File (fileLocation + fileName + ".csv");
                }
                else{
                    filePath = new File ("sales.csv");
                    fileLocation = "";
                    fileName = "sales";
                }
                if (filePath.exists()){
                    fileFound = true;
                }
                else{
                    System.out.println("File not found. Please enter a valid file name and location.");
                }
                strFilePath = fileLocation + fileName + ".csv";

        } 
        return strFilePath;
    }

    /**
     * This method reads in a CSV file at the specified file path and returns an ArrayList of the first digits of each numerical value in the file.
     * @param strFilePath the file path of the CSV file to be read
     * @return arr: ArrayList of the first digits of each numerical value in the file
    */
   public static ArrayList<String> createSalesList(String strFilePath) {

        ArrayList<String> arr = new ArrayList<String>();
        try {
            //initializing buffer reader
            BufferedReader br = new BufferedReader(new FileReader(strFilePath));
            String row;
            while ((row = br.readLine()) != null) {
                String[] numArray = row.split(",");
                for (String numStr : numArray) {
                    try {
                        //used to test if numStr is able to be converted to a Double. If not, non-numerical values are caught and skipped
                        Double numTest = Double.parseDouble(numStr);
                        //appends the first digit of each sale to a list 
                        arr.add(Character.toString(numStr.charAt(0)));
                    } 
                    catch (NumberFormatException e) {
                        // skip non-numeric values
                    }
                }
            }
            System.out.println(arr);
            br.close();
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
        return arr;
    }   

    /**
     * Calculates the frequency of each digit from 1-9 in the given sales list.
     * @param salesList the ArrayList of the first digit of all sales represented as strings 
     * @return frequencyList: the integer ArrayList of frequencies of digits from 1-9
    */
    public static ArrayList<Integer> findFrequencies(ArrayList<String> salesList){

        ArrayList<Integer> frequencyList = new ArrayList<Integer>();
        int digitFrequency;

        for (int i = 0; i < 9; i++) {
            digitFrequency = Collections.frequency(salesList, String.valueOf(i+1));
            frequencyList.add(digitFrequency);
        }

        System.out.println(frequencyList);
        return frequencyList;

    }

    /**
     * Calculates the percentage of each digit in the sales list.
     * @param frequencyList an ArrayList of integers representing the frequency of each digit in the sales list
     * @return percentageList: ArrayList of doubles representing the frequency (percentage) of each digit in the sales list
    */
    public static ArrayList<Double> calculatePercentage(ArrayList<Integer> frequencyList){

        ArrayList<Double> percentageList = new ArrayList<Double>();
        int totalDigits = 0;
        
        // adds up all of the elements in frequencyList to find the total number of digits
        for (int i = 0; i < frequencyList.size(); i++) {
            totalDigits += frequencyList.get(i);
        }

        for (int i = 0; i < frequencyList.size(); i++) {
            double percentage = Math.round(frequencyList.get(i) * 1000.0 / totalDigits) / 10.0;
            percentageList.add(percentage);
        }

        System.out.println(percentageList);
        return percentageList;
    }

    /**
     * This method checks whether or not fraud has occurred by analyzing the percentage of sales that begin with the digit 1.
     * If the percentage falls within the range of 29-32%, fraud is deemed unlikely.
     * If the percentage falls outside of this range, fraud is deemed likely.
     * @param percentageList an ArrayList of percentages representing the frequency of sales that begin with each digit
    */
    public static void checkFraud(ArrayList<Double> percentageList){

        double digit1Percentage = percentageList.get(0);

        if (digit1Percentage >= 29 && digit1Percentage <= 32) {
            System.out.println("\nFraud did not likely occur");
        } 
        else {
            System.out.println("\nFraud likely occured");
        }
    }

    /**
     * Exports the list of percentages to a CSV file named "results.csv"
     * @param percentages an ArrayList of Doubles representing the percentages to be exported
    */
    public static void exportResults(ArrayList<Double> percentages){

        File newFile = new File("results.csv");
        try { newFile.createNewFile(); }
        catch (IOException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
        try {
            FileWriter myWriter = new FileWriter("sales.csv");
            myWriter.write("Digit, Frequency (%)Digit, Frequency (%)");
            for (int i = 0; i < percentages.size(); i++) {
                if (i == percentages.size()-1) {
                    myWriter.write(Double.toString(percentages.get(i)));
                }
                else {
                    myWriter.write(percentages.get(i) + ", ");
                }
            }
            myWriter.close();
        }
        catch (IOException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
    }    

    /**
     * Displays a menu to the user and calls various functions based on user input.
     * The program first initializes empty variables for Luhn Algorithm and Benford's Law.
     * The user can choose to enter customer information, generate customer data file, append to customer data file,
     * report sales data, check for fraud using Benford's Law, and exit the program.
    */
    public static void main(String[] args) {
        
        Scanner reader = new Scanner(System.in);
        boolean preReq = false;
        String userInput = "";
        String enterCustomerOption = "1";
        String createCustomerFile = "2";
        String appendFile = "3";
        String reportSalesOption = "4";
        String checkFraudOption = "5";
        String exitCondition = "9";

        // More variables for the main may be declared in the space below

        //Initializing empty lists for Luhn Algorithm
        ArrayList<String> totalUsrData = new ArrayList<String>();
        ArrayList<String> editedData = new ArrayList<String>();
        int id = 0;

        //Initializing empty lists for Benford's Law
        ArrayList<String> salesList = new ArrayList<String>();
        ArrayList<Integer> frequencyList = new ArrayList<Integer>();
        ArrayList<Double> percentageList = new ArrayList<Double>();

        while (!userInput.equals(exitCondition)){
            printMenu(); // Printing out the main menu
            System.out.println("Enter a number: ");         
            userInput = reader.nextLine(); // User selection from the menu  
              
            if (userInput.equals(enterCustomerOption)){
                id = id + 1;
                totalUsrData = enterCustomerInfo(id, totalUsrData);
            }
            else if (userInput.equals(createCustomerFile)){
                //generates customer data file and clears customer information
                generateCustomerDataFile(id, totalUsrData);
                totalUsrData.clear();
                id = 0;
            }
            else if (userInput.equals(appendFile)){
                //appends customer data file and clears customer information
                appendCustomerFile(id, totalUsrData);
                totalUsrData.clear();
                id = 0;
            }
            else if (userInput.equals(reportSalesOption)){
                //reads sales file to assign values in salesList
                String filePath = validateSalesFile();
                salesList = createSalesList(filePath);
                System.out.println("Sales file data loaded successfully!");
                preReq = true; //allows the user to choose option 5
            }
            else if (userInput.equals(checkFraudOption) && (preReq == true)){ 
                frequencyList = findFrequencies(salesList);
                percentList = calculatePercentage(frequencyList);
                checkFraud(percentList);
                exportResults(percentList);
                //plotting graph
                launch(args); //method in JavaFX that launches the JavaFX application
            }
            else{
                //if prerequisite (completing option 4 before choosing option 5) is not met 
                if(preReq == false){ 
                    System.out.println("Please report sales data first (option 4)");
                }
                else{
                    System.out.println("Please type in a valid option (A number from 1-9)");
                }
            }
        }
        // Exits once the user types 
        System.out.println("Program Terminated");
        reader.close();
    }
} 
