package BookBoutique;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Accueil : class
 * Accueil is the first page that is displayed when
 * the application is launched. This class displays
 * the books.
 * Accueil is a JPanel that holds all of the components
 * placed at the center of the JFrame.
 */
public class Accueil extends JPanel
{
	// Class Attributes
	private JPanel wrapper;
	private HashMap<String, Livre> books;
	private String[] bookKeys;
	
	public Accueil(HashMap<String, Livre> books, String name)
	{	
		JPanel test = new JPanel(new BorderLayout());
		this.books = books;
		this.bookKeys = books.keySet().toArray(new String[0]);
		setLayout(new BorderLayout());
		createJPanels();
		
		if (!name.equals("")) {
			test.add(createGenreTitle(name), BorderLayout.NORTH);
		}
		test.add(browsingButtons(), BorderLayout.CENTER);
		add(test, BorderLayout.NORTH);
		
		add(Controlleur.scrollPane(wrapper, getWidth(), getHeight()), BorderLayout.CENTER);
		setVisible(true);
	}
	
	public JPanel createGenreTitle(String name) {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JLabel label = new JLabel("  You are now browsing the \"" + name + "\" category  ");
		

		label.setFont(new Font("Georgia", Font.BOLD, 20));
		label.setBorder(BorderFactory.createRaisedBevelBorder());
		panel.add(label);
		add(panel);
		
		return panel;
	}
	
	/**
	 * createJPanels:
	 * 		Accueil is a 'JPanel' that holds the 'JPanel'
	 * 		wrapper.
	 * @wrapper - wrapper wraps around all the central
	 * 		components. The method createJPanels() creates
	 * 		individual 'JPanels' that are placed in wrapper.
	 * @books - a 'HashMap' that contains all the book data
	 * 		to be displayed in the 'JPanels' within wrapper.
	 * @return 'JPanel' returns wrapper after its been
	 * populated with the 'JPanels' holding the content. 
	 */
	private void createJPanels()
	{
		int i = 0;
		int numRows = books.size() > 12 ? 3 : 2;
		//numRows = (books.size() % 4 == 0) ? numRows : (numRows + 1);
		wrapper = new JPanel();
		wrapper.setLayout(new GridLayout(numRows, 4, 15, 15));
		
		for (; i < numRows * 4; i++) {
			Livre currBook;
			try {
				currBook = books.get(bookKeys[i]);
			}
			catch (Exception e) {
				currBook = null;
			}

			articleCreator(currBook);
		}
	}
	
	/**
	 * articleCreator:
	 * 		Creates individual 'JPanels' to place inside
	 * 		of the wrapper. This method is called in a 
	 * 		loop inside the createJPanels() method.
	 * @articles - the 'JPanel' holding the book content
	 * 		and the 'add to cart'/'more' 'JButtons'.
	 * @buttonHolder - the 'JPanel' holding the 'JButtons'
	 * 		'add to cart' and 'more'.
	 * @contentHolder - the 'JLabel' holding the book image,
	 * 		title and price.
	 * @addToCart and @more - 'JButtons'
	 */
	private void articleCreator(Livre book)
	{
		JPanel article = new JPanel();
		JPanel imageHolder = new JPanel(new FlowLayout(FlowLayout.CENTER));

		DisplayImage image = new DisplayImage("src\\bookCovers\\NoImage.jpeg", 0, 0, 140, 180);
		
		JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JLabel infoLabel = new JLabel();
		
		JPanel buttonHolder = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JButton addToCart = new JButton();
		JButton more = new JButton();
		
		article.setLayout(new BoxLayout(article, BoxLayout.Y_AXIS));
		article.setPreferredSize(new Dimension(100, 300));
		
		imageHolder.add(image);
			
		if (book != null) {
			article.setBorder(BorderFactory.createLineBorder(Color.black));
			((DisplayImage) imageHolder.getComponent(0)).setImage(book.picture, 0, 0, 140, 180);
			setButtons(addToCart, book, "  Add to cart  ");
			setButtons(more, book, "  More  ");
			setContentHolder(infoLabel, book);
			buttonHolder.add(addToCart);
			buttonHolder.add(more);
		}
		
		infoPanel.add(infoLabel);
		article.add(imageHolder);
		article.add(infoPanel);
		article.add(buttonHolder);
		wrapper.add(article);
	}
	
	/**
	 * setButtons:
	 * 		a helper method that sets the style of the 'JButton'
	 * 		passed to it and adds an actionListener to it.
	 * @param btn - the 'JButton' to be set.
	 * @param book - an entry in the books 'HashMap'. The data
	 * 		from this entry is used to associate each button with
	 * 		a specific book data.
	 * @param name - the name of the 'JButton'. It's either 'Add to cart'
	 * or 'more'. The name is used in order to specify which button has
	 * been clicked in order to define the appropriate behavior for it.
	 */
	private void setButtons(JButton btn, Livre book, String name)
	{
		btn.setText(name);
		btn.setBackground(new Color(0x732d21));
		btn.setFocusable(false);
		btn.setForeground(Color.WHITE);
		btn.setBorder(BorderFactory.createRaisedBevelBorder());
		Controlleur.anotherOnHover(btn);
		ActionListener[] actionsToRemove = btn.getActionListeners();
		for (ActionListener action : actionsToRemove) {
			btn.removeActionListener(action);
		}
		ActionListener btnActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (name.equals("  Add to cart  ")) {
					Controlleur.cart.addToCart(book);
				}
				else {
					Controlleur.more.openMore(book);
				}
			}
		};
		btn.addActionListener(btnActionListener);
	}
	
	/**
	 * setContentHolder:
	 * 		sets the 'JLabels' holding the book content 
	 * @param cHolder - the 'JLabel' to hold the book data
	 * @param book - an entry from the books 'HashMap'. The
	 * data from this entry will be inserted into cHolder.
	 */
	private void setContentHolder(JLabel cHolder, Livre book)
	{
		cHolder.setText("<html><p>" + book.title + "</p><br><p>Price: " + book.price * 10 + " MAD</p></html>");
		cHolder.setVerticalTextPosition(JLabel.BOTTOM);
		cHolder.setHorizontalTextPosition(JLabel.CENTER);
		cHolder.setHorizontalAlignment(JLabel.CENTER);
	}
	
	private void editDisplay(int btnNum) {
		int k = 0, j = btnNum * 12, i;
		
		for (i = j - 12; i < j; i++) {
			JPanel article = (JPanel) wrapper.getComponent(k);
			
			JPanel imageHolder = (JPanel) article.getComponent(0);
			DisplayImage image = (DisplayImage) imageHolder.getComponent(0);
			
			JPanel infoPanel = (JPanel) article.getComponent(1);
			JLabel infoLabel = (JLabel) infoPanel.getComponent(0);
			
			JPanel buttonHolder = (JPanel) article.getComponent(2);
			JButton addToCart = (JButton) buttonHolder.getComponent(0);
			JButton more = (JButton) buttonHolder.getComponent(1);
			
			try {
				Livre currBook = books.get(bookKeys[i]);
				image.setImage(currBook.picture, 70, 10, 140, 180);
				setContentHolder(infoLabel, currBook);
				setButtons(addToCart, currBook, "  Add to cart  ");
				setButtons(more, currBook, "  more  ");
				if (!article.isVisible())
					article.setVisible(true);
			}
			catch (Exception e) {
				article.setVisible(false);
			}
	        revalidate();
	        repaint();
			k++;
		}
	}
	
	private JPanel browsingButtons() {
		JPanel buttons = new JPanel();
		int numPages = (int)Math.floorDiv(books.size(), 12) + 1;
		
		buttons.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
		for (int j = 0; j < numPages; j++) {
			final int buttonIndex = j;
			JButton btn = new JButton("  " + Integer.toString(j + 1) + "  ");
			
			btn.setBackground(new Color(0x732d21));
			btn.setFocusable(false);
			btn.setForeground(Color.WHITE);
			btn.setBorder(BorderFactory.createRaisedBevelBorder());
			Controlleur.anotherOnHover(btn);
			
			if (numPages > 1) {
				btn.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						editDisplay(buttonIndex + 1);
					}
				});
			}
			buttons.add(btn);
		}
		return buttons;
	}
}
