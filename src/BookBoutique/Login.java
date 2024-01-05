package BookBoutique;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentListener;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Map.Entry;

import javax.swing.border.BevelBorder;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.*;
import java.awt.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class Login extends JFrame {
	//User's info
	char seXe;
	int agE;
	String userName, passWord, passWordComfirm, firstName, lastName, emaiL;
	//Class attributes
	JTextField usernameTextField;
	JTextField passwordTextField;
	JTextField confirmPWDTextField;
	JTextField emailTextField;
	JTextField ageTextField;
	JTextField fNameTextField;
	JTextField lNameTextField;
	JLabel ageError;
	//Checkers
	boolean pwdChecker = false, ageChecker = true, allFieldsChecker = false,
			existantUsername = false, wrongPassword = false, sexeSelected = false,
			isMaleSelected, isFemaleSelected, connected = false, firstOpen = true;
	//hashmap for reading files
	public static HashMap<String, User> users = new HashMap<String, User>();
	
	//class constructor
	public Login() {
		//this.setLocationRelativeTo(null);
		setLayout(null);
		setIconImage(Controlleur.logo.getImage());
		setTitle("BookBoutique");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setVisible(false);
	}
	
	/**
	 * backgroundImage - creates a High end quality of qualidad image
	 * @param path: path to where the image is stored
	 * @param width: image's width
	 * @param height: image's height
	 * @return a background image
	 */
	
	private DisplayImage backgroundImage(String path, int width, int height) {
		DisplayImage image = new DisplayImage(path, 0, 0, width, height);
		image.setBounds(0, 0, width, height);
		return image;
	}
	
	/**
	 * addFields - creates the login panel
	 */
	
	private void addFields() {
		JPanel contentHolder = backgroundImage("src\\Images\\ClosedBook.png", 350, 440);
		JLabel username = new JLabel();
		usernameTextField = new JTextField(20);
		
		JLabel password = new JLabel();
		passwordTextField = new JTextField(20);
		
		JButton login = new JButton();
		JButton createAccount = new JButton();
		
		JLabel usernameError = new JLabel();
		JLabel passwordError = new JLabel();
		
		contentHolder.setLayout(null);
		
		setLabels(username, "Username", 80, 100, 160, 40, 20, Color.WHITE);		
		setLabels(password, "Password", 80, 180, 160, 40, 20, Color.WHITE);
		
		setTextFields(usernameTextField, 80, 140, 215, 20);
		setTextFields(passwordTextField, 80, 220, 215, 20);
		
		setButtons(login, "Login", 80, 280, 80, 20, Color.WHITE, Color.BLACK);
		setButtons(createAccount, "Create Account", 175, 280, 120, 20, Color.WHITE, Color.BLACK);
		
		setErrorlabelVisibles(usernameError, "Wrong username", 80, 170, 105, 15, 10);
		setErrorlabelVisibles(passwordError, "Wrong password", 80, 250, 95, 15, 10);
		
		usernameError.setVisible(false);
		passwordError.setVisible(false);
		
		usernameTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
            	
            }

            @Override
            public void focusLost(FocusEvent e) {
            	if (usernameTextField.getText().isBlank()) {
            		setErrorlabelInvisible(usernameError);
            	}
            	else if (!existantLoginUsername(usernameTextField.getText())) {
            		setErrorlabelVisible(usernameError, "Please register first");
            	}
            	else {
            		setErrorlabelInvisible(usernameError);
            	}
            }
        });
		
		login.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				wrongPassword = passwordCorrect(usernameTextField.getText(), passwordTextField.getText());
				boolean existantUsername = existantLoginUsername(usernameTextField.getText());
				if(!leftChecks()) {
					setErrorlabelVisible(passwordError, "Fill all text fields");
				}
				else if(wrongPassword) {
					setErrorlabelVisible(passwordError, "Wrong password");
				}
				else if (!existantUsername) {
					setErrorlabelVisible(usernameError, "Please register first");
				}
				else if (existantUsername && !wrongPassword) {
					connected = true;
					Controlleur.onHover(Controlleur.loginButton, "Log Out");
		    		Controlleur.cart.loadCart();
					dispose();
					Controlleur.aboutUs.setTextFields(Controlleur.connectedUser);
				}
				revalidate();
				repaint();
				}
			});
		
		createAccount.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Timer timer = new Timer();
				getContentPane().removeAll();
				setVisible(false);
				setBounds(0, 0, 800, 490);
				createRegisterPanel();
				setLocationRelativeTo(null);
				revalidate();
				repaint();
				timer.schedule(new TimerTask() {
		            @Override
		            public void run() {
		            	setVisible(true);
		            }
		        }, 20);
			}
		});
		
		contentHolder.add(username);
		contentHolder.add(usernameTextField);
		contentHolder.add(usernameError);
		contentHolder.add(password);
		contentHolder.add(passwordTextField);
		contentHolder.add(passwordError);
		contentHolder.add(login);
		contentHolder.add(createAccount);
		add(contentHolder);
	}
	
	/**
	 * setErrorlabelVisibles - fix the style of the error labels
	 * @param label: label to be styled
	 * @param text: label's text
	 * @param x: x coordinate
	 * @param y: y coordinate
	 * @param width: label's width
	 * @param height: label's height
	 * @param weight: label's weight
	 */
	
	private void setErrorlabelVisibles(JLabel label, String text, int x, int y, int width,
								int height, int weight) {
		label.setBounds(x, y, width, height);
		label.setText(text);
		label.setFont(new Font("Georgia", Font.BOLD, weight));
		label.setForeground(Color.RED);
		label.setBorder(BorderFactory.createRaisedSoftBevelBorder());
		label.setVisible(false);
	}
	
	/**
	 * setLabels - styles labels
	 * @param label: label to be styled
	 * @param text: label's text
	 * @param x: x coordinate
	 * @param y: y coordinate
	 * @param width: label's width
	 * @param height: label's height
	 * @param weight: label's weight
	 * @param color: label's foreground color
	 */
	
	private void setLabels(JLabel label, String text, int x, int y, int width,
						   int height , int weight, Color color) {
		label.setBounds(x, y, width, height);
		label.setText(text);
		label.setFont(new Font("Broadway", Font.BOLD, weight));
		label.setForeground(color);
	}
	
	/**
	 * setTextFields -sts the style of the textFields
	 * @param textField: the text field to be styled
	 * @param x: x coordinates
	 * @param y: y coordinates
	 * @param width: textField's width
	 * @param height: textField's height
	 */
	
	private void setTextFields(JTextField textField, int x, int y, int width, int height) {
		textField.setBounds(x, y, width, height);
		textField.setBackground(Color.WHITE);
		textField.setBorder(BorderFactory.createLoweredBevelBorder());
	}
	
	/**
	 * setButtons - sets the style of JButtons
	 * @param btn: the button to be styled
	 * @param name: its text
	 * @param x: x coordinate
	 * @param y: y coordinate
	 * @param width: the button's width
	 * @param height: the button's height
	 * @param background: button's background color
	 * @param foreground: button's foreground color
	 */
	
	private void setButtons(JButton btn, String name, int x, int y, int width,
							int height, Color background, Color foreground) {
		btn.setBounds(x, y, width, height);
		btn.setText(name);
		btn.setFont(new Font("Georgia", Font.PLAIN, 15));
		btn.setBackground(background);
		btn.setForeground(foreground);
		btn.setBorder(BorderFactory.createRaisedBevelBorder());
	}
	
	/**
	 * createRegisterPanel - creates the make a new account panel
	 */
	
	private void createRegisterPanel() {
		JPanel contentHolder = backgroundImage("src\\Images\\OpenBook.png", 780, 450);
		
		JLabel username = new JLabel();
		usernameTextField = new JTextField(20);
		JLabel usernameError = new JLabel();
		
		JLabel password = new JLabel();
		passwordTextField = new JTextField(20);
		JLabel passwordError = new JLabel();
		
		JLabel confirmPWD = new JLabel();
		confirmPWDTextField = new JTextField(20);
		JLabel confirmPWDError = new JLabel();
		
		JLabel fName = new JLabel();
		fNameTextField = new JTextField(20);
		
		JLabel lName = new JLabel();
		lNameTextField = new JTextField(20);
		
		ageError = new JLabel();
		JLabel age = new JLabel();
		ageTextField = new JTextField();
		
		JPanel gender = new JPanel();
		JLabel genderText = new JLabel("Gender");
		JLabel genderError = new JLabel();
        ButtonGroup sexe = new ButtonGroup();
		JRadioButton male = new JRadioButton("M");
	    JRadioButton female = new JRadioButton("F");
	    sexe.add(male);
	    sexe.add(female);
	    gender.add(male);
	    gender.add(female);
	    
	    JLabel email = new JLabel();
		emailTextField = new JTextField(20);
		JLabel emailError = new JLabel();
		
		JLabel allFieldsError = new JLabel();
		
		JButton register = new JButton();
		JButton goBack = new JButton();
		
		contentHolder.setLayout(null);
		
		// Left Side
		setLabels(username, "Username", 170, 70, 160, 40, 15, Color.decode("#625b51"));	
		setTextFields(usernameTextField, 170, 100, 200, 20);
		setErrorlabelVisibles(usernameError, "Username already used", 170, 125, 140, 15, 10);
		
		setLabels(password, "Password", 160, 150, 160, 40, 15, Color.decode("#625b51"));
		setTextFields(passwordTextField, 160, 180, 200, 20);
		setErrorlabelVisibles(passwordError, "Wrong password", 160, 205, 150, 15, 10);
		
		setLabels(confirmPWD, "Confirm Password", 150, 230, 200, 40, 15, Color.decode("#625b51"));
		setTextFields(confirmPWDTextField, 150, 260, 200, 20);
		setErrorlabelVisibles(confirmPWDError, "Password doesn't match", 150, 285, 140, 15, 10);
		
		setLabels(age, "Age", 140, 300, 50, 50, 15, Color.decode("#625b51"));
		ageTextField.setBounds(180, 314, 40, 20);
		setErrorlabelVisibles(ageError, "Age out of range", 165, 337, 70, 15, 10);
		
		setPanel(gender, 230, 308, 160, 50, 15);
		setLabels(genderText, "Gender:", 230, 300, 100, 50, 15, Color.decode("#625b51"));
		setErrorlabelVisibles(genderError, "Select a gender", 230, 337, 88, 15, 10);
		male.setBounds(300, 320, 50, 10);
		male.setOpaque(false);
		
		female.setBounds(380, 320, 50, 10);
		female.setOpaque(false);
		
		gender.add(genderText);
		gender.add(male);
		gender.add(female);
		gender.add(genderError);
		
		// Right Side
		setLabels(fName, "First Name", 428, 70, 160, 40, 15, Color.decode("#625b51"));	
		setTextFields(fNameTextField, 428, 100, 200, 20);
		
		setLabels(lName, "Last Name", 438, 150, 160, 40, 15, Color.decode("#625b51"));
		setTextFields(lNameTextField, 438, 180, 200, 20);
		
		setLabels(email, "Email", 448, 230, 200, 40, 15, Color.decode("#625b51"));
		setTextFields(emailTextField, 448, 260, 200, 20);
		setErrorlabelVisibles(emailError, "Invalid Email", 448, 285, 145, 15, 10);
		
		setErrorlabelVisibles(allFieldsError, "Fill all fields", 448, 300, 80, 15, 10);
		
		setButtons(register, "Register", 500, 320, 70, 25, Color.decode("#625b51"), Color.WHITE);
		setButtons(goBack, "Already have an account?", 440, 345, 200, 25, Color.decode("#625b51"), Color.WHITE);
		
		usernameTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
            	
            }

            @Override
            public void focusLost(FocusEvent e) {
            	if (existantLoginUsername(usernameTextField.getText().trim())) {
            		setErrorlabelVisible(usernameError, "Username already used");
            	}
            	else {
            		setErrorlabelInvisible(usernameError);
            	}
            }
        });
		
		confirmPWDTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
            	
            }

            @Override
            public void focusLost(FocusEvent e) {
            	if (!passwordFieldsCheck()) {
            		setErrorlabelVisible(confirmPWDError, "Password doesn't match");
            	}
            	else {
            		setErrorlabelInvisible(confirmPWDError);
            	}
            }
        });
		
		emailTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
            	
            }

            @Override
            public void focusLost(FocusEvent e) {
            	if (!isValidEmail(emailTextField.getText().trim())) {
				   setErrorlabelVisible(emailError, "Please enter a valid email");
            	}
            	else if (existantEmail(emailTextField.getText().trim())) {
				   setErrorlabelVisible(emailError, "Email already used!");
            	}
            	else if (isValidEmail(emailTextField.getText().trim()) && !existantEmail(emailTextField.getText().trim())){
            		setErrorlabelInvisible(emailError);
            	}
            }
        });
		
		usernameTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
            	
            }

            @Override
            public void focusLost(FocusEvent e) {
            	if (existantLoginUsername(usernameTextField.getText().trim())) {
         		   setErrorlabelVisible(usernameError, "User name already exists");
            	}
            	else {
            		setErrorlabelInvisible(usernameError);
            	}
            }
        });
		
		ageTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
            	
            }

            @Override
            public void focusLost(FocusEvent e) {
            	if (ageCheck()) {
            		setErrorlabelVisible(ageError, "Age invalid");
            	}
            	else {
            		setErrorlabelInvisible(ageError);
            	}
            	
            }
        });
		
		male.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
            	
            }

            @Override
            public void focusLost(FocusEvent e) {
            	if (!male.isSelected() && !female.isSelected()) {
            		setErrorlabelVisible(genderError, "Select a gender");
            		sexeSelected = false;
            	}
            	else {
            		setErrorlabelInvisible(genderError);
            	}
            }
        });
		
		male.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seXe = 'M';
                sexeSelected = true;
                setErrorlabelInvisible(genderError);
            }
        });

        female.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seXe = 'F';
                sexeSelected = true;
                setErrorlabelInvisible(genderError);
            }
        });
		
		//sexeSelected();
		register.addActionListener(new ActionListener(){
			   public void actionPerformed(ActionEvent ae){
				   userName = usernameTextField.getText().trim();
				   passWord = passwordTextField.getText().trim();
				   firstName = fNameTextField.getText().trim();
				   lastName = lNameTextField.getText().trim();
				   emaiL = emailTextField.getText().trim();
				   empty();
				   allChecks(allFieldsError);
			   }
			});
		
		goBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Timer timer = new Timer();
				getContentPane().removeAll();
				setVisible(false);
				setBounds(0, 0, 365, 455);
				addFields();
				revalidate();
				repaint();
				timer.schedule(new TimerTask() {
		            @Override
		            public void run() {
		            	setVisible(true);
		            	setLocationRelativeTo(null);
		            }
		        }, 20);
			}
		});
		
		// Left side
		contentHolder.add(username);
		contentHolder.add(usernameTextField);
		contentHolder.add(usernameError);
		contentHolder.add(password);
		contentHolder.add(passwordTextField);
		contentHolder.add(passwordError);
		contentHolder.add(confirmPWD);
		contentHolder.add(confirmPWDTextField);
		contentHolder.add(confirmPWDError);
		contentHolder.add(age);
		contentHolder.add(ageTextField);
		contentHolder.add(ageError);
		contentHolder.add(gender);
		
		// Right side
		contentHolder.add(fName);
		contentHolder.add(fNameTextField);
		contentHolder.add(lName);
		contentHolder.add(lNameTextField);
		contentHolder.add(email);
		contentHolder.add(emailTextField);
		contentHolder.add(emailError);
		contentHolder.add(allFieldsError);
		contentHolder.add(register);
		contentHolder.add(goBack);

		add(contentHolder);
	}
	
	/**
	 * empty - checks if all the fields are empty or not
	 * @return true if one of more fields are empty and false otherwise
	 */
	
	public boolean empty(){
		boolean pwd = passwordTextField.getText().isEmpty();
		boolean comfirmPwd = confirmPWDTextField.getText().isEmpty();
		boolean userName = usernameTextField.getText().trim().isEmpty();
		boolean firstName = fNameTextField.getText().trim().isEmpty();
		boolean familyName = lNameTextField.getText().trim().isEmpty();
		boolean age = ageTextField.getText().isEmpty();
		String mail = emailTextField.getText().trim();
		
		if ((pwd || comfirmPwd || userName || firstName || familyName || age || existantEmail(mail) || sexeSelected())) {
			allFieldsChecker = true;
			return true;
		}
		else
			allFieldsChecker = false;
		return false;
	}
	
	/**
	 * setPanel - sets a panel's bounds, font and opacity
	 * @param panel: the panel to be set
	 * @param x: x axis where the panel starts
	 * @param y; y axis where the panel starts
	 * @param width: the panel's width
	 * @param height: the panel's height
	 * @param weight: the panel's weight
	 */
	
	private void setPanel(JPanel panel, int x, int y, int width,
			   				int height , int weight) {
		panel.setBounds(x, y, width, height);
		panel.setFont(new Font("Broadway", Font.BOLD, weight));
		panel.setOpaque(false);
	}
	
	/**
	 * passwordFieldsCheck - checks if the fields: password and comfirmPassword r identical
	 * @return true if they are and false otherwise
	 */
	
	private boolean passwordFieldsCheck() {
		if (passwordTextField.getText().equals(confirmPWDTextField.getText())) {
			pwdChecker = true;
			return true;
		}
		else {
			pwdChecker = false;
			return false;
		}
	}
	
	/**
	 * appear - sets the jframe to visible is it wasn't before. Also sets its dimensions
	 */
	
	public void appear() {
		if (firstOpen) {
			setBounds(0, 0, 365, 455);
			setLocationRelativeTo(null);
			addFields();
			firstOpen = false;
		}
		toFront();
		setVisible(true);
	}
	
	/**
	 * sexeSelected - checks if either of the sexe radio buttons are selected
	 * @return true if one of them is selected and false otherwise
	 */
	
	private boolean sexeSelected() {
		if (isMaleSelected || isFemaleSelected) {
			allFieldsChecker = true;
			return true;
		}
		return false;
	}
	
	/**
	 * allChecks - turns an object to string then sends it to the file if all tests are passed
	 * @param allFieldsError: the error label to be shown in case "saisie" was wrong
	 */
	
	private void allChecks(JLabel allFieldsError) {
		if (!empty() && sexeSelected  && ageChecker && !existantEmail(emaiL) && isValidEmail(emaiL) && !existantLoginUsername(userName)) {
			String[] userString = new String[7];
			userString[0] = userName;
			userString[1] = passWord; 
			userString[2] = firstName;
			userString[3] = lastName; 
			userString[4] = String.valueOf(seXe); 
			userString[5] = emaiL;
			userString[6] = Integer.toString(agE);
			User newUser = new User(userString);
			users.put(userName, newUser);
			connected = true;
			for (HashMap.Entry<String, User> entry : users.entrySet()) {
	            User user = entry.getValue();
	            saveToFile(user);
	        }
    		Controlleur.onHover(Controlleur.loginButton, "Log Out");
    		Controlleur.connectedUser = newUser;
    		dispose();
    		Controlleur.aboutUs.setTextFields(Controlleur.connectedUser);

			}
		if (empty()){
			allFieldsError.setVisible(true);
		}
		revalidate();
		repaint();
	}
	
	/**
	 * ageCheck - checks if the age entered is valid or not
	 * @return: true if the age is either out of bounds or not integer, and false otherwise
	 */
	
	private boolean ageCheck() {
		try {
			   agE = Integer.parseInt(ageTextField.getText());
		   }
		   catch (Exception e) {
			   ageChecker = false;
			   return true;
		   }
			if (agE < 16 || agE > 99) {
				   ageChecker = false;
				   return true;
			   }
			else {
				ageChecker = true;
				return false;
			}
	}
	
	/**
	 * userToString - turns a User object into a string
	 * @param user: the object to be turned into string
	 * @return a string of the user's attributes 
	 */
	
	private static String userToString(User user) {
        // Convert the User object to a string representation
        return user.userName + "²" + user.password + "²" + user.firstName + 
        		"²" + user.familyName + "²" + user.sexe + "²" + user.email + "²" + user.age + System.lineSeparator();
    }
	
	/**
	 * leftChecks - checks if all login fields aree filled
	 * @return true if both r full and false otherwise
	 */
	
	private boolean leftChecks() {
		if(usernameTextField.getText().trim().isEmpty() || passwordTextField.getText().trim().isEmpty()) {
			return false;
		}
		return true;
	}
	
	/**
	 * saveToFile - exports a string to a file
	 * @param user: the object to save
	 */
	
	private void saveToFile(User user) {
		Path filePath = Path.of("src/usersLogins/users.txt");
        try {
            // Write the string to the file
            Files.write(filePath, userToString(user).getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            System.out.println("String written to file successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	/**
	 * existantUsername - checks if the username is already used
	 * @return true if the username already exists and false otherwise
	 */
	
	private boolean existantLoginUsername(String n) {
		ArrayList<User> usersList = User.getUsers();
		for(User currUser : usersList) {
			if (currUser.userName.equalsIgnoreCase(n)) {
				Controlleur.connectedUser = currUser;
				return true;
			}
		}
		return false;
	}
	
	/**
	 * existantEmail - checks if the email already exists in the file
	 * @param: email! mail to be checked
	 * @return: true if the email is already used and false otherwise
	 */
	
	private boolean existantEmail(String email) {
		ArrayList<User> usersList = User.getUsers();
		for(User currUser : usersList) {
			if (currUser.email.equals(email)) {
					return true;
			}
		}
		return false;
	}
	
	/**
	 * passwordCorred - checks if a password is correct for a given username
	 * @param username: the username to check the password for
	 * @param password: the password entered to compare with the one in the file
	 * @return true if the password is incorrect and false otherwise
	 */
	
	//returns true if the pwd is wrong n false if iss correct 
	private boolean passwordCorrect(String username, String password) {
		ArrayList<User> usersList = User.getUsers();
		for(User currUser : usersList) {
			if (currUser.userName.equalsIgnoreCase(username)) {
				if (!currUser.password.equals(password)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * isValidEmail - checks if the email written is valid
	 * @param email: string to verify
	 * @return: true if the email is valid and false otherwise
	 */
	
	private static boolean isValidEmail(String email) {
        // Define a regex pattern for basic email validation
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        // Create a Pattern object
        Pattern pattern = Pattern.compile(emailRegex);

        // Create a matcher object
        Matcher matcher = pattern.matcher(email);

        // Return true if the email matches the pattern
        return matcher.matches();
    }
	
	/**
	 * setErrorlabelVisible - sets error labels visible after adding an error message
	 * @param errorLabel: the error label to be set visible
	 * @param errorMessage: the error message
	 */
	
	private void setErrorlabelVisible(JLabel errorLabel, String errorMessage) {
		errorLabel.setText(errorMessage);
		errorLabel.setVisible(true);
		revalidate();
		repaint();
	}
	
	/**
	 * setErrorlabelInvisible - sets e
	 * @param errorLabel
	 */
	
	private void setErrorlabelInvisible(JLabel errorLabel) {
		errorLabel.setVisible(false);
		revalidate();
		repaint();
	}
}