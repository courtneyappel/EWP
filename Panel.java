import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;



public class Panel extends JPanel{

	String user = "csadmin";
	String pw = "csci323";
	boolean loggedIn = false;

	JLabel top;
	JLabel bottom;

	JButton home;
	JButton logout;

	JTextField username;
	JTextField password;
	JButton login;

	JButton account;
	JButton deposit;
	JButton withdrawal;
	JButton newAccount;
	JButton viewAccount;
	JButton deletAccount;

	JPanel topPanel = new JPanel();
	JPanel loginPanel = new JPanel();
	JPanel homePanel = new JPanel();
	JPanel accountPanel = new JPanel();

	public Panel()
	{
		setBackground(Color.lightGray);
		setPreferredSize(new Dimension(500, 500));
		setLayout(new BorderLayout());

		//create header
		topPanel.setBackground(Color.lightGray);
		topPanel.setLayout(new BorderLayout());
		top = new JLabel("Account Manager", JLabel.CENTER);
		home = new JButton("HOME");
		home.addActionListener(new homeListener());
		logout = new JButton("Log Out");
		logout.addActionListener(new logoutListener());
		topPanel.add(top, BorderLayout.CENTER);
		topPanel.add(home, BorderLayout.WEST);
		topPanel.add(logout, BorderLayout.EAST);
		add(topPanel, BorderLayout.PAGE_START);

		//create footer
		bottom = new JLabel("Developed with love by EWP 2017", JLabel.CENTER);
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

		newAccount = new JButton("newAccount"){
			{
				setSize(200,100);
				setMaximumSize(getSize());
			}

		};
		newAccount.setAlignmentX(JButton.CENTER_ALIGNMENT);
		newAccount.addActionListener(new accountListener());

		viewAccount = new JButton("viewAccount"){
			{
				setSize(200,100);
				setMaximumSize(getSize());
			}

		};
		viewAccount.setAlignmentX(JButton.CENTER_ALIGNMENT);
		viewAccount.addActionListener(new depositListener());

		deletAccount = new JButton("deletAccount"){
			{
				setSize(200,100);
				setMaximumSize(getSize());
			}

		};
		deletAccount.setAlignmentX(JButton.CENTER_ALIGNMENT);
		deletAccount.addActionListener(new withdrawalListener());

		accountPanel.add(Box.createRigidArea(new Dimension (0,25)));
		accountPanel.add(newAccount);
		accountPanel.add(Box.createRigidArea(new Dimension (0,25)));
		accountPanel.add(viewAccount);
		accountPanel.add(Box.createRigidArea(new Dimension (0,25)));
		accountPanel.add(deletAccount);


	}

	private class homeListener implements ActionListener
	{
		public void actionPerformed (ActionEvent event)
        {
			if(loggedIn == true)
			{
				System.out.println("HOME");
				removeAll();
				add(bottom, BorderLayout.PAGE_END);
				add(homePanel, BorderLayout.CENTER);
				add(topPanel, BorderLayout.PAGE_START);
				repaint();
			}
        }
	}

	private class logoutListener implements ActionListener
	{
		public void actionPerformed (ActionEvent event)
        {
			if(loggedIn == true)
			{
				loggedIn = false;
				removeAll();


				add(bottom, BorderLayout.PAGE_END);
				add(loginPanel, BorderLayout.CENTER);
				add(topPanel, BorderLayout.PAGE_START);
				repaint();

				setBackground(Color.red);
				username.setText("Username");
				password.setText("Password");



			}
        }
	}


	private class loginListener implements ActionListener
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
				topPanel.setBackground(Color.lightGray);
				loginPanel.setBackground(Color.lightGray);

				remove(loginPanel);
				add(homePanel, BorderLayout.CENTER);
				revalidate();
			}
			else
			{
				setBackground(Color.red);
				loginPanel.setBackground(Color.red);
				topPanel.setBackground(Color.red);
			}


		}
	}

	private class accountListener implements ActionListener
	{
		public void actionPerformed (ActionEvent event)
        {
			System.out.println("Account");
			removeAll();

			add(bottom, BorderLayout.PAGE_END);
			add(topPanel, BorderLayout.PAGE_START);
			add(accountPanel, BorderLayout.CENTER);
			revalidate();

        }
	}

	private class depositListener implements ActionListener
	{
		public void actionPerformed (ActionEvent event)
        {
			System.out.println("Deposit");
        }
	}

	private class withdrawalListener implements ActionListener
	{
		public void actionPerformed (ActionEvent event)
        {
			System.out.println("Withdrawal");
        }
	}


}
