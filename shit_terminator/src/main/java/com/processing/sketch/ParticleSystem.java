package com.processing.sketch;

import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

/**
 * @Author: huangyiqin
 * @Date: 5/16/2021 8:37 PM
 * @Modified: wangzilinn@gmail.com
 */
// A class to describe a group of Particles
// An ArrayList is used to manage the list of Particles

class ParticleSystem{
    private PApplet sketch;
    ArrayList<Particle> particles;
    PVector origin;

    ParticleSystem(PApplet sketch, PVector position) {
        this.sketch = sketch;
        origin = position.copy();
        particles = new ArrayList<Particle>();
    }

    void addParticle(PVector position) {
        particles.add(new Particle(sketch, position));
    }

    void run() {
        for (int i = particles.size()-1; i >= 0; i--) {
            Particle p = particles.get(i);
            p.run();
            if (p.isDead()) {
                particles.remove(i);
            }
        }
    }
}
