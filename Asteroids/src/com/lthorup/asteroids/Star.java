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
public class Star extends Circle {

    private static final double STAR_RADIUS = 0.5;
    private static final Color STAR_COLOR = Color.WHITE;
    private int intensity;

    public Star(Vec2 pos) {
        super(pos, 0, STAR_RADIUS, STAR_COLOR);
        intensity = (int)(Math.random() * 200) + 55;
        color = new Color(intensity,intensity,intensity);
    }

    @Override
    public void update() {
        int dc = (int)(Math.random() * 20);
        if (Math.random() < 0.5)
            dc = -dc;
        intensity += dc;
        if (intensity < 50)
            intensity = 50;
        else if (intensity > 255)
            intensity = 255;
        color = new Color(intensity,intensity,intensity);
    }
}
