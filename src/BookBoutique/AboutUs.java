package BookBoutique;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
/**
 * FAQ - class
 * Displays information about BookBoutique and has
 * a Frequently Asked Questions Section
 */
public class AboutUs extends JPanel
{
	private JPanel wrapper = new JPanel(new BorderLayout());
	private JTextArea textArea;
	private JButton send;
	
	public AboutUs ()
	{
		setLayout(new FlowLayout(FlowLayout.CENTER));
		
		add(fullPanel());
		int width = Controlleur.innerPanel.getComponent(1).getWidth();
		int height = Controlleur.innerPanel.getComponent(1).getHeight();
		add(Controlleur.scrollPane(wrapper, width, height));
		setVisible(true);
	}
	
	/**
	 * aboutUsText:
	 * 		Uses a 'BufferedReader' to read a text file.
	 * 		Inserts the text into a 'StringBuilder'. The
	 * 		text in the 'StringBuilder' is then passed to
	 * 		a 'JEditorPane'. This is because the text is
	 * 		in HTML. 'JEditorPane' provides support for
	 * 		HTML text and formatting.
	 * @return - the 'JEditorPane' containing the formatted
	 * HTML text.
	 */
	private JEditorPane aboutUsText() {
		JEditorPane editorPane = new JEditorPane();
		String fileName = "src\\aboutUsText.txt";
		BufferedReader reader = null;
		StringBuilder sb = null;
		File file = null;
		String line;
		
		try {
			
			file = new File(fileName);
			reader = new BufferedReader(new FileReader(file));
			sb = new StringBuilder();
			
			while ((line = reader.readLine()) != null)
                sb.append(line);
			
			reader.close();
			
			editorPane.setContentType("text/html");
            editorPane.setEditable(false);
            editorPane.setText(sb.toString());   
            editorPane.setBackground(getBackground());
		}
		catch (Exception e) {
			e.getStackTrace();
		}
		return editorPane;
	}
	
	/**
	 * fullPanel:
	 * 		Creates a 'JPanel' to hold the following
	 * 		components: 
	 * 		- a 'JPanel' with 5 'JButtons',
	 * 		each containing some text(an FAQ).
	 * 		- a 'JPanel' containing a 'JTextArea'
	 * 		to type either a question or a comment 
	 * 		and a button to send(save) the comment/question.
	 * Before these components are added, the 'JEditorPane'
	 * containing the About Us text is added to the wrapper
	 * 'JPanel' using the aboutUsText() method.
	 * @return - the wrapper 'JPanel' with the added components
	 * This method is called in the constructor: 
	 */
	private JPanel fullPanel() {		
		JPanel contentHolder = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel questionsOrComments = new JPanel(new BorderLayout());
		JPanel emailHolder = new JPanel(new BorderLayout());
		JPanel usernameHolder = new JPanel(new BorderLayout());
		JPanel commentHolder = new JPanel(new BorderLayout());
		JPanel fieldHolder = new JPanel(new BorderLayout());
		
		JLabel emailLabel = new JLabel("Email: ");
		JLabel usernameLabel = new JLabel("Username: ");
		JLabel cqLabel = new JLabel("Comment/Question: ");
		
		JTextField email = new JTextField(10);
		JTextField username = new JTextField(10);
		textArea = new JTextArea(13, 30);
		send = new JButton("Send");
				
		emailHolder.add(emailLabel, BorderLayout.NORTH);
		emailHolder.add(email, BorderLayout.CENTER);
		
		usernameHolder.add(usernameLabel, BorderLayout.NORTH);
		usernameHolder.add(username, BorderLayout.CENTER);
		
		commentHolder.add(cqLabel, BorderLayout.NORTH);
		commentHolder.add(textArea, BorderLayout.CENTER);
		
		fieldHolder.add(emailHolder, BorderLayout.NORTH);
		fieldHolder.add(usernameHolder, BorderLayout.CENTER);
		
		setTextArea();
		setSendButton(email, username);
		
		questionsOrComments.add(fieldHolder, BorderLayout.NORTH);
		questionsOrComments.add(Controlleur.scrollPane(commentHolder, 350, 250), BorderLayout.CENTER);
		questionsOrComments.add(send, BorderLayout.SOUTH);
		
		contentHolder.add(questionsOrComments);

		wrapper.add(aboutUsText(), BorderLayout.CENTER);
		wrapper.add(contentHolder, BorderLayout.SOUTH);
		
		
		return wrapper;
	}
	
	private void setTextArea() {
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setBorder(BorderFactory.createEtchedBorder());
	}
	
	private void setSendButton(JTextField email, JTextField username) {
		send.setBackground(new Color(0X798751));
		send.setForeground(Color.WHITE);
		send.setFocusable(false);
		onHover(send);
		
		send.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!email.getText().isBlank() &&
					!username.getText().isBlank() &&
					!textArea.getText().isBlank()
				) {
					saveUserComment(email.getText(), username.getText(), textArea.getText());
					sent(email);
					sent(username);
				}
				else {
					blankArea(email);
					blankArea(username);
				}
			}
		});
	}
	
	private void blankArea(JTextField textField) {
		Timer timer = new Timer();
		
		if (textField.getText().isBlank()) {
			textField.setBackground(Color.decode("#ffe5e6"));
			timer.schedule(new TimerTask() {
	            @Override
	            public void run() {
	            	textField.setBackground(Color.WHITE);
	            	
	            }
	        }, 2000);
		}
		if (textArea.getText().isBlank()) {
			textArea.setBackground(Color.decode("#ffe5e6"));
			timer.schedule(new TimerTask() {
	            @Override
	            public void run() {
	            	textArea.setBackground(Color.WHITE);
	            }
	        }, 2000);
		}
        
	}
	
	private void sent(JTextField field) {
		Timer timer = new Timer();

		field.setText("");
		field.setBackground(Color.decode("#d7ffdd"));
		timer.schedule(new TimerTask() {
            @Override
            public void run() {
            	field.setBackground(Color.WHITE);
            }
        }, 2000);
		
		textArea.setText("");
		textArea.setBackground(Color.decode("#d7ffdd"));
		timer.schedule(new TimerTask() {
            @Override
            public void run() {
            	textArea.setBackground(Color.WHITE);
            }
        }, 2000);
	}
	
	private void saveUserComment(String email, String userName, String comment) {
        String filePath = "src\\Comments\\" + userName + ".txt";
        Path file = Paths.get(filePath);

        if (!Files.exists(file)) {
	        // Use try-with-resources to automatically close the resources
	        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
	            writer.write("Email: " + email + "\nUsername: " + userName + "\n");
	            System.out.println("Comment has been saved.");
	        } catch (IOException e) {
	            System.out.println("An error occurred while writing to the file: " + e.getMessage());
	        }
        }
    	try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
        	LocalDateTime currentDateTime = LocalDateTime.now();
            writer.write(currentDateTime.toString() + "\n" + comment + "\n");
            System.out.println("Comment has been saved.");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file: " + e.getMessage());
        }
	}
	
	
	/**
	 * onHover:
	 * 		Defines the behavior of the 'JButtons'
	 * 		when hovered over.
	 * @param btn - the 'JButton' to define the
	 * 		hover functionality for.
	 */
	public void onHover(JButton btn) {
		btn.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseEntered(MouseEvent e) {
		        btn.setBackground(new Color(0x9bad67));
		    }

		    @Override
		    public void mouseExited(MouseEvent e) {
		        btn.setBackground(new Color(0X798751));
		    }
		});
	}
}