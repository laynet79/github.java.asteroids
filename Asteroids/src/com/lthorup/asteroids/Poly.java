/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lthorup.asteroids;

import java.awt.*;
import java.util.*;

/**
 *
 * @author layne
 */
public class Poly extends Shape {

    protected ArrayList<Vec2> points = new ArrayList<Vec2>();
    protected ArrayList<Vec2> transPoints = new ArrayList<Vec2>();
    private static int[] X = new int[100];
    private static int[] Y = new int[100];

    public Poly(Vec2 pos, double rot, Color color) {
        super(pos, rot, 0.0, color);
    }

    public void addPoint(Vec2 p) {
        points.add(p);
        transPoints.add(p);
        double r = p.length();
        if (r > radius)
            radius = r;
    }

    private void transform() {
        for (int i = 0; i < points.size(); i++) {
            transPoints.set(i, Vec2.add(Vec2.rotate(points.get(i),rot), pos));
        }
    }

    public boolean contains(Vec2 p) {
        boolean contained = false;
        for (int i=0,j=points.size()-1; i < points.size(); j=i++) {
            Vec2 a = transPoints.get(i);
            Vec2 b = transPoints.get(j);
            if (((a.y > p.y) != (b.y > p.y)) &&
                (p.x < ((b.x-a.x) * (p.y-a.y) / (b.y-a.y) + a.x)))
            {
                contained = !contained;
            }
        }
        return contained;
    }

    public boolean intersects(Poly other) {
        if (super.intersects(other)) { // do sphere test first
            for (Vec2 p : transPoints)
                    if (other.contains(p))
                return true;
            for (Vec2 p : other.transPoints)
                if (contains(p))
                    return true;
        }
        return false;
    }

    @Override
    public void update() {
        if (! active)
            return;
        super.update();
        transform();
    }

    public void paint(Graphics g) {
        if (! active)
            return;
        for (int i = 0; i < points.size(); i++) {
            X[i] = (int)transPoints.get(i).x;
            Y[i] = (int)transPoints.get(i).y;
        }
        g.setColor(color);
        g.fillPolygon(X,Y,points.size());
    }

}
