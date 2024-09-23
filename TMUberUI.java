//501250717
//Kavin Ainkaran

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

// Simulation of a Simple Command-line based Uber App 

// This system supports "ride sharing" service and a delivery service

public class TMUberUI
{
  public static void main(String[] args)
  {
    // Create the System Manager - the main system code is in here 

    TMUberSystemManager tmuber = new TMUberSystemManager();
    
    Scanner scanner = new Scanner(System.in);
    System.out.print(">");

    // Process keyboard actions
    while (scanner.hasNextLine())
    {
      String action = scanner.nextLine();

      if (action == null || action.equals("")) 
      {
        System.out.print("\n>");
        continue;
      }
      // Quit the App
      else if (action.equalsIgnoreCase("Q") || action.equalsIgnoreCase("QUIT"))
        return;
      // Print all the registered drivers
      else if (action.equalsIgnoreCase("DRIVERS"))  // List all drivers
      {
        tmuber.listAllDrivers(); 
      }
      // Print all the registered users
      else if (action.equalsIgnoreCase("USERS"))  // List all users
      {
        tmuber.listAllUsers(); 
      }
      // Print all current ride requests or delivery requests
      else if (action.equalsIgnoreCase("REQUESTS"))  // List all requests
      {
        tmuber.listAllServiceRequests(); 
      }
      // Register a new driver
      else if (action.equalsIgnoreCase("REGDRIVER")) 
      {
        String name = "";
        System.out.print("Name: ");
        if (scanner.hasNextLine())
        {
          name = scanner.nextLine();
        }
        String carModel = "";
        System.out.print("Car Model: ");
        if (scanner.hasNextLine())
        {
          carModel = scanner.nextLine();
        }
        String license = "";
        System.out.print("Car License: ");
        if (scanner.hasNextLine())
        {
          license = scanner.nextLine();
        }
        //Recieve address from user
        String address = "";
        System.out.print("Address: ");
        if (scanner.hasNextLine())
        {
          address = scanner.nextLine();
        }

        //Use Try/Catch blocks, catching cutom exceptions of the method
        try{
          tmuber.registerNewDriver(name, carModel, license, address);
          System.out.printf("Driver: %-15s Car Model: %-15s License Plate: %-10s Address: %-10s", name, carModel, license, address);
        } catch (InvalidDriverNameException e){
          System.out.println(e.getMessage());
        } catch (InvalidCarModelException e){
          System.out.println(e.getMessage());
        } catch (InvalidAddressException e){
          System.out.println(e.getMessage());
        } catch (InvalidLicencePlateException e){
          System.out.println(e.getMessage());
        } catch (DriverAlreadyExistsException e){
          System.out.println(e.getMessage());
        }
        
      }
      // Register a new user
      else if (action.equalsIgnoreCase("REGUSER")) 
      {
        String name = "";
        System.out.print("Name: ");
        if (scanner.hasNextLine())
        {
          name = scanner.nextLine();
        }
        String address = "";
        System.out.print("Address: ");
        if (scanner.hasNextLine())
        {
          address = scanner.nextLine();
        }
        double wallet = 0.0;
        System.out.print("Wallet: ");
        if (scanner.hasNextDouble())
        {
          wallet = scanner.nextDouble();
          scanner.nextLine(); // consume nl
        }
        //Same process as for REGDRIVER
        try{
          tmuber.registerNewUser(name, address, wallet);
          System.out.printf("User: %-15s Address: %-15s Wallet: %2.2f", name, address, wallet);
        } catch (InvalidUsernameException e){
          System.out.println(e.getMessage());
        } catch (InvalidUserAddressException e){
          System.out.println(e.getMessage());
        } catch (InvalidWalletException e){
          System.out.println(e.getMessage());
        } catch (UserAlreadyExistsException e){
          System.out.println(e.getMessage());
        } 
      }
      // Request a ride
      else if (action.equalsIgnoreCase("REQRIDE")) 
      {
        String account = "";
        System.out.print("User Account Id: ");
        if (scanner.hasNextLine())
        {
          account = scanner.nextLine();
        }
        String from = "";
        System.out.print("From Address: ");
        if (scanner.hasNextLine())
        {
          from = scanner.nextLine();
        }
        String to = "";
        System.out.print("To Address: ");
        if (scanner.hasNextLine())
        {
          to = scanner.nextLine();
        }
        
        try{
          tmuber.requestRide(account, from, to);
        
          User user = tmuber.getUser(account);
          System.out.printf("\nRIDE for: %-15s From: %-15s To: %-15s", user.getName(), from, to);
        } catch (UserNotFoundException e){
          System.out.println(e.getMessage());
        } catch (InvalidAddressException e){
          System.out.println(e.getMessage());
        } catch (InsufficientDistanceException e){
          System.out.println(e.getMessage());
        } catch (InsufficientFundsException e){
          System.out.println(e.getMessage());
        } catch (NoAvailableDriversException e){
          System.out.println(e.getMessage());
        } catch (RideExistsException e){
          System.out.println(e.getMessage());
        } 

      }
      // Request a food delivery
      else if (action.equalsIgnoreCase("REQDLVY")) 
      {
        String account = "";
        System.out.print("User Account Id: ");
        if (scanner.hasNextLine())
        {
          account = scanner.nextLine();
        }
        String from = "";
        System.out.print("From Address: ");
        if (scanner.hasNextLine())
        {
          from = scanner.nextLine();
        }
        String to = "";
        System.out.print("To Address: ");
        if (scanner.hasNextLine())
        {
          to = scanner.nextLine();
        }
        String restaurant = "";
        System.out.print("Restaurant: ");
        if (scanner.hasNextLine())
        {
          restaurant = scanner.nextLine();
        }
        String foodOrder = "";
        System.out.print("Food Order #: ");
        if (scanner.hasNextLine())
        {
          foodOrder = scanner.nextLine();
        }
        try{
          tmuber.requestDelivery(account, from, to, restaurant, foodOrder);
          User user = tmuber.getUser(account);
          System.out.printf("\nDELIVERY for: %-15s From: %-15s To: %-15s", user.getName(), from, to); 
        } catch (UserNotFoundException e){
          System.out.println(e.getMessage());
        } catch (InvalidAddressException e){
          System.out.println(e.getMessage());
        } catch (InvalidRestaurantException e){
          System.out.println(e.getMessage());
        } catch (InvalidFoodOrderIdException e){
          System.out.println(e.getMessage());
        } catch (InsufficientDistanceException e){
          System.out.println(e.getMessage());
        } catch (InsufficientFundsException e){
          System.out.println(e.getMessage());
        } catch (NoAvailableDriversException e){
          System.out.println(e.getMessage());
        } catch (DeliveryExistsException e){
          System.out.println(e.getMessage());
        } 
        
      }
      // Sort users by name
      else if (action.equalsIgnoreCase("SORTBYNAME")) 
      {
        tmuber.sortByUserName();
      }
      // Sort users by number of ride they have had
      else if (action.equalsIgnoreCase("SORTBYWALLET")) 
      {
        tmuber.sortByWallet();
      }
      // Sort current service requests (ride or delivery) by distance
      else if (action.equalsIgnoreCase("SORTBYDIST")) 
      {
        tmuber.sortByDistance();
      }
      // Cancel a current service (ride or delivery) request
      else if (action.equalsIgnoreCase("CANCELREQ")) 
      {
        int request = -1;
        System.out.print("Request #: ");
        if (scanner.hasNextInt())
        {
          request = scanner.nextInt();
          scanner.nextLine(); // consume nl character
        }

        //Modified action now takes zone number as well
        int zoneNumber = -1;
        System.out.print("Zone #: ");
        if (scanner.hasNextInt())
        {
          zoneNumber = scanner.nextInt();
          scanner.nextLine(); // consume nl character
        }

        try{
          tmuber.cancelServiceRequest(request, zoneNumber);
          System.out.println("Service request #" + request + " cancelled");
        } catch (InvalidZoneException e){
          System.out.println(e.getMessage());
        } catch (InvalidRequestException e){
          System.out.println(e.getMessage());
        }
      }
      // Drop-off the user or the food delivery to the destination address
      else if (action.equalsIgnoreCase("DROPOFF")) 
      {
        /*
        int request = -1;
        System.out.print("Request #: ");
        if (scanner.hasNextInt())
        {
          request = scanner.nextInt();
          scanner.nextLine(); // consume nl
        }
        */
        //Instead of request number, use a string, driverId, to get the service via class Driver
        String driverId = "";
        System.out.print("Driver ID: ");
        if (scanner.hasNextInt())
        {
          driverId = scanner.nextLine();
        }

        try{
          tmuber.dropOff(driverId);
        System.out.println("Successful Drop Off - Driver " + driverId + " dropping off");
      } catch (DriverNotFoundException e){
        System.out.println(e.getMessage());
      } 
        //Catch the InvalidDriverIdException that comes with the custom getDriverWithId() method
        //Must catch for the following methods: dropOff, pickUp, driveTo
        catch (InvalidDriverIdException e){
        System.out.println(e.getMessage());
      }

    }
      // Get the Current Total Revenues
      else if (action.equalsIgnoreCase("REVENUES")) 
      {
        System.out.println("Total Revenue: " + tmuber.totalRevenue);
      }
      // Unit Test of Valid City Address 
      else if (action.equalsIgnoreCase("ADDR")) 
      {
        String address = "";
        System.out.print("Address: ");
        if (scanner.hasNextLine())
        {
          address = scanner.nextLine();
        }
        System.out.print(address);
        if (CityMap.validAddress(address))
          System.out.println("\nValid Address"); 
        else
          System.out.println("\nBad Address"); 
      }
      
      // Unit Test of CityMap Distance Method
      else if (action.equalsIgnoreCase("DIST")) 
      {
        String from = "";
        System.out.print("From: ");
        if (scanner.hasNextLine())
        {
          from = scanner.nextLine();
        }
        String to = "";
        System.out.print("To: ");
        if (scanner.hasNextLine())
        {
          to = scanner.nextLine();
        }
        System.out.print("\nFrom: " + from + " To: " + to);
        System.out.println("\nDistance: " + CityMap.getDistance(from, to) + " City Blocks");
      }
      
      // PICKUP command
      else if (action.equalsIgnoreCase("PICKUP")){
        String driverId = "";
        System.out.print("Driver ID: ");
        if (scanner.hasNextLine()){
          driverId = scanner.nextLine();
        }
        try{
          tmuber.pickUp(driverId);
        } catch (InvalidDriverIdException e){
          System.out.println(e.getMessage());
        } catch (NoServiceRequestsException e){
          System.out.println(e.getMessage());
        } catch (NoAvailableDriversException e){
          System.out.println(e.getMessage());
        } catch (DriverNotFoundException e){
          System.out.println(e.getMessage());
        } 
      }

      //LOADUSERS command
      else if (action.equalsIgnoreCase("LOADUSERS")){
        //Create a string that will hold the filename
        String filename = "";
        //Print text that asks user for filename
        System.out.print("Filename: ");
        //If there is a response from the user, store the response as the filename
        if (scanner.hasNextLine()){
          filename = scanner.nextLine();
        }
        try{
          ArrayList<User> users = TMUberRegistered.loadPreregisteredUsers(filename);
          tmuber.setUsers(users);
          System.out.println("Users Loaded");
        } catch (FileNotFoundException e) {
          System.out.println(filename + " Not Found");
        } catch (IOException e) {
          //Close program if IOException is called  
          return;
        }
        
      }

      //LOADDRIVERS command
      else if (action.equalsIgnoreCase("LOADDRIVERS")){
        String filename = "";
        System.out.print("Filename: ");
        if (scanner.hasNextLine()){
          filename = scanner.nextLine();
        }
        try{
          tmuber.setDrivers(TMUberRegistered.loadPreregisteredDrivers(filename));
          System.out.println("Drivers Loaded");
        } catch (FileNotFoundException e) {
          System.out.println(filename + " Not Found");
        } catch (IOException e) {
          //Close program if IOException is called  
          return;
        }
      }


      //DRIVETO command
      else if (action.equalsIgnoreCase("DRIVETO")){
        String driverId = "";
        System.out.print("Driver ID: ");
        if (scanner.hasNextLine()){
          driverId = scanner.nextLine();
        }
        String address = "";
        System.out.print("Address: ");
        if (scanner.hasNextLine()){
          address = scanner.nextLine();
        }
        try{
        tmuber.driveTo(driverId, address);
        } catch (InvalidDriverIdException e){
          System.out.println(e.getMessage());
        } catch (InvalidAddressException e){
          System.out.println(e.getMessage());
        } catch (DriverNotFoundException e){
          System.out.println(e.getMessage());
        } 

      }

      System.out.print("\n>");
    }
  }
}

