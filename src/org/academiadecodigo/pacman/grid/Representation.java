package org.academiadecodigo.pacman.grid;

import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.ScreenWriter;
import com.googlecode.lanterna.terminal.Terminal;
import org.academiadecodigo.pacman.Constants;
import org.academiadecodigo.pacman.FileHelper;
import org.academiadecodigo.pacman.objects.movables.Player;

import java.util.LinkedList;

/**
 * Created by codecadet on 05/11/17.
 */
public class Representation {

    private Screen screen;
    private ScreenWriter screenWriter;
    private int cols = Constants.GRID_COLS;
    private int rows = Constants.GRID_ROWS;
    public static LinkedList<Position> walkablePositions = new LinkedList<>();

    public void init() {

        screen = TerminalFacade.createScreen();

        screen.getTerminal().getTerminalSize().setColumns(cols);
        screen.getTerminal().getTerminalSize().setRows(rows);

        //screen.getTerminal().setCursorVisible(false);
        screen.setCursorPosition(null);

        screenWriter = new ScreenWriter(screen);

        screen.startScreen();

    }

    public void drawGrid(Player player) {

        screen.clear();

        String[] rows = FileHelper.readFromFile().split("\\n");

        for (int i = 0; i < rows.length; i++) {

            char[] row = rows[i].toCharArray();

            for (int j = 0; j < row.length; j++) {

                if (!(row[j] == '1')) {

                    walkablePositions.add(new Position(j, i));
                    screenWriter.drawString(j, i, " ");
                    screenWriter.setBackgroundColor(Terminal.Color.WHITE);
                }
                //screen.putString(2, 2, "", Terminal.Color.WHITE, Terminal.Color.WHITE);
            }
        }

        //TODO DRAW ALL OBJECTS
        screen.putString(player.getPosition().getCol(), player.getPosition().getRow(), "C", Terminal.Color.GREEN, Terminal.Color.BLACK);

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