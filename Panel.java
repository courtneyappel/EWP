import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Iterator;

public class Panel extends JPanel{
	//variable inits
	ArrayList<Account> accountArray = new ArrayList();

	String newAccountname,newAccountemail,newAccountphoneNum,newAccountdescription;

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

  //for header Image and Buttons (imageLabel now replaces topPanel)
  ImageIcon backgroundPic = new ImageIcon("headerImage.png");
	JLabel imageLabel = new JLabel();

 //start of the main panel
	public Panel()
	{

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

	}
	//end of main "Panel()"




	//methods for apply visual settings, I will focus on creating seperate classes for these so its not cluttered
	public void setTextField(JTextField field){
		field.setPreferredSize( new Dimension( 200, 42 ) );
		field.setMaximumSize( new Dimension( 450, 42 ) );
		field.setFont(new Font("Tahoma", Font.BOLD, 14));
	}

	public void setButton(JButton testButton){

				testButton.setBackground(Color.darkGray);
				testButton.setForeground(Color.WHITE);
				testButton.setFocusPainted(false);
				testButton.setFont(new Font("Tahoma", Font.BOLD, 12));

		}






	//Button listeners
	private class confirmDeletionListener implements ActionListener{
		public void actionPerformed (ActionEvent enver){
			Iterator<Account> it = accountArray.iterator();
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
				setBackground(Color.darkGray);
				loginPanel.setBackground(Color.darkGray);
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
        }
	}

	private class withdrawalListener implements ActionListener // GButton for creating a withdrawl
	{
		public void actionPerformed (ActionEvent event)
        {
			System.out.println("Withdrawal");
        }
	}

}
