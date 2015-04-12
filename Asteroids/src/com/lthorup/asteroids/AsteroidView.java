package com.lthorup.asteroids;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

public class AsteroidView extends JPanel {

    private boolean initialized = false;
    private final int NUM_ROCKS = 5;
    private final double ROCK_MIN_SPEED = 2.0;
    private final double ROCK_MAX_SPEED = 4.0;
    private final double ROCK_MIN_ROT = 1.0;
    private final double ROCK_MAX_ROT = 4.0;
    private final int NUM_STARS = 200;

    private ArrayList<Rock> rocks = new ArrayList<Rock>();
    private ArrayList<Rock> deadRocks = new ArrayList<Rock>();
    private ArrayList<Rock> newRocks = new ArrayList<Rock>();
    private Ship ship;
    private int shipCntDown;
    private boolean gameOver = false;
    private int gameCntDown;
    private KeyBoard keys;
    private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    private ArrayList<Bullet> deadBullets = new ArrayList<Bullet>();
    private ArrayList<Star> stars = new ArrayList<Star>();

    /** Creates new form AsteroidView */
    public AsteroidView() {
        shipCntDown = gameCntDown = 0;
        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() { public void run() { update(); }}, 0, 30);
    }
    
    public void setKeyBoard(KeyBoard keys) {
        this.keys = keys;
        addKeyListener(keys);
    }

    public void initialize() {
        Shape.setBoard(getWidth(), getHeight());
        createRocks();
        createStars();
        createShip();
        initialized = true;
    }

    private void createRocks() {
        rocks.clear();
        for (int i = 0; i < NUM_ROCKS; i++) {
            Vec2 pos = Vec2.random(0,0,getWidth(),getHeight());
            double rot = Math.random() * 360.0;
            double speed = Math.random() * (ROCK_MAX_SPEED-ROCK_MIN_SPEED) + ROCK_MIN_SPEED;
            double rotSpeed = Math.random() * (ROCK_MAX_ROT-ROCK_MIN_ROT) + ROCK_MIN_ROT;
            if (Math.random() > 0.5)
                rotSpeed = -rotSpeed;
            Rock rock = new Rock(pos, rot, 1);
            rock.accelerate(speed, rotSpeed);
            rocks.add(rock);
        }
    }

    private Rock createChildRock(Rock p, Bullet b, double angle) {
        Vec2 cVel = Vec2.rotate(b.getVel(), angle);
        cVel = Vec2.scale(cVel, 0.3 / cVel.length());
        cVel = Vec2.add(cVel, p.getVel());
        Rock c = new Rock(p.getPos(), 0, p.getGen()+1);
        double rotSpeed = Math.random() * (ROCK_MAX_ROT-ROCK_MIN_ROT) + ROCK_MIN_ROT;
        c.setVel(cVel);
        c.accelerate(0, rotSpeed);
        return c;
    }

    private void createStars() {
        for (int i = 0; i < NUM_STARS; i++)
            stars.add(new Star(Vec2.random(0,0,getWidth(),getHeight())));
    }

    private void createShip() {
        ship = new Ship(new Vec2(getWidth()/2,getHeight()/2), -90.0);
    }

    public void update() {
        if (! initialized)
            return;

        if (! gameOver && rocks.isEmpty()) {
            gameOver = true;
            gameCntDown = 100;
        }
        if (gameOver) {
            gameCntDown--;
            if (gameCntDown == 0) {
                createRocks();
                gameOver = false;
            }
        }

        for (Rock r : rocks)
            r.update();
        ship.update();
        for (Bullet b : bullets)
            b.update();

        keys.update();
        handleShip(keys);
        handleRocks();
        handleBullets(keys);
        handleStars();

        repaint();
    }

    private void handleShip(KeyBoard keys) {
        if (keys.keyDown(KeyEvent.VK_UP))
            ship.accelerate(0.25, 0);
        if (keys.keyDown(KeyEvent.VK_DOWN))
            ship.accelerate(-0.25, 0);
        if (keys.keyDown(KeyEvent.VK_LEFT))
            ship.rotate(-5);
        if (keys.keyDown(KeyEvent.VK_RIGHT))
            ship.rotate(5);

        if (shipCntDown > 0) {
            shipCntDown--;
            if (shipCntDown <= 0) {
                ship.setColor(Color.GREEN);
            }
        }
    }

    private void handleRocks() {
        for (Rock rock : rocks) {
            if (rock.isActive() && ship.intersects(rock)) {
                ship.setColor(Color.RED);
                shipCntDown = 100;
            }
        }
        for (Rock rock : deadRocks)
            rocks.remove(rock);
        deadRocks.clear();
    }

    private void handleBullets(KeyBoard keys) {
        if (keys.keyJustPressed(KeyEvent.VK_SPACE))
            bullets.add(new Bullet(ship.getPos(), ship.getRot()));

        for (Bullet b : bullets) {
            if (b.isActive()) {
                for (Rock rock : rocks) {
                    if (rock.isActive() && rock.contains(b.getPos())) {
                        deadBullets.add(b);
                        deadRocks.add(rock);
                        if (rock.getGen() < 3) {
                            newRocks.add(createChildRock(rock, b, 90));
                            newRocks.add(createChildRock(rock, b, -90));
                        }
                    }
                }
            }
            else {
                deadBullets.add(b);
            }
        }
        for (Bullet b : deadBullets)
            bullets.remove(b);
        deadBullets.clear();
        for (Rock rock : deadRocks)
            rocks.remove(rock);
        deadRocks.clear();
        for (Rock rock : newRocks)
            rocks.add(rock);
        newRocks.clear();
    }

    private void handleStars() {
        for (Star s : stars)
            s.update();
    }

    @Override
    public void paint(Graphics g) {
    	if (! initialized)
    		initialize();
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        for (Star s : stars)
            s.paint(g);
        for (Bullet b : bullets)
            b.paint(g);
        ship.paint(g);
        for (Rock r : rocks)
            r.paint(g);
    }

}
