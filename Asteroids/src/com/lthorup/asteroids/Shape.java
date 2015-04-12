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
public abstract class Shape {
    protected Color color;
    protected Vec2 pos;
    protected double rot;
    protected double radius;
    protected Vec2 vel;
    protected double rotVel;
    protected boolean active;

    protected static int boardWidth, boardHeight;

    public static void setBoard(int width, int height)
    { boardWidth = width; boardHeight = height; }

    public Shape(Vec2 pos, double rot, double radius, Color color) {
        this.pos = pos;
        this.rot = rot;
        this.radius = radius;
        this.color = color;
        this.vel = new Vec2(0,0);
        this.rotVel = 0.0;
        this.active = true;
    }

    public void setActive() { active = true; }
    public void setInactive() { active = false; }
    public boolean isActive() { return active; }

    public void setPos(Vec2 pos) { this.pos = pos; }
    public Vec2 getPos() { return pos; }
    public void setRot(double rot) { this.rot = rot; }
    public double getRot() { return rot; }
    public void setVel(Vec2 vel) { this.vel = vel; }
    public Vec2 getVel() { return vel; }

    public void setColor(Color color) {
        this.color = color;
    }

    public void accelerate(double forwardAcc, double rotAcc) {
        if (! active)
            return;
        vel.x += forwardAcc * Math.cos(Math.toRadians(rot));
        vel.y += forwardAcc * Math.sin(Math.toRadians(rot));
        rotVel += rotAcc;
    }

    public void rotate(double angle) {
        rot += angle;
        rot = normalizeAngle(rot);
    }

    public void slowRotation(double amount) {
        if (Math.abs(rotVel) < amount)
            rotVel = 0;
        if (rotVel > 0)
            rotVel -= amount;
        else if (rotVel < 0)
            rotVel += amount;
    }

    public void update() {
        if (! active)
            return;

        rot += rotVel;
        rot = normalizeAngle(rot);

        pos = Vec2.add(pos, vel);
        if ((pos.x + radius) < 0.0)
            pos.x = boardWidth + radius;
        else if ((pos.x - radius) > boardWidth)
            pos.x = -radius;
        if ((pos.y + radius) < 0.0)
            pos.y = boardHeight + radius;
        else if ((pos.y - radius) > boardHeight)
            pos.y = -radius;
    }

    public boolean intersects(Shape other) {
        return Vec2.sub(pos, other.pos).length() < (radius+other.radius);
    }

    private double normalizeAngle(double angle) {
        if (angle > 360.0)
            angle -= 360.0;
        else if (angle < 0.0)
            angle += 360.0;
        return angle;
    }

    public abstract void paint(Graphics g);

}
