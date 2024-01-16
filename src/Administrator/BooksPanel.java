package Administrator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import BookBoutique.ConnectionDB;
import BookBoutique.Controlleur;
import BookBoutique.Livre;

public class BooksPanel extends JPanel implements ActionListener
{
	private JButton previous = new JButton("Previous"), next= new JButton("Next");
	private int pageNumber = 0, numberOfPages, bookCount;
	private JPanel innerPanel;
	private String searchTerm;
	private AddEditBook popUp;
	
	public BooksPanel(String searchTerm) {
		this.popUp = new AddEditBook();
		
		this.searchTerm = searchTerm;
		
		this.bookCount = searchTerm.isEmpty() ? ConnectionDB.getCount("books") : Controlleur.connection.getBookCount(searchTerm);
		
		this.numberOfPages = (int) (bookCount / 8);
		if (bookCount % 8 != 0) {
			this.numberOfPages++;
		}
		
		this.innerPanel = createInnerPanel(pageNumber);
		
		setLayout(new BorderLayout());
		
		add(createSearchBar(), BorderLayout.NORTH);
		add(innerPanel, BorderLayout.CENTER);
		add(GenrePanel.paginationButtons(next, previous, Color.decode("#ff9518"), Color.decode("#ffc218")), BorderLayout.SOUTH);
		previous.addActionListener(this);
		next.addActionListener(this);
		setVisible(true);
	}
	
	public static JPanel createSearchBar() {
		JPanel searchHolder = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JTextField searchField = new JTextField(80);
		JButton searchButton = new JButton("Search");
		
		searchHolder.add(searchField);
		searchHolder.add(searchButton);
		return searchHolder;
	}
	
	private JPanel createInnerPanel(int start) {
		innerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel bookHolder = new JPanel(new GridLayout(3, 3, 20, 35));
		HashMap<String, Livre> books = searchTerm.isEmpty() ? Controlleur.connection.getBooksFromDB("SELECT * FROM books LIMIT " + start + ", 8;") : Controlleur.connection.test(searchTerm, start);
		
		for (Livre book : books.values()) {
			RoundedPanel BooksPanel = new RoundedPanel(book.picture, 380, 130, new int[] {20, 15, 80, 100});
			BooksPanel.insertLabel(book.title, 110, 10, 270, 25, 15);
			BooksPanel.insertLabel(book.authName, 110, 30, 270, 25, 15);
			BooksPanel.insertLabel("$" + book.price, 110, 50, 90, 25, 15);
			BooksPanel.add(generateButton("Modify", book.title, 110, 85, 75, 30));
			BooksPanel.add(generateButton("Delete", book.title, 195, 85, 75, 30));
			bookHolder.add(BooksPanel);
		}
		bookHolder.add(GenrePanel.addNewPanel(380, 130, new int[] {55, 30, 65, 65}, "Add New Book", 145, 50, 200, 25, 25, () -> popUp.appear("")));
		innerPanel.add(bookHolder);
		return innerPanel;
	}
	
	
	private JButton generateButton(String buttonLabel, String bookTitle, int x, int y, int width, int height) {
		JButton btn = new JButton();
		
		btn.setBounds(x, y, width, height);
		
		btn.setText(buttonLabel);
		if (buttonLabel.equals("Modify"))
			btn.setBackground(Color.decode("#ff9518"));
		else
			btn.setBackground(Color.decode("#fe3745"));
		btn.setFocusable(false);
		btn.setForeground(Color.WHITE);
		btn.setBorder(BorderFactory.createRaisedBevelBorder());

		ActionListener btnActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (buttonLabel.equals("Modify")) {
					popUp.appear(bookTitle);
				}
				else {
					System.out.println("Deleted " + bookTitle);
					
				}
			}
		};
		btn.addActionListener(btnActionListener);
		return btn;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == previous) {
			if (pageNumber > 0) {
				pageNumber--;
				removeAndAdd(pageNumber == 0 ? 1 : pageNumber * 8); 
			}
		}
		else if (e.getSource() == next) {
			if (pageNumber < numberOfPages - 1) {
				pageNumber++;
				removeAndAdd(pageNumber == 0 ? 8 : pageNumber * 8);
			}
		}
	}
	
	private void removeAndAdd(int start) {
		this.remove(innerPanel);
		innerPanel = createInnerPanel(start);
		add(innerPanel, BorderLayout.CENTER);
		revalidate();
		repaint();
	}
}
