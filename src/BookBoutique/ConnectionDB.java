package BookBoutique;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class ConnectionDB {
	static public Connection con;
	
	public ConnectionDB() {
		establishConnection();
	}
	
	private String getBookGenres(int bookID) {
		String genres = "";
		Statement stm;
		ResultSet res;
		
		try {
			stm = con.createStatement();
			res = stm.executeQuery("SELECT DISTINCT g.title\r\n"
								 	+ "FROM books b\r\n"
								 	+ "INNER JOIN belongto bt ON b.ID = bt.bookID\r\n"
								 	+ "INNER JOIN genres g ON g.ID = bt.genreID\r\n"
								 	+ "WHERE bt.bookID = " + bookID);
			while (res.next()) {
				genres += res.getString(1) + "/";
			}
			return genres.substring(0, genres.length() - 1);
		} catch (SQLException e) {
			e.printStackTrace();
			return genres;
		}
	}
	
	public ArrayList<String> getAllGenres() {
		ArrayList<String> allGenres = new ArrayList<String>();
		
		Statement stm;
		ResultSet res;
		try {
			stm = con.createStatement();
			res = stm.executeQuery("SELECT title FROM genres");
			while (res.next()) {
				allGenres.add(res.getString(1));
			}
			return allGenres;
		} catch (SQLException e) {
			e.printStackTrace();
			return allGenres;
		}
	}
	
	public HashMap<String, Livre> getBooksFromDB(String query) {
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
			return books;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private void establishConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookboutiquedb?useUnicode=true&characterEncoding=utf-8","root","");
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
