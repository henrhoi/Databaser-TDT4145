import java.sql.Connection;
import java.sql.SQLException;

public class Main extends DBConn{
	
	
	public void init() {
		
	}
	
	public void run() {
		
	}
	
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Connection conn = new Main().connect();
		System.out.println("Testing");
		AdminController.insertExerciseGroup(conn, "TESTGRUPPE2");
		

	}
}
