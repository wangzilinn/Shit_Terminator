package com.processing.sketch;

import processing.core.PApplet;
import processing.core.PVector;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * @Author: huangyiqin
 * @Date: 5/16/2021 8:37 PM
 * @Modified: wangzilinn@gmail.com
 */
// A class to describe a group of Particles
// An ArrayList is used to manage the list of Particles

class ParticleGroup {
    private final PApplet sketch;
    LinkedList<Particle> particles;
    /**
     * 离子束的高度
     */
    float height;
    /**
     * 离子束的宽度
     */
    float width;
    /**
     * 粒子从诞生到消失的帧数
     */
    int life;

    ParticleGroup(PApplet sketch, float height, float width, int life) {
        this.sketch = sketch;
        particles = new LinkedList<>();
        this.height = height;
        this.width = width;
        this.life = life;
    }

    void addParticle(PVector position) {
        particles.add(new Particle(sketch, position, height, width, life));
    }

    void run() {
        Iterator<Particle> particlesIter = particles.iterator();
        while (particlesIter.hasNext()) {
            Particle p = particlesIter.next();
            p.run();
            if (p.isDead()) {
                particlesIter.remove();
            }
        }
    }

    /**
     * 平移所有粒子的位置
     *
     * @param offsetPosition 粒子相对当前位置平移的单位
     */
    void updatePosition(PVector offsetPosition) {
        for (Particle particle : particles) {
            particle.position.add(offsetPosition);
        }
    }

}
