package it.polimi.ingsw.view.client.gui;

import it.polimi.ingsw.model.map.MapLoader;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;

public class ImageHandler {

    private ImageHandler() {
        throw new IllegalStateException("MapLoader class cannot be instantiated");
    }

    public static JLabel setImage(String s, double xMod, double yMod, int width, int height) throws IOException {

       /* URL url = ImageHandler.class.getClassLoader().getResource(s);
        File file = new File(s);*/
        //FileInputStream f = new FileInputStream(s);
        //InputStream f = ImageHandler.class.getClassLoader().getResourceAsStream(s);
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
