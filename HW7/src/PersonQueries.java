//20200113
// PreparedStatements used by the Address Book application
//Connect to addresses and create PreparedStatements
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

//連線database後把東西抓回來給addressbookdisplay顯示
public class PersonQueries {
	private static final String URL = "jdbc:mysql://localhost/member?serverTimezone=CST";
	private static final String USERNAME = "java";
	private static final String PASSWORD = "java";

	private Connection connection; // manages connection

	// SQL statement is precompiled and stored in a PreparedStatement object
	private PreparedStatement selectAllPeople;
	private PreparedStatement selectPeopleByPhoneOrName;
	private PreparedStatement insertNewPerson;
	private PreparedStatement selectPeopleByName;
	private PreparedStatement updatePeopleByName;
	private PreparedStatement deletePeopleByName;

	// constructor
	public PersonQueries() {
		try {
			connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

			// create query that selects all entries in the AddressBook
			selectAllPeople = // browseButton
					connection.prepareStatement("SELECT * FROM people");

			selectPeopleByPhoneOrName = connection.prepareStatement("SELECT * FROM people WHERE name = ? OR phone = ?");

			// create insert that adds a new entry into the database
			insertNewPerson = connection.prepareStatement(// insertButton
					"INSERT INTO PEOPLE " + "(name, type, phone) " + "VALUES (?, ?, ?)");
			
			selectPeopleByName = connection.prepareStatement("SELECT * FROM people WHERE name = ?");
			
			updatePeopleByName = connection.prepareStatement("UPDATE people SET name = ?, type = ?, phone = ? WHERE name = ?");
			
			deletePeopleByName = connection.prepareStatement("DELETE FROM people WHERE name = ?");
			
			
			
			
			
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
			System.exit(1);
		}
	} // end PersonQueries constructor

	// select all of the addresses in the database
	public List<Person> getAllPeople() {
		List<Person> results = null;
		ResultSet resultSet = null;

		try {
			// executeQuery returns ResultSet containing matching entries
			resultSet = selectAllPeople.executeQuery();
			results = new ArrayList<Person>();

			while (resultSet.next()) {
				results.add(new Person(resultSet.getInt("MemberID"), resultSet.getString("name"),
						resultSet.getString("type"), resultSet.getString("phone")));
			}
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		} finally {
			try {
				resultSet.close();
			} catch (SQLException sqlException) {
				sqlException.printStackTrace();
				close();
			}
		}

		return results; // return resultSet的吉果
	}
	
	
	// select person by last name   
	   public List< Person > getPeopleByPhoneOrName(String filter)
	   {
	      List< Person > results = null;
	      ResultSet resultSet = null;

	      try {
	    	  selectPeopleByPhoneOrName.setString(1, filter); // specify last name
	    	  selectPeopleByPhoneOrName.setString(2, filter); 
	         // executeQuery returns ResultSet containing matching entries
	         resultSet = selectPeopleByPhoneOrName.executeQuery(); 

	         results = new ArrayList< Person >();

	         while (resultSet.next()){
	            results.add(new Person(resultSet.getInt("MemberID"),
	               resultSet.getString("name"),
	               resultSet.getString("type"),
	               resultSet.getString("phone")));
	         } 
	      } 
	      catch (SQLException sqlException)
	      {
	         sqlException.printStackTrace();
	      } 
	      finally
	      {
	         try 
	         {
	            resultSet.close();
	         }
	         catch (SQLException sqlException)
	         {
	            sqlException.printStackTrace();         
	            close();
	         }
	      } 
	      
	      return results;
	   } 
	   
	   public List< Person > getPeopleByName(String name)
	   {
	      List< Person > results = null;
	      ResultSet resultSet = null;

	      try 
	      {
	         selectPeopleByName.setString(1, name); // specify last name

	         // executeQuery returns ResultSet containing matching entries
	         resultSet = selectPeopleByName.executeQuery(); 

	         results = new ArrayList< Person >();

	         while (resultSet.next())
	         {
	            results.add(new Person(resultSet.getInt("MemberID"),
	               resultSet.getString("name"),
	               resultSet.getString("type"),
	               resultSet.getString("phone")));
	         } 
	      } 
	      catch (SQLException sqlException)
	      {
	         sqlException.printStackTrace();
	      } 
	      finally
	      {
	         try 
	         {
	            resultSet.close();
	         }
	         catch (SQLException sqlException)
	         {
	            sqlException.printStackTrace();         
	            close();
	         }
	      } 
	      
	      return results;
	   } 
	   
	   // add an entry
	   public int addPerson(String name, String type, String phone)
	   {
	      int result = 0;
	      
	      // set parameters, then execute insertNewPerson
	      try 
	      {
	         insertNewPerson.setString(1, name);
	         insertNewPerson.setString(2, type);
	         insertNewPerson.setString(3, phone);

	         // insert the new entry; returns # of rows updated
	         result = insertNewPerson.executeUpdate(); //insert非select要用executeUpdate，回傳共insert多少筆
	      }
	      catch (SQLException sqlException)
	      {
	         sqlException.printStackTrace();
	         close();
	      } 
	      
	      return result;
	   } 
	   
	   public int updatePersonByName(String oldName, String name, String type, String phone)
	   {
	      int result = 0;
	      
	      // set parameters, then execute insertNewPerson
	      try 
	      {
	    	  updatePeopleByName.setString(1, name);
	    	  updatePeopleByName.setString(2, type);
	    	  updatePeopleByName.setString(3, phone);
	    	  updatePeopleByName.setString(4, oldName);

	         // insert the new entry; returns # of rows updated
	         result = updatePeopleByName.executeUpdate(); //insert非select要用executeUpdate，回傳共insert多少筆
	      }
	      catch (SQLException sqlException)
	      {
	         sqlException.printStackTrace();
	         close();
	      } 
	      
	      return result;
	   } 
	   
	   public int deletePersonByName(String name){
	      int result = 0;
			
	      // set parameters, then execute insertNewPerson
	      try {
	         // insert the new entry; returns # of rows updated
	    	  deletePeopleByName.setString(1, name);
	          result = deletePeopleByName.executeUpdate(); //insert非select要用executeUpdate，回傳共insert多少筆
	      }
	      catch (SQLException sqlException){
	         sqlException.printStackTrace();
	         close();
	      } 
	      
	      return result;
	   } 

	// close the database connection
	public void close() {
		try {
			connection.close();
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
	}
} // end class PersonQueries

/**************************************************************************
 * (C) Copyright 1992-2014 by Deitel & Associates, Inc. and * Pearson Education,
 * Inc. All Rights Reserved. * * DISCLAIMER: The authors and publisher of this
 * book have used their * best efforts in preparing the book. These efforts
 * include the * development, research, and testing of the theories and programs
 * * to determine their effectiveness. The authors and publisher make * no
 * warranty of any kind, expressed or implied, with regard to these * programs
 * or to the documentation contained in these books. The authors * and publisher
 * shall not be liable in any event for incidental or * consequential damages in
 * connection with, or arising out of, the * furnishing, performance, or use of
 * these programs. *
 *************************************************************************/
