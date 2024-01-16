package Administrator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import BookBoutique.ConnectionDB;
import BookBoutique.Controlleur;
import BookBoutique.Livre;

public class GenrePanel extends JPanel implements ActionListener
{
	private JButton previous, next;
	private int pageNumber = 0, numberOfPages, genreCount;
	private JPanel innerPanel;
	
	public GenrePanel() {
		this.genreCount = ConnectionDB.getCount("genres");
		
		this.numberOfPages = (int) (genreCount / 11);
		
		if (genreCount % 11 != 0) {
			this.numberOfPages++;
		}
		
		this.innerPanel = createInnerPanel(0);
		
		setLayout(new BorderLayout());
		
		add(createSearchBar(), BorderLayout.NORTH);
		add(innerPanel, BorderLayout.CENTER);
		add(paginationButtons(), BorderLayout.SOUTH);
		
		setVisible(true);
	}
	
	public static JPanel createSearchBar() {
		JPanel searchHolder = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JTextField searchField = new JTextField(80);
		JButton searchButton = new JButton("Search");
		
		searchHolder.add(searchField);
		searchHolder.add(searchButton);
		return searchHolder;
	}
	
	private JPanel createInnerPanel(int start) {
		innerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel genreHolder = new JPanel(new GridLayout(4, 3, 25, 25));
		ArrayList<String> genreArray = Controlleur.connection.getNGenres("genres", start, 11);
		
		for (String genre : genreArray) {
			RoundedPanel genrePanel = new RoundedPanel("", 335, 100, new int[] {});
			genrePanel.insertLabel(genre, 65, 10, 200, 25, 20);
			((JLabel) genrePanel.getComponent(0)).setHorizontalAlignment(JLabel.CENTER);
			genrePanel.add(generateButton("Books", genre, 45, 45, 75, 30));
			genrePanel.add(generateButton("Edit", genre, 130, 45, 75, 30));
			genrePanel.add(generateButton("Delete", genre, 215, 45, 75, 30));
			genreHolder.add(genrePanel);
		}
		genreHolder.add(addNewPanel(335, 100, new int[] {55, 15, 70, 70}, "Add New Genre", 135, 37, 200, 25, 20, () -> System.out.println("test")));
		innerPanel.add(genreHolder);
		
		return innerPanel;
	}
	
	static public RoundedPanel addNewPanel(int panelWidth, int panelHeight, int[] imageCoords,
									String panelTitle, int x, int y, int width, int height,
									int weight, Runnable codeToRun) {
		RoundedPanel addPanel = new RoundedPanel("src\\Images\\add.png", panelWidth, panelHeight, imageCoords);
		addPanel.insertLabel(panelTitle, x, y, width, height, weight);
		addPanel.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				addPanel.setBackground(Color.decode("#fff3da"));
				codeToRun.run();
			}

			@Override
			public void mousePressed(MouseEvent e) {
				addPanel.setBackground(Color.decode("#fff3da"));
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				addPanel.setBackground(Color.WHITE);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				addPanel.setBackground(Color.decode("#fff3da"));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				addPanel.setBackground(Color.WHITE);
			}
			
		});
		return addPanel;
	}
	private JPanel paginationButtons() {
		JPanel buttonHolder = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
		
		previous = new JButton("Previous");
		next = new JButton("    Next    ");
		
		previous.addActionListener(this);
		next.addActionListener(this);
		
		buttonHolder.add(previous);
		buttonHolder.add(next);
		return buttonHolder;
	}
	
	private JButton generateButton(String buttonLabel, String genre, int x, int y, int width, int height) {
		JButton btn = new JButton();
		
		btn.setBounds(x, y, width, height);
		
		btn.setText(buttonLabel);
		if (buttonLabel.equals("Books"))
			btn.setBackground(Color.decode("#ffc218"));
		else if (buttonLabel.equals("Delete"))
			btn.setBackground(Color.decode("#fe3745"));
		else
			btn.setBackground(Color.decode("#ff9518"));
		btn.setFocusable(false);
		btn.setForeground(Color.WHITE);
		btn.setBorder(BorderFactory.createRaisedBevelBorder());

		ActionListener btnActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (buttonLabel.equals("Books")) {
					Admin.rightWrapper.removeAll();
					Admin.rightWrapper.add(new BooksPanel(genre));
					Admin.rightWrapper.revalidate();
					Admin.rightWrapper.repaint();
				}
				else {
					
				}
			}
		};
		btn.addActionListener(btnActionListener);
		return btn;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == previous) {
			if (pageNumber > 0) {
				pageNumber--;
				removeAndAdd(pageNumber == 0 ? 1 : pageNumber * 11); 
			}
		}
		else if (e.getSource() == next) {
			if (pageNumber < numberOfPages - 1) {
				pageNumber++;
				removeAndAdd(pageNumber == 0 ? 11 : pageNumber * 11);
			}
		}
	}
	
	private void removeAndAdd(int start) {
		this.remove(innerPanel);
		innerPanel = createInnerPanel(start);
		add(innerPanel, BorderLayout.CENTER);
		revalidate();
		repaint();
	}
}
