package BookBoutique;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class Login extends JFrame {
	
	JPanel loginPanel;
	JPanel buttons;
	
	JLabel username;
	JLabel password;
	JLabel comfirmPassword;
	JLabel errorMsg;
	
	JTextField usernameT;
	JTextField passwordT;
	JTextField comfirmPasswordT;
	
	JButton login;
	JButton register;
	JButton createNewAccount;
	
	String userName, passWord, passWordComfirm;
	
	public static Login thisFrame;
	public boolean connected = false;
	public String connectedUser;
	
	public Login() {
		thisFrame = this;
		this.connectedUser = "";
		this.connected = false;
		loginPanel = new JPanel();
		
		setLayout(new FlowLayout());
        setTitle("BookBoutique");
        setIconImage(Controlleur.logo.getImage());
        
		createLoginPanel();
        add(loginPanel);
	}
	
	public void createLoginPanel() {
		loginPanel.removeAll();
		buttons = new JPanel();
		
		username = new JLabel("Username:");
		password = new JLabel("Password:");
		errorMsg = new JLabel("Must be similar to password!");
		
		usernameT = new JTextField(30);
		passwordT = new JTextField(30);
		
		login = new JButton("Log in");
		register = new JButton("Register");
		errorMsg.setVisible(false);
		
		login.setBackground(new Color(0x732d21));
	    login.setForeground(Color.WHITE);
	    login.setFocusable(false);
	    
	    register.setBackground(new Color(0x732d21));
	    register.setForeground(Color.WHITE);
	    register.setFocusable(false);
	    
	    register.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				createRegisterPanel();
				}
			});
	    buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
	    buttons.add(login);
	    buttons.add(register);
	    
		
		loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
		loginPanel.add(username);
		loginPanel.add(usernameT);
		loginPanel.add(password);
		loginPanel.add(passwordT);
		loginPanel.add(buttons);
		revalidate();
		repaint();
		
	}
	
	public void displayLogin() {
		setVisible(true);
		pack();
	}
	
	public void createRegisterPanel() {
		comfirmPassword = new JLabel("Comfirm password:");
		comfirmPasswordT = new JTextField(30);
		createNewAccount = new JButton("Create new account");
		
		createNewAccount.setBackground(new Color(0x732d21));
		createNewAccount.setForeground(Color.WHITE);
	    createNewAccount.setFocusable(false);

		buttons.add(createNewAccount);
		buttons.remove(login);
		buttons.remove(register);
	    
		loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
		loginPanel.add(comfirmPassword);
		loginPanel.add(comfirmPasswordT);
		
		createNewAccount.addActionListener(new ActionListener(){
			   public void actionPerformed(ActionEvent ae){
				   passWord = passwordT.getText();
				   passWordComfirm = comfirmPasswordT.getText();
				   if (!passWord.equals(passWordComfirm))
				   {
					   errorMsg.setVisible(true);
				   }
				   else
				   {
					   errorMsg.setVisible(false);
					   connected = true;
					   connectedUser = usernameT.getText();
					   if (connected == true) {
			        		for (MouseListener ml : Controlleur.login.getMouseListeners()) {
			        			Controlleur.login.removeMouseListener(ml);
			        		}
			        		Controlleur.onHover(Controlleur.loginButton,connectedUser);
			        	}
					   thisFrame.dispose();
				   }
			   }
			});
		
		loginPanel.add(errorMsg);
		loginPanel.add(buttons);
		revalidate();
		repaint();
	}

}