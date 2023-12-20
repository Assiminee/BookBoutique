package BookBoutique;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


public class Catalogue extends JPanel {
	
	ArrayList<String> genres ;
	HashMap<String, Livre> books;
	
	
	
	public Catalogue(ArrayList<String> genres,HashMap<String, Livre> books) {
		
		this.genres=genres;
		this.books=books;
		
		setLayout(new BorderLayout());
		//add(createJPanels());
		add(scrollPane());
		
		setVisible(true);
	}
	
	
	
	
	
	
	private JPanel createJPanels()
	{
		
		
		JPanel main_panel = new JPanel(new GridLayout((int)Math.floorDiv(genres.size(), 2) + 1, 2,20,20));
		
		for (int i = 0; i < genres.size(); i++) {//récuperé le nombre des elements de la liste des genres.
		JPanel panel= new JPanel();
		String name = genres.get(i);
			

	     
		
		panel.setBackground(new Color(0Xc6c2ac));
		JLabel etiquette = new JLabel(name);//recuperer le nom du genre avec un boucle qui bouclera sur la liste des genres.
		
		panel.add(etiquette);
		
		 // Ajouter un écouteur pour détecter le survol
        panel.addMouseListener(new MouseListener() {
            @SuppressWarnings("unlikely-arg-type")
			@Override
            public void mouseClicked(MouseEvent e) {
                // Action à effectuer lors d'un clic
            	//System.out.println(genres);
            	//Controlleur panel = new Controlleur();
            	//panel.removeAndAdd(etiquette);
            	
            	HashMap<String,Livre> test = Controlleur.searchBooks(books, name, "genre");
        		Accueil livre = new Accueil(test);
        		Controlleur.removeAndAdd(livre);
            	
            }

            @Override
            public void mousePressed(MouseEvent e) {
                // Action à effectuer lors d'un appui sur le bouton de la souris
            	
            	
            	
            	
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // Action à effectuer lors du relâchement du bouton de la souris
            }

            @Override
		    public void mouseEntered(MouseEvent e) {
		    	panel.setToolTipText(name);//ajouter le genre
		    	panel.setForeground(new Color(0X732d21));
		    	panel.setFont(new Font("Forte", Font.BOLD, 20));
		    	panel.setBackground(new Color(0xece7cd));
		    }

            @Override
		    public void mouseExited(MouseEvent e) {
		    	panel.setToolTipText(null);
		        panel.setBackground(new Color(0Xc6c2ac));
		        
		    }
        });

		
        main_panel.add(panel);
	}
		
		return main_panel;
	}
	
	
	
	
	
	public JScrollPane scrollPane()
	{
		
		
		JScrollPane scrolle = new JScrollPane(createJPanels());
		
		scrolle.setPreferredSize(new Dimension(getWidth() - 100, getHeight()));
		// Activer la barre de défilement verticale
		scrolle.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Activer la barre de défilement horizontale
		scrolle.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrolle.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		return scrolle;
		
	}

}