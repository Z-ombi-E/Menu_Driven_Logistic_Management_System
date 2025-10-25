import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu_Driven_Logistic_Management_System {
    public static Scanner scanner = new Scanner(System.in);
    public static final int MAX_CITIES = 31;
    public static String filePathCities = "Cities.txt";
    private static final String [] Cities = new String[MAX_CITIES];
    private static int cityCount = 0;
    public static String filePathDistance = "Distance.txt";
    public static  String[][] intercityDistance = new String[MAX_CITIES][MAX_CITIES];
    public static List<String> cityNames;
    public static final int VEHICLE_TYPES = 3;
    public static final String[] VEHICLE_NAMES = {"Van", "Truck", "Lorry"};
    public static final int[] VEHICLE_CAPACITIES = {1000, 5000, 10000};
    public static final int[] VEHICLE_RATES = {30, 40, 80};
    public static final int[] VEHICLE_SPEEDS = {60, 50, 45};
    public static final int[] VEHICLE_FUEL_EFFICIENCY = {12, 6, 4};
    public static final double FUEL_PRICE = 400.0;
    public static final double PROFIT_MARKUP = 0.25;
    public static List<String> displayCityNames;
    //for calculations
    public static int currentSourceIndex = -1;
    public static int currentDestinationIndex = -1;
    public static int currentDistance = -1;
    public static int currentWeight = -1;
    public static int currentVehicleType = -1;
    public static List<String> currentCityNames = null;



    // Delivery Records
    public static final int MAX_DELIVERIES = 50;
    public static String[] deliveryRecords = new String[MAX_DELIVERIES];
    public static int deliveryCount = 0;

    public static void main(String[] args) {
        int mainChoice;
        String name;
        System.out.print("Please Enter Your Name Before Using Our System😊😊:  ");
        name = scanner.nextLine();
        String cappedName = capitalizeFirstLetter(name);

        do {
            System.out.println("Hello There "+ cappedName+ " !!!");
            System.out.println("Welcome To Our Menu Driven Logistic Management System..\n");
            System.out.println("Press 1 to Enter City Management.. ");
            System.out.println("Press 2 to Enter Distance Management.. ");
            System.out.println("Press 3 to Enter Vehicle Management.. ");
            System.out.println("Press 4 to Enter Delivery Request Handling And Calculations And The Sample OUTPUT.. ");
            System.out.println("Press 5 to Enter To Find The Least-Cost Route (Least-Distance) .. ");
            System.out.println("Press 6 to Enter To Get Reports.. ");
            System.out.println("Press 0 to EXIT....");
            System.out.print("Enter Your Choice: ");
            mainChoice = scanner.nextInt();
            scanner.nextLine();

            switch (mainChoice){
                case 1-> cityManagement ();
                case 2-> distanceManagement();
                case 3-> vehicleManagement();
                case 4-> deliveryRequestHandlingCalculations();
                case 5-> leastDistance();
                case 6-> performanceReports();
                case 0-> System.out.println("See You Again! Thank You for Using Our System!😊😊");
                default -> System.out.println("Enter a VALID Choice!!");
            }



        }while (mainChoice!=0);
    }




    //CITY MANAGEMENT
    private static void cityManagement() {
        int choice;

        do {
            System.out.println("____City Management____");
            System.out.println("Make sure to ADD cities before rename, remove or display cities.");
            System.out.println("Press 1 to Add New City..");
            System.out.println("Press 2 to Rename City..");
            System.out.println("Press 3 to Remove City..");
            System.out.println("Press 4 to Display Entered Cities..");
            System.out.println("Press 0 to Exit..");
            System.out.print("Enter Your Choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();


            switch (choice){
                case 1 -> addCity();
                case 2 -> renameCity();
                case 3 -> removeCity();
                case 4 -> displayCityManagement();
                case 0 -> System.out.println("Returning To Main Menu.....\n");
                default -> System.out.println("Invalid Choice!");
            }
        }while(choice!=0);
    }
    //ADD CITIES
    private static void addCity(){
        //read file
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePathCities));
            cityCount = Math.min(lines.size(), MAX_CITIES-1);


            //copy from the file to array

            for (int i = 0; i <cityCount ; i++) {
                Cities[i] = lines.get(i);

            }
            System.out.println("Number of Cities existed: "+cityCount);
            if (cityCount>0){
                System.out.println("Existing Cities:");
                for (int i = 0; i <cityCount; i++){
                    System.out.println((i+1)+ ". "+ Cities[i]);
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not Found...");

        }catch (IOException e){
            System.out.println("Something went wrong!");
            cityCount = 0;
        }

        if (cityCount >= MAX_CITIES-1){
            System.out.println("Already Reached the limit!");
            return;
        }
//begin to add
        System.out.println("Enter Cities---    You can add up to "+(MAX_CITIES-cityCount)+" more cities.");
        System.out.println("If you want to stop, type 'stop'");
        for (int i = cityCount; i < MAX_CITIES ; i++) {
            System.out.print("Enter City "+(i+1)+": ");
            String city = scanner.nextLine().trim().toLowerCase();


            //stop
            if (city.equalsIgnoreCase("stop")){
                System.out.println("To main menu...");
                return;
            }
            //empty
            if (city.isEmpty()){
                System.out.println("Cannot be empty!");
                i--;
                continue;
            }
            String capitalizedCity = capitalizeFirstLetter(city);

            if(isCityExists(capitalizedCity)){
                System.out.println("City Already Exists!");
                i--;
                continue;


            }else {
                Cities[i] = capitalizedCity;
                cityCount++;
            }



            System.out.println(capitalizedCity+" Added Successfully!");

            //continue?

            if (cityCount<MAX_CITIES){
                System.out.print("Add another city? (Press Enter to continue or type 'Stop' to finish): ");
                String addAnotherCityNoCaps = scanner.nextLine().trim().toLowerCase();

                if (addAnotherCityNoCaps.equalsIgnoreCase("stop")){
                    System.out.println("To main menu...");
                    saveToFile();
                    return;
                }
            }else {
                System.out.println(MAX_CITIES+" Cities Reached!");
            }


        }

        saveToFile();

    }
    //City existence check
    private static boolean isCityExists(String capitalizedCity) {
        for (int i = 0; i <cityCount; i++) {
            if (Cities[i] != null && Cities[i].equalsIgnoreCase(capitalizedCity)){
                return true;
            }
        }
        return false;
    }
    //RENAME CITIES
    private static void renameCity(){
        if (cityCount == 0){
            System.out.println("There ara no cities to rename, Please enter cities First!");
            return;
        }

        displayCityManagement();


        while (true){
            System.out.print("Enter City Number to Rename (type 'Stop' to if you go back to main menu): ");
            String cityIndexString = scanner.nextLine().trim().toLowerCase();

            if (cityIndexString.equalsIgnoreCase("stop")){
                System.out.println("Returning to Main Menu...");
                return;
            }

            try{
                int cityIndexInt = Integer.parseInt(cityIndexString);
                if (cityIndexInt>0 && cityIndexInt<=cityCount){
                    String oldCityName = Cities[cityIndexInt-1];

                    while (true){
                        System.out.print("Enter New Name for "+ Cities[cityIndexInt-1]+ "(or 'Stop' to return to main menu): ");
                        String newCityName = scanner.nextLine().trim().toLowerCase();



                        if (newCityName.equalsIgnoreCase("stop")){
                            System.out.println("Returning to Main Menu...");
                            return;
                        }
                        if (newCityName.isEmpty()){
                            System.out.println("Cannot be empty..");
                            continue;
                        }

                        //check the new name is same as old one
                        String capitalizedNewCity = capitalizeFirstLetter(newCityName);
                        if (capitalizedNewCity.equalsIgnoreCase(oldCityName)){
                            System.out.println("It's the same name");
                            continue;
                        }

                        //already existed?

                        if (isCityExists(capitalizedNewCity) && !Cities[cityIndexInt-1].equalsIgnoreCase(capitalizedNewCity)){
                            System.out.println("Entered City Already Exists, Try a different name..");
                            continue;
                        }


                        Cities[cityIndexInt-1] = capitalizedNewCity;


                        System.out.println(oldCityName +" renamed to "+capitalizedNewCity);
                        saveToFile();
                        return;

                    }

                }else {
                    System.out.println("Invalid City Number!");
                }


            }catch (NumberFormatException e){
                System.out.println("Please Enter A Valid Number!");
            }
        }



    }
    //REMOVE CITIES
    private static void removeCity(){
        if (cityCount==0){
            System.out.println("Please Enter at least a city Before REMOVE!");
            return;
        }
        displayCityManagement();

        while (true){
            System.out.print("Enter the city number to REMOVE (or 'stop' to return to MAIN MENU): ");
            String wannaRemoveCityStr = scanner.nextLine().trim().toLowerCase();

            if (wannaRemoveCityStr.equalsIgnoreCase("stop")){
                System.out.println("Returning to MAIN MENU...");
                return;
            }
            try {
                int wannaRemoveCityInt = Integer.parseInt(wannaRemoveCityStr);

                if (wannaRemoveCityInt >0 && wannaRemoveCityInt <= cityCount){
                    String wannaRemoveCity = Cities[wannaRemoveCityInt-1];


                    //shifting the gap for removed city
                    for (int i = (wannaRemoveCityInt-1); i <(cityCount-1) ; i++) {

                        Cities[i] = Cities[i+1];

                    }
                    Cities[wannaRemoveCityInt-1]=null;
                    cityCount--;

                    System.out.println(wannaRemoveCity +" removed successfully!");
                    saveToFile();


                }else {
                    System.out.println("Invalid City Number!");
                }
            }catch (NumberFormatException e){
                System.out.println("Please Enter A Valid Number!");
            }


        }

    }
    private static void displayCityManagement(){
     //        if (cityCount==0){
//            System.out.println("Please Enter Cities First!");
//            return;
//        }
//        try (BufferedReader reader = new BufferedReader(new FileReader(filePathCities))){
//
//                String line;
//                while ((line = reader.readLine()) != null){
//                    System.out.println(line);
//                }
//
//
//            //System.out.println("That File exists.");
//
//        }catch (FileNotFoundException e){
//            System.out.println("Could not locate file.");
//        }catch (IOException e){
//            System.out.println("Something Went Wrong.");
//        }

//        System.out.println("____Available Cities____");
//        for (int i = 0; i < cityCount; i++) {
//            System.out.println(((i+1) +". " + Cities[i]));
//
//        }
//        System.out.println("Total Cities: " +cityCount+ "\n");
        try{

            List<String> displayCityNames = Files.readAllLines(Paths.get(filePathCities));
            cityCount = Math.min(displayCityNames.size(), MAX_CITIES);
            System.out.println("\n\n____Current Cities____");

            for (int i = 0; i < cityCount; i++) {
                int cityID = (i+1);
                String city = displayCityNames.get(i);
                System.out.printf("%d. %-5s \n",cityID,city);

            }

        }catch (FileNotFoundException e){
            System.out.println("Could not locate file.");
        }catch (IOException e){
            System.out.println("Something Went Wrong.");
        }


    }
    //saving cities to Cities.txt line by line
    private static void saveToFile(){
        try{
            java.util.List<String> saveCities = new ArrayList<>();
            for (int i = 0; i <cityCount ; i++) {
                if (Cities[i] != null) {
                    saveCities.add(Cities[i]);
                }

            }
//            System.out.println("Attempting to save " + saveCities.size() + " cities to: " + filePath);
//            System.out.println("Cities to save: " + saveCities);
            Files.write(Paths.get(filePathCities), saveCities);
            System.out.println("Saved Successfully!");

        }catch (IOException e){
            System.out.println("Something went Wrong!" + e.getMessage());
        }
    }
    //capitalize first letter of each city
    public static String capitalizeFirstLetter(String name){
        if (name == null || name.isEmpty()){
            return name;
        }
        return name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();

    }

//DISTANCE MANAGEMENT
    private static void distanceManagement() {
        displayCities();
        loadExistingDis();

        int choice;

        do {
            System.out.println("___Intercity Distance Management___");
            System.out.println("Press 1 to Add/Edit Intercity Distances..");
            System.out.println("Press 2 to display Current distances...");
            System.out.println("Press 0 to go back to Main Menu...");
            System.out.print("Enter Your Choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice){
                case 1-> addIntercityDis();
                case 2-> displayIntercityDis();
                case 0-> System.out.println("Returning..");
                default -> System.out.println("Enter a valid number!");
            }
        }while (choice!=0);
    }
    //add intercity distances
    private static void addIntercityDis() {
        System.out.println("Add Distance Between Two Cities");
//Display Entered Cities
        System.out.println("Available Cities");
        for (int i = 0; i <cityCount ; i++) {
            System.out.printf("C%d - %s\n",(i+1), cityNames.get(i));
        }

        //For Same City To Itself Distance should be ZERO


        for (int i = 1; i <cityCount ; i++) {
            if (intercityDistance[i][i]==null){
                intercityDistance[i][i]="0 km";
                //save self distances
                try(FileWriter fileWriter = new FileWriter(filePathDistance,true)){
                    fileWriter.write(String.format("C%d,C%d,0 km%n",i,i));

                }catch(IOException e){
                    System.out.println("File Not Found!"+e.getMessage());
                }
            }
        }

        //get city1 index
        int cityIndex1 = getCityIndex("Enter First City ID: ");
        if (cityIndex1==-1){
            return;
        }
        //get city2 index
        int cityIndex2 = getCityIndex("Enter Second City ID: ");
        if (cityIndex2==-1){
            return;
        }
        //same city distance check
        if (cityIndex1==cityIndex2){
            System.out.println("Distance From "+cityNames.get(cityIndex1)+" to "+ cityNames.get(cityIndex2)+" is 0 km");
            intercityDistance[cityIndex1+1][cityIndex2+1] = "0 km";
            saveDistance(cityIndex1,cityIndex2,"0 km");
            return;
        }

        //add distance
        while (true){
            System.out.print("Enter Distance Between "+cityNames.get(cityIndex1)+" and "+cityNames.get(cityIndex2)+" (to closest decimal point in km) ");
            String interCityDisSTR= scanner.nextLine().trim();

            try {
                int interCityDisINT = Integer.parseInt(interCityDisSTR);
                if (interCityDisINT<0){
                    System.out.println("Please Enter A Positive Number!");
                    continue;
                }

                String  disWithUnit = interCityDisSTR + " km";


                //validating symmetric positions
                intercityDistance[cityIndex1+1][cityIndex2+1] = disWithUnit;
                intercityDistance[cityIndex2+1][cityIndex1+1] = disWithUnit;
                saveDistance(cityIndex1,cityIndex2,disWithUnit);

                System.out.println("Distance Added Successfully!");
                break;
            }catch (NumberFormatException e){
                System.out.println("Invalid Distance! Please Only use Numbers!");
            }
        }
    }
    //Display Current entered distances
    private static void displayIntercityDis() {

        String format = "%-10s";
        System.out.println("Current Entered Intercity Distances..");
        //header
        System.out.printf("%-10s","");
        for (int i = 1; i <= cityCount ; i++) {
            System.out.printf(format,intercityDistance[0][i]);
        }
        System.out.println();

        //data rows
        for (int i = 1; i <= cityCount ; i++) {
            System.out.printf(format,intercityDistance[i][0]);
            for (int j = 1; j <=cityCount ; j++) {
                String distance = (intercityDistance[i][j] == null) ? "---" : intercityDistance[i][j];
                System.out.printf(format,distance);

            }
            System.out.println();


        }
    }
    //Display Cities By Reading the file
    public static void displayCities(){
        try {
            //System.out.println("That File exists.");
//            String line;
//            while ((line = reader.readLine()) != null){
//                System.out.println(line);
//            }
            cityNames = Files.readAllLines(Paths.get(filePathCities));
            cityCount = Math.min(cityNames.size(),(MAX_CITIES));
//Assigning a unique ID to cities..C1,C2,C3,............
            for (int i = 0; i <cityCount ; i++) {

//                String city = cityNames.get(i);
                String cityID = "C"+(i+1);
                intercityDistance[i+1][0] = cityID;
                intercityDistance[0][i+1] = cityID;
            }
//            System.out.println("Cities with IDs...");
//
//            for (String[] row : intercityDistance) {
//                for (String city : row){
//                    if (city==null){
//                        System.out.printf("%-15s","___");
//                    }else if (city.startsWith("C")){
//                        System.out.printf("%-15s",city);
//                    }
//
//                }
//                System.out.println();
//            }

            System.out.println("\n\n____City IDs for Cities____");

            for (int i = 0; i < cityCount; i++) {
                String cityID = "C"+(i+1);
                String city = cityNames.get(i);
                System.out.printf("%s %-5s \n",cityID,city);

            }

        }catch (FileNotFoundException e){
            System.out.println("Could not locate file.");
        }catch (IOException e){
            System.out.println("Something Went Wrong.");
        }

    }
    //get index of the city
    public static int getCityIndex(String userInput){
        while (true){
            System.out.print(userInput);
            String cityId = scanner.nextLine().trim().toUpperCase();

            if (cityId.equalsIgnoreCase("STOP")){
                return -1;
            }

            if (!cityId.matches("C\\d+")){
                System.out.println("Invalid Format! Use C1, C2, C3,....");
                continue;
            }

            try {
                int cityIndex = Integer.parseInt(cityId.substring(1))-1;
                if (cityIndex>=0 && cityIndex<cityCount){
                    return cityIndex;
                }else {
                    System.out.println("City ID not Found.."+" (Available C1 to C"+cityCount+")");
                }
            }catch (NumberFormatException e){
                System.out.println("Enter A Valid City ID!");
            }
        }

    }
    //save entered distances to a file
    public static void saveDistance(int cityIndex1, int cityIndex2, String distance) {
        try (FileWriter fileWriter = new FileWriter(filePathDistance,true)){
            //write city ids and distance to the file
            String cityRecords = String.format("C%d,C%d,%s%n",(cityIndex1+1),(cityIndex2+1),distance);
            fileWriter.write(cityRecords);
            System.out.println("Distance Saved Successfully!");


        }catch (IOException e){
            System.out.println("File Destination Couldn't Found!");

        }


    }
    //load existing intercity distances to the array before the program runs
    public static void loadExistingDis(){
        try{
            if (!Files.exists(Paths.get(filePathDistance))){
                System.out.println("File Not Found!");
                return;
            }

            List<String> lines = Files.readAllLines(Paths.get(filePathDistance));
            for (String line : lines){
                line= line.trim();
                if (line.isEmpty()){
                    continue;
                }

                //parsing city ids and distances

                String[] parts = line.split(",");
                if (parts.length==3){
                    String city1ID = parts[0];
                    String city2ID = parts[1];
                    String distance = parts[2];

                    //Extract City Numbers
                    int city1 = Integer.parseInt(city1ID.substring(1))-1;
                    int city2 = Integer.parseInt(city2ID.substring(1))-1;

                    //updating with the existing cities
                    if ((city1>0 && city1<cityCount) && (city2>0 && city2<cityCount)){
                        intercityDistance[city1+1][city2+1] = distance;
                        intercityDistance[city2+1][city1+1] = distance;
                    }

                }
            }
            System.out.println("Existing City Distances Were Added!");

        }catch (IOException e){
            System.out.println("File Not Found!" + e.getMessage());
        }catch (NumberFormatException e){
            System.out.println("Some Data maybe corrupted or Invalid data type!");
        }
    }


//VEHICLE MANAGEMENT
    private static void vehicleManagement() {
        int choice;
        do {
            System.out.println("___Vehicle Management___");
            System.out.println("Press 1 to Display Available Vehicle Types with All Details..");
            System.out.println("Press 0 to Return to Main Menu..");
            System.out.print("Enter Choice: ");
            choice = scanner.nextInt();

            switch (choice){
                case 1 -> displayVehicleTypes();
                case 0 -> System.out.println("Returning to Main Menu...");
                default -> System.out.println("Enter A Valid Number!");
            }

        }while (choice !=0);
    }
    //Display vehicle types with details
    private static void displayVehicleTypes() {
        System.out.println("\n=== Available Vehicle Types ===");
        System.out.printf("%-20s %-20s %-20s %-20s %-20s%n",
                "Type", "Capacity(kg)", "Rate per km (LKR)", "Avg Speed (km/h)", "Fuel Efficiency (km/l)");
        System.out.println("-----------------------------------------------------------------------------------------------------------");

        for (int i = 0; i < VEHICLE_TYPES; i++) {
            System.out.printf("%-20s %-20d %-20d %-20d %-20d%n",
                    VEHICLE_NAMES[i],
                    VEHICLE_CAPACITIES[i],
                    VEHICLE_RATES[i],
                    VEHICLE_SPEEDS[i],
                    VEHICLE_FUEL_EFFICIENCY[i]);
        }
        System.out.println();
    }

//delivery handling and Calculation
    private static void deliveryRequestHandlingCalculations() {

        int choice;
        do {
            System.out.println("Press 1 To Enter Delivery Details..");
            System.out.println("Press 2 To Do Calculations...");
            System.out.println("Press 3 To Show Output with All Calculations");
            System.out.println("Press 0 To Return To Main Menu.. ");
            System.out.print("Enter Your Choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice){
                case 1 -> deliveryRequestHandling();
                case 2-> calculations();
                case 3 -> finalOutput();
                case 0-> System.out.println("Returning..");
                default -> System.out.println("Enter A Valid Choice!");
            }
        }while (choice!=0);
   }
    public static void deliveryRequestHandling(){
        List<String> displayCityNames = null;
        int distanceWithoutKM = 0;
        int sourceIndex = -1;
        int destinationIndex = -1;
        int weight = -1;
        int vehicleType = -1;
        try {
            // Display available cities
            displayCityNames = Files.readAllLines(Paths.get(filePathCities));
            cityCount = Math.min(displayCityNames.size(), MAX_CITIES);
            System.out.println("Available Cities:");
            for (int i = 0; i < cityCount; i++) {
                System.out.printf("C%d - %s%n", (i + 1), displayCityNames.get(i));
            }

        }catch (IOException e){
            System.out.println("File Not Found!"+e.getMessage());
        }
        //Get source and destination cities indexes
        sourceIndex = getCityIndex("Enter Source City Index: ");
        if (sourceIndex==-1){
            return;
        }
        destinationIndex = getCityIndex("Enter Destination City Index: ");
        if (destinationIndex==-1){
            return;
        }
        //get the distance between entered cities
        String intercityDistanceSTR = intercityDistance[sourceIndex+1][destinationIndex+1];
        if (intercityDistanceSTR==null || intercityDistanceSTR.equals("---")){
            System.out.println("Distances Are Not Available! Please Update Them First!");
            return;
        }
        //pass the obtain value to an INT
        try{
            distanceWithoutKM = Integer.parseInt(intercityDistanceSTR.replace(" km","").trim());

        }catch (NumberFormatException e){
            System.out.println("Invalid format!");
            return;
        }

        weight = getWeight();
        if (weight==-1){
            return;
        }
        displayVehicles();
        vehicleType = getVehicleSelection();
        //System.out.println("You Selected ");
        if (vehicleType==-1){
            return;
        }
        //validating the carrying capacity with the chosen vehicle's capacity
        if (weight>VEHICLE_CAPACITIES[vehicleType]){
            System.out.printf("Weight %d kg exceeds %s Capacity %d!%n",weight,VEHICLE_NAMES[vehicleType],VEHICLE_CAPACITIES[vehicleType]);
            return;
        }
        //store inputs to public variables to get them to the calculation method
        currentSourceIndex = sourceIndex;
        currentDestinationIndex = destinationIndex;
        currentDistance = distanceWithoutKM;
        currentWeight = weight;
        currentVehicleType = vehicleType;
        currentCityNames = displayCityNames;

        System.out.printf("The Distance Between %s to %s is %d km%n.",displayCityNames.get(sourceIndex),displayCityNames.get(destinationIndex),distanceWithoutKM);
        System.out.printf("Chosen Vehicle Type is %s%n",VEHICLE_NAMES[vehicleType]);
        System.out.printf("Package Weight : %d kg%n",weight);
        System.out.println("Delivery Request Saved Successfully!");


    }
    //get a valid weight from the user
    public static int getWeight(){
        while (true){
            System.out.println("Enter The Package Weight (in kg to closest decimal point or 0 to cancel): ");
            try{
                int weight = scanner.nextInt();
                scanner.nextLine();

                if (weight==0){
                    return -1;
                } else if (weight>0) {
                    return weight;

                }else {
                    System.out.println("Weight Must Be A Positive Number!");
                }
            }catch (Exception e){
                System.out.println("Invalid Input");
                scanner.nextLine();
            }
        }

    }

    //display available vehicle list
    private static void displayVehicles() {
        System.out.println("Available Vehicles..");
        for (int i = 0;i<3 ; i++) {
            System.out.println((i+1)+" "+VEHICLE_NAMES[i]);

        }
    }
    //Get vehicle by index
    private static int getVehicleSelection(){
        while (true){
            System.out.print("Select Vehicle Type (0 to Cancel): ");
            try{
                int choice = scanner.nextInt();
                scanner.nextLine();

                if (choice==0){
                    return -1;
                }
                if (choice >= 1 && choice <= 3) {
                    return choice - 1; // Convert to 0-based index
                }else {
                    System.out.println("Enter A Given Value!");
                }


            }catch (Exception e){
                System.out.println("Enter A Valid Input!");
                scanner.nextLine();
            }
        }
    }
//CALCULATIONS
    public static void calculations(){
        //Checking the saved inputs are not empty
        if (currentSourceIndex==-1|| currentDestinationIndex==-1|| currentDistance==-1||currentWeight==-1|| currentVehicleType==-1|| currentCityNames==null){
            System.out.println("You Should Enter Details Before Calculations!");
            System.out.println("First Go to the option 1 and enter the data!");
            return;
        }

        // Assigning again the imported values to new variables
        int sourceIndex = currentSourceIndex;
        int destinationIndex = currentDestinationIndex;
        int distance = currentDistance;
        int weight = currentWeight;
        int vehicleType = currentVehicleType;
        List<String> cityNames = currentCityNames;

        //get the other information from the arrays
        String vehicleName = VEHICLE_NAMES[vehicleType];
        int rate = VEHICLE_RATES[vehicleType];
        int speed = VEHICLE_SPEEDS[vehicleType];
        int fuelEfficiency = VEHICLE_FUEL_EFFICIENCY[vehicleType];

        //THE REAL CALCULATIONS BEGIN.....
        //a.delivery cost
        double deliveryCost = distance * rate * (1+ weight * (1.0/10000.0));
        //b.estimated time
        double estimatedDeliveryTime = (double) distance /speed;
        //c.fuel consumption/used
        double fuelUsed = (double) distance/fuelEfficiency;
        //d.fuel cost
        double fuelCost  = fuelUsed * FUEL_PRICE;
        //e.Total Operational Cost
        double totalOperationalCost = deliveryCost + fuelCost;
        //f.Profit Calculation
        double profit = (deliveryCost*PROFIT_MARKUP);
        //g.Final Charge
        double finalCharge = totalOperationalCost + profit;

        System.out.println(deliveryCost);
        System.out.println(estimatedDeliveryTime);
        System.out.println(fuelUsed);
        System.out.println(fuelCost);
        System.out.println(totalOperationalCost);
        System.out.println(profit);
        System.out.println(finalCharge);

        System.out.println("Calculations Done!");
        System.out.println("Go to Option 3 For Display the Full Detailed Output!😊😊");




    }

    public static void finalOutput(){

    }




    //LEAST DISTANCE
    private static void leastDistance() {
    }


//PERFORMANCE REPORTS
    private static void performanceReports() {
    }

















}

