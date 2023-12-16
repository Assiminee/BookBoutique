package BookBoutique;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class FAQ extends JPanel
{
	private JPanel wrapper = new JPanel(new BorderLayout());
	private ArrayList<String> questions = new ArrayList<String>();
	private JTextArea textArea;
	
	public FAQ ()
	{
		setLayout(new FlowLayout(FlowLayout.CENTER));
		
		add(upperPanel());
		add(Controlleur.scrollPane(wrapper, 970, 440));
		
		setVisible(true);
	}
	
	private JPanel upperPanel() {		
		JPanel contentHolder = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel FAQs = new JPanel(new GridLayout(3, 2));
		JPanel questionsOrComments = new JPanel(new BorderLayout());
		textArea = new JTextArea(13, 30);
		JButton send = new JButton("Send");
		
		textReader("src\\FAQs.txt");
		
		for (int i = 0; i < 6; i++) {
			FAQs.add(createButton(questions.get(i)));
		}
		
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setBorder(BorderFactory.createEtchedBorder());
		
		send.setBackground(new Color(0X798751));
		send.setForeground(Color.WHITE);
		send.setFocusable(false);
		onHover(send);
		
		questionsOrComments.add(Controlleur.scrollPane(textArea, 350, 250), BorderLayout.NORTH);
		questionsOrComments.add(send, BorderLayout.PAGE_END);
		
		contentHolder.add(FAQs);
		contentHolder.add(questionsOrComments);
		
		wrapper.add(aboutUsText(), BorderLayout.CENTER);
		wrapper.add(contentHolder, BorderLayout.SOUTH);
		
		
		return wrapper;
	}
	
	private JPanel createButton(String question) {
		JPanel btnHolder = new JPanel();
		JButton btn = new JButton(question);
		
		btn.setPreferredSize(new Dimension(264, 70));
		btn.setBackground(new Color(0X798751));
		btn.setForeground(Color.WHITE);
		btn.setFont(new Font("Georgia", Font.BOLD, 12));
		btn.setFocusable(false);
		btn.setBorder(null);
		hoverFunctionality(btn);
		btnHolder.add(btn);
		return btnHolder;
	}
	
	
	
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
            editorPane.setBackground(new Color(0, 0, 0, 0));
		}
		catch (Exception e) {
			e.getStackTrace();
		}
		return editorPane;
	}
	
	private void textReader(String fileName) {
		BufferedReader reader = null;
		File file = null;
		String line;
		
		try {
			file = new File(fileName);
			reader = new BufferedReader(new FileReader(file));
			
			while ((line = reader.readLine()) != null)
                questions.add(line);
			
			reader.close();
		}
		catch (Exception e) {
			e.getStackTrace();
		}
	}
	
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
	
	private void hoverFunctionality(JButton btn) {
		onHover(btn);
	}
}
