           
import javax.swing.*;
import java.sql.Connection;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException; 

public class RankAllocator{

    //Creating Object References
    public static JFrame mainFrame;
    public static JLabel nameLabel;
    public static JLabel rollNoLabel;
    public static JLabel dobLabel;
    public static JLabel SUBJECT1LAB;
    public static JLabel SUBJECT2LAB;
    public static JLabel SUBJECT3LAB;
    public static JLabel SUBJECT4LAB;
    public static JLabel SUBJECT5LAB;
    public static JLabel SUBJECT6LAB;
    public static JLabel SUBJECT7LAB;
    public static JLabel SUBJECT8LAB;
    public static JTextField nameInput;
    public static JTextField rollNoInput;
    public static JTextField dobInput;
    public static JTextField SUBJECT1;
    public static JTextField SUBJECT2;
    public static JTextField SUBJECT3;
    public static JTextField SUBJECT4;
    public static JTextField SUBJECT5;
    public static JTextField SUBJECT6;
    public static JTextField SUBJECT7;
    public static JTextField SUBJECT8;
    public static JButton submitButton;
    public static JButton allocateRankButton;
    
    public static JButton showResult;
    public static ImageIcon icon;
    public static JLabel totalMarksLabel;
    public static JTextField totalMarksInput;
    public static JLabel boardSelection;
    public static JComboBox<?> selectBoard;
    

    //Declaring Variables
    static String name, dob, board; 
    static int rollNo,SUB1M,SUB2M,SUB3M,SUB4M,SUB5M,SUB6M,SUB7M,SUB8M ;
    static double cutOff, totalMark, percent,GPA;
    
    static void designFrame()
    {
        //Designing Main Frame
        mainFrame = new JFrame("GPA CALCULATOR");
        mainFrame.setSize(500, 500);
        mainFrame.setLayout(new GridLayout(20, 1));
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setResizable(true);
    }

    static void createElements()
    {
        //Creating GUI Elements
        nameLabel = new JLabel("Name:");
        rollNoLabel = new JLabel("Roll Number:");
        dobLabel = new JLabel("Date Of Birth:");
        SUBJECT1LAB = new JLabel("SUBJECT1:");
        SUBJECT2LAB = new JLabel("SUBJECT2:");
        SUBJECT3LAB = new JLabel("SUBJECT3:");
        SUBJECT4LAB = new JLabel("SUBJECT4:");
        SUBJECT5LAB = new JLabel("SUBJECT5:");
        SUBJECT6LAB = new JLabel("SUBJECT6:");
        SUBJECT7LAB = new JLabel("SUBJECT7:");
        SUBJECT8LAB = new JLabel("SUBJECT8:");
       
        nameInput = new JTextField(50);
        rollNoInput = new JTextField(10);
        dobInput = new JTextField(10);
        dobInput.setText("DD-MM-YYYY");
        SUBJECT1 = new JTextField(5);
        SUBJECT2 = new JTextField(5);
        SUBJECT3 = new JTextField(5);
        SUBJECT4 = new JTextField(5);
        SUBJECT5 = new JTextField(5);
        SUBJECT6 = new JTextField(5);
        SUBJECT7 = new JTextField(5);
        SUBJECT8 = new JTextField(5);
        
        totalMarksLabel = new JLabel("Total Marks");
        totalMarksInput = new JTextField(5);
        submitButton = new JButton("Submit");
        submitButton.setFocusable(false);
        submitButton.addActionListener(e -> submitButtonResponse());
        allocateRankButton = new JButton("CALCULATE GPA");
        allocateRankButton.setFocusable(false);
        allocateRankButton.addActionListener(e -> allocateButtonResponse());
        showResult = new JButton("RESULT");
        showResult.setFocusable(false);
        showResult.addActionListener(e -> resultButtonResponse());
        icon = new ImageIcon("gpa.png");
        boardSelection = new JLabel("UNIVERSITY");
        String[] options = {"ANNAUNIVERSITY", "OTHERUNIVERSITY"};
        selectBoard = new JComboBox(options);
        selectBoard.addActionListener(e -> selectBoardResponse());
        selectBoard.setEditable(false);
    }

    static void selectBoardResponse(){ }
    static void submitButtonResponse()
    {
        //This Method implements the functionality of the Submit button
        name = nameInput.getText();
        rollNo = Integer.valueOf(rollNoInput.getText());
        dob = dobInput.getText();
        board = String.valueOf(selectBoard.getSelectedItem());
        SUB1M = Integer.valueOf(SUBJECT1.getText());
        SUB2M = Integer.valueOf(SUBJECT2.getText());
        SUB3M = Integer.valueOf(SUBJECT3.getText());
        SUB4M = Integer.valueOf(SUBJECT4.getText());
        SUB5M = Integer.valueOf(SUBJECT5.getText());
        SUB6M = Integer.valueOf(SUBJECT6.getText());
        SUB7M = Integer.valueOf(SUBJECT7.getText());
        SUB8M = Integer.valueOf(SUBJECT8.getText());
       
        totalMark = Double.valueOf(totalMarksInput.getText());
        cutOff = SUB1M+SUB2M+SUB3M+SUB4M+SUB5M+SUB6M+SUB7M+SUB8M;
        GPA=(cutOff/8)/10;
        percent = Math.round(cutOff/ 800 * 100);
        String msg = "Do you really want to submit?";
        String[] optionButtons = {"Submit", "Cancel"};
        int response = JOptionPane.showOptionDialog(null, msg, "Confirmation Alert", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, optionButtons, 0); 
        if(response == 0) {  
            insertToStudents(rollNo, name, dob, board, SUB1M, SUB2M, SUB3M,SUB4M,SUB5M,SUB6M,SUB7M,SUB8M, totalMark, cutOff, percent);
            nameInput.setText("");
            rollNoInput.setText("");
            dobInput.setText("DD-MM-YYYY");
            SUBJECT1.setText("");
            SUBJECT2.setText("");
            SUBJECT3.setText("");
            SUBJECT4.setText("");
            SUBJECT5.setText("");
            SUBJECT6.setText("");
            SUBJECT7.setText("");
            SUBJECT8.setText("");
            
            totalMarksInput.setText("");
        }
    }
    
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
    
    static void deleteRank() {
    	String query = "DELETE FROM AllocatedRank;";
    	try(Connection connect = connect(); PreparedStatement prep = connect.prepareStatement(query)){
    		prep.executeUpdate();
    	}catch(SQLException e){
    		System.out.println("Oops! Can't Connect to the Database\nFollowing issues have raised\n" + e.getMessage());
    	}
    }
    
     static void insertToStudents(int rollNo, String name,  String dob, String board, int SUB1M, int SUB2M, int SUB3M,int SUB4M, int SUB5M, int SUB6M, int SUB7M,int SUB8M, double total, double cutOff, double percent){
    	 String query = "INSERT INTO StudentInfo(RollNo, Name, DOB, Board, Subject1, Subject2,Subject3,Subject4,Subject5,Subject6,Subject7,Subject8, Total, Percentage, CutOff)"
    			 		+ " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?,?)";
    	 try(Connection connect = connect(); PreparedStatement prep_state = connect.prepareStatement(query)){
    		 prep_state.setInt(1, rollNo);
    		 prep_state.setString(2, name);
    		 prep_state.setString(3, dob);
    		 prep_state.setString(4, board);
    		 prep_state.setInt(5, SUB1M);
    		 prep_state.setInt(6, SUB2M);
    		 prep_state.setInt(7, SUB3M);
             prep_state.setInt(8, SUB4M);
    		 prep_state.setInt(9, SUB5M);
    		 prep_state.setInt(10, SUB6M);
             prep_state.setInt(11, SUB7M);
    		 prep_state.setInt(12, SUB8M);
    		 prep_state.setDouble(13, total);
    		 prep_state.setDouble(14, percent);
    		 prep_state.setDouble(15, cutOff);
    		 prep_state.executeUpdate();
    		 JOptionPane.showMessageDialog(null, "Submitted Successfully");
    	 }catch(SQLException e) {
    		 System.out.println("Oops! Can't Connect to the Database\nFollowing issues have raised\n" + e.getMessage());
    	 }
     }
     
     static void allocateRank() {
    	 String query = "SELECT RollNo, Name FROM StudentInfo ORDER BY CutOff DESC;";
    	 try(Connection connect = connect(); Statement state = connect.createStatement();  ResultSet result = state.executeQuery(query)){
    		int rank = 1;
    		 while(result.next()) {
    			 int rollNo = result.getInt("RollNo");
    			 String name = result.getString("Name");
    			 query = "INSERT INTO AllocatedRank(Rank, Rollno, Name) VALUES(?, ?, ?);";
    			 PreparedStatement prep_state = connect.prepareStatement(query);
    			 prep_state.setInt(1, rank);
    			 prep_state.setInt(2, rollNo);
        		 prep_state.setString(3, name);
        		 prep_state.executeUpdate();
        		 rank++;
    		}
    		 state.close();
    		 result.close();
       	 }catch(SQLException e) {
    	 	System.out.println("Oops! Can't Connect to the Database\\nFollowing issues have raised\n" + e.getMessage());
       	 } 
    		
    	 
    	
     }
      

    static void allocateButtonResponse(){

        //This Method implements the functionality of the Rank Allocate button
        String msg = "Are you sure?";
        String[] options = {"Calculate", "Cancel"};
        int response = JOptionPane.showOptionDialog(null, msg, "Confirmation Alert", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, 0);
        allocateRankButton.setEnabled(false);
        if(response == 0) {
        	deleteRank();
        	allocateRank();
            JOptionPane.showMessageDialog(null, "GPA CALCULATED");
            JOptionPane.showMessageDialog(null, "YOUR CGPA IS :="+GPA);
        }
            allocateRankButton.setEnabled(true);
    }
    
    static void resultButtonResponse(){

        //This Method implements the functionality of the Result button
    	String query = "SELECT * FROM AllocatedRank;";
        try(Connection connect = connect(); Statement state = connect.createStatement()){
        	ResultSet result = state.executeQuery(query);
        	System.out.println("Rank\t|\tRollno\t\t|\tName");
        	System.out.println("------------------------------------------------------------");
        	while(result.next()) {
        		int rank = result.getInt("Rank");
        		int rollNo = result.getInt("RollNo");
        		String name = result.getString("Name");
        		System.out.println(rank + "\t|\t" + rollNo + "\t|\t" + name);
           	}
      	}catch(SQLException e) {
        	 System.out.println("Oops! Can't Connect to the Database\nFollowing issues have raised\n" + e.getMessage());
        }
    }

 
    static void addElements()
    {
        
        //Adding GUI Elements to the Main Frame
        mainFrame.add(nameLabel);
        mainFrame.add(nameInput);
        mainFrame.add(rollNoLabel);
        mainFrame.add(rollNoInput);
        mainFrame.add(dobLabel);
        mainFrame.add(dobInput);
        mainFrame.add(boardSelection);
        mainFrame.add(selectBoard);
        mainFrame.add(SUBJECT1LAB);
        mainFrame.add(SUBJECT1);
        mainFrame.add(SUBJECT2LAB);
        mainFrame.add(SUBJECT2);
        mainFrame.add(SUBJECT3LAB);
        mainFrame.add(SUBJECT3);
        mainFrame.add(SUBJECT4LAB);
        mainFrame.add(SUBJECT4);
        mainFrame.add(SUBJECT5LAB);
        mainFrame.add(SUBJECT5);
        mainFrame.add(SUBJECT6LAB);
        mainFrame.add(SUBJECT6);
        mainFrame.add(SUBJECT7LAB);
        mainFrame.add(SUBJECT7);
        mainFrame.add(SUBJECT8LAB);
        mainFrame.add(SUBJECT8);
        mainFrame.add(totalMarksLabel);
        mainFrame.add(totalMarksInput);
        mainFrame.add(submitButton);
        mainFrame.add(allocateRankButton);
        mainFrame.add(showResult);
        mainFrame.setIconImage(icon.getImage());
    }
    
    
    public static void main(String[] args){
        
        designFrame();
        createElements();
        addElements();       
        //Displaying the Main Frame
        mainFrame.setVisible(true);
    }
}


