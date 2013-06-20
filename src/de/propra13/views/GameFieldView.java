package de.propra13.views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.util.List;

import de.propra13.Main;
import de.propra13.assets.Theme;
import de.propra13.controllers.GameController;
import de.propra13.views.objects.GameObject;
import de.propra13.views.objects.NpcObject;

public class GameFieldView extends AbstractGameView {

    public static final int GRID = 50;
    private static final long serialVersionUID = 7383103785685757479L;
    private boolean drawsGrid = false;
    private BufferedImage lightMap = null;

    public GameFieldView(GameController controller, Theme theme) {
        super(controller, theme);
    }

    private static BufferedImage createCompatibleImage(int width, int height) {
        return GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getDefaultScreenDevice().getDefaultConfiguration()
                .createCompatibleImage(width, height, Transparency.TRANSLUCENT);
    }

    private BufferedImage getLightMap() {
        if (lightMapIsGarbage()) {
            lightMap = createCompatibleImage(getWidth(), getHeight());
        }

        return lightMap;
    }

    private boolean lightMapIsGarbage() {
        return lightMap == null || lightMap.getWidth() != getWidth()
                || lightMap.getHeight() != getHeight();
    }

    @Override
    protected void render(Graphics2D gfx) {

        drawFloor(gfx);

        drawStart(gfx);
        drawGoal(gfx);
        drawGameObjects(controller.getCurrentRoom().getWalls(), gfx);

        drawGameObjects(controller.getCurrentRoom().getBalls(), gfx);
        drawGameObjects(controller.getCurrentRoom().getItems(), gfx);
        drawGameObjects(controller.getCurrentRoom().getEnemies(), gfx);
        drawGameObjects(controller.getCurrentRoom().getNpcs(), gfx);
        drawGameObjects(controller.getCurrentRoom().getMagics(), gfx);
        drawPlayer(gfx);

        if (drawsGrid)
            drawGrid(gfx);

        drawLightMap(gfx);

        drawText(gfx);
    }

    private void drawText(Graphics2D gfx) {
        for (NpcObject<?> object : controller.getCurrentRoom().getNpcs()) {
            object.drawText(gfx, this);
        }
    }

    private void drawGameObjects(List<? extends GameObject> objects,
            Graphics2D gfx) {
        for (GameObject object : objects) {
            object.draw(gfx, this);
        }
    }

    private void drawLightMap(Graphics2D gfx) {
        BufferedImage lightMap = getLightMap();

        Graphics2D lightGfx = lightMap.createGraphics();
        try {
            lightGfx.setBackground(Color.black);
            lightGfx.clearRect(0, 0, lightMap.getWidth(), lightMap.getHeight());

            for (GameObject gameObject : controller.getCurrentRoom()
                    .getAllObjects()) {
                if (gameObject.glows())
                    drawLight(lightGfx, gameObject);
            }

        } finally {
            lightGfx.dispose();
        }

        gfx.drawImage(lightMap, 0, 0, null);
    }

    private void drawLight(Graphics2D gfx, GameObject gameObject) {
        Graphics2D copyGfx = (Graphics2D) gfx.create();
        try {
            int px = (int) gameObject.getX();
            int py = (int) gameObject.getY();
            copyGfx.translate(px + gameObject.getWidth() / 2,
                    py + gameObject.getHeight() / 2);
            gameObject.glow(copyGfx);
        } finally {
            copyGfx.dispose();
        }
    }

    private void drawStart(Graphics2D gfx) {
        controller.getCurrentRoom().getStart().draw(gfx, this);
    }

    private void drawGoal(Graphics2D gfx) {
        controller.getCurrentRoom().getGoal().draw(gfx, this);
    }

    private void drawFloor(Graphics2D gfx) {
        Dimension dim = getSize();
        Image image = theme.getFloorImage();
        int width = image.getWidth(this);
        int height = image.getHeight(this);

        for (int x = 0; x <= dim.width / width; x++) {
            for (int y = 0; y <= dim.height / height; y++) {
                gfx.drawImage(image, x * width, y * height, this);
            }
        }
    }

    private void drawPlayer(Graphics2D gfx) {
        controller.getCurrentRoom().getPlayerObject().draw(gfx, this);

    }

    private void drawGrid(Graphics2D gfx) {
        gfx.setPaint(Color.WHITE);
        Dimension dim = getSize();
        for (int x = GRID; x < dim.width; x += GRID)
            gfx.drawLine(x, 0, x, Main.GAMEFIELDHEIGHT);
        for (int y = GRID; y < dim.height; y += GRID)
            gfx.drawLine(0, y, Main.GAMEFIELDWIDTH, y);
    }
}
