package BookBoutique;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Catalogue extends JPanel {
	
	ArrayList<String> genres ;
	HashMap<String, Livre> books;
	
	public Catalogue(ArrayList<String> genres,HashMap<String, Livre> books) {
		
		this.genres = genres;
		this.books = books;
		
		setLayout(new BorderLayout());
		add(Controlleur.scrollPane(createJPanels(), getWidth(), getHeight()), BorderLayout.CENTER);
		
		setVisible(true);
	}

	private JPanel createJPanels()
	{
		int numRows = (int)Math.floorDiv(genres.size(), 5) + 1;
		JPanel main_panel = new JPanel(new GridLayout(numRows, 5, 20, 20));
		
		for (int i = 0; i < genres.size(); i++) 
		{
			//récuperé le nombre des elements de la liste des genres.
			JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
			String name = genres.get(i);
			JLabel etiquette = new JLabel(name);
			
			etiquette.setFont(new Font("Georgia", Font.BOLD, 15));

			panel.setBackground(new Color(0Xc6c2ac));
			panel.add(etiquette);
			
	        panel.addMouseListener(new MouseListener() {
				@Override
	            public void mouseClicked(MouseEvent e) {
	            	HashMap<String,Livre> selectedBooks = Controlleur.genreBasedSearch(books, name);
	        		Accueil newPage = new Accueil(selectedBooks, name);
	        		Controlleur.removeAndAdd(newPage);
	            }
	
	            @Override
	            public void mousePressed(MouseEvent e) {
	            	
	            }
	
	            @Override
	            public void mouseReleased(MouseEvent e) {
	                // Action à effectuer lors du relâchement du bouton de la souris
	            }
	
	            @Override
			    public void mouseEntered(MouseEvent e) {
			    	panel.setBackground(new Color(0xece7cd));
			    }
	
	            @Override
			    public void mouseExited(MouseEvent e) {
			    	panel.setBackground(new Color(0Xc6c2ac));
			    }
	        });
	        main_panel.add(panel);
		}
		
		return main_panel;
	}

}