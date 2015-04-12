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
public class Circle extends Shape {

    public Circle(Vec2 pos, double rot, double radius, Color color) {
        super(pos, rot, radius, color);
    }

    public void paint(Graphics g) {
        if (! active)
            return;
        g.setColor(color);
        g.fillOval((int)(pos.x - radius), (int)(pos.y-radius), (int)(radius*2), (int)(radius*2));
    }
}
