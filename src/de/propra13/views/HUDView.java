package de.propra13.views;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import de.propra13.assets.Theme;
import de.propra13.controllers.GameController;
import de.propra13.models.Player;

public class HUDView extends AbstractGameView {

    private static final long serialVersionUID = 7383103785685757479L;

    private Font font;
    private float scale = 1;
    private double counter = 0;
    private BufferedImage manaBulb;

    public HUDView(GameController controller, Theme theme) throws IOException {
        super(controller, theme);
        font = new Font("Verdana", Font.PLAIN, 16);
        loadManaBulb();
    }

    private void loadManaBulb() throws IOException {
        File url = new File("res/manabulb.png");
        manaBulb = ImageIO.read(url);
    }

    @Override
    protected void render(Graphics2D gfx) {
        gfx.setBackground(Color.black);
        gfx.clearRect(0, 0, 1000, 1000);

        drawLifes(gfx);
        drawMana(gfx);
    }

    private void drawMana(Graphics2D gfx) {
        Player player = getCurrentRoom().getPlayerObject().getPlayer();
        int manaRecHeight = (int) (player.getMana() / Player.MAXMANA * 43);
        gfx.setPaint(new Color(5, 81, 186));
        gfx.fillRect(70, 46 - manaRecHeight, 50, 50);
        gfx.drawImage(manaBulb, 70, 0, this);
    }

    private void drawLifes(Graphics2D gfx) {
        Player player = currentRoom.getPlayerObject().getPlayer();
        Graphics2D gfxCopy = (Graphics2D) gfx.create();

        try {

            switch (player.getLifes()) {
            case 3:
                gfxCopy.scale(scale, scale);
                scale = 1 + 0.05f * (float) (Math.sin(counter += 0.07));
                gfxCopy.drawImage(theme.getHeartFullImage(), 10, 10, 30, 30,
                        null);
                break;
            case 2:
                gfxCopy.scale(scale, scale);
                scale = 1 + 0.07f * (float) (Math.sin(counter += 0.1));
                gfxCopy.drawImage(theme.getHeartDamagedImage(), 10, 10, 30, 30,
                        null);

                break;
            case 1:
                gfxCopy.scale(scale, scale);
                scale = 1 + 0.1f * (float) (Math.sin(counter += 0.13));
                gfxCopy.drawImage(theme.getHeartRiscImage(), 10, 10, 30, 30,
                        null);
                break;
            }
        } finally {
            gfxCopy.dispose();
        }

        gfx.setPaint(Color.white);
        gfx.setFont(font);
        gfx.drawString("x" + String.valueOf(player.getLifes()), 42, 35);
    }
}
