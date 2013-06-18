package de.propra13.assets;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import de.propra13.models.Club;
import de.propra13.models.Sword;

public class Theme {

    private BufferedImage heartFullImage;
    private BufferedImage heartDamagedImage;
    private BufferedImage heartRiscImage;
    private BufferedImage spellImage;
    private BufferedImage goldImage;
    private BufferedImage floorImage;

    private BlunaCrate goalBluna;
    private BlunaCrate startBluna;
    private HashMap<String, HashMap<String, BlunaCrate>> playerBlunas;
    private HashMap<String, HashMap<String, BlunaCrate>> dragonBlunas;
    private HashMap<String, HashMap<String, BlunaCrate>> maxBlunas;
    private BlunaCrate magicFireballBluna;
    private BlunaCrate magicFireballExplosionBluna;
    private BlunaCrate skullBluna;
    private BlunaCrate wallBluna;
    private BlunaCrate swordBluna;
    private BlunaCrate clubBluna;
    private BlunaCrate herbBluna;
    private BlunaCrate onehundretBluna;

    private String themeName;

    Theme(String dirName) {
        this.themeName = dirName;

        try {
            heartFullImage = readImage("heart1.png");
            heartDamagedImage = readImage("heart2.png");
            heartRiscImage = readImage("heart3.png");
            spellImage = readImage("spell.png");
            goldImage = readImage("money.png");
            floorImage = readImage("floor.jpg");

            goalBluna = BlunaCrateFactory
                    .getSimpleBlunaCrate(readImage("goal.jpg"));
            startBluna = BlunaCrateFactory
                    .getSimpleBlunaCrate(readImage("start.jpg"));
            wallBluna = BlunaCrateFactory
                    .getSimpleBlunaCrate(readImage("wall1.jpg"));

            initPlayerBlunas();
            initDragonBlunas();
            initFlyingObjectBlunas();
            initNPCBlunas();
            initItemBlunas();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-2);
        }
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

    public BlunaCrate getGoalBluna() {
        return goalBluna;
    }

    public BufferedImage getFloorImage() {
        return floorImage;
    }

    public BlunaCrate getStartBluna() {
        return startBluna;
    }

    public BlunaCrate getWallBluna() {
        return wallBluna;
    }

    public BufferedImage getGoldImage() {
        return goldImage;
    }

    private void initPlayerBlunas() throws IOException {
        String[] animations = new String[] { "stands", "walks", "attacks",
                "takes_hit", "dies", "magics" };
        String[] types = new String[] { Club.class.getSimpleName(),
                Sword.class.getSimpleName() };

        playerBlunas = createAnimationsFor("player", animations, types,
                0x271b11, 76);
    }

    public HashMap<String, HashMap<String, BlunaCrate>> getPlayerBlunas() {
        return playerBlunas;
    }

    private void initDragonBlunas() throws IOException {
        String[] animations = new String[] { "stands", "walks", "dies", "dead",
                "attacks", "takes_hit" };
        dragonBlunas = createAnimationsFor("dragon", animations,
                new String[] { "default" }, 0x1f160d, 128);
    }

    public HashMap<String, HashMap<String, BlunaCrate>> getDragonBlunas() {
        return dragonBlunas;
    }

    private void initNPCBlunas() throws IOException {
        String[] animations = new String[] { "stands", "crouches", "talks" };
        maxBlunas = createAnimationsFor("max", animations,
                new String[] { "default" }, 0x2b1f15, 76);
    }

    public HashMap<String, HashMap<String, BlunaCrate>> getMaxBlunas() {
        return maxBlunas;
    }

    private void initFlyingObjectBlunas() throws IOException {
        magicFireballBluna = BlunaCrateFactory.getBlunaCrate(
                readImage("bluna/magic_fireball.png"), 1, 16, 0);
        magicFireballExplosionBluna = BlunaCrateFactory.getBlunaCrate(
                readImage("bluna/firemagic_explodes.png"), 1, 20, 0);
        skullBluna = BlunaCrateFactory.getBlunaCrate(
                readImage("bluna/skull.png"), 8, 10, 0x1f160d);
    }

    public BlunaCrate getSkullBluna() {
        return skullBluna;
    }

    public BlunaCrate getMagicFireballBluna() {
        return magicFireballBluna;
    }

    public BlunaCrate getMagicFireballExplosionBluna() {
        return magicFireballExplosionBluna;
    }

    private void initItemBlunas() throws IOException {
        swordBluna = BlunaCrateFactory
                .getSimpleBlunaCrate(readImage("sword.png"));
        clubBluna = BlunaCrateFactory
                .getSimpleBlunaCrate(readImage("club.png"));

        herbBluna = BlunaCrateFactory.getBlunaCrate(
                readImage("bluna/herbitem.png"), 1, 8, 0x1f160d);
        onehundretBluna = BlunaCrateFactory.getBlunaCrate(
                readImage("bluna/onehundret.png"), 1, 8, 0x1f160d);
    }

    public BlunaCrate getHerbBluna() {
        return herbBluna;
    }

    public BlunaCrate getOnehundretBluna() {
        return onehundretBluna;
    }

    public BlunaCrate getSwordBluna() {
        return swordBluna;
    }

    public BlunaCrate getClubBluna() {
        return clubBluna;
    }

    private HashMap<String, HashMap<String, BlunaCrate>> createAnimationsFor(
            String entity, String[] animations, String[] types, int shadow,
            int spriteWidth) throws IOException {
        HashMap<String, HashMap<String, BlunaCrate>> blunas = new HashMap<>(
                animations.length);

        for (String animation : animations) {
            HashMap<String, BlunaCrate> set = new HashMap<>(types.length);

            for (String type : types) {
                String fileName = "bluna/" + type + "_" + entity + "_"
                        + animation + ".png";
                BufferedImage image = readImage(fileName.toLowerCase());
                int frames = image.getWidth() / spriteWidth;
                set.put(type, BlunaCrateFactory.getBlunaCrate(image, 8, frames,
                        shadow));

                blunas.put(animation, set);
            }
        }
        return blunas;
    }

    private BufferedImage readImage(String fileName) throws IOException {
        File url = new File("res/leveltheme/" + themeName + "/images/"
                + fileName);
        BufferedImage bluna = null;
        try {
            bluna = ImageIO.read(url);
        } catch (IOException e) {
            throw new IOException("Cannot read " + url.toString());
        }
        return bluna;
    }

}
