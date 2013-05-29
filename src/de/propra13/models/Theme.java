package de.propra13.models;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Theme {

    private Image goalImage;
    private Image startImage;
    private BufferedImage playerImage;
    private BufferedImage fireballImage;
    private Image wallImage;
    private Image floorImage;
    private Image weaponImage;

    private String themeName;

    public Theme(String dirName) {
        try {
            this.themeName = dirName;
            goalImage = initImage("goal.jpg");
            startImage = initImage("start.jpg");
            playerImage = initBluna("player.png");
            fireballImage = initBluna("skull.png");
            wallImage = initImage("wall1.jpg");
            floorImage = initImage("floor.jpg");
            weaponImage = initImage("sword.png");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("dun dun dun");
            System.exit(-2);
        }
    }

    private Image initImage(String fileName) {
        ImageIcon icon = new ImageIcon("res/leveltheme/" + themeName
                + "/images/" + fileName);
        return icon.getImage();
    }

    private BufferedImage initBluna(String fileName) throws IOException {
        File url = new File("res/leveltheme/" + themeName + "/images/bluna/"
                + fileName);
        BufferedImage bluna = ImageIO.read(url);
        return bluna;
    }

    public Image getGoalImage() {
        return goalImage;
    }

    public Image getFloorImage() {
        return floorImage;
    }

    public Image getStartImage() {
        return startImage;
    }

    public Image getWallImage() {
        return wallImage;
    }

    public Image getWeaponImage() {
        return weaponImage;
    }

    public BufferedImage getFireballBluna() {
        return fireballImage;
    }

    public BufferedImage getPlayerBluna() {
        return playerImage;
    }

}