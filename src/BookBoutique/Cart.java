package BookBoutique;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Cart - class
 * Creates a JFrame which is used as a cart.
 */
public class Cart extends JFrame implements ActionListener
{
	private double total;
	private JButton purchaseAll;
	private JButton freeCart;
	
	private JLabel totalPriceLabel;
	private JPanel wrapper;
	
	private HashMap<Livre, Integer> cartItems;
	private HashMap<Livre, JLabel> displayedBooks;
	
	public Cart() {
		
		this.total = 0.00;
		this.cartItems = new HashMap<Livre, Integer>();
		this.displayedBooks = new HashMap<Livre, JLabel>();
		wrapper = new JPanel();
		wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
		
		setLayout(new BorderLayout());
		setIconImage(Controlleur.logo.getImage());
		setTitle("BookBoutique");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		
		addPricePanel();
		
		add(Controlleur.scrollPane(wrapper, 350, 500), BorderLayout.CENTER);
	}
	
	public void displayCart() {
		setVisible(true);
		pack();
	}
	
	private void addPricePanel() {
		JPanel priceHolder = new JPanel();
		JPanel buttonHolder = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel container = new JPanel(new BorderLayout());
		purchaseAll = new JButton("  Purchase All  ");
		freeCart = new JButton("  Free Cart  ");
		totalPriceLabel = new JLabel();
		
		priceHolder.setBackground(new Color(0x732d21));
		
		
		totalPriceLabel.setText("    Total = " + total + "    ");
		totalPriceLabel.setForeground(Color.WHITE);
		
		setButtons(purchaseAll);
		setButtons(freeCart);
		
		purchaseAll.addActionListener(this);
		freeCart.addActionListener(this);
		
		buttonHolder.add(purchaseAll);
		buttonHolder.add(freeCart);
		
		priceHolder.add(totalPriceLabel);
		
		container.add(priceHolder, BorderLayout.NORTH);
		container.add(buttonHolder, BorderLayout.CENTER);
		
		add(container, BorderLayout.NORTH);
	}
	
	@Override
    public void actionPerformed(ActionEvent action)
	{
		if (action.getSource() == purchaseAll || action.getSource() == freeCart) {
        	this.total = 0.00;
        	totalPriceLabel.setText("    Total = " + String.format("%.2f", total) + "    ");
        	wrapper.removeAll();
        	revalidate();
    		repaint();
    		cartItems.clear();
        	displayedBooks.clear();
        }
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
		JPanel bookHolder = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 10));
		JPanel infoPanel = new JPanel(new BorderLayout());
		JPanel buttonHolder = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel plusMinus = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		JLabel bookInfo = new JLabel();
		JLabel quantity = new JLabel();
		
		JButton remove = new JButton("  Remove from Cart  ");
		
		JButton minus = new JButton("  -  ");
		JButton plus = new JButton("  +  ");
		
		DisplayImage bookImage = new DisplayImage(book.picture, 0, 0, 100, 140);
		
		editHashMaps(book, quantity);
		
		this.total += book.price;
		
		totalPriceLabel.setText("    Total = " + String.format("%.2f", total) + "    ");
		displayedBooks.get(book).setText(" Quantity: " + cartItems.get(book) + " ");
				
		if (cartItems.get(book) == 1) {
			bookInfo.setText("<HTML><P>" + book.title + "</P>"
							   + "<P>" + book.authName + "</P>"
							   + "<P>" + book.price + "</P><HTML>");
			
			addRemoveAction(remove, book, bookHolder);
			
			setButtons(remove);
			setButtons(plus);
			setButtons(minus);
			
			buttonHolder.add(remove);
			
			plusMinusActions(minus, "minus", book);
			plusMinusActions(plus, "plus", book);
			
			plusMinus.add(minus);
			plusMinus.add(quantity);
			plusMinus.add(plus);
					
			infoPanel.add(bookInfo, BorderLayout.NORTH);
			infoPanel.add(plusMinus, BorderLayout.CENTER);
			infoPanel.add(buttonHolder, BorderLayout.SOUTH);
			
			bookHolder.add(bookImage);
			bookHolder.add(infoPanel);
			
			wrapper.add(bookHolder);
		}
		wrapper.revalidate();
		wrapper.repaint();
	}
	
	private void editHashMaps(Livre book, JLabel label) {
		if (this.cartItems.containsKey(book)) {
			int amount = cartItems.get(book);
			this.cartItems.put(book, amount + 1);
		}
		else {
			this.displayedBooks.put(book, label);
			this.cartItems.put(book, 1);
		}
	}
	
	private void setButtons(JButton btn) {
		btn.setBackground(new Color(0x732d21));
		btn.setForeground(Color.WHITE);
		btn.setBorder(BorderFactory.createRaisedBevelBorder());
		Controlleur.anotherOnHover(btn);
	}

	private void addRemoveAction(JButton btn, Livre book, JPanel parent) {
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				wrapper.remove(parent);
				total -= book.price * cartItems.get(book);
				totalPriceLabel.setText("    Total = " + String.format("%.2f", total) + "    ");
				wrapper.revalidate();
				wrapper.repaint();
				cartItems.remove(book);
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
					
					totalPriceLabel.setText("    Total = " + String.format("%.2f", total) + "    ");
					displayedBooks.get(book).setText(" Quantity: " + cartItems.get(book) + " ");	
					
					if (cartItems.get(book) == 0) {
						cartItems.remove(book);
						JPanel plusMinus = (JPanel) displayedBooks.get(book).getParent();
						JPanel infoPanel = (JPanel) plusMinus.getParent();
						JPanel bookHolder = (JPanel) infoPanel.getParent();
						displayedBooks.remove(book);
						wrapper.remove(bookHolder);
					}
					wrapper.revalidate();
					wrapper.repaint();
				}
			}
		});
	}
	
	
}
