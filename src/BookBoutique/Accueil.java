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
public class Accueil extends JPanel implements ActionListener
{
	// Class Attributes
	public boolean showBooks;
	private JPanel wrapper;
	
	public Accueil(boolean showBooks)
	{
		// Temp Variable for Testing
		this.showBooks = showBooks;
		
		setLayout(new FlowLayout());
		
		add(createJPanels());
		add(Controlleur.scrollPane(wrapper, 1150, 440));

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
	private JPanel createJPanels()
	{
		wrapper = new JPanel();
		wrapper.setLayout(new GridLayout(3, 4, 15, 15));
		wrapper.setPreferredSize(new Dimension(700, 1000));
		HashMap<String, Livre> books = getBooks();
		int i = 0;
		
		for (String key : books.keySet()) {
			articleCreator(books.get(key));
			i++;
			if (i == 12)
				break;
		}

		return wrapper;
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
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (name == "Add to cart") {
					Controlleur.cart.addToCart(book);
				}
				else {
					System.out.println("Find out more about " + book.title);
				}
			}
		});
	}
	
	/**
	 * setContentHolder:
	 * 		sets the 'JLabels' holding the 
	 * @param cHolder
	 * @param book
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


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
