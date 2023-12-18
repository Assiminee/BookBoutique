package BookBoutique;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
	static public ImageIcon logo = new ImageIcon("src\\Images\\books.png");
	private JPanel innerPanel = new JPanel(new BorderLayout());
	public static JButton accueil, catalogue, login, search, FAQ, aboutUs;
	private Accueil firstPage = new Accueil(true);
	private Accueil firstPage2 = new Accueil(false);
	private FAQ faqPage = new FAQ();
	static public Cart cart = new Cart();
	static public More more = new More();
	static public Login lgn = new Login();
	
	public Controlleur()
	{
		// Setting the JFrame
		setLayout(new BorderLayout());
		setIconImage(logo.getImage());
		setTitle("BookBoutique");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Adding Components
		add(header(), BorderLayout.NORTH);
		add(addInnerPanel(), BorderLayout.CENTER);
		
		// Hover Functionality for Buttons
		hoverFunctionality();
		
		// Setting JFrame some more
		setVisible(true);	
		pack();
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
		
		buttons.add(buttonHolder(), BorderLayout.NORTH);		
		buttons.add(SearchBar(), BorderLayout.CENTER);
		
		innerPanel.add(buttons, BorderLayout.NORTH);
		innerPanel.add(firstPage, BorderLayout.CENTER);
		
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
		search = new JButton("Search");
		JTextField searchBar = new JTextField(80);
		
		search.setBackground(new Color(0X732d21));
		search.setForeground(Color.white);
		search.setFocusable(false);
		search.addActionListener(this);
		
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
		ImageIcon homeIcon = new ImageIcon("src\\Images\\home.png");
		ImageIcon catalogueIcon = new ImageIcon("src\\Images\\catalog.png");
		ImageIcon loginIcon = new ImageIcon("src\\Images\\login.png");
		ImageIcon faqIcon = new ImageIcon("src\\Images\\faq.png");
		ImageIcon aboutUsIcon = new ImageIcon("src\\Images\\cart.png");
		
		// Buttons
		accueil = new JButton();
		catalogue = new JButton();
		login = new JButton();
		FAQ = new JButton();
		aboutUs = new JButton();
		
		// Adding Buttons
		buttonHolder.add(buttonHelper(accueil, fixResolution(homeIcon, 50, 50)));
		buttonHolder.add(buttonHelper(catalogue, fixResolution(catalogueIcon, 50, 50)));
		buttonHolder.add(buttonHelper(login, fixResolution(loginIcon, 70, 70)));
		buttonHolder.add(buttonHelper(FAQ, fixResolution(faqIcon, 50, 50)));
		buttonHolder.add(buttonHelper(aboutUs, fixResolution(aboutUsIcon, 50, 50)));
		
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
		//btn.setPreferredSize(new Dimension(264, 70));
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
	private void removeAndAdd(Component newComp)
	{
		try {
			Component panelToRemove = innerPanel.getComponent(1);
			innerPanel.remove(panelToRemove);
			innerPanel.revalidate();
			innerPanel.repaint();
		}
		catch (Exception e) {
			System.out.print(e.getMessage());
		}
		finally {
			innerPanel.add(newComp, BorderLayout.CENTER);
		}
	}
	
	/**
	 * actionPerformed:
	 * 		Defines the behavior of the buttons.
	 */
	@Override
    public void actionPerformed(ActionEvent action)
	{
        
		if (action.getSource() == accueil) {
        	removeAndAdd(firstPage);
        }
        else if (action.getSource() == catalogue) {
        	
        }
        else if (action.getSource() == login) {
        	lgn.displayLogin();
        }
        else if (action.getSource() == search) {
        	
        }
        else if (action.getSource() == FAQ) {
        	removeAndAdd(faqPage);
        }
        else if (action.getSource() == aboutUs) {
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
	
	/**
	 * hoverFunctionality:
	 * 		adds the hover functionality for
	 * 		the 5 'JButtons' at the top of the
	 * 		JFrame. This method is called in the
	 * 		constructor.
	 */
	private void hoverFunctionality() {
		onHover(accueil, "Home");
		onHover(catalogue, "Catalog");
		onHover(login, "Login");
		onHover(FAQ, "FAQ");
		onHover(aboutUs, "Cart");
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
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        return scrollPane;
	}

}