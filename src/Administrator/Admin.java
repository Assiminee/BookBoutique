package Administrator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import BookBoutique.Controlleur;

public class Admin extends JFrame implements ActionListener
{
	static public JButton users, books, genres, searchButton;
	
	
	public Admin() {
		setLayout(new BorderLayout());
		setIconImage(Controlleur.logo.getImage());
		setTitle("BookBoutique");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		add(createWrapperPanel(), BorderLayout.CENTER);
		setVisible(true);
	}

	private JPanel createWrapperPanel() {
		JPanel outerWrapper = new JPanel(new BorderLayout());
		
		outerWrapper.add(leftSidePanel(), BorderLayout.WEST);
		outerWrapper.add(rightSidePanel(), BorderLayout.CENTER);
		return outerWrapper;
	}
	
	private JPanel leftSidePanel() {
		JPanel buttonHolder = new JPanel();
		users = new JButton("Users");
		books = new JButton("Books");
		genres = new JButton("Genres");
		
		buttonHolder.setLayout(new BoxLayout(buttonHolder, BoxLayout.Y_AXIS));
		buttonHolder.add(buttonHelper(users, Controlleur.fixResolution(new ImageIcon("src\\Images\\user.png"), 60, 60)));
		buttonHolder.add(buttonHelper(books, Controlleur.fixResolution(new ImageIcon("src\\Images\\book.png"), 60, 60)));
		buttonHolder.add(buttonHelper(genres, Controlleur.fixResolution(new ImageIcon("src\\Images\\categories.png"), 60, 60)));
		return buttonHolder;
	}
	
	private JPanel rightSidePanel() {
		JPanel rightWrapper = new JPanel(new BorderLayout());
		JPanel innerPanel = new JPanel(new BorderLayout());
		JTextField search = new JTextField(80);
		JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		searchButton = new JButton("search");
		
		searchPanel.add(search);
		searchPanel.add(searchButton);
		
		innerPanel.add(new RoundedPanel("src\\Images\\booksIcon.png", "Test", 18));
		rightWrapper.add(searchPanel, BorderLayout.NORTH);
		rightWrapper.add(Controlleur.scrollPane(innerPanel, this.getWidth(), this.getHeight()), BorderLayout.CENTER);
		return rightWrapper;
	}
	
	private JPanel buttonHelper(JButton btn, ImageIcon icon)
	{
		JPanel btnPanel = new JPanel(new BorderLayout());

		// Setting buttons
		btn.setPreferredSize(new Dimension(80, 80));
		btn.setIcon(icon);
		btn.setBackground(Color.decode("#b14724"));
		btn.setForeground(Color.decode("#ece7cd"));
		btn.setHorizontalTextPosition(JButton.CENTER);
		btn.setVerticalTextPosition(JButton.BOTTOM);
		btn.setFocusable(false);
		btn.setBorder(null);
		btn.addMouseListener(new MouseAdapter() {
			@Override
		    public void mouseEntered(MouseEvent e) {
		        btn.setBackground(Color.decode("#d7562b"));
		    }

		    @Override
		    public void mouseExited(MouseEvent e) {
		        btn.setBackground(Color.decode("#b14724"));
		    }
		});
		btn.addActionListener(this);
		
		btnPanel.add(btn, BorderLayout.CENTER);
		
		return btnPanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == users) {
			
		}
	}
	
	
}

