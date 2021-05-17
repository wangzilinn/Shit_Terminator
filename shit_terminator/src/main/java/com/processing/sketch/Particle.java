package com.processing.sketch;

import processing.core.PApplet;
import processing.core.PVector;

/**
 * @Author: huangyiqin
 * @Date: 5/16/2021 8:35 PM
 * @Modified: wangzilinn@gmail.com
 */
// A simple Particle class

class Particle{
    private final PApplet sketch;
    PVector position;
    PVector velocity;
    PVector acceleration;
    int lifespan;

    Particle(PApplet sketch, PVector position, float height, float width, int lifespan) {
        this.sketch = sketch;
        acceleration = new PVector((float) 0.05, 0);
        velocity = new PVector(this.sketch.random(0, width), this.sketch.random(-(height / 2), height / 2));
        this.position = position.copy();
        this.lifespan = lifespan;
    }

    void run() {
        update();
        display();
    }

    // Method to update position
    void update() {
        velocity.add(acceleration);
        position.add(velocity);
        lifespan -= 5;
    }

    // Method to display
    void display() {
        sketch.stroke(0, lifespan);
        sketch.fill(0, lifespan);
        sketch.ellipse(position.x, position.y, 8, 8);
    }

    // Is the particle still useful?
    boolean isDead() {
        return lifespan < 0;
    }
}
