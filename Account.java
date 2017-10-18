public class Account{
  //Class for the account
  private String name;
  private String email;
  private String phoneNum;
  private String description;
  private double balance;
  public Account(String name, String email, String phoneNum, String description ){
      this.name = name;
      this.email = email;
      this.phoneNum = phoneNum;
      this.description = description;
      balance = 0.0;
  }
  public String toString(){ //for whatever reason when i have the accounts as Iterators I can't seem to access any functions but having it to
  //Default as the tostring method. So I use that there.
    return this.name;
  }

  public String getName(){
    return this.name;
  }
  public String getAllInfo(){
    return "<html>Name: " + this.name + "<br>Email: " + this.email + "<br>Phone Number: " + this.phoneNum + "<br>Description: " + this.description + "<br>Balance: " + this.balance + "</html>";
  }
}
