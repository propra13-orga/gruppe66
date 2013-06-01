package de.propra13.views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import de.propra13.Main;
import de.propra13.assets.Theme;
import de.propra13.controllers.GameController;
import de.propra13.models.Room;
import de.propra13.views.objects.ItemObject;
import de.propra13.views.objects.MoveableGameObject;
import de.propra13.views.objects.SkullObject;
import de.propra13.views.objects.WallObject;

public class GameFieldView extends JPanel {

    public static final int GRID = 50;

    private static final long serialVersionUID = 7383103785685757479L;

    private Room currentRoom;
    private Theme theme;

    private RenderingHints rh;

    private boolean drawsGrid = false;

    private GameController controller;

    public GameFieldView(GameController controller, Theme theme) {
        super();
        this.theme = theme;
        initComponent();
        initRenderingHints();

        this.controller = controller;
    }

    public List<MoveableGameObject> getMoveableGameObjects() {
        List<MoveableGameObject> list = new ArrayList<>();

        list.add(currentRoom.getPlayerObject());
        list.addAll(currentRoom.getBalls());

        return list;
    }

    private void initComponent() {
        setFocusable(true);
        setDoubleBuffered(true);
    }

    private void initRenderingHints() {
        rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        rh.put(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room room) {
        this.currentRoom = room;
        currentRoom.getPlayerObject().setMoved(false);
    }

    @Override
    public void removeNotify() {
        super.removeNotify();
        controller.stop();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        if (controller.isRunning()) {
            Graphics2D gfx = (Graphics2D) g;

            clearAndSetRenderingHints(gfx);

            drawStart(gfx);
            drawGoal(gfx);
            drawWalls(gfx);

            drawPlayer(gfx);
            drawBalls(gfx);
            drawItems(gfx);

            if (drawsGrid)
                drawGrid(gfx);

            Toolkit.getDefaultToolkit().sync();
        }
        g.dispose();
    }

    private void drawStart(Graphics2D gfx) {
        currentRoom.getStart().draw(gfx, this);
    }

    private void drawGoal(Graphics2D gfx) {
        currentRoom.getGoal().draw(gfx, this);
    }

    private void clearAndSetRenderingHints(Graphics2D gfx) {
        Dimension dim = getSize();
        gfx.setBackground(Color.white);
        gfx.clearRect(0, 0, dim.width, dim.height);

        gfx.setRenderingHints(rh);

        drawFloor(gfx);
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

    private void drawGrid(Graphics2D gfx) {
        gfx.setPaint(Color.WHITE);
        Dimension dim = getSize();
        for (int x = GRID; x < dim.width; x += GRID)
            gfx.drawLine(x, 0, x, Main.HEIGHT);
        for (int y = GRID; y < dim.height; y += GRID)
            gfx.drawLine(0, y, Main.WIDTH, y);
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }
}
