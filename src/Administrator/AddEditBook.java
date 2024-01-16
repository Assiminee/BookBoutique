package Administrator;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import BookBoutique.Controlleur;
import BookBoutique.Livre;

public class AddEditBook extends JFrame
{
	public JPanel wrapper;
	private static AddEditBook instance;
	private String bookTitle;
	private JTextField title, authName, price, quantity;
	private JTextArea synopsis;
	private boolean added = false;
	private ArrayList<String> book;
	public AddEditBook() {
		instance = this;
		this.wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
		setIconImage(Controlleur.logo.getImage());
		setTitle("BookBoutique");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setLayout(new FlowLayout(FlowLayout.CENTER));
		add(wrapper);
	}
	
	public void appear(String bookTitle) {
		this.bookTitle = bookTitle;
		this.book = bookTitle.isEmpty() ? null : Controlleur.connection.getSingleBook("SELECT * FROM books WHERE title LIKE \"%" + bookTitle + "%\";");
		if (added)
			wrapper.removeAll();
		wrapper.add(fieldsPanel());
		revalidate();
		repaint();
		toFront();
		setVisible(true);
		pack();
		setLocationRelativeTo(null);
	}
	private RoundedPanel fieldsPanel() {
		RoundedPanel fullPanel = new RoundedPanel(book != null ? book.get(5) : "src\\Images\\empty.jpg", 500, 500, new int[] {20, 20, 100, 140});
		
		fullPanel.insertLabel("Title: ", 155, 45, 60, 20, 20);
		fullPanel.add(title = generateRow(book != null ? book.get(0) : "", 220, 40, 240, 30));
		
		fullPanel.insertLabel("Author: ", 130, 115, 90, 20, 20);
		fullPanel.add(authName = generateRow(book != null ? book.get(1) : "", 220, 110, 240, 30));
		
		fullPanel.insertLabel("Price: ", 125, 190, 60, 20, 20);
		fullPanel.add(price = generateRow(book != null ? book.get(2) : "", 185, 185, 75, 30));
		
		fullPanel.insertLabel("Quantity: ", 285, 190, 120, 20, 20);
		fullPanel.add(quantity = generateRow(book != null ? book.get(3) : "", 385, 185, 75, 30));
		
		fullPanel.insertLabel("Synopsis: ", 20, 250, 120, 20, 20);
		fullPanel.add(synopsis = generateTextArea(book != null ? book.get(4) : "", 125, 250, 335, 200));
		
		fullPanel.add(generateButtons("Upload Image", 105, 460, 140, 35, () -> uploadImage(fullPanel)));
		fullPanel.add(generateButtons("Add", 255, 460, 140, 35, () -> System.out.println("Test")));
		added = true;
		return fullPanel;
	}
	
	private JButton generateButtons(String title, int x, int y, int width, int height, Runnable codeToRun) {
		JButton btn = new JButton(title);
		
		btn.setBounds(x, y, width, height);
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				codeToRun.run();
			}
		});
		return btn;
	}
	
	private JTextArea generateTextArea(String areaText, int x, int y, int width, int height) {
		JTextArea textArea = new JTextArea(width, height);
		
		textArea.setBounds(x, y, width, height);
		textArea.setText(areaText);
		textArea.setBorder(BorderFactory.createEtchedBorder());
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				int max = 500;
				String text = textArea.getText();
				if (text.length() >= max) {
					String shortened = text.substring(0, max - 1);
					textArea.setText(shortened);
					e.consume();
				}
			}
		});
		
		return textArea;
	}
	
	private JTextField generateRow(String fieldText, int x, int y, int width, int height) {
		JTextField textField = new JTextField(width);
		textField.setBounds(x, y, width, height);
		textField.setText(fieldText);
		textField.setBorder(BorderFactory.createEtchedBorder());
		
		return textField;
	}
	
	public static void uploadImage(RoundedPanel panel) {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(instance);

        if (result == JFileChooser.APPROVE_OPTION) {
            // absolute 
            File selectedFile = fileChooser.getSelectedFile();
            String image = copyImageToRessources(selectedFile);
            try {
				panel.image = ImageIO.read(new File(image));
			} catch (IOException e) {
				e.printStackTrace();
			}
            panel.repaint();
            AddEditBook.instance.revalidate();
            AddEditBook.instance.repaint();
        }
    }

    public static String copyImageToRessources(File sourceFile) {
    	Path dest = null;
        try {
            Path folderPath = Paths.get("src\\Images");

            if (Files.notExists(folderPath)) {
                Files.createDirectory(folderPath);
            }

            dest = folderPath.resolve(sourceFile.getName());
            Files.copy(sourceFile.toPath(), dest, StandardCopyOption.REPLACE_EXISTING);
            System.out.println(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dest.toString();
    }
}
