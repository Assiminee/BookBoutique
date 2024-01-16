package BookBoutique;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
	
	//private HashMap<Livre, Integer> cartItems;
	//private HashMap<Livre, JLabel> displayedBooks;
	
	public void storeCart() {
		String filePath = "src/carts/" + Controlleur.connectedUser.userName + ".txt";
		Path file = Paths.get(filePath);
		String entry = "";
		
		
		if (Files.exists(file)) {
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
				writer.write("");
				System.out.println("File has been cleared");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
			for (Livre book : cartItems.keySet()) {
				String[] genres = book.genre;
				String bookGenres = "[";
				for (String genre : genres) {
					bookGenres += (genre + "/");
				}
				bookGenres = bookGenres.substring(0, bookGenres.length() - 1);
				bookGenres += "]";
				entry = book.title + ";"
						+ book.picture + ";"
						+ book.authName + ";"
						+ book.synopsis + ";"
						+ bookGenres + ";"
						+ book.price + ";"
						+ cartItems.get(book);
				writer.write(entry);
				writer.newLine();
			}
			System.out.println("File has been updated");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadCart() {
		String filePath = "src/carts/" + Controlleur.connectedUser.userName + ".txt";
		Path file = Paths.get(filePath);
        BufferedReader reader = null;
        String line = "";


        if (Files.exists(file)) {
	        try {
	            reader = new BufferedReader(new FileReader(filePath));
	            
	            while ((line = reader.readLine()) != null) {

	                String[] row = line.split(";");
	                String[] newArray = new String[row.length - 1];
	                String genreStr = row[4];
	                genreStr = genreStr.substring(1, genreStr.length() - 1);
	                int quantity = Integer.parseInt(row[row.length - 1]);
	                for (int i = 0; i < row.length - 1; i++) {
	                	if (i == 4) {
	                		newArray[i] = genreStr;
	                		continue;
	                	}
	                	newArray[i] = row[i];
	                }
	                Livre book = new Livre(newArray);
	                for (int i = 0; i < quantity; i++) {
		                addToCart(book);
	                }
	            }
	        }
	        catch (Exception e) {
	        	System.out.println("Failed to load file");
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
		}
	}
	
	public void emptyCart() {
		this.total = 0.00;
    	totalPriceLabel.setText("    Total = " + String.format("%.2f", total) + "    ");
    	wrapper.removeAll();
    	revalidate();
		repaint();
		cartItems.clear();
    	displayedBooks.clear();
	}
	
	public void displayCart() {
		toFront();
		setVisible(true);
		pack();
		setLocationRelativeTo(null);
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
	
	public static ArrayList<User> getUsers() {
		String fileName = "src/usersLogins/users.txt";
        BufferedReader reader = null;
        String line = "";
		ArrayList<User> users = new ArrayList<User>();

        try {
            reader = new BufferedReader(new FileReader(fileName));

            while ((line = reader.readLine()) != null) {
                String[] row = line.split("²");
                User currUser = new User(row);
                users.add(currUser);
            }
        }
        catch (Exception e) {
        	System.out.println("Failed to load user");
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
		return users;
	}
	@Override
    public void actionPerformed(ActionEvent action)
	{
		if (action.getSource() == purchaseAll || action.getSource() == freeCart) {
			if (action.getSource() == purchaseAll) {
				StringBuilder items = new StringBuilder();
				for (Livre book : cartItems.keySet()) {
					items.append(book.title + "\t" + book.price + "\t" + cartItems.get(book) + "\t" + book.price * cartItems.get(book) + "\n");
				}
				EmailService.send(Controlleur.connectedUser.email, "Invoice", items.toString());
			}
			emptyCart();
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
			bookInfo.setPreferredSize(new Dimension(100, 80));
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
