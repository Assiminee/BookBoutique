package BookBoutique;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JLabel;

/**
 * Controlleur - class
 * Controlleur is the foundation of the program. It extends 'JFrame'
 * to create the initial container of all the components. It also
 * implements 'ActionListener' to give the ability to switch pages.
 * The logic is as follows:
 * 		1/ Controlleur creates the header of the entire application
 * 		which includes the logo, title, buttons and search bar.
 * 		2/ Every other class is instantiated within controlleur.
 * 		When one of the buttons is pressed, controlleur removes
 * 		whatever JPanel was placed at the center and places a new
 * 		JPanel instead (accueil, catalog, or FAQ). 
 */
public class Controlleur extends JFrame implements ActionListener
{
	// Class Variables
	static public ImageIcon logo;
	public static JPanel innerPanel;
	public static JButton accueilButton, catalogButton, loginButton, search, aboutUsButton, cartButton;
	JTextField searchBar;
	public HashMap<String, Livre> books;
	public ArrayList<String> genres;
	
	private Accueil accueil;
	private AboutUs aboutUs;
	private Catalogue catalog;
	static public Cart cart;
	static public More more;
	static public Login login;
	
	public Controlleur()
	{
		// Setting class variables
		Controlleur.logo = new ImageIcon("src\\Images\\books.png");
		Controlleur.cart = new Cart();
		Controlleur.more = new More();
		Controlleur.login = new Login();
		this.books = getBooks("src\\books.csv");
		this.genres = getGenres(books);
		this.accueil = new Accueil(books, "");

		this.catalog = new Catalogue(this.genres, this.books);
	
		// Setting the JFrame
		setLayout(new BorderLayout());
		setIconImage(logo.getImage());
		setTitle("BookBoutique");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		
		// Adding Components
		add(header(), BorderLayout.NORTH);
		add(addInnerPanel(), BorderLayout.CENTER);
		
		// Hover Functionality for Buttons
		hoverFunctionality();
		
		// Setting JFrame some more
		setVisible(true);
		this.aboutUs = new AboutUs();
	}
	
	/**
	 * fixResolution: 
	 * 		Resizing and scaling of an image.
	 * 		This method is static since it is meant to be used
	 * 		by all other classes without having to create an
	 * 		instance of controlleur.
	 * @param icon - This is the ImageIcon that is meant
	 * 				 to be resized.
	 * @param width - The desired width of the ImageIcon
	 * @param height - The desired height of the ImageIcon
	 * @return a scaled ImageIcon
	 */
	static public ImageIcon fixResolution(ImageIcon icon, int width, int height)
	{
		Image img = icon.getImage();
	    Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
	    ImageIcon scaledIcon = new ImageIcon(scaledImg);
	    
	    return scaledIcon;
	}
	
	/**
	 * header:
	 * 		Creates a 'JPanel' to hold the logo and title.
	 * @head - the 'JPanel' that holds the logo and title.
	 * @iconHolder - the 'JLabel' that holds the logo.
	 * @titre - the 'JLabel' that holds the title.
	 * 
	 * @return - 'JPanel' containing the logo and title.
	 * This method is called in the constructor of the 
	 * Controlleur class. Since this method returns a
	 * 'JPanel', its return value is added to the NORTH
	 * of the frame: add(header(), BorderLayout.NORTH);
	 * 
	 */
	private JPanel header()
	{
		JPanel head = new JPanel(new BorderLayout());
		JLabel iconHolder = new JLabel();
		JLabel titre = new JLabel("BookBoutique");

		// Title
		titre.setHorizontalAlignment(JLabel.CENTER);
		titre.setForeground(new Color(0X732d21));
		titre.setFont(new Font("Forte", Font.BOLD, 45));
		
		// Logo
		iconHolder.setText("    ");
		iconHolder.setHorizontalTextPosition(JLabel.LEFT);
		iconHolder.setIcon(logo);
		
		head.add(Box.createVerticalStrut(5), BorderLayout.NORTH);
		head.add(iconHolder, BorderLayout.WEST);
		head.add(titre, BorderLayout.CENTER);
		
		return head;
	}
	
	/**
	 * addInnerPanel:
	 * 		Create a 'JPanel' that is placed at the center
	 * 		of the JFrame.
	 * 		This 'JPanel' holds the buttons that allow the user
	 * 		to browse the various pages of the application
	 * 		(accueil, catalogue, login, FAQ, cart). It also holds
	 * 		the central 'JPanel' that holds the search bar as well as
	 * 		the content of accueil, catalogue, and FAQ.
	 * @buttons - a 'JPanel' holding buttons.
	 * @return a 'JPanel'. This method is called in the constructor
	 * and since its return value is a 'JPanel', it is added to the
	 * frame in the following way: 
	 * 		add(addInnerPanel(), BorderLayout.CENTER);
	 */
	private JPanel addInnerPanel()
	{
		JPanel buttons = new JPanel(new BorderLayout());
		innerPanel = new JPanel(new BorderLayout());
		
		buttons.add(buttonHolder(), BorderLayout.NORTH);		
		buttons.add(SearchBar(), BorderLayout.CENTER);
		
		innerPanel.add(buttons, BorderLayout.NORTH);
		innerPanel.add(accueil, BorderLayout.CENTER);
		
		return innerPanel;
	}
	
	/**
	 * searchBar:
	 * 		Creates a 'JPanel' that holds a 'JTextField'
	 * 		and a 'JButton'.
	 * @searchPanel - 'JPanel'
	 * @search - 'JButton'
	 * @return - a 'JPanel' holding a 'JTextField' and a
	 * 'JButton'. This method is called in the addInnerPanel()
	 * method since the 'JPanel' returned by this mathod is
	 * meant to hold these components + the content JPanel:
	 * buttons.add(SearchBar(), BorderLayout.CENTER);
	 */
	private JPanel SearchBar()
	{
		JPanel searchPanel = new JPanel(new FlowLayout());
		search = new JButton("  Search  ");
		searchBar = new JTextField(60);
		
		search.setBackground(new Color(0X732d21));
		search.setForeground(Color.white);
		search.setFocusable(false);
		search.addActionListener(this);
		search.setBorder(BorderFactory.createRaisedBevelBorder());
		anotherOnHover(search);
		
		searchPanel.add(searchBar);
		searchPanel.add(search);
		
		return searchPanel;
	}
	
	/**
	 * buttonHolder:
	 * 		Creates the 'JPanel' that holds the JButtons
	 * 		used to browse the application.
	 * @ImageIcons - each ImageIcon is created to be added
	 * 		to the appropriate button.
	 * @JButtons - the JButtons created here a declared 
	 * 		global to the class so that they could be called
	 * 		in the actionPerformed method.
	 * 		Each button is being passed to the buttonHelper() method
	 * 		which sets its background color, foreground color, border,
	 * 		icon. It then adds an actionListener to it and places it in
	 * 		a 'JPanel'. The 'JPanel' (containing the button) that is 
	 * 		returned by this method is then added to the 'JPanel' buttonHolder.
	 * @buttonHolder - a 'JPanel' holding all 5 'JButtons'
	 * @return - a 'JPanel' holding all the buttons. This method is called 
	 * in the addInnerPanel() method before adding the search bar:
	 * 		innerPanel.add(buttons, BorderLayout.NORTH); 
	 */
	private JPanel buttonHolder()
	{	
		JPanel buttonHolder = new JPanel(new GridLayout(1, 5));
		
		// Button Icons
		ImageIcon homeIcon = fixResolution(new ImageIcon("src\\Images\\home.png"), 50, 50);
		ImageIcon catalogIcon = fixResolution(new ImageIcon("src\\Images\\catalog.png"), 50, 50);
		ImageIcon loginIcon = fixResolution(new ImageIcon("src\\Images\\login.png"), 70, 70);
		ImageIcon aboutUsIcon = fixResolution(new ImageIcon("src\\Images\\aboutUs.png"), 60, 60);
		ImageIcon cartIcon = fixResolution(new ImageIcon("src\\Images\\cart.png"), 50, 50);
		
		// Buttons
		accueilButton = new JButton();
		catalogButton = new JButton();
		loginButton = new JButton();
		aboutUsButton = new JButton();
		cartButton = new JButton();
		
		// Adding Buttons
		buttonHolder.add(buttonHelper(accueilButton, homeIcon));
		buttonHolder.add(buttonHelper(catalogButton, catalogIcon));
		buttonHolder.add(buttonHelper(loginButton, loginIcon));
		buttonHolder.add(buttonHelper(aboutUsButton, aboutUsIcon));
		buttonHolder.add(buttonHelper(cartButton, cartIcon));
		
		return buttonHolder;
	}
	
	/**
	 * buttonHelper:
	 * 		Sets the style (icon, background, foreground, border)
	 * 		of the 'JButtons' passed to it. Adds an actionListener.
	 * @param btn - the 'JButton' to set.
	 * @param icon - the 'ImageIcon' to add to the 'JButton'
	 * @return - a 'JPanel'. The 'JButton' passed to this
	 * method is placed inside of a 'JPanel'. This 'JPanel'
	 * is then returned and added to the buttonHolder in the
	 * method 'buttonHolder()'.
	 */
	private JPanel buttonHelper(JButton btn, ImageIcon icon)
	{
		JPanel btnPanel = new JPanel(new BorderLayout());

		// Setting buttons
		btn.setIcon(icon);
		btn.setBackground(new Color(0Xc6c2ac));
		btn.setForeground(Color.white);
		btn.setFocusable(false);
		btn.setBorder(null);
		btn.addActionListener(this);
		
		btnPanel.add(btn, BorderLayout.CENTER);
		
		return btnPanel;
	}
	
	/**
	 * removeAndAdd:
	 * 		Removes whatever component is placed at the center
	 * 		of the JFrame and places a new component in its
	 * 		place.
	 * @param newComp - the new component to add.
	 * This method is called in the actionPerformed method.
	 * On the click of a button, the old component is removed
	 * and another one is placed.
	 */
	public static void removeAndAdd(Component newComp)
	{
		try {
			Component panelToRemove = innerPanel.getComponent(1);
			innerPanel.remove(panelToRemove);
		}
		catch (Exception e) {
			System.out.print(e.getMessage());
		}
		finally {
			innerPanel.add(newComp, BorderLayout.CENTER);
			innerPanel.revalidate();
			innerPanel.repaint();
		}
	}
	
	/**
	 * actionPerformed:
	 * 		Defines the behavior of the buttons.
	 */
	@Override
    public void actionPerformed(ActionEvent action)
	{
        
		if (action.getSource() == accueilButton) {
        	removeAndAdd(accueil);
        }
        else if (action.getSource() == catalogButton) {
        	removeAndAdd(catalog);
        }
        else if (action.getSource() == loginButton) {
        	login.displayLogin();
        }
        else if (action.getSource() == search) {
        	//searchFunctionality();
        	String searchTerm = searchBar.getText();
        	HashMap<String, Livre> result = search(books, searchTerm);
        	Accueil newPage = new Accueil(result, "");
        	removeAndAdd(newPage);
        }
        else if (action.getSource() == aboutUsButton) {
        	removeAndAdd(aboutUs);
        }
        else if (action.getSource() == cartButton) {
        	cart.displayCart();
        }
    }
	
	/**
	 * onHover:
	 * 		Defines the behavior of the JButtons
	 * 		if they are hovered over.
	 * @param btn - a 'JButton'.
	 * @param name - a 'String to be displayed on
	 * 		the 'JButton' when it is hovered over.
	 */
	public static void onHover(JButton btn, String name) {
		btn.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseEntered(MouseEvent e) {
		    	btn.setText(name);
		    	btn.setForeground(new Color(0X732d21));
		    	btn.setFont(new Font("Forte", Font.BOLD, 20));
		    	btn.setHorizontalTextPosition(JButton.RIGHT);
		    	btn.setVerticalTextPosition(JButton.CENTER);
		    	btn.setIconTextGap(10);
		        btn.setBackground(new Color(0xece7cd));
		    }

		    @Override
		    public void mouseExited(MouseEvent e) {
		    	btn.setText(null);
		        btn.setBackground(new Color(0Xc6c2ac));
		    }
		});
	}
	
	public static void anotherOnHover(JButton btn) {
		btn.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseEntered(MouseEvent e) {
		        btn.setBackground(new Color(0x993b2b));
		    }

		    @Override
		    public void mouseExited(MouseEvent e) {
		        btn.setBackground(new Color(0x732d21));
		    }
		});
	}
	
	/**
	 * hoverFunctionality:
	 * 		adds the hover functionality for
	 * 		the 5 'JButtons' at the top of the
	 * 		JFrame. This method is called in the
	 * 		constructor.
	 */
	private void hoverFunctionality() {
		onHover(accueilButton, "Home");
		onHover(catalogButton, "Catalog");
		onHover(loginButton, "Login");
		onHover(aboutUsButton, "About Us");
		onHover(cartButton, "Cart");
	}
	
	/**
	 * scrollPane:
	 * 		adds a 'JScrollPane' to whatever component
	 * 		it is called on. This method is static since
	 * 		it is meant to be used by all classes that
	 * 		require it within the package.
	 * @param cmp - the 'Component' to add the 'JScrollPane' to
	 * @param width - the desired width of the 'JScrollPane'
	 * @param height - the desired height of the 'JScrollPane'
	 * @return - a 'JScrollPane'
	 */
	static public JScrollPane scrollPane(Component cmp, int width, int height)
	{
		JScrollPane scrollPane = new JScrollPane(cmp);
		
		if (width != 0) {
			scrollPane.setPreferredSize(new Dimension(width, height));
		}
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return scrollPane;
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
	private HashMap<String, Livre> getBooks(String fileName) {
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
	
	private ArrayList<String> getGenres(HashMap<String, Livre> books) {
		ArrayList<String> genres = new ArrayList<String>();
		
		for (Livre book : books.values()) {
			for (String genre : book.genre) {
				if (!genres.contains(genre)) {
					genres.add(genre);
				}
			}
		}
		return genres;
	}
	
	private HashMap<String, Livre> search(HashMap<String, Livre> books, String searchTerm) {
		HashMap<String, Livre> newBooks = new HashMap<String, Livre>();
		boolean added;
		
		for (Livre book : books.values()) {
			added = false;
			for (String bookGenre : book.genre) {
				if (bookGenre.toLowerCase().contains(searchTerm.toLowerCase())) {
					newBooks.put(book.title, book);
					added = true;
					break;
				}
			}
			if ((book.authName).toLowerCase().contains(searchTerm.toLowerCase()) && !added) {
				newBooks.put(book.title, book); 
				added = true;
			}
			if ((book.title).toLowerCase().contains(searchTerm.toLowerCase()) && !added) {
				newBooks.put(book.title, book); 
			}
		}
		return newBooks;
	}
	
	static public HashMap<String, Livre> genreBasedSearch(HashMap<String, Livre> books, String input) {
		HashMap<String, Livre> newBooks = new HashMap<String, Livre>();
		
		for (Livre book : books.values()) {
			for (String bookGenre : book.genre) {
				if (bookGenre.equalsIgnoreCase(input)) {
					newBooks.put(book.title, book);
					break;
				}
			}
		}
		return newBooks;
	}
}