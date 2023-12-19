package BookBoutique;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

class DisplayImage extends JPanel {
    private BufferedImage image;
    private int x, y, width, height;
    
    public DisplayImage(String imagePath, int x, int y, int width, int height) {
    	this.x = x;
    	this.y = y;
    	this.width = width;
    	this.height = height;
    	setLayout(new FlowLayout(FlowLayout.CENTER));
    	setPreferredSize(new Dimension(width, height));
    	loadImage(imagePath);
    }

    private void loadImage(String imagePath) {
        try {
            image = ImageIO.read(new File(imagePath));
            if (image == null) {
                System.out.println("Unable to load the image.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setImage(String imagePath, int x, int y, int width, int height) {
        loadImage(imagePath);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        if (image != null) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.drawImage(image, x, y, width, height, null);
            g2d.dispose();
        }
    }
}