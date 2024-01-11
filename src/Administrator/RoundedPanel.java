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

	private int cornerRadius = 30;
    private BufferedImage image;
    public RoundedPanel(String imagePath, String label, int count) {
        super();
        try {
            image = ImageIO.read(new File(imagePath));
            JLabel fieldTitle = new JLabel(label);
            JLabel elementCount = new JLabel(Integer.toString(count));
            fieldTitle.setFont(new Font("Cambria", Font.TYPE1_FONT, 20));
            elementCount.setFont(new Font("Cambria", Font.TYPE1_FONT, 10));
            setLayout(null);
            setBorder(new EmptyBorder(0, 10, 10, 10));
            setBackground(Color.white);
            fieldTitle.setBounds(160, 50, 100, 20);
            elementCount.setBounds(160, 70, 100, 20);
            add(fieldTitle);
            add(elementCount);
            setPreferredSize(new Dimension(300, 250));
        } catch (IOException e) {
            e.printStackTrace();
        }

        setOpaque(false);
    }

    public void setCornerRadius(int cornerRadius) {
        this.cornerRadius = cornerRadius;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D graphics2D = (Graphics2D) g.create();

        int width = 300;
        int height = 150;

        graphics2D.setColor(getBackground());
        graphics2D.fillRoundRect(0, 0, width, height, cornerRadius, cornerRadius);
        if (image != null) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.drawImage(image, 20, 25, 100, 100, null); // Drawing the image to fit the panel size
        }
        graphics2D.dispose();
    }
}
