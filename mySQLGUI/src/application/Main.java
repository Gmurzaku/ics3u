package application;
	
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;


public class Main extends Application {
	
	private static ResultSet rs;
	private Connection con ;
    private Statement st ;
    private ResultSetMetaData rsMetaData;
    
    private String dbType = "jdbc:mysql://";
    private String host = "sql9.freesqldatabase.com:";
    private String port = "3306";
    private String dbName = "/sql9345474";
    private String url = dbType+host+port+dbName;
    private String user = "sql9345474";
    private String password = "jZU3r3wg1J";
    private String query = "SELECT * FROM users";
    
    private TextArea display;
    private Button btn;
    private ScrollPane scrollPane;
    private GridPane root;
	@Override
	public void start(Stage primaryStage) {
			setup();
			Scene scene = new Scene(root,500,500);
			primaryStage.setScene(scene);
			primaryStage.show();
			actions();
	}
	public void setup() {
		btn = new Button();
        btn.setText("Display data");
        display = new TextArea();
    	display.setMinSize(100, 100);
    	display.setEditable(false);
    	scrollPane = new ScrollPane();
    	scrollPane.setContent(display);
        root = new GridPane();
        root.setHgap(10);
    	root.setVgap(10);
    	GridPane.setConstraints(btn, 1, 1);
    	GridPane.setConstraints(scrollPane, 1, 2);
        root.getChildren().addAll(btn,scrollPane);
	}
	public void actions() {
		btn.setOnAction((ActionEvent event) -> {
            try {
            	if (connect(url,user,password)) {
            		display.setText(executeQuery(query));
            	}else {
            		display.setText("not able to connect!");
            	}
				
			} catch (SQLException e) {
				display.setText("cannot get data");
			}
        });
	}
	public boolean connect(String url, String user, String password){
		try {
			con = DriverManager.getConnection(url, user, password);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return false;
		}
	}
	public String executeQuery(String query) throws SQLException {
		String result = "";
		st = con.createStatement();
		rs = st.executeQuery(query);
		rsMetaData = rs.getMetaData();
		int cols = rsMetaData.getColumnCount();
		if(cols>1) {
			while(rs.next()) {
				//iterate through columns
				for(int i = 0 ; i < cols; i++) {
					if(i+1==cols) {
						result+=rs.getString(i+1)+"\n";
					}else {
						result+=rs.getString(i+1)+", ";
					}
				}
			}
		}
		return result;
	}
	public static void main(String[] args) {
		launch(args);
	}
}
