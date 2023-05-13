import java.util.*; //Scanner, Arraylist
import java.io.*; //BufferReader
import java.lang.Math;
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

public class CustomerSystem extends Application {
    private static ArrayList<Double> percentList = new ArrayList<Double>();
    @Override
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
    
    public static void printMenu(){
        // print menu
        System.out.println("Customer and Sales System");
        System.out.println("1. Enter Customer Information");
        System.out.println("2. Generate Customer Data");
        System.out.println("3. Append Customer Data to Existing File");
        System.out.println("4. Report on Total Sales");
        System.out.println("5. Check for Fraud in Sales Data");
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

        long creditCardNum;
        String strCard;

        do {
            System.out.println("Enter your credit card number (9 or more numbers): ");
            strCard = reader.nextLine();
            strCard = strCard.replaceAll("[^0-9]", ""); // Deletes all non-digits 
            
            if(!strCard.isEmpty()){
                creditCardNum = Long.parseLong(strCard); //Converts string to long data value
            }
            else{
                creditCardNum = 0;
                strCard = "0";
            }

            if (strCard.length() < 9 || !validateCreditCard(creditCardNum)) {
                System.out.println("This credit card number is not valid.");
            }

        } 
        while (strCard.length() < 9 || !validateCreditCard(creditCardNum));
        
        System.out.println("Customer valdiated successfully!");

        dataSaver.add(id + "," + firstName + "," + lastName + "," + city + "," + postalCode + "," + creditCardNum); 
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
        }
        catch (IOException e) { //"catch" id used for error handling IOexceptions, such as file corruption, permissions, accessibility, etc
            e.printStackTrace();
            return false;
        } 
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
        System.out.println("Enter new csv file name (without extension): ");
        String fileName = reader.nextLine();
        System.out.println("Enter file location (ex: 'C:\\Users\\username\\Desktop\\'): ");
        String filePath = reader.nextLine();
        File newFile = new File(filePath + fileName + ".csv");

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
    
    public static void appendCustomerFile(int id,  ArrayList<String> data){
        Scanner reader = new Scanner(System.in);
        System.out.println("Enter existing file name (without extensions): ");
        String fileName = reader.nextLine();
        System.out.println("Enter path of existing file (ex: 'C:\\Users\\username\\Desktop\\'): ");
        String fileLocation = reader.nextLine();
        String filePath = fileLocation + fileName + ".csv";
        int newId = getId(data, filePath);
        String holder = "";
        try {
            FileWriter csvwriter = new FileWriter(filePath, true);
            for (int i = 0; i < id; i++) {
                holder = data.get(i);
                char charId = (char)(newId + '0');
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

    //End of Luhn Algorithm Functions

    //Start of Benford's Law Functions (minus graph plotting done at top)

    public static String validateSalesFile(){
        Scanner reader = new Scanner(System.in);

        Boolean fileFound = false;
        File filePath;
        String fileLocation;
        String strFilePath = "";

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
    

   public static ArrayList<String> createSalesList(String strFilePath) {   
    ArrayList<String> arr = new ArrayList<String>();
    try {
        BufferedReader br = new BufferedReader(new FileReader(strFilePath));
        String row;
        while ((row = br.readLine()) != null) {
            String[] numArray = row.split(",");
            for (String numStr : numArray) {
                try {
                    Double num = Double.parseDouble(numStr); //used to test if numStr is able to be converted to a Double. If not, non-numerical values are caught and skipped
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

    public static void checkFraud(ArrayList<Double> percentageList){

        double digit1Percentage = percentageList.get(0);

        if (digit1Percentage >= 29 && digit1Percentage <= 32) {
            System.out.println("\nFraud did not likely occur");
        } 
        else {
            System.out.println("\nFraud likely occured");
        }
    }
    
    public static void exportResults(ArrayList<Double> percentages){
        File newFile = new File("results.csv");
        try { newFile.createNewFile(); }
        catch (IOException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
        try {
            FileWriter myWriter = new FileWriter("sales.csv");
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
                // Only the line below may be editted based on the parameter list and how you design the method return
                // Any necessary variables may be added to this if section, but nowhere else in the code
                id = id + 1;
                totalUsrData = enterCustomerInfo(id, totalUsrData);
            }
            else if (userInput.equals(createCustomerFile)){
                // Only the line below may be editted based on the parameter list and how you design the method return
                generateCustomerDataFile(id, totalUsrData);
                totalUsrData.clear();
                id = 0;
            }
            else if (userInput.equals(appendFile)){
                appendCustomerFile(id, totalUsrData);
                totalUsrData.clear();
                id = 0;
            }
            else if (userInput.equals(reportSalesOption)){
                String filePath = validateSalesFile();
                salesList = createSalesList(filePath);
                System.out.println("Sales file data loaded successfully!");
                preReq = true; //Allows the user to c
            }
            else if (userInput.equals(checkFraudOption) && (preReq == true)){ 
                frequencyList = findFrequencies(salesList);
                percentageList = calculatePercentage(frequencyList);
                percentList = calculatePercentage(frequencyList);
                //plot graph here
                checkFraud(percentList);
                exportResults(percentList);
                launch(args);
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
