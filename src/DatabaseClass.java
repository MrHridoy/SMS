import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JOptionPane;

public class DatabaseClass{
      Connection conn=null;
      public static Connection ConnectDB(){
    	  try {
    		  Class.forName("com.mysql.cj.jdbc.Driver");
        	  Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/db_student","root","");
        	  System.out.println("Database Connected");
        	  
        	  return conn;
    	  }
    	  catch(Exception e){
    		  JOptionPane.showMessageDialog(null, "Database Connection Failed");
    		  
    		  return null; 
    		 
    	  }
    	  
    	  
      }
}
