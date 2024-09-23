//501250717
//Kavin Ainkaran

/*
 * 
 * This class simulates a car driver in a simple Uber-like app 
 */



public class Driver
{
  private String id;
  private String name;
  private String carModel;
  private String licensePlate;
  private double wallet;
  private String type;

  //New instance variables
  private TMUberService service;
  private String address;
  private int zone;
  
  public static enum Status {AVAILABLE, DRIVING};
  private Status status;
    
  //Add address to constructor class
  public Driver(String id, String name, String carModel, String licensePlate, String address)
  {
    this.id = id;
    this.name = name;
    this.carModel = carModel;
    this.licensePlate = licensePlate;
    //Add address as a parameter
    this.address = address;
    //Use getCityZone with the address to get the zone
    this.zone = CityMap.getCityZone(address);
    //Initialize service to null
    this.service = null;

    this.status = Status.AVAILABLE;
    this.wallet = 0;
    this.type = "";
  }
  // Print Information about a driver
  public void printInfo()
  {
    System.out.printf("Id: %-3s Name: %-15s Car Model: %-15s License Plate: %-10s Wallet: %2.2f", 
        id, name, carModel, licensePlate, wallet);
    System.out.println();
    System.out.println("STATUS: " + status + "  Address: " + address + "  Zone: " + zone);
  }
  
  // Getters and Setters
  public String getType()
  {
    return type;
  }
  public void setType(String type)
  {
    this.type = type;
  }
  public String getId()
  {
    return id;
  }
  public void setId(String id)
  {
    this.id = id;
  }
  public String getName()
  {
    return name;
  }
  public void setName(String name)
  {
    this.name = name;
  }
  public String getCarModel()
  {
    return carModel;
  }
  public void setCarModel(String carModel)
  {
    this.carModel = carModel;
  }
  public String getLicensePlate()
  {
    return licensePlate;
  }
  public void setLicensePlate(String licensePlate)
  {
    this.licensePlate = licensePlate;
  }
  public Status getStatus()
  {
    return status;
  }
  public void setStatus(Status status)
  {
    this.status = status;
  }
  
  //Getter and Setter Methods for address and zone
  public String getAddress(){
    return address;
  }
  
  public void setAddress(String address){
    this.address = address;
  }

  public int getZone(){
    return CityMap.getCityZone(address);
  }

  public void setZone(int zone){
    this.zone = zone;
  }

  public void setService(TMUberService service){
    this.service = service;
  }

  public TMUberService getService(){
    return this.service;
  }
  
  public double getWallet()
  {
    return wallet;
  }
  public void setWallet(double wallet)
  {
    this.wallet = wallet;
  }


  /*
   * Two drivers are equal if they have the same name and license plates.
   * This method is overriding the inherited method in superclass Object
   */
  public boolean equals(Object other)
  {
    Driver otherDriver = (Driver) other;
    return this.name.equals(otherDriver.name) && 
           this.licensePlate.equals(otherDriver.licensePlate);
  }
  
  // A driver earns a fee for every ride or delivery
  public void pay(double fee)
  {
    wallet += fee;
  }
}
