package Administrator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import BookBoutique.ConnectionDB;
import BookBoutique.Controlleur;

public class UsersPanel extends JPanel implements ActionListener
{
private JButton previous, next;
private int pageNumber = 0, numberOfPages, usersCount;
private JPanel innerPanel;
//private AddEditUsers popUp;
//private OrdersUsers orders; 

	public UsersPanel() {
		//this.popUp = new AddEditUsers();
		this.usersCount = ConnectionDB.getCount("users");
		
		this.numberOfPages = (int) (usersCount / 11);
		
		if (usersCount % 11 != 0) {
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
		JPanel usersHolder = new JPanel(new GridLayout(4, 3, 25, 25));
		ArrayList<String> usersArray = Controlleur.connection.getUsers("users", start, 11);
		
		for (String users : usersArray) {
			RoundedPanel usersPanel = new RoundedPanel("", 335, 100, new int[] {});
			usersPanel.insertLabel(users, 65, 10, 200, 25, 20);
			((JLabel) usersPanel.getComponent(0)).setHorizontalAlignment(JLabel.CENTER);
			usersPanel.add(generateButton("Delete", users, 45, 45, 75, 30));
			usersPanel.add(generateButton("Modify", users, 130, 45, 75, 30));
			usersPanel.add(generateButton("Orders", users, 215, 45, 75, 30));
			usersHolder.add(usersPanel);
		}
		usersHolder.add(addNewPanel(335, 100, new int[] {55, 15, 70, 70}, "Add New users", 135, 37, 200, 25, 20, () -> System.out.println("test")));
		innerPanel.add(usersHolder);
		
		return innerPanel;
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
	

	
	private JButton generateButton(String buttonLabel, String users, int x, int y, int width, int height) {
		JButton btn = new JButton();
		
		btn.setBounds(x, y, width, height);
		
		btn.setText(buttonLabel);
		if (buttonLabel.equals("Modify"))
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
				if (buttonLabel.equals("Modify")) {
					//popUp.appear(users);
				}
				else if(buttonLabel.equals("Orders"))
				{
					//new OrdersUsers(true);
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
