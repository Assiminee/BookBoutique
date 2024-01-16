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
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return books;
	}
	
	public ArrayList<String> getSingleBook(String query) {
		ArrayList<String> book = new ArrayList<String>();
		
		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(query);
			while (rs.next()) {
				book.add(rs.getString(2));
				book.add(rs.getString(4));
				book.add(Double.toString(rs.getDouble(6)));
				book.add(Integer.toString(rs.getInt(7)));
				book.add(rs.getString(5));
				book.add(rs.getString(3));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return book;
	}
	
	public ArrayList<String> getNGenres(String tableName, int start, int count) {
		ResultSet res;
		Statement stm;
		ArrayList<String> entries = new ArrayList<String>();
		
		try 
		{
			stm = con.createStatement();
			res = stm.executeQuery("SELECT * FROM " + tableName + " LIMIT " + start + ", " + count);
			while (res.next()) {
				entries.add(res.getString(2));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return entries;
	}
	
	public static int getCount(String tableName) {
		String query = "SELECT COUNT(*) FROM " + tableName;
		ResultSet res;
		Statement stm;
		
		try 
		{
			stm = con.createStatement();
			res = stm.executeQuery(query);
			while (res.next()) {
				return res.getInt(1);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
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

	
	public HashMap<String, Livre> genreBasedSearch(String searchTerm) {
		String genreBasedSearchQuery = "SELECT DISTINCT b.* FROM books b\r\n"
										+ "INNER JOIN belongto bt ON b.ID = bt.bookID\r\n"
										+ "INNER JOIN genres g ON g.ID = bt.genreID\r\n"
										+ "WHERE g.title LIKE \"%" + searchTerm + "%\";";

		return getBooksFromDB(genreBasedSearchQuery);
	}
	
	public int getBookCount(String searchTerm) {
		String query = "(SELECT DISTINCT b.* FROM books b\r\n"
						+ "INNER JOIN belongto bt ON b.ID = bt.bookID\r\n"
						+ "INNER JOIN genres g ON g.ID = bt.genreID\r\n"
						+ "WHERE g.title LIKE \"%" + searchTerm + "%\") AS test;";
		
		return getCount(query);
	}
	
	public HashMap<String, Livre> test(String searchTerm, int start) {
		String genreBasedSearchQuery = "SELECT DISTINCT b.* FROM books b\r\n"
										+ "INNER JOIN belongto bt ON b.ID = bt.bookID\r\n"
										+ "INNER JOIN genres g ON g.ID = bt.genreID\r\n"
										+ "WHERE g.title LIKE \"%" + searchTerm + "%\" LIMIT " + start + ", 8;";
		
		return getBooksFromDB(genreBasedSearchQuery);
	}
	
	public HashMap<String, Livre> search(String searchTerm) {
		HashMap<String, Livre> searchedBooks = new HashMap<String, Livre>();
		
		String authorBasedSearchQuery = "SELECT * FROM books\r\n"
										+ "WHERE authName LIKE \"%" + searchTerm + "%\";";
		
		String titleBasedSearchQuery = "SELECT * FROM books\r\n"
										+ "WHERE title LIKE \"%" + searchTerm + "%\";";
		
		
		searchedBooks.putAll(genreBasedSearch(searchTerm));
		searchedBooks.putAll(getBooksFromDB(authorBasedSearchQuery));
		searchedBooks.putAll(getBooksFromDB(titleBasedSearchQuery));
		return searchedBooks;
	}
}
