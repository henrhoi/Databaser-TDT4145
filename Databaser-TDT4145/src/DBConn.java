
import java.sql.*;
import java.util.Properties;

public abstract class DBConn {
    protected Connection conn;
    public DBConn () {
    }
    public Connection connect() {
    	try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            // Properties for user and password. Here the user and password are both 'paulr'
            Properties p = new Properties();
            p.put("user", "henrhoi_TDT4145");
            p.put("password", "MyNewPass");           
            conn = DriverManager.getConnection("jdbc:mysql://mysql.stud.ntnu.no/henrhoi_TDT4145?autoReconnect=true&useSSL=false",p);
            return conn;
        } catch (Exception e)
    	{
            throw new RuntimeException("Unable to connect", e);
    	}
    }
}
