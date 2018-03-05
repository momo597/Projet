/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.info;

import java.util.ArrayList;
import java.util.List;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author 1624013
 */
public class IntroState extends BasicGameState {

    public final static int ID = 4;
    private StateBasedGame game;
    private List<String> lines;
    private int renderRow = 0;
    private int renderCol = 0;
    public static final int TYPE_DELAY = 50;
    private int time = TYPE_DELAY;
    private boolean finished = false;
    private Font font;
    private int width = 400;

    @Override
    public int getID() {
        return ID;
    }

    public void keyPressed(int key, char c) {
        if (key == Input.KEY_SPACE) {
            showAll();
        }
        if(finished=true){
            if(key==Input.KEY_ENTER){
                game.enterState(MapGameState.ID);
            }
        }
    }
    public void showAll() {
        if (lines.isEmpty()) {
            renderRow = renderCol = 0;
        } else {
            renderRow = lines.size() - 1;
            renderCol = lines.get(renderRow).length();
        }
        finished = true;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        this.game = game;
        font = container.getDefaultFont();
        String text = "Nous sommes en l'an 2040, le président des États-Unis "
                + "est atteint d'un mal inconnu et intraitable. La technologie "
                + "biomédicale a grandement avancé et le rétrécissement moléculaire "
                + "est maintenant possible. C'est ainsi que Victor Krux, le meilleur "
                + "scientifique de l'époque est envoyé dans le corps du président "
                + "pour comprendre la nature du mal qui l'habite et ansi le soigner. "
                + "Malheureusement, Victor se perdit dans le corps et se retrouva coincé. "
                + "C'est ainsi que vous rentrez dans l'action. Vous êtes le meilleur élève "
                + "du scientifique et vous avez été envoyé pour le sauver et localiser "
                + "le mal du président. Bonne Chance!\n\n "
                + "Appuyez sur enter pour continuer";
        lines = wrap(text, font, width);
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        int x = 200;
        int y = 150;
        g.setColor(Color.white);
        int lineHeight = font.getLineHeight();

        //only render the rows we have typed out so far (renderRow = current row)
        for (int i = 0; i < renderRow + 1; i++) {
            String line = lines.get(i);
            //render whole line if it's a previous one, otherwise render the col
            int len = i < renderRow ? line.length() : renderCol;
            String t = line.substring(0, len);
            if (t.length() != 0) {
                g.drawString(t, x, y);
            }
            y += lineHeight;
        }

    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        time -= delta;
        if (time <= 0 && !finished) {
            time = TYPE_DELAY;
            String line = lines.get(renderRow);

            //if we are moving down to the next line
            if (renderCol > lines.get(renderRow).length() - 1) {
                //we've rendered all characters
                if (renderRow >= lines.size() - 1) {
                    finished = true;
                } //move to next line
                else {
                    renderRow++;
                    renderCol = 0;
                }
            } else {
                //move to next character
                renderCol++;
            }
        }
    }

    private List<String> wrap(String text, Font font, int width) {
        //A less accurate but more efficient wrap would be to specify the max 
        //number of columns (e.g. using the width of the 'M' character or something). 
        //The below method will look nicer in the end, though.

        List<String> list = new ArrayList<String>();
        String str = text;
        String line = "";

        //we will go through adding characters, once we hit the max width
        //we will either split the line at the last space OR split the line
        //at the given char if no last space exists
        //while we still have text to check
        int i = 0;
        int lastSpace = -1;
        while (i < str.length()) {
            char c = str.charAt(i);
            if (Character.isWhitespace(c)) {
                lastSpace = i;
            }

            //time to wrap 
            if (c == '\n' || font.getWidth(line + c) > width) {
                //if we've hit a space recently, use that
                int split = lastSpace != -1 ? lastSpace : i;
                int splitTrimmed = split;

                //if we are splitting by space, trim it off for the start of the
                //next line
                if (lastSpace != -1 && split < str.length() - 1) {
                    splitTrimmed++;
                }

                line = str.substring(0, split);
                str = str.substring(splitTrimmed);

                //add the line and reset our values
                list.add(line);
                line = "";
                i = 0;
                lastSpace = -1;
            } //not time to wrap, just keep moving along
            else {
                line += c;
                i++;
            }
        }
        if (str.length() != 0) {
            list.add(str);
        }
        return list;
    }
}

