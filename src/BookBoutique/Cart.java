package BookBoutique;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.HashMap;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Cart extends JFrame {
	private JPanel wrapper;
	private HashMap<String, Livre> cartItems = new HashMap<String, Livre>();
	
	public Cart() {
		setLayout(new BorderLayout());
		setIconImage(Controlleur.logo.getImage());
		setTitle("BookBoutique");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		
		wrapper = new JPanel();
		wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
		
		add(wrapper);
		add(Controlleur.scrollPane(wrapper, 400, 500));
	}
	
	public void displayCart() {
		setVisible(true);
		pack();
	}
	
	public void addToCart(Livre book) {
		this.cartItems.put(book.title, book);
		
		JPanel bookHolder = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel infoPanel = new JPanel(new BorderLayout());
		JPanel buttonHolder = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		JLabel imageHolder = new JLabel();
		JLabel bookInfo = new JLabel();
		
		JButton remove = new JButton("Remove from Cart");
		JButton buy = new JButton("Buy Item");
		
		ImageIcon bookImage = new ImageIcon(book.picture);
		
		imageHolder.setIcon(Controlleur.fixResolution(bookImage, 90, 140));
		bookInfo.setText("<HTML><P>" + book.title + "</P>"
						   + "<P>" + book.authName + "</P>"
						   + "<P>" + book.price + "</P><HTML>");
		
		buttonHolder.add(remove);
		buttonHolder.add(buy);
		
		infoPanel.add(bookInfo, BorderLayout.CENTER);
		infoPanel.add(buttonHolder, BorderLayout.SOUTH);
		
		bookHolder.add(imageHolder);
		bookHolder.add(infoPanel);
		
		wrapper.add(bookHolder);
		wrapper.revalidate();
		wrapper.repaint();
	}

}
