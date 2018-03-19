import java.net.ConnectException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminController{
	
	
	
	
	///////////////////////////Kravspesifikasjon 1///////////////////////////
	
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
		String preQueryStatement = "INSERT INTO exercisegroup (NAVN) VALUES (?)";
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
	
	public static void insertWorkoutContainsExercise(Connection myConn,String workoutDate,String exerciseName,int antallKilo, int antallSet) throws SQLException{
		//Begge er fremmednøkkler til sin entitet
		String preQueryStatement = "INSERT INTO WORKOUTCONTAINSEXERCISE (WORKOUTDATE, EXERCISENAME, ANTALLKILO, ANTALLSET) VALUES (?,?,?,?)";
		PreparedStatement prepStat = myConn.prepareStatement(preQueryStatement);
		prepStat.setString(1, workoutDate);
		prepStat.setString(2, exerciseName);
		prepStat.setInt(3, antallKilo); //Hvis disse verdiene ikke er relevante settes de til null
		prepStat.setInt(4, antallSet); //Hvis disse verdiene ikke er relevante settes de til null -  feks på øvelser som ikke er på apparat
		prepStat.execute();
	}
	
	
	
	
///////////////////////////Kravspesifikasjon 2///////////////////////////
	
	
	
	//Hente de n siste Workout med all informasjon
	public static List<Workout> getNWorkouts(Connection conn, int n) throws SQLException{
		List<Workout> workouts = new ArrayList<Workout>();
		
		//First find a list over the N last workouts
		String stmt = "select * from Workout order by dato desc limit ?";
		PreparedStatement prepStat = conn.prepareStatement(stmt);
		prepStat.setInt(1, n);
		ResultSet rs = prepStat.executeQuery();
		while(rs.next()) {
			Workout w = new Workout(rs.getDate("dato"), rs.getTime("tidspunkt"), rs.getTime("varighet"), rs.getInt("personligForm"), rs.getInt("prestasjon"), rs.getString("notat"));
			workouts.add(w);
		}
		
		//Then for every workout get all the exercises
		for (Workout w : workouts) {
			Date id = w.getDato();
			stmt = "select * from workoutcontainsexercise where dato = ?";
			prepStat = conn.prepareStatement(stmt);
			prepStat.setDate(1, id);
			while(rs.next()) {
				Exercise e = new Exercise(rs.getString("exersicename"),rs.getInt("kilo"),rs.getInt("sett"));
				
				//Hen exercise beskrivelse
				String tmpStmt = "Select * from Exercise where navn = ?";
				PreparedStatement pr = conn.prepareStatement(tmpStmt);
				ResultSet tmpRes = pr.executeQuery();
				e.setDescription(tmpRes.getString("beskrivelse"));
				
				//Hvis øvelsen gjøres på en maskin, hent apparatinfo
				//hmm
				
				//Legg til øvelse i workout
				w.addExercise(e);
			}
		}
		return workouts;
	}
	
	
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
