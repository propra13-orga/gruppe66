package de.propra13.assets;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import de.propra13.models.Weapon;

public class Theme {

    private BufferedImage goalImage;
    private BufferedImage startImage;
    private BufferedImage playerImage;
    private BufferedImage playerWalksImage;
    private BufferedImage playerAttacksImage;
    private BufferedImage playerDiesImage;
    private BufferedImage swordPlayerImage;
    private BufferedImage swordPlayerWalksImage;
    private BufferedImage swordPlayerAttacksImage;
    private BufferedImage swordPlayerDiesImage;
    private BufferedImage fireballImage;
    private BufferedImage wallImage;
    private BufferedImage floorImage;
    private BufferedImage weaponImage;
    private BufferedImage herbImage;
    private BufferedImage onehundretImage;
    private BufferedImage dragonImage;
    private BufferedImage dragonWalksImage;
    private BufferedImage dragonDiesImage;

    private String themeName;

    public Theme(String dirName) {
        try {
            this.themeName = dirName;
            goalImage = initBluna("goal.jpg");
            startImage = initBluna("start.jpg");
            playerImage = initBluna("bluna/player.png");
            playerWalksImage = initBluna("bluna/player_walks.png");
            playerAttacksImage = initBluna("bluna/player_attacks.png");
            playerDiesImage = initBluna("bluna/player_dies.png");
            swordPlayerImage = initBluna("bluna/sword_player.png");
            swordPlayerAttacksImage = initBluna("bluna/sword_player_attacks.png");
            swordPlayerWalksImage = initBluna("bluna/sword_player_walks.png");
            swordPlayerDiesImage = initBluna("bluna/sword_player_dies.png");
            fireballImage = initBluna("bluna/skull.png");
            wallImage = initBluna("wall1.jpg");
            floorImage = initBluna("floor.jpg");
            weaponImage = initBluna("sword.png");
            herbImage = initBluna("bluna/herbitem.png");
            onehundretImage = initBluna("bluna/onehundret.png");
            dragonImage = initBluna("bluna/dragon.png");
            dragonWalksImage = initBluna("bluna/dragon_walks.png");
            dragonDiesImage = initBluna("bluna/dragon_dies.png");
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

    public BufferedImage getPlayerWalksBluna() {
        return playerWalksImage;
    }

    public BufferedImage getPlayerAttacksBluna() {
        return playerAttacksImage;
    }

    public BufferedImage getPlayerDiesBluna() {
        return playerDiesImage;
    }

    public BufferedImage getHerbImage() {
        return herbImage;
    }

    public BufferedImage getOnehundretImage() {
        return onehundretImage;
    }

    public BufferedImage getDragonBluna() {
        return dragonImage;
    }

    public BufferedImage getDragonWalksBluna() {
        return dragonWalksImage;
    }

    public BufferedImage getSwordPlayerImage() {
        return swordPlayerImage;
    }

    public BufferedImage getSwordPlayerWalksImage() {
        return swordPlayerWalksImage;
    }

    public BufferedImage getDragonDiesBluna() {
        return dragonDiesImage;
    }

    public HashMap<String, BufferedImage> getPlayerBlunaSet() {
        HashMap<String, BufferedImage> set = new HashMap<>();
        set.put(Weapon.SWORD, swordPlayerImage);
        return set;
    }

    public HashMap<String, BufferedImage> getPlayerWalksBlunaSet() {
        HashMap<String, BufferedImage> set = new HashMap<>();
        set.put(Weapon.SWORD, swordPlayerWalksImage);
        return set;
    }

    public HashMap<String, BufferedImage> getPlayerAttacksBlunaSet() {
        HashMap<String, BufferedImage> set = new HashMap<>();
        set.put(Weapon.SWORD, swordPlayerAttacksImage);
        return set;
    }

    public HashMap<String, BufferedImage> getPlayerDiesBlunaSet() {
        HashMap<String, BufferedImage> set = new HashMap<>();
        set.put(Weapon.SWORD, swordPlayerDiesImage);
        return set;
    }

}