package com.processing.sketch;
import processing.core.PApplet;
import processing.core.PVector;

/**
 * @Author: huangyiqin
 * @Date: 5/16/2021 8:37 PM
 * @Modified: wangzilinn@gmail.com
 */
class Bullet {
    private final PApplet sketch;

    PVector position;
    PVector size;
    ParticleGroup ps;

    int distance;

    /**
     * 这个子弹可以让坏蛋掉多少血
     */
    float damage;

    Bullet(PApplet sketch, float x, float y, float damage) {
        this.sketch = sketch;
        this.position = new PVector(x, y);
        this.size = new PVector(10, 10);
        this.damage = damage;
        this.ps = new ParticleGroup(sketch, 1, 5, 100);
    }

    @CalledByDraw
    void updateAndDraw() {
        distance++;
        position.x -= 5;
        sketch.fill(0);
        sketch.ellipse(position.x, position.y, size.x, size.y);
        //把原来的所有粒子向左移动
        ps.updatePosition(new PVector(-5, 0));
        //使用distance降低粒子出现的频率,防止卡顿
        ps.addParticle(position);
        ps.run();

    }
}
