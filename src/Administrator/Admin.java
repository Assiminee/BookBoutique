package Administrator;

import java.awt.BorderLayout;
import java.awt.Color;
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
import BookBoutique.ConnectionDB;
import BookBoutique.Controlleur;

public class Admin extends JFrame implements ActionListener
{
	static public JButton users, books, genres, accueil, searchButton;
	public static JPanel rightWrapper;
	GenrePanel genrePanel;
	BooksPanel bookPanel;
	
	
	public Admin() {
		this.genrePanel = new GenrePanel();
		this.bookPanel = new BooksPanel("");
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
		
		genres.addActionListener(this);
		
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
		rightWrapper = new JPanel(new BorderLayout());
		JPanel innerPanel = new JPanel();
		JPanel firstSet = new JPanel(new FlowLayout(FlowLayout.CENTER, 32, 0));
		JPanel secondSet = new JPanel(new FlowLayout(FlowLayout.CENTER, 32, 0));
		JPanel thirdSet = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		RoundedPanel bookPanel = new RoundedPanel("src\\Images\\booksIcon.png", 300, 150, new int[] {20, 40, 80, 80});
		RoundedPanel genresPanel = new RoundedPanel("src\\Images\\genre.png", 300, 150, new int[] {20, 40, 80, 80});
		RoundedPanel usersPanel = new RoundedPanel("src\\Images\\users.png", 300, 150, new int[] {20, 40, 80, 80});
		RoundedPanel commentsPanel = new RoundedPanel("src\\Images\\comments.png", 300, 150, new int[] {20, 40, 80, 80});
		RoundedPanel ordersPanel = new RoundedPanel("src\\Images\\Orders.png", 300, 150, new int[] {20, 40, 80, 80});

		bookPanel.insertLabel("Books", 130, 60, 100, 20, 25);
		bookPanel.insertLabel(Integer.toString(ConnectionDB.getCount("books")), 130, 80, 100, 20, 15);
		
		genresPanel.insertLabel("Genres", 130, 60, 100, 20, 25);
		genresPanel.insertLabel(Integer.toString(ConnectionDB.getCount("genres")), 130, 80, 100, 20, 15);
		
		usersPanel.insertLabel("Users", 130, 60, 100, 20, 25);
		usersPanel.insertLabel(Integer.toString(ConnectionDB.getCount("users")), 130, 80, 100, 20, 15);
		
		commentsPanel.insertLabel("Comments", 130, 60, 170, 20, 25);
		commentsPanel.insertLabel(Integer.toString(ConnectionDB.getCount("comments")), 130, 80, 100, 20, 15);
		
		ordersPanel.insertLabel("Orders", 130, 60, 100, 20, 25);
		ordersPanel.insertLabel(Integer.toString(ConnectionDB.getCount("orders")), 130, 80, 100, 20, 15);
		
		innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));
		
		firstSet.add(bookPanel);
		firstSet.add(genresPanel);
		
		secondSet.add(usersPanel);
		secondSet.add(commentsPanel);
		
		thirdSet.add(ordersPanel);

		innerPanel.add(Box.createVerticalStrut(30));
		innerPanel.add(firstSet);
		innerPanel.add(secondSet);
		innerPanel.add(thirdSet);
		
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
		if (e.getSource() == genres) {
			removeAndAdd(genrePanel);
		}
		if (e.getSource() == books) {
			removeAndAdd(bookPanel);
		}
		if (e.getSource() == accueil) {
			showRegular();
		}
	}
	
	public void removeAndAdd(JPanel addition) {
		rightWrapper.removeAll();
		rightWrapper.add(addition, BorderLayout.CENTER);
		rightWrapper.revalidate();
		rightWrapper.repaint();
	}
	
	public void showRegular() {
		Controlleur.controlleur.setVisible(true);
		this.setVisible(false);
	}
}

