package de.propra13.assets;

import java.awt.Rectangle;
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
    private BufferedImage shopHealthImage;
    private BufferedImage shopManaImage;
    private BufferedImage shopArmorImage;
    private BufferedImage shopSwordImage;

    private BlunaCrate goalBluna;
    private BlunaCrate startBluna;
    private HashMap<String, HashMap<String, BlunaCrate>> playerBlunas;
    private HashMap<String, HashMap<String, BlunaCrate>> dragonBlunas;
    private HashMap<String, HashMap<String, BlunaCrate>> maxBlunas;
    private HashMap<String, HashMap<String, BlunaCrate>> traderBlunas;
    private BlunaCrate magicFireballBluna;
    private BlunaCrate magicFireballExplosionBluna;
    private BlunaCrate skullBluna;
    private BlunaCrate wallBluna;
    private BlunaCrate swordBluna;
    private BlunaCrate clubBluna;
    private BlunaCrate healthBluna;
    private BlunaCrate manaBluna;
    private BlunaCrate armorBluna;
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
            shopHealthImage = readImage("shop_health.png");
            shopManaImage = readImage("shop_mana.png");
            shopArmorImage = readImage("shop_armor.png");
            shopSwordImage = readImage("shop_sword.png");

            goalBluna = getSimpleBlunaCrate(readImage("goal.jpg"));
            startBluna = getSimpleBlunaCrate(readImage("start.jpg"));
            wallBluna = getSimpleBlunaCrate(readImage("wall1.jpg"));

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

    public BufferedImage getShopHealthImage() {
        return shopHealthImage;
    }

    public BufferedImage getShopManaImage() {
        return shopManaImage;
    }

    public BufferedImage getShopArmorImage() {
        return shopArmorImage;
    }

    public BufferedImage getShopSwordImage() {
        return shopSwordImage;
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
        String[] animations = new String[] { "stands", "idles", "talks" };
        maxBlunas = createAnimationsFor("max", animations,
                new String[] { "default" }, 0x2b1f15, 76);

        traderBlunas = createAnimationsFor("trader", animations,
                new String[] { "default" }, 0x23180f, 96);
    }

    public HashMap<String, HashMap<String, BlunaCrate>> getMaxBlunas() {
        return maxBlunas;
    }

    public HashMap<String, HashMap<String, BlunaCrate>> getTraderBlunas() {
        return traderBlunas;
    }

    private void initFlyingObjectBlunas() throws IOException {
        magicFireballBluna = getBlunaCrate(
                readImage("bluna/magic_fireball.png"), 1, 16, 0);
        magicFireballExplosionBluna = getBlunaCrate(
                readImage("bluna/firemagic_explodes.png"), 1, 20, 0);
        skullBluna = getBlunaCrate(readImage("bluna/skull.png"), 8, 10,
                0x1f160d);
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
        swordBluna = getSimpleBlunaCrate(readImage("sword.png"));
        clubBluna = getSimpleBlunaCrate(readImage("club.png"));

        healthBluna = getBlunaCrate(readImage("bluna/healthitem.png"), 1, 8,
                0x1f160d);
        manaBluna = getBlunaCrate(readImage("bluna/manaitem.png"), 1, 8,
                0x1f160d);
        armorBluna = getSimpleBlunaCrate(readImage("armoritem.png"));
        onehundretBluna = getBlunaCrate(readImage("bluna/onehundret.png"), 1,
                8, 0x1f160d);
    }

    public BlunaCrate getHealthBluna() {
        return healthBluna;
    }

    public BlunaCrate getManaBluna() {
        return manaBluna;
    }

    public BlunaCrate getArmorBluna() {
        return armorBluna;
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
                set.put(type, getBlunaCrate(image, 8, frames, shadow));

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

    private static BlunaCrate getSimpleBlunaCrate(BufferedImage image) {
        return getBlunaCrate(image, 1, 1, 0);
    }

    private static BlunaCrate getBlunaCrate(BufferedImage image,
            int directions, int frames, int shadowRGB) {
        return getBlunaCrate(image, directions, frames, shadowRGB, null);
    }

    private static BlunaCrate getBlunaCrate(BufferedImage image,
            int directions, int frames, int shadowRGB, Rectangle bounds) {
        return createBlunaCrate(image, directions, frames, shadowRGB, bounds);

    }

    private static BlunaCrate createBlunaCrate(BufferedImage image,
            int directions, int frames, int shadowRGB, Rectangle bounds) {
        BlunaCrate blunaCrate = new BlunaCrate(image, directions, frames);
        if (bounds == null)
            blunaCrate.calculateBounds(shadowRGB);
        else
            blunaCrate.setBounds(bounds);

        return blunaCrate;
    }
}
