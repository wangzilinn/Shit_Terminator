package entity;

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

public class ParticleGroup {
    private final PApplet sketch;
    LinkedList<Particle> particles;
    /**
     * 离子束的的纵向速度
     */
    float yVelocity;
    /**
     * 离子束的横向速度
     */
    float xVelocity;
    /**
     * 粒子从诞生到消失的帧数
     */
    int life;

    public ParticleGroup(PApplet sketch, float yVelocity, float xVelocity, int life) {
        this.sketch = sketch;
        particles = new LinkedList<>();
        this.yVelocity = yVelocity;
        this.xVelocity = xVelocity;
        this.life = life;
    }

    public void addParticle(PVector position) {
        particles.add(new Particle(sketch, position, yVelocity, xVelocity, life));
    }

    public void run() {
        Iterator<Particle> particlesIter = particles.iterator();
        while (particlesIter.hasNext()) {
            Particle p = particlesIter.next();
            p.run();
            if (p.isDead()) {
                particlesIter.remove();
            }
        }
    }

}
