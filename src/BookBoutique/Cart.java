package BookBoutique;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

/**
 * Cart - class
 * Creates a JFrame which is used as a cart.
 */
public class Cart extends JFrame {
	private double total;
	private double selected;
	private JLabel totalPriceLabel;
	private JLabel selectedItemsLabel;
	private JPanel wrapper;
	private HashMap<String, Livre> cartItems;
	private HashMap<String, Livre> selectedItems;
	
	public Cart() {
		
		this.total = 0.00;
		this.selected = 0.00;
		this.cartItems = new HashMap<String, Livre>();
		this.selectedItems = new HashMap<String, Livre>();
		
		setLayout(new BorderLayout());
		setIconImage(Controlleur.logo.getImage());
		setTitle("BookBoutique");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		
		wrapper = new JPanel();
		wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
		
		addPricePanel();
		
		add(wrapper);
		add(Controlleur.scrollPane(wrapper, 400, 500));
	}
	
	public void displayCart() {
		setVisible(true);
		pack();
	}
	
	private void addPricePanel() {
		JPanel priceHolder = new JPanel();
		totalPriceLabel = new JLabel();
		selectedItemsLabel = new JLabel();
		
		totalPriceLabel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.BLACK, Color.BLACK));
		totalPriceLabel.setText("<HTML><P>Total = " + total + "</P></HTML>");
		
		selectedItemsLabel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.BLACK, Color.BLACK));
		selectedItemsLabel.setText("<HTML><P>Total Selected = " + selected + "</P></HTML>");
		
		priceHolder.add(totalPriceLabel);
		priceHolder.add(selectedItemsLabel);
		
		wrapper.add(priceHolder);
	}
	/**
	 * addToCart:
	 * 		Adds the books added to the cart in Accueil
	 * 		into a 'HashMap' which is used to display
	 * 		the books the user selected.
	 * @param book - the book selected by the user
	 * This method creates a JPanel for each book selected
	 * adds it to the wrapper 'JPanel', refreshes the display
	 * and shows all the items selected.
	 */
	public void addToCart(Livre book) {
		JPanel bookHolder = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel infoPanel = new JPanel(new BorderLayout());
		JPanel buttonHolder = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		JLabel imageHolder = new JLabel();
		JLabel bookInfo = new JLabel();
		
		JButton remove = new JButton("Remove from Cart");
		JCheckBox select = new JCheckBox("Select Item");
		
		ImageIcon bookImage = new ImageIcon(book.picture);
		
		this.cartItems.put(book.title, book);
		this.total += book.price;
		
		totalPriceLabel.setText("<HTML><P>Total = " + String.format("%.2f", total) + "</P></HTML>");
		
		imageHolder.setIcon(Controlleur.fixResolution(bookImage, 100, 140));
		bookInfo.setText("<HTML><P>" + book.title + "</P>"
						   + "<P>" + book.authName + "</P>"
						   + "<P>" + book.price + "</P><HTML>");
		
		addButtonAction(remove, book, bookHolder);
		addCheckboxAction(select, book);
		
		buttonHolder.add(remove);
		buttonHolder.add(select);
				
		infoPanel.add(bookInfo, BorderLayout.CENTER);
		infoPanel.add(buttonHolder, BorderLayout.SOUTH);
		
		bookHolder.add(imageHolder);
		bookHolder.add(infoPanel);
		
		wrapper.add(bookHolder);
		wrapper.revalidate();
		wrapper.repaint();
	}

	private void addButtonAction(JButton btn, Livre book, JPanel parent) {
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				wrapper.remove(parent);
				cartItems.remove(book.title);
				total -= book.price;
				if (selectedItems.containsKey(book.title)) {
					selected -= book.price;
					selectedItemsLabel.setText("<HTML><P>Selected = " + String.format("%.2f", selected) + "</P></HTML>");
					selectedItems.remove(book.title);
				}
				totalPriceLabel.setText("<HTML><P>Total = " + String.format("%.2f", total) + "</P></HTML>");
			}
		});
	}
	
	private void addCheckboxAction(JCheckBox select, Livre book) {
		select.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (select.isSelected()) {
					selected += book.price;
					selectedItems.put(book.title, book);
				}
				else {
					selected -= book.price;
					selectedItems.remove(book.title, book);
				}
				selectedItemsLabel.setText("<HTML><P>Selected = " + String.format("%.2f", selected) + "</P></HTML>");
			}
		});
	}
}
