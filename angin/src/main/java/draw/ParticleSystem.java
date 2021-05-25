package draw;

import draw.printer.ParticlePrinter;
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

public class ParticleSystem {
    private final PApplet sketch;
    LinkedList<ParticlePrinter> particlePrinters;
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

    public ParticleSystem(PApplet sketch, float yVelocity, float xVelocity, int life) {
        this.sketch = sketch;
        particlePrinters = new LinkedList<>();
        this.yVelocity = yVelocity;
        this.xVelocity = xVelocity;
        this.life = life;
    }

    public void addParticle(PVector position) {
        particlePrinters.add(new ParticlePrinter(sketch, position, yVelocity, xVelocity, life));
    }

    public void run() {
        Iterator<ParticlePrinter> particlesIter = particlePrinters.iterator();
        while (particlesIter.hasNext()) {
            ParticlePrinter p = particlesIter.next();
            p.run();
            if (p.isDead()) {
                particlesIter.remove();
            }
        }
    }

}
