/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.info;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author 1624013
 */
public class StateGame extends StateBasedGame {

    public static void main(String[] args) throws SlickException {
        new AppGameContainer(new StateGame(), 800, 600, false).start();
    }

    public StateGame() {
        super("Quest inside the human's body");
    }

    @Override
    public void initStatesList(GameContainer container) throws SlickException {
        addState(new MainScreenGameState());
        addState(new MapGameState());
         addState(new OptionsMenuState());
         addState(new IntroState());
    }

}

