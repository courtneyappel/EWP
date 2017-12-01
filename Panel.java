import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;
@SuppressWarnings("serial")
public class Panel extends JPanel {
	//variable inits
	ArrayList<Account> accountArray = new ArrayList<Account>();
	ArrayList<Transaction> transactionArray = new ArrayList<Transaction>();

	String newAccountname,newAccountemail,newAccountphoneNum,newAccountdescription;
	String newWithdrawalName, newWithdrawalDate, newWithdrawalAmount, newWithdrawalAccount;
	String newDepositName, newDepositDate, newDepositAmount, newDepositAccount;
	String accountToView;
	String codeChoice;
	double newAccountBalance;

	String user = "csadmin";
	String pw = "csci323";
	boolean loggedIn = false;

	JLabel top,bottom,dMessage,wMessage,enterInCredentials,listOfAccounts,accountInfo,userLabel,pwLabel, codeLabel;
	JButton home,logout,login,account,deposit,withdrawal,newAccount,viewAccount,deletAccount,depositButton,withdrawalButton;
	JButton submitAccountInfo,displayEnteredInfo,deleteSelectedAccount,confirmDeletion,exitApp,newCodeButton;
	JTextField username,dAmount,dAccount, dName,wAmount, wAccount, wName,name,email,phoneNum,description,wDate,dDate,newCodeBox;
	JCheckBox check;
	JComboBox<Account> accountList;
	JComboBox<String> codeList, cbAccount;
	//JCheckBox cc;
	JPasswordField password;
	JPanel topPanel = new JPanel();//(imageLabel now replaces topPanel)
	JPanel loginPanel = new JPanel();
	JPanel homePanel = new JPanel();
	JPanel accountPanel = new JPanel();
	JPanel depositPanel = new JPanel();
	JPanel withdrawalPanel = new JPanel();
	JPanel accountCreationPanel = new JPanel();
	JPanel accountViewPanel = new JPanel();
	Date myDate = new Date();


	String[] codez = new String[50];
	int numCodez = 0;
	//String fileName;
    //for header Image and Buttons (imageLabel now replaces topPanel)
    ImageIcon backgroundPic = new ImageIcon("headerImage2.png");
	JLabel imageLabel = new JLabel();
	ImageIcon ewpLogo =new ImageIcon("ewpLogo.png");
	JLabel logoLabel = new JLabel();
    //start of the main panel
	public Panel()
	{
	     accountToView ="";
			 codeChoice="";
        //main panel looks
        setBackground(Color.lightGray);
        setPreferredSize(new Dimension(500, 500));
        setLayout(new BorderLayout());

		//read in the save file
	    initFile();
		//create the header and footer
		createHeaderFooter();
		//create the login screen
		createLogin();
		//create the home screen
		createHomeScreen();
		//create the withdrawal Panel
		createWithdrawPanel();
		//create the deposit Panel
		createDepositPanel();
		//create the Account Panel
		createAccountPanel();
		//create the accountCreationPanel
		createCreationPanel();
		//create the accountViewPanel
		createViewPanel();
		//Finally, Apply custom visuals for buttons and text fields
		setAll();

	}
	//end of main "Panel()"


	//Methods Used to Construct the Panels

	//broad method to apply visual settings
	public void setAll() {
	    //Here are visual settings for things like buttons, textfields...etc
        setButton(home); setButton(logout); setButton(login);
        setButton(account); setButton(deposit); setButton(withdrawal);
        setButton(newAccount);
        setButton(submitAccountInfo); setButton(displayEnteredInfo);
        setButton(deleteSelectedAccount); setButton(confirmDeletion);
        setButton(exitApp);
				setButton(newCodeButton);

        setTextField(username);
        setTextField(name); setTextField(email);
        setTextField(phoneNum); setTextField(description);

        setTextField(dName);
        setTextField(dAmount);
        setTextField(wDate);
        setTextField(dDate);
        setButton(depositButton);

        setTextField(wName);
				setTextField(newCodeBox);
        setTextField(wAmount);

        setButton(withdrawalButton);

        setPasswordField(password);
        setComboBoxAccount(accountList);
        setComboBoxString(codeList);

	}

	public void setTextField(JTextField field){
		field.setPreferredSize( new Dimension( 300, 42 ) );
		field.setMaximumSize( new Dimension( 450, 42 ) );
		field.setFont(new Font("Tahoma", Font.BOLD, 14));
		field.addFocusListener(new FocusListener(){ //Sets the text to null on click
        public void focusGained(FocusEvent e){
            field.setText("");
        }
				public void focusLost(FocusEvent e) {

				}
    });
	}

	public void setPasswordField(JPasswordField field){
			field.setPreferredSize( new Dimension( 300, 42 ) );
			field.setMaximumSize( new Dimension( 450, 42 ) );
			field.setFont(new Font("Tahoma", Font.BOLD, 14));
			field.addFocusListener(new FocusListener(){ //Sets the text to null on click
	        @Override
	        public void focusGained(FocusEvent e){
	            field.setText("");
	        }

					@Override
					public void focusLost(FocusEvent e) {

					}
	    });
		}

	public void setComboBoxAccount(JComboBox<Account> field) {
	     field.setPreferredSize( new Dimension( 200, 42 ) );
	        field.setMaximumSize( new Dimension( 450, 42 ) );
	        field.setFont(new Font("Tahoma", Font.BOLD, 14));
	        Cursor cursor = new Cursor(Cursor.HAND_CURSOR);
	        field.setCursor(cursor);
	        //field.setBackground(Color.LIGHT_GRAY);
	}

    public void setComboBoxString(JComboBox<String> field) {
         field.setPreferredSize( new Dimension( 200, 42 ) );
            field.setMaximumSize( new Dimension( 450, 42 ) );
            field.setFont(new Font("Tahoma", Font.BOLD, 14));
            Cursor cursor = new Cursor(Cursor.HAND_CURSOR);
            field.setCursor(cursor);
            //field.setBackground(Color.LIGHT_GRAY);
    }

    public void setButton(JButton testButton){

				testButton.setOpaque(true);
				testButton.setBorderPainted(false);
				testButton.setBackground(Color.darkGray);
				testButton.setForeground(Color.WHITE);
				testButton.setFocusPainted(false);
				testButton.setFont(new Font("Tahoma", Font.BOLD, 12));

		}

    //Manually updates the array for accounts when needed
	public void updateAccountArray() {
	    try {
	    accountArray.clear();
	    accountList.removeAllItems();
        File myFile = new File("SaveFile.txt");
        Scanner myScan = new Scanner(myFile);
        while(myScan.hasNextLine()){
            String line = myScan.nextLine();
            Scanner lineScan = new Scanner(line);
            lineScan.useDelimiter(",");
             newAccountname = lineScan.next();
             newAccountemail = lineScan.next();
             newAccountphoneNum = lineScan.next();
             newAccountdescription = lineScan.next();
             newAccountBalance = lineScan.nextDouble();

            Account myAccount = new Account(newAccountname, newAccountemail,newAccountphoneNum,newAccountdescription);
            myAccount.setBalance(newAccountBalance);
            accountArray.add(myAccount);
            //accountList.addItem(myAccount);
            lineScan.close();
        }
        accountList = new JComboBox(accountArray.toArray());
        setComboBoxAccount(accountList);
        myScan.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

	}
	//Manually saves the transaction array with given transaction
	public void saveTransactionArray(ArrayList<Transaction> transactionArray,Transaction transac) {
        try{
            FileWriter Writer = new FileWriter("TransactionFile.txt",true);
            Writer.write(transac.getName()+","+transac.getAccount()+",$"+transac.getAmount()+","+transac.getDate()+","+transac.getType()+", Code:"+transac.getCode());
            Writer.write("\n");
            Writer.close();
        }
        catch(IOException ioe){
            System.err.println("You done goofed");
        }
	}
	//Initializes the save file
	public void initFile() {
            try {
                File myFile = new File("SaveFile.txt");
                Scanner myScan = new Scanner(myFile);
                while(myScan.hasNextLine()){
                    String line = myScan.nextLine();
                    Scanner lineScan = new Scanner(line);
                    lineScan.useDelimiter(",");
                     newAccountname = lineScan.next();
                     newAccountemail = lineScan.next();
                     newAccountphoneNum = lineScan.next();
                     newAccountdescription = lineScan.next();
                     newAccountBalance = lineScan.nextDouble();
                    Account myAccount = new Account(newAccountname, newAccountemail,newAccountphoneNum,newAccountdescription);
                    myAccount.setBalance(newAccountBalance);
                    accountArray.add(myAccount);
                    //accountList.addItem(myAccount);
                    lineScan.close();
                }
                accountList = new JComboBox(accountArray.toArray());
                myScan.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try{
					File myFile = new File("Codes.txt");
					Scanner myScan = new Scanner(myFile);
					while(myScan.hasNextLine()){
							String code = myScan.nextLine();
							codez[numCodez] = code;
							numCodez++;
					}
				}
				catch(FileNotFoundException e){
					e.printStackTrace();
				}
	}
	//Methods to setup all Panel Objects
	public void createHeaderFooter(){
	        imageLabel.setIcon( backgroundPic );
	        imageLabel.setLayout(new BorderLayout());
	        topPanel.setOpaque(false); //
	        topPanel.setBackground(Color.lightGray);
	        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER,20,20));
	        top = new JLabel("Account Manager", JLabel.CENTER);
	        top.setFont(new Font("Tahoma", Font.BOLD, 42));
	        home = new JButton("HOME");
	        home.addActionListener(new homeListener());
	        logout = new JButton("Log Out");
	        logout.addActionListener(new logoutListener());
	        exitApp = new JButton("X");
	        exitApp.addActionListener(new exitListener());
	        topPanel.add(home);
	        topPanel.add(Box.createRigidArea(new Dimension (250,25)));
	        topPanel.add(top);
	        topPanel.add(Box.createRigidArea(new Dimension (175,25)));
	        topPanel.add(logout);
	        topPanel.add(exitApp);
	        imageLabel.add(topPanel);
	        add(imageLabel, BorderLayout.PAGE_START);
			logoLabel.setIcon(ewpLogo);
			logoLabel.setHorizontalAlignment(JLabel.CENTER);
			//logoLabel.setLayout(new BoarderLayout());
	        bottom = new JLabel("Developed with love by EWP 2017", JLabel.CENTER);
	        bottom.setFont(new Font("Tahoma", Font.BOLD, 12));
	        add(bottom, BorderLayout.PAGE_END);
					add(logoLabel, BorderLayout.PAGE_END);
	}

	public void createLogin() {
		loginPanel.setBackground(Color.lightGray);
		loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
		Font myFont = new Font("Tahoma", Font.BOLD, 24);
		loginPanel.setBackground(Color.lightGray);
		userLabel = new JLabel("Username");
		userLabel.setFont(myFont);
		username = new JTextField();
		pwLabel = new JLabel("Password");
		pwLabel.setFont(myFont);
		password = new JPasswordField();
		login = new JButton("Log In");

		login.addActionListener(new loginListener());

		userLabel.setAlignmentX(CENTER_ALIGNMENT);
		username.setAlignmentX(CENTER_ALIGNMENT);
		pwLabel.setAlignmentX(CENTER_ALIGNMENT);
		password.setAlignmentX(CENTER_ALIGNMENT);
		login.setAlignmentX(CENTER_ALIGNMENT);

		loginPanel.add(Box.createRigidArea(new Dimension (0,25)));
		loginPanel.add(userLabel);
		loginPanel.add(username);
		loginPanel.add(Box.createRigidArea(new Dimension (0,25)));
		loginPanel.add(pwLabel);
		loginPanel.add(password);
		loginPanel.add(Box.createRigidArea(new Dimension (0,25)));
		loginPanel.add(login);

		add(loginPanel, BorderLayout.CENTER);
	}

	public void createHomeScreen(){
	        homePanel.setBackground(Color.lightGray);
	        homePanel.setLayout(new BoxLayout(homePanel, BoxLayout.Y_AXIS));

	        account = new JButton("ACCOUNTS"){
	            {
	                setSize(200,100);
	                setMaximumSize(getSize());
	            }

	        };
	        account.setAlignmentX(JButton.CENTER_ALIGNMENT);
	        account.addActionListener(new accountListener());

	        deposit = new JButton("NEW DEPOSIT"){
	            {
	                setSize(200,100);
	                setMaximumSize(getSize());
	            }

	        };
	        deposit.setAlignmentX(JButton.CENTER_ALIGNMENT);
	        deposit.addActionListener(new depositListener());

	        withdrawal = new JButton("NEW WITHDRAWAL"){
	            {
	                setSize(200,100);
	                setMaximumSize(getSize());
	            }

	        };
	        withdrawal.setAlignmentX(JButton.CENTER_ALIGNMENT);
	        withdrawal.addActionListener(new withdrawalListener());

	        homePanel.add(Box.createRigidArea(new Dimension (0,25)));
	        homePanel.add(account);
	        homePanel.add(Box.createRigidArea(new Dimension (0,25)));
	        homePanel.add(deposit);
	        homePanel.add(Box.createRigidArea(new Dimension (0,25)));
	        homePanel.add(withdrawal);
	}

	public void createWithdrawPanel() {
	       withdrawalPanel.setBackground(Color.lightGray);
	        withdrawalPanel.setLayout(new BoxLayout(withdrawalPanel, BoxLayout.Y_AXIS));
	        wName = new JTextField("Your Name");
	        cbAccount = new JComboBox<String>();
	        for(Account i: accountArray) {
	            cbAccount.addItem(i.getName());
	        }
	        setComboBoxString(cbAccount);
	        wAmount = new JTextField("Withdrawal Amount");
	        wDate = new JTextField(myDate.toString());
	        wDate.setFont(new Font("Tahoma", Font.BOLD, 16));
	        withdrawalButton = new JButton("Withdraw");
	        wMessage = new JLabel("");

			codeLabel = new JLabel("Select Withdrawal Code");
			codeList = new JComboBox<String>(codez);
			newCodeButton = new JButton("Add new code");
			newCodeButton.addActionListener(new codeButtonListener());
			newCodeBox = new JTextField("Type in hear to add a new code.");

	        withdrawalButton.addActionListener(new withdrawalButtonListener());

	        wName.setAlignmentX(CENTER_ALIGNMENT);
	        cbAccount.setAlignmentX(CENTER_ALIGNMENT);
	        wAmount.setAlignmentX(CENTER_ALIGNMENT);
	        wDate.setAlignmentX(CENTER_ALIGNMENT);
	        //codeLabel.setAlignmentX(JButton.CENTER_ALIGNMENT);
	        codeList.setAlignmentX(CENTER_ALIGNMENT);
					newCodeButton.setAlignmentX(CENTER_ALIGNMENT);
					newCodeBox.setAlignmentX(CENTER_ALIGNMENT);
	        withdrawalButton.setAlignmentX(CENTER_ALIGNMENT);
	        wMessage.setAlignmentX(CENTER_ALIGNMENT);

	        withdrawalPanel.add(Box.createRigidArea(new Dimension (0,25)));
	        withdrawalPanel.add(wName);
	        withdrawalPanel.add(cbAccount);
			withdrawalPanel.add(accountList);
	        withdrawalPanel.add(wAmount);
	        withdrawalPanel.add(wDate);
			//withdrawalPanel.add(codeLabel);
			withdrawalPanel.add(codeList);
			withdrawalPanel.add(Box.createRigidArea(new Dimension (0,25)));
	        withdrawalPanel.add(withdrawalButton);
					withdrawalPanel.add(newCodeBox);
					withdrawalPanel.add(newCodeButton);
	        withdrawalPanel.add(wMessage);
	}

	public void createDepositPanel() {
	       depositPanel.setBackground(Color.lightGray);
	        depositPanel.setLayout(new BoxLayout(depositPanel, BoxLayout.Y_AXIS));
	        dName = new JTextField("Your Name");
            cbAccount = new JComboBox<String>();
            for(Account i: accountArray) {
                cbAccount.addItem(i.getName());
            }
            setComboBoxString(cbAccount);
	        dAmount = new JTextField("Deposit Amount");
	        dDate = new JTextField(myDate.toString());
	        dDate.setFont(new Font("Tahoma", Font.BOLD, 16));
	        depositButton = new JButton("Submit Deposit");
	        dMessage = new JLabel("");
			//codeLabel = new JLabel("<html> Select your desired code:");
			//codeList = new JComboBox<String>(codez);
	        //cc = new JCheckBox("Credit Card");
	        //cc.setMnemonic(KeyEvent.VK_C);
	        //cc.setSelected(true);
	        check = new JCheckBox("Check? If Credit; deselect");
	        //check.setMnemonic(KeyEvent.VK_C);
	        check.setSelected(false);

	        depositButton.addActionListener(new depositButtonListener());
			codeList.addActionListener(new codeListener());


	        dName.setAlignmentX(CENTER_ALIGNMENT);
            cbAccount.setAlignmentX(CENTER_ALIGNMENT);
            dAmount.setAlignmentX(CENTER_ALIGNMENT);
            dDate.setAlignmentX(CENTER_ALIGNMENT);
            check.setAlignmentX(CENTER_ALIGNMENT);
            //codeLabel.setAlignmentX(JButton.CENTER_ALIGNMENT);
            //codeList.setAlignmentX(JButton.CENTER_ALIGNMENT);
            depositButton.setAlignmentX(CENTER_ALIGNMENT);
            dMessage.setAlignmentX(CENTER_ALIGNMENT);

            depositPanel.add(Box.createRigidArea(new Dimension (0,25)));
	        depositPanel.add(dName);
	        depositPanel.add(cbAccount);
	        depositPanel.add(dAmount);
	        depositPanel.add(dDate);
			//depositPanel.add(codeLabel);
			//depositPanel.add(codeList);
	        //depositPanel.add(cc);
	        depositPanel.add(Box.createRigidArea(new Dimension (0,25)));
	        depositPanel.add(check);
	        depositPanel.add(Box.createRigidArea(new Dimension (0,25)));
	        depositPanel.add(depositButton);
	        depositPanel.add(dMessage);
	}

	public void createAccountPanel() {
	       accountPanel.setBackground(Color.lightGray);
	        accountPanel.setLayout(new BoxLayout(accountPanel, BoxLayout.Y_AXIS));

	        newAccount = new JButton("NEW ACCOUNT"){
	            {
	                setSize(200,100);
	                setMaximumSize(getSize());
	            }

	        };
	        newAccount.setAlignmentX(CENTER_ALIGNMENT);
	        newAccount.addActionListener(new newAccountListener());

	        accountPanel.add(Box.createRigidArea(new Dimension (0,25)));
	        accountPanel.add(newAccount);

	}

	public void createCreationPanel() {
	       accountCreationPanel.setBackground(Color.lightGray);
	        accountCreationPanel.setLayout(new BoxLayout(accountCreationPanel, BoxLayout.Y_AXIS));
	        name = new JTextField("Name");
	        email = new JTextField("Email");
	        phoneNum = new JTextField("Phone Number");
	        description = new JTextField("Description");
	        enterInCredentials = new JLabel("Please enter in the information.");
	        submitAccountInfo = new JButton("Submit");
	        submitAccountInfo.addActionListener(new submitAccountInfoListener());

	        enterInCredentials.setAlignmentX(CENTER_ALIGNMENT);
	        name.setAlignmentX(CENTER_ALIGNMENT);
	        email.setAlignmentX(CENTER_ALIGNMENT);
	        phoneNum.setAlignmentX(CENTER_ALIGNMENT);
	        description.setAlignmentX(CENTER_ALIGNMENT);
	        submitAccountInfo.setAlignmentX(CENTER_ALIGNMENT);
            accountCreationPanel.add(Box.createRigidArea(new Dimension (0,25)));

	        accountCreationPanel.add(enterInCredentials);
	          accountCreationPanel.add(Box.createRigidArea(new Dimension (0,25)));
	        accountCreationPanel.add(name);
	        accountCreationPanel.add(email);
	        accountCreationPanel.add(phoneNum);
	        accountCreationPanel.add(description);
	          accountCreationPanel.add(Box.createRigidArea(new Dimension (0,25)));
	        accountCreationPanel.add(submitAccountInfo);
	}

	public void createViewPanel() {
	        accountViewPanel.setBackground(Color.lightGray);
	        accountViewPanel.setLayout(new BoxLayout(accountViewPanel, BoxLayout.Y_AXIS));

	        listOfAccounts = new JLabel("<html>Here is a list of current accounts.<br>");
	        accountInfo = new JLabel("");
	        displayEnteredInfo = new JButton("View selected account");
	        deleteSelectedAccount = new JButton("Delete Account");
	        confirmDeletion = new JButton("Confirm Delete");

	        confirmDeletion.addActionListener(new confirmDeletionListener());
	        deleteSelectedAccount.addActionListener(new deleteSelectedAccountListener());
	        displayEnteredInfo.addActionListener(new displayEnteredInfoListener());

	        accountList.setAlignmentX(CENTER_ALIGNMENT);
	        accountInfo.setAlignmentX(CENTER_ALIGNMENT);
	        displayEnteredInfo.setAlignmentX(CENTER_ALIGNMENT);
	        listOfAccounts.setAlignmentX(CENTER_ALIGNMENT);
	        deleteSelectedAccount.setAlignmentX(CENTER_ALIGNMENT);
	        confirmDeletion.setAlignmentX(CENTER_ALIGNMENT);

            accountViewPanel.add(Box.createRigidArea(new Dimension (0,25)));
	        //accountViewPanel.add(listOfAccounts);
	        accountViewPanel.add(accountList);
	        accountViewPanel.add(accountInfo);
	        accountViewPanel.add(Box.createRigidArea(new Dimension (0,25)));
	        accountViewPanel.add(displayEnteredInfo);
            accountViewPanel.add(Box.createRigidArea(new Dimension (0,25)));
            accountViewPanel.add(deleteSelectedAccount);

	        accountPanel.add(accountViewPanel);
	}
	//End of creation for panel objects


	//Methods for Listeners

	//final action for perm deleting an account
  public void deleteAccountFinal() {
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
          if(accountToView.equalsIgnoreCase(current.toString())){
              it.remove();
              accountList.removeItem(current);
              System.out.println(accountToView + " was removed.");
              accountInfo.setText(accountToView + " was removed.");
              accountViewPanel.remove(confirmDeletion);
              repaint();
              revalidate();
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

  public void codeSelection(){
		codeChoice = String.valueOf(codeList.getSelectedItem());
		System.out.println(codeChoice);
		codeLabel.setText(codeChoice+"?");
	}
	//changes window to confirm account delete
  public void deleteAccountSelect() {

      Account myAccount = (Account) accountList.getSelectedItem();
      accountToView = String.valueOf(myAccount);
			if(!accountToView.equalsIgnoreCase("Master Account")){
				//accountList.removeItem(myAccount);
	      //String accountToDisplay = "";
	      Boolean foundAccount = false;
	      for(Account name:accountArray){
	              if(name.toString().equalsIgnoreCase(accountToView)){ // When it gets the account selected it displays it.
	                  foundAccount = true;
	              }
	      }
	      if(foundAccount){
	          accountViewPanel.removeAll();
	          accountInfo.setText("Warning you are about to delete an account.");
	          accountViewPanel.add(accountInfo);
	          accountViewPanel.add(confirmDeletion);
	          revalidate();
	          repaint();
	      }
	      else{
	          listOfAccounts.setText(listOfAccounts.getText() + "<html><br>I'm sorry but the account you entered doesn't exist. Please try again. </html>");

	      }
			}
			else{
					accountInfo.setText("You can not delete main account");
			}

  }
    //changes window to display account information from selected account
  public void viewAccountAction() {

      accountToView = String.valueOf(accountList.getSelectedItem());
      String accountToDisplay = "";
      Boolean foundAccount = false;
      for(Account name:accountArray){
              if(name.toString().equalsIgnoreCase(accountToView)){ // When it gets the account selected it displays it.
                  foundAccount = true;
                  accountToDisplay = name.getAllInfo();
									System.out.println("Transaction History");

              }
      }
      if(foundAccount){
          accountViewPanel.removeAll();
          accountInfo.setText(accountToDisplay);
          accountViewPanel.add(accountInfo);
          revalidate();
          repaint();
      }
      else{
          listOfAccounts.setText(listOfAccounts.getText() + "<html><br>I'm sorry but the account you entered doesn't exist. Please try again. </html>");

      }
  }
    //Main Action to go to the home menu
  public void goHome() {
      if(loggedIn == true)
      {
          System.out.println("HOME");
          removeAll();
          name.setText("Name"); //I have this on here and the log out button so that it will reset the text in the boxes after you leave the page.
          email.setText("Email");
          phoneNum.setText("Phone Number");
          description.setText("Description");
          accountCreationPanel.setBackground(Color.lightGray);
          add(bottom, BorderLayout.PAGE_END);
					add(logoLabel, BorderLayout.PAGE_END);
          add(homePanel, BorderLayout.CENTER);
          add(imageLabel, BorderLayout.PAGE_START);

          accountViewPanel.removeAll();
          accountInfo.setText("");
          accountViewPanel.add(Box.createRigidArea(new Dimension (0,25)));
          accountViewPanel.add(accountList);
          accountViewPanel.add(accountInfo);
          accountViewPanel.add(Box.createRigidArea(new Dimension (0,25)));
          accountViewPanel.add(displayEnteredInfo);
          accountViewPanel.add(Box.createRigidArea(new Dimension (0,25)));
          accountViewPanel.add(deleteSelectedAccount);


          accountToView = "";
          dMessage.setText("");
          depositButton.setEnabled(true);
          wMessage.setText("");
          withdrawalButton.setEnabled(true);
          submitAccountInfo.setEnabled(true);
          repaint();
      }
  }
    //Final action to enter a new account into the program
  public void submitAccountAction() {
      //if info is invalid
			boolean existingName = false;
			for(Account accountNames:accountArray){
				if(accountNames.toString().equalsIgnoreCase(name.getText())){
					existingName = true;
				}
			}
      if(name.getText().equalsIgnoreCase("Name") || name.getText().equalsIgnoreCase("") //This is a messy way of doing it but, hey it works.
      || email.getText().equalsIgnoreCase("Email") || email.getText().equalsIgnoreCase("")
      || phoneNum.getText().equalsIgnoreCase("Phone Number") || phoneNum.getText().equalsIgnoreCase("")
      || description.getText().equalsIgnoreCase("Description") || description.getText().equalsIgnoreCase("")){
          accountCreationPanel.setBackground(Color.red);
          enterInCredentials.setText("Please enter in all the information.");
          System.out.println("Invalid information");
      }

      else if(!existingName){
          accountCreationPanel.setBackground(Color.lightGray);
          //setBackground(Color.darkGray);
          newAccountname = name.getText();
          newAccountemail = email.getText();
          newAccountphoneNum = phoneNum.getText();
          newAccountdescription = description.getText();
          Account myAccount = new Account(newAccountname, newAccountemail,newAccountphoneNum,newAccountdescription);
          accountArray.add(myAccount);
          accountList.addItem(myAccount);
          System.out.println("Account created");
          submitAccountInfo.setEnabled(false);
          try{
              FileWriter myWriter = new FileWriter("SaveFile.txt",true);
              myWriter.write(newAccountname+","+newAccountemail+","+newAccountphoneNum+","+newAccountdescription+",0.0");
              myWriter.write("\n");
              myWriter.close();
          }
          catch(IOException ioe){
              System.err.println("You done goofed");
          }

      }
			else{
				accountCreationPanel.setBackground(Color.red);
				enterInCredentials.setText("An account with this name already exists.");
			}
  }
    //changes the window to prepare creating a new account
  public void newAccountSetup() {
      System.out.println("making a new account");
      removeAll();
      add(bottom, BorderLayout.PAGE_END);
	  add(logoLabel, BorderLayout.PAGE_END);
      add(accountCreationPanel, BorderLayout.CENTER);
      add(imageLabel, BorderLayout.PAGE_START);
      revalidate();
      repaint();
  }
    //action for logging the user out
  public void logoutAction() {
      if(loggedIn == true)
      {
          loggedIn = false;
          removeAll();
          name.setText("Name"); //I have this on here and the home button so that it will reset the text in the boxes after you leave the page.
          email.setText("Email");
          phoneNum.setText("Phone Number");
          description.setText("Description");
          accountCreationPanel.setBackground(Color.lightGray);

          accountViewPanel.removeAll();
          accountInfo.setText("");
          accountViewPanel.add(Box.createRigidArea(new Dimension (0,25)));
          accountViewPanel.add(accountList);
          accountViewPanel.add(accountInfo);
          accountViewPanel.add(Box.createRigidArea(new Dimension (0,25)));
          accountViewPanel.add(displayEnteredInfo);
          accountViewPanel.add(Box.createRigidArea(new Dimension (0,25)));
          accountViewPanel.add(deleteSelectedAccount);

          add(bottom, BorderLayout.PAGE_END);
					add(logoLabel, BorderLayout.PAGE_END);
          add(loginPanel, BorderLayout.CENTER);
          add(imageLabel, BorderLayout.PAGE_START);
          repaint();


      }
  }
    //action for logging the user in
  public void loginAction() {
      String userEntry = username.getText();
      String pwEntry = password.getText();

      username.setText("");
      password.setText("");

      if(userEntry.equalsIgnoreCase(user) && pwEntry.equalsIgnoreCase(pw))
      {
          loggedIn = true;
          userEntry = null;
          pwEntry = null;
          setBackground(Color.lightGray);
          loginPanel.setBackground(Color.lightGray);

          remove(loginPanel);
          add(homePanel, BorderLayout.CENTER);
          revalidate();
          repaint();
      }
      else
      {
          setBackground(Color.red);
          loginPanel.setBackground(Color.red);
      }
  }
    //Setups of the main HomePage for navigation
  public void accountSetup() {
      System.out.println("Account");
      removeAll();
      add(bottom, BorderLayout.PAGE_END);
	  add(logoLabel, BorderLayout.PAGE_END);
      add(imageLabel, BorderLayout.PAGE_START);
      add(accountPanel, BorderLayout.CENTER);
      revalidate();
      repaint();
  }
    //changes the window to prepare new deposits
  public void depositSetup(){
      System.out.println("Deposit");
      removeAll();
      add(bottom, BorderLayout.PAGE_END);
	  add(logoLabel, BorderLayout.PAGE_END);
      add(imageLabel, BorderLayout.PAGE_START);
      add(depositPanel, BorderLayout.CENTER);
      dName.setText("Your Name");
      dAmount.setText("Deposit Amount");
      dDate.setText(myDate.toString());
      depositButton.setText("Submit Deposit");
      revalidate();
      repaint();
  }
    //final action to enter in the deposit
  public void depositConfirm(){

      if (dName.getText().equalsIgnoreCase("") || dAmount.getText().equalsIgnoreCase("") ||
      dName.getText().equalsIgnoreCase("Your Name") || dAmount.getText().equalsIgnoreCase("Deposit Amount")){
          dMessage.setText("Please Fill All Fields");
          depositPanel.setBackground(Color.red);
      }
      else{
          System.out.println("Deposit has been Made");
          dMessage.setText("Congrats! You have made a deposit!");
          codeLabel.setText("");
          accountToView = (String) cbAccount.getSelectedItem();
          depositPanel.setBackground(Color.lightGray);
          newDepositName = dName.getText();
          newDepositAccount = (String) cbAccount.getSelectedItem();
          newDepositAmount = dAmount.getText();
          double tempDA = Double.parseDouble(newDepositAmount);
          newDepositDate = dDate.getText();
          Transaction myTransaction;
          if(check.isSelected()) {
              System.out.println("awd");
              tempDA = tempDA*(.92);
              myTransaction = new Transaction(newDepositName, newDepositAccount, tempDA, newDepositDate, false, accountArray, accountToView,true, false, " no applicable code ");
          }
          else {
        	  tempDA = tempDA*(.88);
              System.out.println("dwa");
              myTransaction = new Transaction(newDepositName, newDepositAccount, tempDA, newDepositDate, false, accountArray, accountToView,false, true, " no applicable code ");
          }
          //System.out.println(myTransaction.getAllInfo());
          //Different transactions based on either Credit or Check/ note the 2 booleans at the end.
          transactionArray.add(myTransaction);
          saveTransactionArray(transactionArray,myTransaction);
          System.out.println("Transaction created");
          System.out.println("Thank you, "+newDepositName+". The amount of $"+newDepositAmount+" has been added to account "+newDepositAccount+" on "+newDepositDate);
          dMessage.setText("<html>Thank you, "+newDepositName+".<br> The amount of $"+newDepositAmount+" has been added to account "+newDepositAccount+" on "+newDepositDate);
          depositButton.setEnabled(false);
          updateAccountArray();
      }
  }
    //changes the window to prepare new withdraws
  public void withdrawSetup() {
      System.out.println("Withdrawal");
      removeAll();
      add(bottom, BorderLayout.PAGE_END);
	  add(logoLabel, BorderLayout.PAGE_END);
      add(imageLabel, BorderLayout.PAGE_START);
      add(withdrawalPanel, BorderLayout.CENTER);
      wName.setText("Your Name");
      wAmount.setText("Withdrawal Amount");
      wDate.setText(myDate.toString());
      withdrawalButton.setText("Withdraw");
      revalidate();
      repaint();
  }

	public void addNewCode(){
		System.out.println("Work fucker");
	}
    //final action to create a withdrawal
  public void withdrawConfirm() {


      if (wName.getText().equalsIgnoreCase("") || wAmount.getText().equalsIgnoreCase("") ||
      wName.getText().equalsIgnoreCase("Your Name") || wAmount.getText().equalsIgnoreCase("Withdrawal Amount")){

          wMessage.setText("Please Fill All Fields");
          withdrawalPanel.setBackground(Color.red);
      }
      else{
          System.out.println("Withdrawal has been Made");
          wMessage.setText("Congrats! You have made a withdrawal!");
          accountToView = (String) cbAccount.getSelectedItem();
          String code = (String) codeList.getSelectedItem();
          withdrawalPanel.setBackground(Color.lightGray);
          newWithdrawalName = wName.getText();
          newWithdrawalAccount = (String) cbAccount.getSelectedItem();
          newWithdrawalAmount = wAmount.getText();
          double tempWA = Double.parseDouble(newWithdrawalAmount);
          newWithdrawalDate = wDate.getText();
          Transaction myTransaction = new Transaction(newWithdrawalName, newWithdrawalAccount, tempWA, newWithdrawalDate, true, accountArray, accountToView, false, false, code);
          transactionArray.add(myTransaction);
          saveTransactionArray(transactionArray,myTransaction);
          System.out.println("Transaction created");
          System.out.println("Thank you, "+newWithdrawalName+". The amount of $"+newWithdrawalAmount+" has been withdrawn from the account "+newWithdrawalAccount+" on "+newWithdrawalDate);
          wMessage.setText("<html>Thank you, "+newWithdrawalName+".<br> The amount of $"+newWithdrawalAmount+" has been withdrawn from the account "+newWithdrawalAccount+" on "+newWithdrawalDate);
          withdrawalButton.setEnabled(false);
          updateAccountArray();
      }
  }

    //Uses methods shown above.
	//Button listeners | These contain the methods shown above.
	private class confirmDeletionListener implements ActionListener{
		public void actionPerformed (ActionEvent enver){
		    deleteAccountFinal();
		}
	}

	private class codeListener implements ActionListener{
		public void actionPerformed (ActionEvent event){
			codeSelection();
		}
	}

	private class deleteSelectedAccountListener implements ActionListener{//button to delete selected account.
		public void actionPerformed (ActionEvent event){
		    deleteAccountSelect();
		}
	}
	private class displayEnteredInfoListener implements ActionListener { //button to display the enteredAccount
				public void actionPerformed (ActionEvent event){
				    viewAccountAction();
				}
	}

	private class homeListener implements ActionListener // Goes to the home page
	{
		public void actionPerformed (ActionEvent event)
        {
		    goHome();
        }
	}

	private class submitAccountInfoListener implements ActionListener{//when you submit the information to create an account
			public void actionPerformed(ActionEvent event){
			    submitAccountAction();
			}
	}
	private class newAccountListener implements ActionListener { //creates a new account
			public void actionPerformed (ActionEvent event){
			    newAccountSetup();
			}
	}

	private class logoutListener implements ActionListener // Logs out
	{
		public void actionPerformed (ActionEvent event)
        {
		    logoutAction();
        }
	}

	private class loginListener implements ActionListener // Logs in
	{
		public void actionPerformed (ActionEvent event)
        {
		    loginAction();
		}
	}

	private class accountListener implements ActionListener //Button for accessing accounts page
	{
		public void actionPerformed (ActionEvent event)
        {
		    accountSetup();
        }
	}

	private class exitListener implements ActionListener
	{
        public void actionPerformed(ActionEvent e)
        {
            System.exit(0);
        }
	}

	//deposit page setup
	private class depositListener implements ActionListener{
		public void actionPerformed (ActionEvent event){
		    depositSetup();
        }
	}
	//deposit confirmation action
	private class depositButtonListener implements ActionListener{
		public void actionPerformed (ActionEvent event){
		    depositConfirm();
		}
	}
	//withdrawal page setup
	private class withdrawalListener implements ActionListener{
		public void actionPerformed (ActionEvent event) {
            withdrawSetup();
		}
	}
	//withdrawal confirmation action
	private class withdrawalButtonListener implements ActionListener{
		public void actionPerformed (ActionEvent event){
           withdrawConfirm();
		}
	}
	private class codeButtonListener implements ActionListener{
		public void actionPerformed (ActionEvent event){
           addNewCode();
		}
	}


}
