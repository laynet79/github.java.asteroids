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
public class Rock extends Poly {

    private static final Color rockColor = Color.BLUE;
    private static final double MIN_RADIUS = 10.0;
    private static final double MAX_RADIUS = 20.0;
    private static final int NUM_POINTS = 16;
    private double rotSpeed;
    private int gen;
    
    public Rock(Vec2 pos, double rot, int gen) {
        super(pos, rot, rockColor);
        this.gen = gen;
        double scale = 1;
        if (gen == 2)
            scale = 0.6;
        else if (gen == 3)
            scale = 0.35;
        double angle = 0.0;
        double dAngle = Math.toRadians(360.0/NUM_POINTS);
        for (int i = 0; i < NUM_POINTS; i++) {
            double r = (Math.random() * (MAX_RADIUS-MIN_RADIUS) + MIN_RADIUS) * scale;
            double x = r * Math.cos(angle);
            double y = r * Math.sin(angle);
            addPoint(new Vec2(x,y));
            angle += dAngle;
        }
    }

    public int getGen() { return gen; }
}
