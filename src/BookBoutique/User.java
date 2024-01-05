package BookBoutique;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class User implements Cloneable{
	public String firstName, familyName, userName, email, password;
	public char sexe;
	public int age;
	public boolean admin = false;
	
	public User(String[] args) {// String firstName, String familyName, String userName, String password, String email, char sexe, int age) {
		this.userName = args[0];
		this.password = args[1];
		this.firstName = args[2];
		this.familyName = args[3];
		this.sexe = args[4].charAt(0);
		this.email = args[5];
		this.age = Integer.parseInt(args[6]);
		}
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

	@Override
    public User clone() {
        try {
            return (User) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Clone not supported", e);
        }
    }
}