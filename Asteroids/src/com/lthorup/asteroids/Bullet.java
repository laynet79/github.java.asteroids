/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lthorup.asteroids;

import java.awt.*;

/**
 *
 * @author layne
 */
public class Bullet extends Circle {

    private static final double BULLET_RADIUS = 2.0;
    private static final Color BULLET_COLOR = Color.RED;
    private static final double BULLET_SPEED = 10.0;

    public Bullet(Vec2 pos, double rot) {
        super(pos, rot, BULLET_RADIUS, BULLET_COLOR);
        accelerate(BULLET_SPEED, 0);
    }

    @Override
    public void update() {
        if (! active)
            return;
        super.update();
        if (pos.x < 0 || pos.x >= boardWidth || pos.y < 0 || pos.y >= boardHeight)
            active = false;
    }
}
