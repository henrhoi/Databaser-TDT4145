import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TrainingController extends Application{
	
	@FXML
	Button registerWorkoutButton, registerExerciseButton, registerMachineButton, registerExcersiceGroupButton,
	registerExerciseInGroupButton, registerExerciseInWorkoutButton, registerExerciseOnMachineButton,
	getExerciseResultsButton;
	
	@FXML
	TextField registerWorkoutField, registerExerciseField, registerMachineField, registerExerciseGroupField,
	registerExerciseInGroupField,registerExerciseInWorkoutField, registerExerciseOnMachineField,
	getNLastWorkoutsField, getExerciseResultsField;
	
	@FXML
	TextArea textArea;
	
	//Connection myConn;
	
	@Override
	public void init() throws Exception {
		System.out.println("hei");
		//myConn = DBConnection.getDBConnection();
		//System.out.println(myConn);
	}	
	
	
	//register workout to databse
	@FXML
	public void registerWorkout() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		//System.out.println(myConn);
		List<String> input = Arrays.asList(registerWorkoutField.getText().split(","));
		List<String> dateString = Arrays.asList(input.get(0).split("-"));
		int year = Integer.parseInt(dateString.get(0));
		int month = Integer.parseInt(dateString.get(1));
		int day = Integer.parseInt(dateString.get(2));
		Date date = new Date(year - 1900, month - 1, day);
		Time time = Time.valueOf(input.get(1));
		int duration = Integer.parseInt(input.get(2));
		int personligForm = Integer.parseInt(input.get(3));
		int prestasjon = Integer.parseInt(input.get(4));
		String notat = input.get(5);
		System.out.println(input);
		Connection myConn = DBConnection.getDBConnection();
		AdminController.insertWorkout(myConn, date, time, duration, personligForm, prestasjon, notat);
		System.out.println("Workout added");
	
	}
	
	
	@FXML
	public void registerExercise() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		List<String> input = Arrays.asList(registerExerciseField.getText().split(","));
		String navn = input.get(0);
		String beskrivelse = input.get(1);
		Connection myConn = DBConnection.getDBConnection();

		AdminController.insertExercise(myConn, navn, beskrivelse);
		System.out.println("Exercise added");
	}
	
	@FXML
	public void registerMachine() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		List<String> input = Arrays.asList(registerMachineField.getText().split(","));
		String navn = input.get(0);
		String beskrivelse = input.get(1);
		Connection myConn = DBConnection.getDBConnection();

		AdminController.insertMachine(myConn, navn, beskrivelse);
		System.out.println("Machine added");
	}
	
	
	@FXML
	public void registerExerciseGroup() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		List<String> input = Arrays.asList(registerExerciseGroupField.getText().split(","));
		String navn = input.get(0);
		Connection myConn = DBConnection.getDBConnection();

		AdminController.insertExerciseGroup(myConn, navn);
		System.out.println("Exercise group added");
	}
	
	
	@FXML
	public void registerExerciseInGroup() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		System.out.println(registerExerciseInGroupField.getText());
		List<String> input = Arrays.asList(registerExerciseInGroupField.getText().split(","));
		String groupName = input.get(0);
		String exerciseName = input.get(1);
		Connection myConn = DBConnection.getDBConnection();

		AdminController.insertGroupContainsExercise(myConn, groupName, exerciseName);
		System.out.println("Exercise added to exercise group.");
	}
	
	
	@FXML
	public void registerExerciseInWorkout() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		List<String> input = Arrays.asList(registerExerciseInWorkoutField.getText().split(","));
		List<String> dateString = Arrays.asList(input.get(0).split("-"));
		int year = Integer.parseInt(dateString.get(0));
		int month = Integer.parseInt(dateString.get(1));
		int day = Integer.parseInt(dateString.get(2));
		Date workoutDate = new Date(year - 1900, month - 1, day);
		System.out.println(input.get(1));
		String exerciseName = input.get(1).replaceAll("\\s", "");
		int antallKilo = Integer.parseInt(input.get(2));
		int antallSet = Integer.parseInt(input.get(3));
		Connection myConn = DBConnection.getDBConnection();
		System.out.println(input);

		AdminController.insertWorkoutContainsExercise(myConn, workoutDate, exerciseName, antallKilo, antallSet);
		System.out.println("Exercise was added to this workout");
	}
	
	
	@FXML
	public void registerExerciseOnMachine() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		List<String> input = Arrays.asList(registerExerciseOnMachineField.getText().split(","));
		String exerciseName = input.get(0);
		String machineName = input.get(1);
		Connection myConn = DBConnection.getDBConnection();
		AdminController.insertExerciseOnMachine(myConn, exerciseName, machineName);
		System.out.println("Exercise was added to the machine");
	}

	
	@FXML
	public void getExerciseGroups() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		Connection myConn = DBConnection.getDBConnection();
		List<ExerciseGroup> groups = AdminController.getExerciseGroups(myConn);
		String result = "Exercise Group \t Exercise\n";
		
		for(ExerciseGroup group : groups) {
			for(Exercise exercise : group.getExercises()) {
				result+= group.getName() + "\t\t\t" + exercise.getName() + "\n";
			}
		}
		textArea.setText(result);
	}
	
	
	@FXML
	public void getNLastWorkouts() throws NumberFormatException, SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		Connection myConn = DBConnection.getDBConnection();
		List<Workout> workouts = AdminController.getNWorkouts(myConn, Integer.parseInt(getNLastWorkoutsField.getText()));
		String result = "Date \t\t tidspunkt \t varighet \t Form \t Prestasjon \t Notat\n";
		
		for(Workout workout : workouts) {
			result += workout.getDato().toString() + "\t";
			result += workout.getTidspunkt().toString() + "            ";
			result += workout.getVarighet() + "\t\t  ";
			result += workout.getPersonligForm() + "\t\t ";
			result += workout.getPrestasjon() + "\t\t\t";
			result += workout.getNotat() + "\n";
		}
		
		textArea.setText(result);
		
	}
	
	
	public void getTotalWorkouts() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Connection myConn = DBConnection.getDBConnection();
		int totalWorkouts = AdminController.getTotalWorkouts(myConn);
		textArea.setText(totalWorkouts + "");
	}
	
	
	public void getExerciseResult() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Connection myConn = DBConnection.getDBConnection();
		List<String> input = Arrays.asList(getExerciseResultsField.getText().split(","));
		List<String> startDate = Arrays.asList(input.get(0).split("-"));
		List<String> endDate = Arrays.asList(input.get(1).split("-"));
		int startYear = Integer.parseInt(startDate.get(0));
		int startMonth = Integer.parseInt(startDate.get(1));
		int startDay = Integer.parseInt(startDate.get(2));
		Date dateStart = new Date(startYear, startMonth, startDay);
		int endYear = Integer.parseInt(endDate.get(0));
		int endMonth = Integer.parseInt(endDate.get(1));
		int endDay = Integer.parseInt(endDate.get(2));
		Date dateEnd = new Date(endYear, endMonth, endDay);
		
		String result = AdminController.getExerciseResult(myConn, dateStart, dateEnd);
		
		textArea.setText(result);
		
		
	}
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
	    		FXMLLoader loader = new FXMLLoader(getClass().getResource("FxTraining.fxml"));
	    		Parent root = loader.load();
	    		Scene scene = new Scene(root);
	    		primaryStage.setScene(scene);
	    		primaryStage.show();
	    		
	    }

    
	
	
	public static void main(String[] args) {
	        launch(args);
	}
	
	
}
