package banking_project_app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
public class Process {
	
	 Scanner sc = new Scanner(System.in);  
	 public Connection connection;
	 public PreparedStatement preparedstatement;
	    public void openAccount() 
	    {  
	        System.out.print("Enter Account No: ");  
	        String acc_no=sc.next();
	        System.out.print("Enter Account type: ");  
	        String acc_type=sc.next();
	        System.out.print("Enter Name: ");  
	        String name=sc.next();
	        System.out.print("Enter Balance: ");  
	        long bal=sc.nextLong();
	        
	        // for entering data into database
	       
	        try {
	        	connection=Main.getcon();
	        	preparedstatement=connection.prepareStatement("insert into bank values (?,?,?,?)");
		        preparedstatement.setString(1, acc_no);
		        preparedstatement.setString(2, acc_type);
		        preparedstatement.setString(3, name);
		        preparedstatement.setLong(4,bal);
		        preparedstatement.executeUpdate();
		        
	        }
	        catch(Exception e) {
	        	e.printStackTrace();
	        }
	    } 
	    public void demoaccount() 
	    {
	    	int  demobalance=50000;
	    	System.out.println("Name of account holder :: " + "Demo user");  
	        System.out.println("Account no             :: " + "8529637412");  
	        System.out.println("Account type           :: " + "demo");  
	        System.out.println("Balance                :: " + demobalance);  
	    	
	    }
	    public void deposite()
	    {
	    	System.out.println("enterr your account number");
	    	String ac_no=sc.next();
	    	System.out.println("Enter the Amount you want to deposit ::");
			int deposit =sc.nextInt();
			long prev=0;
			try {
				
				connection=Main.getcon();
				
				// for fetching the current balance of user
				PreparedStatement pre=connection.prepareStatement("select balance from bank where accno=?");
				pre.setString(1, ac_no);
				ResultSet r1=pre.executeQuery();
				while(r1.next()) {
					prev=r1.getLong("balance");
				}
				// for updating the balance due to deposit
				PreparedStatement pre1=connection.prepareStatement("UPDATE bank set balance=? where accno=?");
				pre1.setLong(1,prev+deposit);
				pre1.setString(2, ac_no);
				pre1.executeUpdate();
				
				// for displaying the updated balance after deposit
				System.out.println(deposit+" is deposited into your Account");
				System.out.println("Current Available Balance is Rs = "+ (prev+deposit));
				
			}
			catch(Exception e) {
				e.printStackTrace();
			}
	    }
	    public void withdraw() 
	    {
	    	Scanner sc= new Scanner(System.in);
	    	System.out.println("Enter the Amount you want to withdraw:");
	    	int withdraw =sc.nextInt();
			System.out.println("enterr your account number");
			String acc_no=sc.next();
			long ava_bal=0;
			try {
				connection=Main.getcon();
				PreparedStatement pre=connection.prepareStatement("select balance from bank where accno=?");
				pre.setString(1, acc_no);
				ResultSet r=pre.executeQuery();
				while(r.next()) {
					ava_bal=r.getLong("balance");
				}
				if(withdraw<ava_bal)
				{
					connection=Main.getcon();
					PreparedStatement pre1=connection.prepareStatement("update bank set balance=? where accno=?");
					pre1.setLong(1, ava_bal-withdraw);
					pre1.setString(2, acc_no);
					pre1.executeUpdate();
					System.out.println(withdraw+" is withdrawn from your Account");
					System.out.println("Current Available Balance is Rs  ::"+ (ava_bal-withdraw));
				}
				else
				{
					System.out.println("Low Balance");
					System.out.println("Current Available Balance is Rs  ::"+ ava_bal);
				}
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			
	    }
		public void checkbalance() 
		{
			try {
				System.out.println("enterr your account number");
				String accno=sc.next();
				connection=Main.getcon();
				PreparedStatement p=connection.prepareStatement("select * from bank where accno=?");
				p.setString(1, accno);
				ResultSet r=p.executeQuery();
				if(r==null)
					System.out.println("incorrect enterred pin");
				else {
					while(r.next()) {
						System.out.println("Account no            :: "+r.getString(1));
				        System.out.println("Account type          :: " +r.getString(2));  
				        System.out.println("Your Name is          :: " + r.getString(3));  
					    System.out.println("Balance               :: " + r.getLong(4));  
						System.out.println("THANKS FOR BALANCE CHECKING ");
					}
				}
			}
			catch(Exception e) {
				e.printStackTrace();
			}	
		}
}
