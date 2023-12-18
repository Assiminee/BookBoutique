package BookBoutique;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
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
	public boolean showBooks;
	private JPanel wrapper;
	private HashMap<String, Livre> books = getBooks();
	private String[] bookKeys = books.keySet().toArray(new String[0]);
	
	public Accueil(boolean showBooks)
	{
		// Temp Variable for Testing
		this.showBooks = showBooks;
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		createJPanels();
		browsingButtons();
		add(Controlleur.scrollPane(wrapper, 1200, 450));
		setVisible(true);
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
		int numRows = (int)Math.floorDiv(books.size(), 4);
		wrapper = new JPanel();
		if (numRows % 4 != 0)
			numRows++;
		wrapper.setLayout(new GridLayout(3, 4, 15, 15));
		//wrapper.setPreferredSize(new Dimension(700, 1000));
		
		for (; i < 12; i++) {
			if (i >= books.size())
				break;
			Livre currBook = books.get(bookKeys[i]);
			articleCreator(currBook);
		}
	}
	
	
	private void editDisplay(int btnNum) {
		int k = 0;
		int j = btnNum * 12;
		for (int i = j - 12; i < j; i++) {
			if (i >= bookKeys.length)
				break;
			Livre currBook = books.get(bookKeys[i]);
			final Livre buttonBook = currBook;
			JPanel article = (JPanel) wrapper.getComponent(k);
			
			JLabel contentHolder = (JLabel) article.getComponent(0);
			JPanel buttonHolder = (JPanel) article.getComponent(1);
			
			JButton addToCart = (JButton) buttonHolder.getComponent(0);
			JButton more = (JButton) buttonHolder.getComponent(1);
			
			setContentHolder(contentHolder, currBook);
			setButtons(addToCart, buttonBook, "Add to cart");
			setButtons(more, buttonBook, "more");
			k++;
		}
	}
	
	private void browsingButtons() {
		JPanel buttons = new JPanel();
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
		for (int j = 0; j < (int)Math.floorDiv(books.size(), 12) + 1; j++) {
			final int buttonIndex = j;
			JButton btn = new JButton(Integer.toString(j + 1));
			btn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					editDisplay(buttonIndex + 1);
				}
			});
			buttons.add(btn);
		}
		add(buttons);
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
		JPanel article = new JPanel(new BorderLayout());
		JPanel buttonHolder = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JLabel contentHolder = new JLabel();
		JButton addToCart = new JButton("Add to Cart");
		JButton more = new JButton("More");
		
		article.setPreferredSize(new Dimension(100, 300));
		article.setBorder(BorderFactory.createLineBorder(Color.black));
				
		setButtons(addToCart, book, "Add to cart");
		setButtons(more, book, "more");
		
		if (this.showBooks)
		{
			setContentHolder(contentHolder, book);	
			
			buttonHolder.add(addToCart);
			buttonHolder.add(more);
			//buttonHolder.setBackground(new Color(0xc6c2ac));
			
			article.add(contentHolder, BorderLayout.CENTER);
			article.add(buttonHolder, BorderLayout.SOUTH);
		}
		
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
		btn.setBackground(new Color(0x732d21));
		btn.setFocusable(false);
		btn.setForeground(Color.WHITE);
		ActionListener[] actionsToRemove = btn.getActionListeners();
		for (ActionListener action : actionsToRemove) {
			btn.removeActionListener(action);
		}
		ActionListener btnActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (name.equals("Add to cart")) {
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
		ImageIcon bookIcon = new ImageIcon(book.picture);
		cHolder.setText("<html><p>" + book.title + "</p><br><p>Price: " + book.price * 10 + " MAD</p></html>");
		cHolder.setIcon(Controlleur.fixResolution(bookIcon, 140, 180));
		cHolder.setIconTextGap(10);
		cHolder.setVerticalTextPosition(JLabel.BOTTOM);
		cHolder.setHorizontalTextPosition(JLabel.CENTER);
		cHolder.setHorizontalAlignment(JLabel.CENTER);
	}
	
	/**
	 * getBooks:
	 * 		Populated the books 'HashMap' with the book
	 * 		data. It does so by reading a CSV file and
	 * 		inserting the data from the latter into the
	 * 		HashMap
	 * @return - the 'HashMap' filled with all the book
	 * data in the CSV file.
	 */
	public HashMap<String, Livre> getBooks() {
		String fileName = "src\\books.csv";
        BufferedReader reader = null;
        String line = "";
        HashMap<String, Livre> data = new HashMap<String, Livre>();

        try {
            reader = new BufferedReader(new FileReader(fileName));
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] row = line.split(";");
                Livre currBook = new Livre(row);
                data.put(currBook.title, currBook);
            }
        }
        catch (Exception e) {
        	System.out.println("Failed");
            e.printStackTrace();
        }
        finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return data;
	}
}
