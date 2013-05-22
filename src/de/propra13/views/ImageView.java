package de.propra13.views;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class ImageView extends JPanel {

    private static final long serialVersionUID = -1076316407747923984L;
    
    private Image image;

    public ImageView(String fileName) {
        ImageIcon icon = new ImageIcon(fileName);
        image = icon.getImage();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(image, (getWidth() - image.getWidth(this)) / 2,
                (getHeight() - image.getHeight(this)) / 2, this);
    }

}
