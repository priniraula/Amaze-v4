package display;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

public class Assets {
    private static final int width = 64;
    private static final int height = 64;
    public static BufferedImage wall, path, player, finish;

    public static void init () {
        Spritesheet sheet = new Spritesheet(Imageloader.loadImage("/home/priniraula/workspace/Amaze-v4/src/assets/sheet.png"));
        System.out.println("Sheet is " + sheet.toString());
        
        wall = sheet.crop(0, 0, width, height);
        path = sheet.crop(width, 0, width, height);
        player = sheet.crop(width * 2, 0, width, height);
        finish = sheet.crop(width * 3, 0, width, height);
    }
}
