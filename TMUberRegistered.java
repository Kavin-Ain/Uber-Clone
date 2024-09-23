//501250717
//Kavin Ainkaran

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class TMUberRegistered
{
    // These variables are used to generate user account and driver ids
    private static int firstUserAccountID = 900;
    private static int firstDriverId = 700;

    // Generate a new user account id
    public static String generateUserAccountId(ArrayList<User> current)
    {
        return "" + firstUserAccountID + current.size();
    }

    // Generate a new driver id
    public static String generateDriverId(ArrayList<Driver> current)
    {
        return "" + firstDriverId + current.size();
    }

    // Database of Preregistered users
    // In Assignment 2 these will be loaded from a file
    // The test scripts and test outputs included with the skeleton code use these
    // users and drivers below. You may want to work with these to test your code (i.e. check your output with the
    // sample output provided).
    
    //Tell the compiler to throw exceptions for both methods
    public static ArrayList<User> loadPreregisteredUsers(String filename) throws IOException
    {
        //Create the arraylist that will hold the users 
        ArrayList<User> users = new ArrayList<User>();
        //Create a scanner that goes through users.txt
         Scanner userSc = new Scanner(new File(filename));
         //Create an arraylist that will contain all the lines of the text file
         ArrayList<String> userInfo = new ArrayList<String>();
         //Add all info in while loop
         while (userSc.hasNext()){
             userInfo.add(userSc.nextLine());
             //check if arraylist has 3 items
             if (userInfo.size() == 3){
                 //add a new user if the arraylist is 3 items large ie. contains all needed info for 1 user
                 users.add(new User(generateUserAccountId(users), userInfo.get(0), userInfo.get(1), Double.parseDouble(userInfo.get(2))));
                 //clear the arraylist to reset it to size 0
                 userInfo.clear();
             }
         }
         userSc.close();
         return users;
    }

    // Database of Preregistered users
    // In Assignment 2 these will be loaded from a file
    public static ArrayList<Driver> loadPreregisteredDrivers(String filename) throws IOException
    {
        ArrayList<Driver> drivers = new ArrayList<Driver>();
        //Create a scanner that goes through drivers.txt
        Scanner driverSc = new Scanner(new File(filename));
        //Create an arraylist that will contain all the lines of the text file
        ArrayList<String> driverInfo = new ArrayList<String>();
        //Add all info in while loop
        while (driverSc.hasNext()){
            driverInfo.add(driverSc.nextLine());
            //check if arraylist has 4 items
            if (driverInfo.size() == 4){
                //add a new driver if the arraylist is 4 items large ie. contains all needed info for 1 driver
                drivers.add(new Driver(generateDriverId(drivers), driverInfo.get(0), driverInfo.get(1), driverInfo.get(2), driverInfo.get(3)));
                //clear the arraylist to reset it to size 0
                driverInfo.clear();
            }
        }
        driverSc.close();
        return drivers;

    }
}

