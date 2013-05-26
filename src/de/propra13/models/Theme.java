package de.propra13.models;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Theme {

    private Image goalImage;
    private Image startImage;
    private Image playerImage;
    private Image fireballImage;
    private Image wallImage;
    private Image floorImage;

    private String themeName;

    public Theme(String dirName) {
        this.themeName = dirName;
        goalImage = initImage("goal.jpg");
        startImage = initImage("start.jpg");
        playerImage = initImage("player.png");
        fireballImage = initImage("fireball.png");
        wallImage = initImage("wall1.jpg");
        floorImage = initImage("floor.jpg");
    }

    private Image initImage(String fileName) {
        ImageIcon icon = new ImageIcon("res/leveltheme/" + themeName
                + "/images/" + fileName);
        return icon.getImage();
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

    public Image getPlayerImage() {
        return playerImage;
    }

    public Image getFireballImage() {
        return fireballImage;
    }

    public Image getWallImage() {
        return wallImage;
    }

}