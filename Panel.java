import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;
import java.io.IOException;

public class Panel extends JPanel {
	//variable inits
	ArrayList<Account> accountArray = new ArrayList();
	ArrayList<Transaction> transactionArray = new ArrayList();
	
	String newAccountname,newAccountemail,newAccountphoneNum,newAccountdescription;
	String newWithdrawalName, newWithdrawalDate, newWithdrawalAmount, newWithdrawalAccount;
	String newDepositName, newDepositDate, newDepositAmount, newDepositAccount;
	double newAccountBalance;

	String user = "csadmin";
	String pw = "csci323";
	boolean loggedIn = false;

	JLabel top,bottom;

	JButton home,logout,login;

	JTextField username,password;

	JButton account,deposit,withdrawal;

	JPanel topPanel = new JPanel();//(imageLabel now replaces topPanel)
	JPanel loginPanel = new JPanel();
	JPanel homePanel = new JPanel();

	JPanel accountPanel = new JPanel();
	JButton newAccount,viewAccount,deletAccount;

	JPanel depositPanel = new JPanel();
	JTextField dAmount, dDate, dAccount, dName;
	JCheckBox cc, check;
	JButton depositButton;
	JLabel dMessage;
	JPanel withdrawalPanel = new JPanel();
	JTextField wAmount, wDate, wAccount, wName;
	JButton withdrawalButton;
	JLabel wMessage;

	JPanel accountCreationPanel = new JPanel();
	JLabel enterInCredentials;
	JTextField name,email,phoneNum,description;
	JButton submitAccountInfo;

	JPanel accountViewPanel = new JPanel();
	JLabel listOfAccounts,accountInfo;
	JTextField enteredAccount;
	JButton displayEnteredInfo,deleteSelectedAccount;
	JButton confirmDeletion;
	String accountToView;


	String fileName;

  //for header Image and Buttons (imageLabel now replaces topPanel)
  ImageIcon backgroundPic = new ImageIcon("headerImage.png");
	JLabel imageLabel = new JLabel();

 //start of the main panel
	public Panel() throws FileNotFoundException
	{
		//read in file
		fileName = "SaveFile.txt";
		File myFile = new File(fileName);
		Scanner myScan = new Scanner(myFile);
		while(myScan.hasNextLine()){
			String line = myScan.nextLine();
			Scanner lineScan = new Scanner(line);
			lineScan.useDelimiter(",");
		  //TODO: see if this works without a while loop because I don't think it needs one.
			 newAccountname = lineScan.next();
			 newAccountemail = lineScan.next();
			 newAccountphoneNum = lineScan.next();
			 newAccountdescription = lineScan.next();
			 newAccountBalance = lineScan.nextDouble();

			Account myAccount = new Account(newAccountname, newAccountemail,newAccountphoneNum,newAccountdescription);
			myAccount.setBalance(newAccountBalance);
			accountArray.add(myAccount);

		}

		accountToView ="";
		//main panel looks
		setBackground(Color.lightGray);
		setPreferredSize(new Dimension(500, 500));
		setLayout(new BorderLayout());

		//create header
		imageLabel.setIcon( backgroundPic );
		imageLabel.setLayout(new BorderLayout());
    topPanel.setOpaque(false); //
		topPanel.setBackground(Color.lightGray);
		topPanel.setLayout(new FlowLayout());
		top = new JLabel("Account Manager", JLabel.CENTER);
		top.setFont(new Font("Tahoma", Font.BOLD, 24));
		home = new JButton("HOME");
		home.addActionListener(new homeListener());
		logout = new JButton("Log Out");
		logout.addActionListener(new logoutListener());
		topPanel.add(home);
		topPanel.add(top);
		topPanel.add(logout);
		imageLabel.add(topPanel);
		add(imageLabel, BorderLayout.PAGE_START);


		//create footer
		bottom = new JLabel("Developed with love by EWP 2017", JLabel.CENTER);
		bottom.setFont(new Font("Tahoma", Font.BOLD, 12));
		add(bottom, BorderLayout.PAGE_END);

		//create login screen

		loginPanel.setBackground(Color.lightGray);

		username = new JTextField("Username");
		password = new JTextField("Password");
		login = new JButton("Log In");
		login.addActionListener(new loginListener());
		loginPanel.add(username);
		loginPanel.add(password);
		loginPanel.add(login);
		add(loginPanel, BorderLayout.CENTER);
		//remove(loginPanel);




		//create home screen
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

		//withdrawal Panel
		withdrawalPanel.setBackground(Color.lightGray);
		withdrawalPanel.setLayout(new BoxLayout(withdrawalPanel, BoxLayout.Y_AXIS));
		wName = new JTextField("Your Name");
		wAccount = new JTextField("Account Name");
		wAmount = new JTextField("Withdrawal Amount");
		wDate = new JTextField("Date (mm/dd/yyyy)");
		withdrawalButton = new JButton("Withdraw");
		wMessage = new JLabel("");

		withdrawalButton.addActionListener(new withdrawalButtonListener());
		withdrawalPanel.add(wName);
		withdrawalPanel.add(wAccount);
		withdrawalPanel.add(wAmount);
		withdrawalPanel.add(wDate);
		withdrawalPanel.add(withdrawalButton);
		withdrawalPanel.add(wMessage);

		//deposit Panel
		depositPanel.setBackground(Color.lightGray);
		depositPanel.setLayout(new BoxLayout(depositPanel, BoxLayout.Y_AXIS));
		dName = new JTextField("Your Name");
		dAccount = new JTextField("Account Name");
		dAmount = new JTextField("Deposit Amount");
		dDate = new JTextField("Date (mm/dd/yyyy)");
		depositButton = new JButton("Submit Deposit");
		dMessage = new JLabel("");

		cc = new JCheckBox("Credit Card");
    //cc.setMnemonic(KeyEvent.VK_C);
    cc.setSelected(true);
		check = new JCheckBox("Check");
		//check.setMnemonic(KeyEvent.VK_C);
		check.setSelected(false);

		depositButton.addActionListener(new depositButtonListener());
		depositPanel.add(dName);
		depositPanel.add(dAccount);
		depositPanel.add(dAmount);
		depositPanel.add(dDate);

		depositPanel.add(cc);
		depositPanel.add(check);

		depositPanel.add(depositButton);
		depositPanel.add(dMessage);

		//Account Panel
		accountPanel.setBackground(Color.lightGray);
		accountPanel.setLayout(new BoxLayout(accountPanel, BoxLayout.Y_AXIS));

		newAccount = new JButton("New Account"){
			{
				setSize(200,100);
				setMaximumSize(getSize());
			}

		};
		newAccount.setAlignmentX(JButton.CENTER_ALIGNMENT);
		newAccount.addActionListener(new newAccountListener());

		viewAccount = new JButton("View Account"){
			{
				setSize(200,100);
				setMaximumSize(getSize());
			}

		};
		viewAccount.setAlignmentX(JButton.CENTER_ALIGNMENT);
		viewAccount.addActionListener(new viewAccountListener());

		deletAccount = new JButton("Delete Account"){
			{
				setSize(200,100);
				setMaximumSize(getSize());
			}

		};
		deletAccount.setAlignmentX(JButton.CENTER_ALIGNMENT);
		deletAccount.addActionListener(new deleteAccountListener());

		accountPanel.add(Box.createRigidArea(new Dimension (0,25)));
		accountPanel.add(newAccount);
		accountPanel.add(Box.createRigidArea(new Dimension (0,25)));
		accountPanel.add(viewAccount);
		accountPanel.add(Box.createRigidArea(new Dimension (0,25)));
		accountPanel.add(deletAccount);



		//accountCreationPanel
		accountCreationPanel.setBackground(Color.lightGray);
		accountCreationPanel.setLayout(new BoxLayout(accountCreationPanel, BoxLayout.Y_AXIS));
		name = new JTextField("Name");
		email = new JTextField("Email");
		phoneNum = new JTextField("Phone Number");
		description = new JTextField("Description");
		enterInCredentials = new JLabel("Please enter in the information.");
		submitAccountInfo = new JButton("Submit");
		submitAccountInfo.addActionListener(new submitAccountInfoListener());
		accountCreationPanel.add(enterInCredentials);
		accountCreationPanel.add(name);
		accountCreationPanel.add(email);
		accountCreationPanel.add(phoneNum);
		accountCreationPanel.add(description);
		accountCreationPanel.add(submitAccountInfo);


		accountViewPanel.setBackground(Color.lightGray);
		accountViewPanel.setLayout(new BoxLayout(accountViewPanel, BoxLayout.Y_AXIS));
		listOfAccounts = new JLabel("<html>Type in the name of the account you would like.<br> Here is a list of current accounts.<br>");
		accountInfo = new JLabel("");
		enteredAccount = new JTextField("");
		displayEnteredInfo = new JButton("View selected account");
		deleteSelectedAccount = new JButton("Delete Account");
		confirmDeletion = new JButton("Confirm Delete");
		confirmDeletion.addActionListener(new confirmDeletionListener());
		deleteSelectedAccount.addActionListener(new deleteSelectedAccountListener());
		displayEnteredInfo.addActionListener(new displayEnteredInfoListener());
		accountViewPanel.add(listOfAccounts);
		accountViewPanel.add(accountInfo);
		accountViewPanel.add(enteredAccount);
		accountViewPanel.add(displayEnteredInfo);// If you guys think of a better way to do this let me know, it's just the best i could come up with.
		//I have a list of accounts and then you type in the name of the account you want to view and click on the button.


		//Here are visual settings for things like buttons, textfields...etc
		setButton(home); setButton(logout); setButton(login);
		setButton(account); setButton(deposit); setButton(withdrawal);
		setButton(newAccount); setButton(viewAccount); setButton(deletAccount);
		setButton(submitAccountInfo); setButton(displayEnteredInfo);
		setButton(deleteSelectedAccount); setButton(confirmDeletion);

		setTextField(username); setTextField(password);
		setTextField(name); setTextField(email);
		setTextField(phoneNum); setTextField(description);
		setTextField(enteredAccount);

		setTextField(dName);
		setTextField(dAccount);
		setTextField(dAmount);
		setTextField(dDate);
		setButton(depositButton);

		setTextField(wName);
		setTextField(wAccount);
		setTextField(wAmount);
		setTextField(wDate);
		setButton(withdrawalButton);
	}
	//end of main "Panel()"




	//methods for apply visual settings, I will focus on creating seperate classes for these so its not cluttered
	public void setTextField(JTextField field){
		field.setPreferredSize( new Dimension( 200, 42 ) );
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

	public void setButton(JButton testButton){

				testButton.setOpaque(true);
				testButton.setBorderPainted(false);
				testButton.setBackground(Color.darkGray);
				testButton.setForeground(Color.WHITE);
				testButton.setFocusPainted(false);
				testButton.setFont(new Font("Tahoma", Font.BOLD, 12));

		}

	public void updateAccountArray() throws FileNotFoundException {
	    
	    accountArray.clear();
	    
        File myFile = new File("SaveFile.txt");
        Scanner myScan = new Scanner(myFile);
        while(myScan.hasNextLine()){
            String line = myScan.nextLine();
            Scanner lineScan = new Scanner(line);
            lineScan.useDelimiter(",");
          //TODO: see if this works without a while loop because I don't think it needs one.
             newAccountname = lineScan.next();
             newAccountemail = lineScan.next();
             newAccountphoneNum = lineScan.next();
             newAccountdescription = lineScan.next();
             newAccountBalance = lineScan.nextDouble();

            Account myAccount = new Account(newAccountname, newAccountemail,newAccountphoneNum,newAccountdescription);
            myAccount.setBalance(newAccountBalance);
            accountArray.add(myAccount);
        }
	}
	
	public void saveTransactionArray(ArrayList<Transaction> transactionArray,Transaction transac) {
        try{
            FileWriter Writer = new FileWriter("TransactionFile.txt",true);
            Writer.write(transac.getName()+","+transac.getAccount()+","+transac.getAmount()+","+transac.getDate()+","+transac.getType());
            Writer.write("\n");
            Writer.close();
        }
        catch(IOException ioe){
            System.err.println("You done goofed");
        }
	}

	
	


	//Button listeners
	private class confirmDeletionListener implements ActionListener{
		public void actionPerformed (ActionEvent enver){
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
	}


	private class deleteSelectedAccountListener implements ActionListener{//button to delete selected account. I got lazy and added this to the view account instead of making a new panel.
		public void actionPerformed (ActionEvent event){
			accountToView = enteredAccount.getText();
			String accountToDisplay = "";
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
	}

	private class displayEnteredInfoListener implements ActionListener { //button to display the enteredAccount
				public void actionPerformed (ActionEvent event){
					accountToView = enteredAccount.getText();
					String accountToDisplay = "";
					Boolean foundAccount = false;
					for(Account name:accountArray){
							if(name.toString().equalsIgnoreCase(accountToView)){ // When it gets the account selected it displays it.
								foundAccount = true;
								accountToDisplay = name.getAllInfo();

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
	}

	private class homeListener implements ActionListener // Goes to the home page
	{
		public void actionPerformed (ActionEvent event)
        {
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
				add(homePanel, BorderLayout.CENTER);
				//add(topPanel, BorderLayout.PAGE_START);
				add(imageLabel, BorderLayout.PAGE_START);
				accountViewPanel.removeAll();
				listOfAccounts.setText("<html>Type in the name of the account you would like to view.<br> Here is a list of current accounts.<br>");
				accountInfo.setText("");
				enteredAccount.setText("");
				accountViewPanel.add(listOfAccounts);
				accountViewPanel.add(accountInfo);
				accountViewPanel.add(enteredAccount);
				accountViewPanel.add(displayEnteredInfo);
				accountToView = "";
				dMessage.setText("");
				depositButton.setEnabled(true);
				wMessage.setText("");
				withdrawalButton.setEnabled(true);
				repaint();
			}
        }
	}

	private class submitAccountInfoListener implements ActionListener{//when you submit the information to create an account
			public void actionPerformed(ActionEvent event){
				//if info is invalid
				if(name.getText().equalsIgnoreCase("Name") || name.getText().equalsIgnoreCase("") //This is a messy way of doing it but, hey it works.
				|| email.getText().equalsIgnoreCase("Email") || email.getText().equalsIgnoreCase("")
				|| phoneNum.getText().equalsIgnoreCase("Phone Number") || phoneNum.getText().equalsIgnoreCase("")
				|| description.getText().equalsIgnoreCase("Description") || description.getText().equalsIgnoreCase("")){
					//TODO: make it loop through the array of already existing accounts so that you can't make an account with the same name as one already made.
					accountCreationPanel.setBackground(Color.red);
					enterInCredentials.setText("Please enter in all the information.");
					System.out.println("Invalid information");
				}
				else{
					accountCreationPanel.setBackground(Color.lightGray);
					setBackground(Color.darkGray);
					newAccountname = name.getText();
					newAccountemail = email.getText();
					newAccountphoneNum = phoneNum.getText();
					newAccountdescription = description.getText();
					Account myAccount = new Account(newAccountname, newAccountemail,newAccountphoneNum,newAccountdescription);
					accountArray.add(myAccount);
					System.out.println("Account created");
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
			}
	}

	private class deleteAccountListener implements ActionListener{ // deletes accounts
			public void actionPerformed (ActionEvent event){
				System.out.println("Deleting account.");
				removeAll();
				add(bottom, BorderLayout.PAGE_END);
				add(accountViewPanel, BorderLayout.CENTER);
				//add(topPanel, BorderLayout.PAGE_START);
				add(imageLabel, BorderLayout.PAGE_START);
				Iterator it = accountArray.iterator();
				while (it.hasNext()){

					listOfAccounts.setText(listOfAccounts.getText() + "<br>" + it.next());
				}
				listOfAccounts.setText(listOfAccounts.getText() + "</html>");
				revalidate();
				repaint();
				accountViewPanel.remove(displayEnteredInfo);
				accountViewPanel.add(deleteSelectedAccount);
			}

	}

	private class viewAccountListener implements ActionListener{ //views the accounts
			public void actionPerformed (ActionEvent event){
				System.out.println("viewing account.");
				removeAll();
				add(bottom, BorderLayout.PAGE_END);
				add(accountViewPanel, BorderLayout.CENTER);
				//add(topPanel, BorderLayout.PAGE_START);
				add(imageLabel, BorderLayout.PAGE_START);
				Iterator it = accountArray.iterator();
				while (it.hasNext()){

					listOfAccounts.setText(listOfAccounts.getText() + "<br>" + it.next());
				}
				listOfAccounts.setText(listOfAccounts.getText() + "</html>");
				revalidate();
				repaint();
			}
	}

	private class newAccountListener implements ActionListener { //creates a new account
			public void actionPerformed (ActionEvent event){
				System.out.println("making a new account");
				removeAll();
				add(bottom, BorderLayout.PAGE_END);
				add(accountCreationPanel, BorderLayout.CENTER);
				//add(topPanel, BorderLayout.PAGE_START);
				add(imageLabel, BorderLayout.PAGE_START);
				revalidate();
				repaint();

			}
	}

	private class logoutListener implements ActionListener // Logs out
	{
		public void actionPerformed (ActionEvent event)
        {
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
				listOfAccounts.setText("<html>Type in the name of the account you would like to view.<br> Here is a list of current accounts.<br>");
				accountInfo.setText("");
				enteredAccount.setText("");
				accountViewPanel.add(listOfAccounts);
				accountViewPanel.add(accountInfo);
				accountViewPanel.add(enteredAccount);
				accountViewPanel.add(displayEnteredInfo);

				add(bottom, BorderLayout.PAGE_END);
				add(loginPanel, BorderLayout.CENTER);
				//add(topPanel, BorderLayout.PAGE_START);
				add(imageLabel, BorderLayout.PAGE_START);
				repaint();

				//setBackground(Color.red);
				username.setText("Username");
				password.setText("Password");



			}
        }
	}


	private class loginListener implements ActionListener // Logs in
	{
		public void actionPerformed (ActionEvent event)
        {
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
				//topPanel.setBackground(Color.lightGray);
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
				//topPanel.setBackground(Color.red);
			}


		}
	}

	private class accountListener implements ActionListener //Button for accessing accounts page
	{
		public void actionPerformed (ActionEvent event)
        {
			System.out.println("Account");
			removeAll();
			add(bottom, BorderLayout.PAGE_END);
			//add(topPanel, BorderLayout.PAGE_START);
			add(imageLabel, BorderLayout.PAGE_START);
			add(accountPanel, BorderLayout.CENTER);
			revalidate();
			repaint();

        }
	}

	private class depositListener implements ActionListener // Button for creating a deposit
	{
		public void actionPerformed (ActionEvent event)
        {
			System.out.println("Deposit");
			removeAll();
			add(bottom, BorderLayout.PAGE_END);
			//add(topPanel, BorderLayout.PAGE_START);
			add(imageLabel, BorderLayout.PAGE_START);
			add(depositPanel, BorderLayout.CENTER);
		    dName.setText("Your Name");
	        dAccount.setText("Account Name");
	        dAmount.setText("Deposit Amount");
	        dDate.setText("Date (mm/dd/yyyy)");
	        depositButton.setText("Submit Deposit");
			revalidate();
			repaint();
        }
	}

//submit deposit button listener
	private class depositButtonListener implements ActionListener{
		public void actionPerformed (ActionEvent event){
			System.out.println("Deposit has been Made");
			dMessage.setText("Congrats! You have made a deposit!");
			if (dName.getText().equalsIgnoreCase("") || dAccount.getText().equalsIgnoreCase("") ||
			dAmount.getText().equalsIgnoreCase("") || dDate.getText().equalsIgnoreCase("") ||
			dName.getText().equalsIgnoreCase("Name") || dAccount.getText().equalsIgnoreCase("Account Name") ||
			dAmount.getText().equalsIgnoreCase("Deposit Amount") || dDate.getText().equalsIgnoreCase("Date (mm/dd/yyyy)")){
				dMessage.setText("Please Fill All Fields");
				depositPanel.setBackground(Color.red);
			}
			else{
			    accountToView = dAccount.getText();
				depositPanel.setBackground(Color.lightGray);
				newDepositName = dName.getText();
				newDepositAccount = dAccount.getText();
				newDepositAmount = dAmount.getText();
				double tempDA = Double.parseDouble(newDepositAmount);
				newDepositDate = dDate.getText();
                Transaction myTransaction = new Transaction(newDepositName, newDepositAccount, tempDA, newDepositDate, false, accountArray, accountToView,true, false);
                //Different transactions based on either Credit or Check/ note the 2 booleans at the end.
                //Transaction myTrasnac = new Transaction(newWithdrawalName, newWithdrawalAccount, tempDA, newWithdrawalDate, false, accountArray, accountToView,false, true);
                transactionArray.add(myTransaction);
                saveTransactionArray(transactionArray,myTransaction);
                System.out.println("Transaction created");
				System.out.println("Thank you, "+newDepositName+". The amount of $"+newDepositAmount+" has been added to account "+newDepositAccount+" on "+newDepositDate);
				dMessage.setText("<html>Thank you, "+newDepositName+".<br> The amount of $"+newDepositAmount+" has been added to account "+newDepositAccount+" on "+newDepositDate);
				depositButton.setEnabled(false);
				try {
                    updateAccountArray();
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
			}
		}
	}

	private class withdrawalListener implements ActionListener // GButton for creating a withdrawl
	{
		public void actionPerformed (ActionEvent event)
        {
			System.out.println("Withdrawal");
			removeAll();
			add(bottom, BorderLayout.PAGE_END);
			//add(topPanel, BorderLayout.PAGE_START);
			add(imageLabel, BorderLayout.PAGE_START);
			add(withdrawalPanel, BorderLayout.CENTER);
		    wName.setText("Your Name");
	        wAccount.setText("Account Name");
	        wAmount.setText("Withdrawal Amount");
	        wDate.setText("Date (mm/dd/yyyy)");
	        withdrawalButton.setText("Withdraw");
			revalidate();
			repaint();
        }
	}
//submit withdrawal button listener
	private class withdrawalButtonListener implements ActionListener{
		public void actionPerformed (ActionEvent event){
			System.out.println("Withdrawal has been Made");
			wMessage.setText("Congrats! You have made a withdrawal!");
			if (wName.getText().equalsIgnoreCase("") || wAccount.getText().equalsIgnoreCase("") ||
			wAmount.getText().equalsIgnoreCase("") || wDate.getText().equalsIgnoreCase("") ||
			wName.getText().equalsIgnoreCase("Name") || wAccount.getText().equalsIgnoreCase("Account Name") ||
			wAmount.getText().equalsIgnoreCase("Deposit Amount") || wDate.getText().equalsIgnoreCase("Date (mm/dd/yyyy)")){
				wMessage.setText("Please Fill All Fields");
				withdrawalPanel.setBackground(Color.red);
			}
			else{
			    accountToView = wAccount.getText();
				withdrawalPanel.setBackground(Color.lightGray);
				newWithdrawalName = wName.getText();
				newWithdrawalAccount = wAccount.getText();
				newWithdrawalAmount = wAmount.getText();
				double tempWA = Double.parseDouble(newWithdrawalAmount);
				newWithdrawalDate = wDate.getText();
                Transaction myTransaction = new Transaction(newWithdrawalName, newWithdrawalAccount, tempWA, newWithdrawalDate, true, accountArray, accountToView, false, false);
                transactionArray.add(myTransaction);
                saveTransactionArray(transactionArray,myTransaction);
                System.out.println("Transaction created");
				System.out.println("Thank you, "+newWithdrawalName+". The amount of $"+newWithdrawalAmount+" has been withdrawn from the account "+newWithdrawalAccount+" on "+newWithdrawalDate);
				wMessage.setText("<html>Thank you, "+newWithdrawalName+".<br> The amount of $"+newWithdrawalAmount+" has been withdrawn from the account "+newWithdrawalAccount+" on "+newWithdrawalDate);
                withdrawalButton.setEnabled(false);
				try {
                    updateAccountArray();
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
			}

		}
	}

}
