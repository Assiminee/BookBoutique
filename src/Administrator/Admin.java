package Administrator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import BookBoutique.Controlleur;

public class Admin extends JFrame implements ActionListener
{
	static public JButton users, books, genres, accueil, searchButton;
	
	
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
		
		outerWrapper.add(createHeader(), BorderLayout.NORTH);
		outerWrapper.add(leftSidePanel(), BorderLayout.WEST);
		outerWrapper.add(rightSidePanel(), BorderLayout.CENTER);
		return outerWrapper;
	}
	
	private JPanel leftSidePanel() {
		JPanel buttonHolder = new JPanel();
		users = new JButton("Users");
		books = new JButton("Books");
		genres = new JButton("Genres");
		accueil = new JButton("Accueil");
		
		buttonHolder.setLayout(new BoxLayout(buttonHolder, BoxLayout.Y_AXIS));
		buttonHolder.add(buttonHelper(users, Controlleur.fixResolution(new ImageIcon("src\\Images\\user.png"), 60, 60)));
		buttonHolder.add(buttonHelper(books, Controlleur.fixResolution(new ImageIcon("src\\Images\\book.png"), 60, 60)));
		buttonHolder.add(buttonHelper(genres, Controlleur.fixResolution(new ImageIcon("src\\Images\\categories.png"), 60, 60)));
		buttonHolder.add(buttonHelper(accueil, Controlleur.fixResolution(new ImageIcon("src\\Images\\GoBack.png"), 60, 60)));
		return buttonHolder;
	}
	
	private JPanel createHeader() {
		JPanel headerPanel = new JPanel(new BorderLayout());
		JLabel titre = new JLabel("BookBoutique");
		
		titre.setHorizontalAlignment(JLabel.CENTER);
		titre.setForeground(Color.decode("#ece7cd"));
		titre.setFont(new Font("Forte", Font.BOLD, 45));
		
		headerPanel.setBackground(Color.decode("#b14724"));
		headerPanel.add(Box.createVerticalStrut(10), BorderLayout.NORTH);
		headerPanel.add(titre, BorderLayout.CENTER);
		
		return headerPanel;
	}
	private JPanel rightSidePanel() {
		JPanel rightWrapper = new JPanel(new BorderLayout());
		JPanel innerPanel = new JPanel();
		//JTextField search = new JTextField(80);
		
		//JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel firstSet = new JPanel(new FlowLayout(FlowLayout.CENTER, 32, 0));
		JPanel secondSet = new JPanel(new FlowLayout(FlowLayout.CENTER, 32, 0));
		JPanel thirdSet = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		//searchButton = new JButton("search");
		//searchPanel.add(Box.createVerticalStrut(80));
		//searchPanel.add(search);
		//searchPanel.add(searchButton);
		
		innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));
		
		firstSet.add(new RoundedPanel("src\\Images\\booksIcon.png", "Books", 18));
		firstSet.add(new RoundedPanel("src\\Images\\genre.png", "Genres", 18));
		
		secondSet.add(new RoundedPanel("src\\Images\\users.png", "Users", 18));
		secondSet.add(new RoundedPanel("src\\Images\\comments.png", "Comments", 18));
		
		thirdSet.add(new RoundedPanel("src\\Images\\Orders.png", "Orders", 18));

		innerPanel.add(Box.createVerticalStrut(30));
		innerPanel.add(firstSet);
		innerPanel.add(secondSet);
		innerPanel.add(thirdSet);
		//innerPanel.add(Box.createVerticalStrut(20));
		
		rightWrapper.add(innerPanel, BorderLayout.CENTER);
		
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

