/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.info;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author 1624013
 */
public class OptionsMenuState extends BasicGameState {

    public final static int ID = 3;
    private StateBasedGame game;
    private Image background;
    java.awt.Font UIFont1;
    org.newdawn.slick.UnicodeFont uniFont;

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        this.game = game;
        this.background = new Image("ca/qc/bdeb/info/res/background/menu_options.jpg");
        try {
            UIFont1 = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT,
                    org.newdawn.slick.util.ResourceLoader.getResourceAsStream("ca/qc/bdeb/info/res/font/SFOuterLimits.ttf"));
            UIFont1 = UIFont1.deriveFont(java.awt.Font.BOLD, 30.f); //You can change "PLAIN" to "BOLD" or "ITALIC"... and 16.f is the size of your font

            uniFont = new org.newdawn.slick.UnicodeFont(UIFont1);
            uniFont.addAsciiGlyphs();
            uniFont.getEffects().add(new ColorEffect(java.awt.Color.white)); //You can change your color here, but you can also change it in the render{ ... }
            uniFont.addAsciiGlyphs();
            uniFont.loadGlyphs();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        background.draw(0, 0, container.getWidth(), container.getHeight());
        Color color = new Color(224, 245, 254);
        uniFont.drawString(470, 370, "Nouvelle Partie", Color.black);
        uniFont.drawString(470, 420, "Charger", Color.black);
        uniFont.drawString(470, 470, "Options", Color.black);
        uniFont.drawString(470, 520, "Quitter", Color.black);

    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        int posX = Mouse.getX();
        int posY = Mouse.getY();
        System.out.println("X: " + posX + " Y:" + posY);
        if ((posX >= 470 && posX <= 766) && (posY >= 210 && posY <= 230)) {
            if (Mouse.isButtonDown(0)) {
                game.enterState(IntroState.ID);
            }
        } else if ((posX >= 470 && posX <= 600) && (posY >= 60 && posY <= 80)) {
            if (Mouse.isButtonDown(0)) {
                container.exit();
            }
        }
    }
}

