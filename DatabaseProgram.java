import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;


public class DatabaseProgram {
	
	private static Connection connect() {
		String url = "jdbc:sqlite:C:\\Users\\Hp\\OneDrive\\Desktop\\gpacalculator\\StudentsDB.db";
    Connection connect = null;
    	try {
    		connect = DriverManager.getConnection(url);
    	 	}
    	catch(SQLException e) {
    		System.out.println("Can't connect to the database!");
    		e.printStackTrace();
    	}
    	return connect;
	}
    	
	static void showResult() {
		String query = "SELECT * FROM StudentInfo ORDER BY CutOff DESC;";
		try(Connection connect = connect(); Statement state = connect.createStatement(); ResultSet res = state.executeQuery(query) ){
			while(res.next()) {
				int rollNo = res.getInt("RollNo");
				String name = res.getString("Name");
				System.out.println(rollNo + " | " + name);
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
   	}
   
	public static void main(String[] args) {
    	showResult();
    }
}