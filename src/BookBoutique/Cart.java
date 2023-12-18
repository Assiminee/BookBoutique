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
public class Cart extends JFrame
{
	private double total;
	private double selected;
	
	private JLabel totalPriceLabel;
	private JLabel selectedItemsLabel;
	private JPanel wrapper;
	
	private HashMap<Livre, Integer> cartItems;
	private HashMap<String, Livre> selectedItems;
	private HashMap<Livre, JLabel> displayedBooks;
	
	public Cart() {
		
		this.total = 0.00;
		this.selected = 0.00;
		
		this.cartItems = new HashMap<Livre, Integer>();
		this.selectedItems = new HashMap<String, Livre>();
		this.displayedBooks = new HashMap<Livre, JLabel>();
		
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
		JPanel plusMinus = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		JLabel imageHolder = new JLabel();
		JLabel bookInfo = new JLabel();
		JLabel quantity = new JLabel();
		
		JButton remove = new JButton("Remove from Cart");
		JCheckBox select = new JCheckBox("Select Item");
		
		JButton minus = new JButton("-");
		JButton plus = new JButton("+");
		
		ImageIcon bookImage = new ImageIcon(book.picture);
		
		if (this.cartItems.containsKey(book)) {
			int amount = cartItems.get(book);
			this.cartItems.put(book, amount + 1);
		}
		else {
			this.displayedBooks.put(book, quantity);
			this.cartItems.put(book, 1);
		}
		
		this.total += book.price;
		
		totalPriceLabel.setText("<HTML><P>Total = " + String.format("%.2f", total) + "</P></HTML>");
		displayedBooks.get(book).setText("<HTML><P>Quantity: " + cartItems.get(book) + "</P><HTML>");
		
		if (cartItems.get(book) == 1) {
			imageHolder.setIcon(Controlleur.fixResolution(bookImage, 100, 140));
			bookInfo.setText("<HTML><P>" + book.title + "</P>"
							   + "<P>" + book.authName + "</P>"
							   + "<P>" + book.price + "</P><HTML>");
			
			addRemoveAction(remove, book, bookHolder);
			addCheckboxAction(select, book);
			
			buttonHolder.add(remove);
			buttonHolder.add(select);
			
			plusMinusActions(minus, "minus", book);
			plusMinusActions(plus, "plus", book);
			
			plusMinus.add(minus);
			plusMinus.add(quantity);
			plusMinus.add(plus);
					
			infoPanel.add(bookInfo, BorderLayout.NORTH);
			infoPanel.add(plusMinus, BorderLayout.CENTER);
			infoPanel.add(buttonHolder, BorderLayout.SOUTH);
			
			bookHolder.add(imageHolder);
			bookHolder.add(infoPanel);
			
			wrapper.add(bookHolder);
		}
		wrapper.revalidate();
		wrapper.repaint();
	}

	private void addRemoveAction(JButton btn, Livre book, JPanel parent) {
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				wrapper.remove(parent);
				total -= book.price * cartItems.get(book);
				if (selectedItems.containsKey(book.title)) {
					selected -= book.price * cartItems.get(book);
					selectedItemsLabel.setText("<HTML><P>Selected = " + String.format("%.2f", selected) + "</P></HTML>");
					selectedItems.remove(book.title);
				}
				totalPriceLabel.setText("<HTML><P>Total = " + String.format("%.2f", total) + "</P></HTML>");
				cartItems.remove(book);
			}
		});
	}
	
	private void addCheckboxAction(JCheckBox select, Livre book) {
		select.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (select.isSelected()) {
					selected += cartItems.get(book) * book.price;
					selectedItems.put(book.title, book);
				}
				else {
					selected -= cartItems.get(book) * book.price;
					selectedItems.remove(book.title, book);
				}
				selectedItemsLabel.setText("<HTML><P>Selected = " + String.format("%.2f", selected) + "</P></HTML>");
			}
		});
	}
	
	private void plusMinusActions(JButton btn, String name, Livre book) {
		btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int amount = cartItems.get(book);
				int sign;
				
				if (amount >= 1) {
					if (name.equals("minus"))
						sign = -1;
					else
						sign = 1;
					
					cartItems.put(book, amount + sign);
					
					total += sign * book.price;
					
					totalPriceLabel.setText("<HTML><P>Total = " + String.format("%.2f", total) + "</P></HTML>");
					displayedBooks.get(book).setText("<HTML><P>Quantity: " + cartItems.get(book) + "</P><HTML>");	
					
					if (selectedItems.containsKey(book.title)) {
						selected += sign * book.price;
						selectedItemsLabel.setText("<HTML><P>Selected = " + String.format("%.2f", selected) + "</P></HTML>");
					}
					
					if (cartItems.get(book) == 0) {
						cartItems.remove(book);
						JPanel plusMinus = (JPanel) displayedBooks.get(book).getParent();
						JPanel infoPanel = (JPanel) plusMinus.getParent();
						JPanel bookHolder = (JPanel) infoPanel.getParent();
						displayedBooks.remove(book);
						selectedItems.remove(book.title, book);
						wrapper.remove(bookHolder);
					}
				}
			}
		});
	}
}
