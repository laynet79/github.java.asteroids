/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lthorup.asteroids;
import java.awt.Point;

/**
 *
 * @author layne
 */
public class Vec2 {
    public double x, y;
    public Vec2(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public Vec2(Point p) {
        this.x = p.x;
        this.y = p.y;
    }

    public Point toPoint() {
        return new Point((int)(x+0.5),(int)(y+0.5));
    }

    public double length() {
        return Math.sqrt(x*x + y*y);
    }

    public static Vec2 add(Vec2 a, Vec2 b) {
        return new Vec2(a.x+b.x, a.y+b.y);
    }
    public static Vec2 sub(Vec2 a, Vec2 b) {
        return new Vec2(a.x-b.x, a.y-b.y);
    }

    public static Vec2 scale(Vec2 v, double s) {
        return new Vec2(v.x*s, v.y*s);
    }

    public static Vec2 rotate(Vec2 v, double angle) {
        double rad = Math.toRadians(angle);
        double cosine = Math.cos(rad);
        double sine = Math.sin(rad);
        return new Vec2(v.x*cosine - v.y*sine, v.x*sine + v.y*cosine);
    }

    public static Vec2 random(double minx, double miny, double maxx, double maxy) {
        double x = Math.random() * (maxx-minx) + minx;
        double y = Math.random() * (maxy-miny) + miny;
        return new Vec2(x,y);
    }

}
