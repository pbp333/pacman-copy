package org.academiadecodigo.pacman.grid;

import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.ScreenWriter;
import com.googlecode.lanterna.terminal.Terminal;
import org.academiadecodigo.pacman.Constants;
import org.academiadecodigo.pacman.FileHelper;
import org.academiadecodigo.pacman.objects.GameObject;
import org.academiadecodigo.pacman.objects.movables.Player;

import java.util.LinkedList;

public class Representation {

    private Screen screen;
    private ScreenWriter screenWriter;
    private int cols = Constants.GRID_COLS;
    private int rows = Constants.GRID_ROWS;
    private String[] mapRow;
    public static LinkedList<Position> walkablePositions = new LinkedList<>();

    public void init() {

        screen = TerminalFacade.createScreen();

        screen.getTerminal().getTerminalSize().setColumns(cols);
        screen.getTerminal().getTerminalSize().setRows(rows);

        screen.setCursorPosition(null);

        screenWriter = new ScreenWriter(screen);

        screen.startScreen();

        mapRow = FileHelper.readFromFile().split("\\n");
    }

    public void drawGrid(GameObject[] gameObjects) {

        screen.clear();

        for (int i = 0; i < mapRow.length; i++) {

            char[] mapColumn = mapRow[i].toCharArray();

            for (int j = 0; j < mapColumn.length; j++) {

                if (!(mapColumn[j] == '1')) {

                    walkablePositions.add(new Position(j, i));
                }

                switch (mapColumn[j]){

                    case '0':
                        screen.putString(j, i, ".", Terminal.Color.YELLOW, Terminal.Color.BLACK);
                        break;
                    case '1':
                        screenWriter.drawString(j, i, " ");
                        screenWriter.setBackgroundColor(Terminal.Color.WHITE);
                        break;
                    case '2':
                        screen.putString(j, i, "", Terminal.Color.RED, Terminal.Color.BLACK);
                        break;
                }
            }
        }

        for (GameObject gameObject : gameObjects) {

            if(gameObject instanceof Player && ((Player)gameObject).isKilled()){
                continue;
            }


            int col = gameObject.getPosition().getCol();
            int row = gameObject.getPosition().getRow();
            String label = gameObject.getType().getLabel();
            Terminal.Color color = gameObject.getType().getColor();
            Terminal.Color background;

            if (gameObject.getType().equals("POWERUP")) {
                background = Terminal.Color.RED;

            } else {
                background = Terminal.Color.YELLOW;
            }

            screen.putString(col, row, label, background, color);
        }

        screen.refresh();
    }

    public static LinkedList<Position> getWalkablePositions() {
        return walkablePositions;
    }

    public Screen getScreen() {
        return screen;
    }

    public ScreenWriter getScreenWriter() {
        return screenWriter;
    }

}
