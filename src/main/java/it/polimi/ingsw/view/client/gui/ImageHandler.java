package it.polimi.ingsw.view.client.gui;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Class that handle the loading of the images
 * @author Scilux
 * @version 1.0
 * @since 2020/06/13
 */

public class ImageHandler {

    /**
     * Private class builder
     */

    private ImageHandler() {
        throw new IllegalStateException("ImageHandler class cannot be instantiated");
    }

    /**
     * Method that handle the loading of the image
     * @param s Path to the image
     * @param xMod Parameter for horizontal scaling
     * @param yMod Parameter for vertical scaling
     * @param width Desired width size
     * @param height Desired height size
     * @return The image as a JLabel
     * @throws IOException if the upload was not successful
     */

    public static JLabel setImage(String s, double xMod, double yMod, int width, int height) throws IOException {

        s = s.replaceFirst("resources", "");

        BufferedImage image = ImageIO.read(ImageHandler.class.getResourceAsStream(s));
        BufferedImage board = new BufferedImage((int)(width*xMod/100), (int)(height*yMod/100), BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = board.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_LCD_CONTRAST, 100);
        g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,RenderingHints.VALUE_STROKE_PURE);
        g.drawImage(image, 0, 0, (int)(width*xMod/100), (int)(height*yMod/100), null);
        g.dispose();

        return new JLabel(new ImageIcon(board));
    }
}
