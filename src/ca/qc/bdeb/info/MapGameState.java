/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.info;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

/**
 *
 * @author 1624013
 */
public class MapGameState extends BasicGameState {

    public static final int ID = 2;
    private GameContainer container;
    private TiledMap map;
    private float x = 300, y = 300;
    private int direction = 0;
    private boolean moving = false;
    private float xCamera = x, yCamera = y;
    private Animation[] animations = new Animation[8];

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        this.container = container;
        this.map = new TiledMap("H:\\Projet Intégrateur\\src\\ca\\qc\\bdeb\\info\\res\\map\\map2.essai.tmx");
        SpriteSheet spriteSheet = new SpriteSheet(("H:\\Projet Intégrateur\\src\\ca\\qc\\bdeb\\info\\res\\map\\sprites\\people\\soldier_altcolor.png"), 64, 64);
        Animation animation = new Animation();
        this.animations[0] = loadAnimation(spriteSheet, 0, 1, 0);
        this.animations[1] = loadAnimation(spriteSheet, 0, 1, 1);
        this.animations[2] = loadAnimation(spriteSheet, 0, 1, 2);
        this.animations[3] = loadAnimation(spriteSheet, 0, 1, 3);
        this.animations[4] = loadAnimation(spriteSheet, 1, 9, 0);
        this.animations[5] = loadAnimation(spriteSheet, 1, 9, 1);
        this.animations[6] = loadAnimation(spriteSheet, 1, 9, 2);
        this.animations[7] = loadAnimation(spriteSheet, 1, 9, 3);
    }

    private Animation loadAnimation(SpriteSheet spriteSheet, int startX, int endX, int y) {
        Animation animation = new Animation();
        for (int x = startX; x < endX; x++) {
            animation.addFrame(spriteSheet.getSprite(x, y), 100);
        }
        return animation;
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        g.translate(container.getWidth() / 2 - (int) this.xCamera,
                container.getHeight() / 2 - (int) this.yCamera);
        this.map.render(0, 0);
        g.setColor(new Color(0, 0, 0, .5f));
        g.fillOval(x - 16, y - 8, 32, 16);
        g.drawAnimation(animations[direction + (moving ? 4 : 0)], x - 32, y - 60);
    }

    @Override
    public void keyReleased(int key, char c) {
        switch (key) {
            case Input.KEY_UP:
                if (direction == 0) {
                    this.moving = false;
                }
                break;
            case Input.KEY_LEFT:
                if (direction == 1) {
                    this.moving = false;
                }
                break;
            case Input.KEY_DOWN:
                if (direction == 2) {
                    this.moving = false;
                }
                break;
            case Input.KEY_RIGHT:
                if (direction == 3) {
                    this.moving = false;
                }
                break;
        }
    }

    @Override
    public void keyPressed(int key, char c) {
        switch (key) {
            case Input.KEY_UP:
                this.direction = 0;
                this.moving = true;
                break;
            case Input.KEY_LEFT:
                this.direction = 1;
                this.moving = true;
                break;
            case Input.KEY_DOWN:
                this.direction = 2;
                this.moving = true;
                break;
            case Input.KEY_RIGHT:
                this.direction = 3;
                this.moving = true;
                break;
        }
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        if (this.moving) {
            float futurX = getFuturX(delta);
            float futurY = getFuturY(delta);
            boolean collision = isCollision(futurX, futurY);
            if (collision) {
                this.moving = false;
            } else {
                this.x = futurX;
                this.y = futurY;
            }
        }
        int w = container.getWidth() / 4;
        if (this.x > this.xCamera + w) {
            this.xCamera = this.x - w;
        }
        if (this.x < this.xCamera - w) {
            this.xCamera = this.x + w;
        }
        int h = container.getHeight() / 4;
        if (this.y > this.yCamera + h) {
            this.yCamera = this.y - h;
        }
        if (this.y < this.yCamera - h) {
            this.yCamera = this.y + h;
        }
       
    }

    private boolean isCollision(float x, float y) {
        int tileW = this.map.getTileWidth();
        int tileH = this.map.getTileHeight();
        int logicLayer = this.map.getLayerIndex("Logic");
        Image tile = this.map.getTileImage((int) x / tileW, (int) y / tileH, logicLayer);
        boolean collision = tile != null;
        if (collision) {
            Color color = tile.getColor((int) x % tileW, (int) y % tileH);
            collision = color.getAlpha() > 0;
        }
        return collision;
    }

    private float getFuturX(int delta) {
        float futurX = this.x;
        switch (this.direction) {
            case 1:
                futurX = this.x - .15f * delta;
                break;
            case 3:
                futurX = this.x + .15f * delta;
                break;
        }
        return futurX;
    }

    private float getFuturY(int delta) {
        float futurY = this.y;
        switch (this.direction) {
            case 0:
                futurY = this.y - .15f * delta;
                break;
            case 2:
                futurY = this.y + .15f * delta;
                break;
        }
        return futurY;
    }

}
