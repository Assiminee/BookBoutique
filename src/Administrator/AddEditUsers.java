package Administrator;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import BookBoutique.Controlleur;
import BookBoutique.User;

public class AddEditUsers extends JFrame {
	
	public JPanel wrapper;
	private static AddEditUsers instance;
	private String username;
	private JTextField firstName, LastName, email, password,age;
	private JButton sexe;
	//private JTextArea synopsis;
	private boolean added = false;
	private ArrayList<String> users;

	
	
	public AddEditUsers() {
	instance = this;
	this.wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
	setIconImage(Controlleur.logo.getImage());
	setTitle("BookBoutique");
	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	setResizable(false);
	setLayout(new FlowLayout(FlowLayout.CENTER));
	add(wrapper);
	}
	
	public void appear(String username) {
		this.username = username;
		this.users = username.isEmpty() ? null : Controlleur.connection.getSingleUsers("SELECT * FROM users WHERE username LIKE \"%" + username + "%\";");
		if (added)
			wrapper.removeAll();
		wrapper.add(fieldsPanel());
		revalidate();
		repaint();
		toFront();
		setVisible(true);
		pack();
		setLocationRelativeTo(null);
	}
	
	
	
	private RoundedPanel fieldsPanel() {
		RoundedPanel fullPanel = new RoundedPanel("", 500, 500, new int[] {20, 20, 100, 140});
		
		fullPanel.insertLabel("Frt Name: ", 55, 40, 90, 30, 20);
		fullPanel.add(firstName = generateRow(users != null ? users.get(2) : "", 220, 40, 240, 30));
		
		fullPanel.insertLabel("Lst Name: ", 55, 110, 90, 30, 20);
		fullPanel.add(LastName = generateRow(users != null ? users.get(3) : "", 220, 110, 240, 30));
		
		fullPanel.insertLabel("Mail..: ", 55, 150, 90, 30, 20);
		fullPanel.add(email = generateRow(users != null ? users.get(5) : "", 220, 150, 240, 30));
		
		fullPanel.insertLabel("Password: ", 55, 190, 90, 30, 20);
		fullPanel.add(password = generateRow(users != null ? users.get(1) : "", 220, 190, 240, 30));
		
		fullPanel.insertLabel("Sexe: ", 55, 230, 50, 30, 20);
		JRadioButton maleRadioButton = new JRadioButton("M");
        JRadioButton femaleRadioButton = new JRadioButton("F");

       
        ButtonGroup group = new ButtonGroup();
        group.add(maleRadioButton);
        group.add(femaleRadioButton);
        
        fullPanel.add(femaleRadioButton);
        fullPanel.add(maleRadioButton);
        
		
		//fullPanel.insertLabel("Age: ", 20, 250, 120, 20, 20);
		//fullPanel.add(age = generateRow(users != null ? users.get(6) :"", 220, 260, 240, 30));
		
		//fullPanel.add(generateButtons("Upload Image", 105, 460, 140, 35, () -> uploadImage(fullPanel)));
		fullPanel.add(generateButtons("Add", 180, 430, 140, 35, () -> System.out.println("Test")));
		added = true;
		return fullPanel;
	}
	
	private JButton generateButtons(String title, int x, int y, int width, int height, Runnable codeToRun) {
		JButton btn = new JButton(title);
		
		btn.setBounds(x, y, width, height);
		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				codeToRun.run();
			}
		});
		return btn;
	}
	
	private JTextArea generateTextArea(String areaText, int x, int y, int width, int height) {
		JTextArea textArea = new JTextArea(width, height);
		
		textArea.setBounds(x, y, width, height);
		textArea.setText(areaText);
		textArea.setBorder(BorderFactory.createEtchedBorder());
		return textArea;
	}
	
	private JTextField generateRow(String fieldText, int x, int y, int width, int height) {
		JTextField textField = new JTextField(width);
		textField.setBounds(x, y, width, height);
		textField.setText(fieldText);
		textField.setBorder(BorderFactory.createEtchedBorder());
		
		return textField;
	}
	
	private static JButton createAndShowGUI() {
        
        JButton btn = new JButton();
        btn.setLayout(new GridLayout(0, 2));

        JRadioButton maleRadioButton = new JRadioButton("M");
        JRadioButton femaleRadioButton = new JRadioButton("F");

       
        ButtonGroup group = new ButtonGroup();
        group.add(maleRadioButton);
        group.add(femaleRadioButton);

        JButton submitButton = new JButton("Soumettre");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (maleRadioButton.isSelected()) {
                   
                } else if (femaleRadioButton.isSelected()) {
                   
                } else {
                    
                }
            }
        });
        
      btn.add(femaleRadioButton);
      btn.add(maleRadioButton);
      
      return btn;
	
	}
	
	public static void removePbel(JPanel main) {
		main.removeAll();
		
		
	}
}
