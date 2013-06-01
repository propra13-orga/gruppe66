package de.propra13.assets;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Theme {

    private BufferedImage goalImage;
    private BufferedImage startImage;
    private BufferedImage playerImage;
    private BufferedImage fireballImage;
    private BufferedImage wallImage;
    private BufferedImage floorImage;
    private BufferedImage weaponImage;
    private BufferedImage herbImage;
    private BufferedImage dragonImage;

    private String themeName;

    public Theme(String dirName) {
        try {
            this.themeName = dirName;
            goalImage = initBluna("goal.jpg");
            startImage = initBluna("start.jpg");
            playerImage = initBluna("bluna/player.png");
            fireballImage = initBluna("bluna/skull.png");
            wallImage = initBluna("wall1.jpg");
            floorImage = initBluna("floor.jpg");
            weaponImage = initBluna("sword.png");
            herbImage = initBluna("bluna/herbitem.png");
            dragonImage = initBluna("bluna/dragon.png");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("dun dun dun");
            System.exit(-2);
        }
    }

    private BufferedImage initBluna(String fileName) throws IOException {
        File url = new File("res/leveltheme/" + themeName + "/images/"
                + fileName);
        BufferedImage bluna = ImageIO.read(url);
        return bluna;
    }

    public BufferedImage getGoalImage() {
        return goalImage;
    }

    public BufferedImage getFloorImage() {
        return floorImage;
    }

    public BufferedImage getStartImage() {
        return startImage;
    }

    public BufferedImage getWallImage() {
        return wallImage;
    }

    public BufferedImage getWeaponImage() {
        return weaponImage;
    }

    public BufferedImage getFireballBluna() {
        return fireballImage;
    }

    public BufferedImage getPlayerBluna() {
        return playerImage;
    }

    public BufferedImage getHerbImage() {
        return herbImage;
    }

    public BufferedImage getDragonBluna() {
        return dragonImage;
    }

}