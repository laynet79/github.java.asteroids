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
public class Ship extends Poly {

    private static final Color shipColor = Color.GREEN;

    public Ship(Vec2 pos, double rot) {
        super(pos, rot, shipColor);
        addPoint(new Vec2(10,0));
        addPoint(new Vec2(-10,-5));
        addPoint(new Vec2(-7,0));
        addPoint(new Vec2(-10,5));
    }

    @Override
    public void update() {
        super.update();
        slowRotation(0.1);
    }
    
}
