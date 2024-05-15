package banking_project_app;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
public class Main {
	private static Connection connection;
	private static  Statement statement;
	static {
		try {
			// loading or registering the driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/akash", "root", "akash 1998");
			statement=connection.createStatement();
			statement.execute("CREATE TABLE IF NOT EXISTS bank (accno varchar(15) primary key ,acc_type varchar(15),name varchar(20),balance long)");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
		
	public static void main(String[] args) 
	{
		Operation operlation = new Operation();
		operlation.bankinfo();
	}
	public static Connection getcon() {
		return connection;
	}
}
