package BookBoutique;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.JFrame;

public class More extends JFrame implements ActionListener
{
	
	//private JPanel wrapper = new JPanel();
	Livre book;
	JPanel picPanel;
    JLabel picLabel;
    JButton addToCart;
    JPanel infoPanel;
    JLabel infoLabel;
	
	public More() {
        setLayout(new FlowLayout());
        setTitle("BookBoutique");
        setIconImage(Controlleur.logo.getImage());
		add(createPicPanel());
		add(createInfoPanel());
        setResizable(false);
	}
	
	private JPanel createPicPanel() {
		picPanel = new JPanel();
		picLabel = new JLabel();
		addToCart = new JButton("  Add to cart  ");
		
		addToCart.setBackground(new Color(0x732d21));
	    addToCart.setForeground(Color.WHITE);
	    addToCart.setFocusable(false);
	    addToCart.addActionListener(this);
	    
	    addToCart.setBorder(BorderFactory.createRaisedBevelBorder());
	    Controlleur.anotherOnHover(addToCart);
	    
	    picLabel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.BLACK, Color.BLACK));
		picPanel.setLayout(new BoxLayout(picPanel, BoxLayout.Y_AXIS));
		picPanel.add(picLabel);
		picPanel.add(Box.createVerticalStrut(5));
		picPanel.add(addToCart);
		return picPanel;
		
	}
	private JPanel createInfoPanel() {
		 infoPanel = new JPanel(new BorderLayout());
		 infoLabel = new JLabel();
		 infoPanel.add(infoLabel, BorderLayout.NORTH);
		 return infoPanel;
	}
    
    public void openMore(Livre book) {  	
    	this.book = book;
        ImageIcon bookPic = new ImageIcon(book.picture);
        picLabel.setIcon(Controlleur.fixResolution(bookPic, 250, 300));
        
        StringBuffer gnr = new StringBuffer();
        for(String gnre : book.genre) { gnr.append(gnre+", "); }
        
        infoLabel.setText("<HTML><P><U>Title:</U> " + book.title + "</P>"
				   + "<P><U>Auteur:</U> " + book.authName + "</P>"
				   + "<P><U>Synopsis:</U> " + book.synopsis + "</P>"
				   + "<P><U>Genre:</U> " + gnr.substring(0, gnr.length()-2) + "</P>"
				   + "<P><U>Prix:</U> " + book.price + "</P><HTML>");
				   
		revalidate();
		repaint();
		toFront();
		setVisible(true);
		pack();
		setLocationRelativeTo(null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == addToCart) {
			if (Controlleur.connectedUser == null) {
				Controlleur.loginAction();
			}
			else {
				Controlleur.cart.addToCart(book);
			}
        }
	}
}
