import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
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

    public static void main(String[] args) {
        int mainChoice;
        String name;
        System.out.println("Please Enter Your Name Before Using Our System😊😊:  ");
        name = scanner.nextLine();
        String cappedName = capitalizeFirstLetter(name);

        do {
            System.out.println("Hello There "+ cappedName+ " !!!");
            System.out.println("Welcome To Our Menu Driven Logistic Management System..\n");
            System.out.println("Press 1 to Enter City Management.. ");
            System.out.println("Press 2 to Enter Distance Management.. ");
            System.out.println("Press 3 to Enter Vehicle Management.. ");
            System.out.println("Press 4 to Enter Delivery Request Handling And Calculations.. ");
            System.out.println("Press 5 to Enter To Find The Least-Cost Route (Least-Distance) .. ");
            System.out.println("Press 6 to Enter To Get Reports.. ");
            System.out.println("Press 7 to Get a Sample Output for a DELIVERY..  ");
            System.out.println("Press 0 to EXIT....");
            System.out.print("Enter Your Choice: ");
            mainChoice = scanner.nextInt();
            scanner.nextLine();

            switch (mainChoice){
                case 1-> cityManagement ();
                case 2-> distanceManagement();
                case 3-> vehicleManagement();
                case 4-> deliveryRequestHandling();
                case 5-> leastDistance();
                case 6-> performanceReports();
                case 7-> sampleFinalOutput();
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

                String city = cityNames.get(i);
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

    private static void vehicleManagement() {
    }

    private static void deliveryRequestHandling() {
    }

    private static void leastDistance() {
    }

    private static void performanceReports() {
    }

    private static void sampleFinalOutput() {
    }














}

