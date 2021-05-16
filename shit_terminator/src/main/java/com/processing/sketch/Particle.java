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
    private PApplet sketch;
    PVector position;
    PVector velocity;
    PVector acceleration;
    float lifespan;

    Particle(PApplet sketch, PVector l) {
        this.sketch = sketch;
        acceleration = new PVector((float) 0.05, 0);
        velocity = new PVector(this.sketch.random(-1, 1), this.sketch.random(-2, 2));
        position = l.copy();
        lifespan = 255.0f;
    }

    void run() {
        update();
        display();
    }

    // Method to update position
    void update() {
        velocity.add(acceleration);
        position.add(velocity);
        lifespan -= 5.0;
    }

    // Method to display
    void display() {
        sketch.stroke(0, lifespan);
        sketch.fill(0, lifespan);
        sketch.ellipse(position.x, position.y, 8, 8);
    }

    // Is the particle still useful?
    boolean isDead() {
        if (lifespan < 0.0) {
            return true;
        } else {
            return false;
        }
    }
}
