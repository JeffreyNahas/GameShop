/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/



// line 22 "model.ump"
// line 166 "model.ump"
public abstract class Account
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Account Attributes
  private String userName;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Account(String aUserName)
  {
    userName = aUserName;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setUserName(String aUserName)
  {
    boolean wasSet = false;
    userName = aUserName;
    wasSet = true;
    return wasSet;
  }

  public String getUserName()
  {
    return userName;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "userName" + ":" + getUserName()+ "]";
  }
}