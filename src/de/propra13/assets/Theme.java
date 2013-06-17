package de.propra13.assets;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import de.propra13.models.Club;
import de.propra13.models.Sword;

public class Theme {

    private BufferedImage goalImage;
    private BufferedImage startImage;
    private BufferedImage heartFullImage;
    private BufferedImage heartDamagedImage;
    private BufferedImage heartRiscImage;
    private BufferedImage playerImage;
    private BufferedImage playerWalksImage;
    private BufferedImage playerAttacksImage;
    private BufferedImage playerDiesImage;
    private BufferedImage playerMagicsImage;
    private BufferedImage magicFireballImage;
    private BufferedImage magicFireballExplosionImage;
    private BufferedImage swordPlayerImage;
    private BufferedImage swordPlayerWalksImage;
    private BufferedImage swordPlayerAttacksImage;
    private BufferedImage swordPlayerDiesImage;
    private BufferedImage swordPlayerMagicsImage;
    private BufferedImage fireballImage;
    private BufferedImage spellImage;
    private BufferedImage goldImage;
    private BufferedImage wallImage;
    private BufferedImage floorImage;
    private BufferedImage swordImage;
    private BufferedImage clubImage;
    private BufferedImage herbImage;
    private BufferedImage onehundretImage;
    private BufferedImage dragonImage;
    private BufferedImage dragonWalksImage;
    private BufferedImage dragonDiesImage;
    private BufferedImage dragonDeadImage;
    private BufferedImage dragonAttacksImage;
    private BufferedImage salesmanImage;
    private BufferedImage salesmanCrouchesImage;
    private BufferedImage salesmanTalksImage;

    private String themeName;

    Theme(String dirName) {
        try {
            this.themeName = dirName;
            goalImage = initBluna("goal.jpg");
            startImage = initBluna("start.jpg");
            heartFullImage = initBluna("heart1.png");
            heartDamagedImage = initBluna("heart2.png");
            heartRiscImage = initBluna("heart3.png");
            playerImage = initBluna("bluna/player.png");
            playerWalksImage = initBluna("bluna/player_walks.png");
            playerAttacksImage = initBluna("bluna/player_attacks.png");
            playerDiesImage = initBluna("bluna/player_dies.png");
            playerMagicsImage = initBluna("bluna/player_magics.png");
            magicFireballImage = initBluna("bluna/magic_fireball.png");
            magicFireballExplosionImage = initBluna("bluna/firemagic_explodes.png");
            swordPlayerImage = initBluna("bluna/sword_player.png");
            swordPlayerAttacksImage = initBluna("bluna/sword_player_attacks.png");
            swordPlayerWalksImage = initBluna("bluna/sword_player_walks.png");
            swordPlayerDiesImage = initBluna("bluna/sword_player_dies.png");
            swordPlayerMagicsImage = initBluna("bluna/sword_player_magics.png");
            fireballImage = initBluna("bluna/skull.png");
            spellImage = initBluna("spell.png");
            wallImage = initBluna("wall1.jpg");
            goldImage = initBluna("money.png");
            floorImage = initBluna("floor.jpg");
            swordImage = initBluna("sword.png");
            clubImage = initBluna("club.png");
            herbImage = initBluna("bluna/herbitem.png");
            onehundretImage = initBluna("bluna/onehundret.png");
            dragonImage = initBluna("bluna/dragon.png");
            dragonWalksImage = initBluna("bluna/dragon_walks.png");
            dragonDiesImage = initBluna("bluna/dragon_dies.png");
            dragonDeadImage = initBluna("bluna/dragon_dead.png");
            dragonAttacksImage = initBluna("bluna/dragon_attacks.png");
            salesmanImage = initBluna("bluna/salesman.png");
            salesmanCrouchesImage = initBluna("bluna/salesman_crouches.png");
            salesmanTalksImage = initBluna("bluna/salesman_talks.png");
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

    public BufferedImage getSalesmanCrouchesImage() {
        return salesmanCrouchesImage;
    }

    public BufferedImage getSalesmanImage() {
        return salesmanImage;
    }

    public BufferedImage getSalesmanTalksImage() {
        return salesmanTalksImage;
    }

    public BufferedImage getSpellImage() {
        return spellImage;
    }

    public BufferedImage getHeartDamagedImage() {
        return heartDamagedImage;
    }

    public BufferedImage getHeartFullImage() {
        return heartFullImage;
    }

    public BufferedImage getHeartRiscImage() {
        return heartRiscImage;
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

    public BufferedImage getSwordImage() {
        return swordImage;
    }

    public BufferedImage getClubImage() {
        return clubImage;
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

    public BufferedImage getPlayerMagicsBluna() {
        return playerMagicsImage;
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

    public BufferedImage getDragonDeadBluna() {
        return dragonDeadImage;
    }

    public BufferedImage getDragonAttacksBluna() {
        return dragonAttacksImage;
    }

    public BufferedImage getSwordPlayerImage() {
        return swordPlayerImage;
    }

    public BufferedImage getSwordPlayerWalksImage() {
        return swordPlayerWalksImage;
    }

    public BufferedImage getSwordPlayerMagicsBluna() {
        return swordPlayerMagicsImage;
    }

    public BufferedImage getDragonDiesBluna() {
        return dragonDiesImage;
    }

    public HashMap<String, BufferedImage> getPlayerBlunaSet() {
        HashMap<String, BufferedImage> set = new HashMap<>();
        set.put(Club.class.getSimpleName(), playerImage);
        set.put(Sword.class.getSimpleName(), swordPlayerImage);
        return set;
    }

    public HashMap<String, BufferedImage> getPlayerWalksBlunaSet() {
        HashMap<String, BufferedImage> set = new HashMap<>();
        set.put(Club.class.getSimpleName(), playerWalksImage);
        set.put(Sword.class.getSimpleName(), swordPlayerWalksImage);
        return set;
    }

    public HashMap<String, BufferedImage> getPlayerAttacksBlunaSet() {
        HashMap<String, BufferedImage> set = new HashMap<>();
        set.put(Club.class.getSimpleName(), playerAttacksImage);
        set.put(Sword.class.getSimpleName(), swordPlayerAttacksImage);
        return set;
    }

    public HashMap<String, BufferedImage> getPlayerDiesBlunaSet() {
        HashMap<String, BufferedImage> set = new HashMap<>();
        set.put(Club.class.getSimpleName(), playerDiesImage);
        set.put(Sword.class.getSimpleName(), swordPlayerDiesImage);
        return set;
    }

    public HashMap<String, BufferedImage> getPlayerMagicsBlunaSet() {
        HashMap<String, BufferedImage> set = new HashMap<>();
        set.put(Club.class.getSimpleName(), playerMagicsImage);
        set.put(Sword.class.getSimpleName(), swordPlayerMagicsImage);
        return set;
    }

    public BufferedImage getMagicFireballImage() {
        return magicFireballImage;
    }

    public BufferedImage getMagicFireballExplosionImage() {
        return magicFireballExplosionImage;
    }

    public BufferedImage getGoldImage() {
        return goldImage;
    }

}