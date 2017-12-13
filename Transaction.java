import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class Transaction {
    private String name;
    private String accountName;
    private double amount;
    private String date;
    private String stringType,stringPayment,stringCode;
    private boolean type,credit,check;
    //Important..
    //Withdrawals are noted as TRUE
    //Deposits are noted as FALSE
    //when creating a new transaction be mindful if your giving or taking away money.

    public Transaction(String name, String accountName, double amount, String date, boolean type, ArrayList<Account> accountArray, String accountToView,boolean check, boolean credit,String stringCode){
        this.name = name;
        this.accountName = accountName;
        this.amount = amount;
        this.date = date;
        this.type = type;
        this.check = check;
        this.credit = credit;
        System.out.println("There better be nothing here -->"+accountToView);
        if (accountToView==""){
          System.out.print("acountToView is empty so i better no do anything");
        }
        else{
          if (type == true) {
              stringType = "Withdrawal";
              //remove/update from balance of accountName
              updateW(accountArray,accountToView);
              stringPayment = "N/A";
              this.stringCode = stringCode;
          }

          else if(type == false) {
              stringType = "Deposit";
              this.stringCode = "N/A";
              //add/update to balance of accountName
              updateD(accountArray,accountToView);
              if (check == true && credit == false) stringPayment = "Check";
              if(credit == true && check == false) stringPayment = "Credit";
          }
        }


    }
    public String toString(){ //for whatever reason when i have the accounts as Iterators I can't seem to access any functions but having it to
    //Default as the tostring method. So I use that there.
      return this.name;
    }

      public String getType() {
          String ret = this.stringType +","+ this.stringPayment;
          return ret;
      }

      public String getCode() {
          return this.stringCode;
      }

      public String getName(){
        return this.name;
      }
      public String getAccount(){
        return this.accountName;
      }
      public double getAmount(){
        return this.amount;
      }
      public String getDate(){
        return this.date;
      }
      public String getAllInfo(){
        return "Name: " + this.name + " Account Name: " + this.accountName + " Amount: " + this.amount + " Date: " + this.date + " "+getType() + " Code: "+this.stringCode;
      }


      //These can be moved to the main Panel with only 1 line edit but I like to save space, so whatever works for ya'll - Wyatt
      public void updateW(ArrayList<Account> accountArray, String accountToView) {
          Iterator<Account> it = accountArray.iterator();
          try{
              FileWriter myWriter = new FileWriter("SaveFile.txt",false); // Delete file to be overwritten later.
              myWriter.write("");
              myWriter.close();
          }
          catch(IOException ioe){
              System.err.println("You done goofed");
          }

          while (it.hasNext()){
              Account current = it.next();
              if(accountToView.equalsIgnoreCase(current.toString())|| current.toString().equalsIgnoreCase("Master Account")){
                  Account tempAccount = new Account(current.getName(),current.getEmail(),current.getPhoneNum(),current.getDescription());
                  double tempBalance = current.getBalance() - this.amount;
                  tempAccount.setBalance(tempBalance);
                  it.equals(tempAccount);
                  try{ //Rewrite file.
                      FileWriter Writer = new FileWriter("SaveFile.txt",true);
                      Writer.write(tempAccount.getName()+","+tempAccount.getEmail()+","+tempAccount.getPhoneNum()+","+tempAccount.getDescription()+","+tempAccount.getBalance());
                      Writer.write("\n");
                      Writer.close();
                  }
                  catch(IOException ioe){
                      System.err.println("You done goofed");
                  }
                  //update line
              }
              else{
                  try{ //Rewrite file.
                      FileWriter Writer = new FileWriter("SaveFile.txt",true);
                      Writer.write(current.getName()+","+current.getEmail()+","+current.getPhoneNum()+","+current.getDescription()+","+current.getBalance());
                      Writer.write("\n");
                      Writer.close();
                  }
                  catch(IOException ioe){
                      System.err.println("You done goofed");
                  }
              }
          }
      }

      public void updateD(ArrayList<Account> accountArray, String accountToView) {
          Iterator<Account> it = accountArray.iterator();
          try{
              FileWriter myWriter = new FileWriter("SaveFile.txt",false); // Delete file to be overwritten later.
              myWriter.write("");
              myWriter.close();
          }
          catch(IOException ioe){
              System.err.println("You done goofed");
          }

          while (it.hasNext()){
              Account current = it.next();
              if(accountToView.equalsIgnoreCase(current.toString()) || current.toString().equalsIgnoreCase("Master Account")){
                  Account tempAccount = new Account(current.getName(),current.getEmail(),current.getPhoneNum(),current.getDescription());
                  double tempBalance = current.getBalance() + this.amount;
                  tempAccount.setBalance(tempBalance);
                  it.equals(tempAccount);
                  try{ //Rewrite file.
                      FileWriter Writer = new FileWriter("SaveFile.txt",true);
                      Writer.write(tempAccount.getName()+","+tempAccount.getEmail()+","+tempAccount.getPhoneNum()+","+tempAccount.getDescription()+","+tempAccount.getBalance());
                      Writer.write("\n");
                      Writer.close();
                  }
                  catch(IOException ioe){
                      System.err.println("You done goofed");
                  }
                  //update line
              }
              else{
                  try{ //Rewrite file.
                      FileWriter Writer = new FileWriter("SaveFile.txt",true);
                      Writer.write(current.getName()+","+current.getEmail()+","+current.getPhoneNum()+","+current.getDescription()+","+current.getBalance());
                      Writer.write("\n");
                      Writer.close();
                  }
                  catch(IOException ioe){
                      System.err.println("You done goofed");
                  }
              }
          }
      }


}
