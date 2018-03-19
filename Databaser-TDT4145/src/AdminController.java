import java.net.ConnectException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminController extends DBConnection{
	
	
	
	//Lage nytt apparat objekt, skrive til database
	public void makeMachine() {
		
	}
	
	
	public static void insertWorkout(Connection myConn, String date, String time, int duration, int personligForm, int prestasjon, String notat )throws SQLException{
		String preQueryStatement = "INSERT INTO WORKOUT (OKTID, DATO, TIDSPUNKT, VARIGHET, PERSONLIGFORM, PRESTASJON, NOTAT) VALUES (?,?,?,?,?,?,?)";
		PreparedStatement prepStat = myConn.prepareStatement(preQueryStatement);
		
		prepStat.setInt(1, 69); //Rullerende id?
		prepStat.setString(2,date);
		prepStat.setString(3, time);
		prepStat.setInt(4, duration);
		prepStat.setInt(5, personligForm);
		prepStat.setInt(6, prestasjon);
		prepStat.setString(7, notat);
		prepStat.execute();
		
	}
	
	public static void insertExerciseGroup(Connection myConn, String navn) throws SQLException {
		String preQueryStatement = "INSERT INTO EXERCISEGROUP (NAVN) VALUES (?)";
		PreparedStatement prepStat = myConn.prepareStatement(preQueryStatement);
		prepStat.setString(1, navn);
		prepStat.execute();
	}
	
	
	public static void insertExercise(Connection myConn, String navn, String beskrivelse) throws SQLException {
		String preQueryStatement = "INSERT INTO EXERCISE (NAVN, BESKRIVELSE) VALUES (?,?)";
		PreparedStatement prepStat = myConn.prepareStatement(preQueryStatement);
		prepStat.setString(1, navn);
		prepStat.setString(2, beskrivelse);
		prepStat.execute();
	}
	
	public static void insertMachine(Connection myConn, String navn, String beskrivelse) throws SQLException{
		String preQueryStatement = "INSERT INTO MACHINE (NAVN, BESKRIVELSE) VALUES (?,?)";
		PreparedStatement prepStat = myConn.prepareStatement(preQueryStatement);
		prepStat.setString(1, navn);
		prepStat.setString(2, beskrivelse);
		prepStat.execute();
	}
	
	public static void insertExerciseOnMachine(Connection myConn,String exerciseName,String machineName) throws SQLException{
		//Begge er fremmednøkkler til sin entitet
		String preQueryStatement = "INSERT INTO EXERCISEONMACHINE (EXERCISENAME, MACHINENAME) VALUES (?,?)";
		PreparedStatement prepStat = myConn.prepareStatement(preQueryStatement);
		prepStat.setString(1, exerciseName);
		prepStat.setString(2, machineName);
		prepStat.execute();
	}
	
	public static void insertGroupContainsExercise(Connection myConn,String groupName,String exerciseName) throws SQLException{
		//Begge er fremmednøkkler til sin entitet
		String preQueryStatement = "INSERT INTO GROPUCONTAINSEXERCISE (GROUPNAME, EXERCISENAME) VALUES (?,?)";
		PreparedStatement prepStat = myConn.prepareStatement(preQueryStatement);
		prepStat.setString(1, groupName);
		prepStat.setString(2, exerciseName);
		prepStat.execute();
	}
	
	public static void insertWorkoutContainsExercise(Connection myConn,String workoutDate,String exerciseName) throws SQLException{
		//Begge er fremmednøkkler til sin entitet
		String preQueryStatement = "INSERT INTO WORKOUTCONTAINSEXERCISE (WORKOUTDATE, EXERCISENAME) VALUES (?,?)";
		PreparedStatement prepStat = myConn.prepareStatement(preQueryStatement);
		prepStat.setString(1, workoutDate);
		prepStat.setString(2, exerciseName);
		prepStat.execute();
	}
	
	
	
	

	
	//delete machine-øvelse
	public static void deleteOvelseMachine(Connection myConn, String name) throws SQLException{
		String preQueryStatement = "DELETE FROM OVELSE where NAVN = ?"; //BURDE KANSKJE HA ID ISTEDEFOR NAVN?
		PreparedStatement prepStat = myConn.prepareStatement(preQueryStatement);
		prepStat.setString(1,name);
		prepStat.execute();
		
		String preQueryStatementMachine = "DELETE FROM Machineexercise where NAVN = ?";//BURDE KANSKJE HA ID ISTEDEFOR NAVN?
		PreparedStatement prepStatStyrke = myConn.prepareStatement(preQueryStatementMachine);
		prepStatStyrke.setString(1,name);
		prepStatStyrke.execute();
	}
	
	//delete ikke-machine øvelse
	public static void deleteOvelseIkkeMachine(Connection myConn, String name) throws SQLException{
		String preQueryStat = "DELETE FROM OVELSE where NAVN = ?";//BURDE KANSKJE HA ID ISTEDEFOR NAVN?
		PreparedStatement preStat = myConn.prepareStatement(preQueryStat);
		preStat.setString(1,name);
		preStat.execute();
		
		String preQueryNotMachine = "DELETE FROM NOTMACHINEEXERCISE where NAVN =?";//BURDE KANSKJE HA ID ISTEDEFOR NAVN?
		PreparedStatement preStatNotMachine = myConn.prepareStatement(preQueryNotMachine);
		preStatNotMachine.setString(1, name);
		preStatNotMachine.execute();
	}
	
	//kravspesifikasjon 2
	
//	//FÅ ALLE FRA EN SPESIFIKK ØVELSE
//	public static ArrayList<Exercise> getOvelseFromOkt(Connection myConn, int oktID) throws SQLException{
//		ArrayList<Exercise> ovelseList = new ArrayList<>();
//		String query = "SELECT * FROM EXERCISE INNER JOIN EXERCISEGROUP EXERCISE.NAVN = EXERCISEGROUP.NAVN WHERE OKTID = ?";//BURDE KANSKJE HA ID ISTEDEFOR NAVN?
//		PreparedStatement preparedStatement = myConn.prepareStatment(query);
//		preparedStatement.setInt(1, oktID);
//		ResultSet resultSet = preparedStatement.executeQuery();
//		
//		while (resultSet.next()) {
//			String name = resultSet.getString("NAVN");
//			String description = resultSet.getString("BESKRIVELSE");
//			
//			Exercise ovelse = new Exercise(name,description); //hvordan fikser jeg dette datagutter?
//			ovelseList.add(ovelse);
//		}
//		return ovelseList;
//	}
	
	public static String getResultOvelseMachine(Connection myConn, int ID) throws SQLException{
		String query = "SELECT * FROM MACHINE INNER JOIN ON OVELSE.NAVN = REULTATSTYRKE.NAVN";
		PreparedStatement preparedStatement = myConn.prepareStatement(query);
		preparedStatement.setInt(1, ID);
		ResultSet resultSet = preparedStatement.executeQuery();
		
		String report = "";
		
		while (resultSet.next()) {
			String navn = resultSet.getString("NAVN");
			String antReps = resultSet.getString("ANTALLREPS");
			String antSet = resultSet.getString("ANTALLSET");
			String kg = String.valueOf(resultSet.getInt("KG"));
			report = navn + antReps + antSet + kg; 
		}
		return report;
	}
}
