package Administrator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class RoundedPanel extends JPanel
{
	private int width, height;
	private int cornerRadius = 50;
    public BufferedImage image;
    public String imagePath;
    public int[] imageSpecs;
    public RoundedPanel(String imagePath, int width, int height, int[] imageSpecs) {
        super();
        try {
        	this.imagePath = imagePath;
            image = ImageIO.read(new File(imagePath));
        }
        catch (IOException e) {
        	if (!imagePath.isEmpty())
        		System.out.println("Image doesn't exist");
        }
        finally
        {
        	this.imageSpecs = imageSpecs;
        	this.width = width;
        	this.height = height;
            setBorder(new EmptyBorder(0, 10, 10, 10));
            setBackground(Color.white);
            setPreferredSize(new Dimension(width, height));
        }

        setOpaque(false);
    }
    
    public void insertLabel(String text, int x, int y, int width, int height, int weight) {
    	setLayout(null);
    	JLabel label = new JLabel(text);
        
    	label.setFont(new Font("Cambria", Font.TYPE1_FONT, weight));
    	label.setBounds(x, y, width, height);
        add(label);
    }
    
    public void setCornerRadius(int cornerRadius) {
        this.cornerRadius = cornerRadius;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D graphics2D = (Graphics2D) g.create();

        graphics2D.setColor(getBackground());
        graphics2D.fillRoundRect(0, 0, width, height, cornerRadius, cornerRadius);
        if (image != null) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.drawImage(image, imageSpecs[0], imageSpecs[1], imageSpecs[2], imageSpecs[3], null); // Drawing the image to fit the panel size
        }
        graphics2D.dispose();
    }
}
