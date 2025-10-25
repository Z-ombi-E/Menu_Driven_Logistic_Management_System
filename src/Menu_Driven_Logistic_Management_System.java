import java.util.Scanner;

public class Menu_Driven_Logistic_Management_System {
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int mainChoice;
        String name;
        System.out.println("Please Enter Your Name Before Using Our System😊😊:  ");
        name = scanner.nextLine();
        String cappedName = CityManage.capitalizeFirstLetter(name);

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

    private static void sampleFinalOutput() {
    }

    private static void performanceReports() {
    }

    private static void leastDistance() {
    }

    private static void deliveryRequestHandling() {
    }

    private static void vehicleManagement() {
    }

    private static void distanceManagement() {
    }

    private static void cityManagement() {
    }





}

