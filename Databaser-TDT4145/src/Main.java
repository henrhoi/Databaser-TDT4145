import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;

public class Main{
	
	
	public void init() {
		
	}
	
	public void run() {
		
	}
	
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Connection myConn = DBConnection.getDBConnection();
		System.out.println("Testing");
		Date dateStart = new Date(3000,4,4);
		Date dateEnd = new Date(4000,4,4);
		System.out.println(dateStart.getYear());
		
		System.out.println(AdminController.getExerciseResult(myConn, dateStart,dateEnd));
	}
}
