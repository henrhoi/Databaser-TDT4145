
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public final class DBConnection {
	
	
	// Statisk funksjon som kan brukes for aa koble seg opp mot databasen. 
	// Passord og brukernavn ligger i koden, mulig det skal gaa inn som startargs eller env. variabler
	public static Connection getDBConnection() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		String url = "jdbc:mysql://mysql.stud.ntnu.no/henrhoi_TDT4145?autoReconnect=true&useSSL=false";
		// Spør meg om tilgang til DB
		String user = "ASK FOR PERMISSION";
		String pass = "ASK FOR PERMISSION";
		
		Properties p = new Properties();
		p.put("user", user);
		p.put("password", pass);
		
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		Connection conn = DriverManager.getConnection (url, p);
		
		return conn;
	}

	public static void main(String[] args) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		Connection conn = DBConnection.getDBConnection();
		PreparedStatement stmt = conn.prepareStatement("SELECT * FROM exercise");
		ResultSet rs = stmt.executeQuery();
		while (rs.next()){
			System.out.println(rs.next());
		}
	}
}
