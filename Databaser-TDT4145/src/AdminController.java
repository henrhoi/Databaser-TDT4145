import java.net.ConnectException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
				pr.setString(1, rs.getString("exersicename"));
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
		
	
	///////////////////////////Kravspesifikasjon 3///////////////////////////
	
	public static String getExerciseResult(Connection myConn, String dateStart,String dateEnd) throws SQLException{
        String query = "SELECT PERSONLIGFROM, VARIGHET FROM WORKOUT WHERE DATO > ? AND DATO < ?";
        PreparedStatement preparedStatement = myConn.prepareStatement(query);
        preparedStatement.setString(1, dateStart);
        preparedStatement.setString(2, dateEnd);
        ResultSet resultSet = preparedStatement.executeQuery();
        int index =0;
        int antallTimer = 0;
        int antallPersonligeForm = 0;
        while (resultSet.next()) {
            
            index++;
            antallTimer += resultSet.getInt("VARIGHET");
            antallPersonligeForm += resultSet.getInt("PERSONLIGFORM");
            
        }
        int personligFormSnitt = antallPersonligeForm/index;
        int varighetSnitt = antallTimer/index;
        
        String report = "I løpet av perioden på "+ index + "dager, trente du "+ antallTimer + "."+ " Gjennomsnittsøkten var på " +varighetSnitt +" timer, med et gjennomsnittlig perosnlig form på "+personligFormSnitt +".";

        return report;
    }
	
	
	///////////////////////////Kravspesifikasjon 4///////////////////////////
	
	
	public static List<ExerciseGroup> getExerciseGroups(Connection conn) throws SQLException{
		List<ExerciseGroup> groups = new ArrayList<ExerciseGroup>();
		
		//Spørr om alle koblingene mellom en exercise og en gruppe
		String stmt = "Select * from groupcontainsexercise";
		PreparedStatement prepStat = conn.prepareStatement(stmt);
		ResultSet rs = prepStat.executeQuery();
		
		//Lag en map hvor gruppenavn er key og ArrayList med exercisenavn er value
		Map<String,ArrayList<String>> map = new HashMap<String,ArrayList<String>>();
		while(rs.next()) {
			if(map.containsKey(rs.getString("gruppenavn"))) {
				map.get(rs.getString("gruppenavn"));
			}
			else {
				map.put(rs.getString("gruppenavn"), new ArrayList<String>(Arrays.asList(rs.getString("exercisename"))));
			}
		}
		
		//Lag objekter ut av mappen
		for(String gruppenavn : map.keySet()) {
			List<Exercise> exercises = new ArrayList<Exercise>();
			for(String exercisename : map.get(gruppenavn)) {
				//Hen exercise beskrivelse
				String tmpStmt = "Select * from Exercise where navn = ?";
				PreparedStatement pr = conn.prepareStatement(tmpStmt);
				pr.setString(1, exercisename);
				ResultSet tmpRes = pr.executeQuery();
				Exercise e = new Exercise(exercisename,tmpRes.getString("beskrivelse"));
				exercises.add(e);
			}
			groups.add(new ExerciseGroup(gruppenavn,exercises));
		}
		
		return groups;
	
	}
	

}
