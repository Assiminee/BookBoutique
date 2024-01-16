package BookBoutique;

import java.time.LocalDate;
import java.util.Locale;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class User implements Cloneable{
	public String firstName, familyName, userName, email, password, type;
	public char sexe;
	public Date dateOfBirth;
	public boolean admin = false;
	//age=>dob, type, create public
	public User(String[] args) {// String firstName, String familyName, String userName, String password, String email, char sexe, int age) {
		this.userName = args[0];
		this.password = args[1];
		this.firstName = args[2];
		this.familyName = args[3];
		this.sexe = args[4].charAt(0);
		this.email = args[5];
		this.type = args[6];
		//this.dateOfBirth = parseDate(args[7]);
		}
	
	/*public HashMap<String, User> getUserFromDB(String query) {
		HashMap<String, Livre> books = new HashMap<String, Livre>();
		
		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(query);
			while (rs.next()) {
				String[] bookData = new String[6];
				
				bookData[0] = rs.getString(2);
				bookData[1] = rs.getString(3);
				bookData[2] = rs.getString(4);
				bookData[3] = rs.getString(5);
				bookData[4] = getBookGenres(rs.getInt(1));
				bookData[5] = Double.toString(rs.getDouble(6));
				Livre book = new Livre(bookData);
				books.put(book.title, book);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return books;
	}*/
	
	//getUser to check if a username is already used and to make sure the pwd is correct
	public static ArrayList<User> getUsers() {
		String fileName = "src/usersLogins/users.txt";
        BufferedReader reader = null;
        String line = "";
		ArrayList<User> users = new ArrayList<User>();

        try {
            reader = new BufferedReader(new FileReader(fileName));

            while ((line = reader.readLine()) != null) {
                String[] row = line.split("²");
                User currUser = new User(row);
                users.add(currUser);
            }
        }
        catch (Exception e) {
        	System.out.println("Failed to load user");
            e.printStackTrace();
        }
        finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
		return users;
	}

	private Date parseDate(String dateString) {
	    try {
	        // Adjust the date format pattern based on the format you are receiving
	        SimpleDateFormat dateFormat = new SimpleDateFormat("DD MMM YYYY", Locale.FRENCH);
	        java.util.Date parsedDate = dateFormat.parse(dateString);
	        return new java.sql.Date(parsedDate.getTime());
	    } catch (ParseException e) {
	        e.printStackTrace();
	        // Handle the parsing exception as needed
	        return null;
	    }
	}
	
	@Override
    public User clone() {
        try {
            return (User) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Clone not supported", e);
        }
    }
}