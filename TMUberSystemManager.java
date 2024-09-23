//501250717
//Kavin Ainkaran

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;
import java.util.LinkedList;

/*
 * 
 * This class contains the main logic of the system.
 * 
 *  It keeps track of all users, drivers and service requests (RIDE or DELIVERY)
 * 
 */
public class TMUberSystemManager
{
  private Map<String, User> users;
  private ArrayList<User> userList;
  private ArrayList<Driver> drivers;
  private Queue<TMUberService>[] serviceRequests; 

  public double totalRevenue; // Total revenues accumulated via rides and deliveries
  
  // Rates per city block
  private static final double DELIVERYRATE = 1.2;
  private static final double RIDERATE = 1.5;
  
  // Portion of a ride/delivery cost paid to the driver
  private static final double PAYRATE = 0.1;

  // These variables are used to generate user account and driver ids
  int userAccountId = 900;
  int driverId = 700;

  public TMUberSystemManager()
  {
    //OLD CODE: users   = new ArrayList<User>();
    //Replace the arraylist with a map
    users = new TreeMap<>();
    userList = new ArrayList<User>();
    drivers = new ArrayList<Driver>();
    
    //OLD CODE: serviceRequests = new ArrayList<TMUberService>(); 
    //Replace the arraylist with an array of queues instead (Each Queue holds requests from a specific zone)
    serviceRequests = new Queue[4];
    //Make the queues a linked list
    for (int i = 0; i < serviceRequests.length; i++) {
      serviceRequests[i] = new LinkedList<TMUberService>();
    }
    
    
    totalRevenue = 0;
  }

  // General string variable used to store an error message when something is invalid 
  // (e.g. user does not exist, invalid address etc.)  
  // The methods below will set this errMsg string and then return false
  
  


  // Generate a new user account id
  private String generateUserAccountId()
  {
    return "" + userAccountId + users.size();
  }
  
  // Generate a new driver id
  private String generateDriverId()
  {
    return "" + driverId + drivers.size();
  }

  // Given user account id, find user in list of users
  public User getUser(String accountId)
  {
    //Grab the users from the map
    for (User user: users.values()){
      if (user.getAccountId().equals(accountId)){
        //Return if user has the same accountId
        return user;
      }
    }
    return null;
  }
  
  // Check for duplicate user
  private boolean userExists(User user)
  {
    //Return true if users conatins the value of the parameter
    for (User userToSearch: users.values()){
      if (userToSearch.equals(user))
        return true;
    }
    return false;
  }
  
 // Check for duplicate driver
 private boolean driverExists(Driver driver)
 {
   // simple way
   // return drivers.contains(driver);
   
   for (int i = 0; i < drivers.size(); i++)
   {
     if (drivers.get(i).equals(driver))
       return true;
   }
   return false;
 }
  
 
 // Given a user, check if user ride/delivery request already exists in service requests
 private boolean existingRequest(TMUberService req)
 {
   // Simple way
   // return serviceRequests.contains(req);
   
   for (int i = 0; i < 4; i++)
   {
    for (TMUberService service: serviceRequests[i]){
      if (service.equals(req))
       return true;
    }
   }
   return false;
 } 
 
  
  // Calculate the cost of a ride or of a delivery based on distance 
  private double getDeliveryCost(int distance)
  {
    return distance * DELIVERYRATE;
  }

  private double getRideCost(int distance)
  {
    return distance * RIDERATE;
  }

  // Go through all drivers and see if one is available
  // Choose the first available driver
  private Driver getAvailableDriver()
  {
    for (int i = 0; i < drivers.size(); i++)
    {
      Driver driver = drivers.get(i);
      if (driver.getStatus() == Driver.Status.AVAILABLE)
        return driver;
    }

    return null;
  }

  //Create a getDriverWithId method so that it retrieves the driver using the driverId
  public Driver getDriverWithId(String driverId){
    //Throw an exception for an invalid driver ID
    if (driverId == null || driverId.equals("")){
      throw new InvalidDriverIdException("Driver ID is invalid");
    }
    
    for (Driver driver: drivers){
      //Check status and check if driverId is equal to one of the ids of the driver's in drivers
      if (driver.getId().equals(driverId))
        return driver;
    }
    return null;
  }


  // Print Information (printInfo()) about all registered users in the system
  public void listAllUsers()
  {
    int index = 1;
    System.out.println();
    for (User user: users.values()){
      System.out.printf("%-2s. ", index);
      user.printInfo();
      System.out.println();
      index ++;
    }
  }

  // Print Information (printInfo()) about all registered drivers in the system
  public void listAllDrivers()
  {
    System.out.println();
    
    for (int i = 0; i < drivers.size(); i++)
    {
      int index = i + 1;
      System.out.printf("%-2s. ", index);
      drivers.get(i).printInfo();
      System.out.println(); 
    }
  }

  // Print Information (printInfo()) about all current service requests
  public void listAllServiceRequests()
  {
    for (int i = 0; i < 4; i ++){
      //Print each Zone, then print equal sighns below 
      System.out.println("ZONE " + i);
      System.out.println("======");
      //Get the index for each service
      int index = 1;
      //Print the info of each service in each queue
      for (TMUberService service: serviceRequests[i]){
        System.out.print(index + ". ------------------------------------------------------------");
        service.printInfo();
        //Print a new line at the bottom
        System.out.println();
        index ++;
      }
      //Print a new line at the bottom
      System.out.println();
    }
  }

  // Add a new user to the system
  public void registerNewUser(String name, String address, double wallet)
  {
    // Check to ensure name is valid
    if (name == null || name.equals(""))
    {
      //Throw an Exception = "Invalid User Name " + name
      throw new InvalidUsernameException("Invalid User Name" + name);
    }
    // Check to ensure address is valid
    if (!CityMap.validAddress(address))
    {
      //Throw an Exception "Invalid User Address " + address
      throw new InvalidUserAddressException("Invalid User Address " + address);
    }
    // Check to ensure wallet amount is valid 
    if (wallet < 0)
    {
      //Throw an Exception = "Invalid Money in Wallet"
      throw new InvalidWalletException("Invalid Money in Wallet");
    }
    // Check for duplicate user
    User user = new User(generateUserAccountId(), name, address, wallet);
    if (userExists(user))
    {
      //Throw an Exception = "User Already Exists in System"
      throw new UserAlreadyExistsException("User Already Exists in System");
    }
    users.put(user.getAccountId(), new User(String.valueOf(userAccountId*10) + String.valueOf(users.size()), name, address, wallet));
    userList.add(new User(String.valueOf(userAccountId*10) + String.valueOf(users.size()), name, address, wallet));   
  }

  // Add a new driver to the system
  public void registerNewDriver(String name, String carModel, String carLicencePlate, String address)
  {
    // Check to ensure name is valid
    if (name == null || name.equals(""))
    {
      //Throw an Exception = "Invalid Driver Name " + name
      throw new InvalidDriverNameException("Invalid Driver Name " + name);
    }
    // Check to ensure car models is valid
    if (carModel == null || carModel.equals(""))
    {
      //Throw an Exception = "Invalid Car Model " + carModel
      throw new InvalidCarModelException("Invalid Car Model " + carModel);
    }
    // Check to ensure car licence plate is valid
    // i.e. not null or empty string
    if (carLicencePlate == null || carLicencePlate.equals(""))
    {
      //Throw an Exception = "Invalid Car Licence Plate " + carLicencePlate
      throw new InvalidLicencePlateException("Invalid Car Licence Plate " + carLicencePlate);
    }
    //Check to ensure driver address is valid
    if (!CityMap.validAddress(address)){
      //Throw an Exception = "Invalid Driver Address " + address
      throw new InvalidAddressException("Invalid Driver Address " + address);
    }
    // Check for duplicate driver. If not a duplicate, add the driver to the drivers list
    Driver driver = new Driver(generateDriverId(), name, carModel, carLicencePlate, address);
    if (driverExists(driver))
    {
      //Throw an Exceptiong = "Driver Already Exists in System"
      throw new DriverAlreadyExistsException("Driver Already Exists in System");
    }
    drivers.add(driver);  
  }

  // Request a ride. User wallet will be reduced when drop off happens
  public void requestRide(String accountId, String from, String to)
  {
    // Check valid user account
    User user = getUser(accountId);
    if (user == null)
    {
      //Throw an Exception = "User Account Not Found " + accountId
      throw new UserNotFoundException("User Account Not Found " + accountId);
    }
    // Check for a valid from and to addresses
    if (!CityMap.validAddress(from))
    {
      //Throw an Exception = "Invalid Address " + from
      throw new InvalidAddressException("Invalid Address " + from);
    }
    if (!CityMap.validAddress(to))
    {
      //Throw an Exception = "Invalid Address " + to
      throw new InvalidAddressException("Invalid Address " + to);
    }
    // Get the distance for this ride
    int distance = CityMap.getDistance(from, to);         // city blocks
    // Distance == 0 or == 1 is not accepted - walk!
    if (!(distance > 1))
    {
      //Throw an Exception = "Insufficient Travel Distance"
      throw new InsufficientDistanceException("Insufficient Travel Distance");
    }
    // Check if user has enough money in wallet for this trip
    double cost = getRideCost(distance);
    if (user.getWallet() < cost)
    {
      //Throw an Exception = "Insufficient Funds"
      throw new InsufficientFundsException("Insufficient Funds");
    }
    
    

    //Throw an exception if driver is not in zone
    

    // Create the request
    TMUberRide req = new TMUberRide(from, to, user, distance, cost);
    
    // Check if existing ride request for this user - only one ride request per user at a time
    if (existingRequest(req))
    {
      //Throw an Exception = "User Already Has Ride Request"
      throw new RideExistsException("User Already Has Ride Request");
    }

    //Get Zone of Ride
    int zone = CityMap.getCityZone(from);
    //Find what queue to place the service in
    Queue<TMUberService> queueOfRide = serviceRequests[zone];
    //Add the service to that queue
    queueOfRide.add(req);
    user.addRide();
  }

  // Request a food delivery. User wallet will be reduced when drop off happens
  public void requestDelivery(String accountId, String from, String to, String restaurant, String foodOrderId)
  {
    // Check for valid user account
    User user = getUser(accountId);
    if (user == null)
    {
      //Throw an Exception = "User Account Not Found " + accountId
      throw new UserNotFoundException("User Account Not Found " + accountId);
    }
    // Check for valid from and to address
    if (!CityMap.validAddress(from))
    {
      //Throw an Exception = "Invalid Address " + from
      throw new InvalidAddressException("Invalid Address " + from);
    }
    if (!CityMap.validAddress(to))
    {
      //Throw an Exception = "Invalid Address " + to
      throw new InvalidAddressException("Invalid Address " + to);
    }
    //Throw an exception if restaurant name is invalid
    if (restaurant == null || restaurant == "")
    {
      //Throw an Exception = "User Account Not Found " + accountId
      throw new InvalidRestaurantException("User Account Not Found " + accountId);
    }
    //Throw an exception if restaurant name is invalid
    if (foodOrderId == null || foodOrderId == "")
    {
      //Throw an Exception = "User Account Not Found " + accountId
      throw new InvalidFoodOrderIdException("User Account Not Found " + accountId);
    }
    // Get the distance to travel
    int distance = CityMap.getDistance(from, to);         // city blocks
    // Distance must be at least 1 city block
    if (distance == 0)
    {
      //Throw an Exception = "Insufficient Travel Distance"
      throw new InsufficientDistanceException("Insufficient Travel Distance");
    }
    // Check if user has enough money in wallet for this delivery
    double cost = getDeliveryCost(distance);
    if (user.getWallet() < cost)
    {
      //Throw an Exception = "Insufficient Funds"
      throw new InsufficientFundsException("Insufficient Funds");
    }

    TMUberDelivery delivery = new TMUberDelivery(from, to, user, distance, cost, restaurant, foodOrderId); 
    // Check if existing delivery request for this user for this restaurant and food order #
    if (existingRequest(delivery))
    {
      //Throw an Exception = "User Already Has Delivery Request at Restaurant with this Food Order"
      throw new DeliveryExistsException("User Already Has Delivery Request at Restaurant with this Food Order");
    }
    
    //Get Zone of Ride
    int zone = CityMap.getCityZone(from);
    //Find what queue to place the service in
    Queue<TMUberService> queueOfDelivery = serviceRequests[zone];
    //Add the service to that queue
    queueOfDelivery.add(delivery);
    //Add the delivery to the user's delivery count
    user.addDelivery();
  }


  // Cancel an existing service request. 
  // parameter request is the index in the serviceRequests array list
  public void cancelServiceRequest(int request, int zone)
  {
    //Check if zone is valid
    if (zone < 0 || zone > 3) {
      throw new InvalidZoneException("Invalid Zone # " + zone);
    }
    //Grab the queue using the zone if valid
    Queue<TMUberService> queue = serviceRequests[zone];
    //Check if service request is valid
    if (request < 1 || request >= queue.size()) {
      throw new InvalidRequestException("Invalid Request # " + request);
    }
    //Create an iterator object for the queue
    Iterator<TMUberService> iter = queue.iterator();
    //Create an index counter
    int index = 0;
    while(iter.hasNext()){
      iter.next();
      //Remove if the next object is the requested number
      if (index + 1 == request){
        iter.remove();
      }
      //Increment the index
      index ++;
    } 
  }
  
  
  // Drop off a ride or a delivery. This completes a service.
  // parameter request is the index in the serviceRequests array list
  public void dropOff(String driverId)
  {
    //THIS IS THE MODIFIED DROP OFF METHOD
    //Get the driver using the driver ID
    Driver driver = getDriverWithId(driverId);
    if (driver.getStatus() == Driver.Status.AVAILABLE){
      throw new DriverNotFoundException("The Driver With The ID " + driverId + " is Not Currently Driving");
    }
    if (driver.getService() == null){
      throw new DriverNotFoundException("There Are No Requests");
    }

    //Get the driver's current service
    TMUberService service = driver.getService();
    //Get the zone of the service
    int zoneOfService = driver.getZone();
    //Get the queue that the service belongs in
    Queue<TMUberService> queueOfService = serviceRequests[zoneOfService];

    queueOfService.remove(service);          // remove request from array list
    totalRevenue += service.getCost();          // add service cost to revenues
    driver.pay(service.getCost()*PAYRATE);      // pay the driver
    totalRevenue -= service.getCost()*PAYRATE;  // deduct driver fee from total revenues
    driver.setStatus(Driver.Status.AVAILABLE);  // driver is now available again
    User user = service.getUser();
    user.payForService(service.getCost());      // user pays for ride or delivery
    
    //Change the driver's service to null
    driver.setService(null);
    //Set the address of the driver to the destination of the service
    driver.setAddress(service.getTo());
    //CHange the zone of the driver to the zone of the new address
    driver.setZone(CityMap.getCityZone(driver.getAddress()));

  }

  //PICKUP SERVICE
  public void pickUp(String driverId){
    //Use the overloaded getAvailableDriver() class to get the driver
    Driver driver = getDriverWithId(driverId);
    //First, check if the driver is valid to perform the delivery
    if (serviceRequests[driver.getZone()].size() == 0){
      throw new NoServiceRequestsException("No Service Request in Zone " + driver.getZone());
    }

    //If driver doesnt exist, throw a DriverNotFound exception
    if (!driverExists(driver)){
      throw new DriverNotFoundException("Driver Doesn't Exist");
    }

    //Get the zone that the driver is in
    int driverZone = driver.getZone();
    //Remove the first service in the zone's respective queue
    if (serviceRequests[driverZone].size() < 1){
    //Throw an exception if the service queue is empty
    throw new NoServiceRequestsException("No Service Request In Zone " + driverZone);
    }

    //Grab the service that the driver will perform
    TMUberService currentService = serviceRequests[driverZone].remove();
    System.out.println("Driver " + driverId + " Picking Up in Zone " + driver.getZone());
    //Set the service to the driver
    driver.setService(currentService);
    //Change the status of the driver to driving
    driver.setStatus(Driver.Status.DRIVING);
    //Change driver address to from address of the service
    driver.setAddress(currentService.getFrom());
    
  }

  public void driveTo(String driverId, String address)
  {
    //Check for invalid addresses
    if (!CityMap.validAddress(address)){
      throw new InvalidAddressException ("Invalid Address " + address);
    }
    //Get the driver using their ID
    Driver driver = getDriverWithId(driverId);
    //Throw exception if driver doesn't exists
    if (driver == null){
      throw new DriverNotFoundException("Driver Doesn't Exist");
    }
    //Throw an exception if the driver's status is not available
    if (driver.getStatus() == Driver.Status.DRIVING){
      throw new DriverNotFoundException("Driver is Currently Driving");
    }

    //If the address is valid AND the driver's status is available...
    if (CityMap.validAddress(address) && driver.getStatus() == Driver.Status.AVAILABLE){
      //Set the drivers address to the new address
      driver.setAddress(address);
      //With the new address, change the drivers zone
      driver.setZone(CityMap.getCityZone(driver.getAddress()));
      //Return the print statement that driver has moved to new zone
      System.out.println("Driver " + driverId + " Now in Zone " + driver.getZone());
    }

  }

  public void setUsers(ArrayList<User> userList)
  {
    //Create an empty map
    users.clear();
    //Create a for loop to grab each user in userList
    for (User user: userList){
      //Put each user and their ID into the map;
      users.put(user.getAccountId(), user);
    }
    //Do the same for userList
    for (User user: users.values()){
      userList.add(user);
    }
  }

  public void setDrivers(ArrayList<Driver> drivers)
  {
    this.drivers = drivers;
  }

  // Sort users by name
  public void sortByUserName()
  {
    Collections.sort(userList, new NameComparator());
    listAllUsers();
  }

  private class NameComparator implements Comparator<User>
  {
    public int compare(User a, User b)
    {
      return a.getName().compareTo(b.getName());
    }
  }

  // Sort users by number amount in wallet
  public void sortByWallet()
  {
    Collections.sort(userList, new UserWalletComparator());
    listAllUsers();
  }

  private class UserWalletComparator implements Comparator<User>
  {
    public int compare(User a, User b)
    {
      if (a.getWallet() > b.getWallet()) return 1;
      if (a.getWallet() < b.getWallet()) return -1; 
      return 0;
    }
  }

  // Sort trips (rides or deliveries) by distance
  // class TMUberService must implement Comparable
  public void sortByDistance()
  {
    ArrayList<TMUberService> serviceList = new ArrayList<TMUberService>();
    for (int i = 0; i < 4; i++){
      for (TMUberService services: serviceRequests[i]){
        serviceList.add(services);
      }
    }
    Collections.sort(serviceList);
    listAllServiceRequests();
  }
}













//Create a new class to create custom exceptions

//Create an exception when the user name is not valid
class InvalidUsernameException extends RuntimeException
{
  //Create a constructor for the exception
  public InvalidUsernameException(String message){
    super(message);
  }
}

//Create an exception when the user address is not valid
class InvalidUserAddressException extends RuntimeException
{
  //Create a constructor for the exception
  public InvalidUserAddressException(String message){
    super(message);
  }
}

//Create an exception if there is an invalid amount of money in the wallet
class InvalidWalletException extends RuntimeException
{
  //Create a constructor for the exception
  public InvalidWalletException(String message){
    super(message);
  }
}

//Create an exception if there is a duplicate user
class UserAlreadyExistsException extends RuntimeException
{
  //Create a constructor for the exception
  public UserAlreadyExistsException(String message){
    super(message);
  }
}

//Create an exception when the Driver name is not valid
class InvalidDriverNameException extends RuntimeException
{
  //Create a constructor for the exception
  public InvalidDriverNameException(String message){
    super(message);
  }
}

//Create an exception when the car model is not valid
class InvalidCarModelException extends RuntimeException
{
  //Create a constructor for the exception
  public InvalidCarModelException(String message){
    super(message);
  }
}

//Create an exception when the car licence plate is not valid
class InvalidLicencePlateException extends RuntimeException
{
  //Create a constructor for the exception
  public InvalidLicencePlateException(String message){
    super(message);
  }
}

//Create an exception if there is a duplicate driver
class DriverAlreadyExistsException extends RuntimeException
{
  //Create a constructor for the exception
  public DriverAlreadyExistsException(String message){
    super(message);
  }
}

//Create an exception if the user does not exist
class UserNotFoundException extends RuntimeException
{
  //Create a constructor for the exception
  public UserNotFoundException(String message){
    super(message);
  }
}

//Create an exception if the address is invalid
class InvalidAddressException extends RuntimeException
{
  //Create a constructor for the exception
  public InvalidAddressException(String message){
    super(message);
  }
}

//Create an exception if the travel distance is insufficient
class InsufficientDistanceException extends RuntimeException
{
  //Create a constructor for the exception
  public InsufficientDistanceException(String message){
    super(message);
  }
}

//Create an exception if there are insufficient funds
class InsufficientFundsException extends RuntimeException
{
  //Create a constructor for the exception
  public InsufficientFundsException(String message){
    super(message);
  }
}


//Create an exception if there are no drivers available
class NoAvailableDriversException extends RuntimeException
{
  //Create a constructor for the exception
  public NoAvailableDriversException(String message){
    super(message);
  }
}

//Create an exception if there is a duplicate ride service
class RideExistsException extends RuntimeException
{
  //Create a constructor for the exception
  public RideExistsException(String message){
    super(message);
  }
}

//Create an exception if there are no drivers available
class InvalidRestaurantException extends RuntimeException
{
  //Create a constructor for the exception
  public InvalidRestaurantException(String message){
    super(message);
  }
}

//Create an exception if there are no drivers available
class InvalidFoodOrderIdException extends RuntimeException
{
  //Create a constructor for the exception
  public InvalidFoodOrderIdException(String message){
    super(message);
  }
}

//Create an exception if there is a duplicate delivery service
class DeliveryExistsException extends RuntimeException
{
  //Create a constructor for the exception
  public DeliveryExistsException(String message){
    super(message);
  }
}

//Create an exception if the request number is invalid
class InvalidRequestException extends RuntimeException
{
  //Create a constructor for the exception
  public InvalidRequestException(String message){
    super(message);
  }
}

//Create an exception if the driver cannot be found
class DriverNotFoundException extends RuntimeException
{
  //Create a constructor for the exception
  public DriverNotFoundException(String message){
    super(message);
  }
}

//Create an exception if the driver cannot be found
class InvalidDriverIdException extends RuntimeException
{
  //Create a constructor for the exception
  public InvalidDriverIdException(String message){
    super(message);
  }
}

//Create an exception if there are no service requests
class NoServiceRequestsException extends RuntimeException
{
  //Create a constructor for the exception
  public NoServiceRequestsException(String message){
    super(message);
  }
}

//Create an exception if there are no service requests
class InvalidZoneException extends RuntimeException
{
  //Create a constructor for the exception
  public InvalidZoneException(String message){
    super(message);
  }
}
