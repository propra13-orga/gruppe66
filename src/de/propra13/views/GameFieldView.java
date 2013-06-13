package de.propra13.views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;

import de.propra13.Main;
import de.propra13.assets.Theme;
import de.propra13.controllers.GameController;
import de.propra13.views.objects.EnemyObject;
import de.propra13.views.objects.ItemObject;
import de.propra13.views.objects.MoveableGameObject;
import de.propra13.views.objects.SkullObject;
import de.propra13.views.objects.WallObject;

public class GameFieldView extends AbstractGameView {

    public static final int GRID = 50;
    private static final long serialVersionUID = 7383103785685757479L;
    private boolean drawsGrid = false;

    public GameFieldView(GameController controller, Theme theme) {
        super(controller, theme);
    }

    @Override
    protected void render(Graphics2D gfx) {
        drawFloor(gfx);

        drawStart(gfx);
        drawGoal(gfx);
        drawWalls(gfx);

        drawBalls(gfx);
        drawItems(gfx);
        drawEnemies(gfx);
        drawMagics(gfx);
        drawPlayer(gfx);

        if (drawsGrid)
            drawGrid(gfx);
    }

    private void drawMagics(Graphics2D gfx) {
        for (MoveableGameObject magics : currentRoom.getMagics()) {
            magics.draw(gfx, this);
        }
    }

    private void drawStart(Graphics2D gfx) {
        currentRoom.getStart().draw(gfx, this);
    }

    private void drawGoal(Graphics2D gfx) {
        currentRoom.getGoal().draw(gfx, this);
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
        currentRoom.getPlayerObject().draw(gfx, this);

    }

    private void drawWalls(Graphics2D gfx) {
        for (WallObject wall : currentRoom.getWalls()) {
            wall.draw(gfx, this);
        }
    }

    private void drawBalls(Graphics2D gfx) {
        for (SkullObject ball : currentRoom.getBalls()) {
            ball.draw(gfx, this);
        }
    }

    private void drawItems(Graphics2D gfx) {
        for (ItemObject item : currentRoom.getItems()) {
            item.draw(gfx, this);
        }
    }

    private void drawEnemies(Graphics2D gfx) {
        for (EnemyObject enemy : currentRoom.getEnemies()) {
            enemy.draw(gfx, this);
        }
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
